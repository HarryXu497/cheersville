import java.awt.*;

/**
 * Represents a person game object
 * Changes:
 *  - Hungry-ness: Getting food will be prioritized over reproduction if a person is hungry
 *      - HUNGRY_THRESHOLD constant (line 29)
 *      - isHungry method (line 149)
 *  - Movement: a person is more likely to continue walking in the same direction
 *      - SAME_DIRECTION_CHANCE constant (line 47)
 *      - lastDirection to track the previous direction (line 62)
 *      - can move smartly towards a target (line 258)
 *  - Sprites
 *      - animations and sprites (line 56-59)
 *  - Reproduction cooldown
 *      - lastReproduced instance variable (line 44)
 *  - Vision
 *      - the person can only see grass within a certain region around it (line 65)
 *  - Aging
 *      - Health is lost at a non-linear rate which increases exponentially as age increases (line 180)
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class Person extends GameObject implements Movable, Collidable, DirectedMovable, Controllable {
    /**
     * a double from 0 to 1 that determines at what percentage of the max health is the person considered hungry
     * This will create a change in behaviour from the person
     */
    public static double HUNGRY_THRESHOLD = 0.5;

    /** determines how the health loss rate */
    public static double HEALTH_LOSS_RATE = 0.01;

    /** The sex of the person, which determines if reproduction can occur*/
    private final Sex sex;

    /** The age when this person can reproduce */
    private final int AGE_OF_CONSENT = 18;

    /** The amount of years that must go by before this person can reproduce again */
    private final int REPRODUCTION_COOLDOWN = 3;

    /** The age when this person reproduced, which allows a cooldown to be implemented */
    private int lastReproduced = AGE_OF_CONSENT - REPRODUCTION_COOLDOWN;

    /** the chance from 0 to 1 that a person remains moving in the same direction */
    private static final double SAME_DIRECTION_CHANCE = 0.2;

    /** The average vision of a person */
    public static int AVERAGE_VISION = 15;

    /** The maximum deviation from this vision that can occur */
    private static final int VISION_DEVIATION = 5;

    /** Sprites */
    private final SpriteList walkingUpSprites;
    private final SpriteList walkingDownSprites;
    private final SpriteList walkingLeftSprites;
    private final SpriteList walkingRightSprites;

    /** a person is more likely to continue walking in the same direction */
    private Direction lastDirection;

    /** the radius around which a person can see objects and react to them */
    private int vision;

    /** the direction to move in when controlled by the player */
    private Direction playerDirection;

    /**
     * constructs a person with a sex and age
     * @param sex the biological sex of the person
     * */
    public Person(Sex sex) {
        super();

        this.sex = sex;

        // random number
        this.vision = (int) ((Math.random() * AVERAGE_VISION - VISION_DEVIATION + 1) + (VISION_DEVIATION * 2));

        // Pick from 2 spritesheets
        if (Math.random() < 0.5) {
            this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_1_UP_SPRITES);
            this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_1_DOWN_SPRITES);
            this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_1_LEFT_SPRITES);
            this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_1_RIGHT_SPRITES);
        } else {
            this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_2_UP_SPRITES);
            this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_2_DOWN_SPRITES);
            this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_2_LEFT_SPRITES);
            this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_2_RIGHT_SPRITES);
        }

        this.lastDirection = Direction.random();
        this.updateSprite(this.lastDirection);
    }

    /**
     * constructs a person with a custom health, sex, and age
     * @param health the health of the person
     * @param sex the biological sex of the person
     * */
    public Person(int health, Sex sex) {
        super(health);

        this.sex = sex;

        // Pick from 2 spritesheets
        if (Math.random() < 0.5) {
            this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_1_UP_SPRITES);
            this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_1_DOWN_SPRITES);
            this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_1_LEFT_SPRITES);
            this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_1_RIGHT_SPRITES);
        } else {
            this.walkingUpSprites = new SpriteList(SpriteSheet.PERSON_2_UP_SPRITES);
            this.walkingDownSprites = new SpriteList(SpriteSheet.PERSON_2_DOWN_SPRITES);
            this.walkingLeftSprites = new SpriteList(SpriteSheet.PERSON_2_LEFT_SPRITES);
            this.walkingRightSprites = new SpriteList(SpriteSheet.PERSON_2_RIGHT_SPRITES);
        }

        this.lastDirection = Direction.random();
        this.updateSprite(this.lastDirection);
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
     * getVision
     * returns the vision of this person
     * @return the vision of this person as an integer
     */
    public int getVision() {
        return this.vision;
    }

    /**
     * update
     * called to update the state of the person object
     */
    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        // A person loses health proportional to their age
        double healthToLose = Math.pow(2, (HEALTH_LOSS_RATE * this.getAge()));

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
                Sex newSex = Sex.randomSex();

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

        // Generate a random direction
        Direction direction = Direction.random();

        // Keep track of the last direction
        this.lastDirection = direction;

        // Update the sprite to match the direction of movement
        this.updateSprite(direction);

        return direction;
    }

    /**
     * move
     * moves the player in a "smart" way, being provided the object's position and the target location
     * @param position the position of the player on the grid
     * @param target the target position the grid
     * @return the direction of travel
     * @throws NullPointerException if either or both of the points are null
     */
    @Override
    public Direction move(Point position, Point target) {
        if ((position == null) || (target == null)) {
            throw new NullPointerException("Points must not be null.");
        }

        // Default to last direction
        Direction direction;

        // Move towards grass
        // move in a straight line if in axis with the grass
        if (position.y == target.y) {
            if (position.x > target.x) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }
        } else if (position.x == target.x) {
            if (position.y > target.y) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        } else {
            // Move in either of the two directions needed to get to the grass
            if ((position.y > target.y) && (position.x > target.x)) {
                // object is to the bottom right of the target
                if (Math.random() > 0.5) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.LEFT;
                }
            } else if (position.y > target.y) {
                // object is to the bottom left of the target
                if (Math.random() > 0.5) {
                    direction = Direction.UP;
                } else {
                    direction = Direction.RIGHT;
                }
            } else if (position.x > target.x) {
                // object is to the top right of the target
                if (Math.random() > 0.5) {
                    direction = Direction.DOWN;
                } else {
                    direction = Direction.LEFT;
                }
            } else {
                // object is to the top left of the target
                if (Math.random() > 0.5) {
                    direction = Direction.DOWN;
                } else {
                    direction = Direction.RIGHT;
                }
            }
        }

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
        // Maintain previous sprite if direction is null
        if (direction == null) {
            return;
        }

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

    /**
     * playerMove
     * returns the direction to move in to the caller
     * @return the direction to move in as an enum constant
     */
    @Override
    public Direction playerMove() {
        this.updateSprite(this.playerDirection);
        return this.playerDirection;
    }

    /**
     * setPlayerMove
     * sets the direction in which the player moves,
     * which allows outside output (i.e. the keyboard) to control movement
     * @param direction the new direction to move towards
     */
    @Override
    public void setPlayerMove(Direction direction) {
        this.playerDirection = direction;
    }
}
