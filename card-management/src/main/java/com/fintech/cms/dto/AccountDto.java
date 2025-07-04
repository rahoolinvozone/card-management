package com.fintech.cms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @NonNull
    @Schema(description = "Status of the account (ACTIVE or INACTIVE)")
    private String status;

    @NonNull
    @Schema(description = "Current balance of the account")
    private BigDecimal balance;
}
