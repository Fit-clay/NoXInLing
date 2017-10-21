package com.beidian.beidiannonxinling.util;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import QcomDiag.QcomDiag;

import static android.content.ContentValues.TAG;

public class FlightModeChangeUtils {

	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_EXIT = "exit\n";
	public static final String COMMAND_LINE_END = "\n";

	private FlightModeChangeUtils() {
		throw new AssertionError();
	}

	public static int setAirPlaneMode(int mode) {
		QcomDiag qcomDiag = QcomDiag.getInstance();
		int result = -1;
		switch (mode) {
		case 1:
			result = qcomDiag.SendCmdMessage(QcomDiag.BIXI_CMD_SET_AIRPLANE_ON,
					null, 0, 0);
			break;
		case 0:
			result = qcomDiag.SendCmdMessage(
					QcomDiag.BIXI_CMD_SET_AIRPLANE_OFF, null, 0, 0);
			break;
		}
		return result;

	}

	/**
	 * 打开飞行模式
	 */
	public static CommandResult openFlightMode(Activity activity) {
		//getRootPermissionNew();
		Log.d(TAG, "getRootPermissionNew: "+ activity.getPackageCodePath());
		ArrayList<String> off_cmds = new ArrayList<String>();
		off_cmds.add("settings put global airplane_mode_on 1");
		off_cmds.add("am broadcast -Abean android.intent.action.AIRPLANE_MODE --ez state true");
		return execCommand(off_cmds, true, true);
	}

	/**
	 * 关闭飞行模式
	 */
	public static CommandResult closeFlightMode(Activity activity) {
		Log.d(TAG, "getRootPermissionNew: "+ activity.getPackageCodePath());
//		getRootPermissionNew();
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("settings put global airplane_mode_on 0");
		cmds.add("am broadcast -Abean android.intent.action.AIRPLANE_MODE --ez state false");
		return execCommand(cmds, true, true);
	}

	public static CommandResult execCommand(List<String> commands,
			boolean isRoot, boolean isNeedResultMsg) {
		return execCommand(
				commands == null ? null
						: (String[]) commands.toArray(new String[0]), isRoot,
				isNeedResultMsg);
	}

	public static CommandResult execCommand(String[] commands, boolean isRoot,
			boolean isNeedResultMsg) {
		int result = -1;
		if ((commands == null) || (commands.length == 0)) {
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
			os = new DataOutputStream(process.getOutputStream());
			String[] arrayOfString = commands;
			int j = commands.length;
			for (int i = 0; i < j; i++) {
				String command = arrayOfString[i];
				if (command != null) {
					os.write(command.getBytes());
					os.writeBytes("\n");
					os.flush();
				}
			}
			os.writeBytes("exit\n");
			os.flush();

			result = process.waitFor();

			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					// String s1;
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null)
					errorMsg.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null)
					errorResult.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}
		return new CommandResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}

	public static class CommandResult {
		public int result;
		public String successMsg;
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}

	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean getRootPermissionNew(Activity activity) {
		boolean isSuccess = false;

//		LogUtils.d("====>"+BeidianXinlingTestApplication.getMainActivity().getPackageCodePath());
		String cmd = "su -c chmod 777 "
				+ activity.getPackageCodePath();
		Log.d(TAG, "getRootPermissionNew: "+ activity.getPackageCodePath());
		QcomDiag qcomDiag = QcomDiag.getInstance();

		qcomDiag.SendCmdMessage(QcomDiag.BIXI_CMD_EXECUTE_SHELL_COMMAND,
				cmd.getBytes(), cmd.length(), cmd.length());
		isSuccess = true;
		// QcomDiag command = "su -c mkdir /sdcard/"
		return isSuccess;
	}

}
