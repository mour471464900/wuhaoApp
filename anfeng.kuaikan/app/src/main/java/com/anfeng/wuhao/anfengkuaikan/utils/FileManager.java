package com.anfeng.wuhao.anfengkuaikan.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;

import java.io.File;
import java.util.List;

/**
 * 配置项和目录管理
 * 
 * @author GaiQS E-mail:gaiqs@sina.com
 * @Date 2015年2月6日
 * @Time 下午5:34:50
 */
public class FileManager {
	public static final File appFile;// 程序SD卡根目录
	public static final File appDownload;// 程序下载目录
	public static final File appCache;// 程序缓存目录
	public static final File appTempFile;// 临时文件目录
	public static final File appImageCache;// 图片缓存目录

	static {
		appFile = new File(Environment.getExternalStorageDirectory(), "/kuaikan/");
		appDownload = new File(appFile, "/download/");
		appCache = new File(appFile, "/cache/");
		appTempFile = new File(appFile, "/temp/");
		appImageCache = new File(appFile, "/imageCache/");
		fileMkdirs(appFile, appDownload,appCache);
	}


	/**
	 * mkdir() 只能在已经存在的目录中创建创建文件夹 mkdirs() 可以在不存在的目录中创建文件夹。既可以创建多级目录。
	 * 如果创建多个文件夹：/sdcard/temp/1206/100就需要使用dir.mkdirs();
	 * 
	 * @param files
	 */
	public static void fileMkdirs(File... files) {
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].exists()) {
					files[i].mkdirs();
				}
			}
		}
	}

	//批量删除文件

	public static void deleteFile(final String path) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				deletefile(path);
			}
		}).start();
	}

	public static void deleteMultipleFile(final List<String> paths) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (String path:paths) {
					deletefile(path);
				}
			}
		}).start();
	}

	public static void deleteAPK(final Context context, final String path) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if(deletefile(path)){
					Looper.getMainLooper().prepare();
					ToastUtil.toastShort("下载文件已删除");
					Looper.getMainLooper().loop();
				};
			}
		}).start();
	}




	// 删除文件
	public static boolean deletefile(String path) {
		boolean delete = false;
		try {
			File file = new File(path);
			delete = false;
			if (file.exists()) {
				delete = file.delete();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delete;
	}
	public static boolean deleteFile(File file) {
		if (file == null || !file.exists())
			return true;
		if (file.isFile()) {
				file.delete();
		} else if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				deleteFile(subFile);
			}
//			file.delete(); //xcr add
		}
		return true;
	}

}
