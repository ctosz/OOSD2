import bagel.Image;
import bagel.Input;

/**
 * This class is used to represent a Ghost with specific movement, image, and speed.
 * Blue ghosts always move vertically, initially moving down.
 * Speed and image from assignment specification.
 */
public class BlueGhost extends Ghost {
    private static final String BLUE_GHOST_PNG = "res/ghostBlue.png";
    private static final double SPEED = 2;

    public BlueGhost(double initX, double initY) {

        super(initX, initY);

        Image blueGhostImg = new Image(BLUE_GHOST_PNG);
        setCurrentImage(blueGhostImg);
        setNonFrenzyImage(blueGhostImg);

        setCurrentDirection(DOWN);
        setMoveSize(SPEED);
    }


    @Override
    public void render(Input input) {
        if (this.isEntityActive()) {
            move(0, getMoveSize());

            notifyObservers();

            drawCurrentImage();
        }
    }

    @Override
    public void reactWall() {

        moveBack();

        // Blue ghosts always move vertically.
        boolean horizontal = false;
        swapDirection(getMoveSize(), horizontal);
    }


}

