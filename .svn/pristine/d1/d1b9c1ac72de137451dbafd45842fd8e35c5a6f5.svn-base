/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.developer.dao.ControlConfigDao;

/**
 * 描述  配置控件操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("controlConfigDao")
public class ControlConfigDaoImpl extends BaseDaoImpl implements ControlConfigDao {

    /**
     * 
     * 描述 根据任务ID判断是否存在控件
     * @author Flex Hu
     * @created 2014年9月18日 上午11:48:36
     * @param missionId
     * @return
     */
    public boolean isExists(String missionId){
        int count = this.jdbcTemplate.queryForInt("select count(*) from "
                + "T_MSJW_DEVELOPER_CONTROLCONFIG WHERE MISSION_ID=? ",new Object[]{missionId});
        if(count==0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 
     * 描述 根据父亲ID获取到孩子记录列表
     * @author Flex Hu
     * @created 2014年9月23日 下午4:53:14
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        StringBuffer sql = new StringBuffer("select *from T_MSJW_DEVELOPER_CONTROLCONFIG ")
                .append("WHERE PARENT_ID=? ORDER BY CREATE_TIME ASC ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{parentId});
    }
}
