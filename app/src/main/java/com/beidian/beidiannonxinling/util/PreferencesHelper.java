package com.beidian.beidiannonxinling.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by Hank on 2016/10/28.
 * Email : laohuangshu@foxmail.com
 */
public class PreferencesHelper {

    private static String STRING = "String", INTEGER = "Integer",BOOLEAN = "Boolean", FLOAT   = "Float",LONG    = "Long";

    /*** 保存在设备里面的文件名 */
    private static final String FILE_NAME = "beidian";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object 已支持: STRING INTEGER BOOLEAN FLOAT LONG
     * 				   待支持: JSONObject JSONArray 等
     */
    public static void put(Context context, String key, Object object) {

        if(null == object){ remove(context,key);return;  }

        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();

        String type = object.getClass().getSimpleName();
        if (STRING.equals(type)) {
            editor.putString(key, (String) object);
        } else if (INTEGER.equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if (BOOLEAN.equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if (FLOAT.equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if (LONG.equals(type)) {
            editor.putLong(key, (Long) object);
        } else if(object instanceof Map){
            editor.putString(key, JSON.toJSONString(object));
        }else{
            //TODO 其他类型默认存储为string
            editor.putString(key, JSON.toJSONString(object));
        }

        editor.commit();
    }

    /**
     * 获取Map
     * @param context
     * @param key
     * @return
     */
    public static Map<String,Object> getMap(Context context,String key){
        String object = getSharedPreferences(context).getString(key,"");
        if("".equals(object)){ return null;}
        return JSON.parseObject(object);
    }

    public static <T> T getBean(Context context,String key,Class<T> cls){
        String str = getSharedPreferences(context).getString(key,"");
        if("".equals(str) || !str.startsWith("{") || !str.endsWith("}")){ return null;}
        return JSONUtil.parseObject(str,cls);
    }

    public static String getString(Context context,String key,String defValue){
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        return getSharedPreferences(context).getBoolean(key,defValue);
    }

    public static float getFloat(Context context,String key,float defValue){
        return getSharedPreferences(context).getFloat(key,defValue);
    }

    public static int getInt(Context context,String key,int defValue){
        return getSharedPreferences(context).getInt(key,defValue);
    }

    public static void remove(Context context,String key){
        Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean contains(Context context,String key){
        return getSharedPreferences(context).contains(key);
    }

}