package com.kpioneer.changeskin.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.system.ErrnoException;

/**
 * @author xionhgu
 * @version [版本号，2017/1/5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class ResourcesManager {

    private Resources mResources;
    private String  mPkgName;

    public ResourcesManager(Resources resources,String pkgName){
        mResources = resources;
        mPkgName =  pkgName;

    }
    public Drawable getDrawableByResName(String name){

        try {
            return  mResources.getDrawable(mResources.getIdentifier(name,"drawable",mPkgName));
        }catch (Exception e)
        {  e.printStackTrace();
            return  null;
        }

    }
    public ColorStateList getColorByResName(String name){
        try {
            return  mResources.getColorStateList(mResources.getIdentifier(name,"color",name));
        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }

    }
}
