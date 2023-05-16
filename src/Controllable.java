import java.awt.event.KeyEvent;

/**
 * represents an entity that can be controlled by the player
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 */
public interface Controllable {
    Direction playerMove();
    void setPlayerMove(Direction direction);
}
