/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

import java.util.ArrayList;
import java.util.List;

class Main {


    public static void main(String[] args) {

        List<GameObject> gameObjects = new ArrayList<>();

        gameObjects.add(new Person(2, 2, Sex.MALE, 10, new Rectangle(0, 0, 9, 9)));
        gameObjects.add(new Person(2, 2, Sex.FEMALE, 10, new Rectangle(0, 0, 9, 9)));



        // Initialize Map
//        moveItemsOnGrid(gameObjects);

        // display the fake grid on Console
        //DisplayGridOnConsole(map);

        //Set up Grid Panel
        // DisplayGrid grid = new DisplayGrid(map);
        MatrixDisplayWithMouse grid = new MatrixDisplayWithMouse("title", 10, 10, gameObjects);

        while(true) {
            //Display the grid on a Panel
            grid.refresh();


            //Small delay
            try{ Thread.sleep(1000); }catch(Exception e) {};


            // Initialize Map (Making changes to map)
            moveItemsOnGrid(gameObjects);

            //Display the grid on a Panel
            grid.refresh();
        }
    }


    // Method to simulate grid movement
    public static void moveItemsOnGrid(List<GameObject> gameObjects) {

        GameObject[][] gameObjectMap = new GameObject[10][10];

        for (GameObject obj : gameObjects) {
            // Move
            if (obj instanceof Movable) {
                ((Movable) obj).move();
            }

            // Collisions
            if (gameObjectMap[obj.getY()][obj.getX()] != null) {
                obj.collide(gameObjectMap[obj.getY()][obj.getX()]);
            }

            gameObjectMap[obj.getY()][obj.getX()] = obj;

            // Update
            obj.update();
        }
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