package com.ryker.monitor.activity;

import com.ryker.monitor.AudioRecorder;
import com.ryker.monitor.DataDisplayView;
import com.ryker.monitor.R;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ZoomControls;
import android.app.Activity;

public class MainActivity extends Activity{
	private ZoomControls zc_x,zc_y;
	private AudioRecorder recorder;
	private DataDisplayView ddp;
	private LinearLayout layout;
	private boolean isMenuVisible=false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ddp = (DataDisplayView)findViewById(R.id.ddp);
        layout=(LinearLayout)findViewById(R.id.layout);
        zc_x=(ZoomControls)findViewById(R.id.zc_x);
        zc_y=(ZoomControls)findViewById(R.id.zc_y);
        recorder=new AudioRecorder();
        ddp.bindData(recorder.getData());
        recorder.record();
        ddp.show();
		ddp.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				ddp.setBaseLine((int)event.getY());
				switch(event.getAction()){
				case MotionEvent.ACTION_UP:
					handleMenu();
					break;
				}
				return true;
			}
		});
		zc_x.setOnZoomOutClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				recorder.setRate(recorder.getRate()+1);
				zc_x.setIsZoomInEnabled(true);
			}
        });
        zc_x.setOnZoomInClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				recorder.setRate(Math.max(1, recorder.getRate()-1));
				if(recorder.getRate()==1){
					zc_x.setIsZoomInEnabled(false);
				}
			}
        });
        zc_y.setOnZoomOutClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ddp.setRate(ddp.getRate()+1);
				zc_y.setIsZoomInEnabled(true);
			}
        });
        zc_y.setOnZoomInClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				ddp.setRate(Math.max(1, ddp.getRate()-1));
				if(ddp.getRate()==1){
					zc_y.setIsZoomInEnabled(false);
				}
			}
        });
    }
    
    private void handleMenu(){
    	if(isMenuVisible){
			layout.setVisibility(View.GONE);
			isMenuVisible=false;
		}else{
			layout.setVisibility(View.VISIBLE);
			isMenuVisible=true;
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if(keyCode==KeyEvent.KEYCODE_MENU){
    		handleMenu();
    		return false;
    	}else{
    		return super.onKeyDown(keyCode, event);
    	}
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(recorder.isRunning()){
			recorder.destroy();
		}
		if(ddp.isRunning()){
			ddp.destroy();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}