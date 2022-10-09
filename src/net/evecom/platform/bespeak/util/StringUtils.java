/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.platform.bespeak.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 
 * 描述字符串处理工具类
 * 
 * @author Panda Chen
 * @created 2016-11-2 下午04:13:16
 */
public class StringUtils {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(StringUtils.class);
    /**
     * 
     * 构造函数：工具类
     * 
     */
    private StringUtils() {
    }

    /**
     * 
     * 描述将传入的ID列表转换成数据库的字符串，如：'aaaa','bbbb'
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:13:26
     * @param strList
     * @return
     */
    public static String listToDbString(List<String> strList) {
        StringBuffer result = new StringBuffer();

        if (strList == null || strList.size() == 0)
            return result.toString();

        for (String str : strList) {
            result.append("'").append(str).append("'").append(",");
        }

        return result.substring(0, result.length() - 1);
    }

    /**
     * 
     * 描述数字字符串转化为整数
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:13:35
     * @param srcStr
     * @param defaultValue
     * @return
     */
    public static int getInt(String srcStr, int defaultValue) {
        if (StringUtils.isNullBlank(srcStr))
            return defaultValue;
        int result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.intValue();
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 
     * 描述数字字符串转化为整数，在转化时发生异常，则返回0
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:13:43
     * @param srcStr
     * @return
     */
    public static int getInt(String srcStr) {
        return getInt(srcStr, 0);
    }

    /**
     * 
     * 描述判断传入的字符串是否为数字
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:13:50
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isNullBlank(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!Character.isDigit(a)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 描述数字字符串转化为双精数字
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:13:58
     * @param srcStr
     * @param defaultValue
     * @return
     */
    public static double getDouble(String srcStr, double defaultValue) {
        if (isNullBlank(srcStr))
            return defaultValue;
        double result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.doubleValue();
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 
     * 描述数字字符串转化为双精数字，在转化时发生异常，则返回0
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:05
     * @param srcStr
     * @return
     */
    public static double getDouble(String srcStr) {
        return getDouble(srcStr, 0);
    }

    /**
     * 
     * 描述 数字字符串转化为长整型
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:11
     * @param srcStr
     * @param defaultValue
     * @return
     */
    public static long getLong(String srcStr, long defaultValue) {
        if (isNullBlank(srcStr))
            return defaultValue;
        long result = defaultValue;
        try {
            Double db = new Double(srcStr);
            result = db.longValue();
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 
     * 描述数字字符串转化为长整型，在转化时发生异常，则返回0
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:18
     * @param srcStr
     * @return
     */
    public static long getLong(String srcStr) {
        return getLong(srcStr, 0);
    }

    /**
     * 
     * 描述字符串转化为布尔型
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:24
     * @param srcStr
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String srcStr, boolean defaultValue) {
        if (isNullBlank(srcStr))
            return defaultValue;
        boolean result = defaultValue;
        if ("true".equalsIgnoreCase(srcStr.trim())) {
            result = true;
        }
        return result;
    }

    /**
     * 
     * 描述字符串转化为布尔型
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:40
     * @param srcStr
     * @return
     */
    public static boolean getBoolean(String srcStr) {
        return getBoolean(srcStr, false);
    }

    /**
     * 
     * 描述双精数字格式化，返回字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:14:58
     * @param db
     * @param fmt
     * @return
     */
    public static String getNumFormat(double db, String fmt) {
        String retu = "";
        if (null == fmt || "".equals(fmt.trim())) {
            return Double.toString(db);
        }

        try {
            DecimalFormat decimalformat = new DecimalFormat(fmt);
            retu = decimalformat.format(db);
            decimalformat = null;
        } catch (IllegalArgumentException iaex) {
            log.error(iaex.getMessage());
            retu = Double.toString(db);
        }
        return retu;
    }

    /**
     * 
     * 描述双精数字格式化，把入参中的双精数格式化为带两位小数的数字字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:06
     * @param db
     * @return
     */
    public static String getNumFormat(double db) {
        return getNumFormat(db, "0.00");
    }

    /**
     * 
     * 描述 数字字符串格式化，返回字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param numStr
     *            String 待格式化的数字字符串， <BR>
     *            如果numStr参数不是的数字则不进行格式化，按原样返回
     * @param fmt
     *            String 格式化样式，参见类说明 <BR>
     *            fmt非法时，将把numStr按原样返回。
     * @return String 格式化后的字符串
     */
    public static String getNumFormat(String numStr, String fmt) {
        double db = getDouble(numStr, 0);
        return getNumFormat(db, fmt);
    }

    /**
     * 数字字符串格式化，把入参中的数字字符串格式化为带两位小数的数字字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param numStr
     *            String 待格式化的数字字符串， <BR>
     *            如果numStr参数不是的数字则不进行格式化，按原样返回
     * @return String 格式化为两位小数的数字字符串
     */
    public static String getNumFormat(String numStr) {
        return getNumFormat(numStr, "0.00");
    }

    /**
     * 普通字符串转化为网页中可显示的，如回车转化为&lt;br&gt;.
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param str
     *            String 待转化的字符串
     * @return String 转化后的字符串
     */
    public static String htmlEncode(String str) {
        String retu = null;
        if (null == str || "".equals(str.trim())) {
            retu = str;
        } else {
            String html = str;
            html = replaceString(html, "&", "&amp;");
            html = replaceString(html, "<", "&lt;");
            html = replaceString(html, ">", "&gt;");
            html = replaceString(html, "\r\n", "\n");
            html = replaceString(html, "\n", "<br>");
            html = replaceString(html, "\t", "    ");
            html = replaceString(html, " ", "&nbsp;");
            html = replaceString(html, "\"", "&quot;");
            retu = html;
            html = null;
        }
        return retu;
    }

    /**
     * 字符串替换，把str字符串中的所有oldStr子串替换为newStr字串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            String 将被替换的字符串，为null时不执行任何替换操作
     * @param oldStr
     *            String 将被替换的子串，为null或为空字符串时不执行替换操作
     * @param newStr
     *            String 将被替换成的子串，为null时不执行替换操作
     * @param ignoreCase
     *            boolean 是否忽略大小写，true表忽略大小写，false表大小写敏感。
     * @return String 替换后的字符串
     */
    public static String replaceString(String srcStr, String oldStr, String newStr, boolean ignoreCase) {
        if (srcStr == null || oldStr == null) {
            return null;
        }
        String result = srcStr;
        String tempStr = srcStr;
        if (ignoreCase) {
            tempStr = srcStr.toUpperCase();
            oldStr = oldStr.toUpperCase();
        }
        int pos = tempStr.indexOf(oldStr);
        while (pos != -1) {
            result = result.substring(0, pos) + newStr + result.substring(pos + oldStr.length());
            pos = tempStr.indexOf(oldStr, pos + newStr.length());
        }
        return result;
    }

    /**
     * 字符串替换，把str字符串中的所有oldStr子串替换为newStr字串(大小写敏感)
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            String 将被替换的字符串，为null时不执行任何替换操作
     * @param oldStr
     *            String 将被替换的子串，为null或为空字符串时不执行替换操作
     * @param newStr
     *            String 将被替换成的子串，为null时不执行替换操作
     * @return String 替换后的字符串
     */
    public static String replaceString(String srcStr, String oldStr, String newStr) {
        return replaceString(srcStr, oldStr, newStr, false);
    }

    /**
     * 字符串按指定分隔符分解为一个数组（大小写敏感）. <BR>
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36 例子： <BR>
     *          String[] array = StringUtils.splictString("AA/BBB/CCC//DDDD//" ,
     *          "/" ); <BR>
     *          结果： <BR>
     *          array[0]="AA" array[1]="BBB" array[2]="CCC" array[3]="DDDD"
     * 
     * @param srcStr
     *            String 源字符串
     * @param split
     *            String 分隔符
     * @return String[] 字符串数组, <BR>
     *         源字符串为null或为空字符串时，返回长度为1的数组，元素值为空字符串 <BR>
     *         分隔符为null或为空字符串时，返回长度为1的数组，元素值为源字符串
     */
    public static String[] split(String srcStr, String split, boolean trim) {
        if (isNullBlank(srcStr) || isNullBlank(split)) {
            return null;
        }
        if (trim) {
            srcStr = trim(srcStr, split);
        }
        return split(srcStr, split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被放入的字符串
     * @param split
     *            间隔符
     * @return 转换后的数组,srcStr或split ＝ null 或 "" 返回null
     */
    public static String[] split(String srcStr, String split) {
        if (isNullBlank(srcStr) || isNullBlank(split)) {
            return null;
        }
        int int_ArraySize = subStringCount(srcStr, split);
        // 如果为-1则返回
        if (int_ArraySize == -1) {
            return null;
        }

        srcStr += split;

        String[] str_RetArr = new String[int_ArraySize + 1];
        int int_pos = srcStr.indexOf(split);
        int int_ArrayPos = 0;
        while (int_pos != -1) {
            str_RetArr[int_ArrayPos++] = srcStr.substring(0, int_pos);
            srcStr = srcStr.substring(int_pos + split.length());
            int_pos = srcStr.indexOf(split);
        }

        return str_RetArr;
    }

    /**
     * 功能简述 :在一个字符串中查找字符串个数(不区分大小写)
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被查询的字符串
     * @param subStr
     *            要查找的字符串
     * @return 找到的个数
     */
    public static int subStringCount(String srcStr, String subStr) {
        if (isNullBlank(srcStr) || isNullBlank(subStr)) {
            return 0;
        }
        int result = 0;
        int int_pos = srcStr.toUpperCase().indexOf(subStr.toUpperCase());
        while (int_pos != -1) {
            result++;
            int_pos = srcStr.toUpperCase().indexOf(subStr.toUpperCase(), int_pos + subStr.length());
        }
        return result;
    }

    /**
     * 用指定的分隔符把字符串数组合并成一个字符串. 数组为null或长度为0时返回空字符串 <BR>
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36 例子： <BR>
     *          String[] array = {"1",null,"","3"}; <BR>
     *          StringUtils.arrayToString(array,"|"); <BR>
     *          返回结果：1|||3
     * 
     * @param array
     *            String[] 待合并的数组
     * @param split
     *            String 数组各元素合并后，它们之间的分隔符，为null时用空字符串代替
     * @return String 合并后的字符串
     */
    public static String arrayToString(String[] array, String split) {
        if (null == array || 0 == array.length) {
            return "";
        }
        if (null == split) {
            split = "";
        }
        StringBuffer strBuf = new StringBuffer("");
        int Len = array.length;
        for (int i = 0; i < Len - 1; i++) {
            strBuf.append((null == array[i]) ? "" : array[i]).append(split);
        }
        strBuf.append((null == array[Len - 1]) ? "" : array[Len - 1]);

        return strBuf.toString();
    }

    /**
     * 用默认分隔符 , 把字符串数组合并成一个字符串. 数组为null或长度为0时返回空字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param array
     *            String[] 待合并的数组
     * @return String 合并后的字符串
     */
    public static String arrayToString(String[] array) {
        return arrayToString(array, ",");
    }

    /**
     * 判断字符串是否为null或为空字符串（包括只含空格的字符串）
     * 
     * @param str
     *            String 待检查的字符串
     * @return boolean 如果为null或空字符串（包括只含空格的字符串）则返回true，否则返回false
     */
    public static boolean isNullBlank(String str) {
        return (null == str || "".equals(str.trim())) ? true : false;
    }

    /**
     * 判断字符串是否不为空
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param str
     *            String
     * @return boolean
     */
    public static boolean isNotBlank(String str) {
        if (isNullBlank(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从字符串中得到指定位置的子串（按分隔符分隔，大小写敏感）. <BR>
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36 例子： <BR>
     *          StringUtils.decomposeString("a||b|c|d","|",1); <BR>
     *          StringUtils.decomposeString("a||b|c|d","|",2); <BR>
     *          StringUtils.decomposeString("a||b|c|d","|",3); <BR>
     *          StringUtils.decomposeString("a||b|c|d","|",4); <BR>
     *          StringUtils.decomposeString("a||b|c|d","|",5); <BR>
     *          返回结果： <BR>
     *          a,空字符串,b,c,d
     * 
     * @param srcStr
     *            String 源字符串
     * @param split
     *            String 分隔符
     * @param pos
     *            int 位置，从1开始
     * @return String 返回指定位置的子串。 <BR>
     *         当分隔符为null或为空字符串时返回源字符串； <BR>
     *         当源字符串为null或为空字符串时返回空字符串； <BR>
     *         当指定位置小于1或大于分隔符个数-1时返回空字符串。
     */
    public static String decomposeString(String srcStr, String split, int pos) {
        String retu = "";
        if (null == srcStr || "".equals(srcStr.trim())) {
            return "";
        }

        if (pos <= 0) {
            return "";
        }

        if (null == split || "".equals(split)) {
            return srcStr;
        }

        srcStr = srcStr + split;
        String tmpStr = srcStr;

        int splitLen = split.length();
        int tmpLen = tmpStr.length();

        for (int i = 0; i < pos - 1; i++) {
            int tmpPosi = tmpStr.indexOf(split);
            if (tmpPosi < 0 || tmpPosi + splitLen >= tmpLen) {
                tmpStr = split;
                break;
            } else {
                tmpStr = tmpStr.substring(tmpPosi + splitLen);
            }
        }
        retu = tmpStr.substring(0, tmpStr.indexOf(split));
        return retu;
    }

    /**
     * 从字符串中得到指定位置的子串（按分隔符 | 分隔）.
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            String 源字符串
     * @param pos
     *            int 位置，从1开始
     * @return String 返回指定位置的子串。 <BR>
     *         当分隔符为null或为空字符串时返回源字符串； <BR>
     *         当源字符串为null或为空字符串时返回空字符串； <BR>
     *         当指定位置小于1或大于分隔符个数-1时返回空字符串。
     */
    public static String decomposeString(String srcStr, int pos) {
        return decomposeString(srcStr, "|", pos);
    }

    /**
     * 删除指定的前导、后导子串（大小写敏感）. <br>
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36 例子： <br>
     *          StringUtils.trim("and and username = '123' and password = 'abc'
     *          and ","and "); <br>
     *          结果：username = '123' and password = 'abc'
     * 
     * @param srcStr
     *            String 待删除的字符串
     * @param removeChar
     *            char 子串
     * @return String 处理后的字符串
     */
    public static String trim(String srcStr, char removeChar) {
        if (srcStr == null) {
            return null;
        }
        srcStr = srcStr.trim();
        int loInt_begin = 0, loInt_end = 0;
        int loInt_len = srcStr.length();
        for (int i = 0; i < loInt_len; i++) {
            if (srcStr.charAt(i) == removeChar) {
                loInt_begin++;
            } else {
                break;
            }
        }
        for (int i = 0; i < loInt_len; i++) {
            if (srcStr.charAt(loInt_len - 1 - i) == removeChar) {
                loInt_end++;
            } else {
                break;
            }
        }
        return srcStr.substring(loInt_begin, loInt_len - loInt_end);
    }

    /**
     * 从源字符串中从第一个字符开始取出给定长度的字串. <BR>
     * 源字符串长度大于len时，尾巴追加一个appendStr串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            String 源字符串
     * @param len
     *            int 取出的长度
     * @param omitStr
     *            String 追加的字符串（常用的appendStr为...）
     * @return String 取出的子串
     */
    public static String clip(String srcStr, int len, String omitStr) {
        if (null == srcStr || "".equals(srcStr)) {
            return srcStr;
        }
        if (len <= 0) {
            return "";
        }

        if (null == omitStr) {
            omitStr = "";
        }

        int sourceLen = srcStr.length();
        if (len >= sourceLen) {
            return srcStr;
        } else {
            return srcStr.substring(0, len) + omitStr;
        }
    }

    /**
     * 将null字符串转换为空串，方便HTML的显示
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            待处理的字符串
     * @return String 处理的的字符串
     */
    public static String toVisualHtmlString(String srcStr) {
        if (srcStr == null || srcStr.equals("")) {
            return "&nbsp;";
        } else {
            return srcStr;
        }
    }

    /**
     * 将null字符串转换为空串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            待处理的字符串
     * @return String 处理的的字符串
     */
    public static String toVisualString(String srcStr) {
        if (srcStr == null || srcStr.equals("")) {
            return "";
        } else {
            return srcStr;
        }
    }

    /**
     * 将字段填充到指定的长度
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            String 操作字符串
     * @param length
     *            int 指定长度
     * @param withChar
     *            char 填充的字符
     * @param isPadToEnd
     *            boolean 是否填充在字符的尾部 true ：是 ,false:填充在头部
     * @return String
     */
    public static String pad(String srcStr, int length, char withChar, boolean isPadToEnd) {
        if (srcStr == null) {
            return null;
        }
        if (srcStr.length() >= length) {
            return srcStr;
        }

        StringBuffer sb = new StringBuffer(srcStr);
        for (int i = 0; i < length - srcStr.length(); i++) {
            if (isPadToEnd) {
                sb.append(withChar);
            } else {
                sb.insert(0, withChar);
            }
        }
        return sb.toString();

    }

    /**
     * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被放入的字符串
     * @param split
     *            间隔符
     * @param trim
     *            自动删除首尾的间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, char split, boolean trim) {
        if (isNullBlank(srcStr))
            return null;
        if (trim) {
            srcStr = trim(srcStr, "" + split);
        }
        return stringToArray(srcStr, "" + split);
    }

    /**
     * 功能简述 :把字符串放入一个数组 系统会自动执行删除字符串首尾的多余的间隔符。如 ,abc,123,, 将自动变成 abc,123
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被放入的字符串
     * @param split
     *            间隔符
     * @param trim
     *            自动删除首尾的间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, String split, boolean trim) {
        if (StringUtils.isNullBlank(srcStr))
            return null;
        if (trim)
            srcStr = trim(srcStr, split + "");
        return stringToArray(srcStr, split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被放入的字符串
     * @param split
     *            间隔符
     * @return 转换后的数组
     */
    public static String[] stringToArray(String srcStr, char split) {
        return stringToArray(srcStr, "" + split);
    }

    /**
     * 功能简述 :把字符串放入一个数组
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param srcStr
     *            要被放入的字符串
     * @param split
     *            间隔符
     * @return 转换后的数组,失败返回 null
     */
    public static String[] stringToArray(String srcStr, String split) {
        if (StringUtils.isNullBlank(srcStr))
            return null;
        srcStr = trim(srcStr, split + "");

        int int_ArraySize = subStringCount(srcStr, split);
        // 如果为-1则返回
        if (int_ArraySize == -1) {
            return null;
        }

        srcStr += split;

        String[] str_RetArr = new String[int_ArraySize + 1];
        int int_pos = srcStr.indexOf(split);
        int int_ArrayPos = 0;
        while (int_pos != -1) {
            str_RetArr[int_ArrayPos++] = srcStr.substring(0, int_pos);
            srcStr = srcStr.substring(int_pos + split.length());
            int_pos = srcStr.indexOf(split);
        }

        return str_RetArr;
    }

    /**
     * 
     * 功能描述：将字符分隔的字符串转换为List
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:05:57
     *          </p>
     * @param srcStr
     * @param splitChar
     * @param trim
     * @return List<String>
     */
    public static List<String> stringToList(String srcStr, char splitChar, boolean trim) {
        return stringToList(srcStr, "" + splitChar, trim);
    }

    /**
     * 
     * 功能描述：将字符分隔的字符串转换为List
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:06:17
     *          </p>
     * @param srcStr
     * @param splitChar
     * @return List<String>
     */
    public static List<String> stringToList(String srcStr, char splitChar) {
        return stringToList(srcStr, "" + splitChar, false);
    }

    /**
     * 
     * 功能描述：将字符分隔的字符串转换为List
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:06:28
     *          </p>
     * @param srcStr
     * @param splitStr
     * @return List<String>
     */
    public static List<String> stringToList(String srcStr, String splitStr) {
        return stringToList(srcStr, splitStr, false);
    }

    /**
     * 
     * 功能描述：将字符分隔的字符串转换为List
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:06:41
     *          </p>
     * @param srcStr
     * @param splitStr
     * @param trim
     * @return List<String>
     */
    public static List<String> stringToList(String srcStr, String splitStr, boolean trim) {
        if (srcStr == null) {
            return null;
        }
        List<String> resultList = new ArrayList<String>();
        if (!isNotBlank(srcStr)) {
            return resultList;
        } else {
            String[] srcStrArr = stringToArray(srcStr, splitStr, trim);
            for (String item : srcStrArr) {
                resultList.add(item);
            }
            return resultList;
        }
    }

    /**
     * 
     * 功能描述：将字符串集成转换为分隔符分隔的字符串
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:06:54
     *          </p>
     * @param collection
     * @param split
     * @return String
     */
    public static final String collToString(Collection<String> collection, String split) {
        StringBuffer sb = new StringBuffer();
        String tempSplit = "";
        for (String item : collection) {
            sb.append(tempSplit + item);
            tempSplit = split;
        }
        return sb.toString();
    }

    /**
     * 以下是对js的escape,进行解码
     */
    private final static byte[] VAL = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00,
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B,
            0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
            0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

    /**
     * 
     * 功能描述：对js的escape进行解码
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:07:16
     *          </p>
     * @param sourceUrl
     * @return String
     */
    public static String urlEncode(String sourceUrl) {
        Assert.notNull(sourceUrl);
        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int len = sourceUrl.length();
        while (i < len) {
            int ch = sourceUrl.charAt(i);
            if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
                sbuf.append((char) ch);
            } else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
                sbuf.append((char) ch);
            } else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
                sbuf.append((char) ch);
            } else if (ch == '-' || ch == '_' // unreserved : as it was
                    || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')') {
                sbuf.append((char) ch);
            } else if (ch == '%') {
                int cint = 0;
                if ('u' != sourceUrl.charAt(i + 1)) { // %XX : map to ascii(XX)
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 1)];
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 2)];
                    i += 2;
                } else { // %uXXXX : map to unicode(XXXX)
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 2)];
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 3)];
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 4)];
                    cint = (cint << 4) | VAL[sourceUrl.charAt(i + 5)];
                    i += 5;
                }
                sbuf.append((char) cint);
            } else { // 对应的字符未经过编码
                sbuf.append((char) ch);
            }
            i++;
        }
        return sbuf.toString();
    }

    /**
     * 
     * 功能描述：删除指定的前导、后导子串（大小写敏感）
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:07:28
     *          </p>
     * @param srcStr
     * @param ch
     * @return String
     */
    public static String trim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        // 删除前导
        int chLen = ch.length();

        while (srcStr.indexOf(ch, 0) == 0) { // 表示还有前导
            srcStr = srcStr.substring(chLen);
        }

        int strLen = srcStr.length();
        while (srcStr.indexOf(ch, (strLen - chLen)) == (strLen - chLen)) { // 表示还有后导
            srcStr = srcStr.substring(0, strLen - chLen);
            strLen = srcStr.length();
        }

        return srcStr;
    }

    /**
     * 
     * 功能描述：删除指定的前导（大小写敏感）
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:07:53
     *          </p>
     * @param srcStr
     * @param ch
     * @return String
     */
    public static String ltrim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        // 删除前导
        int chLen = ch.length();

        if (srcStr.indexOf(ch, 0) == 0) { // 表示还有前导
            srcStr = srcStr.substring(chLen);
        }

        return srcStr;
    }

    /**
     * 
     * 功能描述：删除指定的后导（大小写敏感）
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:08:07
     *          </p>
     * @param srcStr
     * @param ch
     * @return String
     */
    public static String rtrim(String srcStr, String ch) {
        if (null == srcStr || "".equals(srcStr.trim())) {
            return srcStr;
        }
        if (null == ch || "".equals(ch)) {
            return srcStr;
        }

        if (ch.length() > srcStr.length()) {
            return srcStr;
        }

        if (srcStr.indexOf(ch) < 0) {
            return srcStr;
        }

        // 删除后导
        int chLen = ch.length();
        int strLen = srcStr.length();
        if (srcStr.indexOf(ch, (strLen - chLen)) == (strLen - chLen)) { // 表示还有后导
            srcStr = srcStr.substring(0, strLen - chLen);
            //strLen = srcStr.length();
        }

        return srcStr;
    }

    /**
     * 
     * 功能描述：功能描述: 判断字符串是否为null或为空字符串，则返回空字符串""
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:08:17
     *          </p>
     * @param obj
     * @return String
     */
    public static String getNullBlankStr(Object obj) {

        if (obj == null) {
            return "";
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 
     * 功能描述：按指定宽度返回字符串，超过指定的长度则截取指定长度，并加"..."
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     *          <p>
     *          创建日期 ：2014-7-18 下午06:08:26
     *          </p>
     * @param stra
     * @param width
     * @return String
     */
    public static String getSubStr(String stra, int width) {
        if (stra == null || stra.length() <= width) {
            return stra;
        } else {
            return stra.substring(0, width) + "...";
        }
    }

    /**
     * 将2进制解释成中文
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param map
     *            map 中文存储，key 2进制，value 2进制中文解释
     * @param bit_
     *            string 2进制
     * @param joint
     *            string 链接符
     * @return string 返回指定的中文解释
     */
    public static String getBinaryToString(Map<String, String> map, String bit_, String joint) {
        StringBuffer mapResult = new StringBuffer();
        int bit_length = 0;
        for (int i = 0; i < bit_.length(); i++) {
            if ((bit_length = bit_.indexOf("1", i)) != -1) {
                String var_ = "1";
                var_ = pad(var_, bit_length + 1, '0', false);
                var_ = pad(var_, bit_.length(), '0', true);
                String resutl = map.get(var_);
                if (StringUtils.isNotBlank(resutl)) {
                    if (StringUtils.isNotBlank(mapResult.toString())) {
                        mapResult.append(joint);
                    }
                    mapResult.append(resutl);
                }
                i = bit_length;
            }
        }
        return mapResult.toString();
    }

    /**
     * 将数字改成2进制整型字符串分割
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:15:36
     * @param int_
     * @param joint
     * @param bitType
     * @return
     */
    public static String getIntSplitBitOrInt(int int_, String joint, boolean bitType) {
        String bit_ = Integer.toBinaryString(int_);
        StringBuffer srcResult = new StringBuffer("");
        int bit_length = 0;
        for (int i = 0; i < bit_.length(); i++) {
            if ((bit_length = bit_.indexOf("1", i)) != -1) {
                String var_ = "1";
                var_ = pad(var_, bit_length + 1, '0', false);
                var_ = pad(var_, bit_.length(), '0', true);
                if (StringUtils.isNotBlank(srcResult.toString())) {
                    srcResult.append(joint);
                }
                if (bitType) {
                    srcResult.append(var_);
                } else {
                    BigInteger rs = new BigInteger(var_, 2);
                    srcResult.append(rs);
                }

                i = bit_length;
            }
        }
        return srcResult.toString();
    }

    /**
     * 
     * 描述首字母大写
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:19:14
     * @param realName
     * @return
     */
    public static String firstUpperCase(String realName) {
        return realName.substring(0, 1).toUpperCase() + realName.substring(1);
    }

    /**
     * 
     * 描述首字母小写
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:19:37
     * @param realName
     * @return
     */
    public static String firstLowerCase(String realName) {
        return realName.substring(0, 1).toLowerCase() + realName.substring(1);
    }

    /**
     * 
     * 描述将entity字段转换成数据库字段
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:19:45
     * @param columns
     * @return
     */
    public static String entityToDb(String columns) {
        StringBuffer dbColumns = new StringBuffer();
        for (int i = 0; i < columns.length(); i++) {
            char c = columns.charAt(i);
            if (Character.isUpperCase(c)) {
                dbColumns.append("_" + c);
            } else {
                dbColumns.append(c);
            }
        }
        return dbColumns.toString().toUpperCase();
    }

    /**
     * 
     * 描述判断空
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:19:51
     * @param object
     * @return
     */
    public static String getString(Object object) {
        if (isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    /**
     * 
     * 描述判断空
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:19:57
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return (true);
        }
        if (object.equals("")) {
            return (true);
        }
        if (object.equals("null")) {
            return (true);
        }
        return (false);
    }

    /**
     * 
     * 描述将entity字段转换成数据库字段
     * 
     * @author Panda Chen
     * @created 2016-11-2 下午04:20:04
     * @param columns
     * @return
     */
    public static String dbToEntity(String columns) {
        StringBuffer dbColumns = new StringBuffer();
        String[] str = columns.split("_");
        for (int i = 0; i < str.length; i++) {
            dbColumns.append(firstUpperCase(str[i].toLowerCase()));
        }
        return firstLowerCase(dbColumns.toString());
    }
}
