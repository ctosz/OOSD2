import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This class is used to represent each entity within the game.
 * Every entity has a corresponding position and image.
 * Each entity is rendered using render() at the frame-rate specified in ShadowPac.update()
 */
public abstract class GameEntity {
    private boolean isActive;
    private Image currentImage;
    private Point position;
    public GameEntity(double initX, double initY) {

        this.position = new Point(initX, initY);
        this.isActive = true;
    }

    /**
     * Render the entity i.e., draw its image to the screen.
     * @param input: the user's input key/s.
     */
    public void render(Input input) {
        if (this.isActive) {
            this.drawCurrentImage();
        }
    }

    /**
     * Method that checks whether two entities collide i.e., overlap.
     * Specific logic implemented in child classes.
     * @param obj: any GameCharacter (PacMan or Ghost) that will be checked against this entity for overlap.
     * @param <T>: in this ShadowPac game, the player (pacman) and ghosts are the only entities whose position determines
     *           the state of other entities. For example, if the player and a Dot collide, the dot should be set inactive
     *           (not rendered in further frames).
     */
    public abstract <T extends GameCharacter> void checkCollision(T obj);

    /**
     * Method that gets the boundaries of an entity.
     * Used for collision checking.
     * From Project 1 Solution (Tharun Dharmawickrema & Stella Li).
     */
    public Rectangle getBoundingBox() {

        return new Rectangle(position, currentImage.getWidth(), currentImage.getHeight());
    }

    /**
     * Method to draw the entity's image to the screen.
     */
    public void drawCurrentImage() {

        currentImage.drawFromTopLeft(position.x, position.y);
    }

    // Getters
    public boolean isEntityActive() { return this.isActive; }
    public Image getCurrentImage() { return currentImage; }
    public Point getPosition() { return this.position; }
    public double getX() { return this.position.x; }
    public double getY() { return this.position.y; }

    // Setters
    public void setInactive() { this.isActive = false; }
    public void setActive() { this.isActive = true; }
    public void setCurrentImage(Image image) { this.currentImage = image; }
    public void setPosition(Point newPos) { this.position = newPos; }
}
