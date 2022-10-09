/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.sync.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.sync.dao.SwbDataDao;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2016-8-25 上午9:56:16
 */
@Repository("swbDataDao")
public class SwbDataDaoImpl extends BaseDaoImpl implements SwbDataDao {

    /**
     * 
     * 描述    是否保存过办件信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param exeId
     * @return
     */
    public boolean isHaveSaveBjxxInfo(String exeId){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) ");
        sql.append(" FROM T_BSFW_SWBDATA_RES R WHERE R.EXE_ID=? and R.DATA_TYPE=10");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 
     * 描述   获取所有已办流程任务
     * @author Danto Huang
     * @created 2016-8-25 下午4:22:16
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> getDoneTaskByExeId(String exeId){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from JBPM6_TASK t where t.exe_id=? and t.is_real_handled=1 and t.step_seq<>1");
        return this.findBySql(sql.toString(), new Object[]{exeId}, null);
    }
    

    /**
     * 
     * 描述    是否保存过过程信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param taskId
     * @return
     */
    public boolean isHaveSaveGcxxInfo(String taskId){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) ");
        sql.append(" FROM T_BSFW_SWBDATA_RES R WHERE R.TASK_ID=? and R.DATA_TYPE=20");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{taskId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 
     * 描述   获取办结节点流程任务
     * @author Danto Huang
     * @created 2016-8-25 下午4:22:16
     * @param exeId
     * @return
     */
    public Map<String,Object> getEndTaskByExeId(String exeId){
        StringBuffer sql = new StringBuffer("select * from JBPM6_TASK t where t.exe_id=? ");
        sql.append("and t.is_real_handled=1 and t.end_time =(select max(e.end_time) from JBPM6_TASK e ");
        sql.append("where e.exe_id=? and e.is_real_handled=1)");
        return this.getByJdbc(sql.toString(), new Object[]{exeId,exeId});
    }

    /**
     * 
     * 描述    是否保存过结果信息类型的指令数据
     * @author Danto Huang
     * @created 2016-8-25 下午2:31:01
     * @param exeId
     * @return
     */
    public boolean isHaveSaveJgxxInfo(String exeId){
        StringBuffer sql = new StringBuffer("SELECT COUNT(*) ");
        sql.append(" FROM T_BSFW_SWBDATA_RES R WHERE R.EXE_ID=? and R.DATA_TYPE=30");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{exeId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
}
