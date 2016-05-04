package com.ryker.monitor;

import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
 
public class AudioRecorder {
	private static final int DEFAULT_FREQUENCY = 44100;
	private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
	private AudioRecord audioRecord;
	private int recBufSize;
	private int rate = 10;
	private ArrayList<short[]> inBuf = new ArrayList<short[]>();
	private boolean isRunning = false;
	
	/**
	 * 
	 */
	public AudioRecorder(){
		recBufSize = AudioRecord.getMinBufferSize(DEFAULT_FREQUENCY,CHANNEL_CONFIG, AUDIO_ENCODING);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, DEFAULT_FREQUENCY,
				CHANNEL_CONFIG, AUDIO_ENCODING, recBufSize);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRate() {
		return rate;
	}
	
	/**
	 * 
	 * @param rate
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<short[]> getData(){
		return inBuf;
	}
	
	/**
	 * 
	 */
	public void record() {
		if(!isRunning){
			isRunning = true;
			new Thread(RecordTask).start();
		}
	}
	
	/**
	 * 
	 */
	public void destroy() {
		isRunning = false;
		inBuf.clear();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRunning(){
		return isRunning;
	}
	
	/**
	 * 
	 */
	private Runnable RecordTask=new Runnable(){
		@Override
		public void run() {
			try {
				short[] buffer = new short[recBufSize];
				audioRecord.startRecording();
				while (isRunning) {
					int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);
					short[] tmpBuf = new short[bufferReadResult / rate];
					for (int i = 0, ii = 0; i < tmpBuf.length; i++, ii = i * rate) {
						tmpBuf[i] = buffer[ii];
					}
					synchronized (inBuf) {
						inBuf.add(tmpBuf);
					}
				}
				audioRecord.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}