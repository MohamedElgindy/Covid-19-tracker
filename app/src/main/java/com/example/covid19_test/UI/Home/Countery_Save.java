package com.example.covid19_test.UI.Home;

import android.content.Context;
import android.content.SharedPreferences;

public class Countery_Save {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    private static final String FILE_NAME="my_File";
    public static final String KEY_COUNTRY ="countiry_name";



    public Countery_Save(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences=mContext.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();

    }


    public void SaveCountiry_shared (String Counter )
    {
        mEditor.putString(KEY_COUNTRY,Counter);

        mEditor.apply();
    }

    public String getCountry_shared ()
    {
        return mSharedPreferences.getString(KEY_COUNTRY,"USA");    }



}
///////////////////////////




