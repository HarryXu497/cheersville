

import java.awt.*;

public class Person extends GameObject implements Movable, Collidable {

    private int age;
    private Sex sex;

    /**
     * constructs a person with a sex and age
     * @param sex the biological sex of the person
     * @param age the age of the person
     * */
    public Person(Sex sex, int age) {
        super();

        this.sex = sex;
        this.age = age;

    }

    /**
     * constructs a person with a custom health, sex, and age
     * @param health the health of the person
     * @param sex the biological sex of the person
     * @param age the age of the person
     * */
    public Person(int health, Sex sex, int age) {
        super(health);

        this.sex = sex;
        this.age = age;
    }

    @Override
    public void update() {
        this.age++;
        this.setHealth(Math.max(this.getHealth() - 2, 0));
    }

    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person o = (Person) other;
            if ((
                ((this.sex == Sex.MALE) && (o.sex == Sex.FEMALE)) ||
                ((this.sex == Sex.FEMALE) && (o.sex == Sex.MALE))) &&
                ((this.age >= 18) && (o.getAge() >= 18))
            ) {
                // Reproduce
                Sex newSex;

                if (Math.random() > 0.5) {
                    newSex = Sex.FEMALE;
                } else {
                    newSex = Sex.MALE;
                }

                return new Person(newSex, 1);
            }
        }

        return null;
    }

    @Override
    public Color draw() {
        Color color;

        if (this.age < 18) {
            return Color.RED;
        }

        if (this.sex == Sex.FEMALE) {
            color = Color.YELLOW;
        } else {
            color = Color.BLUE;
        }

        return color;
    }

    /**
     * move
     * moves the player in a random direction in one of its 4 adjacent tiles
     */
    @Override
    public Direction move() {
        // Generate a random valid adjacent position
        int moveDirection = (int) (Math.random() * 4);

        // move up
        if (moveDirection == 0) {
            return Direction.UP;
        } else if (moveDirection == 1) {
            return Direction.DOWN;
        } else if(moveDirection == 2) {
            return Direction.LEFT;
        } else {
            return Direction.RIGHT;
        }
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
     * getAge
     * returns the age of the person
     * @return the age of the person as an integer
     */
    public int getAge() {
        return this.age;
    }
}
