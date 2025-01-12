package casino.game.state.menu;

import casino.game.Casino;
import casino.game.controller.Controller;
import casino.game.controller.menu.MenuController;
import casino.game.gui.GUI;
import casino.game.model.menu.Menu;
import casino.game.states.MenuState;
import casino.game.viewer.Viewer;
import casino.game.viewer.MenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MenuStateTest {
    private MenuState menuState;
    private Menu mockMenu;
    private MenuController mockController;
    private MenuViewer mockViewer;
    private Casino mockCasino;
    private GUI mockGui;

    @BeforeEach
    void setUp() {

        mockMenu = mock(Menu.class);
        mockController = mock(MenuController.class);
        mockViewer = mock(MenuViewer.class);
        mockCasino = mock(Casino.class);
        mockGui = mock(GUI.class);

        menuState = new MenuState(mockMenu) {
            @Override
            protected Viewer<Menu> getViewer() {
                return mockViewer;
            }

            @Override
            protected Controller<Menu> getController() {
                return mockController;
            }
        };
    }

    @Test
    void testGetModel() {
        assertEquals(mockMenu, menuState.getModel(), "Model should be the provided Menu instance.");
    }

    @Test
    void testStepUP() throws Exception {
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.UP);

        menuState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.UP);
    }

    @Test
    void testStepDown() throws Exception {
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.DOWN);

        menuState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.DOWN);
    }

    @Test
    void testStepEnter() throws Exception {
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        menuState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.ENTER);
    }

    @Test
    void testStepAnythingElse() throws Exception {
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.NONE);

        menuState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.NONE);
    }



}
