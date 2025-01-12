package casino.game.model.game.blackjack;

import casino.game.model.game.elements.CardDeck;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;


public abstract class BlackJackBuilder {

    public Blackjack createBlackjack(BetStrategy betStrategy, PointsStrategy pointsStrategy) {
        Blackjack blackjack = new Blackjack(betStrategy, pointsStrategy);
        blackjack.setDeck(createDeck());
        return blackjack;
    }

    protected abstract CardDeck createDeck();
}
