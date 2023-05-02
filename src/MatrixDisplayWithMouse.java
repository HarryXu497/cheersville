import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/* [MatrixDisplayWithMouse.java]
 * A small program showing how to use the MatrixDisplayWithMouse class
 *  NOTE - A lot of things to fix here!
 * @author Mangat
 */


class MatrixDisplayWithMouse extends JFrame {

    int maxX,maxY, GridToScreenRatio;
    String[][] matrix;

    MatrixDisplayWithMouse(String title, String[][] matrix) {
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

        MatrixPanel() {

            addMouseListener(new MatrixPanelMouseListener());
        }

        public void paintComponent(Graphics g) {
            super.repaint();

            setDoubleBuffered(true);
            g.setColor(Color.BLACK);
            g.drawOval(50, 50, 50, 50);


            for(int i = 0; i<matrix[0].length;i=i+1)  {
                for(int j = 0; j<matrix.length;j=j+1)  {

                    if (matrix[i][j].equals("1"))    //This block can be changed to match character-color pairs
                        g.setColor(Color.RED);
                    else if (matrix[i][j].equals("2"))
                        g.setColor(Color.BLUE);
                    else
                        g.setColor(Color.GREEN);

                    g.fillRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*GridToScreenRatio, i*GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
                }
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