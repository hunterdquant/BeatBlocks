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

    private BeatBlockBoard beatBlockBoard;
    private BeatBlockBoardView beatBlockBoardView;
    private MoveGestureListener mgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beatBlockBoard = new BeatBlockBoard(5);
        beatBlockBoardView = new BeatBlockBoardView(this, beatBlockBoard);
        beatBlockBoardView.setOnTouchListener(new MoveGestureListener(this));
        setContentView(beatBlockBoardView);
        //setContentView(R.layout.activity_main);
    }

    class MoveGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

        GestureDetector gd;
        Context context;

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

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY) {

            Index moveIndex = new Index((int)e1.getX()/200, (int)e1.getY()/200);
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

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            return gd.onTouchEvent(e);
        }
    }
}
