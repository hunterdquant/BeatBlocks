package group.cs242.beatblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * This class serves as a backend framework for the interactive game board for Beat Blocks.
 */
public class BeatBlockBoard extends GameBoard {

    /* constructors */
    public BeatBlockBoard(int size) {
        board = new byte[width = size][height = size];
        populate();
    }

    /* public methods */

    /**
     *
     * @param i - The index to move up.
     */
    public void moveBlockUp(Index i) {
        if (!validBounds(i)) {
            return;
        }
        if (i.getY() - 1 < 0) {
            return;
        }
        try {
            byte temp = board[i.getX()][i.getY()-1];
            board[i.getX()][i.getY()-1] = board[i.getX()][i.getY()];
            board[i.getX()][i.getY()] = temp;
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
        checkMatches();
    }

    /**
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
        checkMatches();
    }


    /**
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
        checkMatches();
    }

    /**
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
        checkMatches();
    }

    /* protected methods */

    /**
     * Populates all empty indices of the game board.
     */
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
     * Finds all matches of 3+ on the game board.
     */
    protected void checkMatches() {
        List<Index> matchedIndices = new ArrayList<Index>();
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
        removeMatches(matchedIndices);
    }

    /**
     * @param matchedIndices - A list of element indices to remove.
     *
     * Sets all matched indices to zero, then calls for a repopulate.
     */
    protected void removeMatches(List<Index> matchedIndices) {
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

        populate();
    }

    /* private methods */

    /**
     *
     * @param i - an index to check for validity.
     * @return true if it's a valid index.
     */
    private boolean validBounds (Index i) {
        if (i.getX() < 0 || i.getY() > board.length - 1) {
            return false;
        }
        if (i.getY() < 0 || i.getX() > board.length - 1) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return The size of the board.
     */
    public int getBoardSize() {
        return width;
    }

    /**
     *
     * @param i - the index to retrieve the value from.
     * @return The value at the index i.
     */
    public int getValAtIndex(Index i) {
        return board[i.getX()][i.getY()];
    }
}
