package com.example.TransportHC.service;

import com.example.TransportHC.dto.request.TransactionCreateRequest;
import com.example.TransportHC.dto.request.TransactionDetailCreateRequest;
import com.example.TransportHC.dto.response.ProductResponse;
import com.example.TransportHC.dto.response.TransactionDetailResponse;
import com.example.TransportHC.dto.response.TransactionResponse;
import com.example.TransportHC.dto.response.UserResponse;
import com.example.TransportHC.entity.Product;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.Transaction;
import com.example.TransportHC.entity.TransactionDetail;
import com.example.TransportHC.enums.TransactionType;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.ProductRepository;
import com.example.TransportHC.repository.TransactionDetailRepository;
import com.example.TransportHC.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionDetailService {
    TransactionRepository transactionRepository;
    TransactionDetailRepository transactionDetailRepository;
    ProductRepository productRepository;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public TransactionDetailResponse createTransactionResponse(TransactionDetailCreateRequest request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .transaction(transaction)
                .product(product)
                .quantityChange(request.getQuantityChange())
                .build();

        transactionDetailRepository.save(transactionDetail);
        changeStockInventory(transactionDetail);
        return entityToTransactionDetail(transactionDetail);
    }

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public List<TransactionDetailResponse> viewTransactionDetailResponse() {
        return transactionDetailRepository.findAll().stream()
                .map(this::entityToTransactionDetail)
                .toList();
    }

//    @PreAuthorize("hasAuthority('CREATE_COST')")
//    TransactionDetailResponse transactionDetailResponse(UUID id, TransactionDetailCreateRequest request) {
//        TransactionDetail transactionDetail = transactionDetailRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_DETAIL_NOT_FOUND));
//
//        transactionDetail.set
//
//    }

    /*
Tạo:
Khi tạo một bản ghi bao gồm thông tin cơ bản và thuộc tính change, before sẽ bằng quantity ở trong Inventory, giá trị after sẽ bằng before +- change, sau đó sẽ gán lại vào quantity để cập nhật số lượng tồn kho. Giá trị after cũng sẽ là giá trị tồn kho tại thời điểm đó
VD: quantity = 50 = before, change = 50, after = newquantity = 100
VD quantity = 100 = before, change = 100, after = newquantity = 200

Xoá:
// khả năng bỏ before, after
Khi xoá 1 bản ghi thì thực hiện xuất ra số lượng đấy rồi xoá bản ghi đi

Update: khi cập nhật thông tin khả năng sẽ xoá bản ghi cũ
// so sánh số lượng chênh lệch và cập nhật thêm hoặc bớt
 đi và cập nhật thông tin mới
     */

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public void deleteTransactionDetail(UUID id) {
        TransactionDetail transactionDetail = transactionDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_DETAIL_NOT_FOUND));
        transactionDetailRepository.delete(transactionDetail);

    }
    private void changeStockInventory(TransactionDetail transactionDetail) {
        Integer change = transactionDetail.getQuantityChange() == null ? 0 : transactionDetail.getQuantityChange();
        Integer quantity = transactionDetail.getProduct().getInventory().getQuantity();

        transactionDetail.setQuantityBefore(quantity);
        if (transactionDetail.getTransaction().getType() == TransactionType.IN) {
            transactionDetail.setQuantityAfter(quantity + change);
        } else {
            if (quantity < change) {
                throw new AppException(ErrorCode.INVENTORY_NOT_ENOUGH);
            } else {
                transactionDetail.setQuantityAfter(quantity - change);
            }
        }
        transactionDetail.getProduct().getInventory().setQuantity(transactionDetail.getQuantityAfter());
        transactionDetail.getProduct().getInventory().setUpToDate(LocalDateTime.now());
        transactionDetailRepository.save(transactionDetail);
    }


    private TransactionDetailResponse entityToTransactionDetail(TransactionDetail transactionDetail) {
        // Chuyển đổi Set<Role> sang Set<String> (role codes)
        Set<String> roleCodes = transactionDetail.getTransaction().getCreatedBy().getRoles() != null
                ? transactionDetail.getTransaction().getCreatedBy().getRoles().stream()
                .map(Role::getCode)
                .collect(Collectors.toSet()): new HashSet<>();

        UserResponse userResponse = UserResponse.builder()
                .id(transactionDetail.getTransaction().getCreatedBy().getUserId())
                .username(transactionDetail.getTransaction().getCreatedBy().getUsername())
                .fullName(transactionDetail.getTransaction().getCreatedBy().getFullName())
                .address(transactionDetail.getTransaction().getCreatedBy().getAddress())
                .phoneNumber(String.valueOf(transactionDetail.getTransaction().getCreatedBy().getPhoneNumber()))
                .status(transactionDetail.getTransaction().getCreatedBy().getStatus())
                .basicSalary(transactionDetail.getTransaction().getCreatedBy().getBasicSalary())
                .advanceMoney(transactionDetail.getTransaction().getCreatedBy().getAdvanceMoney())
                .roles(roleCodes)
                .build();

        TransactionResponse transactionResponse = TransactionResponse.builder()
                .transactionId(transactionDetail.getTransaction().getTransactionId())
                .transactionType(transactionDetail.getTransaction().getType())
                .date(transactionDetail.getTransaction().getDate())
                .location(transactionDetail.getTransaction().getLocation())
                .note(transactionDetail.getTransaction().getNote())
                .createdBy(userResponse)
                .build();

        ProductResponse productResponse = ProductResponse.builder()
                .id(transactionDetail.getProduct().getProductId())
                .name(transactionDetail.getProduct().getName())
                .category(transactionDetail.getProduct().getCategory())
                .price(transactionDetail.getProduct().getPrice())
                .build();

        return TransactionDetailResponse.builder()
                .transactionDetailId(transactionDetail.getId())
                .transaction(transactionResponse)
                .product(productResponse)
                .quantityChange(transactionDetail.getQuantityChange())
                .quantityAfter(transactionDetail.getQuantityAfter())
                .quantityBefore(transactionDetail.getQuantityBefore())
                .build();
    }

}