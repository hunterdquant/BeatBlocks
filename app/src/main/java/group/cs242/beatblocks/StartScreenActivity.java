package group.cs242.beatblocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.zip.Inflater;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout.
        LayoutInflater inflater = LayoutInflater.from(this);
        View startLayout = inflater.inflate(R.layout.start_screen_activity, null);

        ImageButton playButton = (ImageButton) startLayout.findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(i);
            }
        });

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
