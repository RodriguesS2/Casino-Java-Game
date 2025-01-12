package casino.game.strategy.points;

import casino.game.model.game.elements.Card;

public class BlackJackPoints implements PointsStrategy {
    private final static String KING = "K";
    private final static String QUEEN = "Q";
    private final static String JACK = "J";
    private final static String ACE = "A";


    @Override
    public int getPoints(Card card) {
        String value = card.getValue();

        if (value.equals(ACE)) {
            return 11;
        }

        if (value.equals(KING) || value.equals(QUEEN) || value.equals(JACK)) {
            return 10;
        }

        return Integer.parseInt(value); //the value corresponds to the number of the integer
    }
}
