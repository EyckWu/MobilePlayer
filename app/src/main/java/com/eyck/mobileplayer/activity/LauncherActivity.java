package com.eyck.mobileplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.eyck.mobileplayer.R;
import com.eyck.mobileplayer.utils.LogUtils;

public class LauncherActivity extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startMainActivity();
        }
    };

    //解决创建多个MainActivity bug
    private boolean isFirstStart = true;
    private void startMainActivity(){
        LogUtils.Logd(this,"startMainActivity()");
        LogUtils.Logd("startMainActivity()");
        if(isFirstStart) {
            isFirstStart = false;
            Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);//移除为处理的消息
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        startMainActivity();
        return true;
    }
}
