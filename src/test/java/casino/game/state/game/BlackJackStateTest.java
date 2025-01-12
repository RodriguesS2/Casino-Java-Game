package casino.game.state.game;

import casino.game.Casino;

import casino.game.controller.Controller;
import casino.game.controller.game.BlackJackController;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.states.games.BlackJackState;
import casino.game.viewer.Viewer;
import casino.game.viewer.game.BlackJackViewer;
import casino.game.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlackJackStateTest {

    private BlackJackState blackJackState;
    private Blackjack mockBlackjack;
    private Controller<Blackjack> mockController;
    private Viewer<Blackjack> mockViewer;
    private Casino mockCasino;
    private GUI mockGui;

    @BeforeEach
    void setUp() {
        mockBlackjack = mock(Blackjack.class);
        mockController = mock(BlackJackController.class);
        mockViewer = mock(BlackJackViewer.class);
        mockCasino = mock(Casino.class);
        mockGui = mock(GUI.class);

        blackJackState = new BlackJackState(mockBlackjack) {
            @Override
            protected Viewer<Blackjack> getViewer() {
                return mockViewer;
            }

            @Override
            protected Controller<Blackjack> getController() {
                return mockController;
            }
        };
    }

    @Test
    void testGetModel() {
        assertEquals(mockBlackjack, blackJackState.getModel());
    }

    @Test
    void testStepRules() throws Exception {
        when(mockBlackjack.getState()).thenReturn("RULES");

        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.ENTER);
    }

    @Test
    void testStepBet() throws Exception {
        when(mockBlackjack.getState()).thenReturn("BET");

        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.ENTER);
    }

    @Test
    void testStepPlaying() throws Exception {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.HIT);

        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.HIT);
        assertEquals("PLAYING", mockBlackjack.getState());
    }

    @Test
    void testStepGameOver() throws Exception {
        when(mockBlackjack.getState()).thenReturn("GAME OVER");

        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.ENTER);
    }


    @Test
    void testStepStayAction() throws Exception {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.STAY);

        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer).draw(mockGui);
        verify(mockController).step(mockCasino, GUI.ACTION.STAY);
    }


    @Test
    void testFullGameFlow() throws Exception {
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        when(mockBlackjack.getState()).thenReturn("RULES");
        blackJackState.step(mockCasino, mockGui);

        when(mockBlackjack.getState()).thenReturn("BET");
        blackJackState.step(mockCasino, mockGui);

        when(mockBlackjack.getState()).thenReturn("PLAYING");
        blackJackState.step(mockCasino, mockGui);

        when(mockBlackjack.getState()).thenReturn("GAME OVER");
        blackJackState.step(mockCasino, mockGui);

        verify(mockViewer, times(4)).draw(mockGui);
        verify(mockController, times(4)).step(any(Casino.class), any(GUI.ACTION.class));
    }
}
