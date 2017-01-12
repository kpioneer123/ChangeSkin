package com.kpioneer.changeskin;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * @author xionhgu
 * @version [版本号，2017/1/6]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public class TestFactoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(mInflater, new LayoutInflaterFactory() {

            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.e("TAG", name);
                for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {

                    String attrName = attrs.getAttributeName(i);
                    String attrVal = attrs.getAttributeValue(i);

                    Log.e("TAG", attrName + " = " + attrVal);

                }
                /** setFactory
                 * 控制布局文件标签的View生成
                 */

                if (name.equals("TextView")) {

                    return new EditText(context, attrs);
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_factory);
    }

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

                 finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
