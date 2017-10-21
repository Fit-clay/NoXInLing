package com.beidian.beidiannonxinling.bean;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/218:04
 * @描述: ------------------------------------------
 */

public class FtpInfo {
    // 连接配置文件
    public FTPClient ftpClient   = new FTPClient();
    public String    ftpHost     = "139.196.168.28";
    public int       ftpPort     = 21;
    public String    ftpUserName = "ftpuser";
    public String    ftpPassword = "Test123";
    public String remote; // 远程文件
    public String local; // 本地文件
    public long   fileSize;  //记录已经获取的字节数
    public long   mFileSize;
    //是否终止ftp下载
    public boolean isStop = false;

    public void stop() {
        isStop = true;
    }

}
