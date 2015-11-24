package group.cs242.beatblocks;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class serves as a backend framework for the interactive game board for Beat Blocks.
 *
 * @author Hunter Quant
 */
public class BeatBlockBoard extends GameBoard {

    /* data members */

    /**
     * An action listener for updating game score.
     */
    private ScoreUpdateListener listener;

    /* constructors */

    /**
     * The default constructor for a BeatBlockBoard.
     */
    public BeatBlockBoard() {
        this(5);
    }

    /**
     * Constructs a BeatBlockBoard object with the specified size.
     *
     * @param size - The size of the board.
     */
    public BeatBlockBoard(int size) {
        this.size = size;
        board = new byte[size][size];
        populate();
    }

    /* public methods */

    /**
     * Gets the calculated points of a move.
     *
     * @param set - The set of blocks in a move.
     * @return The calculated points.
     */
    public int getPoints(Set<Index> set) {
        return 10*set.size()*set.size();
    }

    /**
     * Sets the action listener.
     *
     * @param sul - The action listener for updating the score.
     */
    public void setListener(ScoreUpdateListener sul) {
        this.listener = sul;
    }

    /**
     * Moves the block at index i up.
     *
     * @param i - The index to move up.
     */
    public void moveBlockUp(Index i) {

        // Return if the bounds aren't valid
        if (!validBounds(i)) {
            return;
        }
        if (i.getY() - 1 < 0) {
            return;
        }

        // Move the specified index up.
        try {
            byte temp = board[i.getX()][i.getY()-1];
            board[i.getX()][i.getY()-1] = board[i.getX()][i.getY()];
            board[i.getX()][i.getY()] = temp;
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }

    /**
     * Moves the block at index i right.
     *
     * @param i - The index to move right.
     */
    public void moveBlockRight(Index i) {
        if (!validBounds(i)) {
            return;
        }
        if (i.getX() + 1 > board.length - 1) {
            return;
        }
        try {
            byte temp = board[i.getX()+1][i.getY()];
            board[i.getX()+1][i.getY()] = board[i.getX()][i.getY()];
            board[i.getX()][i.getY()] = temp;
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }


    /**
     * Moves the block at index i down.
     *
     * @param i - The index to move down.
     */
    public void moveBlockDown(Index i) {
        if (!validBounds(i)) {
            return;
        }
        if (i.getY() + 1 > board.length - 1) {
            return;
        }
        try {
            byte temp = board[i.getX()][i.getY()+1];
            board[i.getX()][i.getY()+1] = board[i.getX()][i.getY()];
            board[i.getX()][i.getY()] = temp;
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }

    /**
     * Moves the block at index i left.
     *
     * @param i - The index to move left.
     */
    public void moveBlockLeft(Index i) {
        if (!validBounds(i)) {
            return;
        }
        if (i.getX() - 1 < 0) {
            return;
        }
        try {
            byte temp = board[i.getX()-1][i.getY()];
            board[i.getX()-1][i.getY()] = board[i.getX()][i.getY()];
            board[i.getX()][i.getY()] = temp;
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }

    /**
     * Populates all empty indices of the game board.
     */
    @Override
    public void populate() {
        Random rand = new Random();
        try {
            //Randomly populate each element of the game board.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] != 0) continue;
                    //Adding 1 to exclude zero.
                    byte blockType = (byte)(rand.nextInt(5) + 1);
                    board[i][j] = blockType;
                }
            }
        } catch (Exception e){
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
        //After population check for matches on the board.
        checkMatches();
    }


    /**
     * @return The size of the board.
     */
    public int getBoardSize() {
        return size;
    }

    /**
     *  Gets the value stored at an index.
     *
     * @param i - the index to retrieve the value from.
     * @return The value at the index i.
     */
    public int getValAtIndex(Index i) {
        return board[i.getX()][i.getY()];
    }

    /* protected methods */

    /**
     * Finds all matches of 3+ on the game board.
     */
    @Override
    public Set<Index> checkMatches() {
        Set<Index> matchedIndices = new HashSet<Index>();
        try {
            //Add all indices in a 3+ match.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (i > 1) {
                        if (board[i-2][j] == board[i][j] && board[i-1][j] == board[i][j]) {
                            matchedIndices.add(new Index(i-2, j));
                            matchedIndices.add(new Index(i-1, j));
                            matchedIndices.add(new Index(i, j));
                        }
                    }
                    if (j > 1) {
                        if (board[i][j-2] == board[i][j] && board[i][j-1] == board[i][j]) {
                            matchedIndices.add(new Index(i, j-2));
                            matchedIndices.add(new Index(i, j-1));
                            matchedIndices.add(new Index(i, j));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
        //After finding the matches, remove the elements at the matched indices.
        return matchedIndices;
    }

    /**
     * Sets all matched indices to zero, then calls for a repopulate.
     *
     * @param matchedIndices - A list of element indices to remove.
     */
    @Override
    public void removeMatches(Set<Index> matchedIndices) {
        if (matchedIndices.size() == 0) {
            return;
        }
        try {
            for (Index i : matchedIndices) {
                board[i.getX()][i.getY()] = 0;
            }
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
        if (listener != null) {
            listener.updateScore(getPoints(matchedIndices));
        }
    }

    /* private methods */

    /**
     * Determines whether a passed index is valid.
     *
     * @param i - an index to check for validity.
     * @return true if it's a valid index.
     */
    private boolean validBounds (Index i) {
        if (i.getX() < 0 || i.getY() > board.length - 1) {
            return false;
        } else if (i.getY() < 0 || i.getX() > board.length - 1) {
            return false;
        }
        return true;
    }
}
