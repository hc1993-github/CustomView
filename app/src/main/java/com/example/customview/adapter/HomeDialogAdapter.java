package com.example.customview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.R;
import com.example.customview.bean.DeviceBean;

import java.util.List;

public class HomeDialogAdapter extends RecyclerView.Adapter<HomeDialogAdapter.activitiy_home_dialog_adapter_holder>{
    List<? extends DeviceBean> datas;
    Context context;
    OnItemClick onItemClick;
    public HomeDialogAdapter(List<? extends DeviceBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public activitiy_home_dialog_adapter_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_dialog_adapter_item, parent, false);
        return new activitiy_home_dialog_adapter_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull activitiy_home_dialog_adapter_holder holder, int position) {
        holder.textView.setText(datas.get(position).getName());
        holder.textView.setTag(R.id.tag1,datas.get(position).getId());
        holder.textView.setTag(R.id.tag2,datas.get(position).getName());
        holder.textView.setTag(R.id.tag3,datas.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected class activitiy_home_dialog_adapter_holder extends RecyclerView.ViewHolder{
        private RelativeLayout relativeLayout;
        private TextView textView;
        public activitiy_home_dialog_adapter_holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.activitiy_home_dialog_adapter_item_tv);
            relativeLayout = itemView.findViewById(R.id.activitiy_home_dialog_adapter_item_relay);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) textView.getTag(R.id.tag1);
                    String name = (String) textView.getTag(R.id.tag2);
                    int status = (int) textView.getTag(R.id.tag3);
                    onItemClick.itemClick(id,name,status);
                }
            });
        }
    }
    public interface OnItemClick{
        void itemClick(String id,String name,int status);
    }
}
