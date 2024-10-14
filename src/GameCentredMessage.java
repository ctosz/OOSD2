/**
 * GameCentredMessage is a concrete implementation of GameMessage.
 * Used for any message that needs to be drawn in the centre of the screen: game win, game loss, level win.
 */
public class GameCentredMessage extends GameMessage {
    private static final int FONT_SIZE = DEFAULT_FONT_SIZE;
    public GameCentredMessage(String message) {

        setMessage(message);
        setFontSize(FONT_SIZE);
        setMessageFont();

        // Following lines from Project 1 Solution (Tharun Dharmawickrema & Stella Li)
        setxPos(ShadowPac.getWindowWidth()/2.0 - (getMessageFont().getWidth(message)/2.0));
        setyPos(ShadowPac.getWindowHeight()/2.0 + (FONT_SIZE/2.0));
    }
}
