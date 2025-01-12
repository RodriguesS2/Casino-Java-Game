package casino.game.viewer.game;

import casino.game.gui.GUI;
import casino.game.model.game.elements.Card;

public class CardViewer implements ElementViewer<Card> {


    @Override
    public void draw(Card card, GUI gui) {
        if (card.isFaceUp()) {
            gui.drawCardUp(card.getPosition(), card);
        } else {
            gui.drawCardDown(card.getPosition());
        }
    }

}
