import java.awt.image.BufferedImage;

public class SpriteSheet {
    /**
     * toSpriteMatrix
     * takes a spritesheet and dimensions and converts it into a 2D array of images
     * @param rows the amount of rows in the spritesheet
     * @param columns the amount of columns in the spritesheet
     * @param spritesheet the loaded image spritesheet
     * @return a 2D array of individual sprites;
     * */
    public static BufferedImage[][] toSpriteMatrix(int rows, int columns, BufferedImage spritesheet) {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows " + rows + " must be positive.");
        }

        if (columns <= 0) {
            throw new IllegalArgumentException("columns " + columns + " must be positive.");
        }

        if (spritesheet == null) {
            throw new NullPointerException("spritesheet cannot be null");
        }


        int tileWidth = spritesheet.getWidth() / columns;
        int tileHeight = spritesheet.getHeight() / rows;

        BufferedImage[][] sprites = new BufferedImage[rows][columns];

        for (int x = 0; x < columns; x++) {
            for (int y = 0 ; y < rows; y++) {
                sprites[y][x] = spritesheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }

        return sprites;
    }
}
