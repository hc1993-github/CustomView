package com.example.customview.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.HomeActivity;
import com.example.customview.adapter.HomeBlueTeethAdapter;
import com.example.customview.adapter.HomeDialogAdapter;
import com.example.customview.bean.Bean;
import com.example.customview.bean.DeviceBean;

import java.util.List;

public class CustomDialog extends Dialog {
    private Builder builder;
    private Button leftBtn;
    private Button rightBtn;
    private RecyclerView recyclerView;
    List<? extends Bean> datas;
    HomeDialogAdapter adapter;
    HomeBlueTeethAdapter blueTeethAdapter;
    DialogOnItemClick dialogOnItemClick;
    int type;
    private CustomDialog(Builder builder) {
        super(builder.context);
        this.builder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                uiOptions |= 0x00001000;
                CustomDialog.this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
        setContentView(builder.layout);
        setCancelable(false);
        this.datas = builder.datas;
        this.type = builder.type;
        initViews();
    }

    private void initViews() {
        leftBtn = findViewById(builder.leftBtnId);
        if (leftBtn != null) {
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.listener.onLeftClick(CustomDialog.this);
                }
            });
        }

        rightBtn = findViewById(builder.rightBtnId);
        if (rightBtn != null) {
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.listener.onRightClick(CustomDialog.this);
                }
            });
        }
        recyclerView = findViewById(builder.recyclerViewId);
        if(recyclerView!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(builder.context,LinearLayoutManager.VERTICAL,false));
            if(type==1){
                adapter = new HomeDialogAdapter(datas,builder.context);
                adapter.setOnItemClick(new HomeDialogAdapter.OnItemClick() {
                    @Override
                    public void itemClick(String id, String name, int status) {
                        dialogOnItemClick.itemClick(id,name,status);
                    }
                });
                recyclerView.setAdapter(adapter);
            }else if(type==2){
                blueTeethAdapter = new HomeBlueTeethAdapter(datas,builder.context);
                blueTeethAdapter.setOnItemClick(new HomeBlueTeethAdapter.OnItemClick() {
                    @Override
                    public void itemClick(Bean bean) {
                        dialogOnItemClick.itemClick(bean.getId(),bean.getName(),1);
                    }
                });
                recyclerView.setAdapter(blueTeethAdapter);
            }
            setCancelable(true);
        }
    }
    public interface DialogOnItemClick{
        void itemClick(String id,String name,int status);
    }

    public void setDialogOnItemClick(DialogOnItemClick dialogOnItemClick) {
        this.dialogOnItemClick = dialogOnItemClick;
    }

    public void setData(List<Bean> datas){
        this.datas = datas;
        if(type==1){
            adapter.notifyDataSetChanged();
        }else if(type==2){
            blueTeethAdapter.notifyDataSetChanged();
        }
    }
    public static class Builder {
        public Context context;
        private String title;
        private int leftBtnId;
        private int rightBtnId;
        private int layout;
        private OnClickListener listener;
        private int recyclerViewId;
        public List<Bean> datas;
        private int type;
        public Builder setRecyclerViewId(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setDatas(List<Bean> datas) {
            this.datas = datas;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLeftBtnId(int leftBtnId) {
            this.leftBtnId = leftBtnId;
            return this;
        }

        public Builder setRightBtnId(int rightBtnId) {
            this.rightBtnId = rightBtnId;
            return this;
        }

        public Builder setLayout(int layout) {
            this.layout = layout;
            return this;
        }

        public Builder setListener(OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }

    public interface OnClickListener {
        void onLeftClick(Dialog dialog);

        void onRightClick(Dialog dialog);
    }
}
