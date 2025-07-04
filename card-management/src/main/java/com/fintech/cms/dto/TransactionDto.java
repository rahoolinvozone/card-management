package com.fintech.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @NonNull
    @Schema(description = "Amount to be transacted")
    private BigDecimal transactionAmount;

    @NonNull
    @Schema(description = "Transaction type: C for Credit, D for Debit")
    private String transactionType;

    @NonNull
    @Schema(description = "ID of the card to perform the transaction with")
    private UUID cardId;
}
