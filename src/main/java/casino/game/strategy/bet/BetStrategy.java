package casino.game.strategy.bet;

public interface BetStrategy {

    int getMinBet();

    int winBet(int amount);

    int tieBet(int amount);

    int loseBet(int amount);
}
