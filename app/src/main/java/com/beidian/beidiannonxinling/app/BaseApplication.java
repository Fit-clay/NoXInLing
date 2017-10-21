package com.beidian.beidiannonxinling.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.beidian.beidiannonxinling.bean.CellinfoListBean;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.activity.MainActivity;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.OsHelper;
import com.beidian.beidiannonxinling.util.PreferencesHelper;
import com.beidian.beidiannonxinling.util.SDCardUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;



/**
 * Created by shanpu on 2017/8/15.
 * <p>
 */
public class BaseApplication extends Application {

    //OkGo默认超时时间:10s
    public static final int DEFAULT_MILLISECONDS = 10000;
    private static final String TAG = BaseApplication.class.getName();
    /*主线程的上下文。(应用程序级别的全局上下文对象，生命周期很长，直到应用程序关闭，该对象才会被销毁)*/
    private static Context mApplicationContext;

    public static Context getAppContext() {
        return mApplicationContext;
    }

    public static List<CellinfoListBean.LineinfoListBean> lineinfoListBeen=new ArrayList<>();

    private static UserInfoBean  userInfo;

    public static UserInfoBean getUserInfo() {
        if(userInfo==null){
            return new UserInfoBean();
        }
        return userInfo;
    }

    public static void setUserInfo(UserInfoBean userInfo) {
        BaseApplication.userInfo = userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化百度地图
        mApplicationContext = getApplicationContext();
        userInfo=PreferencesHelper.getBean(this,Const.Preferences.USER_INFO,UserInfoBean.class);
        //初始化bugly
//        Beta.autoCheckUpgrade = false;//自动检查更新开关
//        CrashReport.initCrashReport(getApplicationContext(), "98aedb892a", false);
//        Beta.canhowUpgradeActs.add(MainActivity.class);
        Beta.canShowUpgradeActs.add(MainActivity.class);

        Bugly.init(getApplicationContext(), "98aedb892a", false);//bugly初始化开关


        SDKInitializer.initialize(mApplicationContext);
        initOkGo(mApplicationContext);
//        CrashHandler.getInstance().init(this, LoginActivity.class);

        boolean DebugSwitch=  PreferencesHelper.getBoolean(this, Const.Preferences.DEBUG_SWITCH,false);
      if(DebugSwitch){
          String processName = OsHelper.getProcessName(this, android.os.Process.myPid());
          if (processName != null) {
              boolean defaultProcess = processName.equals(OsHelper.getPackage(getBaseContext()));
              //TODO onCreate会根据不同的process,执行多次.故通过分支根据不同情况做相应处理
              if (defaultProcess) {
                  Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
                      @Override public void uncaughtException(Thread thread, Throwable ex) {
                          doUncaughtExceptionHandler(thread, ex);
                      }
                  };
                  // 以下用来捕获程序崩溃异常
                  Thread.setDefaultUncaughtExceptionHandler(restartHandler);
              }
          }
      }
    }
    /**
     * 初始化OkGo
     *
     * @param mApplicationContext
     */
    private void initOkGo(Context mApplicationContext) {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();

//        headers.put("commonHeaderKey1", "commonHeaderValue1");//header参数不支持中文
//        headers.put("commonHeaderKey2", "commonHeaderValue2");

        HttpParams params = new HttpParams();
//        params.put("commonParamsKye1", "commonParamsValue1");//param支持中文,直接传,不要自己编码
//        params.put("commonParamsKye2", "这里支持中文参数");
//        params.put("commonParamsKye3", "全局请求参数");

        //必须调用初始化
        OkGo.init((Application) mApplicationContext);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, false)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(0)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
//              .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

                    //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

                    //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

                    //这两行同上，不需要就不要加入
                    .addCommonHeaders(headers)  //设置全局公共头
                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {

        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void doUncaughtExceptionHandler(Thread thread, Throwable ex) {
        String time     = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(new Date());
        String path     = SDCardUtils.getExternalSdCardPath() + OsHelper.getPackage(this) +"/log/",
                fileName = "crash-" + time + "-" + System.currentTimeMillis() + ".log";
        if (SDCardUtils.canUse()) { FileUtils.save(path,fileName,ex); }

    }


}
