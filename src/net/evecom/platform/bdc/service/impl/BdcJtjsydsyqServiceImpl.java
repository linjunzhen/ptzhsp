/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSONArray;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcJtjsydsyqDao;
import net.evecom.platform.bdc.service.BdcDyqscdjService;
import net.evecom.platform.bdc.service.BdcJtjsydsyqService;
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
 * 描述  不动产全流程集体建设用地使用权
 * @author Madison You
 * @created 2020年4月21日 下午4:17:01
 */
@Service("bdcJtjsydsyqService")
public class BdcJtjsydsyqServiceImpl extends BaseServiceImpl implements BdcJtjsydsyqService {


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/21 17:58:00
     * @param 
     * @return 
     */
    @Resource
    private BdcJtjsydsyqDao bdcJtjsydsyqDao;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/29 8:42:00
     * @param 
     * @return 
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/26 16:35:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/21 17:59:00
     * @param 
     * @return 
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcJtjsydsyqDao;
    }

    /**
     * 描述:不动产全流程集体建设用地使用权转移登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:59:00
     * @param 
     * @return 
     */
    @Override
    public void setJtjsydsyqzydjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置不动产全流程集体建设用地使用权转移登记数据*/
        this.setJtjsydsyqzydjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);

    }

    /**
     * 描述:不动产全流程集体建设用地使用权变更登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:59:00
     * @param 
     * @return 
     */
    @Override
    public void setJtjsydsyqbgdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置不动产全流程集体建设用地使用权变更登记数据*/
        this.setJtjsydsyqbgdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);

    }

    /**
     * 描述:不动产全流程集体建设用地使用权首次登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:59:00
     * @param
     * @return
     */
    @Override
    public void setJtjsydsyqscdjData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置预购商品房预告登记业务数据*/
        this.setJtjsydsyqscdjBusInfo(returnMap, busInfo, execution);

        /*模板字符串替换*/
        bdcSpbConfig.bdcCreateSpbConfig(returnMap);

    }


    /**
     * 描述:不动产全流程集体建设用地使用权首次登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:09:00
     * @param
     * @return
     */
    private void setJtjsydsyqscdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                               Map<String, Object> execution) {
        returnMap.put("qllx", "集体建设用地使用权");
        returnMap.put("djlx", "首次登记");

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
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();

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
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");
            }

            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
            returnMap.put("lxdh1", bdcSpbConfig.bdcStringOutFormate(lxdh1));
            returnMap.put("lxdh2", bdcSpbConfig.bdcStringOutFormate(lxdh2));
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
            StringBuffer lxdh3 = new StringBuffer();
            StringBuffer lxdh4 = new StringBuffer();
            /*原不动产权证书号*/
            StringBuffer ybdcqzsh = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "QLR")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "BDC_QLRZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "BDC_QLRZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "BDC_FDDBRXM")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "BDC_DLRXM")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "BDC_DLJGMC")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "BDC_FDDBRDH")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "BDC_DLRDH")).append(",");
                ybdcqzsh.append(StringUtil.getValue(qsMap, "POWERSOURCEIDNUM")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSpbConfig.bdcStringOutFormate(ybdcqzsh));
        }

        returnMap.put("zl", StringUtil.getValue(busInfo, "ZD_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(
                StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        /*面积*/
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²");
        returnMap.put("mj", mj);
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM"));
        /*登记原因*/
        returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
        /*登记原因证明文件*/
        bdcSpbConfig.getMaterNameList(returnMap);
        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "QDJG"));
        /*填写初审核定意见'*/
        bdcSpbConfig.getbdcDjshOpinion(busInfo,returnMap);
        //bdcSpbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

    /**
     * 描述:不动产全流程集体建设用地使用权变更登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:09:00
     * @param
     * @return
     */
    private void setJtjsydsyqbgdjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                               Map<String, Object> execution) {
        returnMap.put("qllx", "集体建设用地使用权");
        returnMap.put("djlx", "变更登记");

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
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();

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
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");
            }

            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
            returnMap.put("lxdh1", bdcSpbConfig.bdcStringOutFormate(lxdh1));
            returnMap.put("lxdh2", bdcSpbConfig.bdcStringOutFormate(lxdh2));
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
            StringBuffer lxdh3 = new StringBuffer();
            StringBuffer lxdh4 = new StringBuffer();
            /*原不动产权证书号*/
            StringBuffer ybdcqzsh = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "QLR")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "BDC_QLRZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "BDC_QLRZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "BDC_FDDBRXM")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "BDC_DLRXM")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "BDC_DLJGMC")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "BDC_FDDBRDH")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "BDC_DLRDH")).append(",");
                ybdcqzsh.append(StringUtil.getValue(qsMap, "POWERSOURCEIDNUM")).append(",");
                
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSpbConfig.bdcStringOutFormate(ybdcqzsh));
            
        }
        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(busInfo, "ZD_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(
                StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        /*面积*/
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²");
        returnMap.put("mj", mj);
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM"));
        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "QDJG"));
        /*登记原因*/
        returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
        /*登记原因证明文件*/
        bdcSpbConfig.getMaterNameList(returnMap);
        /*填写初审核定意见'*/
        bdcSpbConfig.getbdcDjshOpinion(busInfo,returnMap);
        //bdcSpbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

    /**
     * 描述:不动产全流程集体建设用地使用权转移登记审批表
     *
     * @author Madison You
     * @created 2020/4/21 17:09:00
     * @param
     * @return
     */
    private void setJtjsydsyqzydjBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                               Map<String, Object> execution) {
        returnMap.put("qllx", "集体建设用地使用权");
        returnMap.put("djlx", "转移登记");

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
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();

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
                lxdh1.append(StringUtil.getValue(piMap, "POWERPEOPLEMOBILE")).append(",");
                lxdh2.append(StringUtil.getValue(piMap, "POWAGENTTEL")).append(",");
            }

            returnMap.put("qlrxm", bdcSpbConfig.bdcStringOutFormate(qlrxm));
            returnMap.put("sfzjzl", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
            returnMap.put("zjhm", bdcSpbConfig.bdcStringOutFormate(zjhm));
            returnMap.put("txdz", bdcSpbConfig.bdcStringOutFormate(txdz));
            returnMap.put("yb", bdcSpbConfig.bdcStringOutFormate(yb));
            returnMap.put("fddbr", bdcSpbConfig.bdcStringOutFormate(fddbr));
            returnMap.put("dlrxm", bdcSpbConfig.bdcStringOutFormate(dlrxm));
            returnMap.put("lxdh1", bdcSpbConfig.bdcStringOutFormate(lxdh1));
            returnMap.put("lxdh2", bdcSpbConfig.bdcStringOutFormate(lxdh2));
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
            StringBuffer lxdh3 = new StringBuffer();
            StringBuffer lxdh4 = new StringBuffer();
            /*原不动产权证书号*/
            StringBuffer ybdcqzsh = new StringBuffer();

            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM")).append(",");
                fddbr1.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBR")).append(",");
                lxdh3.append(StringUtil.getValue(qsMap, "POWERSOURCE_FDDBR_TEL")).append(",");
                dlrxm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLR")).append(",");
                lxdh4.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLR_TEL")).append(",");
                ybdcqzsh.append(StringUtil.getValue(qsMap, "POWERSOURCE_QSWH")).append(",");
                dljgmc1.append(StringUtil.getValue(qsMap, "POWERSOURCE_DLJGMC")).append(",");
            }

            returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
            returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl1));
            returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
            returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSpbConfig.bdcStringOutFormate(ybdcqzsh));
        }
        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(busInfo, "ZD_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(
                StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        /*面积*/
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²");
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM"));
        //抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "QDJG"));
        //登记原因
        returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
        /*登记原因证明文件*/
        bdcSpbConfig.getMaterNameList(returnMap);
        /*填写初审核定意见'*/
        bdcSpbConfig.getbdcDjshOpinion(busInfo,returnMap);
        //bdcSpbConfig.getTaskInfo(returnMap,"登簿");
        returnMap.put("bz", StringUtil.getValue(busInfo, "REMARK"));
    }

}
