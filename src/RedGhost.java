import bagel.Image;
import bagel.Input;

/**
 * This class is used to represent a Ghost with specific movement, image, and speed.
 * Red ghosts either move or don't, depending on the level.
 * If they can move, they can only move horizontally, initially to the right.
 * Speed and image from assignment specification.
 */

public class RedGhost extends Ghost {
    private final boolean MOVABLE;
    private static final double SPEED = 1;
    private static final String RED_GHOST_PNG = "res/ghostRed.png";

    public RedGhost(double initX, double initY, boolean movable) {

        super(initX, initY);

        Image redGhostImg = new Image(RED_GHOST_PNG);
        setCurrentImage(redGhostImg);
        setNonFrenzyImage(redGhostImg);

        this.MOVABLE = movable;

        setCurrentDirection(RIGHT);

        setMoveSize(SPEED);
    }

    @Override
    public void render(Input input) {
        if (this.isEntityActive()) {
            if (MOVABLE) {
                move(getMoveSize(), 0);
            }

            notifyObservers();

            drawCurrentImage();
        }
    }

    @Override
    public void reactWall() {

        moveBack();

        // Red Ghosts always move horizontally.
        boolean horizontal = true;
        swapDirection(getMoveSize(), horizontal);
    }
}
