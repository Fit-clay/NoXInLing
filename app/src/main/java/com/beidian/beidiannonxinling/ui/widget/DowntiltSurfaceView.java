package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * @author liuzhiguo cameraview
 */
@SuppressWarnings("deprecation")
public class DowntiltSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    private Camera        camera        = null;
    private SurfaceHolder surfaceHolder = null;

    public DowntiltSurfaceView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        if (camera == null)
            camera = Camera.open();
        camera.setDisplayOrientation(90);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public DowntiltSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // try {
        // camera.setPreviewDisplay(surfaceHolder);
        // camera.startPreview();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // add by liuren
        if (camera == null)
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException exception) {
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        // 根本没有可处理的SurfaceView
        if (surfaceHolder==null) {
            return;
        }
        if (surfaceHolder.getSurface() == null) {

            return;
        }

        // 先停止Camera的预览
        try {
            camera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 这里可以做一些我们要做的变换。
        // 重新开启Camera的预览功能
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            // camera.release();
            camera = null;
        }
        if (surfaceHolder != null) {
            surfaceHolder = null;
        }
    }

}
