package com.yds.multimodellive.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.Iterator;
import java.util.List;

public class PermissionUtil {
    public static void checkPermission(Activity activity, List<String> permissions){
        Iterator<String> iterator = permissions.iterator();
        while(iterator.hasNext()){
            String permission = iterator.next();
            if(ActivityCompat.checkSelfPermission(activity,permission)==PackageManager.PERMISSION_GRANTED){
                iterator.remove();
            }
        }
        if(permissions.size()>0){
            String[] perm = new String[permissions.size()];
            ActivityCompat.requestPermissions(activity,permissions.toArray(perm),123);
        }


    }
}
