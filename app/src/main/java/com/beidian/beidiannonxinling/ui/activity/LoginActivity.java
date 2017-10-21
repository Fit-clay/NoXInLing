package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.LoginInfoBean;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.util.JsonUtils;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.PermissionUtil;
import com.beidian.beidiannonxinling.util.PreferencesHelper;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    public PermissionUtil.PermissionGrant mPermissionGrant = new PermissionUtil.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
        }

    };
    protected String username;
    protected String password;
    EditText tv_name, tv_password;
    Button bt_login;
    private TextView textView;
    private int index = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        initePermission();
        tv_name = findView(R.id.user);
        tv_password = findView(R.id.password);
        bt_login = findView(R.id.bt_login);
        textView = findView(R.id.textView);
    }

    @Override
    protected void initData() {
        LoginInfoBean bean = PreferencesHelper.getBean(mContext, Const.Preferences.LOGIN_INFO, LoginInfoBean.class);
        if (bean != null) {
            username = bean.getName();
            password = bean.getPassword();
            tv_name.setText(username);
            tv_password.setText(password);
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                requestfortheLogin(username, password);
            }
        }
    }
    private boolean isQuit = false;

    @Override
    public void onBackPressed() {

        if (!isQuit) {
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            isQuit = true;

            //这段代码意思是,在两秒钟之后isQuit会变成false
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        isQuit = false;
                    }
                }
            }).start();


        } else {
            System.exit(0);
        }
    }
    @Override
    protected void initListener() {
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_name.getText() != null && tv_password.getText() != null) {
                    requestfortheLogin(tv_name.getText().toString(), tv_password.getText().toString());
                } else {
                    ToastUtils.makeText(LoginActivity.this, "用户名或者密码不能为空");
                }
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                index++;
                if (index == 3) {
                    startActivity(new Intent(mContext, DebugActivity.class));
                }
                return false;
            }
        });
    }

    private void requestfortheLogin(String username, final String password) {

        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("password", password);
        OkGo.post(Const.Url.URL_LOGIN).tag(LoginActivity.this).params(params).execute(new ResultCallback(LoginActivity.this, true) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {
                ToastUtils.makeText(LoginActivity.this, "登入失败" + response);


            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.i(TAG, "网络请求结果：" + s);
                UserInfoBean userInfoBean = JsonUtils.getUserInforBean(s);
                if (userInfoBean != null) {
                    LogUtils.i(TAG, "网络请求结果：" + userInfoBean.getMsg());
                    if (userInfoBean.getMsg() != null) {
                        if (userInfoBean.getMsg().equals("用户名或密码错误")) {
                            ToastUtils.makeText(LoginActivity.this, "用户名或密码错误");
                        }
                    } else if (userInfoBean.getOffice() != null && userInfoBean.getTelphone() != null && userInfoBean.getUsername() != null) {
                        ToastUtils.makeText(mContext, s);
                        PreferencesHelper.put(mContext, Const.Preferences.LOGIN_INFO, new LoginInfoBean(tv_name.getText().toString().trim(), tv_password.getText().toString().trim()));

                        PreferencesHelper.put(mContext,Const.Preferences.USER_INFO,userInfoBean);
                        BaseApplication.setUserInfo(userInfoBean);

                        Intent intent = new Intent();
                        intent.putExtra("avatar", userInfoBean.getAvatar());
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.makeText(LoginActivity.this, "发生意错误");
                    }
                }
            }
        });
    }


    public void initePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionUtil.requestMultiPermissions(this, mPermissionGrant, new int[]{
                    PermissionUtil.CODE_READ_EXTERNAL_STORAGE,
                    PermissionUtil.CODE_CAMERA,
                    PermissionUtil.CODE_CALL_PHONE,
                    PermissionUtil.CODE_LOCATION
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //requestCode=100，一次性申请多个权限
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] < 0) {
                initePermission();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
