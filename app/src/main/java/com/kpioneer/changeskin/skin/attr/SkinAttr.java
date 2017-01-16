package com.kpioneer.changeskin.skin.attr;

import android.view.View;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinAttr {




    private String resName;
    private SkinAttrType type;

    public SkinAttr(String resName, SkinAttrType type) {
        this.resName = resName;
        this.type = type;
    }

    public void apply(View view) {
        type.apply(view,resName);
    }


}
