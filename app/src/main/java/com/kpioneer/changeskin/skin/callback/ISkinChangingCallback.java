package com.kpioneer.changeskin.skin.callback;

/**
 * @author xionhgu
 * @version [版本号，2017/1/12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

public interface ISkinChangingCallback {

    void onStart();

    void onError(Exception e);

    void onComplete();

     DefaultSkinChangingCallback DEFAULT_SKIN_CHANGING_CALLBACK = new DefaultSkinChangingCallback();

    class  DefaultSkinChangingCallback implements ISkinChangingCallback
    {


        @Override
        public void onStart() {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
