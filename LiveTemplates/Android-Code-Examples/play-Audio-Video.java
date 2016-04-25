package com.phonegap;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

public class PGPolyphonicVoice implements OnPreparedListener, OnCompletionListener {

	private static final int INVALID = 0;
	private static final int PREPARED = 1;
	private static final int PENDING_PLAY = 2;
	private static final int PLAYING = 3;
	private static final int PENDING_LOOP = 4;
	private static final int LOOPING = 5;
	
	private MediaPlayer mp;
	private int state;
	
	public PGPolyphonicVoice( AssetFileDescriptor afd )  throws IOException
	{
		state = INVALID;
		mp = new MediaPlayer();
		mp.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);  
		mp.prepare();
	}
	
	public void play() throws IOException
	{
		invokePlay( false );
	}
	
	private void invokePlay( Boolean loop )
	{
		Boolean playing = ( mp.isLooping() || mp.isPlaying() );
		if ( playing )
		{
			mp.pause();
			mp.setLooping(loop);
			mp.seekTo(0);
			mp.start();
		}
		if ( !playing && state == PREPARED )
		{
			state = PENDING_LOOP;
			onPrepared( mp );
		}
		else if ( !playing )
		{
			state = PENDING_LOOP;
			mp.setLooping(loop);
			mp.start();
		}
	}
	
	public void stop() throws IOException
	{
		if ( mp.isLooping() || mp.isPlaying() )
		{
			state = INVALID;
			mp.pause();
			mp.seekTo(0);
		}
	}
	
	public void loop() throws IOException
	{
		invokePlay( true );
	}
	
	public void unload() throws IOException
	{
		this.stop();
		mp.release();
	}
	
	public void onPrepared(MediaPlayer mPlayer) 
	{
		if (state == PENDING_PLAY) 
		{
			mp.setLooping(false);
			mp.seekTo(0);
			mp.start();
			state = PLAYING;
		}
		else if ( state == PENDING_LOOP )
		{
			mp.setLooping(true);
			mp.seekTo(0);
			mp.start();
			state = LOOPING;
		}
		else
		{
			state = PREPARED;
			mp.seekTo(0);
		}
	}
	
	public void onCompletion(MediaPlayer mPlayer)
	{
		if (state != LOOPING)
		{
			this.state = INVALID;
			try {
				this.stop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}








/////////////////////////////////////////////////////////////////////////////////////////////////////////////////





package com.android.mediaframeworktest.power;

import com.android.mediaframeworktest.MediaFrameworkTest;
import com.android.mediaframeworktest.MediaNames;
import android.media.MediaPlayer;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.io.File;

/**
 * Junit / Instrumentation test case for the power measurment the media player
 */
public class MediaPlayerPowerTest extends ActivityInstrumentationTestCase2<MediaFrameworkTest> {
    private String TAG = "MediaPlayerPowerTest";
    private String MP3_POWERTEST =
            Environment.getExternalStorageDirectory().toString() + "/power_sample_mp3.mp3";
    private String MP3_STREAM = "http://75.17.48.204:10088/power_media/power_sample_mp3.mp3";
    private String OGG_STREAM = "http://75.17.48.204:10088/power_media/power_sample_ogg.mp3";
    private String AAC_STREAM = "http://75.17.48.204:10088/power_media/power_sample_aac.mp3";

    public MediaPlayerPowerTest() {
        super("com.android.mediaframeworktest", MediaFrameworkTest.class);
    }

    protected void setUp() throws Exception {
        getActivity();
        super.setUp();

    }

    public void audioPlayback(String filePath) {
        try {
            MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(filePath);
            mp.prepare();
            mp.start();
            Thread.sleep(200000);
            mp.stop();
            mp.release();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            assertTrue("MP3 Playback", false);
        }
    }

    // A very simple test case which start the audio player.
    // Power measurment will be done in other application.
    public void testPowerLocalMP3Playback() throws Exception {
        audioPlayback(MP3_POWERTEST);
    }

    public void testPowerStreamMP3Playback() throws Exception {
        audioPlayback(MP3_STREAM);
    }

    public void testPowerStreamOGGPlayback() throws Exception {
        audioPlayback(OGG_STREAM);
    }

    public void testPowerStreamAACPlayback() throws Exception {
        audioPlayback(AAC_STREAM);
    }
}
