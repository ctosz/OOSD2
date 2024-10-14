import bagel.*;

/**
 * This class is used to represent a specific PlayableCharacter.
 * PacMan has two image options (open & closed) and switches between the two every 15 frames (15 calls of render()).
 * Speed and image from assignment specification.
 */

public class PacMan extends PlayableCharacter {

    private static final int PAC_SPEED = 3;
    private static final int MAX_NUM_LIVES = 3;
    private static final int SWITCH_IMAGE_FRAME_RATE = 15;
    private static final int PAC_FRENZY_SPEED_CHANGE = 1;
    private static final String PAC_PNG = "res/pac.png";
    private static final String PAC_OPEN_PNG = "res/pacOpen.png";

    private int frameCounter;
    private boolean pacIsOpen;


    public PacMan(double initX, double initY) {

        super(initX, initY, MAX_NUM_LIVES);
        this.frameCounter = SWITCH_IMAGE_FRAME_RATE;
        this.pacIsOpen = false;

        setMoveSize(PAC_SPEED);
        setCurrentImage(new Image(PAC_PNG));
    }

    @Override
    public void render(Input input) {

        /* Switch image every 15 frames. Remember, this method executes once per frame!
        Modelled from Project 1 Solution (Tharun Dharmawickrema & Stella Li) */
        frameCounter --;

        renderLives();

        imageSwitchCheck();

        controlArrows(input, PAC_SPEED);
        //printRotation();
        notifyObservers();

        drawImageWithRotation();

        drawScore();

    }

    /**
     * Method to check whether image needs to be switched yet - and if it does, swap is executed.
     */
    private void imageSwitchCheck() {
        if (frameCounter == 0) {
            if (pacIsOpen) {
                // Pac is currently open. Change to closed.
                setCurrentImage(new Image(PAC_PNG));
                pacIsOpen = false;
            }
            else {
                // Pac is currently closed. Change to open.
                setCurrentImage(new Image(PAC_OPEN_PNG));
                pacIsOpen = true;
            }

            frameCounter = SWITCH_IMAGE_FRAME_RATE;
        }
    }

    // Modelled from Project 1 Solution (Tharun Dharmawickrema & Stella Li)
    @Override
    public void resetPosition() {

        this.setPosition(getStartingPosition());
        this.setCurrentImage(new Image(PAC_PNG));
        this.setRotator(ROTATE_DEFAULT);
    }


    @Override
    public void reactGhost(Ghost g) {

        /* When game is in frenzy mode, player-ghost collisions give player +30 points.
            Also, the ghost will not be rendered again until the end of frenzy mode, at which point
            it will reappear at its starting position. */
        if (this.getFrenzyMode()) {

            this.incrementScore(Ghost.GHOST_FRENZY_POINT_VALUE);

            g.setInactive();
            g.setReset();
        }
        else {

            // Ghost position always resets along with player's when they collide.
            this.resetPosition();
            g.resetPosition();
            this.loseLife();
        }
    }


    @Override
    public void reactEdible(EdibleObject edib) {
        this.incrementScore(edib.getPointValue());
    }


    @Override
    public void frenzy() {

        this.startFrenzyMode();
        this.setMoveSize(Math.abs(this.getMoveSize()) + PAC_FRENZY_SPEED_CHANGE);
    }

    @Override
    public void endFrenzy() {

        this.endFrenzyMode();
        this.setMoveSize(Math.abs(this.getMoveSize()) - PAC_FRENZY_SPEED_CHANGE);
    }

    @Override
    public void checkCollision(GameCharacter obj) {

        if (this.getBoundingBox().intersects(obj.getBoundingBox())) {

            this.resetPosition();
        }
    }
}
