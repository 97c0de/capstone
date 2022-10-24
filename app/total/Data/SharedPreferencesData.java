package com.han.total.Data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


// 로컬데이터 저장하는 함수
class SharedPreferencesData {

    private final static String PREF_NAME = "pref_sharedpreferences_data";
    private static final String NEW_NOTIFY_YN_KEY = "new_notify_yn_key";

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;

    private static SharedPreferencesData mInstance;
    public synchronized static SharedPreferencesData getInstance(Context ctx) {
        mContext = ctx;
        if (mInstance == null) {
            mInstance = new SharedPreferencesData();
            mSharedPreferences = ctx.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
        return mInstance;
    }


    /*------ this is for new notify  --------*/
    public void setNewNotify(String flag){
        mEditor.putString(NEW_NOTIFY_YN_KEY, flag);
        mEditor.commit();
    }

    public String getNewNotify(){
        return mSharedPreferences.getString(NEW_NOTIFY_YN_KEY, "N");
    }


}
