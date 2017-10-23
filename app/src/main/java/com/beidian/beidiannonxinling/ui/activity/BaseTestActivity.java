package com.beidian.beidiannonxinling.ui.activity;

import android.Manifest;
import android.app.Dialog;
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
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.adapter.HongZhanTestExpandableListViewAdapter;
import com.beidian.beidiannonxinling.adapter.HongZhanTestListViewAdapter;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.BaseTestBean;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.Workorder;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.net.ResultCallback;
import com.beidian.beidiannonxinling.ui.widget.CustomExpandableListView;
import com.beidian.beidiannonxinling.ui.widget.CustomListView;
import com.beidian.beidiannonxinling.ui.widget.customview.HandWriteSign;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ImageUtils;
import com.beidian.beidiannonxinling.util.JSONUtil;
import com.beidian.beidiannonxinling.util.LogUtils;
import com.beidian.beidiannonxinling.util.TimeUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.ZipUtils;
import com.beidian.beidiannonxinling.util.coreprogress.FileUpDown;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.baidu.location.h.h.J;

/**
 *
 */
public class BaseTestActivity extends BaseActivity implements View.OnClickListener {
    private final int PERMISSION_WRITE_EXTERNAL_STORAGE = 100;
    private final int PERMISSION_CAMERA = 200;
    private final int TAKE_CAMERA = 1000;
    private LinearLayout ll_back;
    private TextView tv_title;
    private TextView tv_siteName;//基站名称
    protected TextView tv_cellCount;//扇区数
    protected CustomExpandableListView expandableListView;
    private CustomListView listView;//测试结果
    private EditText et_leaveProblem;//遗留问题
    private RelativeLayout rl_signArea;//签名区域
    private ImageView iv_signImage;//签名区域
    private TextView tv_signLocation;//签名经纬度
    private TextView tv_signTime;//签名时间
    private ImageView iv_takePhoto;//拍照按钮
    private ImageView iv_image;//拍照结果
    private Button bt_commit;//任务提交
    private AppCompatDialog appCompatDialog;
    private String waterMarkImagePath;//拍照水印照片路径
    private String signImagePath;//签名文件图片路径
    private File newFile;//拍照的文件
    private Uri contentUri;//拍照生成的图片Uri
    private String[] marks = new String[]{"基站小区名", "任务工单", "经度", "纬度", "日期"};
    private String jsonDir;
    private String jsonFileAbsolutePathName;
    private String workOrder;//工单号
    private String stationName;//基站名称
    protected HongZhanTestExpandableListViewAdapter mExpandableListViewAdapter;//多级列表数据适配器
    private  Dialog upDialog;
    private  ProgressBar progBar;
    private String orderType;
    private String saveFilePath;
    private   List<BaseTestBean.ParentListBean.ChildListBean> listViewDatas; // 测试结果集
    @Override
    protected void initView() {
        setContentView(R.layout.activity_hong_zhan_test);
        ll_back = findView(R.id.ll_back);
        tv_title = findView(R.id.tv_title);
        tv_siteName = findView(R.id.tv_siteName);
        tv_cellCount = findView(R.id.tv_cellCount);
        expandableListView = findView(R.id.expandableListView);
        listView = findView(R.id.listView);
        et_leaveProblem = findView(R.id.et_leaveProblem);
        rl_signArea = findView(R.id.rl_signArea);
        iv_signImage = findView(R.id.iv_signImage);
        tv_signLocation = findView(R.id.tv_signLocation);
        tv_signTime = findView(R.id.tv_signTime);
        iv_takePhoto = findView(R.id.iv_takePhoto);
        iv_image = findView(R.id.iv_image);
        bt_commit = findView(R.id.bt_commit);
        tv_title.setText(getIntent().getStringExtra("title"));
        expandableListView.setFocusable(false);
        expandableListView.setGroupIndicator(null);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Workorder task = (Workorder) intent.getSerializableExtra("task");
            saveFilePath =intent.getStringExtra(Const.IntentTransfer.FILE_PATH);
            if (task != null) {
                //工单号
                workOrder = String.valueOf(task.getWorkorderno());
                //基站名称
                stationName = task.getSitename();
                orderType=task.getWorkordertype();
                if (!TextUtils.isEmpty(workOrder) && !TextUtils.isEmpty(stationName)) {
                    handleWorkOrder(workOrder);
                } else {
                    ToastUtils.makeText(mContext, "无法获取工单号、基站名称");
                }
            } else {
                workOrder = (String) intent.getSerializableExtra("workorder");
                if (workOrder != null) {
                    handleWorkOrder(workOrder);
                } else {
                    ToastUtils.makeText(mContext, "无法获取工单号、基站名称");
                }

            }
        }

    }
    /**
     * 处理工单：HCC_test_0001
     * 1. 根据工单创建文件夹
     * 2. 获取网络数据
     * 3. 保存数据
     * @param workOrder   工单号
     */
    private void handleWorkOrder(final String workOrder) {
        //创建json文件夹
        jsonDir = FileUtils.getFileDir(Const.SaveFile.getDir(workOrder,saveFilePath, ""));
        if (TextUtils.isEmpty(jsonDir) ) {
            ToastUtils.makeText(mContext, "文件夹创建失败，请检查SD卡");
            return;
        }
        signImagePath = jsonDir + File.separator + "手写签名.jpg";
        waterMarkImagePath = jsonDir + File.separator + tv_title.getText()+"测试结果.jpg";
        jsonFileAbsolutePathName = Const.SaveFile.getJsonAbsoluteFilePath(jsonDir, workOrder);

        HttpParams params = new HttpParams();
        params.put("workorderNo", workOrder);
//        params.put("workorderNo", "HCC_test_0001");
        //获取服务器数据
        OkGo.post(Const.Url.URL_GET_SITE_INFO).tag(mContext).params(params).execute(new ResultCallback(mContext, true) {
            @Override
            public void onFailure(Call call, Response response, Exception e) {

            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.i("json数据 fileAbsolutePathName：" + jsonFileAbsolutePathName);
                FileUtils.saveFile(mContext, s, jsonFileAbsolutePathName);
                handleNetResult(s, jsonFileAbsolutePathName, workOrder);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initExpandableListView(jsonFileAbsolutePathName, workOrder);

    }

    /**
     * 处理网络数据
     *
     * @param s                        服务器数据
     * @param jsonFileAbsolutePathName 保存的json文件路径
     * @param workOrder                工单号
     * @param stationName              基站名
     */
    protected void handleNetResult(String s, String jsonFileAbsolutePathName, String workOrder) {
        if (TextUtils.isEmpty(s))
            return;
        Gson gson = new Gson();
        BaseInfoTestBean bean = gson.fromJson(s, BaseInfoTestBean.class);
        BaseInfoTestBean.SiteInfoBean siteInfo = bean.getSiteInfo();

        tv_siteName.setText(siteInfo.getAddress());

        List<CellinfoListBean> cellinfoList = siteInfo.getCellinfoList();

        tv_cellCount.setText("" + (cellinfoList == null ? "" : (cellinfoList.size())));
        tv_signLocation.setText(siteInfo.getLng() + "\t\t" + siteInfo.getLat());
        String currentTime = TimeUtils.getyyyyMMddHHmmss();
        tv_signTime.setText(currentTime);
        marks[0] = siteInfo.getAddress();//基站小区名
        marks[1] = "任务工单:" + siteInfo.getWorkorderno();//任务工单
        marks[2] = "经度:" + siteInfo.getLng();//经度
        marks[3] = "纬度:" + siteInfo.getLat();//纬度
        marks[4] = currentTime;//日期


        //初始化测试列表
        initExpandableListView(jsonFileAbsolutePathName, workOrder);
    }



    protected void initExpandableListView(String jsonFileAbsolutePathName, String workOrder) {

        BaseTestBean.ParentListBean parentBean1 = new BaseTestBean.ParentListBean();
        parentBean1.setGroupName("基础信息采集");

        List<BaseTestBean.ParentListBean.ChildListBean> childList1 = new ArrayList<>();
        List<BaseTestBean.ParentListBean.ChildListBean> childList2 = new ArrayList<>();

        setChaildListBean(Const.SaveFile.BASE_INFO_COLLECTION, childList1);

        parentBean1.setChildList(childList1);

        parentBean1.setState(isTest(childList1));

        BaseTestBean.ParentListBean parentBean2 = new BaseTestBean.ParentListBean();
        setChaildListBean(Const.SaveFile.BASE_CQT_TEST_COLLECTION, childList2);
        parentBean2.setGroupName("CQT定点测试");
        parentBean2.setChildList(childList2);
        parentBean2.setState(isTest(childList2));

        //组数据集合
        List<BaseTestBean.ParentListBean> parentList = new ArrayList<>();
        parentList.add(parentBean1);
        parentList.add(parentBean2);


        mExpandableListViewAdapter = new HongZhanTestExpandableListViewAdapter(this, parentList, jsonFileAbsolutePathName, workOrder,orderType);
        expandableListView.setAdapter(mExpandableListViewAdapter);

        //设置测试结果
        initListView(parentList);
    }

    protected String isTest( List<BaseTestBean.ParentListBean.ChildListBean> childList1 ){
       if(childList1.size()>0){
           return "已测试";
    }
        return "未测试";
    }

    protected void setChaildListBean(String filePath, List<BaseTestBean.ParentListBean.ChildListBean> childList) {
        String baseFileDir = FileUtils.getFileDir(Const.SaveFile.getDir(workOrder, filePath, ""));
        //遍历上述文件夹，获取所有的 .txt文件，然后显示出来
        List<File> baseFileList = FileUtils.getFiles(baseFileDir,true);
        if (baseFileList != null && baseFileList.size() != 0) {
            for (File file : baseFileList) {
                String jsonFile=file.getAbsoluteFile()+"/"+file.getName()+".txt";
                if(FileUtils.fileIsExists(jsonFile)){
                    String json = FileUtils.readSDFile(jsonFile);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String itemName, itemState;

                        if (jsonObject.has("siteInfo")) {
                            JSONObject siteInfo = jsonObject.getJSONObject("siteInfo");
                            itemName = siteInfo.getString("itemname");
                            itemState = siteInfo.getString("itemstate");
                        } else {
                            itemName = jsonObject.getString("itemName");
                            itemState = jsonObject.getString("itemState");
                        }
                        if (!TextUtils.isEmpty(itemName) && !TextUtils.isEmpty(itemState)) {
                            //子数据1、子数据2、子数据3
                            BaseTestBean.ParentListBean.ChildListBean childBean = new BaseTestBean.ParentListBean.ChildListBean();
                            childBean.setItemName(itemName);
                            childBean.setItemState(itemState);
                            childBean.setFileAbsolutePath(file.getAbsolutePath()+"/"+file.getName()+".txt");
                            childList.add(childBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    protected void initListView(List<BaseTestBean.ParentListBean> parentList) {
        if (parentList == null && parentList.size() == 0) return;
        listViewDatas = new ArrayList<>();
        listViewDatas.clear();
        if(parentList.size()>1){
            List<BaseTestBean.ParentListBean.ChildListBean> childList = parentList.get(1).getChildList();
            BaseTestBean.ParentListBean.ChildListBean bean;
            for (int j = 0; j < childList.size(); j++) {
                bean=new BaseTestBean.ParentListBean.ChildListBean();
                bean.setItemState(childList.get(j).getItemState());
                bean.setItemName(childList.get(j).getItemName());
                bean.setFileAbsolutePath(childList.get(j).getFileAbsolutePath());
                listViewDatas.add(bean);
            }

            if(!parentList.isEmpty()&&!parentList.get(0).getChildList().isEmpty()&&parentList.get(0).getChildList().get(0).getItemState().equals("未通过"))
            {
                for(int i =0;i< listViewDatas.size();i++){
                    listViewDatas.get(i).setItemState("未通过");
                }
            }
            HongZhanTestListViewAdapter mAdapter = new HongZhanTestListViewAdapter(this, listViewDatas);
            listView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initListener() {
        ll_back.setOnClickListener(this);
        rl_signArea.setOnClickListener(this);
        iv_takePhoto.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
        iv_image.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;

            case R.id.rl_signArea:
                //手写签名
                //动态申请读写SD卡权限，适配Android6.0  7.0  8.0
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //校验权限
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
                    } else {
                        openSignDialog();
                    }
                } else {
                    ToastUtils.makeText(mContext, "SD卡不可用");
                }
                break;
            case R.id.iv_takePhoto:
                takePhoto();
                break;
            case R.id.bt_commit:
                //签名图片
                bt_commit.setEnabled(false);
                if (!TextUtils.isEmpty(waterMarkImagePath)) {
                    if(upDialog==null){
                        upDialog= DialogUtil.customProgress(mContext,"正在上传",new DialogUtil.OnProgressDialogCallBack(){
                            @Override
                            public void getProgressbar(ProgressBar progressBar) {
                                progBar=progressBar;
                            }
                        });
                    }else {
                        progBar.setProgress(0);
                        upDialog.show();
                    }
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            if (!TextUtils.isEmpty(signImagePath)) {
                                File file = ImageUtils.saveViewToSDCard(mContext,rl_signArea, signImagePath);
                                if (file != null && file.exists()) {
                                    ToastUtils.makeText(mContext, "签名图片保存成功");
                                } else {
                                    ToastUtils.makeText(mContext, "签名图片保存失败，请重新签名");
                                    return;
                                }
                            } else {
                                ToastUtils.makeText(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
                                return;
                            }
                            String problemPath = Const.SaveFile.getJsonAbsoluteFilePath(jsonDir, "remainingProblem");
                            com.alibaba.fastjson.JSONObject jb=new com.alibaba.fastjson.JSONObject();
                            jb.put("quest", et_leaveProblem.getText().toString());
                            jb.put("testResult", JSONUtil.toJSONArray(listViewDatas));
                            FileUtils.saveFile(mContext,jb.toString(), problemPath);

                            String workorfilePathName = Environment.getExternalStorageDirectory().toString() + File.separator + Const.SaveFile.ROOT_FILE_DIR + File.separator + workOrder + File.separator;
                            String zipWorkorfilePathName = Environment.getExternalStorageDirectory().toString() + File.separator + Const.SaveFile.ROOT_FILE_DIR + File.separator + workOrder + ".zip";
                            File[] files = new File[]{new File(workorfilePathName)};
                            File zip = new File(zipWorkorfilePathName);
                            try {
                                ZipUtils.ZipFiles(zip, "", files);
                            } catch (IOException e) {
                                e.printStackTrace();
                                bt_commit.setEnabled(true);

                            }
                            FileUpDown.upload(mContext, zipWorkorfilePathName, Const.Url.LOG_UP, new FileUpDown.FileUpChange() {
                                @Override
                                public void changePositong(float position) {
                                    progBar.setProgress((int) (100*position));
                                }
                                @Override
                                public void onSuccess() {
                                    upDialog.dismiss();
                                    DialogUtil.showConfirmDialog(mContext,"上传完成");
                                    bt_commit.setEnabled(true);
                                }
                                @Override
                                public void onFail() {
                                    bt_commit.setEnabled(true);
                                }
                            });
                        }
                    }.start();
                } else {
                    DialogUtil.showConfirmDialog(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
                    break;
                }
                break;

            case R.id.iv_image:
                if (!TextUtils.isEmpty(waterMarkImagePath)) {
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    intent.putExtra("waterMarkImage", waterMarkImagePath);
                    startActivity(intent);
                }
                break;
        }
    }

    private void openSignDialog() {
        appCompatDialog = new AppCompatDialog(this, R.style.customRoundRectDialogStyle);
        appCompatDialog.show();
        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_open_sign, null);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.9);
        int height = (int) (display.getHeight() * 0.7);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);
        appCompatDialog.setContentView(view, lp);

        final HandWriteSign handWriteSign = (HandWriteSign) view.findViewById(R.id.handWriteSign);

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(jsonDir)) {
                    ToastUtils.makeText(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
                    return;
                }

                final String signFilePath = jsonDir + File.separator + "手写签名(原本).jpg";
                handWriteSign.save(signFilePath, new HandWriteSign.SaveSignCallback() {
                    @Override
                    public void saveSuccess(String path) {
                        final Bitmap bitmap = BitmapFactory.decodeFile(path);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                appCompatDialog.dismiss();
                                iv_signImage.setImageBitmap(bitmap);
                            }
                        });

                        File file = new File(signFilePath);
                        if (file.exists())
                            file.delete();
                    }

                    @Override
                    public void saveFailed() {
                        appCompatDialog.dismiss();
                    }
                });


            }
        });
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWriteSign.clear();
            }
        });

    }

    /**
     * 拍照：(照片水印：基站小区名、任务工单、经纬度、日期)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhoto() {
        //判断是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //检测是否有相机和读写文件权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CAMERA);
            } else {
                startCameraAndroid7();
            }

        } else {
            ToastUtils.makeText(this, "SD卡不可用");
        }
    }

    /**
     * Android7.0适配
     */
    private void startCameraAndroid7() {
        if (TextUtils.isEmpty(jsonDir)) {
            ToastUtils.makeText(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
            return;
        }
//        newFile = new File(jsonDir, ImageUtils.getFileNameTimeStamp(".jpg"));
        newFile = new File(jsonDir,tv_title.getText()+"测试结果.jpg");

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
        processDataAndroid(requestCode, resultCode, data);//Android7.0适配
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCameraAndroid7();
                } else {
                    // 如果权限被拒绝，grantResults 为空
                    ToastUtils.makeText(mContext, "该功能需要相机和读写文件权限");
                }
                break;

            case PERMISSION_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openSignDialog();
                } else {
                    // 如果权限被拒绝，grantResults 为空
                    ToastUtils.makeText(mContext, "该功能需要读写文件权限");
                }
                break;
        }
    }


    private void processDataAndroid(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_CAMERA && resultCode == RESULT_OK) {
            ContentResolver contentProvider = getContentResolver();
            ParcelFileDescriptor mInputPFD;
            try {
                long startTime = System.currentTimeMillis();
                LogUtils.i("获取contentProvider图片耗时： " + (System.currentTimeMillis() - startTime) + "毫秒");

                //获取contentProvider图片
                mInputPFD = contentProvider.openFileDescriptor(contentUri, "r");
                FileDescriptor fileDescriptor = mInputPFD.getFileDescriptor();
                //获取到拍摄的图片，大小为4068 x 3456 约等于1600万像素. 并且获取contentProvider图片耗时平均需要1375毫秒
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

                //压缩图片，然后保存
                ImageUtils.compressBitmap(mContext, waterMarkImagePath, bitmap, 432, 576, marks, new ImageUtils.CompressCallback() {
                    @Override
                    public void onCompressSuccess(String imagePath, final Bitmap bitmap) {
                        LogUtils.i(TAG, "压缩图片onCompressSuccess: " + waterMarkImagePath);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_image.setImageBitmap(bitmap);
                            }
                        });
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (newFile != null && newFile.exists())
                    newFile.delete();
            }
//        } else if (requestCode == 0 && resultCode == 0) {
//            if (data != null) {
//                //  /storage/emulated/0/BeiDian/HCC_test_0001/BasicMessageCollectionActivity/json/基站1_20170904155630.txt
////                String jsonFilePath = data.getStringExtra("jsonFilePath");
////                readJsonDataFromFile(jsonFilePath);
//                initExpandableListView(jsonFileAbsolutePathName, workOrder, stationName);
//            }
        }
    }


}
