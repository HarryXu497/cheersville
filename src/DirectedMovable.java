import java.awt.*;

/**
 * Represents a movable with "smarter" movements,
 * which requires their position and a target position
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 * */
public interface DirectedMovable {
    /**
     * move
     * allows the object to move given location information about the object and its target
     * @param position the point at which the object is located on the mop
     * @param target the point at which the object's target is located on the map
     * @return the direction to move in
     * */
    Direction move(Point position, Point target);
}
