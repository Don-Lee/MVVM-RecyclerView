package don.rjgc.cn.mvvmframework.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import don.rjgc.cn.mvvmframework.model.bean.Country;
import don.rjgc.cn.mvvmframework.util.Utils;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   : 静态内部类单例模式
 *     version: 1.0
 * </pre>
 */

public class CountryManager {
    private List<Country> mCountryList;
    private CountryManager() {
        mCountryList = new ArrayList<>();
    }

    private static class SingletonHolder{
        private static final CountryManager INSTANCE = new CountryManager();
    }

    public static CountryManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initData(Context context) {
        String json = Utils.getTextFromAssets(context, "countrycode.json");
        mCountryList = new Gson()
                .fromJson(json, new TypeToken<List<Country>>() {
                }.getType());
    }

    public List<Country> getCountryList() {
        return mCountryList;
    }

    /**
     * @return 按国家id正序
     */
    public Comparator<Country> comparatorIdAcs() {
        return (t1, t2) -> t1.getCountryId() - t2.getCountryId();
    }
}
