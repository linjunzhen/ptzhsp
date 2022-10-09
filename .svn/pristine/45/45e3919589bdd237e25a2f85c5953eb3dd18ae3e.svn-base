/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;


import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 描述 密码强度验证配置文件
 * @author Rider Chen
 * @created 2020年6月28日 下午2:33:51
 */
public class CheckPwdConfig {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CheckPwdConfig.class);

    /**
     * 密码口令检测对应系统等级
     */
    public static String systemGrade;

    /**
     * 是否检测密码口令长度标识
     */
    public static String checkPasswordLength;
    /**
     * 密码最小长度，默认为8
     */
    public static String minLength;
    /**
     * 密码最大长度，默认为20
     */
    public static String maxLength;

    /**
     * 是否包含数字
     */
    public static String checkContainDigit;
    /**
     * 是否区分大小写
     */
    public static String checkDistingguishCase;
    /**
     * 是否包含小写字母
     */
    public static String checkLowerCase;
    /**
     * 是否包含大写字母
     */
    public static String checkUpperCase;
    /**
     * 是否包含特殊符号
     */
    public static String checkContainSpecialChar;
    /**
     * 默认特殊符号集合
     */
    public static String defaultSpecialChar = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    /**
     * 特殊符号集合
     */
    public static String specialChar;

    /**
     * 是否检测键盘按键横向连续
     */
    public static String checkHorizontalKeySequential;
    /**
     * 键盘物理位置横向不允许最小的连续个数
     */
    public static String limitHorizontalNumKey;
    /**
     * 是否检测键盘按键斜向连续
     */
    public static String checkSlopeKeySequential;
    /**
     * 键盘物理位置斜向不允许最小的连续个数
     */
    public static String limitSlopeNumKey;

    /**
     * 是否检测逻辑位置连续
     */
    public static String checkLogicSequential;
    /**
     * 密码口令中字符在逻辑位置上不允许最小的连续个数
     */
    public static String limitLogicNumChar;

    /**
     * 是否检测连续字符相同
     */
    public static String checkSequentialCharSame;
    /**
     * 密码口令中相同字符不允许最小的连续个数
     */
    public static String limitNumSameChar;

    /**
     * 键盘横向方向规则
     */
    public static String[] keyboardHorizontalArr = { "01234567890", "qwertyuiop", "asdfghjkl", "zxcvbnm", };
    /**
     * 键盘斜线方向规则
     */
    public static String[] keyboardSlopeArr = { "1qaz", "2wsx", "3edc", "4rfv", "5tgb", "6yhn", "7ujm", "8ik,",
            "9ol.", "0p;/", "=[;.", "-pl,", "0okm", "9ijn", "8uhb", "7ygv", "6tfc", "5rdx", "4esz" };

    static {
        try {
            // 读取属性文件enc.properties
            Properties prop = FileUtil.readProperties("password.properties");
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();

                if (key.equals("systemGrade")) {
                    systemGrade = prop.getProperty(key);
                }

                if (key.equals("checkPasswordLength")) {
                    checkPasswordLength = prop.getProperty(key);
                }
                if (key.equals("limitPassMinLength")) {
                    minLength = prop.getProperty(key);
                }
                if (key.equals("limitPassMaxLength")) {
                    maxLength = prop.getProperty(key);
                }

                if (key.equals("checkContainDigit")) {
                    checkContainDigit = prop.getProperty(key);
                }
                if (key.equals("checkContainUpperLowerCase")) {
                    checkDistingguishCase = prop.getProperty(key);
                }
                if (key.equals("checkContainLowerCase")) {
                    checkLowerCase = prop.getProperty(key);
                }
                if (key.equals("checkContainUpperCase")) {
                    checkUpperCase = prop.getProperty(key);
                }
                if (key.equals("checkContainSpecialChar")) {
                    checkContainSpecialChar = prop.getProperty(key);
                }
                if (key.equals("specialCharSet")) {
                    specialChar = prop.getProperty(key);
                }

                if (key.equals("checkHorizontalKeySequential")) {
                    checkHorizontalKeySequential = prop.getProperty(key);
                }
                if (key.equals("horizontalKeyLimitNum")) {
                    limitHorizontalNumKey = prop.getProperty(key);
                }
                if (key.equals("checkSlopeKeySequential")) {
                    checkSlopeKeySequential = prop.getProperty(key);
                }
                if (key.equals("slopeKeyLimitNum")) {
                    limitSlopeNumKey = prop.getProperty(key);
                }

                if (key.equals("checkLogicSequential")) {
                    checkLogicSequential = prop.getProperty(key);
                }
                if (key.equals("logicLimitNum")) {
                    limitLogicNumChar = prop.getProperty(key);
                }

                if (key.equals("checkSequentialCharSame")) {
                    checkSequentialCharSame = prop.getProperty(key);
                }
                if (key.equals("sequentialCharNum")) {
                    limitNumSameChar = prop.getProperty(key);
                }

            }

            if ("2".equals(systemGrade) || "3".equals(systemGrade)) {

                if ("".equals(checkPasswordLength)) {
                    checkPasswordLength = "enable";
                    minLength = "8";
                    maxLength = "20";
                }
                if ("".equals(checkContainDigit)) {
                    checkContainDigit = "enable";
                }
                if ("".equals(checkDistingguishCase)) {
                    checkDistingguishCase = "disable";
                }
                if ("".equals(checkLowerCase)) {
                    checkLowerCase = "enable";
                }
                if ("".equals(checkUpperCase)) {
                    checkUpperCase = "enable";
                }
                if ("".equals(checkContainSpecialChar)) {
                    if ("2".equals(systemGrade)) {
                        checkContainSpecialChar = "disable";
                    } else {
                        checkContainSpecialChar = "enable";
                        if ("".equals(specialChar)) {
                            specialChar = defaultSpecialChar;
                        }
                    }
                }

                if ("".equals(checkHorizontalKeySequential)) {
                    checkHorizontalKeySequential = "enable";
                    if ("2".equals(systemGrade)) {
                        limitHorizontalNumKey = "4";
                    } else {
                        limitHorizontalNumKey = "3";
                    }
                }

                if ("".equals(checkSlopeKeySequential)) {
                    checkSlopeKeySequential = "enable";
                    if ("2".equals(systemGrade)) {
                        limitSlopeNumKey = "4";
                    } else {
                        limitSlopeNumKey = "3";
                    }
                }

                if ("".equals(checkLogicSequential)) {
                    checkLogicSequential = "enable";
                    if ("2".equals(systemGrade)) {
                        limitLogicNumChar = "4";
                    } else {
                        limitLogicNumChar = "3";
                    }

                }
                if ("".equals(checkSequentialCharSame)) {
                    checkSequentialCharSame = "enable";
                    if ("2".equals(systemGrade)) {
                        limitNumSameChar = "4";
                    } else {
                        limitNumSameChar = "3";
                    }
                }
            } else {
                systemGrade = "3";
                checkPasswordLength = "enable";
                minLength = "8";
                maxLength = "20";
                checkContainDigit = "enable";
                checkLowerCase = "enable";
                checkUpperCase = "enable";
                checkContainSpecialChar = "enable";
                checkHorizontalKeySequential = "enable";
                limitHorizontalNumKey = "3";
                checkSlopeKeySequential = "enable";
                limitSlopeNumKey = "3";
                checkLogicSequential = "enable";
                limitLogicNumChar = "3";
                checkSequentialCharSame = "enable";
                limitNumSameChar = "3";
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }
}
