package don.rjgc.cn.mvvmframework.viewmodel;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import don.rjgc.cn.mvvmframework.R;
import don.rjgc.cn.mvvmframework.base.recycler.BaseAdapter;
import don.rjgc.cn.mvvmframework.base.recycler.BaseViewHolder;
import don.rjgc.cn.mvvmframework.base.recycler.multi.MultiAdapter;
import don.rjgc.cn.mvvmframework.base.recycler.single.SingleAdapter;
import don.rjgc.cn.mvvmframework.databinding.ItemGridviewBinding;
import don.rjgc.cn.mvvmframework.databinding.ItemMultiBinding;
import don.rjgc.cn.mvvmframework.databinding.ItemSingleBinding;
import don.rjgc.cn.mvvmframework.model.CountryManager;
import don.rjgc.cn.mvvmframework.model.bean.Country;
import don.rjgc.cn.mvvmframework.view.MainActivity;
import don.rjgc.cn.mvvmframework.base.BaseActivityViewModel;
import don.rjgc.cn.mvvmframework.databinding.ActivityMainBinding;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MainActivityViewModel extends BaseActivityViewModel<MainActivity, ActivityMainBinding> {
    private static final String TAG = "MainActivityViewModel";
    private BaseAdapter<Country, ItemSingleBinding, BaseViewHolder<ItemSingleBinding>> mAdapter;

    @Override
    protected void onCreateViewModel(Bundle savedInstanceState) {
        initSingleRecyclerView();
    }

    public void initSingleRecyclerView() {
        mAdapter = new BaseAdapter<Country, ItemSingleBinding,BaseViewHolder<ItemSingleBinding>>(R.layout.item_single,
                CountryManager.getInstance().getCountryList()) {
            @Override
            public void bindData(int position, Country country, ItemSingleBinding binding, BaseViewHolder<ItemSingleBinding> holder) {
                binding.setModel(country);
                binding.countryName.setOnClickListener((view) -> {
                    Toast.makeText(getActivity(), country.getCountryNameCn(), Toast.LENGTH_SHORT).show();
                });

                binding.btnDel.setOnClickListener(v -> Toast.makeText(getActivity(), "del:" + country.getCountryNameCn(), Toast.LENGTH_SHORT).show());
                binding.btnUpdate.setOnClickListener(v -> Toast.makeText(getActivity(), "update:" + country.getCountryNameCn(), Toast.LENGTH_SHORT).show());


                binding.swipeContent.setOnClickListener(v -> Toast.makeText(getActivity(), "item:" + country.getCountryNameCn(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onItemClick(View v, int position, int layoutId) {
//                Log.e("TAG", CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
//                        CountryManager.getInstance().getCountryList().get(position).getCountryNameEn());
                //使用SwipeMenuLayout包裹布局实现侧滑时此方法被拦截，如果想实现类似Item的点击事件请在bindData中给控件添加click事件
                Toast.makeText(getActivity(), CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
                        CountryManager.getInstance().getCountryList().get(position).getCountryNameEn(), Toast.LENGTH_SHORT).show();
            }

        };

        //添加水平分割线
        getBinding().contentMain.recyclerview.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getBinding().setAdapter(mAdapter);
        getBinding().setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    public void initMultiRecyclerView() {
        MultiAdapter<Country> adapter=new MultiAdapter<Country>(
                CountryManager.getInstance().getCountryList()) {
            @Override
            public int getItemLayoutId(int position, @Nullable Country country) {
                if ("阿富汗".equals(country.getCountryNameCn()) || "中国".equals(country.getCountryNameCn())) {
                    return R.layout.item_multi;
                } else {
                    return R.layout.item_single;
                }
            }

            @Override
            public void bindData(int position, Country country, ViewDataBinding binding, BaseViewHolder<ViewDataBinding> holder) {
                if (binding instanceof ItemSingleBinding) {
                    ((ItemSingleBinding) binding).setModel(country);
                } else {
                    ((ItemMultiBinding) binding).setModel(country);
                }
            }

            @Override
            public void onItemClick(View v, int position, int layoutId) {
                Toast.makeText(getActivity(), CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
                        CountryManager.getInstance().getCountryList().get(position).getCountryNameEn(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View v, int position, int layoutId) {
                Toast.makeText(getActivity(), CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
                        CountryManager.getInstance().getCountryList().get(position).getCountryCode(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        //添加水平分割线
        getBinding().contentMain.recyclerview.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getBinding().setAdapter(adapter);
        getBinding().setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initGridView() {
        SingleAdapter<Country, ItemGridviewBinding> adapter = new SingleAdapter<Country, ItemGridviewBinding>(R.layout.item_gridview,
                CountryManager.getInstance().getCountryList()) {
            @Override
            public void bindData(int position, Country country, ItemGridviewBinding binding, BaseViewHolder<ItemGridviewBinding> holder) {
                binding.setModel(country);
                binding.countryName.setOnClickListener((view) -> {
                    Toast.makeText(getActivity(), country.getCountryNameCn(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onItemClick(View v, int position, int layoutId) {
//                Log.e("TAG", CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
//                        CountryManager.getInstance().getCountryList().get(position).getCountryNameEn());

                Toast.makeText(getActivity(), CountryManager.getInstance().getCountryList().get(position).getCountryNameCn() +
                        CountryManager.getInstance().getCountryList().get(position).getCountryNameEn(), Toast.LENGTH_SHORT).show();
            }

        };
        //添加水平分割线
        getBinding().contentMain.recyclerview.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //添加水平分割线
        getBinding().contentMain.recyclerview.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        getBinding().setAdapter(adapter);
        getBinding().setLayoutManager(new GridLayoutManager(getActivity(), 3));

    }
}
