package com.beidian.beidiannonxinling.ui.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.BaseInfoRecycleAdapter;
import com.beidian.beidiannonxinling.adapter.DistrictPopuWindowAdapter;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.DistrictBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.listener.CellPhoneStateListener;
import com.beidian.beidiannonxinling.ui.widget.DistrictPopuWindown;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ImageUtils;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.ViewUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/9/8.
 */

public class BaseInfoCollectionActivity extends BaseActivity implements View.OnClickListener{

    TextView tv_title;
    ImageView imageButton_right, iv_left;
    ImageView base_station_take_picture,iv_hongzhan_station_take_picture,iv_hongzhan_antenna_picture,iv_hongzhan_cover_picture;
    Spinner sp_cel, sp_channel, shan_rru, sp_la_yuan, sp_village_corver_type, sp_antenna_surface, sp_antenna_platform_name, sp_lat_long, spResult;

    //小区信息和基本信息，不可更改
    EditText ed_city, et_county, et_station_type, et_enodebid, et_station_name, et_planning_id, et_planning_name, et_station_situation, et_test_lng,
            et_test_lat, et_bbu_nums, et_uul_nums, et_site_address, et_village_cel_plannint_value, et_channel_cel_plannint_value,
            et_rru_cel_plannint_value;

    //小区信息和基本信息，可更改
    EditText et_village_cel_cellection_value, et_channel_cel_cellection_value, et_rru_cel_cellection_value, et_pcl_plannint_value,
            et_earfcn_plannint_value, et_cover_scene, et_antenna_nums;
    TextView add_antenna_tx, tx_tip_name, tx_net_ways, tx_operator, add_antenna_txs,tv_base_district_consistency,tv_collection_eci;
    TextView tvDistrictName;
    LinearLayout ll_take_picture, ll_take_picture_ant, add_ll,ll_right_button,ly_hongzhan,ly_base_photo,ly_cover_photo,ly_panoramic,ly_antenna,ly_cover;
    //    CustomListView cusListView;
    Button btn_save;
    ScrollView scrollView;
    LinearLayout linearLayout;

    RecyclerView recyclerView;
    BaseInfoRecycleAdapter recycleAdapter;

    private String baseInfoPath;
    private String stationName;
    private int indexType;
    private String  orderType;
    BaseInfoTestBean testBean;
    BaseInfoTestBean.SiteInfoBean siteInfo;
    String workorder;
    List<CellinfoListBean> cellInfoList;   //小区列表
    List<CellinfoListBean.LineinfoListBean> lineInfoList; //天线列表

    private int index = 0;

    String jsonDir;
    String times;
    String[] is_rru_array, isDistrisct;

    private final int PERMISSION_CAMERA = 200;
    private int ImagePosition = 0;

    List<DistrictBean> distrisctList;
    boolean isLine = false;//是否是天线调用相机

    int flag = 0;//谁调用相机  0基站  1 建筑物全景 2 天线整体 3 扇区覆盖 4天线

    private String[] marks = new String[]{"基站小区名", "任务工单", "经度", "纬度", "日期"};//水印基本信息

    public static final int Mechanical_RESULT_OK = 101, Camera_RESULT_OK = 103;

    private EditText edtCl, edtValue;
    private String[] resultStr = {"通过", "未通过"};

    private DistrictPopuWindown popuWindown;

    private LoadingDialog loadingDialog;
    TelephonyManager    mTelephonyManager;
    CellPhoneStateListener  mCellPhoneStateListener;
    private String [] ortherTest=new String[]{"CQT定点测试","DT测试","网络架构"};

    private String defultFilePath;  //服务器下发基站模板信息

