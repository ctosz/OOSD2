import bagel.Image;
import bagel.Input;

/**
 * This class is used to represent a Ghost with specific movement, image, and speed.
 * Green ghosts can move either horizontally or vertically - this is randomly chosen at instantiation.
 * Speed and image from assignment specification.
 */

public class GreenGhost extends Ghost {
    private static final double SPEED = 4;
    private static final String GREEN_GHOST_PNG = "res/ghostGreen.png";
    private final boolean horizontal;

    public GreenGhost(double initX, double initY, boolean horizontal) {

        super(initX, initY);

        Image greenGhostImg = new Image(GREEN_GHOST_PNG);
        setCurrentImage(greenGhostImg);
        setNonFrenzyImage(greenGhostImg);

        this.horizontal = horizontal;

        // If the green ghost moves horizontally:
        if (this.horizontal) {
            setCurrentDirection(RIGHT);
        }
        else {
            // Chosen movement is vertical.
            setCurrentDirection(DOWN);
        }

        setMoveSize(SPEED);
    }

    @Override
    public void render(Input input) {
        if (this.isEntityActive()) {
            if (horizontal) {
                move(getMoveSize(), 0);
            }
            else {
                move(0, getMoveSize());
            }

            notifyObservers();

            drawCurrentImage();
        }
    }

    @Override
    public void reactWall() {

        moveBack();

        swapDirection(getMoveSize(), this.horizontal);
    }
}
