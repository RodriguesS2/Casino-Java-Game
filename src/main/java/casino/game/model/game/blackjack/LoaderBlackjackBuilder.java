package casino.game.model.game.blackjack;

import casino.game.model.game.elements.CardDeck;


public class LoaderBlackjackBuilder extends BlackJackBuilder{

    @Override
    protected CardDeck createDeck() {
        return new CardDeck();
    }
}
