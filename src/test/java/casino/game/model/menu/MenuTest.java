package casino.game.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    void testInitialSelection() {
        assertTrue(menu.isSelectedStartBlackJack());
        assertFalse(menu.isSelectedStartOther());
        assertFalse(menu.isSelectedExit());
    }

    @Test
    void testNextEntry() {

        menu.nextEntry();
        assertFalse(menu.isSelectedStartBlackJack());
        assertTrue(menu.isSelectedStartOther());
        assertFalse(menu.isSelectedExit());

        menu.nextEntry();
        assertFalse(menu.isSelectedStartBlackJack());
        assertFalse(menu.isSelectedStartOther());
        assertTrue(menu.isSelectedExit());

        menu.nextEntry();
        assertTrue(menu.isSelectedStartBlackJack());
        assertFalse(menu.isSelectedStartOther());
        assertFalse(menu.isSelectedExit());
    }

    @Test
    void testPreviousEntry() {
        menu.nextEntry();
        menu.nextEntry();
        assertTrue(menu.isSelectedExit());

        menu.previousEntry();
        assertTrue(menu.isSelectedStartOther());
        assertFalse(menu.isSelectedStartBlackJack());
        assertFalse(menu.isSelectedExit());

        menu.previousEntry();
        assertTrue(menu.isSelectedStartBlackJack());
        assertFalse(menu.isSelectedStartOther());
        assertFalse(menu.isSelectedExit());
    }


    @Test
    void testGetNumberEntries() {
        assertEquals(3, menu.getNumberEntries());
    }

    @Test
    void testGetEntry() {
        assertEquals("Blackjack", menu.getEntry(0));
        assertEquals("Blackjack XMAS :)", menu.getEntry(1));
        assertEquals("Exit", menu.getEntry(2));
    }

}
