package group.cs242.beatblocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

/**
 * This activity provides the user with information about the game and how to play.
 *
 * @author Hunter Quant
 */
public class HelpActivity extends AppCompatActivity {

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
        View helpLayout = inflater.inflate(R.layout.help_activity, null);

        // Set button to switch back to the start activity.
        ImageButton playButton = (ImageButton) helpLayout.findViewById(R.id.backButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StartScreenActivity.class);
                startActivity(i);
                finish();
            }
        });

        setContentView(helpLayout);
    }
}
