package com.kpioneer.changeskin.skin.attr;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpioneer.changeskin.skin.ResourcesManager;
import com.kpioneer.changeskin.skin.SkinManager;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public enum SkinAttrType {

    BACKGROUND("background"){
        @Override
        public void apply(View view,String resName){
            Drawable drawable =getResourcesManager().getDrawableByResName(resName);
            if(drawable!=null){
                view.setBackgroundDrawable(drawable);
            }

        }
    }
    ,SRC("src"){
        @Override
        public void apply(View view,String resName){
         Drawable drawable = getResourcesManager().getDrawableByResName(resName);
            if(view instanceof ImageView){
                ImageView imageView =(ImageView) view;
                if(drawable !=null){
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    },TEXT_COLOR("textColor"){
        @Override
        public void apply(View view,String resName){
            ColorStateList colorStateList = getResourcesManager().getColorByResName(resName);
            if(view instanceof TextView){
                TextView textView = (TextView) view;
                if(colorStateList !=null){
                    textView.setTextColor(colorStateList);
                }
            }
        }
    };

    public String getResType() {
        return resType;
    }



    String resType ;
    SkinAttrType(String type){
        resType = type;
    }
    public abstract void  apply(View view, String mResName);

    public ResourcesManager getResourcesManager(){

        return  SkinManager.getsInstance().getResourcesManager();
    }
}
