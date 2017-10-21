package com.beidian.beidiannonxinling.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Dot;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestBaseBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.ui.fragment.GisTestinfoFragment;
import com.beidian.beidiannonxinling.ui.widget.ColorConfigDialog;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.util.BaiDuMapDraw;
import com.beidian.beidiannonxinling.util.ColorConfigUtils;
import com.beidian.beidiannonxinling.util.DensityUtil;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.coreprogress.GisDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class GisActivity extends BaseActivity implements View.OnClickListener, SensorEventListener, GisTestinfoFragment.ResultLsitener {

    private boolean isShowTab   = true;
    private boolean isSatellite = false;

    public  MapView        mMapView;
    private LinearLayout   mLayoutTabInfo;
    private ImageView      mImg_gis_show_tab;
    private View           mInc_title;
    private View           mLl_back;
    private View           mTv_title;
    private View           mLl_right_button;
    private ImageView      iv_right_button;
    private GisActivity    mGisActivity;
    private ListView       mLv_menu_list;
    private LinearLayout   mLl_color_table;
    private int            mPadding;
    private View           mPopwindos_right_top_menu;
    private PopupWindow    mPopupWindow;
    public  BaiduMap       mBaiduMap;
    private String[]       mArr_data;
    private LocationClient mLocClient;
    private SensorManager  mSensorManager;
    private Double lastX             = 0.0;
    public  int    mCurrentDirection = 0;
    public  double mCurrentLat       = 0.0;
    public  double mCurrentLon       = 0.0;
    private float mCurrentAccracy;
    //    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;

    private GisTestinfoFragment mGisTestinfoFragment;
    Spinner  spinner;
    String[] spin_way;
    Button   btn_conforms;
    SeekBar  seekBar;
    TextView textView, tx_yd;
    LinearLayout ll_gis, ll_yd;
    int heightPixels, widthPixels, textHeight, textWidth;
    //    private OneKeyTestBean oneKeyTestBean;
    private GisDialog mGisDialog;

    private boolean        isCellNameShow  = false;
    private boolean        isCellShow      = false;
    public  OneKeyTestBean mOneKeyTestBean = new OneKeyTestBean();//封装数据
    private Button mBt_stop;
    private List<ColorConfigItem> mColorItemBean = ColorConfigUtils.getColorItemBean(Const.ColorManager.COVERAGE_RSRP);
    private int                   mColorItemType = Const.ColorManager.COVERAGE_RSRP;
    private List<Overlay>         mOverlays      = new ArrayList<Overlay>();
    private int                   minSeekBar     = 300;//seekbar最小值
    private BaiDuMapDraw mBaiDuMapDraw;
    private String mType; //页面类型,重测还是新增Const.ADD   Const.RESET
    protected void initData() {
        mBaiDuMapDraw = new BaiDuMapDraw(this, mBaiduMap);//初始化百度地图绘制类
        Intent intent = getIntent();
        mType = intent.getExtras().get(Const.IntentTransfer.TYPE).toString();
        spin_way = getResources().getStringArray(R.array.ways_test);
        initSpinner(spinner, spin_way);
    }

    /**
     * 开始打点
     */
    public void startGisTest() {

        //开始测
        mGisTestinfoFragment.testStart(mOneKeyTestBean);
        mLl_back.setVisibility(View.VISIBLE);
    }

    /**
     * 打点
     *
     * @param testResult
     */
    int a = 0;

    private void dataGis(TestResult testResult) {
        Log.d(TAG, "dataGis: " + a);
        a++;
        LatLng latLng = new LatLng(Double.valueOf(testResult.getLat()) + a * 0.0001, Double.valueOf(testResult.getLng()) + a * 0.00001);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Const.TestManage.RESULT_TYPE_SIGNALLING, testResult);
        OverlayOptions ooDot = new DotOptions().center(latLng).radius(8)
                .color(getColorValue(testResult)).extraInfo(bundle);
        //        colorConfigItem.getColor()

        Overlay overlay = mBaiduMap.addOverlay(ooDot);
        mOverlays.add(overlay);
    }

    /**
     * 百度地图截图
     */
    public void snapshot() {
        if (mBaiduMap == null || mOneKeyTestBean == null) {
            return;
        }
        // 截图，在SnapshotReadyCallback中保存图片到 sd 卡
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            public void onSnapshotReady(Bitmap snapshot) {
                File file = new File("/storage/emulated/0/BeiDian/signalling.png");
                FileOutputStream out;
                try {
                    out = new FileOutputStream(file);
                    if (snapshot.compress(
                            Bitmap.CompressFormat.PNG, 100, out)) {
                        out.flush();
                        out.close();
                    }
                    Toast.makeText(GisActivity.this,
                            "屏幕截图成功，图片存在: " + file.toString(),
                            Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Toast.makeText(GisActivity.this, "正在截取屏幕图片...",
                Toast.LENGTH_SHORT).show();


    }

    /**
     * 更新打点
     */
    private void upDataGis() {
        final List<Overlay> overlays = new ArrayList<>();
        overlays.addAll(mOverlays);
        if (overlays.size() <= 0) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Overlay overlay : overlays) {
                    Bundle extraInfo = overlay.getExtraInfo();
                    TestResult testResult = (TestResult) extraInfo.get(Const.TestManage.RESULT_TYPE_SIGNALLING);
                    ((Dot) overlay).setColor(getColorValue(testResult));
                }
            }
        }).start();
    }


    @Override
    protected void initListener() {
        mImg_gis_show_tab.setOnClickListener(this);
        mLl_back.setOnClickListener(this);
        mLl_right_button.setOnClickListener(this);
        btn_conforms.setOnClickListener(this);
        mGisTestinfoFragment.setResultLsitener(this);
        mBt_stop.setOnClickListener(this);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int seekProgress = seekBar.getProgress();
                if (seekProgress < 50) {
                    seekBar.setProgress(0);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 50 && seekProgress < 150) {
                    seekBar.setProgress(100);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 150 && seekProgress < 250) {
                    seekBar.setProgress(200);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 250 && seekProgress < 350) {
                    seekBar.setProgress(300);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 350 && seekProgress < 450) {
                    seekBar.setProgress(400);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 450 && seekProgress < 550) {
                    seekBar.setProgress(500);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 550 && seekProgress < 650) {
                    seekBar.setProgress(600);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                } else if (seekProgress >= 650 && seekProgress < 750) {
                    seekBar.setProgress(700);
                    textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText((seekBar.getProgress() + minSeekBar) + "ms");
            }
        });
    }

    protected void initView() {
        mGisActivity = this;
        setContentView(R.layout.activity_gis);
        mPadding = DensityUtil.dip2px(this, 5);

        mLayoutTabInfo = (LinearLayout) findViewById(R.id.layout_tab_info);//测试信息显示的tab
        mImg_gis_show_tab = (ImageView) findViewById(R.id.img_gis_show_tab);//切换信息显示tab的按钮
        mInc_title = (View) findViewById(R.id.inc_title);//引用layout-title
        mLl_back = mInc_title.findViewById(R.id.ll_back);//返回按钮
        mTv_title = mInc_title.findViewById(R.id.tv_title);//title
        mBt_stop = (Button) findViewById(R.id.bt_stop);     //停止按钮
        mGisTestinfoFragment = new GisTestinfoFragment();
        //        MyLocationListenner myLocationListenner = new MyLocationListenner();
        mGisTestinfoFragment.initeFragment(this);
        spinner = (Spinner) findViewById(R.id.spinner2);
        btn_conforms = (Button) findViewById(R.id.btn_conforms);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.tx_seekbar);
        tx_yd = (TextView) findViewById(R.id.tx_yd);
        ll_gis = (LinearLayout) findViewById(R.id.ll_gis);
        ll_yd = (LinearLayout) findViewById(R.id.ll_yd);
        //        seekBar.setProgress(100);
        //  mGisTestinfoFragment = new GisTestinfoFragment();
        //  mGisTestinfoFragment.initeFragment(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_tab_info, mGisTestinfoFragment, "mGisTestinfoFragment");
        fragmentTransaction.commit();
        initeBaidu();
        initMenu();
        initColorConfig();
        initTestConfigDialog();


    }

    /**
     * 初始化测试配置dialog
     */
    private void initTestConfigDialog() {
        mGisDialog = new GisDialog(GisActivity.this);
        mGisDialog.show();
        //// TODO: 2017/10/6 测试用,要删掉
        //        String strAddress = getAssetsString("work.txt");
        //获取上一级界面传递过来的数据
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String path = (String) extras.get(Const.IntentTransfer.DEFULT_INFO_PATH);//获取基站工单的实例文件地址
        String strAddress = FileUtils.readSDFile(path);
        BaseInfoTestBean baseInfoTestBean = new Gson().fromJson(strAddress, BaseInfoTestBean.class);
        mOneKeyTestBean.setBaseInfoTestBean(baseInfoTestBean);//设置测试基本信息实例
        mOneKeyTestBean.setWorkorderno(baseInfoTestBean.getSiteInfo().getWorkorderno());//设置工单号


        //获取模板的数据
        HttpParams params = new HttpParams();
        params.put("account", BaseApplication.getUserInfo().getUsername());
        params.put("workorderNo", baseInfoTestBean.getSiteInfo().getWorkorderno());
        final LoadingDialog loadingDialog = DialogUtil.showLodingDialog(this, "正在请求数据，请稍后。。。");
        loadingDialog.show();
        OkGo.post(Const.Url.URL_GET_TEST_MODEL_BY_ACCOUNT).tag(mContext).params(params).execute(new ResultCallback(mContext, false) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {
                loadingDialog.dismiss();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                //                TestModelBean cqtTestModelBean = new Gson().fromJson(s, TestModelBean.class);
                TestModelBean testModelBean = JSONArray.parseObject(s, TestModelBean.class);
                mOneKeyTestBean.setTestModelBean(testModelBean);
                mGisDialog.initView(mOneKeyTestBean, mGisActivity);//把模板数据传给dialog
                loadingDialog.dismiss();
            }
        });

    }


    /**
     * 初始化百度
     */
    private void initeBaidu() {

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //        double x0 = 39.93923, y0 = 116.357428;

    }


    /**
     * 初始化色标
     */
    private void initColorConfig() {
        mLl_color_table = (LinearLayout) findViewById(R.id.ll_color_table);//图例参照表
        mLl_color_table.removeAllViews();

        if (mColorItemBean == null) {
            return;
        }
        for (ColorConfigItem colorConfigItem : mColorItemBean) {
            TextView textView = new TextView(this);
            textView.setText("    " + colorConfigItem.getMinValue() + "    ");
            textView.setBackgroundColor(colorConfigItem.getColor());
            textView.setTag(colorConfigItem.getColor());
            textView.setPadding(mPadding, mPadding, mPadding, mPadding);
            //            textView.setLayoutParams(lp);
            mLl_color_table.addView(textView);

        }


    }

    /**
     * 获取当前需要打点的颜色
     *
     * @param testResult
     * @return
     */
    private int getColorValue(TestResult testResult) {
        Integer value = null;
        switch (mColorItemType) {
            case Const.ColorManager.COVERAGE_PCI:
                String pci = testResult.getPci();
                if (pci == "") {
                    break;
                }
                value = new Integer(pci);
                break;
            case Const.ColorManager.COVERAGE_RSRP:
                String rsrp = testResult.getRsrp();
                if (rsrp == "") {
                    break;
                }
                value = new Integer(rsrp);
                break;
            //            case Const.COVERAGE_RSRQ:
            //                value = new Integer(testResult.getr());
            //                break;
            case Const.ColorManager.COVERAGE_SINR:
                String sinr = testResult.getSinr();
                if (sinr == "") {
                    break;
                }
                value = new Integer(sinr);
                break;
            case Const.ColorManager.COVERAGE_DL:
                String speedDL = testResult.getSpeedDL();
                if (speedDL == "") {
                    break;
                }
                value = new Integer(speedDL);
                break;
            case Const.ColorManager.COVERAGE_UL:
                String speedUL = testResult.getSpeedUL();
                if (speedUL == "") {
                    break;
                }
                value = new Integer(speedUL);
                break;
        }
        if (value == null) {
            return 0xFF000000;
        }
        for (ColorConfigItem colorConfigItem : mColorItemBean) {
            if (colorConfigItem.getMaxValue() > value && colorConfigItem.getMinValue() <= value) {
                return colorConfigItem.getColor();
            }
        }
        return 0xFF000000;
    }

    /**
     * 初始化menu菜单
     */
    private void initMenu() {

        mLl_right_button = mInc_title.findViewById(R.id.ll_right_button);//menu菜单按钮
        iv_right_button = ((ImageView) mInc_title.findViewById(R.id.iv_right_button));//menu菜单按钮图片
        iv_right_button.setImageResource(R.mipmap.icon_menu);//设置menu图标
        mPopwindos_right_top_menu = LayoutInflater.from(this).inflate(R.layout.popwindos_right_top_menu, null);//右上角菜单popwind
        mPopupWindow = new PopupWindow(mPopwindos_right_top_menu, DensityUtil.dip2px(this, 90), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mLv_menu_list = (ListView) mPopwindos_right_top_menu.findViewById(R.id.lv_menu_list);
        //定义数据源作为ListView内容
        mArr_data = new String[]{"图例选择", "距离测量", "卫星地图", "小区名显示", "扇区显示", "位置解锁", "测试配置", "vbn"};

        //新建一个数组适配器ArrayAdapter绑定数据，参数(当前的Activity，布局文件，数据源)
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, R.layout.item_menu_textview, mArr_data);
        mLv_menu_list.setAdapter(arr_adapter);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://图例选择
                        ToastUtils.makeText(mGisActivity, "0" + ((TextView) view).getText());
                        mPopupWindow.dismiss();

                        final ColorConfigDialog colorConfigDialog = new ColorConfigDialog(GisActivity.this);
                        colorConfigDialog.setNegativeButtonClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        colorConfigDialog.setPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (colorConfigDialog.checkValue > -1) {
                                    mColorItemBean = colorConfigDialog.getColorItemBean(colorConfigDialog.checkValue);
                                    mColorItemType = colorConfigDialog.checkValue;
                                    initColorConfig();
                                    upDataGis();//更新打点数据
                                }
                                dialog.dismiss();
                            }
                        });
                        colorConfigDialog.show();

                        break;
                    case 1://距离测量
                        ToastUtils.makeText(mGisActivity, "1" + ((TextView) view).getText());
                        mPopupWindow.dismiss();
                        mBaiDuMapDraw.distanceMeasure();
                        break;
                    case 2://卫星地图
                        ToastUtils.makeText(mGisActivity, "2" + ((TextView) view).getText());
                        mPopupWindow.dismiss();
                        if (!isSatellite) {
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                            mArr_data[2] = "基础地图";
                            isSatellite = true;
                        } else {
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                            mArr_data[2] = "卫星地图";
                            isSatellite = false;
                        }
                        break;
                    case 3://小区名称显示
                        ToastUtils.makeText(mGisActivity, "3" + ((TextView) view).getText());
                        mPopupWindow.dismiss();

                        if (isCellNameShow) {
                            mArr_data[3] = "小区名称显示";
                            mBaiDuMapDraw.removeAroundSiteInfo("名称");//删除绘制的扇区
                            isCellNameShow = false;
                        } else {
                            mArr_data[3] = "小区名称隐藏";
                            mBaiDuMapDraw.drawAroundSiteInfo("名称");//获取基站信息并且绘制扇区
                            isCellNameShow = true;
                        }
                        break;
                    case 4://扇区显示
                        ToastUtils.makeText(mGisActivity, "4" + ((TextView) view).getText());
                        mPopupWindow.dismiss();

                        if (isCellShow) {
                            mArr_data[4] = "扇区显示";
                            mBaiDuMapDraw.removeAroundSiteInfo("扇区");//删除绘制的扇区
                            isCellShow = false;
                        } else {
                            mArr_data[4] = "扇区隐藏";
                            mBaiDuMapDraw.drawAroundSiteInfo("扇区");//获取基站信息并且绘制扇区
                            isCellShow = true;
                        }
                        break;
                    case 5://位置锁定
                        ToastUtils.makeText(mGisActivity, "5" + ((TextView) view).getText());
                        mPopupWindow.dismiss();
                        if (mBaiDuMapDraw.isBaiDuMapLock) {
                            mArr_data[5] = "位置锁定";
                            mBaiDuMapDraw.isBaiDuMapLock = false;
                        } else {
                            mArr_data[5] = "位置解锁";
                            mBaiDuMapDraw.isBaiDuMapLock = true;
                        }
                        break;
                    case 6://测试配置
                        ToastUtils.makeText(mGisActivity, "6" + ((TextView) view).getText());
                        mPopupWindow.dismiss();
                        mGisDialog.show();
                        break;
                    case 7:
                        //                        snapshot();
                        break;

                }
            }


        };
        mLv_menu_list.setOnItemClickListener(onItemClickListener);

    }


    /**
     * 回放页面 改变Tab页面是否可见
     */
    public void changeShowTabState() {

        isShowTab = !isShowTab;
        if (isShowTab) {
            mLayoutTabInfo.setVisibility(View.INVISIBLE);
            mImg_gis_show_tab.animate().rotation(45);
            mLayoutTabInfo.animate().alpha(1);
            mLayoutTabInfo.animate().setDuration(500);
            mLayoutTabInfo.setVisibility(View.VISIBLE);
        } else {
            mImg_gis_show_tab.animate().rotation(0);
            mLayoutTabInfo.animate().alpha(0);
            mLayoutTabInfo.animate().setDuration(500);
            mLayoutTabInfo.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_gis_show_tab://切换信息显示tab的按钮
                changeShowTabState();
                break;
            case R.id.ll_right_button://menu菜单按钮:
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopupWindow.showAsDropDown(iv_right_button);
                break;
            case R.id.ll_back://返回按钮
                exitDialog();
                break;
            case R.id.btn_conforms://路径规划按钮
                String collectModel = spinner.getSelectedItem().toString();//采集模式
                String collectRate = textView.getText().toString();//采集频率
                ll_gis.setVisibility(View.INVISIBLE);
                widthPixels = getResources().getDisplayMetrics().widthPixels;
                heightPixels = getResources().getDisplayMetrics().heightPixels;
                textWidth = (int) ll_yd.getX();
                textHeight = (int) ll_yd.getY();
                int mu = heightPixels - (textHeight - ll_yd.getHeight() * 2);
                int mm = DensityUtil.px2dip(GisActivity.this, mu);
                TranslateAnimation animation = new TranslateAnimation(0, 0, 0, mm);
                animation.setDuration(2000);//设置动画持续时间
                animation.setFillAfter(true);
                ll_yd.setAnimation(animation);
                animation.start();
                //采集频率写死
                Const.TestManage.COLLECTION_SPEED = 1000;
                //保存当前页面的数据
                TestBaseBean testBaseBean = new TestBaseBean();
                //        String fileAbsolutePathName = getFileDir( Const.SaveFile.ROOT_FILE_DIR + File.separator + mOneKeyTestBean.getWorkorderno()+ File.separator + mOneKeyTestBean.getResultPath())
                //                + File.separator  +mOneKeyTestBean.getSector() + "_" + TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss")+".txt";
                String fileName = mOneKeyTestBean.getSector() + "_" + TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
                String fileAbsolutePathName = FileUtils.getFileDir(Const.SaveFile.ROOT_FILE_DIR + File.separator//根路径
                        + mOneKeyTestBean.getWorkorderno() + File.separator//工单号
                        + getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH)//文件路径
                        + File.separator + fileName);//选择的扇区和时间戳
                testBaseBean.setItemName(fileName);
                String str = JSONObject.toJSONString(testBaseBean);
                FileUtils.saveFile(this, str.toString(), fileAbsolutePathName + File.separator + fileName + ".txt");
                File file = new File(fileAbsolutePathName);
                mOneKeyTestBean.setResultPath(file.getParentFile().toString());//把文件路径保存进去
                //开始测试
                startGisTest();
                break;
            case R.id.bt_stop:
                exitDialog();
                break;
        }
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出");
        builder.setMessage("确定要退出测试!!");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGisTestinfoFragment.closeActivity();
            }
        });
        builder.show();
    }


    @Override
    protected void onDestroy() {


        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;

        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];

        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void initSpinner(Spinner spinners, String[] strArra) {
        ArrayAdapter<String> arrA = new ArrayAdapter<String>(this, R.layout.spinner_style, strArra);
        spinners.setAdapter(arrA);
    }

    /**
     * 获取资源文件
     *
     * @param fileName
     * @return
     */
    private String getAssetsString(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    getAssets().open(fileName), "UTF-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d("ssx", "finalize: 我已经被GC回收" + getClass().getName());
        super.finalize();
    }

    @Override
    public void onTestResult(TestResult testResult) {

    }

    @Override
    public void onSignalLingResult(TestResult testResult) {

        dataGis(testResult);
        mBaiDuMapDraw.baiDuMapLocation();
    }


    //阻止back退出界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

