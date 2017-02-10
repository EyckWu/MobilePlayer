package com.eyck.mobileplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LauncherActivity extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

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
}
