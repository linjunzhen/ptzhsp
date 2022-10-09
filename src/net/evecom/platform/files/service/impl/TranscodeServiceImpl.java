/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.files.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.files.dao.TranscodeDao;
import net.evecom.platform.files.service.TranscodeService;

/**
 * 描述 视频转码管理Service实现类
 * @author Bryce Zhang
 * @created 2016-10-27 下午6:21:07
 */
@Service("transcodeService")
public class TranscodeServiceImpl extends BaseServiceImpl implements TranscodeService{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TranscodeServiceImpl.class);
    
    /**
     * 转码配置管理Dao
     */
    @Resource
    private TranscodeDao dao;
    
    /** 
     * 描述 转码配置管理Dao
     * @author Bryce Zhang
     * @created 2016-10-27 下午6:22:41
     * @return
     */
    @Override
    protected GenericDao getEntityDao(){
        return dao;
    }
    
    /**
     * 描述 获取某媒体的转码配置
     * @author Bryce Zhang
     * @created 2016-10-27 下午6:42:50
     * @param mediaType
     * @return
     */
    public Map<String, Object> getConfByMedia(String mediaType){
        StringBuffer sql = new StringBuffer("select t.* from T_FILES_TRANSCODECONFIG t ");
        sql.append("where t.MEDIA_TYPE = ? order by t.UPDATE_TIME desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{mediaType.toLowerCase()}, null);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 描述 保存或更新转码配置
     * @author Bryce Zhang
     * @created 2016-10-28 上午9:31:19
     * @param variables
     */
    public void saveOrUpdateConf(Map<String, Object> variables){
        //删除原配置
        StringBuffer delSql = new StringBuffer("delete from T_FILES_TRANSCODECONFIG where MEDIA_TYPE = ? ");
        dao.executeSql(delSql.toString(), new Object[]{variables.get("MEDIA_TYPE")});
        //新增配置
        StringBuffer insertSql = new StringBuffer("insert into T_FILES_TRANSCODECONFIG");
        insertSql.append("(CONFIG_ID,MEDIA_TYPE,AUDIO_CODEC,AUDIO_BITRATE,AUDIO_CHANNELS,AUDIO_SAMPLINGRATE,");
        insertSql.append("VIDEO_CODEC,VIDEO_BITRATE,VIDEO_FRAMERATE,VIDEO_SIZE,UPDATE_USER,UPDATE_TIME) ");
        insertSql.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
        String uuid = UUIDGenerator.getUUID();
        dao.executeSql(insertSql.toString(), new Object[]{uuid, variables.get("MEDIA_TYPE"), 
               variables.get("AUDIO_CODEC"), variables.get("AUDIO_BITRATE"), variables.get("AUDIO_CHANNELS"), 
               variables.get("AUDIO_SAMPLINGRATE"), variables.get("VIDEO_CODEC"), variables.get("VIDEO_BITRATE"), 
               variables.get("VIDEO_FRAMERATE"), variables.get("VIDEO_SIZE"), variables.get("UPDATE_USER"), 
               variables.get("UPDATE_TIME")});
    }

}
