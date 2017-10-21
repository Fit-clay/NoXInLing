package com.beidian.beidiannonxinling.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.ShiFenBaseInfoAntennaAdapter;
import com.beidian.beidiannonxinling.adapter.ShiFenBbuRruAdapter;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.ShiFenBBU_RRUBean;
import com.beidian.beidiannonxinling.bean.ShiFenBaseInfoAntennaBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.listener.CellPhoneStateListener;
import com.beidian.beidiannonxinling.ui.activity.BaseCqtTestActivity;
import com.beidian.beidiannonxinling.ui.activity.LTEShiFenTestActivity;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.PhotoUitl;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ViewUtils;
import com.bumptech.glide.Glide;

import java.io.File;

import static com.beidian.beidiannonxinling.util.DialogUtil.showLodingDialog;


/**
 * Created by Eric on 2017/9/21.
 */

public class ShiFenBaseInfoFragment extends BaseLazyLoadFragment implements View.OnClickListener {
    private EditText edtCity, edtCounty, edtSiteType, edtEnodebid, edtSiteName, edtPalnId, edtPlanName, edtSamSituationo, edtLat, edtLng, edtBbuumber, edtRruNumber, edtSiteAddress,
            edtPlanEci, edtCollectionEci, edtPlanBand, edtCollectionBand, edtPlanRruNum, edtCollectionRruNum, edtPic, edtEarfcn, edtCover, edtAntennaNum;
    private Spinner spBand, spRru, spZoomOut, spCoverType, spResult;
    private TextView tvCollection, tvCollResult, tvDistractName, tvOtherTest;
    private ImageView ivBuildingZ, ivBuildingC, ivAddBbu, ivAddRru;

    private EditText edtBbuAddressInfo,edtRruAddressInfo;

    private ListView lv_bbu, lv_rru, lv_antenna;

    private RecyclerView rv_rru, rv_bbu;

    private BaseInfoTestBean.SiteInfoBean siteInfoBean;
    ShiFenBaseInfoAntennaAdapter antennaAdapter;
    private Context mContext;
    private int index = 0;
    private int indexType;
    String[] is_rru_array, isDistrisct;
    private String fileDir;
    private int BuildZ = 1230;
    private int BuildF = 1231;
    private ScrollView scrollView;
    private int ScrollY = 0;
    CellinfoListBean cellinfoListBean;
    ShiFenBbuRruAdapter RruRecycleAdapter, BbuRecycleAdapter;
    LoadingDialog loadingDialog;
    TelephonyManager mTelephonyManager;
    CellPhoneStateListener mCellPhoneStateListener;
    private String imagePath;    //用来存放拍照的图片路径，当返回时用于添加水印

