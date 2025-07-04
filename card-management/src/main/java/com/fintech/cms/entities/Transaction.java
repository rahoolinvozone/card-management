package com.fintech.cms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Column(nullable = false)
    private Timestamp transactionDate;

    @Column(nullable = false)
    private String transactionType; // C or D

    private String response;

    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Card card;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}