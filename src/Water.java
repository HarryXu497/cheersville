import java.awt.*;

public class Water extends GameObject {

    private Image currentSprite;

    public Water() {
        this.currentSprite = SpriteSheet.WATER_SPRITES[(int) (Math.random() * 3)];
    }

    @Override
    public void update() {
        this.setAge(this.getAge() + 1);

        this.currentSprite = SpriteSheet.WATER_SPRITES[(int) (Math.random() * 3)];
    }

    @Override
    public Image draw() {
        return this.currentSprite;
    }
}
