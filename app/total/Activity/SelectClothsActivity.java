package com.han.total.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.han.total.Adapter.NewAdapter;
import com.han.total.Adapter.TemplateAdapter;
import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;
import com.han.total.dialog.CustomDialog;
import com.han.total.dialog.DeleteDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SelectClothsActivity extends AppCompatActivity implements NewAdapter.AdapterCallback{

    Context mContext;
    @BindView(R.id.tv_weather)
    TextView tv_weather;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.template_recycler)
    RecyclerView template_recycler;

    String weather;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cloths);
        mContext = this;
        ButterKnife.bind(this);

        // 인텐트 파라미터 받는 과정
        Intent intent = getIntent();
        weather =  intent.getStringExtra("weather");
        type =  intent.getStringExtra("type");

        Log.e("HAN","type: "+type);

        if(weather.equals("sf")){
            tv_weather.setText("봄, 가을");
        }else if(weather.equals("su")){
            tv_weather.setText("여름");
        }else if(weather.equals("wi")){
            tv_weather.setText("겨울");
        }

        if(type.equals("up")){
            tv_type.setText("상의");
        }else if(type.equals("do")){
            tv_type.setText("하의");
        }else if(type.equals("ou")){
            tv_type.setText("아우터");
        }
        //tv_weather.setText(weather);
        //tv_type.setText(type);
        Init(type,weather);
        InitRecylerView(weather,type);
    }

    // 이메일 띄우기
    @OnClick({R.id.fl_fragment1}) void Click(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        String[] address = {"email@address.com"};
        email.putExtra(Intent.EXTRA_EMAIL, address);
        email.putExtra(Intent.EXTRA_SUBJECT, "test@test");
        email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)");
        startActivity(email);
    }

    public void InitRecylerView(String weather , String type){

        //fr_fragment.setVisibility(View.GONE);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList();
        Log.e("HAN","길이: "+data.getInstance(mContext).NewgetNumber(weather+type));
        for (int i=1; i<data.getInstance(mContext).NewgetNumber(weather+type)+1; i++) {
            Log.e("HAN","파일이름: "+data.getInstance(mContext).NewgetCloth(weather+type,i));
            if(!data.getInstance(mContext).NewgetCloth(weather+type,i).equals("null")) {
               // Log.e("HAN","파일이름: "+data.getInstance(mContext).NewgetCloth(weather+type,i));
                list.add(data.getInstance(mContext).NewgetCloth(weather + type, i));
                index.add(i);
            }
        }
        template_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)) ;
        NewAdapter adapter = new NewAdapter(list,index,this,mContext) ;
        template_recycler.setAdapter(adapter) ;
    }

    @Override
    public void DoSomeThing(int posionion){
        // 리스트에서 클릭 리스너
        //((MainActivity)getActivity()).FragmentSwitch(3,mContext);
        //Toast.makeText(mContext,"posion: "+posionion,Toast.LENGTH_SHORT).show();

        DeleteDialog dialog = new DeleteDialog(mContext);
        dialog.setDialogListener(new DeleteDialog.CustomDialogListener() {
            @Override
            public void onClicked0() {
                Toast.makeText(mContext,"삭제 되었습니다.",Toast.LENGTH_LONG).show();
                data.getInstance(mContext).NewsetCloth("null",weather+type,posionion);
                InitRecylerView(weather,type);
                dialog.dismiss();
            }
            @Override
            public void onClicked1() {
                dialog.dismiss();
            }

        });
        dialog.show();

    }


    void Init(String type , String weather){
//        if(weather.equals("봄")){
//            if(type.equals("아우터")){
//
//            }else if(type.equals("상의")){
//
//            }else if(type.equals("하의")){
//
//            }
//        }else if(weather.equals("여름")){}
//        else if(weather.equals("겨울")){}
//
//        Logg.e(Global.USER_HTJ,"타입: "+type);
//        Logg.e(Global.USER_HTJ,"날씨: "+weather);
//        Logg.e(Global.USER_HTJ,"개수: "+data.getInstance(mContext).getNumber(type+weather));
//
//        if(data.getInstance(mContext).getNumber(type+weather)==0){
//
//        }else if(data.getInstance(mContext).getNumber(type+weather)==1){
//            String name = data.getInstance(mContext).getPicture(type+weather,1);
//            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
//            //iv_9.setVisibility(View.GONE);
//        }else{
//            String name = data.getInstance(mContext).getPicture(type+weather,1);
//            Logg.e(Global.USER_HTJ,"아우터 겨울1: "+name);
//            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,true);
//            name = data.getInstance(mContext).getPicture(type+weather,2);
//            Logg.e(Global.USER_HTJ,"아우터 겨울2: "+name);
//            loadImageFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/",name,false);
//        }
    }


    private void loadImageFromStorage(String path, String name,boolean flag)
    {
        try {
            File f;
            f = new File(path, name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            if (flag){
                //iv_8.setImageBitmap(b);
            }else{
                //iv_9.setImageBitmap(b);
            }

        }
        catch (FileNotFoundException e)
        {
            Log.e("HAN","exception: "+e);
            e.printStackTrace();
        }
    }


}