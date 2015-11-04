package group.cs242.beatblocks;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *
 * Defines the specifications for the Beat Map displayed in game
 */
public class BeatMap {

    //Data Members
    Beat beats[];    //contains all of the beats that will be used throughout a game
    Beat accepting_range;  //an unmoving Beat the will represent when a move can be made
    //^^^Should be a constant in terms of pixels, which I'll figure out later
    int beats_per_minute;  //represents the the number of beats in a minute of the song used
    int song_length;       //represents how long the song is in minutes


    //Methods
    public boolean isGoodMove() //To be called whenever a move is made. Will return true if there
    {                           //is a beat that has not been used in the accepting range
        //TO DO
        return true;
    }
    private void createBeats() //Called in the constructor, creates the Beats array
    {
        int num_beats = beats_per_minute * song_length;


        //create Beats array here

    }


    //Constructors
    BeatMap(int bpm, int songl)
    {
        beats_per_minute = bpm;
        song_length = songl;
        createBeats();
    }




}
