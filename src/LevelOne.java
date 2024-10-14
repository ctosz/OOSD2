/**
 * LevelOne is an extension of GameLevel with specific logic implemented for
 * drawing the start screen and winning the level.
 * */
public class LevelOne extends GameLevel {
    private static final boolean DO_GHOSTS_MOVE = true;
    private static final InstructionMessageLevelOne instructionMessage = new InstructionMessageLevelOne();
    private static final int WIN_SCORE = 800;

    public LevelOne(String csvFile) {

        super(csvFile, DO_GHOSTS_MOVE);
        setWinScore(WIN_SCORE);

    }

    @Override
    public void startScreen() { instructionMessage.drawMessage(); }

    @Override
    public void levelWin() { this.setGameWon(); }
}
