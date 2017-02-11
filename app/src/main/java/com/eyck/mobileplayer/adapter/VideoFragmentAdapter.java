package com.eyck.mobileplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyck.mobileplayer.DataBean.MediaInfo;
import com.eyck.mobileplayer.R;
import com.eyck.mobileplayer.utils.LogUtils;
import com.eyck.mobileplayer.utils.TimeUtils;

import java.util.List;

/**
 * Created by Eyck on 2017/2/10.
 */

public class VideoFragmentAdapter extends BaseAdapter {

    private Context mContext;
    private List<MediaInfo> mediaInfos;
    private MediaInfo mediaInfo;

    public VideoFragmentAdapter(Context mContext,List<MediaInfo> mediaInfos){
        this.mContext = mContext;
        this.mediaInfos = mediaInfos;

    }

    @Override
    public int getCount() {
        return mediaInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_video_fragment,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon_video = (ImageView) convertView.findViewById(R.id.iv_icon_video);
            viewHolder.tv_name_video = (TextView) convertView.findViewById(R.id.tv_name_video);
            viewHolder.tv_time_video = (TextView) convertView.findViewById(R.id.tv_time_video);
            viewHolder.tv_size_video = (TextView) convertView.findViewById(R.id.tv_size_video);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        mediaInfo = mediaInfos.get(position);
        LogUtils.Logd(mediaInfo.getData());
        viewHolder.tv_name_video.setText(mediaInfo.getName());
        viewHolder.tv_time_video.setText(new TimeUtils().stringForTime((int) mediaInfo.getDuration()));
        viewHolder.tv_size_video.setText(Formatter.formatFileSize(mContext,mediaInfo.getSize()));
        return convertView;
    }
    static class ViewHolder{
        ImageView iv_icon_video;
        TextView tv_name_video;
        TextView tv_time_video;
        TextView tv_size_video;

    }
}
