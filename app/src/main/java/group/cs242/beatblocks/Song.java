package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import java.io.IOException;

/**
 * Created by Cameron on 11/16/2015.
 */
public class Song implements MediaPlayer.OnPreparedListener {
    Context context;
    int beats_per_minute;
    long song_length;
    MediaPlayer player;

    //Constructor
    public Song(Context c, int bpm)
    {
        beats_per_minute = bpm;
        context = c;
        player = new MediaPlayer();
    }


    //Methods

    //Sets up and prepares the media player
    public void play()
    {
        try
        {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(R.raw.sadpast);
            player.setOnPreparedListener(this);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            player.setLooping(true);
            player.prepare();

        }
        catch (IOException e)
        {
            Log.d("Error", "in setDataSource method");
        }
    }

    //When the media player is prepared, the song will start
    @Override public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }
}
