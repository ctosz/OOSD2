/**
 * Implementation specific for the ShadowPac GameTitle.
 * X and Y positions are predetermined by assignment specification.
 */
public class GameTitle extends GameMessage {
    private static final int FONT_SIZE = DEFAULT_FONT_SIZE;
    private static final int TITLE_X = 260;
    private static final int TITLE_Y = 250;

    public GameTitle() {

        setMessage(ShadowPac.GAME_TITLE);
        setFontSize(FONT_SIZE);

        setxPos(TITLE_X);
        setyPos(TITLE_Y);

        setMessageFont();
    }

    // Getters
    public static int getTitleX() { return TITLE_X; }
    public static int getTitleY() { return TITLE_Y; }
}
