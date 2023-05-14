/**
 * represents the 4 ordinal directions a game object can travel
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /**
     * fromInteger
     * converts an integer from 0 to 3 inclusive to a direction
     * @param x the int to map a direction to
     * @return a random Direction
     * @throws IllegalArgumentException if no mapping can be made from x to a direction
     */
    public static Direction fromInteger(int x) {
        switch (x) {
            case 0:
                return UP;
            case 1:
                return DOWN;
            case 2:
                return LEFT;
            case 3:
                return RIGHT;
            default:
                throw new IllegalArgumentException("x must be either 0, 1, 2, or 3.");
        }
    }

    /**
     * random
     * generates and returns a random direction
     * @return a random Direction
     */
    public static Direction random() {
        return Direction.fromInteger((int) (Math.random() * 4));
    }
}
