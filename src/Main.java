/* [GridTest.java]
 * A program to demonstrate usage of DisplayGrid.java.
 * @author Mangat
 */

class Main {


    public static void main(String[] args) {

        String[][] map = new String[10][10];

        // Initialize Map
        moveItemsOnGrid(map);

        // display the fake grid on Console
        //DisplayGridOnConsole(map);

        //Set up Grid Panel
        // DisplayGrid grid = new DisplayGrid(map);
        MatrixDisplayWithMouse grid = new MatrixDisplayWithMouse("title", map);

        while(true) {
            //Display the grid on a Panel
            grid.refresh();


            //Small delay
            try{ Thread.sleep(1000); }catch(Exception e) {};


            // Initialize Map (Making changes to map)
            moveItemsOnGrid(map);

            //Display the grid on a Panel
            grid.refresh();
        }
    }


    // Method to simulate grid movement
    public static void moveItemsOnGrid(String[][] map) {

        for(int i = 0; i<map[0].length;i++)
            for(int j = 0; j<map.length;j++)
            {

                map[i][j]=((int)(Math.random()* 3))+"";
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