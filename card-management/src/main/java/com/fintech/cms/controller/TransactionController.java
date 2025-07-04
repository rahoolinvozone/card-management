package com.fintech.cms.controller;

import com.fintech.cms.dto.TransactionDto;
import com.fintech.cms.entities.*;
import com.fintech.cms.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService txnService;

    public TransactionController(TransactionService txnService) {
        this.txnService = txnService;
    }

    @PostMapping
    @Operation(summary = "Create and process a new transaction")
    public Transaction createTransaction(@RequestBody TransactionDto transactionDto){
        return txnService.processTransaction(transactionDto);
    }
}
