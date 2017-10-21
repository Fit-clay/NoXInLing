package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ShiFenBaseInfoAntennaBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Eric on 2017/9/22.
 */

public class ShiFenBaseInfoAntennaAdapter extends TBaseAdapter<ShiFenBaseInfoAntennaBean> {

    private ImageView ivBuild,ivAntenna1,ivAntenna2,iv_add_antenna,iv_delete_antenna;

    ChangeAntennaPhoto photoLinstener;

    public interface ChangeAntennaPhoto{
        void ChangePhoto(int position,int flag);
    }
    AddOrDeleteAntennaListener addOrDeleteAntennaListener;
    public interface AddOrDeleteAntennaListener{
        void addOrDeleteAntenna(boolean flag,int position);
    }

    public void setAddOrDeleteAntennaListener(AddOrDeleteAntennaListener addOrDeleteAntennaListener) {
        this.addOrDeleteAntennaListener = addOrDeleteAntennaListener;
    }

    public void setPhotoLinstener(ChangeAntennaPhoto photoLinstener) {
        this.photoLinstener = photoLinstener;
    }


    public ShiFenBaseInfoAntennaAdapter(Context context, List<ShiFenBaseInfoAntennaBean> listData) {
        super(context, R.layout.item_shifen_info_antenna, listData);
    }

    @Override
    public void doHandler(ViewHolder holder, final int position, View convertView, ViewGroup parent) {
        ivBuild=holder.getView(R.id.iv_item_build);
        ivAntenna1=holder.getView(R.id.iv_item_antenna_1);
        ivAntenna2=holder.getView(R.id.iv_item_antenna_2);
        iv_add_antenna=holder.getView(R.id.iv_add_antenna);
        iv_delete_antenna=holder.getView(R.id.iv_delete_antenna);
        ShiFenBaseInfoAntennaBean bean=getItem(position);
        if(!TextUtils.isEmpty(bean.getBuildPath())&& FileUtils.fileIsExists(bean.getBuildPath())){
            Glide.with(getContext()).load(bean.getBuildPath()).into(ivBuild);
        }else {
            Glide.with(getContext()).load(R.mipmap.add_imageview).into(ivBuild);
        }
        if(!TextUtils.isEmpty(bean.getAntenna1())&& FileUtils.fileIsExists(bean.getAntenna1())){
            Glide.with(getContext()).load(bean.getAntenna1()).into(ivAntenna1);
        }else {
            Glide.with(getContext()).load(R.mipmap.add_imageview).into(ivAntenna1);
        }
        if(!TextUtils.isEmpty(bean.getAntenna2())&& FileUtils.fileIsExists(bean.getAntenna2())){
            Glide.with(getContext()).load(bean.getAntenna2()).into(ivAntenna2);
        }else {
            Glide.with(getContext()).load(R.mipmap.add_imageview).into(ivAntenna2);
        }

        ivBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoLinstener!=null){
                    photoLinstener.ChangePhoto(position, Const.AntennaBuild);
                }
            }
        });
        ivAntenna1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoLinstener!=null) {
                    photoLinstener.ChangePhoto(position, Const.Antenna1);
                }
            }
        });
        ivAntenna2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(photoLinstener!=null) {
                    photoLinstener.ChangePhoto(position, Const.Antenna2);
                }
            }
        });
        if(position==0){
            iv_add_antenna.setVisibility(View.VISIBLE);
            iv_delete_antenna.setVisibility(View.GONE);
        }else {
            iv_add_antenna.setVisibility(View.GONE);
            iv_delete_antenna.setVisibility(View.VISIBLE);
        }
        iv_add_antenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrDeleteAntennaListener.addOrDeleteAntenna(false,position);
            }
        });
        iv_delete_antenna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrDeleteAntennaListener.addOrDeleteAntenna(true,position);
            }
        });
    }
}
