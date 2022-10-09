/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.files.dao.AudioFileDao;
import net.evecom.platform.files.service.AudioFileService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 多媒体中心音频类管理Service实现类
 * @author Bryce Zhang
 * @created 2016-10-19 下午4:54:55
 */
@Service("audioFileService")
public class AudioFileServiceImpl extends BaseServiceImpl implements AudioFileService{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(AudioFileServiceImpl.class);
    
    /**
     * 多媒体中心音频类管理Dao
     */
    @Resource
    private AudioFileDao dao;
    
    /**
     * 描述 多媒体中心音频类管理Dao
     * @author Bryce Zhang
     * @created 2016-10-19 下午4:59:54
     * @return
     */
    @Override
    protected GenericDao getEntityDao(){
        return dao;
    }
    
    /**
     * 描述 根据sqlFilter获取列表
     * @author Bryce Zhang
     * @created 2016-10-20 上午10:23:54
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_FILES_AUDIO T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 描述 根据类别，统计数量
     * @author Bryce Zhang
     * @created 2016-11-28 下午5:51:40
     * @param fileType
     * @return
     */
    public int countByFileType(String fileType){
        return dao.countByFileType(fileType);
    }
    
    /**
     * 描述 保存移动结果
     * @author Bryce Zhang
     * @created 2016-11-28 下午8:31:23
     * @param colValues
     */
    public void saveMove(Map<String, Object> colValues){
        dao.saveMove(colValues);
    }
    
    /**
     * 描述 根据主键id集合，查询列表
     * @author Bryce Zhang
     * @created 2016-12-1 上午10:21:46
     * @param attachIds
     * @return
     */
    public List<Map<String, Object>> findByIds(String attachIds){
        StringBuffer sql = new StringBuffer("select t.AUDIO_ID as FILE_ID, t.AUDIO_NAME as FILE_NAME, ");
        sql.append("t.AUDIO_PATH as FILE_PATH, t.FILESERVER_URL as FILESERVER_URL, t.AUDIO_SUFFIX as FILE_SUFFIX, ");
        sql.append("t.UPLOADER_NAME as UPLOADER_NAME from T_FILES_AUDIO t where t.AUDIO_ID in ");
        sql.append(StringUtil.getValueArray(attachIds));
        sql.append(" order by t.CREATE_TIME desc ");
        return dao.findBySql(sql.toString(), null, null);
    }

}
