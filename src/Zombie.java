/**
 * represents a zombie game object
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 * */
public class Zombie extends GameObject implements Movable, Collidable {

    /** the chance from 0 to 1 that a person remains moving in the same direction */
    private static final double SAME_DIRECTION_CHANCE = 0.1;

    /** The percentage health loss per update */
    public static double HEALTH_LOSS_RATE = 0.01;

    /** Sprites */
    private final SpriteList walkingUpSprites;
    private final SpriteList walkingDownSprites;
    private final SpriteList walkingLeftSprites;
    private final SpriteList walkingRightSprites;

    /** a person is more likely to continue walking in the same direction */
    private Direction lastDirection;

    /**
     * constructs a zombie with a default health
     */
    public Zombie() {
        super();

        this.walkingUpSprites = new SpriteList(SpriteSheet.ZOMBIE_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.ZOMBIE_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.ZOMBIE_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.ZOMBIE_RIGHT_SPRITES);

        this.lastDirection = Direction.random();
        this.updateSprite(this.lastDirection);
    }

    /**
     * constructs a zombie with a custom health
     * @param health the custom health to spawn with
     */
    public Zombie(int health) {
        super(health);

        this.walkingUpSprites = new SpriteList(SpriteSheet.ZOMBIE_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.ZOMBIE_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.ZOMBIE_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.ZOMBIE_RIGHT_SPRITES);

        this.lastDirection = Direction.random();
        this.updateSprite(this.lastDirection);
    }

    /**
     * update
     * called to update the state of the zombie object
     */
    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        this.setHealth(Math.max(this.getHealth() - (HEALTH_LOSS_RATE * GameObject.DEFAULT_MAXIMUM_HEALTH), 0));
    }

    /**
     * collide
     * called upon collision with another game object.
     * @param other the other object in the interaction.
     * @return a new {@link GameObject} or null if no new game object is created
     */
    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person person = (Person) other;
            double personHealth = person.getHealth();

            // Kills the person
            person.setHealth(0);

            if (this.getHealth() <= personHealth) {
                // Infect them
                return new Zombie();
            } else {
                // Increase health
                this.setHealth(Math.min(this.getHealth() + personHealth, 100));
            }
        }

        if (other instanceof Grass) {
            Grass grass = (Grass) other;

            grass.setHealth(0);
        }

        return null;
    }

    /**
     * move
     * moves the player in a random direction in one of its 4 adjacent tiles
     */
    @Override
    public Direction move() {
        // zombie is more likely to continue moving in the same direction
        if (Math.random() < SAME_DIRECTION_CHANCE) {
            return this.lastDirection;
        }

        // Generate a random direction
        Direction direction = Direction.random();

        // Keep track of the last direction
        this.lastDirection = direction;

        // Update the sprite to match the direction of movement
        this.updateSprite(direction);

        return direction;
    }

    /**
     * updateSprite
     * updates the sprite in the appropriate fashion according to the direction of movement
     * @param direction the direction of movement
     */
    private void updateSprite(Direction direction) {
        switch (direction) {
            case UP:
                this.setCurrentSprite(this.walkingUpSprites.nextImage());
                break;
            case DOWN:
                this.setCurrentSprite(this.walkingDownSprites.nextImage());
                break;
            case LEFT:
                this.setCurrentSprite(this.walkingLeftSprites.nextImage());
                break;
            case RIGHT:
                this.setCurrentSprite(this.walkingRightSprites.nextImage());
                break;
        }
    }
}
