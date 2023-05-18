

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * The top-level JFrame for the Cheersville simulation
 * Addition features
 *  - Ability to add different objects upon click and to select and control a zombie or player
 *      - inner class MatrixPanelKeyListener and class ClickActions
 * @author Harry Xu + Mr.Mangat
 * @version 1.0 - May 17th 2023
 * */
class Simulation extends JFrame {

    int maxX,maxY, GridToScreenRatio;
    GameObject[][] matrix;
    Sliders sliders;

    /** The game object type to add upon click */
    public static ClickActions gameObjectToAdd;

    /**
     * constructs the simulation frame and initializes components
     */
    public Simulation(String title, GameObject[][] matrix) {
        super(title);

        this.matrix = matrix;
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (matrix.length+1);  //ratio to fit in screen as square map

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Simulation panel
        MatrixPanel simulation = new MatrixPanel();

        // Sliders and stats panel
        this.sliders = new Sliders();

        // JSplitPane to separate teh two
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, simulation, this.sliders);
        splitPane.setDividerLocation(GridToScreenRatio * matrix[0].length);

        splitPane.setOneTouchExpandable(true);

        simulation.setMinimumSize(new Dimension(GridToScreenRatio * matrix[0].length, this.getHeight()));

        // Add to JFrame
        this.add(splitPane);

        this.setVisible(true);

        // Keyboard listener
        addKeyListener(new MatrixPanelKeyListener());
    }

    /**
     * refresh
     * Refreshes and repaints the data of this frame and its children
     */
    public void refresh() {
        this.sliders.refresh();
        this.repaint();
    }

    /**
     * the grid and simulation in the user interface
     * @author Harry Xu
     * @version 1.0 - May 17th 2023
     * */
    class MatrixPanel extends JPanel {

        Image[][] dirtSprites;

        /**
         * constructs the matrix panel and initializes components
         */
        public MatrixPanel() {
            // Scale the images
            SpriteSheet.scale(GridToScreenRatio);

            // Static dirt sprites for each tile in the matrix
            this.dirtSprites = new Image[matrix.length][matrix[0].length];

            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    this.dirtSprites[y][x] = SpriteSheet.DIRT_SPRITES[(int) (Math.random() * 3)];
                }
            }

            addMouseListener(new MatrixPanelMouseListener());
        }

        /**
         * draws the images on the simulation panel
         * @param g the graphic context to draw on
         */
        public void paintComponent(Graphics g) {
            super.repaint();

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);


            for(int i = 0; i<matrix[0].length;i=i+1)  {
                for(int j = 0; j<matrix.length;j=j+1)  {
                    GameObject currentObject = matrix[i][j];

                    // Draw dirt
                    g.drawImage(
                        dirtSprites[i][j],
                        j*GridToScreenRatio, i*GridToScreenRatio, null
                    );

                    // Draw a red tile around the selected object
                    if ((Main.selectedObject != null) && (currentObject == Main.selectedObject)) {
                        g.setColor(Color.RED);
                        g.fillRect(j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    }

                    // Draw game object
                    if (currentObject != null) {
                        g.drawImage(
                            currentObject.draw(),
                            j*GridToScreenRatio, i*GridToScreenRatio, null
                        );
                    }
                }
            }

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, matrix.length * GridToScreenRatio, matrix[0].length * GridToScreenRatio);
        }
    }


    /**
     * the mouse listener for the simulation
     * @author Harry Xu
     * @version 1.0 - May 17th 2023
     */
    class MatrixPanelMouseListener implements MouseListener{

        /**
         * mousePressed
         * invoked when a mouse button is pressed
         * @param e the mouse event object
         */
        public void mousePressed(MouseEvent e) {
            requestFocus();

            Point clickedPoint = e.getPoint();

            // calculate corresponding tile
            int x = clickedPoint.x / GridToScreenRatio;
            int y = clickedPoint.y / GridToScreenRatio;

            // Add selected item if the spot is available
            if (Utils.validPosition(x, y, matrix) && (!(matrix[y][x] instanceof Water))) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Add selected entity
                    if (gameObjectToAdd != ClickActions.SELECT) {
                        matrix[y][x] = ClickActions.toGameObject(gameObjectToAdd);
                    }

                    // Select object
                    if ((gameObjectToAdd == ClickActions.SELECT) && (matrix[y][x] instanceof Controllable)) {
                        Main.selectedObject = (Controllable) matrix[y][x];
                    }
                }

            }
        }

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mouseClicked(MouseEvent e) {}

    }

    /**
     * the keyboard listener for the simulation
     * @author Harry Xu
     * @version 1.0 - May 17th 2023
     */
    static class MatrixPanelKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        /**
         * keyPressed
         * map each key to a direction
         * @param e the key event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            // Return if no selected object
            if (Main.selectedObject == null) {
                return;
            }

            // Map keys to directions
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    Main.selectedObject.setPlayerMove(Direction.UP);
                    break;

                case KeyEvent.VK_DOWN:
                    Main.selectedObject.setPlayerMove(Direction.DOWN);
                    break;

                case KeyEvent.VK_LEFT:
                    Main.selectedObject.setPlayerMove(Direction.LEFT);
                    break;

                case KeyEvent.VK_RIGHT:
                    Main.selectedObject.setPlayerMove(Direction.RIGHT);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}