package com.beidian.beidiannonxinling.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.beidian.beidiannonxinling.R;

/**
 * 图片预览
 */
public class ImagePreviewActivity extends BaseActivity {
    private ImageView imageView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_image_preview);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
            String signImagePath = intent.getStringExtra("waterMarkImage");
            if(signImagePath!=null){
                Bitmap bitmap = BitmapFactory.decodeFile(signImagePath);
                if (bitmap != null)
                    imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void initListener() {

    }
}
