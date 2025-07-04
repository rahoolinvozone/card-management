
package com.fintech.cms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String status; // ACTIVE / INACTIVE

    @Column(nullable = false)
    private LocalDate expiry;

    @Column(name = "card_number", nullable = false)
    private String cardNumber; // stored encrypted

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
}