import java.awt.*;

public class Zombie extends GameObject implements Movable, Collidable {

    /**
     * a double between 1 and 0 which determines how likely it is for zombies to start hunting
     * */
    private static final double HUNTING_THRESHOLD = 0.5;

    private int huntingStartAge;
    private boolean isHunting;

    public Zombie() {
        super();

        this.huntingStartAge = this.getAge();
        this.isHunting = false;
    }

    public Zombie(int health) {
        super(health);

        this.huntingStartAge = this.getAge();
        this.isHunting = true;
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        if ((!this.isHunting) && (Math.random() > HUNTING_THRESHOLD)) {
            this.isHunting = true;
            this.huntingStartAge = this.getAge();
        }

        if ((this.isHunting) && (this.getAge() - this.huntingStartAge >= 8)) {
            this.isHunting = false;
        }

        this.setHealth(Math.max(this.getHealth() - 4, 0));
    }

    /**
     * isHunting
     * if the zombie is on the hunt for a person
     * @return if the zombie is hunting
     * */
    public boolean isHunting() {
        return this.isHunting;
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

            // Stop hunting
            this.isHunting = false;
        }

        if (other instanceof Grass) {
            Grass grass = (Grass) other;

            grass.setHealth(0);
        }


        return null;
    }

    @Override
    public Color draw() {
        return Color.BLACK;
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
