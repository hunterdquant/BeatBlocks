package group.cs242.beatblocks;

/**
 * Serves as a pair (x, y) for indices of a 2D array.
 *
 * @author Hunter Quant
 */
public class Index {

    /* data members */

    /**
     * The x and y index or coordinate.
     */
    private int x, y;

    /**
     * The unique id of a point
     */
    private int id;

    /* constructors */

    /**
     * Default constructor for the Index class.
     */
    public Index() {
        this(0, 0);
    }

    /**
     * Constructs a Index object with the specified point (x, y)
     *
     * @param x - the x index
     * @param y - the y index
     */
    public Index(int x, int y) {
        this.x = x;
        this.y = y;
        id = calcId();
    }

    /* public methods */

    /**
     * @return the x position of the index.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y position of the array.
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if 2 Index objects are equal.
     *
     * @param obj - an object to compare to.
     * @return True if the two Index objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Index) {
            Index i = (Index)obj;
            if (i.getY() == getY() && i.getX() == getX()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The unique Index id as the hash code.
     */
    @Override
    public int hashCode() {
        return id;
    }

    /* private methods */

    /**
     * Calculates the semi-unique Index id.
     *
     * <p>
     *     The id is the x concatenated with the y value.
     *     This is only unique for the purposes of this game and doesn't scale with board size > 10.
     * </p>
     *
     * @return the index id.
     */
    private int calcId() {
        return 10*x + y;
    }
}