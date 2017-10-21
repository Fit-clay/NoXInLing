package com.beidian.beidiannonxinling.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicMessageCollectionFragment extends BaseFragment implements View.OnClickListener {
    private static final int PHOTO_GRAPH = 1;
    Spinner shan_zhan_diffi,village_surface_spinner,village_type,is_rru_spinner;
    String[] mItem,village_surface_array,village_type_array,is_rru_array;
    ArrayAdapter<String> arrayAdapter1,arrayAdapter2,arrayAdapter3,arrayAdapter4;
    ImageButton base_station_take_picture,take_photo_btn;
    Bitmap photo;
    LinearLayout ll_take_picture,ll_jizhan_takePhoto;
    static int loca;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_basic_message_collection,null);
        shan_zhan_diffi=(Spinner)view.findViewById(R.id.shan_zhan_diffi);
        village_surface_spinner=(Spinner)view.findViewById(R.id.village_surface_spinner);
        village_type=(Spinner)view.findViewById(R.id.village_type);
        is_rru_spinner=(Spinner)view.findViewById(R.id.is_rru_spinner);
        base_station_take_picture=(ImageButton)view.findViewById(R.id.base_station_take_picture);
        take_photo_btn=(ImageButton)view.findViewById(R.id.take_photo_btn);
        ll_take_picture=(LinearLayout)view.findViewById(R.id.ll_take_picture);
        ll_jizhan_takePhoto=(LinearLayout)view.findViewById(R.id.ll_jizhan_takePhoto);
        base_station_take_picture.setOnClickListener(this);
        take_photo_btn.setOnClickListener(this);
        initeSpinnerData();
        initetSpennerView();
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.base_station_take_picture:
                int llChildCount=getLLSize(ll_take_picture);
                openTakePhoto(llChildCount);
                loca=0;
                break;
            case R.id.take_photo_btn:
                int llChildCounts=getLLSize(ll_jizhan_takePhoto);
                openTakePhoto(llChildCounts);
                loca=1;
                break;
            default:
                break;
        }
    }
    public void initeSpinnerData(){
        mItem=getResources().getStringArray(R.array.hong_zhan_difficult);
        village_surface_array=getResources().getStringArray(R.array.antenna_surfaces);
        village_type_array=getResources().getStringArray(R.array.village_corver_tp);
        is_rru_array=getResources().getStringArray(R.array.is_rru);
    }
    public void initetSpennerView(){
        arrayAdapter1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mItem);
        shan_zhan_diffi.setAdapter(arrayAdapter1);

        arrayAdapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,village_surface_array);
        village_surface_spinner.setAdapter(arrayAdapter2);

        arrayAdapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,village_type_array);
        village_type.setAdapter(arrayAdapter3);

        arrayAdapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,is_rru_array);
        is_rru_spinner.setAdapter(arrayAdapter4);
    }

    private void openTakePhoto(int llChildCount){
        if(llChildCount<=3) {
            String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
            if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, PHOTO_GRAPH);
            } else {
                Toast.makeText(getActivity(), "sdcard不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public int getLLSize(LinearLayout linearLayout){
        int size=linearLayout.getChildCount();
        return size;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (data!= null) {
            switch (requestCode) {
                case 1:
                    //拍摄图片并选择      //两种方式 获取拍好的图片
                    if (data.getData() != null|| data.getExtras() != null){
                        //防止没有返回结果
                        Uri uri =data.getData();
                        if (uri != null) {
                            photo = BitmapFactory.decodeFile(uri.getPath());
                            //拿到图片
                        }
                        if (photo == null) {
                            Bundle bundle =data.getExtras();
                            if (bundle != null){
                                photo =(Bitmap) bundle.get("data");
                                photo=compressImage(photo);
                            } else {
                                Toast.makeText(getActivity(),
                                        "找不到图片",Toast.LENGTH_SHORT).show();
                            }

                        }

                        inteView(photo);
                    }
                    break;
            }

        }

    }
    //动态添加图片
    public void inteView(Bitmap map){
        ImageView imgs=new ImageView(getActivity());
        imgs.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imgs.setImageBitmap(map);
        if(loca==0) {
            if (ll_take_picture.getChildCount() == 3) {
                base_station_take_picture.setVisibility(View.GONE);
            }
            ll_take_picture.addView(imgs, 0);
        }
        if(loca==1){
            if(ll_jizhan_takePhoto.getChildCount()==3){
                take_photo_btn.setVisibility(View.GONE);
            }
            ll_jizhan_takePhoto.addView(imgs,0);
        }

        photo=null;
    }
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.PNG, options, baos);
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
