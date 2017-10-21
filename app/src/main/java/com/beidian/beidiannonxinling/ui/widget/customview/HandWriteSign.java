package com.beidian.beidiannonxinling.ui.widget.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;

import static android.R.attr.bitmap;

/**
 * Created by shanpu on 2017/8/22.
 * <p>
 * 手写签名
 */

public class HandWriteSign extends View {
    private String TAG = "HandWriteSign";
    private Paint mPaint;//手势画笔
    private Path mPath;//路径

    public HandWriteSign(Context context) {
        super(context);
        init(context, null);
    }

    public HandWriteSign(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HandWriteSign(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HandWriteSign(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    //旋转屏幕时切换
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private int signColor;
    private float signWidth;

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        //获取自定义属性信息
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.HandWriteSign);
        signColor = typedArray.getColor(R.styleable.HandWriteSign_signColor, Color.DKGRAY);
        signWidth = typedArray.getDimension(R.styleable.HandWriteSign_signWidth, 10f);

        LogUtils.i(TAG, "init()");
        //设置画笔
        mPaint = new Paint();
//        mPaint.setStrokeWidth(dp2px(4));
//        mPaint.setColor(Color.parseColor("#FF4081"));
        mPaint.setColor(signColor);
        mPaint.setStrokeWidth(signWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);//

        //新建路径
        mPath = new Path();
    }


    private float mStartX;//起始X坐标
    private float mStartY;//起始Y坐标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //按下

                mStartX = event.getX();
                mStartY = event.getY();
                mPath.moveTo(mStartX, mStartY);//路径绘制的起始位置
                break;
            case MotionEvent.ACTION_MOVE://移动

                float mCurrentX = event.getX();//当前手指X坐标
                float mCurrentY = event.getY();//当前手指坐标

                //计算两点之间的距离
                float dX = (mCurrentX - mStartX) * (mCurrentX - mStartX);
                float dY = (mCurrentY - mStartY) * (mCurrentY - mStartY);
                double sqrt = Math.sqrt(dX + dY);
                if (sqrt > 2) {
                    //距离大于等于3，取中点
                    float centerX = (mStartX + mCurrentX) / 2;
                    float centerY = (mStartY + mCurrentY) / 2;
                    //绘制贝塞尔曲线
                    mPath.quadTo(mStartX, mStartY, centerX, centerY);//起点(x1,y1) -- 终点(x2,y2)
                    mStartX = mCurrentX;
                    mStartY = mCurrentY;
                } else {
                    mPath.quadTo(mStartX, mStartY, mCurrentX, mCurrentY);//起点(x1,y1) -- 终点(x2,y2)
                    mStartX = mCurrentX;
                    mStartY = mCurrentY;
                }

                break;
            case MotionEvent.ACTION_UP: //抬起

                break;
        }

        invalidate();//更新绘制
        return true;
    }


    //处理自定义控件与ScrollView滑动冲突问题
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //不让父控件拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //不让父控件拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    /**
     * 获取画板的bitmap
     *
     * @return
     */
    public Bitmap getBitmap() {
        invalidate();
        setDrawingCacheEnabled(true);
        Bitmap bitmap = getDrawingCache(true);
        return bitmap;
    }


    public void clear() {
        mPath.reset();
        mPath.close();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制路径
        canvas.drawPath(mPath, mPaint);
    }


    /**
     * 保存画板
     *
     * @param path 保存到路径
     */
    public void save(final String path, final SaveSignCallback saveSignCallback) {
        invalidate();
        setDrawingCacheEnabled(true);
        final Bitmap bitmap = getDrawingCache(true);
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] buffer = bos.toByteArray();
                    if (buffer != null) {
                        OutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(buffer);
                        outputStream.close();
                        saveSignCallback.saveSuccess(path);
                        setDrawingCacheEnabled(false);
                        LogUtils.i("save", "签名图片保存成功:" + path);
                    } else {
                        saveSignCallback.saveFailed();
                    }
                } catch (Exception e) {
                    setDrawingCacheEnabled(false);
                    LogUtils.i("save", "签名图片保存失败!");
                    saveSignCallback.saveFailed();
                }
            }
        });


    }


    public interface SaveSignCallback {
        void saveSuccess(String path);

        void saveFailed();
    }


    public float dp2px(float dpValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return density * dpValue + 0.5f;
    }

    public int dp2px(int dpValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dpValue + 0.5f);
    }

    public int sp2px(int value) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * value + 0.5f);
    }


    private Bitmap mBitmap;

    public void setBackgroundBitmap(Bitmap bitmap) {

        invalidate();
        setDrawingCacheEnabled(true);
        Bitmap tempBitmap = getDrawingCache(true);
        mBitmap = tempBitmap;
    }

    public interface OnSaveBitmapInterface {
        void saveBitmap(Bitmap bitmap);
    }


    public Bitmap getBackgroundBitmap() {
        return mBitmap;
    }


    public void saveBackgroundBitmap(OnSaveBitmapInterface mOnSaveBitmapInterface) {
        invalidate();
        setDrawingCacheEnabled(true);
        Bitmap bitmap = getDrawingCache(true);
        mOnSaveBitmapInterface.saveBitmap(bitmap);
    }


}
