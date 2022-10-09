/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.lang.String;

/**
 * 
 * 描述：密码强度验证
 * 
 * @author Rider Chen
 * @created 2020年6月28日 下午2:33:06
 */
public class CheckPwd {
    /**
     * @brief 检测密码中字符长度
     * @param[in] password 密码字符串
     * @return 符合长度要求 返回true
     */
    public static boolean checkPasswordLength(String password) {
        boolean flag = false;

        if ("".equals(CheckPwdConfig.maxLength)) {
            if (password.length() >= Integer.parseInt(CheckPwdConfig.minLength)) {
                flag = true;
            }
        } else {
            if (password.length() >= Integer.parseInt(CheckPwdConfig.minLength)
                    && password.length() <= Integer.parseInt(CheckPwdConfig.maxLength)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @brief 检测密码中是否包含数字
     * @param[in] password 密码字符串
     * @return 包含数字 返回true
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int num_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isDigit(chPass[i])) {
                num_count++;
            }
        }

        if (num_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @brief 检测密码中是否包含字母（不区分大小写）
     * @param[in] password 密码字符串
     * @return 包含字母 返回true
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLetter(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @brief 检测密码中是否包含小写字母
     * @param[in] password 密码字符串
     * @return 包含小写字母 返回true
     */
    public static boolean checkContainLowerCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLowerCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @brief 检测密码中是否包含大写字母
     * @param[in] password 密码字符串
     * @return 包含大写字母 返回true
     */
    public static boolean checkContainUpperCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isUpperCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @brief 检测密码中是否包含特殊符号
     * @param[in] password 密码字符串
     * @return 包含特殊符号 返回true
     */
    public static boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int special_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (CheckPwdConfig.specialChar.indexOf(chPass[i]) != -1) {
                special_count++;
            }
        }

        if (special_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @brief 键盘规则匹配器 横向连续检测
     * @param[in] password 密码字符串
     * @return 含有横向连续字符串 返回true
     */
    public static boolean checkLateralKeyboardSite(String password) {
        String t_password = new String(password);
        // 将所有输入字符转为小写
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘横向规则检测
         */
        boolean flag = false;
        int arrLen = CheckPwdConfig.keyboardHorizontalArr.length;
        int limit_num = Integer.parseInt(CheckPwdConfig.limitHorizontalNumKey);

        for (int i = 0; i + limit_num <= n; i++) {
            String str = t_password.substring(i, i + limit_num);
            String distinguishStr = password.substring(i, i + limit_num);

            for (int j = 0; j < arrLen; j++) {
                String CheckPwdConfigStr = CheckPwdConfig.keyboardHorizontalArr[j];
                String revOrderStr = new StringBuffer(CheckPwdConfig.keyboardHorizontalArr[j]).reverse().toString();

                // 检测包含字母(区分大小写)
                if ("enable".equals(CheckPwdConfig.checkDistingguishCase)) {
                    // 考虑 大写键盘匹配的情况
                    String UpperStr = CheckPwdConfig.keyboardHorizontalArr[j].toUpperCase();
                    if ((CheckPwdConfigStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                } else {
                    if (CheckPwdConfigStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    if (revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @brief 键盘规则匹配器 斜向规则检测
     * @param[in] password 密码字符串
     * @return 含有斜向连续字符串 返回true
     */
    public static boolean checkKeyboardSlantSite(String password) {
        String t_password = new String(password);
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘斜线方向规则检测
         */
        boolean flag = false;
        int arrLen = CheckPwdConfig.keyboardSlopeArr.length;
        int limit_num = Integer.parseInt(CheckPwdConfig.limitSlopeNumKey);

        for (int i = 0; i + limit_num <= n; i++) {
            String str = t_password.substring(i, i + limit_num);
            String distinguishStr = password.substring(i, i + limit_num);
            for (int j = 0; j < arrLen; j++) {
                String CheckPwdConfigStr = CheckPwdConfig.keyboardSlopeArr[j];
                String revOrderStr = new StringBuffer(CheckPwdConfig.keyboardSlopeArr[j]).reverse().toString();
                // 检测包含字母(区分大小写)
                if ("enable".equals(CheckPwdConfig.checkDistingguishCase)) {

                    // 考虑 大写键盘匹配的情况
                    String UpperStr = CheckPwdConfig.keyboardSlopeArr[j].toUpperCase();
                    if ((CheckPwdConfigStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if ((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                } else {
                    if (CheckPwdConfigStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    // 考虑逆序输入情况下 连续输入
                    if (revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @brief 评估a-z,z-a这样的连续字符
     * @param[in] password 密码字符串
     * @return 含有a-z,z-a连续字符串 返回true
     */
    public static boolean checkSequentialChars(String password) {
        String t_password = new String(password);
        boolean flag = false;
        int limit_num = Integer.parseInt(CheckPwdConfig.limitLogicNumChar);
        int normal_count = 0;
        int reversed_count = 0;

        // 检测包含字母(区分大小写)
        if ("enable".equals(CheckPwdConfig.checkDistingguishCase)) {

        } else {
            t_password = t_password.toLowerCase();
        }
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();

        for (int i = 0; i + limit_num <= n; i++) {
            normal_count = 0;
            reversed_count = 0;
            for (int j = 0; j < limit_num - 1; j++) {
                if (pwdCharArr[i + j + 1] - pwdCharArr[i + j] == 1) {
                    normal_count++;
                    if (normal_count == limit_num - 1) {
                        return true;
                    }
                }

                if (pwdCharArr[i + j] - pwdCharArr[i + j + 1] == 1) {
                    reversed_count++;
                    if (reversed_count == limit_num - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @brief 评估aaaa,1111这样的相同连续字符
     * @param[in] password 密码字符串
     * @return 含有aaaa,1111等连续字符串 返回true
     */
    public static boolean checkSequentialSameChars(String password) {
        String t_password = new String(password);
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();
        boolean flag = false;
        int limit_num = Integer.parseInt(CheckPwdConfig.limitNumSameChar);
        int count = 0;
        for (int i = 0; i + limit_num <= n; i++) {
            count = 0;
            for (int j = 0; j < limit_num - 1; j++) {
                if (pwdCharArr[i + j] == pwdCharArr[i + j + 1]) {
                    count++;
                    if (count == limit_num - 1) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * @brief 评估密码中包含的字符类型是否符合要求
     * @param[in] password 密码字符串
     * @return 符合要求 返回true
     */
    public static boolean evalPwd(String password) {
        if (password == null || "".equals(password)) {
            return false;
        }
        boolean flag = false;

        /**
         * 检测长度
         */
        if ("enable".equals(CheckPwdConfig.checkPasswordLength)) {
            flag = checkPasswordLength(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测包含数字
         */
        if ("enable".equals(CheckPwdConfig.checkContainDigit)) {
            flag = checkContainDigit(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测包含字母(区分大小写)
         */
        if ("enable".equals(CheckPwdConfig.checkDistingguishCase)) {
            // 检测包含小写字母
            if ("enable".equals(CheckPwdConfig.checkLowerCase)) {
                flag = checkContainLowerCase(password);
                if (!flag) {
                    return false;
                }
            }

            // 检测包含大写字母
            if ("enable".equals(CheckPwdConfig.checkUpperCase)) {
                flag = checkContainUpperCase(password);
                if (!flag) {
                    return false;
                }
            }
        } else {
            flag = checkContainCase(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测包含特殊符号
         */
        if ("enable".equals(CheckPwdConfig.checkContainSpecialChar)) {
            flag = checkContainSpecialChar(password);
            if (!flag) {
                return false;
            }
        }

        /**
         * 检测键盘横向连续
         */
        if ("enable".equals(CheckPwdConfig.checkHorizontalKeySequential)) {
            flag = checkLateralKeyboardSite(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测键盘斜向连续
         */
        if ("enable".equals(CheckPwdConfig.checkSlopeKeySequential)) {
            flag = checkKeyboardSlantSite(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测逻辑位置连续
         */
        if ("enable".equals(CheckPwdConfig.checkLogicSequential)) {
            flag = checkSequentialChars(password);
            if (flag) {
                return false;
            }
        }

        /**
         * 检测相邻字符是否相同
         */
        if ("enable".equals(CheckPwdConfig.checkSequentialCharSame)) {
            flag = checkSequentialSameChars(password);
            if (flag) {
                return false;
            }
        }
        return true;
    }
}