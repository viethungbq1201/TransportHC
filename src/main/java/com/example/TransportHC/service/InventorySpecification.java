package com.example.TransportHC.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;

import com.example.TransportHC.dto.request.InventoryFilterRequest;
import com.example.TransportHC.entity.Category;
import com.example.TransportHC.entity.Inventory;
import com.example.TransportHC.entity.Product;

public class InventorySpecification {

    public static Specification<Inventory> filter(InventoryFilterRequest request) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Inventory, Product> product = root.join("product", JoinType.INNER);
            Join<Product, Category> category = product.join("category", JoinType.INNER);

            if (request.getProductName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(product.get("name")),
                        "%" + request.getProductName().toLowerCase() + "%"));
            }
            if (request.getCategoryName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(category.get("name")),
                        "%" + request.getCategoryName().toLowerCase() + "%"));
            }

            if (request.getQuantityMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), request.getQuantityMin()));
            }
            if (request.getQuantityMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), request.getQuantityMax()));
            }

            if (request.getFromDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("upToDate"), request.getFromDate()));
            }
            if (request.getToDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("upToDate"), request.getToDate()));
            }

            Expression<Double> total = criteriaBuilder.prod(
                    product.get("price").as(Double.class), root.get("quantity").as(Double.class));
            if (request.getTotalMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(total, request.getTotalMin()));
            }
            if (request.getTotalMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(total, request.getTotalMax()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
