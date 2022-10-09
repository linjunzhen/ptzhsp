/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.files.dao.FileTypeDao;
import net.evecom.platform.files.service.FileTypeService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 多媒体类别管理Service实现类
 * @author Bryce Zhang
 * @created 2016-11-28 下午3:36:11
 */
@Service("fileTypeService")
public class FileTypeServiceImpl extends BaseServiceImpl implements FileTypeService{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(FileTypeServiceImpl.class);
    
    /**
     * 多媒体类别管理Dao
     */
    @Resource
    private FileTypeDao dao;
    
    /**
     * 描述 多媒体类别管理Dao
     * @author Bryce Zhang
     * @created 2016-11-28 下午3:36:11
     * @return
     */
    @Override
    protected GenericDao getEntityDao(){
        return dao;
    }
    
    /**
     * 描述 根据父亲节点，查询
     * @author Bryce Zhang
     * @created 2016-11-28 下午3:55:54
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId){
        return dao.findByParentId(parentId);
    }
    
    /**
     * 描述 根据类别id，删除
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:46:11
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        dao.removeByTypeId(typeId);
    }
    
    /**
     * 描述 根据类别，统计多媒体资源数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午6:12:36
     * @param fileType
     * @return
     */
    public int countFileNumByType(String fileType){
        return dao.countFileNumByType(fileType);
    }

}
