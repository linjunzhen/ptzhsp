/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.developer.dao.CodeMissionDao;

/**
 * 描述  代码任务操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("codeMissionDao")
public class CodeMissionDaoImpl extends BaseDaoImpl implements CodeMissionDao {

    /**
     * 
     * 描述 根据任务ID获取项目ID
     * @author Flex Hu
     * @created 2014年9月21日 上午8:20:50
     * @param missionId
     * @return
     */
    public String getProjectId(String missionId){
        StringBuffer sql = new StringBuffer("select PROJECT_ID FROM ")
                .append("T_MSJW_DEVELOPER_CODEMISSION WHERE MISSION_ID=? ");
        return (String) this.getUniqueBySql(sql.toString(), new Object[]{missionId});
    }
}
