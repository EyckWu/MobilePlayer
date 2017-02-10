package com.eyck.mobileplayer.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Eyck on 2017/2/10.
 */

public class AudioFragment extends BaseFragment {

    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setTextSize(23);
        textView.setText("本地音频");
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
