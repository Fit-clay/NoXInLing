package com.beidian.beidiannonxinling.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/1910:34
 * @描述: 更新app
 */


public class UpdateManager {
    public static void init(Context context) {
        final ProgressBar[] progressBar = new ProgressBar[1];
        final Dialog dialog = DialogUtil.customProgress(context, "更新中。。。。", new DialogUtil.OnProgressDialogCallBack() {
            @Override
            public void getProgressbar(ProgressBar progressBar1) {
                progressBar[0] = progressBar1;
            }
        });
        dialog.hide();
        //*bugly注册下载监听，监听下载事件*/
        //        Beta.autoCheckUpgrade = true;//自动检查更新开关
        Beta.registerDownloadListener(new DownloadListener() {
            @Override
            public void onReceive(DownloadTask task) {
                dialog.show();
                progressBar[0].setMax((int) task.getTotalLength() / 100);
                progressBar[0].setProgress((int) task.getSavedLength() / 100);
            }

            @Override
            public void onCompleted(final DownloadTask task) {

                dialog.hide();
            }

            @Override
            public void onFailed(DownloadTask task, int code, String extMsg) {

                dialog.hide();

            }
        });
    }

}