package com.beidian.beidiannonxinling.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.ConstructionBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ViewUtils;

/**
 * Created by Eric on 2017/9/22.
 */

public class ConstructionFragment extends BaseLazyLoadFragment {
    private Spinner spZhuBobi,spOutAntenna,spTianKui,spBattery,spKuiXian,spXianLan,spJieTou,spMark;
    private EditText edtZhuBobi,edtOutAntenna,edtTianKui,edtBattery,edtKuiXian,edtXianLan,edtJieTou,edtMark;
    private String [] is_rru_array;
    private LinearLayout construction;
    private   ConstructionBean constructionBean;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_construction,container,false);
       spZhuBobi=findView(view,R.id.sp_zhu_bo_bi);
        spOutAntenna=findView(view,R.id.sp_out_antenna);
        spTianKui=findView(view,R.id.sp_tiankui);
        spBattery=findView(view,R.id.sp_battery);
        spKuiXian=findView(view,R.id.sp_kuixian);
        spXianLan=findView(view,R.id.sp_xianlan);
        spJieTou=findView(view,R.id.sp_jietou);
        spMark=findView(view,R.id.sp_mark);
        edtZhuBobi=findView(view,R.id.edt_zhubobi);
        edtOutAntenna=findView(view,R.id.edt_out_antenna);
        edtTianKui=findView(view,R.id.edt_tiankui);
        edtBattery=findView(view,R.id.edt_battery);
        edtKuiXian=findView(view,R.id.edt_kuixian);
        edtXianLan=findView(view,R.id.edt_xianlan);
        edtJieTou=findView(view,R.id.edt_jietou);
        edtMark=findView(view,R.id.edt_mark);
        construction=findView(view,R.id.lv_construction);
        return view;
    }
    @Override
    protected void lazyLoad() {
       is_rru_array = getResources().getStringArray(R.array.qualified_type);
        initSpinner(spZhuBobi,is_rru_array);
        initSpinner(spOutAntenna,is_rru_array);
        initSpinner(spTianKui,is_rru_array);
        initSpinner(spBattery,is_rru_array);
        initSpinner(spKuiXian,is_rru_array);
        initSpinner(spXianLan,is_rru_array);
        initSpinner(spJieTou,is_rru_array);
        initSpinner(spMark,is_rru_array);
    }
    public void setData(BaseInfoTestBean.SiteInfoBean  siteInfoBean,int indexType){


        if(siteInfoBean!=null){
            constructionBean =siteInfoBean.getConstruction();
            if(indexType== Const.LOOK){
                ViewUtils.disableSubControls(construction);
            }
            if(constructionBean!=null){
                spZhuBobi.setSelection(!TextUtils.isEmpty(constructionBean.getZhuBoBi())&&constructionBean.getZhuBoBi().equals(is_rru_array[0])?0:1);
                spOutAntenna.setSelection(!TextUtils.isEmpty(constructionBean.getOutAntenna())&&constructionBean.getOutAntenna().equals(is_rru_array[0])?0:1);
                spTianKui.setSelection(!TextUtils.isEmpty(constructionBean.getSkyKui())&&constructionBean.getSkyKui().equals(is_rru_array[0])?0:1);
                spBattery.setSelection(!TextUtils.isEmpty(constructionBean.getReserveBattery())&&constructionBean.getReserveBattery().equals(is_rru_array[0])?0:1);
                spKuiXian.setSelection(!TextUtils.isEmpty(constructionBean.getKuiXian())&&constructionBean.getKuiXian().equals(is_rru_array[0])?0:1);
                spXianLan.setSelection(!TextUtils.isEmpty(constructionBean.getXianLan())&&constructionBean.getXianLan().equals(is_rru_array[0])?0:1);
                spJieTou.setSelection(!TextUtils.isEmpty(constructionBean.getJieKou())&&constructionBean.getJieKou().equals(is_rru_array[0])?0:1);
                spMark.setSelection(!TextUtils.isEmpty(constructionBean.getMark())&&constructionBean.getMark().equals(is_rru_array[0])?0:1);

                edtZhuBobi.setText(constructionBean.getInputZhuBoBi());
                edtOutAntenna.setText(constructionBean.getInputOutAntenna());
                edtTianKui.setText(constructionBean.getInputSkyKui());
                edtBattery.setText(constructionBean.getInputReserveBattery());
                edtKuiXian.setText(constructionBean.getInputKuiXian());
                edtXianLan.setText(constructionBean.getInputXianLan());
                edtJieTou.setText(constructionBean.getInputJieKou());
                edtMark.setText(constructionBean.getInputMark());
            }
        }

    }

    @Override
    protected void initListener() {

    }
    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }
    public void saveInfo(){
        if(constructionBean!=null){
            constructionBean.setZhuBoBi(spZhuBobi.getSelectedItem().toString());
            constructionBean.setOutAntenna(spOutAntenna.getSelectedItem().toString());
            constructionBean.setSkyKui(spTianKui.getSelectedItem().toString());
            constructionBean.setReserveBattery(spBattery.getSelectedItem().toString());
            constructionBean.setXianLan(spXianLan.getSelectedItem().toString());
            constructionBean.setKuiXian(spKuiXian.getSelectedItem().toString());
            constructionBean.setJieKou(spJieTou.getSelectedItem().toString());
            constructionBean.setMark(spMark.getSelectedItem().toString());

            constructionBean.setInputZhuBoBi(edtZhuBobi.getText().toString());
            constructionBean.setInputOutAntenna(edtOutAntenna.getText().toString());
            constructionBean.setInputSkyKui(edtTianKui.getText().toString());
            constructionBean.setInputReserveBattery(edtBattery.getText().toString());
            constructionBean.setInputKuiXian(edtKuiXian.getText().toString());
            constructionBean.setInputXianLan(edtXianLan.getText().toString());
            constructionBean.setInputJieKou(edtJieTou.getText().toString());
            constructionBean.setInputMark(edtMark.getText().toString());
        }


    }
}
