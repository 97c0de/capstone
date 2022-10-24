package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


import com.han.total.Adapter.TemplateAdapter;

import com.han.total.Fragment.fragment_tab0;
import com.han.total.Fragment.fragment_tab1;
import com.han.total.Fragment.fragment_tab2;
import com.han.total.Fragment.fragment_tab3;

import com.han.total.R;
import com.han.total.Util.CustomDialog;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity {

    Context mContext;
    @BindView(R.id.fl_fragment0)
    FrameLayout fl_fragment0;
    @BindView(R.id.fl_fragment1)
    FrameLayout fl_fragment1;
    @BindView(R.id.fl_fragment2)
    FrameLayout fl_fragment2;
    @BindView(R.id.fl_fragment3)
    FrameLayout fl_fragment3;
//    @BindView(R.id.ll_bottom_fragment_total)
//    LinearLayout ll_bottom_fragment_total;
    @BindView(R.id.fr_fragment)
    FrameLayout fr_fragment;
    int fposition = 0 ;
//    @BindView(R.id.template_recycler)
//    RecyclerView template_recycler;
    //
    TemplateAdapter.AdapterCallback mAdapterCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        InitFragement();
    }


    // 프레그먼트 초기값
    public void InitFragement(){
        if(Global.FRAGMENT_NUMBERS==0){
            //ll_bottom_fragment_total.setVisibility(View.GONE);
            fr_fragment.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==1){
            fl_fragment1.setVisibility(View.GONE);
            fl_fragment2.setVisibility(View.GONE);
            fl_fragment3.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==2){
            fl_fragment2.setVisibility(View.GONE);
            fl_fragment3.setVisibility(View.GONE);
        }else if(Global.FRAGMENT_NUMBERS==3){
            fl_fragment3.setVisibility(View.GONE);
        }
        if(Global.FRAGMENT_NUMBERS>0) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.fr_fragment, new fragment_tab0(mContext));
            fragmentTransaction.commit();
        }
    }

    // 클릭 리스너
    @OnClick({R.id.fl_fragment0,R.id.fl_fragment1,R.id.fl_fragment2,R.id.fl_fragment3}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0(mContext);
        if(id==R.id.fl_fragment0){  //메인화면
            fr = new fragment_tab0(mContext) ;
            fposition = 0;
        }else if(id==R.id.fl_fragment1){  //이메일 띄워주는 기능
            fposition= 1;
            //fr = new fragment_tab1(mContext) ;
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            String[] address = {"pa3278@naver.com"};
            email.putExtra(Intent.EXTRA_EMAIL, address);
            email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
            email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
            startActivity(email);
        }else if(id==R.id.fl_fragment2){  ////가을 봄 여름 선택하는 기능
            fposition = 2;
            fr = new fragment_tab2(mContext) ;
        }else if(id==R.id.fl_fragment3){  //Today outfit view
            fposition = 3;
            fr = new fragment_tab3(mContext) ;
            //fr = new fragment_alarm(mContext) ;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }

    // 프래그먼트 바꾸는 기능, 위 클릭 리스너랑 비슷한 기능인지
    public void FragmentSwitch(int position,Context mContext){
        Fragment fr =  new fragment_tab0(mContext);
        if(position==3){
            fposition =3;
            Logg.e(Global.USER_HTJ,"positon: "+fposition);
            fr = new fragment_tab3(mContext) ;
            fr = new fragment_tab3(mContext) ;
        }else if(position==1){
            fposition =1;
            Logg.e(Global.USER_HTJ,"positon: "+fposition);
            fr = new fragment_tab1(mContext) ;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }


    // 소프트웨어 백버튼
    // 메인화면일경우 앱을 종료하고, 그게 아닌 경우 이전 화면으로 이동하기
    @Override
    public void onBackPressed() {
        Logg.e(Global.USER_HTJ,"positon: "+fposition);
        if(fposition==0){
            finish();
        }else {
            Fragment fr = new fragment_tab0(mContext);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fr_fragment, fr);
            fragmentTransaction.commit();
        }
    }


}