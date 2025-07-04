package com.fintech.cms.fraud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
public class FraudPolicy {
    private UUID id;
    private BigDecimal fraudLimit;
    private Duration timeInterval;

    public FraudPolicy() {
        this.id = UUID.randomUUID();
        this.fraudLimit = new BigDecimal("10000.00");
        this.timeInterval = Duration.ofHours(1);
    }
}