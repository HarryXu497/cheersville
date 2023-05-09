import java.awt.*;

/**
 * Represents a person game object
 * Changes:
 *  - Hungry-ness: Getting food will be prioritized over reproduction if a person is hungry
 *      - HUNGRY_THRESHOLD constant (line 24)
 *      - isHungry method (line 110)
 *  - Movement: a person is more likely to continue walking in the same direction
 *      - SAME_DIRECTION_CHANCE constant (line 39)
 *      - lastDirection to track the previous direction (line 50)
 *  - Sprites
 *      - animations and sprites (line 42, 44, 45, 46, 47)
 *  - Reproduction cooldown
 *      - lastReproduced instance variable (line 36)
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class Person extends GameObject implements Movable, Collidable {
    /**
     * a double from 0 to 1 that determines at what percentage of the max health is the person considered hungry
     * This will create a change in behaviour from the person
     */
    private static final double HUNGRY_THRESHOLD = 0.5;

    /** The sex of the person, which determines if reproduction can occur*/
    private final Sex sex;

    /** The age when this person can reproduce */
    private final int AGE_OF_CONSENT = 18;

    /** The amount of years that must go bye before this person can reproduce again */
    private final int REPRODUCTION_COOLDOWN = 2;

    /** The age when this person reproduced, which allows a cooldown to be implemented */
    private int lastReproduced = AGE_OF_CONSENT - REPRODUCTION_COOLDOWN;

    /** the chance from 0 to 1 that a person remains moving in the same direction */
    private static final double SAME_DIRECTION_CHANCE = 0.2;

    /** Sprites */
    private Image currentSprite;

    private final SpriteList walkingUpSprites;
    private final SpriteList walkingDownSprites;
    private final SpriteList walkingLeftSprites;
    private final SpriteList walkingRightSprites;

    /** a person is more likely to continue walking in the same direction */
    private Direction lastDirection;

    /**
     * constructs a person with a sex and age
     * @param sex the biological sex of the person
     * */
    public Person(Sex sex) {
        super();

        this.sex = sex;

        this.lastDirection = Direction.fromInteger((int) (Math.random() * 3));

        this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_RIGHT_SPRITES);
        this.currentSprite = this.walkingUpSprites.nextImage();
    }

    /**
     * constructs a person with a custom health, sex, and age
     * @param health the health of the person
     * @param sex the biological sex of the person
     * */
    public Person(int health, Sex sex) {
        super(health);

        this.sex = sex;

        this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_UP_SPRITES);
        this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_DOWN_SPRITES);
        this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_LEFT_SPRITES);
        this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_RIGHT_SPRITES);
        this.currentSprite = this.walkingUpSprites.nextImage();
    }

    /**
     * getLastReproduced
     * gets the age at which a person last reproduced
     * @return the age as an int
     */
    public int getLastReproduced() {
        return this.lastReproduced;
    }

    /**
     * setLastReproduced
     * sets the age at which a person last reproduced
     * @param age the new age as an int
     */
    public void setLastReproduced(int age) {
        this.lastReproduced = age;
    }

    /**
     * isHungry
     * gets if the person is hungry, marking a change in behaviour
     * @return if the person is hungry.
     * */
    public boolean isHungry() {
        return this.getHealth() < HUNGRY_THRESHOLD * GameObject.DEFAULT_MAXIMUM_HEALTH;
    }

    /**
     * getSex
     * returns the sex of this person
     * @return the sex of this person as a Sex enum
     */
    public Sex getSex() {
        return this.sex;
    }

    /**
     * update
     * called to update the state of the person object
     */
    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        // A person loses health proportional to their age
        double healthToLose = Math.pow(2, ((1.0 / 100.0) * this.getAge()));

        this.setHealth(Math.max(this.getHealth() - healthToLose, 0));
    }

    /**
     * collide
     * called upon collision with another game object.
     * @param other the other object in the interaction.
     * @return a new GameObject or null if no new game object is created
     */
    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person p = (Person) other;
            if ((
                ((this.sex == Sex.MALE) && (p.sex == Sex.FEMALE)) ||
                ((this.sex == Sex.FEMALE) && (p.sex == Sex.MALE))) &&
                ((this.getAge() >= AGE_OF_CONSENT) && (p.getAge() >= AGE_OF_CONSENT)) &&
                ((!this.isHungry()) && (!p.isHungry())) &&
                ((this.getAge() - this.getLastReproduced() >= REPRODUCTION_COOLDOWN) && (p.getAge() - p.getLastReproduced() >= REPRODUCTION_COOLDOWN))
            ) {
                // Reproduce
                Sex newSex;

                if (Math.random() > 0.5) {
                    newSex = Sex.FEMALE;
                } else {
                    newSex = Sex.MALE;
                }

                // Set cooldown
                this.setLastReproduced(this.getAge());
                p.setLastReproduced(p.getAge());

                return new Person(newSex);
            }
        }

        if (other instanceof Grass) {
            Grass g = (Grass) other;

            // replenishes an amount of health, maxing out at the maximum health
            this.setHealth(Math.min(this.getHealth() + g.getNutritionalValue(), GameObject.DEFAULT_MAXIMUM_HEALTH));

            g.setHealth(0);
        }

        return null;
    }

    /**
     * move
     * moves the player in a semi-random direction in one of its 4 adjacent tiles
     */
    @Override
    public Direction move() {
        // person is more likely to continue moving in the same direction
        if (Math.random() < SAME_DIRECTION_CHANCE) {
            return this.lastDirection;
        }

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

        // Keep track of the last direction
        this.lastDirection = direction;

        return direction;
    }

    /**
     * draw
     * called to get the person sprite to draw
     */
    @Override
    public Image draw() {
        return this.currentSprite;
    }
}
