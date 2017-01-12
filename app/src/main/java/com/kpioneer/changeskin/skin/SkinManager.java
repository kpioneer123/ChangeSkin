package com.kpioneer.changeskin.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinManager {
    private Context mcontext;
    private  static  SkinManager sInstance ;
    private ResourcesManager mResourcesManager;
    private SkinManager() {
    }
    public ResourcesManager getResourcesManager(){
        return mResourcesManager;
    }
    public   static  SkinManager getsInstance( ){

        if(sInstance == null){

            synchronized (SkinManager.class){
                if(sInstance == null){
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context){
        mcontext = context.getApplicationContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void loadPlugin(String SkinPluginPath, String SkinPluginPkg) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath",String.class);
            addAssetPathMethod.invoke(assetManager,SkinPluginPath);
            Resources superResources = mcontext.getResources();
            Resources resources = new Resources(assetManager,superResources.getDisplayMetrics(),superResources.getConfiguration());

             mResourcesManager = new ResourcesManager(resources, SkinPluginPkg);



        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
