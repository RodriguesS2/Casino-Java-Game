package casino.game.strategy.bet;

public class XMAsBet implements BetStrategy {
    private final static int DEFAULT_BET = 50;
    private final static int MULTIPLIER = 5;
    private final static int DEFAULT_RETURN = 0;

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
