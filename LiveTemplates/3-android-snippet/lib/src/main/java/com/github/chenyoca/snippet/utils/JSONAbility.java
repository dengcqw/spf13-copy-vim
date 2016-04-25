package com.github.chenyoca.snippet.utils;


import java.lang.reflect.Field;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2013-4-7
 * 将当前对象的成员全部按JSON格式输出
 */
public class JSONAbility {
	
	@Override
	public final String toString(){
		return parseFieldsToJSON().toString();
	}
	
	public StringBuffer parseFieldsToJSON(){
		Class<?> clazz = this.getClass();  
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer buffer = new StringBuffer("{");
		for(Field f : fields){
			try {
				buffer.append(f.getName()).append(":");
				Object value = f.get(this);
				if(value != null) {
					if(value instanceof String){
						buffer.append("'").append(value).append("'").append(", ");
					}else{
						buffer.append(value).append(", ");
					}
				}else{
					buffer.append("''").append(", ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int offset = buffer.length();
		buffer.replace(offset-2, offset, "");
		return buffer.append("}");
	}
	
}
