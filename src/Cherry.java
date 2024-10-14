import bagel.Image;
/**
 * This class is used to represent a specific concrete EdibleObject - Cherry.
 * Point value and image predetermined by specifications sheet; double Dot point value.
 */
public class Cherry extends EdibleObject {
    private static final String CHERRY_PNG = "res/cherry.png";
    private static final int POINT_VALUE = 20;

    public Cherry(double initX, double initY) {

        super(initX, initY);
        setCurrentImage(new Image(CHERRY_PNG));
        setPointValue(POINT_VALUE);
    }
}
