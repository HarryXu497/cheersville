public class Person extends GameObject implements Movable {

    private int age;
    private Sex sex;

    public Person(int x, int y, Sex sex, int age) {
        super(x, y);

        this.sex = sex;
        this.age = age;
    }

    public Person(int x, int y, int health, Sex sex, int age) {
        super(x, y, health);

        this.sex = sex;
        this.age = age;
    }

    @Override
    public void update() {
        this.age++;
        this.setHealth(this.getHealth() - 1);
    }

    @Override
    public void collide(GameObject other) {

    }

    @Override
    public void move() {

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
