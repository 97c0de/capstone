package com.han.total.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.han.total.Interface.DelayCallback;


public class Util {



    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;
    private static Util mInstance;
    private final static String PREF_NAME = "pref_config"; // real File Name

    public synchronized static Util getInstance(Context ctx) {
        mContext = ctx;
        if (mInstance == null) {
            mInstance = new Util();
            //mSharedPreferences = ctx.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
            //mEditor = mSharedPreferences.edit();
        }
        return mInstance;
    }

    //Delay Function
    private DelayCallback mDelayCallback;
    public void DelayCallback(int DelayTime,DelayCallback callback){
        mDelayCallback = callback;
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                mDelayCallback.DoSomething();
            }
        }, DelayTime); // 0.5초후
    }
}
