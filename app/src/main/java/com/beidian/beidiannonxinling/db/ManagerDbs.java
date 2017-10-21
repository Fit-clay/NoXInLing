package com.beidian.beidiannonxinling.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.beidian.beidiannonxinling.ui.activity.BaseActivity;
import com.beidian.beidiannonxinling.util.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29.
 */

public class ManagerDbs {
    public static String ECI="ECI";
    public static String ENODEBID="ENODEBID";
    public static String CELLNAME="CELLNAME";
    public static String LINENUM="LINENUM";
    public static String BBUNUM="BBUNUM";
    public static String RRUNUM="RRUNUM";
    public static String PCIS="PCI";
    public static String FREQUENCY="FREQUENCY";
    public static String WORKFREQUENCY="WORKFREQUENCY";
    public static String DOWNBANDWIDTH="DOWNBANDWIDTH";
    public static String LNG="LNG";
    public static String LAT="LAT";
    public static String COVERAGETYPE="COVERAGETYPE";
    public static String DISHI="DISHI";
    public static String QUXIAN="QUXIAN";
    public static String LINESERVICE="LINESERVICE";
    public static String LINETYPE="LINETYPE";
    public static String DIRECTIONANGLE="DIRECTIONANGLE";
    public static String MECHANICALINCLINATION="MECHANICALINCLINATION";
    public static String ANTENNAHEIGHT="ANTENNAHEIGHT";//天线高度

    //将sd文件的数据插入数据库
    public static boolean readFileAndInsertData(String filepath,LineDataDao dao,ProgressDialog proDialog) {
        boolean isSuccess;
        Gson gsons  = new Gson();
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date    curDate    =   new Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        Log.i("Time",str);
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String s = null;
            int i = 1;

            while ((s = br.readLine()) != null) {
                    LineData lineData = gsons.fromJson(s, LineData.class);
                    dao.insertInTx(lineData);

            }
            Date    curDate1    =   new Date(System.currentTimeMillis());//获取当前时间
            String    str1    =    formatter.format(curDate1);
            Log.i("Time1",str1);
            br.close();
            isSuccess=true;
            proDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
             isSuccess=false;
        }
       return isSuccess;
    }
    //根据输入条件查询
    public static List<LineData> QueryLlineData(LineDataDao lineDataDao,String str1,String coName){
        QueryBuilder<LineData> lineData=null;
        if(LineDataDao.Properties.Antennaheight.columnName.equals(coName)) {
             lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Antennaheight.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.BBUnum.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.BBUnum.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Cellname.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Cellname.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Coveragetype.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Coveragetype.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Directionangle.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Antennaheight.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Dishi.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Dishi.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Downbandwidth.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Downbandwidth.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Eci.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Eci.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Enodebid.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Enodebid.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Frequency.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Frequency.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Ids.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Ids.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Lat.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Lat.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Linenum.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Linenum.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Lineservice.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Lineservice.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Linetype.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Linetype.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Lng.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Lng.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Mechanicalinclination.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Mechanicalinclination.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Pci.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Pci.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Quxian.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Quxian.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.RRUnum.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.RRUnum.like("%" + str1 + "%"));
        }else if(LineDataDao.Properties.Workfrequency.columnName.equals(coName)){
            lineData = lineDataDao.queryBuilder().where(LineDataDao.Properties.Workfrequency.like("%" + str1 + "%"));
        }

        return lineData.list();
    }


}
