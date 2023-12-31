package com.meta.commons.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Random;

public class RandomUtil {

    public static final String COUPONS_SOURCE = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static Random random = new Random(new Date().getTime());

    public static String getNext() {
        return String.valueOf(random.nextLong());
    }

    public static String getNumbers(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String getRandomString(String source, int length) {
        if (StringUtils.isBlank(source)) {
            source = COUPONS_SOURCE;// 含有字符和数字的字符串
        }
        Random random = new Random();// 随机类初始化
        StringBuilder sb = new StringBuilder();// StringBuffer类生成，为了拼接字符串

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(source.length());

            sb.append(source.charAt(number));
        }
        return sb.toString();
    }
}
