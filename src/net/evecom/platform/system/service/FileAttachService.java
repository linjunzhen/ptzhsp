/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service;

import java.util.List;
import java.util.Map;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 系统附件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface FileAttachService extends BaseService {
    /**
     * 
     * 描述 删除文件
     * @author Flex Hu
     * @created 2014年9月27日 下午4:32:27
     * @param fileIds
     */
    public void removeFile(String[] fileIds);
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
     * 描述 获取multi附件列表
     * @param ids
     * @return
     */
    public List<Map<String,Object>> findListForResult(String ids);
    
    /**
     * 
     * 描述 根据附件ID获取对象数据
     * @author Flex Hu
     * @created 2015年1月16日 下午3:14:12
     * @param file_id
     * @return
     */
    public Map<String,Object> getFileAttachObject(String file_id);
    /**
     * 
     * 描述 根据exeid获取出生一件事附件数据
     * @author Yanisin Shi
     * @param exeid
     * @return
     */
    public Map<String,Object> getBirthFileInfo(String exeid);
    /**
     * 
     * 描述 获取附件根路径
     * @author Flex Hu
     * @created 2015年1月16日 下午3:19:27
     * @return
     */
    public String getFileAttachRootPath();
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
    public boolean isExists(String attachKey,String fileIds);
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
     * 描述 根据业务表名称、业务表记录ID、材料KEY获取上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @param attachKey
     * @return
     */
    public List<Map<String,Object>> findByList(String busTableName,String busRecordId,String attachKey);

    /**
     *
     * 描述 根据业务表名称、业务表记录ID、材料KEY获取上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findByList(String busTableName,String busRecordId);
    /**
     * 
     * 描述 根据业务表名称、业务表记录ID上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findYsqByList(String busTableName,String busRecordId);
    /**
     * 
     * 描述 保存申报材料的记录
     * @author Flex Hu
     * @created 2015年11月4日 下午3:49:33
     * @param submitMaterFileJson
     */
    public void saveItemMaterFiles(String submitMaterFileJson,String busTableName,
            String busRecordId,Map<String,Object> flowVars);
    /**
     * 
     * 描述 根据流程实例ID
     * @author Flex Hu
     * @created 2015年12月7日 下午3:29:41
     * @param fileIds
     * @param exeId
     */
    public void updateExeId(String fileIds,String exeId);
    /**
     * 
     * 描述 根据流程实例ID获取列表
     * @author Flex Hu
     * @created 2015年12月8日 下午3:02:15
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findByExeId(String exeId,String sfhzd);
    /**
     * 
     * 描述 根据exeid获取快递信息
     * @author Kester Chen
     * @created 2018年8月16日 上午11:03:39
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findMailInfoByExeId(String exeId);
    /**
     * 
     * 描述 更新网站用户上传材料的状态
     * @author Flex Hu
     * @created 2015年12月16日 上午10:06:54
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateUserMaterStatus(Map<String,Object> flowVars);
    /**
     * 
     * 描述 退回补件更新材料状态
     * @author Faker Li
     * @created 2015年12月16日 上午11:28:10
     * @param fieShzt
     * @param flowVars
     * @param key
     */
    public void updateFileShzt(String fieShzt, Map<String, Object> flowVars, String key);
    /**
     * 
     * 描述 保存审批材料数据
     * @author Flex Hu
     * @created 2016年2月1日 下午12:23:20
     * @param EFLOW_APPMATERFILEJSON
     */
    public void saveAuditerFiles(String EFLOW_APPMATERFILEJSON,Map<String,Object> flowInfo);
    
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
     * 
     * 根据材料标签和上传用户ID获取列表
     * @author Danto Huang
     * @created 2017-5-4 下午2:20:05
     * @param tagId
     * @param userId
     * @return
     */
    public List<Map<String,Object>> findListByTagAndUserId(String tagId,String userId);
    public List<Map<String, Object>> findDatagrid(Map<String, Object> variables);
    /**
     * 上传文件
     * @param variables
     */
    public List<Map<String,Object>> useHisMatAgainByIds(Map<String,Object> variables);
    public List<Map<String, Object>> wdcllist(Map<String, Object> variables);
    
    

    /**
     * 
     * 描述：根据项目代码与材料名称获取列表
     * @author Rider Chen
     * @created 2019年6月14日 下午3:15:12
     * @param projectcode
     * @param fileName
     * @return
     */
    public List<Map<String,Object>> findListByCodeAndName(String projectCode,String fileName);
    
    public List<Map<String, Object>> findResByExeId(String exeId);
    
    /**
     * 
     * 描述：根据项目代码与材料编码获取列表
     * @author Rider Chen
     * @created 2019年6月14日 下午3:15:12
     * @param projectcode
     * @param fileName
     * @return
     */
    public List<Map<String,Object>> findListByCodeAndKey(String projectCode,String attachKey);
    public List<Map<String, Object>> findHisDatagrid(Map<String, Object> variables);

    /**
     * 描述:查询此办件的所有上传过的材料
     *
     * @author Madison You
     * @created 2021/4/7 14:32:00
     * @param
     * @return
     */
    List<Map<String, Object>> queryAllUploadFiles(HttpServletRequest request);
    /**
     * 描述:获取办结结果附件
     *
     * @author Yanisin Shi
     * @created 2021/07/29 10:32:00
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> findListByResultExeId(String exeId);
    /**
     * 
     * 描述    更新出生一件事附件内容
     * @author Yanisin Shi
     * @param fileIds
     * @param exeId
     */
    public void updateBirthFileByFileId(String fileId,String yw_id,String BIRTH_FILE_ID,String BIRTH_FILE_URL);
}
