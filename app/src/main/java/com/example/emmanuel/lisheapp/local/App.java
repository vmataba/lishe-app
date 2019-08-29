package com.example.emmanuel.lisheapp.local;

import android.content.Context;
import android.content.SharedPreferences;

public class App {

    private static final String FIRST_RUN = "first_run";

    public static void setFirstRun(Context context){

        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_RUN,false);

    }

    public static boolean isFirstRun(Context context){
        SharedPreferences preferences = context.getSharedPreferences(FIRST_RUN,Context.MODE_PRIVATE);
        return preferences.getBoolean(FIRST_RUN,true);
    }

}
