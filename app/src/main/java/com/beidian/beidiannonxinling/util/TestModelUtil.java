package com.beidian.beidiannonxinling.util;

import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.bean.ModelBean;
import com.beidian.beidiannonxinling.bean.ModelCofingBean;
import com.beidian.beidiannonxinling.bean.TestModelBean;
import com.beidian.beidiannonxinling.bean.TestTask;
import com.beidian.beidiannonxinling.common.Const;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/10/8.
 */

public class TestModelUtil {
    public static List<TestModelBean.TemplateBean> getModelNewTask(){
         List< TestModelBean.TemplateBean> items = null;
        String result = null;

        result = FileUtils.readFile(Const.ReadFile.getBaseDir("model") + "/model.txt");
        items = getNewModel(result);
        return items;
    }
    public static void ModifiTemplate(List<TestModelBean.TemplateBean> list){
        JSON json= (JSON) JSON.toJSON(list);
        String jsonDir=FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));
        String jsonFilePath=Const.SaveFile.getJsonColorFilePaht(jsonDir,"model");
        saveFile(json.toString(),jsonFilePath);

    }
    public static void modifiModelDetail(List<TestTask> testTasks){
        List<TestModelBean.TemplateBean> model=null;
        String result=null;

        result=FileUtils.readFile(Const.ReadFile.getBaseDir("model")+"/model.txt");
        model=getNewModel(result);
        for (TestModelBean.TemplateBean mo:model){
                for (TestTask ta:mo.getTaskList()){
                    if (ta.getTaskid()==mo.getTaskid()){
                        mo.setTaskList(testTasks);
                        JSON json = (JSON) JSON.toJSON(model);
                        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));
                        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "model");
                        saveFile(json.toString(), jsonFilePath);
                    }
                }
        }


    }
    public static void updatetestTask(List<TestTask> testTasks){
        List<TestModelBean.TemplateBean> model=null;
        String result=null;

        result=FileUtils.readFile(Const.ReadFile.getBaseDir("model")+"/model.txt");
        model=getNewModel(result);

        JSON json = (JSON) JSON.toJSON(model);
        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));
        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "model");
        saveFile(json.toString(), jsonFilePath);

    }

    public static void modifiModelBean(List<ModelBean> list) {

        JSON json = (JSON) JSON.toJSON(list);
        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));
        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "model");
        saveFile(json.toString(), jsonFilePath);
    }

    public static void modelfiModelDetail(List<ModelCofingBean> bean) {
        List<ModelBean> items = null;
        String result = null;

        result = FileUtils.readFile(Const.ReadFile.getBaseDir("model") + "/model.txt");
        items = getModel(result);
        for (ModelBean mo : items
                ) {
            for (ModelCofingBean mb : bean
                    ) {
                if (mo.getModelId() == mb.getModelId()) {
                    mo.setTaskList(bean);
                    break;
                }
            }
        }
        JSON json = (JSON) JSON.toJSON(items);
        String jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("model"));
        String jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "model");
        saveFile(json.toString(), jsonFilePath);
    }
    public static List<TestModelBean.TemplateBean> getNewModel(String json){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Type types = new TypeToken<ArrayList<TestModelBean.TemplateBean>>() {
        }.getType();
        List<TestModelBean.TemplateBean> model = gson.fromJson(json, types);
        return model;
    }
    public static List<ModelBean> getModel(String json) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Type types = new TypeToken<ArrayList<ModelBean>>() {
        }.getType();
        List<ModelBean> model = gson.fromJson(json, types);
        return model;
    }

    public static void saveFile(String str, String fileAbsolutePathName) {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!hasSDCard) {

            return;
        }

        if (TextUtils.isEmpty(fileAbsolutePathName)) {

            return;
        }

        File file = new File(fileAbsolutePathName);
        //如果父目录不存在则创建文件夹
        File parentFileDir = file.getParentFile();
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
