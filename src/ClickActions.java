/**
 * Represents the different actions that occur with a mouse click
 * @author Harry Xu
 * @version 1.0 - May 15th 2023
 */
public enum ClickActions {
    PERSON,
    ZOMBIE,
    WATER,
    SELECT;

    /**
     * toGameObject
     *  maps a ClickAction to a game object for ease of use
     * @param action the action to map to
     * @return the game object
     * @throws NullPointerException if action is null
     * @throws IllegalArgumentException if the action is SELECT, as it cannot be mapped to a game object
     * */
    public static GameObject toGameObject(ClickActions action) {
        if (action == null) {
            throw new NullPointerException("action cannot be null");
        }

        switch (action) {
            case PERSON:
                return new Person(Sex.randomSex());
            case ZOMBIE:
                return new Zombie();
            case WATER:
                return new Water();
            case SELECT:
                throw new IllegalArgumentException("The SELECT action cannot be mapped to a game object");
        }

        // TODO: ask mangat
        throw new RuntimeException("This should never happen");
    }
}
