/**
 * represents an entity that can be controlled by the player
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 */
public interface Controllable {
    /**
     * playerMove
     * returns the direction to move in to the caller
     * @return the direction to move in as an enum constant
     */
    Direction playerMove();

    /**
     * setPlayerMove
     * sets the direction in which the player moves,
     * which allows outside output (i.e. the keyboard) to control movement
     * @param direction the new direction to move towards
     */
    void setPlayerMove(Direction direction);
}
