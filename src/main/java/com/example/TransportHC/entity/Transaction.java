package com.example.TransportHC.entity;

import com.example.TransportHC.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID transactionId;

    @Enumerated(EnumType.STRING)
    TransactionType type;

    LocalDateTime date;
    String location;
    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    User createdBy;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    List<TransactionDetail> transactionDetails;


}
