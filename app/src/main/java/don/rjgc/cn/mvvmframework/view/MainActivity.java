package don.rjgc.cn.mvvmframework.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import don.rjgc.cn.mvvmframework.R;
import don.rjgc.cn.mvvmframework.base.BaseActivity;
import don.rjgc.cn.mvvmframework.databinding.ActivityMainBinding;
import don.rjgc.cn.mvvmframework.viewmodel.MainActivityViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getDataBinding().toolbarLayout.toolbar.setTitle("");
        getDataBinding().toolbarLayout.toolbarTitle.setText("基于MVVM下的RecyclerView");
        setSupportActionBar(getDataBinding().toolbarLayout.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_single) {
            getViewModel().initSingleRecyclerView();
            return true;
        } else if (id == R.id.action_multi) {
            getViewModel().initMultiRecyclerView();
        } else if (id == R.id.action_gridview) {
            getViewModel().initGridView();
        }

        return super.onOptionsItemSelected(item);
    }
}
