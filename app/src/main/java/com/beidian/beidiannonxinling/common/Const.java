package com.beidian.beidiannonxinling.common;

import android.os.Environment;

import com.beidian.beidiannonxinling.app.BaseApplication;
import com.beidian.beidiannonxinling.util.PreferencesHelper;

import java.io.File;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2310:27
 * @描述: 常量类
 */

public class Const {
    public static final int TYPE_DIALOG_OK = 1;
    public static final int TYPE_DIALOG_STOP = 2;
    public static final int TYPE_DIALOG_IMAGE = 3;
    public static final int TYPE_DIALOG_KONW = 4;
    public static final int TYPE_DIALOG_EXIT = 5;
    public static final float ABOUT_DEGREE = 10;

    public static final int ADD = 112; //新增
    public static final int LOOK = 113; //查看
    public static final int RESET = 114; //重测
    public static final String HONGZHAN = "1"; //宏站
    public static final String BASE = "0"; //基础
    public static final String SHIFEN = "2"; //室分

    public static final int Antenna1=1101,Antenna2=1102,AntennaBuild=1100;

    /**
     * url常量类
     */
    public static class Url {
        //log上传文件路径
        public static String SERVER_W = "http://180.168.181.102:9009";//外网服务器
/*<<<<<<< .mine
//                public static  String SERVER_N ="http://192.168.3.219:9009";  //内网服务器
//                public static  String SERVER_N ="http://192.168.3.242:9009";  //内网服务器
||||||| .r11345
=======
        public static String SERVER_N = "http://192.168.3.219:9009";  //内网服务器
>>>>>>> .r11406*/
//        public static  String SERVER_N ="http://192.168.3.219:9009";  //内网服务器
        public static  String SERVER_N ="http://192.168.3.242:9009";  //内网服务器
        public static String servicePah = PreferencesHelper.getString(BaseApplication.getAppContext(), Const.Preferences.DEFULT_SERVICE_PATH, SERVER_N);

        public static String SERVER = SERVER_W;

        public final static String LOG_UP                        = SERVER + "/app/upload1";
        public static final String URL_LOGIN                     = SERVER + "/app/login";
        public static final String URL_GET_WORK_ORDER            = SERVER + "/app/getWorkOrder";
        public static final String URL_SEARCH_WORK_ORDER         = SERVER + "/app/searchWorkOrder";
        public static final String URL_GET_SITE_INFO             = SERVER + "/app/getSiteInfo";//获取站点信息
        public static final String URL_GET_TEST_MODEL            = SERVER + "/app/getTestTemplate";//
        public static final String URL_GET_TEST_MODEL_BY_ACCOUNT = SERVER + "/app/getTestTemplateByAccount";//
        public static final String URL_QUERY_SITE                = SERVER +"/app/datedao";//

        public static void setSERVER(String SERVER) {
            Url.SERVER = SERVER;
        }
    }

    /**
     * ftp常量类
     */
    public static class Ftp {
        public final static String FTP_USERNAME   = "ftpuser";
        public final static String FTP_PORT       = "21";
        public final static String FTP_URL        = "139.196.168.28";
        public final static String FTP_PASSWORD   = "Test123";
        public final static String FTP_REMOTEPATH = "/";
        public final static String FTP_FILENAME   = "50M.rar";
        public final static String FTP_LOCALPATH  = "mnt/shell/emulated/0/";

        //上传文件路径
        public final static String FTP_TEPY_UP_FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "100M.rar";


    }

    /**
     * 网络提示常量类
     */
    public static class NetConst {
        public final static String NET_NOT_CONNECT = "请先连接网络";//没有连接WIFI/2G/3G/4G网络的时候才弹出此提示
        public final static String NET_TIMEOUT = "网络连接超时";
        public final static String NET_CONNECT_EXCEPTION = "网络连接异常，请检查网络";
        public final static String NET_SERVER_ERROR = "服务器异常";//只有在解析json的try-catch捕获异常的时候才弹出此提示
        public final static String NET_LOADING = "正在加载...";
        public final static String NO_MORE_DATA = "没有更多数据";
    }

