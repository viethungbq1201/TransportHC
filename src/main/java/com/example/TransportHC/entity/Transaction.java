package com.example.TransportHC.entity;

import com.example.TransportHC.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Transaction {

    @Id
    @GeneratedValue
    UUID transactionId;

    @Enumerated(EnumType.STRING)
    TransactionType type;

    LocalDateTime date;
    String location;
    String note;

    @ManyToOne
    User createdBy;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    List<TransactionDetail> details;
}
