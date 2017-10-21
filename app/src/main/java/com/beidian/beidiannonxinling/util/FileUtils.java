package com.beidian.beidiannonxinling.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.beidian.beidiannonxinling.common.Const;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.beidian.beidiannonxinling.common.Const.SaveFile.ROOT_FILE_DIR;

/**
 * Created by shanpu on 2017/8/31.
 * <p>
 */

public class FileUtils {


    /**
     * @param dirName 文件夹名称
     * @return 文件夹路径 String字符串
     */
    public static String getFileDir(String dirName) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LogUtils.i("SD卡不可用");
            return null;
        }
        File filePath = new File(Environment.getExternalStorageDirectory(), dirName);
        if (!filePath.exists()) {
            boolean mkdirs = filePath.mkdirs();
            if (mkdirs) {
                LogUtils.i(Const.SaveFile.FILE_DIR_CREATE_SUCCESS + filePath.getAbsolutePath());
                return filePath.getAbsolutePath();
            } else {
                LogUtils.i(Const.SaveFile.FILE_DIR_CREATE_FAILED);
                return null;
            }
        }
        LogUtils.i(filePath.getAbsolutePath() + "文件夹已存在");
        return filePath.getAbsolutePath();
    }

    public static String getImagePath() {
        return getFileDir(Const.SaveFile.IMAGE_DIR);
    }


    /**
     * 创建以工单号命名的文件夹
     *
     * @param workOrder 工单号
     * @return 以工单号命名的文件夹
     */
    public static String getWorkerOrderDir(String workOrder) {
        String WORK_ORDER_DIR = ROOT_FILE_DIR + File.separator + workOrder;
        return getFileDir(WORK_ORDER_DIR);
    }


    /**
     * @param context
     * @param str
     * @param fileAbsolutePathName 文件绝对路径名称
     */
    public static void saveFile(Context context, String str, String fileAbsolutePathName) {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!hasSDCard) {
            ToastUtils.makeText(context, "SD卡不可用");
            return;
        }

        if (TextUtils.isEmpty(fileAbsolutePathName)) {
            ToastUtils.makeText(context, "文件名不能为空");
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

    /**
     * 创建固定大小的文件
     * @param filePath
     * @param length
     * @throws IOException
     */
    public static void createFixLengthFile(String filePath, long length) throws IOException{
        long start = System.currentTimeMillis();
        Log.d(TAG, "createFixLengthFile: "+filePath);
        FileOutputStream fos = null;
        FileChannel output = null;
        File file = new File(filePath);
        if (!file.exists()) {
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
        }
        try {
            fos = new FileOutputStream(file);
            output = fos.getChannel();
            output.write(ByteBuffer.allocate(1), length-1);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("total times "+(end-start));
    }
    /**
     * @param context 上下文
     * @param str 需要写入的数据
     * @param fileAbsolutePathName 文件绝对路径名称
     * @param isInsert 是否从文件的最后插入数据
     */
    public static void saveFile(Context context, String str, String fileAbsolutePathName, boolean isInsert) {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!hasSDCard) {
            ToastUtils.makeText(context, "SD卡不可用");
            return;
        }

        if (TextUtils.isEmpty(fileAbsolutePathName)) {
            ToastUtils.makeText(context, "文件名不能为空");
            return;
        }

        File file = new File(fileAbsolutePathName);
        //如果父目录不存在则创建文件夹
        File parentFileDir = file.getParentFile();
//        Log.d(TAG, "parentFileDir: "+parentFileDir);
        if (!parentFileDir.exists()) {
            parentFileDir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            if (isInsert) {
                fos = new FileOutputStream(file, true);

            } else {
                fos = new FileOutputStream(file);
            }
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
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If Abean deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
          //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    public static String readSDFile(String paths) {
        StringBuffer sb = new StringBuffer();
        File file = new File(paths);
        try {
            InputStreamReader fis = new InputStreamReader(new FileInputStream(file), "utf-8");
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);

            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String readSDFile(File file) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader fis = new InputStreamReader(new FileInputStream(file), "utf-8");
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }


    public static String readFile(String filepath) {
        StringBuilder result = new StringBuilder();
        if(FileUtils.fileIsExists(filepath)){
            try {
                BufferedReader br = new BufferedReader(new FileReader(filepath));
                String s = null;
                while ((s = br.readLine()) != null) {
                    result.append(s);
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();//http://blog.csdn.net/chaoyue0071/article/details/47045629

        }else {
            Log.d("readFile","文件不存在");
        }
        return null;

    }
 public static List<String> readFileOnLine(String filepath) {
        File file=new File(filepath);
     if(!file.exists()){
        return  null;
     }
     List<String> mList=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String s = null;
            while ((s = br.readLine()) != null) {
                mList.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;//http://blog.csdn.net/chaoyue0071/article/details/47045629
    }








    public static void save(String path, String fileName, Throwable ex) {

        StringBuffer sb = new StringBuffer();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fos = new FileOutputStream(path + fileName);
            fos.write(sb.toString().getBytes());


        } catch (Exception e) {
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


    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }



    /**
     * 获取该路径下所以的文件夹或文件
     * @param path 路径
     * @param flag true 返回文件夹 false所有文件
     * @return
     */
    public static List<File> getFiles(String path,boolean flag) {

        List<File> fileList = new ArrayList<>();
        fileList.clear();

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                Log.d("getFiles","文件夹是空的");
                return fileList;
            } else {
                for (File file2 : files) {
                    if(flag){
                        if(file2.isDirectory()){
                            fileList.add(file2);
                        }
                    }else {
                        if(!file2.isDirectory()){
                            fileList.add(file2);
                        }
                    }

                }
            }
        } else {
            Log.d("getFiles","文件不存在");
        }
        return fileList;
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static int copy(String fromFile, String toFile)
    {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if(!root.exists())
        {
            return -1;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if(!targetDir.exists())
        {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for(int i= 0;i<currentFiles.length;i++)
        {
            if(currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            }else//如果当前项为文件则进行文件拷贝
            {
                CopySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return 0;
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int CopySdcardFile(String fromFile, String toFile)
    {

        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex)
        {
            return -1;
        }
    }
}
