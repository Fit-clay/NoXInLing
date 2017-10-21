package com.beidian.beidiannonxinling.test;

import android.content.Context;

import com.beidian.beidiannonxinling.bean.PingResul;
import com.beidian.beidiannonxinling.util.NonSignaLlingTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class PingTest {
	private Integer   timesTransfor;// 发送次数
	private Integer   timesReceiver;// 接收次数
	private String    rateOfSuccess;// 成功率
	private String    delay;// 延时
	public String    isSuccess;//是否成功
	private PingResul result;//

	public boolean isEnd;// ping测试是否结束标志变量

	/*
	 * ping测试
	 */
	public PingResul ping(String url, String count, Context context) {

		try {
			InetAddress inetAddress = InetAddress.getByName(url.trim());//獲取ip
//			Log.d("ping", "inetAddress: "+inetAddress.getHostAddress());
			isEnd = false;
			String lost = new String();
			Process p;
			try {
				p = Runtime.getRuntime()
						.exec("ping -c " + count.trim() + " -i 0.2 -w 2 " + inetAddress.getHostAddress());
				int status = p.waitFor();
				InputStream input = p.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(input));
				String line = "";
				while ((line = in.readLine()) != null) {

					if (line.contains("packet loss")) {
						int l = line.indexOf("loss");//
						int i = line.indexOf("received");//
						if (!line.substring(0, 2).trim().equals("0")) {
							timesTransfor = Integer.parseInt(line.substring(0, 2).trim());
						} else {
							timesTransfor = 0;
						}
						if (!line.substring(i - 3, i - 1).trim().equals("0")) {
							timesReceiver = Integer.parseInt(line.substring(i - 3, i - 1).trim());
						} else {
							timesTransfor = 0;
						}
						lost = line.substring(l - 12, l - 9);
						if (lost.contains(",")) {
							lost = line.substring(l - 11, l - 9);
						}
						double lost_temp = 100 - Double.parseDouble(lost);
						rateOfSuccess = String.valueOf(lost_temp);
					}
					if (line.contains("avg")) {
						int i = line.indexOf("/", 20);
						int j = line.indexOf(".", i);
						delay = line.substring(i + 1, j + 4);
						int delay_temp = (int) (Double.parseDouble(delay));
						delay = String.valueOf(delay_temp);
					}

					if (rateOfSuccess != null) {
						if (Double.parseDouble(rateOfSuccess) > 0) {
							isSuccess = "成功";
						} else {
							isSuccess = "失败";
						}
					} else {
						isSuccess = "失败";
					}
					result = new PingResul(new NonSignaLlingTools(context).getNetworkType(), url.trim(), timesTransfor,
							timesReceiver, rateOfSuccess, delay, isSuccess);

				}

			} catch (IOException e) {
				isSuccess = "失败";
				e.printStackTrace();
			} catch (InterruptedException e) {
				isSuccess = "失败";
				e.printStackTrace();
			}
			isEnd = true;

		} catch (UnknownHostException e1) {
			isSuccess = "失败";
			/*
			 * 主机地址未知异常
			 */
			e1.printStackTrace();
		}
		return result;
	}

	public boolean isEnd() {
		return isEnd;
	}

	/*
	 * 获取ping测试结果
	 */
	public PingResul getPingResult(String url, String count, Context context) {
		ping(url, count, context);
		long startTime = new Date().getTime();
		while (!isEnd) {
			long currentTime = new Date().getTime();
			if (currentTime - startTime > 20000) {
				break;
			}
			// Log.i("isEnd:", "" + isEnd + " " + (currentTime - startTime));
		}
		if (result == null) {
			result = new PingResul();
			result.setResult("失败");
			return result;
		} else {
			return result;
		}
	}
}
