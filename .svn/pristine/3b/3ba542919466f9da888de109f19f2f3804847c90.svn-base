/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.developer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.developer.model.CodeTableInfo;
import net.evecom.platform.developer.service.CodeProjectService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午10:35:25
 */
public class CodeProjectTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CodeProjectTestCase.class);
    /***
     * 引入service
     */
    @Resource
    private CodeProjectService codeProjectService;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午10:36:06
     */
    @Test
    public void saveOrUpdate(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("PACKAGE_NAME", "system");
        map.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        map.put("MAIN_TABLENAME", "T_MSJW_SYSTEM_SCHEDULE");
        map.put("MAIN_CLASSNAME","Schedule");
        map.put("MAIN_CHINESE","定时任务");
        codeProjectService.saveOrUpdate(map, "T_MSJW_DEVELOPER_PROJECT",null);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月14日 下午1:39:23
     * @param args
     */
    public static void main(String[] args){
        StringBuffer sql = new StringBuffer("select a.name,a.sex from t_system_user a left join");
        sql.append(" t_system_dept d on a.defId=d.depId ");
        Map<String,String> tables = new HashMap<String,String>();
        ///Set<String> tableNames = new HashSet<String>();
        String upperSql = sql.toString().toUpperCase();
        String tableSql = "";
        if(upperSql.indexOf("WHERE")!=-1){
            tableSql = upperSql.substring(upperSql.indexOf("FROM")+4,upperSql.indexOf("WHERE"));
            
        }else{
            tableSql = upperSql.substring(upperSql.indexOf("FROM")+4,upperSql.length());
        }
        if(tableSql.contains("JOIN")){
            String[] splitSqls = tableSql.split("JOIN");
            for(String splitSql:splitSqls){
                String tableName=splitSql.trim().split(" ")[0];
                String aliasName=splitSql.trim().split(" ")[1];
                tables.put(tableName,aliasName);
                ///tableNames.add(tableName);
            }
        }else{
            String[] targetSqls = tableSql.trim().split(",");
            for(String targetSql:targetSqls){
                tables.put(targetSql.split(" ")[0],targetSql.split(" ")[1]);
            }
        }
        for(String key:tables.keySet()){
            log.info("Key:"+key+","+tables.get(key));
        }
    }
    
    @Test
    public void findByProject(){
        List<CodeTableInfo> infos = codeProjectService.findByProject("402881e54890d095014890f31dbf000f");
        log.info(infos.size());
    }
}
