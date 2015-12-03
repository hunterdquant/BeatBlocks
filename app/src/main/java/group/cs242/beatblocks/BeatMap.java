package group.cs242.beatblocks;

import android.graphics.Rect;
import android.os.Handler;



/**
 * @author Cameron Icso
 *
 * Defines the specifications for the Beat Map displayed in game
 */
public class BeatMap{

    //Data Members
    /**
     * An array of the Beats that will be used
     */
    Beat beats[];
    /**
     * an unmoving Rectangle that represents when a move can be made
     */
    Rect accepting_range;
    /**
     * an unmoving Rectangle that will create a border around the Beats and Accepting Range
     */
    Rect border;
    /**
     * The top border of the Beats and Rectangles
     */
    int top;
    /**
     * The bottom border of the Beats and Rectangles
     */
    int bottom;
    /**
     * The width of each of the beats
     */
    int beat_width;
    /**
     * The width of the view where the BeatMap will be displayed
     */
    int width;
    /**
     * The height of the view where the BeatMap will be displayed
     */
    int height;
    /**
     * The milliseconds per beat of the song, used for Animation of the beats
     */
    long milliseconds_per_beat;
    /**
     * A constant for how long one beat will take to get from start to finish
     * Also represents the amount of beats that will be created
     */
    final int duration_constant = 4;
    /**
     * The Song associated with the BeatMap
     */
    Song song;
    /**
     * Used to delay the start of the song
     */
    Handler handler = new Handler();
    /**
     * In charge of starting the song
     */
    Runnable runner = new Runnable() {
        @Override
        public void run() {
            song.play();
        }
    };

    //Constructors

    /**
     * Constructor given a width and a height
     *
     * initializes the width, height, top, bottom, beat width, accepting range, and border
     *
     * @param w - the width of the view
     * @param h - the height of the view
     */
    BeatMap(int w, int h)
    {
        width = w;
        height = h;
        top = height-300;
        bottom = height;
        beat_width = (int) ((float)width/27);
        accepting_range = new Rect(width - 200, top, width+beat_width, bottom);
        border = new Rect(0, top, width, bottom);

    }

    //Methods

    /**
     * @return If a beat is within the accepting range and not used yet
     *
     * Will set the beat's used to true if that beat is used in the move
     */
    public boolean isGoodMove()
    {
        for(int i = 0; i < duration_constant; i++)
        {
            if ((beats[i].getUsed() == false) &&
                    (beats[i].rectangle.right>accepting_range.left))
            {
                beats[i].setUsed(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Called in a constructor with a Song or the setUp(Song) method
     * Creates the beats array and initializes each Beat
     */
    private void createBeats()
    {
        beats = new Beat[duration_constant];
        for(int i = 0; i < duration_constant; i++)
        {
            beats[i] = new Beat(-beat_width, top, 0, bottom, i*milliseconds_per_beat,
                    duration_constant * milliseconds_per_beat, beat_width, width);
        }
    }

    /**
     * Runs the animation of all of the beats as well as playing the song
     */
    void run()
    {

        for (int i = 0; i < duration_constant; i++) {
            beats[i].Animate();
        }

        handler.postDelayed(runner, (duration_constant - 1) * milliseconds_per_beat);


    }

    /**
     * Updates the top, bottom, accepting range, border, and beats
     */
    void setUp()
    {
        top = height-300;
        bottom = height;
        accepting_range.left = width - 150;
        accepting_range.top= top;
        accepting_range.right = width;
        accepting_range.bottom = bottom;
        border.bottom = bottom;
        border.top = top;
        border.right = width;
        for (int i = 0; i < duration_constant; i++) {
            beats[i].update(top, bottom);
            beats[i].centAnim.setIntValues(beats[i].cent, width + beat_width/2);
        }
    }


    /**
     * @param sng - the song to be used in the BeatMap
     *
     * Initializes the song, milliseconds per beat, and creates the beat array
     */
    void setUp(Song sng)
    {
        song = sng;
        milliseconds_per_beat = (long) 60000 / song.beats_per_minute;
        createBeats();
}





}