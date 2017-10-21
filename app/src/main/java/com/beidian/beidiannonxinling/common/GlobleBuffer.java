package com.beidian.beidiannonxinling.common;

import java.util.ArrayList;
import java.util.List;

public class GlobleBuffer {
	/**
	 * 用来存放邻区列表的信息
	 */
//	public static List<NeiborCellStructureShow> neiborCellList = new ArrayList<NeiborCellStructureShow>();

	/**
	 * 用来存放测试数据信息
	 */
//	public static BasicCurrResultCell basicCurrResultCell;

	/**
	 * 用来存放服务区信息
	 */

//	public static NeiborCellStructureShow serverCellInfo = new NeiborCellStructureShow();

	/**
	 * 用来存放写入文件时，L3数据的缓冲
	 */

//	public static List<L3Info> l3ListInfo = new ArrayList<L3Info>();
	/*
	 * attach test queue
	 */
//	public static BlockingQueue<L3Info> attachInfoQueue = new ArrayBlockingQueue<L3Info>(100);

	/**
	 * 用来存放写入文件时，L3临时数据的缓冲
	 */
//	public static List<L3Info> l3TempListInfo = new ArrayList<L3Info>();

	/**
	 * 用来存放写入文件时，邻区数据的缓冲
	 */
//	public static List<NeiborInfo> neiborListInfo = new ArrayList<NeiborInfo>();

	/**
	 * 用来存放写入文件时，邻区数据的缓冲
	 */
//	public static List<NeiborInfo> neiborTempListInfo = new ArrayList<NeiborInfo>();


//	public static CopyOnWriteArrayList<BasicCurrResultCell> l1l2DataList = new CopyOnWriteArrayList<BasicCurrResultCell>();

//	public static BlockingQueue<TestResult> testResultQueue = new ArrayBlockingQueue<TestResult>(1000);

	/**
	 * 事件标志队列切换成功1， 切换失败0
	 */
//	public static BlockingQueue<Byte> eventsQueue = new ArrayBlockingQueue<Byte>(100);

	/**
	 * 事件列表
	 */
//	public static List<SignalEvent> signalEventInfos = new ArrayList<SignalEvent>();

	/**
	 * 事件零时列表
	 */
//	public static List<SignalEvent> signalEventsTempInfos = new ArrayList<SignalEvent>();

	public static String eventName = null;

	public static List<String> l3JsonDataqueue = new ArrayList<String>(100);
	public static List<String> l1l2JsonDataqueue = new ArrayList<String>(100);
	public static List<String> neiborJsonDataqueue = new ArrayList<String>(100);
	public static List<String> eventJsonDataqueue = new ArrayList<String>(100);

//	public static LatLng testLatLng = null;

	/**
	 * 存放gis的点
	 */
//	public static BlockingQueue<GisDrawPointDataInfo> gisPoints = new ArrayBlockingQueue<GisDrawPointDataInfo>(100);


}
