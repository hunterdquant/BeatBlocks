package group.cs242.beatblocks;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // The view to be drawn to the screen
    private BeatBlockBoardView beatBlockBoardView;
    // The GestureListener for detecting touch events.
    private MoveGestureListener mgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        beatBlockBoardView = new BeatBlockBoardView(this);
        // Attach the GestureListener to the game view.
        beatBlockBoardView.setOnTouchListener(new MoveGestureListener(this));
        setContentView(beatBlockBoardView/*R.layout.activity_main*/);

        ActionBar bar = getSupportActionBar();
        bar.hide();

        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.beat_blocks_action_bar, null);
        TextView barTitle = (TextView) view.findViewById(R.id.title);
        barTitle.setText("Beat Blocks");
        ImageButton imgButton = (ImageButton) view.findViewById(R.id.pauseButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beatBlockBoardView.togglePause();
            }
        });
        bar.setCustomView(view);
        bar.setDisplayShowCustomEnabled(true);
        bar.show();

    }

    /**
     * Handles touch events for the game view.
     */
    class MoveGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

        /* Data members */

        private GestureDetector gd;
        Context context;

        /* Constructors */

        public MoveGestureListener() {
            super();
        }

        public MoveGestureListener(Context context) {
            this(context, null);
        }

        public MoveGestureListener(Context context, GestureDetector gd) {
            if (gd == null) {
                this.gd = new GestureDetector(context, this);
            }
            this.context = context;
        }

        /* public methods */

        /**
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
            // The index to move is the initial events x/y divided by the dimensions of the square.
            Index moveIndex = new Index((int)e1.getX()/200, (int)e1.getY()/200);
            // Move in the appropriate direction if the user swiped a distance of 100 px.
            if (Math.abs(e1.getX() - e2.getX()) >= Math.abs(e1.getY() - e2.getY())) {
                if (e1.getX() - e2.getX() <= -100) {
                    beatBlockBoardView.moveBlockRight(moveIndex);
                } else if (e1.getX() - e2.getX() >= 100) {
                    beatBlockBoardView.moveBlockLeft(moveIndex);
                }
            } else {
                if (e1.getY() - e2.getY() >= 100) {
                    beatBlockBoardView.moveBlockUp(moveIndex);
                } else if (e1.getY() - e2.getY() <= -100){
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
         *
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
