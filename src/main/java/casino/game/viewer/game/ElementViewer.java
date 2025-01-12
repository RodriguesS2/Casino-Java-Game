package casino.game.viewer.game;

import casino.game.gui.GUI;
import casino.game.model.game.elements.Element;

public interface ElementViewer<T extends Element> {
    void draw(T element, GUI gui);
}