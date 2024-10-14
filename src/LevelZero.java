/**
 * LevelZero is an extension of GameLevel with specific logic implemented for
 * drawing the start screen and winning the level.
 * */
public class LevelZero extends GameLevel {
    private static final boolean DO_GHOSTS_MOVE = false;
    private static final GameTitle GAME_TITLE = new GameTitle();
    private static final InstructionMessageLevelZero INSTRUCTION_MESSAGE = new InstructionMessageLevelZero();
    private static final int WIN_SCORE = 1210;

    public LevelZero(String csvFile) {

        super(csvFile, DO_GHOSTS_MOVE);
        setWinScore(WIN_SCORE);
    }

    @Override
    public void startScreen() {
        GAME_TITLE.drawMessage();
        INSTRUCTION_MESSAGE.drawMessage();
    }

    @Override
    public void levelWin() {

        if (getWinMsgFrameCounter() > 0) {
            levelWinScreen();
            reduceWinMsgFrameCount();
        }

        else {
            setLevelWon();
        }
    }
}
