package com.example.customview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.bean.TempertureBean;

import java.util.List;

public class TestAdapter extends BaseAdapter<TempertureBean>{
    public TestAdapter(List<TempertureBean> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public void bind(BaseHolder baseHolder,TempertureBean bean, int position) {
        ImageView imageView = baseHolder.getView(R.id.img_temperture);
        TextView textView = baseHolder.getView(R.id.tv_temperture_time);
        TextView textView1 = baseHolder.getView(R.id.tv_temperture_store1);
        TextView textView2 = baseHolder.getView(R.id.tv_temperture_store2);
        TextView textView3 = baseHolder.getView(R.id.tv_temperture_store3);
        textView.setText(bean.getTime());
        textView1.setText(bean.getTemp1());
        textView2.setText(bean.getTemp2());
        textView3.setText(bean.getTemp3());
        if(bean.isSelected()){
            imageView.setImageResource(R.drawable.img_green_printer);
        }else {
            imageView.setImageResource(R.drawable.img_white_printer);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.isSelected()){
                    bean.setSelected(false);
                }else {
                    bean.setSelected(true);
                }
                setItemSelected(position);
                onItemClickListen.onItemClick(bean,position);
            }
        });
    }

}
