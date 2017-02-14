package com.eyck.mobileplayer.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.eyck.mobileplayer.R;

public class SystemVideoPlayer extends Activity implements View.OnClickListener {



    private VideoView videoview;
    Uri uri;

    private LinearLayout llStateTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button voiceControl;
    private Button switchControl;
    private LinearLayout llStateBottom;
    private TextView currentTime;
    private TextView totalTime;
    private Button btnExit;
    private Button btnPre;
    private Button btnPlayingStatus;
    private Button btnNext;
    private Button btnScreenStatus;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-14 23:52:39 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        llStateTop = (LinearLayout)findViewById( R.id.ll_state_top );
        tvName = (TextView)findViewById( R.id.tv_name );
        ivBattery = (ImageView)findViewById( R.id.iv_battery );
        tvSystemTime = (TextView)findViewById( R.id.tv_system_time );
        voiceControl = (Button)findViewById( R.id.voice_control );
        switchControl = (Button)findViewById( R.id.switch_control );
        llStateBottom = (LinearLayout)findViewById( R.id.ll_state_bottom );
        currentTime = (TextView)findViewById( R.id.current_time );
        totalTime = (TextView)findViewById( R.id.total_time );
        btnExit = (Button)findViewById( R.id.btn_exit );
        btnPre = (Button)findViewById( R.id.btn_pre );
        btnPlayingStatus = (Button)findViewById( R.id.btn_playing_status );
        btnNext = (Button)findViewById( R.id.btn_next );
        btnScreenStatus = (Button)findViewById( R.id.btn_screen_status );
        videoview = (VideoView)findViewById(R.id.videoview);

        voiceControl.setOnClickListener( this );
        switchControl.setOnClickListener( this );
        btnExit.setOnClickListener( this );
        btnPre.setOnClickListener( this );
        btnPlayingStatus.setOnClickListener( this );
        btnNext.setOnClickListener( this );
        btnScreenStatus.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-02-14 23:52:39 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == voiceControl ) {
            // Handle clicks for voiceControl
        } else if ( v == switchControl ) {
            // Handle clicks for switchControl
        } else if ( v == btnExit ) {
            // Handle clicks for btnExit
        } else if ( v == btnPre ) {
            // Handle clicks for btnPre
        } else if ( v == btnPlayingStatus ) {
            // Handle clicks for btnPlayingStatus
            if(videoview.isPlaying()) {
                //暂停播放
                videoview.pause();
                //将按钮设置为播放
                btnPlayingStatus.setBackgroundResource(R.drawable.btn_play_drawable_selector);
            }else {
                //播放
                videoview.start();
                //将按钮设置为暂停
                btnPlayingStatus.setBackgroundResource(R.drawable.btn_pause_drawable_selector);
            }
        } else if ( v == btnNext ) {
            // Handle clicks for btnNext
        } else if ( v == btnScreenStatus ) {
            // Handle clicks for btnScreenStatus
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        findViews();
//        videoview = (VideoView)findViewById(R.id.videoview);

        videoview.setOnPreparedListener(new MyOnPreparedListener());
        videoview.setOnCompletionListener(new MyOnCompletionListener());
        videoview.setOnErrorListener(new MyOnErrorListener());
//        videoview.setMediaController(new MediaController(SystemVideoPlayer.this));

        uri = getIntent().getData();
        if(uri != null) {
            videoview.setVideoURI(uri);
        }


    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoview.start();//播放
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(SystemVideoPlayer.this, "播放完成", Toast.LENGTH_SHORT).show();
        }
    }
    class MyOnErrorListener implements MediaPlayer.OnErrorListener {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(SystemVideoPlayer.this, "播放出错了哦", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
