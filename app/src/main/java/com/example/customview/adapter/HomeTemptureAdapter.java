package com.example.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.R;
import com.example.customview.bean.TempertureBean;

import java.util.List;

public class HomeTemptureAdapter extends RecyclerView.Adapter<HomeTemptureAdapter.HomeTemptureAdapterHolder>{
    List<TempertureBean> datas;
    Context context;
    TempOnItemClick tempOnItemClick;
    public HomeTemptureAdapter(List<TempertureBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeTemptureAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_tempertures_adapter_item, parent, false);
        return new HomeTemptureAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTemptureAdapterHolder holder, int position) {
        TempertureBean bean = datas.get(position);
        if(bean.isSelected()){
            holder.imageView.setImageResource(R.drawable.img_green_printer);
        }else {
            holder.imageView.setImageResource(R.drawable.img_white_printer);
        }
        holder.textView_time.setText(bean.getTime());
        holder.textView_store1.setText(bean.getTemp1());
        holder.textView_store2.setText(bean.getTemp2());
        holder.textView_store3.setText(bean.getTemp3());
        holder.textView_time.setTag(R.id.tag1,bean);
        holder.textView_time.setTag(R.id.tag2,position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setTempOnItemClick(TempOnItemClick tempOnItemClick) {
        this.tempOnItemClick = tempOnItemClick;
    }

    public void selectedAll(boolean isSelectedAll){
        if(isSelectedAll){
            for(TempertureBean bean:datas){
                bean.setSelected(true);
            }
        }else {
            for(TempertureBean bean:datas){
                bean.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
    public void setItemSelected(int position){
        notifyItemChanged(position);
    }
    protected class HomeTemptureAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView_time;
        private TextView textView_store1;
        private TextView textView_store2;
        private TextView textView_store3;
        public HomeTemptureAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_temperture);
            textView_time = itemView.findViewById(R.id.tv_temperture_time);
            textView_store1 = itemView.findViewById(R.id.tv_temperture_store1);
            textView_store2 = itemView.findViewById(R.id.tv_temperture_store2);
            textView_store3 = itemView.findViewById(R.id.tv_temperture_store3);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TempertureBean bean = (TempertureBean) textView_time.getTag(R.id.tag1);
                    int position = (int) textView_time.getTag(R.id.tag2);
                    if(bean.isSelected()){
                        bean.setSelected(false);
                    }else {
                        bean.setSelected(true);
                    }
                    setItemSelected(position);
                    tempOnItemClick.itemClick(position,bean);
                }
            });
        }
    }

    public interface TempOnItemClick{
        void itemClick(int position,TempertureBean bean);
    }
}
