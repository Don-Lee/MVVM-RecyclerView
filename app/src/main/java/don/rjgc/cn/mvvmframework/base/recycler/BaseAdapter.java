package don.rjgc.cn.mvvmframework.base.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   : RecyclerView基础Adapter
 *     version: 1.0
 *
 *     @param <T>  数据类型
 *     @param <DB> 内容布局绑定类
 *     @param <VH> ViewHold类
 * </pre>
 */

public abstract class BaseAdapter<T, DB extends ViewDataBinding, VH extends BaseViewHolder<DB>>
        extends RecyclerView.Adapter<VH> implements RecyclerViewClickListener {

    private Context mContext;
    @LayoutRes
    private int layoutId;

    private List<T> mData;

    /**
     * 实现该抽象方法，完成数据的绑定。
     *
     * @param position 当前item的position
     * @param t        position 对应的数据
     * @param binding  {@link DB}
     * @param holder   {@link VH}
     */
    public abstract void bindData(int position, T t, DB binding, VH holder);

    /**
     * @param layoutId adapter需要的布局资源id
     * @param data     数据
     */
    public BaseAdapter(@LayoutRes int layoutId, List<T> data) {
        this.layoutId = layoutId;
        mData = data;
        //解决notifyDataSetChanged时导致图片闪烁;另外getItemId方法必须return postion
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public VH onCreateViewHolder(ViewGroup parent, @LayoutRes int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DB binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return (VH) VH.get(binding, viewType, this);
    }

    //重写onBindViewHolder3个参数的方法可实现局部刷新，避免图片闪烁
    @Override
    public void onBindViewHolder(VH holder, int position) {
        mContext = holder.getContext();
        DB binding = holder.getBinding();
        binding.getRoot().setTag(holder.getLayoutPosition());
        bindData(position, getItem(position), binding, holder);
        //当变量或者 observable 发生变动时，会在下一帧触发 binding。有时候 binding 需要马上执行，这时候可以使用 executePendingBindings()。
        binding.executePendingBindings();
    }

    /**
     * @return item数量
     */
    @Override
    public int getItemCount() {
        int size = 0;
        if (mData != null) {
            size = mData.size();
        }
        return size;
    }

    /**
     *  多布局类型时使用layout id 判断布局类型
     * @param position 当前行数
     * @return layout id
     */
    @LayoutRes
    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }


    /**
     * 单击事件
     *
     * @param v 点击的item
     * @param position 当前行数，采用{@link VH#getLayoutPosition()}
     * @param layoutId item布局id{@link VH#getLayoutId()}
     */
    @Override
    public void onItemClick(View v, int position, int layoutId) {

    }

    /**
     * 长按事件
     *
     * @param v 点击的item
     * @param position 当前行数，采用{@link VH#getLayoutPosition()}
     * @param layoutId item布局id{@link VH#getLayoutId()}
     * @return 是否消费事件
     */
    @Override
    public boolean onItemLongClick(View v, int position, int layoutId) {
        return false;
    }

    /**
     * @param position item索引
     * @return 获得item数据封装
     */
    protected T getItem(int position) {
        if (mData != null && mData.size() > 0) {
            return mData.get(position);
        }
        return null;
    }
}
