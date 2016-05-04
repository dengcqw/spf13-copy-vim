package com.rincliu.example.animator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;

public class MainActivity extends Activity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View v = findViewById(R.id.iv);

        v.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //performViewPropertyAnim(v));
                performObjectAnim(new ViewWrapper(v));
                //performValueAnim(v);
            }
        });

    }
    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void performViewPropertyAnim(View v)
    {
        ViewPropertyAnimator vpa = v.animate();
        vpa.alpha(0);  
        vpa.rotationX(360);
        vpa.rotationY(360);
        vpa.translationXBy(500);  
        vpa.scaleX(1.5f);  
        vpa.scaleY(1.5f);
        vpa.setInterpolator(new BounceInterpolator());  
        vpa.setDuration(5 * 1000);
        vpa.setListener(listener);
        vpa.start();
    }

    private void performObjectAnim(ViewWrapper vw)
    {
        startObjAnim(ObjectAnimator.ofInt(vw, "width", 100, 1000));
        
        startObjAnim(ObjectAnimator.ofInt(vw, "height", 100, 1000));
        
        startObjAnim(ObjectAnimator.ofInt(vw, "backgroundColor", 0x99000000, 0x99ffffff));
        
        startObjAnim(ObjectAnimator.ofFloat(vw, "alpha", 0.5f, 1.0f));
        
        startObjAnim(ObjectAnimator.ofFloat(vw, "rotationX", 0, 360));
        
        startObjAnim(ObjectAnimator.ofFloat(vw, "rotationY", 0, 360));
        
        startObjAnim(ObjectAnimator.ofFloat(vw, "scaleX", 0.1f, 1.0f));
        
        startObjAnim(ObjectAnimator.ofFloat(vw, "scaleY", 0.1f, 1.0f));
    }
    
    private void startObjAnim(ObjectAnimator oa)
    {
        oa.setDuration(5 * 1000);
        oa.setRepeatCount(Integer.MAX_VALUE);
        oa.setRepeatMode(ObjectAnimator.REVERSE);
        oa.setInterpolator(new BounceInterpolator());
        oa.addListener(listener);
        oa.start();
    }
    
    private void performValueAnim(final View v)
    {
        ValueAnimator va = ValueAnimator.ofFloat(0.1f, 1.0f);
        //va.setTarget(v);
        
        va.addUpdateListener(new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float scale = (Float) animation.getAnimatedValue();
                v.setScaleX(scale);
                v.setScaleY(scale);
            }
        });
        
        va.setDuration(5 * 1000);
        va.setRepeatCount(Integer.MAX_VALUE);
        va.setRepeatMode(ObjectAnimator.REVERSE);
        va.setInterpolator(new BounceInterpolator());
        va.addListener(listener);
        va.start();
    }

    class ViewWrapper
    {
        private View v;

        ViewWrapper(View v)
        {
            this.v = v;
        }

        int getWidth()
        {
            return v.getLayoutParams().width;
        }

        void setWidth(int w)
        {
            v.getLayoutParams().width = w;
            v.requestLayout();
        }

        int getHeight()
        {
            return v.getLayoutParams().height;
        }

        void setHeight(int h)
        {
            v.getLayoutParams().height = h;
            v.requestLayout();
        }

        void setBackgroundColor(int color)
        {
            v.setBackgroundColor(color);
        }

        int getBackgroundColor()
        {
            ColorDrawable cd = (ColorDrawable) v.getBackground();
            return cd.getColor();
        }

        void setAlpha(float alpha)
        {
            v.setAlpha(alpha);
        }

        float getAlpha()
        {
            return v.getAlpha();
        }
        
        void setRotationX(float rotationX)
        {
            v.setRotationX(rotationX);
        }
        
        float getRotationX()
        {
            return v.getRotationX();
        }
        
        void setRotationY(float rotationY)
        {
            v.setRotationY(rotationY);
        }
        
        float getRotationY()
        {
            return v.getRotationY();
        }
        
        void setScaleX(float scaleX)
        {
            v.setScaleX(scaleX);
        }
        
        float getScaleX()
        {
            return v.getScaleX();
        }
        
        void setScaleY(float scaleY)
        {
            v.setScaleY(scaleY);
        }
        
        float getScaleY()
        {
            return v.getScaleY();
        }
    }
    
    private AnimatorListener listener = new AnimatorListener(){

        @Override
        public void onAnimationStart(Animator animation)
        {
            android.util.Log.d("@", "Animator Started...");
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            android.util.Log.d("@", "Animator Stopped...");
        }

        @Override
        public void onAnimationCancel(Animator animation)
        {
            android.util.Log.d("@", "Animator Canceled...");
        }

        @Override
        public void onAnimationRepeat(Animator animation)
        {
            android.util.Log.d("@", "Animator Repeated...");
        }
    };

}
