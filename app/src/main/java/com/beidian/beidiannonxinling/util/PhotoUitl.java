package com.beidian.beidiannonxinling.util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.beidian.beidiannonxinling.common.Const;

import java.io.File;
import java.util.List;

//import net.bither.util.NativeUtil;


/**
 * Created by Eric on 2017/9/28.
 */

public class PhotoUitl {
    /**
     * 拍照：(照片水印：基站小区名、任务工单、经纬度、日期)
     */
public interface ZidImageCallback{
        void callBack(String path, Bitmap bitmap);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String takePhoto(String filePath,String imageName, Context mContext,int TAKE_CAMERA) {
        //判断是否有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //检测是否有相机和读写文件权限
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ((Activity)mContext).requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            } else {
                return startCameraAndroid7(filePath,imageName,mContext,TAKE_CAMERA);
            }
        } else {
            ToastUtils.makeText(mContext, "SD卡不可用");
        }
        return null;
    }
    /**
     * Android7.0适配
     */
    private static String startCameraAndroid7(String filePath,String imageNmae, Context mContext,int TAKE_CAMERA) {
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.makeText(mContext, Const.SaveFile.FILE_DIR_CREATE_FAILED);
            return "";
        }
//        File   newFile = new File(filePath, ImageUtils.getFileNameTimeStamp(".jpg"));
        File   newFile = new File(filePath,imageNmae+".jpg");

        //第二参数是在manifest.xml定义 provider的authorities属性
        Uri  contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileProvider", newFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //兼容版本处理，因为 intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) 只在5.0以上的版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ClipData clip = ClipData.newUri(mContext.getContentResolver(), "A photo", contentUri);
            intent.setClipData(clip);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                mContext.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        ((Activity)mContext).startActivityForResult(intent, TAKE_CAMERA);
        return newFile.getAbsolutePath();

    }

    public static void Compression(Bitmap bm, String filePath){
      //  NativeUtil.compressBitmap(bm,65,filePath,true);
    }

    public static void AddMarkForImage(Context mContext, String path, String[] marks, final ZidImageCallback zidImageCallback){
        final Bitmap bitmap = BitmapFactory.decodeFile(path);
        ImageUtils.compressBitmap(mContext, path, bitmap, 432, 576, marks, new ImageUtils.CompressCallback() {
            @Override
            public void onCompressSuccess(String imagePath, Bitmap bitmap) {
                zidImageCallback.callBack(imagePath,bitmap);
            }
        });



    }

}
