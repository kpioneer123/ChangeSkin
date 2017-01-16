package com.kpioneer.changeskin.skin;

import android.content.Context;
import android.content.SharedPreferences;

import com.kpioneer.changeskin.skin.config.Const;

/**
 * @author xionhgu
 * @version [版本号，2017/1/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class PrefUtils {

    private Context mContext ;

    public  PrefUtils(Context context)
    {
        this.mContext = context;
    }

    public void savePluginPath(String path)
    {
       SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PATH,path).apply();
    }
    public void savePluginPkg(String pkg)
    {
        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PKG,pkg).apply();
    }
    public String getPluginPath()
    {
        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PATH ,"");
    }
    public String getPluginPkg()
    {
        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PKG ,"");
    }

    public void saveSuffix(String suffix)
    {
        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_SUFFIX,suffix).apply();
    }
    public String getSuffix()
    {
        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_SUFFIX ,"");
    }

    public void clear() {

        SharedPreferences sp =  mContext.getSharedPreferences(Const.PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();

    }
}
