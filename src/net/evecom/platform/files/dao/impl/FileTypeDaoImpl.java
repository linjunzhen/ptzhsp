/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.files.dao.FileTypeDao;

/**
 * 描述 多媒体类别管理Dao实现类
 * @author Bryce Zhang
 * @created 2016-11-28 下午3:32:58
 */
@Repository("fileTypeDao")
public class FileTypeDaoImpl extends BaseDaoImpl implements FileTypeDao{
    
    /**
     * 描述 根据父亲节点，查询
     * @author Bryce Zhang
     * @created 2016-11-28 下午3:55:54
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId){
        String sql = "select * from T_FILES_TYPE WHERE PARENT_ID = ? ORDER BY TREE_SN ASC";
        return this.findBySql(sql, new Object[]{parentId}, null);
    }
    
    /**
     * 描述 根据类别id，删除
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:46:11
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        this.remove("T_FILES_TYPE", "TYPE_ID", new Object[]{typeId});
    }
    
    /**
     * 描述 根据类别，统计多媒体资源数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午6:12:36
     * @param fileType
     * @return
     */
    public int countFileNumByType(String fileType){
        StringBuffer sql = new StringBuffer("select sum(filenum) from  (");
        sql.append("select count(*) as filenum from T_FILES_attachment where type_id = ? union ");
        sql.append("select count(*) as filenum from T_FILES_audio where type_id = ? union ");
        sql.append("select count(*) as filenum from T_FILES_image where type_id = ? union ");
        sql.append("select count(*) as filenum from T_FILES_swf where type_id = ? union ");
        sql.append("select count(*) as filenum from T_FILES_video where type_id = ? )");
        return jdbcTemplate.queryForInt(sql.toString(), new Object[]{fileType,fileType,fileType,fileType,fileType});
    }

}
