/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.evaluate.service;

import java.util.Map;

import net.evecom.core.service.BaseService;

/**
 * @Description 好差评管理系统评价信息Service
 * @author Luffy Cai
 *
 */
@SuppressWarnings("rawtypes")
public interface EvaluateService extends BaseService {

    /**
     * 
     * @Description 好差评系统数据上报(平板)
     * @author Luffy Cai
     * @date 2020年7月31日 void
     */
    public void pushEvaluationData();
    
    /**
     * 
     * @Description 好差评系统数据上报(批量件)
     * @author Jason Lin
     * @date 2021年1月10日 void
     */
    public void pushEvaluationDataPLJ();

    /**
     * 
     * @Description 好差评系统数据上报(按键器)
     * @author Luffy Cai
     * @date 2020年7月31日 void
     */
    public void pushEvaluationDataByKey();
    
    
    /**
     * 
     * @Description 获取评价统计数据
     * @author Luffy Cai
     * @date 2020年9月25日
     * @param beginTime
     * @param endTime
     * @return Map<String,Object>
     */
    public Map<String, Object> getEvaluationStatistics(String beginTime,String endTime);
    
    
    /**
     * 
     * @Description 好差评评价信息上报(第三方)
     * @author Luffy Cai
     * @date 2020年9月25日
     * @param evaData
     * @return Map<String,Object>
     */
    public Map<String, Object> pushEvaluateDataByThird(Map<String, Object> evaData);
    
    
    /**
     * 
     * @Description 好差评系统数据上报(平板)
     * @author Luffy Cai
     * @date 2020年7月31日 void
     */
    public void pushBadEvaluationData();

    /**
     * 
     * @Description 好差评系统数据上报(按键器)
     * @author Luffy Cai
     * @date 2020年7月31日 void
     */
    public void pushBadEvaluationDataByKey();

    /**
     *
     * @Description 办件评价数据上报（二维码+短信）
     * @author Nero Wang
     * @date 2021年5月14日 void
     */
    public void pushEvaluationDataByExe();

    /**
     *
     * @Description 办件评价数据上报（二维码+短信）
     * @author Nero Wang
     * @date 2021年5月14日 void
     */
    public void pushBadEvaluationDataByExe();
    
    /**
     * 
     * @Description 审批系统办结办件短信评价定时器
     * @author Luffy Cai
     * @date 2021年5月31日 void
     */
    public void sendEvaluateMsg();
    
    /**
     * 
     * @Description 好差评系统数据补报
     * @author Luffy Cai
     * @date 2021年12月15日 void
     */
    public void getEvaluationSupplement(); 
    
    /**
     * 
     * @Description 好差评系统数据补报
     * @author Luffy Cai
     * @date 2021年12月15日 void
     */
    public void getEvaluationSupplementAugust(); 
    
    /**
     * 
     * @Description 好差评系统未评价记录默认好评
     * @author Jason Lin
     * @date 2021年12月15日 void
     */
    public void evaluateTheDefaultHighPraise(); 
}
