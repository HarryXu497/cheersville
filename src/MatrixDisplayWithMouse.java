import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.List;

/* [MatrixDisplayWithMouse.java]
 * A small program showing how to use the MatrixDisplayWithMouse class
 *  NOTE - A lot of things to fix here!
 * @author Mangat
 */


class MatrixDisplayWithMouse extends JFrame {

    int maxX, maxY, GridToScreenRatio;
    private List<GameObject> gameObjects;
    int length;
    int height;


    MatrixDisplayWithMouse(String title, int length, int height, List<GameObject> gameObjects) {
        super(title);

        this.gameObjects = gameObjects;
        this.length = length;
        this.height = height;
        maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
        GridToScreenRatio = maxY / (this.height+1);  //ratio to fit in screen as square map

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

        MatrixPanel() {

            addMouseListener(new MatrixPanelMouseListener());
        }

        public void paintComponent(Graphics g) {
            super.repaint();

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);
            g.drawOval(50, 50, 50, 50);


            for(int i = 0; i<length;i=i+1)  {
                for(int j = 0; j<height;j=j+1)  {
                    g.setColor(Color.WHITE);
                    g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                }
            }

            for (GameObject obj : gameObjects) {
                // Draw object
                g.setColor(obj.draw());
                g.fillRect(obj.getY()*GridToScreenRatio, obj.getX()*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
            }

        }
    }


    //Mouse Listener
    static class MatrixPanelMouseListener implements MouseListener{
        //Mouse Listener Stuff
        public void mousePressed(MouseEvent e) {
            System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
            System.out.println("x: " + e.getPoint().x + ",y: " + e.getPoint().y);
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