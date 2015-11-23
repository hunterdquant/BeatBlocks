package group.cs242.beatblocks;

import java.util.Set;

/**
 * @author Hunter Quant <quanthd@clarkson.edu> <hunterdquant@gmail.com>
 *
 * This interface serves as a handler for updating the game score..
 */
public interface ScoreUpdateListener {
    public void updateScore(Set<Index> Set);
}
