package casino.game.strategy.points;

import casino.game.model.game.elements.Card;

public interface PointsStrategy {

    int getPoints(Card card);
}