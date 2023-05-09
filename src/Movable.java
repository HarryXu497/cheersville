/**
 * An object that can move (i.e. return a direction to the caller)
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public interface Movable {
    /**
     * move
     * generates a direction and returns it to the caller
     * @return the direction to move in
     */
    Direction move();
}