    /**
     * testmanage常量类
     */
    public static class TestManage {

        //测试类型TEST_TYPE_WAIT
        public final static String TEST_TYPE_FTP_DOWNLOAD = "ftp_down";
        public final static String TEST_TYPE_FTP_UP = "ftp_up";
        public final static String TEST_TYPE_WAIT = "wait";
        public final static String TEST_TYPE_IDLE = "idle";
        public final static String TEST_TYPE_CALL_VOLTEZ = "call_voltez";
        public final static String TEST_TYPE_CALL_VOLTEB = "call_volteb";
        public final static String TEST_TYPE_PING = "ping";
        public final static String TEST_TYPE_CALL_CSFBZ = "call_csfbz";
        public final static String TEST_TYPE_CALL_CSFBB = "call_csfbb";
        public final static String TEST_TYPE_ATTACH = "attach";
        public final static String TEST_TYPE_HTTP = "http";
        public final static String RESULT_TYPE_TEST = "得到结果后返回";
        public final static String RESULT_TYPE_SIGNALLING = "这个数据每秒一次";
        //计次还是计时或者是限时计次
        public final static String TEST_MODE_TIME_COUNT = "timecount";//限时计次
        public final static String TEST_MODE_TIME       = "time";//计时
        public final static String TEST_MODE_COUNT      = "count";//计次
        //oneByOne_ok返回线程池状态是否空闲
        public final static String ONE_BY_ONE_OK        = "ok";
        //oneByOne_ok返回线程池状态是否忙
        public final static String ONE_BY_ONE_BUSY      = "busy";
        //测试类型为呼叫
        public final static String TEST_CALL            = "TEST_CALL";
        //采集速度
        public static  int COLLECTION_SPEED        = 300;
        //测试类型日志记录的间隔时间 testManage  FTP会用到,更改的时候注意是否有影响
        public  static int TEST_LOG_SAVE_TIME           = 1000;
    }

    public static class ReadFile {
        public final static String ROOT_FILE_DIR = "BeiDian";

        public static String getBaseDir(String dirName) {
            return Environment.getExternalStorageDirectory().toString() + File.separator + ROOT_FILE_DIR + File.separator + dirName;
        }
    }

    public static class SaveFile {
        public final static String FILE_DIR_CREATE_SUCCESS = "文件夹创建成功";
        public final static String FILE_DIR_CREATE_FAILED = "文件夹创建失败，无法保存数据";
        //根目录文件夹
        public final static String ROOT_FILE_DIR = "BeiDian";
        //图片文件夹
        public final static String IMAGE_DIR = ROOT_FILE_DIR + File.separator + "image";
        //log文件文件夹
        public final static String LOG_FILE_DIR = "Log";

        public static String FILE_ANDRESS = null;

        public static String BASE_TEST = "baseTest";
        public static String BASE_INFO_COLLECTION = BASE_TEST + "/baseInfoCollection";
        public static String BASE_CQT_TEST_COLLECTION = BASE_TEST + "/cqtTestCollection";

        public static String HONGZHAN_TEST = "hongZhanTest";
        public static String HONGZHAN_INFO_COLLECTION = HONGZHAN_TEST + "/hongZhanInfoCollection";
        public static String HONGZHAN_CQT_TEST_COLLECTION = HONGZHAN_TEST + "/cqtTestCollection";
        public static String HONGZHAN_DT_TEST = HONGZHAN_TEST + "/DT_Test";
        public static String HONGZHAN_NET = HONGZHAN_TEST + "/net";

        public static String SHIFEN_TEST = "shiFenTest";
        public static String CQTRRULIST = "cqtrrulist";
        public static String SHIFEN_INFO_COLLECTION = SHIFEN_TEST + "/shiFenInfoCollection";
        public static String SHIFEN_MASTER_DEVICE = SHIFEN_TEST + "/masterDevice";
        public static String SHIFEN_DISTRIBUTED = SHIFEN_TEST + "/distributed";
        public static String SHIFEN_CHANGE = SHIFEN_TEST + "/change";
        public static String SHIFEN_LEAKED = SHIFEN_TEST + "/leaked";



