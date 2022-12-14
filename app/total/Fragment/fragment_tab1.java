package com.han.total.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.Util.MediaScanning;
import com.han.total.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class fragment_tab1 extends Fragment{



    private Context mContext;
    private static final int CAMEARA_REQUEST_CODE = 101;
    @BindView(R.id.ll_weather)
    LinearLayout ll_weather;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;

    @BindView(R.id.tv_title_type)
    TextView tv_title_type;
    @BindView(R.id.tv_title_weather)
    TextView tv_title_weather;

    @BindView(R.id.iv_camera)
    ImageView iv_camera;


    Bitmap bitmap;
    String weather="null",type="null";

    public fragment_tab1(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public fragment_tab1() {
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
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // ????????? ????????? ??????
    @OnClick(R.id.iv_camera) void ClickCamera(){
        Camera();
    }
    // ????????? ????????? ??????
    void Camera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    //??????????????? ,
    @OnClick({R.id.ll_click_type,R.id.ll_click_weather,R.id.ll_save}) void Click(View v){
        if(v.getId()==R.id.ll_click_type){
            Clear();
            ll_type.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_click_weather){
            Clear();
            ll_weather.setVisibility(View.VISIBLE);
        }else if(v.getId()==R.id.ll_save){

            if(weather.equals("null") || type.equals("null")){
                Toast.makeText(mContext,"????????? ???????????? ???????????????",Toast.LENGTH_SHORT).show();
            }else{
                Save(type,weather);
            }

        }
    }

    // ?????????, ?????? , ?????? ?????????
    @OnClick({R.id.tv_outer,R.id.tv_top,R.id.tv_bottom,R.id.tv_spring,R.id.tv_summer,R.id.tv_winter}) void Click1(View v){
        if(v.getId()==R.id.tv_outer){
            Clear();
            tv_title_type.setText("?????????");
            type="ou";
            Log.e("HAN","?????????");
        }else if(v.getId()==R.id.tv_top){
            Clear();
            type="up";
            Log.e("HAN","??????");
            tv_title_type.setText("??????");
        }else if(v.getId()==R.id.tv_bottom){
            Clear();
            type="do";
            Log.e("HAN","??????");
            tv_title_type.setText("??????");
        }else if(v.getId()==R.id.tv_spring){
            Clear();
            weather="sf";
            tv_title_weather.setText("??? / ??????");
        }else if(v.getId()==R.id.tv_summer){
            Clear();
            weather="su";
            tv_title_weather.setText("??????");
        }else if(v.getId()==R.id.tv_winter){
            Clear();
            weather="wi";
            tv_title_weather.setText("??????");
        }
    }

    void Clear(){
        ll_type.setVisibility(View.GONE);
        ll_weather.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case CAMEARA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        iv_camera.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }

    void Save(String type, String weather){
        String path="";
        String name="";
        Logg.e(Global.USER_HTJ,"?????? ??????: "+type);
        Logg.e(Global.USER_HTJ,"?????? ??????: "+weather);
       // Logg.e(Global.USER_HTJ,"data.getInstance(mContext).setNumber(2,type+weather);: "+data.getInstance(mContext).getNumber(type+weather));

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        name = dateFormat.format(date)+".jpg";
        path = createPictureFilePath(name);
        saveBitmapAsFile(bitmap,path,weather,type);
        scanMediaFile(new File(path));
    }

    // ???????????? ?????? ????????? ?????? ????????? ???????????? ??????
    private void saveBitmapAsFile(Bitmap bitmap, String filepath, String weather , String type) {
        File file = new File(filepath);
        OutputStream os = null;

        try {
            file.createNewFile();
            os = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
            //Toast.makeText(mContext,"?????? ??????",Toast.LENGTH_SHORT).show();
            int index = data.getInstance(mContext).NewgetNumber(weather+type)+1;
            data.getInstance(mContext).NewsetNumber(index,weather+type);
            Log.e("HAN","??????"+weather+type);
            Log.e("HAN","??????: "+data.getInstance(mContext).NewgetNumber(weather+type));

            //data.getInstance(mContext).NewgetNumber(weather+type);
            data.getInstance(mContext).NewsetCloth(filepath,weather+type,index);
            Log.e("HAN","????????????: "+data.getInstance(mContext).NewgetCloth(weather+type,index));
            Log.e("HTJ","?????? ??????: "+filepath);
        } catch (Exception e) {
            Log.e("HTJ","Exception: "+e);
            e.printStackTrace();
        }
    }

    // ????????? ???????????? ????????????
    private void scanMediaFile(final File file) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new MediaScanning(mContext, file);
                Toast.makeText(mContext,"????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
            }
        }, 900);
    }

    public static final String SAVE_MEDIA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/";
    public String createPictureFilePath(String fileName) {
        createMediaFileDirectory();
        //String fileName = "img1"+ ".jpg";
        String fullPath = SAVE_MEDIA_PATH + fileName;
        return fullPath;
    }

    // ?????? ????????? ????????? ??????????????? ???????????? ??????
    private void createMediaFileDirectory() {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        File cameraDir = new File(SAVE_MEDIA_PATH);
        if (!cameraDir.exists()) {
            cameraDir.mkdir();
        }
    }




}