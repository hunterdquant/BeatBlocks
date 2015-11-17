package group.cs242.beatblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * @author Cameron Icso <icsoc@clarkson.edu> <icsoc22@gmail.com>
 *     defines the Drawing of the BeatMap
 */
public class BeatMapView extends View {
    private BeatMap bmap;
    private Paint paint1;
    private Paint paint2;


    //Constructor
    //Requires a context, a BeatMap and 2 colors
    //Color 1 defines the color of the accepting range
    //Color 2 defines the color of the beats
    public BeatMapView(Context context, BeatMap beatMap, int color1, int color2) {
        super(context);
        bmap = beatMap;
        paint1 = new Paint();
        paint2 = new Paint();
        paint1.setColor(color1);
        paint2.setColor(color2);
    }

    //Draws the accepting range and the beats
    @Override public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRect(bmap.accepting_range, paint1);
        for(int i = 0; i< bmap.total_beats; i++)
        {
            canvas.drawRect(bmap.beats[i].rectangle, paint2);
        }
        invalidate();
    }
}
