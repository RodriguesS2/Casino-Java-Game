package casino.game.viewer.game;

import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.game.blackjack.Blackjack;
import casino.game.model.game.elements.Card;
import casino.game.model.game.elements.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class BlackJackViewerTest {
    @Mock
    private Blackjack mockBlackjack;

    @Mock
    private GUI mockGui;

    @Mock
    private ElementViewer<Card> mockCardViewer;

    private BlackJackViewer blackJackViewer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        blackJackViewer = new BlackJackViewer(mockBlackjack) {
            @Override
            protected <T extends Element> void drawElements(GUI gui, List<T> elements, ElementViewer<T> viewer) {
                for (T element : elements) {
                    mockCardViewer.draw((Card) element, gui);
                }
            }
        };
    }

    @Test
    public void testDrawElements_RULES() throws IOException {
        when(mockBlackjack.getState()).thenReturn("RULES");

        blackJackViewer.drawElements(mockGui);

        verify(mockGui).drawRules(new Position(0, 0));
    }

    @Test
    public void testDrawElements_BETValidAmount() throws IOException {
        when(mockBlackjack.getState()).thenReturn("BET");
        when(mockBlackjack.getPlayerTokens()).thenReturn(1000);
        // simulate valid bet input of 100
        when(mockGui.betInput(1000)).thenReturn(100);
        when(mockBlackjack.canBet(100)).thenReturn(true);

        blackJackViewer.drawElements(mockGui);

        verify(mockGui).setBackgroundColor("#45150d");

        verify(mockGui).drawText(new Position(26, 19), "Confirm with Enter", "#FFFF00", "#45150d");
    }

    @Test
    public void testDrawElements_BETInvalidAmount() throws IOException {
        // Invalid amount
        when(mockBlackjack.getState()).thenReturn("BET");
        when(mockBlackjack.getPlayerTokens()).thenReturn(50);
        // simulate Invalid bet input of 100
        when(mockGui.betInput(50)).thenReturn(100);
        when(mockBlackjack.canBet(100)).thenReturn(false, true); //true needed to break loop

        blackJackViewer.drawElements(mockGui);

        verify(mockGui).setBackgroundColor("#45150d");

        verify(mockGui, times(1)).invalidAmount();
    }

    @Test
    public void testDrawElements_PLAYING() throws IOException {
        when(mockBlackjack.getState()).thenReturn("PLAYING");

        List<Card> playerCards = List.of(new Card("A", "♥", 0, 0, true), new Card("10", "♦", 0, 0, true));
        List<Card> dealerCards = List.of(new Card("9", "♥", 0, 0, true), new Card("10", "♦", 0, 0, false));

        when(mockBlackjack.getPlayerPoints()).thenReturn(21);
        when(mockBlackjack.getPlayerCards()).thenReturn(playerCards);
        when(mockBlackjack.getDealerCards()).thenReturn(dealerCards);


        blackJackViewer.drawElements(mockGui);

        verify(mockGui).setBackgroundColor("#06402B");
        verify(mockGui).drawText(new Position(2, 20), "Player Total: 21", "#FFFFFF", "#06402B");

        // Verify drawElements was called for both player and dealer cards
        for (Card card : playerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }
        for (Card card : dealerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }
    }

    @Test
    public void testDrawGameOver_Tie() throws IOException {

        List<Card> playerCards = List.of(new Card("A", "♥", 0, 0, true), new Card("10", "♦", 0, 0, true));
        List<Card> dealerCards = List.of(new Card("10", "♥", 0, 0, true), new Card("A", "♦", 0, 0, false));

        when(mockBlackjack.getState()).thenReturn("GAME_OVER");
        when(mockBlackjack.getPlayerPoints()).thenReturn(21);
        when(mockBlackjack.getDealerPoints()).thenReturn(21);
        when(mockBlackjack.getWinner()).thenReturn("Tie");
        when(mockBlackjack.getPlayerCards()).thenReturn(playerCards);
        when(mockBlackjack.getDealerCards()).thenReturn(dealerCards);

        blackJackViewer.drawElements(mockGui);

        for (Card card : playerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }
        for (Card card : dealerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }

        verify(mockGui).setBackgroundColor("#06402B");
        verify(mockGui).drawText(new Position(2, 20), "Player Total: 21", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(2, 2), "Dealer Total: 21", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(36, 14), "Tie", "#FF0000", "#06402B");
        verify(mockGui).drawText(new Position(21, 16), "Press ENTER to restart the game.", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(25, 17), "Press Q to exit the game.", "#FFFFFF", "#06402B");
    }

    @Test
    public void testDrawGameOver_PlayerWins() throws IOException {

        List<Card> playerCards = List.of(new Card("A", "♥", 0, 0, true), new Card("10", "♦", 0, 0, true));
        List<Card> dealerCards = List.of(new Card("5", "♥", 0, 0, true), new Card("A", "♦", 0, 0, false));

        when(mockBlackjack.getState()).thenReturn("GAME_OVER");
        when(mockBlackjack.getPlayerPoints()).thenReturn(21);
        when(mockBlackjack.getDealerPoints()).thenReturn(16);
        when(mockBlackjack.getWinner()).thenReturn("PLAYER");
        when(mockBlackjack.getPlayerCards()).thenReturn(playerCards);
        when(mockBlackjack.getDealerCards()).thenReturn(dealerCards);

        blackJackViewer.drawElements(mockGui);

        for (Card card : playerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }
        for (Card card : dealerCards) {
            verify(mockCardViewer).draw(card, mockGui);
        }

        verify(mockGui).setBackgroundColor("#06402B");
        verify(mockGui).drawText(new Position(2, 20), "Player Total: 21", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(2, 2), "Dealer Total: 16", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(30, 14), "PLAYER wins!", "#FF0000", "#06402B");
        verify(mockGui).drawText(new Position(21, 16), "Press ENTER to restart the game", "#FFFFFF", "#06402B");
        verify(mockGui).drawText(new Position(25, 17), "Press Q to exit the game", "#FFFFFF", "#06402B");
    }

    @Test
    public void testDrawElements() {
        ElementViewer<Element> mockViewer = mock(ElementViewer.class);

        Element mockElement1 = mock(Element.class);
        Element mockElement2 = mock(Element.class);
        List<Element> elements = List.of(mockElement1, mockElement2);
        BlackJackViewer blackJackViewer = new BlackJackViewer(mock(Blackjack.class));

        blackJackViewer.drawElements(mockGui, elements, mockViewer);

        verify(mockViewer, times(1)).draw(mockElement1, mockGui);
        verify(mockViewer, times(1)).draw(mockElement2, mockGui);
    }



}

