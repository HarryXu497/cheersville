import java.awt.*;

public class Zombie extends GameObject implements Movable, Collidable {

    private final SpriteList walkingUpSprites;
    private final SpriteList walkingDownSprites;
    private final SpriteList walkingLeftSprites;
    private final SpriteList walkingRightSprites;

    private Image currentSprite;

    public Zombie() {
        super();

        this.walkingUpSprites = new SpriteList(SpriteSheet.ZOMBIE_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.ZOMBIE_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.ZOMBIE_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.ZOMBIE_RIGHT_SPRITES);
        this.currentSprite = this.walkingUpSprites.nextImage();
    }

    public Zombie(int health) {
        super(health);

        this.walkingUpSprites = new SpriteList(SpriteSheet.ZOMBIE_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.ZOMBIE_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.ZOMBIE_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.ZOMBIE_RIGHT_SPRITES);
        this.currentSprite = this.walkingUpSprites.nextImage();
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        this.setHealth(Math.max(this.getHealth() - 4, 0));
    }


    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person person = (Person) other;
            // Kills the person
            person.setHealth(0);

            // Infect them
            if (this.getHealth() > person.getHealth()) {
                return new Zombie();
            }
        }

        if (other instanceof Grass) {
            Grass grass = (Grass) other;

            grass.setHealth(0);
        }


        return null;
    }

    @Override
    public Image draw() {
        return this.currentSprite;
    }

    /**
     * move
     * moves the player in a random direction in one of its 4 adjacent tiles
     */
    @Override
    public Direction move() {
        // Generate a random valid adjacent position
        int moveDirection = (int) (Math.random() * 4);

        if (moveDirection == 0) {
            this.currentSprite = this.walkingUpSprites.nextImage();
            return Direction.UP;
        } else if (moveDirection == 1) {
            this.currentSprite = this.walkingDownSprites.nextImage();
            return Direction.DOWN;
        } else if (moveDirection == 2) {
            this.currentSprite = this.walkingLeftSprites.nextImage();
            return Direction.LEFT;
        } else {
            this.currentSprite = this.walkingRightSprites.nextImage();
            return Direction.RIGHT;
        }
    }
}
