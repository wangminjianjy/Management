package com.example.management.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.management.R;

/**
 * Description:
 * Creator: wangminjian
 * Create time: 2018/4/10.
 */

public class AlertDialogUtil extends Dialog {

    private Context context;
    private ImageView img;
    private TextView msg;
    private Button btnCancel;
    private Button btnSubmit;
    private Button btnReturn;
    private String title;
    private int imgPath;
    private boolean isSingle = false;
    private int requestCode;
    private OnDialogClickListener listener;

    public AlertDialogUtil(Context context, String title, int imgPath, boolean singleType, int requestCode, OnDialogClickListener listener) {
        super(context);
        this.context = context;
        this.title = title;
        this.imgPath = imgPath;
        this.isSingle = singleType;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setContentView(R.layout.dialog_layout);
        img = findViewById(R.id.dialog_img);
        msg = findViewById(R.id.dialog_title);
        btnCancel = findViewById(R.id.dialog_cancel);
        btnSubmit = findViewById(R.id.dialog_submit);
        btnReturn = findViewById(R.id.dialog_return);

        changeBtnStatus();
        if (imgPath != 0) {
            img.setVisibility(View.VISIBLE);
            img.setImageResource(imgPath);
        }
        msg.setText(title);
        btnSubmit.setText("确定");
        btnCancel.setText("取消");
        btnReturn.setText("确定");

        btnCancel.setOnClickListener(onClick);
        btnSubmit.setOnClickListener(onClick);
        btnReturn.setOnClickListener(onClick);
    }

    public interface OnDialogClickListener {
        void onDialogClick(int requestCode, boolean isPositive);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_submit:
                    dismiss();
                    // 确定按钮
                    listener.onDialogClick(requestCode, true);
                    break;
                case R.id.dialog_cancel:
                    dismiss();
                    // 取消按钮
                    listener.onDialogClick(requestCode, false);
                    break;
                case R.id.dialog_return:
                    dismiss();
                    // 返回按钮
                    listener.onDialogClick(requestCode, true);
                    break;
            }
        }
    };

    private void changeBtnStatus() {
        if (isSingle) {
            btnCancel.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnReturn.setVisibility(View.GONE);
        }
    }
}
