package com.example.administrator.myapplication;

import android.os.Environment;

/**
 * Created by Administrator on 2017/5/27.
 */

public class KgeApp {
    public static String getExternalStorageAppRoot(){
        return  Environment.getExternalStorageDirectory() + "zzsoft/";
    }
}
