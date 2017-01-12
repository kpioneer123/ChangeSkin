package com.kpioneer.changeskin.skin.attr;

import android.view.View;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinAttr {




    private String mResName;
    private SkinAttrType mType;

    public SkinAttr(String ResName, SkinAttrType Type) {
        mResName = ResName;
        mType = Type;
    }

    public void apply(View view) {
        mType.apply(view,mResName);
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String ResName) {
        this.mResName = ResName;
    }

    public SkinAttrType getmType() {
        return mType;
    }

    public void setType(SkinAttrType Type) {
        this.mType = Type;
    }
}
