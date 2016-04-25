package com.github.chenyoca.snippet.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class InnerFragment extends Fragment {

	protected static final String KEY_LAYOUT_RES = "layout_res_id";
    protected static final int INVALID_LAYOUT_ID = -228441083;

	protected Activity activity;
	protected View contentView;

	private int layoutResourceId;

	/**
	 * 设置Fragment的布局文件资源ID
	 * @param layoutResId 布局文件资源ID
	 */
	public final void setLayoutResId (int layoutResId){
		Bundle args = new Bundle();
	    args.putInt(KEY_LAYOUT_RES, layoutResId);
	    setArguments(args);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layoutResourceId = getLayoutResId();
	}

	int getLayoutResId(){
		Bundle args = getArguments();
		if( args != null ){
			return args.getInt(KEY_LAYOUT_RES);
		}else{
			return INVALID_LAYOUT_ID;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if(layoutResourceId == INVALID_LAYOUT_ID) {
			throw new IllegalArgumentException("Illegal LayoutResourceId !!!");
		}
		contentView = inflater.inflate(layoutResourceId, null);
        onViewCreated(contentView);
		return contentView;
	}

    protected void onViewCreated(View contentView){}

}