package com.github.chenyoca.snippet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-9-13
 * 抽象Adapter类
 */
public class SimpleAdapter<T> extends BaseAdapter {

	protected List<T> dataSetReference;
    protected LayoutInflater layoutInflater;
	protected ViewCreator<T> viewCreator;

	/**
	 * 创建Adapter，需要给定View创建接口。
	 * @param inflater LayoutInflater，{@link ViewCreator}动态载入XML视图布局时，使用此引用。
	 * @param delegate Adapter Cell视图的构建过程，由此接口实现。
	 */
	public SimpleAdapter(LayoutInflater inflater, ViewCreator<T> delegate){
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

	/**
	 * 清空Adapter的数据集
	 */
	public void clear(){
		if(dataSetReference != null){
			dataSetReference.clear();
		}
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T data = dataSetReference.get(position);
        if(null == convertView){
            convertView = viewCreator.createView(layoutInflater,data);
        }
        viewCreator.bind(convertView, position, data);
        return convertView;
    }

	@Override
	public int getCount() {
		return null == dataSetReference ? 0 : dataSetReference.size();
	}

	@Override
	public T getItem(int position) {
		return null == dataSetReference ? null : dataSetReference.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}