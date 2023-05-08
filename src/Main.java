/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

import java.awt.*;
import java.util.List;

class Main {


    public static void main(String[] args) {

        GameObject[][] map = new GameObject[20][20];

        map[3][3] = new Person(Sex.MALE);
        map[5][5] = new Person(Sex.MALE);
        map[7][7] = new Person(Sex.FEMALE);
        map[9][9] = new Person(Sex.FEMALE);
        map[11][11] = new Grass();

        // Initialize Map
//        moveItemsOnGrid(map);

        // display the fake grid on Console
        //DisplayGridOnConsole(map);

        //Set up Grid Panel
        // DisplayGrid grid = new DisplayGrid(map);
        MatrixDisplayWithMouse grid = new MatrixDisplayWithMouse("title", map);

        while(true) {
            //Display the grid on a Panel
            grid.refresh();


            //Small delay
            try{ Thread.sleep(100); }catch(Exception e) {};


            // Initialize Map (Making changes to map)
            moveItemsOnGrid(map);

            //Display the grid on a Panel
            grid.refresh();
        }
    }


    // Method to simulate grid movement
    public static void moveItemsOnGrid(GameObject[][] map) {

        GameObject[][] mapCopy = map.clone();

        for (int i = 0; i < mapCopy.length; i++) {
            mapCopy[i] = map[i].clone();
        }

        for (int y = 0; y < mapCopy.length; y++) {
            for (int x = 0; x < mapCopy[y].length; x++) {
                GameObject currentGameObj = mapCopy[y][x];

                if (currentGameObj == null) {
                    continue;
                }

                // Remove dead game objects
                if (currentGameObj.getHealth() <= 0) {
                    map[y][x] = null;
                    continue;
                }

                // Update object
                currentGameObj.update();

                // Move object if it has open adjacent tiles and implements Movable
                if ((currentGameObj instanceof Movable) && (canMove(x, y, map))) {
                    Point newLocation;

                    // If the person is hungry, move them towards the closest grass tile
                    if ((currentGameObj instanceof Person) && (((Person) currentGameObj).isHungry())) {
                        int minDistance = Integer.MAX_VALUE;
                        Point target = null;

                        for (int grassY = 0; grassY < mapCopy.length; grassY++) {
                            for (int grassX = 0; grassX < mapCopy[y].length; grassX++) {
                                if (map[grassY][grassX] instanceof Grass) {
                                    int distance = (Math.abs(grassX - x)) + (Math.abs(grassY - y));
                                    if (distance < minDistance) {
                                        minDistance = distance;
                                        target = new Point(grassX, grassY);
                                    }
                                }
                            }
                        }

                        // Target found
                        if (target != null) {
                            Direction direction;

                            if (y > target.y) {
                                direction = Direction.UP;
                            } else if (y < target.y) {
                                direction = Direction.DOWN;
                            } else if (x < target.x) {
                                direction = Direction.RIGHT;
                            } else {
                                direction = Direction.LEFT;
                            }

                            newLocation = directionToTile(x, y, direction);
                        } else {
                            // TODO: code duplication
                            // If hungry but no grass, move random
                            do {
                                Direction direction = ((Movable) currentGameObj).move();
                                newLocation = directionToTile(x, y, direction);
                            } while (!Utils.validPosition(newLocation, map));
                        }
                    } else {
                        // Else use random movement
                        do {
                            Direction direction = ((Movable) currentGameObj).move();
                            newLocation = directionToTile(x, y, direction);
                        } while (!Utils.validPosition(newLocation, map));
                    }

                    int newX = newLocation.x;
                    int newY = newLocation.y;

                    // Check if position is valid
                    if (Utils.validPosition(newX, newY, map)) {
                        // Collision
                        if ((map[newY][newX] != null) && (currentGameObj instanceof Collidable)) {
                            GameObject objToAdd = ((Collidable) currentGameObj).collide(map[newY][newX]);

                            // Add baby person to an adjacent tile
                            if (objToAdd instanceof Person) {
                                Person baby = (Person) objToAdd;

                                int babyX;
                                int babyY;

                                do {
                                    babyX = (int) (Math.random() * map[0].length);
                                    babyY = (int) (Math.random() * map.length);
                                } while ((!Utils.validPosition(babyX, babyY, map)) || (map[babyY][babyX] != null));

                                map[babyY][babyX] = baby;
                            }

                            // Replace person with zombie
                            if (objToAdd instanceof Zombie) {
                                map[newY][newX] = objToAdd;
                            }

                            // Move current object over grass
                            if (map[newY][newX] instanceof Grass) {
//                                map[newY][newX] = currentGameObj;
//                                map[y][x] = null;
                            }

                        }
                        else {
                            map[newY][newX] = currentGameObj;
                            map[y][x] = null;
                        }
                    }

                }

                // Grass
                if (currentGameObj instanceof Grass) {
                    List<Direction> neighbors = ((Grass) currentGameObj).reproduce();

                    for (Direction neighbor : neighbors) {
                        Point point = directionToTile(x, y, neighbor);

                        if ((Utils.validPosition(point, map)) && (map[point.y][point.x] == null)) {
                            map[point.y][point.x] = new Grass();
                        }
                    }
                }
            }
        }
    }

    /**
     * canMove
     * checks if a game object at a specified point has any open adjacent tiles
     * The adjacent tile can also be grass, as game objects can walk over grass
     * */
    private static boolean canMove(int x, int y, GameObject[][] map) {
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
     * @param point the origin point
     * @param direction the direction to traverse
     * */
    private static Point directionToTile(Point point, Direction direction) {
        return directionToTile(point.x, point.y, direction);
    }

    /**
     * directionToTile
     * maps a direction and an origin point to the corresponding neighbor
     * @param x the x coordinate of the origin point
     * @param y the y coordinate of the origin point
     * @param direction the direction to traverse
     * */
    private static Point directionToTile(int x, int y, Direction direction) {
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

    //method to display grid a text for debugging
    public static void DisplayGridOnConsole(String[][] map) {
        for(int i = 0; i<map.length;i++){
            for(int j = 0; j<map[0].length;j++)
                System.out.print(map[i][j]+" ");
            System.out.println("");
        }
    }
}