package group.cs242.beatblocks;

import android.util.Log;

import java.util.List;
import java.util.Random;
/**
 * Created by Hunter on 10/25/2015.
 */
abstract class GameBoard {

    protected byte [][] board;

    protected abstract void populate();
    protected abstract void checkMatches();
    protected abstract void removeMatches(List<BeatBlockBoard.Index> matchedIndices);
}
