package com.beidian.beidiannonxinling.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by Eric on 2017/10/10.
 */

public class ShiFenBbuRruAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private int indexType;
    private boolean isRRU;
    private Context mContext;
    private List<ShiFenBBU_RRUBean> listData;
    private static final int addressPlan=1120,addressInfo=1121,infactAddress=1122;

    public interface ChangePhotoLinstener{
        void changePhoto(int position);
    }
    ChangePhotoLinstener changePhotoLinstener;

    ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener addOrDeleteAntennaListener;
    public void setAddOrDeleteAntennaListener(ShiFenBaseInfoAntennaAdapter.AddOrDeleteAntennaListener addOrDeleteAntennaListener) {
        this.addOrDeleteAntennaListener = addOrDeleteAntennaListener;
    }

    public ShiFenBbuRruAdapter(Context context, List<ShiFenBBU_RRUBean> listData,  boolean isRRU,int indexType) {
        this.indexType = indexType;
        this.isRRU = isRRU;
        this.listData = listData;
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_rru_audit,parent,false);
        BbuRruViewHolder holder=new BbuRruViewHolder(view,indexType,isRRU);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            final BbuRruViewHolder viewHolder=(BbuRruViewHolder) holder;
            final ShiFenBBU_RRUBean bean=listData.get(position);
        viewHolder.  planAddress.setText(bean.getPlanAdress());
        viewHolder.   inFactAddress.setText(bean.getInfactAdress());
        viewHolder.   tvTitle.setText(bean.getTitle());


       Glide.with(mContext).load(R.mipmap.add_imageview).into( viewHolder.imageView);
       if(!TextUtils.isEmpty(bean.getImageUrl())&& FileUtils.fileIsExists(bean.getImageUrl())){
            Glide.with(mContext).load(bean.getImageUrl()).into(viewHolder.imageView);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changePhotoLinstener!=null){
                    if(!TextUtils.isEmpty(bean.getImageUrl())&&FileUtils.fileIsExists(bean.getImageUrl())){
                        DialogUtil.showImageDialog(mContext, BitmapFactory.decodeFile(bean.getImageUrl()),indexType, new DialogUtil.OnImageDialogCallBack() {
                            @Override
                            public void isDelete(boolean isDelete) {
                                if (isDelete) {
                                    new File(bean.getImageUrl()).delete();
                                    bean.setImageUrl("");
                                    notifyItemChanged(position);
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
        viewHolder. ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addOrDeleteAntennaListener!=null){
                    if(indexType!=Const.LOOK){
                        addOrDeleteAntennaListener.addOrDeleteAntenna(true,position);
                    }
                }
            }
        });
       changeEdt(viewHolder.inFactAddress,infactAddress,position);
        changeEdt(viewHolder.planAddress,addressPlan,position);
    }

    @Override
    public int getItemCount() {
        if(listData==null){
            return -1;
        }
        return listData.size();
    }

    public void setChangePhotoLinstener(ChangePhotoLinstener changePhotoLinstener) {
        this.changePhotoLinstener = changePhotoLinstener;
    }

    public void changeEdt(final EditText editText, final int type, final int position){
        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override  public void afterTextChanged(Editable s) {
                ShiFenBBU_RRUBean bean=listData.get(position);
             if(!TextUtils.isEmpty(s.toString().trim())) {
                       String str = editText.getText().toString();
                       switch (type) {
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



/*
                    ShiFenBBU_RRUBean bean = listData.get(position);
                    String str = editText.getText().toString();
                    Log.d("==============" + position, "changeEdt" + str);
                    switch (type) {
                        case addressPlan:
                            bean.setPlanAdress(str);
                            break;
                        case addressInfo:
                            bean.setAdressInfo(str);
                            break;
                        case infactAddress:
                            bean.setInfactAdress(str);
                            break;*/

//                }
            }
        });
    }





    class BbuRruViewHolder extends RecyclerView.ViewHolder{
        EditText planAddress,inFactAddress;
        LinearLayout ly_address_info,ly_item;
        ImageView imageView,ivDelete;
        TextView tvTitle;
        public BbuRruViewHolder(View itemView,int indexType,boolean isRRU ) {
            super(itemView);
            planAddress= (EditText) itemView.findViewById(R.id.edt_plan_address);
            inFactAddress= (EditText) itemView.findViewById(R.id.edt_in_fact_address);
            ly_address_info=(LinearLayout)itemView.findViewById(R.id.ly_address_info);
            ly_item=(LinearLayout)itemView.findViewById(R.id.rru_audit_ly);
            imageView=(ImageView)itemView.findViewById(R.id.iv_address_photo);
            ivDelete=(ImageView)itemView.findViewById(R.id.iv_delete_rru);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_title_number);
            if(indexType== Const.LOOK){
                ViewUtils.disableSubControls(ly_item);
            }
            if(isRRU){
                ly_address_info.setVisibility(View.VISIBLE);
            }else {
                ly_address_info.setVisibility(View.GONE);
            }

        }
    }
}
