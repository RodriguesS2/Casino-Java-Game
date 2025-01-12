package casino.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {
    Position position;

    @BeforeEach
    void setup() {
        position = new Position(5, 10);
    }

    @Test
    void testGetX() {
        assertEquals(5, position.getX(), "getX should return the x value");
    }

    @Test
    void testGetY() {
        assertEquals(10, position.getY(), "getY should return the y value");
    }

    @Test
    public void testEquals_SamePositionShouldReturnTrue() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(1, 1);
        assertEquals(position1, position2);
    }


    @Test
    public void testEquals_DifferentPositionShouldReturnFalse() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);
        assertNotEquals(position1, position2);
    }

    @Test
    public void testEquals_NullAndDifferentClassShouldReturnFalse() {
        Position position = new Position(1, 1);
        assertNotEquals(position, null);
        assertNotEquals(position, new Object());
    }

    @Test
    public void testHashCode_SameValuesShouldReturnSameHashCode() {
        Position position1 = new Position(1, 1);
        assertEquals(position1.hashCode(), position1.hashCode());
    }

    @Test
    public void testHashCode_DifferentValuesShouldReturnDifferentHashCode() {
        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);
        assertNotEquals(position1.hashCode(), position2.hashCode());
    }
}
