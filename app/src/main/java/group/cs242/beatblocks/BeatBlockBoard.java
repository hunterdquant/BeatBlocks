package group.cs242.beatblocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hunter on 10/25/2015.
 */
public class BeatBlockBoard extends GameBoard{

    public BeatBlockBoard() {
        board = new byte[7][7];
    }

    protected void populate() {
        Random rand = new Random();
        try {
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
        checkMatches();
    }

    protected void checkMatches() {
        List<Index> matchedIndices = new ArrayList<Index>();
        try {
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
    }

    protected void removeMatches(List<Index> matchedIndices) {

    }

    class Index {
        private int i;
        private int j;

        private Index(int i, int j) {
            this.i = i;
            this.j = j;
        }

        private int getI() {
            return i;
        }

        private int getJ() {
            return j;
        }
    }
}
