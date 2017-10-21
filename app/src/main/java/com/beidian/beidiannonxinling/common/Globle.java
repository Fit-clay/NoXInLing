package com.beidian.beidiannonxinling.common;

/**
 * 存放瞬时全局变量
 * 
 * @author henry
 *
 */
public class Globle {

	public static boolean testUtilsBreak = false; // 测试工具类的进程结束标志

	public static volatile boolean isOver = false;// ftp上传线程结束标志
	public static boolean isSignStrenthChange = false;// 信号强度发生改变
	public static volatile boolean isTest = false;// GIS测试是否测试中的信号量
	public static int testNum;// 测试项测试次数
	public static int nowTestNum = 1;// 测试项执行次数
	public static int testTime = 0;// 测试项执行的时间
	public static int alreadyTime;// 已经测试的时间
	public static int testmodel;// 测试模式
	public static int blockTime = 0;// 测试项中设定的阻塞时间
	public static int alreadyBlockTime = 0;// 测试过程中已近阻塞的时间
	public static int alreadyDuringTime = 0;// 测试过程中已接通的时间

	public static int keepingTime = 0;// 已经保持的时间
	public static String incomingPhoneNumber = "";// 来电号码

	public static String DOWNSCORE_VERSION = "";// 基站运营商version

	// public static byte currentTestType = -1;// 当前的测试类型

	// kai
//	public static IdleTestResult idleTestResult;// idle测试结果
	// end
//	public static FtpTestResult ftpTestResult;// ftp测试结果
//	public static HttpResult httpTestResult;// http测试结果
//	public static PingResul pingTestResult;// ping测试结果
//	public static VoiceCallTestResult voiceCallTestResult;// voiceCall测试结果
	// public static VoiceCallPassiveTestResult voiceCallPassiveTestResult;//
	// voiceCall被叫测试结果
//	public static TachTestResult tachTestResult;// Tach测试结果
	public static int voiceCallSuccessTimes = 0;// 语音通话的成功次数

	public static String hugeCellId = "";// 宏站路测扇区编号
	public static String sectorType = "";// 宏站路测方向 顺时针、逆时针
	public static boolean isStartRecordLog = false;// 是否开始录制文件，默认不录制
	public static int collectModle = 0;// 采集模式 0自动打点 1手动打点

	/*
	 * 文件命名相关字段
	 */
	public static volatile boolean isCallingFlag = false; // 是否正在拨打电话标志
	public static volatile boolean isCallOffHook = false; // CALL_STATE_OFFHOOK
	public static String BusinessFileName = "";// 业务测试文件
	public static String L3FileName = "";// L3测试文件
	public static String L1L2FileName = "";// L1L2测试文件
	public static String NeiborFileName = "";// 邻区测试文件
	public static String TaskDirectory = "";// 日志存放目录路径
	public static String PointFileName = "";// 存放室分打点数据
	public static String EventFileName = "";// 事件log名

	public static boolean isShifenTest = false;// 是否是室分测试
	public static boolean isNotRecordDotTest = false;// 是否是语音测试

	public static boolean isNotDotTest = false;// 是否需要打点（室分定点测试活着只有电话相关的测试时不需要打点）
	// public static boolean isVoiceTest = false;// 是否是语音测试
	// public static String subTitle = ""; // 信令名称
	public static boolean isSuspend = false;// 是否室分暂停 点击暂停为true
	public static long ftpStartTime = 0; // 计时，限时计次模式下ftp开始测试的毫秒数

	/**
	 * 电话拨打状态
	 */
	public static long callSetupData;// 开始拨打电话的时间点
	public static long callAlerting;// 第一声嘟的时间点
	public static long callBlockTime;// 阻塞时间
	public static long callConnect;// 接通时间点
	public static long duringTime;// 通话时长
	public static long delayTime;// （setup和听到第一声嘟一声之间的时间差） （Volte呼叫时延)
	// public static long callDate;// 拨打电话按钮按下的时间点
	public static long connDelay;// 连接时延（setup到connect之间的时间差）(volte接通时延)
	public static long backDelayTime;// 回落时间点

	public static long callCurSetupData;// 开始拨打电话的时间点(系统时间)
	public static long callCurAlerting;// 第一声嘟的时间点(系统时间)
	public static long callCurConnect;// 接通时间点(系统时间)
	// public static long callDate;// 拨打电话按钮按下的时间点(系统时间)
	public static long backDelayCurTime;// 回落时间点(系统时间)

	public static int callstate = 0;
	/*
	 * 更新模块
	 */
	public static boolean AUTO_CHECK = false; // 自动检查更新
	public static boolean isUpdating = false;
	public static boolean MODIFY_UPDATE = false;

	// attach测试使用
	public volatile static boolean isAttachTestBegin = false;
	// public static volatile Object mAttachObject = new Object();
	/*
	 * 呼入号码
	 */
	public static String callPhoneNum = null;

	public static String currentTestSiteKeyId = "";// 当前在测试的任务的sitekeyId

	public static int testItemSet = -99;

	public static int testItemType = -99;

	public static long fileSize = 0;

	public static long threadNum = 0;

	public static double avgSpeed = 0.00;

	public static double sumSpeed = 0.00;

	public static double maxSpeed = 0;

	public static double progress = 0d;

	public volatile static int l1L2Cent = 0;
	public volatile static int l3Cent = 0;
	public volatile static int eventCent = 0;
	public volatile static int nerCent = 0;
	
	public static boolean isStopCurrTest = false;

}
