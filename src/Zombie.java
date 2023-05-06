import java.awt.*;

public class Zombie extends GameObject implements Movable, Collidable {

    private int age;

    public Zombie(int age) {
        super();

        this.age = age;
    }

    public Zombie(int health, int age) {
        super(health);

        this.age = age;
    }

    /**
     * getAge
     * gets the age of the zombie
     * @return the age of the zombie as an int
     */
    public int getAge() {
        return this.age;
    }

    @Override
    public void update() {
        this.age++;
        this.setHealth(Math.max(this.getHealth() - 2, 0));
    }

    @Override
    public GameObject collide(GameObject other) {
        if (other instanceof Person) {
            Person person = (Person) other;
            // Kills the person
            person.setHealth(0);

            // Infect them
            if (this.getHealth() > person.getHealth()) {
                return new Zombie(1);
            }
        }


        return null;
    }

    @Override
    public Color draw() {
        return Color.GREEN;
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
}
