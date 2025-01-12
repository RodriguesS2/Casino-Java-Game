package casino.game.viewer;

import casino.game.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.verify;

public class ViewerTest {

    @Mock
    private GUI mockGui;

    private Viewer<String> testViewer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testViewer = new Viewer<>("Test Model") {
            @Override
            protected void drawElements(GUI gui) {
                gui.drawText(new casino.game.model.Position(0, 0), "Test", "#FFFFFF", "#000000");
            }
        };
    }

    @Test
    void testGetModel() {
        assert testViewer.getModel().equals("Test Model");
    }

    @Test
    void testDrawCallsLifecycleMethods() throws IOException, InterruptedException {
        testViewer.draw(mockGui);

        verify(mockGui).clear();
        verify(mockGui).refresh();
        verify(mockGui).drawText(new casino.game.model.Position(0, 0), "Test", "#FFFFFF", "#000000");
    }


}
