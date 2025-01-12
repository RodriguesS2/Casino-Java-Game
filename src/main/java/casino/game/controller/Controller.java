package casino.game.controller;

import casino.game.Casino;
import casino.game.gui.GUI;

import java.io.IOException;

public abstract class Controller<T> {

    private final T model;

    public Controller(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public abstract void step(Casino casino, GUI.ACTION action) throws IOException;
}
