package com.kpioneer.changeskin;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.kpioneer.changeskin.skin.ResourcesManager;
import com.nineoldandroids.view.ViewHelper;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.id_listview)
    ListView idListview;
    @BindView(R.id.id_drawerLayout)
    DrawerLayout idDrawerLayout;

    private String[] mDatas = new String[]{"Activity","Service","Activity","Service","Activity","Service","Activity","Service"};

    private LeftMenuAdapter leftMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initView() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.id_left_menu_container);
        if(fragment == null){
            fm.beginTransaction().add(R.id.id_left_menu_container, new MeauLeftFragment()).commit();
        }

        leftMenuAdapter = new LeftMenuAdapter(this);
        leftMenuAdapter.setData(Arrays.asList(mDatas));
        idListview.setAdapter(leftMenuAdapter);

    }

    private void initEvent() {
        idDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = idDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                if(drawerView.getTag().equals("LEFT")){

                    float leftScale = 1 - 0.3f * scale;

                    //设置左边菜单滑动后的占据屏幕大小
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    //设置菜单透明度
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));

                    //设置内容界面水平和垂直方向偏转量
                    //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                    ViewHelper.setTranslationX(mContent,
                            mMenu.getMeasuredWidth() * (1 - scale));
                    //设置内容界面操作无效（比如有button就会点击无效）
                    mContent.invalidate();
                    //设置右边菜单滑动后的占据屏幕大小
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private String mSkinPluginPath  = Environment.getExternalStorageDirectory()+ File.separator +"skin_plugin.apk";
    private String mSkinPluginPkg = "com.kpioneer.skinplugin";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.meau_main,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
        switch (id)
        {
            case R.id.action_changeSkin:

                loadPlugin(mSkinPluginPath,mSkinPluginPkg);
                break;
            case R.id.action_testFactory:
                Intent intent = new Intent(this,TestFactoryActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void loadPlugin(String SkinPluginPath, String SkinPluginPkg) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath",String.class);
            addAssetPathMethod.invoke(assetManager,SkinPluginPath);
            Resources superResources = getResources();
            Resources resources = new Resources(assetManager,superResources.getDisplayMetrics(),superResources.getConfiguration());

            ResourcesManager resourcesManager = new ResourcesManager(resources,SkinPluginPkg);
            Drawable drawable =resourcesManager.getDrawableByResName("skin_main_bg");


            if(drawable!=null){
                idDrawerLayout.setBackground(drawable);
            }

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
