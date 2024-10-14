import bagel.*;
import bagel.util.Point;

/**
 * This class is used to create and display the player's lives.
 * Contains all relevant information to do with their image and positioning.
 */
public class Life {
    private static final int LEFTMOST_LIFE_X = 900;
    private static final int LEFTMOST_LIFE_Y = 10;
    private static final int PIXEL_INCREASE = 30;
    private static final String LIFE_PNG = "res/heart.png";
    private final Point position;
    private boolean isActive;
    private Image image;

    public Life(int initX, int initY) {

        this.position = new Point(initX, initY);
        this.isActive = true;

        setImage(new Image(LIFE_PNG));
    }

    /**
     * A method to set the images and positions of a given number of lives.
     * @param num: the number of lives to instantiate.
     */
    public static Life[] setLives(int num) {

        Life[] lives = new Life[num];
        for (int i = 0; i < num; i++) {
            lives[i] = new Life(LEFTMOST_LIFE_X + (PIXEL_INCREASE*i), LEFTMOST_LIFE_Y);
        }

        return lives;
    }

    /**
     * A method to draw the life image to the screen.
     */
    public void drawImage() {
        image.drawFromTopLeft(position.x, position.y);
    }

    // Getter
    public boolean isEntityActive() { return this.isActive; }

    // Setters
    public void setImage(Image image) { this.image = image; }
    public void setInactive() {
        this.isActive = false;
    }
}
