package com.beidian.beidiannonxinling.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.BaseInfoTestBean;
import com.beidian.beidiannonxinling.bean.ChangeTestModelBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.ui.dialog.AddModeDialog;
import com.beidian.beidiannonxinling.ui.dialog.ColorDialog;
import com.beidian.beidiannonxinling.ui.dialog.ModelAddDialog;
import com.beidian.beidiannonxinling.ui.dialog.ModifyModeDialog;
import com.beidian.beidiannonxinling.ui.widget.LoadingDialog;

import java.util.List;

/**
 * Created by Eric on 2017/9/7.
 */

public class DialogUtil {


  public static Dialog customProgress(Context context, String title, OnProgressDialogCallBack onDialogCallBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.view_progressbar_dialog);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        ProgressBar progressBar = (ProgressBar) window.findViewById(R.id.pb_progressbar);
        TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        onDialogCallBack.getProgressbar(progressBar);

        return alertDialog;
    }

    public static ShiFenTestConfigDialog showConfigDialog(Context context, BaseInfoTestBean baseInfoTestBean, ChangeTestModelBean changeTestModelBean){
        return new ShiFenTestConfigDialog(context,baseInfoTestBean,changeTestModelBean);
    }


    public static AlertDialog showConfirmDialog(Context context, String msg) {
        return showConfirmDialog(context, msg, false, null);
    }

    public static AlertDialog showConfirmDialog(Context context, CharSequence errorMsg, boolean showClose, final Object[][] objects) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.layout_common_dialog);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView messageView = (TextView) window.findViewById(R.id.tv_common_dialog_message);
        TextView mConcle = (TextView) window.findViewById(R.id.bt_record_permission_dialog_concle);
        TextView mConfirm = (TextView) window.findViewById(R.id.bt_common_dialog_confirm);
        ImageView btCancel = (ImageView) window.findViewById(R.id.bt_common_dialog_cancel);

        messageView.setText(errorMsg);
        btCancel.setVisibility(showClose ? View.VISIBLE : View.INVISIBLE);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        };

        btCancel.setOnClickListener(listener);
        mConcle.setOnClickListener(listener);
        mConfirm.setOnClickListener(listener);
        mConcle.setVisibility(View.VISIBLE);
        if (null != objects) {
            if (objects.length >= 1) {
                mConcle.setText(objects[0].length == 0 ? "取消" : (objects[0][0] == null ? null : (String) objects[0][0]));
                mConcle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (objects[0].length >= 2 && objects[0][1] != null) {
                            ((DialogCallBack) objects[0][1]).onCallBack(alertDialog);
                        }
                    }
                });
            }
            if (objects.length >= 2) {
                mConfirm.setText(objects[0].length == 0 ? "确定" : (objects[0][0] == null ? null : (String) objects[0][0]));
                if (objects[0].length >= 2 && objects[0][1] != null) {
                    mConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((DialogCallBack) objects[0][1]).onCallBack(alertDialog);

                        }
                    });
                }
            }
        } else {
            mConcle.setVisibility(View.GONE);
        }
        return alertDialog;
    }

    public static AlertDialog showImageDialog(Context context, String imagePath, int indexType, final OnImageDialogCallBack imageDialogCallBack) {
        if (!TextUtils.isEmpty(imagePath)) {
            return showImageDialog(context, new BitmapFactory().decodeFile(imagePath), indexType, imageDialogCallBack);
        }
        return null;
    }

    public static AlertDialog showImageDialog(Context context, Bitmap bitmap, int indexType, final OnImageDialogCallBack imageDialogCallBack) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_image);
        ImageView iv = (ImageView) window.findViewById(R.id.iv_image);
        TextView tvDelete = (TextView) window.findViewById(R.id.tv_delete);
        LinearLayout ly_delete = (LinearLayout) window.findViewById(R.id.ly_delete);
        TextView tvConfirm = (TextView) window.findViewById(R.id.tv_confirm);
        iv.setImageBitmap(bitmap);
        if (indexType == Const.LOOK) {
            ly_delete.setVisibility(View.GONE);
        }
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialogCallBack.isDelete(true);
                alertDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialogCallBack.isDelete(false);
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }
    public static ColorDialog showColorDialogwithhanlder(Context context, int type){
        if (context==null){return null;}
        ColorDialog colorDialog=new ColorDialog(context,type);
        colorDialog.setCanceledOnTouchOutside(true);
        colorDialog.show();
        return colorDialog;
    }
    public static ColorDialog showColorDialog(Context context){
        if (context==null){return null;}
        ColorDialog colorDialog=new ColorDialog(context);
        colorDialog.setCanceledOnTouchOutside(true);
        colorDialog.show();
        return colorDialog;
    }
    public static ModelAddDialog modelAddDialog(Context context){
        ModelAddDialog modelAddDialog=new ModelAddDialog(context);
        modelAddDialog.setCanceledOnTouchOutside(true);
        modelAddDialog.show();
        return modelAddDialog;
    }
    public static AddModeDialog addModeDialog(Context context){
        AddModeDialog addModeDialog=new AddModeDialog(context);
        addModeDialog.setCanceledOnTouchOutside(true);
        addModeDialog.show();
        return addModeDialog;

    }
    public static ModifyModeDialog modifyModeDialog(Context context, List<TestModelBean.TemplateBean>  date,int Taskid ,int id){
        ModifyModeDialog modifyModeDialog=new ModifyModeDialog(context,date,Taskid,id);
        modifyModeDialog.setCanceledOnTouchOutside(true);
        modifyModeDialog.show();
        return modifyModeDialog;
    }

    public static LoadingDialog showLodingDialog(Context context, CharSequence cs) {
        if (context == null) {
            return null;
        }

        LoadingDialog dialog = new LoadingDialog(context);
        //if(null != cs){ dialog.setTitle(cs); }
        dialog.setTitle(cs);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }
    public interface DialogCallBack {
        void onCallBack(Dialog dialog);
    }

    public interface OnProgressDialogCallBack {
        void getProgressbar(ProgressBar progressBar);
    }

    public interface OnImageDialogCallBack {
        void isDelete(boolean isDelete);
    }

    public interface OnItemClick {
        void ItemClick(int position);
    }

    public static AlertDialog showListDialog(Context context, List<String> mList, OnItemClick itemClick) {
        return null;
    }
    public static AlertDialog.Builder alert;
    public static void showCommonListDialog(Context context, String title, String[] araString, final OnItemClick itemClick) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        //设置普通文本格式的对话框，设置的是普通的Item；
        alert.setItems(araString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemClick.ItemClick(which);
            }
        });
        alert.show();
    }
}

