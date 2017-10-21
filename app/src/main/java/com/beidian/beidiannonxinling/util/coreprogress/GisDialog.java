package com.beidian.beidiannonxinling.util.coreprogress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.ui.activity.GisActivity;
import com.beidian.beidiannonxinling.ui.widget.DialogPopuWindow;
import com.beidian.beidiannonxinling.util.TestModelUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class GisDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Context        context;
    List<TestTask> list;
    TextView tx_test;//测试选项
    Spinner sp_cell;
    TestModelBean testModelBean;
    Spinner sp_mode;//测试模板
    DialogPopuWindow dialogPopuWindow;
    OneKeyTestBean oneKeyTestBeans;
    GisActivity mGisActivity;
    EditText et_remarks;//备注

    public GisDialog(@NonNull Context context) {
        super(context, R.style.GisDialog);
        this.context = context;
    }

    public GisDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }


    public List<TestTask> getList() {
        return list;
    }

    public void setList(List<TestTask> list) {
        this.list = list;
    }

    /**
     * 初始化选择的数据
     *
     * @param oneKeyTestBean
     * @param activity
     */
    public void initView(OneKeyTestBean oneKeyTestBean, GisActivity activity) {
        oneKeyTestBeans=oneKeyTestBean;
        mGisActivity=activity;
        BaseInfoTestBean baseInfoTestBean = oneKeyTestBean.getBaseInfoTestBean();//基站的所有信息
        testModelBean = oneKeyTestBean.getTestModelBean();//模板的所有信息
        List<TestModelBean.TemplateBean> list=TestModelUtil.getModelNewTask();//获取本地测试模板
        if(list!=null && list.size()>0) {
            for (TestModelBean.TemplateBean mode : list) {
                testModelBean.getTemplate().add(mode);
            }
        }
        String[] cellName = new String[baseInfoTestBean.getSiteInfo().getCellinfoList().size()];
        String[] testMode = new String[testModelBean.getTemplate().size()];
        //设置扇区数据
        for (int i = 0; i < cellName.length; i++) {
            if(baseInfoTestBean.getSiteInfo().getCellinfoList().get(i).getCellname()!=null) {
                cellName[i] = baseInfoTestBean.getSiteInfo().getCellinfoList().get(i).getCellname();
            }
        }
        //设置测试模板数据
        for (int i = 0; i < testMode.length; i++) {
            if(testModelBean.getTemplate().get(i).getTemplatename()!=null) {
                testMode[i] = testModelBean.getTemplate().get(i).getTemplatename();
            }
        }
        setContentView(R.layout.gis_dialog);
        final Window dialogWindow = getWindow();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.4);
        p.width = (int) (d.getWidth() * 0.9);
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);

        sp_cell = (Spinner) findViewById(R.id.sp_sq);//扇区选择
        sp_mode = (Spinner) findViewById(R.id.sp_mode);
        tx_test = (TextView) findViewById(R.id.tx_test);
        Button btn_gis_dialog_con = (Button) findViewById(R.id.btn_gis_dialog_con);
        Button btn_gis_dialog = (Button) findViewById(R.id.btn_gis_dialog);
        et_remarks = (EditText) findViewById(R.id.et_remarks);
        initSpinner(sp_cell, cellName);
        initSpinner(sp_mode, testMode);
        initePopu(0,true);
        btn_gis_dialog_con.setOnClickListener(this);
        btn_gis_dialog.setOnClickListener(this);
        tx_test.setOnClickListener(this);

        OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        setOnKeyListener(keylistener);
        setCancelable(false);

        sp_mode.setOnItemSelectedListener(this);
    }


    public void initePopu(int position,boolean isExist) {
        if (!isExist) {
            dialogPopuWindow.dismissPopu();
        }
        dialogPopuWindow = new DialogPopuWindow(context, tx_test);
        dialogPopuWindow.setDataList(testModelBean.getTemplate().get(position).getTaskList());
  //      dialogPopuWindow.setSelectCount(100);
        dialogPopuWindow.setCheckedSet(null);
    }

    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(context, R.layout.dialog_spinner, strArra);
        spinners.setAdapter(arrA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gis_dialog://取消
                GisDialog.this.dismiss();
                ((Activity) context).finish();
                break;
            case R.id.btn_gis_dialog_con://确定
                if (!dialogPopuWindow.getCheckedOptions().isEmpty() && dialogPopuWindow.getCheckedOptions().size() > 0) {
                    oneKeyTestBeans.setChangeModel(dialogPopuWindow.getCheckedOptions());
                    oneKeyTestBeans.setSector(sp_cell.getSelectedItem().toString());
                    //   oneKeyTestBean.setTestModelBean();
                    oneKeyTestBeans.setRemarks(et_remarks.getText().toString());
                    mGisActivity.mOneKeyTestBean=oneKeyTestBeans;
                    GisDialog.this.dismiss();
                }else{
                    Toast.makeText(context,"请选择测试项",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tx_test://测试选项
                if(dialogPopuWindow.isPopuShow()) {
                    dialogPopuWindow.dismissPopu();
                }else{
                    dialogPopuWindow.showPopu(v);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tx_test.setText("");
        initePopu(position,false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
