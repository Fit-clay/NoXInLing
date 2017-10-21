package com.beidian.beidiannonxinling.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ColorConfigUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */

public class ColorConfigDialog extends Dialog implements CompoundButton.OnCheckedChangeListener {
    Context context;
    private OnClickListener positiveButtonClickListener;
    private OnClickListener negativeButtonClickListener;
    CheckBox check_pcl,check_rsrp,check_sinr,check_dl,check_ul;
    Button btn_gis_dialog_con,btn_gis_dialog;
    List<CheckBox> list;
    public int checkValue=-1;
    public ColorConfigDialog(@NonNull Context context) {
        super(context, R.style.GisDialog);

    }
    public OnClickListener getPositiveButtonClickListener() {
        return positiveButtonClickListener;
    }

    public void setPositiveButtonClickListener(OnClickListener positiveButtonClickListener) {
        this.positiveButtonClickListener = positiveButtonClickListener;
    }

    public OnClickListener getNegativeButtonClickListener() {
        return negativeButtonClickListener;
    }

    public void setNegativeButtonClickListener(OnClickListener negativeButtonClickListener) {
        this.negativeButtonClickListener = negativeButtonClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_dialog);
        list=new ArrayList<>();
        final Window dialogWindow = getWindow();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.45);
        p.width = (int) (d.getWidth() * 0.9);
        p.gravity = Gravity.CENTER;
        //        p.alpha = 0.5f;
        dialogWindow.setAttributes(p);
        check_pcl=(CheckBox)findViewById(R.id.check_pcl);
        check_rsrp=(CheckBox)findViewById(R.id.check_rsrp);
        check_sinr=(CheckBox)findViewById(R.id.check_sinr);
        check_dl=(CheckBox)findViewById(R.id.check_dl);
        check_ul=(CheckBox)findViewById(R.id.check_ul);
        btn_gis_dialog_con = (Button) findViewById(R.id.btn_gis_dialog_con);
        btn_gis_dialog = (Button) findViewById(R.id.btn_gis_dialog);
        list.add(check_pcl);
        list.add(check_rsrp);
        list.add(check_sinr);
        list.add(check_dl);
        list.add(check_ul);
        check_pcl.setOnCheckedChangeListener(this);

        check_rsrp.setOnCheckedChangeListener(this);


        check_sinr.setOnCheckedChangeListener(this);

        check_dl.setOnCheckedChangeListener(this);

        check_ul.setOnCheckedChangeListener(this);


        btn_gis_dialog_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positiveButtonClickListener.onClick(ColorConfigDialog.this,DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_gis_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeButtonClickListener.onClick(ColorConfigDialog.this,DialogInterface.BUTTON_NEGATIVE);

            }
        });

    }

   public List<ColorConfigItem> getColorItemBean(int value){
       List<ColorConfigItem> colorItemBean = ColorConfigUtils.getColorItemBean(value);
       return colorItemBean;
   }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.check_pcl:
                if(isChecked){
                    for(CheckBox list1: list){
                        if(!list1.equals(check_pcl)){
                            list1.setChecked(false);
                        }
                    }
                    checkValue=Const.ColorManager.COVERAGE_PCI;
                }
                break;
            case R.id.check_rsrp:
                if(isChecked){
                    for(CheckBox list1: list){
                        if(!list1.equals(check_rsrp)){
                            list1.setChecked(false);
                        }
                    }
                    checkValue=Const.ColorManager.COVERAGE_RSRP;
                }
                break;
            case R.id.check_sinr:
                if(isChecked){
                    for(CheckBox list1: list){
                        if(!list1.equals(check_sinr)){
                            list1.setChecked(false);
                        }
                    }
                    checkValue=Const.ColorManager.COVERAGE_SINR;
                }
                break;
            case R.id.check_dl:
                if(isChecked){
                    for(CheckBox list1: list){
                        if(!list1.equals(check_dl)){
                            list1.setChecked(false);
                        }
                    }
                    checkValue=Const.ColorManager.COVERAGE_DL;
                }
                break;
            case R.id.check_ul:
                if(isChecked){
                    for(CheckBox list1: list){
                        if(!list1.equals(check_ul)){
                            list1.setChecked(false);
                        }
                    }
                    checkValue=Const.ColorManager.COVERAGE_UL;
                }
                break;
            default:
                break;
        }
    }
}
