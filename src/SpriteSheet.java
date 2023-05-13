import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to load images and sprites
 * @author Harry Xu
 * @version 1.0 - May 8th 2023
 */
public class SpriteSheet {
    /** Zombie Sprites */
    public static final String ZOMBIE_FILE_PATH = "images/zombie/walk.png";
    public static List<Image> ZOMBIE_UP_SPRITES;
    public static List<Image> ZOMBIE_DOWN_SPRITES;
    public static List<Image> ZOMBIE_LEFT_SPRITES;
    public static List<Image> ZOMBIE_RIGHT_SPRITES;

    /** Person Sprites */
    public static final String PERSON_1_FILE_PATH = "images/people/walk1.png";
    public static List<Image> PERSON_1_UP_SPRITES;
    public static List<Image> PERSON_1_DOWN_SPRITES;
    public static List<Image> PERSON_1_LEFT_SPRITES;
    public static List<Image> PERSON_1_RIGHT_SPRITES;

    public static final String PERSON_2_FILE_PATH = "images/people/walk2.png";
    public static List<Image> PERSON_2_UP_SPRITES;
    public static List<Image> PERSON_2_DOWN_SPRITES;
    public static List<Image> PERSON_2_LEFT_SPRITES;
    public static List<Image> PERSON_2_RIGHT_SPRITES;

    /** Grass Sprites */
    public static final String GRASS_FILE_PATH = "images/grass/grass.png";
    public static Image[][] GRASS_SPRITES;

    /** Dirt Sprites */
    public static final String DIRT_FILE_PATH = "images/dirt/dirt.png";
    public static Image[] DIRT_SPRITES;

    /** Water Sprites */
    public static final String WATER_FILE_PATH = "images/water/water.png";
    public static Image[] WATER_SPRITES;

    /**
     * toSpriteMatrix
     * takes a spritesheet and dimensions and converts it into a 2D array of images
     * @param rows the amount of rows in the spritesheet
     * @param columns the amount of columns in the spritesheet
     * @param spritesheet the loaded image spritesheet
     * @return a 2D array of individual sprites;
     * */
    public static Image[][] toSpriteMatrix(int rows, int columns, BufferedImage spritesheet) {
        // Valid arguments
        if (rows <= 0) {
            throw new IllegalArgumentException("rows " + rows + " must be positive.");
        }

        if (columns <= 0) {
            throw new IllegalArgumentException("columns " + columns + " must be positive.");
        }

        if (spritesheet == null) {
            throw new NullPointerException("spritesheet cannot be null");
        }

        // Calculate tile dimensions
        int tileWidth = spritesheet.getWidth() / columns;
        int tileHeight = spritesheet.getHeight() / rows;

        BufferedImage[][] sprites = new BufferedImage[rows][columns];

        // Read images
        for (int x = 0; x < columns; x++) {
            for (int y = 0 ; y < rows; y++) {
                sprites[y][x] = spritesheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
            }
        }

        return sprites;
    }


