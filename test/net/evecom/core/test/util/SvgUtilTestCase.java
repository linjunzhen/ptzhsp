/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.core.test.util;

import java.io.File;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.SvgUtil;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.1
 * 
 */
public class SvgUtilTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SvgUtilTestCase.class);
    /**
     * @author Water Guo
     * @param args
     */
    public static void main(String[] args) {
        String content = FileUtil.getContentOfFile("d:/1.svg");
        try {
            SvgUtil.convertStr2Png(content, new File("d:/mysvg.png"));
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (TranscoderException e) {
            log.error(e.getMessage());
        }
    }

}
