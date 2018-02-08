package com.zqb.vseekbar;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by XXX on 2018/2/6.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
