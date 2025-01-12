package casino.game.states;

import casino.game.controller.Controller;
import casino.game.controller.menu.MenuController;
import casino.game.model.menu.Menu;
import casino.game.viewer.Viewer;
import casino.game.viewer.MenuViewer;

public class MenuState extends State<Menu> {
    public MenuState(Menu model) {
        super(model);
    }

    @Override
    protected Viewer<Menu> getViewer() {
        return new MenuViewer(getModel());
    }

    @Override
    protected Controller<Menu> getController() {
        return new MenuController(getModel());
    }
}
