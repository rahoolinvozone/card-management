package com.fintech.cms;

import com.fintech.cms.controller.CardController;
import com.fintech.cms.dto.CardDto;
import com.fintech.cms.entities.Account;
import com.fintech.cms.entities.Card;
import com.fintech.cms.exception.NotFoundException;
import com.fintech.cms.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    private UUID cardId;
    private UUID accountId;
    private Account account;
    private Card card;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cardId = UUID.randomUUID();
        accountId = UUID.randomUUID();
        account = new Account(accountId, "ACTIVE", null);
        card = new Card(cardId, "INACTIVE", LocalDate.now().plusYears(1), "encryptedCard123", account);
    }

    @Test
    public void testCreateCard_success() {
        CardDto dto = new CardDto();
        dto.setStatus("ACTIVE");
        dto.setExpiry(LocalDate.now().plusYears(1));
        dto.setCardNumber("1234-5678-9012-3456");
        dto.setAccountId(accountId);

        when(cardService.createCard(dto)).thenReturn(
                new Card(UUID.randomUUID(), dto.getStatus(), dto.getExpiry(), dto.getCardNumber(), account)
        );

        Card result = cardController.createCard(dto);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(cardService).createCard(dto);
    }

    @Test
    public void testActivateCard_success() {
        Card activeCard = new Card(cardId, "ACTIVE", card.getExpiry(), card.getCardNumber(), account);

        when(cardService.activateCard(cardId)).thenReturn(activeCard);

        Card result = cardController.activateCard(cardId);

        assertNotNull(result);
        assertEquals("ACTIVE", result.getStatus());
        verify(cardService).activateCard(cardId);
    }

    @Test
    public void testDeactivateCard_success() {
        Card inactiveCard = new Card(cardId, "INACTIVE", card.getExpiry(), card.getCardNumber(), account);

        when(cardService.deactivateCard(cardId)).thenReturn(inactiveCard);

        Card result = cardController.deactivateCard(cardId);

        assertNotNull(result);
        assertEquals("INACTIVE", result.getStatus());
        verify(cardService).deactivateCard(cardId);
    }

    @Test
    public void testGetCard_notFound() {
        when(cardService.getCardById(cardId)).thenThrow(new NotFoundException("Card not found"));

        assertThrows(NotFoundException.class, () -> cardController.getCard(cardId));
        verify(cardService).getCardById(cardId);
    }
}
