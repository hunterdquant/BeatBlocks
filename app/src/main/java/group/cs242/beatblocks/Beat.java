package group.cs242.beatblocks;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *
 *  Represents an idividual Beat to be used in the BeatMap
 */
public class Beat {
    //Data Members
    int rightbound;  //The right bound of the beat
    int leftbound;   //The left bound of the beat
    boolean used;    //represents if the beat has been used yet



    //Constructors
    Beat(int right, int left)
    {
        rightbound = right;
        leftbound = left;
        used = false;
    }
}
