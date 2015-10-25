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
    public BeatBlockBoard() {
        board = new byte[7][7];
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
        if (i.getJ() - 1 < 0) {
            return;
        }
        try {
            byte temp = board[i.getI()][i.getJ()-1];
            board[i.getI()][i.getJ()-1] = board[i.getI()][i.getJ()];
            board[i.getI()][i.getJ()] = temp;
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
        if (i.getI() + 1 > board.length - 1) {
            return;
        }
        try {
            byte temp = board[i.getI()+1][i.getJ()];
            board[i.getI()+1][i.getJ()] = board[i.getI()][i.getJ()];
            board[i.getI()][i.getJ()] = temp;
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
        if (i.getJ() + 1 > board.length - 1) {
            return;
        }
        try {
            byte temp = board[i.getI()][i.getJ()+1];
            board[i.getI()][i.getJ()+1] = board[i.getI()][i.getJ()];
            board[i.getI()][i.getJ()] = temp;
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
        if (i.getI() - 1 < 0) {
            return;
        }
        try {
            byte temp = board[i.getI()-1][i.getJ()];
            board[i.getI()-1][i.getJ()] = board[i.getI()][i.getJ()];
            board[i.getI()][i.getJ()] = temp;
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
    protected void populate() {
        Random rand = new Random();
        try {
            //Randomly populate each element of the game board.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] != 0) continue;
                    //Adding 1 to exclude zero.
                    byte blockType = (byte)(rand.nextInt(4) + 1);
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
                board[i.getI()][i.getJ()] = 0;
            }
        } catch (Exception e) {
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
        //Repopulate.
        populate();
    }

    /* private methods */

    private boolean validBounds (Index i) {
        if (i.getI() < 0 || i.getI() > board.length - 1) {
            return false;
        }
        if (i.getJ() < 0 || i.getJ() > board.length - 1) {
            return false;
        }
        return true;
    }
}
