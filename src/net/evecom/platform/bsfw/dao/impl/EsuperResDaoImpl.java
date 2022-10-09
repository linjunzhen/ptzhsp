/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.bsfw.dao.EsuperResDao;
import net.evecom.platform.bsfw.service.EsuperResService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月13日 下午1:59:15
 */
@Repository("esuperResDao")
public class EsuperResDaoImpl extends BaseDaoImpl implements EsuperResDao {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EsuperResDaoImpl.class);
    /**
     * 
     * 描述 根据实例ID和数据类型判断是否存在记录
     * @author Flex Hu
     * @created 2016年4月13日 下午2:55:26
     * @param exeId
     * @param dataType
     * @return
     */
    public boolean isExists(String exeId,String dataType){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM ");
        sql.append(" T_BSFW_ESUPER_RES R WHERE R.BUS_ID=? AND R.DATA_TYPE=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{exeId,dataType});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 判断数据是否存在
     * @author Flex Hu
     * @created 2016年4月14日 上午10:46:08
     * @param exeId
     * @param taskId
     * @param dataType
     * @return
     */
    public boolean isExists(String exeId,String taskId,String dataType){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM ");
        sql.append(" T_BSFW_ESUPER_RES R WHERE R.BUS_ID=? AND R.DATA_TYPE=? ");
        sql.append(" AND R.TASK_ID=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{exeId,dataType,taskId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 获取过程ID
     * @author Flex Hu
     * @created 2016年5月4日 下午4:50:10
     * @param nodeName
     * @param itemCode
     * @return
     */
    public String getProcessId(String nodeName,String itemCode){
        String dicCode = EsuperResService.ITEM_HEAD+itemCode;
        StringBuffer sql = new StringBuffer("select T.DIC_CODE from T_MSJW_SYSTEM_DICTIONARY T");
        sql.append(" WHERE T.TYPE_CODE='GCBM' AND T.DIC_NAME=? AND T.DIC_CODE LIKE ? ");
        String processid = null;
        try{
            processid = this.jdbcTemplate.queryForObject(sql.toString(), 
                    new Object[]{nodeName,dicCode+".%"},String.class);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return processid;
    }
}
