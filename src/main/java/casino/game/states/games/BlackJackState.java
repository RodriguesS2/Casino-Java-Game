package casino.game.states.games;

import casino.game.controller.Controller;
import casino.game.controller.game.BlackJackController;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.states.State;
import casino.game.viewer.Viewer;
import casino.game.viewer.game.BlackJackViewer;

public class BlackJackState extends State<Blackjack> {
    public BlackJackState(Blackjack table) {
        super(table);
    }

    @Override
    protected Viewer<Blackjack> getViewer() {
        return new BlackJackViewer(getModel());
    }

    @Override
    protected Controller<Blackjack> getController() {
        return new BlackJackController(getModel());
    }
}
