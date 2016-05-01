// STEP 1: add permission: RECORD_AUDIO, WRITE_EXTERNAL_STORAGE.
/* STEP 2: Create some obj
private MediaRecorder myRecorder;
private MediaPlayer myPlayer;
private String outputFile = null;
----
outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/khoaphamvn.3gpp";
myRecorder = new MediaRecorder();
myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
myRecorder.setOutputFile(outputFile);
*/ 

// STEP 3:
// 3.1: start recording:
public void start(View view){
    try {
        myRecorder.prepare();
        myRecorder.start();
    } catch (IllegalStateException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    Toast.makeText(getApplicationContext(), "Start recording...",
            Toast.LENGTH_SHORT).show();
}

// 3.2: stop recording:
public void stop(View view){
    try {
        myRecorder.stop();
        myRecorder.release();
        myRecorder  = null;

        Toast.makeText(getApplicationContext(), "Stop recording...",
                Toast.LENGTH_SHORT).show();
    } catch (IllegalStateException e) {
        e.printStackTrace();
    } catch (RuntimeException e) {
        e.printStackTrace();
    }
}

// 3.3: play your new audio
public void play(View view) {
    try{
        myPlayer = new MediaPlayer();
        myPlayer.setDataSource(outputFile);
        myPlayer.prepare();
        myPlayer.start();

        Toast.makeText(getApplicationContext(), "Start play the recording...",Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// 3.4: stop playing your new audio:
public void stopPlay(View view) {
    try {
        if (myPlayer != null) {
            myPlayer.stop();
            myPlayer.release();
            myPlayer = null;

            Toast.makeText(getApplicationContext(), "Stop playing the recording...", Toast.LENGTH_SHORT).show();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
















/////////////////////////////////////////      VIEW MORE EXAMPLE     ////////////////////////////////////////////////////
public class AudioHandler implements OnCompletionListener, OnPreparedListener, OnErrorListener {
	private MediaRecorder recorder;
	private boolean isRecording = false;
	MediaPlayer mPlayer;
	private boolean isPlaying = false;
	private String recording;
	private String saveFile;
	private Context mCtx;
	
	public AudioHandler(String file, Context ctx) {
		this.recording = file;
		this.mCtx = ctx;
	}	
	
	protected void startRecording(String file){
		if (!isRecording){
			saveFile=file;
			recorder = new MediaRecorder();
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			recorder.setOutputFile(this.recording);
			try {
				recorder.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			isRecording = true;
			recorder.start();
		}
	}
	
	private void moveFile(String file) {
		/* this is a hack to save the file as the specified name */
		File f = new File (this.recording);
		f.renameTo(new File("/sdcard" + file));
	}
	
	protected void stopRecording(){
		try{
			if((recorder != null)&&(isRecording))
			{
				isRecording = false;
				recorder.stop();
		        recorder.release(); 
			}
			moveFile(saveFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	protected void startPlaying(String file) {
		if (isPlaying==false) {
			try {
				mPlayer = new MediaPlayer();
				isPlaying=true;
				Log.d("Audio startPlaying", "audio: " + file);
				if (isStreaming(file))
				{
					Log.d("AudioStartPlaying", "Streaming");
					// Streaming prepare async
					mPlayer.setDataSource(file);
					mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
					mPlayer.prepareAsync();
				} else {
					Log.d("AudioStartPlaying", "File");
					// Not streaming prepare synchronous, abstract base directory
					mPlayer.setDataSource("/sdcard/" + file);
					mPlayer.prepare();
				}
				mPlayer.setOnPreparedListener(this);
			} catch (Exception e) 
			{ 
				e.printStackTrace(); 
			}
		}
	} 

	public void stopPlaying() {
		if (isPlaying) {
			mPlayer.stop();
			mPlayer.release();
			isPlaying=false;
		}
	}
	
	public void onCompletion(MediaPlayer mPlayer) {
		mPlayer.stop();
		mPlayer.release();
		isPlaying=false;
    } 
	
	protected long getCurrentPosition() {
		if (isPlaying) 
		{
			return(mPlayer.getCurrentPosition());
		} else { return(-1); }
	}
	
	private boolean isStreaming(String file) 
	{
		if (file.contains("http://")) {
			return true;
		} else {
			return false;
		}
	}
	
	protected long getDuration(String file) {
		long duration = -2;
		if (!isPlaying & !isStreaming(file)) {
			try {
				mPlayer = new MediaPlayer();
				mPlayer.setDataSource("/sdcard/" + file);
				mPlayer.prepare();
				duration = mPlayer.getDuration();
				mPlayer.release();
			} catch (Exception e) { e.printStackTrace(); return(-3); }
		} else
		if (isPlaying & !isStreaming(file)) {
			duration = mPlayer.getDuration();
		} else 
		if (isPlaying & isStreaming(file)) {
			try {
				duration = mPlayer.getDuration();
			} catch (Exception e) { e.printStackTrace(); return(-4); }
		}else { return -1; }
		return duration;
	}

	public void onPrepared(MediaPlayer mPlayer) {
		if (isPlaying) {
			mPlayer.setOnCompletionListener(this);
			mPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener()
			{
				public void onBufferingUpdate(MediaPlayer mPlayer, int percent)
				{
					/* TODO: call back, e.g. update outer progress bar */
					Log.d("AudioOnBufferingUpdate", "percent: " + percent); 
				}
			});
			mPlayer.start();
		}
	}

	public boolean onError(MediaPlayer mPlayer, int arg1, int arg2) {
		Log.e("AUDIO onError", "error " + arg1 + " " + arg2);
		return false;
	}
	
	protected void setAudioOutputDevice(int output){
		// Changes the default audio output device to speaker or earpiece 
		AudioManager audiMgr = (AudioManager) mCtx.getSystemService(Context.AUDIO_SERVICE);
		if (output == (2))
			audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_SPEAKER, AudioManager.ROUTE_ALL);
		else if (output == (1)){
			audiMgr.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
		}else
			Log.e("AudioHandler setAudioOutputDevice", " unknown output device");	
	}
	
	protected int getAudioOutputDevice(){
		AudioManager audiMgr = (AudioManager) mCtx.getSystemService(Context.AUDIO_SERVICE);
		if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_EARPIECE)
			return 1;
		else if (audiMgr.getRouting(AudioManager.MODE_NORMAL) == AudioManager.ROUTE_SPEAKER)
			return 2;
		else
			return -1;
	}
}













//////////////////////////////////////////////////////////////////////////////////////////////////













public class SoundRecordingActivity extends Activity {

	MediaRecorder recorder;
	File audiofile = null;
	private static final String TAG = "SoundRecordingActivity";
	private View startButton;
	private View stopButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		startButton = findViewById(R.id.start);
		stopButton = findViewById(R.id.stop);
	}

	public void startRecording(View view) throws IOException {

		startButton.setEnabled(false);
		stopButton.setEnabled(true);

		File sampleDir = Environment.getExternalStorageDirectory();
		try {
			audiofile = File.createTempFile("sound", ".3gp", sampleDir);
		} catch (IOException e) {
			Log.e(TAG, "sdcard access error");
			return;
		}
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(audiofile.getAbsolutePath());
		recorder.prepare();
		recorder.start();
	}

	public void stopRecording(View view) {
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		recorder.stop();
		recorder.release();
		addRecordingToMediaLibrary();
	}

	protected void addRecordingToMediaLibrary() {
		ContentValues values = new ContentValues(4);
		long current = System.currentTimeMillis();
		values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
		values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
		values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
		values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
		ContentResolver contentResolver = getContentResolver();

		Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = contentResolver.insert(base, values);

		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
		Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
	}
}
