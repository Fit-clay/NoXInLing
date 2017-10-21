package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.MapUtil;
import com.beidian.beidiannonxinling.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Eric on 2017/9/11.
 */

public class BaseInfoRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CellinfoListBean.LineinfoListBean> listBeen;
    LocationClient mLocClient;
    LoadingDialog loadingDialog;

    public static final int Lat = 0, Lng = 1, Antenna = 2, Angles = 3, AnglesValue = 4;

    public interface onTakePhotoListener {
        void setImageView(int position);
    }

    onTakePhotoListener onTakePhotoListener;
    private List<Map<String, String>> resultList = new ArrayList<>();

    public interface onChangeListener {
        void setChageBean(EditText editText, int position, boolean flag);
    }

    public interface onDeleteAntennaListener {
        void onDelete(int position);
    }

    onChangeListener changeListener;
    onDeleteAntennaListener onDeleteAntenna;

    private int indexType;

    public BaseInfoRecycleAdapter(Context context, List<CellinfoListBean.LineinfoListBean> listBeen, int indexType) {
        this.mContext = context;
        this.listBeen = listBeen;
        this.indexType = indexType;
        BaseApplication.lineinfoListBeen.clear();
        BaseApplication.lineinfoListBeen.addAll(listBeen);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_basic_antenna_mode, parent, false);
        AntennaViewHolder holder = new AntennaViewHolder(view, indexType);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AntennaViewHolder viewHolder = (AntennaViewHolder) holder;
        final CellinfoListBean
                .LineinfoListBean bean = listBeen.get(position);
        viewHolder.tx_get_antenna_message.setText("天线" + bean.getId() + "信息采集");
        viewHolder.et_lat_plannint.setText(bean.getLat());
        viewHolder.et_long_plannint.setText(bean.getLng());
        viewHolder.et_antenna_plannint.setText(bean.getLineexterior());
        viewHolder.et_angles_plannint.setText(bean.getDirectionrange());
        viewHolder.et_angles_plannint_value.setText(bean.getElectronicuprange());
        viewHolder.et_antenna_hight.setText(bean.getLineheight());

        viewHolder.et_lat_collection.setText(bean.getCllat());
        viewHolder.et_long_collection.setText(bean.getCllong());
        viewHolder.et_antenna_cellection.setText(bean.getCllineexterior());

        viewHolder.et_angles_cellection.setText(bean.getClemechanicalup());
        viewHolder.et_angles_cellection_value.setText(bean.getClmechanical());
        viewHolder.tv_base_district_consistency.setText(!TextUtils.isEmpty(bean.getIslatlng()) ? bean.getIslatlng() : "是");

        initeLLs(viewHolder.ll_take_photos, bean);

        if (!TextUtils.isEmpty(bean.getIslatlng())) {
            viewHolder.sp_lat_longs.setSelection(bean.getIslatlng().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getIslineexterior())) {
            viewHolder.sp_antenna_platform_names.setSelection(bean.getIslineexterior().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getLltype())) {
            for (int i = 0; i < viewHolder.village_surface_array.length; i++) {
                if (viewHolder.village_surface_array[i].equals(bean.getLltype())) {
                    viewHolder.sp_antenna_surfaces.setSelection(i, true);
                }
            }
        }

        changeEdt(viewHolder.et_lat_collection, Lat, position);
        changeEdt(viewHolder.et_long_collection, Lng, position);
        changeEdt(viewHolder.et_antenna_cellection, Antenna, position);

        changeEdt(viewHolder.et_angles_cellection, Angles, position);
        changeEdt(viewHolder.et_angles_cellection_value, AnglesValue, position);

        changeSp(viewHolder.sp_lat_longs, Lat, bean);
        changeSp(viewHolder.sp_antenna_platform_names, Lng, bean);
        changeSp(viewHolder.sp_antenna_surfaces, Antenna, bean);

        viewHolder.base_station_take_picture_ant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTakePhotoListener != null) {
                    onTakePhotoListener.setImageView(position);
                }
            }
        });
        if (bean.getImgPathList().size() > 2) {
            viewHolder.base_station_take_picture_ant.setVisibility(View.GONE);
        } else {
            viewHolder.base_station_take_picture_ant.setVisibility(View.VISIBLE);
        }

        viewHolder.tx_test_mechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeListener != null) {
                    changeListener.setChageBean(viewHolder.et_angles_cellection, position, false);
                }
            }
        });
        viewHolder.tx_test_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeListener != null) {
                    changeListener.setChageBean(viewHolder.et_angles_cellection_value, position, true);

                }
            }
        });
        viewHolder.tv_delete_antenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeListener != null) {
                    if (onDeleteAntenna != null) {
                        onDeleteAntenna.onDelete(position);
                    }

                }
            }
        });
        viewHolder.tv_collection_eci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingDialog == null) {
                    loadingDialog = DialogUtil.showLodingDialog(mContext, "采集中");
                } else {
                    loadingDialog.show();
                }
                mLocClient = new LocationClient(mContext);
                mLocClient.registerLocationListener(new BDLocationListener() {
                    @Override
                    public void onReceiveLocation(BDLocation bdLocation) {
                        loadingDialog.dismiss();
                        if (bdLocation == null) {
                            return;
                        }
                        viewHolder.et_lat_collection.setText(String.valueOf(bdLocation.getLatitude()));
                        viewHolder.et_long_collection.setText(String.valueOf(bdLocation.getLongitude()));

                        if (!TextUtils.isEmpty(viewHolder.et_lat_plannint.getText()) && !TextUtils.isEmpty(viewHolder.et_long_plannint.getText())) {
                            String distance = MapUtil.getDistanceFromXtoY(Double.valueOf(viewHolder.et_lat_plannint.getText().toString()), Double.valueOf(viewHolder.et_long_plannint.getText().toString()), Double.valueOf(viewHolder.et_lat_collection.getText().toString()), Double.valueOf(viewHolder.et_long_collection.getText().toString()));
                            String type;
                            if (Math.abs(Double.valueOf(distance) * 1000) > 50) {
                                type = "否";
                            } else {
                                type = "是";
                            }

                            viewHolder.tv_base_district_consistency.setText(type);
                            bean.setIslatlng(type);
                            if (mLocClient.isStarted()) {
                                mLocClient.unRegisterLocationListener(this);
                                mLocClient.stop();
                            }
                        }
                    }
                });
                mLocClient.setLocOption(MapUtil.setLocationOption());
                mLocClient.start();
            }
        });

        if (bean.isUserAdd()) {
            viewHolder.tv_delete_antenna.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_delete_antenna.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    public void setOnDeleteAntenna(onDeleteAntennaListener onDeleteAntenna) {
        this.onDeleteAntenna = onDeleteAntenna;
    }

    public void changeEdt(final EditText editText, final int type, final int position) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                CellinfoListBean.LineinfoListBean bean = BaseApplication.lineinfoListBeen.get(position);
                Log.d("=========", "changeEdt" + position);
                switch (type) {
                    case Lat:
                        bean.setCllat(editText.getText().toString());
                        break;
                    case Lng:
                        bean.setCllong(editText.getText().toString());
                        break;
                    case Antenna:
                        bean.setCllineexterior(editText.getText().toString());

                        break;
                    case Angles:
                        bean.setClemechanicalup(editText.getText().toString());
                        break;
                    case AnglesValue:
                        bean.setClmechanical(editText.getText().toString());
                        break;
                }
            }
        });
    }

    public void changeSp(final Spinner spinner, final int type, final CellinfoListBean.LineinfoListBean bean) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (type) {
                    case Lat:
                        bean.setIslatlng(spinner.getSelectedItem().toString());
                        break;
                    case Lng:
                        bean.setIslineexterior(spinner.getSelectedItem().toString());
                        break;
                    case Antenna:
                        bean.setLltype(spinner.getSelectedItem().toString());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setOnTakePhotoListener(onTakePhotoListener onTakePhotoListener) {
        this.onTakePhotoListener = onTakePhotoListener;
    }

    public void setOnChangeListener(onChangeListener onChangeListener) {
        this.changeListener = onChangeListener;
    }

    public void initeLLs(LinearLayout lly, final CellinfoListBean.LineinfoListBean bean) {
        if (bean.getImgPathList() != null) {
            lly.removeAllViews();
            for (int i = 0; i < bean.getImgPathList().size(); i++) {
                final String imagePath = bean.getImgPathList().get(i);
                final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                ImageView imgs = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
                params.setMargins(10, 0, 0, 0);
                imgs.setScaleType(ImageView.ScaleType.FIT_XY);
                imgs.setLayoutParams(params);
                imgs.setImageBitmap(bitmap);
                imgs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.showImageDialog(mContext, bitmap, indexType, new DialogUtil.OnImageDialogCallBack() {
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
    }

    class AntennaViewHolder extends RecyclerView.ViewHolder {
        EditText et_lat_plannint, et_long_plannint, et_antenna_plannint, et_angles_plannint, et_angles_plannint_value, et_antenna_hight, et_lat_collection, et_long_collection,
                et_antenna_cellection, et_angles_cellection, et_angles_cellection_value;
        TextView tx_test_mechanical, tx_test_camera, tx_get_antenna_message, tv_base_district_consistency, tv_collection_eci, tv_delete_antenna;
        Spinner sp_lat_longs, sp_antenna_platform_names, sp_antenna_surfaces;
        LinearLayout ll_take_photos, ly_base_antenna;
        String[] is_rru_array, village_surface_array;
        ImageView base_station_take_picture_ant;

        public AntennaViewHolder(View itemView, int indexType) {
            super(itemView);
            is_rru_array = mContext.getResources().getStringArray(R.array.is_rru);
            village_surface_array = mContext.getResources().getStringArray(R.array.antenna_surfaces);
            tx_get_antenna_message = (TextView) itemView.findViewById(R.id.tx_get_antenna_message);
            et_lat_plannint = (EditText) itemView.findViewById(R.id.et_lat_plannint);
            et_long_plannint = (EditText) itemView.findViewById(R.id.et_long_plannint);
            et_antenna_plannint = (EditText) itemView.findViewById(R.id.et_antenna_plannint);
            et_angles_plannint = (EditText) itemView.findViewById(R.id.et_angles_plannint);
            et_angles_plannint_value = (EditText) itemView.findViewById(R.id.et_angles_plannint_value);
            et_antenna_hight = (EditText) itemView.findViewById(R.id.et_antenna_hight);

            //天线信息，可以更改
            et_lat_collection = (EditText) itemView.findViewById(R.id.et_lat_collection);
            et_long_collection = (EditText) itemView.findViewById(R.id.et_long_collection);
            et_antenna_cellection = (EditText) itemView.findViewById(R.id.et_antenna_cellection);
            et_angles_cellection = (EditText) itemView.findViewById(R.id.et_angles_cellection);
            et_angles_cellection_value = (EditText) itemView.findViewById(R.id.et_angles_cellection_value);

            tx_test_mechanical = (TextView) itemView.findViewById(R.id.tx_test_mechanical);
            tx_test_camera = (TextView) itemView.findViewById(R.id.tx_test_camera);
            tv_base_district_consistency = (TextView) itemView.findViewById(R.id.tv_base_district_consistency);
            tv_collection_eci = (TextView) itemView.findViewById(R.id.tv_collection_eci);
            tv_delete_antenna = (TextView) itemView.findViewById(R.id.tv_delete_antenna);

            sp_lat_longs = (Spinner) itemView.findViewById(R.id.sp_lat_long);
            sp_antenna_platform_names = (Spinner) itemView.findViewById(R.id.sp_antenna_platform_name);
            sp_antenna_surfaces = (Spinner) itemView.findViewById(R.id.sp_antenna_surface);

            ll_take_photos = (LinearLayout) itemView.findViewById(R.id.ll_take_picture_ant);
            ly_base_antenna = (LinearLayout) itemView.findViewById(R.id.ly_base_antenna);
            base_station_take_picture_ant = (ImageView) itemView.findViewById(R.id.base_station_take_picture_ant);
            initSpinner(sp_antenna_platform_names, is_rru_array);
            initSpinner(sp_lat_longs, is_rru_array);
            initSpinner(sp_antenna_surfaces, village_surface_array);
//             tx_test_camera.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(changeListener!=null){
//                        changeListener.setChageBean(et_angles_cellection_value,getPosition(),true);
//
//                    }
//                }
//            });
            if (indexType == Const.LOOK) {
                ViewUtils.disableSubControls(ly_base_antenna);
                base_station_take_picture_ant.setEnabled(false);
                base_station_take_picture_ant.setClickable(false);
            }
        }
    }

    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(mContext, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

}
