package casino.game.viewer.game;

import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.game.blackjack.Blackjack;

import java.io.IOException;

public class BlackJackXMasViewer extends BlackJackViewer {
    public BlackJackXMasViewer(Blackjack blackjack) {
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
        String LINE_COLOR = "#FFFF00";
        String BACKGROUND_COLOR = "#45150d";
        while (true) {
            int bet = gui.betInput(getModel().getPlayerTokens());
            if (getModel().canBet(bet)) {
                getModel().setBetAmount(bet);
                gui.drawText(new Position(26, 19), "Confirm with Enter", LINE_COLOR, BACKGROUND_COLOR);
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
        String LINE_COLOR = "#000000";
        String BACKGROUND_COLOR = "#F5F5DC";
        gui.setBackgroundPNG("backgrounds/snowflakes.png");

        gui.drawText(new Position(2, 20), "Player Total: " + getModel().getPlayerPoints(), LINE_COLOR, BACKGROUND_COLOR);

        drawElements(gui, getModel().getPlayerCards(), new CardViewer());
        drawElements(gui, getModel().getDealerCards(), new CardViewer());
    }

    private void drawGameOver(GUI gui) throws IOException {
        String LINE_COLOR = "#000000";
        String HIGHLIGHT = "#FF0000";
        String BACKGROUND_COLOR = "#F5F5DC";
        gui.setBackgroundPNG("backgrounds/snowman.png");

        drawElements(gui, getModel().getPlayerCards(), new CardViewer());
        drawElements(gui, getModel().getDealerCards(), new CardViewer());

        gui.drawText(new Position(2, 20), "Player Total: " + getModel().getPlayerPoints(), LINE_COLOR, BACKGROUND_COLOR);
        gui.drawText(new Position(2, 2), "Dealer Total: " + getModel().getDealerPoints(), LINE_COLOR, BACKGROUND_COLOR);

        if (getModel().getWinner().equals("Tie")) {
            gui.drawText(new Position(36, 14), getModel().getWinner(), HIGHLIGHT, BACKGROUND_COLOR);
            gui.drawText(new Position(21, 16), "Press ENTER to restart the game.", LINE_COLOR, BACKGROUND_COLOR);
            gui.drawText(new Position(25, 17), "Press Q to exit the game.", LINE_COLOR, BACKGROUND_COLOR);

        } else {
            gui.drawText(new Position(32, 14), getModel().getWinner() + " wins!", HIGHLIGHT, BACKGROUND_COLOR);
            gui.drawText(new Position(21, 16), "Press ENTER to restart the game.", LINE_COLOR, BACKGROUND_COLOR);
            gui.drawText(new Position(25, 17), "Press Q to exit the game.", LINE_COLOR, BACKGROUND_COLOR);

        }
    }


}
