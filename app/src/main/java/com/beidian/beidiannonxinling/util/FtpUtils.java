package com.beidian.beidiannonxinling.util;

import android.content.Context;
import android.util.Log;

import com.beidian.beidiannonxinling.bean.FtpConfig;
import com.beidian.beidiannonxinling.bean.FtpTestResult;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FtpUtils {
    /*
     * ftp下载
     */
    public boolean download(FtpConfig ftpConfig,
                            FtpTestResult ftpTestResult, Context context) throws IOException {
        List<Double> downloadSpeeds = new ArrayList<Double>();
        String url = ftpConfig.getUrl(); // 服务器
        int port = ftpConfig.getPort(); // 端口
        String username = ftpConfig.getUsername(); // FTP user
        String password = ftpConfig.getPassword(); // FTP pwd
        String remotePath = ftpConfig.getRemotePath(); // FTP服务器路径
        String fileName = ftpConfig.getFileName(); // 文件名
        long fileLength;// 文件长度
        int currentByte = 0;// 当前下载量
        long lastFinished = 0;// 上次完成量
        ftpTestResult.hadFinished = 0L;
        ftpTestResult.speed = (double) 0;
        /*
         * 创建ftp上传的文件
		 */
        // String ftpUpUrl = Const.UPLOAD_FILE_PATH +
        // Const.FTP_UPLOAD_FLIE_NAME;
        // if (FileUtils.isFileExist(ftpUpUrl))
        // FileUtils.del(ftpUpUrl);
        // File upLoadFile = new File(Const.UPLOAD_FILE_PATH,
        // Const.FTP_UPLOAD_FLIE_NAME);
        // upLoadFile.setExecutable(true);
        // upLoadFile.setReadable(true);
        // upLoadFile.setWritable(true);
        ftpTestResult.status = "开始";
        //获取网络状况
        ftpTestResult.netType = new NonSignaLlingTools(context).getNetworkType();
        boolean success = false;
        FTPClient ftp = new FTPClient();// ftp客户端
        int reply;// 服务器返回码
        ftp.connect(url, port);// 连接服务器
        ftp.login(username, password);// 登陆
		/*
		 * 获取服务器返回码
		 */
        reply = ftp.getReplyCode();
		/*
		 * FTPReply.isPositiveCompletion(reply)true代表连接正常
		 */
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpTestResult.status = "断开连接";
            ftp.disconnect();// 断开连接
            return success;
        }

        ftp.changeWorkingDirectory(remotePath);// 改变路径
        FTPFile[] fs = ftp.listFiles();
        for (FTPFile ff : fs) {
            //            Log.d(TAG, "FTPFile.getName: "+ff.getName());
            //判断要下载的文件名和服务器的文件名是否匹配
            if (ff.getName().equals(fileName)) {
                //获取文件大小
                ftpTestResult.fileLength = UtilsTo.getFloatNumber(
                        ff.getSize() / (double) (1024 * 1024), 2);
                fileLength = ff.getSize();
                BufferedInputStream bis = new BufferedInputStream(
                        ftp.retrieveFileStream(ff.getName()));
                long startTime = System.currentTimeMillis();
                long lastTime = startTime;
                long currentTime = 0;
                byte b[] = new byte[1024 * 1024 * 10];
                while (((currentByte = bis.read(b, 0, b.length)) != -1)
                        ) {
                    //已经上传文件大小
                    ftpTestResult.hadFinished += currentByte;
                    currentTime = System.currentTimeMillis();
                    long hadFinished = ftpTestResult.hadFinished;
                    //如果时间大于500毫秒就计算一次上传速度
                    if (currentTime - lastTime > 0) {
                        ftpTestResult.speed = (((hadFinished - lastFinished) / (currentTime - lastTime)) * 1000 / 1024d / 1024d);

                        downloadSpeeds.add(ftpTestResult.speed);
                        //上次完成量=下载或者上传已完成量
                        lastFinished = hadFinished;
                        //上次时间=当前时间
                        lastTime = currentTime;
                        //计算进度
                        ftpTestResult.progress = (int) (ftpTestResult.hadFinished
                                / (double) fileLength * 100);
                        //打印数据
                                                Log.d(TAG, "download: "+ftpTestResult.toString());
                    }
                    // FileUtil.writeContent2file(upLoadFile, b);
                    //计算下载用时间
                    ftpTestResult.totalTime = (int) ((System
                            .currentTimeMillis() - startTime) / 1000);// 下载用时

                }
                //设置进度为1000
                ftpTestResult.progress = 100;
                //计算平均速度
                int sum = 0;
                for (Double doubleV : downloadSpeeds) {
                    sum += doubleV;
                }
                double avgSpeed = (int) (sum / (double) (downloadSpeeds
                        .size()));// 计算平均速度
                // ftpTestResult.avgSpeed = avgSpeed;
                //                LogUtils.d("平均速度:" + avgSpeed);
                ftpTestResult.avgSpeed = avgSpeed;
                double shake = avgSpeed * 0.5;// 计算抖动次数偏差量
                int shakeCount = 0;
                for (Double doubleV : downloadSpeeds) {
                    if (Math.abs(avgSpeed - doubleV) > shake) {
                        shakeCount++;
                    }
                }
                ftpTestResult.shakeCount = shakeCount;
                ftpTestResult.status = "下载完成";
                bis.close();
            }

        }
        ftp.completePendingCommand();
        success = true;
        if (ftp.isConnected()) {
            ftp.disconnect();
        }
        //打印数据
                Log.d(TAG, "download: "+ftpTestResult.toString());

        // ftp.logout();
        return success;
    }

    /*
     * ftp上传
     */
    public boolean uploadFile(FtpConfig ftpConfig,
                              FtpTestResult ftpTestResult, Context context, String filePath)
            throws IOException {
        // String[] aa = filePath.split(File.separator);
        // int len = aa.length;
        // String fileName = aa[len - 1];
        // File file = new File(Const.UPLOAD_FILE_PATH
        // + Const.FTP_UPLOAD_FLIE_NAME);
        // String uploadPath = Const.UPLOAD_FILE_PATH +
        // Const.FTP_UPLOAD_FLIE_NAME;
        // try {
        // if (FileUtils.isFileExist(uploadPath))
        // FileUtils.del(uploadPath);
        // FileUtils.copyFile(new File(filePath), new File(uploadPath));
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        // String uploadfilePath;
        // T.showShort(context,
        // FileUtil.getRealFilePath(context, Uri.parse(uploadFilePath)));
		/*
		 * 上传文件的路劲
		 */
        // if (filePath == null) {
        // uploadfilePath = Const.UPLOAD_FILE_PATH
        // + Const.FTP_UPLOAD_FLIE_NAME;
        // } else {
        // uploadfilePath = filePath;

        // }

        String url = ftpConfig.getUrl(); // 主机地址
        int port = ftpConfig.getPort();// 端口
        String username = ftpConfig.getUsername(); // 用户名
        String password = ftpConfig.getPassword(); // 密码
        String path = ftpConfig.getRemotePath(); // ftp服务器保存目录
        List<Double> uploadSpeed = new ArrayList<Double>();
        boolean success = false;
        long fileLength = 0;// 文件长度
        ftpTestResult.status = "开始";
        ftpTestResult.netType = new NonSignaLlingTools(context).getNetworkType();
        ftpTestResult.hadFinished = 0L;
        ftpTestResult.speed = (double) 0;
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
            ftpTestResult.status = "结束";
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

            ftpTestResult.fileLength = UtilsTo.getFloatNumber(tempFile.length()
                    / (double) (1024 * 1024), 2);
            fileLength = tempFile.length();
            int count = 0;
            long lastFinished = 0;
            byte b[] = new byte[20480];
            long startTime = System.currentTimeMillis();
            long lastTime = startTime;
            long currentTime = 0;

            while (((count = fis.read(b, 0, b.length)) != -1)) {
                if (os != null) {
                    os.write(b);
                    ftpTestResult.hadFinished += count;
                    currentTime = System.currentTimeMillis();
                    if (currentTime - lastTime > 1000) {
                        ftpTestResult.speed = 8 * ((ftpTestResult.hadFinished - lastFinished)
                                / (currentTime - lastTime) * 1000 / 1024d);
                        uploadSpeed.add(ftpTestResult.speed);
                        ftpTestResult.progress = (int) ((ftpTestResult.hadFinished / (double) fileLength) * 100);
                        lastFinished = ftpTestResult.hadFinished;
                        lastTime = System.currentTimeMillis();
                        ftpTestResult.totalTime = (int) ((System
                                .currentTimeMillis() - startTime) / 1000);// 上传用时
                        //打印上传
                        //                        Log.d(TAG, "uploadFile: "+ftpTestResult.toString());
                    }
                }
            }

            double sum = 0;
            for (Double doubleV : uploadSpeed) {
                sum += doubleV;
            }
            double avgSpeed = (int) (sum / ((double) (uploadSpeed.size())));
            ftpTestResult.avgSpeed = avgSpeed;
            // ftpTestResult.speed = avgSpeed;
            double shake = avgSpeed * 0.5;
            int shakeCount = 0;// 抖动次数
            for (Double doubleV : uploadSpeed) {
                if (Math.abs(avgSpeed - doubleV) > shake) {
                    shakeCount++;
                }
            }
            ftpTestResult.shakeCount = shakeCount;
            ftpTestResult.status = "结束";
            ftpTestResult.progress = 100;
            // ftp.logout();
            success = true;
            os.close();
            fis.close();
        } else {
            success = false;
        }
        // }
        if (ftp.isConnected()) {
            ftp.disconnect();
        }
        // // 只有FTP默认模板状态下删除已上传文件
        // if (filePath == null) {
        // if (FileUtils.isFileExist(uploadfilePath))
        // FileUtils.del(uploadfilePath);
        // }
        return success;
    }


}
