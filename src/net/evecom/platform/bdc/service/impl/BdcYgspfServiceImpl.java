/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FtpUtils;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.bdc.dao.BdcYgspfDao;
import net.evecom.platform.bdc.service.BdcRegisterInterfaceService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.service.BdcYgspfService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 描述:不动产全流程预购商品房
 *
 * @author Madison You
 * @created 2020/04/27 11:23
 */
@Service("bdcYgspfService")
public class BdcYgspfServiceImpl extends BaseServiceImpl implements BdcYgspfService {

    /**
     * 描述:log4J声明
     *
     * @author Madison You
     * @created 2020/4/27 11:37:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/27 11:28:00
     * @param
     * @return
     */
    @Resource
    private BdcYgspfDao bdcYgspfDao;

    /**
     * 描述:审批表打印配置类
     *
     * @author Madison You
     * @created 2020/4/27 11:29:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSqbConfig;
    
    /**
     * 所引入的serivice
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * exeDataService
     */
    @Resource
    private BdcRegisterInterfaceService bdcRegisterInterfaceService;
    /*
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/27 11:28:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcYgspfDao;
    }


    /**
     * 描述:置预购商品房抵押权预告登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 11:31:00
     * @param
     * @return
     */
    @Override
    public void setYgspfdyqygdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房抵押权预告登记业务数据*/
        this.setYgspfdyqygdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:预购商品房预告登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:20:00
     * @param
     * @return
     */
    @Override
    public void setYgspfygdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房预告登记业务数据*/
        this.setYgspfygdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:预购商品房抵押权预告注销登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:21:00
     * @param
     * @return
     */
    @Override
    public void setYgspfdyqygzxdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房抵押权预告注销登记业务数据*/
        this.setYgspfdyqygzxdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:预购商品房预告注销登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:29:00
     * @param
     * @return
     */
    @Override
    public void setYgspfygzxdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房预告注销登记业务数据*/
        this.setYgspfygzxdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:预购商品房抵押权预告变更登记数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:45:00
     * @param
     * @return
     */
    @Override
    public void setYgspfdyqygdjbgData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房抵押权预告变更登记业务数据*/
        this.setYgspfdyqygdjbgBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:预购商品房预告登记变更数据回填
     *
     * @author Madison You
     * @created 2020/4/27 14:45:00
     * @param
     * @return
     */
    @Override
    public void setYgspfygdjbgData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSqbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSqbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房预告登记变更业务数据*/
        this.setYgspfygdjbgBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSqbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:设置预购商品房抵押权预告登记业务数据
     *
     * @author Madison You
     * @created 2020/4/27 11:31:00
     * @param
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setYgspfdyqygdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                        Map<String, Object> execution) {
        returnMap.put("qllx", "预告登记");
        returnMap.put("djlx", "预购商品房抵押权预告登记");
        
        String sqfs = execution.get("SQFS") == null ? "" : execution.get("SQFS").toString();
        String sjr = "平潭综合实验区行政服务中心";
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            sjr = StringUtil.getValue(execution, "CREATOR_NAME");
        }
        returnMap.put("sjr", sjr);
        
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "QLR_MC"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "QLR_ZJLX"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "QLR_ZJNO"));
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "DLR_MC"));
        
        setYgspfdyqygdjYwrxx(returnMap, busInfo);
        
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DLR2_MC"));
              
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        returnMap.put("zl", StringUtil.getValue(busInfo, "LOCATED"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(busInfo, "DJXX_JZAREA"));
        returnMap.put("yt", StringUtil.getValue(busInfo, "YTSM"));
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DBSE"));
        String qssj = StringUtil.getValue(busInfo, "ZW_BEGIN");
        String jssj = StringUtil.getValue(busInfo, "ZW_END");
        if (!qssj.equals("") && !jssj.equals("")) {
            String zwBegin = bdcSqbConfig.bdcDateFormat(qssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSqbConfig.bdcDateFormat(jssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("djyy", StringUtil.getValue(busInfo, "DJ_REASON"));
        
        returnMap.put("hd", StringUtil.getValue(busInfo, "HZ_OPINION_CONTENT"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "HZ_OPINION_NAME"));
        returnMap.put("hdsj", StringUtil.getValue(busInfo, "HZ_OPINION_TIME"));
        
        returnMap.put("lxdh2", StringUtil.getValue(busInfo, "DLR_SJHM"));//权利人代理人手机号码
        returnMap.put("lxdh4", StringUtil.getValue(busInfo, "DLR2_SJHM"));//义务人代理人手机号码
        //bdcSqbConfig.getTaskInfo(returnMap,"登簿");
    }


    private void setYgspfdyqygdjYwrxx(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        
        String ywrJson = StringUtil.getValue(busInfo, "YWR_JSON");
        if(StringUtils.isNotEmpty(ywrJson)){
            List<Map> list = JSON.parseArray(ywrJson, Map.class);
            StringBuffer ywrxm =  new StringBuffer("");
            StringBuffer sfzjzl =  new StringBuffer("");
            StringBuffer zjhm =  new StringBuffer("");
            for (Map map : list) {
                if(StringUtils.isNotEmpty(ywrxm)){
                    ywrxm.append(",");
                }
                ywrxm.append(StringUtil.getValue(map, "YWR_MC"));                
                if(StringUtils.isNotEmpty(sfzjzl)){
                    sfzjzl.append(",");
                }
                sfzjzl.append(StringUtil.getValue(map, "YWR_ZJLX"));                
                if(StringUtils.isNotEmpty(zjhm)){
                    zjhm.append(",");
                }
                zjhm.append(StringUtil.getValue(map, "YWR_ZJNO"));                
            }
            returnMap.put("ywrxm", ywrxm.toString());
            returnMap.put("sfzjzl1",  sfzjzl.toString());
            returnMap.put("zjhm1",  zjhm.toString());
        }
    }

    /**
     * 描述:设置预购商品房预告登记业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:17:00
     * @param
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setYgspfygdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                        Map<String, Object> execution) {
        returnMap.put("qllx", "预告登记");
        returnMap.put("djlx", "预购商品房预告登记");
        String sqfs = execution.get("SQFS") == null ? "" : execution.get("SQFS").toString();
        String sjr = "平潭综合实验区行政服务中心";
        if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("2")) {
            sjr = StringUtil.getValue(execution, "CREATOR_NAME");
        }
        returnMap.put("sjr", sjr);
        
        setYgspfygdjQlrxx(returnMap, busInfo);
        
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "LZRXM"));
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "ZRFXM"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "ZRFZJLB"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "ZRFZJHM"));
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DLRXM"));
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        /*Map<String, Object> htbaMap = null;
        String htbaxxJson = StringUtil.getValue(busInfo, "HTBAXX_JSON");
        if (!htbaxxJson.equals("")) {
            List<Map> list = JSON.parseArray(htbaxxJson, Map.class);
            if (list != null && list.size() > 0) {
                htbaMap = list.get(0);
            }
        }*/
        returnMap.put("zl", StringUtil.getValue(busInfo, "ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap, "FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(busInfo, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(busInfo, "YTSM"));
        returnMap.put("djyy", StringUtil.getValue(busInfo, "DJYY"));
        

        returnMap.put("hd", StringUtil.getValue(busInfo, "HZ_OPINION_CONTENT"));
        returnMap.put("fzr", StringUtil.getValue(busInfo, "HZ_OPINION_NAME"));
        returnMap.put("hdsj", StringUtil.getValue(busInfo, "HZ_OPINION_TIME"));

        returnMap.put("lxdh2", StringUtil.getValue(busInfo, "LZRSJHM"));//权利人代理人手机号码
        returnMap.put("lxdh4", StringUtil.getValue(busInfo, "DLRSJHM"));//义务人代理人手机号码
        
        //bdcSqbConfig.getTaskInfo(returnMap,"登簿");
    }


    private void setYgspfygdjQlrxx(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        String qlrJson = StringUtil.getValue(busInfo, "QLR_JSON");
        if(StringUtils.isNotEmpty(qlrJson)){
            List<Map> list = JSON.parseArray(qlrJson, Map.class);
            StringBuffer qlrxm =  new StringBuffer("");
            StringBuffer sfzjzl =  new StringBuffer("");
            StringBuffer zjhm =  new StringBuffer("");
            for (Map map : list) {
                if(StringUtils.isNotEmpty(qlrxm)){
                    qlrxm.append(",");
                }
                qlrxm.append(StringUtil.getValue(map, "MSFXM"));                
                if(StringUtils.isNotEmpty(sfzjzl)){
                    sfzjzl.append(",");
                }
                sfzjzl.append(StringUtil.getValue(map, "MSFZJLB"));                
                if(StringUtils.isNotEmpty(zjhm)){
                    zjhm.append(",");
                }
                zjhm.append(StringUtil.getValue(map, "MSFZJHM"));                
            }
            returnMap.put("qlrxm", qlrxm.toString());
            returnMap.put("sfzjzl",  sfzjzl.toString());
            returnMap.put("zjhm",  zjhm.toString());
        }
    }

    /**
     * 描述:设置预购商品房抵押权预告注销登记业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:23:00
     * @param
     * @return
     */
    private void setYgspfdyqygzxdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                        Map<String, Object> execution) {
        returnMap.put("qllx", "预告注销登记");
        returnMap.put("djlx", "预购商品房抵押预告注销登记");
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "QLR"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "QLRZJZL"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "QLRZJH"));
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "LZRXM"));
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "YWR"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "YWRZJZL"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "YWRZJH"));
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DLRXM"));
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (!ygxxJson.equals("")) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
        String zwqssj = StringUtil.getValue(busInfo, "QSSJ");
        String zwjssj = StringUtil.getValue(busInfo, "JSSJ");
        if (zwjssj.equals("") || zwqssj.equals("")) {
            returnMap.put("zwlxqx", "");
        } else {
            String zwBegin = bdcSqbConfig.bdcDateFormat(zwqssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSqbConfig.bdcDateFormat(zwjssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("djyy", StringUtil.getValue(busInfo, "ZXYY"));
        bdcSqbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

    /**
     * 描述:设置预购商品房预告注销登记业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:29:00
     * @param
     * @return
     */
    private void setYgspfygzxdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                       Map<String, Object> execution) {
        returnMap.put("qllx", "预告注销登记");
        returnMap.put("djlx", "预购商品房预告注销登记");
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "QLR"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "QLRZJZL"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "QLRZJH"));
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "YWR"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "YWRZJZL"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "YWRZJH"));
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (!ygxxJson.equals("")) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
        String zwqssj = StringUtil.getValue(busInfo, "QSSJ");
        String zwjssj = StringUtil.getValue(busInfo, "JSSJ");
        if (!zwjssj.equals("") && !zwqssj.equals("")) {
            String zwBegin = bdcSqbConfig.bdcDateFormat(zwqssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSqbConfig.bdcDateFormat(zwjssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("djyy", StringUtil.getValue(busInfo, "ZXYY"));
        bdcSqbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

    /**
     * 描述:预购商品房抵押权预告登记变更业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:47:00
     * @param
     * @return
     */
    private void setYgspfdyqygdjbgBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                       Map<String, Object> execution) {
        returnMap.put("qllx", "预告注销登记");
        returnMap.put("djlx", "预购商品房抵押权预告登记变更");
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "QLR_MC"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "QLR_ZJLX"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "QLR_ZJNO"));
        setYgspfdyqygdjYwrxx(returnMap, busInfo);
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (!ygxxJson.equals("")) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
        String zwqssj = StringUtil.getValue(busInfo, "ZW_BEGIN");
        String zwjssj = StringUtil.getValue(busInfo, "ZW_END");
        if (!zwjssj.equals("") && !zwqssj.equals("")) {
            String zwBegin = bdcSqbConfig.bdcDateFormat(zwqssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSqbConfig.bdcDateFormat(zwjssj,
                    "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("djyy", StringUtil.getValue(busInfo, "DJ_REASON"));
        bdcSqbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

    /**
     * 描述:预购商品房预告登记变更业务数据
     *
     * @author Madison You
     * @created 2020/4/27 14:47:00
     * @param
     * @return
     */
    private void setYgspfygdjbgBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                       Map<String, Object> execution) {
        returnMap.put("qllx", "预告变更登记");
        returnMap.put("djlx", "预购商品房预告登记变更");
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        setYgspfygdjQlrxx(returnMap, busInfo);
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "LZRXM"));
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "ZRFXM"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "ZRFZJLB"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "ZRFZJHM"));
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DLRXM"));
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (!fwxxJson.equals("")) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (!ygxxJson.equals("")) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
        returnMap.put("djyy", StringUtil.getValue(busInfo, "DJYY"));
        bdcSqbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }


    /**
     * 
     * 描述：保存审核意见到数据表
     * @author Rider Chen
     * @created 2020年8月16日 下午1:29:00
     * @param flowDatas
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveShyjData(Map<String, Object> flowDatas) {
        /*String isTempSave = (String) flowDatas.get("EFLOW_ISTEMPSAVE");
        if("-1".equals(isTempSave)) {
            //保存要推送到省网的办结数据
            bdcRegisterInterfaceService.concludeDataStorage(flowDatas);
        }*/
        // TODO Auto-generated method stub
        Map<String, Object> param = new HashMap<String, Object>();
        // 获取外网申请编号
        String bdcWwsqbh = (String) flowDatas.get("BDC_WWSQBH");
        // 获取审核意见
        String handleOpinion = (String) flowDatas.get("EFLOW_HANDLE_OPINION");
        // 获取当前节点
        String nodeName = (String) flowDatas.get("EFLOW_CURUSEROPERNODENAME");
        if(StringUtils.isNotEmpty(bdcWwsqbh)){
            Map<String, Object> sqbMap = this.getByJdbc("T_BDCQLC_WWSJ_SQB",
                    new String[]{"SLBH"}, new Object[]{bdcWwsqbh});
            if(null != sqbMap && sqbMap.size()>0){
                String id = sqbMap.get("ID").toString();//外网主键ID
                param.put("WYZJID", id);
                param.put("SLBH", bdcWwsqbh);
                param.put("SHYJ", StringUtils.isEmpty(handleOpinion)?"无审核意见":handleOpinion);
                if(StringUtils.isNotEmpty(nodeName) && nodeName.equals("网上预审")){
                    // 获取当前节点
                    String applyStatus = (String) flowDatas.get("EFLOW_APPLY_STATUS");
                    if (StringUtils.isNotEmpty(applyStatus) && applyStatus.equals("2")) {
                        param.put("SHZT", 1);
                    } else if (StringUtils.isNotEmpty(applyStatus) && applyStatus.equals("3")) {
                        param.put("SHZT", 2);
                    } else if (StringUtils.isNotEmpty(applyStatus) && applyStatus.equals("4")) {
                        param.put("SHZT", 3);
                    } else if (StringUtils.isNotEmpty(applyStatus) && applyStatus.equals("7")) {
                        param.put("SHZT", 2);
                    }
                } else if(StringUtils.isNotEmpty(nodeName) && nodeName.equals("办结")){
                    param.put("SHZT", 4);
                }
                this.saveOrUpdate(param, "T_BDCQLC_WWSJ_SHYJTOFTP", null);
                
            }
        }
        return flowDatas;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> pushFileToFtp(Map<String, Object> map) {
        // TODO Auto-generated method stub
        Properties properties = FileUtil.readProperties("project.properties");
        String filePath = properties.getProperty("PdfFilePath");
        List<Object> param = new ArrayList<Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select T.WYZJID,T.SLBH,T.SHZT,T.SHYJ,T.DXNR,'' AS NWYWH,T.YW_ID ");
        sql.append(" from T_BDCQLC_WWSJ_SHYJTOFTP t where t.push_status = ? ORDER BY T.SHZT ASC ");
        param.add(0);
        List<Map<String, Object>> list = bdcYgspfDao.findBySql(sql.toString(), param.toArray(), null);
        if(null != list && list.size()>0){
            String tplPath = AppUtil.getAppAbsolutePath() + "/webpage/bdcqlc/xlsTemplate/shyjTemplate.xls";
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String outFilePath = filePath + "/attachFiles/excel/" + currentDate + "/";
            int startRow = 2;
            int startCol = 1;
            Set<String> excludeFields = new HashSet<String>();
            excludeFields.add("YW_ID");
            String fileName = "3511001-" + UUIDGenerator.getUUID() + "-" + new Date().getTime() + ".xls";
            ExcelUtil.exportXls(tplPath, list, fileName, excludeFields, outFilePath, startRow, startCol);
            String bdcFtpHost = properties.getProperty("BDC_FTP_HOST");
            String bdcFtpPort = properties.getProperty("BDC_FTP_PORT");
            int bdcFtpPortParse = Integer.parseInt(bdcFtpPort);
            String bdcFtpUserName = properties.getProperty("BDC_FTP_USERNAME");
            String bdcFtpPassword = properties.getProperty("BDC_FTP_PASSWORD");
            String bdcFtpFtppath = properties.getProperty("BDC_FTP_FTPPATH_OUT");
            // 把文件上传在ftp上
            File file = new File(outFilePath + fileName);
            boolean success = FtpUtils.uploadFtpFile(bdcFtpHost, bdcFtpPortParse, bdcFtpUserName, bdcFtpPassword,
                    bdcFtpFtppath, file);
            result.put("success", success);
            result.put("filePath", outFilePath + fileName);
            if (success) {
                for (Map<String, Object> map2 : list) {
                    map2.put("push_status", 1);
                    bdcYgspfDao.saveOrUpdate(map2, "T_BDCQLC_WWSJ_SHYJTOFTP", map2.get("YW_ID").toString());
                }
                result.put("msg", "文件上传成功");
            } else {
                result.put("msg", "文件上传失败");
            }
        }
        return result;
    }
    

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月24日 下午2:52:52
     * @param flowVars
     * @return
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars){
        Map<String,Object> result = new HashMap<String, Object>();
        String YWR_JSON = (String) flowVars.get("YWR_JSON");
        List YWR_LIST = JSON.parseArray(YWR_JSON, Map.class);
        for(int i=0;i<YWR_LIST.size();i++){
            Map ywr = (Map) YWR_LIST.get(i);
            ywr.put("YWR_ZJLX_C", zjlxFormmat(ywr.get("YWR_ZJLX").toString()));
            if(i==0){
                if(YWR_LIST.size()==1){
                    ywr.put("GYQK", "■单独所有  □共同共有  □按份共有______%"); 
                }else{
                    ywr.put("GYQK", "□单独所有 ■共同共有  □按份共有______%");  
                }               
            }else{
                ywr.put("GYQK", "■共同共有 □按份共有______%");
            }
        }
        result.put("YWR_LIST", YWR_LIST);

        if(flowVars.get("DLR2_MC")!=null){
            result.put("YWR_LXR", flowVars.get("DLR2_MC"));
        }else{
            result.put("YWR_LXR", "");
        }
        if(flowVars.get("DLR2_SJHM")!=null){
            result.put("YWR_LXRSJ", flowVars.get("DLR2_SJHM"));
        }else{
            result.put("YWR_LXRSJ", "");
        }
        result.put("DLR2_MC", flowVars.get("DLR2_MC")==null?"":flowVars.get("DLR2_MC"));
        result.put("DLR2_ZJLX_C", zjlxFormmat(flowVars.get("DLR2_ZJLX")==null?"":flowVars.get("DLR2_ZJLX").toString()));
        result.put("DLR2_ZJNO", flowVars.get("DLR2_ZJNO")==null?"":flowVars.get("DLR2_ZJNO"));
        result.put("DLR2_SJHM", flowVars.get("DLR2_SJHM")==null?"":flowVars.get("DLR2_SJHM"));
        //权利人
        result.put("QLR_MC", flowVars.get("QLR_MC"));
        result.put("QLR_ZJLX", flowVars.get("QLR_ZJLX"));
        result.put("QLR_ZJLX_C", zjlxFormmat(flowVars.get("QLR_ZJLX").toString()));
        result.put("QLR_ZJNO", flowVars.get("QLR_ZJNO"));
        result.put("DLR_MC", flowVars.get("DLR_MC"));
        result.put("DLR_ZJLX", flowVars.get("DLR_ZJLX"));
        result.put("DLR_ZJLX_C", zjlxFormmat(flowVars.get("DLR_ZJLX").toString()));
        result.put("DLR_ZJNO", flowVars.get("DLR_ZJNO"));
        result.put("DLR_SJHM", flowVars.get("DLR_SJHM"));
        result.put("CLSCSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        result.put("DYRQZSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        result.put("DYQRQZSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        
        result.put("LOCATED", flowVars.get("LOCATED")==null?"":flowVars.get("LOCATED"));
        //result.put("BDC_BDCDJZMH", flowVars.get("BDC_BDCDJZMH")==null?"":flowVars.get("BDC_BDCDJZMH"));
        result.put("BDC_BDCDJZMH", flowVars.get("DJXX_CQZH")==null?"":flowVars.get("DJXX_CQZH"));
        return result;
    }


    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月24日 下午5:28:32
     * @param zjlx
     * @return
     */
    private String zjlxFormmat(String zjlx){
        String fmtzjlx = "";
        if(zjlx.equals("身份证")){
            fmtzjlx = "IDCard";
        }else if(zjlx.equals("港澳居民来往内地通行证")){
            fmtzjlx = "HMPass";
        }else if(zjlx.equals("护照")){
            fmtzjlx = "Passport";
        }else if(zjlx.equals("营业执照")||zjlx.equals("统一社会信用代码")){
            fmtzjlx = "SOCNO";
        }else if(zjlx.equals("组织机构代码")){
            fmtzjlx = "ORANO";
        }else{
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }
    
    /**
     * 
     * 描述    生成预抵申请表
     * @author Danto Huang
     * @created 2020年8月25日 上午9:43:53
     * @param returnMap
     * @param path
     */
    public void generateSQB(Map<String, Object> returnMap,String path){
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(path));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();
            for (XWPFTableRow row : xwpfDocument.getTables().get(0).getRows()) {
                cells.addAll(row.getTableCells());
            }
            for (XWPFTableCell cell1 : cells) {
                String tag = cell1.getText().trim();
                List<XWPFParagraph> paragraphs = cell1.getParagraphs();
                WordRedrawUtil.processParagraphs(paragraphs, returnMap, xwpfDocument);
            }
            // 处理动态表格
            List<Map<String, Object>> equipList = (List<Map<String, Object>>) returnMap.get("YWR_LIST");
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                // table.addRow(rows.get(0));
                XWPFTableRow oldRow = null;
     
                WordReplaceUtil.addTableRow3(returnMap, table, rows, oldRow, equipList, "YWR_MC");
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> celllists = row.getTableCells();
                    for (XWPFTableCell cell : celllists) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        WordReplaceUtil.setTableRow(returnMap, xwpfDocument, celllists, paragraphListTable, equipList,
                                "YWR_MC");
                        WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                    }
                }
            }
            
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
}
