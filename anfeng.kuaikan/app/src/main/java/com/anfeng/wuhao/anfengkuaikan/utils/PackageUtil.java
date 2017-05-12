package com.anfeng.wuhao.anfengkuaikan.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取手机安装应用列表工具类
 */
public class PackageUtil {

    public static final String TAG = "PackageUtil";
    public static class AppInfo{
       private String appName ;        // 应用名称
        private String packageName;     // 应用包名
        private Drawable drawable  ;    //    应用图标
        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        @Override
        public String toString() {
            return "AppInfo{" + "应用名称：'" + appName + '\'' + ",该应用包名：'" + packageName + '\'' + '}';
        }
    }

    /**
     * 获取手机中安装应用的列表
     * @param context 上下文
     * @return 返回应用列表
     */
    public static List<AppInfo> getAppInfoList(Context context) {
        List<AppInfo> mList = new ArrayList<>();
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);//获取已经安装的
        for (PackageInfo packageInfo : packages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//非系统应用
                AppInfo appInfo=new AppInfo();
                String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                appInfo.setAppName(appName);//包名
                appInfo.setPackageName(packageInfo.packageName);
                appInfo.setDrawable(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            }
        }
        return mList;
    }

}
