package com.example.l.myplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private SeekBar mSeekBar;
    private MediaPlayer mMediaPlayer;
    private boolean flag = true;
    private MyReceiver MyReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mSurfaceView = findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mSeekBar = findViewById(R.id.seekBar);
        mMediaPlayer = MediaPlayer.create(this,R.raw.feifan);

        mHolder.addCallback(this);//为mHolder添加监听
        //固定位置
        mHolder.setKeepScreenOn(true);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型

        int duration = mMediaPlayer.getDuration();//获取视频的总大小
        mSeekBar.setMax(duration);//为SeekBar设置最大值
        mSeekBar.setProgress(0);//为seekBar设置初始值
        //注册广播
        MyReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("alice.phone");
        registerReceiver(MyReceiver,filter);
        //为seekBar设置监听
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //到的位置
            //b==true  是拖动的   b==false
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mMediaPlayer.seekTo(i);
                }
            }
            //开始拖动
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.pause();//暂停
            }
            //停止拖动
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.start();
            }
        });


    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_bt://播放按钮
                if(mMediaPlayer!=null && !mMediaPlayer.isPlaying()){
                    mMediaPlayer.start();//开始播放
                }

                break;
            case R.id.pause_bt://暂停按钮
                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();//暂停播放
                }
                break;

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(mMediaPlayer!=null){
            mMediaPlayer.setDisplay(surfaceHolder);//视频在SurfaceView上面展示  绑定
        }
        new MediaplayProgess().start();//启动子线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    class  MediaplayProgess  extends Thread{
        @Override
        public void run() {
            super.run();

            Intent intent = new Intent();
            intent.setAction("alice.phone");
            while (flag){
                int currentPosition = mMediaPlayer.getCurrentPosition();//获取当前进度
                intent.putExtra("pross",currentPosition);
                if(currentPosition == mMediaPlayer.getDuration()){
                    flag= false;
                }
                sendBroadcast(intent);
            }

        }
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                int pross = intent.getIntExtra("pross", -1);
                mSeekBar.setProgress(pross);//为seekbar设置进度
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(MyReceiver);
        if(mMediaPlayer!=null){
            mMediaPlayer.release();//释放资源
            mMediaPlayer = null;
        }
    }
}
