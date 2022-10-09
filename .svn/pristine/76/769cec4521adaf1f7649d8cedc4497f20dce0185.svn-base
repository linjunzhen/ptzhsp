/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.statistics;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;

import java.util.Map;
import java.util.Objects;

/**
 * 描述   企业信息实体类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public abstract  class CompanyInfoTemplate  {
    /**
     * 根据办件id获取企业信息
     * @param exeId
     */
    public Map<String,Object>  getInfoByExeId(String exeId){
       Map<String,Object> busRecord=setBusRecordByExeId(exeId);
       setCompanyName(busRecord);
       setLegalName(busRecord);
       setCompanyMobile(busRecord);
       setAddress(busRecord);
       setBusinessScope(busRecord);
       setCapital(busRecord);
       return busRecord;
    }

    /**
     * 设置办件业务信息
     * @param exeId
     */
    protected Map<String, Object>  setBusRecordByExeId(String exeId){
        ExeDataService exeDataService=(ExeDataService) AppUtil.getBean("exeDataService");
        Map<String, Object> busRecord=exeDataService.getBuscordAndExeMap(exeId);
        return  busRecord;
    }

    /**
     * 企业名称
     */
    public abstract void setCompanyName(Map<String, Object>  busRecord);
    /**
     * 负责人（有限公司时为-法定代表人/合伙时为-执行事务合伙人/个体时为-经营者）、
     */
    public abstract void setLegalName(Map<String, Object>  busRecord);

    /**
     *联系电话（有限公司时为-企业联系电话/合伙时为-企业联系电话/个体时为-经营者信息中移动电话）、
     * @param busRecord
     */
    public abstract void setCompanyMobile(Map<String, Object>  busRecord);
    /**
     *注册资本
     * @param busRecord
     */
    public abstract void setCapital(Map<String, Object>  busRecord);
    /**
     *经营范围
     * @param busRecord
     */
    public abstract void setBusinessScope(Map<String, Object>  busRecord);
    /**
     *住所
     * @param busRecord
     */
    public abstract void setAddress(Map<String, Object>  busRecord);

}
