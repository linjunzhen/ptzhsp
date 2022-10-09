/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.usercenter.dao.impl;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.usercenter.dao.TagDao;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-4-28 下午4:55:34
 */
@Repository("tagDao")
public class TagDaoImpl extends BaseDaoImpl implements TagDao {
    /**
     * 
     * 根据类别ID删除掉数据
     * @author Danto Huang
     * @created 2017-5-2 下午2:22:55
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        StringBuffer deleteDic = new StringBuffer("delete from T_USERCENTER_TAG D")
            .append(" WHERE D.TYPE_ID=? ");
        this.jdbcTemplate.update(deleteDic.toString(), new Object[]{typeId});
        this.remove("T_USERCENTER_TAGTYPE","TYPE_ID",new Object[]{typeId});
    }
    
    /**
     * 判断某个目录类别下是否存在相同编码的目录
     *    
     * @author Danto Huang
     * @created 2017-5-2 下午2:38:31
     * @param typeId
     * @param key
     * @return
     */
    public boolean isExist(String typeId, String key) {
        StringBuffer sql = new StringBuffer("select count(*) from")
                .append(" T_USERCENTER_TAG WHERE TYPE_ID=? AND TAG_KEY=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { typeId, key });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
}
