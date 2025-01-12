package casino.game.viewer.game;

import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.game.elements.Card;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CardViewerTest {

    @Test
    public void testDrawFaceUpCard() {
        GUI mockGui = mock(GUI.class);
        Card mockCard = mock(Card.class);
        when(mockCard.isFaceUp()).thenReturn(true);
        when(mockCard.getPosition()).thenReturn(new Position(10, 5));

        CardViewer cardViewer = new CardViewer();

        cardViewer.draw(mockCard, mockGui);

        verify(mockGui, times(1)).drawCardUp(new Position(10, 5), mockCard);
        verify(mockGui, never()).drawCardDown(any());
    }

    @Test
    public void testDrawFaceDownCard() {
        GUI mockGui = mock(GUI.class);
        Card mockCard = mock(Card.class);
        when(mockCard.isFaceUp()).thenReturn(false);
        when(mockCard.getPosition()).thenReturn(new Position(10, 5));

        CardViewer cardViewer = new CardViewer();

        cardViewer.draw(mockCard, mockGui);

        verify(mockGui, times(1)).drawCardDown(new Position(10, 5));
        verify(mockGui, never()).drawCardUp(any(), any());
    }


}
