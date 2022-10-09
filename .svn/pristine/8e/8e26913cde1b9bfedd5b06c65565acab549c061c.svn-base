/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述 投资项目申报操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:08
 */
public interface ProjectApplyService extends BaseService {
    /**
     * 
     * 描述：查询列表信息
     * 
     * @author Rider Chen
     * @created 2019年6月11日 上午9:43:31
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql);

    /**
     * 
     * 描述：根据项目代码获取投资项目消息
     * 
     * @author Rider Chen
     * @created 2019年6月11日 上午9:43:36
     * @param projectCode
     * @return
     */
    public Map<String, Object> loadTzxmxxData(String projectCode);

    /**
     * 
     * 描述：获取分类下的事项列表
     * 
     * @author Rider Chen
     * @created 2019年6月11日 上午11:28:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findByPublishSqlFilter(SqlFilter sqlFilter);


    /**
     * 
     * 描述： 根据状态获取项目信息列表
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午3:54:24
     * @param status
     * @return
     */
    public List<Map<String, Object>> findProjectList(int status, String id);

    /**
     * 
     * 描述：根据参数获取项目单位信息列表
     * 
     * @author Rider Chen
     * @created 2019年6月19日 上午9:33:21
     * @param parentMap
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findXmdwxxbList(Map<String, Object> parentMap, int status, String id);

    /**
     * 
     * 描述：获取工程项目分类
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午6:56:51
     * @param status
     * @return
     */
    public List<Map<String, Object>> findFlowCategoryList(int status, String id);

    /**
     * 
     * 描述：获取工程项目流程节点
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午6:56:51
     * @param status
     * @return
     */
    public List<Map<String, Object>> findFlowCategoryList(Map<String, Object> parentMap, int status, String id);

    /**
     * 
     * 描述：获取工程项目流程节点事项信息
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午6:56:51
     * @param status
     * @return
     */
    public List<Map<String, Object>> findFlowItemList(Map<String, Object> parentMap, int status, String id);

    /**
     * 
     * 描述： 根据序列名称获取序列值
     * 
     * @author Rider Chen
     * @created 2019年6月17日 下午4:57:49
     * @param seqName
     * @return
     */
    public String getSeqValue(String seqName);

    /**
     * 
     * 描述 保存地方项目审批流程信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveDfxmsplcxxb(String id);

    /**
     * 
     * 描述 保存地方项目审批流程阶段信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveDfxmsplcjdxxb(String id);

    /**
     * 
     * 描述 保存地方项目审批流程阶段事项信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveDfxmsplcjdsxxxb(String id);

    /**
     * 
     * 描述：修改推送状态
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:54:57
     * @param tableName
     *            表名
     * @param status
     *            修改的状态
     * @param key
     *            主键字段名称
     * @param id
     *            主键字段值
     * @return
     */
    public void updatePushStatus(String tableName, int status, String key, String id);

    /**
     * 
     * 描述 保存项目基本信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveXmjbxxb(String id);
    /**
     *
     * 描述 推送赋码信息变更
     *
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param id
     * @return
     */
    public Map<String, Object> pushChangeXmjbxxb(String id);

    /**
     * 
     * 描述 保存项目单位信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveXmdwxxb(String id);

    /**
     * 
     * 描述 项目审批事项办理信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveXmspsxblxxb(String id);

    /**
     * 
     * 描述 项目审批事项办理详细信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveXmspsxblxxxxb(String id);

    /**
     * 
     * 描述 项目其他附件信息表
     * 
     * @author Rider Chen
     * @created 2019年6月18日 上午9:02:33
     * @param request
     * @return
     */
    public Map<String, Object> saveXmqtfjxxb(String id);
    
    

    /**
     * 
     * 描述：获取项目审批事项办理信息表
     * @author Rider Chen
     * @created 2019年6月19日 上午11:08:00
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findEexcutionList(int status, String id);
    /**
     * 
     * 描述：获取项目审批事项办理详细信息表
     * @author Rider Chen
     * @created 2019年6月19日 上午11:08:00
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findEexcutionTaskList(Map<String, Object> parentMap,int status, String id);

    /**
     * 
     * 描述：获取项目审批事项申报材料附件信息
     * @author Rider Chen
     * @created 2019年6月19日 下午3:22:14
     * @param parentMap
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findEexcutionFileList(Map<String, Object> parentMap,int status, String id);
    

   /**
    * 
    * 描述：判断项目是否办结
    * @author Rider Chen
    * @created 2019年6月19日 下午4:32:21
    * @param projectCode
    * @return
    */
    public boolean isNoEndXmjbxxb(String projectCode);
    /**
     * 
     * 描述：根据办结状态获取项目列表
     * @author Rider Chen
     * @created 2019年6月19日 下午4:19:10
     * @param state
     * @return
     */
    public List<Map<String, Object>> findNoEndXmjbxxb(String state);

    /**
     * 
     * 描述： 根据项目基本信息ID修改是否办结状态
     * @author Rider Chen
     * @created 2019年6月19日 下午4:11:54
     * @param xmjbxxb_id
     * @return
     */
    public void updateXmsfwqbj(String xmjbxxb_id);
    
    
    /**
     * 
     * 描述：工程建设项目保存附件信息到中间表
     * @author Rider Chen
     * @created 2019年6月24日 上午9:54:50
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveAfterFile(Map<String, Object> flowDatas);
    /**
     * 
     * 描述：工程建设项目保存信息到中间表
     * @author Rider Chen
     * @created 2019年6月24日 上午9:54:50
     * @param flowDatas
     * @return
     */
    public void saveAfterToXmspsxblxxxxb(int blzt,String exeId,String blr,String blyj);
    /**
     * 
     * 描述 工程建设项目保存项目审批事项办理详细信息
     * @author Rider Chen
     * @created 2019年7月5日 下午4:04:24
     * @param flowDatas
     */
    public void saveAfterToXmspsxblxxxxb(Map<String, Object> flowDatas);

