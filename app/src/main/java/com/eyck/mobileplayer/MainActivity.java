package com.eyck.mobileplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        TextView textView = new TextView(this);
        textView.setText("我是主界面");
        setContentView(textView);
    }
}