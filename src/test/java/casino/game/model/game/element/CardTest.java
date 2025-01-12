package casino.game.model.game.element;

import static org.junit.jupiter.api.Assertions .*;

import casino.game.model.Position;
import casino.game.model.game.elements.Card;
import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Provide("cardValues")
    Arbitrary<String> cardValues() {
        return Arbitraries.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
    }

    @Provide("cardSuits")
    Arbitrary<String> cardSuits() {
        return Arbitraries.of("♠", "♣", "♥", "♦");
    }

    @Property
    void testCardInitialization(@ForAll("cardValues") String value, @ForAll("cardSuits") String suit) {
        Card card = new Card(value, suit, 0, 0, true);

        assertEquals(value, card.getValue());
        assertEquals(suit, card.getSuit());
        assertEquals(value + suit, card.toString());
    }

    @Property
    void testCardFaceUpState(@ForAll("cardValues") String value, @ForAll("cardSuits") String suit) {
        Card card = new Card(value, suit, 0, 0, false);

        card.setFaceUp(true);

        assertTrue(card.isFaceUp());
    }

    @Property
    void testAceIdentification(@ForAll("cardValues") String value, @ForAll("cardSuits") String suit) {
        Card card = new Card(value, suit, 0, 0, true);

        if (value.equals("A")) {
            assertTrue(card.isAce());
        } else {
            assertFalse(card.isAce());
        }
    }

    @Test
    void testIsFaceUp() {
        Card card = new Card("A", "♠", 0, 0, true);

        assertTrue(card.isFaceUp());
        card.setFaceUp(false);
        assertFalse(card.isFaceUp());
    }

    @Test
    void testGetCardPosition() {
        Card card = new Card("A", "♠", 20, 12, true);

        Position position = new Position(20,12);
        assertEquals(position, card.getPosition());
    }
}


