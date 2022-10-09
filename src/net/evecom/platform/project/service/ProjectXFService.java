/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service;

import java.io.File;
import java.util.Map;
import java.util.Set;

import net.evecom.core.service.BaseService;
import net.evecom.platform.bsfw.model.PtspFile;
/**
 * 
 * 描述：工程建设项目消防事项数据共享定时器
 * @author Scolder Lin
 * @created 2020年1月8日 下午5:30:39
 */
@SuppressWarnings("rawtypes")
public interface ProjectXFService extends BaseService {
    /**
     * 
     * 描述 保存消防项目基本信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFBaseInfo();
    /**
     * 
     * 描述 保存消防项目单体信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFUnitInfo();
    /**
     * 
     * 描述 保存消防项目责任主体信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFCorpInfo();
    /**
     * 
     * 描述 保存消防项目储罐信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFStorageInfo();
    /**
     * 
     * 描述 保存消防项目堆场信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFYardInfo();
    /**
     * 
     * 描述 保存消防项目建筑保温信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFInsuInfo();
    /**
     * 
     * 描述 保存消防项目装修工程信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveXFDecorateInfo();
    /**
     * 
     * 描述 保存竣工验收备案信息表
     * 
     * @param request
     * @return
     */
    public Map<String, Object> saveFinishManageInfo();

    /**
     * 描述:保存消防验收（备案）申请信息
     *
     * @author Madison You
     * @created 2020/1/16 10:38:00
     * @param
     * @return
     */
    public Map<String,Object> saveXFYsbaInfo();

    /**
     * 描述:保存消防验收情况信息
     *
     * @author Madison You
     * @created 2020/1/16 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> saveXFYsqkInfo();
    
    /**
     * 描述:受理是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String,Object> flowVars);
    /**
     * 描述:受理环节后置事件（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getAcceptResult(Map<String,Object> flowVars);    
    /**
     * 描述:是否随机抽中（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsRandomCheck(Map<String,Object> flowVars);
    /**
     * 描述:审批环节后置事件（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getApprovalResult(Map<String,Object> flowVars);    
    
    /**
     * 描述:结论登记是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsConcludePass(Map<String,Object> flowVars);
    
    /**
     * 描述:结论登记是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String, Object> getIsBack(Map<String,Object> flowVars);    
    /**
     * 描述:结论登记是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String, Object> getIsReturn(Map<String,Object> flowVars);   
    /**
     * 描述:结论复查是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsRecheckPass(Map<String,Object> flowVars);  
    
    /**
     * 描述:结论审批是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsConclusionPass(Map<String,Object> flowVars);  
    
    /**
     * 描述:结论审批后置事件（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getConclusionResult(Map<String,Object> flowVars);    
    /**
     * 描述:监督报告初审是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsFirstTrialPass(Map<String,Object> flowVars);     
    
    /**
     * 描述:监督报告审批是否通过（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Set<String> getIsReportPass(Map<String,Object> flowVars);  
    /**
     * 描述:监督报告审批后置事件（工程-建设工程消防验收及备案抽查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getReportResult(Map<String,Object> flowVars);  
    
    /**
     * 描述受理后置事件（工程-建设工程消防设计审查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getAcceptPass(Map<String,Object> flowVars);  
    
    /**
     * 描述审查后置事件（工程-建设工程消防设计审查流程）
     *
     * @author Luffy Cai
     * @created 2020/5/26 11:17:00
     * @param
     * @return
     */
    public Map<String,Object> getOfficeAction(Map<String,Object> flowVars);    
    
    /**
     * 描述 上传文件服务器并获取Ptspfile对象
     * @author Keravon Feng
     * @created 2021年11月18日 下午5:04:53
     * @param url
     * @param fileName
     * @param file
     * @param busTableName
     * @return
     */
    public PtspFile uploadAndGetFileInfo(String url, String fileName, File file, String busTableName);
    
    /**
     * 根据主键id和tableName获取业务表数据
     * 
     * @param busRecordId
     * @param tableName
     * @return
     */
    public Map<String, Object> getBuscordByIdAndTableName(String busRecordId, String busTableName);
}
