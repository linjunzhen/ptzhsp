/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.statistics;

import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.model.sign.CompanySignContent;
import net.evecom.platform.zzhy.model.sign.NzJointventureSignContent;
import net.evecom.platform.zzhy.model.sign.SignContent;

import java.util.Objects;

/**
 * 描述   根据业务表名获取短信发送类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class CompanyInfoFactory {
    /**
     * 根据业务表名获取公司信息
     * @param tableName
     * @return
     */
    public static CompanyInfoTemplate createCompanyInfoTemplate(String tableName){
        CompanyInfoTemplate companyInfoTemplate=null;
        if(Objects.equals(tableName, TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal())
                ||Objects.equals(tableName,"T_COMMERCIAL_COMPANY")){
            companyInfoTemplate=new YxCompanyInfo();
        }else if(Objects.equals(tableName, TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal())){
            companyInfoTemplate=new NzJointventureInfo();
        }else if(Objects.equals(tableName,TableNameEnum.T_COMMERCIAL_INDIVIDUAL.getVal())){
            companyInfoTemplate=new IndividualInfo();
        }
        return companyInfoTemplate;
    }
}
