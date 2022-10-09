/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcZjfwsyzydjDao;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcZjfwsyzydjService;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 描述     宅基地使用权及房屋所有权转移登记service
 * @author Allin Lin
 * @created 2020年5月20日 下午3:17:52
 */
@Service("bdcZjfwsyzydjService")
public class BdcZjfwsyzydjServiceImpl extends BaseServiceImpl implements BdcZjfwsyzydjService{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcZjfwsyzydjServiceImpl.class);
    /**
     * 引入service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入service
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;
    
    /**
     * 引入dao
     */
    @Resource
    private BdcZjfwsyzydjDao dao;
    
    /**
     * 引入dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }
    
    /**
     * 描述  宅基地使用权及房屋所有权转移登记审批表业务数据
     * @author Allin Lin
     * @created 2020年5月12日 上午10:54:03
     * @param args
     * @see net.evecom.platform.bdc.service.BdcZjfwsyzydjService#setZjfwsyzydjData(java.util.Map)
     */
    @Override
    public void setZjfwsyzydjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*国有建设用地使用权转移登记通用业务数据*/
        this.setZjfwsybgdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);
    }
    
    /**
     * 描述    宅基地使用权及房屋所有权转移登记审批表业务数据
     * @author Allin Lin
     * @created 2020年5月12日 上午10:56:20
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setZjfwsybgdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
        returnMap.put("qllx", "宅基地使用权及房屋所有权");
        returnMap.put("djlx", "转移登记");
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        /* 权利人信息JSON */
        String piJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrxm = new StringBuffer();//权利人姓名
            StringBuffer sfzjzl = new StringBuffer();//身份证件种类
            StringBuffer zjhm = new StringBuffer();//证件号码
            StringBuffer txdz = new StringBuffer();//通讯地址
            StringBuffer yb = new StringBuffer();//邮编
            StringBuffer fddbr = new StringBuffer();//法定代表人             
            StringBuffer dlrxm = new StringBuffer();//代理人         

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /* 证件类型代码转换 */
                String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                sfzjzl.append(documentType).append(",");
                zjhm.append(StringUtil.getValue(piMap, "POWERPEOPLEIDNUM")).append(",");
                txdz.append(StringUtil.getValue(piMap, "POWERPEOPLEADDR")).append(",");
                yb.append(StringUtil.getValue(piMap, "POWERPEOPLEPOSTCODE")).append(",");
                fddbr.append(StringUtil.getValue(piMap, "POWLEGALNAME")).append(",");
                dlrxm.append(StringUtil.getValue(piMap, "POWAGENTNAME")).append(",");
            }

            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
        }

        /* 义务人信息JSON */
        String qsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {

            StringBuffer ywrxm = new StringBuffer();//义务人姓名
            StringBuffer sfzjzl1 = new StringBuffer();//身份证件种类
            StringBuffer zjhm1 = new StringBuffer();//证件号码
            StringBuffer fddbr1 = new StringBuffer();//法人
            StringBuffer lxdh3 = new StringBuffer();//法人联系电话
            StringBuffer dlrxm1 = new StringBuffer();//代理人
            StringBuffer lxdh4 = new StringBuffer();//代理人电话
            StringBuffer dljgmc1 = new StringBuffer();//代理机构名称

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBRXM")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBRDH")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLRXM")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLRDH")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLJGMC")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));            
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
            
        }

        /* 不动产情况 */
        returnMap.put("zl", StringUtil.getValue(busInfo, "ZD_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz",
                dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(busInfo, "ZD_MJ"));
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM"));
        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DY_DBSE"));

        bdcSpbConfig.getTaskInfo(returnMap, "登簿");
    }
}
