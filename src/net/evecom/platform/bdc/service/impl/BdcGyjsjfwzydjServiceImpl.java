/*

 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import ewebeditor.admin.login_jsp;
import icepdf.a;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.servlet.DownLoadServlet;
import net.evecom.platform.bdc.dao.BdcGyjsjfwzydjDao;
import net.evecom.platform.bdc.service.BdcGyjsjfwzydjService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 
 * 描述 国有建设用地及房屋转移登记Service
 * 
 * @author Roger Li
 * @created 2020年1月6日 下午3:36:55
 */
@Service("bdcGyjsjfwzydjService")
public class BdcGyjsjfwzydjServiceImpl extends BaseServiceImpl implements BdcGyjsjfwzydjService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcGyjsjfwzydjServiceImpl.class);
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
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    
    
    /**
     * bdcQlcMaterGenAndSignService
     */
    @Resource
    private  BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * 
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 引入dao
     */
    @Resource
    private BdcGyjsjfwzydjDao dao;

    /**
     * 引入dao
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    /***
     * 
     * 描述 不动产登记审批表两审抵押联办数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月30日 下午5:53:01
     * @param args
     * @see net.evecom.platform.bdc.service.BdcDyqscdjService#(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setSecondAudit(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> serviceItem = args.get("serviceItem");
        Map<String, Object> returnMap = args.get("returnMap");
        Map<String, Object> variables = args.get("requestMap");

        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }

        // 为标签设初值以防页面出现标签
        this.setDefaultValue(returnMap, StringUtil.getValue(execution, "EXE_ID"));
        // 设值流程主表相关信息 如收件人
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值流程相关信息 如环节审核人、审核意见等
        this.setFlowAllObj1(returnMap, flowAllObj);
        // 设值服务事项和流程表单相关信息 如服务事项名称
        this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo1(returnMap, busInfo, variables);

        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            if (WordRedrawUtil.encode(byteArrayOut, sbh)) {
                Map<String, Object> img = new HashMap<String, Object>();
                img.put("width", WordRedrawUtil.bdcQRCODEWIDTH);
                img.put("height", WordRedrawUtil.bdcQRCODEHEIGHT);
                img.put("type", WordRedrawUtil.pICEXT);
                img.put("content", byteArrayOut.toByteArray());
                returnMap.put("p_ewm", img);
            }
            try {
                byteArrayOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                WordRedrawUtil.processNormalTable07(
                        "attachFiles//readtemplate//files//" + returnMap.get("TemplatePath"), returnMap);
            }
        }
    }

    /**
     * 
     * 描述 为标签设初值以防页面出现标签
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:35:52
     * @param returnMap
     * @return
     */
    private void setDefaultValue(Map<String, Object> returnMap, String exeId) {
        returnMap.put("dlrxm", "");
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");
        returnMap.put("dlrxm2", "");
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc2", "");
        returnMap.put("yb", "");
        returnMap.put("yb1", "");
        returnMap.put("lz", "");
        returnMap.put("bz", "");
        returnMap.put("xydzl", "");
        returnMap.put("xydbdcdyh", "");
        returnMap.put("ewm", "");
        returnMap.put("djyy", "");
        returnMap.put("djyyzmwj", "");

        // 审核意见
        returnMap.put("hd", "");
        returnMap.put("fzr", "");
        returnMap.put("hdsj", "");
        returnMap.put("sh", "");
        returnMap.put("scr1", "");
        returnMap.put("fhrq", "");
        returnMap.put("cs", "");
        returnMap.put("scr", "");
        returnMap.put("csrq", "");
        
        // 水电气广
        returnMap.put("waternum", "");
        returnMap.put("pownum", "");
        returnMap.put("gasnum", "");
        returnMap.put("svanum", "");
    }

    /**
     * 
     * 描述 获取流程主表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:52:45
     * @param execution
     * @return
     */
    private void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 申请人通信地址
        // returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 
     * 描述 设值服务事项和流程表单相关信息
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:26:46
     * @param returnMap,serviceItem
     * @return
     */
    private void setItemAndDefInfo(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }

    /**
     * 
     * 描述 GYJsJFWZYDJ
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     * @param busInfo
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setBusRecordInfo1(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> variables) {
        // TODO 两审抵押联办 有些字段不确定
        List<Map> powerPeopleList = JSON.parseArray(StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON"), Map.class);
        List<Map> powerSourceList = JSON.parseArray(StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON"), Map.class);

        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "ZDQLLX", StringUtil.getValue(busInfo, "ZD_QLLX") });
        returnMap.put("qllx", dictionary.get("DIC_NAME"));
        returnMap.put("djlx",
                dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(variables, "ZYDJ_TYPE"), "zydjlx"));
        // 权利人/义务人
        StringBuilder qlrxm = new StringBuilder();
        StringBuilder sfzjzl = new StringBuilder();
        StringBuilder zjhm = new StringBuilder();
        Map<String, Object> powerPeopleInfo = null;
        String idType = null;
        // 遍历权利人列表
        Iterator iter = powerPeopleList.iterator();
        while (iter.hasNext()) {
            powerPeopleInfo = (Map<String, Object>) iter.next();
            qlrxm.append(StringUtil.getValue(powerPeopleInfo, "POWERPEOPLENAME"));
            idType = dictionaryService.findByDicCodeAndTypeCode(
                    StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEIDTYPE"), "DocumentType");
            sfzjzl.append(idType);
            zjhm.append(StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEIDNUM"));
            if (iter.hasNext()) {
                qlrxm.append(",");
                sfzjzl.append(",");
                zjhm.append(",");
            }
        }
        returnMap.put("qlrxm", qlrxm.toString());
        returnMap.put("sfzjzl", sfzjzl.toString());
        returnMap.put("zjhm", zjhm.toString());
        returnMap.put("txdz", StringUtil.getValue(powerPeopleList.get(0), "POWERPEOPLEADDR"));
        returnMap.put("yb", StringUtil.getValue(powerPeopleList.get(0), "POWERPEOPLEPOSTCODE"));
        // 义务人信息
        StringBuilder ywrxm = new StringBuilder();
        StringBuilder sfzjzl1 = new StringBuilder();
        StringBuilder zjhm1 = new StringBuilder();
        Map<String, Object> powerSource = null;
        Iterator powerSourceIter = powerSourceList.iterator();
        while (powerSourceIter.hasNext()) {
            powerSource = (Map<String, Object>) powerSourceIter.next();
            ywrxm.append(StringUtil.getValue(powerSource, "POWERSOURCE_QLRMC"));
            sfzjzl1.append(StringUtil.getValue(powerSource, "POWERSOURCE_ZJLX"));
            zjhm1.append(StringUtil.getValue(powerSource, "POWERSOURCE_ZJHM"));
            if (powerSourceIter.hasNext()) {
                ywrxm.append(",");
                sfzjzl1.append(",");
                zjhm1.append(",");
            }
        }
        returnMap.put("ywrxm", ywrxm.toString());
        returnMap.put("sfzjzl1", sfzjzl1.toString());
        returnMap.put("zjhm1", zjhm1.toString());
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        // 抵押权人/抵押人
        returnMap.put("dyqrxm", StringUtil.getValue(busInfo, "DY_DYQR"));
        returnMap.put("sfzjzl2", StringUtil.getValue(busInfo, "DY_DYQRZJLX"));
        returnMap.put("zjhm2", StringUtil.getValue(busInfo, "DY_DYQRZJHM"));
        returnMap.put("txdz2", "");
        returnMap.put("yb2", "");
        returnMap.put("dyrxm", StringUtil.getValue(busInfo, "DY_DYR"));
        returnMap.put("sfzjzl3", StringUtil.getValue(busInfo, "DY_DYRZJLX"));
        returnMap.put("zjhm3", StringUtil.getValue(busInfo, "DY_DYRZJHM"));
        returnMap.put("txdz3", "");
        returnMap.put("yb3", "");
        // 不动产信息
        returnMap.put("zl", StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        StringBuilder qlxz = new StringBuilder();
        // 字典qlxz
        qlxz.append(dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"))
                .append("/").append(StringUtil.getValue(busInfo, "FW_XZ"));
        returnMap.put("qlxz", qlxz.toString());
        // TODO 写死
        returnMap.put("bdclx", "土地、房产"); //
        String zdmj = StringUtil.getValue(busInfo, "ZD_MJ");
        String fwjzmj = StringUtil.getValue(busInfo, "FW_JZMJ");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(zdmj)) {
            sb.append("宗地面积:").append(zdmj).append("平方米/");
            if (StringUtils.isNotBlank(fwjzmj)) {
                sb.append("房屋建筑面积:").append(fwjzmj).append("平方米");
            }
        }
        returnMap.put("mj", sb.toString());
        String zdyt = StringUtil.getValue(busInfo, "ZD_TDYT");
        String fwyt = StringUtil.getValue(busInfo, "FW_GHYT");
        StringBuilder yt = new StringBuilder();
        if (StringUtils.isNotBlank(zdyt)) {
            yt.append(zdyt).append("/");
            if (StringUtils.isNotBlank(fwyt)) {
                yt.append(fwyt);
            }
        }
        returnMap.put("yt", yt.toString());
        returnMap.put("ybdcqzsh", StringUtil.getValue(powerSourceList.get(0), "POWERSOURCE_QSWH"));
        returnMap.put("gzwlx", "");
        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DY_DBSE"));

        if (StringUtils.isNotBlank((String) busInfo.get("DY_QLQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("DY_QLJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder();
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DY_QLQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DY_QLJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", "");
        }
        // 地役权情况
    }

    /**
     * 
     * 描述 两审审核意见
     * 
     * @author Roger Li
     * @created 2020年1月6日 上午9:55:16
     * @param returnMap
     * @param flowAllObj
     */
    @SuppressWarnings("unchecked")
    private void setFlowAllObj1(Map<String, Object> returnMap, Map<String, Object> flowAllObj) {
        String exeId = StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOWOBJ"), "EFLOW_EXEID");
        List<Map<String, Object>> tasks = this.findAuditTaskListByExeId(exeId);
        for (Map<String, Object> task : tasks) {
            String nodeName = StringUtil.getValue(task, "TASK_NODENAME");
            String assigner = StringUtil.getValue(task, "ASSIGNER_NAME");
            String opinion = StringUtil.getValue(task, "HANDLE_OPINION");
            String date = StringUtil.getValue(task, "END_TIME");
            if (StringUtils.isNotBlank(date)) {
                date = DateTimeUtil.getChinaDate(date);
            } else {
                date = "";
            }
            if (StringUtils.isBlank(assigner)) {
                assigner = StringUtil.getValue(task, "TEAM_NAMES");
            }
            if (nodeName.equals("登簿")) {
                returnMap.put("hd", opinion);
                returnMap.put("fzr", assigner);
                returnMap.put("hdsj", date);
            }
            if (nodeName.equals("预登簿")) {
                returnMap.put("sh", opinion);
                returnMap.put("scr1", assigner);
                returnMap.put("fhrq", date);
            }
        }
    }

    /**
     * 
     * 描述 不动产登记审批表三审数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月30日 下午5:52:05
     * @param args
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setThirdAudit(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> serviceItem = args.get("serviceItem");
        Map<String, Object> returnMap = args.get("returnMap");
        Map<String, Object> variables = args.get("requestMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        // 为标签设初值以防页面出现标签
        this.setDefaultValue(returnMap, StringUtil.getValue(execution, "EXE_ID"));
        // 设值流程主表相关信息 如收件人
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值流程相关信息 如环节审核人、审核意见等
        this.setFlowAllObj2(returnMap, flowAllObj);
        // 设值服务事项和流程表单相关信息 如服务事项名称
        this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo4(returnMap, busInfo, variables);

        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isNotBlank(sbh)) {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            if (WordRedrawUtil.encode(byteArrayOut, sbh)) {
                Map<String, Object> img = new HashMap<String, Object>();
                img.put("width", WordRedrawUtil.bdcQRCODEWIDTH);
                img.put("height", WordRedrawUtil.bdcQRCODEHEIGHT);
                img.put("type", WordRedrawUtil.pICEXT);
                img.put("content", byteArrayOut.toByteArray());
                returnMap.put("p_ewm", img);
            }
            try {
                byteArrayOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                String docPath = returnMap.get("TemplatePath").toString().split("\\.")[0];
                WordRedrawUtil.processNormalTable07(
                        "attachFiles//readtemplate//files//" + docPath + ".docx", returnMap);
            }
        }
    }

    /**
     * 
     * 描述 GYJsJFWZYDJ
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     * @param busInfo
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setBusRecordInfo4(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> variables) {
        // TODO 三审
        List<Map> powerPeopleList = JSON.parseArray(StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON"), Map.class);
        List<Map> powerSourceList = JSON.parseArray(StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON"), Map.class);

        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "ZDQLLX", StringUtil.getValue(busInfo, "ZD_QLLX") });
        returnMap.put("qllx", dictionary.get("DIC_NAME"));
        returnMap.put("djlx",
                dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(variables, "ZYDJ_TYPE"), "zydjlx"));
        // 申请人情况
        // 权利人/义务人
        // 权利人信息：多个权利人以逗号隔开
        StringBuilder qlrxm = new StringBuilder();
        StringBuilder sfzjzl = new StringBuilder();
        StringBuilder zjhm = new StringBuilder();
        Map<String, Object> powerPeopleInfo = null;
        String idType = null;
        Iterator iter = powerPeopleList.iterator();
        while (iter.hasNext()) {
            powerPeopleInfo = (Map<String, Object>) iter.next();
            qlrxm.append(StringUtil.getValue(powerPeopleInfo, "POWERPEOPLENAME"));
            idType = dictionaryService.findByDicCodeAndTypeCode(
                    StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEIDTYPE"), "DocumentType");
            sfzjzl.append(idType);
            zjhm.append(StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEIDNUM"));
            if (iter.hasNext()) {
                qlrxm.append(",");
                sfzjzl.append(",");
                zjhm.append(",");
            }
        }
        returnMap.put("qlrxm", qlrxm.toString());
        returnMap.put("sfzjzl", sfzjzl.toString());
        returnMap.put("zjhm", zjhm.toString());
        returnMap.put("txdz", StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEADDR"));
        returnMap.put("yb", StringUtil.getValue(powerPeopleInfo, "POWERPEOPLEPOSTCODE"));
        returnMap.put("fddbr", StringUtil.getValue(powerPeopleInfo, "POWLEGALNAME"));
        returnMap.put("lxdh1", "");
        returnMap.put("dlrxm", StringUtil.getValue(powerPeopleInfo, "POWAGENTNAME"));
        returnMap.put("lxdh2", "");
        returnMap.put("dljgmc", "");
        // 义务人信息
        StringBuilder ywrxm = new StringBuilder();
        StringBuilder sfzjzl1 = new StringBuilder();
        StringBuilder zjhm1 = new StringBuilder();
        Map<String, Object> powerSource = null;
        Iterator powerSourceIter = powerSourceList.iterator();
        while (powerSourceIter.hasNext()) {
            powerSource = (Map<String, Object>) powerSourceIter.next();
            ywrxm.append(StringUtil.getValue(powerSource, "POWERSOURCE_QLRMC"));
            sfzjzl1.append(StringUtil.getValue(powerSource, "POWERSOURCE_ZJLX"));
            zjhm1.append(StringUtil.getValue(powerSource, "POWERSOURCE_ZJHM"));
            if (powerSourceIter.hasNext()) {
                ywrxm.append(",");
                sfzjzl1.append(",");
                zjhm1.append(",");
            }
        }
        returnMap.put("ywrxm", ywrxm.toString());
        returnMap.put("sfzjzl1", sfzjzl1.toString());
        returnMap.put("zjhm1", zjhm1.toString());
        returnMap.put("txdz1", "");
        returnMap.put("yb1", "");
        returnMap.put("fddbr1", "");
        returnMap.put("lxdh3", "");
        returnMap.put("dlrxm1", "");
        returnMap.put("lxdh4", "");
        returnMap.put("dljgmc1", "");

        // 不动产情况
        returnMap.put("zl", StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        StringBuilder qlxz = new StringBuilder();
        // 字典qlxz
        qlxz.append(dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"))
                .append("/").append(StringUtil.getValue(busInfo, "FW_XZ"));
        returnMap.put("qlxz", qlxz.toString());
        // TODO 写死
        returnMap.put("bdclx", "土地、房产");
        String zdmj = StringUtil.getValue(busInfo, "ZD_MJ");
        String fwjzmj = StringUtil.getValue(busInfo, "FW_JZMJ");
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(zdmj)) {
            sb.append("宗地面积:").append(zdmj).append("平方米/");
            if (StringUtils.isNotBlank(fwjzmj)) {
                sb.append("房屋建筑面积:").append(fwjzmj).append("平方米");
            }
        }
        returnMap.put("mj", sb.toString());
        String zdyt = StringUtil.getValue(busInfo, "ZD_TDYT");
        String fwyt = StringUtil.getValue(busInfo, "FW_GHYT");
        StringBuilder yt = new StringBuilder();
        if (StringUtils.isNotBlank(zdyt)) {
            yt.append(zdyt).append("/");
            if (StringUtils.isNotBlank(fwyt)) {
                yt.append(fwyt);
            }
        }
        returnMap.put("yt", yt.toString());
        returnMap.put("ybdcqzsh", StringUtil.getValue(powerSourceList.get(0), "POWERSOURCE_QSWH"));
        returnMap.put("gzwlx", "");

        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DY_DBSE"));
        if (StringUtils.isNotBlank((String) busInfo.get("DY_QLQSSJ"))
                && StringUtils.isNotBlank((String) busInfo.get("DY_QLJSSJ"))) {
            StringBuilder zwlxqx = new StringBuilder();
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DY_QLQSSJ"))).append("起 ");
            zwlxqx.append(DateTimeUtil.getChinaDate(StringUtil.getValue(busInfo, "DY_QLJSSJ"))).append("止");
            returnMap.put("zwlxqx", zwlxqx.toString());
        } else {
            returnMap.put("zwlxqx", "");
        }
        // 地役权情况
    }

    /**
     * 
     * 描述 三审审核意见
     * 
     * @author Roger Li
     * @created 2020年1月6日 上午9:55:16
     * @param returnMap
     * @param flowAllObj
     */
    @SuppressWarnings("unchecked")
    private void setFlowAllObj2(Map<String, Object> returnMap, Map<String, Object> flowAllObj) {
        String exeId = StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOWOBJ"), "EFLOW_EXEID");
        List<Map<String, Object>> tasks = this.findAuditTaskListByExeId(exeId);
        for (Map<String, Object> task : tasks) {
            String nodeName = StringUtil.getValue(task, "TASK_NODENAME");
            String assigner = StringUtil.getValue(task, "ASSIGNER_NAME");
            String opinion = StringUtil.getValue(task, "HANDLE_OPINION");
            String date = StringUtil.getValue(task, "END_TIME");
            if (StringUtils.isNotBlank(date)) {
                date = DateTimeUtil.getChinaDate(date);
            } else {
                date = "";
            }
            if (StringUtils.isBlank(assigner)) {
                assigner = StringUtil.getValue(task, "TEAM_NAMES");
            }
            if (nodeName.equals("登簿")) {
                returnMap.put("hd", opinion);
                returnMap.put("fzr", assigner);
                returnMap.put("hdsj", date);
            }
            if (nodeName.equals("预登簿")) {
                returnMap.put("sh", opinion);
                returnMap.put("scr1", assigner);
                returnMap.put("fhrq", date);
            }
            if (nodeName.equals("初审")) {
                returnMap.put("cs", opinion);
                returnMap.put("scr", assigner);
                returnMap.put("csrq", date);
            }
        }
    }

    /**
     * 
     * 描述 打印抵押物明细_审批表数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月31日 下午2:32:19
     * @param args
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setDYMXAudit(Map<String, Map<String, Object>> args) {
        // TODO 抵押物明细_审批表
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> returnMap = args.get("returnMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        int seq = 1;
        List<Map> powerSourceList = JSON.parseArray(StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON"), Map.class);
        Map<String, Object> powerSource = powerSourceList.get(0);

        List<List> llistList = new ArrayList<List>();
        List rowData = new ArrayList<>();

        rowData.add(seq);
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "ESTATE_NUM"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(powerSource, "POWERSOURCE_QSWH"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "FW_ZL"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "ZD_MJ"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "ZD_TDYT"), " "));
        // 参考字典bdclx
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" }, new Object[] { "bdclx",
                        StringUtil.getValue((Map<String, Object>) flowAllObj.get("EFLOW_FLOWDEF"), "DEF_ID") });
        returnMap.put("bdclx", StringUtil.getValue(dictionary, "DIC_NAME"));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(dictionary, "DIC_NAME"), "土地、房产"));

        llistList.add(rowData);
        returnMap.put("table", llistList);
        if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
            WordRedrawUtil.processTableList("attachFiles//readtemplate//files//" + returnMap.get("TemplatePath"),
                    returnMap);
        }
    }

    /**
     * 
     * 描述 打印抵押物明细_在建工程数据回填方法
     * 
     * @author Roger Li
     * @created 2019年12月31日 下午2:32:19
     * @param args
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setDYMXBuilding(Map<String, Map<String, Object>> args) {
        // TODO 抵押物明细_在建工程
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> returnMap = args.get("returnMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        int seq = 1;
        List<List> llistList = new ArrayList<List>();
        List rowData = new ArrayList<>();
        rowData.add(seq);
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "FW_ZH"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "FW_HH"), " "));
        rowData.add(StringUtils.defaultIfEmpty(StringUtil.getValue(busInfo, "ESTATE_NUM"), " "));

        llistList.add(rowData);
        returnMap.put("table", llistList);
        if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
            WordRedrawUtil.processTableList("attachFiles//readtemplate//files//" + returnMap.get("TemplatePath"),
                    returnMap);
        }
    }

    /**
     * 
     * 描述 根据实例Id获取task列表
     * 
     * @author Roger Li
     * @created 2020年1月7日 下午6:42:24
     * @param exeid
     * @return
     * @see net.evecom.platform.bdc.service.BdcGyjsjfwzydjService#findAuditTaskListByExeId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAuditTaskListByExeId(String exeid) {
        StringBuffer sql = new StringBuffer(
                "SELECT T.TASK_NODENAME,T.ASSIGNER_NAME,T.TEAM_NAMES,T.END_TIME,T.HANDLE_OPINION FROM JBPM6_TASK T ");
        sql.append("WHERE T.EXE_ID=? AND T.STEP_SEQ!=0 order by T.STEP_SEQ asc");
        return dao.findBySql(sql.toString(), new Object[] { exeid }, null);
    }

    /**
     * 描述:设置国有建设及房屋转移登记通用数据
     *
     * @author Madison You
     * @created 2020/4/30 9:09:00
     * @param args 参数
     * @return
     */
    @Override
    public void setGyjsjfwzydjtyData(Map<String, Map<String, Object>> args) {
        /*获取args数据*/
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> returnMap = args.get("returnMap");

        /*获取业务数据*/
        Map<String, Object> busInfo = bdcSpbConfig.bdcGetBusInfo(flowAllObj);

        /*初始化审批表字段*/
        bdcSpbConfig.bdcInitSpbVariables(returnMap);

        /*设置国有建设及房屋转移登记通用业务数据*/
        try{
            this.setGyjsjfwzydjtyBusInfo(returnMap, busInfo, execution);
        } catch (Exception e) {
            log.error("国有建设及房屋转移登记数据回填出错");
            log.error(e.getMessage(), e);
        }

        /*模板字符串替换*/
        String tableType = StringUtil.getValue(returnMap, "tableType");
        String docPath = returnMap.get("TemplatePath").toString().split("\\.")[0];
        if (StringUtils.isNotEmpty(tableType) && tableType.equals("1")) {
            String bdcdyh = StringUtil.getValue(returnMap, "bdcdyh");
            bdcSpbConfig.createQrCode(returnMap, bdcdyh);
            docPath = "BDCQLCSQB";
        } else {
            String sbh = StringUtil.getValue(returnMap, "xmsqbh");
            bdcSpbConfig.createQrCode(returnMap, sbh);
            if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
                String zydjType = StringUtil.getValue(busInfo,"ZYDJ_TYPE");
                String isFinishtax = StringUtil.getValue(busInfo, "IS_FINISHTAX");
                if (zydjType.equals("4")) {
                    /*税费联办业务固定两审表格加审批框*/
                    docPath = "GYJSJFWZYDJTYSPBLSS";
                } else if (zydjType.equals("3")) {
                    /*权利限制没框*/
                    docPath = "GYJSJFWZYDJTYSPBYS";
                } else if (zydjType.equals("5")) {
                    /*抵押联办业务专用审批表*/
                    if (isFinishtax.equals("0")) {
                        docPath = "GYJSJFWZYDJTYSPBDYYSS";
                    } else {
                        docPath = "GYJSJFWZYDJTYSPBDYYS";
                    }
                } else {
                    /*一审表格分户，其他判断是否完税，否加框，权利限制没框*/
                    if (isFinishtax.equals("0")) {
                        docPath = "GYJSJFWZYDJTYSPBYSS";
                    } else {
                        docPath = "GYJSJFWZYDJTYSPBYS";
                    }
                }
            }
        }
        //水电气联办事项
        if("345071904QR01404".equals(busInfo.get("ITEM_CODE"))){
            if("BDCQLCSQB".equals(docPath)){
                docPath = "BDCQLCSQB(SDQG)";
            }else{
                docPath = "GYJSJFWZYDJTYSPBYS(SDQG)";
            }
        }
        this.setSdqInfo(returnMap, String.valueOf(execution.get("EXE_ID")));
        if (StringUtils.isNotBlank(StringUtil.getValue(returnMap, "TemplatePath"))) {
            WordRedrawUtil.processNormalTable07(
                    "attachFiles//readtemplate//files//" + docPath + ".docx", returnMap);
        }
    }
    
    /**
     * 描述:获取打印水力，电力，燃气，广电数据
     *
     * @author Madison You
     * @created 2020/6/21 14:10:00
     * @param
     * @return
     */
    public void setSdqInfo(Map<String, Object> returnMap,String exe_id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT X.* FROM JBPM6_EXECUTION T LEFT JOIN T_BDCQLC_GYJSJFWZYDJ X ON T.BUS_RECORDID = X.YW_ID where T.EXE_ID = ? ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] {exe_id}, null);
        if(list != null && list.size() >= 1){
            Map<String, Object> map = list.get(0);
            returnMap.put("waternum", "是    缴费户号:" + map.get("WATER_NUM"));
            returnMap.put("pownum", "是    缴费户号:" +map.get("POW_NUM"));
            returnMap.put("gasnum", "是    缴费户号:" +map.get("GAS_NUM"));
            returnMap.put("svanum", "是    缴费户号:" +map.get("SVA_NUM"));
            if(map.get("WATER_NUM") == null){
                returnMap.put("waternum", "否");
            }
            if(map.get("POW_NUM") == null){
                returnMap.put("pownum", "否");
            }
            if(map.get("GAS_NUM") == null){
                returnMap.put("gasnum", "否");
            }
            if(map.get("SVA_NUM") == null){
                returnMap.put("svanum", "否");
            }
        }
    }
    

    /**
     * 描述:获取宗地坐落数据
     *
     * @author Madison You
     * @created 2020/6/21 14:10:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> finZdzlData(String value , String typeCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT T.DIC_CODE VALUE , T.DIC_NAME TEXT FROM T_MSJW_SYSTEM_DICTIONARY T ");
        sql.append(" WHERE TYPE_CODE = '").append(typeCode).append("'AND DIC_CODE LIKE '%").append(value).append("%'");
        sql.append(" ORDER BY DIC_SN ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }

    /**
     * 描述:查询已在办理此不动产单元号的业务
     *
     * @author Madison You
     * @created 2020/6/28 16:35:00
     * @param
     * @return
     */
    @Override
    public List<String> findIsDealBdcdyh(String bdcdyh) {
        List<String> bdcdyhList = new ArrayList<>();
        /*查询字典表中设置的不动产业务及不动产单元号字段*/
        List<Map<String, Object>> dicList = dictionaryService.findByTypeCode("BDCDYHCXPD");
        for (Map<String, Object> dicMap : dicList) {
            String busTableName = (String) dicMap.get("DIC_NAME");
            String busTableColumn = (String) dicMap.get("DIC_CODE");
            String tablePrimaryKeyName = dao.getPrimaryKeyName(busTableName).get(0).toString();
            StringBuffer sql = new StringBuffer();
            sql.append(" SELECT DISTINCT C.ITEM_NAME FROM JBPM6_EXECUTION A LEFT JOIN ").append(busTableName);
            sql.append(" B ON A.BUS_RECORDID = B.").append(tablePrimaryKeyName);
            sql.append(" LEFT JOIN T_WSBS_SERVICEITEM C ON A.ITEM_CODE = C.ITEM_CODE ");
            sql.append(" WHERE RUN_STATUS = 1 AND ").append(busTableColumn).append(" = ? ");
            List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{bdcdyh}, null);
            if (list != null && !list.isEmpty()) {
                bdcdyhList.add(list.get(0).get("ITEM_NAME").toString());
            }
        }
        return bdcdyhList;
    }

    /**
     * 描述:获取不动产银行列表
     *
     * @author Madison You
     * @created 2020/8/18 9:20:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBdcBankList(HttpServletRequest request) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" SELECT BANK_ID,BANK_CODE DIC_CODE,BANK_NAME DIC_NAME,BANK_LEGAL_NAME,BANK_CONTECT_PHONE, ");
        sql.append(" BANK_LEGAL_PHONE,BANK_LEGAL_CARD,BANK_CONTECT_CARD, ");
        sql.append(" BANK_CONTECT_NAME,BANK_ADDRESS FROM T_BDCQLC_BANK ORDER BY CREATE_TIME ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:回填不动产签章材料数据
     *
     * @author Madison You
     * @created 2020/8/18 14:43:00
     * @param
     * @return
     */
    @Override
    public void initBdcFieldValue(Map<String, Object> returnMap, Map<String, Object> flowVars) {
        setGyjsjfwzydjtyBusInfo(returnMap, flowVars, flowVars);
        returnMap.put("xmsqbh", StringUtil.getValue(flowVars, "EFLOW_EXEID"));
        Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowVars.get("EFLOW_EXEID") });
        if(flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] { flowVars.get("EFLOW_EXEID")});
        }
        returnMap.put("xmsqsj", DateTimeUtil.getChinaDate(StringUtil.getValue(flowExe, "CREATE_TIME")));
        String bdcdyh = StringUtil.getValue(returnMap, "bdcdyh");
        bdcSpbConfig.createQrCode(returnMap, bdcdyh);
    }
    /**
     * 描述:回填不动产通用模板签章材料数据
     *
     * @author Madison You
     * @created 2020/8/18 14:43:00
     * @param
     * @return
     */
    @Override
    public void initBdcGeneralFieldValue(Map<String, Object> returnMap, Map<String, Object> flowVars) {
        //设置业务数据
        setBdcGeneralBusInfo(returnMap, flowVars, flowVars);
        returnMap.put("xmsqbh", StringUtil.getValue(flowVars, "EFLOW_EXEID"));
        Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { flowVars.get("EFLOW_EXEID") });
        if(flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] { flowVars.get("EFLOW_EXEID")});
        }
        returnMap.put("xmsqsj", DateTimeUtil.getChinaDate(StringUtil.getValue(flowExe, "CREATE_TIME")));
        String bdcdyh = StringUtil.getValue(returnMap, "bdcdyh");
        bdcSpbConfig.createQrCode(returnMap, bdcdyh);
    }

    /**
     * 描述:保存国有转移的额外需要自动生成的字段
     *
     * @author Madison You
     * @created 2020/10/22 14:19:00
     * @param
     * @return
     */
    @Override
    public Map<String,Object> saveGyjsjfwzyElseField(Map<String, Object> flowVars) {
        String EFLOW_EXEID = StringUtil.getValue(flowVars, "EFLOW_EXEID");
        String EFLOW_BUSRECORDID = StringUtil.getValue(flowVars, "EFLOW_BUSRECORDID");
        String EFLOW_BUSTABLENAME = StringUtil.getValue(flowVars, "EFLOW_BUSTABLENAME");
        //国有转移6个事项虚拟主表替换真实表名称
        if(EFLOW_BUSTABLENAME.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            EFLOW_BUSTABLENAME = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String ISQCWB = StringUtil.getValue(flowVars, "ISQCWB");
        String SQFS = StringUtil.getString(flowVars.get("SQFS"));// 受理方式（1：网上申请 触发签章）
        if (StringUtils.isNotEmpty(EFLOW_EXEID) &&  "1".equals(SQFS)){
            Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { EFLOW_EXEID });
            if (flowExe == null) {
                flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] {EFLOW_EXEID});
            }
            String pttSqfs = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
            if (("1".equals(ISQCWB)) || (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs))) {
                /* 房屋附记自动填写 */
                String ZYDJ_TYPE = StringUtil.getValue(flowVars, "ZYDJ_TYPE");
                String ZD_MJ = StringUtil.getValue(flowVars, "ZD_MJ");
                if (StringUtils.isNotEmpty(ZYDJ_TYPE) && ZYDJ_TYPE.equals("1")) {
                    String POWERSOURCEINFO_JSON = StringUtil.getValue(flowVars, "POWERSOURCEINFO_JSON");
                    StringBuffer text = new StringBuffer();
                    text.append("业务编号：").append(EFLOW_EXEID.substring(4)).append("；\r")
                            .append("不动产取得方式：国有建设用地使用权及房屋所有权转移登记（分户）；\r");
                    StringBuffer POWERSOURCE_QSWH = new StringBuffer();
                    if (StringUtils.isNotEmpty(POWERSOURCEINFO_JSON)) {
                        List<Map> qsList = JSONArray.parseArray(POWERSOURCEINFO_JSON, Map.class);
                        for (Map<String, Object> qsMap : qsList) {
                            POWERSOURCE_QSWH.append(StringUtil.getValue(qsMap, "POWERSOURCE_QSWH")).append(",");
                        }
                    }
                    text.append("原权证号：");
                    if (StringUtils.isNotEmpty(POWERSOURCE_QSWH)) {
                        text.append(POWERSOURCE_QSWH.substring(0, POWERSOURCE_QSWH.length() - 1)).append("；\r");
                    }
                    text.append("该宗地为共用宗，土地使用权为全体业主共有，共有使用权面积为").append(ZD_MJ).append("平方米");
                    HashMap<String, Object> variables = new HashMap<>();
                    /* 填写房屋附记 */
                    variables.put("FW_FJ", text);
                    /* 填写核准意见 */
                    String username = dictionaryService.findByDicCodeAndTypeCode("dbr", "dslzdtz");
                    String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                    variables.put("HZ_OPINION_CONTENT", "准予登记");// 审核意见-核定意见
                    variables.put("HZ_OPINION_NAME", username);// 审核意见-填写人
                    variables.put("HZ_OPINION_TIME", dateStr);// 审核意见-填写时间
                    dao.saveOrUpdate(variables, EFLOW_BUSTABLENAME, EFLOW_BUSRECORDID);
                }
            }
        }
        return flowVars;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/1 14:35:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getTaxRelatedFileList(String fileIds) {
        String valueArray = StringUtil.getValueArray(fileIds);
        return dao.findBySql("SELECT * FROM T_MSJW_SYSTEM_FILEATTACH WHERE FILE_ID IN " + valueArray, null, null);
    }

    /**
     * 描述:设置国有建设及房屋转移登记通用业务数据
     *
     * @author Madison You
     * @created 2020/4/30 9:18:00
     * @param
     * @return
     */
    private void setGyjsjfwzydjtyBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                         Map<String, Object> execution) {
        returnMap.put("qllx", "国有建设用地使用权/房屋所有权");
        returnMap.put("djlx", "转移登记");
        returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(),"yyyy年MM月dd日"));
        if (StringUtil.getValue(execution, "SQFS").equals("1")) {
            returnMap.put("sjr", "");
        } else {
            returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
        }
        String sbh = StringUtil.getValue(returnMap, "xmsqbh");
        if (StringUtils.isEmpty(sbh)) {
            returnMap.put("xmsqbh", StringUtil.getValue(busInfo, "EFLOW_EXEID"));
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
            StringBuffer lxdh1 = new StringBuffer();
            StringBuffer lxdh2 = new StringBuffer();
            List<Map<String,Object>> sqrInfoList = new ArrayList<>();
            
            List<Object> tccns = new LinkedList<Object>();//套次承诺书

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

                Map<String,Object> sqlInfoMap = new HashMap<>();
                sqlInfoMap.put("sqrxm", StringUtil.getValue(piMap,"POWERPEOPLENAME"));
                if (powerpeopleidtype.equals("SF")) {
                    sqlInfoMap.put("sqrzjlx", "IDCard");
                } else if (powerpeopleidtype.equals("HZ")) {
                    sqlInfoMap.put("sqrzjlx", "Passport");
                } else if (powerpeopleidtype.equals("GATX")) {
                    sqlInfoMap.put("sqrzjlx", "HMPass");
                } else {
                    sqlInfoMap.put("sqrzjlx", "Other");
                }
                sqlInfoMap.put("sqrzjhm", StringUtil.getValue(piMap,"POWERPEOPLEIDNUM"));
                sqlInfoMap.put("sqrsjhm", StringUtil.getValue(piMap,"POWERPEOPLEMOBILE"));
                sqrInfoList.add(sqlInfoMap);
                
                //套次承诺书
                String cns = "  本人承诺现购买的房产作为家庭住房（成员范围包括购房人、配偶以及未成年子女），"
                        + "符合如下条件（请选择）：□家庭唯一住房；□家庭第二套住房；□家庭第三套及第三套以上住房。";
                if("0".equals(StringUtil.getValue(piMap, "POWPERPEOPLECNS"))){
                    cns = "  本人（"+StringUtil.getValue(piMap, "POWERPEOPLENAME") +"）承诺现购买的房产作为家庭住房（成员范围包括购房人、配偶以及未成年子女），"
                            + "符合如下条件（请选择）：☑家庭唯一住房；□家庭第二套住房；□家庭第三套及第三套以上住房。";
                }else if("1".equals(StringUtil.getValue(piMap, "POWPERPEOPLECNS"))){
                    cns = "  本人（"+StringUtil.getValue(piMap, "POWERPEOPLENAME") +"）承诺现购买的房产作为家庭住房（成员范围包括购房人、配偶以及未成年子女），"
                            + "符合如下条件（请选择）：□家庭唯一住房；☑家庭第二套住房；□家庭第三套及第三套以上住房。";
                }else if("2".equals(StringUtil.getValue(piMap, "POWPERPEOPLECNS"))){
                    cns = "  本人（"+StringUtil.getValue(piMap, "POWERPEOPLENAME") +"）承诺现购买的房产作为家庭住房（成员范围包括购房人、配偶以及未成年子女），"
                            + "符合如下条件（请选择）：□家庭唯一住房；□家庭第二套住房；☑家庭第三套及第三套以上住房。";
                }
                tccns.add(cns);
                
            }
            returnMap.put("BDC_TCCNS", tccns);
            returnMap.put("sqrInfoList", sqrInfoList);
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

        initYhdjsqrFieldForm(returnMap, busInfo, execution);

        /*义务人信息JSON*/
        String qsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        StringBuffer ywrxm = new StringBuffer();
        StringBuffer sfzjzl1 = new StringBuffer();
        StringBuffer zjhm1 = new StringBuffer();
        StringBuffer lxdh1 = new StringBuffer();
        StringBuffer fddbr1 = new StringBuffer();
        StringBuffer lxdh3 = new StringBuffer();
        StringBuffer dlrxm1 = new StringBuffer();
        StringBuffer lxdh4 = new StringBuffer();
        /*原不动产权证书号*/
        StringBuffer ybdcqzsh = new StringBuffer();
        StringBuffer dljgmc1 = new StringBuffer();
        if (StringUtils.isNotEmpty(qsJson)) {
            List<Map> qsList = JSONArray.parseArray(qsJson, Map.class);
            for (Map<String, Object> qsMap : qsList) {
                ywrxm.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLRMC")).append(",");
                sfzjzl1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJLX")).append(",");
                zjhm1.append(StringUtil.getValue(qsMap, "POWERSOURCE_ZJHM")).append(",");
                lxdh1.append(StringUtil.getValue(qsMap, "POWERSOURCE_QLR_TEL")).append(",");
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
            returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
            returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
            returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
            returnMap.put("ybdcqzsh", bdcSpbConfig.bdcStringOutFormate(ybdcqzsh));
            returnMap.put("dljgmc1", bdcSpbConfig.bdcStringOutFormate(dljgmc1));
        }
        /*抵押人信息*/
        String DY_DYQR = StringUtil.getValue(busInfo, "DY_DYQR");
        returnMap.put("dyqrxm", StringUtil.getValue(busInfo,"DY_DYQR"));
        if (StringUtils.isNotEmpty(DY_DYQR)) {
            Map<String,Object> bankMap = dao.getByJdbc("T_BDCQLC_BANK", new String[]{"BANK_NAME"},
                    new Object[]{DY_DYQR});
            if (bankMap != null) {
                returnMap.put("yhmc", StringUtil.getValue(bankMap,"BANK_NAME"));
                returnMap.put("yhdm", StringUtil.getValue(bankMap,"BANK_CODE"));
                returnMap.put("yhzzlx", "SOCNO");
                returnMap.put("yhlxr", StringUtil.getValue(bankMap,"BANK_CONTECT_NAME"));
                returnMap.put("yhlxdh", StringUtil.getValue(bankMap,"BANK_CONTECT_PHONE"));
                returnMap.put("yhlxzjhm", StringUtil.getValue(bankMap,"BANK_CONTECT_CARD"));
                returnMap.put("yhlxzjlx", "IDCard");
                returnMap.put("yhfr", StringUtil.getValue(bankMap,"BANK_LEGAL_NAME"));
                returnMap.put("yhfrsjh", StringUtil.getValue(bankMap,"BANK_LEGAL_PHONE"));
                returnMap.put("yhfrzjhm", StringUtil.getValue(bankMap,"BANK_LEGAL_CARD"));
                returnMap.put("yhfrzjlx", "IDCard");
                returnMap.put("yhlxdz", StringUtil.getValue(bankMap,"BANK_ADDRESS"));
            }
        }
        returnMap.put("sfzjzl2", StringUtil.getValue(busInfo,"DY_DYQRZJLX"));
        returnMap.put("zjhm2", StringUtil.getValue(busInfo,"DY_DYQRZJHM"));
        returnMap.put("dyrxm", StringUtil.getValue(busInfo,"DY_DYR"));
        returnMap.put("sfzjzl3", StringUtil.getValue(busInfo,"DY_DYRZJLX1"));
        returnMap.put("zjhm3", StringUtil.getValue(busInfo,"DY_DYRZJHM1"));
        returnMap.put("dybdcdjzmh", StringUtil.getValue(busInfo,"DY_BDCDJZMH"));

        /*债务起止时间*/
        /*债务履行期限*/
        String DY_QLQSSJ = StringUtil.getValue(busInfo, "DY_QLQSSJ");
        String DY_QLJSSJ = StringUtil.getValue(busInfo, "DY_QLJSSJ");
        if (!DY_QLQSSJ.equals("") && !DY_QLJSSJ.equals("")) {
            String zwBegin = bdcSpbConfig.bdcDateFormat(DY_QLQSSJ,"yyyy-MM-dd","yyyy年MM月dd日");
            String zwEnd = bdcSpbConfig.bdcDateFormat(DY_QLJSSJ,"yyyy-MM-dd","yyyy年MM月dd日");
            returnMap.put("zwqssj", zwBegin);
            returnMap.put("zwjssj", zwEnd);
            returnMap.put("zwlxqx", zwBegin + "起" + zwEnd + "止");
        }
        returnMap.put("zzqhtdkqx", StringUtil.getValue(busInfo,"DY_ZZQHTDKQX"));
        /*被担保债券数额*/
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo,"DY_DBSE"));
        /*在建建筑物抵押范围*/
        returnMap.put("zjjzwdyfw", "");
        /*不动产情况*/
        returnMap.put("zl", StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        // 字典qlxz
        returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(
                StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
        returnMap.put("bdclx", "土地、房产");
        /*面积*/
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²").append("/");
        mj.append("房屋建筑面积：").append(StringUtil.getValue(busInfo, "FW_JZMJ")).append("m²");
        returnMap.put("mj", mj);
        returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM") + "/" + StringUtil.getValue(busInfo,"FW_YTSM"));
        /*登记原因*/
        returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
        /*登记原因证明文件*/
        bdcSpbConfig.getMaterNameList(returnMap);
        // 抵押情况
        returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DY_DBSE"));
        /*问询记录*/
        bdcSpbConfig.fillWxjlForm(returnMap, busInfo);
        /*填写初审核定意见'*/
        bdcSpbConfig.getbdcDjshOpinion(busInfo,returnMap);
        
        //税费联办前台申报在线签章补充信息
        //纳税人承诺信息
     /*   returnMap.put("nsrxm", StringUtil.getValue(busInfo, "NSR_XM"));
        returnMap.put("nsrzjlx",dictionaryService.getDicNames("DocumentType", 
                StringUtil.getValue(busInfo, "NSR_ZJLX")));
        returnMap.put("nsrsfh", StringUtil.getValue(busInfo, "NSR_ZJHM"));
        returnMap.put("nsrpoxm", StringUtil.getValue(busInfo, "NSR_POXM"));
        returnMap.put("nsrposfh", StringUtil.getValue(busInfo, "NSR_POZJHM"));
        List<Object> nsrChildList = new LinkedList<Object>();
        if(StringUtils.isNotEmpty(StringUtil.getString(busInfo.get("NSR_CHILDJSON")))){
            List<Map> childJson = 
                    JSON.parseArray(StringUtil.getString(busInfo.get("NSR_CHILDJSON")),Map.class);
            for (int i = 0; i < childJson.size(); i++) {
                Object child;
                child = "   未成年子女"+(i+1)+"姓名："+StringUtil.getValue(childJson.get(i),"NSR_ZNXM")+
                        "身份证件号码："+StringUtil.getValue(childJson.get(i),"NSR_ZNZJHM");
                nsrChildList.add(child.toString());
            }
        }
        if(nsrChildList.size()>0){
            returnMap.put("nsrChildList", nsrChildList);
        }
        returnMap.put("nsrfcdz", StringUtil.getValue(busInfo, "NSR_FCDZ"));
        selectYesOrNo("nsrzfqk1","NSR_ZFQK","0",busInfo,returnMap);
        selectYesOrNo("nsrzfqk2","NSR_ZFQK","1",busInfo,returnMap);
        selectYesOrNo("nsrzfqk3","NSR_ZFQK","2",busInfo,returnMap);
        selectYesOrNo("nsrzfqk4","NSR_ZFQK","3",busInfo,returnMap);
        selectYesOrNo("nsrzfqk5","NSR_ZFQK","4",busInfo,returnMap);
        selectYesOrNo("nsrzfqk6","NSR_ZFQK","5",busInfo,returnMap);*/
        returnMap.put("sqsj", DateTimeUtil.getStrOfDate(new Date(),"yyyy年MM月dd日"));
        selectYesOrNo("ISPOWTRANSFER_1","IS_POWTRANSFER","1",busInfo,returnMap);
        selectYesOrNo("ISPOWTRANSFER_2","IS_POWTRANSFER","0",busInfo,returnMap);
        returnMap.put("POW_NUM", StringUtil.getValue(busInfo, "POW_NUM"));
        selectYesOrNo("ISWATERTRANSFER_1","IS_WATERTRANSFER","1",busInfo,returnMap);
        selectYesOrNo("ISWATERTRANSFER_2","IS_WATERTRANSFER","0",busInfo,returnMap);
        returnMap.put("WATER_NUM", StringUtil.getValue(busInfo, "WATER_NUM"));
        selectYesOrNo("ISGASTRANSFER_1","IS_GASTRANSFER","1",busInfo,returnMap);
        selectYesOrNo("ISGASTRANSFER_2","IS_GASTRANSFER","0",busInfo,returnMap);
        returnMap.put("GAS_NUM", StringUtil.getValue(busInfo, "GAS_NUM"));
        selectYesOrNo("ISSVATRANSFER_1","IS_SVATRANSFER","1",busInfo,returnMap);
        selectYesOrNo("ISSVATRANSFER_2","IS_SVATRANSFER","0",busInfo,returnMap);
        returnMap.put("SVA_NUM", StringUtil.getValue(busInfo, "SVA_NUM"));
        
        selectYesOrNo("FBCZ1","TAKECARD_TYPE","0",busInfo,returnMap);
        selectYesOrNo("FBCZ2","TAKECARD_TYPE","1",busInfo,returnMap);
        
        //买方承诺信息JSON
        List<Object> buycnList = new LinkedList<Object>();//买方承诺信息
        String buyCnJson = StringUtil.getValue(busInfo, "BUYCN_JSON");
        if (StringUtils.isNotEmpty(buyCnJson)  && !"[]".equals(buyCnJson)) {
            List<Map> buyList = JSONArray.parseArray(buyCnJson, Map.class);
            for (Map<String, Object> buy : buyList) {
                //买方信息
                StringBuffer buyXx = new StringBuffer();
                buyXx.append("  买方").append(StringUtil.getString(buy.get("MFXM")))
                .append("承诺现购买的房产作为家庭住房（成员范围包括购房人、配偶以及未成年子女），符合如下条件（请选择）：");
                if("0".equals(StringUtil.getValue(buy, "MFZFQK"))){
                    buyXx .append("☑家庭唯一住房；□家庭第二套住房；□家庭第三套及第三套以上住房。");
                }else if("1".equals(StringUtil.getValue(buy, "MFZFQK"))){
                    buyXx .append("□家庭唯一住房；☑家庭第二套住房；□家庭第三套及第三套以上住房。");
                }else if("2".equals(StringUtil.getValue(buy, "MFZFQK"))){
                    buyXx .append("□家庭唯一住房；□家庭第二套住房；☑家庭第三套及第三套以上住房。");
                }
                String hyzk = dictionaryService.getDicNames("hyzk",
                        StringUtil.getString(buy.get("MFHYZK")));
                if(StringUtils.isNotEmpty(hyzk)){
                    buyXx.append("婚姻状况:").append(hyzk).append("，"); 
                }
                //判断是否有无家庭成员信息
                String buyChildJson = StringUtil.getValue(buy, "BUYCHILD_JSON");//未成年子女信息
                if(StringUtils.isNotEmpty(StringUtil.getString(buy.get("MFPOXM"))) 
                        || (StringUtils.isNotEmpty(buyChildJson) && !"[]".equals(buyChildJson))){
                    buyXx.append(" 家庭成员信息如下：");
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(buy.get("MFPOXM")))){
                    buyXx.append(" 配偶姓名：").append(StringUtil.getString(buy.get("MFPOXM"))); 
                    buyXx.append(" 身份证件号码：").append(StringUtil.getString(buy.get("MFPOZJHM"))).append(";"); 
                }
                if(StringUtils.isNotEmpty(buyChildJson)){
                    List<Map> buyChildList = JSONArray.parseArray(buyChildJson, Map.class);
                    if(buyChildList.size()>0){
                        for (Map buyChild : buyChildList) {
                            buyXx.append(" 未成年子女姓名：").append(StringUtil.getString(buyChild.get("MFCHILDXM"))); 
                            buyXx.append(" 身份证件号码：").append(StringUtil.getString(buyChild.get("MFCHILDZJHM")))
                            .append(";"); 
                        } 
                    }
                  
                }
                buycnList.add(buyXx.toString());
            }
        }
        returnMap.put("BUYLIST", buycnList);
        
        //卖方承诺信息JSON
        List<Object> sellcnList = new LinkedList<Object>();//卖方承诺信息
        String sellCnJson = StringUtil.getValue(busInfo, "SELLCN_JSON");
        if (StringUtils.isNotEmpty(sellCnJson) && !"[]".equals(sellCnJson)) {
            List<Map> sellList = JSONArray.parseArray(sellCnJson, Map.class);
            for (Map<String, Object> sell : sellList) {
                //卖方信息
                StringBuffer sellXx = new StringBuffer();
                sellXx.append("卖方").append(StringUtil.getString(sell.get("SELLXM")));
                String hyzk = dictionaryService.getDicNames("hyzk",
                        StringUtil.getString(sell.get("SELLHYZK")));
                if(StringUtils.isNotEmpty(hyzk)){
                    sellXx.append("婚姻状况:").append(hyzk).append("，"); 
                }
                
                //判断是否有无家庭成员信息
                String sellChildJson = StringUtil.getValue(sell, "SELLCHILD_JSON");//未成年子女信息
                if(StringUtils.isNotEmpty(StringUtil.getString(sell.get("SELLPOXM"))) 
                        || (StringUtils.isNotEmpty(sellChildJson) && !"[]".equals(sellChildJson))){
                    sellXx.append(" 承诺家庭成员信息如下：");
                }
                if(StringUtils.isNotEmpty(StringUtil.getString(sell.get("SELLPOXM")))){
                    sellXx.append(" 配偶姓名：").append(StringUtil.getString(sell.get("SELLPOXM"))); 
                    sellXx.append(" 身份证件号码：").append(StringUtil.getString(sell.get("SELLPOZJHM"))).append(";"); 
                }
                if(StringUtils.isNotEmpty(sellChildJson)){
                    List<Map> sellChildList = JSONArray.parseArray(sellChildJson, Map.class);
                    if(sellChildList.size()>0){
                        for (Map sellChild : sellChildList) {
                            sellXx.append(" 未成年子女姓名：").append(StringUtil.getString(sellChild.get("SELLCHILDXM"))); 
                            sellXx.append(" 身份证件号码：").append(StringUtil.getString(sellChild.get("SELLCHILDZJHM")))
                            .append(";");
                        }  
                    }
                }
                sellcnList.add(sellXx.toString());
            }
        }
        returnMap.put("SELLLIST", sellcnList);
        
        //不动产权属转移涉税补充信息填写确认单
        selectYesOrNo("isagree1","IS_AGREE","0",busInfo,returnMap);
        selectYesOrNo("isagree2","IS_AGREE","1",busInfo,returnMap);
        selectYesOrNo("isknow","IS_KNOW","0",busInfo,returnMap);
        selectYesOrNo("gfzm1","GF_ZM","0",busInfo,returnMap);
        selectYesOrNo("gfzm2","GF_ZM","1",busInfo,returnMap);
        selectYesOrNo("gfzm3","GF_ZM","2",busInfo,returnMap);
        selectYesOrNo("esfjy1","ESF_JY","0",busInfo,returnMap);
        selectYesOrNo("esfjy2","ESF_JY","1",busInfo,returnMap);
        selectYesOrNo("grsdszsfs1","GRSDS_ZSFS","0",busInfo,returnMap);
        selectYesOrNo("grsdszsfs2","GRSDS_ZSFS","1",busInfo,returnMap);
        selectYesOrNo("grsdsmz1","GRSDS_MZ","0",busInfo,returnMap);
        selectYesOrNo("grsdsmz2","GRSDS_MZ","1",busInfo,returnMap);
        selectYesOrNo("isswfs","IS_SFWS","0",busInfo,returnMap);
        //存量房评估补充信息
        returnMap.put("clffcdz", StringUtil.getValue(busInfo, "CLF_FCDZ"));
        selectYesOrNo("clfzxqk1","CLF_ZXQK","0",busInfo,returnMap);
        selectYesOrNo("clfzxqk2","CLF_ZXQK","1",busInfo,returnMap);
        selectYesOrNo("clfzxqk3","CLF_ZXQK","2",busInfo,returnMap);
        selectYesOrNo("clfzxqk4","CLF_ZXQK","3",busInfo,returnMap);
        selectYesOrNo("clfcgqk1","CLF_CGQK","0",busInfo,returnMap);
        selectYesOrNo("clfcgqk2","CLF_CGQK","1",busInfo,returnMap);
        selectYesOrNo("clfcgqk3","CLF_CGQK","2",busInfo,returnMap);
        selectYesOrNo("clfcgqk4","CLF_CGQK","3",busInfo,returnMap);
        selectYesOrNo("clfcgqk5","CLF_CGQK","4",busInfo,returnMap);
        selectYesOrNo("clfdtqk1","CLF_DTQK","0",busInfo,returnMap);
        selectYesOrNo("clfdtqk2","CLF_DTQK","1",busInfo,returnMap);
        selectYesOrNo("clfyjqk1","CLF_YJQK","0",busInfo,returnMap);
        selectYesOrNo("clfyjqk2","CLF_YJQK","1",busInfo,returnMap);
        selectYesOrNo("clfljzt1","CLF_LJZT","0",busInfo,returnMap);
        selectYesOrNo("clfljzt2","CLF_LJZT","1",busInfo,returnMap);
        selectYesOrNo("clfljzt3","CLF_LJZT","2",busInfo,returnMap);
        selectYesOrNo("clfljzt4","CLF_LJZT","3",busInfo,returnMap);
        /*家庭唯一生活用用房-房屋权利人信息JSON*/
        String fwJson = StringUtil.getValue(busInfo, "FWPOWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(fwJson)) {
            List<Map<String,Object>> fwqlrInfoList = new LinkedList<>();
            List<Map> fwList = JSONArray.parseArray(fwJson, Map.class);
            for (Map<String, Object> fwMap : fwList) {
                fwMap.put("sqrxm", StringUtil.getValue(fwMap,"WYYF_QLRXM"));
                fwMap.put("sqrzjlx", zjlxFormmat(StringUtil.getValue(fwMap,"WYYF_QLRZJLX")));
                fwMap.put("sqrzjhm", StringUtil.getValue(fwMap,"WYYF_QLRZJHM"));
                fwMap.put("sqrsjhm", StringUtil.getValue(fwMap,"WYYF_LXDH"));
                fwqlrInfoList.add(fwMap);
            }
            returnMap.put("fwqlrInfoList", fwqlrInfoList);
        }
        
        //个人无偿赠与不动产登记表
        /*returnMap.put("zyrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
        returnMap.put("zyrsfh",  bdcSpbConfig.bdcStringOutFormate(zjhm1));
        returnMap.put("zyrdz", StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("zyrlxdh", bdcSpbConfig.bdcStringOutFormate(lxdh1));
        returnMap.put("zyrbdcdz",  StringUtil.getValue(busInfo, "FW_ZL"));
        returnMap.put("zyrmj",  StringUtil.getValue(busInfo, "FW_JZMJ"));
        returnMap.put("szrxm", StringUtil.getValue(busInfo, "SZR_XM"));
        returnMap.put("szrsfh", StringUtil.getValue(busInfo, "SZR_ZJHM"));
        returnMap.put("szrzz", StringUtil.getValue(busInfo, "SZR_ZZ"));
        returnMap.put("szrlxdh", StringUtil.getValue(busInfo, "SZR_LXDH"));
        returnMap.put("szryzrgx", StringUtil.getValue(busInfo, "SZR_YZRGX"));*/
        //实名办税授权委托书、从上传的excel附件中提取数据
        /*List<Map<String, Object>> excelList = new ArrayList<Map<String,Object>>();
        Map<String, Object> file = this.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[]{"file_id"}, 
                new Object[]{busInfo.get("SWDJ_FILE_ID")});
        if(file!=null){
            excelList = getExecelDataList(StringUtil.getString(file.get("FILE_PATH")),3);
            if(excelList.size()>0){
                returnMap.put("wtrList", excelList);
                returnMap.put("allname", StringUtil.getString(excelList.get(0).get("swdjxm")));
                returnMap.put("total", excelList.size());
            }
        }
        returnMap.put("SQJG_CREDIT_CODE",  StringUtil.getString(busInfo.get("SQJG_CREDIT_CODE")));//企业统一社会信用代码
        returnMap.put("JBR_ZJHM",  StringUtil.getString(busInfo.get("JBR_ZJHM")));//经办证件号码*/
    }
    /**
     * 
     * 描述  设置不动产通用模板签章业务数据
     * @author Yanisin Shi
     * @param returnMap
     * @param busInfo
     * @param execution
     */
    private void setBdcGeneralBusInfo(Map<String, Object> returnMap, Map<String, Object> busInfo,
            Map<String, Object> execution) {
         returnMap.put("qllx", "不动产全程网办通用流程");
         returnMap.put("djlx", StringUtil.getValue(busInfo, "DJLX"));
         returnMap.put("clscsj", DateTimeUtil.getStrOfDate(new Date(),"yyyy年MM月dd日"));
         if (StringUtil.getValue(execution, "SQFS").equals("1")) {
             returnMap.put("sjr", "");
         } else {
             returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
         }
         String sbh = StringUtil.getValue(returnMap, "xmsqbh");
         if (StringUtils.isEmpty(sbh)) {
             returnMap.put("xmsqbh", StringUtil.getValue(busInfo, "EFLOW_EXEID"));
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
             StringBuffer lxdh1 = new StringBuffer();
             StringBuffer lxdh2 = new StringBuffer();
             List<Map<String,Object>> sqrInfoList = new ArrayList<>();
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
                 Map<String,Object> sqlInfoMap = new HashMap<>();
                 sqlInfoMap.put("sqrxm", StringUtil.getValue(piMap,"POWERPEOPLENAME"));
                 if (powerpeopleidtype.equals("SF")) {
                     sqlInfoMap.put("sqrzjlx", "IDCard");
                 } else if (powerpeopleidtype.equals("HZ")) {
                     sqlInfoMap.put("sqrzjlx", "Passport");
                 } else if (powerpeopleidtype.equals("GATX")) {
                     sqlInfoMap.put("sqrzjlx", "HMPass");
                 } else {
                     sqlInfoMap.put("sqrzjlx", "Other");
                 }
                 sqlInfoMap.put("sqrzjhm", StringUtil.getValue(piMap,"POWERPEOPLEIDNUM"));
                 sqlInfoMap.put("sqrsjhm", StringUtil.getValue(piMap,"POWERPEOPLEMOBILE"));
                 sqrInfoList.add(sqlInfoMap);
             }
             returnMap.put("sqrInfoList", sqrInfoList);
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
         String ywJson = StringUtil.getValue(busInfo, "YWPEOPLEINFO_JSON");
         if (StringUtils.isNotEmpty(ywJson)) {
             StringBuffer ywrxm = new StringBuffer();
             StringBuffer sfzjzl = new StringBuffer();
             StringBuffer zjhm1 = new StringBuffer();
             StringBuffer txdz1 = new StringBuffer();
             StringBuffer yb1 = new StringBuffer();
             StringBuffer fddbr1 = new StringBuffer();
             StringBuffer dlrxm1 = new StringBuffer();
             StringBuffer lxdh3 = new StringBuffer();
             StringBuffer lxdh4 = new StringBuffer();
             List<Map<String,Object>> ywrInfoList = new ArrayList<>();
             List<Map> piList = JSONArray.parseArray(ywJson, Map.class);
             for (Map<String, Object> piMap : piList) {
                 ywrxm.append(StringUtil.getValue(piMap, "YWPEOPLENAME")).append(",");
                 /*证件类型代码转换*/
                 String ywpeopleidtype = StringUtil.getValue(piMap, "YWPEOPLEIDTYPE");
                 String documentType = dictionaryService.getDicNames("DocumentType", ywpeopleidtype);
                 sfzjzl.append(documentType).append(",");
                 zjhm1.append(StringUtil.getValue(piMap, "YWPEOPLEIDNUM")).append(",");
                 txdz1.append(StringUtil.getValue(piMap, "YWPEOPLEADDR")).append(",");
                 yb1.append(StringUtil.getValue(piMap, "YWPEOPLEPOSTCODE")).append(",");
                 fddbr1.append(StringUtil.getValue(piMap, "YWLEGALNAME")).append(",");
                 dlrxm1.append(StringUtil.getValue(piMap, "YWAGENTNAME")).append(",");
                 lxdh3.append(StringUtil.getValue(piMap, "YWPEOPLEMOBILE")).append(",");
                 lxdh4.append(StringUtil.getValue(piMap, "YWAGENTTEL")).append(",");
                 Map<String,Object> ywrInfoMap = new HashMap<>();
                 ywrInfoMap.put("ywrxm", StringUtil.getValue(piMap,"YWPEOPLENAME"));
                 if (ywpeopleidtype.equals("SF")) {
                     ywrInfoMap.put("ywrzjlx", "IDCard");
                 } else if (ywpeopleidtype.equals("HZ")) {
                     ywrInfoMap.put("ywrzjlx", "Passport");
                 } else if (ywpeopleidtype.equals("GATX")) {
                     ywrInfoMap.put("ywrzjlx", "HMPass");
                 } else {
                     ywrInfoMap.put("ywrzjlx", "Other");
                 }
                 ywrInfoMap.put("ywrzjhm", StringUtil.getValue(piMap,"YWPEOPLEIDNUM"));
                 ywrInfoMap.put("ywrsjhm", StringUtil.getValue(piMap,"YWPEOPLEMOBILE"));
                 ywrInfoList.add(ywrInfoMap);
             }
             returnMap.put("ywrInfoList", ywrInfoList);
             returnMap.put("ywrxm", bdcSpbConfig.bdcStringOutFormate(ywrxm));
             returnMap.put("sfzjzl1", bdcSpbConfig.bdcStringOutFormate(sfzjzl));
             returnMap.put("zjhm1", bdcSpbConfig.bdcStringOutFormate(zjhm1));
             returnMap.put("txdz1", bdcSpbConfig.bdcStringOutFormate(txdz1));
             returnMap.put("yb1", bdcSpbConfig.bdcStringOutFormate(yb1));
             returnMap.put("fddbr1", bdcSpbConfig.bdcStringOutFormate(fddbr1));
             returnMap.put("dlrxm1", bdcSpbConfig.bdcStringOutFormate(dlrxm1));
             returnMap.put("lxdh3", bdcSpbConfig.bdcStringOutFormate(lxdh3));
             returnMap.put("lxdh4", bdcSpbConfig.bdcStringOutFormate(lxdh4));
         }
         selectYesOrNo("FBCZ1","TAKECARD_TYPE","0",busInfo,returnMap);
         selectYesOrNo("FBCZ2","TAKECARD_TYPE","1",busInfo,returnMap);
         /*不动产情况*/
         returnMap.put("zl", StringUtil.getValue(busInfo, "LOCATED"));
         returnMap.put("bdcdyh", StringUtil.getValue(busInfo, "ESTATE_NUM"));
         // 字典qlxz
         returnMap.put("qlxz", dictionaryService.findByDicCodeAndTypeCode(
                 StringUtil.getValue(busInfo, "ZD_QLXZ"), "100"));
         returnMap.put("bdclx", "土地、房产");
         /*面积*/
         StringBuffer mj = new StringBuffer();
         mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²").append("/");
         mj.append("房屋建筑面积：").append(StringUtil.getValue(busInfo, "FW_JZMJ")).append("m²");
         returnMap.put("mj", mj);
         returnMap.put("yt", StringUtil.getValue(busInfo, "ZD_YTSM") + "/" + StringUtil.getValue(busInfo,"FW_YTSM"));
         /*登记原因*/
         returnMap.put("djyy", StringUtil.getValue(busInfo, "FW_DJYY"));
         /*登记原因证明文件*/
         bdcSpbConfig.getMaterNameList(returnMap);
         // 抵押情况
         returnMap.put("bdbzqse", StringUtil.getValue(busInfo, "DY_DBSE"));
}

    /**
     * 
     * 描述      提取指定路径excel文件中的数据
     * @author Allin Lin
     * @created 2021年10月15日 下午5:21:34
     * @param path  文件相对路径
     * @param rowNum  开始行数
     * @return
     */
    public List<Map<String, Object>> getExecelDataList(String path,int rowNum){
        DownLoadServlet downLoadServlet = new DownLoadServlet();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        Properties properties = FileUtil.readProperties("project.properties");
        LinkedList<Map<String, Object>> ryList = new LinkedList<Map<String, Object>>();
        String fileServer = properties.getProperty("uploadFileUrlIn");
        POIFSFileSystem fs;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        try {
            InputStream is = downLoadServlet.getStreamDownloadOutFile(fileServer+
                    path);
            fs = new POIFSFileSystem(is);
            // 取得相应工作簿
            workbook = new HSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int k = rowNum; k < rowCount; k++) { // 获取到第K行 HSSFRow
               Map<String, Object> wtr=new HashMap<String, Object>();
                HSSFRow row = sheet.getRow(k);
                //获取台湾地区职业资格采信证书基本信息
                Object swdjindex = ExcelUtil.getCellValue(row, 0);
                Object swdjxm = ExcelUtil.getCellValue(row, 1);        
                Object swdjzjlx = ExcelUtil.getCellValue(row, 2);
                Object swdjzjhm = ExcelUtil.getCellValue(row, 3);
                Object swdjsjhm = ExcelUtil.getCellValue(row, 4);
                Object swdjgzbm = ExcelUtil.getCellValue(row, 5);
                Object swdjzw = ExcelUtil.getCellValue(row, 6); 
                Object swdjsfwt = ExcelUtil.getCellValue(row, 7); 
                Object swdjfxsq = ExcelUtil.getCellValue(row, 8);
                Object swdjsqqx = ExcelUtil.getCellValue(row, 9);  
                wtr.put("swdjindex", StringUtil.getString(swdjindex));
                wtr.put("swdjxm", StringUtil.getString(swdjxm));
                wtr.put("swdjzjlx", StringUtil.getString(swdjzjlx));
                wtr.put("swdjzjhm", StringUtil.getString(swdjzjhm));
                wtr.put("swdjsjhm", StringUtil.getString(swdjsjhm));
                wtr.put("swdjgzbm", StringUtil.getString(swdjgzbm));
                wtr.put("swdjzw", StringUtil.getString(swdjzw));
                wtr.put("swdjsfwt", StringUtil.getString(swdjsfwt));
                wtr.put("swdjfxsq", StringUtil.getString(swdjfxsq));
                wtr.put("swdjsqqx", StringUtil.getString(swdjsqqx));
                if(StringUtils.isNotEmpty(StringUtil.getString(swdjindex))
                        && StringUtils.isNotEmpty(StringUtil.getString(swdjxm))){
                    ryList.add(wtr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ryList.size()>0){
            returnMap.put("ryList", ryList);
        }
        return ryList;
    }
    
    
    
    /**
     * 描述:是否选择
     *
     * @author Madison You
     * @created 2020/8/17 14:25:00
     * @param
     * @return
     */
    public void selectYesOrNo(String tagColumn,String column, String columnValue , Map<String,Object> busInfo , Map<String,Object> returnMap) {
        String value = StringUtil.getValue(busInfo, column);
        if (StringUtils.isNotEmpty(value)) {
            if (value.equals(columnValue)) {
                returnMap.put(tagColumn, "☑");
            } else {
                returnMap.put(tagColumn, "□");
            }
        } else {
            returnMap.put(tagColumn, "□");
        }
    }
    
    
    
    /**
     * 描述:回填银行登记申请人字段
     *
     * @author Madison You
     * @created 2020/8/18 17:20:00
     * @param
     * @return
     */
    private void initYhdjsqrFieldForm(Map<String, Object> returnMap, Map<String, Object> busInfo,
                                      Map<String, Object> execution) {
        /*权利人信息JSON*/
        String piJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(piJson)) {
            List<Map> piList = JSONArray.parseArray(piJson, Map.class);
            String GYTD_GYFS = StringUtil.getValue(busInfo, "GYTD_GYFS");
            if (piList != null && piList.size() > 0) {
                Map<String, Object> piMapLxr = piList.get(0);
                returnMap.put("sqrgyqklxr", StringUtil.getValue(piMapLxr, "POWERPEOPLENAME"));
                returnMap.put("sqrgyqklxdz", StringUtil.getValue(piMapLxr, "POWERPEOPLEADDR"));
                returnMap.put("sqrgyqklxdh", StringUtil.getValue(piMapLxr, "POWERPEOPLEMOBILE"));

                if (GYTD_GYFS.equals("0")) {
                    Map<String, Object> piMap = piList.get(0);
                    returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap, "POWERPEOPLENAME"));
                    /*证件类型代码转换*/
                    String powerpeopleidtype = StringUtil.getValue(piMap, "POWERPEOPLEIDTYPE");
                    String documentType = dictionaryService.getDicNames("DocumentType", powerpeopleidtype);
                    returnMap.put("sqrgyqkzjzl1", documentType);
                    returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap, "POWERPEOPLEIDNUM"));
                    returnMap.put("sqrgyqkgj1", "中国");
                    returnMap.put("sqrgyqk1_1", "■");
                    returnMap.put("sqrgyqk1_2", "□");
                    returnMap.put("sqrgyqk1_3", "□");
                    returnMap.put("sqrgyqkfe1", "______");

                    returnMap.put("sqrgyqk2_1", "□");
                    returnMap.put("sqrgyqk2_2", "□");
                    returnMap.put("sqrgyqkfe2", "______");
                } else if (GYTD_GYFS.equals("1")) {
                        Map<String, Object> piMap1 = piList.get(0);
                        returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                        /*证件类型代码转换*/
                        String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                        String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                        returnMap.put("sqrgyqkzjzl1", documentType1);
                        returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqk1_1", "□");
                        returnMap.put("sqrgyqk1_2", "■");
                        returnMap.put("sqrgyqk1_3", "□");
                        returnMap.put("sqrgyqkfe1", "______");
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /*证件类型代码转换*/
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "■");
                        returnMap.put("sqrgyqk2_2", "□");
                        returnMap.put("sqrgyqkfe2", "______");
                    }
                } else if (GYTD_GYFS.equals("2")) {
                        Map<String, Object> piMap1 = piList.get(0);
                        returnMap.put("sqrgyqkxm1", StringUtil.getValue(piMap1, "POWERPEOPLENAME"));
                        /*证件类型代码转换*/
                        String powerpeopleidtype1 = StringUtil.getValue(piMap1, "POWERPEOPLEIDTYPE");
                        String documentType1 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype1);
                        returnMap.put("sqrgyqkzjzl1", documentType1);
                        returnMap.put("sqrgyqkzjhm1", StringUtil.getValue(piMap1, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj1", "中国");
                        returnMap.put("sqrgyqk1_1", "□");
                        returnMap.put("sqrgyqk1_2", "□");
                        returnMap.put("sqrgyqk1_3", "■");
                        returnMap.put("sqrgyqkfe1", StringUtil.getValue(piMap1, "POWERPEOPLEPRO"));
                    if (piList.size() > 1) {
                        Map<String, Object> piMap2 = piList.get(1);
                        returnMap.put("sqrgyqkxm2", StringUtil.getValue(piMap2, "POWERPEOPLENAME"));
                        /*证件类型代码转换*/
                        String powerpeopleidtype2 = StringUtil.getValue(piMap2, "POWERPEOPLEIDTYPE");
                        String documentType2 = dictionaryService.getDicNames("DocumentType", powerpeopleidtype2);
                        returnMap.put("sqrgyqkzjzl2", documentType2);
                        returnMap.put("sqrgyqkzjhm2", StringUtil.getValue(piMap2, "POWERPEOPLEIDNUM"));
                        returnMap.put("sqrgyqkgj2", "中国");
                        returnMap.put("sqrgyqk2_1", "□");
                        returnMap.put("sqrgyqk2_2", "■");
                        returnMap.put("sqrgyqkfe2", StringUtil.getValue(piMap2, "POWERPEOPLEPRO"));
                    }
                }
            }
        }
    }
    
   /**
    * 
    * 描述    国有转移6个事项业务数据存储业务表
    * @author Allin Lin
    * @created 2021年9月9日 上午11:36:06
    * @param flowDatas
    * @return
    */
    @SuppressWarnings("unchecked")
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        //虚拟表转换成实际表
        busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        // 进行业务表数据的保存操作
        busRecordId = dao.saveOrUpdate(flowDatas, busTableName, busRecordId);
        // 获取上传的附件ID
        String fileIds = (String) flowDatas.get("EFLOW_FILEATTACHIDS");
        if (StringUtils.isNotEmpty(fileIds)) {
            // 进行附件业务表记录ID的更新
            fileAttachService.updateTableName(fileIds, busRecordId);
        }
        // 获取申报提交的材料JSON
        String submitMaterFileJson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
        if (StringUtils.isNotEmpty(submitMaterFileJson)) {
            //转移材料表中存储的业务表为对应的虚拟表
            busTableName =  (String) flowDatas.get("EFLOW_BUSTABLENAME");
            fileAttachService.saveItemMaterFiles(submitMaterFileJson, busTableName, busRecordId, flowDatas);
        }
        flowDatas.put("EFLOW_BUSRECORDID", busRecordId);
        return flowDatas;
    }
    
    /**
     * 
     * 描述    存量房税费联办-涉税申报在线签章所有用户创建
     * @author Allin Lin
     * @created 2021年10月25日 上午11:40:38
     * @param flowVars
     * @param returnMap
     */
    public void clfsflbSssbSignUser(Map<String, Object> flowVars,Map<String, Object> returnMap)throws Exception{
        flowVars.put("SIGN_FLAG", true);//设置签章标志位
        
        String IS_AGREE = StringUtil.getString(flowVars.get("IS_AGREE"));
        String  GRSDS_MZ = StringUtil.getString(flowVars.get("GRSDS_MZ"));
        //String  IS_SWDJ = StringUtil.getString(flowVars.get("IS_SWDJ"));
        
        //不动产权属转移涉税补充信息填写确认单-权利人签章
        if((StringUtils.isNotEmpty(IS_AGREE) && "0".equals(IS_AGREE))){
            List<Map<String, Object>> sqlList = (List<Map<String, Object>> )returnMap.get("sqrInfoList");
            for (Map<String, Object> sql : sqlList) {
                Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(sql); 
                if(!(boolean)exUser.get("USER_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                    break;
                }
            }
        }
        
        //家庭唯一生活用房承诺书-多个房屋权利人签章
        if((StringUtils.isNotEmpty(GRSDS_MZ) && "0".equals(GRSDS_MZ))){
            List<Map<String, Object>> fwqlrList = (List<Map<String, Object>> )returnMap.get("fwqlrInfoList");
            for (Map<String, Object> fwqlr : fwqlrList) {
                Map<String, Object> exUser = bdcQlcMaterGenAndSignService.creExUser(fwqlr); 
                if(!(boolean)exUser.get("USER_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", exUser.get("SIGN_MSG"));
                    break;
                }
            }
        }
        
        /*if(StringUtils.isNotEmpty(IS_SWDJ) && "0".equals(IS_SWDJ)){
            //实名办税授权委托书、企业公章+法人代表签字
            if((boolean)flowVars.get(("SIGN_FLAG"))){ 
                Map<String, Object> institution = new HashMap<String, Object>();
                //企业信息
                institution.put("licenseNumber", StringUtil.getString(flowVars.get("SQJG_CREDIT_CODE")));//企业统一社会信用代码
                institution.put("organizeName", StringUtil.getString(flowVars.get("SQJG_NAME")));//企业名称
                institution.put("licenseType","SOCNO");//企业证照类型-统一社会信用代码
                //企业经办信息
                institution.put("sqrsjhm", StringUtil.getString(flowVars.get("JBR_MOBILE")));//手机号码
                institution.put("sqrzjhm", StringUtil.getString(flowVars.get("JBR_ZJHM")));//证件号码
                institution.put("sqrzjlx", zjlxFormmat(flowVars.get("JBR_ZJLX").toString()));//证件类型
                institution.put("sqrxm", StringUtil.getString(flowVars.get("JBR_NAME")));//姓名               
                institution = bdcQlcMaterGenAndSignService.creExInstitutions(institution);
                if(!(boolean)institution.get("INS_CREATEFLAG")){
                    flowVars.put("SIGN_FLAG", false);
                    flowVars.put("SIGN_MSG", institution.get("SIGN_MSG"));  
                }  
            }
        }*/
    }
    
    /**
     * 
     * 描述    签章证件类型格式化
     * @author Allin Lin
     * @created 2021年10月26日 下午2:37:57
     * @param zjlx
     * @return
     */
    private String zjlxFormmat(String zjlx){
        String fmtzjlx = "";
        if(zjlx.equals("身份证") || zjlx.equals("SF")){
            fmtzjlx = "IDCard";
        }else if(zjlx.equals("港澳居民来往内地通行证") || zjlx.equals("GATX")){
            fmtzjlx = "HMPass";
        }else if(zjlx.equals("护照") || zjlx.equals("HZ")){
            fmtzjlx = "Passport";
        }else if(zjlx.equals("台湾居民来往内地通行证") || zjlx.equals("TWTX")){
            fmtzjlx = "MTP";
        }else if(zjlx.equals("营业执照")|| zjlx.equals("统一社会信用代码") || zjlx.equals("YYZZ") || zjlx.equals("XYDM")){
            fmtzjlx = "SOCNO";
        }else if(zjlx.equals("组织机构代码") || zjlx.equals("JGDM")){
            fmtzjlx = "ORANO";
        }else{
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }

    
    /**
     * 
     * 描述    来源平潭通的二手房转移办件下发短信告知工作人员需要预审
     * @author Allin Lin
     * @created 2021年12月7日 上午10:21:53
     * @param flowVars
     */
    public Map<String, Object> sendMsgToJb(Map<String, Object> flowVars){
        String exeId = StringUtil.getValue(flowVars, "EFLOW_EXEID");
        if(StringUtils.isEmpty(exeId)){
            log.error("平潭通二手房转移办件下发短信通知工作人员失败：申报号不能为空");
            return flowVars;
        }
        //获取流程实例数据
        Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        if (flowExe == null) {
            flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                    new Object[] {exeId});
        }
        Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
        String PTT_SQFS = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申请方式  1智能审批 2全程网办
        if(StringUtils.isNotEmpty(PTT_SQFS)&& "2".equals(PTT_SQFS)){
            String sqr = StringUtil.getValue(flowExe, "SQRMC");
            String createTime = StringUtil.getValue(flowExe, "CREATE_TIME");
            Map<String, Object> returnMap = new HashMap<String, Object>();
            if(StringUtils.isEmpty(sqr)){
                log.error("平潭通二手房转移办件下发短信通知工作人员失败：申请人不能为空");
                return flowVars;
            }
            if(StringUtils.isEmpty(createTime)){
                log.error("平潭通二手房转移办件下发短信通知工作人员失败：办件创建时间不能为空");
                return flowVars;
            }
            log.info("平潭通二手房转移办件-"+exeId+"下发短信通知工作人员开始");
            Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
            String templetId = properties.getProperty("sendMsgForPtt");
            StringBuffer logInfo = new StringBuffer();
            String waitToSendMsg = "";
            //获取短信下发工作人员名单
            List<Map<String, Object>> jbList = dictionaryService.getAllByJdbc("T_MSJW_SYSTEM_DICTIONARY", 
                    new String[]{"TYPE_CODE"}, new Object[]{"JBBD"});
            if(jbList.size()>0){
                for (Map<String, Object> jb : jbList) {
                    String toPhone = StringUtil.getString(jb.get("DIC_CODE"));
                    try {
                        String content =  "【" +sqr +"】在【" + createTime +"】提交了新的二手房转移登记全程网办"
                                +"【"+exeId+"】，请半小时内及时登录审批系统进行网上预审。";
                        Map<String, Object> colValues = new HashMap<String, Object>();
                        colValues.put("CONTENT", content);
                        colValues.put("RECEIVER_MOB", toPhone);
                        colValues.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        colValues.put("SEND_TIME", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        colValues.put("SEND_STATUS", "2");
                        waitToSendMsg = sqr+","+createTime+","+exeId;
                        returnMap = SmsSendUtil.sendSms(waitToSendMsg, toPhone, templetId);
                        if (returnMap != null) {
                            String resultcode = returnMap.get("resultcode").toString();
                            if (Objects.equals(resultcode, "0")) {
                                colValues.put("SEND_STATUS", "1");
                                logInfo.append("申报号：").append(exeId).append("发送短信提醒工作人员手机号："+toPhone+"成功,手机号码为：")
                                .append(toPhone);
                            } else {
                                logInfo.append("申报号：").append(exeId).append("发送短信提醒工作人员手机号："+toPhone+"失败,错误信息为：")
                                .append(JSON.toJSONString(returnMap));
                            }
                        }else{
                            logInfo.append("申报号：").append(exeId).append("returnMap为null,手机号码为：").append(toPhone);
                        }
                        log.info(logInfo);
                        dao.saveOrUpdate(colValues, "T_MSJW_SYSTEM_MSGSEND", null);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        log.error(logInfo.append("申报号：").append(exeId).append("报错e")
                                .append(e).append(",手机号码为：").append(toPhone));
                    }
                }
            } 
        }
        return flowVars;
    }
}
