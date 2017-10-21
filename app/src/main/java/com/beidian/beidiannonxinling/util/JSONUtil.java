package com.beidian.beidiannonxinling.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2016/10/28.
 */
public class JSONUtil {

    protected JSONUtil() {}

    public static String toJSONString(Object object){
        if(null==object){
            return  null;
        }
        if(object instanceof String){
            return (String)object;
        }
        if(object instanceof JSONObject){
            return ((JSONObject)object).toJSONString();
        }
        return JSON.toJSONString(object);
    }

    public static String getString(Object object,String key){
        JSONObject o = JSONObject.parseObject(JSONObject.toJSONString(object));
        return o.getString(key);
    }

    public static <T> T parseObject(Object jsonString,Class<T> cls){
        String s;
        if(!(jsonString instanceof String)){
            s = JSONUtil.toJSONString(jsonString);
        }else{
            s = (String)jsonString;
        }
        try{
//            return JSONObject.parseObject(s, cls);
            return new Gson().fromJson(s, cls);
        }catch (Exception e){
            return null;
        }

    }

    public static <T> List<T> parseArray(String jsonString,Class<T> cls){
        return JSON.parseArray(jsonString, cls);
    }

    /**
     * 转成map
     * @param jsonString
     * @return
     */
    public static Map<String,Object> toMaps(String jsonString){
        return JSON.parseObject(jsonString);
    }
    /**
     * 转成JSONArray
     * @param jsonString
     * @return
     */
    public static  <T> JSONArray toJSONArray(List<T> list){
        JSONArray ja=new JSONArray();
        for(T t:list){
            ja.add(t);
        }
        return ja;
    }

}
