package group.cs242.beatblocks;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * Serves as a pair of points for indices of a 2D array.
 */
public class Index {

    /* data members */
    private int x;
    private int y;

    /* constructors */

    /**
     *
     * @param x - the x index
     * @param y - the y index
     */
    public Index(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /* public methods */

    /**
     *
     * @return the x position of the index.
     */
    public int getX() {

        return x;
    }

    /**
     *
     * @return the y position of the array.
     */
    public int getY() {

        return y;
    }
}