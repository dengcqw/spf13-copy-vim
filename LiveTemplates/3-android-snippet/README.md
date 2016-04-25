# Android Snippet - Android代码小片段

> 复用率非常高的代码。

这是从我工作的项目中分离出来的，复用率非常高的代码。

## Dependency

Add repository

```groovy

    allprojects {
     repositories {
         // !!!! ADD THIS !!!!
         maven{ url 'http://oss.sonatype.org/content/groups/public/' }
     }
    }

```

Add dependency

```groovy

    dependencies {
        compile 'com.github.chenyoca:android-snippet:1.2-SNAPSHOT@aar'
    }

```


----

## DelaySwitchActivity

#### 作用

作闪屏Activity功能用。设定跳转目标和延时时间。

#### 使用

```java

    public class SplashActivity extends DelaySwitchActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // CONFIG !!!
            getConfig().target(TargetActivity.class).delayed(2000);
        }

    }

```

----

## 常用的适配器 - Adapters


### FastCellAdapter - Holder View Like Adapter

用于ListView、GridView等组件。通过 `ViewCreator2` 接口 来将创建View的任务从Adapter中分离。

`FastCellAdapter` 类似 HolderViewAdapter，解决了 cell view 被多次 Inflate 的问题。

### SimpleAdapter

#### 使用

```java

    ViewCreator<ValueObject> viewCreator = new ViewCreator(){
        ...
    };

    SimpleAdapter<ValueObject> adapter = new SimpleAdapter(activity.getLayoutInflater(), viewCreator);

    ListView listView = ....

    listView.setAdapter(adapter);

    List<ValueObject> dataSet = ...

    adapter.update(dataSet);
    adapter.notifyDataSetChanged();

```

----

## Android 系统相关的工具类 - Android System Utils

### AppWideBroadcast

#### 作用

在应用内部发送广播，与全局广播相比，提高性能和广播效率。

#### 使用


> 注册广播接收器： AppWideBroadcast.register(...)

> 发送广播： AppWideBroadcast.send(...)

> 反注册广播接收器： AppWideBroadcast.unregister(...)

----

## 辅助工具类 - Helper Utils

### ActivityUtil

	使用最多的工具类，包含Activity常用设置方法。如全屏，去掉标题栏，显示Toast，隐藏/显示输入法，计算px与dp等方法。

### ApkUtil

	Apk文件检测是否存在，安装指定APK文件。

### InputStreamUtil

	输入流转换工具。可将InputStream转换成Bitmap,BitmapDrawable,String,StringBuffer,Byte数组等。

### DeviceUtil

	设备信息

### EncryptUtil

	Hash Encrypt工具，Hash类加密：MD5，SHA-1, SHA-256

### Exlog

	调试必备！可以输入调试信息在哪一个类哪一行的辅助工具类。包括：取得当前代码所在方法名、输出调用方法链等方法。

### InputStreamUtil

	将输入流转换成其它数据：如字符串，图片文件等。

### IntentUtil

	常用的Intent

### JSONAbility

	将ValueObject输入为JSON格式字符串

### NetworkUtil

	网络状态判断

### PhoneUtil

	打电话，发信息

### RandomUtil

	生成最大值范围的随机数，指定长度的随机字符串。

### ReflectUtil

	反射工具类


## 开源协议 Apache License 2.0

The code of this project is released under the Apache License 2.0, see [LICENSE](https://github.com/chenyoca/async-http-connection-core/blob/master/LICENSE)

