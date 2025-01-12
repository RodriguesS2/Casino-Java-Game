package casino.game.model.game.element;

import casino.game.model.game.elements.Card;
import casino.game.model.game.elements.CardDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CardDeckTest {
    CardDeck deck;

    @BeforeEach
    public void BaseSetup() {
        deck = new CardDeck();
    }

    @Test
    void testDeckCreation() {
        Assertions.assertEquals(52, deck.getDeckSize());
    }

    @Test
    void testGetDeck() {
        List<Card> deckList = deck.getDeck();

        Assertions.assertNotNull(deckList);
        Assertions.assertEquals(52, deckList.size());
    }

    @Test
    void testPickCard() {
        Card pickedCard = deck.getCard();

        Assertions.assertNotNull(pickedCard);
    }

    @Test
    void testRemoveCard() {

        int initial_size = deck.getDeckSize();
        deck.removeCard();

        Assertions.assertEquals(initial_size - 1, deck.getDeckSize());
    }



    @Test
    void testShuffle() {

        List<Card> originalDeck = new ArrayList<>(deck.getDeck());

        deck.shuffle();

        Assertions.assertNotEquals(originalDeck, deck);
        Assertions.assertEquals(52, deck.getDeckSize());
    }
}
