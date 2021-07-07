package com.example.management.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.management.R;

/**
 * Created by wangminjian on 2018/12/23.
 */

public class AlertDialogTextUtil extends Dialog {

    private Context context;
    private TextView msg;
    private EditText etContent;
    private Button btnCancel;
    private Button btnSubmit;
    private String title;
    private int requestCode;
    private OnDialogClickListener listener;

    public AlertDialogTextUtil(Context context) {
        super(context);
        this.context = context;
    }

    public AlertDialogTextUtil(Context context, String title, int requestCode, OnDialogClickListener listener) {
        super(context);
        this.context = context;
        this.title = title;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setContentView(R.layout.dialog_layout_text);
        msg = findViewById(R.id.dialog_title);
        etContent = findViewById(R.id.dialog_content);
        btnCancel = findViewById(R.id.dialog_cancel);
        btnSubmit = findViewById(R.id.dialog_submit);

        msg.setText(title);
        btnSubmit.setText("确定");
        btnCancel.setText("取消");

        btnCancel.setOnClickListener(onClick);
        btnSubmit.setOnClickListener(onClick);
    }

    public interface OnDialogClickListener {
        void onDialogClick(int requestCode, boolean isPositive, String contentStr);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_submit:
                    // 确定按钮
                    String refuseStr = etContent.getText().toString();
                    listener.onDialogClick(requestCode, true, refuseStr);
                    break;
                case R.id.dialog_cancel:
                    dismiss();
                    // 取消按钮
                    listener.onDialogClick(requestCode, false, "");
                    break;
            }
        }
    };
}
