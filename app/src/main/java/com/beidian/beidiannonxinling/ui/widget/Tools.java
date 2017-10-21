package com.beidian.beidiannonxinling.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Tools {

	private static ProgressDialog progressDialog;

	/**
	 * 隐藏键盘
	 * 
	 * @param context
	 * @param view
	 *            view为接受软键盘输入的视图
	 */
	public static void hidenKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 强制隐藏键盘
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 打开键盘
	 * 
	 * @param context
	 */
	public static void openKeyBoard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 打开加载框
	 * 
	 * @param activity
	 */
	@SuppressLint("InflateParams")
	public static void showProgressDialog(Activity activity, String msg) {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(activity)
				.inflate(R.layout.activity_progress_dialog, null);
		TextView tvMsg = (TextView) layout.findViewById(R.id.tv_msg);
		tvMsg.setText(msg);
		// if (progressDialog == null || oldContextName == null
		// || !(nowContextName.equals(oldContextName))) {
		progressDialog = new ProgressDialog(activity, layout);
		if(msg.equals(activity.getString(R.string.locating))){
			progressDialog.setCancelable(true);
		}else{
			progressDialog.setCancelable(false);
		}
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		Window dialogWindow = progressDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = (int) (activity.getWindow().getWindowManager().getDefaultDisplay().getWidth() * 0.4); // 设置宽度
		lp.height = lp.width;
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 关闭加载框
	 * 
	 * @param context
	 */
	public static void closeProgressDialog(Context context) {
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	/**
	 * Date转String
	 * 
	 * @param date
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String date2String(Date date, String format) {
		// "format yyyy-MM-dd HH:mm"
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * String转Date
	 * 
	 * @param string
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date string2Date(String string, String format) {
		try {
			return new SimpleDateFormat(format).parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 填充字符串
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static String formatText(String format, Object... args) {
		return MessageFormat.format(format, args);
	}

	/**
	 * 格式化double
	 * 
	 * @param num
	 * @return
	 */
	public static String formatDouble(Object num) {
		DecimalFormat df = new DecimalFormat("0.0");
		try {
			return df.format(num);
		} catch (Exception e) {
			return "0";
		}

	}
	
	/**
	 * 格式化double,保留2位有效数字
	 * 
	 * @param num
	 * @return
	 */
	public static  double formatDouble2(double num) {
		DecimalFormat df = new DecimalFormat("0.00");
		try {
			String valueStr=df.format(num);
			double value= Double.valueOf(valueStr);
			return value;
		} catch (Exception e) {
			return 0.00;
		}
	}

	/**
	 * 格式化double,保留4位有效数字
	 * 
	 * @param num
	 * @return
	 */
	public static String formatDouble4(Object num) {
		DecimalFormat df = new DecimalFormat("0.0000");
		try {
			return df.format(num);
		} catch (Exception e) {
			return "0";
		}

	}

	/**
	 * 格式化double,保留6位有效数字
	 * 
	 * @param num
	 * @return
	 */
	public static String formatDouble6(Object num) {
		DecimalFormat df = new DecimalFormat("0.000000");
		try {
			return df.format(num);
		} catch (Exception e) {
			return "0";
		}

	}

	/**
	 * 格式化double,返回整数
	 * 
	 * @param num
	 * @return
	 */
	public static String formatDoubleNoDig(Object num) {
		DecimalFormat df = new DecimalFormat("0");
		try {
			return df.format(num);
		} catch (Exception e) {
			return "0";
		}

	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获取今天的日期
	 * 
	 * @param context
	 * @return
	 */
	public static Date getTodayDate(Context context) {
		return new Date(System.currentTimeMillis());// 获取当前时间
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * Get application version.
	 * 
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static Integer getVersionCode(Context context)
			throws NameNotFoundException {
		// Retrieve packagemanager
		PackageManager packageManager = context.getPackageManager();
		// Retrieve package information
		PackageInfo packageInfo = packageManager
				.getPackageInfo(context.getPackageName(), 0);
		Integer versionCode = Integer.valueOf(packageInfo.versionCode);
		return versionCode;
	}

	public static HashMap<String, String> parseUpdateXml(InputStream inStream)
			throws Exception {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inStream);
		Element root = document.getDocumentElement();
		NodeList childNodes = root.getElementsByTagName("data");
		for (int i = 0; i < childNodes.getLength(); i++) {
			Element ele = (Element) childNodes.item(i);
			NodeList eleNodeList = ele.getChildNodes();
			for (int j = 0; j < eleNodeList.getLength(); j++) {
				Node childNode = (Node) eleNodeList.item(j);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) childNode;
					if ("versioncode".equals(childElement.getNodeName())) {
						hashMap.put("versioncode",
								childElement.getFirstChild().getNodeValue());
					} else if ("name".equals(childElement.getNodeName())) {
						hashMap.put("name",
								childElement.getFirstChild().getNodeValue());
					} else if ("url".equals(childElement.getNodeName())) {
						hashMap.put("url",
								childElement.getFirstChild().getNodeValue());
					} else if ("detail".equals(childElement.getNodeName())) {
						hashMap.put("details",
								childElement.getFirstChild().getNodeValue());
					}
				}
			}
		}
		return hashMap;
	}

	/**
	 * 毫秒转date
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date string2DateFromSeconds(String string)
			throws ParseException {
		return new Date(Long.parseLong(string));

	}

	@SuppressWarnings("deprecation")
	public static boolean isTrainAppAtTop(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTasks = am.getRunningTasks(1);
		if (!runningTasks.isEmpty()) {
			ComponentName topActivity = runningTasks.get(0).topActivity;
			if (topActivity.getPackageName()
					.equals("com.newtouch.beidiansurvey")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public static Integer getViewHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();

	}

	/**
	 * 检查app是否具有某个权限,该方法只是检查在Manifest文件中是否申请了权限，如果被关闭了则检查不到
	 * 
	 * @param context
	 *            上下文环境
	 * @param permName
	 *            权限名称或者权限组名称
	 * @param pkgName
	 *            包名
	 * @return true|false true:该app具有该权限 false:该app不具有该权限
	 */
	public static boolean hasPermission(Context context, String permName,
										String pkgName) {
		PackageManager pmManager = context.getPackageManager();
		return PackageManager.PERMISSION_GRANTED == pmManager
				.checkPermission(permName, pkgName);
	}

	/**
	 * 跳转到该应用详情页面
	 * 
	 * @param context
	 *            上下文环境
	 */
	public static void switchToAppDetail(Context context) {
		getAppDetailSettingIntent(context);
	}

	public static void getAppDetailSettingIntent(Context context) {
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= 9) {
			localIntent
					.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			localIntent.setData(
					Uri.fromParts("package", context.getPackageName(), null));
		} else if (Build.VERSION.SDK_INT <= 8) {
			// localIntent.setAction(Intent.ACTION_VIEW);
			// localIntent.setClassName("com.android.settings",
			// "com.android.settings.InstalledAppDetails");
			// localIntent.putExtra("com.android.settings.ApplicationPkgName",
			// getPackageName());
		}
		context.startActivity(localIntent);
	}
	
	/**
	 * 压缩图片
	 * @param sourceFile
	 * 				源文件
	 * @param newpath
	 * 				目标文件
	 * @return
	 */
	public static String saveBitmapToFile(File sourceFile, String newpath) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image
 
            FileInputStream inputStream = new FileInputStream(sourceFile);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
 
            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;
 
            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(sourceFile);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
 
            // here i override the original image file
//            file.createNewFile();
//
//
//            FileOutputStream outputStream = new FileOutputStream(file);
//
//            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);
 
            File aa = new File(newpath);
            FileOutputStream outputStream = new FileOutputStream(aa);
            
            //choose another format if PNG doesn't suit you
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            String filepath = aa.getAbsolutePath();
            return filepath;
        } catch (Exception e) {
            return null;
        }
    }

	/**
	 * 压缩图片
	 * @param picPath
	 * @param reqWidth
	 * @param reqHeight
	 */
	public static void scalePic(String picPath, int reqWidth, int reqHeight){
		BitmapFactory.Options option=new BitmapFactory.Options();
		option.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(picPath, option);
		final int width=option.outHeight;
		final int height=option.outWidth;
		int inSampleSize=1;
		if(reqWidth!=0 && reqHeight!=0){
			if (height > reqHeight || width > reqWidth) {
		        final int halfHeight = height / 2;
		        final int halfWidth = width / 2;
		        // 计算图片宽高缩小为要求大小时的最大inSampleSize值
		        while ((halfHeight / inSampleSize) > reqHeight
		                && (halfWidth / inSampleSize) > reqWidth) {
		            inSampleSize *= 2;
		        }
		    }
		}else{
			return;
		}
//		if(inSampleSize==1){
//			return;
//		}
		option.inSampleSize=inSampleSize;
		option.inJustDecodeBounds=false;
		Bitmap picBitmap= BitmapFactory.decodeFile(picPath, option);
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(picPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		picBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
		try {
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		picBitmap.recycle();
		picBitmap=null;
	}
	

}
