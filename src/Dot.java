import bagel.Image;
/**
 * This class is used to represent a specific concrete EdibleObject - Dot.
 * Most frequent edible object in ShadowPac.
 * Point value and image determined by specifications sheet.
 */
public class Dot extends EdibleObject {
    private static final String DOT_PNG = "res/dot.png";
    private static final int POINT_VALUE = 10;

    public Dot(double initX, double initY) {

        super(initX, initY);
        setCurrentImage(new Image(DOT_PNG));
        setPointValue(POINT_VALUE);
    }
}
