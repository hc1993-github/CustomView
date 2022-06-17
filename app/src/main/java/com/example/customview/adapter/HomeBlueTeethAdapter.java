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

public class HomeBlueTeethAdapter extends RecyclerView.Adapter<HomeBlueTeethAdapter.HomeBlueTeethAdapterHolder>{
    List<? extends DeviceBean> datas;
    Context context;
    OnItemClick onItemClick;
    public HomeBlueTeethAdapter(List<? extends DeviceBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeBlueTeethAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_blueteeth_dialog_adapter_item, parent, false);
        return new HomeBlueTeethAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBlueTeethAdapterHolder holder, int position) {
        DeviceBean bean = datas.get(position);
        holder.textView_name.setText(bean.getId());
        holder.textView_address.setText(bean.getName());
        holder.textView_name.setTag(R.id.tag1,datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    protected class HomeBlueTeethAdapterHolder extends RecyclerView.ViewHolder{
        private TextView textView_name;
        private TextView textView_address;
        private RelativeLayout relativeLayout;
        public HomeBlueTeethAdapterHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.activitiy_home_blueteeth_dialog_adapter_item_tv_name);
            textView_address = itemView.findViewById(R.id.activitiy_home_blueteeth_dialog_adapter_item_tv_address);
            relativeLayout = itemView.findViewById(R.id.activitiy_home_blueteeth_dialog_adapter_item_relay);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeviceBean bean = (DeviceBean) textView_name.getTag(R.id.tag1);
                    onItemClick.itemClick(bean);
                }
            });
        }
    }
    public interface OnItemClick{
        void itemClick(DeviceBean bean);
    }
}
