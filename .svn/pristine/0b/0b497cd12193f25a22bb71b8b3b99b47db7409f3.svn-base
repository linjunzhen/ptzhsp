/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.statistics;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.exesuccess.ExeContent;
import net.evecom.platform.zzhy.util.RobotUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述  个体信息统计类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class IndividualInfo extends  CompanyInfoTemplate {

    @Override
    public void setCompanyName(Map<String, Object> busRecord) {
        String companyName=StringUtil.getString(busRecord.get("INDIVIDUAL_NAME"));
        busRecord.put("COMPANY_NAME",companyName);
    }

    @Override
    public void setLegalName(Map<String, Object> busRecord) {
        String dealerName=StringUtil.getString(busRecord.get("DEALER_NAME"));
        busRecord.put("LEGAL_NAME",dealerName);
    }

    @Override
    public void setCompanyMobile(Map<String, Object> busRecord) {
        String dealerMobile=StringUtil.getString(busRecord.get("DEALER_MOBILE"));
        busRecord.put("CONTACT_PHONE",dealerMobile);
    }

    @Override
    public void setCapital(Map<String, Object> busRecord) {
        String capitalAmount=StringUtil.getString(busRecord.get("CAPITAL_AMOUNT"));
        busRecord.put("INVESTMENT",capitalAmount);
    }

    @Override
    public void setBusinessScope(Map<String, Object> busRecord) {
        String businessScope=StringUtil.getString(busRecord.get("BUSINESS_SCOPE"));
        busRecord.put("BUSSINESS_SCOPE",businessScope);
    }

    @Override
    public void setAddress(Map<String, Object> busRecord) {
        String businessPlace=StringUtil.getString(busRecord.get("BUSINESS_PLACE"));
        busRecord.put("REGISTER_ADDR",businessPlace);
    }
}
