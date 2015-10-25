package group.cs242.beatblocks;

import android.util.Log;

import java.util.Random;
/**
 * Created by Hunter on 10/25/2015.
 */
abstract class GameBoard {

    protected int [][] board;

    protected abstract void populate();
    //protected abstract void checkMatches();
    //protected abstract void removeMatches();
}
