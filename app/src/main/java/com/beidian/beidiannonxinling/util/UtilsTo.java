package com.beidian.beidiannonxinling.util;



import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsTo {

	/**
	 * 计算下载速度是mb/s 还是kb/s
	 * 
	 * @author henry.liu
	 * @param speed
	 * @return
	 */
	public static String downLoadSpeed(double speed) {
		String downLoad;
		if (speed > 1024) {
			downLoad = UtilsTo.getFloatNumber((speed / (float) 1024), 2) + "Mbps";
			return downLoad;
		} else {
			downLoad = UtilsTo.getFloatNumber(speed, 2) + "kbps";
			return downLoad;
		}

	}

	/**
	 * 精确到小数的某一位
	 * 
	 * @author henry.liu
	 * 
	 * @param f
	 *            传入的数值
	 * @param num
	 *            小数点后的位数
	 * @return
	 */
	public static float getFloatNumber(float f, int num) {
		BigDecimal bd = new BigDecimal(f);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	/**
	 * 
	 * 精确到小数的某一位
	 * 
	 * @param d
	 *            传入的数值
	 * @param num
	 *            小数点后的位数
	 * @return
	 */
	public static Double getFloatNumber(Double d, int num) {
		BigDecimal bd = new BigDecimal(d);
		BigDecimal bd1 = bd.setScale(num, BigDecimal.ROUND_HALF_UP);
		return bd1.doubleValue();
	}

	/**
	 * 按四舍5入处理数值精确到小树点后指定的位数
	 * 
	 * @param value
	 *            处理的数值scale设置要精确的小数点后的位数
	 * @param
	 * @return
	 */
	public static Double getSaveValueBy(Double value, int scale) {
		Double valueInt = null;
		if (value != null) {
			// int roundingMode = 4;// 表示四舍五入
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
			valueInt = bd.doubleValue();
		}
		return valueInt;
	}

	/**
	 * 按四舍5入处理数值精确到小树点后指定的位数
	 * 
	 * @param value
	 *            处理的数值
	 * @param scale
	 *            设置要精确的小数点后的位数
	 * @return
	 */
	public static Float getSaveValueBy(Float value, int scale) {
		Float valueInt = null;
		if (value != null) {
			int roundingMode = 4;// 表示四舍五入
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(scale, roundingMode);
			valueInt = bd.floatValue();
		}
		return valueInt;
	}

	/**
	 * 将double类型转换为String类型
	 * 
	 * @param bd
	 * @return
	 */
	public static String getStringSaveValue(double bd) {
		BigDecimal bg = new BigDecimal(bd);
		return bg.setScale(6, RoundingMode.HALF_DOWN).toString();
	}

	// public static String getStringSaveValue(double bd) {
	// BigDecimal bg = new BigDecimal(bd);
	// return bg.setScale(6, RoundingMode.HALF_DOWN).toString();
	// }


	/**
	 * 下载运营商基站
	 * 
	 */
	// public static void getAllStationDataFromServer(final Context context) {
	// // "6ce9185adf2a446fafe23dd46c4ad65b"
	// long stationDatabaseDownloadTime = SPUtils
	// .getStationDatabaseDownloadTime(context);
	// String url = com.newtouch.beidiantest.utils.HttpUtils.setUrl(context,
	// context.getString(R.string.url_station_database_download),
	// SPUtils.get(context, Const.SP_PHONE_NUMBER, "").toString(),
	// Globle.downloadOfficeId,
	// String.valueOf(stationDatabaseDownloadTime),
	// PhoneUtil.getImei(context));
	// HttpUtils httpUtils = new HttpUtils();
	// httpUtils.configTimeout(60000);
	// httpUtils.configSoTimeout(60000);
	// httpUtils.download(HttpMethod.GET, url,
	// Const.DOWNLOAD_STATION_DATABASE_PATH + "station.zip", null,
	// true, false, new RequestCallBack<File>() {
	//
	// @Override
	// public void onLoading(long total, long current,
	// boolean isUploading) {
	// if (total > current) {
	// double percent = (double) current / (double) total;
	// Globle.downloadStatu = Double.toString(UtilsTo
	// .getSaveValueBy(percent, 1));
	// }
	// super.onLoading(total, current, isUploading);
	// }
	//
	// @Override
	// public void onStart() {
	// CommonUtils.showProgressDialog(context,
	// context.getString(R.string.please_hold), false);
	//
	// }
	//
	// @Override
	// public void onFailure(HttpException e, String msg) {
	// T.showLong(context,
	// context.getString(R.string.net_request_fail));
	// CommonUtils.closeProgressDialog(context);
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<File> responseInfo) {
	// if (responseInfo.result.exists()
	// && responseInfo.result != null) {
	// SPUtils.setStationDatabaseDownloadUpdateTime(
	// context, System.currentTimeMillis());
	// // 从responseInfo.result拿到下载的zip文件，并且解压缩
	// try {
	// FileUtil.unZipFile(responseInfo.result,
	// Const.DOWNLOAD_STATION_DATABASE_PATH);
	// } catch (ZipException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// File station = new File(
	// Const.DOWNLOAD_STATION_DATABASE_PATH,
	// "station.txt");
	// // 从解压缩的文件读取Json字符串
	// final String stationText = CommonUtils
	// .fileToString(station);
	// // 得到要解析的Json字符串后把解压的文件和压缩的文件都删除掉
	// if (FileUtils
	// .isFileExist(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.txt")) {
	// try {
	// FileUtils
	// .del(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.txt");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// if (FileUtils
	// .isFileExist(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.zip")) {
	// try {
	// FileUtils
	// .del(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.zip");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// Looper.prepare();
	// dealJsonDataObject(stationText);
	// Looper.loop();
	// }
	// }).start();
	// } else {
	// if (FileUtils
	// .isFileExist(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.zip")) {
	// try {
	// FileUtils
	// .del(Const.DOWNLOAD_STATION_DATABASE_PATH
	// + "station.zip");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// T.showShort(context, "没有可下载的数据");
	// CommonUtils.closeProgressDialog(context);
	// }
	//
	// }
	//
	// private synchronized void dealJsonDataObject(
	// String stationText) {
	// // 解析Json数据成相应的对象，存到数据库
	// try {
	// DbUtil.deleteAllBasicCellInfo(context);
	// JSONArray currSiteCellJsonArray = new JSONArray(
	// stationText);
	// List<BasicCellInfo> cells = new ArrayList<BasicCellInfo>();
	// for (int i = 0; i < currSiteCellJsonArray.length(); i++) {
	// BasicCellInfo basicCellInfo = new Gson()
	// .fromJson(currSiteCellJsonArray
	// .getJSONObject(i).toString(),
	// BasicCellInfo.class);
	// cells.add(basicCellInfo);
	// }
	// DbUtil.createOrUpdateBasicCellInfoOld(context,
	// cells);
	// CommonUtils.closeProgressDialog(context);
	// } catch (Exception e) {
	// e.printStackTrace();
	// CommonUtils.closeProgressDialog(context);
	// }
	// }
	// });
	// }



	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 校验手机格式是否正确
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * log写入文件
	 * 
	 * @param file
	 * @param conent
	 */
	public static void method1(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
