package group.cs242.beatblocks;

import java.util.Random;

/**
 * Created by hunte on 10/25/2015.
 */
public class BeatBlockBoard extends GameBoard{

    private enum Block {
        RED(1), BLUE(2), GREEN(3), YELLOW(4);

        private int value;

        private Block(int value) {
            this.value = value;
        }
    }

    public BeatBlockBoard() {
        board = new int[7][7];
    }

    protected void populate() {
        Random rand = new Random();
        try {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] != 0) continue;
                    //Adding 1 to exclude zero.
                    int blockType = rand.nextInt(4) + 1;
                    board[i][j] = blockType;
                }
            }
        } catch (Exception e){
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }
}
