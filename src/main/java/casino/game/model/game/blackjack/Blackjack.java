package casino.game.model.game.blackjack;

import casino.game.model.game.elements.Card;
import casino.game.model.game.elements.CardDeck;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Blackjack {

    private CardDeck deck;
    private final List<Card> playerCards = new ArrayList<>();
    private final List<Card> dealerCards = new ArrayList<>();
    private int playerPoints = 0;
    private int dealerPoints = 0;
    private int playerAceCount = 0; //counts the aces that are worth 11
    private int dealerAceCount = 0;
    private String state = "RULES";
    private String winner;
    private int playerTokens = 1000;
    private int betAmount;
    private final BetStrategy betStrategy;
    private final PointsStrategy pointsStrategy ;

    public Blackjack(BetStrategy betStrategy, PointsStrategy pointsStrategy) {
        this.betStrategy = betStrategy;
        this.pointsStrategy = pointsStrategy;
    }

    public boolean canBet(int amount) {
        if (amount < betStrategy.getMinBet()) {
            return false;
        } else return amount <= getPlayerTokens() && amount > 0;
    }

    public BetStrategy getBetStrategy() {
        return betStrategy;
    }

    public int getPlayerTokens() {
        return playerTokens;
    }

    public void addPlayerTokens(int amount) {
        this.playerTokens += amount;
    }


    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
        this.playerTokens -= betAmount;
    }

    public CardDeck getDeck() {
        return deck;
    }

    public void setDeck(CardDeck deck) {
        this.deck = deck;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void addPlayerCard(Card card) {
        playerCards.add(card);
    }

    public void addDealerCard(Card card) {
        dealerCards.add(card);
    }

    public int getDealerPoints() {
        return dealerPoints;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

     public void addDealerPoints(int Points) {
        this.dealerPoints += Points;
    }

    public void addPlayerPoints(int Points) {
        this.playerPoints += Points;
    }

    public int getDealerAceCount() {
        return dealerAceCount;
    }

    public int getPlayerAceCount() {
        return playerAceCount;
    }

    public void addDealerAceCount() {
        this.dealerAceCount += 1;
    }

    public void addPlayerAceCount() {
        this.playerAceCount += 1;
    }

    public void subDealerAceCount() {
        this.dealerAceCount -= 1;
    }

    public void subPlayerAceCount() {
        this.playerAceCount -= 1;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void dealerFaceUp() {
        dealerCards.get(1).setFaceUp(true);
    }

    public void dealerFaceDown() {
        dealerCards.get(1).setFaceUp(false);
    }

    public PointsStrategy getPointsStrategy() {
        return pointsStrategy;
    }

    public void resetGame() { //restarts the game info for another round
        this.playerPoints = 0;
        this.dealerPoints = 0;
        this.playerAceCount = 0;
        this.dealerAceCount = 0;
        this.playerCards.clear();
        this.dealerCards.clear();
        this.deck = new CardDeck();
    }
}
