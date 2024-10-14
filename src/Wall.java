import bagel.Image;

/**
 * This class is used to represent a specific concrete GameObject - Wall.
 * Stationary object.
 * Image predetermined by specifications sheet.
 */
public class Wall extends GameObject {
    private static final String WALL_PNG = "res/wall.png";
    public Wall(double initX, double initY) {

        super(initX, initY);
        setCurrentImage(new Image(WALL_PNG));
    }

    @Override
    public void checkCollision(GameCharacter obj) {

        if (this.getBoundingBox().intersects(obj.getBoundingBox())) {

            obj.reactWall();
        }
    }
}
