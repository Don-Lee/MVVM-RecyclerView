package don.rjgc.cn.mvvmframework.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class BaseActivityViewModel<ACT extends BaseActivity, DB extends ViewDataBinding> {

    //Activity
    private ACT mActivity;
    //视图绑定对象
    private DB mBinding;

    private static final int PERMISSION_REQUESTCODE = 0;


    void initViewModel(ACT activity, DB binding) {
        mActivity = activity;
        mBinding = binding;
    }


    protected abstract void onCreateViewModel(Bundle savedInstanceState);

    protected ACT getActivity() {
        return mActivity;
    }

    protected DB getBinding() {
        return mBinding;
    }

    protected void onRestartViewModel() {
        // do something...
    }

    protected void onResumeViewModel() {
        // do something...
    }

    protected void onPauseViewModel() {
        // do something...
    }

    protected void onDestroyViewModel() {
        // do something...
    }

    protected void onStopViewModel() {
        // do something...
    }

    protected void onStartViewModel() {
        // do something...
    }

    protected void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        getActivity().startActivity(intent);
    }

    protected void startActivity(Class<?> activityClass, Bundle data) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.putExtras(data);
        getActivity().startActivity(intent);
    }


    protected Context getContext() {
        return getActivity().getContext();
    }

    protected Resources getResources() {
        return getActivity().getResources();
    }


    protected void startActivityForResult(Intent intent, int requestCode) {
        getActivity().startActivityForResult(intent, requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    //final 修饰的方法不允许被子类重写
    @NonNull
    protected final String getString(@StringRes int resId) {
        return getActivity().getString(resId);
    }

    @NonNull
    protected final String getString(@StringRes int resId, Object... formatArgs) {
        return getActivity().getString(resId, formatArgs);
    }


    protected void onSaveInstanceState(Bundle outState) {
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    }


    protected void checkPermissions(String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (needRequestPermissionList != null &&
                needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(getActivity(), needRequestPermissionList.toArray(
                    new String[needRequestPermissionList.size()]), PERMISSION_REQUESTCODE);
        }
    }

    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUESTCODE) {
            if (!verifyPermissions(grantResults)) {
                showMissingPermissionDialog();
            }
        }
    }

    //获取权限集中需要申请权限的列表
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), perm) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    //监测是否所有的权限都已授权
    private boolean verifyPermissions(int[] grantResults) {
        boolean res = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                res = false;
            }
        }
        return res;
    }

    //权限提示框
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击'设置-‘权限’-打开所需权限'");
        builder.setNegativeButton("取消", (dialog, which) -> getActivity().finish());
        builder.setNegativeButton("设置", ((dialog, which) -> startAppSettings()));
    }

    //跳转手机设置界面
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        getActivity().startActivity(intent);
    }

}
