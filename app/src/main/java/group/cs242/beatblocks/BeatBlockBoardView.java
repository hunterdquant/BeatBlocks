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

import java.util.List;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    /* Data members */

    // The game board.
    private BeatBlockBoard beatBlockBoard;
    private boolean canPopulate = true;

    // An array of bitmaps. Their array index is matched with a value in beatBlockBoard.
    private Bitmap[] bitmaps = {
                        getBitmap(R.mipmap.blackblock),
                        getBitmap(R.mipmap.yellowblock),
                        getBitmap(R.mipmap.purpleblock),
                        getBitmap(R.mipmap.greenblock),
                        getBitmap(R.mipmap.blueblock),
                        getBitmap(R.mipmap.redblock)};
    // View dimensions.
    private int width, height;

    /* Constructor */

    /**
     *
     * @param context - The current context.
     * @param bbb - A BeatBlockBoard object to handle board operations.
     */
    public BeatBlockBoardView(Context context, BeatBlockBoard bbb) {
        super(context);
        beatBlockBoard = bbb;
        beatBlockBoard.populate();
        beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());

    }

    /* Protected methods */

    /**
     * Draws all the bitmaps in a grid.
     *
     * @param canvas - canvas to draw elements upon.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < beatBlockBoard.getBoardSize(); i++) {
            for (int j = 0; j < beatBlockBoard.getBoardSize(); j++) {
                int bitMapVal = beatBlockBoard.getValAtIndex(new Index(i, j));
                canvas.drawBitmap(bitmaps[bitMapVal], 200*i, 200*j, null);
            }
        }

        if (canPopulate) {
            beatBlockBoard.populate();
            List<Index> matchList = beatBlockBoard.checkMatches();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (matchList.size() > 2) {
                beatBlockBoard.removeMatches(matchList);
                canPopulate = false;
            }
        } else {
            try {
                Thread.sleep(100);
                canPopulate = true;
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        // Used to refresh.
        invalidate();
    }

    /* Public methods */

    /**
     *
     * @return The width of the view
     */
    public int getViewWidth() {

        return width;
    }

    /**
     *
     * @return The height of the view.
     */
    public int getViewHeight() {

        return height;
    }

    /* Private methods */

    /**
     *
     * @param imgId - the id og an image resource.
     * @return The loaded bitmap.
     */
    private Bitmap getBitmap(int imgId) {
        return BitmapFactory.decodeResource(getResources(), imgId);
    }
}
