/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.developer.dao.CodeProjectDao;
import net.evecom.platform.developer.model.CodeTableInfo;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("codeProjectDao")
public class CodeProjectDaoImpl extends BaseDaoImpl implements CodeProjectDao {

    /**
     * 
     * 描述 获取所有的建模包
     * @author Flex Hu
     * @created 2014年9月17日 上午8:56:12
     * @return
     */
    public List<Map<String,Object>> findAllProject(){
        String sql = "select *from T_MSJW_DEVELOPER_PROJECT ORDER BY CREATE_TIME desc";
        return this.jdbcTemplate.queryForList(sql);
    }
    
    /**
     * 
     * 描述 根据项目ID删除掉数据
     * @author Flex Hu
     * @created 2014年9月19日 上午10:42:26
     * @param projectId
     */
    public void removeByProjectId(String projectId){
        StringBuffer deleteControlSql = new StringBuffer("delete from ");
        deleteControlSql.append("T_MSJW_DEVELOPER_CONTROLCONFIG C WHERE ")
                .append("C.MISSION_ID IN (SELECT M.MISSION_ID FROM T_MSJW_DEVELOPER_CODEMISSION M ")
                .append("WHERE M.PROJECT_ID=? ) ");
        this.jdbcTemplate.update(deleteControlSql.toString(), new Object[] { projectId });
        StringBuffer deleteMissionSql = new StringBuffer("delete from ")
                .append("T_MSJW_DEVELOPER_CODEMISSION M WHERE M.PROJECT_ID=? ");
        this.jdbcTemplate.update(deleteMissionSql.toString(), new Object[] { projectId });
        StringBuffer deleteProjectSql = new StringBuffer("delete from ")
                .append("T_MSJW_DEVELOPER_PROJECT P WHERE P.PROJECT_ID=? ");
        this.jdbcTemplate.update(deleteProjectSql.toString(),new Object[]{projectId});
    }
    
}
