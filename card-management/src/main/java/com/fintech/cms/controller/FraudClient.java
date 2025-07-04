package com.fintech.cms.controller;

import com.fintech.cms.dto.FraudCheckRequest;
import com.fintech.cms.dto.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fraud-client", url = "${client.fraud.url}")
public interface FraudClient {
    @PostMapping("/fraud-check")
    FraudCheckResponse checkFraud(@RequestBody FraudCheckRequest request);
}