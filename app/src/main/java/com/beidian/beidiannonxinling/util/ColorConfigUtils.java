package com.beidian.beidiannonxinling.util;

import android.os.Environment;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.beidian.beidiannonxinling.bean.ColorConfigItem;
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
 * Created by ASUS on 2017/9/22.
 */

public class ColorConfigUtils {

    /**
     *读取图例Json数据
     * @param type
     * @return 当前模式图例的数据bean
     */
    public static List<ColorConfigItem> getColorItemBean(int type){
        List<ColorConfigItem> items=null;
        String result=null;
        switch (type) {
            case Const.ColorManager.COVERAGE_PCI:
                result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/pci.txt");
                items = getColor(result);
                break;
            case Const.ColorManager.COVERAGE_RSRP:
                result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/rsrp.txt");
                items = getColor(result);
                break;
//            case Const.ColorManager.COVERAGE_RSRQ:
//                result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/rsrq.txt");
//                items = getColor(result);
//                break;
            case Const.ColorManager.COVERAGE_SINR:
                result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/sinr.txt");
                items = getColor(result);
                break;
            case Const.ColorManager.COVERAGE_DL:
                result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/appdl.txt");
                items = getColor(result);
                break;
            case Const.ColorManager.COVERAGE_UL:
                result= FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/appul.txt");
                items = getColor(result);
                break;

        }
        if (result==null){
            return null;
        }else {
            return items;
        }
    }
    public static void modifeDataBytype( final int type, final List<ColorConfigItem> datas){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result=null,jsonDir=null,jsonFilePath=null;
                    JSON json=null;
                    System.out.println("类型:"+type);
                    switch (type) {

                        case Const.ColorManager.COVERAGE_PCI:
                             List<ColorConfigItem> items=new ArrayList<ColorConfigItem>();
                             result = Const.ReadFile.getBaseDir("colors") + "/pci.txt";
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "pci");
                            saveFile( json.toString(), jsonFilePath);

                            break;
                        case Const.ColorManager.COVERAGE_RSRP:
                            items=new ArrayList<ColorConfigItem>();
                            result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/rsrp.txt");
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "rsrp");
                            saveFile( json.toString(), jsonFilePath);
                            break;
                        case Const.ColorManager.COVERAGE_RSRQ:
                            items=new ArrayList<ColorConfigItem>();
                            result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/rsqp.txt");
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "rsqp");
                            saveFile( json.toString(), jsonFilePath);
                            break;
                        case Const.ColorManager.COVERAGE_SINR:
                            items=new ArrayList<ColorConfigItem>();
                            result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/sinr.txt");
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "sinr");
                            saveFile( json.toString(), jsonFilePath);
                            break;
                        case Const.ColorManager.COVERAGE_DL:
                            items=new ArrayList<ColorConfigItem>();
                            result = FileUtils.readFile(Const.ReadFile.getBaseDir("colors") + "/appdl.txt");
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "appdl");
                            saveFile( json.toString(), jsonFilePath);
                            break;
                        case Const.ColorManager.COVERAGE_UL:
                             items=new ArrayList<ColorConfigItem>();
                            for (int n = 0; n < datas.size(); n++) {
                                ColorConfigItem configItem = new ColorConfigItem();
                                configItem.setColor(datas.get(n).getColor());
                                configItem.setLeftType(datas.get(n).getLeftType());
                                configItem.setMinValue(datas.get(n).getMinValue());
                                configItem.setRightType(datas.get(n).getRightType());
                                configItem.setMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMaxValue(datas.get(n).getMaxValue());
                                configItem.setVirtualMinValue(datas.get(n).getMinValue());
                                configItem.setType(type);
                                items.add(configItem);
                            }

                            json = (JSON) JSON.toJSON(items);
                            jsonDir = FileUtils.getFileDir(Const.SaveFile.getBaseDir("colors"));
                            jsonFilePath = Const.SaveFile.getJsonColorFilePaht(jsonDir, "appul");
                            saveFile( json.toString(), jsonFilePath);
                            break;

                    }

                }
            }).run();


    }
    public static List<ColorConfigItem> getColor(String json) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Type types = new TypeToken<ArrayList<ColorConfigItem>>() {
        }.getType();
        List<ColorConfigItem> colors = gson.fromJson(json, types);
        return colors;
    }
    public static void saveFile(String str, String fileAbsolutePathName){
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
