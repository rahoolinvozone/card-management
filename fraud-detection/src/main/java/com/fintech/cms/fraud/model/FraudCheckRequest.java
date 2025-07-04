package com.fintech.cms.fraud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FraudCheckRequest {
    private UUID cardId;
    private BigDecimal amount;
}
