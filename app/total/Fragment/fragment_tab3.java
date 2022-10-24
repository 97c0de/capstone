package com.han.total.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.han.total.Activity.MainActivity;
import com.han.total.Adapter.NewAdapter;
import com.han.total.Adapter.TemplateAdapter;
import com.han.total.R;
import com.han.total.data;

import java.util.ArrayList;

public class fragment_tab3 extends Fragment  implements NewAdapter.AdapterCallback{


    private Context mContext;

    @BindView(R.id.template_recycler)
    RecyclerView template_recycler;

    public fragment_tab3(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public fragment_tab3() {
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        ButterKnife.bind(this, view);
        InitRecylerView();
        return view;
        //return inflater.inflate(R.layout.fragment_tab0, container, false);
    }


    //RecyclerView
    @Override
    public void DoSomeThing(int posionion){
        //((MainActivity)getActivity()).FragmentSwitch(3,mContext);
        //Toast.makeText(mContext,"posion: "+posionion,Toast.LENGTH_SHORT).show();
    }


    //RecyclerView
    // 리스트뷰 초기화

    public void InitRecylerView(){

        String[] weather=new String[3];
        weather[0]="sf";
        weather[1]="su";
        weather[2]="wi";

        String[] type=new String[3];
        type[0]="ou";
        type[1]="up";
        type[2]="do";
//        String weather = "sf";
//        String type = "ou";
        //fr_fragment.setVisibility(View.GONE);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList();
        //Log.e("HAN","길이: "+ data.getInstance(mContext).NewgetNumber(weather+type));

        for (int j=0;j<3;j++) {

            for (int k=0;k<3;k++) {

                for (int i = 1; i < data.getInstance(mContext).NewgetNumber(weather[j] + type[k]) + 1; i++) {
                    //Log.e("HAN","파일이름: "+data.getInstance(mContext).NewgetCloth(weather+type,i));

                    if (!data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i).equals("null")) {
                        list.add(data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i));
                        index.add(0);
                    }
//
//                    if (!data.getInstance(mContext).NewgetCloth(weather + "ou", i).equals("null")) {
//                        list.add(data.getInstance(mContext).NewgetCloth(weather + type, i));
//                        index.add(0);
//                    }
//                    if (!data.getInstance(mContext).NewgetCloth(weather + "up", i).equals("null")) {
//                        list.add(data.getInstance(mContext).NewgetCloth(weather + type, i));
//                        index.add(0);
//                    }
//                    if (!data.getInstance(mContext).NewgetCloth(weather + "do", i).equals("null")) {
//                        list.add(data.getInstance(mContext).NewgetCloth(weather + type, i));
//                        index.add(0);
//                    }
                }
            }
        }

        template_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)) ;
        NewAdapter adapter = new NewAdapter(list,index,this,mContext) ;
        template_recycler.setAdapter(adapter) ;
    }





}