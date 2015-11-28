package group.cs242.beatblocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout.
        LayoutInflater inflater = LayoutInflater.from(this);
        View helpLayout = inflater.inflate(R.layout.help_activity, null);

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
