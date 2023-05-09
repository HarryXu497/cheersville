/**
 * Represents a collidable object, whose collide method is called when it tries to move into another game object
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public interface Collidable {
    /**
     * collide
     * called upon collision with another game object.
     * @param other the other object in the interaction.
     * @return a new GameObject or null if no new game object is created
     */
    GameObject collide(GameObject other);
}
