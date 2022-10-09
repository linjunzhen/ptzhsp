/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.wsbs.dao.LawReguDao;

/**
 * 描述  法律法规操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("lawReguDao")
public class LawReguDaoImpl extends BaseDaoImpl implements LawReguDao {

    /**
     * 
     * 描述 根据办事项目ID获取法律法规列表
     * @author Flex Hu
     * @created 2015年9月2日 上午9:10:14
     * @param guideId
     * @return
     */
    public List<Map<String,Object>> findByGuideId(String guideId){
        StringBuffer sql = new StringBuffer("SELECT R.LAW_ID,R.LAW_TITLE FROM");
        sql.append(" T_WSBS_LAWREGU R WHERE R.LAW_ID IN (SELECT ");
        sql.append(" G.LAW_ID FROM T_WSBS_LAW_GUIDE G ");
        sql.append(" WHERE G.GUIDE_ID=? ) ");
        sql.append(" ORDER BY R.UPDATE_TIME DESC ");
        return this.jdbcTemplate.queryForList(sql.toString(), new Object[]{guideId});
    }
    
    /**
     * 
     * 描述 根据指南ID删除掉中间表配置信息
     * @author Flex Hu
     * @created 2015年9月2日 上午9:54:08
     * @param guideId
     */
    public void deleteMiddelConfig(String guideId){
        StringBuffer sql = new StringBuffer("DELETE FROM T_WSBS_LAW_GUIDE LG");
        sql.append(" WHERE LG.GUIDE_ID=? ");
        this.jdbcTemplate.update(sql.toString(),new Object[]{guideId});
    }
    
    /**
     * 
     * 描述 更新中间表
     * @author Flex Hu
     * @created 2015年9月2日 上午10:10:51
     * @param guideId
     * @param lawIds
     */
    public void saveMiddleConfig(String guideId,String lawIds){
        StringBuffer sql = new StringBuffer("insert into T_WSBS_LAW_GUIDE");
        sql.append("(LAW_ID,GUIDE_ID) values(?,?) ");
        String[] lawIdArray = lawIds.split(",");
        for(String lawId:lawIdArray){
            this.jdbcTemplate.update(sql.toString(), new Object[]{lawId,guideId});
        }
    }
}
