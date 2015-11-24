package group.cs242.beatblocks;

import java.util.Set;

/**
 * Serves as a template for game boards.
 *
 * @author Hunter Quant
 */
abstract class GameBoard {

    /* data members */

    /**
     * The size of the game board.
     */
    public int size;

    /**
     * The 2D array representation of the board.
     */
    protected byte [][] board;

    /* public abstract methods */

    /**
     * populates a game board.
     */
    public abstract void populate();

    /**
     * Gets all indices of a match.
     *
     * @return The set of indices.
     */
    public abstract Set<Index> checkMatches();

    /**
     * Removes all matched indices.
     *
     * @param matchedIndices - set of matched indices
     */
    public abstract void removeMatches(Set<Index> matchedIndices);
}
