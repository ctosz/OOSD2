import bagel.*;

/**
 * This class is used to contain the player's score in each level.
 * Though it is the same player throughout the whole game, they are re-rendered for each level and the score resets to 0.
 * The player's score is made up of both a String - 'Score', and the actual integer value.
 */
public class Score  {
    private int score;
    private static final String SCORE_STRING = "SCORE ";
    private static final int SCORE_SIZE = 20;
    private static final int SCORE_X = 25;
    private static final int SCORE_Y = 25;
    private final Font SCORE_FONT = new Font(ShadowPac.FONT_FILE, SCORE_SIZE);

    public Score() { this.score = 0; }

    /**
     * Draws both the string and the integer part of the score to the screen.
     */
    public void drawScore() {

        SCORE_FONT.drawString(SCORE_STRING + score, SCORE_X, SCORE_Y);
    }

    // Getter
    public int getScore() { return this.score;}

    // Setters
    public void setScore(int score) { this.score = score; }
    public void incrementScore(int increment) { this.score += increment; }
}
