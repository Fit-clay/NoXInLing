package com.beidian.beidiannonxinling.util;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
import com.beidian.beidiannonxinling.bean.MapBean;
import com.beidian.beidiannonxinling.bean.UserInfoBean;
import com.beidian.beidiannonxinling.bean.Workorder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/8/24.
 */

public class JsonUtils {
    public static UserInfoBean getUserInforBean(String json) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Type types = new TypeToken<UserInfoBean>() {
        }.getType();
        UserInfoBean userInfoBean = gson.fromJson(json, types);
        return userInfoBean;
    }


    public static List<Workorder> getTask(String json,double y, double x) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Type types = new TypeToken<ArrayList<Workorder>>() {
        }.getType();
        List<Workorder> lists = gson.fromJson(json, types);
       for (Workorder li:lists) {
           if (lists.size() > 0) {
               MapUtil.getDistanceFromXtoY(li.getLatitude(), li.getLongitude(), y, x);
               li.setRange(String.valueOf(MapUtil.getDistanceFromXtoY(li.getLatitude(),li.getLongitude(), y, x)));
               li.setStartX(String.valueOf(x));
               li.setStartY(String.valueOf(y));
           }
       }
        return lists;
    }

    public static List<MapBean> getMapList(String json) {
        if (json != null) {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(true);
            Type types = new TypeToken<ArrayList<Workorder>>() {
            }.getType();
            List<Workorder> lists = gson.fromJson(json, types);
            List<MapBean> lat = new ArrayList<>();
            for (Workorder o : lists) {
                LatLng latlng = new LatLng(o.getLatitude(),o.getLongitude());
                String id=o.getWorkorderno();
                MapBean map = new MapBean(id, latlng, o.getWorkorderstatus());
                lat.add(map);

            }
            return lat;
        } else {
            return null;
        }
    }

    public static List<Workorder> getWorknow(String json, double y, double x) {
        if (json != null) {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.setLenient(true);
            Type types = new TypeToken<ArrayList<Workorder>>() {
            }.getType();
            List<Workorder> lists = gson.fromJson(json, types);
            for (Workorder o : lists) {
                if (y != 0 && x != 0) {
                    MapUtil.getDistanceFromXtoY(o.getLatitude(), o.getLongitude(), y, x);
                    o.setRange(String.valueOf(MapUtil.getDistanceFromXtoY(o.getLatitude(), o.getLongitude(), y, x)));
                    o.setStartX(String.valueOf(x));
                    o.setStartY(String.valueOf(y));
                } else {
                    o.setRange("距离初始化中请刷新");
                }

            }
            return lists;
        } else {
            return null;
        }
    }


    public static String getAssetsJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
