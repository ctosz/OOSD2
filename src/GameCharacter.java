import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * This class is used to represent GameEntities which can move and are observed.
 * Observable refers to other entities that react to a change in their position.
 */
public abstract class GameCharacter extends GameEntity {

    // The following four attributes are public as they are used by various child classes.
    public static final int UP = 3;
    public static final int RIGHT = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;

    /* Keeping track of both previous and staring position is necessary for movable entities, since
        they will be required to be able to move back (undo a move) as well as reset to their original position.
        From Project 1 Solution (Tharun Dharmawickrema & Stella Li). */
    private Point prevPosition;
    private final Point startingPosition;

    /* A GameCharacter's collision observers are any entities which react to GameCharacter position change by changing
        their own state and/or causing a reaction (state change) in the observed entity. */
    private ArrayList<GameEntity> collisionObservers = new ArrayList<>();
    private boolean frenzyMode;
    private double moveSize;

    public GameCharacter(double initX, double initY) {
        super(initX, initY);
        startingPosition = new Point(initX, initY); // from p1 solution
        prevPosition = startingPosition;
        this.frenzyMode = false;
    }

    /**
     * Method to move the rendered position of an GameCharacter.
     * Inputs must consider their sign in order for directions to be correct, as this method does not
     * take this into account (e.g., entity moving upwards --> y value is decreasing --> yMove input must be negative).
     * @param xMove: offset in x direction.
     * @param yMove: offset y direction.
     */
    public void move(double xMove, double yMove) {

        this.prevPosition = this.getPosition();
        this.setPosition(new Point(this.getX() + xMove, this.getY() + yMove));
    }

    /**
     * Method to 'undo' a character's move, such as when colliding with a Wall.
     */
    public void moveBack() { setPosition(this.prevPosition); }

    /**
     * Method to reset the character's position to their original/starting position (as per CSV input file).
     * Further implementation in Overridden method in PacMan class due to resetting of player rotation and image.
     */
    public void resetPosition() { this.setPosition(getStartingPosition()); }


    // collision observers are any game entities that will change state when another object collides with them

    /**
     * Method to add single collision observers to a GameCharacter's ArrayList.
     * @param entity: the entity being added.
     */
    public void addObserver(GameEntity entity) { this.collisionObservers.add(entity); }

    /**
     * Method to add an ArrayList of any GameEntities to the character's list of observers.
     * <a href="https://stackoverflow.com/questions/31158775/subtype-of-generic-type-in-java">...</a>
     * @param elements: the ArrayList.
     * @param <T>: allows ArrayLists of child classes of GameEntity to be added (rather than having to be
     *           ArrayLists of GameEntities themselves, or having to add the whole ArrayList of all gameEntities).
     *           This enables different characters to have different observer lists e.g., players
     *           are observed by EdibleObjects but ghosts are not.
     */
    public <T extends GameEntity> void addObservers(ArrayList<T> elements) {
        for (T e : elements) {
            addObserver(e);
        }
    }


//     notify of new position when player moves but BEFORE rendering player to screen

    /**
     * Method to notify all observing entities each time the character's position moves.
     * Ensure notified before rendering the character as some reactionary state updates may be required.
     */
    public void notifyObservers() {

        for (GameEntity e : this.collisionObservers) {
            // For each observer, check for collisions. If there IS a collision, the objects handle it themselves,
            // reacting if appropriate.
            e.checkCollision(this);
        }
    }


    /**
     * Methods to 'react' to state changes depending on what entity was collided with.
     * When an observer is notified of a position change, they will check for a collision. If there is a collision,
     *      the entity will induce any required state updates of this GameCharacter by calling one of the following react
     *      method depending on its type.
     * No characters can move through Walls, hence moveBack(). However, Ghosts colliding with Walls
     *      causes their direction to reverse - see Overridden method in their subclasses.
     * EdibleObject and Ghost collision reaction logic is left to specific child classes.
     */
    public void reactWall() { moveBack(); }
    public abstract void reactEdible(EdibleObject edib); // react to any change in observed objects
    public abstract void reactGhost(Ghost g);


    /**
     * Methods to start and end frenzy mode.
     * Overridden methods in child classes contain specific logic on the changes
     * in that class' state.
     */
    public abstract void frenzy();
    public abstract void endFrenzy();


    // Getters
    public boolean getFrenzyMode() { return this.frenzyMode; }
    public double getMoveSize() { return this.moveSize; }
    public Point getStartingPosition() { return this.startingPosition; }


    // Setters
    public void startFrenzyMode() { this.frenzyMode = true; }
    public void endFrenzyMode() { this.frenzyMode = false; }
    public void setMoveSize(double moveSize) { this.moveSize = moveSize; }
}
