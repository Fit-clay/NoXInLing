package com.beidian.beidiannonxinling.test;

import android.util.Log;

import com.beidian.beidiannonxinling.bean.FtpConfig;
import com.beidian.beidiannonxinling.bean.FtpInfo;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2816:36
 * @描述: ------------------------------------------
 */

public class Up extends FtpInfo {



    /**
     * @param ftpconfig  ftp 配置信息
     */
    public Up(FtpConfig ftpconfig) {
        this.remote = ftpconfig.getRemotePath() + ftpconfig.getFileName();
        this.ftpHost = ftpconfig.getUrl();
        this.ftpPort = ftpconfig.getPort();
        this.ftpUserName = ftpconfig.getUsername();
        this.ftpPassword = ftpconfig.getPassword();
        this.local = ftpconfig.getLocalPath();
    }

    /*
         * ftp上传
         */
    public boolean uploadFile(
            String filePath, long sartPot, long endPot)
            throws IOException {


        String url = ftpHost; // 主机地址
        int port = ftpPort;// 端口
        String username = ftpUserName; // 用户名
        String password = ftpPassword; // 密码
        String path = remote; // ftp服务器保存目录
        List<Double> uploadSpeed = new ArrayList<Double>();
        boolean success = false;
        long fileLength = 0;// 文件长度

		/*
         * 创建指定大小的空文件,用于上传
		 */
        File tempFile = new File(filePath);
        FileInputStream fis = new FileInputStream(tempFile);

        FTPClient ftp = new FTPClient();
        int reply;
        ftp.connect(url, port);
        boolean flag = ftp.login(username, password);
        /*
         * 获取服务器返回码
		 */
        reply = ftp.getReplyCode();
		/*
		 * FTPReply.isPositiveCompletion(reply)true代表连接正常
		 */
        if (!FTPReply.isPositiveCompletion(reply)) {
			/*
			 * 断开socket连接
			 */
            ftp.disconnect();
            fis.close();
            return success;
        }

        ftp.changeWorkingDirectory("/upload");
        try {
            ftp.deleteFile("ftpUpload");
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputStream os = null;
        try {
            os = ftp.storeFileStream("ftpUpload");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (os != null) {

            //            ftpTestResult.fileLength = UtilsTo.getFloatNumber(tempFile.length()
            //                    / (double) (1024 * 1024), 2);
            fileLength = tempFile.length();
            int count = 0;
            long lastFinished = 0;
            byte b[] = new byte[1024];
            long startTime = System.currentTimeMillis();
            long lastTime = startTime;
            long currentTime = 0;

            while (((count = fis.read(b, 0, b.length)) != -1)) {
                count += b.length;
                if (os != null) {
                    os.write(b);
                    fileSize += count;
                }
                //当上传文件大于需要上传的总数量的时候就终止
                if (count > endPot - sartPot) {
                    break;
                }
                //当当前测试管理类被stop的时候终止测试
                if (isStop) {
                    break;
                }
            }

            double sum = 0;
            for (Double doubleV : uploadSpeed) {
                sum += doubleV;
            }
            double avgSpeed = (int) (sum / ((double) (uploadSpeed.size())));
            Log.d(TAG, "avgSpeed: " + avgSpeed);
            //            double shake = avgSpeed * 0.5;
            //            int shakeCount = 0;// 抖动次数
            //            for (Double doubleV : uploadSpeed) {
            //                if (Math.abs(avgSpeed - doubleV) > shake) {
            //                    shakeCount++;
            //                }
            //            }
            success = true;
            os.close();
            fis.close();
        } else {
            success = false;
        }
        if (ftp.isConnected()) {
            ftp.disconnect();
        }
        return success;
    }


}
