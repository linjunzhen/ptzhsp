/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.bsfw.dao.DataAbutLogDao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

/**
 * 描述  对接日志操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("dataAbutLogDao")
public class DataAbutLogDaoImpl extends BaseDaoImpl implements DataAbutLogDao {
    /**
     * log4j属性
     */
    private static Log log = LogFactory.getLog(DataAbutLogDaoImpl.class);

    /**
     * 
     * 描述 获取日志ID
     * @author Flex Hu
     * @created 2015年10月13日 下午3:48:58
     * @param abutCode
     * @param busIdValue
     * @return
     */
    public String getAbutLogId(String abutCode,String busIdValue){
        if(StringUtils.isNotEmpty(busIdValue)){
            StringBuffer sql = new StringBuffer("select F.LOG_ID from ");
            sql.append("T_BSFW_DATAABUTLOG F WHERE F.ABUT_CODE=? ");
            sql.append(" AND F.BUS_IDVALUE=? ");
            String logId = null;
            try{
                logId = this.jdbcTemplate.queryForObject(sql.toString(), 
                        new Object[]{abutCode,busIdValue},String.class);
            }catch(Exception e){
                log.error(e.getMessage());
            }
            return logId;
        }else{
            return null;
        }
    }
}
