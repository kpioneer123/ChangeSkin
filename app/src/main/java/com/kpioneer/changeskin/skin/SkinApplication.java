package com.kpioneer.changeskin.skin;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author xionhgu
 * @version [版本号，2017/1/13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinApplication extends Application {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getsInstance().init(this);
    }
}
