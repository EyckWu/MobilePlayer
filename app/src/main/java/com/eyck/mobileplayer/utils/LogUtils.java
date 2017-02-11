package com.eyck.mobileplayer.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Eyck on 2017/2/10.
 */

public class LogUtils {

    public static void Logd(Context context, String msg){
        Log.d(context.getClass().getSimpleName(),msg);
    }

    public static void Logd(String msg){
        Log.d("EYCK",msg);
    }
}