    /**
     * 推送工程建设状态数据
     * @param flowDatas
     */
    public void pushGcjsStatusData(Map<String,Object> flowDatas);

    /**
     * 
     * 描述：设置前置库数据失效
     * @author Rider Chen
     * @created 2019年9月23日 上午9:11:01
     * @return
     */
    public Map<String, Object> setProjectInvalid();

    /**
     * 描述:根据事项编码获取工程建设项目当前所处阶段
     *
     * @author Madison You
     * @created 2019/10/14 11:25:00
     * @param
     * @return
     */
    Map<String, Object> findProjectJD(String itemCode);
    /**
     * 根据配置分类表ID，查找工程项目阶段列表
     * @param categoryId
     * @return
     */
    List<Map<String, Object>> findStageList(String categoryId);
    /**
     * 
     * 描述：获取阶段进展情况
     * 
     * @author Scolder Lin
     * @created 2019年11月28日 上午14:28:42
     * @param projectCode
     * @return
     */
    public String findStageProgress(String projectCode);
    /**
     * 获取工程事项详情列表信息
     * 
     * @author Scolder Lin
     * @created 2019年11月28日 上午15:28:42
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findProjectDetailList(SqlFilter sqlFilter);
    
    /**
     * 
     * 描述：获取项目审批事项办理特别程序信息
     * @author Rider Chen
     * @created 2019年12月23日 下午3:54:20
     * @param parentMap
     * @param status
     * @return
     */
    public List<Map<String, Object>> findXmspsxbltbcxxxb(Map<String, Object> parentMap, int status);
    

    /**
     * 
     * 描述：项目审批事项办理特别程序信息表
     * @author Rider Chen
     * @created 2019年12月23日 下午3:54:06
     * @param id
     * @return
     */
    public Map<String, Object> saveXmspsxbltbcxxxb(String id);

    /**
     * 获取工程建设项目撤回列表数据(未审核)
     * @return
     */
    public List<Map<String, Object>> findProjectRecallList();
    /**
     * 去掉流程表中projectCode值
     * @param exeId
     */
    public void updateExeProCode(String exeId);
    /**
     * 更新工程项目撤回表信息
     * @param recallId
     * @param type
     */
    public void updateRecallInfo(String recallId, String type);
    /**
     * 
     * 描述：工程建设项目撤回事项列表
     * 
     * @author Scolder Lin
     * @created 2020年1月3日 上午9:43:31
     * @param sqlFilter
     * @param whereSql
     * @return
     */
    public List<Map<String, Object>> findProjectRecallData(SqlFilter sqlFilter, String whereSql);

    /**
     * 
     * 描述：获取工程建设施工许可信息
     * @author Rider Chen
     * @created 2019年12月23日 下午3:54:20
     * @param parentMap
     * @param status
     * @return
     */
    public List<Map<String, Object>> findTBBuilderLicenceManage(Map<String, Object> parentMap, int status);
    

    /**
     * 
     * 描述：保存工程建设施工许可信息到中间表
     * @author Rider Chen
     * @created 2019年12月23日 下午3:54:06
     * @param id
     * @return
     */
    public Map<String, Object> saveTBBuilderLicenceManage(String id);
    /**
     * 
     * 描述 保存工程建设施工许可变更信息到中间表
     * @author Rider Chen
     * @created 2020年5月21日 上午10:08:05
     * @param request
     * @return
     */
     public Map<String, Object> saveTBBuilderLicenceChangeInfo(String id);
     

     /**
      * 
      * 描述 保存工程建设施工许可废止信息到中间表
      * @author Rider Chen
      * @created 2020年5月21日 上午10:08:05
      * @param request
      * @return
      */
      public Map<String, Object> saveTBBuilderLicenceCancelInfo(String id);
    
    
    /**
     * 
     * 描述：获取办件结果信息（3.5.5　项目审批事项批复文件信息表）
     * @author Rider Chen
     * @created 2020年4月16日 上午9:17:58
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findFlowResultList(int status, String exeId);
    
    /**
     * 
     * 描述： 推送项目审批事项批复文件信息表
     * @author Rider Chen
     * @created 2020年4月16日 上午9:44:14
     * @param id
     * @return
     */
    public Map<String, Object> saveXmspsxpfwjxxb(String id);
    
    /**
     * 
     * 描述：获取办件信息（3.5.3　项目审批事项征求意见信息表）
     * @author Rider Chen
     * @created 2020年4月17日 11:27:48
     * @param status
     * @param id
     * @return
     */
    public List<Map<String, Object>> findFlowTaskList(int status, String exeId);
    
    /**
     * 
     * 描述： 推送项目审批事项征求意见信息表
     * @author Rider Chen
     * @created 2020年4月17日 11:27:51
     * @param id
     * @return
     */
    public Map<String, Object> saveXmspsxzqyjxxb(String id);
    
    
    /**
     * 
     * 描述：向住建上报数据接口
     * @author Rider Chen
     * @created 2020年5月7日 下午4:39:10
     * @return
     */
    public Map<String, Object> startWorkflow(Map<String, Object> info);
    
    /**
     * 
     * 描述：向住建上报数据接口(数据整理)
     * @author Rider Chen
     * @created 2020年5月7日 下午4:39:10
     * @return
     */
    public void pushFlowinfo();

    /**
     * 
     * @Description 根据事项编码获取列表
     * @author Luffy Cai
     * @date 2020年11月6日
     * @param projectCode
     * @return List<Map<String,Object>>
     */
    public List<Map<String, Object>> findListByProjectCode(String projectCode);
    
}
