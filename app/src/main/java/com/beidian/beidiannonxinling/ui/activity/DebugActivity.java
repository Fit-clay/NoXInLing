package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.PreferencesHelper;

/**
 * Created by Eric on 2017/9/18.
 */

public class DebugActivity extends BaseActivity {
    EditText serviceAddress;
    Switch aSwitch;
    Button save;
    @Override
    protected void initView() {
    setContentView(R.layout.activity_debug);
    serviceAddress=findView(R.id.edt_service);
    aSwitch=findView(R.id.sw_debug);
    save=findView(R.id.btn_save);
    }

    @Override
    protected void initData() {
        serviceAddress.setText(Const.Url.SERVER);
     boolean flag= PreferencesHelper.getBoolean(mContext,Const.Preferences.DEBUG_SWITCH,false);
        if(flag){
            aSwitch.setChecked(true);
        }
    }

    @Override
    protected void initListener() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.Url.setSERVER(serviceAddress.getText().toString());
                if(aSwitch.isChecked()){
                    PreferencesHelper.put(mContext,Const.Preferences.DEBUG_SWITCH,true);
                }else {
                    PreferencesHelper.put(mContext,Const.Preferences.DEBUG_SWITCH,false);
                }
                PreferencesHelper.put(mContext,Const.Preferences.DEFULT_SERVICE_PATH,serviceAddress.getText().toString());

                startActivity(new Intent(mContext,LoginActivity.class));
            }
        });
    }
}
