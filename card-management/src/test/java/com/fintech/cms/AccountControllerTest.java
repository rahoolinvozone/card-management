package com.fintech.cms;

import com.fintech.cms.controller.AccountController;
import com.fintech.cms.dto.AccountDto;
import com.fintech.cms.entities.Account;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private UUID accountId;
    private Account account;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        accountId = UUID.randomUUID();
        account = new Account(accountId, "ACTIVE", new BigDecimal("1000.00"));
    }

    @Test
    public void testCreateAccount_success() {
        AccountDto dto = new AccountDto();
        dto.setStatus("ACTIVE");
        dto.setBalance(new BigDecimal("500.00"));

        when(accountService.createAccount(dto)).thenReturn(new Account(UUID.randomUUID(), dto.getStatus(), dto.getBalance()));

        Account result = accountController.createAccount(dto);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        assertEquals(new BigDecimal("500.00"), result.getBalance());
    }

    @Test
    public void testGetAccount_success() {
        when(accountService.getAccount(accountId)).thenReturn(account);

        Account result = accountController.getAccount(accountId);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
    }

    @Test
    public void testUpdateAccount_success() {
        AccountDto dto = new AccountDto();
        dto.setStatus("INACTIVE");
        dto.setBalance(new BigDecimal("200.00"));

        Account updatedAccount = new Account(accountId, dto.getStatus(), dto.getBalance());

        when(accountService.updateAccount(accountId, dto)).thenReturn(updatedAccount);

        Account result = accountController.updateAccount(accountId, dto);

        assertEquals("INACTIVE", result.getStatus());
        assertEquals(new BigDecimal("200.00"), result.getBalance());
    }

    @Test
    public void testDeleteAccount_success() {
        doNothing().when(accountService).deleteAccount(accountId);

        accountController.deleteAccount(accountId);

        verify(accountService, times(1)).deleteAccount(accountId);
    }

    @Test
    public void testGetAccount_notFound() {
        when(accountService.getAccount(accountId)).thenThrow(new NotFoundException("Account not found"));

        assertThrows(NotFoundException.class, () -> accountController.getAccount(accountId));
    }
}
