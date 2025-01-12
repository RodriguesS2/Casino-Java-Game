package casino.game.viewer;

import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.menu.Menu;

import java.io.IOException;

public class MenuViewer extends Viewer<Menu> {
    public MenuViewer(Menu menu) {
        super(menu);
    }

    @Override
    public void drawElements(GUI gui) throws IOException {

        String LINE_COLOR = "#FFFFFF";
        String BACKGROUND_COLOR = "#45150d";
        String HIGHLIGHTED = "#FFD700";
        gui.setBackgroundColor(BACKGROUND_COLOR); // for background color

        gui.drawText(new Position(6, 5), "   ______                        __                      ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 6), "  /      \\                      |  \\                     ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 7), "  |  $$$$$$\\  ______    _______  \\$$ _______    ______   ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 8), "  | $$   \\$$ |      \\  /       \\|  \\|       \\  /      \\  ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 9), "  | $$        \\$$$$$$\\|  $$$$$$$| $$| $$$$$$$\\|  $$$$$$\\ ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 10), "  | $$   __  /      $$ \\$$    \\ | $$| $$  | $$| $$  | $$ ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 11), "  | $$__/  \\|  $$$$$$$ _\\$$$$$$\\| $$| $$  | $$| $$__/ $$ ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 12), "   \\$$    $$ \\$$    $$|       $$| $$| $$  | $$ \\$$    $$ ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 13), "    \\$$$$$$   \\$$$$$$$ \\$$$$$$$  \\$$ \\$$   \\$$  \\$$$$$$  ", LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(6, 14), "                                                         ", LINE_COLOR, BACKGROUND_COLOR);


        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            String entry = getModel().getEntry(i);
            int textLength = entry.length();
            int positionX = (gui.getWidth() - textLength) / 2;
            int positionY = 20 + i * 4;

            if (getModel().isSelected(i)) {
                gui.drawSelected(new Position(positionX - 1, positionY - 1), textLength + 2, 1, HIGHLIGHTED, BACKGROUND_COLOR);
                gui.drawText(new Position(positionX, positionY), entry, HIGHLIGHTED, BACKGROUND_COLOR);
            } else {
                gui.drawText(new Position(positionX, positionY), entry, LINE_COLOR, BACKGROUND_COLOR);
            }
        }
    }
}