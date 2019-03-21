package com.yds.multimodellive.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.yds.multimodellive.app.App;
import com.yds.multimodellive.model.User;

public class ConfigManager {

    public SharedPreferences getSharedPreferences(String name,int mode){
        SharedPreferences preferences = App.getInstance().getSharedPreferences(name,mode);
        return preferences;
    }

    public void updateUser(User user){
        SharedPreferences preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account",user.getAccount());
        editor.putString("name",user.getName());
        editor.putString("description",user.getDescription());
        editor.apply();
    }

    public User getUser(){
        SharedPreferences preferences = getSharedPreferences("user",Context.MODE_PRIVATE);
        User user = new User();
        user.setAccount(preferences.getString("account","未登录"));
        user.setName(preferences.getString("name","未设置"));
        user.setDescription(preferences.getString("description","未设置"));
        return user;
    }
}
