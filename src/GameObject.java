public abstract class GameObject {
    // The maximum health of the object, which is also the default health
    private static int DEFAULT_MAXIMUM_HEALTH = 100;

    // health of the object
    private int health;

    // Position
    private int x;
    private int y;

    /**
     * constructs a new game object with a default health
     */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = DEFAULT_MAXIMUM_HEALTH;
    }

    /**
     * constructs a new game object with a default health
     * @param health the custom health of the object
     * @throws IllegalArgumentException if the health is negative or larger than the maximum (100).
     */
    public GameObject(int x, int y, int health) {
        if (health < 0) {
            throw new IllegalArgumentException("health " + health + " is negative.");
        }
        if (health > DEFAULT_MAXIMUM_HEALTH) {
            throw new IllegalArgumentException("health " + health + " is larger than the maximum health " + DEFAULT_MAXIMUM_HEALTH + ".");
        }

        this.health = health;
        this.x = x;
        this.y = y;
    }

    /**
     * getHealth
     * gets the health of the game object
     * @return the health as an integer
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * setHealth
     * sets the health of the game object
     * @param health the new health of the object
     * @throws IllegalArgumentException if the health is negative or larger than the maximum (100).
     */
    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("health " + health + " is negative.");
        }
        if (health > DEFAULT_MAXIMUM_HEALTH) {
            throw new IllegalArgumentException("health " + health + " is larger than the maximum health " + DEFAULT_MAXIMUM_HEALTH + ".");
        }

        this.health = health;
    }

    /**
     * update
     * called to update the state of the game object (i.e. called once every loop.
     */
    public abstract void update();

    /**
     * collide
     * called upon collision with another game object.
     */
    public abstract void collide(GameObject other);
}
