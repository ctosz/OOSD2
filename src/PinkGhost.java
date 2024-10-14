import bagel.Image;
import bagel.Input;
import java.util.Random;

/**
 * This class is used to represent a Ghost with specific movement, image, and speed.
 * Pink ghosts can move either horizontally or vertically - their first direction is chosen randomly at instantiation,
 *      and then each time they collide with a wall their direction is again reassigned randomly.
 * Speed and image from assignment specification.
 */
public class PinkGhost extends Ghost {
    private static final double SPEED = 3;
    private static final String PINK_GHOST_PNG = "res/ghostPink.png";
    private boolean horizontal;

    public PinkGhost(double initX, double initY, int initDirection) {

        super(initX, initY);

        Image pinkGhostImg = new Image(PINK_GHOST_PNG);
        setCurrentImage(pinkGhostImg);
        setNonFrenzyImage(pinkGhostImg);

        setMoveSize(SPEED);

        setCurrentDirection(initDirection);

        horizontal = initDirection == RIGHT || initDirection == LEFT;
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

        int newDir = new Random().nextInt(4);
        setCurrentDirection(newDir);

        if (newDir == RIGHT) {
            this.horizontal = true;
            setMoveSize(getMoveSize());
        }
        else if (newDir == LEFT) {
            this.horizontal = true;
            setMoveSize(Ghost.NEGATIVE_DIRECTION * getMoveSize());
        }
        else if (newDir == UP) {
            this.horizontal = false;
            setMoveSize(Ghost.NEGATIVE_DIRECTION * getMoveSize());
        }
        else {
            this.horizontal = false;
            setMoveSize(getMoveSize());
        }
    }
}
