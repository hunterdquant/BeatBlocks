package group.cs242.beatblocks;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/**
 * @author Cameron Icso
 *
 *  Represents an idividual Beat to be used in the BeatMap
 */
public class Beat implements ValueAnimator.AnimatorUpdateListener{
    //Data Members
    /**
     * The rectangle that represents the position of the beat
     */
    Rect rectangle;
    /**
     * Represents if the beat has been used or not
     */
    boolean used;
    /**
     * The width of the beat
     */
    int beat_width;
    /**
     * The width of the view
     */
    int width;
    /**
     * The horizontal center of the beat
     */
    int cent;
    /**
     * The Animator of the beat. Animates the center value
     */
    ValueAnimator centAnim;

    //Constructors

    /**
     * Constructor, initializes the data members, and sets up the Animator
     *
     * @param left - the left edge of the beat
     * @param top -  the top edge of the beat
     * @param right - the right edge of the beat
     * @param bottom -  the bottom edge of the beat
     * @param time_delay - the time-delay for the animation in milliseconds
     * @param duration - the duration of the animation in milliseconds
     * @param bw - the width of the beat
     * @param w - the width of the view
     */
    Beat(int left, int top, int right, int bottom, long time_delay, long duration, int bw, int w)
    {
        rectangle = new Rect(left, top, right, bottom);
        width = w;
        beat_width = bw;
        used = false;
        cent = beat_width/2;
        centAnim = new ValueAnimator();
        centAnim.setIntValues(cent, width + beat_width/2);
        centAnim.setInterpolator(null);
        centAnim.setDuration(duration);
        centAnim.setRepeatMode(ValueAnimator.RESTART);
        centAnim.setRepeatCount(ValueAnimator.INFINITE);
        centAnim.setStartDelay(time_delay);
        centAnim.addUpdateListener(this);
    }

    //Methods

    /**
     *
     * @return if the beat has been used
     */
    public boolean getUsed()
    {
        return used;
    }

    /**
     * Sets the beat's used value
     *
     * @param u - a boolean value for if the beat should be used or not
     */
    public void setUsed(boolean u)
    {
        used = u;
    }

    /**
     * Updates the top and bottom of the rectangle
     *
     * @param t - top value
     * @param b - bottom value
     */
    public void update(int t, int b)
    {
        rectangle.top = t;
        rectangle.bottom = b;
    }

    /**
     * Updates the right and left values of the rectangle as the center value is animated
     * If the beat gets to the end and is used, the beat will be set to unused.
     * If the beat is unused, game will be over
     *
     * @param animation - The animation that will be used
     */
    @Override public void onAnimationUpdate(ValueAnimator animation)
    {
        rectangle.right = ((Integer) centAnim.getAnimatedValue()) + beat_width/2;
        rectangle.left = ((Integer) centAnim.getAnimatedValue()) - beat_width/2;

        if(rectangle.left == width)
        {
            if (used == true)
                used = false;
            else ;//lose
        }
    }

    /**
     * Starts the animator
     */
    void Animate()
    {
        centAnim.start();
    }

}
