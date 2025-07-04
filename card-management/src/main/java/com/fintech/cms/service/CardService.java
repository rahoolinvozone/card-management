package com.fintech.cms.service;

import com.fintech.cms.dto.CardDto;
import com.fintech.cms.entities.Account;
import com.fintech.cms.entities.Card;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.repository.AccountRepository;
import com.fintech.cms.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardService {

    private final CardRepository cardRepo;
    private final AccountRepository accountRepo;

    public CardService(CardRepository cardRepo, AccountRepository accountRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
    }

    public Card createCard(CardDto cardDto) {
        Account account = accountRepo.findById(cardDto.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));
        if (!cardDto.getStatus().equalsIgnoreCase("ACTIVE") && !cardDto.getStatus().equalsIgnoreCase("INACTIVE")){
            throw  new RuntimeException("Status can be only ACTIVE or INACTIVE");
        }
        Card card = new Card(
                UUID.randomUUID(),
                cardDto.getStatus(),
                cardDto.getExpiry(),
                cardDto.getCardNumber(),
                account
        );

        return cardRepo.save(card);
    }

    public Card activateCard(UUID id) {
        Card card = getCardById(id);
        card.setStatus("ACTIVE");
        return cardRepo.save(card);
    }

    public Card deactivateCard(UUID id) {
        Card card = getCardById(id);
        card.setStatus("INACTIVE");
        return cardRepo.save(card);
    }

    public Card getCardById(UUID id) {
        return cardRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
    }
}
