package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.ShiFenBBU_RRUBean;
import com.beidian.beidiannonxinling.common.Const;
import com.beidian.beidiannonxinling.util.DialogUtil;
import com.beidian.beidiannonxinling.util.FileUtils;
import com.beidian.beidiannonxinling.util.ViewUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

/**
 * Created by Eric on 2017/9/22.
 */

public class ShiFenBaseInfoBBUAdapter extends TBaseAdapter<ShiFenBBU_RRUBean>  {

    private boolean isRRU;
     ShiFenBBU_RRUBean bbu_rruBean;
    private int indexType;
    private static final int addressPlan=1120,addressInfo=1121,infactAddress=1122;

    private  List<ShiFenBBU_RRUBean>  temList;

    public interface ChangePhotoLinstener{
        void changePhoto(int position);
    }
    ChangePhotoLinstener changePhotoLinstener;
    public ShiFenBaseInfoBBUAdapter(Context context, List<ShiFenBBU_RRUBean> listData,List<ShiFenBBU_RRUBean>  temList,boolean isRRU,int indexType) {
        super(context, R.layout.item_rru_audit, listData);
        this.isRRU=isRRU;
        this.indexType=indexType;
        this.temList=temList;
    }
    ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener addOrDeleteAntennaListener;
    public void setAddOrDeleteAntennaListener(ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener addOrDeleteAntennaListener) {
        this.addOrDeleteAntennaListener = addOrDeleteAntennaListener;
    }

    @Override
    public void doHandler(ViewHolder holder, final int position, View convertView, ViewGroup parent) {
         EditText  planAddress=holder.getView(R.id.edt_plan_address);
         EditText  inFactAddress=holder.getView(R.id.edt_in_fact_address);
        LinearLayout  ly_address_info=holder.getView(R.id.ly_address_info);
        LinearLayout  ly_item=holder.getView(R.id.rru_audit_ly);
        ImageView  imageView=holder.getView(R.id.iv_address_photo);
        ImageView  ivDelete=holder.getView(R.id.iv_delete_rru);
        if(indexType==Const.LOOK){
            ViewUtils.disableSubControls(ly_item);
        }
        if(isRRU){
            ly_address_info.setVisibility(View.VISIBLE);
        }else {
            ly_address_info.setVisibility(View.GONE);
        }
        planAddress.setText(getItem(position).getPlanAdress());
        inFactAddress.setText(getItem(position).getInfactAdress());

        Glide.with(getContext()).load(R.mipmap.add_imageview).into(imageView);
        if(!TextUtils.isEmpty(getItem(position).getImageUrl())&&FileUtils.fileIsExists(getItem(position).getImageUrl())){
                Glide.with(getContext()).load(getItem(position).getImageUrl()).into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changePhotoLinstener!=null){
                   if(!TextUtils.isEmpty(getItem(position).getImageUrl())&&FileUtils.fileIsExists(getItem(position).getImageUrl())){
                        DialogUtil.showImageDialog(getContext(), BitmapFactory.decodeFile(getItem(position).getImageUrl()),indexType, new DialogUtil.OnImageDialogCallBack() {
                            @Override
                            public void isDelete(boolean isDelete) {
                                if (isDelete) {
                                    new File(bbu_rruBean.getImageUrl()).delete();
                                    bbu_rruBean.setImageUrl("");
                                    notifyDataSetChanged();
                                }
                            }
                        });
                    }else {
                       if (indexType != Const.LOOK) {
                           changePhotoLinstener.changePhoto(position);
                       }
                   }
                }
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addOrDeleteAntennaListener!=null){
                    if(indexType!=Const.LOOK){
                        addOrDeleteAntennaListener.addOrDeleteAntenna(true,position);
                    }
                }
            }
        });
        changeEdt(inFactAddress,infactAddress,position);
        changeEdt(planAddress,addressPlan,position);
    }

    public void setChangePhotoLinstener(ChangePhotoLinstener changePhotoLinstener) {
        this.changePhotoLinstener = changePhotoLinstener;
    }

    public List<ShiFenBBU_RRUBean> getTemList() {
        return temList;
    }

    public void changeEdt(final EditText editText, final int type, final int position){
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override  public void afterTextChanged(Editable s) {
                ShiFenBBU_RRUBean bean=temList.get(position);
                String str=editText.getText().toString();
                switch (type){
                    case addressPlan:
                        bean.setPlanAdress(str);
                        break;
                    case addressInfo:
                        bean.setAdressInfo(str);
                        break;
                    case infactAddress:
                        bean.setInfactAdress(str);
                        break;

                }

            }
        });
    }
}
