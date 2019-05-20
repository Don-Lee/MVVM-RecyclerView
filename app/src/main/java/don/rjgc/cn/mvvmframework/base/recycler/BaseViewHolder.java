package don.rjgc.cn.mvvmframework.base.recycler;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   : RecyclerView的ViewHolder基类
 *     version: 1.0
 * </pre>
 */

public class BaseViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private DB mBinding;

    @LayoutRes
    private int layoutId;

    private BaseViewHolder(DB binding, @LayoutRes int layoutId, RecyclerViewClickListener listener) {
        super(binding.getRoot());
        mBinding = binding;
        this.layoutId = layoutId;


        //添加监听事件
        itemView.setOnClickListener(view -> listener.onItemClick(view, getLayoutPosition(), getLayoutId()));
        itemView.setOnLongClickListener(v -> listener.onItemLongClick(v, getLayoutPosition(), getLayoutId()));
        //添加监听事件
//        binding.getRoot().setOnClickListener(v -> listener.onItemClick(v, getLayoutPosition(), layoutId));
//        binding.getRoot().setOnLongClickListener(v -> listener.onItemLongClick(v, getLayoutPosition(), layoutId));
    }

    /**
     * 自定义ViewHolder创建方法
     *
     * @param binding
     * @param layoutId 该条目的layout id，可用于多条目的区分
     * @param <DB>
     * @return {@link BaseViewHolder}
     */
    public static <DB extends ViewDataBinding> BaseViewHolder get(
            DB binding, @LayoutRes int layoutId, RecyclerViewClickListener listener) {
        return new BaseViewHolder(binding, layoutId, listener);
    }

    public DB getBinding() {
        return mBinding;
    }

    /**
     * 获得item布局资源id（可用于multi adapter里区别不同item）
     *
     * @return item view res id
     */
    @LayoutRes
    public int getLayoutId() {
        return layoutId;
    }

    /**
     * 获得context，建议布局里使用用此方法得到context。
     *
     * @return {@link Context}
     */
    public Context getContext() {
        return itemView.getContext();
    }
}
