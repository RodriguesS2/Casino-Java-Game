package casino.game.controller.menu;

import casino.game.Casino;
import casino.game.controller.Controller;
import casino.game.gui.GUI;
import casino.game.model.game.blackjack.LoaderBlackjackBuilder;
import casino.game.model.menu.Menu;
import casino.game.states.games.BlackJackState;
import casino.game.states.games.BlackJackXmasState;
import casino.game.strategy.bet.SimpleBet;
import casino.game.strategy.bet.XMAsBet;
import casino.game.strategy.points.BlackJackPoints;


public class MenuController extends Controller<Menu> {
    public MenuController(Menu menu) {
        super(menu);
    }

    @Override
    public void step(Casino casino, GUI.ACTION action) {
        switch (action) {
            case UP:
                getModel().previousEntry();
                break;
            case DOWN:
                getModel().nextEntry();
                break;
            case ENTER:
                if (getModel().isSelectedExit()) {
                    casino.setState(null);
                }
                if (getModel().isSelectedStartBlackJack()) {
                    casino.setState(new BlackJackState(new LoaderBlackjackBuilder().createBlackjack(new SimpleBet(), new BlackJackPoints())));
                }
                if (getModel().isSelectedStartOther()) {
                    casino.setState(new BlackJackXmasState(new LoaderBlackjackBuilder().createBlackjack(new XMAsBet(), new BlackJackPoints())));
                }
            // fall through
            default:
                break;
        }
    }
}
