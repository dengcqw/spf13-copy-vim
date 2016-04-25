package com.example.mysurfaceview;

import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	MySurfaceView mySurfaceView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mySurfaceView=new MySurfaceView(this);
		setContentView(mySurfaceView);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mySurfaceView.r+=2;
		return super.onTouchEvent(event);
	}
}