/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.sb.dao.SbCommonDao;
import net.evecom.platform.sb.service.SbCommonService;
import net.evecom.platform.sb.service.SbQyzgcxService;
import net.evecom.platform.sb.util.SbCommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述  城乡居民社会养老保险业务通用操作services
 * @author Allin Lin
 * @created 2020年2月18日 下午2:39:54
 */
@Service("/sbQyzgcxService")
public class SbQyzgcxServiceImpl extends BaseServiceImpl implements SbQyzgcxService {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SbQyzgcxServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private SbCommonDao dao;
    /**
     * exedataService
     */
    @Resource
    private ExeDataService exedataService;
    
    /**
     * 描述 覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年2月18日 下午2:43:47
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 获取regTable用于打印缴费明细动态表格
     * @param exeId
     * @return
     */
    public Map<String,Object> getRegTableForPaiedDetail(String printData){
        List<List<String>> regTableList= Lists.newArrayList();
        //Map<String,Object> busCord=exedataService.getBuscordMap(exeId);
        //String paydetailjson= StringUtil.getValue(busCord,"PAYDETAILJSON");
        List<Map<String,Object>> payList=(List) JSONObject.parse(printData);
        for(int i=0;i<payList.size();i++){
            Map<String,Object> pay=payList.get(i);
            List<String> row= Lists.newArrayList();
            row.add(StringUtil.getString(i+1));
            row.add(StringUtil.getValue(pay,"aab001"));
            row.add(StringUtil.getValue(pay,"aab004"));
            row.add(StringUtil.getValue(pay,"aae002"));
            String aae002=StringUtil.getValue(pay,"aae002");
            String aae033=StringUtil.getValue(pay,"aae033");
            String date=String.format("%s-%s",aae002,aae033);
            row.add(date);
            row.add(StringUtil.getValue(pay,"aae202"));
            row.add(StringUtil.getValue(pay,"aae180"));
            row.add(StringUtil.getValue(pay,"aaa103"));
            regTableList.add(row);
        }
        Map<String,Object> regTable= Maps.newHashMap();
        regTable.put("PO_T002",regTableList);
        return regTable;
    }

}
