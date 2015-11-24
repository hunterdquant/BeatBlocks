package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.Set;

/**
 * A view object for displaying the gameboard.
 *
 * <p>
 *     Handles the drawing of the gameboard and updating score information from the gameboard.
 * </p>
 *
 * @author Hunter Quant
 */
public class BeatBlockBoardView extends View {

    /* data members */

    /**
     * The constant representing board size.
     */
    private final int BOARD_SIZE = 5;

    /**
     * The BeatBlockBoard to serve as the game backend.
     */
    private BeatBlockBoard beatBlockBoard;

    /**
     * A status flag for whether new blocks can be poplulated.
     */
    private boolean canPopulate = true;

    /**
     * A status flag for whether the game is paused for not
     */
    private boolean paused = false;

    /**
     * The dimensions in pixels of a block/board.
     */
    private int blockDimensions, dimensions;

    /**
     * An array for storing the block bitmap tiles.
     */
    private Bitmap[] bitmaps = new Bitmap[6];

    /**
     * References to the score views that belong the the parent activity.
     */
    private TextView score, highScore;


    /* constructors */

    /**
     * Constructor for object creation without xml.
     *
     * @param context - The context
     * @param width - the device width
     * @param height- the device height
     */
    public BeatBlockBoardView(Context context, int width, int height) {
        super(context);

        // The shorter of width and height/2 is the size.
        dimensions = width > height/2 ? height/2 : width;
        // 5 blocks per board.
        blockDimensions = dimensions/BOARD_SIZE;

        // Initialize the bitmaps.
        bitmaps[0] = getBitmap(R.mipmap.blackblock);
        bitmaps[1] = getBitmap(R.mipmap.yellowblock);
        bitmaps[2] = getBitmap(R.mipmap.purpleblock);
        bitmaps[3] = getBitmap(R.mipmap.greenblock);
        bitmaps[4] = getBitmap(R.mipmap.blueblock);
        bitmaps[5] = getBitmap(R.mipmap.redblock);

        // Initialize the board and populate it.
        beatBlockBoard = new BeatBlockBoard(BOARD_SIZE);
        beatBlockBoard.populate();
        beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());

        // Remove all matches before starting.
        while (beatBlockBoard.checkMatches().size() != 0) {
            beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());
            beatBlockBoard.populate();
        }

        // Listener for updating the score views when a combo is made.
        beatBlockBoard.setListener(new ScoreUpdateListener() {

            /**
             * Calls the score update function.
             *
             * @param points - The points to be added to the score.
             */
            @Override
            public void updateScore(final int points) {
                updateScores(points);
            }
        });
    }

    /**
     * Constructor used for defining via xml
     *
     * @param context - The current context
     * @param attrs - The set of attributes as defined by the xml layout game_activity
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

        // Set the dimensions to which ever is shorter between width and height/2.
        dimensions = width > height/2 ? height/2 : width;
        // The board
        blockDimensions = dimensions/BOARD_SIZE;

        bitmaps[0] = getBitmap(R.mipmap.blackblock);
        bitmaps[1] = getBitmap(R.mipmap.yellowblock);
        bitmaps[2] = getBitmap(R.mipmap.purpleblock);
        bitmaps[3] = getBitmap(R.mipmap.greenblock);
        bitmaps[4] = getBitmap(R.mipmap.blueblock);
        bitmaps[5] = getBitmap(R.mipmap.redblock);

        beatBlockBoard = new BeatBlockBoard(BOARD_SIZE);
        beatBlockBoard.populate();
        beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());

        // Remove all matches before starting.
        while (beatBlockBoard.checkMatches().size() != 0) {
            beatBlockBoard.removeMatches(beatBlockBoard.checkMatches());
            beatBlockBoard.populate();
        }

        // Listener for updating the score views when a combo is made.
        beatBlockBoard.setListener(new ScoreUpdateListener() {

            /**
             * Calls the score update function.
             *
             * @param points - The points to be added to the score.
             */
            @Override
            public void updateScore(final int points) {
                updateScores(points);
            }
        });
    }

    /* public methods */

    /**
     * Sets the score field to the passed parameter.
     *
     * @param score - A TextView to be updated.
     */
    public void setScore(TextView score) {
        this.score = score;
    }

    /**
     * Sets the highScore field to the passed parameter.
     *
     * @param highScore - A TextView to be updated.
     */
    public void setHighScore(TextView highScore) {
        this.highScore = highScore;
    }

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
     * Sets the dimensions of the blocks and board.
     * <p>
     *     Has to be called
     *     because you can not get the screen dimensions through xml.
     *     Also reloads the bitmaps.
     * </p>
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

    /* protected methods */

    /**
     * Draws all the bitmaps in a grid.
     *
     * <p>
     *     Draws all blocks on the screen in a BOARD_SIZE * BOARD_SIZE grid.
     *     If the game is paused the board is not drawn.
     * </p>
     *
     * @param canvas - canvas to draw elements upon.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw if the game is paused.
        if (!paused) {

            // Draw the grid of blocks.
            for (int i = 0; i < beatBlockBoard.getBoardSize(); i++) {
                for (int j = 0; j < beatBlockBoard.getBoardSize(); j++) {
                    int bitMapVal = beatBlockBoard.getValAtIndex(new Index(i, j));
                    canvas.drawBitmap(bitmaps[bitMapVal], blockDimensions * i, blockDimensions * j, null);
                }
            }

            // Handles the transition of blocks to blanks.
            if (canPopulate) {
                beatBlockBoard.populate();
                Set<Index> matchSet = beatBlockBoard.checkMatches();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                if (matchSet.size() > 2) {
                    beatBlockBoard.removeMatches(matchSet);
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
     * @param widthMeasureSpec - Device specified view width
     * @param heightMeasureSpec - Device specified view height
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(dimensions, dimensions);
    }

    /* private methods */

    /**
     * Updates the total score.
     *
     * <p>
     *     The score is calculated using the BeatBlockBoard function getPoints.
     *     The score text fields of the parent activity are then updated with the new score.
     * </p>
     *
     * @param additionalPoints - The amount of points to add to the score.
     */
    private void updateScores(int additionalPoints) {

        // Parse the current score text.
        String[] s = score.getText().toString().trim().split(" ");
        // Calculate the total points.
        int totalPoints = (Integer.parseInt(s[1]) + additionalPoints);
        CharSequence newScore = s[0] + " " + totalPoints;

        // Assign the new score. If the new score is greater than the highscore, update the highscore.
        String[] hs = highScore.getText().toString().trim().split(" ");
        if (totalPoints >= Integer.parseInt(hs[2])) {
            CharSequence newHighScore = hs[0] + " " + hs[1] + " " + totalPoints;
            highScore.setText(newHighScore);
        }
        score.setText(newScore);
    }

    /**
     *  Loads and scales the specified image.
     *
     * @param imgId - the id of an image resource.
     * @return The loaded bitmap.
     */
    private Bitmap getBitmap(int imgId) {
        // Load and scale the bitmaps.
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), imgId), blockDimensions, blockDimensions, false);
    }
}
