package casino.game.model.game.blackjack;

import casino.game.model.game.elements.Card;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlackjackTest {

    private Blackjack blackjack;
    private Card mockCard;

    @BeforeEach
    void setUp() {
        BetStrategy mockBetStrategy = mock(BetStrategy.class);
        when(mockBetStrategy.getMinBet()).thenReturn(20);
        PointsStrategy mockPointsStrategy = mock(PointsStrategy.class);
        mockCard = mock(Card.class);
        blackjack = new Blackjack(mockBetStrategy, mockPointsStrategy);
    }

    @Property
    void testCanBet(@ForAll int betAmount) {
        setUp();

        if (betAmount >= 20 && betAmount <= 1000) {
            assertTrue(blackjack.canBet(betAmount), "Bet should be valid");
        } else {
            assertFalse(blackjack.canBet(betAmount), "Bet should be invalid");
        }
    }

    @Property
    void testSetBetAmount(@ForAll int betAmount) {
        setUp();

        if (betAmount >= 20 && betAmount <= blackjack.getPlayerTokens()) {
            blackjack.setBetAmount(betAmount);
            assertEquals(1000 - betAmount, blackjack.getPlayerTokens());
            assertEquals(betAmount, blackjack.getBetAmount());
        } else {
            if(blackjack.canBet(betAmount)) {
                blackjack.setBetAmount(betAmount);
                assertEquals(1000, blackjack.getPlayerTokens());
                assertEquals(0, blackjack.getBetAmount());
            }
        }
    }

    @Test
    public void testGetBetStrategy() {
        assertNotNull(blackjack.getBetStrategy());
    }

    @Test
    public void testAddPlayerTokens() {
        blackjack.addPlayerTokens(100);
        assertEquals(1100, blackjack.getPlayerTokens());
    }

    @Test
    void testAddPlayerAndDealerCards() {
        blackjack.addPlayerCard(mockCard);
        blackjack.addDealerCard(mockCard);

        assertEquals(1, blackjack.getPlayerCards().size());
        assertEquals(1, blackjack.getDealerCards().size());
    }

    @Test
    void testPlayerAndDealerPoints() {
        blackjack.addPlayerPoints(10);
        blackjack.addDealerPoints(8);

        assertEquals(10, blackjack.getPlayerPoints());
        assertEquals(8, blackjack.getDealerPoints());

        blackjack.addPlayerPoints(5);
        assertEquals(15, blackjack.getPlayerPoints());
    }

    @Test
    void testResetGame() {
        blackjack.addPlayerPoints(10);
        blackjack.addDealerPoints(15);
        blackjack.addPlayerCard(mock(Card.class));
        blackjack.addDealerCard(mock(Card.class));

        blackjack.resetGame();

        assertEquals(0, blackjack.getPlayerPoints());
        assertEquals(0, blackjack.getDealerPoints());
        assertEquals(0, blackjack.getPlayerAceCount());
        assertEquals(0, blackjack.getDealerAceCount());
        assertEquals(0, blackjack.getPlayerCards().size());
        assertEquals(0, blackjack.getDealerCards().size());
        assertNotNull(blackjack.getDeck());  // The deck should be reinitialized
    }

    @Test
    void testDealerFaceUpAndDown() {
        blackjack.addDealerCard(mock(Card.class));
        blackjack.addDealerCard(mockCard);

        blackjack.dealerFaceUp();
        verify(mockCard).setFaceUp(true);

        blackjack.dealerFaceDown();
        verify(mockCard).setFaceUp(false);
    }


    @Test
    void testWinner() {
        blackjack.setWinner("Player");
        assertEquals("Player", blackjack.getWinner());  // Verify the winner is set correctly

        blackjack.setWinner("Dealer");
        assertEquals("Dealer", blackjack.getWinner());  // Verify winner changes
    }
}
