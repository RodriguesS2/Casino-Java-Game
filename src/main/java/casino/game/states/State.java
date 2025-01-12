package casino.game.states;

import casino.game.Casino;
import casino.game.controller.Controller;
import casino.game.gui.GUI;
import casino.game.viewer.Viewer;

import java.io.IOException;

public abstract class State<T> {

    private final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;

    public State(T model) {
        this.model = model;
        this.viewer = getViewer();
        this.controller = getController();
    }

    protected abstract Viewer<T> getViewer();

    protected abstract Controller<T> getController();

    public T getModel() {
        return model;
    }

    public void step(Casino game, GUI gui) throws IOException, InterruptedException {
        viewer.draw(gui);
        GUI.ACTION action = gui.getNextAction();
        controller.step(game, action);
    }
}
