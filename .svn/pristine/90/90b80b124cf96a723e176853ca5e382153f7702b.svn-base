/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.wsbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.service.BdcYgspfService;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;
import net.evecom.platform.wsbs.service.BusTypeService;

import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年9月30日 下午5:00:44
 */
public class BusTypeTestCase extends BaseTestCase {
    /**
     * 
     */
    @Resource
    private BusTypeService busTypeService;
    /**
     * 
     */
    @Resource
    private BdcYgspfygdjService bdcYgspfygdjService;
    /**
     * 
     */
    @Resource
    private BdcYgspfService bdcYgspfService;
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年9月30日 下午5:01:14
     */
    @Test
    public void impDatas(){
        List<String> lines = StringUtil.readTxtByLine("d:/1.txt", "UTF-8");
        for(String line:lines){
            String[] values = line.trim().split("\\s+");
            String typeName = values[0];
            String typeCode = values[1];
            String parentId = "402883e6501d67d501501d98ab71000c";
            Map<String,Object> busType = new HashMap<String,Object>();
            busType.put("TYPE_NAME", typeName);
            busType.put("TYPE_CODE", typeCode);
            busType.put("PARENT_ID",parentId);
            busTypeService.saveOrUpdateTreeData(parentId, busType, "T_WSBS_BUSTYPE",null);
        }
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月4日 上午10:58:25
     */
    @Test
    public void gentest(){
        Map<String, Object> ygsq = bdcYgspfygdjService.getByJdbc("T_BDCQLC_YGSPFDYQYGDJ", new String[] { "YW_ID" },
                new Object[] { "4028b7b9740b1fe601740b38e8ac0011" });
        Map<String,Object> returnMap = bdcYgspfService.initGenValue(ygsq);

        Properties properties = FileUtil.readProperties("project.properties");
        String AttachFilePath = properties.getProperty("AttachFilePath");
        String path = "attachFiles//signFileTemplate//ygspfdyqdj//bdcsqb-yd.docx";
        bdcYgspfService.generateSQB(returnMap,AttachFilePath+path);
    }
}