    private int indexRru;      //用于新增rru时title设置
    private int indexBbu;      //用于新增bbu时title设置

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setData(siteInfoBean,index,fileDir,indexType);
            loadingDialog.dismiss();
        }
    };

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shifen_base_info, container, false);
        mContext = getContext();
        edtCity = findView(view, R.id.edt_city);
        scrollView = findView(view, R.id.shifen_base_sv);
        edtCounty = findView(view, R.id.edt_county);
        edtSiteType = findView(view, R.id.edt_site_type);
        edtEnodebid = findView(view, R.id.edt_enodebid);
        edtSiteName = findView(view, R.id.edt_site_name);
        edtPalnId = findView(view, R.id.edt_plan_id);
        edtPlanName = findView(view, R.id.edt_plan_name);
        edtSamSituationo = findView(view, R.id.edt_same_situation);
        edtLat = findView(view, R.id.edt_lat);
        edtLng = findView(view, R.id.edt_lng);
        edtBbuumber = findView(view, R.id.edt_bbu_number);
        edtRruNumber = findView(view, R.id.edt_rru_number);
        edtSiteAddress = findView(view, R.id.edt_site_address);
        edtPlanEci = findView(view, R.id.et_village_cel_plannint_value);
        edtCollectionEci = findView(view, R.id.et_village_cel_cellection_value);
        edtPlanBand = findView(view, R.id.et_channel_cel_plannint_value);
        edtCollectionBand = findView(view, R.id.et_channel_cel_cellection_value);
        edtPlanRruNum = findView(view, R.id.et_rru_cel_plannint_value);
        edtCollectionRruNum = findView(view, R.id.et_rru_cel_cellection_value);
        edtPic = findView(view, R.id.et_pcl_plannint_value);
        edtEarfcn = findView(view, R.id.et_earfcn_plannint_value);
        edtCover = findView(view, R.id.et_cover_scene);
        edtAntennaNum = findView(view, R.id.et_antenna_nums);
        spBand = findView(view, R.id.sp_band);
        spRru = findView(view, R.id.sp_is_rru);
        spZoomOut = findView(view, R.id.sp_la_yuan);
        spCoverType = findView(view, R.id.sp_village_corver_type);
        spResult = findView(view, R.id.sp_result);
        tvCollection = findView(view, R.id.tv_collection_eci);
        tvCollResult = findView(view, R.id.tv_base_district_consistency);
        ivBuildingZ = findView(view, R.id.iv_build_z);
        ivBuildingC = findView(view, R.id.iv_build_c);

        lv_antenna = findView(view, R.id.lv_antenna);
        tvDistractName = findView(view, R.id.tv_distract_name);
        ivAddBbu = findView(view, R.id.iv_add_bbu);
        ivAddRru = findView(view, R.id.iv_add_rru);
        rv_rru = findView(view, R.id.rv_rru);
        rv_bbu = findView(view, R.id.rv_bbu);
        tvOtherTest = findView(view, R.id.tv_other_test);
        edtBbuAddressInfo = findView(view, R.id.edt_bbu_address_info);
        edtRruAddressInfo = findView(view, R.id.edt_rru_address_info);
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    public void setData(BaseInfoTestBean.SiteInfoBean siteInfoBean, int index, String fileDir, int indexType) {
        this.siteInfoBean = siteInfoBean;
        this.index = index;
        this.fileDir = fileDir;
        this.indexType = indexType;
        if (indexType == Const.LOOK) {
            ViewUtils.disableSubControls(scrollView);
        }
        edtCity.setText(siteInfoBean.getCity());
        edtCounty.setText(siteInfoBean.getSiteareacourny());
        edtSiteType.setText(siteInfoBean.getSitetype());
        edtEnodebid.setText(siteInfoBean.getEnodebid());
        edtSiteName.setText(siteInfoBean.getSitename());
        edtPalnId.setText(siteInfoBean.getEnodebid());
        edtPlanName.setText(siteInfoBean.getSitename());
        edtSamSituationo.setText(siteInfoBean.getCommonsite());
        edtLat.setText(siteInfoBean.getLat());
        edtLng.setText(siteInfoBean.getLng());
        edtBbuumber.setText(siteInfoBean.getBbunum());
        edtRruNumber.setText(siteInfoBean.getRrunum());
        edtSiteAddress.setText(siteInfoBean.getAddress());
        is_rru_array = getResources().getStringArray(R.array.is_rru);
        isDistrisct = getResources().getStringArray(R.array.village_corver_tp);
        edtBbuAddressInfo.setText(siteInfoBean.getBbuaddressinfo());
        ivBuildingZ.setImageResource(R.mipmap.add_imageview);
        if (!TextUtils.isEmpty(siteInfoBean.getBuildingZ())) {
            if (FileUtils.fileIsExists(siteInfoBean.getBuildingZ())) {
                Glide.with(getContext()).load(siteInfoBean.getBuildingZ()).into(ivBuildingZ);
            }
        }
     /*   else {
            ivBuildingZ.setImageResource(R.mipmap.add_imageview);
        }*/
        ivBuildingC.setImageResource(R.mipmap.add_imageview);
        if (!TextUtils.isEmpty(siteInfoBean.getBuildingF())) {
            if (FileUtils.fileIsExists(siteInfoBean.getBuildingF())) {
                Glide.with(getContext()).load(siteInfoBean.getBuildingF()).into(ivBuildingC);
            }
        }
     /*   else {
            ivBuildingC.setImageResource(R.mipmap.add_imageview);
        }*/
        setBBuData();
        if (siteInfoBean.getCellinfoList().size() > 0) {
            setDistract();
        }
        doListener();
    }

    private void doListener() {
        if (indexType != Const.LOOK) {
            ivAddBbu.setOnClickListener(this);
            ivAddRru.setOnClickListener(this);
        }
        ivBuildingZ.setOnClickListener(this);
        ivBuildingC.setOnClickListener(this);
        tvCollection.setOnClickListener(this);
        tvOtherTest.setOnClickListener(this);
        spResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                siteInfoBean.setItemstate(spResult.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setBBuData() {
        rv_bbu.setLayoutManager(new LinearLayoutManager(mContext));
        BbuRecycleAdapter = new ShiFenBbuRruAdapter(mContext, siteInfoBean.getBbulist(), false, indexType);
        indexBbu = siteInfoBean.getBbulist().size();
        BbuRecycleAdapter.setAddOrDeleteAntennaListener(new ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener() {
            @Override
            public void addOrDeleteAntenna(boolean flag, int position) {
                if (flag) {
                    setAdapterDelete(false, position);
                }
            }
        });
        rv_bbu.setAdapter(BbuRecycleAdapter);


    }

    private void setDistract() {
        cellinfoListBean = siteInfoBean.getCellinfoList().get(index);
        tvDistractName.setText("小区" + Integer.valueOf(index + 1) + "采集结果");
        edtPlanBand.setText(cellinfoListBean.getWorkfrqband());
        edtAntennaNum.setText(cellinfoListBean.getLinenum());
        edtPlanRruNum.setText(cellinfoListBean.getRru());
        edtPic.setText(cellinfoListBean.getPci());
        edtEarfcn.setText(cellinfoListBean.getEarfcn());
        edtCover.setText(cellinfoListBean.getScenes());

        edtCollectionEci.setText(cellinfoListBean.getCelleci());
        edtCollectionBand.setText(cellinfoListBean.getClchanal());
        edtCollectionRruNum.setText(cellinfoListBean.getClrru());
        edtRruAddressInfo.setText(cellinfoListBean.getRruaddressinfo());

        ViewUtils.initSpinner(mContext, spBand, is_rru_array);
        ViewUtils.initSpinner(mContext, spRru, is_rru_array);
        ViewUtils.initSpinner(mContext, spZoomOut, is_rru_array);
        ViewUtils.initSpinner(mContext, spCoverType, isDistrisct);
        ViewUtils.initSpinner(mContext, spResult, new String[]{"通过", "未通过"});

        String state = siteInfoBean.getItemstate();
        spResult.setSelection(!TextUtils.isEmpty(state) && state.equals("通过") ? 0 : 1);

        tvCollResult.setText(cellinfoListBean.getIscleci());
        spBand.setSelection("是".equals(cellinfoListBean.getIsclchanal()) ? 0 : 1);
        spRru.setSelection("是".equals(cellinfoListBean.getIsrrucell()) ? 0 : 1);
        spZoomOut.setSelection("是".equals(cellinfoListBean.getIslayuan()) ? 0 : 1);
        spCoverType.setSelection("室外".equals(cellinfoListBean.getIsclcorve()) ? 0 : 1);


        rv_rru.setLayoutManager(new LinearLayoutManager(mContext));
        RruRecycleAdapter = new ShiFenBbuRruAdapter(mContext, cellinfoListBean.getRrulist(), true, indexType);
        indexRru= cellinfoListBean.getRrulist().size();

        RruRecycleAdapter.setChangePhotoLinstener(new ShiFenBbuRruAdapter.ChangePhotoLinstener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void changePhoto(int position) {
                imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + cellinfoListBean.getRrulist().get(position).getTitle() + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng(), mContext, position);
                siteInfoBean.getCellinfoList().get(index).getRrulist().get(position).setImageUrl(imagePath);
            }
        });
        RruRecycleAdapter.setAddOrDeleteAntennaListener(new ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener() {
            @Override
            public void addOrDeleteAntenna(boolean flag, int position) {
                if (flag) {
                    setAdapterDelete(true, position);
                }
            }
        });
        rv_rru.setAdapter(RruRecycleAdapter);

        setAntenna();
    }

    private void setAntenna() {
        antennaAdapter = new ShiFenBaseInfoAntennaAdapter(getContext(), cellinfoListBean.getAntennaList());
        antennaAdapter.setPhotoLinstener(new ShiFenBaseInfoAntennaAdapter.ChangeAntennaPhoto() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void ChangePhoto(final int position, int flag) {
                ShiFenBaseInfoAntennaBean antennaBean = cellinfoListBean.getAntennaList().get(position);
                switch (flag) {
                    case Const.Antenna1:
                        if (!TextUtils.isEmpty(antennaBean.getAntenna1()) && FileUtils.fileIsExists(antennaBean.getAntenna1())) {
                            DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(antennaBean.getAntenna1()), indexType, new DialogUtil.OnImageDialogCallBack() {
                                @Override
                                public void isDelete(boolean isDelete) {
                                    if (isDelete) {
                                        new File(cellinfoListBean.getAntennaList().get(position).getAntenna1()).delete();
                                        cellinfoListBean.getAntennaList().get(position).setAntenna1("");
                                        setData(siteInfoBean, index, fileDir, indexType);
                                    }
                                }
                            });
                        } else {
                            if (indexType != Const.LOOK) {
                                imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + "楼内天线1" + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng() + "_" + position, mContext, position);
                                cellinfoListBean.getAntennaList().get(position).setAntenna1(imagePath);
                            }
                        }

                        break;
                    case Const.Antenna2:
                        if (!TextUtils.isEmpty(antennaBean.getAntenna2()) && FileUtils.fileIsExists(antennaBean.getAntenna2())) {
                            DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(antennaBean.getAntenna2()), indexType, new DialogUtil.OnImageDialogCallBack() {
                                @Override
                                public void isDelete(boolean isDelete) {
                                    if (isDelete) {
                                        new File(cellinfoListBean.getAntennaList().get(position).getAntenna2()).delete();
                                        cellinfoListBean.getAntennaList().get(position).setAntenna2("");
                                        setData(siteInfoBean, index, fileDir, indexType);
                                    }
                                }
                            });
                        } else {
                            if (indexType != Const.LOOK) {
                                imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + "楼内天线2" + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng() + "_" + position, mContext, position);
                                cellinfoListBean.getAntennaList().get(position).setAntenna2(imagePath);
                            }
                        }
                        break;
                    case Const.AntennaBuild:
                        if (!TextUtils.isEmpty(antennaBean.getBuildPath()) && FileUtils.fileIsExists(antennaBean.getBuildPath())) {
                            DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(antennaBean.getBuildPath()), indexType, new DialogUtil.OnImageDialogCallBack() {
                                @Override
                                public void isDelete(boolean isDelete) {
                                    if (isDelete) {
                                        new File(cellinfoListBean.getAntennaList().get(position).getBuildPath()).delete();
                                        cellinfoListBean.getAntennaList().get(position).setAntenna1("");
                                        setData(siteInfoBean, index, fileDir, indexType);
                                    }
                                }
                            });
                        } else {
                            if (indexType != Const.LOOK) {
                                imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + "建筑物全景图" + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng() + "_" + position, mContext, position);
                                cellinfoListBean.getAntennaList().get(position).setBuildPath(imagePath);
                            }
                        }
                        break;
                }
            }
        });
        antennaAdapter.setAddOrDeleteAntennaListener(new ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener() {
            @Override
            public void addOrDeleteAntenna(boolean flag, int position) {
                if (indexType != Const.LOOK) {
                    if (flag) {
                        cellinfoListBean.getAntennaList().remove(position);
                    } else {
                        cellinfoListBean.getAntennaList().add(new ShiFenBaseInfoAntennaBean("", "", ""));
                    }
                    antennaAdapter.notifyDataSetChanged();
                }
            }
        });
        lv_antenna.setAdapter(antennaAdapter);

    }

    private void setAdapterDelete(final boolean adapterType, int position) {
        if (adapterType) {
            cellinfoListBean.getRrulist().remove(position);
            RruRecycleAdapter = new ShiFenBbuRruAdapter(mContext, cellinfoListBean.getRrulist(), true, indexType);
            rv_rru.setAdapter(RruRecycleAdapter);
            RruRecycleAdapter.setAddOrDeleteAntennaListener(new ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener() {
                @Override
                public void addOrDeleteAntenna(boolean flag, int position) {
                    if (flag) {
                        setAdapterDelete(adapterType, position);
                    }
                }
            });
            RruRecycleAdapter.setChangePhotoLinstener(new ShiFenBbuRruAdapter.ChangePhotoLinstener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void changePhoto(int position) {
                    imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + cellinfoListBean.getRrulist().get(position).getTitle() + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng(), mContext, position);
                    siteInfoBean.getCellinfoList().get(index).getRrulist().get(position).setImageUrl(imagePath);
                }
            });
        } else {
            siteInfoBean.getBbulist().remove(position);
            BbuRecycleAdapter = new ShiFenBbuRruAdapter(mContext, siteInfoBean.getBbulist(), false, indexType);
            rv_bbu.setAdapter(BbuRecycleAdapter);
            BbuRecycleAdapter.setAddOrDeleteAntennaListener(new ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener() {
                @Override
                public void addOrDeleteAntenna(boolean flag, int position) {
                    if (flag) {
                        setAdapterDelete(adapterType, position);
                    }
                }
            });
        }

    }

    @Override
    protected void initListener() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_bbu:
                siteInfoBean.getBbulist().add(new ShiFenBBU_RRUBean("BBU-"+indexBbu, "233333", "", "",""));
                indexBbu++;
                BbuRecycleAdapter.notifyItemInserted( siteInfoBean.getBbulist().size());
                break;
            case R.id.iv_add_rru:
                siteInfoBean.getCellinfoList().get(index).getRrulist().add(new ShiFenBBU_RRUBean("RRU-"+indexRru, "233333", "", "",""));
                indexRru++;
                RruRecycleAdapter.notifyItemInserted(siteInfoBean.getCellinfoList().get(index).getRrulist().size());
                break;
            case R.id.tv_other_test:
                DialogUtil.showCommonListDialog(mContext, "选择其他测试", new String[]{"主设备测试", "分布式系统测试"}, new DialogUtil.OnItemClick() {
                    @Override
                    public void ItemClick(int position) {
                        Intent cqtIntent = null;
                        switch (position) {
                            case 0:
                                cqtIntent = new Intent(mContext, BaseCqtTestActivity.class);
                                cqtIntent.putExtra(Const.IntentTransfer.FILE_PATH, Const.SaveFile.SHIFEN_MASTER_DEVICE);
                                cqtIntent.putExtra(Const.IntentTransfer.ORDER_TYPE, true);
                                break;
                            case 1:
                                cqtIntent = new Intent(mContext, LTEShiFenTestActivity.class);
                                cqtIntent.putExtra("title", "室内打点测试");
                                cqtIntent.putExtra(Const.IntentTransfer.FILE_PATH, Const.SaveFile.SHIFEN_DISTRIBUTED);
                                break;
                        }
                        cqtIntent.putExtra(Const.IntentTransfer.DEFULT_INFO_PATH, getActivity().getIntent().getStringExtra(Const.IntentTransfer.DEFULT_INFO_PATH));
                        cqtIntent.putExtra(Const.IntentTransfer.TYPE, Const.ADD);
                        cqtIntent.putExtra(Const.IntentTransfer.WORKDERNO, siteInfoBean.getWorkorderno());

                        mContext.startActivity(cqtIntent);
                    }
                });

                break;
            case R.id.iv_build_z:
                if (!TextUtils.isEmpty(siteInfoBean.getBuildingZ()) && FileUtils.fileIsExists(siteInfoBean.getBuildingZ())) {
                    DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(siteInfoBean.getBuildingZ()), indexType, new DialogUtil.OnImageDialogCallBack() {
                        @Override
                        public void isDelete(boolean isDelete) {
                            if (isDelete) {
                                new File(siteInfoBean.getBuildingZ()).delete();
                                siteInfoBean.setBuildingZ("");
                                setData(siteInfoBean, index, fileDir, indexType);
                            }
                        }
                    });
                } else {
                    if (indexType != Const.LOOK) {
                        imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + "建筑物全景正面" + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng(), getContext(), BuildZ);
                        siteInfoBean.setBuildingZ(imagePath);
                    }
                }
                break;
            case R.id.iv_build_c:
                if (!TextUtils.isEmpty(siteInfoBean.getBuildingF()) && FileUtils.fileIsExists(siteInfoBean.getBuildingF())) {
                    DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(siteInfoBean.getBuildingF()), indexType, new DialogUtil.OnImageDialogCallBack() {
                        @Override
                        public void isDelete(boolean isDelete) {
                            if (isDelete) {
                                new File(siteInfoBean.getBuildingF()).delete();
                                siteInfoBean.setBuildingF("");
                                setData(siteInfoBean, index, fileDir, indexType);
                            }
                        }
                    });
                } else {
                    if (indexType != Const.LOOK) {
                        imagePath=PhotoUitl.takePhoto(fileDir, siteInfoBean.getItemname() + "_" + "建筑物全景反面" + "_" + siteInfoBean.getLat() + "_" + siteInfoBean.getLng(), getContext(), BuildF);
                        siteInfoBean.setBuildingF(imagePath);
                    }
                }
                break;
            case R.id.tv_collection_eci:
                if (loadingDialog == null) {
                    loadingDialog = showLodingDialog(mContext, "采集中");
                } else {
                    loadingDialog.show();
                }
                if (mTelephonyManager == null) {
                    mTelephonyManager = (TelephonyManager) (BaseApplication.getAppContext().getSystemService(BaseApplication.getAppContext().TELEPHONY_SERVICE));
                }
                if (mCellPhoneStateListener == null) {
                    mCellPhoneStateListener = new CellPhoneStateListener(mTelephonyManager);
                }
                mTelephonyManager.listen(mCellPhoneStateListener,
                        PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                                PhoneStateListener.LISTEN_CALL_STATE |
                                PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edtCollectionEci.setText(mCellPhoneStateListener.mLteCellBean.getCi());
                        if (edtPlanEci.getText().toString().equals(edtCollectionEci.getText().toString())) {
                            tvCollResult.setText("是");
                        } else {
                            tvCollResult.setText("否");
                        }
                        loadingDialog.dismiss();
                    }
                }, 3000);
                break;
        }
    }

    public void saveInfo() {
        cellinfoListBean.setCelleci(edtCollectionEci.getText().toString());
        cellinfoListBean.setClchanal(edtCollectionBand.getText().toString());
        cellinfoListBean.setClrru(edtCollectionRruNum.getText().toString());


        cellinfoListBean.setIscleci(tvCollResult.getText().toString());
        cellinfoListBean.setIscleci(tvCollResult.getText().toString());
        cellinfoListBean.setIsclchanal(spBand.getSelectedItem().toString());
        cellinfoListBean.setIsrrucell(spRru.getSelectedItem().toString());
        cellinfoListBean.setIslayuan(spZoomOut.getSelectedItem().toString());
        cellinfoListBean.setIsclcorve(spCoverType.getSelectedItem().toString());
    }


    @Override
    public void onPause() {
        super.onPause();
        ScrollY = scrollView.getScrollY();
        saveInfo();
    }


    public void onFragmentResult(){
        if(FileUtils.fileIsExists(imagePath)){
            if(loadingDialog==null){
                loadingDialog= DialogUtil.showLodingDialog(mContext,"加载中");
            }else {
                if(!loadingDialog.isShowing()){
                    loadingDialog.show();
                }
            }
            String[] marks=new String[5];
            String currentTime = TimeUtils.getyyyyMMddHHmmss();
            marks[0] = siteInfoBean.getAddress();//基站小区名
            marks[1] = "任务工单:" + siteInfoBean.getWorkorderno();//任务工单
            marks[2] = "经度:" + siteInfoBean.getLng();//经度
            marks[3] = "纬度:" + siteInfoBean.getLat();//纬度
            marks[4] = currentTime;//日期
            PhotoUitl.AddMarkForImage(mContext, imagePath, marks, new PhotoUitl.ZidImageCallback() {
                @Override
                public void callBack(String path, Bitmap bitmap) {
                    Message msg=handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            });

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, ScrollY);
            }
        }, 500);
    }

}
