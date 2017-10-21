package com.beidian.beidiannonxinling.net;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shanpu on 2017/8/16.
 * Describe: String类型返回结果的回调,带有网络请求加载框
 */

public abstract class ResultCallback extends AbsCallback<String> {

    protected Context mContext;
    protected ProgressDialog dialog;
    protected boolean isShowDialog = true;//是否显示对话框


    /**
     * 构造方法
     *
     * @param context
     * @param isShowDialog 是否显示对话框： true显示，false不显示
     */
    public ResultCallback(Context context, boolean isShowDialog) {
        this(context, isShowDialog, isShowDialog);
    }

    public ResultCallback(Context context, boolean isShowDialog, boolean initDialog) {
        super();
        this.mContext = context;
        this.isShowDialog = isShowDialog;
        if (initDialog)
            initDialog(context, isShowDialog);
    }

    //初始化对话框
    protected void initDialog(Context context, boolean isShowDialog) {
        if (isShowDialog) {
            dialog = new ProgressDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(Const.NetConst.NET_LOADING);
        }

    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if (!NetUtils.isConnected(mContext)) {
            //提示请先连接网络
            Toast.makeText(mContext, Const.NetConst.NET_NOT_CONNECT, Toast.LENGTH_SHORT).show();
            OkGo.getInstance().cancelAll();
            return;
        } else {
            //在网路请求之前显示对话框
            if (isShowDialog) {
                if (null != dialog && !dialog.isShowing()) {
                    dialog.show();
                }
            }
        }
    }


    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网路请求结束后关闭对话框
        if (isShowDialog) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }


    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        onFailure(call, response, e);
        if (isShowDialog) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        //在有网络连接的情况下再进行判断
        if (NetUtils.isConnected(mContext)) {
            if (e instanceof SocketTimeoutException) {
                ToastUtils.makeText(mContext, Const.NetConst.NET_TIMEOUT);
            } else if (e instanceof ConnectException) {
                ToastUtils.makeText(mContext, Const.NetConst.NET_CONNECT_EXCEPTION);
            } else {
                ToastUtils.makeText(mContext, Const.NetConst.NET_SERVER_ERROR);
            }
        }
    }

    /**
     * 请求失败的回调
     */
    public abstract void onFailure(Call call, Response response, Exception e);


    @Override
    public String convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        return s;
    }


}
