package com.ryker.monitor;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;
 
public class DataDisplayView extends SurfaceView{
	private ArrayList<short[]> inBuf;
	private boolean isRunning = false;
	private int rate = 10;
	private int baseLine = 0;
	private Paint paint=new Paint();
	private int backgroundColor=Color.BLACK;
	private int baseLineColor=Color.WHITE;
	private int dataColor=Color.GREEN;
	
	/**
	 * 
	 * @param context
	 */
	public DataDisplayView(Context context) {
		super(context);
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public DataDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 
	 * @param inBuf
	 */
	public void bindData(ArrayList<short[]> inBuf) {
		this.inBuf=inBuf;
		this.baseLine = this.getLayoutParams().height/2;
		System.out.println("@@@@@@@@@@ "+baseLine);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
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
	public int getBaseLine() {
		return baseLine;
	}
	
	/**
	 * 
	 * @param baseLine
	 */
	public void setBaseLine(int baseLine) {
		this.baseLine = baseLine;
	}
	
	/**
	 * 
	 * @param paint
	 * @param backgroundColor
	 * @param baseLineColor
	 * @param dataColor
	 */
	public void setPaintAndColors(Paint paint, int backgroundColor, int baseLineColor, int dataColor){
		this.paint=paint;
		this.backgroundColor=backgroundColor;
		this.baseLineColor=baseLineColor;
		this.dataColor=dataColor;
	}
	
	/**
	 * 
	 */
	public void show() {
		if(!isRunning){
			isRunning = true;
			new Thread(DrawTask).start();
		}
	}
	
	/**
	 * 
	 */
	public void destroy() {
		isRunning = false;
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
	private Runnable DrawTask=new Runnable(){
		private int oldX = 0;
		private int oldY = 0;
		private int currentX = 0;
		@Override
		public void run() {
			while (isRunning) {
				ArrayList<short[]> buf = new ArrayList<short[]>();
				synchronized (inBuf) {
					if (inBuf.size() == 0){
						continue;
					}
					buf = (ArrayList<short[]>) inBuf.clone();
					inBuf.clear();
				}
				for (int i = 0; i < buf.size(); i++) {
					short[] tmpBuf = buf.get(i);
					drawData(currentX, tmpBuf);
					currentX = currentX + tmpBuf.length;
					if (currentX > getWidth()) {
						currentX = 0;
					}
				}
			}
		}
		/**
		 * 
		 */
		private void drawData(int start, short[] buffer) {
			if (start == 0){
				oldX = 0;
			}
			Canvas canvas = getHolder().lockCanvas(
					new Rect(start, 0, start + buffer.length, getHeight()));
			if(canvas==null){
				return;
			}
			canvas.drawColor(backgroundColor);
			paint.setColor(baseLineColor);
			canvas.drawLine(0, baseLine, getWidth(), baseLine, paint);
			paint.setColor(dataColor);
			int y;
			for (int i = 0; i < buffer.length; i++) {
				int x = i + start;
				y = buffer[i] / rate + baseLine;
				canvas.drawLine(oldX, oldY, x, y, paint);
				oldX = x;
				oldY = y;
			}
			getHolder().unlockCanvasAndPost(canvas);
		}
	};
}