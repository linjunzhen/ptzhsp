/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.ScheduleDao;

/**
 * 描述  定时器操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("scheduleDao")
public class ScheduleDaoImpl extends BaseDaoImpl implements ScheduleDao {

    /**
     * 
     * 描述 根据定时器状态获取数据列表
     * @author Flex Hu
     * @created 2014年9月29日 上午10:59:16
     * @param status
     * @return
     */
    public List<Map<String,Object>> findByStatus(int status){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").append("T_MSJW_SYSTEM_SCHEDULE WHERE JOB_STATUS=?"
                + " ORDER BY ")
                .append("CREATE_TIME ASC ");
        return this.findBySql(sql.toString(), new Object[]{status}, null);
    }
    
    /**
     * 
     * 描述 更新定时器状态
     * @author Flex Hu
     * @created 2014年9月29日 上午11:56:46
     * @param jobIds
     * @param status
     */
    public void updateStatus(String jobIds,int status){
        StringBuffer sql = new StringBuffer("update T_MSJW_SYSTEM_SCHEDULE S ")
                .append("SET S.JOB_STATUS=? WHERE S.JOB_ID IN ");
        sql.append(StringUtil.getValueArray(jobIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{status});
    }
}
