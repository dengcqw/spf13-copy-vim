package com.github.chenyoca.snippet.adapters;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by YooJia.Chen
 * YooJia.Chen@gmail.com
 * 2014-07-15
 */
public interface ViewCreator2<T> {

    public class ViewHolder {

        public final View parent;
        final SparseArray<View> children = new SparseArray<View>(10);

        public ViewHolder(View parent) {
            this.parent = parent;
        }

        public void hold(View...views){
            for (View v : views){
                int id = v.getId();
                if (id <= 0) throw new IllegalArgumentException("All hold view MUST has a view ID !!!");
                children.put(id, v);
            }
        }

        public void holdById(int...viewIds){
            for (int id : viewIds){
                children.put(id, parent.findViewById(id));
            }
        }

        public View getView(int viewId){
            return children.get(viewId);
        }

        public <E> E get (int viewId){
            return (E) getView(viewId);
        }
    }

    ViewHolder createHolder(LayoutInflater inflater, T data);

    void bind(ViewHolder holder, int position, T data);

}
