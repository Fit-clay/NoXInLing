package com.beidian.beidiannonxinling.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.DistrictPopuWindowAdapter;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.ChangeTestModelBean;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.bean.DistrictBean;
import com.beidian.beidiannonxinling.bean.LETBean;
import com.beidian.beidiannonxinling.bean.OneKeyTestBean;
import com.beidian.beidiannonxinling.bean.TestResult;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.fragment.GisTestinfoFragment;
import com.beidian.beidiannonxinling.ui.widget.ColorConfigDialog;
import com.beidian.beidiannonxinling.ui.widget.DistrictPopuWindown;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;
import com.beidian.beidiannonxinling.ui.widget.PointCanvasView;
import com.beidian.beidiannonxinling.util.ActionSheetDialog;
import com.beidian.beidiannonxinling.util.ColorConfigUtils;
import com.beidian.beidiannonxinling.util.DensityUtil;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ImageUtils;
import com.beidian.beidiannonxinling.util.JSONUtil;
import com.beidian.beidiannonxinling.util.ShiFenTestConfigDialog;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.ViewUtils;
import com.google.gson.Gson;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eric on 2017/9/23.
 */

public class LTEShiFenTestActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout mLl_color_table;
    private TextView     tvTitle,tvContent,tvPlanWay,tv_is_mobile;
    private ImageView    ivMenu,ivChangePhoto,ivBackground;
    private LinearLayout lyBack,lyMenu;
    private SeekBar seekBar;
    private TextView tv_seek_num;
    private DistrictPopuWindown popuWindown;
    private Spinner spChangeMode;
    private PointCanvasView pointCanvasView;

    private boolean isShowTab   = true;
    private String imgePath;
    private String workOrder;
    private String baseInfoAddress;
    private String fileInfoAddress;
    private LoadingDialog loadingDialog;
    private BaseInfoTestBean baseInfoTestBean;
    private BaseInfoTestBean.SiteInfoBean siteInfoBean;
    private GisTestinfoFragment  mGisTestinfoFragment;
    private ImageView      mImg_gis_show_tab;
    private LinearLayout   mLayoutTabInfo;
    private LinearLayout   mLayoutBottom;
    private Button conform;

    private int              mPadding;
    public static final int TO_PHOTO=1001;
    public static final int TAKE_CAMERA=1002;
    private final int PERMISSION_CAMERA = 200;
    private  List<String> tremList;
    private ShiFenTestConfigDialog dialog;
    private ChangeTestModelBean changeTestModelBean;
    String fileDir;
    private String backgroundBitmapPath;
    String  times;
    private int            mColorItemType= Const.ColorManager.COVERAGE_RSRP;
    private List<ColorConfigItem> mColorItemBean = ColorConfigUtils.getColorItemBean(Const.ColorManager.COVERAGE_RSRP);

    private int indexType;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingDialog.dismiss();
                mGisTestinfoFragment.closeActivity();
        }

    };
    Bitmap bit;
    boolean isSave;
    String jsonFilePath;
    private SensorManager sensorManager = null;
    Sensor mPressure;
    SensorEventListener pressureListener;

    private   int mobileNum;
    private boolean mobileType;

    private boolean beginTestType;  //是否开始测试
    private String defultBackgroundImagePath;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_lte_shifen_test);
        mLl_color_table=findView(R.id.ll_color_table);
        tvTitle=findView(R.id.tv_title);
        tv_is_mobile=findView(R.id.tv_is_mobile);
        ivMenu=findView(R.id.iv_right_button);
        lyBack=findView(R.id.ll_back);
        lyMenu=findView(R.id.ll_right_button);
        ivChangePhoto=findView(R.id.iv_change_photo);
        ivBackground=findView(R.id.iv_background);
        tvContent=findView(R.id.tv_content);
        seekBar=findView(R.id.seekbar);
        tv_seek_num=findView(R.id.tx_seekbar);
        spChangeMode=findView(R.id.spinner2);
        pointCanvasView=findView(R.id.pv_lte_image);
        mImg_gis_show_tab=findView(R.id.img_gis_show_tab);
        conform=findView(R.id.btn_conforms);
        mLayoutBottom=findView(R.id.ll_bottom);
