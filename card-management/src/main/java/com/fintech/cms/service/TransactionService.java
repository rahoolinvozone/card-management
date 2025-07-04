
package com.fintech.cms.service;

import com.fintech.cms.controller.FraudClient;
import com.fintech.cms.dto.FraudCheckRequest;
import com.fintech.cms.dto.FraudCheckResponse;
import com.fintech.cms.dto.TransactionDto;
import com.fintech.cms.entities.*;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {
    private final CardRepository cardRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository txnRepo;

    private final FraudClient fraudClient;

    public TransactionService(CardRepository cardRepo, AccountRepository accountRepo, TransactionRepository txnRepo, FraudClient fraudClient) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
        this.txnRepo = txnRepo;
        this.fraudClient = fraudClient;
    }

    @Transactional
    public Transaction processTransaction(TransactionDto txn) {
        Card card = cardRepo.findById(txn.getCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        if (!"ACTIVE".equalsIgnoreCase(card.getStatus())) {
            throw new RuntimeException("Card not active");
        }

        if (card.getExpiry().isBefore(LocalDate.now())) {
            throw new RuntimeException("Card expired");
        }

        Account account = card.getAccount();

        if (!"ACTIVE".equalsIgnoreCase(account.getStatus())) {
            throw new RuntimeException("Account not active");
        }

        BigDecimal amount = txn.getTransactionAmount();

        if(!"D".equalsIgnoreCase(txn.getTransactionType()) && !"C".equalsIgnoreCase(txn.getTransactionType())) {
            throw new RuntimeException("Invalid transaction type");
        }

        if ("D".equalsIgnoreCase(txn.getTransactionType()) && account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        FraudCheckRequest fraudRequest = new FraudCheckRequest(card.getId(), txn.getTransactionAmount());
        FraudCheckResponse fraudResponse = fraudClient.checkFraud(fraudRequest);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                amount,
                new Timestamp(System.currentTimeMillis()),
                txn.getTransactionType(),
                fraudResponse.isFraud() ? "REJECTED" : "APPROVED",
                card,
                account
        );

        if (fraudResponse.isFraud()) {
            return txnRepo.save(transaction);
        }

        if ("D".equals(txn.getTransactionType())) {
            account.setBalance(account.getBalance().subtract(txn.getTransactionAmount()));
        } else if ("C".equals(txn.getTransactionType())) {
            account.setBalance(account.getBalance().add(txn.getTransactionAmount()));
        }


        accountRepo.save(account);
        return txnRepo.save(transaction);
    }
}