/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao;

import java.util.List;
import java.util.Map;

import net.evecom.core.dao.BaseDao;

/**
 * 描述 系统附件操作dao
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月16日 上午9:42:43
 */
public interface FileAttachDao extends BaseDao {
    /**
     * 
     * 描述 更新业务表字段
     * @author Flex Hu
     * @created 2014年10月2日 上午10:48:45
     * @param fileIds
     * @param recordId
     */
    public void updateTableName(String fileIds,String recordId);
    /**
     * 
     * 描述 更新业务表字段
     * @author Flex Hu
     * @created 2015年8月31日 下午4:57:09
     * @param fileIds
     * @param recordId
     */
    public void updateBusTableRecordId(String[] fileIds,String recordId);
    /**
     * 
     * 描述 获取附件列表
     * @author Flex Hu
     * @created 2014年10月2日 上午11:01:56
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findList(String busTableName,String busRecordId);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年3月17日 下午2:08:34
     * @param file_id
     * @return
     */
    public Map<String,Object> getFileAttachObject(String file_id);
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年3月17日 下午2:21:29
     * @param busTableName
     * @param busRecordId
     * @param attachKey
     * @return
     */
    public List<Map<String,Object>> findList(String busTableName,String busRecordId,String attachKey);
    /**
     * 
     * 描述 根据key和附件IDS判断是否存在这个附件
     * @author Flex Hu
     * @created 2015年4月5日 上午10:02:10
     * @param attachKey
     * @param fileIds
     * @return
     */
    public boolean isExists(String attachKey, String fileIds);
    /**
     * 
     * 描述 根据实例ID和模版ID获取文件
     * @author Flex Hu
     * @created 2016年4月2日 上午8:56:23
     * @param exeId
     * @param tplId
     * @return
     */
    public Map<String,Object> getFilePath(String exeId,String tplId);
    
    /**
     * 描述 根据exid 获取办结结果附件
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> findListByResultExeId(String exeId);
    /**
     * 
     * 描述 根据附件ID获取出生一件事附件数据
     * @author Yanisin Shi
     * @param fileId
     * @return
     */
    public Map<String, Object> getBirthFileInfo(String fileId);
    /**
     * 
     * 描述   更新出生一件事附件内容
     * @author Yanisin Shi
     * @param fileId
     * @return
     */
    public void updateBirthFileByFileId(String fileId,String yw_id,String BIRTH_FILE_ID,String BIRTH_FILE_URL);
}
