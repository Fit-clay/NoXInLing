package com.beidian.beidiannonxinling.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beidian.beidiannonxinling.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

/**
 * Created by shanpu on 2017/8/24.
 * <p>
 */

public class ImageUtils {


    public interface CompressCallback {
        void onCompressSuccess(String imagePath, Bitmap bitmap);
    }


    /**
     * 以当前时间戳为后缀的文件名
     *
     * @param fileSuffixName（例如: .jpg）
     * @return 以当前时间戳为后缀的文件名(例如:20160729160056.jpg)
     */
    public static String getFileNameTimeStamp(String fileSuffixName) {
        //创建图片的名字：以中国当地时间作为名字
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());//当前时间的毫秒值
        String formatTime = dateFormat.format(date);
        return formatTime + fileSuffixName;
    }
    /**
     * 以当前时间戳为后缀的文件名
     *
     * @param fileSuffixName（例如: .jpg）
     * @return 以当前时间戳为后缀的文件名(例如:20160729160056.jpg)
     */
    public static String getImageName(String distract,String lat,String lng,String info,String fileSuffixName) {
//        //创建图片的名字：以中国当地时间作为名字
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
//        Date date = new Date(System.currentTimeMillis());//当前时间的毫秒值
//        String formatTime = dateFormat.format(date);
        return distract+"_"+lat+"_"+lng+"_"+info+"_" + fileSuffixName;
    }


    /**
     * @param view     将视图view以图片的形式保存到sd卡
     * @param filePath
     */
    public static File saveViewToSDCard(Context mContext ,View view, String filePath) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = view.getDrawingCache(true);
        return saveBitmapToSDCard(mContext,bitmap, filePath);
    }

    /**
     * 将bitmap对象保存到sd卡
     *
     * @param bitmap
     * @param filePath 文件路径
     */
    public static File saveBitmapToSDCard(Context mContext,Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.i("saveBitmapToSDCard", "图片保存成功: " + file.getAbsolutePath());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void saveBitmapToFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        //再创建文件对象
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            Log.i("saveBitmapToFile", "图片保存成功: " + file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param context
     * @param filePath 图片地址
     * @param bitmap 图片资源
     * @param newWidth 压缩后宽度
     * @param newHeight 压缩后高度
     * @param marks  水印信息
     * @param compressCallback
     */
    public static void compressBitmap(final Context context, final String filePath, final Bitmap bitmap, final int newWidth, final int newHeight, final String[] marks, final CompressCallback compressCallback) {

        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.i("compressBitmap", "Executors.newCachedThreadPool()-->开始压缩图片...");
                //压缩图片
                Bitmap compressByScale = compressByScale(bitmap, newWidth, newHeight, false);
                //添加水印耗时：平均值 95ms
                final Bitmap watermarkBitmap = createWatermarkBitmap2(context, compressByScale, marks, ContextCompat.getColor(context, R.color.waterMarkColor), 10);  //添加水印
                //保存图片
                saveBitmapToFile(watermarkBitmap, filePath);
                compressCallback.onCompressSuccess(filePath, watermarkBitmap);
                LogUtils.i("compressBitmap", "Executors.newCachedThreadPool()-->压缩完成...");
            }
        });
    }


    /**
     * @param bitmap bitmap对象
     * @param marks  文案数组
     * @return
     */
    private static Bitmap createWatermarkBitmap(Context context, Bitmap bitmap, String[] marks, int textColor, int textSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tempBitmap);
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(DPUtils.sp2px(context, textSize));
        paint.setAntiAlias(true);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float descent = fontMetrics.descent;
        float ascent = fontMetrics.ascent;
        float textHeight = descent - ascent;
        canvas.drawBitmap(bitmap, 0, 0, paint);
        for (int i = 0; i < marks.length; i++) {
            String mark = marks[i];
            canvas.drawText(mark, 0, textHeight + textHeight * i, paint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return tempBitmap;
    }

    private static Bitmap createWatermarkBitmap2(Context context, Bitmap bitmap, String[] marks, int textColor, int textSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tempBitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(DPUtils.sp2px(context, textSize));
        textPaint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0, 0, textPaint);
        for (int i = 0; i < marks.length; i++) {
            String mark = marks[i];
            StaticLayout layout = new StaticLayout(mark, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            layout.draw(canvas);
            canvas.translate(0, DPUtils.dp2px(context, 2f) + layout.getHeight());
        }
        canvas.save();
        canvas.restore();
        return tempBitmap;
    }

    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放后的图片
     */
    public static Bitmap compressByScale(Bitmap src, int newWidth, int newHeight, boolean recycle) {
        if (src == null) return null;
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }


    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // 创建目录失败返回false
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }





    /**动态添加图片
     *
     * @param mContext
     * @param linearLayout 父布局
     * @param imageView 需要隐藏的 触发事件的view
     * @param maps   添加的图片资源
     * @param number 动态添加的图片上线
     */
    public void addLLView(Context mContext,LinearLayout linearLayout, ImageView imageView, Bitmap maps, int number) {
        ImageView imgs = new ImageView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        imgs.setLayoutParams(params);
        imgs.setImageBitmap(maps);
        if (linearLayout.getChildCount() == number) {
            imageView.setVisibility(View.GONE);
        }
        linearLayout.addView(imgs, 0);
    }
}
