package com.github.chenyoca.snippet.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-9-13
 * PageView Adapter类
 */
public class SimplePagerAdapter<T> extends PagerAdapter {

	protected List<T> dataSetReference;
    protected LayoutInflater layoutInflater;
	protected ViewCreator<T> viewCreator;

	/**
	 * 创建Adapter，需要给定View创建接口。
	 * @param inflater LayoutInflater，{@link ViewCreator}动态载入XML视图布局时，使用此引用。
	 * @param delegate Adapter Cell视图的构建过程，由此接口实现。
	 */
	public SimplePagerAdapter(LayoutInflater inflater, ViewCreator<T> delegate){
		this.layoutInflater = inflater;
		this.viewCreator = delegate;
	}

	/**
	 * 更新Adapter的数据集。
	 * @param data 数据集
	 */
	public void update(List<T> data){
		dataSetReference = data;
	}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        T data = dataSetReference.get(position);
        View view = viewCreator.createView(layoutInflater, data);
        viewCreator.bind(view, position, data);
        container.addView(view);
        return view;
    }

    @Override
	public int getCount() {
		return null == dataSetReference ? 0 : dataSetReference.size();
	}

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

}