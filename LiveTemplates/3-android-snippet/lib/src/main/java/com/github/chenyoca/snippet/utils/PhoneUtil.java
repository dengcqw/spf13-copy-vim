package com.github.chenyoca.snippet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date    : 2012-7-21
 * 电话工具类
 */
public class PhoneUtil {

	/**
	 * 直接拨打电话
	 * @param activity Activity
	 * @param phoneNumber 电话号码
	 */
	public static void makeCall(Activity activity, String phoneNumber){
		Intent dialIntent = new Intent(Intent.ACTION_CALL,Uri.parse(String.format("tel:%s", phoneNumber)));
		try{
		    activity.startActivity(dialIntent);
	    }catch(Exception e){
	        Toast.makeText(activity,
	                "无法拨打电话！可能本应用拨打电话的权限被安全软件禁用，请您检查后重试。", Toast.LENGTH_LONG).show();
	    }
	}
	 
	/**
	 * 直接发送信息
	 * @param activity Activity
	 * @param receive 接收方号码
	 * @param content 信息内容
	 */
	public static void sms(Activity activity, String receive, String content){
		Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms://")); 
		sendIntent.putExtra("address", receive);
		sendIntent.putExtra("sms_body", content);
		try{
			activity.startActivity(sendIntent);
	    }catch(Exception e){
	        Toast.makeText(activity, 
	                "无法发送！可能本应用拨打电话的权限被安全软件禁用，请您检查后重试。", Toast.LENGTH_SHORT).show();
	    }
	}

    /**
     * 调用系统短信发送界面并预设接收号码、短信内容。
     * @param activity Activity
     * @param receive 接收方号码
     * @param content 预设内容
     */
    public static void preSMS(Activity activity,String receive,String content){
        Uri smsToUri = Uri.parse(String.format("smsto:%s",receive));
        Intent intent = new Intent( Intent.ACTION_SENDTO, smsToUri );
        intent.putExtra("sms_body", content);
        activity.startActivity( intent );
    }

    /**
     * 发送到
     * @param activity Activity
     * @param subject 主题
     * @param content 内容
     * @param receivers 接收者，可以多个
     */
    public static void mailTo(Activity activity,String subject,String content,String...receivers){
        Intent emailIntent=new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
        emailIntent.setType("message/rfc822");
        activity.startActivity(emailIntent);
    }
	
	/**
	 * 获取手机号码
	 * @param context Context
	 * @return 手机号码
	 */
	public static String getPhoneNum(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
	
}
