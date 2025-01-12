package casino.game.strategy.points;

import casino.game.model.game.elements.Card;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackJackPointsTest {

    // could also be done with property testing

    public static Stream<Object[]> cardPointsData() {
        return Stream.of(
                new Object[]{"A", 11},
                new Object[]{"K", 10},
                new Object[]{"Q", 10},
                new Object[]{"J", 10},

                new Object[]{"2", 2},
                new Object[]{"3", 3},
                new Object[]{"4", 4},
                new Object[]{"5", 5},
                new Object[]{"6", 6},
                new Object[]{"7", 7},
                new Object[]{"8", 8},
                new Object[]{"9", 9},
                new Object[]{"10", 10}
        );
    }

    @ParameterizedTest
    @MethodSource("cardPointsData")
    void testGetPoints(String cardValue, int expectedPoints) {
        BlackJackPoints pointsStrategy = new BlackJackPoints();
        Card card = new Card(cardValue, "♠", 0, 0, true);

        int points = pointsStrategy.getPoints(card);

        assertEquals(expectedPoints, points);
    }
}
