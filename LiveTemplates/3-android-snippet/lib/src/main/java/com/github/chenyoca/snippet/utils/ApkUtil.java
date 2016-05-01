package com.github.chenyoca.snippet.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-8-11
 * APK相关功能帮助器类
 */
public class ApkUtil {

	/**
	 * 判断APK包是否已经安装
	 * @param context 上下文，一般为Activity
	 * @param packageName 包名
	 * @return 包存在则返回true，否则返回false
	 */
	public static boolean isPackageExists(Context context, String packageName) {
		if (null == packageName || "".equals(packageName)) {
			throw new IllegalArgumentException("Package name cannot be null or empty !");
		}
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
			return null != info;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 安装指定APK文件
	 * @param activity Activity
	 * @param apkFile APK文件对象
	 */
	public static void install(Activity activity, File apkFile) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}
	
	/**
	 * 启动一个指定包名的应用的默认Activity
	 * @param activity Activity
	 * @param packageName 包名
	 */
	public static void launch(Activity activity,String packageName){
		Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
		if( null != intent ){
			activity.startActivity(intent);
		}
	}
	
}
