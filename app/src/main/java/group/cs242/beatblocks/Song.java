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

    public Song(Context c, int bpm, long length)
    {
        beats_per_minute = bpm;
        song_length = length;
        context = c;
        player = new MediaPlayer();
    }

    public void play()
    {
        try
        {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(R.raw.song);
            player.setOnPreparedListener(this);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            player.prepare();

        }
        catch (IOException e)
        {
            Log.d("I", "error in setDataSource method");
        }
    }

    @Override public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }
}
