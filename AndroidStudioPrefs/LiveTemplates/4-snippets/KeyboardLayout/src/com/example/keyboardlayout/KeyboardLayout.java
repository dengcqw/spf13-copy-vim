package com.example.keyboardlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class KeyboardLayout extends LinearLayout
{
    public KeyboardLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public KeyboardLayout(Context context)
    {
        super(context);
    }

    private OnKeyboardListener onKeyboardListener;

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        if (onKeyboardListener != null)
        {
            final int newSpec = MeasureSpec.getSize(heightMeasureSpec);
            final int oldSpec = getMeasuredHeight();
            if (oldSpec > newSpec)
            {
                onKeyboardListener.onShown();
            }
            else
            {
                onKeyboardListener.onHidden();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public final void setOnKeyboardListener(final OnKeyboardListener onKeyboardListener)
    {
        this.onKeyboardListener = onKeyboardListener;
    }

    public interface OnKeyboardListener
    {
        public void onShown();

        public void onHidden();
    }
}
