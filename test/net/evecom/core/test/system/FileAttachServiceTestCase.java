/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.system;

import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.FileAttachService;
import oracle.sql.BLOB;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年1月13日 下午3:07:45
 */
public class FileAttachServiceTestCase extends BaseTestCase {
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年1月13日 下午3:08:34
     */
    @Test
    public void testGenFile(){
        Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{"402881b45239bfc3015239c06af60002"});
        byte[] bys = (byte[])fileAttach.get("FILE_CONTENT");
        String hexString = StringUtil.byte2hex(bys);
        FileUtil.convertStringToFile(hexString, "d:/1.jpg");
    }
}