    @Override
    protected void initView() {
        setContentView(R.layout.activity_base_info);
        tv_title = findView(R.id.tv_title);
        tx_tip_name =findView(R.id.tx_tip_name);
        tx_net_ways =findView(R.id.tx_net_ways);
        tx_operator = findView(R.id.tx_operator);
        add_antenna_tx =findView(R.id.add_antenna_tx);
        imageButton_right = findView(R.id.iv_right_button);
        ll_right_button =  findView(R.id.ll_right_button);
        iv_left = findView(R.id.iv_left);
        base_station_take_picture = findView(R.id.base_station_take_picture);
        iv_hongzhan_station_take_picture = findView(R.id.iv_hongzhan_station_take_picture);
        iv_hongzhan_antenna_picture = findView(R.id.iv_hongzhan_antenna_picture);
        iv_hongzhan_cover_picture = findView(R.id.iv_hongzhan_cover_picture);

         imageButton_right.setImageResource(R.mipmap.icon_menu);
        sp_cel = findView(R.id.sp_cel);
        sp_channel = findView(R.id.sp_channel);
        shan_rru = findView(R.id.shan_rru);
        sp_la_yuan = findView(R.id.sp_la_yuan);
        sp_village_corver_type = findView(R.id.sp_village_corver_type);
        sp_antenna_surface = findView(R.id.sp_antenna_surface);
        sp_antenna_platform_name = findView(R.id.sp_antenna_platform_name);
        sp_lat_long = findView(R.id.sp_lat_long);

        //editext 小区信息和基本信息，不可更改
        ed_city = findView(R.id.ed_city);
        et_county = findView(R.id.et_county);
        et_station_type = findView(R.id.et_station_type);
        et_enodebid = findView(R.id.et_enodebid);
        et_station_name = findView(R.id.et_station_name);
        et_planning_id = findView(R.id.et_planning_id);
        et_planning_name = findView(R.id.et_planning_name);
        et_station_situation = findView(R.id.et_station_situation);
        et_test_lng = findView(R.id.et_test_lng);
        et_test_lat = findView(R.id.et_test_lat);
        et_bbu_nums = findView(R.id.et_bbu_nums);
        et_uul_nums = findView(R.id.et_uul_nums);
        et_site_address = findView(R.id.et_site_address);
        et_village_cel_plannint_value = findView(R.id.et_village_cel_plannint_value);
        et_channel_cel_plannint_value = findView(R.id.et_channel_cel_plannint_value);
        et_rru_cel_plannint_value = findView(R.id.et_rru_cel_plannint_value);

        //小区信息和基本信息，可更改
        et_village_cel_cellection_value = findView(R.id.et_village_cel_cellection_value);
        et_channel_cel_cellection_value = findView(R.id.et_channel_cel_cellection_value);
        et_rru_cel_cellection_value = findView(R.id.et_rru_cel_cellection_value);
        et_pcl_plannint_value = findView(R.id.et_pcl_plannint_value);
        et_earfcn_plannint_value = findView(R.id.et_earfcn_plannint_value);
        et_cover_scene = findView(R.id.et_cover_scene);
        et_antenna_nums = findView(R.id.et_antenna_nums);

        ll_take_picture = findView(R.id.ll_take_picture);
        ll_take_picture_ant = findView(R.id.ll_take_picture_ant);
        add_ll = findView(R.id.add_ll);
        btn_save = findView(R.id.btn_save);
        scrollView = findView(R.id.sv_base_info);
        linearLayout = findView(R.id.ly_base_info);
        tvDistrictName = findView(R.id.tx_get_village_message);
        recyclerView = findView(R.id.rv_base_info);
        add_antenna_txs = findView(R.id.add_antenna_txs);
        spResult = findView(R.id.sp_result);
        tv_base_district_consistency = findView(R.id.tv_base_district_consistency);
        tv_collection_eci = findView(R.id.tv_collection_eci);
        ly_hongzhan = findView(R.id.ly_hongzhan);
        ly_base_photo = findView(R.id.ly_base_photo);
        ly_cover_photo = findView(R.id.ly_cover_photo);
        ly_panoramic = findView(R.id.ly_panoramic);
        ly_antenna = findView(R.id.ly_antenna);
        ly_cover = findView(R.id.ly_cover);



    }

    @Override
    protected void initData() {
        tv_title.setText(R.string.basic_name);

        Intent intent = getIntent();
        workorder = intent.getStringExtra(Const.IntentTransfer.WORKDERNO);
        baseInfoPath = intent.getStringExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH);
        indexType = Integer.valueOf(intent.getExtras().get(Const.IntentTransfer.TYPE).toString());
        orderType = intent.getStringExtra(Const.IntentTransfer.ORDER_TYPE);
        is_rru_array = getResources().getStringArray(R.array.is_rru);
        isDistrisct = getResources().getStringArray(R.array.village_corver_tp);
        defultFilePath=intent.getStringExtra(Const.IntentTransfer.DEFULT_INFO_PATH);
        //判断是宏站还是基础
        if(orderType.equals(Const.BASE)){
            ly_base_photo.setVisibility(View.VISIBLE);
            ly_hongzhan.setVisibility(View.GONE);
            ly_cover_photo.setVisibility(View.GONE);
        }else {
            ly_base_photo.setVisibility(View.GONE);
            ly_hongzhan.setVisibility(View.VISIBLE);
            ly_cover_photo.setVisibility(View.VISIBLE);
            add_antenna_txs.setText("其他测试");
        }

