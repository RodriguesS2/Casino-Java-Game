package casino.game.viewer.menu;


import casino.game.gui.GUI;
import casino.game.model.Position;
import casino.game.model.menu.Menu;
import casino.game.viewer.MenuViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MenuViewerTest {
    @Mock
    private Menu mockMenu;

    @Mock
    private GUI mockGui;

    private MenuViewer menuViewer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menuViewer = new MenuViewer(mockMenu);
    }

    @Test
    public void testDrawElements_BackgroundColor() throws IOException {
        menuViewer.drawElements(mockGui);

        verify(mockGui).setBackgroundColor("#45150d");
    }

    @Test
    public void testDrawElements_DrawTextForLogo() throws IOException {
        menuViewer.drawElements(mockGui);

        verify(mockGui, times(10)).drawText(any(Position.class), anyString(), eq("#FFFFFF"), eq("#45150d"));
    }

    @Test
    public void testDrawElements_DrawMenuEntries() throws IOException {

        when(mockMenu.getNumberEntries()).thenReturn(3);
        when(mockMenu.getEntry(0)).thenReturn("Blackjack");
        when(mockMenu.getEntry(1)).thenReturn("Blackjack XMAS");
        when(mockMenu.getEntry(2)).thenReturn("Exit");
        when(mockMenu.isSelected(0)).thenReturn(true);
        when(mockMenu.isSelected(1)).thenReturn(false);
        when(mockMenu.isSelected(2)).thenReturn(false);

        menuViewer.drawElements(mockGui);

        verify(mockGui).drawSelected(any(Position.class), eq(11), eq(1), eq("#FFD700"), eq("#45150d"));
        verify(mockGui).drawText(any(Position.class), eq("Blackjack"), eq("#FFD700"), eq("#45150d"));

        verify(mockGui).drawText(any(Position.class), eq("Blackjack XMAS"), eq("#FFFFFF"), eq("#45150d"));
        verify(mockGui).drawText(any(Position.class), eq("Exit"), eq("#FFFFFF"), eq("#45150d"));
    }
}

