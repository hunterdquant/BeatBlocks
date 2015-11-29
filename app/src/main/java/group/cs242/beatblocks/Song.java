package group.cs242.beatblocks;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * @author Cameron Icso
 *
 * Contains information about the song to be played as well as housing the MediaPlayer
 */
public class Song implements MediaPlayer.OnPreparedListener {

    //Data Members
    /**
     * The context used when retrieving the Audio File
     */
    Context context;
    /**
     * Integer representing the Beats per Minute of the song
     */
    int beats_per_minute;
    /**
     * The MediaPlayer that is in charge of managing the song
     */
    MediaPlayer player;

    //Constructor

    /**
     * Constructor that initializes the data members
     *
     * @param c - the context of where the song will be retrieved from
     * @param bpm - beats per minute of the song to be played
     */
    public Song(Context c, int bpm)
    {
        beats_per_minute = bpm;
        context = c;
        player = new MediaPlayer();
    }


    //Methods

    /**
     * Attempts to set up and prepare the MediaPlayer from a given source file in the raw folder.
     *
     * If setting up the data source fails, will print an error to the log
     */
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
            Log.e("Error", "in setDataSource method");
        }
    }

    /**
     *
     * @param mp - the MediaPlayer that will be listened for
     *
     * Will be called automatically  if the MediaPlayer contained within the object is prepared
     *           successfully in the play() method
     */
    @Override public void onPrepared(MediaPlayer mp)
    {
        mp.start();
    }
}
