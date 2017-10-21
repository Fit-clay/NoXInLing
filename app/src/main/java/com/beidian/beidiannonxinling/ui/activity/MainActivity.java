package com.beidian.beidiannonxinling.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.db.ManagerDbs;
import com.beidian.beidiannonxinling.ui.fragment.MapFragment;
import com.beidian.beidiannonxinling.ui.fragment.TaskListFragment;
import com.beidian.beidiannonxinling.ui.widget.RoundImageView;
import com.beidian.beidiannonxinling.ui.widget.customview.MyDialog;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.PreferencesHelper;
import com.beidian.beidiannonxinling.util.SPUtils;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.beidian.beidiannonxinling.util.UpdateManager;
import com.beidian.beidiannonxinling.util.coreprogress.GetVersionUtils;
import com.beidian.beidiannonxinling.util.coreprogress.ProgressDialogs;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 侧滑导航
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    TaskListFragment taskListFragment;
    MapFragment fragment2;
    TextView menuTitle, user_name, user_phone, tv_companyName;
    RoundImageView person_img;
    LinearLayout ll_userInfo, ll_company, LL_testModeConfigure, ll_pictureManage, ll_siteDatabaseManage,
            ll_offLineMap, ll_checkVersion, ll_about, ll_help, ll_feedback;
    Button btn_exit;
    private LinearLayout ll_back;
    private ImageView iv_left,im_delete;
    private TextView tv_title, tv_meun, bt_serch;//标题,侧边栏
    private RadioGroup radioGroup;
    private EditText ed_text;
    private SlidingMenu slidingMenu;
    private TextView mTextView4;
    private TextView mTextView5;
    private String account,telphone,company;
    private int flag=0; //debug计次
    String sqlitePath;
    String sqliteName="sqlite.txt";
    ProgressDialogs mDialog;
    boolean isSuccess;//文件写进数据库

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        initSlidingMenu();
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        switchFragmentByAddHideShow(0);//默认显示列表模式Fragment
        tv_meun = (TextView) findViewById(R.id.tv_right_text);
        ed_text = (EditText) findViewById(R.id.ed_text);
        bt_serch = (TextView) findViewById(R.id.bt_serch);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        im_delete= (ImageView) findViewById(R.id.iv_delete);
        tv_title.setText("任务列表");
        tv_meun.setText("排序");
        iv_left.setImageResource(R.drawable.icon_setting);

        mTextView4 = (TextView) findViewById(R.id.textView4);//方位角
        mTextView5 = (TextView) findViewById(R.id.textView5);//下倾角
        mTextView4.setOnClickListener(this);
        mTextView5.setOnClickListener(this);
    }

    /**
     * 设置侧滑菜单
     */
    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        // configure the SlidingMenu
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.4f);
        slidingMenu.setOffsetFadeDegree(0.4f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        View menuView = View.inflate(this, R.layout.sliding_menu, null);
        slidingMenu.setMenu(menuView);
        initeMenuView(menuView);
    }

    //侧滑菜单初始化
    public void initeMenuView(View view) {
        menuTitle = (TextView) view.findViewById(R.id.tv_menutitle);
        person_img = (RoundImageView) view.findViewById(R.id.person_img);
        user_name = (TextView) view.findViewById(R.id.tv_username);
        user_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_companyName = (TextView) view.findViewById(R.id.tv_companyName);

        ll_userInfo=(LinearLayout)view.findViewById(R.id.ll_userInfo);
        ll_company = (LinearLayout) view.findViewById(R.id.ll_company);
        LL_testModeConfigure = (LinearLayout) view.findViewById(R.id.LL_testModeConfigure);
        ll_pictureManage = (LinearLayout) view.findViewById(R.id.ll_pictureManage);
        ll_siteDatabaseManage = (LinearLayout) view.findViewById(R.id.ll_siteDatabaseManage);
        ll_offLineMap = (LinearLayout) view.findViewById(R.id.ll_offLineMap);
        ll_checkVersion = (LinearLayout) view.findViewById(R.id.ll_checkVersion);
        ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
        ll_help = (LinearLayout) view.findViewById(R.id.ll_help);
        ll_feedback = (LinearLayout) view.findViewById(R.id.ll_feedback);


        //功能暂时没有设置不可见
//        LL_testModeConfigure.setVisibility(View.GONE);
//        ll_pictureManage.setVisibility(View.GONE);
//        ll_siteDatabaseManage.setVisibility(View.GONE);
//        ll_offLineMap.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        if (!SPUtils.getColorConfigInitializeInfo(getApplicationContext())) {
            initDefaultColorConfigItem();
            initDefaultModelCofingItem();
            SPUtils.setColorConfigInitializeInfo(getApplicationContext(), true);
        }
        UserInfoBean bean= BaseApplication.getUserInfo();
        account =  bean.getUsername();
        telphone =bean.getTelphone();
        company =bean.getOffice();
        user_name.setText(account);
        user_phone.setText(telphone);
        tv_companyName.setText(company);
        setIamge(Const.Url.SERVER + "/" + bean.getAvatar());
        sqlitePath= FileUtils.getFileDir(Const.SaveFile.getPage("MainActivity"));

    }

    private void setIamge(String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.personss)
                .error(R.drawable.personss)
                .priority(Priority.HIGH);

        Glide.with(this).load(url).apply(options).into(person_img);

    }

    @Override
    protected void initListener() {
        ll_userInfo.setOnClickListener(this);
        ll_company.setOnClickListener(this);
        LL_testModeConfigure.setOnClickListener(this);
        ll_pictureManage.setOnClickListener(this);
        ll_siteDatabaseManage.setOnClickListener(this);
        ll_offLineMap.setOnClickListener(this);
        ll_checkVersion.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_text.getText().clear();
            }
        });
        /**
         *查询监听
         */
        ed_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length()>0){
                    im_delete.setVisibility(View.VISIBLE);
                }else  {
                    im_delete.setVisibility(View.GONE);
                }
                if(s.length()==0){
                    taskListFragment.serchTop();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    im_delete.setVisibility(View.VISIBLE);
                }else  {
                    im_delete.setVisibility(View.GONE);
                }
                if(s.length()==0){
                    taskListFragment.serchTop();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    im_delete.setVisibility(View.VISIBLE);
                }else  {
                    im_delete.setVisibility(View.GONE);
                }
                if(s.length()==0){
                    taskListFragment.serchTop();
                }
            }
        });
        bt_serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                taskListFragment.serch(ed_text.getText().toString());
                ToastUtils.makeText(MainActivity.this, "搜索");
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_list://列表模式
                        switchFragmentByAddHideShow(0);
                        break;
                    case R.id.radioButton_map://地图模式
                        switchFragmentByAddHideShow(1);
                        break;

                }
            }
        });


        /**
         * 排序监听
         */
        tv_meun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建弹出式菜单对象（最低版本11）
                PopupMenu popup = new PopupMenu(MainActivity.this, view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.main, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {

                            case R.id.time:
                                Toast.makeText(MainActivity.this, "按时间排序", Toast.LENGTH_SHORT).show();
                                taskListFragment.groupBy(1, 1);
                                break;
                            case R.id.name:
                                Toast.makeText(MainActivity.this, "按距离排序", Toast.LENGTH_SHORT).show();
                                taskListFragment.groupBy(2, 1);
                                break;
                            case R.id.state:
                                Toast.makeText(MainActivity.this, "按状态排序", Toast.LENGTH_SHORT).show();
                                taskListFragment.groupBy(3, 1);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }

                });
                //显示(这一行代码不要忘记了)
                popup.show();
            }
        });
        /**
         *
         */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_list://列表模式
                        switchFragmentByAddHideShow(0);
                        break;
                    case R.id.radioButton_map://地图模式
                        switchFragmentByAddHideShow(1);
                        break;

                }
            }
        });
        tv_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                flag++;
                if (flag == 3) {
                    startActivity(new Intent(mContext, DebugActivity.class));
                    finish();
                }
                return false;
            }
        });
        UpdateManager.init(this);//初始化更新监听
    }


    /**
     * add-hide-show方式切换Fragment内容区域
     *
     * @param position
     */
    public void switchFragmentByAddHideShow(int position) {
        // 通过事务来操作fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0://
                hideFragment(1, transaction);
                if (taskListFragment == null) {
                    taskListFragment = new TaskListFragment();
                    // 新建的framgent还要添加到内容区域中
                    transaction.add(R.id.contentPanel, taskListFragment, "Fragment1");
                } else {
                    transaction.show(taskListFragment);
                }

                break;
            case 1://
                hideFragment(0, transaction);
                if (fragment2 == null) {
                    fragment2 = new MapFragment();
                    // 新建的framgent还要添加到内容区域中
                    transaction.add(R.id.contentPanel, fragment2, "Fragment2");
                } else {
                    transaction.show(fragment2);
                }
                break;
        }

        // 提交事务
        transaction.commit();
    }


    /**
     * 隐藏所有的Fragment
     *
     * @param position
     * @param transaction
     */
    private void hideFragment(int position, FragmentTransaction transaction) {
        switch (position) {
            case 0:
                if (taskListFragment != null) {
                    transaction.hide(taskListFragment);
                }
                break;
            case 1:
                if (fragment2 != null) {
                    transaction.hide(fragment2);
                }
                break;

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView4:
                Intent intent1 = new Intent(MainActivity.this, CameraActivity.class);
                intent1.putExtra(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS, Const.IntentTransfer.MAIN_ACTIVITY_TOOLS);
                startActivity(intent1);
                break;
            case R.id.textView5:
                Intent intent2 = new Intent(MainActivity.this, MechanicalActivity.class);
                intent2.putExtra(Const.IntentTransfer.MAIN_ACTIVITY_TOOLS, Const.IntentTransfer.MAIN_ACTIVITY_TOOLS);
                startActivity(intent2);
                break;
            case R.id.ll_userInfo://用户信息
                slidingMenu.toggle();
                ToastUtils.makeText(mContext, "用户信息");
                break;
            case R.id.ll_company://公司单位
                break;
            case R.id.LL_testModeConfigure://测试模板配置
                Intent testmodel = new Intent(MainActivity.this,TestModelActivity.class);
                startActivity(testmodel);
                break;
            case R.id.ll_pictureManage://图例管理
                Intent colormanager = new Intent(MainActivity.this,ColorManagerActivity.class);
                startActivity(colormanager);
                break;
            case R.id.ll_siteDatabaseManage://基站数据库管理
                //让ProgressDialog显示
                final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
                normalDialog.setTitle("下载");
                normalDialog.setMessage("您确定下载数据库吗？");
                normalDialog.setCancelable(false);
                normalDialog.setNegativeButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                dialog.dismiss();
                                mDialog = new ProgressDialogs(MainActivity.this);
                                mDialog.setMessage("正在下载");
                                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                mDialog.setCancelable(false);
                                mDialog.show();
                                OkGo.get("http://192.168.3.242:9009/app/downTxt?workorderNo=110120").tag(this).execute(new FileCallback(sqlitePath,sqliteName) {

                                    @Override
                                    public void onSuccess(File file, okhttp3.Call call, Response response) {
                                        mDialog.dismiss();
                                        final ProgressDialog proDialog=new ProgressDialog(MainActivity.this);
                                        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                                        proDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
                                        proDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                                        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                                        proDialog.setTitle("提示");
                                        proDialog.setMessage("读取文件中，请稍后！");
                                        proDialog.show();

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                isSuccess =ManagerDbs.readFileAndInsertData(sqlitePath+"/"+sqliteName,getDaoSession().getLineDataDao(),proDialog);
                                                if(isSuccess){
                                                    ToastUtils.makeText(MainActivity.this,"插入数据库成功");
                                                }
                                            }
                                        }).start();

                                    }

                                    @Override
                                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                                        mDialog.setMax(Integer.parseInt(totalSize+""));
                                        mDialog.setProgress(Integer.parseInt(currentSize+""));
                                        if(progress==1){
                                            mDialog.cancel();
                                        }
                                    }

                                    @Override
                                    public void onError(Call call, Response response, Exception e) {
                                        super.onError(call, response, e);
                                        mDialog.dismiss();
                                        ToastUtils.makeText(mContext, "服务器发生错误，请稍后再试！");
                                    }

                                });


                            }

                        });
                normalDialog.setPositiveButton("否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                dialog.dismiss();
                            }

                        });

                normalDialog.show();
                break;
            case R.id.ll_offLineMap://离线地图
                break;
            case R.id.ll_checkVersion://检查版本
                //                /***** 检查更新 *****/
                Beta.checkUpgrade();

                break;
            case R.id.ll_about://关于
                final MyDialog dialog = new MyDialog(this);
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.my_dialog, null);
                LinearLayout linearLayout=(LinearLayout)layout.findViewById(R.id.ll_back);
                TextView tx=(TextView)layout.findViewById(R.id.tv_title);
                TextView txVersion=(TextView)layout.findViewById(R.id.tx_version);
                txVersion.setText("版本号"+GetVersionUtils.getAppVersionName(this));
                tx.setText("关于");
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();
                dialog.setCancelable(true);
                dialog.setContentView(layout);// show方法要在前面
                break;
            case R.id.ll_help://帮助
                break;
            case R.id.ll_feedback://反馈
                break;
            case R.id.btn_exit://退出
                ToastUtils.makeText(mContext, "退出");
                PreferencesHelper.remove(mContext,Const.Preferences.LOGIN_INFO);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    private boolean isQuit = false;

    @Override
    public void onBackPressed() {

        if (!isQuit) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            isQuit = true;

            //这段代码意思是,在两秒钟之后isQuit会变成false
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        isQuit = false;
                    }
                }
            }).start();


        } else {
            System.exit(0);
        }
    }
    public void initDefaultModelCofingItem(){
        List<TestModelBean.TemplateBean> mlist=new ArrayList<>();

        TestModelBean.TemplateBean bean=new TestModelBean.TemplateBean();
        UserInfoBean userbean= BaseApplication.getUserInfo();
        List<TestTask> tasks=new ArrayList<>();
        bean.setId(0);
        bean.setAccount( userbean.getUsername());
        bean.setTemplatename("默认模板");
        bean.setWorkorderno("HCC_test_0001");



        List<ModelCofingBean> defaults = new ArrayList<>();//删除

        /**
         * mAttachItems
         */
        TestTask mAttachItems=new TestTask();
        mAttachItems.setId(0);
        mAttachItems.setTaskname("ATTACH(DETACH)");//名称
        mAttachItems.setTesttype(Const.TestManage.TEST_TYPE_ATTACH);//测试类型
        mAttachItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);//测试模式
        mAttachItems.setTestcount(String.valueOf(10));//测试次数
        mAttachItems.setTestnumber(String.valueOf(20));//限时计次时长
        mAttachItems.setTesttime(String.valueOf(5));//测试时长
        mAttachItems.setTestinterval(String.valueOf(5));//循环间隔
        mAttachItems.setRetentiontime(String.valueOf(2));//保持时间
        mAttachItems.setComeFromService(true);
        tasks.add(mAttachItems);
        /**
         * mFtpDownItem
         */
        TestTask mFtpDownItems=new TestTask();
        mFtpDownItems.setTaskname("FTP(UP)");
        mFtpDownItems.setId(1);
        mFtpDownItems.setTesttype(Const.TestManage.TEST_TYPE_FTP_UP);
        mFtpDownItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpDownItems.setTestcount(String.valueOf(6));
        mFtpDownItems.setTesttime(String.valueOf(5));//测试时长
        mFtpDownItems.setTestinterval(String.valueOf(5));//循环间隔
        mFtpDownItems.setRetentiontime(String.valueOf(2));//保持时间
        mFtpDownItems.setThreadcount(String.valueOf(3));
        mFtpDownItems.setTimeout(String.valueOf(60));
        mFtpDownItems.setExectimeout(String.valueOf(10));
        TestTask.TargetUrlBean tb=new TestTask.TargetUrlBean();
        mFtpDownItems.setServerpath("android/com/beidian/xinling");
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mFtpDownItems.setTargeturl(tb);
        mFtpDownItems.setServeraddress("101.231.82.82");
        mFtpDownItems.setUsername("ceping");
        mFtpDownItems.setPassword("Ceping&2015");
        mFtpDownItems.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_ACTIVE));
        mFtpDownItems.setPort(String.valueOf(21));
        mFtpDownItems.setComeFromService(true);
        tasks.add(mFtpDownItems);

        /**
         * mFtpUpItem
         */

        TestTask mFtpUpItems=new TestTask();
        mFtpUpItems.setTaskname("FTP(DOWN)");
        mFtpUpItems.setId(2);
        mFtpUpItems.setTesttype(Const.TestManage.TEST_TYPE_FTP_DOWNLOAD);
        mFtpUpItems.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mFtpUpItems.setTestcount(String.valueOf(6));//测试次数
        mFtpUpItems.setTesttime(String.valueOf(5));//测试时长
        mFtpUpItems.setTestinterval(String.valueOf(5));//循环间隔
        mFtpUpItems.setRetentiontime(String.valueOf(2));//保持时间
        mFtpUpItems.setThreadcount(String.valueOf(3));
        mFtpUpItems.setTimeout(String.valueOf(60));
        mFtpUpItems.setExectimeout(String.valueOf(10));
        tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mFtpUpItems.setServerpath("android/com/beidian/xinling");
        mFtpUpItems.setTargeturl(tb);
        mFtpUpItems.setServeraddress("101.231.82.82");
        mFtpUpItems.setUsername("ceping");
        mFtpUpItems.setPassword("Ceping&2015");
        mFtpUpItems.setFtpmode(String.valueOf(Const.FTP_PASSIVE_MODEL_PASSIVE));
        mFtpUpItems.setPort(String.valueOf(21));
        mFtpUpItems.setComeFromService(true);
        tasks.add(mFtpUpItems);
        /**
         * HTTP
         */
        TestTask mHttpItem = new TestTask();
        mHttpItem.setId(3);
        mHttpItem.setTestinterval(String.valueOf(4));
        mHttpItem.setTaskname("HTTP");
        mHttpItem.setTesttype(Const.TestManage.TEST_TYPE_HTTP);
        mHttpItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mHttpItem.setTesttime(String.valueOf(new Integer(10)));
        mHttpItem.setTestcount(String.valueOf(6));
        mHttpItem.setTestnumber(String.valueOf(8));// 8s
         tb=new TestTask.TargetUrlBean();
        tb.setName("百度");
        tb.setUrl("htttp://www.baidu.com");
        mHttpItem.setTargeturl(tb);
        mHttpItem.setComeFromService(true);
        tasks.add(mHttpItem);


        /**
         * Csfbcall
         */
        TestTask mVoiceCallItem = new TestTask();
        mVoiceCallItem.setId(4);
        mVoiceCallItem.setTaskname("VOICE_CALL(csfb主叫)");
        mVoiceCallItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBZ);
        mVoiceCallItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVoiceCallItem.setTesttime(String.valueOf(20));
        mVoiceCallItem.setTestcount(String.valueOf(10));
        mVoiceCallItem.setTestnumber(String.valueOf(6));// 8s
        mVoiceCallItem.setTestinterval(String.valueOf(4));
        mVoiceCallItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVoiceCallItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVoiceCallItem.setCallphone("10086");
        mVoiceCallItem.setRetentiontime(String.valueOf(45));// 10s
        mVoiceCallItem.setBlockingtime(String.valueOf(20));// 60s
        mVoiceCallItem.setComeFromService(true);
        tasks.add(mVoiceCallItem);
        /**
         * Csfbpa
         */
        TestTask mCfsbCallPassiveItem = new TestTask();
        mCfsbCallPassiveItem.setId(5);
        mCfsbCallPassiveItem.setTaskname("VOICE_CALL(csfb被叫)");
        mCfsbCallPassiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_CSFBB);
        mCfsbCallPassiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mCfsbCallPassiveItem.setTesttime(String.valueOf(20));
        mCfsbCallPassiveItem.setTestcount(String.valueOf(10));
        mCfsbCallPassiveItem.setTestnumber(String.valueOf(6));// 8s
        mCfsbCallPassiveItem.setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mCfsbCallPassiveItem.setTestinterval(String.valueOf(4));
        mCfsbCallPassiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mCfsbCallPassiveItem.setCallphone("10086");
        mCfsbCallPassiveItem.setRetentiontime(String.valueOf(45));// 10s
        mCfsbCallPassiveItem.setBlockingtime(String.valueOf(20));// 6s
        mCfsbCallPassiveItem.setComeFromService(true);
        tasks.add(mCfsbCallPassiveItem);
        /**
         * voteCall
         */
        TestTask mVolteCallActiveItem = new TestTask();
        mVolteCallActiveItem.setId(6);
        mVolteCallActiveItem.setTestinterval(String.valueOf(4));
        mVolteCallActiveItem.setTaskname("VOICE_CALL(volte主叫)");
        mVolteCallActiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_VOLTEZ);
        mVolteCallActiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVolteCallActiveItem.setTesttime(String.valueOf(20));
        mVolteCallActiveItem.setTestcount(String.valueOf(10));
        mVolteCallActiveItem.setTestnumber(String.valueOf(6));// 6s
        mVolteCallActiveItem
                .setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVolteCallActiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVolteCallActiveItem.setCallphone("10086");
        mVolteCallActiveItem.setRetentiontime(String.valueOf(45));// 10s
        mVolteCallActiveItem.setBlockingtime(String.valueOf(20));// 6s
        mVolteCallActiveItem.setComeFromService(true);
        tasks.add(mVolteCallActiveItem);
        /**
         * votePa
         */
        TestTask mVolteCallPassiveItem = new TestTask();
        mVolteCallPassiveItem.setId(7);

        mVolteCallPassiveItem.setTestinterval(String.valueOf(4));
        mVolteCallPassiveItem.setTaskname("VOICE_CALL(volte被叫)");
        mVolteCallPassiveItem.setTesttype(Const.TestManage.TEST_TYPE_CALL_VOLTEB);
        mVolteCallPassiveItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mVolteCallPassiveItem.setTesttime(String.valueOf(20));
        mVolteCallPassiveItem.setTestcount(String.valueOf(10));
        mVolteCallPassiveItem.setTestnumber(String.valueOf(6));// 6s
        mVolteCallPassiveItem
                .setCoordinatemode(String.valueOf(Const.VOICE_ITEM_COORDINATION_MODE_COMMON));
        mVolteCallPassiveItem.setCalltype(String.valueOf(Const.VOICE_CALL_MODE_VOICE));
        mVolteCallPassiveItem.setCallphone("10086");
        mVolteCallPassiveItem.setRetentiontime(String.valueOf(45));// 10s
        mVolteCallPassiveItem.setBlockingtime(String.valueOf(20));// 6s
        mVolteCallPassiveItem.setComeFromService(true);
        tasks.add(mVolteCallPassiveItem);
        /**
         * Wait
         */
        TestTask mWaitItem = new TestTask();
        mWaitItem.setId(8);
        mWaitItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        mWaitItem.setTaskname("WAIT");
        mWaitItem.setTesttime(String.valueOf(10));// 10s
        mWaitItem.setTesttype(String.valueOf(Const.TestManage.TEST_TYPE_WAIT));
        mWaitItem.setComeFromService(true);
        tasks.add(mWaitItem);

         /**
         * PING
         */
        TestTask mPingItem = new TestTask();
        mPingItem.setId(9);
        mPingItem.setTaskname("PING");
        mPingItem.setTesttype(Const.TestManage.TEST_TYPE_PING);
        mPingItem.setTestmode(Const.TestManage.TEST_MODE_COUNT);
        mPingItem.setTesttime(String.valueOf(10));
        mPingItem.setTestcount(String.valueOf(6));
        mPingItem.setTestnumber(String.valueOf(3));// 8s

        mPingItem.setTestinterval(String.valueOf(4));
        TestTask.TargetUrlBean mp=new TestTask.TargetUrlBean();
        mp.setUrl("www.baidu.com");
        mp.setName("百度");
        mPingItem.setTargeturl(mp);
        mPingItem.setComeFromService(true);
        tasks.add(mPingItem);
        /**
         *idle
         */
        TestTask midleItem = new TestTask();
        midleItem.setTaskname("IDLE");
        midleItem.setId(10);
        midleItem.setTesttype(Const.TestManage.TEST_TYPE_IDLE);
        midleItem.setTestmode(Const.TestManage.TEST_MODE_TIME);
        midleItem.setTesttime(String.valueOf(9));
        midleItem.setComeFromService(true);
        tasks.add(midleItem);

        bean.setTaskList(tasks);
        mlist.add(bean);
        Log.i(TAG, "initDefaultModelCofingItem: " );
        JSON json = (JSON) JSON.toJSON(mlist);
        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));

        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "model");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);


    }
    public void initDefaultColorConfigItem() {

        /**
         * 初始化PCI的颜色值
         */
        List<ColorConfigItem> pci = new ArrayList<>();
        for (int i = 0; i < Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG.length; i++) {

            ColorConfigItem configItem = new ColorConfigItem();
            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][2]]);
            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
            configItem.setMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0]);
            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
            configItem.setMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
            configItem.setVirtualMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
            configItem.setVirtualMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0] + 1);
            configItem.setType(Const.ColorManager.COVERAGE_PCI);
            pci.add(configItem);
        }
        Log.i(TAG, "initDefaultColorConfigItem: " + pci.size());
        JSON json = (JSON) JSON.toJSON(pci);
        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "pci");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
