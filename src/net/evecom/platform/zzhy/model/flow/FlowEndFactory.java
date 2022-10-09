/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.flow;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.model.exesuccess.ExeContent;
import net.evecom.platform.zzhy.model.exesuccess.MpCompanyExeContent;
import net.evecom.platform.zzhy.model.exesuccess.MpIndividualExeContent;
import net.evecom.platform.zzhy.model.exesuccess.MpNzjointventureExeContent;

import java.util.Map;
import java.util.Objects;

/**
 * 描述   办件办结工厂类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class FlowEndFactory {
    /**
     * 根据业务表名获取办结类
     * @param exeId
     * @return
     */
    public static FlowEndProcess createFlowEndProcess(String exeId){
        FlowEndProcess flowEndProcess=null;
        //获取业务数据
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> busMap=exeDataService.getExeAndBuscordMap(exeId);
        String busTableName=String.valueOf(busMap.get("BUS_TABLENAME"));
        //内资有限
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal(),busTableName)){
            flowEndProcess=new CompanyFlowEndProcess();
        }
        //内资合伙
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal(),busTableName)){
            flowEndProcess=new NzjointventureFlowEndProcess();
        }
        //个体
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_INDIVIDUAL.getVal(),busTableName)){
            flowEndProcess=new IndividualFlowEndProcess();
        }
        return flowEndProcess;
    }
}
