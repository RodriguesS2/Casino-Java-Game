package casino.game.logic;

import casino.game.model.Position;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.model.game.elements.Card;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;


public class BlackJackLogic {

    private final Blackjack game;
    private final PointsStrategy pointsStrategy;
    private final BetStrategy betStrategy;

    public BlackJackLogic(Blackjack game, BetStrategy betStrategy, PointsStrategy pointsStrategy) {
        this.game = game;
        this.betStrategy = betStrategy;
        this.pointsStrategy = pointsStrategy;
    }

    public void initializeGame() {
        game.getDeck().shuffle();
        dealInitialCards();
    }

    public void dealInitialCards() {
        for (int i = 0; i < 2; i++) {
            drawCardForPlayer();
            drawCardForDealer();
        }
        game.dealerFaceDown();
    }

    public void drawCardForPlayer() {
        Card card = game.getDeck().getCard();
        game.addPlayerCard(card);
        updatePlayerPoints(card);
        game.getDeck().removeCard ();
        increasePositionPlayer();
    }

    public void drawCardForDealer() {
        Card card = game.getDeck().getCard();
        game.addDealerCard(card);
        updateDealerPoints(card);
        game.getDeck().removeCard();
        increasePositionDealer();
    }

    public void updatePlayerPoints(Card card) {
        int points = pointsStrategy.getPoints(card);
        if (card.isAce()) {
            game.addPlayerAceCount();
        }
        game.addPlayerPoints(points);
    }

    public void updateDealerPoints(Card card) {
        int points = pointsStrategy.getPoints(card);
        if (card.isAce()) {
            game.addDealerAceCount();
        }
        game.addDealerPoints(points);
    }

    public void handlePlayerAcePoints() {
        game.subPlayerAceCount();
        game.addPlayerPoints(-10);
    }

    public void handleDealerAcePoints() {
        game.subDealerAceCount();
        game.addDealerPoints(-10);
    }

    public void increasePositionDealer() {
        if (game.getDealerCards().size() == 1) {
            game.getDealerCards().getFirst().setPosition(new Position(2, 4));
        } else {
            game.getDealerCards().getLast().setPosition(new Position(game.getDealerCards().get(game.getDealerCards().size() - 2).getPosition().getX() + 13, 4));
        }
    }

    public void increasePositionPlayer() {
        if (game.getPlayerCards().size() == 1) {
            game.getPlayerCards().getFirst().setPosition(new Position(2, 22));
        } else {
            game.getPlayerCards().getLast().setPosition(new Position(game.getPlayerCards().get(game.getPlayerCards().size() - 2).getPosition().getX() + 13, 22));
        }
    }

    public void betResult(int amount) {

        if (decideWinner().equals("Player")) {
            int betResult = betStrategy.winBet(amount);
            game.addPlayerTokens(betResult);
        } else if (decideWinner().equals("Dealer")) {
            int betResult = betStrategy.loseBet(amount);
            game.addPlayerTokens(betResult);
        } else if (decideWinner().equals("Tie")) {
            int betResult = betStrategy.tieBet(amount);
            game.addPlayerTokens(betResult);
        }
    }


    public void resetGame() {
        game.resetGame();
        initializeGame();
    }

    public void finishGame() {
        game.setWinner(decideWinner());
        game.dealerFaceUp();
        game.setState("GAME_OVER");
    }

    public String decideWinner() {
        if (game.getPlayerPoints() == 21) {
            return "Player";
        } else if (game.getDealerPoints() > 21) {
            return "Player";
        } else if (game.getPlayerPoints() > 21) {
            return "Dealer";
        } else if (game.getPlayerPoints() > game.getDealerPoints()) {
            return "Player";
        } else if (game.getPlayerPoints() < game.getDealerPoints()) {
            return "Dealer";
        }
        return "Tie";
    }

}
