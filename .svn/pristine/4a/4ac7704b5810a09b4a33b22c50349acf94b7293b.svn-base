/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.hflow.dao.FlowHangInfoDao;

/**
 * 描述  流程挂起信息表操作dao
 * @author  Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("flowHangInfoDao")
public class FlowHangInfoDaoImpl extends BaseDaoImpl implements FlowHangInfoDao {

    /**
     * 
     * 描述 更新流程挂机结束时间
     * @author Faker Li
     * @created 2016年4月14日 上午10:41:24
     * @param selectTaskIds
     * @see net.evecom.platform.hflow.dao.FlowHangInfoDao#endHangTime(java.lang.String)
     */
    public void endHangTime(String taskId,String endTime) {
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_HANGINFO T SET T.END_TIME = ? ");
        sql.append(" WHERE T.HANG_ID IN ( ");
        sql.append(" SELECT JH.HANG_ID FROM(SELECT * FROM JBPM6_HANGINFO HI WHERE HI.TASK_ID = ? ");
        sql.append(" ORDER BY HI.BEGIN_TIME DESC)JH WHERE ROWNUM=1 ) ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{endTime,taskId});
    }

    /**
     * 描述 更新流程挂机说明
     * @author Water Guo
     * @created 2016年11月11日 上午10:39:16
     * @param taskId,explain
     */
    @Override
    public void hangExplain(String taskId, String explain, String fileid) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("UPDATE JBPM6_HANGINFO T SET T.EXPLAIN = ? ,T.HANG_FILE_ID = ?");
        sql.append(",T.LINK_STATUS='0' ");
        sql.append(" WHERE T.HANG_ID IN ( ");
        sql.append(" SELECT JH.HANG_ID FROM(SELECT * FROM JBPM6_HANGINFO HI WHERE HI.TASK_ID = ? ");
        sql.append(" ORDER BY HI.BEGIN_TIME DESC)JH WHERE ROWNUM=1 ) ");
        this.jdbcTemplate.update(sql.toString(), new Object[]{explain,fileid,taskId});
    }

}
