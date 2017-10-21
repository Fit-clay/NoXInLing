package com.beidian.beidiannonxinling.test;

import com.beidian.beidiannonxinling.bean.FtpConfig;
import com.beidian.beidiannonxinling.bean.FtpInfo;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/8/2816:36
 * @描述: ------------------------------------------
 */

public class DownLoad extends FtpInfo {


    /**
     * @param ftpconfig ftp 配置信息
     */
    public DownLoad(FtpConfig ftpconfig) {
        this.remote = ftpconfig.getRemotePath() + ftpconfig.getFileName();
        this.ftpHost = ftpconfig.getUrl();
        this.ftpPort = ftpconfig.getPort();
        this.ftpUserName = ftpconfig.getUsername();
        this.ftpPassword = ftpconfig.getPassword();
        this.local = ftpconfig.getLocalPath();
    }


    //    	/** */
    //    	/**
    //    	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
    //    	 *
    //    	 * @param remote
    //    	 *            远程文件路径
    //    	 * @param local
    //    	 *            本地文件路径
    //    	 * @return 上传的状态
    //    	 * @throws IOException
    //    	 */
    public void download(long sartPot, long endPot) throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));
        if (files.length != 1) {
            System.out.println("远程文件不存在");
            return;
        }
        final long lRemoteSize = files[0].getSize();
        long l = lRemoteSize;
        //本地文件路径
        final File f = new File(local);
        if (!f.exists()) {
            File dir = new File(f.getParent());
            dir.mkdirs();
            f.createNewFile();
        }
        ftpRestartOffset(lRemoteSize, f, sartPot, endPot);
    }

    public long getFileSize(String remoteName) throws IOException {
        ftpClient.connect(ftpHost, ftpPort);
        ftpClient.setControlEncoding("GBK");
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            if (ftpClient.login(ftpUserName, ftpPassword)) {
                // 设置被动模式
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制方式传输
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                // DownloadStatus result;
                // 检查远程文件是否存在
                FTPFile[] files = ftpClient.listFiles(new String(remoteName.getBytes("GBK"), "iso-8859-1"));
                if (files.length != 1) {
                    System.out.println("远程文件不存在");
                    // return DownloadStatus.Remote_File_Noexist;
                    return 0;
                }
                long lRemoteSize = files[0].getSize();
                disconnect();
                return lRemoteSize;
            }
        }
        disconnect();
        return 0;
    }

    /**
     * @param lRemoteSize 远程文件大小
     * @param f           本地文件路径
     * @param sartPot     开始位置
     * @param endPot      结束位置
     * @throws IOException
     */
    private void ftpRestartOffset(long lRemoteSize, File f, long sartPot, long endPot) throws IOException {
        //已经获取的文件大小
        long size1 = 0;
        // 判断本地文件大小是否大于远程文件大小
        if (size1 >= lRemoteSize) {
            System.out.println("本地文件大于远程文件，下载中止");
        }
        // 进行断点续传，并记录状态
        FileOutputStream out = new FileOutputStream(f, true);
        ftpClient.setRestartOffset(sartPot);
        InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
        byte[] bytes = new byte[1024];
        int c = 0;
        //如果当前获取的字节数大于了需要获取的字节数则终止循环
        while (size1 <= endPot - sartPot && (c = in.read(bytes)) != -1) {
            //当当前测试管理类被stop的时候终止测试
            if (isStop) {
                break;
            }
            //            out.write(bytes, 0, c);
            size1 += c;
            //统计当前已经下载的数据总量
            fileSize = fileSize + c;
            //            Log.d(TAG, "ftpRestartOffset: " +fileSize+Thread.currentThread().getName());
        }
        in.close();
        out.close();
        //        boolean isDo = ftpClient.completePendingCommand();
    }

    public boolean connect() throws IOException {
        ftpClient.connect(ftpHost, ftpPort);
        ftpClient.setControlEncoding("GBK");
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            if (ftpClient.login(ftpUserName, ftpPassword)) {
                return true;
            }
        }
        disconnect();
        return false;
    }
    /** */
    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     */
    public void disconnect() {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
