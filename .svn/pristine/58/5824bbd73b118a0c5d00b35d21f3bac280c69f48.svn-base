/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.developer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月21日 上午11:35:10
 */
public class TestAddMethodToJava {
    /**
     * 描述
     * @author Flex Hu
     * @created 2014年9月21日 上午11:35:10
     * @param args
     */
    public static void main(String[] args) {
        Map<String,Object> root = new HashMap<String,Object>();
        root.put("JSPNAME", "SysLogList");
        root.put("packageName","system");
        root.put("className","SysLog");
        String content = FileUtil.getContentOfFile("d:/ForwardToJsp.java");
        String result = FreeMarkerUtil.getResultString(content, root);
        File f1 =new File("d:/SysLogController.java");
        String javaContent = FileUtil.addMethodToJava(f1,result);
        FileUtil.writeTextToFile(f1, javaContent);
    }

}
