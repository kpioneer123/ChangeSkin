package com.kpioneer.changeskin.skin.attr;

import android.view.View;

import java.util.List;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class SkinView {
    private View mView;

    private List<SkinAttr> mAttrs;

    public SkinView(View view,List<SkinAttr> attrs) throws Exception {
        if(view == null)
        {
            throw new Exception("view cant not be null");  //开发中的输入合法性检验
        }else {
            mView  = view;
        }
        if(attrs == null)
        {
            throw new Exception("List<SkinAttr> cant not be null");
        }else {
            mAttrs  = attrs;
        }
    }

    public View getView() {
        return mView;
    }

    public void setView(View View) {
        this.mView = View;
    }

    public void apply(){
        if (mView == null) return;
        for(SkinAttr attr : mAttrs){
            attr.apply(mView);
        }
    }
}
