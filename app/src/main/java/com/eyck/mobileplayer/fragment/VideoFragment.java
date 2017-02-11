package com.eyck.mobileplayer.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eyck.mobileplayer.DataBean.MediaInfo;
import com.eyck.mobileplayer.R;
import com.eyck.mobileplayer.activity.SystemVideoPlayer;
import com.eyck.mobileplayer.adapter.VideoFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/2/10.
 */

public class VideoFragment extends BaseFragment {

    private ListView lv_fragment_video;
    private TextView tv_fragment_video;
    private ProgressBar pb_fragment_video;
    private List<MediaInfo> mediaInfos;
    private MediaInfo mediaInfo;
    private VideoFragmentAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(mediaInfos != null && mediaInfos.size()>0) {
                //设置适配器
                adapter = new VideoFragmentAdapter(mContext,mediaInfos);
                lv_fragment_video.setAdapter(adapter);
            }else {
                //提示文本
                tv_fragment_video.setVisibility(View.VISIBLE);
            }
            //隐藏进度条
            pb_fragment_video.setVisibility(View.GONE);
        }
    };

    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_video,null);
        lv_fragment_video = (ListView) view.findViewById(R.id.lv_fragment_video);
        tv_fragment_video = (TextView) view.findViewById(R.id.tv_fragment_video);
        pb_fragment_video = (ProgressBar) view.findViewById(R.id.pb_fragment_video);
        lv_fragment_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mediaInfo = mediaInfos.get(position);
                // 1、调用播放器-隐式意图
//                Intent intent = new Intent();
//                intent.setDataAndType(Uri.parse(mediaInfo.getData()),"video/*");
//                mContext.startActivity(intent);

                // 2、调用系统播放器
                Intent intent = new Intent(mContext,SystemVideoPlayer.class);
                intent.setDataAndType(Uri.parse(mediaInfo.getData()),"video/*");
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mediaInfos = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                //获取本地视频文件，通过内容提供者
                isGrantExternalRW((Activity) mContext);
                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                String[] projection = new String[]{
                        MediaStore.Video.Media.DISPLAY_NAME,//视频名称
                        MediaStore.Video.Media.DURATION,//视频时长
                        MediaStore.Video.Media.SIZE,//视频大小
                        MediaStore.Video.Media.DATA,//视频地址
                        MediaStore.Video.Media.ARTIST,//艺术家
                };
                Cursor cursor = resolver.query(uri, projection, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext()){
                        String name = cursor.getString(0);
                        long duration = cursor.getLong(1);
                        long size = cursor.getLong(2);
                        String data = cursor.getString(3);
                        String artist = cursor.getString(4);
                        mediaInfos.add(new MediaInfo(name,duration,size,data,artist));
                    }
                }
                cursor.close();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
}
