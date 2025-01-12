package casino.game.strategy.bet;

public class SimpleBet implements BetStrategy {
    private static final int DEFAULT_BET = 20;
    private static final int MULTIPLIER = 2;
    private static final int DEFAULT_RETURN = 0;

    @Override
    public int getMinBet() {
        return DEFAULT_BET;
    }

    @Override
    public int winBet(int amount) {
        return MULTIPLIER * amount;
    }

    @Override
    public int tieBet(int amount) {
        return amount;
    }

    @Override
    public int loseBet(int amount) {
        return DEFAULT_RETURN;
    }
}