package com.fintech.cms.controller;

import com.fintech.cms.dto.AccountDto;
import com.fintech.cms.entities.*;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.repository.*;
import com.fintech.cms.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account details by ID")
    public Account getAccount(@PathVariable UUID id) {
        return accountService.getAccount(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing account")
    public Account updateAccount(@PathVariable UUID id, @RequestBody AccountDto updated) {
        return accountService.updateAccount(id, updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an account by ID")
    public void deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }

    @GetMapping
    @Operation(summary = "List all accounts")
    public List<Account> listAccounts() {
        return accountService.listAccounts();
    }
}