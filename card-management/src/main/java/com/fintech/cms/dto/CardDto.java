package com.fintech.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    @NonNull
    @Schema(description = "Status of the card (ACTIVE or INACTIVE)")
    private String status;

    @NonNull
    @Schema(description = "Expiry date of the card")
    private LocalDate expiry;

    @NonNull
    @Schema(description = "card number")
    private String cardNumber;

    @NonNull
    @Schema(description = "ID of the associated account")
    private UUID accountId;
}
