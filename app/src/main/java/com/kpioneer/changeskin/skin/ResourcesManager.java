package com.kpioneer.changeskin.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.system.ErrnoException;
import android.text.TextUtils;

/**
 * @author xionhgu
 * @version [版本号，2017/1/5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class ResourcesManager {

    private Resources mResources;
    private String  mPkgName;
    private String  mSufix;

    public ResourcesManager(Resources resources,String pkgName ,String suffix){
        mResources = resources;
        mPkgName =  pkgName;

        if(suffix == null){
            suffix = "";
        }
        mSufix = suffix;

    }
    public Drawable getDrawableByResName(String name){

        name = appendSuffix(name);
        try {
            return  mResources.getDrawable(mResources.getIdentifier(name,"drawable",mPkgName));
        }catch (Exception e)
        {  e.printStackTrace();
            return  null;
        }

    }

    private String appendSuffix(String name) {

        if(TextUtils.isEmpty(mSufix)){
            name += "_"+mSufix;
        }
        return name;
    }

    public ColorStateList getColorByResName(String name){
        name = appendSuffix(name);
        try {
            return  mResources.getColorStateList(mResources.getIdentifier(name,"color",name));
        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }

    }
}
