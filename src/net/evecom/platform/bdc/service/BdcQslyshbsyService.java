/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * 描述 不动产权属来源审核业务service
 * @author Allin Lin
 * @created 2020年5月12日 上午10:41:56
 */
public interface BdcQslyshbsyService extends BaseService{
    /**
     * 不允许签章提示语
     */
    public static String IS_NOT_PERMIT_SIGN_MSG ="正在签署，请稍后再试";
    /**
     * 签章Synchronize关键字前缀
     */
    public static String PRE_SIGN_STATUS ="signStatus";
    
    /**
     * 签章版本Synchronize关键字前缀
     */
    public static String PRE_SIGN_VERSION ="signVersion";
    
    /**
     * 审核表模板名
     */
    public static String SHB_MATERNAME ="SHB.docx";
    
    /**
     * 流程定义KEY(不动产权属来源)
     */
    public static String DEF_KEY ="bdcqslyshbsy";
    /**
     * 流程定义KEY(民宿)
     */
    public static String DEF_MS_KEY ="msbasqlc";
    /**
     * 流程定义KEY(民宿 新)
     */
    public static String DEF_NMS_KEY ="msbasqlcx";
    
    
    /**
     * 描述    判断当前环节是否已签署完成
     * @author Allin Lin
     * @created 2020年10月30日 上午9:45:19
     * @param exeId
     * @param curNodeName
     * @return
     */
    public AjaxJson isFinishSign(String exeId,String curNodeName);
    
    /**
     * 否允许进行签章,并把状态置为其他人不可签章状态
     * @param exeId
     * @return
     */
    public AjaxJson isPermitSign(String exeId);
    
    /**
     * 描述    当前签署版本号是否一致
     * @author Allin Lin
     * @created 2020年10月27日 下午7:24:42
     * @param exeId
     * @param signVersion
     * @return
     */
    public AjaxJson isSameSignVersion(String exeId,String signVersion);

    /**
     * 根据办件id进行签章状态修改
     * @param exeId
     */
    public AjaxJson changeSignStatus(String exeId);
    
    /**
     * 描述    保存或更新签署文件（指定申报号）
     * @author Allin Lin
     * @created 2020年10月29日 下午5:39:24
     * @param exeId
     * @param fileId
     * @return
     */
    public AjaxJson saveOrUpdateSignFile(String exeId,String fileId,String fileType);
    
    
    /**
     * 描述  开始- 后置事件（生成签章模板文件）
     * @author Allin Lin
     * @created 2020年8月15日 上午11:09:17
     * @param flowVars
     * @return
     */
    public Map<String,Object> genSignFile(Map<String,Object> flowVars);
    
    /**
     * 描述    获取并审汇总环节需要自动跳转的任务
     * @author Allin Lin
     * @created 2020年10月27日 上午9:45:44
     * @return
     */
    public List<Map<String, Object>> findNeedAutoJump();
    
    /**
     * 描述    并审汇总环节自动跳转
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToAuto(Map<String, Object> data)throws Exception;
    
    /**
     * 
     * 描述   当前签章文件版本号是否一致（前置事件） 
     * @author Allin Lin
     * @created 2020年10月27日 下午9:34:19
     * @param flowVars
     * @return
     */
     public Map<String, Object> validateSignVersion(Map<String, Object> flowVars);

}
