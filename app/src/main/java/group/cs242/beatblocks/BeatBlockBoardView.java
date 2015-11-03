package group.cs242.beatblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    private BeatBlockBoard beatBlockBoard;

    public BeatBlockBoardView(Context context, BeatBlockBoard bbb) {
        super(context);
        beatBlockBoard = bbb;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
    }
}
