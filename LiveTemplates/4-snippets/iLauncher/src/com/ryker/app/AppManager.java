package com.ryker.app;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

public class AppManager {
	/**
	 * 
	 * @return
	 */
	@SuppressLint("NewApi")
	public static ArrayList<AppInfo> getInstalledAppList(Context context){
		PackageManager pManager=context.getPackageManager();
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
		List<PackageInfo> packages = pManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for(int i=0;i<packages.size();i++) { 
	        PackageInfo pInfo = packages.get(i); 
	        AppInfo app = new AppInfo();  
	        app.setPackageName(pInfo.packageName); 
	        app.setVersionName(pInfo.versionName); 
	        app.setVersionCode(pInfo.versionCode); 
	        app.setAppName(pInfo.applicationInfo.loadLabel(pManager).toString());
	        app.setAppIcon(pInfo.applicationInfo.loadIcon(pManager));
	        int flags=pInfo.applicationInfo.flags;
	        if((flags&ApplicationInfo.FLAG_SYSTEM)!=0){
	        	app.setInSystem(true);
	        }else{
	        	app.setInSystem(false);
	        }
	        if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0){
	        	app.setInSdcard(true);
	        }else{
	        	app.setInSdcard(false);
	        }
	        if((flags&ApplicationInfo.FLAG_SYSTEM)<=0||
	        		(flags&ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)!=0){  
                app.setThirdPart(true);
            }else{
            	app.setThirdPart(false);
            }
	        app.setDataDir(pInfo.applicationInfo.dataDir);
	        app.setTargetSdkVersion(pInfo.applicationInfo.targetSdkVersion);
	        String os=null;
	        switch(app.getTargetSdkVersion()){
	        case 1:
	        	os="Android 1.0(BASE)";
	        	break;
	        case 2:
	        	os="Android 1.1(BASE_1_1)";
	        	break;
	        case 3:
	        	os="Android 1.5(CUPCAKE)";
	        	break;
	        case 4:
	        	os="Android 1.6(DONUT)";
	        	break;
	        case 5:
	        	os="Android 2.0(ECLAIR)";
	        	break;
	        case 6:
	        	os="Android 2.0.1(ECLAIR_0_1)";
	        	break;
	        case 7:
	        	os="Android 2.1(ECLAIR_MR1)";
	        	break;
	        case 8:
	        	os="Android 2.2(FROYO)";
	        	break;
	        case 9:
	        	os="Android 2.3(GINGERBREAD)";
	        	break;
	        case 10:
	        	os="Android 2.3.3(GINGERBREAD_MR1)";
	        	break;
	        case 11:
	        	os="Android 3.0(HONEYCOMB)";
	        	break;
	        case 12:
	        	os="Android 3.1(HONEYCOMB_MR1)";
	        	break;
	        case 13:
	        	os="Android 3.2(HONEYCOMB_MR2)";
	        	break;
	        case 14:
	        	os="Android 4.0(ICE_CREAM_SANDWICH)";
	        	break;
	        case 15:
	        	os="Android 4.0.3(ICE_CREAM_SANDWICH_MR1)";
	        	break;
	        case 16:
	        	os="Android 4.1(JELLY_BEAN)";
	        	break;
	        case 17:
	        	os="Android 4.2(JELLY_BEAN_MR1)";
	        	break;
	        }
	        app.setTargetOsVersion(os);
	        app.setProcessName(pInfo.applicationInfo.processName);
	        app.setPublicSourceDir(pInfo.applicationInfo.publicSourceDir);
	        app.setNativeLibDir(pInfo.applicationInfo.nativeLibraryDir);
	        app.setFirstInstallTime(pInfo.firstInstallTime);
	        app.setLastUpdateTime(pInfo.lastUpdateTime);
	        appList.add(app);
	    }
		return appList;
	}
	
	/**
	 * 
	 * @param context
	 * @param packageName
	 */
	@SuppressLint("InlinedApi")
	public static void openSys(Context context,String packageName){
		Intent intent = new Intent();  
	    final int apiLevel = Build.VERSION.SDK_INT;  
	    if (apiLevel >= 9) {
	        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);   
	        intent.setData(Uri.fromParts("package", packageName, null));  
	    } else {  
	        intent.setAction(Intent.ACTION_VIEW);  
	        intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");  
	        String appPkgName=(apiLevel==8?"pkg":"com.android.settings.ApplicationPkgName");
	        intent.putExtra(appPkgName, packageName);  
	    }  
	    context.startActivity(intent);  
	}
	
	/**
	 * 
	 * @param context
	 * @param packageName
	 */
	public static void uninstall(Context context,String packageName){
		Uri uri = Uri.fromParts("package", packageName, null);
		Intent it = new Intent(Intent.ACTION_DELETE, uri);
		context.startActivity(it);
	}
	
	@SuppressLint("NewApi")
	private static boolean requestRootPermission(String path) {
	    Process process = null;
	    DataOutputStream os = null;
	    try {
	        process = Runtime.getRuntime().exec("su");
	        os = new DataOutputStream(process.getOutputStream());
	        os.writeBytes("chmod 777 -R "+path+"\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        process.waitFor();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (os != null) {
	                os.close();
	            }
	            process.destroy();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    return true;
	}

	/**
	 * 
	 * @param context
	 * @param packageName
	 */
	@SuppressLint("NewApi")
	public static void clearCache(Context context,String packageName){
		Context pContext=null;
		try {
			pContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(pContext.getCacheDir()!=null){
			File root = pContext.getCacheDir();
			if(root.exists()&&requestRootPermission(root.getPath())){
				for(String dir:root.list()){
					deleteDir(new File(root, dir));
				}
				Toast.makeText(context, "内部缓存已清空", Toast.LENGTH_SHORT).show();
	        }
		}
		if(pContext.getExternalCacheDir()!=null){
			File root = pContext.getExternalCacheDir();
			if(root.exists()&&requestRootPermission(root.getPath())){
				for(String dir:root.list()){
					deleteDir(new File(root, dir));
				}
				Toast.makeText(context, "SD卡缓存已清空", Toast.LENGTH_SHORT).show();
	        }
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param packageName
	 */
	@SuppressLint("NewApi")
	public static void clearData(Context context,String packageName){
		Context pContext=null;
		try {
			pContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(pContext.getCacheDir()!=null){
			String path1=pContext.getCacheDir().getParent();
			File root1 = new File(path1);
			if(root1.exists()&&requestRootPermission(path1)){
				for(String dir:root1.list()){
					deleteDir(new File(root1, dir));
				}
				Toast.makeText(context, "内部数据已清空", Toast.LENGTH_SHORT).show();
	        }
		}
		if(pContext.getExternalCacheDir()!=null){
			String path2=pContext.getExternalCacheDir().getParent();
			File root2 = new File(path2);
			if(root2.exists()&&requestRootPermission(path2)){
				for(String dir:root2.list()){
					deleteDir(new File(root2, dir));
				}
				Toast.makeText(context, "SD卡数据已清空", Toast.LENGTH_SHORT).show();
	        }
		}
	}
	
	private static void deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	        	deleteDir(new File(dir, children[i]));
	        }
	    }
    	dir.delete();
	} 
	
	/**
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean launch(Context context,String packageName){
		PackageManager pManager=context.getPackageManager();
		Intent intent=pManager.getLaunchIntentForPackage(packageName);
		if(intent==null){
			return false;
		}else{
			context.startActivity(pManager.getLaunchIntentForPackage(packageName));
			return true;
		}
	}
}