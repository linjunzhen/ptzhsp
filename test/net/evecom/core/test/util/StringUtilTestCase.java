/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import net.evecom.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年1月13日 下午4:18:08
 */
public class StringUtilTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(StringUtil.class);
    
    public static String readTxtFile(String filePath) {
        StringBuffer sb = new StringBuffer();
        InputStreamReader read = null;// 考虑到编码格式
        BufferedReader bufferedReader = null;
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
                bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    log.info(lineTxt);
                    sb.append(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
            }
        } catch (Exception e) {
            //log.info("读取文件内容出错");
            log.error(e.getMessage());
        }finally{
            if (bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
            if (read!=null) {
                try {
                    read.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
    /**
     * 描述
     * @author Flex Hu
     * @created 2015年1月13日 下午4:18:08
     * @param args
     * @throws UnknownHostException 
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
        /*float num= (float)23/90;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String s = df.format(num);//返回的是String类型
        log.info(s);
        log.info(StringUtil.getPercentFormat(Double.parseDouble(s), 2));
        log.info(StringUtil.getPercentFormatDouble(Double.parseDouble(s), 1));*/
        double a = 93.3;
        double b = 43.7;
        double c = 77.3;
        double d = (a+b+c)/3;
        DecimalFormat df = new DecimalFormat("0.0");
        log.info(df.format(d));
    }

}
