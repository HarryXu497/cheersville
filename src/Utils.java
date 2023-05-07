import java.awt.*;

public class Utils {
    public static boolean validPosition(Point point, GameObject[][] map) {
        return validPosition(point.x, point.y, map);
    }

    public static boolean validPosition(int x, int y, GameObject[][] map) {
        // Valid y index
        boolean validY = ((0 <= y) && (y < map.length));

        if (!validY) {
            return false;
        }

        // Valid x index
        return ((0 <= x) && (x < map[y].length));
    }
}
