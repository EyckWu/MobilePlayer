package com.eyck.mobileplayer.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Eyck on 2017/2/10.
 */

public class FunFragment extends BaseFragment {

    @Override
    protected View initView() {
        TextView textView = new TextView(mContext);
        textView.setTextSize(23);
        textView.setText("大杂烩");
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
