public interface Collidable {
    /**
     * collide
     * called upon collision with another game object.
     * @param other the other object in the interaction.
     * @return a new GameObject or null if no new game object is created
     */
    GameObject collide(GameObject other);
}