        public static String LTE_SHIFEN_TEST = "lteShiFenTest";

        /**
         * 获取log日志的路径
         *
         * @param workOrder   工单号
         * @param testOrderId 任务号
         * @param logFile     log文件名
         * @return 路径
         */
        public static String getLogFilePath(String workOrder, String testOrderId, String logFile) {
            return Environment.getExternalStorageDirectory().toString() + File.separator + ROOT_FILE_DIR + File.separator + workOrder + File.separator + LOG_FILE_DIR + File.separator + testOrderId + File.separator + logFile;
        }

        //直接创建文件夹
        public static String getPage(String activityName) {
            return ROOT_FILE_DIR + File.separator + activityName;
        }

        //根据工单号，创建页面文件夹
        public static String getPageDir(String workOrder, String activityName) {
            return ROOT_FILE_DIR + File.separator + workOrder + File.separator + activityName;
        }

        //根据工单号，创建指定页面下的文件夹
        public static String getDir(String workOrder, String activityName, String dirName) {
            return ROOT_FILE_DIR + File.separator + workOrder + File.separator + activityName + File.separator + dirName;
        }

        public static String getBaseDir(String dirName) {
            return ROOT_FILE_DIR + File.separator + dirName;
        }
        public static String getModelDir(String dirName){
            return ROOT_FILE_DIR+"/model"+File.separator+dirName;
        }

        //根据文件夹路径和工单号，获取json文件的绝对路径
        public static String getJsonAbsoluteFilePath(String jsonDir, String workOrder) {
            return jsonDir + File.separator + workOrder + ".txt";
        }

        //根据文件夹路径和工单号，获取json文件的绝对路径
        public static String getJsonAbsoluteFilePaths(String jsonDir, String timers, String stationName) {
            return jsonDir + File.separator + stationName + timers + ".txt";
        }

        //
        public static String getJsonFilePaths(String jsonDir, String stationName) {
            return jsonDir + File.separator + stationName + ".txt";
        }

        public static String getJsonColorFilePaht(String jsonDir, String fileName) {
            return jsonDir + File.separator + fileName + ".txt";
        }


    }

    public static class IntentTransfer {
        public final static String ONE_KEY_TEST = "oneKeyTest";
        public final static String WORKDERNO = "workorderno";

        public final static int resultCode_CqtTestActivityActivity = 1001;
        public final static String resultCode_CameraActivity = "CameraActivity";
        public final static String resultCode_MechanicalActivity = "MechanicalActivity";
        public final static String TYPE = "type";
        public final static String DEFULT_INFO_PATH = "defult_info_address";//默认信息路径
        public final static String DEFULT_INFO_BEAN = "defult_info_bean";
        public final static String MAIN_ACTIVITY_TOOLS = "defult_info_bean";

        public final static String BASE_ABSOLUTEPATH = "jsonFileAbsolutePathName";
        public final static String FILE_PATH = "filePath";//当前文件要存储到的路径
        public final static String ORDER_TYPE = "orderType";

    }

    //    PreferencesHelper
    public static class Preferences {
        public final static String DEBUG_SWITCH = "debugSwitch";
        public final static String LOGIN_INFO = "login_info";
        public final static String DEFULT_SERVICE_PATH = "defult_service_path";
        public final static String USER_INFO = "userInfo";
    }

    public static class FileName {
//        public final static String END_NAME = ".log";
//        public final static String SIGNALLING = "/signalling" + END_NAME;
//        public final static String FTP_UP = "/ftp_up" + END_NAME;
//        public final static String FTP_DOWN = "/ftp_down" + END_NAME;
//        public final static String PING = "/ping" + END_NAME;
//        public final static String HTTP = "/http" + END_NAME;
//        public final static String CALL_VOLTEZ = "/call_voltez" + END_NAME;
//        public final static String CALL_CSFBZ = "/call_csfbz" + END_NAME;

