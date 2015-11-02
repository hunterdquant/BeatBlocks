package group.cs242.beatblocks;

import java.util.List;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * Serves as a template for game boards.
 */
abstract class GameBoard {

    /* data members */
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;

    protected byte [][] board;

    /* protected abstract methods */

    protected abstract void populate();
    protected abstract void checkMatches();
    protected abstract void removeMatches(List<Index> matchedIndices);
}
