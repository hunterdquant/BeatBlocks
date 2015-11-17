package group.cs242.beatblocks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    /* Data members */

    // The game board.
    private BeatBlockBoard beatBlockBoard;
    private boolean canPopulate = true;
    private boolean paused = false;
    private int blockDimensions;
    private int dimensions;
    // An array of bitmaps. Their array index is matched with a value in beatBlockBoard.
    private Bitmap[] bitmaps = new Bitmap[6];


    /* Constructor */

    /**
     *
     * @param context - The current context.
     */
    public BeatBlockBoardView(Context context, int width, int height) {
        super(context);
        dimensions = width > height/2 ? height/2 : width;
        blockDimensions = dimensions/5;
        bitmaps[0] = getBitmap(R.mipmap.blackblock);
        bitmaps[1] = getBitmap(R.mipmap.yellowblock);
        bitmaps[2] = getBitmap(R.mipmap.purpleblock);
        bitmaps[3] = getBitmap(R.mipmap.greenblock);
        bitmaps[4] = getBitmap(R.mipmap.blueblock);
        bitmaps[5] = getBitmap(R.mipmap.redblock);

        beatBlockBoard = new BeatBlockBoard(5);
        beatBlockBoard.populate();
        beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());
    }

    public BeatBlockBoardView(Context context, AttributeSet as) {
        super(context, as);
        beatBlockBoard = new BeatBlockBoard(5);
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
        if (!paused) {
            for (int i = 0; i < beatBlockBoard.getBoardSize(); i++) {
                for (int j = 0; j < beatBlockBoard.getBoardSize(); j++) {
                    int bitMapVal = beatBlockBoard.getValAtIndex(new Index(i, j));
                    canvas.drawBitmap(bitmaps[bitMapVal], blockDimensions * i, blockDimensions * j, null);
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
        }
        // Used to refresh.
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(dimensions, dimensions);
    }

    public void moveBlockUp(Index i) {
        beatBlockBoard.moveBlockUp(i);
    }

    public void moveBlockRight(Index i) {
        beatBlockBoard.moveBlockRight(i);
    }

    public void moveBlockDown(Index i) {
        beatBlockBoard.moveBlockDown(i);
    }

    public void moveBlockLeft(Index i) {
        beatBlockBoard.moveBlockLeft(i);
    }

    public void togglePause() {
        paused = !paused;
    }

    /* Public methods */

    public boolean isPaused() {
        return paused;
    }

    public int getBlockDimensions() {
        return blockDimensions;
    }

    /* Private methods */

    /**
     *
     * @param imgId - the id og an image resource.
     * @return The loaded bitmap.
     */
    private Bitmap getBitmap(int imgId) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), imgId), blockDimensions, blockDimensions, false);
    }


}
