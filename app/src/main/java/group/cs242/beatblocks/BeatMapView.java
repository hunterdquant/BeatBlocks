package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Cameron Icso
 *     defines the Drawing of the BeatMap
 */
public class BeatMapView extends View {
    //Data Members
    /**
     * The BeatMap that will be displayed
     */
    private BeatMap bmap;
    /**
     * Describes how the Accepting Range will be drawn
     */
    private Paint paintA =  new Paint();
    /**
     * Describes how the Beats will be drawn
     */
    private Paint paintB = new Paint();
    /**
     * Describes how the Border will be drawn
     */
    private Paint paintC = new Paint();
    /**
     * Whether or not the BeatMap should be paused
     */
    private boolean paused = false;
    /**
     * Whether or not the song thread has to be resumed
     */
    private boolean need_resumed = false;




    /**
     * Constructor used for defining via xml
     *
     * @param context - The current context
     * @param attributeSet - The set of attributes as defined by the xml layout game_activity
     */
    public BeatMapView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.BeatMapView, 0, 0);
        int width, height;
        try {
            width = typedArray.getInteger(R.styleable.BeatMapView_width_screen, 1080);
            height = typedArray.getInteger(R.styleable.BeatMapView_height_screen, 1920);
        }
        finally {
            typedArray.recycle();
        }

        bmap = new BeatMap(width, height);
        paintA.setColor(Color.GREEN);
        paintB.setColor(Color.WHITE);
        paintC.setColor(Color.BLACK);
        paintC.setStyle(Paint.Style.STROKE);
        paintC.setStrokeWidth(10);
    }

    /**
     * Calls the setUp(Song) method in the BeatMap
     *
     * @param song- The song to be used
     */
    public void setUp(Song song)
    {
        bmap.setUp(song);
    }


    /**
     * Updates the BeatMap based on the measured width and height of the view
     *
     * @param widthMeasureSpec - the measured width of the view
     * @param heightMeasureSpec - the measured height of the view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        bmap.width = MeasureSpec.getSize(widthMeasureSpec);
        bmap.height = MeasureSpec.getSize(heightMeasureSpec);
        bmap.setUp();
    }

    /**
     * Draws the BeatMap to the canvas if the game isnt paused
     *
     * @param canvas - The canvas to be drawn to
     */
    @Override public void onDraw(Canvas canvas)
    {
        if(!paused) {
            super.onDraw(canvas);
            canvas.drawRect(bmap.accepting_range, paintA);
            canvas.drawRect(bmap.border, paintC);
            for (int i = 0; i < bmap.duration_constant; i++) {
                canvas.drawRect(bmap.beats[i].rectangle, paintB);
            }

        }
        invalidate();
    }

    /**
     * Calls the run method in the BeatMap
     */
    public void run()
    {
        bmap.run();
    }

    /**
     * Pauses or unpauses the song and animations
     */
    public void togglePause()
    {
     if(paused == false)
     {
         paused = true;
         if(!bmap.song.player.isPlaying())
         {
             bmap.handler.removeCallbacks(bmap.runner);
            need_resumed = true;
         }
         else bmap.song.player.pause();
         for(int i = 0; i < bmap.duration_constant; i++)
         {
             bmap.beats[i].centAnim.pause();
         }
     }

        else if (paused == true)
     {
        paused = false;
         if(need_resumed)
         {
             long resume_delay = (bmap.duration_constant - 1)*bmap.milliseconds_per_beat*
                     (((bmap.accepting_range.right+bmap.accepting_range.left)/2)-((bmap.beats[0].rectangle.right + bmap.beats[0].rectangle.left)/2))
                     /(bmap.width+bmap.beat_width/2);
             bmap.handler.postDelayed(bmap.runner, resume_delay);
             need_resumed = false;
         }
         else bmap.song.player.start();
         for(int i = 0; i < bmap.duration_constant; i++)
         {
             bmap.beats[i].centAnim.resume();
         }
     }

    }

    /**
     * Calls the BeatMap's isGoodMove() method
     *
     * @return - whether the move is good or not
     */
    public boolean isGoodMove()
    {
        return bmap.isGoodMove();
    }
}
