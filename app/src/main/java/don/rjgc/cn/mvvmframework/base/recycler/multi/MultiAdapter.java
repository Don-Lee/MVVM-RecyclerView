package don.rjgc.cn.mvvmframework.base.recycler.multi;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import java.util.List;

import don.rjgc.cn.mvvmframework.base.recycler.BaseAdapter;
import don.rjgc.cn.mvvmframework.base.recycler.BaseViewHolder;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/20
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class MultiAdapter<T> extends BaseAdapter<T, ViewDataBinding, BaseViewHolder<ViewDataBinding>> {

    /**
     * @param data     数据
     */
    public MultiAdapter(List<T> data) {
        super(-1, data);
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(position, getItem(position));
    }

    /**
     * 实现该抽象方法，得到单个item的layout id。
     *
     * @param position 当前item的position
     * @param t        position 对应的对象
     * @return layout id
     */
    public abstract int getItemLayoutId(int position, @Nullable T t);
}
