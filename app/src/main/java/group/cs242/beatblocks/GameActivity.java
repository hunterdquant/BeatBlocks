package group.cs242.beatblocks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The activity that is run to play the game.
 *
 * @author Hunter Quant
 * @author Cameron Icso
 */

public class GameActivity extends AppCompatActivity {

    /* data members */

    /**
     * The file name to store preferences to.
     */
    private static final String PREF_FILE_NAME = "PreferencesFile";

    /**
     * The board to be drawn to the screen.
     */
    private BeatBlockBoardView beatBlockBoardView;

    /**
     * Reference to the highscore TextView.
     */
    private TextView highScore;

    /**
     * Reference to the pause status image button.
     */
    private ImageButton imgButton;

    /**
     * The beat map to be drawn to the screen.
     */
    private BeatMapView beatMapView;

    /**
     * The song to be played.
     */
    private Song song;

    /**
     * The number of missed beats.
     */
    private volatile int missedBeats = 0;

    /* public methods */

    /**
     * Closes the game activity upon pressing the back button.
     *
     * @param keyCode - The value of the pressed key.
     * @param ke - The key event.
     * @return The value returned by the parent classes method.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent ke) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, ke);
    }

    /* protected methods */

    /**
     * Called on activity creation.
     *
     * @param savedInstanceState - saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Get the device dimensions.
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);

        // Load the song.
        song = new Song(getApplicationContext(), 45);

        // Inflate the layout.
        LayoutInflater inflater = LayoutInflater.from(this);
        View gameLayout = inflater.inflate(R.layout.game_activity, null);

        // Get stored highscore data.
        SharedPreferences preferences = getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        if (!preferences.contains("highScore")) {
            preferencesEditor.putString("highScore", "High Score: 0");
            preferencesEditor.commit();
        }
        // Set the displayed highscore.
        highScore = (TextView) gameLayout.findViewById(R.id.high_score);
        highScore.setText(preferences.getString("highScore", "High Score: 0"));

        final TextView misses = (TextView) gameLayout.findViewById(R.id.misses);

        // The misses text view needs to be threaded to update properly.
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(250);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                misses.setText("Misses: " + missedBeats);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        // Retrieve and setup the beatBlockBoardView.
        beatBlockBoardView = (BeatBlockBoardView) gameLayout.findViewById(R.id.beat_block_board_view);
        beatBlockBoardView.setOnTouchListener(new MoveGestureListener(this));
        beatBlockBoardView.setDimensions(p.x, p.y);
        beatBlockBoardView.setScore((TextView) gameLayout.findViewById(R.id.score));
        beatBlockBoardView.setHighScore(highScore);

        // Retrieve and setup the beatMapView.
        beatMapView = (BeatMapView) gameLayout.findViewById(R.id.beat_map_view);
        beatMapView.setUp(song);
        beatMapView.run();

        //Set the title
        TextView title = (TextView) gameLayout.findViewById(R.id.title);
        title.setText("Beat Blocks");

        // Retrieve, scale, and set the on click function of the image button.
        imgButton = (ImageButton) gameLayout.findViewById(R.id.pauseButton);
        imgButton.setScaleX(2);
        imgButton.setScaleY(2);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beatBlockBoardView.togglePause();
                beatMapView.togglePause();
                if (beatBlockBoardView.isPaused()) {
                    imgButton.setImageResource(R.mipmap.play);
                } else {
                    imgButton.setImageResource(R.mipmap.pause);
                }
            }
        });

        // Retrieve and scale the image.
        ImageView img = (ImageView) gameLayout.findViewById(R.id.gameIcon);
        img.setScaleX(2);
        img.setScaleY(2);

        // Set the content as the layout.
        setContentView(gameLayout);
    }

    /**
     * When leaving the application pause the game.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (!beatBlockBoardView.isPaused()) {
            beatBlockBoardView.togglePause();
            beatMapView.togglePause();
            imgButton.setImageResource(R.mipmap.play);
        }
    }

    /**
     * Open closing the application store the high score data.
     */
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("highScore", highScore.getText().toString());
        preferencesEditor.commit();
    }



    /**
     * Handles touch events for the game view.
     */
    class MoveGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

        /* data members */

        /**
         * The GestureDetector
         */
        private GestureDetector gd;

        /**
         * The context
         */
        Context context;

        /* constructors */

        /**
         * The default constructor for a MoveGestureListener.
         */
        public MoveGestureListener() {
            super();
        }

        /**
         * Constructs a MoveGestureListener with the current context.
         *
         * @param context - the context.
         */
        public MoveGestureListener(Context context) {
            this(context, null);
        }

        /**
         * Constructs a MoveGestureListener with the current context and GestureDetector.
         *
         * @param context - the context.
         * @param gd - the passed GestureDetector.
         */
        public MoveGestureListener(Context context, GestureDetector gd) {
            if (gd == null) {
                this.gd = new GestureDetector(context, this);
            }
            this.context = context;
        }

        /* public methods */

        /**
         *  Moves blocks according to the direction and magnitude of the fling event.
         *
         * @param e1 -  The initial event.
         * @param e2 - The ending event.
         * @param velX - x velocity
         * @param velY - y velocity
         * @return true.
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY) {
            if (beatBlockBoardView.isPaused()) {
                return false;
            }

            int blockSize = beatBlockBoardView.getBlockDimensions();
            // The index to move is the initial events x/y divided by the dimensions of the square.
            Index moveIndex = new Index((int)e1.getX()/blockSize, (int)e1.getY()/blockSize);

            // Thread for checking if moves are made while a beat is in the accepting region.
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!beatMapView.isGoodMove()) {
                        missedBeats++;
                    }
                    // If they have used all their misses end the activity.
                    if (missedBeats >= 3) {
                        Intent i = new Intent(getApplicationContext(), GameOverActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            });

            // Move in the appropriate direction if the user swiped a distance of 100 px.
            if (Math.abs(e1.getX() - e2.getX()) >= Math.abs(e1.getY() - e2.getY())) {
                if (e1.getX() - e2.getX() <= -blockSize / 2) {
                    t.start();
                    beatBlockBoardView.moveBlockRight(moveIndex);
                } else if (e1.getX() - e2.getX() >= blockSize / 2) {
                    t.start();
                    beatBlockBoardView.moveBlockLeft(moveIndex);
                }
            } else {
                if (e1.getY() - e2.getY() >= blockSize / 2) {
                    t.start();
                    beatBlockBoardView.moveBlockUp(moveIndex);
                } else if (e1.getY() - e2.getY() <= -blockSize / 2) {
                    t.start();
                    beatBlockBoardView.moveBlockDown(moveIndex);
                }
            }
            return true;
        }



        /**
         * Needs to return true because a fling is dependant on this event.
         *
         * @param e - An event.
         * @return true.
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * @param v - the attached view.
         * @param e - An event.
         * @return a boolean.
         */
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            return gd.onTouchEvent(e);
        }
    }
}