//        /**
//         * 初始化RSRQ的颜色值
//         */
//        List<ColorConfigItem> rsrq = new ArrayList<>();
//        for (int i = 0; i < Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG.length; i++) {
//
//            ColorConfigItem configItem = new ColorConfigItem();
//            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][2]]);
//            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
//            configItem.setMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0]);
//            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
//            configItem.setMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
//            configItem.setVirtualMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
//            configItem.setVirtualMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0] + 1);
//            configItem.setType(Const.ColorManager.COVERAGE_RSRQ);
//            rsrq.add(configItem);
//        }
//        Log.i(TAG, "initDefaultColorConfigItem: " + rsrq.size());
//        json = (JSON) JSON.toJSON(rsrq);
//        jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
//        jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "rsrq");
//        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
        /**
         * 初始化Sinr的颜色值
         */
        List<ColorConfigItem> sinr = new ArrayList<>();
        for (int i = 0; i < Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG.length; i++) {

            ColorConfigItem configItem = new ColorConfigItem();
            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][2]]);
            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
            configItem.setMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0]);
            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
            configItem.setMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
            configItem.setVirtualMaxValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][1]);
            configItem.setVirtualMinValue(Const.ColorManager.SINR_DEFAULT_COLOR_CONFIG[i][0] + 1);
            configItem.setType(Const.ColorManager.COVERAGE_SINR);
            sinr.add(configItem);
        }
        Log.i(TAG, "initDefaultColorConfigItem: " + sinr.size());
        json = (JSON) JSON.toJSON(sinr);
        jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
        jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "sinr");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
        /**
         * 初始化RSRP
         */
        List<ColorConfigItem> rsrp = new ArrayList<>();
        for (int n = 0; n < Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG.length; n++) {
            ColorConfigItem configItem = new ColorConfigItem();
            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG[n][2]]);
            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
            configItem.setMinValue(Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG[n][0]);
            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
            configItem.setMaxValue(Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMaxValue(Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMinValue(Const.ColorManager.RSRP_DEFAULT_COLOR_CONFIG[n][0] + 1);
            configItem.setType(Const.ColorManager.COVERAGE_RSRP);
            rsrp.add(configItem);

        }
        Log.i(TAG, "initDefaultColorConfigItem: " + rsrp.size());
        json = (JSON) JSON.toJSON(rsrp);
        jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
        jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "rsrp");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);

        /**
         * 初始化APPDL
         */
        List<ColorConfigItem> appdl = new ArrayList<>();
        for (int n = 0; n < Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG.length; n++) {
            ColorConfigItem configItem = new ColorConfigItem();
            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG[n][2]]);
            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
            configItem.setMinValue(Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG[n][0]);
            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
            configItem.setMaxValue(Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMaxValue(Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMinValue(Const.ColorManager.APPDL_DEFAULT_COLOR_CONFIG[n][0] + 1);
            configItem.setType(Const.ColorManager.COVERAGE_DL);
            appdl.add(configItem);
        }
        Log.i(TAG, "initDefaultColorConfigItem: " + appdl.size());
        json = (JSON) JSON.toJSON(appdl);
        jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
        jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "appdl");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
        /**
         * 初始化APPUL
         */
        List<ColorConfigItem> appul = new ArrayList<>();
        for (int n = 0; n < Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG.length; n++) {
            ColorConfigItem configItem = new ColorConfigItem();
            configItem.setColor(Const.ColorManager.COLOR_TONE[Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG[n][2]]);
            configItem.setLeftType(Const.ColorManager.COVERAGE_EXCLUSIVE);
            configItem.setMinValue(Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG[n][0]);
            configItem.setRightType(Const.ColorManager.COVERAGE_INCLUDE);
            configItem.setMaxValue(Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMaxValue(Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG[n][1]);
            configItem.setVirtualMinValue(Const.ColorManager.APPUL_DEFAULT_COLOR_CONFIG[n][0] + 1);
            configItem.setType(Const.ColorManager.COVERAGE_UL);
            appul.add(configItem);

        }
        Log.i(TAG, "initDefaultColorConfigItem: " + appul.size());
        json = (JSON) JSON.toJSON(appul);
        jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
        jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "appul");
        FileUtils.saveFile(mContext, json.toString(), jsonFilePath);
    }

}
