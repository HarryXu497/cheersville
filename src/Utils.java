import java.awt.*;

/**
 * A class containing static utility methods
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class Utils {
    /**
     * validPosition
     * checks if a point on a map is valid (i.e. in bounds)
     * @param point the point to check
     * @param map the reference map
     * @return if the position is in bounds
     */
    public static boolean validPosition(Point point, Object[][] map) {
        return validPosition(point.x, point.y, map);
    }

    /**
     * validPosition
     * checks if a point on a map is valid (i.e. in bounds)
     * @param x the x value of the point to check
     * @param y the y value of the point to check
     * @param map the reference map
     * @return if the position is in bounds
     */
    public static boolean validPosition(int x, int y, Object[][] map) {
        // Valid y index
        boolean validY = ((0 <= y) && (y < map.length));

        if (!validY) {
            return false;
        }

        // Valid x index
        return ((0 <= x) && (x < map[y].length));
    }
}
