package com.kpioneer.changeskin.skin;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.kpioneer.changeskin.skin.attr.SkinView;
import com.kpioneer.changeskin.skin.base.BaseSkinActivity;
import com.kpioneer.changeskin.skin.callback.ISkinChangedListener;
import com.kpioneer.changeskin.skin.callback.ISkinChangingCallback;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private Map<ISkinChangedListener,List<SkinView>> mSkinViewMaps = new HashMap<ISkinChangedListener,List<SkinView>>();
    private List<ISkinChangedListener> mListener =new ArrayList<ISkinChangedListener>();


    private PrefUtils mPrefUtils;

    /**
     * 换肤资源后缀
     */
    private String mCurPath;
    private String mCurPkg;
    private String mSuffix="";

    private SkinManager() {
    }

    public ResourcesManager getResourcesManager(){
        if(!usePlugin()){

            return  new ResourcesManager(mcontext.getResources(),mcontext.getPackageName(),mSuffix);
        }
        return mResourcesManager;
    }


    public   static  SkinManager getsInstance(){

        if(sInstance == null){

            synchronized (SkinManager.class){
                if(sInstance == null){
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void init(Context context){
        mcontext   = context.getApplicationContext();
        mPrefUtils = new PrefUtils(mcontext);


        try{
        String pluginPath = mPrefUtils.getPluginPath();
        String pluginPkg = mPrefUtils.getPluginPkg();
            mSuffix = mPrefUtils.getSuffix();
        File file = new File(pluginPath);
        if(file.exists()){
            loadPlugin(pluginPath,pluginPkg);
        }
        } catch (Exception e)
        {
            e.printStackTrace();
            mPrefUtils.clear();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void loadPlugin(String skinPluginPath, String skinPluginPkg) {
        if(skinPluginPath.equals(mCurPath) && skinPluginPkg.equals(mCurPkg))return;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath",String.class);
            addAssetPathMethod.invoke(assetManager,skinPluginPath);
            Resources superResources = mcontext.getResources();
            Resources resources = new Resources(assetManager,superResources.getDisplayMetrics(),superResources.getConfiguration());

             mResourcesManager = new ResourcesManager(resources, skinPluginPkg,null);

            mCurPath  = skinPluginPath;
            mCurPkg   = skinPluginPkg;

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

    public List<SkinView> getSkinViews(BaseSkinActivity listener)
    {
        return  mSkinViewMaps.get(listener);
    }

    public void addSkinView(ISkinChangedListener listener,List<SkinView> views){
        mSkinViewMaps.put(listener,views);
    }

    //换肤注册监听防止 内存泄漏
    public void registerListener(ISkinChangedListener listener)
    {
        mListener.add(listener);
    }
    public void unRegisterListener(ISkinChangedListener listener)
    {
        mListener.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    public void changeSkin(String suffix)
    {
        clearPluginInfo();
        mSuffix = suffix;
        mPrefUtils.saveSuffix(suffix);
        notifyChangedLisener();
    }

    private void clearPluginInfo() {
        mCurPath  = null;
        mCurPkg   = null;
        mSuffix   = null;
        mPrefUtils.clear();
    }

    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, ISkinChangingCallback callback) {
        if(callback == null){
            callback = ISkinChangingCallback.DEFAULT_SKIN_CHANGING_CALLBACK;
        }
        final ISkinChangingCallback finalCallback = callback;
        new AsyncTask<Void,Void,Integer>(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath,skinPluginPkg);
                }catch (Exception e){
                    e.printStackTrace();
                    finalCallback.onError(e);
                    return -1;
                }

                return 0;
            }

            @Override
            protected void onPostExecute(Integer code) {


                if(code == -1){
                    finalCallback.onError(null);
                    return;
                }
                try {
                    notifyChangedLisener();
                    finalCallback.onComplete();

                    updatePluginInfo(skinPluginPath,skinPluginPkg);
                }catch (Exception e){
                    e.printStackTrace();
                    finalCallback.onError(e);

                }

            }
        }.execute();
    }

    private void updatePluginInfo(String path, String pkg) {

        mPrefUtils.savePluginPath(path);
        mPrefUtils.savePluginPkg(pkg);
    }



    private void notifyChangedLisener() {

        for(ISkinChangedListener listener : mListener)
        {
            skinChanged(listener);
            listener.onSkinChanged();
        }
    }

    public void skinChanged(ISkinChangedListener listener) {
        List<SkinView> skinViews = mSkinViewMaps.get(listener);
        for(SkinView skinView :skinViews)
        {
            skinView.apply();
        }

    }

    public boolean needChangeSkin() {

        return usePlugin()||useSuffix();
    }
    public boolean usePlugin()
    {
        return mCurPath !=null && !mCurPath.trim().equals("");
    }
    public boolean useSuffix()
    {
        return mSuffix !=null && !mSuffix.trim().equals("");
    }
}
