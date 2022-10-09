/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.exesuccess;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.model.sign.CompanySignContent;
import net.evecom.platform.zzhy.model.sign.NzJointventureSignContent;
import net.evecom.platform.zzhy.model.sign.SignContent;

import java.util.Map;
import java.util.Objects;

/**
 * 描述   办件办结成功短信
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class ExeMsgFactory {
    /**
     * 根据业务表名获取短信发送类
     * @param exeId
     * @return
     */
    public static ExeContent createExeMsg(String exeId){
        ExeContent exeContent=null;
        //获取业务数据
        ExeDataService exeDataService= (ExeDataService) AppUtil.getBean("exeDataService");
        Map<String,Object> busMap=exeDataService.getExeAndBuscordMap(exeId);
        String busTableName=String.valueOf(busMap.get("BUS_TABLENAME"));
        String sssblx= StringUtil.getString(busMap.get("SSSBLX"));
        //内资秒批
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal(),busTableName)&&Objects.equals("1",sssblx)){
            exeContent=new MpCompanyExeContent();
        }
        //内资合伙秒批
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal(),busTableName)&&Objects.equals("1",sssblx)){
            exeContent=new MpNzjointventureExeContent();
        }
        //个体秒批
        if(Objects.equals(TableNameEnum.T_COMMERCIAL_INDIVIDUAL.getVal(),busTableName)&&Objects.equals("1",sssblx)){
            exeContent=new MpIndividualExeContent();
        }
        return exeContent;
    }
}
