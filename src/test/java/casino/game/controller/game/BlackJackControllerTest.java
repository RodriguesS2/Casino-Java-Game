package casino.game.controller.game;

import casino.game.Casino;
import casino.game.gui.GUI;
import casino.game.logic.BlackJackLogic;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.states.MenuState;
import casino.game.strategy.bet.BetStrategy;
import casino.game.strategy.points.PointsStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.mockito.Mockito.*;

public class BlackJackControllerTest {

    private BlackJackController controller;
    private Blackjack mockBlackjack;
    private Casino mockCasino;
    private GUI mockGui;
    private BlackJackLogic mockLogic;

    @BeforeEach
    void setUp() {
        mockBlackjack = mock(Blackjack.class);
        mockCasino = mock(Casino.class);
        mockGui = mock(GUI.class);
        mockLogic = mock(BlackJackLogic.class);

        when(mockBlackjack.getBetStrategy()).thenReturn(mock(BetStrategy.class));
        when(mockBlackjack.getPointsStrategy()).thenReturn(mock(PointsStrategy.class));


        controller = new BlackJackController(mockBlackjack) {
            @Override
            protected BlackJackLogic getBlackJackLogic() {
                return mockLogic;  // Inject mock BlackJackLogic
            }
        };

    }

    @Test
    void testRulesState() throws IOException {

        when(mockBlackjack.getState()).thenReturn("RULES");
        when(mockGui.getNextAction()).thenReturn(GUI.ACTION.ENTER);

        controller.step(mockCasino, mockGui.getNextAction());

        verify(mockBlackjack, times(1)).setState("BET");
    }

    @Test
    void testBetState() {
        when(mockBlackjack.getState()).thenReturn("BET");

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockBlackjack, times(1)).setState("PLAYING");
    }

    @Test
    void gameIsCorrectlyInitialized() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getPlayerPoints()).thenReturn(8);

        controller.step(mockCasino, GUI.ACTION.NONE);

        verify(mockLogic, times(1)).initializeGame();
    }

    @Test
    void PlayerHits21WithoutDrawing() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getPlayerPoints()).thenReturn(21);

        controller.step(mockCasino, GUI.ACTION.NONE);

        verify(mockLogic, times(1)).initializeGame();
        verify(mockLogic, times(1)).finishGame();
    }

    @Test
    void PlayerBustsWithoutAce() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getPlayerPoints()).thenReturn(20,30); //two face cards and then a 10
        when(mockBlackjack.getPlayerAceCount()).thenReturn(0);

        controller.step(mockCasino, GUI.ACTION.HIT);

        verify(mockLogic, times(1)).drawCardForPlayer();
        verify(mockLogic, times(1)).finishGame();
    }

    @Test
    void PlayerBustsWithAce() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getPlayerPoints()).thenReturn(22);
        when(mockBlackjack.getPlayerAceCount()).thenReturn(1);

        controller.step(mockCasino, GUI.ACTION.HIT);

        verify(mockLogic, times(1)).drawCardForPlayer();
        verify(mockLogic, times(1)).handlePlayerAcePoints();
        verify(mockLogic, never()).finishGame();
    }

    @Test
    void DealerBusts() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getDealerPoints()).thenReturn(22);
        when(mockBlackjack.getDealerAceCount()).thenReturn(0);

        controller.step(mockCasino, GUI.ACTION.STAY);

        verify(mockBlackjack, times(1)).dealerFaceUp();
        verify(mockLogic, times(1)).finishGame();

    }

    @Test
    void DealerBustsWithAce() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");
        when(mockBlackjack.getDealerPoints()).thenReturn(22, 12, 22, 12, 23, 13, 23); // First two aces, draws a queen, and then an ace and a 10
        when(mockBlackjack.getDealerAceCount()).thenReturn(2, 1, 1, 0);

        controller.step(mockCasino, GUI.ACTION.STAY);

        verify(mockBlackjack, times(1)).dealerFaceUp();
        verify(mockLogic, times(3)).handleDealerAcePoints();
        verify(mockLogic, times(3)).drawCardForDealer();
        verify(mockLogic, times(1)).finishGame();


    }

    @Test
    void GameOverPlayerTokensBelowMinBet() {
        when(mockBlackjack.getState()).thenReturn("GAME_OVER");
        when(mockBlackjack.getPlayerTokens()).thenReturn(10);
        when(mockBlackjack.getBetStrategy().getMinBet()).thenReturn(20);

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockCasino, times(1)).setState(any(MenuState.class));
    }

    @Test
    void GameOverPlayerContinues() {
        when(mockBlackjack.getState()).thenReturn("GAME_OVER");
        when(mockBlackjack.getPlayerTokens()).thenReturn(50);
        when(mockBlackjack.getBetStrategy().getMinBet()).thenReturn(20);

        controller.step(mockCasino, GUI.ACTION.ENTER);

        verify(mockLogic, times(1)).betResult(anyInt());
        verify(mockBlackjack, times(1)).setState("BET");
        verify(mockLogic, times(1)).resetGame();
    }

    @Test
    void testQuitAction() {
        when(mockBlackjack.getState()).thenReturn("PLAYING");

        controller.step(mockCasino, GUI.ACTION.QUIT);

        verify(mockCasino, times(1)).setState(any(MenuState.class));
    }


}
