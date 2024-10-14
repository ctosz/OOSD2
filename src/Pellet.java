import bagel.Image;
/**
 * This class is used to represent a specific concrete EdibleObject - Pellet.
 * When eaten, activates frenzy mode.
 * Image determined by specifications sheet.
 */
public class Pellet extends EdibleObject {
    private static final String PELLET_PNG = "res/pellet.png";
    public Pellet(double initX, double initY) {

        super(initX, initY);
        setCurrentImage(new Image(PELLET_PNG));
    }
    @Override
    public void checkCollision(GameCharacter obj) {

        if (this.getBoundingBox().intersects(obj.getBoundingBox())) {

            // If collision: activate frenzy mode
            obj.frenzy();
            this.setInactive();
        }
    }
}
