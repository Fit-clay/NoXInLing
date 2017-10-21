package com.beidian.beidiannonxinling.test;

import android.content.Context;

import com.beidian.beidiannonxinling.bean.HttpResult;
import com.beidian.beidiannonxinling.util.NonSignaLlingTools;
import com.beidian.beidiannonxinling.util.UtilsTo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * http测试类
 */
public class HttpTest {
	/*
	 * http测试
	 */
	public HttpResult doHttpTest(Context context,
								 HttpResult httpTestResult, String url) throws IOException {
		//获取当前时间
		long startTime = System.currentTimeMillis();
		//初始化状态
		httpTestResult.status = "begin";
		//获取网路制式2g，3g，4g
		httpTestResult.netType = new NonSignaLlingTools(context).getNetworkType();
		//获取http客户端
		HttpClient httpClient = new DefaultHttpClient();
		//设置请求参数
		httpClient.getParams()
				.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);// 请求超时时间
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				10000);// 读取超时时间
		//http请求前缀
		if (!url.contains("http://")) {
			url = "http://" + url;
		}
		httpTestResult.target = url;
		HttpGet httpGet = new HttpGet(url);
		//获取请求的数据
		HttpResponse httpResponse = httpClient.execute(httpGet);
		//获取状态吗
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK) {// 响应正常
			//获取服务器的响应内容
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream is = httpEntity.getContent();

			//获取数据大小
			long size = httpEntity.getContentLength();
			if (size < 0) {
				if (url.contains("www.10086.cn")) {
					size = 53247;
				} else if (url.contains("www.baidu.com")) {
					size = 99150;
				} else if (url.contains("www.qq.com")) {
					size = 658480;
				} else if (url.contains("www.youku.com")) {
					size = 647007;
				} else {
					size = 204800;
				}
			}


			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer sb = new StringBuffer();
			int sbLen = sb.length();
			if (sbLen != 0)
				sb.delete(0, sbLen);
			//获取系统时间--开始加载时间
			long startLoadTime = System.currentTimeMillis();
			int currentByte = 0;
			while ((line = br.readLine()) != null ) {
				// if (line.contains("href=\"")) {
				// LogUtils.d("url:", line);
				// }
				//计算获取的字节数,计算进度
				currentByte += (line.getBytes().length + 1);
				httpTestResult.progress = (int) (currentByte / ((double) size)
						* 100);
				sb.append(line);
			}


			httpTestResult.progress = 100;
			httpTestResult.size = UtilsTo
					.getFloatNumber((currentByte - 1) / (float) 1024, 2);// 总量
			long endTime = System.currentTimeMillis();
			byte[] b = sb.toString().getBytes();

			float time = (endTime - startLoadTime) / (float) 1000;// 时间
			float feelTime = (endTime - startTime) / (float) 1000;// 感知时间
			httpTestResult.costTime = UtilsTo.getFloatNumber(feelTime, 2);
//			httpTestResult.feelTime = UtilsTo.getFloatNumber(feelTime, 2);
			// LogUtils.d("size:" + currentByte);
			// LogUtils.d("content size:" + size);
			float speed = b.length / ((float) (1024 * feelTime)) * 8;// 速率

			try {
//				Log.d(TAG, "speed: "+speed);
				httpTestResult.speed = UtilsTo.getFloatNumber(speed, 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			httpTestResult.status = "finish";
			// LogUtils.d("速率:" + speed + "kbps");
			br.close();
			is.close();

		} else {
			// 响应不正常
			httpTestResult.status = "fail";

		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return httpTestResult;
	}
}
