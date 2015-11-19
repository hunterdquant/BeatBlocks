package group.cs242.beatblocks;

import android.graphics.Rect;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *
 * Defines the specifications for the Beat Map displayed in game
 */
public class BeatMap{

    //Data Members
    Beat beats[];    //contains all of the beats that will be used throughout a game
    Rect accepting_range;  //an unmoving Rectangle the will represent when a move can be made
    int top;
    int bottom;
    int beat_width;
    int width;
    int height;
    int total_beats; //to be used in loops
    long milliseconds_per_beat;
    int current_beat;   //should help speed up isGoodMove a little
    final int duration_constant = 10;
    Song song;

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
            beats[i] = new Beat(-beat_width, top, 0, bottom, i*milliseconds_per_beat,
                    duration_constant * milliseconds_per_beat, beat_width, width);
        }
    }


    void run() //Runs the animation of all of the beats
    {
        song.play();
        for (int i = 0; i < total_beats; i++) {
            beats[i].Animate();
        }

    }

    void setUp()
    {
        top = height-300;
        bottom = height;
        accepting_range.left = width - 200;
        accepting_range.top= top;
        accepting_range.right = width;
        accepting_range.bottom = bottom;
        for (int i = 0; i < total_beats; i++) {
            beats[i].update(top, bottom);
        }
    }


    
    void setUp(Song sng)
    {
        song = sng;
        total_beats = (int) (song.beats_per_minute * song.song_length);
        milliseconds_per_beat = (long) 60000 / song.beats_per_minute;
        current_beat = 0;
        createBeats();
}

    //Constructor

    BeatMap(int w, int h)
    {
        width = w;
        height = h;
        top = height-300;
        bottom = height;
        beat_width = (int) ((float)width/27);
        accepting_range = new Rect(width - 200, top, width, bottom);

    }


    //Requires a beats per minute and song length in minutes
    BeatMap(Song sng)
    {
        song = sng;
        total_beats = (int) (song.beats_per_minute * song.song_length);
        milliseconds_per_beat = (long) 60000 / song.beats_per_minute;
        current_beat = 0;
        createBeats();


    }




}