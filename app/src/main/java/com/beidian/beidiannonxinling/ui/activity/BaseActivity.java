package com.beidian.beidiannonxinling.ui.activity;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.db.DaoMaster;
import com.beidian.beidiannonxinling.db.DaoSession;
import com.beidian.beidiannonxinling.util.StatusBarUtil;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.lzy.okgo.OkGo;

/**
 * Created by shanpu on 2017/8/15.
 * <p>
 * Describe:BaseActivity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    //Log标记
    public final String TAG = getClass().getSimpleName();
    public Context mContext;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext=this;
        //设置屏幕为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();//初始化界面
        initData();//加载数据
        initListener();//初始化监听器
        //设置状态栏颜色
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.appMainColor),0);
        setDatabase();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消与该界面相关的网络请求，防止内存泄漏
        OkGo.getInstance().cancelTag("Fragment1_1");
    }



    /**
     * 初始化界面,需要调用{@link #setContentView(int)}
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initData();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 记录上次点击按钮的时间
     **/
    private long lastClickTime;
    /**
     * 按钮连续点击最低间隔时间 单位：毫秒
     **/
    public final static int CLICK_TIME = 500;

    /**
     * 验证上次点击按钮时间间隔，防止重复点击
     */
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    /**
     * 收起键盘
     */
    public void closeInputMethod() {
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void showToast(String message) {
        ToastUtils.makeText(this, message);
    }

    public void showToastLong(String message) {
        ToastUtils.makeTextLong(this, message);
    }

    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "sq-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
