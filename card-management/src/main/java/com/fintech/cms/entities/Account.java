package com.fintech.cms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String status; // ACTIVE / INACTIVE

    @Column(nullable = false)
    private BigDecimal balance;
}