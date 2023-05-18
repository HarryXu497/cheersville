import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * [Main.java]
 * Runs a simulation with swing of the town of Cheersville, with people, zombies, grass, and water
 * Additional features:
 *  - statistic variables which are displayed in the UI
 *      - generation, numPeople, numZombie, numGrass (line 25, 27, 28, 29)
 *  - player controllable game objects
 *      - static selectedObject variable, {@link Person} and {@link Zombie} both implement {@link Controllable}
 *  - JOptionPane to get game board size from the user + retrying with error handling
 *      - line 49 - 75
 * @author Harry Xu
 * @version 1.0 - May 17th 2023
 */
class Main {

    /** A double between 0 and 1 that determines how often new grass is added */
    public static double NEW_GRASS_THRESHOLD = 0.1;

    /** The generation number */
    public static int generation = 0;

    /** Game object counts */
    public static int numPeople = 0;
    public static int numZombie = 0;
    public static int numGrass = 0;

    /** selected controllable entity */
    public static Controllable selectedObject;

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
        Simulation grid = new Simulation("Cheersville", map);

        while(true) {
            //Display the grid on a Panel
            grid.refresh();

            generation++;

            //Small delay
            try{ Thread.sleep(100); }catch(Exception e) {};


            // Initialize Map (Making changes to map)
            nextGeneration(map);

            //Display the grid on a Panel
            grid.refresh();
        }
    }


    /**
     * nextGeneration
     * computes the next generation of the simulation
     * @param map the board of game objects
     */
    public static void nextGeneration(GameObject[][] map) {

        GameObject[][] mapCopy = map.clone();

        for (int i = 0; i < mapCopy.length; i++) {
            mapCopy[i] = map[i].clone();
        }

        // Reset statistic counts
        numPeople = 0;
        numZombie = 0;
        numGrass = 0;

        for (int y = 0; y < mapCopy.length; y++) {
            for (int x = 0; x < mapCopy[y].length; x++) {
                GameObject currentGameObj = mapCopy[y][x];

                // Skip null values
                if (currentGameObj == null) {
                    continue;
                }

                // Remove dead game objects
                if (currentGameObj.getHealth() <= 0) {
                    if (currentGameObj == selectedObject) {
                        selectedObject = null;
                    }
                    map[y][x] = null;
                    continue;
                }

                // Update object
                currentGameObj.update();

                // Object counts
                if (currentGameObj instanceof Person) {
                    numPeople++;
                }
                if (currentGameObj instanceof Zombie) {
                    numZombie++;
                }
                if (currentGameObj instanceof Grass) {
                    numGrass++;
                }

                // Move object if it has open adjacent tiles and implements Movable
                if ((currentGameObj instanceof Movable) && (Utils.canMove(x, y, map))) {
                    Point newLocation;

                    // Moves the selected object according to the player
                    if (currentGameObj == selectedObject) {
                        newLocation = Utils.directionToTile(x, y, ((Controllable) currentGameObj).playerMove());

                        // Reset move direction after each move
                        ((Controllable) currentGameObj).setPlayerMove(null);
                    } else {
                        // If the person is hungry, move them towards the closest grass tile
                        if ((currentGameObj instanceof Person) && (((Person) currentGameObj).isHungry())) {
                            int minDistance = Integer.MAX_VALUE;
                            Point target = null;
                            Person p = ((Person) currentGameObj);

                            // Find the closest grass in vision
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

                                newLocation = Utils.directionToTile(x, y, direction);
                            } else {
                                // Target not found: hungry but no grass -> move randomly
                                newLocation = Utils.generateNextPosition(x, y, (Movable) currentGameObj, map);
                            }
                        } else {
                            // Else use random movement
                            newLocation = Utils.generateNextPosition(x, y, (Movable) currentGameObj, map);
                        }
                    }

                    int newX = newLocation.x;
                    int newY = newLocation.y;

                    // Check if position is valid
                    if (Utils.validPosition(newX, newY, map)) {
                        // Collision occurs
                        if ((map[newY][newX] != null) && (currentGameObj instanceof Collidable)) {
                            GameObject objToAdd = ((Collidable) currentGameObj).collide(map[newY][newX]);

                            // Add baby person to an adjacent tile
                            if (objToAdd instanceof Person) {
                                Person baby = (Person) objToAdd;

                                int babyX;
                                int babyY;

                                if (Utils.canMove(x, y, map)) {
                                    // Generate random valid adjacent tile
                                    do {
                                        Direction direction = ((Movable) baby).move();
                                        newLocation = Utils.directionToTile(x, y, direction);
                                    } while ((!Utils.validPosition(newLocation, map)) ||
                                            ((map[newLocation.y][newLocation.x] != null) &&
                                            (!(map[newLocation.y][newLocation.x] instanceof Grass))));

                                    babyX = newLocation.x;
                                    babyY = newLocation.y;

                                } else {
                                    // Random spawn location as there is no room around the parents
                                    do {
                                        babyX = (int) (Math.random() * map[0].length);
                                        babyY = (int) (Math.random() * map.length);
                                    } while ((!Utils.validPosition(babyX, babyY, map)) ||
                                            ((map[babyY][babyX] != null) &&
                                            (!(map[babyY][babyX] instanceof Grass))));

                                }

                                // Add baby to map
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
                        Point point = Utils.directionToTile(x, y, neighbor);

                        if ((Utils.validPosition(point, map)) && (map[point.y][point.x] == null)) {
                            map[point.y][point.x] = new Grass();
                        }
                    }
                }
            }
        }

        // Add new grass
        boolean addNewGrass = Math.random() < NEW_GRASS_THRESHOLD;

        // Add grass randomly
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
}