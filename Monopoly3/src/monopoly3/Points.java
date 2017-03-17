/**
 * Created by dgc1
 * @author Denise Grace Crowley
 * @version 1.0
 *
 */
package monopoly3;

/**
 * The Points class keeps the x and y coordinates of the player on board
 */
public class Points {

    /**
     * 
     * @param x
     * @param y
     */
    public Points(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;

    /**
     *Returns the X coordinate parameter
     * @return X
     */
    public int getX() {
        return x;
    }

    /**
     * Assign the x coordinate value
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *Returns the Y coordinate parameter
     * @return Y
     */
    public int getY() {
        return y;
    }

    /**
     * Assign the y coordinate values
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
}
