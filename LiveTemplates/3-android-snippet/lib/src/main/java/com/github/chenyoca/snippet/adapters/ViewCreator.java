package com.github.chenyoca.snippet.adapters;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by CHENYOCA
 * chenyoca@gmail.com
 * 2014-07-09
 */
public interface ViewCreator<T>{

    /**
     * Create a cell view
     * @param inflater Layout inflater
     * @param data Data for the cell view
     * @return A view
     */
    View createView(LayoutInflater inflater, T data);

    /**
     * Bind data to the view
     * @param view The view to be bind data
     * @param position Position of data
     * @param data Data for the cell view
     */
    void bind(View view, int position, T data);
}