        public final static String END_NAME = ".log";
        public final static String SIGNALLING = "/log/signalling" + END_NAME;
        public final static String FTP_UP = "/log/ftp_up" + END_NAME;
        public final static String FTP_DOWN = "/log/ftp_down" + END_NAME;
        public final static String PING = "/log/ping" + END_NAME;
        public final static String HTTP = "/log/http" + END_NAME;
        public final static String CALL_VOLTEZ = "/log/call_voltez" + END_NAME;
        public final static String CALL_CSFBZ = "/log/call_csfbz" + END_NAME;

    }
    public static class ColorManager{
        /**
         * 颜色相关
         */
        /**
         * 颜色阈值设置
         */

        public static final int COVERAGE_REFRESH_SELECTED_COLOR = 0X005;//刷新选择的颜色

        public static final int COVERAGE_SETTING_SUCCESS = 0X006;//颜色分段设置成功

        public static final int COVERAGE_REFRESH_COLOR_LIST = 0X007;//刷新颜色列表

        public static final int COVERAGE_REFRESH_DOT_COLOR = 0X008;//切换地图上的点的颜色

        public static final int TIP_PLEASE_SELECT_COLOR = 0X009;//请选择颜色

        public static final int TIP_CAN_NOT_EMPTY = 0X010;//阈值不能为空

        public static final int TIP_INPUT_VALUE_MUST_BE_NUMBER = 0X011;//阈值必须为数字

        public static final int TIP_INPUT_VALUE_NOT_EXIST = 0X012;//阈值必须为数字

        public static final int TIP_INPUT_VALUE_REPEAT = 0X013;//阈值区间重复

        //liuren add
        public static final int COVERAGE_CHANGE = 0X0014;//图层切换
        public static final int COVERAGE_CHANGE_END = 0X0015;//图层切换结束

        /**
         * 图层管理 各个图层对应的值
         */
        public static final int COVERAGE_PCI = 0;
        public static final int COVERAGE_RSRP = 1;
        public static final int COVERAGE_RSRQ = 2;
        public static final int COVERAGE_SINR = 3;
        public static final int COVERAGE_DL = 4;
        public static final int COVERAGE_UL = 5;

        public static final String SP_COLORCONFIGINITIALIZE="colorConfigInitialize";

        public static final String SP_CURRENT_COLOR_TYPE="colorType";

        /**
         * 可供选择的颜色色值
         */
        public static final int[] COLOR_TONE = {0XFFB9C2E9, 0XFF00FFFF, 0XFFF8FF00, 0XFFFF8C00, 0XFF757575,
                0XFF67D7F1, 0XFFCD6889, 0XFF4D0099, 0XFF990099, 0XFF994D00, 0XFF999900, 0XFF009999, 0XFF00994D, 0XFF0000D6,
                0XFFFF1463, 0XFF990080, 0XFFFF00FF, 0XFF8000FF, 0XFFFF7A7A, 0XFF00FF80, 0XFFFFCCE6, 0XFF63AEFF};

        /**
         * sinr 默认颜色设置
         */
        public static int[][] SINR_DEFAULT_COLOR_CONFIG = {{-15, -3, 7}, {-3, 0, 5}, {0, 6, 4}, {6, 13, 2},
                {13, 20, 3}, {20, 28, 1}, {28, 50, 0}};

        /**
         * DL 默认颜色设置 Kbps
         */
        public static int[][] APPDL_DEFAULT_COLOR_CONFIG = {{0, 10 * 1000, 7}, {10 * 1000, 20 * 1000, 4}, {20 * 1000, 30 * 1000, 2},
                {30 * 1000, 40 * 1000, 3}, {40 * 1000, 50 * 1000, 1}, {50 * 1000, 100 * 1000, 0}};
        /**
         * UL默认颜色设置  Kbps
         */
        public static int[][] APPUL_DEFAULT_COLOR_CONFIG = {{0, 1 * 1000, 7}, {1 * 1000, 2 * 1000, 5}, {2 * 1000, 3 * 1000, 4}, {3 * 1000, 4 * 1000, 3},
                {4 * 1000, 5 * 1000, 2}, {6 * 1000, 7 * 1000, 1}, {7 * 1000, 12 * 1000, 0}};
        /**
         * RSRP默认颜色设置
         */
        public static int[][] RSRP_DEFAULT_COLOR_CONFIG = {{-130, -115, 7}, {-115, -105, 6}, {-105, -100, 5},
                {-100, -95, 4}, {-95, -85, 3}, {-85, -80, 2},
                {-80, -75, 1}, {-75, 0, 0}};
        /**
         * 颜色阈值设置
         */

