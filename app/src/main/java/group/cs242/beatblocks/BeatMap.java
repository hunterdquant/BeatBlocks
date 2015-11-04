package group.cs242.beatblocks;

import java.io.File;

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
    int song_length;       //represents how long the song is in minutes\
    final int num = 6;   //Represents the number of beats displayed at one time
    final int width = 1080; //Represents width in pixels
    //^^^^^^Should be changed in future for scaling and such
    File song;



    //Methods
    public boolean isGoodMove() //To be called whenever a move is made. Will return true if there
    {                           //is a beat that has not been used in the accepting range
        for(int i = 0; i < num; i++)
        {
            if ((beats[i].getUsed() == false) &&
                    (beats[i].getLeft()>=accepting_range.getLeft()) &&
                    (beats[i].getRight()<=accepting_range.getRight()))
            {
                beats[i].setUsed(true);
                return true;
            }
        }
        return false;
    }
    private void createBeats() //Called in the constructor, creates the Beats array
    {

        int num_beats = beats_per_minute * song_length;
        int beat_range = width/num;
        beats = new Beat[num];
        for(int i = 0; i < num; i++)
        {
            beats[i] = new Beat(-(i*beat_range), (-(i*beat_range) - 20));
        }



        //create Beats array here

    }

    void draw()
    {
        for(int i = 0; i < num; i++)
        {
            beats[i].draw();
        }
        accepting_range.draw();
    }

    void run()
    {
        
    }



    //Constructors
    BeatMap(int bpm, int songl)
    {
        beats_per_minute = bpm;
        song_length = songl;
        createBeats();
    }




}
