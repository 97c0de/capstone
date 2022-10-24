package com.han.total.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.han.total.Activity.SelectClothsActivity;
import com.han.total.R;

public class fragment_tab2 extends Fragment {


    private Context mContext;

    @BindView(R.id.ll_spring1)
    LinearLayout ll_spring1;
    @BindView(R.id.ll_winter1)
    LinearLayout ll_winter1;
    @BindView(R.id.ll_summer1)
    LinearLayout ll_summer1;

    public fragment_tab2(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public fragment_tab2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // 날씨 클릭 리스너
    @OnClick({R.id.ll_spring0,R.id.ll_summer0,R.id.ll_save}) void ClickWeather(View v){
        Clear();
        if(v.getId()==R.id.ll_summer0){
            ll_summer1.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_save){
            ll_winter1.setVisibility(View.VISIBLE);
        }if(v.getId()==R.id.ll_spring0){
            ll_spring1.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_bottom_spring,R.id.tv_outer_spring,R.id.tv_top_spring,
            R.id.tv_bottom_summer,R.id.tv_outer_summer,R.id.tv_top_summer,
            R.id.tv_bottom_winter,R.id.tv_outer_winter,R.id.tv_top_winter}) void ClickCloths(View v){
            Intent intent = new Intent(mContext, SelectClothsActivity.class);
            if(v.getId()==R.id.tv_bottom_spring | v.getId()==R.id.tv_top_spring  | v.getId()==R.id.tv_outer_spring ){
                 intent.putExtra("weather","sf");
                if(v.getId()==R.id.tv_bottom_spring){
                    intent.putExtra("type","do");
                }else if(v.getId()==R.id.tv_outer_spring){
                    intent.putExtra("type","ou");
                }else if(v.getId()==R.id.tv_top_spring){
                    intent.putExtra("type","up");
                }
            }else if(v.getId()==R.id.tv_bottom_summer | v.getId()==R.id.tv_outer_summer  | v.getId()==R.id.tv_top_summer ){
                intent.putExtra("weather","su");
                if(v.getId()==R.id.tv_bottom_summer){
                    intent.putExtra("type","do");
                }else if(v.getId()==R.id.tv_outer_summer){
                    intent.putExtra("type","ou");
                }else if(v.getId()==R.id.tv_top_summer){
                    intent.putExtra("type","up");
                }
           // }else if(v.getId()==R.id.tv_bottom_winter | v.getId()==R.id.tv_outer_winter  | v.getId()==R.id.tv_top_winter ){
            }else {
                intent.putExtra("weather","wi");
                if(v.getId()==R.id.tv_bottom_winter){
                    intent.putExtra("type","do");
                }else if(v.getId()==R.id.tv_outer_winter){
                    intent.putExtra("type","ou");
                }else if(v.getId()==R.id.tv_top_winter){
                    intent.putExtra("type","up");
                }
            }
            startActivity(intent);
    }

    void Clear(){
        ll_spring1.setVisibility(View.GONE);
        ll_winter1.setVisibility(View.GONE);
        ll_summer1.setVisibility(View.GONE);
    }

}