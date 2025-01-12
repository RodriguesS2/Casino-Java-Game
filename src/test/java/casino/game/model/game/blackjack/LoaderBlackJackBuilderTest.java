package casino.game.model.game.blackjack;

import casino.game.model.game.elements.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoaderBlackJackBuilderTest {

    private LoaderBlackjackBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new LoaderBlackjackBuilder();
    }

    @Test
    public void testCreateDeck() {
        CardDeck deck = builder.createDeck();
        assertNotNull(deck);
    }
}