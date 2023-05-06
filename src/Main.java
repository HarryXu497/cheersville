/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

class Main {


    public static void main(String[] args) {

        GameObject[][] map = new GameObject[10][10];

        map[3][3] = new Person(Sex.MALE, 18);
        map[4][3] = new Person(Sex.MALE, 18);
        map[3][4] = new Person(Sex.FEMALE, 18);
        map[4][4] = new Person(Sex.FEMALE, 18);
//        map[3][5] = new Zombie(18);

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

                if (currentGameObj instanceof Movable) {
                    Direction direction = ((Movable) currentGameObj).move();

                    int newX = x;
                    int newY = y;

                    // Move the object
                    switch (direction) {
                        case UP:
                            newY--;
                            break;
                        case DOWN:
                            newY++;
                            break;
                        case LEFT:
                            newX--;
                            break;
                        case RIGHT:
                            newX++;
                            break;
                    }

                    // Check if position is valid
                    if (validPosition(newX, newY, map)) {
                        // Already an object there
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
//                                    Direction babyDirection = baby.move();
                                    // Move the object
//                                    switch (babyDirection) {
//                                        case UP:
//                                            babyY--;
//                                            break;
//                                        case DOWN:
//                                            babyY++;
//                                            break;
//                                        case LEFT:
//                                            babyX--;
//                                            break;
//                                        case RIGHT:
//                                            babyX++;
//                                            break;
//                                    }
                                } while ((!validPosition(babyX, babyY, map)) || (map[babyY][babyX] != null));

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
            }
        }
    }

    private static boolean validPosition(int x, int y, GameObject[][] map) {
        // Valid y index
        boolean validY = ((0 <= y) && (y < map.length));

        if (!validY) {
            return false;
        }

        // Valid x index
        return ((0 <= x) && (x < map[y].length));
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