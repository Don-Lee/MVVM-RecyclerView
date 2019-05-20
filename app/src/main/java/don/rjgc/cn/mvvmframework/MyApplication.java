package don.rjgc.cn.mvvmframework;

import android.app.Application;

import java.lang.ref.WeakReference;

import don.rjgc.cn.mvvmframework.model.CountryManager;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyApplication extends Application {

    private static WeakReference<MyApplication> mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<>(this);

        CountryManager.getInstance().initData(mContext.get());

    }

    public static MyApplication getContext() {
        if (mContext == null || mContext.get() == null)
            throw new NullPointerException("Global context not init.");
        return mContext.get();
    }
}
