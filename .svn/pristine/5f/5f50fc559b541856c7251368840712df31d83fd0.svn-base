/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 下午3:25:47
 */
public class StringUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(StringUtil.class);
    /**
     * 国标码和区位码转换常量
     */
    private static final int GB_SP_DIFF = 160;
    /**
     * 加密算法,可用 DES,DESede,Blowfish.
     */
    private static final String ALGORITHM = "DES";
    /**
     * 存放国标一级汉字不同读音的起始区位码
     */
    private static final int[] SECPOSVALUELIST = { 1601, 1637, 1833, 
        2078, 2274, 2302, 2433, 2594, 2787, 3106,
        3212,3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
    /**
     * 存放国标一级汉字不同读音的起始区位码对应读音
     */
    private static final char[] FIRSTLETTER = { 'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o','p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

    /**
     * 获取一个字符串的拼音码的首字母 比方说:输入胡裕必胜 返回的结果将会是:hybs
     * 
     * @param oriStr
     * @return
     */
    public static String getFirstLetter(String oriStr) {
        String str = oriStr.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
            ch = str.charAt(i);
            temp = new char[] { ch };
            byte[] uniCode = new String(temp).getBytes();
            if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
                buffer.append(temp);
            } else {
                buffer.append(convert(uniCode));
            }
        }
        return buffer.toString();
    }

    /**
     * 获取一个汉字的拼音首字母。
     * 
     * 　　* GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 
     * 　　* 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 
     * 　　* 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’ 　
     */
    private static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= SECPOSVALUELIST[i] && secPosValue < SECPOSVALUELIST[i + 1]) {
                result = FIRSTLETTER[i];
                break;
            }
        }
        return result;
    }

    /**
     * 转换为字符串
     * @param obj
     * @return
     */
    public static String getString(Object obj){
        return obj==null?"":obj.toString().trim();
    }
    /**
     * 获得汉语拼音首字母
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        StringBuffer pinyinName = new StringBuffer("");
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    pinyinName.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    log.error("", e);
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
        }
        return pinyinName.toString().toLowerCase();
    }
    
    public static String convertQuot(String orgStr) {
        return orgStr.replace("'", "\\'").replace("\"", "\\\"");
    }

    /**
     * 获取加密配置为SHA-256的加密码
     * 
     * @param inputStr
     * @return
     */
    public static synchronized String encryptSha256(String inputStr) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(digest));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 
     * 描述 s
     * 
     * @author Rider Chen
     * @created 2017年5月22日 上午9:28:26
     * @param decrypt
     * @return
     */
    public static synchronized String encryptSha1(String decrypt) {
        try {
            // 指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decrypt.getBytes());
            // 获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
//                if (shaHex.length() < 2) {
//                    hexString.append(0);
//                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 
     * @方法功能说明：字符串格式的日期进行比较
     * @创建时间：May 18, 2012
     * @参数说明：
     * @返回值说明：int
     */
    public static int compareDate(String date_start, String date_end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int ret = 0;
        try {
            Date dt1 = df.parse(date_start);
            Date dt2 = df.parse(date_end);
            if (dt1.getTime() > dt2.getTime()) {
                ret = -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                ret = 1;
            } else {
                ret = 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    /**
     *
     * @方法功能说明：字符串格式的日期进行比较
     * @创建时间：May 18, 2012
     * @参数说明：
     * @返回值说明：int
     */
    public static int compareDateF(String date_start, String date_end , String formate) {
        DateFormat df = new SimpleDateFormat(formate);
        int ret = 0;
        try {
            Date dt1 = df.parse(date_start);
            Date dt2 = df.parse(date_end);
            if (dt1.getTime() > dt2.getTime()) {
                ret = -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                ret = 1;
            } else {
                ret = 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ret;
    }

    /**
     * 
     * @方法功能说明：HH:mm格式的时间字符串比较
     * @创建时间：May 18, 2012
     * @参数说明：
     * @返回值说明：int
     */
    public static int compareTimeHHmm(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        int ret = 0;
        try {
            Date dt1 = df.parse(time1);
            Date dt2 = df.parse(time2);
            if (dt1.getTime() > dt2.getTime()) {
                ret = -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                ret = 1;
            } else {
                ret = 0;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ret;
    }
    /**
     * 
     * 描述  获取加密为MD5的算法字符串(不进行补位)
     * @author Rider Chen
     * @created 2017年5月19日 下午4:09:45
     * @param inputStr
     * @param charset
     * @return
     */
    public static String getMd5Encode(String inputStr,String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if(StringUtils.isNotEmpty(charset)){
                md.update(inputStr.getBytes(charset));
            }else{
                md.update(inputStr.getBytes());
            }
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 获取加密为MD5的算法字符串
     * 
     * @author Flex Hu
     * @param inputStr
     * @return
     */
    public static String getMd5Encode(String inputStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(inputStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取加密为MD5的算法字符串
     * 
     * @author Toddle Chen
     * @param inputStr
     * @return
     */
    public static String encryptMd5(String inputStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(inputStr.getBytes("utf8"));
            StringBuilder ret = new StringBuilder(bytes.length << 1);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
                ret.append(Character.forDigit(bytes[i] & 0xf, 16));
            }
            return ret.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } catch (UnsupportedEncodingException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return null;
    }
    public static String htmlEntityToString(String dataStr) {
        int start = 0;
        int end = 0;
        StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            int system = 10;
            if (start == 0) {
                int t = dataStr.indexOf("&#");
                if (start != t)
                    start = t;
            }
            end = dataStr.indexOf(";", start + 2);
            String charStr = "";
            if (end != -1) {
                charStr = dataStr.substring(start + 2, end);

                char s = charStr.charAt(0);
                if ((s == 'x') || (s == 'X')) {
                    system = 16;
                    charStr = charStr.substring(1);
                }
            }
            try {
                char letter = (char) Integer.parseInt(charStr, system);
                buffer.append(Character.valueOf(letter).toString());
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
            }

            start = dataStr.indexOf("&#", end);
            if (start - end > 1) {
                buffer.append(dataStr.substring(end + 1, start));
            }

            if (start == -1) {
                int length = dataStr.length();
                if (end + 1 != length)
                    buffer.append(dataStr.substring(end + 1, length));
            }
        }

        return buffer.toString();
    }

    public static String stringToHtmlEntity(String str) {
        char c;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); ++i) {
            c = str.charAt(i);

            switch (c) {
                case '\n':
                    sb.append(c);
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                default:
                    if ((c < ' ') || (c > '~')) {
                        sb.append("&#x");
                        sb.append(Integer.toString(c, 16));
                        sb.append(';');
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    public static String stringToUnicode(String s) {
        StringBuffer unicode=new StringBuffer();
        char[] charAry = new char[s.length()];
        for (int i = 0; i < charAry.length; ++i) {
            charAry[i] = s.charAt(i);
            unicode.append("\\u").append(Integer.toString(charAry[i], 16));
        }
        return unicode.toString();
    }

    public static String unicodeToString(String unicodeStr) {
        StringBuffer sb = new StringBuffer();
        String[] str = unicodeStr.toUpperCase().split("\\\\U");
        for (int i = 0; i < str.length; ++i) {
            if (str[i].equals(""))
                continue;
            char c = (char) Integer.parseInt(str[i].trim(), 16);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 把html转换成文本
     * 
     * @author Flex Hu
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            Pattern p_script = Pattern.compile(regEx_script, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
//            System.err.println("Html2Text: " + e.getMessage());
            log.error(e.getMessage());
        }

        return textStr;
    }

    public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object[] args) {
        int argsLen = args.length;
        boolean markFound = false;
        StringBuilder sb = new StringBuilder(msgWithFormat);
        if (argsLen > 0) {
            for (int i = 0; i < argsLen; i++) {
                String flag = "%" + (i + 1);
                int idx = sb.indexOf(flag);

                while (idx >= 0) {
                    markFound = true;
                    sb.replace(idx, idx + 2, toString(args[i], autoQuote));
                    idx = sb.indexOf(flag);
                }
            }
            if ((args[(argsLen - 1)] instanceof Throwable)) {
                StringWriter sw = new StringWriter();
                log.info(new PrintWriter(sw));
                sb.append("\n").append(sw.toString());
            } else if ((argsLen == 1) && (!markFound)) {
                sb.append(args[(argsLen - 1)].toString());
            }
        }
        return sb;
    }

    public static StringBuilder formatMsg(String msgWithFormat, Object[] args) {
        return formatMsg(new StringBuilder(msgWithFormat), true, args);
    }

    public static String toString(Object obj, boolean autoQuote) {
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            sb.append("NULL");
        } else if ((obj instanceof Object[])) {
            for (int i = 0; i < ((Object[]) obj).length; i++) {
                sb.append(((Object[]) obj)[i]).append(", ");
            }
            if (sb.length() > 0)
                sb.delete(sb.length() - 2, sb.length());
        } else {
            sb.append(obj.toString());
        }
        if ((autoQuote) && (sb.length() > 0) && ((sb.charAt(0) != '[') || (sb.charAt(sb.length() - 1) != ']'))
                && ((sb.charAt(0) != '{') || (sb.charAt(sb.length() - 1) != '}'))) {
            sb.insert(0, "[").append("]");
        }
        return sb.toString();
    }

    /**
     * 字符串数组转为字符串
     * 
     * @param array
     * @param split
     * @return
     * @author Water Guo
     */
    public static String getStringByArray(String[] array, String split) {
        StringBuffer str = new StringBuffer();
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                str.append(array[i]).append(split);
            }
            return str.substring(0, str.length() - split.length());
        }
        return str.toString();
    }

    /**
     * 将数组转换成以逗号拼接的字符串
     * 
     * @author Flex Hu
     * @param array
     * @return
     */
    public static String getStringByArray(String[] array) {
        StringBuffer str = new StringBuffer();
        for (String a : array) {
            str.append(a).append(",");
        }
        if (str.length() > 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    /**
     * 字符串集合转成字符串
     * 
     * @param list
     * @param split
     * @return Water Guo 11, 2011 9:05:14 PM
     */
    public static String getStringByList(List<String> list, String split) {
        StringBuffer str=new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                str.append(list.get(i)).append(split);
            }
            if (str.length() > split.length()) {
                return str.substring(0, str.length() - split.length());
            }
        }
        return str.toString();
    }

    @SuppressWarnings("unchecked")
    public static String[] convertClassFieldName(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();// 根据Class对象获得属性 私有的也可以获得
        List<String> list = new ArrayList<String>();
        int j = 0;
        for (Field f : fields) {
            String name = f.getName();
            if (name.startsWith("wf")) {
                list.add(convertFieldName(name));
                j++;
            }
        }
        String[] fieldArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            fieldArr[i] = list.get(i);
        }
        return fieldArr;
    }

    public static String convertFieldName(String name) {
        String str = "";
        char[] names = name.toCharArray();
        StringBuffer append = new StringBuffer("");
        for (int i = 0; i < names.length; i++) {
            if (Character.isUpperCase(names[i])) {
                append.append("_").append(String.valueOf(names[i]).toLowerCase());
            } else {
                append.append(names[i]);
            }
        }
        str = append.toString();
        append = null;
        return str;
    }

    public static String nullToString(String str) {
        return str == null ? "" : str;
    }

    public static String nullORSpaceToDefault(String str, String default_value) {
        return str == null || str.trim().equals("") ? default_value : str;
    }

    /*
     * 通用状态显示
     * 
     * @param String型 STR1------提示消息字符串
     * 
     * @param String型 STR2------提示消息字符串
     * 
     * @param String型 SIGN1-----状态标志值
     * 
     * @param String型 SIGN2-----状态标志值
     * 
     * @return 返回字符串STR1或STR2
     */
    public static String commShowST(String SIGN1, String SIGN2, String STR1, String STR2) {
        return SIGN1.equals(SIGN2) ? STR1 : STR2;
    }

    /**
     * 把字符串转成Long数组,这个用于批量删除
     * 
     * @param str
     *            :字符串数组
     * @return
     */
    public static Long[] convertStringToLong(String[] str) {
        Long[] ids = new Long[str.length];
        for (int i = 0; i < str.length; i++) {
            ids[i] = Long.parseLong(str[i]);
        }
        return ids;
    }

    /**
     * 格式化字符串为金钱类型
     * 
     * @param value
     * @return
     */
    public static Double parseDouble(String value) {
        DecimalFormat formator = new DecimalFormat("###,###,###.##");
        try {
            return formator.parse(value).doubleValue();
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return 0.0;
    }

    /**
     * 格式化double类型
     * 
     * @param value
     * @return
     */
    public static Double formatDouble(Double value) {
        DecimalFormat formator = new DecimalFormat("#.##");
        return Double.parseDouble(formator.format(value));
    }

    /**
     * 获取格式化Double数据后的字符串,用金钱方式显示
     * 
     * @param value
     * @return
     */
    public static String getStrOfDouble(Double value) {
        DecimalFormat formator = new DecimalFormat("###,###,##0.00");
        return formator.format(value);
        // NumberFormat numberFormat =
        // NumberFormat.getCurrencyInstance(Locale.CHINA);
        // return numberFormat.format(value);
    }

    /**
     * 字符串替换
     * 
     * @param replaceStr
     *            :要替换的字符串
     * @param head
     *            :字符串头
     * @param tail
     *            :字符串尾部
     * @return
     */
    public static String replace(String targetStr, String replaceStr, String head, String tail) {
        while (targetStr.lastIndexOf(tail) != -1 && targetStr.lastIndexOf(head) != -1) {
            String frontSql = targetStr.substring(0, targetStr.lastIndexOf(tail) - 1);
            frontSql = frontSql.substring(0, frontSql.lastIndexOf(head));
            String lastSql = targetStr.substring(targetStr.lastIndexOf(tail) + tail.length() + 1);
            targetStr = frontSql + replaceStr + lastSql;
        }
        return targetStr;
    }

    /**
     * 判断字符串是否是数字类型
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 将传入的字符串格式例如a,b,c,转化SQL语句IN sql语句使用('a','b','c')
     */
    public static String getValueArray(String values) {
        StringBuffer sb = new StringBuffer();
        String[] valuesArray = values.split(",");
        for (String value : valuesArray) {
            sb.append(",'" + value + "'");
        }
        sb.delete(0, 1);
        sb.insert(0, "(");
        sb.append(")");
        return sb.toString();
    }
    /**
     * 
     * 描述 将传入的set集合,转化SQL语句IN sql语句使用('a','b','c')
     * @author Flex Hu
     * @created 2014年10月22日 下午5:16:04
     * @param values
     * @return
     */
    public static String getValueArray(Set<String> values){
        StringBuffer sb = new StringBuffer("(");
        for(String value:values){
            sb.append("'").append(value).append("',");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * 反格式化byte
     * 
     * @param s
     * @return
     */
    public static byte[] hex2byte(String s) {
        byte[] src = s.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a') : hi - '0');
            low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a') : low - '0');
            ret[i / 2] = (byte) (hi << 4 | low);
        }
        return ret;
    }

    /**
     * 二进制转字符串
     * 
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 根据索引位置进行字符的替换
     * 
     * @author Flex Hu
     * @param index
     *            :索引位置
     * @param orignalStr
     *            :原始字符串
     * @param replaceStr
     *            :要替换成什么样的串
     * @return
     */
    public static String replace(int index, String orignalStr, String replaceStr) {
        StringBuffer sb = new StringBuffer(orignalStr);
        sb.deleteCharAt(index);
        sb.insert(index, replaceStr);
        return sb.toString();
    }

    /**
     * 产生某个数字以内的随机整数
     * 
     * @author Flex Hu
     * @param maxNum
     * @return
     */
    public static int getRandomIntNumber(int maxNum) {
        Random rand = new Random();
        int num = rand.nextInt(); // int范围类的随机数
        num = rand.nextInt(maxNum);
        return num;
    }

    /**
     * 
     * 描述 产生两个数字以内的随机整数
     * @author Rider Chen
     * @created 2016年5月6日 上午11:06:38
     * @param maxNum
     * @return
     */
    public static int getRandomIntNumber(int maxNum,int minNum) {
        Random rand = new Random();
        int num  = rand.nextInt(maxNum)+minNum;// int范围类的随机数
        return num;
    }
    /**
     * 
     * 描述 转换字符串的首字母为小写
     * @author Flex Hu
     * @created 2014年9月28日 上午9:55:23
     * @param srcString
     * @return
     */
    public static String convertFirstLetterToLower(String srcString){
        return srcString.substring(0,1).toLowerCase()+srcString.substring(1,srcString.length());
    }
    /**
     * 
     * 描述 HTML转码
     * @author Flex Hu
     * @created 2014年10月2日 下午6:53:01
     * @param i
     * @return
     */
    public static String htmlEncode(int i) {
        if (i == '&')
            return "&amp;";
        else if (i == '<')
            return "&lt;";
        else if (i == '>')
            return "&gt;";
        else if (i == '"')
            return "&quot;";
        else
            return "" + (char) i;

    }
    /**
     * 
     * 描述 HTML转码
     * @author Flex Hu
     * @created 2014年10月2日 下午6:53:01
     * @param i
     * @return
     */
    public static String htmlEncode(String str) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            buf.append(htmlEncode(str.charAt(i)));
        }
        return buf.toString();
    }
    /**
     * 
     * 描述 获取拼音
     * @author Flex Hu
     * @created 2014年12月16日 上午9:36:38
     * @param src
     * @return
     */
    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = null;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuffer t4=new StringBuffer();
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4.append(t2[0]);
                } else
                    t4.append(java.lang.Character.toString(t1[i]));
            }
            return t4.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            log.error(e1.getMessage());
        }
        return t4.toString();
    }
    /**
     * 
     * 描述 获取字符串的base64编码
     * @author Flex Hu
     * @created 2014年12月16日 上午9:40:45
     * @param str
     * @return
     */
    public static String getBase64Encode(String str,String code){
        byte[] b = null;  
        String s = null;  
        try {  
            //b = str.getBytes("utf-8");  
            b = str.getBytes(code);
        } catch (UnsupportedEncodingException e) {  
            log.error(e.getMessage());  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }
    /**
     * 
     * 描述 获取base64的解码
     * @author Flex Hu
     * @created 2014年12月16日 上午9:41:34
     * @param str
     * @return
     */
    public static String getBase64Decode(String str,String code){
        byte[] b = null;  
        String result = null;  
        if (str != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(str);  
                //result = new String(b, "utf-8");  
                result = new String(b,code);
            } catch (Exception e) {  
                log.error(e.getMessage());  
            }  
        }  
        return result;  
    }
    /**
     * 
     * 描述 获取格式化的字符串,例如原值为10，
     * 长度为6,那么格式化为000010
     * @author Flex Hu
     * @created 2015年3月27日 下午6:15:07
     * @param formatLength
     * @param value
     * @return
     */
    public static String getFormatNumber(int formatLength,String value){
        int oldLength = value.length();
        if(oldLength<formatLength){
            StringBuffer zeros = new StringBuffer("");
            for(int i =0;i<formatLength-oldLength;i++){
                zeros.append("0");
            }
            zeros.append(value);
            return zeros.toString();
        }else{
            return value;
        }
    }
    /**
     *
     * 描述 获取格式化的字符串,例如原值为10，长度为6,那么格式化为100000
     * @author Flex Hu
     * @created 2015年3月27日 下午6:15:07
     * @param formatLength
     * @param value
     * @return
     */
    public static String getAppendNumber(int formatLength,String value){
        StringBuffer v=new StringBuffer(value);
        int oldLength = value.length();
        if(oldLength<formatLength){
            StringBuffer zeros = new StringBuffer("");
            for(int i =0;i<formatLength-oldLength;i++){
                zeros.append("0");
            }
            v.append(zeros);
            return v.toString();
        }else{
            return value;
        }
    }
    /**
     * 
     * 描述 对字符串进行DES解密
     * @author Flex Hu
     * @created 2015年7月1日 上午11:01:16
     * @param sourceStr
     * @param key
     * @return
     */
    public static String decryptForDes(String sourceStr,String key){
        try {
            return new String(StringUtil.decrypt(hex2byte(sourceStr.getBytes()),  
                    key.getBytes()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }  
        return null;
    }
    /**
     * 
     * 描述 将十六进制转成byte
     * @author Flex Hu
     * @created 2015年7月1日 上午11:02:56
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) {  
        if ((b.length % 2) != 0)  
            throw new IllegalArgumentException("长度不是偶数");  
        byte[] b2 = new byte[b.length / 2];  
        for (int n = 0; n < b.length; n += 2) {  
            String item = new String(b, n, 2);  
            b2[n / 2] = (byte) Integer.parseInt(item, 16);  
        }  
        return b2;  
    }  
    
    /** 
     * 用指定的key对数据进行DES解密. 
     * @param data 待解密的数据 
     * @param key DES解密的key 
     * @return 返回DES解密后的数据 
     * @throws Exception 
     * @author Flex Hu
     * Creation date: 2007-7-31 - 下午12:10:34 
     */  
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {  
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();  
        // 从原始密匙数据创建一个DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);  
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey securekey = keyFactory.generateSecret(dks);  
        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);  
        // 现在，获取数据并解密  
        // 正式执行解密操作  
        return cipher.doFinal(data);  
    }  
    
    /**
     * 
     * 描述 对字符串进行DES加密
     * @author Flex Hu
     * @created 2015年7月1日 上午10:56:44
     * @param sourceStr
     * @param key:加密的KEY
     * @return
     */
    public static String encryptForDes(String sourceStr,String key){
        try {
            return byte2hex(StringUtil.encrypt(sourceStr.getBytes(), key  
                    .getBytes()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }  
        return null;
    }
    
    /** 
     * 用指定的key对数据进行DES加密. 
     */  
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {  
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();  
        // 从原始密匙数据创建DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);  
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey securekey = keyFactory.generateSecret(dks);  
        // Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);  
        // 现在，获取数据并加密  
        // 正式执行加密操作  
        return cipher.doFinal(data);  
    }  
    /**
     * 
     * 描述 将字符串转成set
     * @author Flex Hu
     * @created 2015年8月22日 上午9:36:06
     * @param targetString
     * @return
     */
    public static Set<String> getSet(String targetString){
        Set<String> value = new HashSet<String>();
        for(String str:targetString.split(",")){
            if(StringUtils.isNotEmpty(str)&&!str.equals("null")){
                value.add(str);
            }
        }
        return value;
    }
    
    /**
     * 
     * 描述 将set转成string
     * @author Flex Hu
     * @created 2015年8月22日 上午9:37:24
     * @param targetSet
     * @return
     */
    public static String getString(Set<String> targetSet){
        StringBuffer values = new StringBuffer("");
        for(String value:targetSet){
            values.append(value).append(",");
        }
        return values.deleteCharAt(values.length()-1).toString();
    }
    /**
     * 
     * 描述 按行读取文本数据,返回列表
     * @author Flex Hu
     * @created 2015年9月30日 下午5:08:21
     * @param filePath
     * @param encode
     * @return
     */
    public static List<String> readTxtByLine(String filePath,String encoding){
        List<String> results = new ArrayList<String>();
        InputStreamReader read = null;// 考虑到编码格式
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    results.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally{
            if (read!=null) {
                try {
                    read.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return results;
    }
    
    /**
     * 
     * 描述 将clob类型转换成string类型
     * @author Flex Hu
     * @created 2015年10月9日 下午5:22:31
     * @param clob
     * @return
     */
    public static String clobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        try {
            Reader inStreamDoc = clob.getCharacterStream();
            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SQLException es) {
            log.error(es.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * 描述 blob转换成string
     * @author Flex Hu
     * @created 2015年10月16日 上午10:07:39
     * @param blob
     * @return
     */
    public static String blobToString(Blob blob,String encode) {
        String content = null;
        try {
            content = new String(blob.getBytes((long) 1, (int) blob.length()),encode);
        } catch (SQLException e) {
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return content;
    }
    /**
     * 
     * 描述 将数字格式化成百分比显示
     * @author Flex Hu
     * @created 2016年3月8日 下午3:28:03
     * @param number:数字
     * @param digitCount:小数位数
     * @return
     */
    public static String getPercentFormat(double number,int digitCount){
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        //最后格式化并输出
        return nt.format(number);
    }
    
    /**
     * 
     * 描述 将数字格式化成百分比
     * @author Flex Hu
     * @created 2016年3月10日 下午5:23:41
     * @param number
     * @param digitCount
     * @return
     */
    public static double getPercentFormatDouble(double number,int digitCount){
      //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        //最后格式化并输出
        String value = nt.format(number);
        return Double.parseDouble(value.replace("%", ""));
    }
    /**
     * 
     * 描述 阿拉伯数字转换成中文数字
     * @author Rider Chen
     * @created 2016年12月19日 下午12:55:10
     * @param num
     * @return
     */
    public static String formatInteger(int num) {
        String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿" };
        char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    // not need process if the last digital bits is 0
                    continue;
                } else {
                    // no unit for 0
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    } 
    /**
     * 
     * 描述 阿拉伯数字转换成中文数字
     * @author Rider Chen
     * @created 2016年12月19日 下午12:55:10
     * @param num
     * @return
     */
    public static String convertInteger(int number) {
        String[] single = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };// 汉字一到九
        String[] unit = { "", "十", "百", "千", "万", "亿" };// 汉字单位
        StringBuilder sb = new StringBuilder();
        int[] unitInt = { 100000000, 10000, 1000, 100, 10 };
        int m = 0;
        int tmp = -1;
        for (int i : unitInt) {

            int n = number / i;
            number = number % i;
            if (sb.length() != 0 || n != 0) {
                if (n > single.length - 1) {
                    String cc = convertInteger(n);
                    sb.append(cc);
                } else {
                    if (0 == tmp && 0 == n) {

                    } else {
                        sb.append(single[n]);
                    }

                    tmp = n;
                }
                if (n != 0) {
                    sb.append(unit[unit.length - 1 - m]);
                }

            }
            m++;

        }
        if (number != 0) {
            sb.append(single[number]);
        }
        String ret = sb.toString();
        if (ret.length() == 0) {
            return "零";
        }
        String last = String.valueOf(ret.charAt(ret.length() - 1));
        if ("零".equals(last)) {
            ret = ret.substring(0, ret.length() - 1);
        } else if(ret.indexOf("一十")==0){
            ret = ret.substring(1, ret.length());
        }

        return ret;
    }
    /**
     * 描述 将行数据Map的key字段由Object转为String类型
     * 
     * @author Reuben Bao
     * @created 2019年3月28日 上午10:41:00
     * @param map 行数据
     * @param key 查询的字段名称
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getValue(Map map, String key) {
        String ret = StringUtils.EMPTY;
        if (map != null) {
            Object obj = map.get(key);
            if (obj != null) {
                return obj.toString().trim();
            }
        }
        return ret;
    }

    /**
     * 判断对象是否为空
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null)
            return true;

        if (object instanceof CharSequence)
            return object.toString().trim().length() == 0;

        if(object instanceof Collection)
            return CollectionUtils.isEmpty((Collection<?>) object);

        if(object instanceof Map)
            return CollectionUtils.isEmpty((Map) object);

        if (object.getClass().isArray())
            return Array.getLength(object) == 0;

        return false;
    }

    /**
     * 判断对象是否非空
     * @param object
     * @return
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    /**
     * 获取map中的布尔类型
     * @param map
     * @param boolKey
     * @return
     */
    public static boolean getBoolean(Map<String,Object> map,String boolKey){
        boolean success = false;
        if(isNotEmpty(map)&&isNotEmpty(map.get(boolKey))){
            success = Boolean.parseBoolean(map.get(boolKey).toString());
        }

        return success;
    }
}
