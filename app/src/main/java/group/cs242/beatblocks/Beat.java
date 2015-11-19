package group.cs242.beatblocks;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *
 *  Represents an idividual Beat to be used in the BeatMap
 */
public class Beat implements ValueAnimator.AnimatorUpdateListener{
    //Data Members
    Rect rectangle;
    boolean used;    //represents if the beat has been used yet
    int beat_width;

    int cent;
    ValueAnimator centAnim;

    //Constructors
    Beat(int left, int top, int right, int bottom, long time_delay, long duration, int bw, int width)
    {
        rectangle = new Rect(left, top, right, bottom);
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
    public boolean getUsed()   //Returns if the beat has been used or not
    {
        return used;
    }

    public void setUsed(boolean u)   //Sets used to the given value
    {
        used = u;
    }

    public void update(int t, int b)
    {
        rectangle.top = t;
        rectangle.bottom = b;
    }

    @Override public void onAnimationUpdate(ValueAnimator animation)
    {
        rectangle.right = ((Integer) centAnim.getAnimatedValue()) + beat_width/2;
        rectangle.left = ((Integer) centAnim.getAnimatedValue()) - beat_width/2;

    }

    void Animate()
    {
        centAnim.start();
    }

}
