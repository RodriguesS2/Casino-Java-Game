package casino.game.strategy.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMAsBetTest {

    private XMAsBet xmasBet;

    @BeforeEach
    void setUp() {
        xmasBet = new XMAsBet();
    }

    @Test
    void testGetMinBet() {
        assertEquals(50, xmasBet.getMinBet(), "Min bet should be 50");
    }

    @Test
    void testWinBet() {
        int amount = 30;

        assertEquals(150, xmasBet.winBet(amount), "Winning bet should multiply the amount by 5");
    }

    @Test
    void testTieBet() {
        int amount = 40;

        assertEquals(40, xmasBet.tieBet(amount), "Tie bet should return the original amount");
    }

    @Test
    void testLoseBet() {
        int amount = 100;

        assertEquals(0, xmasBet.loseBet(amount), "Lose bet should return 0");
    }
}
