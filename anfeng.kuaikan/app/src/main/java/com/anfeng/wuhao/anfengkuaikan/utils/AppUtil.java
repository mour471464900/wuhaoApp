package com.anfeng.wuhao.anfengkuaikan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class AppUtil {
    /**
     * 设置当前Activity全透明状态
     *
     * @param activity 上下文
     */
    public static void setupActivityFullyTransparent(Activity activity) {
        activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 判断该集合是否为空（）
     *
     * @param list 某个集合
     * @param <T>  集合元素的类型
     * @return true   表示该集合内部的元素个数大于0
     * false  该集合为空，或者集合元素个数为0
     */
    public static <T> boolean isListEmpty(List<T> list) {
        if (null != list && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 获取屏幕的宽高
     * @param context 上下文
     * @return
     */
    public static int[] getDisplaySize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int[] size = new int[]{width, height};
        return size;
    }

    /**
     * 获取屏幕宽度
     * @param context   上下文
     * @return
     */
    public static int  getDisplayWidth(Context context){
          return  getDisplaySize(context)[0];
    }

    /**
     * 获取屏幕高度
     * @param context   上下文
     * @return
     */
    public static int  getDisplayHeight(Context context){
        return  getDisplaySize(context)[1];
    }

    /**
     * px转dip 像素转独立像素
     */
    public static int pxTransformDip(Context context, float pxValue) {
        // 屏幕密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip转px 独立像素转像素
     *
     */
    public static int dipTransformPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 读取json 数据
     * @param context
     * @param fileName
     * @return
     */
    public static  String getJson(Context context,String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.e("json","获取json数据成功");
        return stringBuilder.toString();
    }


}
