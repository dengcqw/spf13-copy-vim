package com.example.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

	SurfaceHolder surfaceHolder;
	int r=10;
	public MySurfaceView(Context context) {
		super(context);
		surfaceHolder=this.getHolder();
		surfaceHolder.addCallback(this);
		this.setFocusable(true);
	}

	@Override
	public void run() {
		while(true){
			Canvas canvas=surfaceHolder.lockCanvas();
			if(surfaceHolder==null || canvas==null){
				return ;
			}
			Paint paint=new Paint();
			paint.setColor(Color.BLUE);
			canvas.drawCircle((320+r)/2, (480+r)/2, r, paint);
			surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    new Thread(this).start();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		// TODO Auto-generated method stub
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}
}