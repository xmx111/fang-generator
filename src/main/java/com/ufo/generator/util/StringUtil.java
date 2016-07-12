package com.ufo.generator.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 字符串工具类
 *
 * @author hekang
 * @created 2016/1/18
 */
public class StringUtil extends StringUtils {

    /**
     * 合法手机号验证
     *
     * @param mobile
     * @return
     */
    public static boolean isMoblile(String mobile) {
        String mobileString = "^0{0,1}(12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$";
        return mobile.matches(mobileString);
    }

    /**
     * 判断字符串编码
     * @param str
     * @param encode
     * @return
     */
    public static boolean isEncoding(String str, String encode) {
        try {
            encode = isBlank(encode) ? "utf-8" : encode;
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return true;
            }
        } catch (Exception exception) {
        }
        return false;
    }

    /**
     * DBC to SBC
     * space SBC 12288 = DBC 32
     * other SBC(33-126) + 65248 = DBC(65281-65374)
     * @param input
     * @return
     */
    public static String toSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * SBC to DBC
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 字符转整型
     * @param str
     * @return
     */
    public static List<Integer> splitToInteger(String str) {
        return splitToInteger(str, ",");
    }

    /**
     * 字符转整型
     * @param str
     * @return
     */
    public static List<Integer> splitToInteger(String str, String separtor) {
        List<Integer> values = new ArrayList<Integer>();
        if (isEmpty(str)) {
            return values;
        }
        for (String value : split(str, separtor)) {
            values.add(Integer.valueOf(value));
        }
        return values;
    }

    /**
     * 数字补零
     * @param num
     * @param size
     * @return
     */
    public static String addZero(int num, int size){
        String str = String.valueOf(num);
        while (str.length() < size){
            str = "0" + str;
        }
        return str;
    }
}
