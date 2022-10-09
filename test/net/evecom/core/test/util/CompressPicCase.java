/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import org.junit.Test;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.CompressPic;

/**
 * 图片压缩测试用例
 * 
 * @author Rider Chen
 * 
 */
public class CompressPicCase extends BaseTestCase {

    /**
     * 
     * 描述
     * 
     * @author Flex Hu
     * @created 2016年1月14日 上午10:34:43
     * @param args
     */
    public static void main(String[] args) {
        CompressPic mypic = new CompressPic();
        mypic.compressPic("d:/1.jpg", "d:/1.jpg", 100, 100);
        // mypic.compressPic("d:/", "d:/", "1.jpg", "chris1.jpg", true,1);
    }

    /**
     * 等比例压缩不改变图片大小测试用例
     */
    @Test
    public void pic1() {
        CompressPic mypic = new CompressPic();
        mypic.compressPic("d:/1.jpg", "d:/2.jpg", true, 1);
    }

    /**
     * 等比例压缩改变图片大小测试用例(改变压缩比率为80%)
     */
    @Test
    public void pic2() {
        CompressPic mypic = new CompressPic();
        mypic.compressPic("d:/1.jpg", "d:/2.jpg", true, 0.8);
    }

    /**
     * 不等比例压缩改变图片大小测试用例(图片压缩为800 600)
     */
    @Test
    public void pic3() {
        CompressPic mypic = new CompressPic();
        mypic.compressPic("d:/1.jpg", "d:/2.jpg", 800, 600);
    }
}
