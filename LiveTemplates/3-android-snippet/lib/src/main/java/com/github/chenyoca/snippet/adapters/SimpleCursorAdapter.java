package com.github.chenyoca.snippet.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-10-15
 * 游标适配器
 */
public class SimpleCursorAdapter extends CursorAdapter {
	
	private final ViewCreator1 viewCreator;

	public SimpleCursorAdapter(Context context, Cursor c, boolean autoReQuery, ViewCreator1 viewCreator) {
		super(context, c, autoReQuery);
		this.viewCreator = viewCreator;
	}
	
	public SimpleCursorAdapter(Context context, Cursor c, ViewCreator1 viewCreator) {
		this(context,c,true, viewCreator);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return viewCreator.newView(context, cursor, parent);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		if(cursor == null) return;
		viewCreator.bindView(view, context, cursor);
	}
	
}
