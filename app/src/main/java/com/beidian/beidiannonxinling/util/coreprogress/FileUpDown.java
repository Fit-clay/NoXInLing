package com.beidian.beidiannonxinling.util.coreprogress;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @version V1.0
 * @创建者: lilei
 * @创建时间: 2017/9/616:41
 * @描述: ------------------------------------------
 */

public class FileUpDown {
//    private static Dialog upDialog;
//    private static ProgressBar progBar;
    public interface FileUpChange{
        void changePositong(float position);
        void onSuccess();
        void onFail();
    }

    public static void upload(final Context context, String upFilePath, String url, final FileUpChange fileUpChange) {
//        uploadInfo.setText("start upload");开始上传
        //创建上传文件,上传url
        File apkFile = new File(upFilePath);
//        String url = "http://192.168.3.219:9009/app/upload1";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2000, TimeUnit.SECONDS)
                .readTimeout(2000, TimeUnit.SECONDS)
                .writeTimeout(2000,TimeUnit.SECONDS)
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        //        MediaType mediaType = new MediaType();
        MediaType JSON = MediaType.parse("multipart/form-data");
        String MULTIPART_FORM_DATA = "multipart/form-data";
        bodyBuilder.addFormDataPart("test", apkFile.getName(), RequestBody.create(JSON, apkFile));

        MultipartBody build = bodyBuilder.build();

        RequestBody requestBody = ProgressHelper.withProgress(build, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);
                Log.e("TAG", "onUIProgressStart:" + totalBytes);
//                Toast.makeText(getApplicationContext(), "开始上传：" + totalBytes, Toast.LENGTH_SHORT).show();
//                upDialog= DialogUtil.customProgress(context,"正在上传",new DialogUtil.OnProgressDialogCallBack(){
//                    @Override
//                    public void getProgressbar(ProgressBar progressBar) {
//                        progBar=progressBar;
//                    }
//                });
            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                Log.e("TAG", "=============start===============");
                Log.e("TAG", "numBytes:" + numBytes);
                Log.e("TAG", "totalBytes:" + totalBytes);
                Log.e("TAG", "percent:" + percent);
                Log.e("TAG", "speed:" + speed);
                Log.e("TAG", "============= end ===============");
//
//                uploadProgress.setProgress((int) (100 * percent));
//                uploadInfo.setText("numBytes:" + numBytes + " bytes" + "\ntotalBytes:" + totalBytes + " bytes" + "\npercent:" + percent * 100 + " %" + "\nspeed:" + speed * 1000 / 1024 / 1024 + "  MB/秒");
                fileUpChange.changePositong(percent);
            }

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();
                Log.e("TAG", "onUIProgressFinish:");
//                Toast.makeText(getApplicationContext(), "结束上传", Toast.LENGTH_SHORT).show();
                fileUpChange.onSuccess();
            }
        });
        builder.post(requestBody);
        Call call = okHttpClient.newCall(builder.build());


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "=============onFailure===============");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", "=============onResponse===============");
                Log.e("TAG", "request headers:" + response.request().headers());
                Log.e("TAG", "response headers:" + response.headers());
            }
        });
    }
}
