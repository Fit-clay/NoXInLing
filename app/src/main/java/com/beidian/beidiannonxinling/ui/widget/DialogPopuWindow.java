package com.beidian.beidiannonxinling.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.beidian.beidiannonxinling.R;
import com.beidian.beidiannonxinling.bean.TestTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/28.
 */

public class DialogPopuWindow extends PopupWindow implements View.OnClickListener {
    Context context;
    View views;
    int width;
    int height;
    ListView listView;
    private List<TestTask> dataList;
    private Adapter adapter;
    private Set<Object> checkedSet;
    private int selectCount=-1;
    TextView textView;
    PopupWindow window;
    boolean isShow=false;

    private boolean isEmpty(){return dataList==null?true:dataList.isEmpty();}

    public List<TestTask> getDataList() {
        return dataList;
    }

    public void setDataList(List<TestTask> dataList) {
        this.dataList = dataList;
        if (adapter==null){
            adapter=new Adapter(dataList);
            this.listView.setAdapter(adapter);
        }else {
            adapter.setList(dataList);
            adapter.notifyDataSetChanged();

        }
    }

    public Set<Object> getCheckedSet() {
        return checkedSet;
    }

    public void setCheckedSet(Set<Object> checkedSet) {
        this.checkedSet=new HashSet<Object>();
        if(checkedSet!=null){
            this.checkedSet.addAll(checkedSet);
        }
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public DialogPopuWindow(Context context,View vs) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        views=inflater.inflate(R.layout.popu_listview,null);

        window = new PopupWindow(views,700, 300);
        listView=(ListView)views.findViewById(R.id.listV);
        View view_page_footer = LayoutInflater.from(context).inflate(

                R.layout.pop_item, null);
        Button btn=(Button)view_page_footer.findViewById(R.id.btns);
        Button button=(Button)view_page_footer.findViewById(R.id.buttons);
        button.setOnClickListener(this);
        listView.addFooterView(view_page_footer);
        btn.setOnClickListener(this);
        adapter =new Adapter(null);
        listView.setAdapter(adapter);
        textView= (TextView) vs;
//        window.showAsDropDown(vs);
    }

    public void showPopu(View vs){
        isShow=true;
        window.showAsDropDown(vs);
    }
    public void dismissPopu(){
        isShow=false;
        window.dismiss();
    }
    public boolean isPopuShow(){
        if(isShow){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btns:
                this.checkedSet=adapter.getCheckedSet();
                showSelectedContent();
                window.dismiss();
                break;
            case R.id.buttons:
                window.dismiss();
        }

    }

    private void showSelectedContent(){
        StringBuilder sb=new StringBuilder();
        for(TestTask option:getCheckedOptions()){
            sb.append(option.getTesttype()).append(",");
        }
        if(sb.length()>0){
            sb.setLength(sb.length()-1);
        }
        textView.setText(sb.toString());
    }

    public List<TestTask> getCheckedOptions(){
        List<TestTask> list=new ArrayList<TestTask>();
        if(isEmpty()||checkedSet==null||checkedSet.isEmpty()){
            return  list;
        }
        for(TestTask option:dataList){
            if(checkedSet.contains(option.getId())){
                list.add(option);
            }
        }
        return list;
    }

    class Adapter extends BaseAdapter implements View.OnClickListener {
        private List<TestTask> list;
        private Set<Object> checkedSet;

        public Adapter(List<TestTask> list){
            this.list=list;
            checkedSet=new HashSet<Object>();
        }

        public void setList(List<TestTask> list) {
            this.list = list;
        }

        public Set<Object> getCheckedSet(){
            return this.checkedSet;
        }

        public void setCheckedSet(Set<Object> checkedSet) {
            this.checkedSet=new HashSet<Object>();
            if(checkedSet!=null){
                this.checkedSet.addAll(checkedSet);
            }
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public Object getItem(int position) {
            return list==null?null:list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TestTask mul=(TestTask)this.getItem(position);
            Wrapper wrapper=null;
            if(convertView==null){
                convertView = LayoutInflater.from(DialogPopuWindow.this.context).inflate(R.layout.item_popu,null);
                wrapper=new Wrapper();
                wrapper.textView=(TextView)convertView.findViewById(R.id.dialog_textView);
                wrapper.checkBox=(CheckBox)convertView.findViewById(R.id.dialog_checkBox);
                wrapper.checkBox.setOnClickListener(this);
                convertView.setTag(wrapper);
            }

            wrapper=(Wrapper)convertView.getTag();
            wrapper.textView.setText(mul.getTesttype());

            if(checkedSet!=null){
                if(checkedSet.contains(mul.getId())){
                    wrapper.checkBox.setChecked(true);
                }else{
                    wrapper.checkBox.setChecked(false);
                }
            }
            wrapper.checkBox.setTag(position);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            CheckBox checkBox=(CheckBox)v;
            Integer position=(Integer)checkBox.getTag();
            if(position==null){
                return;
            }
            TestTask op=(TestTask)getItem(position);
            if(checkBox.isChecked()){
//                int maxCount= DialogPopuWindow.this.getSelectCount();
//                if(maxCount>-1&&checkedSet.size()>=maxCount){
//                    checkBox.setChecked(false);
//                    Toast.makeText(DialogPopuWindow.this.context, String.format("最多只能选择 %s 个", selectCount), Toast.LENGTH_SHORT).show();
//                    return;
//                }
                checkedSet.add(op.getId());
            }else{
                checkedSet.remove(op.getId());
            }
        }


        class Wrapper{
            public TextView textView;
            public CheckBox checkBox;
        }
    }

}
