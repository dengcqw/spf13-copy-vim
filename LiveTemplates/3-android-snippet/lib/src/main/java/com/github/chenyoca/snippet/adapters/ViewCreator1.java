package com.github.chenyoca.snippet.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CHENYOCA
 * chenyoca@gmail.com
 * 2014-07-09
 * ViewCreator for Cursor adapter
 */
public interface ViewCreator1 {

    View newView(Context context, Cursor cursor, ViewGroup parent);

    void bindView(View view, Context context, Cursor cursor);
}
