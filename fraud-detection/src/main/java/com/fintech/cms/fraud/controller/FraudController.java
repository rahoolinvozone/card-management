package com.fintech.cms.fraud.controller;

import com.fintech.cms.fraud.model.FraudCheckRequest;
import com.fintech.cms.fraud.model.FraudCheckResponse;
import com.fintech.cms.fraud.model.FraudPolicy;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/fraud-check")
public class FraudController {

    private final FraudPolicy policy = new FraudPolicy();

    // Simulated in-memory transaction history
    private final Map<UUID, List<Instant>> transactionHistory = new ConcurrentHashMap<>();

    @PostMapping
    public FraudCheckResponse checkFraud(@RequestBody FraudCheckRequest request) {
        UUID cardId = request.getCardId();
        BigDecimal amount = request.getAmount();
        Instant now = Instant.now();

        // Check if amount exceeds fraud limit
        if (amount.compareTo(policy.getFraudLimit()) > 0) {
            return new FraudCheckResponse(true, "Amount exceeds fraud limit of $" + policy.getFraudLimit());
        }

        // Frequency check within timeInterval
        List<Instant> txns = transactionHistory.getOrDefault(cardId, new ArrayList<>());
        txns.removeIf(t -> t.isBefore(now.minus(policy.getTimeInterval())));

        if (txns.size() >= 8) {
            return new FraudCheckResponse(true, "Transaction frequency exceeds 8/hour limit");
        }

        txns.add(now);
        transactionHistory.put(cardId, txns);

        return new FraudCheckResponse(false, "Transaction approved");
    }
}
