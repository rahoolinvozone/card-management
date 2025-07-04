package com.fintech.cms;

import com.fintech.cms.controller.FraudClient;
import com.fintech.cms.dto.FraudCheckRequest;
import com.fintech.cms.dto.FraudCheckResponse;
import com.fintech.cms.dto.TransactionDto;
import com.fintech.cms.entities.Account;
import com.fintech.cms.entities.Card;
import com.fintech.cms.entities.Transaction;
import com.fintech.cms.repository.AccountRepository;
import com.fintech.cms.repository.CardRepository;
import com.fintech.cms.repository.TransactionRepository;
import com.fintech.cms.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private CardRepository cardRepo;

    @Mock
    private AccountRepository accountRepo;

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private FraudClient fraudClient;

    private UUID cardId;
    private UUID accountId;
    private Card card;
    private Account account;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cardId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        account = new Account(accountId, "ACTIVE", new BigDecimal("1000.00"));
        card = new Card(cardId, "ACTIVE", LocalDate.now().plusYears(1), "1234-5678-9012-3456", account);
    }

    @Test
    public void testProcessTransaction_creditSuccess() {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionAmount(new BigDecimal("200.00"));
        dto.setTransactionType("C");
        dto.setCardId(cardId);

        when(cardRepo.findById(cardId)).thenReturn(Optional.of(card));
        when(accountRepo.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);
        when(fraudClient.checkFraud(any(FraudCheckRequest.class))).thenReturn(new FraudCheckResponse(false,"Transaction approved"));

        Transaction result = transactionService.processTransaction(dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("1200.00"), account.getBalance());
        verify(transactionRepo, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testProcessTransaction_debitSuccess() {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionAmount(new BigDecimal("300.00"));
        dto.setTransactionType("D");
        dto.setCardId(cardId);

        when(cardRepo.findById(cardId)).thenReturn(Optional.of(card));
        when(accountRepo.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);
        when(fraudClient.checkFraud(any(FraudCheckRequest.class))).thenReturn(new FraudCheckResponse(false,"Transaction approved"));

        Transaction result = transactionService.processTransaction(dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("700.00"), account.getBalance());
        verify(transactionRepo, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testProcessTransaction_debitInsufficientBalance() {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionAmount(new BigDecimal("1500.00"));
        dto.setTransactionType("D");
        dto.setCardId(cardId);

        when(cardRepo.findById(cardId)).thenReturn(Optional.of(card));
        when(accountRepo.findById(accountId)).thenReturn(Optional.of(account));

        Exception ex = assertThrows(RuntimeException.class, () -> transactionService.processTransaction(dto));
        assertTrue(ex.getMessage().contains("Insufficient balance"));
    }

    @Test
    public void testProcessTransaction_cardExpired() {
        card.setExpiry(LocalDate.now().minusDays(1));
        TransactionDto dto = new TransactionDto();
        dto.setTransactionAmount(new BigDecimal("100.00"));
        dto.setTransactionType("C");
        dto.setCardId(cardId);

        when(cardRepo.findById(cardId)).thenReturn(Optional.of(card));

        Exception ex = assertThrows(RuntimeException.class, () -> transactionService.processTransaction(dto));
        assertTrue(ex.getMessage().contains("Card expired"));
    }
}
