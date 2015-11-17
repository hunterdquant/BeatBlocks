package group.cs242.beatblocks;

import android.graphics.Rect;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *
 * Defines the specifications for the Beat Map displayed in game
 */
public class BeatMap {

    //Data Members
    Beat beats[];    //contains all of the beats that will be used throughout a game
    Rect accepting_range = new Rect(980, 525, 1080, 840);  //an unmoving Rectangle the will represent when a move can be made
    int beats_per_minute;  //represents the the number of beats in a minute of the song used
    double song_length;       //represents how long the song is in minutes
    int top = 525;
    int bottom = 840;
    int total_beats; //to be used in loops
    long milliseconds_per_beat;
    int current_beat;   //should help speed up isGoodMove a little

    //Methods
    public boolean isGoodMove() //To be called whenever a move is made. Will return true if there
    {                           //is a beat that has not been used in the accepting range
        for(int i = current_beat; i < total_beats; i++)
        {
            if ((beats[i].getUsed() == false) &&
                    (accepting_range.contains(beats[i].rectangle)))
            {
                beats[i].setUsed(true);
                current_beat = i+1;
                return true;
            }
        }
        return false;
    }
    private void createBeats() //Called in the constructor, creates the Beats array
    {
        beats = new Beat[total_beats];
        for(int i = 0; i < total_beats; i++)
        {
            beats[i] = new Beat(-40, top, 0, bottom, i*milliseconds_per_beat, milliseconds_per_beat);
        }
    }


    void run() //Runs the animation of all of the beats
    {
        for(int i = 0; i < total_beats; i++)
        {
            beats[i].Animate();
        }
    }



    //Constructor
    //Requires a beats per minute and song length in minutes
    BeatMap(int bpm, double songl)
    {
        beats_per_minute = bpm;
        song_length = songl;
        total_beats = (int) (beats_per_minute*song_length);
        milliseconds_per_beat = (long) 60000/beats_per_minute;
        current_beat = 0;
        createBeats();

    }




}
