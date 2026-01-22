package com.example.TransportHC.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

import com.example.TransportHC.enums.ApproveStatus;
import com.example.TransportHC.enums.TransactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @Enumerated(EnumType.STRING)
    ApproveStatus approveStatus;

    LocalDateTime date;
    String location;
    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    User createdBy;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<TransactionDetail> transactionDetails;

    @OneToOne(mappedBy = "transaction")
    private Schedule schedule;
}
