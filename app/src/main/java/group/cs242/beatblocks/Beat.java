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

    int cent;
    ValueAnimator centAnim;

    //Constructors
    Beat(int left, int top, int right, int bottom, long time_delay, long mspb)
    {
        rectangle = new Rect(left, top, right, bottom);
        used = false;
        cent = (right+left)/2;
        centAnim = new ValueAnimator();
        centAnim.setIntValues(cent, 1100);
        centAnim.setInterpolator(null);
        centAnim.setDuration(10*mspb);
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

    @Override public void onAnimationUpdate(ValueAnimator animation)
    {
        rectangle.right = ((Integer) centAnim.getAnimatedValue()) + 20;
        rectangle.left = ((Integer) centAnim.getAnimatedValue()) - 20;

    }

    void Animate()
    {
        centAnim.start();
    }

}
