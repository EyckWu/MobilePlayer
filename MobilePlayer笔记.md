#MediaPlayer和VideoView
Android 系统中提供给开发真开发多媒体应用（音视频）方面。</br>
一、MeidaPlayer是负责和底层打交道，解码的是底层，封装了很多方法start,pause,stop，播放视频的类。</br>
这个MediaPlayer可以播放本地和网络的音视频，播放网络音视频的时候要获取网络权限。</br>
1、执行流程</br>:![image](note_resource/MediaPlayer.PNG)</br>
2、视频支持的格式：MP4/sgp/m3u8(直播)</br>


二、VideoView</br>
显示视频，继承于SurfaceView类，实现MediaPlayerControl接口，封装了MediaPlayer的start,pause,stop，本质上是使用MediaPlayer.

SurfaceView默认使用双缓存技术，它支持在子线程中绘制图像，这样就不会阻塞主线程所以它更适合于游戏和视频开发。</br>
实现MediaPlayerControl接口，便于控制面板调用VideoView

    public interface MediaPlayerControl {
        void    start();
        void    pause();
        int     getDuration();
        int     getCurrentPosition();
        void    seekTo(int pos);
        boolean isPlaying();
        int     getBufferPercentage();
        boolean canPause();
        boolean canSeekBackward();
        boolean canSeekForward();

        /**
         * Get the audio session id for the player used by this VideoView. This can be used to
         * apply audio effects to the audio track of a video.
         * @return The audio session, or 0 if there was an error.
         */
        int     getAudioSessionId();
    }


#Activity的生命周期和横竖屏切换的生命周期

一、生命周期

    1、创建Activity的时候执行的方法
	oncreate->onStart->onResume
    2、销毁Activity的时候执行的方法
	onPause->onStop->onDestory


二、A页面B页面，点击返回，这个过程中的生命周期
    
    (1)B页面完全覆盖A页面的情况
    A跳转B页面的生命周期方法执行顺序：
	onPause(A)->onCreate(B)->onStart(B)->onResume(B)->onStop(A)
	注：不执行onDestory(A)方法，所以在使用Intent跳转到其他页面的时候要使用finish()方法，这样A才会执行onDestory()。此时可创建一个ActiviyCollector类管理多个Activity
    
    B页面点击后返回生命周期执行顺序：
	onPause(B)->onRestart(A)->onStart(A)->onResume(A)->onStop(B)-onDestory(B)
	注：按back键返回会调用onDestory()
    

    (2)B页面不完全覆盖A页面的请求(将B页面设置为对话框形式)
    A跳转B页面的生命周期方法执行顺序
	onPause(A)->onCreate(B)->onStart(B)->onResume(B)

    B页面点击后返回生命周期执行的顺序
	onPause(B)->onResume(A)->onStop(B)->onDestory(B)

#Activity横竖屏切换的生命周期
    默认情况：onPause->onStop->onDestory->onCreate->onStart->onResume
	屏蔽横竖屏切换：
	在配置清单文件(AndroidManifest.xml)中的<Activity />如下配置
	<activity
            android:name=".activity.SystemVideoPlayer"
            android:configChanges="orientation|screenSize|keyboardHidden"
            ></activity>

#全屏模式
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen"

#findViewById离线使用
findViewById离线使用脚本快速实例化控件

#视频Seekbar更新
    1、视频的总时长和SeekBar的setMax(总时长)
	注：在准备好了的监听获取	

    2、实例化Handler,每秒得到当前视频播放进度，SeekBar.setProgress(当前进度)