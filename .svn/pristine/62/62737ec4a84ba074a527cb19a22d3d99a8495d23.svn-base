/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.commercial.dao.ExtraDicTypeDao;

/**
 * 
 * 描述
 * @author Danto Huang
 * @created 2021年3月30日 上午11:39:53
 */
@Repository("extraDicTypeDao")
public class ExtraDicTypeDaoImpl extends BaseDaoImpl implements ExtraDicTypeDao {

    /**
     * 
     * 根据字典类别ID删除掉数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:49:21
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        StringBuffer deleteDic = new StringBuffer("delete from T_COMMERCIAL_DIC D")
            .append(" WHERE D.TYPE_ID=? ");
        this.jdbcTemplate.update(deleteDic.toString(), new Object[]{typeId});
        this.remove("T_COMMERCIAL_DICTYPE","TYPE_ID",new Object[]{typeId});
    }
    
    /**
     * 
     * 根据父亲ID获取类别数据
     * @author Danto Huang
     * @created 2021年3月30日 上午11:54:57
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findByParentId(String parentId){
        String sql = "select * from T_COMMERCIAL_DICTYPE WHERE PARENT_ID=? ORDER BY TREE_SN ASC";
        return this.findBySql(sql, new Object[]{parentId}, null);
    }
}
