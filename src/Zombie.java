import java.awt.*;

/**
 * represents a zombie game object
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 * */
public class Zombie extends GameObject implements Movable, Collidable {

    /** Sprites */
    private Image currentSprite;

    private final SpriteList walkingUpSprites;
    private final SpriteList walkingDownSprites;
    private final SpriteList walkingLeftSprites;
    private final SpriteList walkingRightSprites;

    /**
     * constructs a zombie with a default health
     */
    public Zombie() {
        super();

        this.walkingUpSprites = new SpriteList(SpriteSheet.ZOMBIE_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.ZOMBIE_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.ZOMBIE_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.ZOMBIE_RIGHT_SPRITES);
        this.currentSprite = this.walkingUpSprites.nextImage();
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
        this.currentSprite = this.walkingUpSprites.nextImage();
    }

    /**
     * update
     * called to update the state of the zombie object
     */
    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        this.setHealth(Math.max(this.getHealth() - 4, 0));
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
        // Generate a random valid adjacent position
        int moveDirection = (int) (Math.random() * 4);

        Direction direction = Direction.fromInteger(moveDirection);

        switch (direction) {
            case UP:
                this.currentSprite = this.walkingUpSprites.nextImage();
                break;
            case DOWN:
                this.currentSprite = this.walkingDownSprites.nextImage();
                break;
            case LEFT:
                this.currentSprite = this.walkingLeftSprites.nextImage();
                break;
            case RIGHT:
                this.currentSprite = this.walkingRightSprites.nextImage();
                break;
        }

        return direction;
    }

    /**
     * draw
     * called to get the zombie sprite to draw
     */
    @Override
    public Image draw() {
        return this.currentSprite;
    }
}
