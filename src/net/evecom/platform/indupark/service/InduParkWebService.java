/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.indupark.service;


/**
 * 描述
 * @author Danto Huang
 * @created 2018年3月29日 上午9:01:15
 */
public interface InduParkWebService{

    /**
     * 
     * 描述    流程表单数据提交
     * @author Danto Huang
     * @created 2018年3月29日 上午9:05:27
     * @param flowInfoJson
     * @return
     */
    public String submitApply(String flowInfoJson);

    /**
     * 
     * 描述 撤回申请
     * @author Kester Chen
     * @created 2018-6-10 下午2:58:13
     * @param exeId
     * @return
     */
    public String getBackMyApply(String exeId);
    /**
     * 
     * 描述    办件状态查询
     * @author Danto Huang
     * @created 2018年4月2日 下午2:45:35
     * @param exeId
     * @return
     */
    public String queryApplyStatus(String exeId);
    /**
     *
     * 描述    社会信用代码同步
     * @created 2019年6月18日 下午2:45:35
     * @param exeId
     * @return
     */
    public String querySocilCode(String exeId);
    /**
     * 
     * 描述 附件下载
     * @author Kester Chen
     * @created 2018-4-18 下午5:57:06
     * @param itemCode
     * @param exeId
     * @return
     */
    public String downLoadFile(String itemCode, String exeId);
    /**
     * 
     * 描述 获取审核意见
     * @author Kester Chen
     * @created 2018-6-10 下午3:39:41
     * @param exeId
     * @return
     */
    public String getAuditOpinion(String exeId);
    /**
     *
     * 描述
     * @author Danto Huang
     * @created 2018年3月29日 上午9:05:27
     * @param flowInfoJson
     * @return
     */
    public String dataChange(String flowInfoJson) ;
}
