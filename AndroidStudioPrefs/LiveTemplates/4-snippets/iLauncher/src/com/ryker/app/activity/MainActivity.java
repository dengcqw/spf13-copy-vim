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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class MainActivity extends Activity {
	private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
	private ArrayList<AppInfo> data,appList;
	private BaseAdapter adapter;
	private int selectedId=0;
	private String YES,NO,VERSION_NAME,
	IS_IN_SYSTEM,IS_IN_SDCARD,IS_THIRD_PART,
	FIRST_INSTALL_TIME,LAST_UPDATE_TIME,
	PROCESS_NAME,PACKAGE_NAME,
	TARGET_OS_VERSION,TARGET_SDK_VERSION,
	SOURCE_DIR,DATA_DIR,NATIVE_LIB_DIR;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		YES=getString(R.string.yes);
		NO=getString(R.string.no);
		VERSION_NAME=getString(R.string.version_name);
		IS_IN_SYSTEM=getString(R.string.is_in_system);
		IS_IN_SDCARD=getString(R.string.is_in_sdcard);
		IS_THIRD_PART=getString(R.string.is_third_part);
		FIRST_INSTALL_TIME=getString(R.string.first_install_time);
		LAST_UPDATE_TIME=getString(R.string.last_update_time);
		PACKAGE_NAME=getString(R.string.package_name);
		PROCESS_NAME=getString(R.string.process_name);
		TARGET_OS_VERSION=getString(R.string.target_os_version);
		TARGET_SDK_VERSION=getString(R.string.target_sdk_version);
		SOURCE_DIR=getString(R.string.source_dir);
		DATA_DIR=getString(R.string.data_dir);
		NATIVE_LIB_DIR=getString(R.string.native_lib_dir);
		appList=AppManager.getInstalledAppList(this);
		data=(ArrayList<AppInfo>) appList.clone();
		GridView lv=(GridView) findViewById(R.id.gv);
		adapter=new BaseAdapter(){
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
				return convertView;
			}
		};
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				if(!AppManager.launch(MainActivity.this,appList.get(position).getPackageName())){
					Toast.makeText(MainActivity.this, R.string.launch_failed, Toast.LENGTH_SHORT).show();
				}
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				final AppInfo app=appList.get(position);
				final AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
				builder1.setItems(getResources().getStringArray(R.array.app_menu_items), new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int position) {
						switch(position){
						case 0:
							AlertDialog.Builder builder2=new AlertDialog.Builder(MainActivity.this);
							builder2.setTitle(app.getAppName())
							.setIcon(app.getAppIcon());
							String msg=VERSION_NAME+app.getVersionName()+"("+app.getVersionCode()+");\n\n"
									+IS_IN_SYSTEM+(app.isInSystem()?YES:NO)+";\n\n"
									+IS_THIRD_PART+(app.isThirdPart()?YES:NO)+";\n\n"
									+IS_IN_SDCARD+(app.isInSdcard()?YES:NO)+";\n\n"
									+PROCESS_NAME+app.getProcessName()+";\n\n"
									+TARGET_OS_VERSION+app.getTargetOsVersion()+";\n\n"
									+TARGET_SDK_VERSION+app.getTargetSdkVersion()+";\n\n"
									+PACKAGE_NAME+app.getPackageName()+";\n\n"
									+SOURCE_DIR+app.getPublicSourceDir()+";\n\n"
									+DATA_DIR+app.getDataDir()+";\n\n"
									+NATIVE_LIB_DIR+app.getNativeLibDir()+";\n\n"
									+FIRST_INSTALL_TIME+sdf.format(new Date(app.getFirstInstallTime()))+";\n\n"
									+LAST_UPDATE_TIME+sdf.format(new Date(app.getLastUpdateTime()))+";"
									;
							builder2.setMessage(msg)
							.setPositiveButton(getString(android.R.string.ok), null)
							.create().show();
							break;
						case 1:
							AppManager.clearCache(MainActivity.this,app.getPackageName());
							break;
						case 2:
							AlertDialog.Builder builder3=new AlertDialog.Builder(MainActivity.this);
							builder3.setMessage(R.string.sure_clear_data)
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.action_filter:
			filter();
			break;
		}
		return true;
	}
	
	private void filter(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final String[] items=getResources().getStringArray(R.array.sys_menu_items);
		builder.setSingleChoiceItems(items, selectedId, new OnClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(DialogInterface dialog, int position) {
				dialog.dismiss();
				selectedId=position;
				setTitle(items[position]);
				appList.clear();
				ArrayList<AppInfo> tmp=new ArrayList<AppInfo>();
				switch(position){
				case 0:
					tmp=(ArrayList<AppInfo>) data.clone();
					break;
				case 1:
					for(AppInfo app:data){
						if(app.isInSystem()){
							tmp.add(app);
						}
					}
					break;
				case 2:
					for(AppInfo app:data){
						if(app.isThirdPart()){
							tmp.add(app);
						}
					}
					break;
				case 3:
					for(AppInfo app:data){
						if(app.isInSdcard()){
							tmp.add(app);
						}
					}
					break;
				}
				appList.addAll(tmp);
				adapter.notifyDataSetChanged();
			}
		})
		.setNegativeButton(android.R.string.cancel, null);
		builder.create().show();
	}
}