    /**
     * loadZombieSprites
     * loads the zombie spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadZombieSprites() throws IOException {
        File file = new File(ZOMBIE_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        Image[][] spriteMatrix = toSpriteMatrix(4, 4, spritesheet);

        ZOMBIE_DOWN_SPRITES = new ArrayList<>();
        ZOMBIE_UP_SPRITES = new ArrayList<>();
        ZOMBIE_RIGHT_SPRITES = new ArrayList<>();
        ZOMBIE_LEFT_SPRITES = new ArrayList<>();

        for (Image[] row : spriteMatrix) {
            ZOMBIE_DOWN_SPRITES.add(row[0]);
            ZOMBIE_UP_SPRITES.add(row[1]);
            ZOMBIE_RIGHT_SPRITES.add(row[2]);
            ZOMBIE_LEFT_SPRITES.add(row[3]);
        }
    }

    /**
     * loadPerson1Sprites
     * loads the person 1 spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadPerson1Sprites() throws IOException {
        File file = new File(PERSON_1_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        Image[][] spriteMatrix = toSpriteMatrix(4, 4, spritesheet);

        PERSON_1_DOWN_SPRITES = new ArrayList<>();
        PERSON_1_UP_SPRITES = new ArrayList<>();
        PERSON_1_RIGHT_SPRITES = new ArrayList<>();
        PERSON_1_LEFT_SPRITES = new ArrayList<>();

        for (Image[] row : spriteMatrix) {
            PERSON_1_DOWN_SPRITES.add(row[0]);
            PERSON_1_UP_SPRITES.add(row[1]);
            PERSON_1_RIGHT_SPRITES.add(row[2]);
            PERSON_1_LEFT_SPRITES.add(row[3]);
        }
    }
    /**
     * loadPerson2Sprites
     * loads the person 2 spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadPerson2Sprites() throws IOException {
        File file = new File(PERSON_2_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        Image[][] spriteMatrix = toSpriteMatrix(4, 4, spritesheet);

        PERSON_2_DOWN_SPRITES = new ArrayList<>();
        PERSON_2_UP_SPRITES = new ArrayList<>();
        PERSON_2_RIGHT_SPRITES = new ArrayList<>();
        PERSON_2_LEFT_SPRITES = new ArrayList<>();

        for (Image[] row : spriteMatrix) {
            PERSON_2_DOWN_SPRITES.add(row[0]);
            PERSON_2_UP_SPRITES.add(row[1]);
            PERSON_2_RIGHT_SPRITES.add(row[2]);
            PERSON_2_LEFT_SPRITES.add(row[3]);
        }
    }


    /**
     * loadGrassSprites
     * loads the grass spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadGrassSprites() throws IOException {
        File file = new File(GRASS_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        GRASS_SPRITES = toSpriteMatrix(3, 3, spritesheet);
    }

    /**
     * loadDirtSprites
     * loads the dirt spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadDirtSprites() throws IOException {
        DIRT_SPRITES = new Image[3];

        File file = new File(DIRT_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        Image[][] sprites = toSpriteMatrix(3, 1, spritesheet);

        for (int i = 0; i < sprites.length; i++) {
            DIRT_SPRITES[i] = sprites[i][0];
        }
    }

    /**
     * loadWaterSprites
     * loads the water spritesheet into a static variable
     * @throws IOException if an error occurs during reading the file
     */
    public static void loadWaterSprites() throws IOException {
        File file = new File(WATER_FILE_PATH);
        BufferedImage spritesheet = ImageIO.read(file);

        WATER_SPRITES = toSpriteMatrix(1, 3, spritesheet)[0];
    }

    /**
     * scale
     * scales all images in this class to a specific size (size x size)
     * @param size the size of the new image
     * @throws IllegalArgumentException if the size is not positive
     */
    public static void scale(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size " + size + " must be positive");
        }

        // Zombie
        scaleList(size, ZOMBIE_LEFT_SPRITES);
        scaleList(size, ZOMBIE_RIGHT_SPRITES);
        scaleList(size, ZOMBIE_DOWN_SPRITES);
        scaleList(size, ZOMBIE_UP_SPRITES);

        // Person 1
        scaleList(size, PERSON_1_LEFT_SPRITES);
        scaleList(size, PERSON_1_RIGHT_SPRITES);
        scaleList(size, PERSON_1_DOWN_SPRITES);
        scaleList(size, PERSON_1_UP_SPRITES);

        // Person 2
        scaleList(size, PERSON_2_LEFT_SPRITES);
        scaleList(size, PERSON_2_RIGHT_SPRITES);
        scaleList(size, PERSON_2_DOWN_SPRITES);
        scaleList(size, PERSON_2_UP_SPRITES);

        // Make new array cause error otherwise
        Image[][] grassImages = new Image[GRASS_SPRITES.length][GRASS_SPRITES[0].length];

        // Grass
        for (int i = 0; i < GRASS_SPRITES.length; i++) {
            for (int j = 0; j < GRASS_SPRITES[i].length; j++) {
                grassImages[i][j] = GRASS_SPRITES[i][j].getScaledInstance(size, size, Image.SCALE_DEFAULT);
            }
        }

        GRASS_SPRITES = grassImages;

        // Dirt
        for (int i = 0; i < DIRT_SPRITES.length; i++) {
            DIRT_SPRITES[i] = DIRT_SPRITES[i].getScaledInstance(size, size, Image.SCALE_DEFAULT);
        }
    }

    /**
     * scaleList
     * scales all images in a list to a specific size (size x size)
     * @param size the size of the new image
     * @param images the images to resize
     * @throws IllegalArgumentException if the size is not positive
     */
    private static void scaleList(int size, List<Image> images) {
        if (size <= 0) {
            throw new IllegalArgumentException("size " + size + " must be positive");
        }

        for (int i = 0; i < images.size(); i++) {
            images.set(i, images.get(i).getScaledInstance(size, size, Image.SCALE_DEFAULT));
        }
    }
}
