import bagel.*;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameLevel outlines the structure for a ShadowPac level.
 * Contains the state of the level (won/lost/neither) and all level entities.
 * When instantiating a GameLevel, reads the input CSV given and extracts all entity positions.
 * GameLevel run using the run() method.
 * */
public abstract class GameLevel {

    private static final int DISPLAY_WIN_MESSAGE_FRAMES = 300;
    private static final int FRENZY_MODE_FRAMES = 1000;
    private static final String LEVEL_WIN_STRING = "LEVEL COMPLETE!";
    private static final GameCentredMessage LEVEL_WIN_MESSAGE = new GameCentredMessage(LEVEL_WIN_STRING);

    private final ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private PlayableCharacter player;
    private final ArrayList<Ghost> ghosts = new ArrayList<>();
    private final ArrayList<Wall> walls = new ArrayList<>();
    private final ArrayList<EdibleObject> edibleObjects = new ArrayList<>();

    // The score that results in a player winning the level - specific to each level.
    private int winScore;
    private boolean hasStarted;
    private boolean gameOver;
    private boolean gameWon;
    private boolean levelWin;

    // Variables to count down frames until level win message / frenzy mode is over.
    private int winMsgFrameCounter;
    private int frenzyFrameCounter;

    public GameLevel(String csvFile, boolean ghostsMove) {

        this.hasStarted = false;
        this.gameOver = false;
        this.levelWin = false;
        this.gameWon = false;
        this.winMsgFrameCounter = DISPLAY_WIN_MESSAGE_FRAMES;
        this.frenzyFrameCounter = FRENZY_MODE_FRAMES;

        readCSV(csvFile, ghostsMove);
        addAllEntities();
        addCollisionObservers();
    }

