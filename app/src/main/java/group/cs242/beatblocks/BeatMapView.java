package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *     defines the Drawing of the BeatMap
 */
public class BeatMapView extends View {
    private BeatMap bmap;
    private Paint paintA;
    private Paint paintB;
    private Paint paintC = new Paint();
    private boolean paused = false;


    //Constructor
    //Requires a context, a BeatMap and 2 colors
    //Color 1 defines the color of the accepting range
    //Color 2 defines the color of the beats
    public BeatMapView(Context context, Song song, int color1, int color2) {
        super(context);
        bmap = new BeatMap(song);
        paintA = new Paint();
        paintB = new Paint();
        paintA.setColor(color1);
        paintB.setColor(color2);

    }

    public BeatMapView(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet,
                R.styleable.BeatMapView, 0, 0);
        int width, height, colorA, colorB;
        try {
            width = typedArray.getInteger(R.styleable.BeatMapView_width_screen, 1080);
            height = typedArray.getInteger(R.styleable.BeatMapView_height_screen, 1920);
            colorA = typedArray.getInteger(R.styleable.BeatMapView_accepting_color, Color.BLUE);
            colorB = typedArray.getInteger(R.styleable.BeatMapView_accepting_color, Color.YELLOW);

        }
        finally {
            typedArray.recycle();
        }

        bmap = new BeatMap(width, height);
        paintA = new Paint(colorA);
        paintB = new Paint(colorB);
    }

    public void setUp(Song song)
    {
        bmap.setUp(song);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        bmap.width = MeasureSpec.getSize(widthMeasureSpec);
        bmap.height = MeasureSpec.getSize(heightMeasureSpec);
        bmap.setUp();
        paintA.setColor(Color.GREEN);
        paintB.setColor(Color.BLACK);
        paintC.setColor(Color.BLACK);
        paintC.setStyle(Paint.Style.STROKE);
        paintC.setStrokeWidth(10);
    }

    //Draws the accepting range and the beats
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

    public void run()
    {
        bmap.run();
    }

    public void togglePause()
    {
     if(paused == false)
     {
         paused = true;
         bmap.song.player.pause();
         for(int i = 0; i < bmap.duration_constant; i++)
         {
             bmap.beats[i].centAnim.pause();
         }
     }

        else if (paused == true)
     {
        paused = false;
         bmap.song.player.start();
         for(int i = 0; i < bmap.duration_constant; i++)
         {
             bmap.beats[i].centAnim.resume();
         }
     }

    }

    public boolean isGoodMove()
    {
        return bmap.isGoodMove();
    }
}
