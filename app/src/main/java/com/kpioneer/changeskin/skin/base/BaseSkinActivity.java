package com.kpioneer.changeskin.skin.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import com.kpioneer.changeskin.skin.SkinManager;
import com.kpioneer.changeskin.skin.attr.SkinAttr;
import com.kpioneer.changeskin.skin.attr.SkinAttrSupport;
import com.kpioneer.changeskin.skin.attr.SkinView;
import com.kpioneer.changeskin.skin.callback.ISkinChangedListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class BaseSkinActivity extends AppCompatActivity implements ISkinChangedListener {

    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();

    private final Object[] mConstructorArgs = new Object[2];

    private Method mCreateViewMethod = null;
    static  final Class<?>[] sCreateViewSignature =new Class[] {View.class,String.class,Context.class, AttributeSet.class};
    private final Object[] mCreateViewArgs = new Object[4]; //让createView 复用
    @Override
    public void onCreate(Bundle savedInstanceState) {

        SkinManager.getsInstance().registerListener(this);
        LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(mInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                //系统有没有使用setFactory
                //完成appcompat factory的工作
                AppCompatDelegate delegate =getDelegate();
                View view =null;
                List<SkinAttr> skinAttrs = null;
                try {
                    if(mCreateViewMethod==null){
                        mCreateViewMethod =  delegate.getClass().getMethod("createView" ,sCreateViewSignature); //反射方法
                    }
                    mCreateViewArgs[0] = parent;
                    mCreateViewArgs[1] = name;
                    mCreateViewArgs[2] = context;
                    mCreateViewArgs[3] = attrs;


                    view = (View) mCreateViewMethod.invoke(delegate,mCreateViewArgs);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                skinAttrs = SkinAttrSupport.getSkinAttrs(attrs,context);
                if(skinAttrs.isEmpty())  //由于CreateView被覆盖，先执行AppCompatViewInflater中的方法CreateView初始化试图
                {
                    return  null;
                }
                if(view == null){
                    view = createViewFromTag(context,name,attrs);
                }
                if(view!=null){
                    try {
                        injectSkin(view,skinAttrs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return view;
            }
        });


        super.onCreate(savedInstanceState);

    }

    private void injectSkin(View view, List<SkinAttr> skinAttrs) throws Exception {
      List<SkinView> skinViews =  SkinManager.getsInstance().getSkinViews(this);

        if(skinViews==null){
            skinViews  = new ArrayList<>();
            SkinManager.getsInstance().addSkinView(this,skinViews);
        }
        skinViews.add(new SkinView(view,skinAttrs));

        //当前是否需要自动换肤，如果需要,则换肤
        if(SkinManager.getsInstance().needChangeSkin()){
            SkinManager.getsInstance().skinChanged(this);

        }
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mCreateViewArgs[0] = context;
            mCreateViewArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mCreateViewArgs[0] = null;
            mCreateViewArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    @Override
    public void onSkinChanged() {

    }

    @Override
    protected void onDestroy() {
        SkinManager.getsInstance().unRegisterListener(this);
        super.onDestroy();

    }
}
