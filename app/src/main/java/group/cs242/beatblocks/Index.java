package group.cs242.beatblocks;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * Serves as a pair of points for indices of a 2D array.
 */
public class Index {

    /* data members */
    private int i;
    private int j;

    /* constructors */

    /**
     *
     * @param i - the x index
     * @param j - the y index
     */
    public Index(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /* public methods */

    /**
     *
     * @return the x position of the index.
     */
    public int getI() {
        return i;
    }

    /**
     *
     * @return the y position of the array.
     */
    public int getJ() {
        return j;
    }
}