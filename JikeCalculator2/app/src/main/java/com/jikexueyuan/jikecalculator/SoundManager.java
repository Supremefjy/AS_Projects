package com.jikexueyuan.jikecalculator;

import android.content.Context;
import android.media.SoundPool;
import java.lang.Runnable;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by xiaoyaoxianzhidao on 15/12/31.
 */
public class SoundManager {
    public static final String TAG = SoundManager.class.getSimpleName();

    private SoundManager() {
    }

    private static SoundManager sMe;
//避免多线程同步问题
    public synchronized static SoundManager getInstance() {
        if (sMe == null) {
            sMe = new SoundManager();

        }
        return sMe;
    }

    private static final int[] RES = {
            R.raw.one,
            R.raw.two,
            R.raw.three,
            R.raw.four,
            R.raw.five,
            R.raw.six,
            R.raw.seven,
            R.raw.eight,
            R.raw.nine,
            R.raw.ac,
            R.raw.del,
            R.raw.div,
            R.raw.del,
            R.raw.plus,
            R.raw.mul,
            R.raw.minus,
            R.raw.equal,
            R.raw.dot,
            R.raw.zero
    };
    private Context mContext;

    private boolean mPlaying;
    //成员变量
    private int mCurrentStreamId;

    private SoundPool mSoundPool;


    private HashMap<String, Sound> mSoundMap;

    private Vector<String> mSoundQueue = new Vector<>();


    public void initSounds(Context context) {
        this.mContext = context;
        this.mSoundPool = new SoundPool(RES.length,3,0);

        this.mPlaying = false;
        this.mSoundMap=new HashMap<>();



        addSound(mContext.getString(R.string.digit1), R.raw.one, 274);
        addSound(mContext.getString(R.string.digit2), R.raw.two, 274);
        addSound(mContext.getString(R.string.digit3), R.raw.three, 274);
        addSound(mContext.getString(R.string.digit4), R.raw.four, 274);
        addSound(mContext.getString(R.string.digit5), R.raw.five, 274);
        addSound(mContext.getString(R.string.digit6), R.raw.six, 274);
        addSound(mContext.getString(R.string.digit7), R.raw.seven, 274);
        addSound(mContext.getString(R.string.digit8), R.raw.eight, 274);
        addSound(mContext.getString(R.string.digit9), R.raw.nine, 274);
        addSound(mContext.getString(R.string.digit0), R.raw.zero, 274);
        addSound(mContext.getString(R.string.clear), R.raw.ac, 274);
        addSound(mContext.getString(R.string.del), R.raw.del, 274);
        addSound(mContext.getString(R.string.plus), R.raw.plus, 274);
        addSound(mContext.getString(R.string.mul), R.raw.mul, 274);
        addSound(mContext.getString(R.string.minus), R.raw.minus, 274);
        addSound(mContext.getString(R.string.div), R.raw.div, 274);
        addSound(mContext.getString(R.string.equal), R.raw.equal, 274);
        addSound(mContext.getString(R.string.dot), R.raw.dot, 274);


    }



public void  unloadAll(){

    stopSound();
    if(this.mSoundMap.size()>0){

//        Iterator<String> iterator = mSoundMap.keySet().iterator();

        ListIterator<String> listIterator = (ListIterator<String>) mSoundMap.keySet().iterator();
        while (listIterator.hasNext()) {


            String str = listIterator.next();
            Sound sound = mSoundMap.get(str);
            this.mSoundPool.unload(sound.id);

        }
    }

}
    public  void cleanUp()
    {
        unloadAll();
        this.mSoundPool.release();
        this.mSoundPool=null;
        sMe=null;

    }

    //循环播放
    private Handler mHandler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord record) {

        }
    } ;
    private Runnable mPlayNext = new Runnable() {
        @Override
        public void run() {
            SoundManager.this.mSoundPool.stop(SoundManager.this.mCurrentStreamId);
            SoundManager.this.playNextSound();
        }
    };

    private void playNextSound() {

        if (mSoundQueue.isEmpty()) {
            return;
        }

        String str = mSoundQueue.remove(0);//获取第一个字符

        Sound sound = mSoundMap.get(str);

        if (sound != null) {

            mCurrentStreamId = mSoundPool.play(sound.id, 0.2F, 0.2F, 1, 0, 1.0F);

            this.mPlaying = true;


            this.mHandler(mPlayNext.sound.time);
        }
    }

    public void PlaySound(String text) {
        stopSound();
        this.mSoundQueue.add(text);
        playNextSound();

    }

    public void playSeqSounds(String[] soundsToPlay) {
        int len = soundsToPlay.length;
        for (int i = 0; ; i++) {
            if (i >= len) {
                if (!this.mPlaying)
                    playNextSound();
                return;

            }
            String str = soundsToPlay[i];
            this.mSoundQueue.add(str);

        }


    }


    public void stopSound() {


       this.mHandler.removeCallbacks(this.mPlayNext);
        this.mSoundQueue.clear();
        this.mSoundPool.stop(this.mCurrentStreamId);
        this.mPlaying = false;

    }

    private void addSound(String text, int resId, int time) {

        Sound localSound = new Sound(this.mSoundPool.load(this.mContext.resId, 1), time);

        this.mSoundMap.put(text, localSound);

    }

    private final class Sound {
        //内部类
        public int id;
        public int time;

        public Sound(int id, int time) {
            this.id = id;
            this.time = time;


        }


    }


}