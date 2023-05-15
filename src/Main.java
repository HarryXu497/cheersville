/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

class Main {

    /** A double between 0 and 1 that determines how often new grass is added */
    public static double NEW_GRASS_THRESHOLD = 0.1;

    /** The generation number */
    public static int GENERATION = 0;

    /** Game object counts */
    public static int NUM_PEOPLE = 0;
    public static int NUM_ZOMBIE = 0;
    public static int NUM_GRASS = 0;

    public static void main(String[] args) {
        try {
            SpriteSheet.loadZombieSprites();
            SpriteSheet.loadPerson1Sprites();
            SpriteSheet.loadPerson2Sprites();
            SpriteSheet.loadGrassSprites();
            SpriteSheet.loadDirtSprites();
            SpriteSheet.loadWaterSprites();
        } catch (IOException e) {
            System.out.println("Image files not found");
            e.printStackTrace();
            return;
        }

        boolean invalid = true;
        int dimensions = 0;

        while (invalid) {
            String input = JOptionPane.showInputDialog("What are the dimensions of the board", 25);

            // Question card cancelled
            if (input == null) {
                return;
            }

            // Get dimensions
            try {
                dimensions = Integer.parseInt(input);

                if (dimensions <= 0) {
                    JOptionPane.showMessageDialog(null, "Size must be positive");
                    continue;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input");
                continue;
            }

            invalid = false;
        }

        GameObject[][] map = new GameObject[dimensions][dimensions];

        //Set up Grid Panel
        // DisplayGrid grid = new DisplayGrid(map);
        MatrixDisplayWithMouse grid = new MatrixDisplayWithMouse("Cheersville", map);

        while(true) {
            //Display the grid on a Panel
            grid.refresh();

            GENERATION++;

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

        NUM_PEOPLE = 0;
        NUM_ZOMBIE = 0;
        NUM_GRASS = 0;

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

                // Object counts
                if (currentGameObj instanceof Person) {
                    NUM_PEOPLE++;
                }
                if (currentGameObj instanceof Zombie) {
                    NUM_ZOMBIE++;
                }
                if (currentGameObj instanceof Grass) {
                    NUM_GRASS++;
                }

                // Move object if it has open adjacent tiles and implements Movable
                if ((currentGameObj instanceof Movable) && (canMove(x, y, map))) {
                    Point newLocation;

                    // If the person is hungry, move them towards the closest grass tile
                    if ((currentGameObj instanceof Person) && (((Person) currentGameObj).isHungry())) {
                        int minDistance = Integer.MAX_VALUE;
                        Point target = null;
                        Person p = ((Person) currentGameObj);

                        for (int grassY = y - p.getVision(); grassY <= y + p.getVision(); grassY++) {
                            for (int grassX = x - p.getVision(); grassX <= y + p.getVision(); grassX++) {
                                if ((Utils.validPosition(grassX, grassY, map)) && (map[grassY][grassX] instanceof Grass)) {
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
                            Direction direction = ((Person) currentGameObj).move(new Point(x, y), target);

                            newLocation = directionToTile(x, y, direction);
                        } else {
                            // If hungry but no grass, move randomly
                            newLocation = generateNextPosition(x, y, (Movable) currentGameObj, map);
                        }
                    } else {
                        // Else use random movement
                        newLocation = generateNextPosition(x, y, (Movable) currentGameObj, map);
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

                                if (canMove(x, y, map)) {
                                    // Random adjacent tile
                                    do {
                                        Direction direction = ((Movable) baby).move();
                                        newLocation = directionToTile(x, y, direction);
                                    } while (!Utils.validPosition(newLocation, map) || ((map[newLocation.y][newLocation.x] != null) && (!(map[newLocation.y][newLocation.x] instanceof Grass))));

                                    babyX = newLocation.x;
                                    babyY = newLocation.y;

                                } else {
                                    // Random spawn location
                                    do {
                                        babyX = (int) (Math.random() * map[0].length);
                                        babyY = (int) (Math.random() * map.length);
                                    } while ((!Utils.validPosition(babyX, babyY, map)) || ((map[babyY][babyX] != null) && (!(map[babyY][babyX] instanceof Grass))));

                                }
                                map[babyY][babyX] = baby;
                            }

                            // Replace person with zombie
                            if (objToAdd instanceof Zombie) {
                                map[newY][newX] = objToAdd;
                            }
                        } else {
                            map[newY][newX] = currentGameObj;
                            map[y][x] = null;
                        }
                    }

                }

                // Grass Spawning
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

        // Add new grass
        boolean addNewGrass = Math.random() < NEW_GRASS_THRESHOLD;

        if (addNewGrass) {
            int numGrass = (int) (Math.random() * 3);

            for (int i = 0; i < numGrass; i++) {
                int y = (int) (Math.random() * map.length);
                int x = (int) (Math.random() * map[y].length);

                if (map[y][x] == null) {
                    map[y][x] = new Grass();
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
     * generateNextPosition
     * generates a valid new position point in the map using a game object
     * @param x the x coordinate of the game object
     * @param y the y coordinate of the game object
     * @param currentGameObj the game object to generate the directions from
     * @param map the game map
     * */
    private static Point generateNextPosition(int x, int y, Movable currentGameObj, GameObject[][] map) {
        Point newLocation;

        do {
            Direction direction = currentGameObj.move();
            newLocation = directionToTile(x, y, direction);
        } while ((!Utils.validPosition(newLocation, map)));

        return newLocation;
    }

    /**
     * directionToTile
     * maps a direction and an origin point to the corresponding neighbor
     * @param x the x coordinate of the origin point
     * @param y the y coordinate of the origin point
     * @param direction the direction to traverse
     * @return the new generated point
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
}