package don.rjgc.cn.mvvmframework.base.recycler.single;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import don.rjgc.cn.mvvmframework.base.recycler.BaseAdapter;
import don.rjgc.cn.mvvmframework.base.recycler.BaseViewHolder;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class SingleAdapter<T, DB extends ViewDataBinding> extends BaseAdapter<T, DB, BaseViewHolder<DB>> {


    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    public SingleAdapter(int layoutId, List<T> data) {
        super(layoutId, data);
    }

}
