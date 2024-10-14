import bagel.Image;
/**
 * This class is used to represent GameCharacters that cause the player to lose lives - Ghosts.
 * Change state (image and moveSize/speed) with frenzy mode.
 */
public abstract class Ghost extends GameCharacter {

    // This attribute is public as it is used by the PacMan class.
    public static final int GHOST_FRENZY_POINT_VALUE = 30;

    // This attribute is public as it is used by Ghost subclass PinkGhost.
    public static final int NEGATIVE_DIRECTION = -1;
    private static final double GHOST_FRENZY_SPEED_CHANGE = 0.5;
    private static final String FRENZY_GHOST_PNG = "res/ghostFrenzy.png";
    private static final Image FRENZY_IMG = new Image(FRENZY_GHOST_PNG);

    private int currentDirection;
    private Image nonFrenzyImage;

    /* Boolean value that determines whether to reset the ghost's position when frenzy mode ends:
        if player 'eats' (collides with) the ghost in frenzy mode, ghost gets reset to starting position. */
    private boolean reset = false;


    public Ghost(double initX, double initY) { super(initX, initY); }

    @Override
    public void checkCollision(GameCharacter obj) {

        if (this.isEntityActive() && this.getBoundingBox().intersects(obj.getBoundingBox())) {

            // if they overlap - get object to react
            obj.reactGhost(this);
        }
    }


    /**
     * Method for logic when ghost collides with an edible object - no reaction as per specification sheet.
     * @param edib: EdibleObject that collided with the ghost.
     */
    @Override
    public void reactEdible(EdibleObject edib) {}

    /**
     * Method for logic when ghosts collide - no reaction as per specification sheet.
     * @param g: particular ghost that collided with this ghost.
     */
    @Override
    public void reactGhost(Ghost g) {}

    /**
     * Method to swap the ghosts movement between UP/DOWN depending on current direction.
     * @param moveSize: ghosts specific speed.
     */
    private void swapDirectionVertical(double moveSize) {
        if (getCurrentDirection() == DOWN) {
            setCurrentDirection(UP);
            setMoveSize(NEGATIVE_DIRECTION * moveSize);
        }
        else {
            setCurrentDirection(DOWN);
            setMoveSize(moveSize);
        }
    }

    /**
     * Method to swap the ghosts movement between RIGHT/LEFT depending on current direction.
     * @param moveSize: ghosts specific speed.
     */
    private void swapDirectionHorizontal(double moveSize) {
        if (getCurrentDirection() == RIGHT) {
            setCurrentDirection(LEFT);
            setMoveSize(NEGATIVE_DIRECTION * moveSize);
        }
        else {
            setCurrentDirection(RIGHT);
            setMoveSize(moveSize);
        }
    }

    /**
     * Method to swap/reverse the ghost's movement.
     * @param moveSize: ghosts specific speed.
     * @param horizontal: whether the current move is horizontal.
     */
    public void swapDirection(double moveSize, boolean horizontal) {
        if (horizontal) {
            swapDirectionHorizontal(moveSize);
        }
        else {
            swapDirectionVertical(moveSize);
        }
    }


    @Override
    public void frenzy() {

        this.startFrenzyMode();
        // reduce speed
        this.setMoveSize(Math.abs(getMoveSize()) - GHOST_FRENZY_SPEED_CHANGE);
        // swap image
        this.setCurrentImage(FRENZY_IMG);
    }

    @Override
    public void endFrenzy() {
        this.endFrenzyMode();

        this.setMoveSize(Math.abs(getMoveSize()) + GHOST_FRENZY_SPEED_CHANGE);
        this.setCurrentImage(this.nonFrenzyImage);
        this.setActive();

        if (this.getReset()) {
            this.resetPosition();
        }
    }

    // Setters
    public void setCurrentDirection(int direction) { this.currentDirection = direction; }
    public void setReset() { this.reset = true; }
    public void setNonFrenzyImage(Image img) { this.nonFrenzyImage = img; }

    // Getters
    public int getCurrentDirection() { return this.currentDirection; }
    public boolean getReset() { return this.reset; }
}
