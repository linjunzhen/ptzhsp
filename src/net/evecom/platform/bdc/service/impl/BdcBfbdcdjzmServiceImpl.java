/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcBfbdcdjzmDao;
import net.evecom.platform.bdc.service.BdcBfbdcdjzmService;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 不动产全流程补发不动产权证书和不动产登记证明
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年3月24日 下午5:13:08
 */
@Service("bdcBfbdcdjzmService")
public class BdcBfbdcdjzmServiceImpl extends BaseServiceImpl implements BdcBfbdcdjzmService {


    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/3/24 17:18:00
     * @param
     * @return
     */
    @Resource
    private BdcBfbdcdjzmDao bdcBfbdcdjzmDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/3/25 10:49:00
     * @param 
     * @return 
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/28 11:40:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/3/24 17:14:00
     * @param 
     * @return 
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcBfbdcdjzmDao;
    }


    /**
     * 描述:不动产全流程补发不动产权证书和不动产登记证明（补发不动产登记）数据回填
     *
     * @author Madison You
     * @created 2020/3/24 17:21:00
     * @param
     * @return
     */
    @Override
    public void setBfbdcdjzmData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置补发不动产权证书和不动产登记证明（补发不动产登记）数据*/
        this.setBfbdcdjzmBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:不动产全流程补发不动产权证书和不动产登记证明数据回填
     *
     * @author Madison You
     * @created 2020/4/28 14:23:00
     * @param
     * @return
     */
    @Override
    public void setBfbdcqzshdjzmData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置补发不动产权证书和不动产登记证明业务数据*/
        this.setBfbdcqzshdjzmBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);
    }

    /**
     * 描述:不动产全流程换发产权登记数据回填
     *
     * @author Madison You
     * @created 2020/4/28 17:00:00
     * @param
     * @return
     */
    @Override
    public void setHfcqdjData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置换发产权登记数据*/
        this.setHfcqdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);
    }


    /**
     * 描述:设置补发不动产权证书和不动产登记证明（补发不动产登记）业务数据
     *
     * @author Madison You
     * @created 2020/3/24 17:22:00
     * @param
     * @return
     */
    private void setBfbdcdjzmBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                     Map<String, Object> execution) {
        /*设置通用数据*/
        this.bfNormalBusInfoData(returnMap,busInfo,execution);

        /*设置三种不同类型数据   1 补发证明登记 2 预购商品房预告补发登记 3 抵押权预告补发登记*/
        String type = StringUtil.getValue(busInfo, "TYPE");
        if (type.equals("1")) {
            /*设置补发登记证明数据*/
            this.bfdjzmBusInfoData(returnMap, busInfo, execution);
        } else if (type.equals("2")) {
            /*设置预购商品房预告补发登记数据*/
            this.ygspfygbfdjBusInfoData(returnMap, busInfo, execution);
        } else if (type.equals("3")) {
            /*设置抵押权预告补发登记数据*/
            this.dyqygbfdjBusInfoData(returnMap, busInfo, execution);
        }

    }

    /**
     * 描述:设置换发产权登记数据
     *
     * @author Madison You
     * @created 2020/4/28 17:03:00
     * @param
     * @return
     */
    private void setHfcqdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                  Map<String, Object> execution) {

        /*设置通用数据*/
        this.hfNormalBusInfoData(returnMap,busInfo,execution);

        /*设置四种不同类型数据 1 换发产权证书 2 换发抵押权登记证明 3 换发预告登记证明 4 换发抵押权预告登记证明*/
        String type = StringUtil.getValue(busInfo, "HF_TYPE");

        if (type.equals("1")) {
            this.hfcqzsBusInfoData(returnMap, busInfo, execution);
        } else if (type.equals("2")) {
            this.hfdyqdjzmBusInfoData(returnMap, busInfo, execution);
        } else if (type.equals("3")) {
            this.hfygdjzmBusInfoData(returnMap, busInfo, execution);
        } else if (type.equals("4")) {
            this.hfdyqygdjzmBusInfoData(returnMap, busInfo, execution);
        }

    }

    /**
     * 描述:设置补发不动产权证书和不动产登记证明业务数据
     *
     * @author Madison You
     * @created 2020/4/28 14:25:00
     * @param
     * @return
     */
    private void setBfbdcqzshdjzmBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                         Map<String, Object> execution) {
        returnMap.put("qllx", "宅基地使用权/房屋所有权");
        returnMap.put("djlx", "抵押权预告补发登记");
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));

        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {

            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /*证件类型代码转换*/
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

        /*义务人信息JSON*/
        String qsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {

            StringBuffer ywrxm = new StringBuffer();
            StringBuffer sfzjzl1 = new StringBuffer();
            StringBuffer zjhm1 = new StringBuffer();
            StringBuffer fddbr1 = new StringBuffer();
            StringBuffer dlrxm1 = new StringBuffer();
            StringBuffer dljgmc1 = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "QLR")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "BDC_QLRZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "BDC_QLRZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "BDC_FDDBRXM")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "BDC_DLRXM")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "BDC_DLJGMC")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
        }

        /*房屋信息*/
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }

        /*预告信息*/
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        /*抵押情况*/
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
        bdcSpbConfig.getTaskInfo(returnMap,"登簿");
    }

    /**
     * 描述:设置抵押权预告补发登记数据
     *
     * @author Madison You
     * @created 2020/3/25 9:42:00
     * @param
     * @return
     */
    private void dyqygbfdjBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                      Map<String, Object> execution) {
        returnMap.put("djlx", "抵押权预告补发登记");

        /*权利人信息*/
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "QLR_MC"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "QLR_ZJLX"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "QLR_ZJNO"));

        /*义务人信息*/
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "YWR_MC"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "YWR_ZJLX"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "YWR_ZJNO"));
        /*债务履行期限*/
        String zw_begin = StringUtil.getValue(busInfo, "ZW_BEGIN");
        String zw_end = StringUtil.getValue(busInfo, "ZW_END");
        if (!zw_begin.equals("") && !zw_end.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(zw_begin,"yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(zw_end,"yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        bdcSpbConfig.getTaskInfo(returnMap, "登簿");

    }

    /**
     * 描述:设置预购商品房预告补发登记数据
     *
     * @author Madison You
     * @created 2020/3/25 9:41:00
     * @param
     * @return
     */
    private void ygspfygbfdjBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                        Map<String, Object> execution) {
        returnMap.put("djlx", "预购商品房预告补发登记");

        /*权利人信息*/
        returnMap.put("qlrxm", StringUtil.getValue(busInfo, "MSFXM"));
        returnMap.put("sfzjzl", StringUtil.getValue(busInfo, "MSFZJLB"));
        returnMap.put("zjhm", StringUtil.getValue(busInfo, "MSFZJHM"));
        returnMap.put("dlrxm", StringUtil.getValue(busInfo, "LZRXM"));

        /*义务人信息*/
        returnMap.put("ywrxm", StringUtil.getValue(busInfo, "ZRFXM"));
        returnMap.put("sfzjzl1", StringUtil.getValue(busInfo, "ZRFZJLB"));
        returnMap.put("zjhm1", StringUtil.getValue(busInfo, "ZRFZJHM"));
        returnMap.put("dlrxm1", StringUtil.getValue(busInfo, "DLRXM"));
        bdcSpbConfig.getTaskInfo(returnMap, "登簿");
    }

    /**
     * 描述:设置补发登记证明数据
     *
     * @author Madison You
     * @created 2020/3/25 9:41:00
     * @param
     * @return
     */
    private void bfdjzmBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                   Map<String, Object> execution) {

        returnMap.put("djlx", "补发证明登记");
        /*债务履行期限*/
        String zwqssj = StringUtil.getValue(busInfo, "ZWQSSJ");
        String zwjssj = StringUtil.getValue(busInfo, "ZWJSSJ");
        if (!zwjssj.equals("") && zwjssj.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(zwqssj,"yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(zwjssj,"yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {

            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();

            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /*证件类型代码转换*/
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


        /*义务人信息JSON*/
        String qsJson = StringUtil.getValue(busInfo, "QSLY_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {

            StringBuffer ywrxm = new StringBuffer();
            StringBuffer sfzjzl1 = new StringBuffer();
            StringBuffer zjhm1 = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "QLR")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "QLRZJZL")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "QLRZJH")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
        }
        bdcSpbConfig.getTaskInfo(returnMap, "登簿");

    }

    /**
     * 描述:设置通用数据
     *
     * @author Madison You
     * @created 2020/3/25 9:34:00
     * @param
     * @return
     */
    private void bfNormalBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                   Map<String, Object> execution) {
        returnMap.put("qllx", "宅基地使用权/房屋所有权");
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        /*房屋信息*/
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(busInfo, "FWXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }

        /*预告信息*/
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(busInfo, "YGXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));
        /*抵押情况*/
        returnMap.put("bdbzqse", StringUtil.getValue(ygMap, "QDJG"));
    }

    /**
     * 描述:换发抵押权预告登记证明数据
     *
     * @author Madison You
     * @created 2020/4/28 17:07:00
     * @param
     * @return
     */
    private void hfdyqygdjzmBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                        Map<String, Object> execution) {
        Map<String, Object> childBusMap = null;
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            childBusMap = bdcSpbConfig.bdcGetChildTableBusInfo(sbh, "T_BDCQLC_HFDYCYGDJ");
        }

        returnMap.put("djlx", "换发抵押权预告登记证明");

        /*权利人信息*/
        returnMap.put("qlrxm", StringUtil.getValue(childBusMap, "QLR_MC"));
        returnMap.put("sfzjzl", StringUtil.getValue(childBusMap, "QLR_ZJLX"));
        returnMap.put("zjhm", StringUtil.getValue(childBusMap, "QLR_ZJNO"));

        /*义务人信息*/
        returnMap.put("ywrxm", StringUtil.getValue(childBusMap, "YWR_MC"));
        returnMap.put("sfzjzl1", StringUtil.getValue(childBusMap, "YWR_ZJLX"));
        returnMap.put("zjhm1", StringUtil.getValue(childBusMap, "YWR_ZJNO"));

        /*债务履行期限*/
        String qlqssj = StringUtil.getValue(childBusMap, "ZW_BEGIN");
        String qljssj = StringUtil.getValue(childBusMap, "ZW_END");
        if (!qljssj.equals("") && !qlqssj.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(qlqssj, "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(qljssj, "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }

        /*预告信息*/
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(childBusMap, "YGXX_JSON");
        if (StringUtils.isNotEmpty(ygxxJson)) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }

        /*房屋信息*/
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(childBusMap, "FWXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));

    }

    /**
     * 描述:换发预告登记证明数据
     *
     * @author Madison You
     * @created 2020/4/28 17:08:00
     * @param
     * @return
     */
    private void hfygdjzmBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                     Map<String, Object> execution) {
        Map<String, Object> childBusMap = null;
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            childBusMap = bdcSpbConfig.bdcGetChildTableBusInfo(sbh, "T_BDCQLC_HFYGDJZM");
        }

        returnMap.put("djlx", "换发预告登记证明");

        /*权利人信息*/
        returnMap.put("qlrxm", StringUtil.getValue(childBusMap, "JBXX_QLR"));
        returnMap.put("sfzjzl", StringUtil.getValue(childBusMap, "JBXX_ZJZL"));
        returnMap.put("zjhm", StringUtil.getValue(childBusMap, "JBXX_ZJHM"));

        /*义务人信息*/
        returnMap.put("ywrxm", StringUtil.getValue(childBusMap, "JBXX_YWR"));
        returnMap.put("sfzjzl1", StringUtil.getValue(childBusMap, "JBXX_YWRZJLB"));
        returnMap.put("zjhm1", StringUtil.getValue(childBusMap, "JBXX_YWRZJHM"));

        /*债务履行期限*/
        String QSSJ = StringUtil.getValue(childBusMap, "QSSJ");
        String JSSJ = StringUtil.getValue(childBusMap, "JSSJ");
        if (!JSSJ.equals("") && !QSSJ.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(QSSJ, "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(JSSJ, "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }

        /*预告信息*/
        Map<String, Object> ygMap = null;
        String ygxxJson = StringUtil.getValue(childBusMap, "YGXX_JSON");
        if (StringUtils.isNotEmpty(ygxxJson)) {
            List<Map> list = JSON.parseArray(ygxxJson, Map.class);
            if (list != null && list.size() > 0) {
                ygMap = list.get(0);
            }
        }

        /*房屋信息*/
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(childBusMap, "FWXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(ygMap, "BDCZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(ygMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(ygMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(ygMap, "YTSM"));

    }

    /**
     * 描述:换发抵押权登记证明数据
     *
     * @author Madison You
     * @created 2020/4/28 17:09:00
     * @param
     * @return
     */
    private void hfdyqdjzmBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                      Map<String, Object> execution) {
        Map<String, Object> childBusMap = null;
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            childBusMap = bdcSpbConfig.bdcGetChildTableBusInfo(sbh, "T_BDCQLC_HFDYQZMDJ");
        }

        returnMap.put("djlx", "换发抵押权登记证明");

        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(childBusMap, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "QLR_DYQR")).append(",");
                sfzjzl.append(StringUtil.getValue(piMap, "QLR_DYQRZJLX")).append(",");
                zjhm.append(StringUtil.getValue(piMap, "QLR_DYQRZJHM")).append(",");
                txdz.append(StringUtil.getValue(piMap, "QLR_DZ")).append(",");
                yb.append(StringUtil.getValue(piMap, "QLR_YB")).append(",");
                fddbr.append(StringUtil.getValue(piMap, "QLR_LEGALNAME")).append(",");
                dlrxm.append(StringUtil.getValue(piMap, "QLR_DLRNAME")).append(",");
            }
            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
        }

        /*义务人信息*/
        returnMap.put("ywrxm", StringUtil.getValue(childBusMap, "JBXX_DYR"));
        returnMap.put("sfzjzl1", StringUtil.getValue(childBusMap, "DYRZJLX"));
        returnMap.put("zjhm1", StringUtil.getValue(childBusMap, "DYRZJHM"));

        /*债务履行期限*/
        String qlqssj = StringUtil.getValue(childBusMap, "QLQSSJ");
        String qljssj = StringUtil.getValue(childBusMap, "QLJSSJ");
        if (!qljssj.equals("") && !qlqssj.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(qlqssj, "yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(qljssj, "yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }

        /*抵押档案信息*/
        Map<String, Object> dydaMap = null;
        String dydaxxJson = StringUtil.getValue(childBusMap, "DYDA_JSON");
        if (StringUtils.isNotEmpty(dydaxxJson)) {
            List<Map> list = JSON.parseArray(dydaxxJson, Map.class);
            if (list != null && list.size() > 0) {
                dydaMap = list.get(0);
            }
        }

        /*房屋信息*/
        Map<String, Object> fwMap = null;
        String fwxxJson = StringUtil.getValue(childBusMap, "FWXX_JSON");
        if (StringUtils.isNotEmpty(fwxxJson)) {
            List<Map> list = JSON.parseArray(fwxxJson, Map.class);
            if (list != null && list.size() > 0) {
                fwMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(fwMap, "ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(dydaMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(fwMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(fwMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(fwMap, "GHYT"));



    }

    /**
     * 描述:换发产权证书数据
     *
     * @author Madison You
     * @created 2020/3/30 11:29:00
     * @param
     * @return
     */
    private void hfcqzsBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                   Map<String, Object> execution) {

        returnMap.put("djlx", "换发产权证书");

        /*获取子表业务数据*/
        Map<String, Object> childBusMap = null;
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            childBusMap = bdcSpbConfig.bdcGetChildTableBusInfo(sbh, "T_BDCQLC_HFCQZS");
        }

        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(childBusMap, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            StringBuffer qlrxm = new StringBuffer();
            StringBuffer sfzjzl = new StringBuffer();
            StringBuffer zjhm = new StringBuffer();
            StringBuffer txdz = new StringBuffer();
            StringBuffer yb = new StringBuffer();
            StringBuffer fddbr = new StringBuffer();
            StringBuffer dlrxm = new StringBuffer();
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            for (Map<String, Object> piMap : piList) {
                qlrxm.append(StringUtil.getValue(piMap, "POWERPEOPLENAME")).append(",");
                /*证件类型代码转换*/
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

        /*义务人信息JSON*/
        String qsJson = StringUtil.getValue(childBusMap, "POWERSOURCEINFO_JSON");
        if (StringUtils.isNotEmpty(qsJson)) {

            StringBuffer ywrxm = new StringBuffer();
            StringBuffer sfzjzl1 = new StringBuffer();
            StringBuffer zjhm1 = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
        }

        /*档案信息*/
        Map<String, Object> daMap = null;
        String daxxJson = StringUtil.getValue(childBusMap, "DAXX_JSON");
        if (StringUtils.isNotEmpty(daxxJson)) {
            List<Map> list = JSON.parseArray(daxxJson, Map.class);
            if (list != null && list.size() > 0) {
                daMap = list.get(0);
            }
        }

        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(daMap, "TDZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(daMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(daMap,"FWXZ"));
        returnMap.put("bdclx", "土地、房产");
        returnMap.put("mj", StringUtil.getValue(daMap, "JZMJ"));
        returnMap.put("yt", StringUtil.getValue(daMap, "TDYTSM"));

    }


    /**
     * 描述:设置通用数据
     *
     * @author Madison You
     * @created 2020/3/30 10:43:00
     * @param
     * @return
     */
    private void hfNormalBusInfoData(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                   Map<String, Object> execution) {
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        returnMap.put("djyy", StringUtil.getValue(busInfo, "DJYY"));
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "ZDQLLX", StringUtil.getValue(busInfo, "HF_QLLX") });
        returnMap.put("qllx", dictionary.get("DIC_NAME"));
        bdcSpbConfig.getTaskInfo(returnMap, "登簿");
    }


}
