/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.nanwei;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.test.flow.FromTaskJsonTestCase;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.service.DataAbutmentService;
import oracle.sql.BLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月16日 上午9:40:35
 */
public class ProvinceInfoTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ProvinceInfoTestCase.class);
    /**
     * 
     */
    @Resource
    private DataAbutmentService dataAbutmentService;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @throws SQLException 
     * @created 2015年10月16日 上午9:41:11
     */
    @Test
    public void testFindList() throws SQLException{
        Map<String,Object> dataAbutment = dataAbutmentService.getByJdbc("T_BSFW_DATAABUTMENT",
                new String[]{"AABUT_CODE"},new Object[]{"0001"});
        String dbUrl = (String) dataAbutment.get("DB_URL");
        String dbUserName = (String) dataAbutment.get("DB_USERNAME");
        String dbPass = (String) dataAbutment.get("DB_PASSWORD");
        Connection conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
        StringBuffer sql  = new StringBuffer("select * from PROVINCEINFO P");
        sql.append(" WHERE P.TYPE=? ORDER BY P.CREATETIME ASC");
        List<Map<String,Object>> list = DbUtil.findBySql(conn, sql.toString(),
                new Object[]{AllConstant.SWB_DATA_TYPE_SX});
        for(Map<String,Object> map:list){
            String unId = (String) map.get("UNID");
            String content = (String) map.get("CONTENT");
            if(unId.equals("DBAE37A6A2BCBC6A0EBFEA7F1CD98FAB")){
                log.info(content);
            }
        }
    }
}
