import java.awt.*;

public class Zombie extends GameObject implements Movable, Collidable {


    public Zombie() {
        super();

    }

    public Zombie(int health) {
        super(health);
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
