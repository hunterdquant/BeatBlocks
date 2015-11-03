package group.cs242.beatblocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private BeatBlockBoard beatBlockBoard;
    private BeatBlockBoardView beatBlockBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beatBlockBoard = new BeatBlockBoard();
        beatBlockBoardView = new BeatBlockBoardView(this, beatBlockBoard);
        beatBlockBoardView.setLayoutParams(
                new ViewGroup.LayoutParams(1080, 860));
        setContentView(beatBlockBoardView);
        //setContentView(R.layout.activity_main);
    }
}
