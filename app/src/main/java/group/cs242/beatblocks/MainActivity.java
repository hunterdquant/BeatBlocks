package group.cs242.beatblocks;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // The game board to be passed to the view
    private BeatBlockBoard beatBlockBoard;
    // The view to be drawn to the screen
    private BeatBlockBoardView beatBlockBoardView;
    // The GestureListener for detecting touch events.
    private MoveGestureListener mgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beatBlockBoard = new BeatBlockBoard(5);
        beatBlockBoardView = new BeatBlockBoardView(this, beatBlockBoard);
        // Attach the GestureListener to the game view.
        beatBlockBoardView.setOnTouchListener(new MoveGestureListener(this));
        setContentView(beatBlockBoardView);
        //setContentView(R.layout.activity_main);
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

            // The index to move is the initial events x/y divided by the dimensions of the square.
            Index moveIndex = new Index((int)e1.getX()/200, (int)e1.getY()/200);

            // Move in the appropriate direction if the user swiped a distance of 150 px.
            if (e1.getX() - e2.getX() <= -150) {
                beatBlockBoard.moveBlockRight(moveIndex);
            } else if (e1.getX() - e2.getX() >= 150) {
                beatBlockBoard.moveBlockLeft(moveIndex);
            } else if (e1.getY() - e2.getY() >= 150) {
                beatBlockBoard.moveBlockUp(moveIndex);
            } else if (e1.getY() - e2.getY() <- 150){
                beatBlockBoard.moveBlockDown(moveIndex);
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
