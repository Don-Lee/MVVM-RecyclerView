package don.rjgc.cn.mvvmframework.base.recycler;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/20
 *     desc   : RecyclerView item click event
 *     version: 1.0
 * </pre>
 */

public interface RecyclerViewClickListener {
    /**
     * 单击事件
     *
     * @param v 点击的item
     * @param position 当前行数，采用{@link BaseViewHolder#getLayoutPosition()}
     * @param layoutId item布局id{@link BaseViewHolder#getLayoutId()}
     */
    void onItemClick(View v, int position, @LayoutRes int layoutId);

    /**
     * 长按事件
     *
     * @param v 点击的item
     * @param position 当前行数，采用{@link BaseViewHolder#getLayoutPosition()}
     * @param layoutId item布局id{@link BaseViewHolder#getLayoutId()}
     * @return 是否消费事件
     */
    boolean onItemLongClick(View v, int position, @LayoutRes int layoutId);
}
