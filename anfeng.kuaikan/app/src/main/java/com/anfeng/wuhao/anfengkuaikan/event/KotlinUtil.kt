package com.anfeng.wuhao.anfengkuaikan.event

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.anfeng.wuhao.anfengkuaikan.base.MainApplication
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil

/**
 * Created by Administrator on 2017/8/22.
 */
object KotlinUtil {

    @JvmStatic
    fun doSomething(msg:String){
         ToastUtil.toastShort(msg)
    }

    /**
     *  dp转px
     */
    @JvmStatic
    fun dipTransformPx(dipValue: Double):Int= (dipValue* (MainApplication.getContext().resources.displayMetrics.density) +0.5f).toInt()

    /**
     *  px 转 dp
     */
    @JvmStatic
    fun pxTransformDip(pxValue:Float): Int= (pxValue / (MainApplication.getContext().resources.displayMetrics.density)+0.5f).toInt()

    /**
     *  沉浸式状态栏设置
     */
    fun setTransparencyStausBar(activity:Activity){
         if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){  //  大于4.4.4 版本
             val localLayoutParams =activity.window.attributes
             localLayoutParams.flags=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
             if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                 activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                 activity.window.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                 activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                 activity.window.statusBarColor = Color.TRANSPARENT
             }
         }
    }

}