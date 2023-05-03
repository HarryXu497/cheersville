public class Rectangle {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    /**
     * constructs a rectangle with 2 opposite points - the top left and bottom right points
     * @param x1 the x value of the first coordinate
     * @param y1 the y value of the first coordinate
     * @param x2 the x value of the second coordinate
     * @param y2 the y value of the second coordinate
     * */
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * contains
     * checks if a point is within the bounds of a rectangle, including the edges
     * @param x the x value of the coordinate.
     * @param y the y value of the coordinate.
     * @return whether the point is contained in this rectangle
     * */
    public boolean contains(int x, int y) {
        boolean containsX = (this.x1 <= x) && (x <= this.x2);
        boolean containsY = (this.y1 <= y) && (y <= this.y2);

        return containsX && containsY;
    }
}
