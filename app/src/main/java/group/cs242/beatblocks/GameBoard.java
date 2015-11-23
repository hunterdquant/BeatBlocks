package group.cs242.beatblocks;

import java.util.List;
import java.util.Set;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * Serves as a template for game boards.
 */
abstract class GameBoard {

    /* data members */
    public int width, height;
    protected byte [][] board;

    /* protected abstract methods */

    public abstract void populate();
    public abstract Set<Index> checkMatches();
    public abstract void removeMatches(Set<Index> matchedIndices);
}