        if (!TextUtils.isEmpty(baseInfoPath)) {
            if (FileUtils.fileIsExists(baseInfoPath)) {
                String str = FileUtils.readSDFile(baseInfoPath);
                Gson gson = new Gson();
                testBean = gson.fromJson(str, BaseInfoTestBean.class);//拿到要展示的bean
                siteInfo = testBean.getSiteInfo();
                stationName = siteInfo.getSitename();
//                setData();
            }
        }else {
            if (FileUtils.fileIsExists(defultFilePath)) {
                String str = FileUtils.readSDFile(defultFilePath);
                Gson gson = new Gson();
                testBean = gson.fromJson(str, BaseInfoTestBean.class);//拿到要展示的bean
                siteInfo = testBean.getSiteInfo();
                stationName = siteInfo.getSitename();
            }
        }
        cellInfoList = siteInfo.getCellinfoList();
        if(siteInfo.getPrictureList()==null){
            siteInfo.setPrictureList(new ArrayList<String>());
        }  if(siteInfo.getPannoramicList()==null){
            siteInfo.setPannoramicList(new ArrayList<String>());
        }  if(siteInfo.getAntennaPannoramicList()==null){
            siteInfo.setAntennaPannoramicList(new ArrayList<String>());
        }
        setData();

//        loadData();

    }

  /*  private void loadData() {
        HttpParams params = new HttpParams();
        params.put("workorderNo", workorder);
//        params.put("workorderNo", "HCC_test_0001");
        OkGo.post(Const.Url.URL_GET_SITE_INFO).tag(this).params(params).execute(new ResultCallback(mContext, true) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                testBean = new Gson().fromJson(s, BaseInfoTestBean.class);
                siteInfo = testBean.getSiteInfo();

                cellInfoList = siteInfo.getCellinfoList();
                if(siteInfo.getPrictureList()==null){
                    siteInfo.setPrictureList(new ArrayList<String>());
                }  if(siteInfo.getPannoramicList()==null){
                    siteInfo.setPannoramicList(new ArrayList<String>());
                }  if(siteInfo.getAntennaPannoramicList()==null){
                    siteInfo.setAntennaPannoramicList(new ArrayList<String>());
                }
                stationName = siteInfo.getSitename();
                if (TextUtils.isEmpty(siteInfo.getWorkorderno())) {
                    ToastUtils.makeText(mContext, "工单号为空");
                } else {
                    setData();
                }
            }
        });
    }*/

    private void setData() {
        if (null != siteInfo) {
            setBaseData();
        }
        workorder = siteInfo.getWorkorderno();
        cellInfoList = siteInfo.getCellinfoList();
        if (cellInfoList != null && cellInfoList.size() > 0) {
            setDistrictData();
            if (TextUtils.isEmpty(workorder)) {
                ToastUtils.makeText(mContext, "工单号为空");
            } else {
                if(indexType==Const.ADD){
                    times = TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
                    jsonDir = FileUtils.getFileDir(Const.SaveFile.getDir(workorder,getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH), stationName+times));//当前页面要存的文件夹
                }else {
                    jsonDir=baseInfoPath.substring(0,baseInfoPath.lastIndexOf("/"));
                }
            }
        } else {
            return;
        }
        if (indexType == Const.LOOK) {
            ViewUtils.disableSubControls(scrollView);
            btn_save.setVisibility(View.GONE);
            base_station_take_picture.setEnabled(false);
            base_station_take_picture.setClickable(false);
            iv_hongzhan_station_take_picture.setEnabled(false);
            iv_hongzhan_station_take_picture.setClickable(false);
            iv_hongzhan_antenna_picture.setEnabled(false);
            iv_hongzhan_antenna_picture.setClickable(false);
            iv_hongzhan_cover_picture.setEnabled(false);
            iv_hongzhan_cover_picture.setClickable(false);
        }
        if(!TextUtils.isEmpty(siteInfo.getItemstate())){
            spResult.setSelection(siteInfo.getItemstate().equals("通过") ? 0 : 1);
        }
    }

    /**
     * 设置基本信息
     */
    private void setBaseData() {
        tx_tip_name.setText(siteInfo.getSitename());
        tx_net_ways.setText(siteInfo.getNetstandard());
        tx_operator.setText(siteInfo.getOperator());
        ed_city.setText(siteInfo.getCity());
        et_county.setText(siteInfo.getSiteareacourny());
        et_station_type.setText(siteInfo.getSitetype());

        et_station_situation.setText(siteInfo.getCommonsite());
        et_test_lng.setText(siteInfo.getLng());
        et_test_lat.setText(siteInfo.getLat());
        et_site_address.setText(siteInfo.getAddress());
        et_enodebid.setText(siteInfo.getEnodebid());
        et_planning_id.setText(siteInfo.getEnodebid());
        et_planning_name.setText(siteInfo.getSitename());
        et_station_name.setText(siteInfo.getSitename());
        et_bbu_nums.setText(siteInfo.getBbunum());
        et_uul_nums.setText(siteInfo.getRrunum());

        et_village_cel_plannint_value.setText(siteInfo.getCellinfoList().get(index).getCelleci());

        tv_base_district_consistency.setText(!TextUtils.isEmpty(siteInfo.getCellinfoList().get(index).getIscleci())?siteInfo.getCellinfoList().get(index).getIscleci():"是");

        if (siteInfo.getPrictureList().size() > 2) {
            base_station_take_picture.setVisibility(View.GONE);
        }else {
            base_station_take_picture.setVisibility(View.VISIBLE);
        }
        if (siteInfo.getPannoramicList().size() > 2) {
            iv_hongzhan_station_take_picture.setVisibility(View.GONE);
        }else {
            iv_hongzhan_station_take_picture.setVisibility(View.VISIBLE);
        }
        if (siteInfo.getAntennaPannoramicList().size() > 2) {
            iv_hongzhan_antenna_picture.setVisibility(View.GONE);
        }else {
            iv_hongzhan_antenna_picture.setVisibility(View.VISIBLE);
        }

        setLyPhotos(ll_take_picture,siteInfo.getPrictureList());
        setLyPhotos(ly_panoramic,siteInfo.getPannoramicList());
        setLyPhotos(ly_antenna,siteInfo.getAntennaPannoramicList());

        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.view_spinner_style_result, resultStr);
        spResult.setAdapter(arrA);
    }
    private void setLyPhotos(LinearLayout ly, final List<String> pathList){
        ly.removeAllViews();
        for (int i = 0; i < pathList.size(); i++) {
            final String imagePath = pathList.get(i);
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ImageView imgs = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(10, 0, 0, 0);
            imgs.setLayoutParams(params);
            imgs.setScaleType(ImageView.ScaleType.FIT_XY);
            imgs.setImageBitmap(bitmap);
            imgs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.showImageDialog(mContext, bitmap,indexType, new DialogUtil.OnImageDialogCallBack() {
                        @Override
                        public void isDelete(boolean isDelete) {
                            if (isDelete) {
                                pathList.remove(imagePath);
                                setBaseData();
                            }
                        }
                    });
                }
            });
            ly.addView(imgs, 0);
            ly.invalidate();
        }
    }

    private void setDistrictData() {
      CellinfoListBean bean = cellInfoList.get(index);
        lineInfoList=bean.getLineinfoList();
        for(CellinfoListBean.LineinfoListBean lineinfoListBean:lineInfoList){
            if(lineinfoListBean.getImgPathList()==null){
                lineinfoListBean.setImgPathList(new ArrayList<String>());
            }
        }
        tvDistrictName.setText("小区" + Integer.valueOf(index + 1) + "采集结果");
        et_channel_cel_plannint_value.setText(bean.getWorkfrqband());
        et_antenna_nums.setText(bean.getLinenum());
        et_rru_cel_plannint_value.setText(siteInfo.getRrunum());
        et_pcl_plannint_value.setText(bean.getPci());
        et_earfcn_plannint_value.setText(bean.getEarfcn());
        et_cover_scene.setText(bean.getScenes());
        initSpinner(sp_cel, is_rru_array);
        initSpinner(sp_channel, is_rru_array);
        initSpinner(shan_rru, is_rru_array);
        initSpinner(sp_la_yuan, is_rru_array);
        initSpinner(sp_village_corver_type, isDistrisct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new BaseInfoRecycleAdapter(this, lineInfoList, indexType);
        recyclerView.setAdapter(recycleAdapter);
        if (bean.getCoverList().size() > 2) {
            iv_hongzhan_cover_picture.setVisibility(View.GONE);
        }else {
            iv_hongzhan_cover_picture.setVisibility(View.VISIBLE);
        }
        setLyPhotos(ly_cover,bean.getCoverList());

        distrisctList = new ArrayList<>();
        for (int i = 0; i < cellInfoList.size(); i++) {
           CellinfoListBean bean1=cellInfoList.get(i);
            distrisctList.add(new DistrictBean("小区"+bean1.getId(),bean1.isUserAdd()));
        }
        if(indexType!=Const.LOOK){
            distrisctList.add(new DistrictBean("新增小区",false));
        }

        if (popuWindown == null) {
            popuWindown = new DistrictPopuWindown(this, distrisctList, new DistrictPopuWindowAdapter.DeleteListenes() {
                @Override
                public void onDeletePosition(int position) {
                    // TODO: 2017/9/13 删除
                    cellInfoList.remove(position);
                    index = position - 1;
                    setDistrictData();
                    popuWindown.setList(distrisctList);
                    popuWindown.dismiss();
                }
            }, new DistrictPopuWindown.ItemClickListener() {
                @Override
                public void onItenClick(int position) {
                    if (position == distrisctList.size() - 1) {
                        if(indexType!=Const.LOOK){
                       CellinfoListBean cellinfoListBean = new CellinfoListBean();
                          cellinfoListBean.setUserAdd(true);
                          cellinfoListBean.setId(cellInfoList.get(cellInfoList.size()-1).getId()+1);
                        cellInfoList.add(cellinfoListBean);
                        saveDistrisctInfo();
                        index = distrisctList.size() - 1;
                        setDistrictData();
                        popuWindown.setList(distrisctList);
                        }else {
                            index = position;
                            setDistrictData();
                            Toast.makeText(mContext, "小区" + position + 1, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        index = position;
                        setDistrictData();
                        Toast.makeText(mContext, "小区" + position + 1, Toast.LENGTH_SHORT).show();
                    }
                    popuWindown.dismiss();
                }
            });
        }
        recycleAdapter.setOnTakePhotoListener(new BaseInfoRecycleAdapter.onTakePhotoListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void setImageView(int position) {
                takePhoto(lineInfoList.get(position).getImgPathList().size(),siteInfo.getItemname()+"天线采集信息"+position+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                ImagePosition = position;
                isLine = true;
                flag=4;
            }
        });
        recycleAdapter.setOnChangeListener(new BaseInfoRecycleAdapter.onChangeListener() {
            @Override
            public void setChageBean(EditText et_angles_cellection, int position, boolean flag) {
                if (flag) {
                    startActivityForResult(new Intent(mContext, MechanicalActivity.class), Mechanical_RESULT_OK);
                    edtCl = et_angles_cellection;
                } else {
                    startActivityForResult(new Intent(mContext, CameraActivity.class), Camera_RESULT_OK);
                    edtValue = et_angles_cellection;
                }
            }
        });
        recycleAdapter.setOnDeleteAntenna(new BaseInfoRecycleAdapter.onDeleteAntennaListener() {
            @Override
            public void onDelete(int position) {
                setAdapterDelete(position);
            }
        });



        et_village_cel_plannint_value.setText(bean.getCelleci());
        et_village_cel_cellection_value.setText(bean.getCleci());
        et_channel_cel_cellection_value.setText(bean.getClchanal());
        et_rru_cel_cellection_value.setText(bean.getRru());
        if (!TextUtils.isEmpty(bean.getIscleci())) {
            sp_cel.setSelection(bean.getIscleci().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getIsclchanal())) {
            sp_channel.setSelection(bean.getIsclchanal().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getIsrrucell())) {
            shan_rru.setSelection(bean.getIsrrucell().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getIslayuan())) {
            sp_la_yuan.setSelection(bean.getIslayuan().equals("是") ? 0 : 1, true);
        }
        if (!TextUtils.isEmpty(bean.getIsclcorve())) {
            sp_village_corver_type.setSelection(bean.getIsclcorve().equals("室外") ? 0 : 1, true);
        }
    }

    private void setAdapterDelete(int position) {

        lineInfoList.remove(position);
        BaseApplication.lineinfoListBeen.remove(position);

        recycleAdapter = new BaseInfoRecycleAdapter(this, lineInfoList, indexType);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.setOnTakePhotoListener(new BaseInfoRecycleAdapter.onTakePhotoListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void setImageView(int position) {
                takePhoto(lineInfoList.get(position).getImgPathList().size(),siteInfo.getItemname()+"天线采集信息"+position+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                ImagePosition = position;
                isLine = true;
                flag=4;
            }
        });
        recycleAdapter.setOnChangeListener(new BaseInfoRecycleAdapter.onChangeListener() {
            @Override
            public void setChageBean(EditText et_angles_cellection, int position, boolean flag) {
                if (flag) {
                    startActivityForResult(new Intent(mContext, MechanicalActivity.class), Mechanical_RESULT_OK);
                    edtCl = et_angles_cellection;
                } else {
                    startActivityForResult(new Intent(mContext, CameraActivity.class), Camera_RESULT_OK);
                    edtValue = et_angles_cellection;
                }
            }
        });
        recycleAdapter.setOnDeleteAntenna(new BaseInfoRecycleAdapter.onDeleteAntennaListener() {
            @Override
            public void onDelete(int position) {
                setAdapterDelete(position);
            }
        });

    }


    @Override
    protected void initListener() {
        add_antenna_tx.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        ll_right_button.setOnClickListener(this);
        base_station_take_picture.setOnClickListener(this);
        iv_hongzhan_station_take_picture.setOnClickListener(this);
        iv_hongzhan_antenna_picture.setOnClickListener(this);
        iv_hongzhan_cover_picture.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        add_antenna_txs.setOnClickListener(this);
        tv_collection_eci.setOnClickListener(this);
    }


    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_antenna_tx:
                CellinfoListBean cellinfoListBean=new CellinfoListBean();
               CellinfoListBean.LineinfoListBean bean= cellinfoListBean.new  LineinfoListBean();
                bean.setUserAdd(true);
                bean.setId(cellInfoList.get(index).getLineinfoList().size());
                cellInfoList.get(index).getLineinfoList().add(bean);
                BaseApplication.lineinfoListBeen.add(bean);
                scrollView.scrollTo(0, linearLayout.getMeasuredHeight() - scrollView.getHeight());
                recycleAdapter.notifyItemInserted(cellInfoList.get(index).getLineinfoList().size());

                break;
            case R.id.btn_save:
                saveDistrisctInfo();
                String jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(jsonDir, times, stationName);
                if (indexType == Const.ADD) {
                    siteInfo.setItemname(stationName  + times);
                } else {
                    jsonFilePath = baseInfoPath;
                }
                siteInfo.setItemstate(spResult.getSelectedItem().toString());
                testBean.setSiteInfo(siteInfo);
                testBean.getSiteInfo().getCellinfoList().get(index).setLineinfoList(BaseApplication.lineinfoListBeen);
                JSON json = (JSON) JSON.toJSON(testBean);
                FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
                Intent intent = new Intent();
                intent.putExtra("jsonFilePath", jsonFilePath);
                setResult(0, intent);
                finish();
                break;
            case R.id.ll_right_button:
                popuWindown.showAsDropDown(imageButton_right, 0, 0);
                break;
            case R.id.base_station_take_picture:
                takePhoto(siteInfo.getPrictureList().size(),siteInfo.getItemname()+"_建筑物全景"+siteInfo.getPrictureList().size()+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                isLine = false;
                flag=0;
                break;
            case R.id.iv_hongzhan_station_take_picture:
                takePhoto(siteInfo.getPrictureList().size(),siteInfo.getItemname()+"_建筑物全景"+siteInfo.getPrictureList().size()+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                flag=1;
                break;
            case R.id.iv_hongzhan_antenna_picture:
                takePhoto(siteInfo.getPrictureList().size(),siteInfo.getItemname()+"天线整体照片"+siteInfo.getPrictureList().size()+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                flag=2;
                break;
            case R.id.iv_hongzhan_cover_picture:
                takePhoto(siteInfo.getPrictureList().size(),siteInfo.getItemname()+"扇区覆盖照片"+siteInfo.getPrictureList().size()+"_"+siteInfo.getLat()+"_"+siteInfo.getLng());
                flag=3;
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_collection_eci:
              if(loadingDialog==null){
                  loadingDialog=DialogUtil.showLodingDialog(mContext,"采集中");
              }else {
                  loadingDialog.show();
              }
              if(mTelephonyManager==null){
                  mTelephonyManager = (TelephonyManager) (BaseApplication.getAppContext().getSystemService(BaseApplication.getAppContext().TELEPHONY_SERVICE));
              }
              if(mCellPhoneStateListener==null){
                mCellPhoneStateListener = new CellPhoneStateListener(mTelephonyManager);
             }
                mTelephonyManager.listen(mCellPhoneStateListener,
                        PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                                PhoneStateListener.LISTEN_CALL_STATE |
                                PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        et_village_cel_cellection_value.setText(mCellPhoneStateListener.mLteCellBean.getCi());
                        if(et_village_cel_plannint_value.getText().toString().equals(et_village_cel_cellection_value.getText().toString())){
                            tv_base_district_consistency.setText("是");
                        }else {
                            tv_base_district_consistency.setText("否");
                        }
                        loadingDialog.dismiss();
                    }
                },3000);
                break;
            case R.id.add_antenna_txs:
                if(orderType.equals(Const.BASE)){
                    Intent CQTintent = new Intent(mContext, BaseCqtTestActivity.class);
                    CQTintent.putExtra(Const.IntentTransfer.WORKDERNO, siteInfo.getWorkorderno());
                    CQTintent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                    CQTintent.putExtra(Const.IntentTransfer.DEFULT_INFO_BEAN,testBean );
                    CQTintent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.BASE_CQT_TEST_COLLECTION );
                    startActivityForResult(CQTintent, 0);
                }else {
                    DialogUtil.showCommonListDialog(mContext,"选择其他测试",ortherTest,new DialogUtil.OnItemClick(){
                        @Override
                        public void ItemClick(int position) {
                            ToastUtils.makeText(mContext,ortherTest[position]);
                            // TODO: 2017/9/21  跳转到不同的测试页面
                            Intent intent = null;
                            switch (position){
                                case 0:
                                    intent   = new Intent(mContext, BaseCqtTestActivity.class);
                                    intent.putExtra(Const.IntentTransfer.WORKDERNO, siteInfo.getWorkorderno());
                                    intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                                    intent.putExtra(Const.IntentTransfer.DEFULT_INFO_BEAN,testBean );
                                    intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_CQT_TEST_COLLECTION );
                                    break;
                                case 1:
                                    intent = new Intent(mContext, GisActivity.class);
                                    intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_DT_TEST);
                                    intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                                    intent.putExtra(Const.IntentTransfer.DEFULT_INFO_BEAN,testBean );
                                    intent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, defultFilePath);
                                    break;   
                                case 2:
                                    intent=new Intent(mContext, NetFrameworkActivity.class);
                                    intent.putExtra(Const.IntentTransfer.FILE_PATH,Const.SaveFile.HONGZHAN_NET);
                                    intent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                                    intent.putExtra(Const.IntentTransfer.WORKDERNO,siteInfo.getWorkorderno());
                                    break;
                            }
                            startActivityForResult(intent, 0);

                        }
                    });
                }
                break;

        }
    }

    private void saveDistrisctInfo() {
       CellinfoListBean bean = cellInfoList.get(index);
        bean.setCleci(et_village_cel_cellection_value.getText().toString());
        bean.setClchanal(et_channel_cel_cellection_value.getText().toString());
        bean.setRru(et_rru_cel_cellection_value.getText().toString());
        bean.setClearfcn(et_earfcn_plannint_value.getText().toString());

        bean.setIscleci(tv_base_district_consistency.getText().toString());
        bean.setIsclchanal(sp_channel.getSelectedItem().toString());
        bean.setIsrrucell(shan_rru.getSelectedItem().toString());
        bean.setIslayuan(sp_la_yuan.getSelectedItem().toString());
        bean.setIsclcorve(sp_village_corver_type.getSelectedItem().toString());

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhoto(int llChildCount,String imageName) {
        if (llChildCount <= 3) {
            //判断是否有SD卡
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //检测是否有相机和读写文件权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
                } else {
                    startCameraAndroid7(imageName);
                }

            } else {
                ToastUtils.makeText(this, "SD卡不可用");
            }
        }
    }

    private File newFile;//拍照的文件
    String takeImagePath;//拍照的图片保存路径
    private Uri contentUri;

    private void startCameraAndroid7(String imageName) {
        if (!TextUtils.isEmpty(jsonDir)) {
            String times = TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
//            newFile = new File(jsonDir, ImageUtils.getFileNameTimeStamp(times + ".jpg"));
            newFile = new File(jsonDir, imageName+ ".jpg");
            takeImagePath = jsonDir + File.separator + times + ".jpg";

        } else {
            LogUtils.i(jsonDir + Const.SaveFile.FILE_DIR_CREATE_FAILED);
        }
        //第二参数是在manifest.xml定义 provider的authorities属性
        contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", newFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //兼容版本处理，因为 intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) 只在5.0以上的版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ClipData clip = ClipData.newUri(getContentResolver(), "A photo", contentUri);
            intent.setClipData(clip);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        processDataAndroid7(requestCode, resultCode, data);//Android7.0适配
    }

    private void processDataAndroid7(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            ContentResolver contentProvider = getContentResolver();
            ParcelFileDescriptor mInputPFD;
            try {
                long startTime = System.currentTimeMillis();
                LogUtils.i("获取contentProvider图片耗时： " + (System.currentTimeMillis() - startTime) + "毫秒");

                //获取contentProvider图片
                mInputPFD = contentProvider.openFileDescriptor(contentUri, "r");
                FileDescriptor fileDescriptor = mInputPFD.getFileDescriptor();
                //获取到拍摄的图片，大小为4068 x 3456 约等于1600万像素. 并且获取contentProvider图片耗时平均需要1375毫秒
                final Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);


                //初始化水印基本信息
                String currentTime = TimeUtils.getyyyyMMddHHmmss();
                marks[0] = siteInfo.getAddress();//基站小区名
                marks[1] = "任务工单:" + siteInfo.getWorkorderno();//任务工单
                marks[2] = "经度:" + siteInfo.getLng();//经度
                marks[3] = "纬度:" + siteInfo.getLat();//纬度
                // TODO: 2017/9/18  规划者还是采集值？？
                marks[4] = currentTime;//日期

                //压缩图片，然后保存
                ImageUtils.compressBitmap(mContext, takeImagePath, bitmap, 432, 576, marks, new ImageUtils.CompressCallback() {
                    @Override
                    public void onCompressSuccess(String imagePath, final Bitmap bitmap) {
                        LogUtils.i(TAG, "压缩图片onCompressSuccess: " + takeImagePath);

               /*         Message msg=handler.obtainMessage();
                        msg.arg1=1;
                        msg.obj=takeImagePath;
                        handler.sendMessage(msg);*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (flag){
                                    case 0:
                                    siteInfo.getPrictureList().add(takeImagePath);
                                    setBaseData();
                                        break;
                                    case 1:
                                        siteInfo.getPannoramicList().add(takeImagePath);
                                        setBaseData();
                                        break;
                                    case 2:
                                        siteInfo.getAntennaPannoramicList().add(takeImagePath);
                                        setBaseData();
                                        break;
                                    case 3:
                                        siteInfo.getCellinfoList().get(index).getCoverList().add(takeImagePath);
                                        setDistrictData();
                                        break;
                                    case 4:
                                        if(cellInfoList.get(index).getLineinfoList().get(ImagePosition).getImgPathList()==null){
                                            cellInfoList.get(index).getLineinfoList().get(ImagePosition).setImgPathList(new ArrayList<String>());
                                        }
                                        cellInfoList.get(index).getLineinfoList().get(ImagePosition).getImgPathList().add(takeImagePath);
                                         recycleAdapter.notifyDataSetChanged();
                                        break;
                                }
                            }
                        });
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
//                if (newFile != null && newFile.exists())
//                    newFile.delete();
            }
        } else if (requestCode == Mechanical_RESULT_OK && data != null) {
            edtCl.setText(data.getExtras().get(Const.IntentTransfer.resultCode_MechanicalActivity).toString());

        } else if (requestCode == Camera_RESULT_OK && data != null) {
            edtValue.setText(data.getExtras().get(Const.IntentTransfer.resultCode_CameraActivity).toString());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterPhoneState();
    }
    public void unRegisterPhoneState() {
        if (mCellPhoneStateListener != null && mTelephonyManager != null) {
            mTelephonyManager.listen(mCellPhoneStateListener, PhoneStateListener.LISTEN_NONE);
            mCellPhoneStateListener = null;
            mTelephonyManager = null;
        }
    }
}
