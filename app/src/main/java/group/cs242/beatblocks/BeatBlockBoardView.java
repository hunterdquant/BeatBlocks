package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by Hunter on 11/3/2015.
 */
public class BeatBlockBoardView extends View {

    /* Data members */

    // The game board.
    private BeatBlockBoard beatBlockBoard;
    // Status flag
    private boolean canPopulate = true;
    // Pause status flag.
    private boolean paused = false;
    // Pixel dimensions of the blocks and board.
    private int blockDimensions;
    private int dimensions;
    // An array of bitmaps. Their array index is matched with a value in beatBlockBoard.
    private Bitmap[] bitmaps = new Bitmap[6];


    /* Constructor */

    /**
     *  Constructor for object creation without xml.
     *
     * @param context - The current context.
     */
    public BeatBlockBoardView(Context context, int width, int height) {
        super(context);

        // The shorter of width and height/2 is the size.
        dimensions = width > height/2 ? height/2 : width;
        // 5 blocks per board.
        blockDimensions = dimensions/5;

        // Initialize the bitmaps.
        bitmaps[0] = getBitmap(R.mipmap.blackblock);
        bitmaps[1] = getBitmap(R.mipmap.yellowblock);
        bitmaps[2] = getBitmap(R.mipmap.purpleblock);
        bitmaps[3] = getBitmap(R.mipmap.greenblock);
        bitmaps[4] = getBitmap(R.mipmap.blueblock);
        bitmaps[5] = getBitmap(R.mipmap.redblock);

        // Initialize the board and populate it.
        beatBlockBoard = new BeatBlockBoard(5);
        beatBlockBoard.populate();
        beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());

        // Remove all matches before starting.
        while (beatBlockBoard.checkMatches().size() != 0) {
            beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());
            beatBlockBoard.populate();
        }
    }

    /**
     * Constructor used for defining via xml
     *
     * @param context - The current context
     * @param attrs - The set of attributes as defined by the xml layout activity_main
     */
    public BeatBlockBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Retrieve the xml defined attributes.
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BeatBlockBoardView,
                0, 0);
        int width, height;
        try {
            width = a.getInteger(R.styleable.BeatBlockBoardView_width_of_screen, 1080);
            height = a.getInteger(R.styleable.BeatBlockBoardView_height_of_screen, 1920);
        } finally {
            a.recycle();
        }

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

        while (beatBlockBoard.checkMatches().size() != 0) {
            beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());
            beatBlockBoard.populate();
        }
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

            // Handles the transition of blocks to blanks.
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

    /**
     * Defines the dimensions of the view.
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(dimensions, dimensions);
    }

    /* Public methods */

    /**
     * Moves the block at index i up
     *
     * @param i - An index to be moved
     */
    public void moveBlockUp(Index i) {
        beatBlockBoard.moveBlockUp(i);
    }

    /**
     * Moves the block at index i right
     *
     * @param i - An index to be moved
     */
    public void moveBlockRight(Index i) {
        beatBlockBoard.moveBlockRight(i);
    }

    /**
     * Moves the block at index i down
     *
     * @param i - An index to be moved
     */
    public void moveBlockDown(Index i) {
        beatBlockBoard.moveBlockDown(i);
    }

    /**
     * Moves the block at index i left
     *
     * @param i - An index to be moved
     */
    public void moveBlockLeft(Index i) {
        beatBlockBoard.moveBlockLeft(i);
    }

    /**
     * Toggles the pause status of the game.
     */
    public void togglePause() {
        paused = !paused;
    }

    /**
     * Gets the pause status of the game.
     *
     * @return True if it's paused else false
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets the block dimensions.
     *
     * @return The dimensions of a block
     */
    public int getBlockDimensions() {
        return blockDimensions;
    }

    /**
     * Sets the dimensions of the blocks and board. Has to be called
     * because you can not get the screen dimensions through xml.
     * Also reloads the bitmaps.
     *
     * @param width - the new width
     * @param height - the new height
     */
    public void setDimensions(int width, int height) {
        dimensions = width > height/2 ? height/2 : width;
        blockDimensions = dimensions/5;

        bitmaps[0] = getBitmap(R.mipmap.blackblock);
        bitmaps[1] = getBitmap(R.mipmap.yellowblock);
        bitmaps[2] = getBitmap(R.mipmap.purpleblock);
        bitmaps[3] = getBitmap(R.mipmap.greenblock);
        bitmaps[4] = getBitmap(R.mipmap.blueblock);
        bitmaps[5] = getBitmap(R.mipmap.redblock);
    }

    /* Private methods */

    /**
     *
     * @param imgId - the id og an image resource.
     * @return The loaded bitmap.
     */
    private Bitmap getBitmap(int imgId) {
        // Load and scale the bitmaps.
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), imgId), blockDimensions, blockDimensions, false);
    }


}
