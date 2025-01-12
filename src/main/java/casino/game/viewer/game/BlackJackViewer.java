package casino.game.viewer.game;


import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.game.elements.Element;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.viewer.Viewer;

import java.io.IOException;
import java.util.List;

public class BlackJackViewer extends Viewer<Blackjack> {
    public BlackJackViewer(Blackjack blackjack) {
        super(blackjack);
    }

    @Override
    public void drawElements(GUI gui) throws IOException {
        switch (getModel().getState()) {
            case "RULES" -> gui.drawRules(new Position(0, 0));
            case "BET" -> drawBet(gui);
            case "PLAYING" -> drawPlaying(gui);
            case "GAME_OVER" -> drawGameOver(gui);
            default -> throw new IllegalStateException("Unexpected value: " + getModel().getState());
        }
    }

    private void drawBet(GUI gui) throws IOException {
        String HIGHLIGHT = "#FFFF00";
        String BACKGROUND_COLOR = "#45150d";
        gui.setBackgroundColor(BACKGROUND_COLOR);

        while (true) {
            int bet = gui.betInput(getModel().getPlayerTokens());

            if (getModel().canBet(bet)) {
                getModel().setBetAmount(bet);
                gui.drawText(new Position(26, 19), "Confirm with Enter", HIGHLIGHT, BACKGROUND_COLOR);
                return;
            } else {
                gui.invalidAmount();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void drawPlaying(GUI gui) throws IOException {
        String LINE_COLOR = "#FFFFFF";
        String BACKGROUND_COLOR = "#06402B";
        gui.setBackgroundColor(BACKGROUND_COLOR);

        gui.drawText(new Position(2, 20), "Player Total: " + getModel().getPlayerPoints(), LINE_COLOR, BACKGROUND_COLOR);

        drawElements(gui, getModel().getPlayerCards(), new CardViewer());
        drawElements(gui, getModel().getDealerCards(), new CardViewer());
    }

    private void drawGameOver(GUI gui) throws IOException {
        String LINE_COLOR = "#FFFFFF";
        String HIGHLIGHT = "#FF0000";
        String BACKGROUND_COLOR = "#06402B";
        gui.setBackgroundColor(BACKGROUND_COLOR);

        drawElements(gui, getModel().getPlayerCards(), new CardViewer());
        drawElements(gui, getModel().getDealerCards(), new CardViewer());

        gui.drawText(new Position(2, 20), "Player Total: " + getModel().getPlayerPoints(), LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(2, 2), "Dealer Total: " + getModel().getDealerPoints(), LINE_COLOR, BACKGROUND_COLOR);

        if (getModel().getWinner().equals("Tie")) {
            gui.drawText(new Position(36, 14), getModel().getWinner(), HIGHLIGHT, BACKGROUND_COLOR);
            gui.drawText(new Position(21, 16), "Press ENTER to restart the game.", LINE_COLOR, BACKGROUND_COLOR);
            gui.drawText(new Position(25, 17), "Press Q to exit the game.", LINE_COLOR, BACKGROUND_COLOR);
        } else {
            gui.drawText(new Position(30, 14), getModel().getWinner() + " wins!", HIGHLIGHT, BACKGROUND_COLOR);
            gui.drawText(new Position(21, 16), "Press ENTER to restart the game", LINE_COLOR, BACKGROUND_COLOR);
            gui.drawText(new Position(25, 17), "Press Q to exit the game", LINE_COLOR, BACKGROUND_COLOR);
        }
    }

    protected <T extends Element> void drawElements(GUI gui, List<T> elements, ElementViewer<T> viewer) {
        for (T element : elements)
            drawElement(gui, element, viewer);
    }

    protected <T extends Element> void drawElement(GUI gui, T element, ElementViewer<T> viewer) {
        viewer.draw(element, gui);
    }
}
