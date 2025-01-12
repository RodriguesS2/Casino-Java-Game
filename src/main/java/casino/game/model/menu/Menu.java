package casino.game.model.menu;

import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    private int currentEntry = 0;

    public Menu() {
        this.entries = Arrays.asList("Blackjack", "Blackjack XMAS :)", "Exit");
    }

    public void nextEntry() {
        currentEntry++;
        if (currentEntry > this.entries.size() - 1)
            currentEntry = 0;
    }

    public void previousEntry() {
        currentEntry--;
        if (currentEntry < 0)
            currentEntry = this.entries.size() - 1;
    }

    public String getEntry(int i) {
        return entries.get(i);
    }

    public boolean isSelected(int i) {
        return currentEntry == i;
    }

    public boolean isSelectedExit() {
        return isSelected(2);
    }

    public boolean isSelectedStartBlackJack() {
        return isSelected(0);
    }

    public boolean isSelectedStartOther() {
        return isSelected(1);
    }

    public int getNumberEntries() {
        return this.entries.size();
    }
}