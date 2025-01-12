package casino.game;

import casino.game.gui.BlackJackScreen;
import casino.game.model.menu.Menu;
import casino.game.states.MenuState;
import casino.game.states.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Casino {
    private final BlackJackScreen gui;
    private State state;

    public Casino() throws IOException, URISyntaxException, FontFormatException {
        this.gui = new BlackJackScreen(70, 50);
        this.state = new MenuState(new Menu());

    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException, InterruptedException {
        new Casino().start();

    }

    public void setState(State state) {
        this.state = state;
    }

    private void start() throws IOException, InterruptedException {
        while (this.state != null) {
            state.step(this, gui);
        }
        gui.close();
    }
}
