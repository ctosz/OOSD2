import bagel.*;

/**
 * GameMessage is a generalised outline of any messages to be rendered in the game.
 * Contains the contents of the message, its font, and the position it should be drawn to.
 */
public abstract class GameMessage {

    // Protected variable as it is used by child classes.
    protected static final int DEFAULT_FONT_SIZE = 64;
    private Font messageFont;
    private String message;
    private int fontSize;
    private double xPos;
    private double yPos;

    public GameMessage() {}

    /**
     * Method that draws (renders) the GameMessage to the screen.
     */
    public void drawMessage() {
        messageFont.drawString(this.message, xPos, yPos);
    }

    // Getters
    public Font getMessageFont() { return this.messageFont; }

    // Setters
    public void setMessage(String message) { this.message = message; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    public void setxPos(double xPos) { this.xPos = xPos; }
    public void setyPos(double yPos) { this.yPos = yPos; }

    // Set using font file specific to ShadowPac game.
    public void setMessageFont() { messageFont = new Font(ShadowPac.FONT_FILE, this.fontSize); }
}
