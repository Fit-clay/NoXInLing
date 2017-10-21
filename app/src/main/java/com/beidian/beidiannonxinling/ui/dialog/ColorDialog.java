package com.beidian.beidiannonxinling.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ColorAdapter;
import com.beidian.beidiannonxinling.adapter.ColorConfigSpinnerAdapter;
import com.beidian.beidiannonxinling.adapter.ColorManagerAdapter;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.ColorManagerDetails;
import com.beidian.beidiannonxinling.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ren·Liu
 *         颜色配置框
 */
public class ColorDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    int displayLowerValue = -9999;//显示的最小值
    int displayUpperValue = -9999;//显示的最大值
    int virtualLowerValue = -9999;//实际有效最小值
    int virtualUpperValue = -9999;//实际有效最大值
    private Spinner sp_coverage_left, sp_coverage_right;
    private EditText et_data_left, et_data_right;
    private GridView gv_gridView;
    private View show;
    private Button bt_cancle, bt_install;
    private ColorConfigSpinnerAdapter myleftAdapter;
    private ColorConfigSpinnerAdapter myrightAdapter;
    private String[] leftSpinnerDatas;
    private String[] rightSpinnerDatas;
    private int leftType;
    private int rightType;
    private boolean edit = false;
    private ColorConfigItem itemConfig = null;
    private int operationType = -1;
    private int selectorColor = 0;
    private ColorManagerAdapter managerAdapter;
    private Handler detailHandler;
    private Handler hanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.ColorManager.COVERAGE_REFRESH_SELECTED_COLOR:
                    selectorColor = msg.arg1;
                    ToastUtils.makeText(mContext,"ffffff"+Const.ColorManager.COVERAGE_REFRESH_SELECTED_COLOR+msg.arg1);
                    show.setBackgroundColor(selectorColor);
                    break;
                //super.handleMessage(msg);
            }
        }

    };

    public ColorDialog(@NonNull Context context ) {
        super(context,  R.style.dialogCommon);
        this.mContext = context;
        init(context);
    }
    public ColorDialog(Context context, int type){
        super(context, R.style.dialogCommon);
        this.mContext = context;
        operationType=type;
        init(context);
    }
    public ColorDialog(Context context, boolean isEdit, int type, Handler handler) {
        super(context, R.style.dialogCommon);
        this.mContext = context;
        this.edit = isEdit;
        operationType=type;
        detailHandler=handler;
    }


    protected ColorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void init(Context context) {
        mContext=context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_color, null);
        setContentView(view);
        show=findViewById(R.id.layout_sel_color);
        gv_gridView= (GridView) findViewById(R.id.gv_color);
        sp_coverage_left = (Spinner) findViewById(R.id.spinner_coverage_left);
        sp_coverage_right = (Spinner) findViewById(R.id.spinner_coverage_right);
        et_data_left = (EditText) findViewById(R.id.et_coverage_data_left);
        et_data_right = (EditText) findViewById(R.id.et_coverage_data_right);
        bt_cancle= (Button) findViewById(R.id.btn_coverage_cancel);
        bt_install= (Button) findViewById(R.id.btn_coverage_ok);
        bt_cancle.setOnClickListener(this);
        bt_install.setOnClickListener(this);

        leftSpinnerDatas=context.getResources().getStringArray(R.array.coverage_color_left_array);
        myleftAdapter=new ColorConfigSpinnerAdapter(context);
        myleftAdapter.setStringArray(leftSpinnerDatas);
        sp_coverage_left.setAdapter(myleftAdapter);

        rightSpinnerDatas=context.getResources().getStringArray(R.array.coverage_color_right_array);
        myrightAdapter=new ColorConfigSpinnerAdapter(context);
        myrightAdapter.setStringArray(rightSpinnerDatas);
        sp_coverage_right.setAdapter(myrightAdapter);


        List<Integer> colorList = new ArrayList<Integer>();
        //读取颜色数据源
        for (int i = 0; i < Const.ColorManager.COLOR_TONE.length; i++) {
            colorList.add(Const.ColorManager.COLOR_TONE[i]);
        }
        ColorAdapter adapter = new ColorAdapter(context,Const.ColorManager.COLOR_TONE,hanlder);
        gv_gridView.setAdapter(adapter);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.95); // 高度设置为屏幕的0.6
        lp.height=(int)(d.heightPixels*0.6);
        dialogWindow.setAttributes(lp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorDialog.this.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_coverage_cancel:
                if(this.isShowing()){
                   // resetData();
                    this.dismiss();
                }
                break;
            case R.id.btn_coverage_ok:
                System.out.println("！！！@@@@@@ 点击了~~~");

                    if (et_data_left.getText().toString().trim() != null &&!et_data_left.getText().toString().trim().equals("")&& et_data_right.getText().toString().trim() != null&&!et_data_right.getText().toString().trim().equals("")) {
                        displayLowerValue = Integer.valueOf(et_data_left.getText().toString());
                        displayUpperValue = Integer.valueOf(et_data_right.getText().toString());
                        if (displayLowerValue > displayUpperValue) { //下限值大于上限值 例如 最小值为6，最大值为4
                            ToastUtils.makeText(mContext, "下限值大于上限值");

                        } else if (displayLowerValue == displayUpperValue) { //下限值数值等于上限值数值 例如 最大值6，最小值为6
                            ToastUtils.makeText(mContext, "下限值大于上限值");

                            if (leftType != 1 || rightType != 1) { //下限值不包含该值，或者上限值不包含该值
                                ToastUtils.makeText(mContext, "上/下 限值不包含该值");
                            } else {
                                virtualLowerValue = displayLowerValue;
                                virtualUpperValue = displayUpperValue;

                            }


                        }else {
                            if(selectorColor!=0){
                                String leftValue = et_data_left.getText().toString().trim();
                                System.out.println(leftValue);
                                String rightValue = et_data_right.getText().toString().trim();
                                System.out.println(rightValue);
                                ColorManagerDetails main3Activity = (ColorManagerDetails) mContext;

                                ColorConfigItem item = new ColorConfigItem();
                                System.out.println("sp_coverage_left:" + (sp_coverage_left.getSelectedItem().toString()));
                                if (sp_coverage_left.getSelectedItem().toString().trim().equals("大于")) {
                                    item.setLeftType(0);
                                } else {
                                    item.setLeftType(1);
                                }
                                System.out.println("sp_coverage_right:" + (sp_coverage_right.getSelectedItem().toString()));
                                if (sp_coverage_right.getSelectedItem().toString().trim().equals("小于")) {
                                    item.setRightType(0);
                                } else {
                                    item.setRightType(1);
                                }
                                item.setType(operationType);
                                item.setMaxValue(Integer.parseInt(leftValue));
                                item.setMinValue(Integer.parseInt(rightValue));
                                item.setColor(selectorColor);
                                ColorManagerDetails parent = (ColorManagerDetails) mContext;
                                ColorManagerAdapter md = parent.getmAdapter();
                                md.add(item, operationType, mContext);
                                this.dismiss();
                            }else {
                                ToastUtils.makeText(mContext,"请填写颜色 ");
                            }

                        }

                    }else {
                        ToastUtils.makeText(mContext,"请填写最大最小值");
                    }
                }

        }
    }


