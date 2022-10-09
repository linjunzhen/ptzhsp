/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.files.dao.ImageFileDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 描述 多媒体中心图片类管理Dao实现类
 * @author Bryce Zhang
 * @created 2016-10-19 下午5:06:33
 */
@Repository("imageFileDao")
public class ImageFileDaoImpl extends BaseDaoImpl implements ImageFileDao{
    
    /**
     * 描述 根据类别，统计数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:51:40
     * @param fileType
     * @return
     */
    public int countByFileType(String fileType){
        StringBuffer sql = new StringBuffer("select count(*) from T_FILES_IMAGE where TYPE_ID = ? ");
        return jdbcTemplate.queryForInt(sql.toString(), new Object[]{fileType});
    }
    
    /**
     * 描述 保存移动结果
     * @author Bryce Zhang
     * @created 2016-11-28 下午8:31:23
     * @param colValues
     */
    public void saveMove(Map<String, Object> colValues){
        String sourceFileIds = (String)colValues.get("sourceFileIds");
        String targetTypeId = (String)colValues.get("targetTypeId");
        if(StringUtils.isNotEmpty(sourceFileIds) && StringUtils.isNotEmpty(targetTypeId)){
            String[] sourceFileIdArray = sourceFileIds.split(",");
            StringBuffer sql = new StringBuffer("update T_FILES_IMAGE set TYPE_ID = ? where IMAGE_ID = ? ");
            List<Object[]> params = new ArrayList<Object[]>();
            for(String sourceFileId: sourceFileIdArray){
                params.add(new Object[]{targetTypeId, sourceFileId});
            }
            jdbcTemplate.batchUpdate(sql.toString(), params);
        }
    }

}
