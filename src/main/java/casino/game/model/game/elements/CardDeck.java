package casino.game.model.game.elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardDeck {

    private final List<Card> deck = new ArrayList<>();
    private final static String KING = "K";
    private final static String QUEEN = "Q";
    private final static String JACK = "J";
    private final static String ACE = "A";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";
    private final static String FIVE = "5";
    private final static String SIX = "6";
    private final static String SEVEN = "7";
    private final static String EIGHT = "8";
    private final static String NINE = "9";
    private final static String TEN = "10";

    private final static String HEARTS = "♠";
    private final static String CLUBS = "♣";
    private final static String SPADES = "♥";
    private final static String DIAMONDS = "♦";

    public CardDeck() {

        String[] suits = {SPADES, CLUBS, HEARTS, DIAMONDS};
        String[] values = {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};

        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(value, suit, 0, 0, true));
            }
        }
    }

    public int getDeckSize() {
        return deck.size();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Random random = new Random();
        int random_index;

        for (int i = deck.size() - 1; i > 0; i--) {
            random_index = random.nextInt(i + 1);

            Card temp_var = deck.get(i);

            deck.set(i, deck.get(random_index));
            deck.set(random_index, temp_var);
        }
    }

    public Card getCard() {
        return deck.getFirst();
    }

    public void removeCard() {
        deck.removeFirst();
    }
}
