/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.developer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.model.TableColumn;
import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.developer.service.CodeMissionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月17日 下午3:53:33
 */
public class CodeMissionTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CodeMissionTestCase.class);
    /**
     * codeMissionService
     */
    @Resource
    private CodeMissionService codeMissionService;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月17日 下午3:55:42
     */
    @Test
    public void saveOrUpdate(){
        Map<String,Object> treeData = new HashMap<String,Object>();
        treeData.put("CONTROL_NAME","Layout控件");
        treeData.put("CONTROL_TYPE","1");
        codeMissionService.saveOrUpdateTreeData(null, treeData, "T_MSJW_DEVELOPER_CONTROLCONFIG",null);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月28日 上午9:12:58
     */
    @Test
    public void findTableColumns(){
        String missionId="4028833648ab7cc00148ab85ea5e0004";
        List<TableColumn> cols = codeMissionService.findTableColumns(missionId,"0",null);
        for(TableColumn col:cols){
            log.info(col.getColumnName()+","+col.getComments()+","+col.getAliasName());
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月28日 上午9:49:16
     * @param args
     */
    public static void main(String[] args){
        String str = "Dictionary";
        log.info(StringUtil.convertFirstLetterToLower(str));
    }
}
