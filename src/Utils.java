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

    /**
     * canMove
     * checks if a game object at a specified point has any open adjacent tiles
     * The adjacent tile can also be grass, as game objects can walk over grass
     * @param x the x position of the coordinate
     * @param y the y position of the coordinate
     * @param map the map on which to check
     * @return if any adjacent tiles are open
     * */
    public static boolean canMove(int x, int y, GameObject[][] map) {
        if (
            ((!Utils.validPosition(x + 1, y, map)) || ((map[y][x + 1] != null) && !(map[y][x + 1] instanceof Grass))) &&
            ((!Utils.validPosition(x - 1, y, map)) || ((map[y][x - 1] != null) && !(map[y][x - 1] instanceof Grass))) &&
            ((!Utils.validPosition(x, y + 1, map)) || ((map[y + 1][x] != null) && !(map[y + 1][x] instanceof Grass))) &&
            ((!Utils.validPosition(x, y - 1, map)) || ((map[y - 1][x] != null) && !(map[y - 1][x] instanceof Grass)))
        ) {
            return false;
        }

        return true;
    }

    /**
     * directionToTile
     * maps a direction and an origin point to the corresponding neighbor
     * @param x the x coordinate of the origin point
     * @param y the y coordinate of the origin point
     * @param direction the direction to traverse
     * @return the new generated point
     * */
    public static Point directionToTile(int x, int y, Direction direction) {
        // Return the current point if direction is null
        if (direction == null) {
            return new Point(x, y);
        }

        // Move the object
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        return new Point(x, y);
    }


    /**
     * generateNextPosition
     * generates a valid new position point in the map using a game object
     * @param x the x coordinate of the game object
     * @param y the y coordinate of the game object
     * @param currentGameObj the game object to generate the directions from
     * @param map the game map
     * */
    public static Point generateNextPosition(int x, int y, Movable currentGameObj, GameObject[][] map) {
        Point newLocation;

        do {
            Direction direction = currentGameObj.move();
            newLocation = Utils.directionToTile(x, y, direction);
        } while ((!Utils.validPosition(newLocation, map)));

        return newLocation;
    }
}