    /**
     * Method used to read file and create objects. Modelled from Project 1 Solution (Tharun Dharmawickrema & Stella Li)
     * @param csvFile: comma separated values in the form of: entityName,xPosition,yPosition for each game entity.
     * @param ghostsMove: boolean value referring to whether or not the ghosts can move in the level.
     */
    private void readCSV(String csvFile, boolean ghostsMove) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))){

            //https://www.tutorialspoint.com/generate-random-boolean-in-java - for Green ghost movement
            //https://mkyong.com/java/java-generate-random-integers-in-a-range/ - for Pink ghost movement
            Random rd = new Random();

            String line;

            while((line = reader.readLine()) != null) {

                String[] sections = line.split(",");

                // .contains() rather than straight switch cases for different ghosts & player is 'pellPlayer' in level1.csv
                if (sections[0].contains("Player")) {
                    this.player = new PacMan(Double.parseDouble(sections[1]), Double.parseDouble(sections[2]));

                } else if (sections[0].contains("Ghost")) {

                    switch (sections[0]) {
                        case "GhostRed":
                        case "Ghost":
                            this.ghosts.add(new RedGhost(Double.parseDouble(sections[1]), Double.parseDouble(sections[2]), ghostsMove));
                            break;
                        case "GhostBlue":
                            this.ghosts.add(new BlueGhost(Double.parseDouble(sections[1]), Double.parseDouble(sections[2])));
                            break;
                        case "GhostGreen":

                            this.ghosts.add(new GreenGhost(Double.parseDouble(sections[1]), Double.parseDouble(sections[2]), rd.nextBoolean()));
                            break;
                        case "GhostPink":
                            this.ghosts.add(new PinkGhost(Double.parseDouble(sections[1]), Double.parseDouble(sections[2]), rd.nextInt(4)));
                            break;
                    }
                } else {
                    switch (sections[0]) {
                        case "Cherry":
                            this.edibleObjects.add(new Cherry(Double.parseDouble(sections[1]), Double.parseDouble(sections[2])));
                            break;
                        case "Pellet":
                            this.edibleObjects.add(new Pellet(Double.parseDouble(sections[1]), Double.parseDouble(sections[2])));
                            break;
                        case "Dot":
                            this.edibleObjects.add(new Dot(Double.parseDouble(sections[1]), Double.parseDouble(sections[2])));
                            break;
                        case "Wall":
                            this.walls.add(new Wall(Double.parseDouble(sections[1]), Double.parseDouble(sections[2])));
                            break;

                    }
                }
            }

        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Add observing entities for the player and ghosts.
     * 'Observing' entities are any who cause a state change of themselves and/or the observed entity when
     * colliding with them. E.g., if a player overlaps/collides with a Dot, their score should increase and the dot
     * should be deactivated.
     */
    public void addCollisionObservers() {

        this.player.addObservers(this.ghosts);
        this.player.addObservers(this.walls);
        this.player.addObservers(this.edibleObjects);

        // Don't need to add player to ghost observers since ghosts all already observe player for collisions.
        for (Ghost g : this.ghosts) {
            g.addObservers(this.walls);
        }
    }

    /**
     * Run (render) the level.
     *
     */
    public void run(Input input) {

        // Skip the level if user pressed 'W'
        if (input.wasPressed(Keys.W)) {
            levelSkip();
        }

        // Level is in frenzy mode
        if (player.getFrenzyMode()) {
            if (frenzyFrameCounter == FRENZY_MODE_FRAMES) {
                player.frenzy();
                for (Ghost g : ghosts) {
                    g.frenzy();
                }
            }
            frenzyFrameCounter--;
            if (frenzyFrameCounter == 0) {
                player.endFrenzy();
                for (Ghost g : ghosts) {
                    g.endFrenzy();
                }
            }
        }

        // Display level start screen (instructions) until user presses space.
        if (!hasLevelStarted()) {
            startScreen();

            if (input.wasPressed(Keys.SPACE)) {
                levelHasStarted();
            }
        }
        // For level 0: skip to level complete screen.
        else if (input.wasPressed(Keys.Q)) {
            player.setScore(this.winScore);
        }
        else if (player.getScore() >= this.winScore) {
            this.levelWin();
        }
        else if (player.getNumLives() == 0) {
            this.levelLose();
        }
        else {
            for (GameEntity gmObj : gameEntities) {
                gmObj.render(input);
            }
        }
    }

    /**
     * Add all game entities to ArrayList, so all can be rendered in one iteration in run().
     */
    private void addAllEntities() {
        gameEntities.addAll(walls);
        gameEntities.addAll(edibleObjects);
        gameEntities.addAll(ghosts);
        gameEntities.add(player);
    }

    /**
     * Draw the level start screen. Dependent on level as instructions differ, so implemented within specific
     * level class (0 or 1).
     * Level-specific overrides modelled from Project 1 Solution (Tharun Dharmawickrema & Stella Li).
     */
    public abstract void startScreen();

    /**
     * Change state of the level object and run win screen/actions.
     * Implementation delegated to specific level classes (LevelZero or LevelOne) since logic differs between them.
     */
    public abstract void levelWin();

    /**
     * For this version of ShadowPac, losing a level means losing the whole game.
     * Hence, set gameOver boolean value for the current level to true.
     * Public for use by subclasses.
     */
    public void levelLose() { this.gameOver = true; }

    /**
     * Draw the message on the level win screen - "Level Complete!".
     * Used by subclasses.
     */
    public void levelWinScreen() { LEVEL_WIN_MESSAGE.drawMessage(); }

    // Setters
    public void reduceWinMsgFrameCount() {
        this.winMsgFrameCounter --;
    }
    public void setLevelWon() {
        this.levelWin = true;
    }
    public void setWinScore(int score) {
        this.winScore = score;
    }
    public void setGameWon() {
        this.gameWon = true;
    }
    public void levelHasStarted() {
        this.hasStarted = true;
    }
    public void levelSkip() {
        this.levelWin = true;
    }

    // Getters
    public boolean hasLevelStarted() {
        return this.hasStarted;
    }
    public int getWinMsgFrameCounter() {
        return this.winMsgFrameCounter;
    }
    public boolean isGameWon() {
        return this.gameWon;
    }
    public boolean isGameOver() {
        return this.gameOver;
    }
    public boolean hasLevelWon() {
        return this.levelWin;
    }
}
