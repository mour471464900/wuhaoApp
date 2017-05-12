// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhoneUtil.java

package com.anfeng.wuhao.anfengkuaikan.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static String imei;
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    private static String TAG;

    public PhoneUtil() {
    }

    public static int dip2px(Context context, int i) {
        return (int) (0.5D + (double) (((float) context.getResources()
                .getDisplayMetrics().densityDpi / 160F) * (float) i));
    }

    public static void init(Context context) {
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        imei = getDeviceId(context);
    }




    public static int px2dip(Context context, float f) {
        return (int) (0.5F + f
                / context.getResources().getDisplayMetrics().density);
    }

    public static String getIMEI(Context context) {
        return ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public static int getScreenHeight() {
        return screenHeight;
    }


    public static int getScreenWidth() {

        return screenWidth;
    }


    public static int getScreenWidth(Context context) {

        return context.getResources().getDisplayMetrics().widthPixels;
    }



    public static int getScreenHeight(Context context)
    {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    public static boolean check(String str, String regex) {
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static final boolean isSdcardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static void sendIntent(Context context, Class classes, String key,
                                  int value) {
        Intent intent = new Intent();
        intent.setClass(context, classes);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * 网络是否可用
     *
     * @param Context
     * @return
     */
    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // @SuppressWarnings("deprecation")
    // @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    // public static boolean copy(String content, Context context) {
    //
    // //
    // SDK11起android.text.ClipboardManager被废弃，使用它的子类android.content.ClipboardManager替代，同样被废弃还有setText/getText/hasText方法，使用setPrimaryClip/getPrimaryClip/hasPrimaryClip替代
    // if (android.os.Build.VERSION.SDK_INT >= 11) {
    // android.content.ClipboardManager c = (android.content.ClipboardManager)
    // context.getSystemService(Context.CLIPBOARD_SERVICE);
    // try {
    // c.setPrimaryClip(ClipData.newPlainText("label", content));
    // } catch (Exception e) {
    // return false;
    // }
    // } else {//SDK11之前请使用android.text.ClipboardManager
    // android.text.ClipboardManager c = (android.text.ClipboardManager)
    // context.getSystemService(Context.CLIPBOARD_SERVICE);
    // c.setText(content);
    //
    // }
    // return true;
    // }

    public static boolean deleteFile(File file) {
        if (file == null || !file.exists())
            return true;
        if (file.isFile()) {
            file.delete();

        } else if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteFile(subFile);
            }
            file.delete(); // xcr add
        }
        return true;
    }

    public static String getMAC(Context context) {
        String mac = null;
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        try {
            WifiInfo info = wm.getConnectionInfo();
            mac = info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    public static String getIP(Context context) {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        int i = wifiManager.getWifiState();
        if (i != WifiManager.WIFI_STATE_ENABLED) {
            return "";
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return "";
        }
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;

    }

    public static String getIEMI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;

    }

    private static String intToIp(int i) {

        return (i & 0xFF) + "." +

                ((i >> 8) & 0xFF) + "." +

                ((i >> 16) & 0xFF) + "." +

                (i >> 24 & 0xFF);

    }

    //获取系统版本号
    public static String getSDKVersion() {
        String sdkVersion = "";
        try {
            sdkVersion = android.os.Build.VERSION.RELEASE;
        } catch (NumberFormatException e) {
            sdkVersion = "";
        }
        return sdkVersion;
    }

    /**
     * 获取手机品牌
     *
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
    /**
     * 获取手机型号
     *
     */
    public static String getModle() {
        return android.os.Build.MODEL;
    }


    /**
     * 获取唯一标识码
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        String dbPath = FileManager.appFile.getPath() + "/" + "deviceId.txt";//
        File file = new File(dbPath);
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String id = bf.readLine();
                if (id != null) {
                    deviceId = id;
                }
                bf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {// 文件不存在
            deviceId = ((TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE))
                    .getDeviceId();
            if (TextUtils.isEmpty(deviceId)
                    || "000000000000000".equals(deviceId)) {//真实有效的设备
                deviceId = UUID.randomUUID().toString();
            }
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                fw.write(deviceId);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogUtil.e("deviceid", "deviceid:" + deviceId);

        return deviceId;

    }

    /**
     * 判断是否有activity在运行
     *
     * @param context
     * @param activityName
     * @return
     */
    public static boolean isActivityOnForeground(Context context,
                                                 String activityName) {
        LogUtil.e(TAG, "in isAppOnForeground() activityName is " + activityName);
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            String topActivity = tasksInfo.get(0).topActivity.getClassName();
            LogUtil.e(TAG, "topActivity is " + topActivity);
            if (activityName.equals(topActivity)) {
                return true;
            }
        }
        return false;
    }














    //判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
    public static boolean isEmulator(Activity activity){

        try{
            TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            String imsi = tm.getSubscriberId();

            if (imei != null && imei.equals("000000000000000")){
                return true;
            }

            if (imsi != null && imsi.equals("310260000000000")){
                return true;
            }

            if(getSystemPropertie("ro.kernel.qemu").equals("1")){
                return true;
            }

            if(CheckQEmuDriverFile()){
                return true;
            }

            WifiManager wifimanage = (WifiManager)activity.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiinfo = wifimanage.getConnectionInfo();
            if(null == wifiinfo){
                return true;
            }

            String roHardware = getSystemPropertie("ro.hardware");

//			UtilsToast.toastShort("imei = " + imei + ", Build.MODEL = " + Build.MODEL +  ", Build.MANUFACTURER = " + Build.MANUFACTURER +  ", roHardware = " + roHardware);

            //下面的这个跟真机一样，所以不能要
            // BlueStacks 这款模拟器中的    || (roHardware.equals("smdk4x12"))
            return (roHardware.equals("goldfish"))     //
                    || (roHardware.equals("ttVM_x86"))  //天天模拟器
                    || (roHardware.equals("vbox86"))    //海马
//					|| (roHardware.equals("qcom"))  //51模拟器
//					|| (roHardware.equals("shamu"))  //夜神模拟器
                    || (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"))
                    || (Build.BRAND.equals("generic"));
        }catch (Exception ioe) {

        }
        return false;
    }

    public static Boolean CheckQEmuDriverFile(){
        String[]known_qemu_drivers = {"goldfish" };
        File driver_file = new File("/proc/tty/drivers");
        if(driver_file.exists()&& driver_file.canRead()){
            byte[]data =new byte[(int)driver_file.length()];
            try{
                InputStream inStream =new FileInputStream(driver_file);
                inStream.read(data);
                inStream.close();
            }catch (Exception e){
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            String driver_data =new String(data);
            for(String known_qemu_driver : known_qemu_drivers){
                if(driver_data.indexOf(known_qemu_driver)!= -1){
                    Log.v("Result:","Find known_qemu_drivers!");
                    return true;
                }
            }
        }
        Log.v("Result:","Not Find known_qemu_drivers!");
        return false;
    }

    /**
     * 获取系统属性值
     */
    public static String getSystemPropertie(String propertyName) {
        String value = "";
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Class[] paramTypes = new Class[1];
            paramTypes[0] = String.class;
            Method method = clazz.getMethod("get", paramTypes);

            Object object = clazz.newInstance();
            Object[] propertyNames = new Object[1];
            propertyNames[0] = propertyName;
            value = (String) method.invoke(object, propertyNames);
        } catch (Exception e) {
        }

        return value;
    }

}
