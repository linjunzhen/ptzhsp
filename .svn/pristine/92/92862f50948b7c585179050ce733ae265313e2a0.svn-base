/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.statistics;


import net.evecom.core.util.StringUtil;
import net.evecom.platform.zzhy.model.sign.NzJointventureSignContent;
import net.evecom.platform.zzhy.util.MsgSendUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 描述  合伙信息统计类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class NzJointventureInfo extends  CompanyInfoTemplate {

    @Override
    public void setCompanyName(Map<String, Object> busRecord) {

    }

    @Override
    public void setLegalName(Map<String, Object> busRecord) {
        //执行事务合伙人
        Map<String,Object> partShip= NzJointventureSignContent.getPartShip(busRecord);
        String legalName="";
        if(Objects.nonNull(partShip)){
            String shareholderType=StringUtil.getString(partShip.get("SHAREHOLDER_TYPE"));
            if(Objects.equals(shareholderType,"zrr")){
                legalName=StringUtil.getString(partShip.get("SHAREHOLDER_NAME"));
            }else{
                legalName=StringUtil.getString(partShip.get("LEGAL_PERSON"));
            }
        }
        busRecord.put("LEGAL_NAME",legalName);
    }

    @Override
    public void setCompanyMobile(Map<String, Object> busRecord) {

    }

    @Override
    public void setCapital(Map<String, Object> busRecord) {

    }

    @Override
    public void setBusinessScope(Map<String, Object> busRecord) {

    }

    @Override
    public void setAddress(Map<String, Object> busRecord) {

    }
}
