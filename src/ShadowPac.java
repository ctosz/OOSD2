import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2023
 * This is the main class for executing the ShadowPac game.
 * @author Claire Tosolini
 * @version 1.0
 */
public class ShadowPac extends AbstractGame  {

    // These attributes are public as they are used by GameMessage and Score classes.
    public static final String FONT_FILE = "res/FSO8BITR.TTF";
    public static final String GAME_TITLE = "SHADOW PAC";

    private static final String GAME_WIN_STRING = "WELL DONE!";
    private static final String GAME_LOSE_STRING = "GAME OVER!";
    private final GameCentredMessage GAME_WIN_MSG = new GameCentredMessage(GAME_WIN_STRING);
    private final GameCentredMessage GAME_LOSE_MSG = new GameCentredMessage(GAME_LOSE_STRING);


    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private static final String LEVEL_0_CSV = "res/level0.csv";
    private static final String LEVEL_1_CSV = "res/level1.csv";

    private final GameLevel levelZero;
    private final GameLevel levelOne;
    private boolean gameOver;
    private boolean gameWin;

    public ShadowPac(){

        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        levelZero = new LevelZero(LEVEL_0_CSV);
        levelOne = new LevelOne(LEVEL_1_CSV);

        this.gameOver = false;
        this.gameWin = false;
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Executes at the refresh rate of current monitor (assessed on 60Hz).
     * Allows the game to exit when the escape key is pressed.
     * Renders all entities for each Level (0 and 1) until the game is won (both levels completed)
     * or lost (player has lost all lives on either level).
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (this.gameWin) {
            gameWinScreen();
        }

        else if (this.gameOver) {
            gameLoseScreen();
        }

        // Run Level 0 while it has not yet been won
        else if (!levelZero.hasLevelWon()) {
            if (levelZero.isGameOver()) {
                this.gameOver = true;
            }
            levelZero.run(input);
        }

        // Run Level 1 once Level 0 is complete
        else {
            if (levelOne.isGameWon()) {
                this.gameWin = true;
            }
            else if (levelOne.isGameOver()) {
                this.gameOver = true;
            }
            levelOne.run(input);
        }
    }

    /**
     * Render the game win screen.
     */
    private void gameWinScreen() {
        GAME_WIN_MSG.drawMessage();
    }

    /**
     * Render the game loss screen.
     */
    private void gameLoseScreen() {
        GAME_LOSE_MSG.drawMessage();
    }

    /**
     * Getters.
     * Both methods are static since window width & height are static attributes of ShadowPac.
     */
    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }
    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }
}
