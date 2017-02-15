package com.eyck.mobileplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.eyck.mobileplayer.DataBean.MediaInfo;
import com.eyck.mobileplayer.R;
import com.eyck.mobileplayer.utils.LogUtils;
import com.eyck.mobileplayer.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SystemVideoPlayer extends Activity implements View.OnClickListener {

    private TimeUtils timeUtils;
    private MyBroadcastReceiver mReceiver;
    private ArrayList<MediaInfo> mediaInfos;
    private int position;

    private static final int PROGRESS = 1;

    private Uri uri;
    private VideoView videoview;
    private LinearLayout llStateTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemTime;
    private Button voiceControl;
    private SeekBar sb_voice;
    private Button switchControl;
    private LinearLayout llStateBottom;
    private TextView currentTime;
    private SeekBar sb_progress;
    private TextView totalTime;
    private Button btnExit;
    private Button btnPre;
    private Button btnPlayingStatus;
    private Button btnNext;
    private Button btnScreenStatus;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case  PROGRESS:
                    //获取当前时长
                    int currentPosition = videoview.getCurrentPosition();
                    //更新进度
                    sb_progress.setProgress(currentPosition);
                    currentTime.setText(timeUtils.stringForTime(currentPosition));
                    //更新系统时间
                    tvSystemTime.setText(getSystemTime());
                    //移除消息重新发送
                    handler.removeMessages(PROGRESS);
                    handler.sendEmptyMessageDelayed(PROGRESS,1000);
                    break;
            }
        }
    };


    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String systemTime = format.format(new Date());
        LogUtils.Logd(systemTime);
        return systemTime;
    }

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
        sb_voice = (SeekBar)findViewById(R.id.sb_voice);
        switchControl = (Button)findViewById( R.id.switch_control );
        llStateBottom = (LinearLayout)findViewById( R.id.ll_state_bottom );
        currentTime = (TextView)findViewById( R.id.current_time );
        sb_progress = (SeekBar)findViewById(R.id.sb_progress);
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
            finish();
        } else if ( v == btnPre ) {
            // Handle clicks for btnPre
            playPreVideo();
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
            playNextVideo();
        } else if ( v == btnScreenStatus ) {
            // Handle clicks for btnScreenStatus
        }
    }

    private void playPreVideo() {
        if(mediaInfos != null && mediaInfos.size()>0) {
            position--;
            if(position>=0) {
                //播放下一个
                MediaInfo mediaInfo = mediaInfos.get(position);
                tvName.setText(mediaInfo.getName());
                videoview.setVideoPath(mediaInfo.getData());
            }else {
                position = 0;
                setButtonStatus(0);
            }
        }else if(uri != null) {
            setButtonStatus(0);
        }
    }

    private void playNextVideo() {
        if(mediaInfos != null && mediaInfos.size()>0) {
            position++;
            if(position<mediaInfos.size()) {
                //播放下一个
                MediaInfo mediaInfo = mediaInfos.get(position);
                tvName.setText(mediaInfo.getName());
                videoview.setVideoPath(mediaInfo.getData());
            }else {
                position = mediaInfos.size()-1;
                setButtonStatus(1);
            }
        }else if(uri != null) {
            setButtonStatus(1);
        }
    }

    private void setButtonStatus(int status) {
        if(status == 1) {
            Toast.makeText(SystemVideoPlayer.this, "没有下一条视频了", Toast.LENGTH_SHORT).show();
        }else if(status == 0) {
            Toast.makeText(SystemVideoPlayer.this, "没有上一条视频了", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        findViews();
        initData();

        setListener();
//        videoview.setMediaController(new MediaController(SystemVideoPlayer.this));

        getData();
        
        setData();


    }

    private void setData() {
        if(mediaInfos != null && mediaInfos.size()>0) {
            MediaInfo mediaInfo = mediaInfos.get(position);
            tvName.setText(mediaInfo.getName());
            videoview.setVideoPath(mediaInfo.getData());

        }else if(uri != null) {
            videoview.setVideoURI(uri);
            tvName.setText(uri.toString());
        }else {
            Toast.makeText(SystemVideoPlayer.this, "没有视频", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        uri = getIntent().getData();
        mediaInfos = (ArrayList<MediaInfo>) getIntent().getSerializableExtra("medialist");
        position = getIntent().getIntExtra("position",0);


        
    }

    @Override
    protected void onDestroy() {
        //移除所有的消息
        handler.removeCallbacksAndMessages(null);

        if(mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }

    private void initData() {
        timeUtils = new TimeUtils();
        //注册广播
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver,filter);
    }

    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);//获取电量
            //设置
            setBattery(level);
        }
    }

    private void setBattery(int level) {
        if(level <= 0) {
            ivBattery.setImageResource(R.drawable.ic_battery_0);
        }else if(level <= 10) {
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        }else if(level <= 20) {
            ivBattery.setImageResource(R.drawable.ic_battery_20);
        }else if(level <= 40) {
            ivBattery.setImageResource(R.drawable.ic_battery_40);
        }else if(level <= 60) {
            ivBattery.setImageResource(R.drawable.ic_battery_60);
        }else if(level <= 80) {
            ivBattery.setImageResource(R.drawable.ic_battery_80);
        }else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    private void setListener() {
        videoview.setOnPreparedListener(new MyOnPreparedListener());
        videoview.setOnCompletionListener(new MyOnCompletionListener());
        videoview.setOnErrorListener(new MyOnErrorListener());

        sb_progress.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{

        /**
         *
         * @param seekBar
         * @param progress
         * @param fromUser 用户操作时返回true,系统更新返回false
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser) {
                videoview.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoview.start();//播放
            //获取视频总时长
            int duration = videoview.getDuration();
            //设置进度
            sb_progress.setMax(duration);
            totalTime.setText(timeUtils.stringForTime(duration));
            //发送消息
            handler.sendEmptyMessage(PROGRESS);
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(SystemVideoPlayer.this, "播放完成", Toast.LENGTH_SHORT).show();
            playNextVideo();
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
