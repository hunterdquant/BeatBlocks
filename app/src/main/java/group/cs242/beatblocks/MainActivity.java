package group.cs242.beatblocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private BeatBlockBoard beatBlockBoard;
    private BeatBlockBoardView beatBlockBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beatBlockBoard = new BeatBlockBoard();
        beatBlockBoardView = new BeatBlockBoardView(this, beatBlockBoard);
        setContentView(beatBlockBoardView);
        //setContentView(R.layout.activity_main);
    }
}
