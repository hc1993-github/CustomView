package com.example.customview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.R;
import com.example.customview.bean.DeviceTask;

import java.util.List;

public class HomeLeftAdapter extends RecyclerView.Adapter<HomeLeftAdapter.HomeLeftAdapterHolder> {
    List<DeviceTask> datas;
    Context context;
    LeftItemClick leftItemClick;

    public HomeLeftAdapter(List<DeviceTask> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeLeftAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_left_adapter_item, parent, false);
        return new HomeLeftAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeLeftAdapterHolder holder, int position) {
        DeviceTask task = datas.get(position);
        String taskName = task.getTaskName();
        String startTime = task.getTaskStartTime();
        String endTime = task.getTaskEndTime()==null?"":task.getTaskEndTime().substring(11,16);
        holder.taskName.setText(taskName);
        holder.taskTime.setText("("+startTime.substring(11,16)+" - "+endTime+")");
        holder.taskTime.setTag(R.id.tag1,position);
        holder.taskTime.setTag(R.id.tag2,task);
        if(datas.get(position).isSelected()){
            holder.imageView.setImageResource(R.drawable.img_green_printer);
        }else {
            holder.imageView.setImageResource(R.drawable.img_white_printer);
        }
        if(datas.get(position).isRed()){
            holder.taskName.setTextColor(Color.RED);
            holder.taskTime.setTextColor(Color.RED);
            holder.imageView.setClickable(false);
        }else {
            holder.imageView.setClickable(true);
            holder.taskName.setTextColor(Color.BLACK);
            holder.taskTime.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setSelected(int position){
        DeviceTask task = datas.get(position);
        if(task.isSelected()){
            task.setSelected(false);
        }else {
            task.setSelected(true);
        }
        notifyDataSetChanged();
    }
    public void setRed(int position){
        for(int i=0;i<datas.size();i++){
            datas.get(i).setRed(false);
            datas.get(i).setSelected(false);
        }
        datas.get(position).setRed(true);
        datas.get(position).setSelected(true);
        notifyDataSetChanged();
    }
    public void setAllSelected(){
        for(int i=0;i<datas.size();i++){
            if(datas.get(i).isSelected()){
                datas.get(i).setSelected(false);
            }else {
                datas.get(i).setSelected(true);
            }
            if(datas.get(i).isRed()){
                datas.get(i).setSelected(true);
            }
        }
        notifyDataSetChanged();
    }
    public void addNewTask(DeviceTask task){
        datas.add(task);
        notifyDataSetChanged();
    }
    public void updateTask(int position,String endTime){
        datas.get(position).setTaskEndTime(endTime);
        notifyDataSetChanged();
    }
    public void setLeftItemClick(LeftItemClick leftItemClick) {
        this.leftItemClick = leftItemClick;
    }

    protected class HomeLeftAdapterHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView taskName;
        private TextView taskTime;
        public HomeLeftAdapterHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.left_item_img);
            taskName = itemView.findViewById(R.id.left_item_taskName);
            taskTime = itemView.findViewById(R.id.left_item_taskTime);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) taskTime.getTag(R.id.tag1);
                    setSelected(position);
                }
            });
            taskName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) taskTime.getTag(R.id.tag1);
                    DeviceTask task = (DeviceTask) taskTime.getTag(R.id.tag2);
                    setRed(position);
                    leftItemClick.itemClick(position,task);
                }
            });
            taskTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) taskTime.getTag(R.id.tag1);
                    DeviceTask task = (DeviceTask) taskTime.getTag(R.id.tag2);
                    setRed(position);
                    leftItemClick.itemClick(position,task);
                }
            });
        }
    }
    public interface LeftItemClick{
        void itemClick(int position,DeviceTask task);
    }
}
