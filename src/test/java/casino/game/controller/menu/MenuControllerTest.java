package casino.game.controller.menu;

import casino.game.Casino;
import casino.game.states.games.BlackJackState;
import casino.game.gui.GUI;
import casino.game.model.menu.Menu;
import casino.game.states.games.BlackJackXmasState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class MenuControllerTest {

    @Mock
    private Menu mockMenu;
    @Mock
    private Casino mockCasino;

    private MenuController controller;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new MenuController(mockMenu);
    }

    @Test
    void upActionMovesToPreviousEntry() {
        controller.step(mockCasino, GUI.ACTION.UP);

        verify(mockMenu).previousEntry();
    }

    @Test
    void downActionMovesToNextEntry() {
        controller.step(mockCasino, GUI.ACTION.DOWN);

        verify(mockMenu).nextEntry();
    }

    @Test
    void enterActionExitGame() {
        when(mockMenu.isSelectedExit()).thenReturn(true);

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockCasino).setState(null);
    }

    @Test
    void enterActionStartBlackJack() {
        when(mockMenu.isSelectedStartBlackJack()).thenReturn(true);

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockCasino).setState(any(BlackJackState.class));
    }

    @Test
    void enterActionStartBlackJackXmas() {
        when(mockMenu.isSelectedStartOther()).thenReturn(true);

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockCasino).setState(any(BlackJackXmasState.class));
    }

}
