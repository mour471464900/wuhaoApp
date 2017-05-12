package com.anfeng.wuhao.anfengkuaikan.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 弹toastShort
 * 2015年12月15日 16:28:10 周作威
 * @author Administrator
 *
 */
public class ToastUtil {
	public static long LAST_CLOCK_TIME;

	public static void toastShort(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	public static void toastShort(Context context, int resId) {
		Toast.makeText( context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	public static void toastLong(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
	}

	// 防误点
	public synchronized static boolean isFastClick() {
		long time = System.currentTimeMillis();
		if (time - LAST_CLOCK_TIME < 500) {
			return true;
		}
		LAST_CLOCK_TIME = time;
		return false;
	}
}