//        tvPlanWay=findView(R.id.tv_plan_way);
        mLayoutTabInfo = (LinearLayout) findViewById(R.id.layout_tab_info);//测试信息显示的tab

        mPadding = DensityUtil.dip2px(this, 5);

        mGisTestinfoFragment = new GisTestinfoFragment();

        mGisTestinfoFragment.initeFragment(this);

        pointCanvasView.setEnabled(false);
        pointCanvasView.setClickable(false);
        loadingDialog=DialogUtil.showLodingDialog(mContext,"加载中...");
        loadingDialog.dismiss();

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        pressureListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub
                mobileType=mobileNum==(int) event.values[0];
                mobileNum=(int) event.values[0];

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

    }

    @Override
    protected void initData() {
        tvTitle.setText(getIntent().getStringExtra("title"));
        ivMenu.setImageResource(R.mipmap.icon_menu);
        workOrder=getIntent().getStringExtra(Const.IntentTransfer.WORKDERNO);
        fileDir=getIntent().getStringExtra(Const.IntentTransfer.FILE_PATH);
        indexType = Integer.valueOf(getIntent().getExtras().get(Const.IntentTransfer.TYPE).toString());

        times= TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");

        baseInfoAddress = getIntent().getStringExtra(Const.IntentTransfer.DEFULT_INFO_PATH);
        fileInfoAddress = getIntent().getStringExtra(Const.IntentTransfer.BASE_ABSOLUTEPATH);

        if(indexType==Const.RESET){
            LETBean bean= JSONUtil.parseObject(FileUtils.readFile(fileInfoAddress), LETBean.class);
            if(bean!=null){
                changeTestModelBean=bean.getChangeTestModelBean();
                pointCanvasView.setBackgroundBitmap(BitmapFactory.decodeFile(fileInfoAddress.substring(0,fileInfoAddress.lastIndexOf("/"))+"/背景图片.jpeg"));
                backgroundBitmapPath=fileInfoAddress.substring(0,fileInfoAddress.lastIndexOf("/"))+"/背景图片.jpeg";
                defultBackgroundImagePath=backgroundBitmapPath;
                tvContent.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(baseInfoAddress)) {
            if (FileUtils.fileIsExists(baseInfoAddress)) {
                String str = FileUtils.readSDFile(baseInfoAddress);
                baseInfoTestBean = new Gson().fromJson(str, BaseInfoTestBean.class);
                siteInfoBean=baseInfoTestBean.getSiteInfo();
                times= TimeUtils.getyyyyMMddHHmmss("yyyyMMddHHmmss");
            }
        }
        dialog= DialogUtil.showConfigDialog(mContext,baseInfoTestBean,changeTestModelBean);
        dialog.setTestModel(new ShiFenTestConfigDialog.ChangeTestModel() {
            @Override
            public void getChangeModel(ChangeTestModelBean bean) {
                changeTestModelBean=bean;
                if(indexType==Const.RESET){
                    fileDir=fileInfoAddress.substring(0,fileInfoAddress.lastIndexOf("/"));
                }else {
                    fileDir = FileUtils.getFileDir(Const.SaveFile.getDir(baseInfoTestBean.getSiteInfo().getWorkorderno(),fileDir, siteInfoBean.getCellinfoList().get(Integer.valueOf(changeTestModelBean.getDistractChange())).getCellname()+"_"+changeTestModelBean.getUnit()+"_"+changeTestModelBean.getFloorInfo()));
                }
            }
        });
        dialog.show();

        List<DistrictBean> distrisctList=new ArrayList<>();
        distrisctList.add(new DistrictBean("图例选择",false));
        distrisctList.add(new DistrictBean("测试配置",false));
        if (popuWindown == null) {
            popuWindown = new DistrictPopuWindown(this, distrisctList, new DistrictPopuWindowAdapter.DeleteListenes() {
                @Override
                public void onDeletePosition(int position) {
                }
            }, new DistrictPopuWindown.ItemClickListener() {
                @Override
                public void onItenClick(int position) {
                    switch (position){
                        case 0:
                            final ColorConfigDialog colorConfigDialog = new ColorConfigDialog(LTEShiFenTestActivity.this);
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
                                        upDataPointCanvas();
                                    }
                                    dialog.dismiss();
                                }
                            });
                            colorConfigDialog.show();
                            popuWindown.dismiss();

                            break;
                        case 1:
                            dialog.show();
                            popuWindown.dismiss();
                            break;
                    }

                }
            });
        }
        ViewUtils.initSpinner(mContext,spChangeMode, getResources().getStringArray(R.array.ways_test));
        initColorConfig();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_tab_info, mGisTestinfoFragment, "mGisTestinfoFragment");
        fragmentTransaction.commit();


        mGisTestinfoFragment.setResultLsitener(new GisTestinfoFragment.ResultLsitener() {
            @Override
            public void onTestResult(TestResult testResult) {
            }
            @Override
            public void onSignalLingResult(TestResult testResult) {
//                PointBean pointBean=new PointBean();
//                pointBean.setTestResult(testResult);
                testResult.setColor(getColorValue(testResult));
                testResult.setMobile(!mobileType);

                if(!mobileType){
                tv_is_mobile.setText("移动检测：移动中");
                }else {
                    tv_is_mobile.setText("移动检测：已停止移动");
                }
                pointCanvasView.setPlist(testResult);
            }
        });
    }

    @Override
    protected void initListener() {
        lyBack.setOnClickListener(this);
        lyMenu.setOnClickListener(this);
        ivChangePhoto.setOnClickListener(this);
//        tvContent.setOnClickListener(this);
        conform.setOnClickListener(this);
        mImg_gis_show_tab.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DecimalFormat df=new DecimalFormat("0");
                tv_seek_num.setText(df.format(progress*0.2*1000)+"ms");
            }
            @Override  public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override  public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        spChangeMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    conform.setText("开始打点");

                }else {
                    conform.setText("规划路径");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_right_button:
                if(!beginTestType){
                    popuWindown.showAsDropDown(ivMenu, 0, 0);
                }
                break;
            case R.id.tv_content:
                if(!beginTestType){
                    showChangeDialog();
                }
                break;
            case R.id.iv_change_photo:
                if(!beginTestType) {
                    showChangeDialog();
                }
                break;
            case R.id.img_gis_show_tab://切换信息显示tab的按钮
                changeShowTabState();
                break;
            case  R.id.btn_conforms:
                if(backgroundBitmapPath!=null){
                    if(changeTestModelBean==null||!(changeTestModelBean.getTestChange().size()>0)) {
                        DialogUtil.showConfirmDialog(mContext,"请选择测试模板");
                        break;
                    }
                        if(conform.getText().equals("开始打点")){
                        if(spChangeMode.getSelectedItem().toString().equals("手动打点")){
                            pointCanvasView.setAuto(true);
                            pointCanvasView.setEnabled(true);
                            pointCanvasView.setClickable(true);
                        }else {
                            conform.setText("下一步");
                            pointCanvasView.setAuto(false);
                        }
                        startTest();
                            sensorManager.registerListener(pressureListener, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
                            conform.setText("完成");
                            beginTestType=true;
                            seekBar.setEnabled(false);
                            seekBar.setSelected(false);
                            spChangeMode.setEnabled(false);
                            spChangeMode.setSelected(false);
                    }else if(conform.getText().toString().equals("规划路径")) {
                        pointCanvasView.setEnabled(true);
                        pointCanvasView.setClickable(true);
                        conform.setText("开始打点");
                    }else if (conform.getText().toString().equals("下一步")){
                        pointCanvasView.nextLine(new PointCanvasView.isFulfil() {
                            @Override
                            public void fulfil() {
                                conform.setText("完成");
                            }
                        });
                        pointCanvasView.setEnabled(false);
                        pointCanvasView.setClickable(false);
                    }else{
                            saveData();
                    }
                }else {
                    DialogUtil.showConfirmDialog(mContext,"请先选择打点图片");
                }
                break;

        }
    }
    private void saveData(){
        bit=pointCanvasView.getBackgroundBitmap();
        loadingDialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                ImageUtils.saveBitmapToSDCard(mContext,bit,fileDir+"/打点图片.jpeg");
                if(!backgroundBitmapPath.equals(fileDir+"/背景图片.jpeg")){
                    FileUtils.copyFile(backgroundBitmapPath,fileDir+"/背景图片.jpeg");
                }
                LETBean bean=new LETBean();
                bean.setTestResults(pointCanvasView.getAllList());
                bean.setChangeTestModelBean(changeTestModelBean);
                bean.setItemName(baseInfoTestBean.getSiteInfo().getSitename());
                JSON jsonObject = (JSON) JSON.toJSON(bean);

                if(indexType==Const.RESET){
                    jsonFilePath= fileInfoAddress;
                }else {
                    jsonFilePath = Const.SaveFile.getJsonAbsoluteFilePaths(fileDir, "" ,siteInfoBean.getCellinfoList().get(Integer.valueOf(changeTestModelBean.getDistractChange())).getCellname()+"_"+changeTestModelBean.getUnit()+"_"+changeTestModelBean.getFloorInfo());
                }
                FileUtils.saveFile(mContext, jsonObject.toString(), jsonFilePath);
                isSave=true;
                Message msg=handler.obtainMessage();
                msg.arg1=1;
                handler.sendMessage(msg);
            }
        }.start();
    }


    private  void showChangeDialog(){
        tremList=new ArrayList<>();
        tremList.add("从相册中选取");
        tremList.add("相机");
        ActionSheetDialog tremDialog = new ActionSheetDialog(mContext)
                    .builder()
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false);
            for(String pur:tremList){
                tremDialog.addSheetItem(pur, ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(int which) {
                        if(which==1){
                            Intent intent = new Intent(Intent.ACTION_PICK,
                             MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, TO_PHOTO);
                        }else  if(which==2){
                            imgePath = FileUtils.getFileDir(Const.SaveFile.getDir(workOrder,Const.SaveFile.LTE_SHIFEN_TEST, ""));
                            takePhoto(imgePath);
                        }
                    }
                });
            }
        tremDialog.show();

    }


    private void startTest(){
            OneKeyTestBean bean1=new OneKeyTestBean(changeTestModelBean.getDistractChange(),workOrder,changeTestModelBean.getTestChange(),"",fileDir,baseInfoTestBean,Integer.valueOf(changeTestModelBean.getDistractChange()),changeTestModelBean.getRemark());
            String str=tv_seek_num.getText().subSequence(0,tv_seek_num.getText().toString().length()-2).toString();
               Const.TestManage.COLLECTION_SPEED=Integer.valueOf(str);
            mGisTestinfoFragment.testStart(bean1);

    }

    /**
     * Android7.0适配
     */
    private File newFile;//拍照的文件
    private Uri contentUri;//拍照生成的图片Uri

    /**
     * 拍照：(照片水印：基站小区名、任务工单、经纬度、日期)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhoto(String filePath) {
        //判断是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //检测是否有相机和读写文件权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
            } else {
                startCameraAndroid7(filePath);
            }

        } else {
            ToastUtils.makeText(this, "SD卡不可用");
        }
    }

    private void startCameraAndroid7( String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.makeText(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
            return;
        }
//        newFile = new File(filePath, ImageUtils.getFileNameTimeStamp(".jpg"));
        newFile = new File(filePath, siteInfoBean.getItemname()+"_打点拍摄图片_"+siteInfoBean.getLat()+"_"+siteInfoBean.getLng()+".jpg");

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
        startActivityForResult(intent, TAKE_CAMERA);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径

        if (resultCode == Activity.RESULT_OK && data != null) {

            if(requestCode==TO_PHOTO){
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
                pointCanvasView.setBackgroundBitmap(BitmapFactory.decodeFile(imagePath));
                backgroundBitmapPath=imagePath;
               tvContent.setVisibility(View.GONE);
            }
        }
         if(requestCode==TAKE_CAMERA){
             pointCanvasView.setBackgroundBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
             backgroundBitmapPath=newFile.getAbsolutePath();

             tvContent.setVisibility(View.GONE);
        }
        loadingDialog.dismiss();

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

    /**
     * 更新打点
     */
    private void upDataPointCanvas() {
     List<List<TestResult>> pointBeanList =pointCanvasView.getArrayLineList();
        for(int i=0;i<pointBeanList.size();i++){
            for(TestResult bean:pointBeanList.get(i)){
                bean.setColor(getColorValue(bean));
            }
        }
        pointCanvasView.invalidate();

    }
    /**
     * 获取当前需要打点的颜色
     * @param testResult
     * @return
     */
    private int getColorValue(TestResult testResult) {

        Integer value =null;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isSave){
            if(fileDir!=null)
            new File(fileDir).delete();
        }
        if(pressureListener!=null){
            sensorManager.unregisterListener(pressureListener);
        }
    }
}
