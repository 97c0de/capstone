package com.han.total.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.han.total.R;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;

import java.util.ArrayList;
import java.util.Random;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {

    //
    public interface AdapterCallback {
        void DoSomeThing(int position);
    }

    private ArrayList<String> mData = null ;
    private AdapterCallback mAdapterCallback;
    Context mContext;
    boolean onoff=true;

    public TemplateAdapter(ArrayList<String> list,TemplateAdapter.AdapterCallback AdapterCallback,Context context) {
        mData = list ;
        mContext = context;
        onoff = true;
        mAdapterCallback = AdapterCallback;
    }

    public TemplateAdapter(ArrayList<String> list,TemplateAdapter.AdapterCallback AdapterCallback,Context context,boolean flag) {
        mData = list ;
        mContext = context;
        onoff = flag;
        mAdapterCallback = AdapterCallback;
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img0 ;
        ImageView iv_img1;
        ImageView iv_img2;
        ViewHolder(View itemView) {
            super(itemView) ;
            iv_img0 = itemView.findViewById(R.id.iv_img0) ;
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);

            iv_img0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });

            iv_img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });

            iv_img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mAdapterCallback != null) {
                        Logg.e(Global.USER_HTJ,"mSelectedIndex: "+getLayoutPosition());
                        mAdapterCallback.DoSomeThing(getLayoutPosition());
                    }
                }
            });
        }

    }

    @Override
    public TemplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.template_adapter_item, parent, false) ;
        TemplateAdapter.ViewHolder vh = new TemplateAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(TemplateAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        int temp = Integer.parseInt(text);

        if(onoff) {
            Logg.e(Global.USER_HTJ,"온도: "+temp);
            if (temp > 20) {  //여름
                int drawableResId1 = mContext.getResources().getIdentifier("summertop" + Random(), "drawable", mContext.getPackageName());
                int drawableResId2 = mContext.getResources().getIdentifier("summerouter" + Random(), "drawable", mContext.getPackageName());
                int drawableResId3 = mContext.getResources().getIdentifier("summerbottom" + Random(), "drawable", mContext.getPackageName());
                //lic void setCloth(int flag, String type, int index){
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            } else if (temp > 5) { // 봄
                int drawableResId1 = mContext.getResources().getIdentifier("springtop" + Random(), "drawable", mContext.getPackageName());
                int drawableResId2 = mContext.getResources().getIdentifier("springouter" + Random(), "drawable", mContext.getPackageName());
                int drawableResId3 = mContext.getResources().getIdentifier("springbottom" + Random(), "drawable", mContext.getPackageName());
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            } else { // 겨울
                int drawableResId1 = mContext.getResources().getIdentifier("wintertop" + Random(), "drawable", mContext.getPackageName());
                int drawableResId2 = mContext.getResources().getIdentifier("winterouter" + Random(), "drawable", mContext.getPackageName());
                int drawableResId3 = mContext.getResources().getIdentifier("winterbottom" + Random(), "drawable", mContext.getPackageName());
                data.getInstance(mContext).setCloth(drawableResId1, "0", position);
                data.getInstance(mContext).setCloth(drawableResId2, "1", position);
                data.getInstance(mContext).setCloth(drawableResId3, "2", position);
                holder.iv_img0.setImageResource(drawableResId1);
                holder.iv_img1.setImageResource(drawableResId2);
                holder.iv_img2.setImageResource(drawableResId3);
            }
        }else{
            holder.iv_img0.setImageResource(data.getInstance(mContext).getCloth( "0", position));
            holder.iv_img1.setImageResource(data.getInstance(mContext).getCloth( "1", position));
            holder.iv_img2.setImageResource(data.getInstance(mContext).getCloth( "2", position));
        }
        //holder.textView1.setText(text) ;
    }



    int Random(){
        int max_num_value = 7;
        int min_num_value = 1;

        Random random = new Random();

        int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
        return randomNum;
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}