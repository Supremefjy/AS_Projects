package com.jikexueyuan.fangxiaoyao;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xiaoyaoxianzhidao on 16/1/16.
 */
public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
