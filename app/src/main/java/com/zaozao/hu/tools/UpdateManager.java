package com.zaozao.hu.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ProgressBar;

import com.zaozao.hu.BuildConfig;
import com.zaozao.hu.module.model.AppInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 胡章孝
 * Date:2018/7/13
 * Describe:软件版本更新类
 */
public class UpdateManager {
    private URL url = null;
    private static final String TAG = "UpdateManager";
    private static final int DOWNLOAD = 1;//下载中
    private static final int DOWNLOAD_FINISH = 2;//下载结束
    private String savePath;//下载保存路径
    private int progress;//下载进度
    private boolean cancelUpdate;//是否取消更新
    private Context context;
    private ProgressBar progressBar;//更新进度条
    private Dialog dialog;//更新对话框
    private MyHandler handler;
    private AppInfo appInfo;

    public UpdateManager(Context context, AppInfo appInfo) {
        this.context = context;
        this.appInfo = appInfo;
        handler = new MyHandler(new SoftReference<>(this));
    }

    static class MyHandler extends Handler {

        private SoftReference<UpdateManager> updateManagerSoftReference;

        public MyHandler(SoftReference<UpdateManager> updateManagerSoftReference) {
            this.updateManagerSoftReference = updateManagerSoftReference;
        }

        @Override
        public void handleMessage(Message msg) {
            UpdateManager manager = updateManagerSoftReference.get();
            if (manager != null) {
                switch (msg.what) {
                    case DOWNLOAD:

                        break;
                    case DOWNLOAD_FINISH:
                        Log.i(TAG, "下载完成");
                        manager.installAPK();
                        break;
                }
            }
        }
    }

    /**
     * 安装APK
     */
    private void installAPK() {
        File apkFile = new File(savePath, "AndroidNote");
        if (!apkFile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(i);
    }

    /**
     * 检查版本更新
     */
    public void checkUpdate() {
        if (isUpdate()) {
            showUpdateDialog();
        }
    }

    /**
     * 检查是否有最新版本
     *
     * @return
     */
    private boolean isUpdate() {
        Double versionCode = getVersionCode(context);
        Double serviceCode = Double.parseDouble(appInfo.getApp_version_code());
        return serviceCode > versionCode;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    private Double getVersionCode(Context context) {
        Double versionCode = 0.0;
        try {
            versionCode = (double) context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示更新弹框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("软件更新");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downloadAPK();
            }
        });
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void downloadAPK() {
        new DownloadAPKThread(appInfo.getApp_download_url()).start();
    }


    private class DownloadAPKThread extends Thread {

        private String downUrl;

        private DownloadAPKThread(String downUrl) {
            this.downUrl = downUrl;
        }

        @Override
        public void run() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String sdPath = Environment.getExternalStorageDirectory() + File.separator;
                savePath = sdPath + "download";
                try {
                    Log.i("downUrl", downUrl);
                    URL url = new URL(downUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                    int length = urlConnection.getContentLength();
                    Log.i(TAG, "文件大小：" + length);
                    InputStream is = urlConnection.getInputStream();
                    File file = new File(savePath);
                    boolean result = false;
                    if (!file.exists()) {
                        result = file.mkdir();
                    }
                    if (file.exists() || result) {
                        File apkFile = new File(savePath, "AndroidNote");
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        int numRead;
                        byte[] buffer = new byte[1024];
                        while ((numRead = is.read(buffer)) != -1) {
                            count += numRead;
                            progress = (int) (((float) count / length) * 100);
                            Log.i(TAG, "下载进度：" + progress);
                            handler.sendEmptyMessage(DOWNLOAD);
                            fos.write(buffer, 0, numRead);
                        }
                        handler.sendEmptyMessage(DOWNLOAD_FINISH);
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
