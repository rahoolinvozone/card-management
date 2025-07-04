package com.fintech.cms.controller;

import com.fintech.cms.dto.CardDto;
import com.fintech.cms.entities.*;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.repository.*;
import com.fintech.cms.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @Operation(summary = "Create a new card")
    public Card createCard(@RequestBody CardDto cardDto) {
        return cardService.createCard(cardDto);
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate a card by ID")
    public Card activateCard(@PathVariable UUID id) {
        return cardService.activateCard(id);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a card by ID")
    public Card deactivateCard(@PathVariable UUID id) {
        return cardService.deactivateCard(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve card details by ID")
    public Card getCard(@PathVariable UUID id) {
        return cardService.getCardById(id);
    }
}