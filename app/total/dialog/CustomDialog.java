package com.han.total.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.han.total.R;

import java.util.Objects;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView tv_title0,tv_title1,tv_title2;
    private Context mContext;
    private Context context;

    public CustomDialogListener customDialogListener;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }


    //인터페이스 설정
    public interface CustomDialogListener{
        void onClicked0();
        void onClicked1();
        void onClicked2();
    }

    //호출할 리스너 초기화
    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        tv_title0 = findViewById(R.id.tv_title0);
        tv_title1 = findViewById(R.id.tv_title1);
        tv_title2 = findViewById(R.id.tv_title2);


        //버튼 클릭 리스너 등록
        tv_title0.setOnClickListener(this);
        tv_title1.setOnClickListener(this);
        tv_title2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title0: //확인 버튼을 눌렀을 때

                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 Activity로 전달
                customDialogListener.onClicked0();
                dismiss();
                break;
            case R.id.tv_title1: //취소 버튼을 눌렀을 때
                customDialogListener.onClicked1();
                cancel();
                break;
            case R.id.tv_title2: //취소 버튼을 눌렀을 때
                customDialogListener.onClicked2();
                cancel();
                break;
        }
    }



}