/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.yb.util;

import org.apache.commons.lang3.StringUtils;


/**
 * 描述 将数字金额转换为大写中文金额
 * @author Madison You
 * @created 2021年01月18日 下午3:44:16
 */
public class ConvertUpMoney {

    /**
     * 描述:大写数字
     *
     * @author Madison You
     * @created 2021/1/18 14:25:00
     * @param
     * @return
     */
    private static final String[] NUMBERS = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    /**
     * 描述:整数部分的单位
     *
     * @author Madison You
     * @created 2021/1/18 14:25:00
     * @param
     * @return
     */
    private static final String[] IUNIT = {"元","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万","拾","佰","仟"};
    /**
     * 描述:小数部分的单位
     *
     * @author Madison You
     * @created 2021/1/18 14:25:00
     * @param
     * @return
     */
    private static final String[] DUNIT = {"角","分","厘"};

    /**
     * 描述:转换为大写的中文金额
     *
     * @author Madison You
     * @created 2021/1/18 14:25:00
     * @param
     * @return
     */
    public static String toChinese(String str) {
        // 判断输入的金额字符串是否符合要求
        if (StringUtils.isBlank(str) || !str.matches("(-)?[\\d]*(.)?[\\d]*")) {
            return "抱歉，请输入数字！";
        }

        if("0".equals(str) || "0.00".equals(str) || "0.0".equals(str)) {
            return "零元";
        }

        // 判断金额数字中是否存在负号"-"
        boolean flag = false;
        if(str.startsWith("-")){
            // 标志位，标志此金额数字为负数
            flag = true;
            str = str.replaceAll("-", "");
        }

        // 去掉金额数字中的逗号","
        str = str.replaceAll(",", "");
        String integerStr;//整数部分数字
        String decimalStr;//小数部分数字

        // 初始化：分离整数部分和小数部分
        if(str.indexOf(".")>0) {
            integerStr = str.substring(0,str.indexOf("."));
            decimalStr = str.substring(str.indexOf(".") + 1);
        }else if(str.indexOf(".")==0) {
            integerStr = "";
            decimalStr = str.substring(1);
        }else {
            integerStr = str;
            decimalStr = "";
        }

        // beyond超出计算能力，直接返回
        if(integerStr.length()>IUNIT.length) {
            return "超出计算能力！";
        }

        // 整数部分数字
        int[] integers = toIntArray(integerStr);
        // 判断整数部分是否存在输入012的情况
        if (integers.length>1 && integers[0] == 0) {
            return "抱歉，输入数字不符合要求！";
        }
        // 设置万单位
        boolean isWan = isWan5(integerStr);
        // 小数部分数字
        int[] decimals = toIntArray(decimalStr);
        // 返回最终的大写金额
        String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);
        if(flag){
            // 如果是负数，加上"负"
            return "负" + result;
        }else{
            return result;
        }
    }

    /**
     *  将字符串转为int数组
     * @param number  数字
     * @return
     */
    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];
        for(int i = 0;i<number.length();i++) {
            array[i] = Integer.parseInt(number.substring(i,i+1));
        }
        return array;
    }

    /**
     *  将整数部分转为大写的金额
     * @param integers 整数部分数字
     * @param isWan  整数部分是否已经是达到【万】
     * @return
     */
    public static String getChineseInteger(int[] integers,boolean isWan) {
        StringBuffer chineseInteger = new StringBuffer("");
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return "";
        }
        for(int i=0; i<length; i++) {
            String key = "";
            if(integers[i] == 0) {
                if((length - i) == 13)//万（亿）
                    key = IUNIT[4];
                else if((length - i) == 9) {//亿
                    key = IUNIT[8];
                }else if((length - i) == 5 && isWan) {//万
                    key = IUNIT[4];
                }else if((length - i) == 1) {//元
                    key = IUNIT[0];
                }
                if((length - i)>1 && integers[i+1]!=0) {
                    key += NUMBERS[0];
                }
            }
            chineseInteger.append(integers[i]==0?key:(NUMBERS[integers[i]]+IUNIT[length - i -1]));
        }
        return chineseInteger.toString();
    }

    /**
     *  将小数部分转为大写的金额
     * @param decimals 小数部分的数字
     * @return
     */
    private static String getChineseDecimal(int[] decimals) {
        StringBuffer chineseDecimal = new StringBuffer("");
        for(int i = 0;i<decimals.length;i++) {
            if(i == 3) {
                break;
            }
            chineseDecimal.append(decimals[i]==0?"":(NUMBERS[decimals[i]]+DUNIT[i]));
        }
        return chineseDecimal.toString();
    }

    /**
     *  判断当前整数部分是否已经是达到【万】
     * @param integerStr  整数部分数字
     * @return
     */
    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if(length > 4) {
            String subInteger = "";
            if(length > 8) {
                subInteger = integerStr.substring(length- 8,length -4);
            }else {
                subInteger = integerStr.substring(0,length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        }else {
            return false;
        }
    }


//    // Test
//    public static void main(String[] args) {
//        String number = "12.56";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "1234567890563886.123";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "1600";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "156,0";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "-156,0";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "0.12";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "0.0";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "01.12";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "0125";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "-0125";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        number = "sdw5655";
//        System.out.println(number+": "+ConvertUpMoney.toChinese(number));
//
//        System.out.println(null+": "+ConvertUpMoney.toChinese(null));
//    }
}
