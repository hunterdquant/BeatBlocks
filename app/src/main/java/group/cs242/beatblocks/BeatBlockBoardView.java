package group.cs242.beatblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    private BeatBlockBoard beatBlockBoard;
    private Bitmap[] bitmaps = {
                        getBitmap(R.mipmap.blackblock),
                        getBitmap(R.mipmap.yellowblock),
                        getBitmap(R.mipmap.purpleblock),
                        getBitmap(R.mipmap.greenblock),
                        getBitmap(R.mipmap.blueblock),
                        getBitmap(R.mipmap.redblock)};
    private int width, height;


    public BeatBlockBoardView(Context context, BeatBlockBoard bbb) {
        super(context);
        beatBlockBoard = bbb;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < beatBlockBoard.getBoardSize(); i++) {
            for (int j = 0; j < beatBlockBoard.getBoardSize(); j++) {
                int bitMapVal = beatBlockBoard.getValAtIndex(new Index(i, j));
                canvas.drawBitmap(bitmaps[bitMapVal], 200*i, 200*j, null);
            }
        }
        invalidate();
    }



    public int getViewWidth() {

        return width;
    }

    public int getViewHeight() {

        return height;
    }

    private Bitmap getBitmap(int imgId) {
        return BitmapFactory.decodeResource(getResources(), imgId);
    }
}
