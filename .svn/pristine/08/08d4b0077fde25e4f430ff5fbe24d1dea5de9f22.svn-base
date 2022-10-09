/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.SceneInfoDao;

/**
 * 描述  场景导航操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("sceneInfoDao")
public class SceneInfoDaoImpl extends BaseDaoImpl implements SceneInfoDao {

    /**
     * 
     * 描述 添加项目到场景导航
     * @author Flex Hu
     * @created 2015年9月1日 上午11:23:58
     * @param sceneId
     * @param guideIds
     */
    public void saveGuides(String sceneId,String guideIds){
        String[] guideidArray = guideIds.split(",");
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SCENE_GUIDE(SCENE_ID,GUIDE_ID) values(?,?)");
        for(String guideId:guideidArray){
            boolean isExists = this.isExists(sceneId, guideId);
            if(!isExists){
                this.jdbcTemplate.update(sql.toString(),new Object[]{sceneId,guideId});
            }
        }
    }
    
    
    /**
     * 
     * 描述 判断记录是否存在
     * @author Flex Hu
     * @created 2015年9月1日 上午11:25:09
     * @param sceneId
     * @param guideId
     * @return
     */
    public boolean isExists(String sceneId,String guideId){
        StringBuffer sql = new StringBuffer("select count(*) from T_WSBS_SCENE_GUIDE");
        sql.append(" SG WHERE SG.SCENE_ID=? AND SG.GUIDE_ID=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{sceneId,guideId});
        if(count!=0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 移除办事项目
     * @author Flex Hu
     * @created 2015年9月1日 上午11:53:46
     * @param sceneId
     * @param guideIds
     */
    public void removeGuides(String sceneId,String guideIds){
        StringBuffer sql = new StringBuffer("delete from T_WSBS_SCENE_GUIDE");
        sql.append(" SG WHERE SG.SCENE_ID=? AND SG.GUIDE_ID in ");
        sql.append(StringUtil.getValueArray(guideIds));
        this.jdbcTemplate.update(sql.toString(),new Object[]{sceneId});
    }
}
