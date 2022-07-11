package com.example.customview.adapter;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder extends RecyclerView.ViewHolder {
    OnItemClickListen onItemClickListen;
    private SparseArray<View> mViewSparseArray;
    public BaseHolder(@NonNull View itemView,OnItemClickListen onItemClickListen) {
        super(itemView);
        this.onItemClickListen = onItemClickListen;
    }
    public <V extends View> V getView(int viewId) {
        if (mViewSparseArray == null) {
            mViewSparseArray = new SparseArray<>();
        }
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (V) view;
    }
    public interface OnItemClickListen<T>{
        void onItemClick(T t,int position);
    }

}
