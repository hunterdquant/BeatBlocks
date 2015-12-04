package group.cs242.beatblocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

/**
 * This activity is the first screen you see when starting the application and is used to navigate between the game and help activities.
 *
 * @author Hunter Quant
 */
public class StartScreenActivity extends AppCompatActivity {

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
        View startLayout = inflater.inflate(R.layout.start_screen_activity, null);

        // Set button the switch to the game activity.
        ImageButton playButton = (ImageButton) startLayout.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
                finish();
            }
        });

        // Set button the switch to the help activity.
        ImageButton helpButton = (ImageButton) startLayout.findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(i);
            }
        });

        setContentView(startLayout);
    }
}
