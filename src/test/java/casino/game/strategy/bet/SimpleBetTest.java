package casino.game.strategy.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleBetTest {

    private SimpleBet simpleBet;

    @BeforeEach
    void setUp() {
        simpleBet = new SimpleBet();
    }

    @Test
    void testGetMinBet() {
        assertEquals(20, simpleBet.getMinBet(), "Min bet should be 20");
    }

    @Test
    void testWinBet() {
        int amount = 50;

        assertEquals(100, simpleBet.winBet(amount), "Winning bet should multiply the amount by 2");
    }

    @Test
    void testTieBet() {
        int amount = 30;

        assertEquals(30, simpleBet.tieBet(amount), "Tie bet should return the original amount");
    }

    @Test
    void testLoseBet() {
        int amount = 100;

        assertEquals(0, simpleBet.loseBet(amount), "Lose bet should return 0");
    }

}
