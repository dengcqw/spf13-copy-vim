package com.github.chenyoca.snippet.utils;

import java.lang.reflect.Field;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2013-4-7
 * 反射工具类
 */
public class ReflectUtil {

	/**
	 * 获取指定字段名的字段对象
	 * @param clazz 需要反射的类类型
	 * @param fieldName 字段名
	 * @return 字段对象。如果字段名不存在，返回null。
	 */
	public static Field getField(Class<?> clazz,String fieldName){
		Field field = null;
		try{
			field = clazz.getDeclaredField(fieldName);
		}catch(Exception e){
			Class<?> superClazz = clazz.getSuperclass();
			if(superClazz != null) return getField(superClazz,fieldName);
		}
		return field;
	}

	/**
	 * 读取对象指定字段名的值
	 * @param object 需要读取数据的对象
	 * @param fieldName 字段名
	 * @return 数据值
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getFieldValue(Object object,String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(object.getClass(),fieldName);
		if( field != null ){
			field.setAccessible(true);
			return field.get(object);
		}
		return null;
	}

	/**
	 * 设置对象指定字段的值
	 * @param object 需要设置数据的对象
	 * @param fieldName 字段名
	 * @param value 数据值
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setFieldValue(Object object,String fieldName,Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(object.getClass(),fieldName);
		if(field != null){
			field.setAccessible(true);
			field.set(object, value);
		}
	}
}
