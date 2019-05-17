package don.rjgc.cn.mvvmframework.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import don.rjgc.cn.mvvmframework.BR;


/**
 * Activity 基类
 */
public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseActivityViewModel>
        extends AppCompatActivity {

    //视图绑定对象
    private DB mDataBinding;
    //ViewModel模型对象
    private VM mViewModel;

    /**
     * 获取视图id
     * @return layout资源id
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     */
    public abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());

        //getGenericSuperclass()返回本类的父类,并且包含泛型参数信息
        Type genType = getClass().getGenericSuperclass();
        //ParameterizedType参数化类型，即泛型
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class<VM> genClass = (Class<VM>) params[1];//获取到ViewModel
        try {
            mViewModel = genClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        //给xml中声明的viewModel变量赋值
        mDataBinding.setVariable(BR.viewModel, mViewModel);

        //初始化视图
        initView();

        //初始化viewmodel
        mViewModel.initViewModel(this, getDataBinding());
        mViewModel.onCreateViewModel(savedInstanceState);
    }

    public DB getDataBinding() {
        return mDataBinding;
    }

    public void setDataBinding(DB dataBinding) {
        mDataBinding = dataBinding;
    }

    public VM getViewModel() {
        return mViewModel;
    }

    public void setViewModel(VM viewModel) {
        mViewModel = viewModel;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewModel.onRestartViewModel();
    }

    @Override
    protected void onStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mViewModel.onStartViewModel();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResumeViewModel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onPauseViewModel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStopViewModel();
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroyViewModel();
        mDataBinding.unbind();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    public Context getContext() {
        return this;
    }

    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    getDataBinding().getRoot().setClickable(true);
                    getDataBinding().getRoot().setFocusable(true);
                    getDataBinding().getRoot().setFocusableInTouchMode(true);
                    getDataBinding().getRoot().requestFocusFromTouch();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
