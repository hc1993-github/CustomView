package com.example.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    List<T> datas;
    Context context;
    int layoutId;
    BaseHolder.OnItemClickListen<T> onItemClickListen;
    public BaseAdapter(List<T> datas, Context context, int layoutId) {
        this.datas = datas;
        this.context = context;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseHolder(view,onItemClickListen);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        T t = datas.get(position);
        bind(holder,t,position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setItemSelected(int position){
        notifyItemChanged(position);
    }

    public abstract void bind(BaseHolder holder,T t,int position);

    public void setOnItemClickListen(BaseHolder.OnItemClickListen<T> onItemClickListen) {
        this.onItemClickListen = onItemClickListen;
    }
}
