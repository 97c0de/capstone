package com.han.total.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {

    //
    public interface AdapterCallback {
        void DoSomeThing(int position);
    }

    private ArrayList<String> mData = null ;
    private ArrayList<Integer> mIndex = null ;
    private AdapterCallback mAdapterCallback;
    Context mContext;
    boolean onoff=true;

    public NewAdapter(ArrayList<String> list0,ArrayList<Integer> list1,NewAdapter.AdapterCallback AdapterCallback,Context context) {
        mData = list0 ;
        mIndex=list1;
        mContext = context;
        onoff = true;
        mAdapterCallback = AdapterCallback;
    }

    public NewAdapter(ArrayList<String> list,NewAdapter.AdapterCallback AdapterCallback,Context context,boolean flag) {
        mData = list ;
        mContext = context;
        onoff = flag;
        mAdapterCallback = AdapterCallback;
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img0 ;

        ViewHolder(View itemView) {
            super(itemView) ;
            iv_img0 = itemView.findViewById(R.id.iv_img0) ;

            iv_img0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        //Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(mIndex.get(getLayoutPosition()));
                    }
                }
            });

        }

    }

    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.new_adapter_item, parent, false) ;
        NewAdapter.ViewHolder vh = new NewAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(NewAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        //int temp = Integer.parseInt(text);

//        if(onoff) {
//            if (temp > 20) {  //여름
//                int drawableResId1 = mContext.getResources().getIdentifier("summertop" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId2 = mContext.getResources().getIdentifier("summerouter" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId3 = mContext.getResources().getIdentifier("summerbottom" + Random(), "drawable", mContext.getPackageName());
//                //lic void setCloth(int flag, String type, int index){
//                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
//                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
//                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
//                holder.iv_img0.setImageResource(drawableResId1);
//
//            } else if (temp > 5) { // 봄
//                int drawableResId1 = mContext.getResources().getIdentifier("springtop" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId2 = mContext.getResources().getIdentifier("springouter" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId3 = mContext.getResources().getIdentifier("springbottom" + Random(), "drawable", mContext.getPackageName());
//                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
//                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
//                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
//                holder.iv_img0.setImageResource(drawableResId1);
//
//            } else { // 겨울
//                int drawableResId1 = mContext.getResources().getIdentifier("wintertop" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId2 = mContext.getResources().getIdentifier("winterouter" + Random(), "drawable", mContext.getPackageName());
//                int drawableResId3 = mContext.getResources().getIdentifier("winterbottom" + Random(), "drawable", mContext.getPackageName());
//                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
//                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
//                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
//                holder.iv_img0.setImageResource(drawableResId1);
//            }
//        }else{  //
//            holder.iv_img0.setImageBitmap(loadImageFromStorage(mData.get(position)));
//            //holder.iv_img0.setImageResource(data.getInstance(mContext).getCloth( "0", position));
//        }
        holder.iv_img0.setImageBitmap(loadImageFromStorage(mData.get(position)));

    }



    int Random(){
        int max_num_value = 7;
        int min_num_value = 1;

        Random random = new Random();

        int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
        return randomNum;
    }

    private Bitmap loadImageFromStorage(String name)
    {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/";
        try {
            File f;
            f = new File(name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            Log.e("HAN","exception: "+e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}