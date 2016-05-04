package com.github.chenyoca.snippet.utils;

import java.util.Random;

/**
 * @author : 桥下一粒砂 chenyoca@gmail.com
 * date   : 2012-11-14
 * 随机数据生成工具
 */
public class RandomUtil {

	/**
	 * 生成指定长度的随机字符内容
	 * @param length 长度
	 * @return 字符内容
	 */
	public static String randomString(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int t = 1; t < length; t++) {
            long time = System.currentTimeMillis() + t;
            if (time % 3 == 0) {
                buffer.append((char) time % 9);
            } else if (time % 3 == 1) {
                buffer.append((char) (65 + time % 26));
            } else {
                buffer.append((char) (97 + time % 26));
            }
        }
        return buffer.toString();
    }

	/**
	 * 生成指定[0,limit)范围的随机数
	 * @param limit 最大值
	 * @return 随机数
	 */
	public static int limitInt(int limit){
		return Math.abs(new Random().nextInt(limit));
	}
	
}
