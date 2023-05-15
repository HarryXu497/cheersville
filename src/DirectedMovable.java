import java.awt.*;

/**
 * Represents a movable with "smarter" movements,
 * which requires their position and a target position
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 * */
public interface DirectedMovable {
    Direction move(Point position, Point target);
}
