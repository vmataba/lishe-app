package com.example.emmanuel.lisheapp.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.emmanuel.lisheapp.user.User;
import com.google.gson.Gson;

public class UserDetails {

    public static final String PREF_USER = "user";

    private Gson gson;
    private String user_json;
    private SharedPreferences userPref;
    private SharedPreferences.Editor userPrefEditor;


    public UserDetails(){

    }

    public UserDetails(Context context,User user){
        userPref = context.getSharedPreferences(PREF_USER,0);
        userPrefEditor = userPref.edit();
        storeUser(user);
    }

   /* public User getUser(){
        String userJson = this.userPref.getString(PREF_USER,"");
        User user = this.gson.fromJson(userJson,User.class);
      return user;
    }*/

    public void storeUser(User user){
        this.gson = new Gson();
        this.user_json = gson.toJson(user);
        this.userPrefEditor.putString(PREF_USER,user_json);
        this.userPrefEditor.commit();
    }

    public static User getUser(Context context){
       SharedPreferences preferences =  context.getSharedPreferences(PREF_USER,0);
       String userJson = preferences.getString(PREF_USER,"");
       Gson gson = new Gson();
       User user = gson.fromJson(userJson,User.class);
       return user;
    }

    public void editUser(Context context,User user){
        userPref = context.getSharedPreferences(PREF_USER,0);
        userPrefEditor = userPref.edit();
        storeUser(user);
    }


}
