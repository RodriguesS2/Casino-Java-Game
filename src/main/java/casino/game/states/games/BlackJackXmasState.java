package casino.game.states.games;

import casino.game.model.game.blackjack.Blackjack;
import casino.game.viewer.Viewer;
import casino.game.viewer.game.BlackJackXMasViewer;

public class BlackJackXmasState extends BlackJackState {

    public BlackJackXmasState(Blackjack table) {
        super(table);
    }

    @Override
    protected Viewer<Blackjack> getViewer() {
        return new BlackJackXMasViewer(getModel());
    }
}