        public static final int COVERAGE_INCLUDE = 1;//包含阈值
        public static final int COVERAGE_EXCLUSIVE = 0;//不包含阈值
    }




    /**
     * 模板相关
     */
    // kevin
    public static final String KEY_MODEL_DETAIL_INFO = "model_detail_info";

    public static final String KEY_TEST_ITEM_DETAIL_INFO = "test_item_detail_info";

    public static final byte ITEM_TEST_MODE_COUNT = 0;// 计次
    public static final byte ITEM_TEST_MODE_TIME = 1;// 计时
    public static final byte ITEM_TEST_MODE_TIME_LIMIT_COUNT = 2;// 限时计次

    public static final byte VOICE_CALL_MODE_VOICE = 0;// 语音电话
    public static final byte VOICE_CALL_MODE_VIDEO = 1;// 视频电话

    public static final byte VOICE_ITEM_COORDINATION_MODE_COMMON = 0;// voicecall普通协调方式
    public static final byte VOICE_ITEM_COORDINATION_MODE_BLUETOOTH = 1;// voice
    // call
    // 蓝牙派对方式

    public static final byte FTP_PASSIVE_MODEL_ACTIVE = 0;// 主动
    public static final byte FTP_PASSIVE_MODEL_PASSIVE = 1;// 被动

    public static final int STATE_TEST_HONGZHAN = 0;// 宏站测试
    public static final int STATE_TEST_FUGAI = 1;// 覆盖测试
    public static final int STATE_TEST_QIEHUAN = 2;// 切换测试
    public static final int STATE_TEST_WAIXIE = 3;// 外泄测试
    public static final int STATE_TEST_FREE = 4;// 自由测试

    public static final int TEST_TYPE_ROAD = 0;// 路测
    public static final int TEST_TYPE_FUNCTION = 1;// 功能测试

    public static final int TEST_TYPE_FLATLAYER = 2;// 平层测试
    public static final int TEST_TYPE_ELEVATOR = 3;// 电梯测试

    public static final int TEST_TYPE_INDOOR_OUTDOOR_AREA = 4;// 室内外小区切换
    public static final int TEST_TYPE_FLATLAYER_AREA = 5;// 平层间小区切换
    public static final int TEST_TYPE_FLATLAYER_ELEVATOR_AREA = 6;// 平层电梯小区切换
    public static final int TEST_TYPE_FIXED_POINT = 7;// 定点测试

    public static final int TEST_TYPE_LOCKED_FREQUENCY_OPERATION = 8;// 锁频业务态
    public static final int TEST_TYPE_LOCKED_FREQUENCY_IDLE = 9;// 锁频空闲态
    public static final int TEST_TYPE_FREE_OPERATION = 10;// 自由业务态
    public static final int TEST_TYPE_FREE_IDLE = 11;// 自由空闲态

    public static final int PIC_MANAGER_LTE_MODE_RSRP = 0;// 图例管理LTE下的RSRP
    public static final int PIC_MANAGER_LTE_MODE_SINR = 1;// 图例管理LTE下的SINR
    public static final int PIC_MANAGER_LTE_MODE_RSRQ = 2;// 图例管理LTE下的RSRQ
    public static final int PIC_MANAGER_LTE_MODE_PCI = 3;// 图例管理LTE下的PCI
    public static final int PIC_MANAGER_LTE_MODE_APP_DL = 4;// 图例管理LTE下的App
    // Throughput DL
    // Mbps
    public static final int PIC_MANAGER_LTE_MODE_APP_UL = 5;// 图例管理LTE下的App
    // Throughput UL
    // Mbps
    public static final String KEY_PIC_MANAGER_TYPE = "pic_manager_type";

    // end

    public static final String SP_NETCLASS_2G = "netClass_2g";
    public static final String SP_NETCLASS_4G = "netClass_4g";


}
