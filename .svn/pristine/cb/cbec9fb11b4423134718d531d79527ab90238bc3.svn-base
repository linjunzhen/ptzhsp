/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 判断手机号码运营商
 * @author Toddle Chen
 * @created Mar 30, 2015 4:01:18 PM
 */
public class MobileUtil {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(MobileUtil.class);
    /**
     * 移动
     */
    private static final String CMCC = "0";//移动
    /**
     * 联通
     */
    private static final String UNICOM = "1";//联通
    /**
     * 电信
     */
    private static final String TELECOM = "2";//电信
    /**
     * 未知
     */
    private static final String UNKNOWN = "-1";//未知

    /**
     * 判断输入的是否为数字
     * 
     * @返回true说明是数字，false说明不全是数字
     */
    private static boolean isNum(String phoneNum) {
        for (int i = 0; i < phoneNum.length(); i++) {
            if (!Character.isDigit(phoneNum.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断运营商
     */
    public static String validateMobile(String phone) {
        String head1 = "";
        String head2 = "";
        // 去除前后的空白
        phone = phone.trim();
        // 判断输入的电话号码是否合法
        if (phone == null || phone.length() < 11) {
//            log.info("length<11");
            return UNKNOWN;
        } else {
            // 处理国内的+86开头
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
        }
        // 去除+86后电话号码应为11位
        if (phone.length() != 11) {
//            log.info("not = 11");
            return UNKNOWN;
        }
        // 判断去除+86后剩余的是否全为数字
        if (!isNum(phone)) {
//            log.info(" not num");
            return UNKNOWN;
        }
        // 截取前3或前4位电话号码，判断运营商
        head1 = phone.substring(0, 3);
        head2 = phone.substring(0, 4);

        // 移动前三位
        boolean cmcctemp3 = head1.equals("135") || head1.equals("136") || head1.equals("137")
                || head1.equals("138") || head1.equals("139") || head1.equals("147") 
                || head1.equals("150") || head1.equals("151") || head1.equals("152") 
                || head1.equals("157") || head1.equals("158") || head1.equals("159")
                || head1.equals("182") || head1.equals("187") || head1.equals("188");

        if (cmcctemp3) {
            return CMCC;
        }
        // 移动前4位
        boolean cmcctemp4 = head2.equals("1340") || head2.equals("1341") || head2.equals("1342")
                || head2.equals("1343") || head2.equals("1344") || head2.equals("1345") 
                || head2.equals("1346") || head2.equals("1347") || head2.equals("1348");

        if (cmcctemp4) {
            return CMCC;
        }
        // 联通前3位
        boolean unicomtemp = head1.equals("130") || head1.equals("131") || head1.equals("132") 
                || head1.equals("145") || head1.equals("155") || head1.equals("156") 
                || head1.equals("185") || head1.equals("186");

        if (unicomtemp) {
            return UNICOM;
        }
        // 电信前3位
        boolean telecomtemp = head1.equals("133") || head1.equals("153") || head1.equals("180") 
                || head1.equals("189");

        if (telecomtemp) {
            return TELECOM;
        }
        return UNKNOWN;
    }
    /**
     * 描述 测试
     * @author Toddle Chen
     * @created Mar 30, 2015 4:03:13 PM
     * @param args
     */
    public static void main(String[] args) {
        log.info(MobileUtil.validateMobile("13999889090"));  
//        log.info(MobileUtil.validateMobile("13418170986"));  
//        log.info(MobileUtil.validateMobile("15392496493"));  
//        log.info(MobileUtil.validateMobile("13399889090"));  
//        log.info(MobileUtil.validateMobile("erot4543545"));  
//        log.info(MobileUtil.validateMobile("erot543545"));  
    }
}
