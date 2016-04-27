package com.example.rincliu.textureview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SurfaceView sv = (SurfaceView) findViewById(R.id.sv);
//        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
//
//            @Override
//            public void surfaceCreated(final SurfaceHolder surfaceHolder) {
//                initCamera();
//                try {
//                    camera.setPreviewDisplay(surfaceHolder);
//                } catch (IOException e) {}
//                camera.startPreview();
//
//                //A SurfaceView can only have one producer;
//                //If you have used it to preview the Camera, then you cannot lock a canvas to draw something.
//                /*
//                new Thread() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            Canvas canvas = surfaceHolder.lockCanvas();
//                            if (canvas != null) {
//                                draw(canvas);
//                                surfaceHolder.unlockCanvasAndPost(canvas);
//                            }
//                            try {
//                                Thread.sleep(100);
//                            } catch (Exception e) {
//                            }
//                        }
//                    }
//                }.start();
//                */
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//                releaseCamera();
//            }
//        });

        final TextureView tv = (TextureView) findViewById(R.id.tv);
        tv.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            //A SurfaceTexture is available only after the TextureView is attached to a window!
            @Override
            public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, int i, int i1) {
                initCamera();
                try {
                    camera.setPreviewTexture(surfaceTexture);
                } catch (IOException e) {}
                camera.startPreview();

                //A TextureView can only have one producer;
                //If you have used it to preview the Camera, then you cannot lock a canvas to draw something.
                /*
                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            Canvas canvas = tv.lockCanvas();
                            if (canvas != null) {
                                draw(canvas);
                                tv.unlockCanvasAndPost(canvas);
                            }
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                        }
                    }
                }.start();
                */
            }

            //Invoked when the SurfaceTexture's buffers size changed.
            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                //Ignored, Camera does all the work for us.
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                releaseCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                //Invoked every time there's a new Camera preview frame.
            }
        });
    }

    private void initCamera() {
        if (camera == null) {
            camera = Camera.open();
        }
    }

    private void releaseCamera() {
        if(camera != null) {
            camera.stopPreview();
            camera.release();
        }
    }

//    private void draw(Canvas canvas) {
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setTextSize(50);
//        canvas.drawText("" + System.currentTimeMillis(), 100, 100, paint);
//        canvas.drawCircle(500, 500, 100, paint);
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        if(bmp != null && !bmp.isRecycled()) {
//            canvas.drawBitmap(bmp ,200, 200, paint);
//        }
//    }

}
