/**
 * Represents the biological sex of specific game objects
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public enum Sex {
    MALE,
    FEMALE;

    /**
     *
     */
    public static Sex randomSex() {
        int sex = (int) (Math.random() * 2);

        switch (sex) {
            case 0:
                return Sex.MALE;
            case 1:
                return Sex.FEMALE;
            default:
                // TODO: ask mangat
                throw new RuntimeException("This should never happen");
        }
    }
}
