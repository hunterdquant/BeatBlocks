package group.cs242.beatblocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

/**
 * This activity is entered upon game over and gives the user the option to go back to the start screen.
 *
 * @author Hunter Quant
 */
public class GameOverActivity extends AppCompatActivity {

    /**
     * Called on activity creation.
     *
     * @param savedInstanceState - saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout.
        LayoutInflater inflater = LayoutInflater.from(this);
        View gameOverLayout = inflater.inflate(R.layout.game_over_activity, null);

        // Set button the switch to the game activity.
        ImageButton playButton = (ImageButton) gameOverLayout.findViewById(R.id.backButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StartScreenActivity.class);
                startActivity(i);
                finish();
            }
        });

        setContentView(gameOverLayout);
    }
}
