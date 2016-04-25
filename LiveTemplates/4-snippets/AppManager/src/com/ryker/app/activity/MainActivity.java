package com.ryker.app.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.ryker.app.AppInfo;
import com.ryker.app.AppManager;
import com.ryker.app.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ArrayList<AppInfo> appList=AppManager.getInstalledAppList(this);
		ListView lv=(ListView) findViewById(R.id.lv);
		lv.setAdapter(new BaseAdapter(){
			@Override
			public int getCount() {
				return appList.size();
			}
			@Override
			public Object getItem(int position) {
				return appList.get(position);
			}
			@Override
			public long getItemId(int position) {
				return position;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				convertView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_item, null);
				AppInfo app=appList.get(position);
				ImageView iv=(ImageView)convertView.findViewById(R.id.iv);
				iv.setImageDrawable(app.getAppIcon());
				TextView tv_name=(TextView)convertView.findViewById(R.id.tv_name);
				tv_name.setText(app.getAppName());
				if(app.isSys()){
					tv_name.setTextColor(Color.RED);
				}else{
					tv_name.setTextColor(Color.BLACK);
				}
				return convertView;
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				AppInfo app=appList.get(position);
				AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(app.getAppName())
				.setIcon(app.getAppIcon());
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
				String msg="�汾: "+app.getVersionName()+"("+app.getVersionCode()+");\n\n"
						+"�Ƿ�ϵͳӦ��: "+(app.isSys()?"��":"��")+";\n\n"
						+"������: "+app.getProcessName()+";\n\n"
						+"Ŀ��ϵͳ�汾: "+app.getTargetOsVersion()+";\n\n"
						+"Ŀ��SDK�汾: API "+app.getTargetSdkVersion()+";\n\n"
						+"����: "+app.getPackageName()+";\n\n"
						+"Դ�ļ�λ��: "+app.getPublicSourceDir()+";\n\n"
						+"�����ļ�λ��: "+app.getDataDir()+";\n\n"
						+"���ؿ�λ��: "+app.getNativeLibDir()+";\n\n"
						+"�״ΰ�װʱ��: "+sdf.format(new Date(app.getFirstInstallTime()))+";\n\n"
						+"�������ʱ��: "+sdf.format(new Date(app.getLastUpdateTime()))+";"
						;
				builder.setMessage(msg)
				.setPositiveButton(getString(android.R.string.ok), null)
				.create().show();
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				final AppInfo app=appList.get(position);
				final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
				builder.setItems(getResources().getStringArray(R.array.menu_items), new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int position) {
						switch(position){
						case 0:
							AppManager.launch(MainActivity.this,app.getPackageName());
							break;
						case 1:
							AppManager.clearCache(MainActivity.this,app.getPackageName());
							break;
						case 2:
							builder.setMessage("ȷ������������ݣ�")
							.setPositiveButton(android.R.string.yes, new OnClickListener(){
								@Override
								public void onClick(DialogInterface arg0,int arg1) {
									AppManager.clearData(MainActivity.this,app.getPackageName());
								}
							})
							.setNegativeButton(android.R.string.cancel, null)
							.create().show();
							break;
						case 3:
							AppManager.uninstall(MainActivity.this,app.getPackageName());
							break;
						case 4:
							AppManager.openSys(MainActivity.this,app.getPackageName());
							break;
						}
					}
				})
				.setNegativeButton(getString(android.R.string.cancel), null)
				.create().show();
				return false;
			}
		});
	}
}