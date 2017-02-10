package com.eyck.mobileplayer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.eyck.mobileplayer.R;
import com.eyck.mobileplayer.fragment.AudioFragment;
import com.eyck.mobileplayer.fragment.BaseFragment;
import com.eyck.mobileplayer.fragment.FunFragment;
import com.eyck.mobileplayer.fragment.NetVideoFragment;
import com.eyck.mobileplayer.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private int position;//页面导航
    private List<BaseFragment> fragments;
    private BaseFragment mFragment;//缓存fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(new VideoFragment());
        fragments.add(new AudioFragment());
        fragments.add(new NetVideoFragment());
        fragments.add(new FunFragment());
    }

    private void initEvent() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_video :
                        position = 0;
                        break;
                    case R.id.rb_audio :
                        position = 1;
                        break;
                    case R.id.rb_net_video :
                        position = 2;
                        break;
                    case R.id.rb_funning :
                        position = 3;
                        break;
                    default:
                        position = 0;
                        break;
                }

                BaseFragment toFragment = fragments.get(position);
                setFragment(mFragment,toFragment);
            }
        });
        rg_main.check(R.id.rb_video);
    }

    private void setFragment(BaseFragment fromFragment,BaseFragment toFragment) {
        //FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        //FragmentTransaction
        FragmentTransaction ft = fm.beginTransaction();
        //逻辑处理
        if(toFragment != null) {
            if(toFragment != fromFragment) {
                mFragment = toFragment;
                if(toFragment.isAdded()) {//已经添加
                    if(fromFragment != null) {
                        ft.hide(fromFragment);
                    }
                    ft.show(toFragment).commit();
                }else {//还没添加
                    if(fromFragment != null) {
                        ft.hide(fromFragment);
                    }
                    ft.add(R.id.fl_main_content,toFragment).commit();
                }
            }
        }
    }

    private void initView() {
        rg_main = (RadioGroup)findViewById(R.id.rg_main);
    }
}
