import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/* [MatrixDisplayWithMouse.java]
 * A small program showing how to use the MatrixDisplayWithMouse class
 *  NOTE - A lot of things to fix here!
 * @author Mangat
 */


class MatrixDisplayWithMouse extends JFrame {

    int maxX,maxY, GridToScreenRatio;
    GameObject[][] matrix;

    MatrixDisplayWithMouse(String title, GameObject[][] matrix) {
        super(title);

        this.matrix = matrix;
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (matrix.length+1);  //ratio to fit in screen as square map

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.add(new MatrixPanel());

        this.setVisible(true);
    }

    public void refresh() {
        this.repaint();
    }

    //Inner Class
    class MatrixPanel extends JPanel {

        Image[][] dirtSprites;

        MatrixPanel() {
            this.dirtSprites = new Image[matrix.length][matrix[0].length];

            for (int y = 0; y < matrix.length; y++) {
                for (int x = 0; x < matrix[y].length; x++) {
                    this.dirtSprites[y][x] = SpriteSheet.DIRT_SPRITES[(int) (Math.random() * 3)];
                }
            }

            addMouseListener(new MatrixPanelMouseListener());
        }

        public void paintComponent(Graphics g) {
            super.repaint();

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);


            for(int i = 0; i<matrix[0].length;i=i+1)  {
                for(int j = 0; j<matrix.length;j=j+1)  {
                    GameObject currentObject = matrix[i][j];

                    g.drawImage(
                        dirtSprites[i][j].getScaledInstance(GridToScreenRatio, GridToScreenRatio, Image.SCALE_DEFAULT),
                        j*GridToScreenRatio, i*GridToScreenRatio, null
                    );

                    if (currentObject != null) {
                        g.drawImage(
                            currentObject.draw().getScaledInstance(GridToScreenRatio, GridToScreenRatio, Image.SCALE_DEFAULT),
                            j*GridToScreenRatio, i*GridToScreenRatio, null
                        );
                    }
                }
            }

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, matrix.length * GridToScreenRatio, matrix[0].length * GridToScreenRatio);
        }
    }


    //Mouse Listener
    class MatrixPanelMouseListener implements MouseListener{
        //Mouse Listener Stuff
        public void mousePressed(MouseEvent e) {
            Point clickedPoint = e.getPoint();

            int x = clickedPoint.x / GridToScreenRatio;
            int y = clickedPoint.y / GridToScreenRatio;

            if (Utils.validPosition(x, y, matrix)) {
                matrix[y][x] = new Zombie();
            }
        }

        public void mouseReleased(MouseEvent e) {
            System.out.println("Mouse released; # of clicks: " + e.getClickCount());
        }

        public void mouseEntered(MouseEvent e) {
            System.out.println("Mouse entered");
        }

        public void mouseExited(MouseEvent e) {
            System.out.println("Mouse exited");
        }

        public void mouseClicked(MouseEvent e) {
            System.out.println("Mouse clicked (# of clicks: "+ e.getClickCount() + ")");
        }

    }

}