package casino.game.controller.game;

import casino.game.Casino;
import casino.game.controller.Controller;
import casino.game.gui.GUI;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.logic.BlackJackLogic;
import casino.game.model.menu.Menu;
import casino.game.states.MenuState;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;

public class BlackJackController extends Controller<Blackjack> {


    private final BlackJackLogic blackJackLogic;
    private boolean isFirstRun;
    private final BetStrategy betStrategy;
    private final PointsStrategy pointsStrategy;


    public BlackJackController(Blackjack game) {
        super(game);
        this.betStrategy = game.getBetStrategy();
        this.pointsStrategy = game.getPointsStrategy();
        this.blackJackLogic = new BlackJackLogic(game, betStrategy, pointsStrategy);
        setFirstRun(true);
    }

    @Override
    public void step(Casino game, GUI.ACTION action) {
        if (getModel().getState().equals("RULES")) {
            rules(action);
            return;
        }
        if (getModel().getState().equals("BET")) {
            bet();
        }
        if (getModel().getState().equals("PLAYING")) {
            playing(game, action);
            return;
        }
        if (getModel().getState().equals("GAME_OVER")) {
            gameOver(game, action);
        }
    }

    private void rules(GUI.ACTION action) {
        if (action == GUI.ACTION.ENTER) {
            getModel().setState("BET");
        }
    }

    private void bet() {
        getModel().setState("PLAYING");
    }

    private void playing(Casino game, GUI.ACTION action) {
        if (isFirstRun) {
            setFirstRun(false);
            getBlackJackLogic().initializeGame();
        }
        if (getModel().getPlayerPoints() == 21) { //check if player automatically wins
            getBlackJackLogic().finishGame();
            return;
        }
        switch (action) {
            case HIT:
                getBlackJackLogic().drawCardForPlayer();

                if (getModel().getPlayerPoints() > 21) {
                    if (getModel().getPlayerAceCount() > 0) { //Player "busts" but has at least one ace worth 11
                        getBlackJackLogic().handlePlayerAcePoints();
                    } else {
                        getBlackJackLogic().finishGame();
                        break;
                    }
                }
                if (getModel().getPlayerPoints() == 21) {
                    getBlackJackLogic().finishGame();
                    break;
                }
                break;

            case STAY:
                getModel().dealerFaceUp();
                // need initial check (2 aces)
                if (getModel().getDealerPoints() > 21) {
                    if (getModel().getDealerAceCount() > 0) { //Dealer "busts" but has at least one ace worth 11
                        getBlackJackLogic().handleDealerAcePoints();
                    }
                }
                // drawing until 17, if above and ace reduce -> draw again if below 17
                while (getModel().getDealerPoints() < 17) {
                    getBlackJackLogic().drawCardForDealer();
                    if (getModel().getDealerPoints() > 21) {
                        if (getModel().getDealerAceCount() > 0) { //Dealer "busts" but has at least one ace worth 11
                            getBlackJackLogic().handleDealerAcePoints();
                        }
                    }
                }
                getBlackJackLogic().finishGame();
                break;

            case QUIT:
                game.setState(new MenuState(new Menu()));
                break;

            default:
                //fall through
        }
    }

    private void gameOver(Casino game, GUI.ACTION action) {
        getBlackJackLogic().betResult(getModel().getBetAmount());
        if (getModel().getPlayerTokens() < betStrategy.getMinBet() || action == GUI.ACTION.QUIT) {
            game.setState(new MenuState(new Menu()));
        }
        else if (action == GUI.ACTION.ENTER) {
            getModel().setState("BET");
            getBlackJackLogic().resetGame();
        }

    }

    // needed for testing
    protected void setFirstRun(boolean firstRun) {
        this.isFirstRun = firstRun;
    }

    protected BlackJackLogic getBlackJackLogic() {
        return blackJackLogic;
    }

}
