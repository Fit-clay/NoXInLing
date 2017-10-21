package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.util.DialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2017/9/8.
 */

public class BaseTestAntennaAdapter extends TBaseAdapter<CellinfoListBean.LineinfoListBean> {
    String []is_rru_array,village_surface_array;
    public static final int Lat=0,Lng=1,Antenna=2,Angles=3,AnglesValue=4;
    private List<Map<String,String>> resultList=new ArrayList<>();
    private boolean flag=true;

    public List<Map<String, String>> getResultList() {
        return resultList;
    }
    public void clearResult(){
        resultList.clear();
    }

    public interface onTakePhotoListener{
        void setImageView(int position);
    }
    onTakePhotoListener onTakePhotoListener;


    public interface onChangeListener{
        void setChageBean(int position,boolean flag);
    }
    onChangeListener changeListener;



    LinearLayout ll_take_photos;
    ImageView base_station_take_picture_ant;

    private int onClickIndex=0;
    public BaseTestAntennaAdapter(Context context,  List<CellinfoListBean.LineinfoListBean> listData) {
        super(context, R.layout.add_basic_antenna_mode, listData);
        is_rru_array = getContext().getResources().getStringArray(R.array.is_rru);
        village_surface_array =getContext().getResources().getStringArray(R.array.antenna_surfaces);
        Map<String ,String> map;
        for(CellinfoListBean.LineinfoListBean bean:listData){
            map=new HashMap<String, String>();
//            bean.setLat(TextUtils.isEmpty(map.get("lat"))?"  ":map.get("lat"));
//            bean.setLng(TextUtils.isEmpty(map.get("lng"))?"  ":map.get("lng"));
//            bean.setCllineexterior(TextUtils.isEmpty(map.get("antenna"))?"  ":map.get("antenna"));
//            bean.setClemechanicalup(TextUtils.isEmpty(map.get("angles"))?"  ":map.get("angles"));
//            bean.setClmechanical(TextUtils.isEmpty(map.get("anglesValue"))?"  ":map.get("anglesValue"));
//            bean.setIslatlng(TextUtils.isEmpty(map.get("islatlng"))?"是":map.get("islatlng"));
//            bean.setIslineexterior(TextUtils.isEmpty(map.get("islineexterior"))?"是":map.get("islineexterior"));
//            bean.setLltype(TextUtils.isEmpty(map.get("lltype"))?"是":map.get("lltype"));

            map.put("lat",TextUtils.isEmpty(bean.getCllat())?" ":bean.getCllat());
            map.put("lng",TextUtils.isEmpty(bean.getCllong())?" ":bean.getCllat());
            map.put("antenna",TextUtils.isEmpty(bean.getCllineexterior())?" ":bean.getCllat());
            map.put("angles",TextUtils.isEmpty(bean.getClemechanicalup())?" ":bean.getCllat());
            map.put("anglesValue",TextUtils.isEmpty(bean.getClmechanical())?" ":bean.getCllat());
//            map.put("islatlng",TextUtils.isEmpty(bean.getIslatlng())?" ":bean.getCllat());
//            map.put("islineexterior",TextUtils.isEmpty(bean.getIslineexterior())?" ":bean.getCllat());
//            map.put("lltype",TextUtils.isEmpty(bean.getLltype())?" ":bean.getCllat());
            resultList.add(map);

        }
        flag=true;
    }
    @Override
    public void doHandler(ViewHolder holder, final int position, View convertView, ViewGroup parent) {
        TextView tx_get_antenna_message = holder.getView(R.id.tx_get_antenna_message);
        ll_take_photos  =  holder.getView(R.id.ll_take_picture_ant);
        base_station_take_picture_ant   =  holder.getView(R.id.base_station_take_picture_ant);
        tx_get_antenna_message.setText("天线" + Integer.valueOf(position+1) + "信息采集");

        Spinner sp_lat_longs =  holder.getView(R.id.sp_lat_long);
        Spinner sp_antenna_platform_names =  holder.getView(R.id.sp_antenna_platform_name);
        Spinner sp_antenna_surfaces = holder.getView(R.id.sp_antenna_surface);

        EditText et_lat_plannint = holder.getView(R.id.et_lat_plannint);
        EditText et_long_plannint = holder.getView(R.id.et_long_plannint);
        EditText et_antenna_plannint = holder.getView(R.id.et_antenna_plannint);
        EditText et_angles_plannint = holder.getView(R.id.et_angles_plannint);
        EditText et_angles_plannint_value = holder.getView(R.id.et_angles_plannint_value);
        EditText et_antenna_hight =  holder.getView(R.id.et_antenna_hight);

        //天线信息，可以更改
         EditText et_lat_collection = holder.getView(R.id.et_lat_collection);
        EditText et_long_collection =  holder.getView(R.id.et_long_collection);
        EditText et_antenna_cellection = holder.getView(R.id.et_antenna_cellection);
        EditText et_angles_cellection =  holder.getView(R.id.et_angles_cellection);
        EditText et_angles_cellection_value =  holder.getView(R.id.et_angles_cellection_value);

        TextView tx_test_mechanical=holder.getView(R.id.tx_test_mechanical);
        TextView tx_test_camera=holder.getView(R.id.tx_test_camera);


        initSpinner( sp_antenna_platform_names, is_rru_array);
        initSpinner( sp_lat_longs, is_rru_array);
        initSpinner(sp_antenna_surfaces, village_surface_array);
        final CellinfoListBean.LineinfoListBean bean=getItem(position);

        et_lat_plannint.setText(bean.getLat());
        et_long_plannint.setText(bean.getLng());
        et_antenna_plannint.setText(bean.getLineexterior());
        et_angles_plannint.setText(bean.getDirectionrange());
        et_angles_plannint_value.setText(bean.getElectronicuprange());
        et_antenna_hight.setText(bean.getLineheight());

        et_lat_collection.setText(bean.getCllat());
        et_long_collection.setText(bean.getCllong());
        et_antenna_cellection.setText(bean.getCllineexterior());

        et_angles_cellection.setText(bean.getClemechanicalup());
        et_angles_cellection_value.setText(bean.getClmechanical());

        initeLLs(ll_take_photos,bean);


        if(!TextUtils.isEmpty(bean.getIslatlng())){
            sp_lat_longs.setSelection(bean.getIslatlng().equals("是")?0:1,true);
        }
        if(!TextUtils.isEmpty(bean.getIslineexterior())){
            sp_antenna_platform_names.setSelection(bean.getIslineexterior().equals("是")?0:1,true);
        }if(!TextUtils.isEmpty(bean.getLltype())){
            sp_antenna_surfaces.setSelection(bean.getLltype().equals("是")?0:1,true);
        }


        if(flag){
            changeEdt(et_lat_collection,Lat,position);
            changeEdt(et_long_collection,Lng,position);
            changeEdt(et_antenna_cellection,Antenna,position);

            changeEdt(et_angles_cellection,Angles,position);
            changeEdt(et_angles_cellection_value,AnglesValue,position);

            changeSp(sp_lat_longs,Lat,bean);
            changeSp(sp_antenna_platform_names,Lng,bean);
            changeSp(sp_antenna_surfaces,Antenna,bean);
            if(position==getCount()){
                flag=false;
            }
        }


        base_station_take_picture_ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTakePhotoListener!=null){
                    onTakePhotoListener.setImageView(position);
                }
            }
        });
        if(bean.getImgPathList().size()>2){
            base_station_take_picture_ant.setVisibility(View.GONE);
        }else {
            base_station_take_picture_ant.setVisibility(View.VISIBLE);
        }

        tx_test_mechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeListener!=null){
                    changeListener.setChageBean(position,false);
                    onClickIndex=position;
                }
            }
        });
        tx_test_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeListener!=null){
                    changeListener.setChageBean(position,true);
                    onClickIndex=position;
                }
            }
        });

    }


    public void setOnTakePhotoListener(onTakePhotoListener onTakePhotoListener){
        this.onTakePhotoListener=onTakePhotoListener;
    }
    public void setOnChangeListener(onChangeListener onChangeListener){
        this.changeListener=onChangeListener;
    }
    public void changeEdt(final EditText editText, final int type, final int position){
//    public void changeEdt(final EditText editText, final int type, final CellinfoListBean.LineinfoListBean bean){
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override  public void afterTextChanged(Editable s) {
             switch (type){
                 case Lat:
                     resultList.get(position).put("lat",editText.getText().toString());
                     break;
                 case Lng:
                     resultList.get(position).put("lng",editText.getText().toString());
                     break;
                 case Antenna:
                     resultList.get(position).put("antenna",editText.getText().toString());
                     break;
                 case Angles:
                     resultList.get(position).put("angles",editText.getText().toString());
                     break;
                 case AnglesValue:
                     resultList.get(position).put("anglesValue",editText.getText().toString());
                     break;
             }
            }
        });
    }

    public void changeSp(final Spinner spinner, final int type, final CellinfoListBean.LineinfoListBean bean){
//    public void changeSp(final Spinner spinner, final int type, final int postion){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (type){
////                    case Lat:
////                        resultList.get(postion).put("islatlng",spinner.getSelectedItem().toString());
////                        break;
////                    case Lng:
////                        resultList.get(postion).put("islineexterior",spinner.getSelectedItem().toString());
////                        break;
////                    case Antenna:
////                        resultList.get(postion).put("lltype",spinner.getSelectedItem().toString());
////                        break;
//
//                    case Lat:
//                        bean.setIslatlng(spinner.getSelectedItem().toString());
////                        resultList.get(position).put("islatlng",spinner.getSelectedItem().toString());
//                        break;
//                    case Lng:
//                        bean.setIslineexterior(spinner.getSelectedItem().toString());
////                        resultList.get(position).put("islineexterior",spinner.getSelectedItem().toString());
//
//                        break;
//                    case Antenna:
//                        bean.setLltype(spinner.getSelectedItem().toString());
////                        resultList.get(position).put("lltype",spinner.getSelectedItem().toString());
//                        break;
////                }
//                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void initSpinner( Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(getContext(), R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

    public void initeLLs(LinearLayout lly, final CellinfoListBean.LineinfoListBean bean) {
            if (bean.getImgPathList() != null) {
                lly.removeAllViews();
                for (int i = 0; i <bean.getImgPathList().size(); i++) {
                    final String imagePath = bean.getImgPathList().get(i);
                    final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    ImageView imgs = new ImageView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,100);
                    params.setMargins(10, 0, 0, 0);
                    imgs.setScaleType(ImageView.ScaleType.FIT_XY);
                    imgs.setLayoutParams(params);
                    imgs.setImageBitmap(bitmap);
                    imgs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.showImageDialog(getContext(), bitmap, -1,new DialogUtil.OnImageDialogCallBack() {
                                @Override
                                public void isDelete(boolean isDelete) {
                                    if (isDelete) {
                                        bean.getImgPathList().remove(imagePath);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    });
                    lly.addView(imgs, 0);
                }
            }
//        }

    }

}
