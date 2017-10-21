package com.beidian.beidiannonxinling.bean;

/**
 * ftp配置
 */
public class FtpConfig {
	/*
	 * ftp 内网：192.168.6.18 外网:139.196.168.28 username:ftpuser pwd:Test123
	 * port:21
	 */
	private String url = "139.196.168.28";// FTPhostname
	private int port = 21; // FTP
	private String username = "ftpuser"; //
	private String password = "Test123"; //
	private String remotePath = "/"; //
	private String remoteFileName = "50M.rar"; //
	private String localPath = "mnt/shell/emulated/0/";

	public FtpConfig(String url, int port, String userName, String password,
					 String fileName ,String localPath) {
		this.url = url;
		this.port = port;
		this.username = userName;
		this.password = password;
		this.remoteFileName = fileName;
		this.localPath = localPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath1(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getFileName() {
		return remoteFileName;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public void setFileName(String fileName) {

		this.remoteFileName = fileName;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
}
