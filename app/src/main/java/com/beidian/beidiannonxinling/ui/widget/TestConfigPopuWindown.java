package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;

/**
 * Created by Eric on 2017/9/25.
 */

public class TestConfigPopuWindown extends PopupWindow {
   private Context mContext;
    private Spinner spChangeDistrict,spUnitInfo,spFloor,spTestWay,spTestModel,spTestItem;
    private TextView tv_cancel;
    public TestConfigPopuWindown(Context context){
        mContext=context;
        View customView = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.view_popuwindown_test_config,null, false);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setContentView(customView);
        initView(customView);
        setData();
    }

    private void initView(View view) {
        spChangeDistrict= (Spinner) view.findViewById(R.id.sp_change_distract);
        spUnitInfo= (Spinner) view.findViewById(R.id.sp_unit_info);
        spFloor= (Spinner) view.findViewById(R.id.sp_floor_info);
        spTestWay= (Spinner) view.findViewById(R.id.sp_test_way);
        spTestModel= (Spinner) view.findViewById(R.id.sp_test_model);
//        spTestItem= (Spinner) view.findViewById(R.id.sp_test_item);
        tv_cancel= (TextView) view.findViewById(R.id.tv_cancel);
    }

    private void setData() {
      String[]  is_rru_array = mContext.getResources().getStringArray(R.array.is_rru);
        initSpinner(spChangeDistrict,is_rru_array);
        initSpinner(spUnitInfo,is_rru_array);
        initSpinner(spFloor,is_rru_array);
        initSpinner(spTestWay,is_rru_array);
        initSpinner(spTestModel,is_rru_array);
//        initSpinner(spTestItem,is_rru_array);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }



    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(mContext, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }
}
