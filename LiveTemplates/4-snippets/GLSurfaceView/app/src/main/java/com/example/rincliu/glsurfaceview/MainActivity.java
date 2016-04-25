package com.example.rincliu.glsurfaceview;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glsv = new MyGLSurfaceView(this);
        setContentView(glsv);
    }

    @Override
    public void onResume() {
        super.onResume();
        glsv.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        glsv.onPause();
    }

    class MyGLSurfaceView extends GLSurfaceView {

        private MyRenderer mRenderer;

        MyGLSurfaceView(Context context) {
            super(context);
            mRenderer = new MyRenderer();
            setRenderer(mRenderer);
        }

        @Override
        public boolean onTouchEvent(final MotionEvent event) {
            queueEvent(new Runnable() {
                public void run() {
                    mRenderer.refresh(getWidth(), getHeight(), event.getX(), event.getY());
                }
            });
            return true;
        }
    }

    class MyRenderer implements GLSurfaceView.Renderer {

        private float r = 1.0f, g = 1.0f, b = 1.0f;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int w, int h) {
            gl.glViewport(0, 0, w, h);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClearColor(r, g, b, 1.0f);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        }

        public void refresh(int W, int H, float x, float y) {
            r = x / W;
            g = y / H;
            b = (x * y) / (W * H);
        }
    }

}
