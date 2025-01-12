package casino.game.model.game.elements;


public class Card extends Element {

    private final String suit;
    private final String value;
    private boolean faceUp;
    private final static String ACE = "A";

    public Card(String value, String suit, int x, int y, boolean faceUp) {
        super(x, y);
        this.faceUp = faceUp;
        this.suit = suit;
        this.value = value;
    }

    public boolean isAce() {
        return value.equals(ACE);
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + suit;
    }

    public void setFaceUp(boolean faceUp) {
         this.faceUp = faceUp;
    }

    public boolean isFaceUp() {
        return faceUp;
    }
}