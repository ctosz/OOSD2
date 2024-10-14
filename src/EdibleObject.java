/**
 * This class is used to represent any GameObject that can be 'eaten' by the player.
 * Each has a point value that will be added to the player's score if they collide.
 */
public abstract class EdibleObject extends GameObject {
    private int pointValue;
    public EdibleObject(double initX, double initY) {
        super(initX, initY);
    }

    @Override
    public void checkCollision(GameCharacter obj) {

        if (this.isEntityActive() && this.getBoundingBox().intersects(obj.getBoundingBox())) {

            obj.reactEdible(this);
            this.setInactive();
        }
    }

    // Getter
    public int getPointValue() {
        return this.pointValue;
    }

    // Setter
    public void setPointValue(int value) { this.pointValue = value; }
}
