package casino.game.gui;

import casino.game.model.Position;
import casino.game.model.game.elements.Card;

import java.io.IOException;

public interface GUI {
    ACTION getNextAction() throws IOException;

    int getHeight();

    int getWidth();

    void drawSelected(Position position, int width, int height, String color, String background);

    void drawCardUp(Position position, Card card);

    void drawCardDown(Position position);

    void drawRules(Position position) throws IOException;

    void drawText(Position position, String text, String color, String background);

    void clear();

    void refresh() throws IOException;

    void close() throws IOException;

    void setBackgroundColor(String color) throws IOException;

    void setBackgroundPNG(String path) throws IOException;

    void invalidAmount() throws IOException;

    int betInput(int balance) throws IOException;

    enum ACTION {UP, DOWN, EXIT, ENTER, QUIT, NONE, HIT, STAY, NUMBER, BACKSPACE}
}