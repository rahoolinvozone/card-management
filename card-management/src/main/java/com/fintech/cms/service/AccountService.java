package com.fintech.cms.service;

import com.fintech.cms.dto.AccountDto;
import com.fintech.cms.entities.Account;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public Account createAccount(AccountDto accountDto) {
        if (!accountDto.getStatus().equalsIgnoreCase("ACTIVE") && !accountDto.getStatus().equalsIgnoreCase("INACTIVE")){
            throw  new RuntimeException("Status can be only ACTIVE or INACTIVE");
        }
        Account account = new Account(UUID.randomUUID(), accountDto.getStatus(), accountDto.getBalance());
        return accountRepo.save(account);
    }

    public Account getAccount(UUID id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    public Account updateAccount(UUID id, AccountDto updated) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        if (!updated.getStatus().equalsIgnoreCase("ACTIVE") && !updated.getStatus().equalsIgnoreCase("INACTIVE")){
            throw  new RuntimeException("Status can be only ACTIVE or INACTIVE");
        }

        account.setStatus(updated.getStatus());
        account.setBalance(updated.getBalance());
        return accountRepo.save(account);
    }

    public void deleteAccount(UUID id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        accountRepo.delete(account);
    }

    public List<Account> listAccounts() {
        return accountRepo.findAll();
    }
}
