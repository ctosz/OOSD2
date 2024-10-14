import bagel.*;

/**
 * This class is used to represent any GameCharacter that can be controlled by the player i.e., input.
 * PlayableCharacters have an image that rotates depending on their direction of movement.
 * Also have a Score and a certain number of lives.
 */
public abstract class PlayableCharacter extends GameCharacter {

    // This attribute is public as it is used by the subclass PacMan.
    public static final double ROTATE_DEFAULT = 0;
    private static final double ROTATE_UP = -Math.PI/2;
    private static final double ROTATE_DOWN = Math.PI/2;
    private static final double ROTATE_LEFT = Math.PI;

    private final DrawOptions rotator = new DrawOptions();
    private final Score score;
    private final Life[] lives;
    private int numLives;

    public PlayableCharacter(double initX, double initY, int maxLives) {

        super(initX, initY);
        this.score = new Score();
        this.numLives = maxLives;
        lives = Life.setLives(maxLives);
    }

    /**
     * Method to rotate the player's image based on the direction of travel i.e., input key (arrow keys specifically).
     * @param keyPressed: the player's keyboard input.
     */
    public void rotate(Keys keyPressed) {
        if (keyPressed == Keys.UP) {
            setRotator(ROTATE_UP);
        }
        else if (keyPressed == Keys.DOWN) {
            setRotator(ROTATE_DOWN);
        }
        else if (keyPressed == Keys.LEFT) {
            setRotator(ROTATE_LEFT);
        }
        else if (keyPressed == Keys.RIGHT) {
            setRotator(ROTATE_DEFAULT);
        }
    }

    /**
     * Method to control the movement of the player using the arrow keys.
     * From Project 1 Solution: Tharun Dharmawickrema & Stella Li.
     * @param input: key pressed by player.
     * @param moveSize: specific to the character (i.e., speed).
     */
    public void controlArrows(Input input, double moveSize) {

        if (input.isDown(Keys.UP)) {
            move(0, -moveSize);
            rotate(Keys.UP);
        }
        else if (input.isDown(Keys.DOWN)) {
            move(0, moveSize);
            rotate(Keys.DOWN);
        }
        else if (input.isDown(Keys.LEFT)) {
            move(-moveSize, 0);
            rotate(Keys.LEFT);
        }
        else if (input.isDown(Keys.RIGHT)) {
            move(moveSize, 0);
            rotate(Keys.RIGHT);
        }
    }

    /**
     * Method to draw the player's current image to the screen, including its rotation.
     */
    public void drawImageWithRotation() {
        getCurrentImage().drawFromTopLeft(getX(), getY(), this.rotator);
    }

    /**
     * Methods to do with the player's score:
     * 1. Draw it to the screen.
     * 2. Increment based on an input parameter value.
     */
    public void drawScore() {
        this.score.drawScore();
    }
    public void incrementScore(int increment) {
        this.score.incrementScore(increment);
    }

    /**
     * Method to render the correct amount of lives for the player, i.e., taking into account whether any have been lost.
     */
    public void renderLives() {
        for (Life l : lives) {
            if (l.isEntityActive()) {
                l.drawImage();
            }
        }
    }

    /**
     * Method to remove a life from the player (when colliding with a ghost in this implementation).
     * Set the right-most life image to inactive.
     */
    public void loseLife() {

        lives[numLives - 1].setInactive();
        numLives --;
    }


    // Setters
    public void setRotator(double rotation) { this.rotator.setRotation(rotation); }
    public void setScore(int score) { this.score.setScore(score); }

    // Getters
    public int getNumLives() { return this.numLives; }
    public int getScore() { return this.score.getScore(); }
}
