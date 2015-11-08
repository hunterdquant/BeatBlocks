package group.cs242.beatblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    private BeatBlockBoard beatBlockBoard;
    private int width, height;

    public BeatBlockBoardView(Context context, BeatBlockBoard bbb) {
        super(context);
        beatBlockBoard = bbb;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
    }

    public int getViewWidth() {

        return width;
    }

    public int getViewHeight() {

        return height;
    }
}
