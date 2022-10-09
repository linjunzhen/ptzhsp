/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bsfw.service.BdcApplyService;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.bsfw.service.ZftzService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.system.service.*;
import net.evecom.platform.wsbs.dao.PrintAttachDao;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.PrintAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 打印附件表操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("printAttachService")
public class PrintAttachServiceImpl extends BaseServiceImpl implements PrintAttachService {
    /**
     * 所引入的dao
     */
    @Resource
    private PrintAttachDao dao;
    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入Service
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 引入Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入Service
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * sysUserService
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * zftzService
     */
    @Resource
    private ZftzService zftzService;

    /**
     * 描述 换发不动产
     */
    @Resource
    private BdcApplyService bdcApplyService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 根据流程实例ID获取打印日志
     * 
     * @author Faker Li
     * @created 2016年1月14日 上午8:48:27
     * @param exeId
     * @return
     * @see net.evecom.platform.wsbs.service.PrintAttachService#findByExeId(java.lang.String)
     */
    public List<Map<String, Object>> findByExeId(String exeId) {
        StringBuffer sql = new StringBuffer("SELECT * FROM T_WSBS_PRINTATTACH T ");
        sql.append(" WHERE T.FLOW_EXEID = ? ORDER BY T.CREATE_TIME ASC");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { exeId }, null);
        return list;
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2016年4月20日 上午10:16:18
     * @param templateData
     * @param exeId
     */
    public void getTemplateDataMapByExeId(Map<String, Object> templateData, String exeId) {
        // 先把可能没有值的置空以防页面出现标签
        templateData.put("zdxm", " ");// 重点项目
        templateData.put("jsgm", " ");// 建设规模
        templateData.put("jsdd", " ");// 建设地点
        templateData.put("xmxz", " ");// 项目性质
        templateData.put("jsxz", " ");// 建设性质
        templateData.put("zjly", " ");// 资金来源
        templateData.put("tzje", " ");// 投资金额
        if (StringUtils.isNotEmpty(exeId) && !exeId.equals("undefined")) {
            Map<String, Object> flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if(flowExe == null) {
                flowExe = this.executionService.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] { exeId });
            }
            Map<String, Object> resultInfo = this.executionService.getByJdbc("JBPM6_FLOW_RESULT",
                    new String[] { "EXE_ID" }, new Object[] { exeId });
            if(resultInfo == null) {
                resultInfo = this.executionService.getByJdbc("JBPM6_FLOW_RESULT_EVEHIS",
                        new String[] { "EXE_ID" }, new Object[] { exeId });
            }
            if (resultInfo != null) {
                templateData.put("sdnr",
                        resultInfo.get("SDCONTENT") == null ? "" : resultInfo.get("SDCONTENT").toString());// 送达内容
            } else {
                templateData.put("sdnr", " ");// 送达内容
            }
            templateData.put("xmsqsj", DateTimeUtil.getChinaDate((String) flowExe.get("CREATE_TIME")));// 申请时间
            templateData.put("blsxmc", (String) flowExe.get("ITEM_NAME"));// 办理事项名称
            // -----------房地产契税奖补---------------
            handQS(templateData, flowExe);
            templateData.put("sqjg_name", flowExe.get("SQJG_NAME")==null?
                    " ":flowExe.get("SQJG_NAME").toString());
            templateData.put("cksjry", flowExe.get("CKSJRYXM")==null?
                    " ":flowExe.get("CKSJRYXM").toString());
            String deadLineDate = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
            String wgzrh = workdayService.getDeadLineDate(deadLineDate, 5);
            templateData.put("wgzrh",DateTimeUtil.getChinaDate(wgzrh));
            String sgzrh = workdayService.getDeadLineDate(deadLineDate, 4);
            templateData.put("sgzrh",DateTimeUtil.getChinaDate(sgzrh));
            // --------------------------
            // 查询密码
            if (StringUtils.isNotEmpty((String) flowExe.get("BJCXMM"))) {
                templateData.put("xmcxmm", (String) flowExe.get("BJCXMM"));
            } else {
                templateData.put("xmcxmm", "");
            }
            bsyhlxHang(templateData, flowExe);
            templateData = settemplateData(templateData, flowExe);
            // 审核办理意见
            if (StringUtils.isNotEmpty((String) flowExe.get("FINAL_HANDLEOPINION"))) {
                templateData.put("shblyj", (String) flowExe.get("FINAL_HANDLEOPINION"));
            } else {
                List<Map<String, Object>> taskList = flowTaskService.findTaskList(exeId, "工商审批");
                if (null != taskList && taskList.size() > 0) {
                    String HANDLE_OPINION = taskList.get(0).get("HANDLE_OPINION") == null ? ""
                            : (String) taskList.get(0).get("HANDLE_OPINION");
                    templateData.put("shblyj", HANDLE_OPINION);
                } else {
                    templateData.put("shblyj", "");
                }
            }
            Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_CODE" }, new Object[] { (String) flowExe.get("ITEM_CODE") });
            String itemid = serviceItem.get("ITEM_ID") == null ? "" : serviceItem.get("ITEM_ID").toString();
            String tshjString = getTshjName(itemid);
            templateData.put("tshjjsx", tshjString);
            // 一窗通办取件提示
            // &Q_E.ACCEPTWAY_EQ=1&Q_S.SXLX_EQ=1
            String IS_YCTB = serviceItem.get("IS_YCTB") == null ? "" : serviceItem.get("IS_YCTB").toString();
            String SXLX = serviceItem.get("SXLX") == null ? "" : serviceItem.get("SXLX").toString();
            String ACCEPTWAY = flowExe.get("ACCEPTWAY") == null ? "" : flowExe.get("ACCEPTWAY").toString();
            String SFCJ = flowExe.get("SFCJ") == null ? "" : flowExe.get("SFCJ").toString();
            if ("1".equals(IS_YCTB) && "1".equals(SXLX) && "1".equals(ACCEPTWAY) && "1".equals(SFCJ)) {
                templateData.put("yctbqjts", "请在30分钟后，凭□本受理单、□申请人或经办人有效身份证原件、□___________，到指定___号出件窗口取件。");
            } else {
                templateData.put("yctbqjts", "办理不动产、公积金、医保、社保、失业保险业务，请到21、22号出件窗口取件，其他业务请到相应指定窗口取件");
            }
            // 收费方式
            String sfsf = (String) serviceItem.get("SFSF");
            if ("0".equals(sfsf)) {
                sfsf = "不收费";
                serviceItem.put("SFFS", "");
                serviceItem.put("YIJU", "");
            } else if ("1".equals(sfsf)) {
                // sfsf="是";
                String sffs = (String) serviceItem.get("SFFS");
                String yiju = (String) serviceItem.get("SFBZJYJ");
                yiju = replaceBlank(yiju);
                sffs = dictionaryService.findByDicCodeAndTypeCode(sffs, "ChargeType");
                sfsf += "    收费方式：" + sffs + "    收费标准及依据：" + yiju;
                serviceItem.put("SFFS", sffs);
                serviceItem.put("YIJU", yiju);
            }
            templateData.put("sfyjjbz", sfsf);
            // 承诺时限工作日
            Object cnsj = serviceItem.get("CNQXGZR");
            templateData.put("cnsxgzr", cnsj.toString());
            // 监督电话
            if (StringUtils.isNotEmpty((String) serviceItem.get("JDDH"))) {
                templateData.put("jddh", (String) flowExe.get("JDDH"));
            } else {
                templateData.put("jddh", "");
            }
            Date startTime = DateTimeUtil.format((String) flowExe.get("CREATE_TIME"), "yyyy-MM-dd");// 承诺结束时间
            String startTimeStr = DateTimeUtil.getStrOfDate(startTime, "yyyy-MM-dd");
            String cnjsTime = startTimeStr;
            int hangCount = executionService.getHangDayCount(exeId);
            if (Integer.parseInt((String) cnsj.toString()) > 0) {
                cnjsTime = workdayService.getDeadLineDate(startTimeStr,
                        Integer.parseInt((String) cnsj.toString()) + hangCount);
            }
            /*
             * if (Integer.parseInt((String) cnsj.toString()) > 0) { cnjsTime =
             * workdayService.getDeadLineDate(startTimeStr, Integer.parseInt((String) cnsj.toString())); }
             */
            String xmcnjssj = DateTimeUtil.getChinaDate(cnjsTime);
            templateData.put("xmcnjssj", xmcnjssj);
            if (StringUtils.isNotEmpty((String) serviceItem.get("LXDH"))) {
                templateData.put("blckdh", (String) serviceItem.get("LXDH"));
            } else {
                templateData.put("blckdh", "");
            }
            Map<String, Object> department = departmentService.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_CODE" }, new Object[] { (String) flowExe.get("SSBMBM") });
            templateData.put("blckbm", (String) department.get("DEPART_NAME"));
            String tableName = (String) flowExe.get("BUS_TABLENAME");
            // 内资表单，将虚拟主表名替换真实主表名称
            if (tableName.equals("T_COMMERCIAL_DOMESTIC")) {
                tableName = "T_COMMERCIAL_COMPANY";
            }
            // 内资表单，将虚拟主表名替换真实主表名称
            if (tableName.equals("T_COMMERCIAL_FOREIGN")) {
                tableName = "T_COMMERCIAL_COMPANY";
            }
            //国有转移拆分的7个事项,将虚拟主表名替换真实主表名称
            if(tableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                tableName = "T_BDCQLC_GYJSJFWZYDJ";
            }
            String colNames = (String) this.getPrimaryKeyName(tableName).get(0);
            String colValues = (String) flowExe.get("BUS_RECORDID");
            getxmxx(templateData, serviceItem, tableName, colNames, colValues);
            // 设置材料信息
            String materTableName = (String) flowExe.get("BUS_TABLENAME");
            setClxxlb(templateData, exeId, serviceItem, materTableName, colValues);
            // 获取招标投标申报信息中的字段
            if (StringUtils.isNotBlank(tableName) && tableName.equals("T_BSFW_TZXMZBTB")) {
                getTzxmSbxx(templateData, serviceItem, colValues);
            }
        }
        templateData.put("xmsqbh", exeId);
        templateData.put("dqsj", DateTimeUtil.getChinaDate(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")));
        getBlscData(templateData, exeId);
        // templateData = cleanMap(templateData);
    }

    /**
     * 替换换行、制表符
     * @param str
     * @return
     */
    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("  ");
        }
        return dest;
    }

    /**
     * 描述 设置材料信息
     * @author Rider Chen
     * @created 2017年5月2日 下午1:43:35
     * @param templateData
     * @param exeId
     * @param serviceItem
     * @param tableName
     * @param colValues
     */
    private void setClxxlb(Map<String, Object> templateData, String exeId, Map<String, Object> serviceItem,
            String tableName, String colValues) {
        List<Map<String, Object>> bcList = findsqclbclb(exeId);
        if (bcList == null) {
            templateData.put("sqclbclb", " ");// 申请材料补充列表
        } else {
            StringBuffer bcStr = new StringBuffer();
            for (int i = 0; i < bcList.size(); i++) {
                bcStr.append(bcList.get(i).get("MATER_NAME").toString().replaceAll("\r|\n", "  "));
                //根据材料编码获取材料说明
                Map<String, Object> mater = this.getByJdbc("T_WSBS_APPLYMATER", 
                        new String[]{"MATER_CODE"}, new Object[]{bcList.get(i).get("MATER_CODE")});
                //判断材料说明是否为空
                if(StringUtils.isEmpty(StringUtil.getString(mater.get("MATER_DESC")))){
                    bcStr.append("\\n");
                }else{
                    bcStr.append("(").append(StringUtil.getString(mater.get("MATER_DESC"))
                         .replaceAll("\r|\n", "  ")).append(")").append("\\n");
                }
            }
            templateData.put("sqclbclb", bcStr);
        }

        Set<String> wsset = findwsqcllb(colValues, tableName, (String) serviceItem.get("ITEM_ID"), exeId);
        StringBuffer wsStr = new StringBuffer();
        for (String str : wsset) {
            Map<String, Object> applyMater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[] { "MATER_CODE" }, new Object[] { str });
            Map<String, Object> sapplyMater = applyMaterService.getByJdbc("T_WSBS_SERVICEITEM_MATER",
                    new String[] { "MATER_ID","ITEM_ID" }, 
                    new Object[] { applyMater.get("MATER_ID").toString(),serviceItem.get("ITEM_ID").toString() });
            String mater_isneed = sapplyMater.get("MATER_ISNEED")==null?
                    "":sapplyMater.get("MATER_ISNEED").toString();
            if ("1".equals(mater_isneed)) {
                wsStr.append(applyMater.get("MATER_NAME").toString().replaceAll("\r|\n", "  ")).append("\\n");
            }
        }
        if (StringUtils.isNotEmpty(wsStr.toString())) {
            templateData.put("wsqcllb", wsStr.toString());// 未收材料列表
        }else {
            templateData.put("wsqcllb", "无");// 未收材料列表
        }

        Set<String> ysset = findysqcllb(colValues, tableName);
        StringBuffer ysStr = new StringBuffer();
        for (String str : ysset) {
            Map<String, Object> applyMater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER",
                    new String[] { "MATER_CODE" }, new Object[] { str });
            if (applyMater != null) {
                String maNameString = applyMater.get("MATER_NAME") == null ? ""
                        : applyMater.get("MATER_NAME").toString();
                if (StringUtils.isNotEmpty(maNameString)) {
                    ysStr.append(maNameString.replaceAll("\r|\n", "  "));
                    //添加材料说明显示
                    if(StringUtils.isEmpty(StringUtil.getString(applyMater.get("MATER_DESC")))){
                        ysStr.append("\\n");
                    }else{
                        ysStr.append("(").append(StringUtil.getString(applyMater.get("MATER_DESC"))
                             .replaceAll("\r|\n", "  ")).append(")").append("\\n");
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(ysStr.toString())){
            templateData.put("ysqcllb", ysStr.toString());// 已收材料列表
        }else{
            templateData.put("ysqcllb", " ");// 已收材料列表
        }
    }

    private void bsyhlxHang(Map<String, Object> templateData, Map<String, Object> flowExe) {
        String bsyhlx = flowExe.get("BSYHLX") == null ? "1" : flowExe.get("BSYHLX").toString();
        if (bsyhlx.equals("1")) {
            // 项目申请人员
            templateData.put("xmsqry", (String) flowExe.get("SQRMC"));
            templateData.put("lxr", (String) flowExe.get("SQRMC"));
            templateData.put("xmsqdw", (String) flowExe.get("SQRMC"));
            templateData.put("jsdxdh", (String) flowExe.get("SQRSJH"));// 联系人手机
            // 联系人电话
            if (StringUtils.isNotEmpty((String) flowExe.get("SQRDHH"))) {
                templateData.put("lxdh", (String) flowExe.get("SQRDHH"));
            } else {
                templateData.put("lxdh", "");
            }
            templateData.put("lxdz", (String) flowExe.get("SQRLXDZ"));// 联系人地址
        } else {
            // 项目申请人员
            templateData.put("xmsqry", (String) flowExe.get("SQJG_NAME"));
            templateData.put("lxr", (String) flowExe.get("JBR_NAME"));
            templateData.put("xmsqdw", (String) flowExe.get("SQJG_NAME"));
            templateData.put("jsdxdh", (String) flowExe.get("JBR_MOBILE"));// 联系人手机
            templateData.put("lxdh", (String) flowExe.get("JBR_LXDH"));// 联系人电话
            // 联系人地址
            if (StringUtils.isNotEmpty((String) flowExe.get("JBR_ADDRESS"))) {
                templateData.put("lxdz", (String) flowExe.get("JBR_ADDRESS"));
            } else {
                templateData.put("lxdz", "");
            }
        }
    }

    /**
     * 
     * 描述：
     * @created 2017-3-19 下午5:34:18
     * @param templateData
     */
    public void getBackDataMapByItemId(Map<String, Object> templateData, String itemCode) {
        Map<String, Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_CODE" }, new Object[] { itemCode });
        if (serviceItem != null) {
            String lxdh = (String) serviceItem.get("LXDH");
            if (lxdh != null && !"".equals(lxdh)) {
                serviceItem.put("LXDH", lxdh.replace("\n", " "));
            }
        }
        templateData.put("blsxmc", StringUtil.getValue(serviceItem,"ITEM_NAME"));// 办理事项名称
        // 办理项目名称
        templateData.put("blxmmc", " ");
        // 重点项目
        templateData.put("zdxm", " ");
        templateData.put("lxr", StringUtil.getValue(serviceItem,"LXR"));// 联系人
        templateData.put("jsdxdh", " ");// 联系人手机
        templateData.put("lxdh", StringUtil.getValue(serviceItem,"LXDH"));// 联系人电话
        templateData.put("lxdz", " ");// 联系地址
        templateData.put("ysqcllb", " ");// 已收材料列表
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(",SM.MATER_ISNEED,SM.MATER_SN,SM.MATER_FPARA from T_WSBS_SERVICEITEM_MATER SM ");
        sql.append("LEFT JOIN T_WSBS_APPLYMATER T ON SM.MATER_ID=T.MATER_ID ");
        sql.append(" WHERE SM.ITEM_ID='");
        sql.append(serviceItem.get("item_id")).append("'");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        if (list == null) {
            templateData.put("sqclbclb", "");// 申请材料补充列表
        } else {
            StringBuffer bcStr = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                int j = i + 1;
                bcStr.append(j).append(". ").append(list.get(i).get("MATER_NAME").toString().replaceAll("\r|\n", "  "))
                        .append("\\n");
            }
            templateData.put("sqclbclb", bcStr);
        }
        // 办理窗口电话
        if (StringUtils.isNotEmpty((String) serviceItem.get("LXDH"))) {
            templateData.put("blckdh", (String) serviceItem.get("LXDH"));
        } else {
            templateData.put("blckdh", "");
        }
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "ServiceItemType", (String) serviceItem.get("SXLX") });
        String dicName = (String) dictionary.get("DIC_NAME");
        if (dicName.equals("即办件")) {
            templateData.put("spfwlx", (String) dictionary.get("DIC_NAME"));
        } else {
            templateData.put("spfwlx", "承诺件");
        }
        Object cnsj = serviceItem.get("CNQXGZR");
        templateData.put("cnsxgzr", cnsj.toString());
        templateData.put("dqsj", DateTimeUtil.getChinaDate(DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd")));
    }

    private void handQS(Map<String, Object> templateData, Map<String, Object> flowExe) {
        String busRecordid = (String) flowExe.get("BUS_RECORDID");
        Map<String, Object> ptjInfo = this.executionService.getByJdbc("T_BSFW_PTJINFO", new String[] { "YW_ID" },
                new Object[] { busRecordid });
        if (ptjInfo != null) {
            if (StringUtils.isNotEmpty((String) ptjInfo.get("FDCQS_AMOUNT"))) {
                templateData.put("qsxm", (String) flowExe.get("SQRMC"));
                templateData.put("qssfzh", (String) flowExe.get("SQRSFZ"));
                templateData.put("qsjnqsje", (String) ptjInfo.get("FDCQS_AMOUNT"));
                templateData.put("qsnssj", (String) ptjInfo.get("FDCQS_PAYMENT_TIME"));
                templateData.put("qssj", (String) flowExe.get("SQRSJH"));
                templateData.put("qskhyh", (String) ptjInfo.get("FDCQS_BANK"));
                templateData.put("qsyhzh", (String) ptjInfo.get("FDCQS_BANK_ACCOUNT"));
                templateData.put("qsjtwz", (String) ptjInfo.get("FDCQS_LOCATION"));
                templateData.put("qskfsjxmmc", (String) ptjInfo.get("FDCQS_DEVELOPERS"));
                templateData.put("qshtbasj", (String) ptjInfo.get("FDCQS_CONTFIL_TIME"));
                templateData.put("qshtbabh", (String) ptjInfo.get("FDCQS_CONTFIL_NUM"));
                templateData.put("qsxsqk", (String) ptjInfo.get("FDCQS_TYPE"));
                templateData.put("qsjeyb", (String) ptjInfo.get("FDCQS_AMOUNT_HALF"));
                templateData.put("qsjeybdx", (String) ptjInfo.get("FDCQS_AMOUNT_HALFCAP"));
            }

        }
    }

    public Map<String, Object> settemplateData(Map<String, Object> templateData, Map<String, Object> flowExe) {
        String jbrName = flowExe.get("JBR_NAME") == null ? " " : flowExe.get("JBR_NAME").toString();
        String jbrMobile = flowExe.get("JBR_MOBILE") == null ? " " : flowExe.get("JBR_MOBILE").toString();
        String jbrLxdh = flowExe.get("JBR_LXDH") == null ? " " : flowExe.get("JBR_LXDH").toString();
        String jbrLxdz = flowExe.get("JBR_ADDRESS") == null ? " " : flowExe.get("JBR_ADDRESS").toString();
        if (StringUtils.isEmpty(StringUtils.trim(jbrLxdh))) {
            jbrLxdh = jbrMobile;
        }
        // String jbrAddress = flowExe.get("JBR_ADDRESS")==null?
        // " ":flowExe.get("JBR_ADDRESS").toString();
        templateData.put("jbrname", jbrName);// 经办人人员
        templateData.put("jbrmobile", jbrMobile);// 经办人手机
        templateData.put("jbrlxdh", jbrLxdh);// 经办人电话
        templateData.put("jbrlxdz", jbrLxdz);// 经办人联系地址
        templateData.put("dyybdbjyjrq", "");
        return templateData;
    }

    /**
     * 描述 获取招标投标申报信息中的字段
     * 
     * @author Derek Zhang
     * @created 2016年8月11日 下午2:44:55
     * @param templateData
     * @param serviceItem
     */
    @SuppressWarnings("unchecked")
    private void getTzxmSbxx(Map<String, Object> templateData, Map<String, Object> serviceItem, String busYwId) {
        StringBuffer sqlItem = new StringBuffer("SELECT * FROM T_BSFW_TZXMZBTB T");
        sqlItem.append("  WHERE T.YW_ID= ? ");
        List<Map<String, Object>> zbXMXXList = this.dao.findBySql(sqlItem.toString(), new Object[] { busYwId }, null);
        if (zbXMXXList != null && !zbXMXXList.isEmpty()) {
            if (zbXMXXList.get(0).get("CONTACT_NAME") != null) {
                templateData.put("zbxmlxr", zbXMXXList.get(0).get("CONTACT_NAME"));
            } else {
                templateData.put("zbxmlxr", " ");
            }
            if (zbXMXXList.get(0).get("CONTACT_MOBILE") != null) {
                templateData.put("zbxmlxrlxfs", zbXMXXList.get(0).get("CONTACT_MOBILE"));
            } else {
                templateData.put("zbxmlxrlxfs", " ");
            }
        }
        StringBuffer sql = new StringBuffer("SELECT * FROM T_BSFW_TZXMZBTB_ITEM I").append("  WHERE I.ZB_ID=? ");
        List<Map<String, Object>> zbXMList = this.dao.findBySql(sql.toString(), new Object[] { busYwId }, null);
        if (zbXMList != null && !zbXMList.isEmpty()) {
            for (int i = 0; i < zftzService.ZBTB_TYPEARRS.length; i++) {
                boolean hasDate = false;
                for (int j = 0; j < zbXMList.size(); j++) {
                    // 匹配对应的类型记录
                    if (zbXMList.get(j).get("ZB_TYPE") != null
                            && zbXMList.get(j).get("ZB_TYPE").equals(ZftzService.ZBTB_TYPEARRS[i][0])) {
                        hasDate = true;
                        // 获取招标项目金额
                        if (zbXMList.get(j).get("ZB_ITEM_AMOUNT") != null) {
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_gsje",
                                    zbXMList.get(j).get("ZB_ITEM_AMOUNT"));
                        } else {
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_gsje", "0");
                        }
                        // 获取招标项目是否邀请招标和不招标
                        if (zbXMList.get(j).get("ZB_XS") != null) {
                            if ("1".equals(zbXMList.get(j).get("ZB_XS"))) {
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_yqzb", "√");
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_bzb", "--");
                            } else if ("2".equals(zbXMList.get(j).get("ZB_XS"))) {
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_yqzb", "--");
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_bzb", "√");
                            } else {
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_yqzb", "--");
                                templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_bzb", "--");
                            }
                        } else {
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_yqzb", "--");
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_bzb", "--");
                        }
                        // 获取招标项目是否邀请招标和不招标
                        if (zbXMList.get(j).get("REASONS") != null
                                && ((String) zbXMList.get(j).get("REASONS")).length() >= 1) {
                            StringBuffer reasons = new StringBuffer();
                            StringBuffer lySql = new StringBuffer("SELECT DIC_NAME,DIC_SN,DIC_DESC");
                            String[] resons = ((String) zbXMList.get(j).get("REASONS")).split(",");
                            String resonInStr = "";
                            if (resons != null && resons.length > 0) {
                                if (resons.length == 1) {
                                    resonInStr = "'" + resons[0] + "'";
                                } else {
                                    for (int k = 0; k < resons.length; k++) {
                                        if (k == 0) {
                                            resonInStr = "'" + resons[0] + "'";
                                        } else {
                                            resonInStr = resonInStr + ",'" + resons[k] + "'";
                                        }
                                    }
                                }
                            }
                            lySql.append(" from t_msjw_system_dictionary d where d.type_code= ? ");
                            lySql.append(" and d.dic_code in ( " + resonInStr).append(" ) order by d.dic_sn asc");
                            String typeCode = "tzxmyqzbly";
                            if (zbXMList.get(j).get("ZB_XS") != null && "2".equals(zbXMList.get(j).get("ZB_XS"))) {
                                typeCode = "tzxmbzbly";
                                reasons.append("不招标理由：\\r\\n");
                            } else {
                                reasons.append("邀请招标理由：\\r\\n");
                            }
                            List<Map<String, Object>> lyList = this.dao.findBySql(lySql.toString(),
                                    new Object[] { typeCode }, null);
                            if (lyList != null && !lyList.isEmpty()) {
                                for (int m = 0; m < lyList.size(); m++) {
                                    reasons.append((m + 1) + ". " + lyList.get(m).get("DIC_DESC"));
                                    if (m == lyList.size() - 1) {
                                        reasons.append("。");
                                    } else {
                                        reasons.append("；\\r\\n");
                                    }
                                }
                            }
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_lysm", reasons.toString());
                        } else {
                            templateData.put(zbXMList.get(j).get("ZB_TYPE") + "_lysm", "");
                        }
                    }
                }
                if (!hasDate) {// 无数据的项目类型设置默认数据
                    templateData.put(ZftzService.ZBTB_TYPEARRS[i][0] + "_gsje", "0");
                    templateData.put(ZftzService.ZBTB_TYPEARRS[i][0] + "_yqzb", "--");
                    templateData.put(ZftzService.ZBTB_TYPEARRS[i][0] + "_bzb", "--");
                    templateData.put(ZftzService.ZBTB_TYPEARRS[i][0] + "_lysm", "");
                }
            }
        }
    }

    /**
     * 
     * 描述 得到项目信息
     * 
     * @author Faker Li
     * @created 2016年1月13日 下午4:45:12
     * @param templateData
     * @param serviceItem
     * @param tableName
     * @param colNames
     * @param colValues
     */
    public void getxmxx(Map<String, Object> templateData, Map<String, Object> serviceItem, String tableName,
            String colNames, String colValues) {
        Map<String, Object> projectInfo = this.getByJdbc(tableName, new String[] { colNames },
                new Object[] { colValues });
        // 判断普通件、即办件与特殊件的区别
        String remark = projectInfo.get("REMARK") == null ? " " : projectInfo.get("REMARK").toString();
        templateData.put("remark", remark);
        String xmmc = (String) projectInfo.get("SBMC");
        // 坐落数据
        templateData.put("zl", StringUtil.getValue(projectInfo, "LOCATED"));
        if(StringUtils.isNotEmpty(StringUtil.getValue(projectInfo, "ZL"))) {
            templateData.put("zl", StringUtil.getValue(projectInfo, "ZL"));
        }
        if (StringUtils.isEmpty(xmmc) && projectInfo.get("PROJECT_TYPE") != null) {
            templateData.put("blxmmc", (String) projectInfo.get("PROJECT_NAME"));
            templateData.put("zdxm", " ");
            templateData.put("jsdd", (String) projectInfo.get("PLACE_CODE"));
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "PROJECTTYPE", (String) projectInfo.get("PROJECT_TYPE") });
            if (dictionary != null) {
                templateData.put("xmxz", (String) dictionary.get("DIC_NAME"));
            }
            dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "PROJECTNATURE", (String) projectInfo.get("PROJECT_NATURE") });
            if (dictionary != null) {
                templateData.put("jsxz", (String) dictionary.get("DIC_NAME"));
            }
            templateData.put("zjly", "");
            templateData.put("tzje", (String) projectInfo.get("TOTAL_MONEY"));
            dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "ServiceItemType", (String) serviceItem.get("SXLX") });
            if (dictionary != null) {
                templateData.put("spfwlx", (String) dictionary.get("DIC_NAME"));
            }
            templateData.put("jsgm", (String) projectInfo.get("SCALE_CONTENT"));
            templateData.put("gsjgxx", (String) projectInfo.get("GSJG"));// 公示结果
        } else {
            templateData.put("blxmmc", xmmc);
            templateData.put("zdxm", " ");
            templateData.put("jsdd", " ");
            templateData.put("xmxz", " ");
            templateData.put("jsxz", " ");
            templateData.put("zjly", " ");
            templateData.put("tzje", " ");
            templateData.put("spfwlx", (String) projectInfo.get("SXLX"));
            templateData.put("jsgm", " ");
            templateData.put("gsjgxx", " ");
        }
    }

    /**
     * 设置不动产查询数据
     * @param variable
     * @return
     */
    public Map<String, Object> setEstateQueryData(Map<String, Object> variable) {
        String exeId = variable.get("EFLOW_EXEID") == null ? "" : variable.get("EFLOW_EXEID").toString();
        String typeId = String.valueOf(variable.get("typeId"));
        Map<String, Object> execution = dao.getByJdbc("jbpm6_execution", new String[] { "EXE_ID" },
                new Object[] { exeId });
        // 设置材料信息
        String itemCode = String.valueOf(execution.get("ITEM_CODE"));
        Map<String, Object> serviceItem = dao.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new Object[] { itemCode });
        String tableName = (String) execution.get("BUS_TABLENAME");
        String colValues = (String) execution.get("BUS_RECORDID");
        setClxxlb(variable, exeId, serviceItem, tableName, colValues);
        // 业务表
        String busTableName = String.valueOf(execution.get("BUS_TABLENAME"));
        String busCordId = String.valueOf(execution.get("BUS_RECORDID"));
        String primaryKeyName = String.valueOf(this.getPrimaryKeyName(busTableName).get(0));
        Map<String, Object> busMap = dao.getByJdbc(busTableName, new String[] { primaryKeyName },
                new Object[] { busCordId });
        String powerpeopleinfoJson = busMap.get("POWERPEOPLEINFO_JSON") == null ? ""
                : busMap.get("POWERPEOPLEINFO_JSON").toString();
        // 权利信息转化
        setPowerPeople(variable, typeId, powerpeopleinfoJson);
        variable.putAll(execution);
        variable.putAll(busMap);

        variable.put("xmsqbh", exeId);
        setDictionaryName(variable);
        return variable;
    }

    /**
     * 设置权利信息
     * @param variable
     * @param typeId
     * @param powerpeopleinfoJson
     */
    private void setPowerPeople(Map<String, Object> variable, String typeId, String powerpeopleinfoJson) {
        if (StringUtils.isNotEmpty(powerpeopleinfoJson)) {
            List<Map<String, Object>> obligees = (List<Map<String, Object>>) JSON.parse(powerpeopleinfoJson);
            String templatePath = variable.get("TemplatePath") == null ? "" : variable.get("TemplatePath").toString();
            if (obligees.size() > 5 && "bdcApoveHave.doc".equals(templatePath)) {
                variable.put("TemplatePath", "bdcApoveHave30.doc");
            }
            // 初始化，空值
            for (int i = 0; i < 30; i++) {
                variable.put("index" + i, "");
                variable.put("NAME" + i, "");
                variable.put("IDNUM" + i, "");
                variable.put("PROPERTY_TYPE" + i, "");
                variable.put("PROPERTY_STATUS" + i, "");
                variable.put("PROPERTY_HUMAN" + i, "");
                variable.put("PROPERTY_ADDR" + i, "");
                variable.put("PROPERTY_NUM" + i, "");
                variable.put("PROPERTY_TIME" + i, "");
                variable.put("PROPERTY_AREA" + i, "");
                variable.put("PROPERTY_USE" + i, "");
                variable.put("PROPERTY_NATURE" + i, "");
            }
            for (int i = 0; i < obligees.size(); i++) {
                variable.put("index" + i, i + 1);
                variable.put("NAME" + i, String.valueOf(obligees.get(i).get("NAME")));
                variable.put("IDNUM" + i, String.valueOf(obligees.get(i).get("IDNUM")));
                String pt = obligees.get(i).get("PROPERTY_TYPE") == null ? ""
                        : obligees.get(i).get("PROPERTY_TYPE").toString();
                variable.put("PROPERTY_TYPE" + i, pt);
                String ps = obligees.get(i).get("PROPERTY_STATUS") == null ? ""
                        : obligees.get(i).get("PROPERTY_STATUS").toString();
                variable.put("PROPERTY_STATUS" + i, ps);
                String ph = obligees.get(i).get("PROPERTY_HUMAN") == null ? ""
                        : obligees.get(i).get("PROPERTY_HUMAN").toString();
                variable.put("PROPERTY_HUMAN" + i, ph);
                String pa = obligees.get(i).get("PROPERTY_ADDR") == null ? ""
                        : obligees.get(i).get("PROPERTY_ADDR").toString();
                variable.put("PROPERTY_ADDR" + i, pa);
                String pn = obligees.get(i).get("PROPERTY_NUM") == null ? ""
                        : obligees.get(i).get("PROPERTY_NUM").toString();
                variable.put("PROPERTY_NUM" + i, pn);
                String ptime = obligees.get(i).get("PROPERTY_TIME") == null ? ""
                        : obligees.get(i).get("PROPERTY_TIME").toString();
                variable.put("PROPERTY_TIME" + i, ptime);
                String pArea = obligees.get(i).get("PROPERTY_AREA") == null ? ""
                        : obligees.get(i).get("PROPERTY_AREA").toString();
                variable.put("PROPERTY_AREA" + i, pArea);
                String pUse = obligees.get(i).get("PROPERTY_USE") == null ? ""
                        : obligees.get(i).get("PROPERTY_USE").toString();
                variable.put("PROPERTY_USE" + i, pUse);
                String pNature = obligees.get(i).get("PROPERTY_NATURE") == null ? ""
                        : obligees.get(i).get("PROPERTY_NATURE").toString();
                variable.put("PROPERTY_NATURE" + i, pNature);
            }
        } else {
            if ("2".equals(typeId)) {
                variable.put("TemplatePath", "bdcApoveNone.doc");
            }
        }
    }

    /**
     * 设置字典值
     * @param variable
     */
    private void setDictionaryName(Map<String, Object> variable) {
        String applypeopleType = String.valueOf(variable.get("APPLYPEOPLE_TYPE"));
        applypeopleType = dictionaryService.getDicNames("applyType", applypeopleType);
        variable.put("APPLYPEOPLE_TYPE", applypeopleType);
        String queryContent = String.valueOf(variable.get("QUERY_CONTENT"));
        queryContent = dictionaryService.getDicNames("qyeryContent", queryContent);
        variable.put("QUERY_CONTENT", queryContent);
        String queryRequire = String.valueOf(variable.get("QUERY_REQUIRE"));
        queryRequire = dictionaryService.getDicNames("queryRequire", queryRequire);
        variable.put("QUERY_REQUIRE", queryRequire);
        String queryOfUse = String.valueOf(variable.get("QUERY_USE"));
        queryOfUse = dictionaryService.getDicNames("queryOfUse", queryOfUse);
        variable.put("QUERY_USE", queryOfUse);
        String sqrzjlx = variable.get("SQRZJLX") == null ? "" : variable.get("SQRZJLX").toString();
        if (StringUtils.isNotEmpty(sqrzjlx)) {
            sqrzjlx = dictionaryService.getDicNames("DocumentType", sqrzjlx);
            variable.put("SQRZJLX", sqrzjlx);
        }

        String jbrzjlx = variable.get("JBR_ZJLX") == null ? "" : variable.get("JBR_ZJLX").toString();
        if (StringUtils.isNotEmpty(sqrzjlx)) {
            jbrzjlx = dictionaryService.getDicNames("DocumentType", jbrzjlx);
            variable.put("JBR_ZJLX", jbrzjlx);
        }
        String handTime = variable.get("HAND_TIME") == null ? "" : variable.get("HAND_TIME").toString();
        if (StringUtils.isNotEmpty(handTime)) {
            variable.put("MHAND_TIME", handTime);
            handTime = DateTimeUtil.getChinaDate(handTime);
            variable.put("HAND_TIME", handTime);
        }
    }

    /**
     * 
     * 描述 获取需要补件列表
     * 
     * @author Faker Li
     * @created 2016年1月13日 下午3:57:37
     * @return
     */
    public List<Map<String, Object>> findsqclbclb(String exeId) {
        Map<String, Object> bjxx = bjxxService.getByJdbc("T_WSBS_BJXX", new String[] { "EXE_ID" },
                new Object[] { exeId });
        List bjclList = null;
        if (bjxx != null) {
            bjclList = JSON.parseArray((String) bjxx.get("BJCLLB"), Map.class);
        }
        return bjclList;
    }

    /**
     * 
     * 描述 获取未收取材料列表
     * 
     * @author Faker Li
     * @created 2016年1月13日 下午4:04:25
     * @param exeId
     * @return
     */
    public Set<String> findwsqcllb(String busId, String busTable, String itemId, String exeId) {
        Set<String> s = new HashSet<String>();
        // 获取材料信息列表
        List<Map<String, Object>> filesMap = applyMaterService.findByItemId(itemId, exeId);
        filesMap = applyMaterService.setUploadFiles(busId, busTable, filesMap);
        for (int i = 0; i < filesMap.size(); i++) {
            String SFSQ = filesMap.get(i).get("SFSQ") == null ? "-1" : filesMap.get(i).get("SFSQ").toString();
            if (!SFSQ.equals("1")) {
                s.add(filesMap.get(i).get("MATER_CODE").toString());
            }

        }
        return s;
    }

    /**
     * 
     * 描述 获取已收取材料列表
     * 
     * @author Faker Li
     * @created 2016年1月13日 下午4:04:25
     * @return
     */
    public Set<String> findysqcllb(String busId, String busTable) {
        Set<String> s = new HashSet<String>();
        List<Map<String, Object>> filesMap = fileAttachService.findList(busTable, busId);
        for (int i = 0; i < filesMap.size(); i++) {
            if (filesMap.get(i).get("SQFS").toString().equals("1")) {
                s.add(filesMap.get(i).get("ATTACH_KEY").toString());
            } else {
                if (filesMap.get(i).get("SFSQ").toString().equals("1")) {
                    s.add(filesMap.get(i).get("ATTACH_KEY").toString());
                }
            }
        }
        return s;
    }

    /**
     * 
     * 描述：获取特殊环节
     * @author Water Guo
     * @created 2017-4-5 上午11:33:09
     * @param itemId
     * @return
     */
    public String getTshj(String itemId) {
        StringBuffer sbTS = new StringBuffer();
        String tshj = "";
        String link_limittime = "";
        List<Map<String, Object>> tshjList = departmentService.getAllByJdbc("T_WSBS_SERVICEITEM_LINK",
                new String[] { "ITEM_ID" }, new Object[] { itemId });
        if (tshjList.size() > 0) {
            for (int i = 0; i < tshjList.size(); i++) {
                int j = i + 1;
                sbTS.append("    特殊环节" + j + "");
                sbTS.append("      名称: ");
                sbTS.append(tshjList.get(i).get("LINK_NAME"));
                sbTS.append("      时限: ");
                link_limittime = tshjList.get(i).get("LINK_LIMITTIME") == null ? ""
                        : tshjList.get(i).get("LINK_LIMITTIME").toString();
                if ("0".equals(link_limittime)) {
                    link_limittime = "无";
                    sbTS.append(link_limittime);
                } else {
                    sbTS.append(link_limittime + " 个工作日");
                }
                sbTS.append("      设定依据: ");
                sbTS.append(tshjList.get(i).get("LINK_BASIS"));
                sbTS.append("\n");
            }
            tshj = sbTS.substring(0, sbTS.lastIndexOf("\n"));
        } else {
            tshj = "  无";
        }
        return tshj;
    }

    /**
     * 
     * 描述：获取特殊环节名称
     * @author Water Guo
     * @created 2017-4-5 上午11:33:09
     * @param itemId
     * @return
     */
    public String getTshjName(String itemId) {
        StringBuffer sbTS = new StringBuffer();
        String tshj = "";
        String link_limittime = "";
        List<Map<String, Object>> tshjList = departmentService.getAllByJdbc("T_WSBS_SERVICEITEM_LINK",
                new String[] { "ITEM_ID" }, new Object[] { itemId });
        if (tshjList.size() > 0) {
            for (int i = 0; i < tshjList.size(); i++) {
                int j = i + 1;
                // sbTS.append(" 特殊环节"+j+"");
                sbTS.append(" " + j);
                sbTS.append("      名称: ");
                sbTS.append(tshjList.get(i).get("LINK_NAME"));
                sbTS.append("      时限: ");
                link_limittime = tshjList.get(i).get("LINK_LIMITTIME") == null ? ""
                        : tshjList.get(i).get("LINK_LIMITTIME").toString();
                if ("0".equals(link_limittime)) {
                    link_limittime = "无";
                    sbTS.append(link_limittime);
                } else {
                    sbTS.append(link_limittime + " 个工作日");
                }
                // sbTS.append(" 设定依据: ");
                // sbTS.append(tshjList.get(i).get("LINK_BASIS"));
                sbTS.append(";");
            }
            tshj = sbTS.substring(0, sbTS.lastIndexOf(";"));
        } else {
            tshj = "  无";
        }
        return tshj;
    }

    /**
     * 
     * 描述 清楚map的空字符串
     * 
     * @author Faker Li
     * @created 2016年2月29日 下午6:07:44
     * @param templateData
     * @return
     */
    private Map<String, Object> cleanMap(Map<String, Object> templateData) {
        for (String o : templateData.keySet()) {
            String content = templateData.get(o) == null ? "" : templateData.get(o).toString();
            // if (StringUtils.isEmpty((String) templateData.get(o))) {
            if (StringUtils.isEmpty(content)) {
                templateData.put(o, "  ");
            } else {
                if (!(o.equals("sqclbclb") && o.equals("ysqcllb"))) {
                    // templateData.put(o, ((String) templateData.get(o)).replaceAll("\r|\n", " "));
                    templateData.put(o, content.replaceAll("\r|\n", "  "));
                }
            }
        }
        return templateData;
    }

    /**
     * 
     * 描述 获取并联审查的回填数据
     * 
     * @author Faker Li
     * @created 2016年4月20日 上午11:16:30
     * @param templateData
     * @param exeId
     */
    public void getBlscData(Map<String, Object> templateData, String exeId) {
        List<Map<String, Object>> blscDicList = dictionaryService.findByTypeCode("allOpinion");
        List<Map<String, Object>> hsblscDicList = dictionaryService.findByTypeCode("twoallOpinion");
        List<Map<String, Object>> blscflowTaskList = flowTaskService.findTaskList(exeId, "并联审查");
        List<Map<String, Object>> hsblscflowTaskList = flowTaskService.findTaskList(exeId, "并联 审查");
        for (int i = 0; i < blscDicList.size(); i++) {
            Map<String, Object> dicMap = blscDicList.get(i);
            String dic_desc = (String) dicMap.get("DIC_DESC");
            StringBuffer str = new StringBuffer();
            for (int j = 0; j < blscflowTaskList.size(); j++) {
                Map<String, Object> taskMap = blscflowTaskList.get(j);
                Map<String, Object> userInfo = sysUserService.getUserInfo((String) taskMap.get("ASSIGNER_CODE"));
                if (userInfo != null) {
                    if (dic_desc.equals((String) userInfo.get("DEPART_ID"))) {
                        str.append((String) taskMap.get("HANDLE_OPINION")).append(" ");
                    }
                }
            }
            templateData.put((String) dicMap.get("DIC_CODE"), str);
        }
        for (int i = 0; i < hsblscDicList.size(); i++) {
            Map<String, Object> dicMap = hsblscDicList.get(i);
            String dic_desc = (String) dicMap.get("DIC_DESC");
            StringBuffer str = new StringBuffer();
            for (int j = 0; j < hsblscflowTaskList.size(); j++) {
                Map<String, Object> taskMap = hsblscflowTaskList.get(j);
                Map<String, Object> userInfo = sysUserService.getUserInfo((String) taskMap.get("ASSIGNER_CODE"));
                if (userInfo != null) {
                    if (dic_desc.equals((String) userInfo.get("DEPART_ID"))) {
                        str.append((String) taskMap.get("HANDLE_OPINION")).append(" ");
                    }
                }
            }
            templateData.put((String) dicMap.get("DIC_CODE"), str.toString());
        }
    }

    /**
     * 描述 获取不动产权力人数据
     * 
     * @author Reuben Bao
     * @created 2019年4月4日 上午11:26:42
     * @param busTableName
     * @param busRecordId
     * @param returnMap
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getPowerPeopleList(String busTableName, String busRecordId, Map<String, Object> returnMap) {
        if (StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(busTableName)) {
            //国有转移拆分的7个事项,将虚拟主表名替换真实主表名称
            if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                busTableName = "T_BDCQLC_GYJSJFWZYDJ";
            }
            // 查询业务表获取权力人数据
            String primaryKeyName = String.valueOf(this.getPrimaryKeyName(busTableName).get(0));
            Map<String, Object> busMap = dao.getByJdbc(busTableName, new String[] { primaryKeyName },
                    new Object[] { busRecordId });
            // 通用型数据设值
            this.setBdcNormalData(busMap, returnMap);
            // 抵押登记
            this.setDyqxData(busMap, returnMap);
            // 国有建设权属数据
            this.setDyqxDataForGyjs(busMap, returnMap);
            // 抵押注销权力人数据
            this.setDyzsData(busMap, returnMap);
            // 预购商品房数据
            this.setYgspfData(busMap, returnMap);
            // 换发不动产
            this.setHfBdcData(busMap, returnMap);
        }
    }

    /**
     * 描述 换发不动产数据设值
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 下午2:02:56
     * @param hfType
     * @param busRecordId
     */
    @SuppressWarnings("unchecked")
    public void setHfBdcData(Map<String, Object> busMap, Map<String, Object> returnMap) {
        String hfType = StringUtil.getValue(busMap, "HF_TYPE");
        String busRecordId = StringUtil.getValue(busMap, "YW_ID");
        if (StringUtils.isNotEmpty(hfType) && StringUtils.isNotEmpty(busRecordId)) {
            Map<String, Object> record = bdcApplyService.getSubRecordInfo(hfType, busRecordId);
            // 换发不动产面积
            // 换发不动产权证书和不动产登记证明（换发产权登记）为界面上的宗地面积与建筑面积。显示格式为：显示格式为：宗地面积：XX m²/房屋建筑面积：XX m²。
            // 换发不动产权证书和不动产登记证明（换发抵押权登记证明）为界面上的面积。
            // 换发不动产权证书和不动产登记证明（换发预告登记证明、换发抵押权预告登记证明）为界面上的建筑面积。
            StringBuffer area = new StringBuffer();
            if ("1".equals(hfType)) {
                // 权力人信息json
                String obJson = StringUtil.getValue(record, "POWERPEOPLEINFO_JSON");
                // 设值权力人信息数据
                this.setHfBdcPowerData(obJson, returnMap, "POWERPEOPLENAME", "POWERPEOPLEIDTYPE", "POWERPEOPLEIDNUM",
                        "POWLEGALNAME", "POWERPEOPLEMOBILE", "POWAGENTNAME", "");
                area.append("宗地面积：").append(StringUtil.getValue(record, "ZD_MJ")).append(" m²/").append("建筑面积：")
                        .append(StringUtil.getValue(record, "FW_JZMJ")).append(" m²");
                returnMap.put("mj", area.toString());
                // 用途说明
                if (StringUtils.isNotEmpty(StringUtil.getValue(record, "ZD_YTSM"))) {
                    returnMap.put("yt", StringUtil.getValue(record, "ZD_YTSM"));
                }
                // 权属数据
                String sourceJson = StringUtil.getValue(record, "POWERSOURCEINFO_JSON");
                if (StringUtils.isNotEmpty(sourceJson) && !"[]".equals(sourceJson)) {
                    Map<String, Object> sourceMap = ((List<Map<String, Object>>) JSON.parse(sourceJson)).get(0);
                    // 权属证书号
                    returnMap.put("ybdcqszsh", StringUtil.getValue(sourceMap, "POWERSOURCE_QSWH"));
                }
            }
            if ("2".equals(hfType)) {
                // 权力人信息json
                String obJson2 = StringUtil.getValue(record, "POWERPEOPLEINFO_JSON");
                this.setHfBdcPowerData(obJson2, returnMap, "QLR_DYQR", "", "QLR_DYQRZJHM", "QLR_LEGALNAME",
                        "QLR_DYQRDH", "QLR_DLRNAME", "QLR_DYQRZJLX");
                returnMap.put("mj", StringUtil.getValue(record, "JZMJ") + " m²");
                returnMap.put("bdcdyh", StringUtil.getValue(record, "DAXX_BDCDYH"));
                returnMap.put("ybdcqszsh", StringUtil.getValue(record, "BDCQZH"));
                returnMap.put("djyy", StringUtil.getValue(record, "DJYY"));
                returnMap.put("zwlxqx",
                        StringUtil.getValue(record, "QLQSSJ") + "~" + StringUtil.getValue(record, "QLJSSJ"));
                returnMap.put("bdbzqse", StringUtil.getValue(record, "BDBZZQSE"));
            }
            if ("3".equals(hfType)) {
                // 权力人姓名
                returnMap.put("qlrxm", StringUtil.getValue(record, "JBXX_QLR"));
                // 权力人证件类型
                returnMap.put("sfzjzl1", StringUtil.getValue(record, "JBXX_ZJZL"));
                // 权力人证件号
                returnMap.put("zjh1", StringUtil.getValue(record, "JBXX_ZJHM"));
                // 义务人姓名
                returnMap.put("ywrxm", StringUtil.getValue(record, "JBXX_YWR"));
                // 法定代表人电话
                returnMap.put("sfzjzl2", StringUtil.getValue(record, "JBXX_YWRZJLB"));
                // 义务人证件号
                returnMap.put("zjh2", StringUtil.getValue(record, "JBXX_YWRZJHM"));
                returnMap.put("yt", StringUtil.getValue(record, "YTSM"));
                returnMap.put("mj", StringUtil.getValue(record, "JZMJ"));
                returnMap.put("zwlxqx",
                        StringUtil.getValue(record, "QSSJ") + "~" + StringUtil.getValue(record, "JSSJ"));
                returnMap.put("bdbzqse", StringUtil.getValue(record, "QDJG"));
                returnMap.put("ybdcqszsh", StringUtil.getValue(record, "BDCDJZMH"));
                returnMap.put("djyy", StringUtil.getValue(record, "JBXX_DJYY"));
            }
            if("4".equals(hfType)) {
                // 权力人姓名
                returnMap.put("qlrxm", StringUtil.getValue(record, "QLR_MC"));
                // 权力人证件类型
                returnMap.put("sfzjzl1", StringUtil.getValue(record, "QLR_ZJLX"));
                // 权力人证件号
                returnMap.put("zjh1", StringUtil.getValue(record, "QLR_ZJNO"));
                // 义务人姓名
                returnMap.put("ywrxm", StringUtil.getValue(record, "YWR_MC"));
                // 义务人证件类型
                returnMap.put("sfzjzl2", StringUtil.getValue(record, "QLR_ZJLX"));
                // 义务人证件号
                returnMap.put("zjh2", StringUtil.getValue(record, "YWR_ZJNO"));
                returnMap.put("mj", StringUtil.getValue(record, "DJXX_JZAREA"));
                returnMap.put("zwlxqx",
                        StringUtil.getValue(record, "ZW_BEGIN") + "~" + StringUtil.getValue(record, "ZW_END"));
                returnMap.put("bdbzqse", StringUtil.getValue(record, "DBSE"));
                returnMap.put("ybdcqszsh", StringUtil.getValue(record, "DJXX_CQZH"));
                returnMap.put("djyy", StringUtil.getValue(record, "DJ_REASON"));
            }
        }

    }

    /**
     * 描述 换发不动产权力人数据
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 下午4:17:07
     * @param obJson
     * @param returnMap
     * @param qlrxm 权力人姓名
     * @param qlrsflx 权力人证件类型
     * @param qlrzjh 权力人证件号
     * @param fddbrxm 法定代表人姓名
     * @param qlrdh 权力人电话
     * @param dlrxm 代理人姓名
     * @param qlrzjlxmc 权力证件类型名称
     */
    @SuppressWarnings("unchecked")
    public void setHfBdcPowerData(String obJson, Map<String, Object> returnMap, String qlrxm, String qlrsflx,
            String qlrzjh, String fddbrxm, String qlrdh, String dlrxm, String qlrzjlxmc) {
        // 权力人名称
        StringBuffer powerPeopleName = new StringBuffer();
        // 权力人证件类型
        StringBuffer powerPeopleCardTypeName = new StringBuffer();
        // 权力人证件证号码
        StringBuffer powerPeopleCardTypeId = new StringBuffer();
        // 权力信息中法定代表人
        StringBuffer powerPer = new StringBuffer();
        // 法定代表人电话
        StringBuffer powerPerTel = new StringBuffer();
        // 代理人
        StringBuffer powagentName = new StringBuffer();
        // 权力证件类型，数据库直接报错名称的情况
        StringBuffer qlrzjlx = new StringBuffer();
        // 设值权力人信息数据
        if (StringUtils.isNotEmpty(obJson) && !"[]".equals(obJson)) {
            List<Map<String, Object>> powerPeopleList = (List<Map<String, Object>>) JSON.parse(obJson);
            if (powerPeopleList != null && powerPeopleList.size() > 0) {
                int length = powerPeopleList.size();
                for (Map<String, Object> powerPeopleMap : powerPeopleList) {
                    if (StringUtils.isNotEmpty(qlrxm)) {
                        powerPeopleName.append(StringUtil.getValue(powerPeopleMap, qlrxm))
                                .append(length > 1 ? "," : "");
                    }
                    if (StringUtils.isNotEmpty(qlrsflx)) {
                        powerPeopleCardTypeName.append(StringUtil.getValue(powerPeopleMap, qlrsflx))
                                .append(length > 1 ? "," : "");
                    }
                    if (StringUtils.isNotEmpty(qlrzjh)) {
                        powerPeopleCardTypeId.append(StringUtil.getValue(powerPeopleMap, qlrzjh))
                                .append(length > 1 ? "," : "");
                    }

                    if (StringUtils.isNotEmpty(fddbrxm)) {
                        powerPer.append(StringUtil.getValue(powerPeopleMap, fddbrxm)).append(length > 1 ? "," : "");
                    }

                    if (StringUtils.isNotEmpty(qlrdh)) {
                        powerPerTel.append(StringUtil.getValue(powerPeopleMap, qlrdh)).append(length > 1 ? "," : "");
                    }

                    if (StringUtils.isNotEmpty(dlrxm)) {
                        powagentName.append(StringUtil.getValue(powerPeopleMap, dlrxm)).append(length > 1 ? "," : "");
                    }

                    if (StringUtils.isNotEmpty(qlrzjlxmc)) {
                        qlrzjlx.append(StringUtil.getValue(powerPeopleMap, qlrzjlxmc)).append(length > 1 ? "," : "");
                    }

                }
            }
        }
        // 权力人姓名
        returnMap.put("qlrxm", powerPeopleName.toString());
        // 权力人证件类型
        if (StringUtils.isNotEmpty(qlrzjlx.toString())) {
            returnMap.put("sfzjzl1", qlrzjlx.toString());
        } else if (StringUtils.isNotEmpty(powerPeopleCardTypeName.toString())) {
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "DocumentType", powerPeopleCardTypeName.toString() });
            returnMap.put("sfzjzl1", StringUtil.getValue(dictionary, "DIC_NAME"));
        }
        // 权力人证件号
        returnMap.put("zjh1", powerPeopleCardTypeId.toString());
        // 法定代表人姓名
        returnMap.put("dbr1", powerPer.toString());
        // 法定代表人电话
        returnMap.put("dh1", powerPerTel.toString());
        // 代理人姓名
        returnMap.put("dlrxm1", powagentName.toString());
    }

    /**
     * 描述 通用型数据设值
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 上午11:29:56
     * @param busMap
     * @param returnMap
     */
    @SuppressWarnings("unchecked")
    public void setBdcNormalData(Map<String, Object> busMap, Map<String, Object> returnMap) {

        // 权力人信息json
        String obJson = StringUtil.getValue(busMap, "POWERPEOPLEINFO_JSON");
        // 权力信息
        String powerJson = StringUtil.getValue(busMap, "POWERINFO_JSON");
        // 抵押权面积数据
        // 抵押宗地总面积
        String dyzszmj = StringUtil.getValue(busMap, "DYQDJ_DYZSZMJ");
        // 抵押建筑总面积
        String dyjzzmj = StringUtil.getValue(busMap, "DYQDJ_DYJZZMJ");
        // 抵押分摊土地总面积
        String dyfttdzmj = StringUtil.getValue(busMap, "DYQDJ_DYFTTDZMJ");
        // 抵押独用土地面积
        String dytdmj = StringUtil.getValue(busMap, "DYQDJ_DYTDMJ");
        StringBuffer area = new StringBuffer();
        if (StringUtils.isNotEmpty(dyzszmj) || StringUtils.isNotEmpty(dyjzzmj) || StringUtils.isNotEmpty(dyfttdzmj)
                || StringUtils.isNotEmpty(dytdmj)) {
            area.append("抵押宗地总面积：").append(dyzszmj).append(" m²/").append("抵押建筑总面积：").append(dyjzzmj).append(" m²/")
                    .append("抵押独用土地面积：").append(dyfttdzmj).append(" m²/").append("抵押独用土地面积：").append(dytdmj)
                    .append(" m²");
            returnMap.put("mj", area.toString());
        }
        if (StringUtils.isNotEmpty(powerJson) && !"[]".equals(powerJson)) {
            // 设值权力信息数据
            Map<String, Object> powerMap = ((List<Map<String, Object>>) JSON.parse(powerJson)).get(0);
            // 不动产由两个部分组成：土地使用权面积（宗地面积）+建筑面积
            String jzmj = StringUtil.getValue(powerMap, "jzarea");
            // 若建筑面积不为空，追加展示建筑面积数据
            // 土地使用权面积为宗地面积。显示格式为：宗地面积：XX m²/房屋建筑面积：XX m²
            if (StringUtils.isNotEmpty(jzmj)) {
                area.append("宗地面积：").append(StringUtil.getValue(powerMap, "area")).append(" m²/").append("建筑面积：")
                        .append(StringUtil.getValue(powerMap, "jzarea")).append(" m²");
            } else if (StringUtils.isNotEmpty(StringUtil.getValue(powerMap, "area"))) {
                area.append(StringUtil.getValue(powerMap, "area")).append(" m²");
            }
            returnMap.put("mj", area.toString());
            // 用途
            returnMap.put("yt", StringUtil.getValue(powerMap, "ytsm"));
            // 登记原因
            if(StringUtils.isNotEmpty(StringUtil.getValue(powerMap, "notes"))) {
                returnMap.put("djyy", StringUtil.getValue(powerMap, "notes"));
            }
            // 国有建设用地使用权及房屋所有权转移登记用途字段
            String yt = null;
            if(StringUtils.isNotEmpty(StringUtil.getValue(powerMap, "power_used_for"))){
                yt = StringUtil.getValue(powerMap, "power_used_for");
            }
            if(StringUtils.isNotEmpty(StringUtil.getValue(powerMap, "ghytsm"))){
                if(yt!=null){
                    yt += " / " + StringUtil.getValue(powerMap, "ghytsm");
                }else{
                    yt = StringUtil.getValue(powerMap, "ghytsm");
                }
            }
            if(yt!=null){
                returnMap.put("yt", yt);
            }
        }
        // 设值权力人信息数据
        this.setPowerData(obJson, returnMap);
        // 坐落
        returnMap.put("zl", StringUtil.getValue(busMap, "LOCATED"));
        if(StringUtils.isNotEmpty(StringUtil.getValue(busMap, "ZL"))) {
            returnMap.put("zl", StringUtil.getValue(busMap, "ZL"));
        }
    }

    /**
     * 描述 通用权力人数据
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 下午2:26:26
     * @param obJson
     * @param returnMap
     */
    @SuppressWarnings("unchecked")
    public void setPowerData(String obJson, Map<String, Object> returnMap) {
        // 权力人名称
        StringBuffer powerPeopleName = new StringBuffer();
        // 权力人证件类型
        StringBuffer powerPeopleCardTypeName = new StringBuffer();
        // 权力人证件证号码
        StringBuffer powerPeopleCardTypeId = new StringBuffer();
        // 权力信息中法定代表人
        StringBuffer powerPer = new StringBuffer();
        // 法定代表人电话
        StringBuffer powerPerTel = new StringBuffer();
        StringBuffer powerIdTypeName = new StringBuffer();
        StringBuffer dlrxm = new StringBuffer();
        // 设值权力人信息数据
        if (StringUtils.isNotEmpty(obJson) && !"[]".equals(obJson)) {
            List<Map<String, Object>> powerPeopleList = (List<Map<String, Object>>) JSON.parse(obJson);
            if (powerPeopleList != null && powerPeopleList.size() > 0) {
                int length = powerPeopleList.size();
                for (Map<String, Object> powerPeopleMap : powerPeopleList) {
                    powerPeopleName.append(StringUtil.getValue(powerPeopleMap, "POWERPEOPLENAME"))
                            .append(length > 1 ? "," : "");
                    powerPeopleCardTypeName.append(StringUtil.getValue(powerPeopleMap, "POWAGENTIDTYPE"))
                            .append(length > 1 ? "," : "");
                    powerPeopleCardTypeId.append(StringUtil.getValue(powerPeopleMap, "POWERPEOPLEIDNUM"))
                            .append(length > 1 ? "," : "");
                    powerPer.append(StringUtil.getValue(powerPeopleMap, "POWLEGALNAME")).append(length > 1 ? "," : "");
                    powerPerTel.append(StringUtil.getValue(powerPeopleMap, "POWERPEOPLEMOBILE"))
                            .append(length > 1 ? "," : "");
                    // 宅基地权力人证件类型
                    powerIdTypeName.append(StringUtil.getValue(powerPeopleMap, "POWERPEOPLEIDTYPE"))
                    .append(length > 1 ? "," : "");
                    // 宅基地代理人名称
                    dlrxm.append(StringUtil.getValue(powerPeopleMap, "POWAGENTNAME"))
                    .append(length > 1 ? "," : "");
                }
            }
        }
        // 权力人姓名
        returnMap.put("qlrxm", powerPeopleName.toString());
        // 权力人证件类型
        if (StringUtils.isNotEmpty(powerPeopleCardTypeName.toString())) {
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "DocumentType", powerPeopleCardTypeName.toString() });
            returnMap.put("sfzjzl1", StringUtil.getValue(dictionary, "DIC_NAME"));
        }
        if (StringUtils.isNotEmpty(powerIdTypeName.toString())) {
            Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                    new String[] { "TYPE_CODE", "DIC_CODE" },
                    new Object[] { "DocumentType", powerIdTypeName.toString() });
            returnMap.put("sfzjzl1", StringUtil.getValue(dictionary, "DIC_NAME"));
        }
        if(StringUtils.isNotEmpty(dlrxm.toString())) {
            returnMap.put("dlrxm1", dlrxm.toString());
        }
        // 权力人证件号
        returnMap.put("zjh1", powerPeopleCardTypeId.toString());
        // 法定代表人姓名
        returnMap.put("dbr1", powerPer.toString());
        // 法定代表人电话
        returnMap.put("dh1", powerPerTel.toString());
    }

    /**
     * 描述 预购商品房数据
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 上午11:13:53
     * @param busMap
     * @param returnMap
     */
    public void setYgspfData(Map<String, Object> busMap, Map<String, Object> returnMap) {
        // 权力人
        String msfxm = StringUtil.getValue(busMap, "MSFXM");
        if (StringUtils.isNotEmpty(msfxm)) {
            returnMap.put("qlrxm", msfxm);
        }
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "MSFZJLB"))) {
            returnMap.put("sfzjzl1", StringUtil.getValue(busMap, "MSFZJLB"));
        }
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "MSFZJHM"))) {
            returnMap.put("zjh1", StringUtil.getValue(busMap, "MSFZJHM"));
        }
        // 代理人
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "LZRXM"))) {
            returnMap.put("dlrxm1", StringUtil.getValue(busMap, "LZRXM"));
        }
        // 义务人
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "ZRFXM"))) {
            returnMap.put("ywrxm", StringUtil.getValue(busMap, "ZRFXM"));
        }
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "ZRFZJLB"))) {
            returnMap.put("sfzjzl2", StringUtil.getValue(busMap, "ZRFZJLB"));
        }
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "ZRFZJHM"))) {
            returnMap.put("zjh2", StringUtil.getValue(busMap, "ZRFZJHM"));
        }
        // 代理人2
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "DLRXM"))) {
            returnMap.put("dlrxm2", StringUtil.getValue(busMap, "DLRXM"));
        }
        // 面积
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "JZMJ"))) {
            returnMap.put("mj", StringUtil.getValue(busMap, "JZMJ"));
        }
        // 用途
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "YTMS"))) {
            returnMap.put("yt", StringUtil.getValue(busMap, "YTMS"));
        }
        // 登记原因
        if (StringUtils.isNotEmpty(StringUtil.getValue(busMap, "DJYY"))) {
            returnMap.put("djyy", StringUtil.getValue(busMap, "DJYY"));
        }
    }

    /**
     * 描述 抵押注销权力人数据
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 上午10:55:04
     * @param busMap
     * @param returnMap
     */
    @SuppressWarnings("unchecked")
    public void setDyzsData(Map<String, Object> busMap, Map<String, Object> returnMap) {
        // 注销明细
        String zxmxJson = StringUtil.getValue(busMap, "ZXMX_JSON");
        if (StringUtils.isNotEmpty(zxmxJson) && !"[]".equals(zxmxJson)) {
            Map<String, Object> zxmx = ((List<Map<String, Object>>) JSON.parse(zxmxJson)).get(0);
            // 抵押权人姓名
            String dyqr = StringUtil.getValue(zxmx, "DYQR");
            if (StringUtils.isNotEmpty(dyqr)) {
                returnMap.put("qlrxm", dyqr);
            }
            // 抵押人姓名
            String dyr = StringUtil.getValue(zxmx, "DYR");
            if (StringUtils.isNotEmpty(dyr)) {
                returnMap.put("ywrxm", dyr);
            }
            
            //动态表格设置
            List<Map<String, Object>> zxmxList = (List<Map<String, Object>>) JSON.parse(zxmxJson);          
            Map<String, Object> regTable = null;
            if (null != returnMap.get("regTable")) {
                regTable = (Map<String, Object>) returnMap.get("regTable");
            } else {
                regTable = new HashMap<String, Object>();
            }
            List<List<String>> newZxmxList = new LinkedList<List<String>>();
            List<String> newZxmx = null;
            for (int i = 0; i < zxmxList.size(); i++) {
                Map<String, Object> map = zxmxList.get(i);
                newZxmx = new LinkedList<String>();
                newZxmx.add(i+1+"");//序号
                newZxmx.add(map.get("BDCQZH")==null?"":map.get("BDCQZH").toString());//不动产权属证书
                newZxmx.add(map.get("DYQR")==null?"":map.get("DYQR").toString());//权利人
                newZxmx.add(map.get("DYBDCLX")==null?"":map.get("DYBDCLX").toString());//不动产权利类型
                newZxmx.add(map.get("ZDDM")==null?"":map.get("ZDDM").toString());//宗地/宗海号
                newZxmx.add(map.get("ZJJZWZL")==null?"":map.get("ZJJZWZL").toString());//不动产坐落（名称）
                newZxmx.add(map.get("FJ")==null?"":map.get("FJ").toString());//备注
                newZxmxList.add(newZxmx);
            }
            regTable.put("PO_regTable_1", newZxmxList);//绑定动态表格书签
            returnMap.put("regTable", regTable);
        }
    }

    /**
     * 描述 国有建设权属数据
     * 
     * @author Reuben Bao
     * @created 2019年4月11日 上午9:52:04
     * @param busMap
     * @param returnMap
     */
    @SuppressWarnings("unchecked")
    public void setDyqxDataForGyjs(Map<String, Object> busMap, Map<String, Object> returnMap) {
        // 申请人2栏权力人名称
        StringBuffer powerPeopleName = new StringBuffer();
        // 申请人2权力人证件类型
        StringBuffer powerPeopleCardTypeName = new StringBuffer();
        // 申请人2权力人证件证号码
        StringBuffer powerPeopleCardTypeId = new StringBuffer();
        // 申请人2权力信息中法定代表人
        StringBuffer powerPer = new StringBuffer();
        // 申请人2法定代表人电话
        StringBuffer powerPerTel = new StringBuffer();
        // 权属文号
        StringBuffer powersourceidNum = new StringBuffer();
        // 代理人2
        StringBuffer dlrxmreg = new StringBuffer();
        // 代理人2电话
        StringBuffer dlrregdh = new StringBuffer();
        // 权属文号
        StringBuffer qswh = new StringBuffer();
        // 国有建设权属信息数据
        String powersourceinfoJson = StringUtil.getValue(busMap, "POWERSOURCEINFO_JSON");
        // 设值权力人信息数据
        if (StringUtils.isNotEmpty(powersourceinfoJson) && !"[]".equals(powersourceinfoJson)) {
            List<Map<String, Object>> powerPeopleList = (List<Map<String, Object>>) JSON.parse(powersourceinfoJson);
            if (powerPeopleList != null && powerPeopleList.size() > 0) {
                int length = powerPeopleList.size();
                for (Map<String, Object> powerPeopleMap : powerPeopleList) {
                    powerPeopleName.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_QLRMC"))
                            .append(length > 1 ? "," : "");
                    powerPeopleCardTypeName.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_ZJLX"))
                            .append(length > 1 ? "," : "");
                    powerPeopleCardTypeId.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_ZJHM"))
                            .append(length > 1 ? "," : "");
                    powerPer.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_FRDB"))
                            .append(length > 1 ? "," : "");
                    powerPerTel.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_FRDH"))
                            .append(length > 1 ? "," : "");
                    powersourceidNum.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCEIDNUM"))
                    .append(length > 1 ? "," : "");
                    dlrxmreg.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_DLRXM"))
                    .append(length > 1 ? "," : "");
                    dlrregdh.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_DLRDH"))
                    .append(length > 1 ? "," : "");
                    qswh.append(StringUtil.getValue(powerPeopleMap, "POWERSOURCE_QSWH"))
                    .append(length > 1 ? "," : "");
                }
            }
        }

        if (StringUtils.isNotEmpty(powerPeopleName.toString())) {
            // 义务人姓名
            returnMap.put("ywrxm", powerPeopleName.toString());
        }
        if (StringUtils.isNotEmpty(qswh.toString())) {
            // 权属文号
            returnMap.put("ybdcqszsh", qswh.toString());
        }
        if (StringUtils.isNotEmpty(powerPeopleCardTypeName.toString())) {
            // 申请人2栏证件类型
            returnMap.put("sfzjzl2", powerPeopleCardTypeName.toString());
        }
        if (StringUtils.isNotEmpty(dlrregdh.toString())) {
            // 申请人2栏代理人电话
            returnMap.put("dh4", dlrregdh.toString());
        }
        if (StringUtils.isNotEmpty(dlrxmreg.toString())) {
            // 申请人2栏代理人2
            returnMap.put("dlrxm2", dlrxmreg.toString());
        }
        if (StringUtils.isNotEmpty(powerPeopleCardTypeId.toString())) {
            // 申请人2栏身份证号
            returnMap.put("zjh2", powerPeopleCardTypeId.toString());
        }
        if (StringUtils.isNotEmpty(powerPer.toString())) {
            // 申请人2法定代表人
            returnMap.put("dbr2", powerPer.toString());
        }
        if (StringUtils.isNotEmpty(powerPerTel.toString())) {
            // 申请人2法定代表人电话
            returnMap.put("dh3", powerPerTel.toString());
        }
        if (StringUtils.isNotEmpty(powersourceidNum.toString())) {
            // 权属文号
            returnMap.put("ybdcqszsh", powersourceidNum.toString());
        }
        // 被担保债权数额
        String powersourceDbse = StringUtil.getValue(busMap, "POWERSOURCE_DBSE");
        // 被担保债权数额
        String powersourceDyfw = StringUtil.getValue(busMap, "POWERSOURCE_DYFW");
        //
        String powersourceQlqssj = StringUtil.getValue(busMap, "POWERSOURCE_QLQSSJ");
        String powersourceQljssj = StringUtil.getValue(busMap, "POWERSOURCE_QLJSSJ");
        if (StringUtils.isNotEmpty(powersourceDbse)) {
            returnMap.put("bdbzqse", powersourceDbse);
        }
        if (StringUtils.isNotEmpty(powersourceDyfw)) {
            returnMap.put("zjjzwdyfw", powersourceDyfw);
        }
        if (StringUtils.isNotEmpty(powersourceQlqssj) || StringUtils.isNotEmpty(powersourceQljssj)) {
            returnMap.put("zwlxqx", powersourceQlqssj + "~" + powersourceQljssj);
        }
    }

    /**
     * 描述 抵押登记
     * 
     * @author Reuben Bao
     * @created 2019年4月9日 下午5:00:38
     * @param busMap
     * @param returnMap
     */
    public void setDyqxData(Map<String, Object> busMap, Map<String, Object> returnMap) {
        // 抵押型模板权力人信息取抵押信息数据
        // 登记申请人1栏，权利人信息为抵押权人信息；登记申请人2栏，义务人信息为抵押登记信息中抵押人信息
        String dyqrxxName = StringUtil.getValue(busMap, "DYQRXX_NAME");
        String dyqrxxIdno = StringUtil.getValue(busMap, "DYQRXX_IDNO");
        String dyqrxxLegalName = StringUtil.getValue(busMap, "DYQRXX_LEGAL_NAME");
        String dyqrxxLegalIdtype = StringUtil.getValue(busMap, "DYQRXX_LEGAL_IDTYPE");
        String dyqrxxAgentName = StringUtil.getValue(busMap, "DYQRXX_AGENT_NAME");
        // 权力人证件号
        if (StringUtils.isNotEmpty(dyqrxxIdno)) {
            returnMap.put("zjh1", dyqrxxIdno);
        }
        if (StringUtils.isNotEmpty(dyqrxxName)) {
            // 权力人姓名
            returnMap.put("qlrxm", dyqrxxName);
        }
        if (StringUtils.isNotEmpty(dyqrxxLegalName)) {
            // 法定代表人姓名
            returnMap.put("dbr1", dyqrxxLegalName);
        }
        if (StringUtils.isNotEmpty(dyqrxxAgentName)) {
            // 代理人姓名
            returnMap.put("dlrxm1", dyqrxxAgentName);
        }
        if (StringUtils.isNotEmpty(dyqrxxLegalIdtype)) {
            // 证件类型
            returnMap.put("sfzjzl1", dyqrxxLegalIdtype);
        }
        // 申请人2栏数据
        String dyqdjName = StringUtil.getValue(busMap, "DYQDJ_NAME");
        String dyqdjIdno = StringUtil.getValue(busMap, "DYQDJ_IDNO");
        String dyqrxxIdtype = StringUtil.getValue(busMap, "DYQDJ_IDTYPE");
        String dyqdjZwqssj = StringUtil.getValue(busMap, "DYQDJ_ZWQSSJ");
        String dyqdjZwjssj = StringUtil.getValue(busMap, "DYQDJ_ZWJSSJ");
        String dyqdjAgentName = StringUtil.getValue(busMap, "DYQDJ_AGENT_NAME");
        if (StringUtils.isNotEmpty(dyqdjName)) {
            // 义务人姓名
            returnMap.put("ywrxm", dyqdjName);
        }
        if (StringUtils.isNotEmpty(dyqrxxIdtype)) {
            // 申请人2栏身份证号
            returnMap.put("sfzjzl2", dyqrxxIdtype);
        }
        if (StringUtils.isNotEmpty(dyqdjIdno)) {
            // 申请人2栏身份证号
            returnMap.put("zjh2", dyqdjIdno);
        }
        if (StringUtils.isNotEmpty(dyqdjZwqssj) || StringUtils.isNotEmpty(dyqdjZwjssj)) {
            // 债务履行期限
            returnMap.put("zwlxqx", dyqdjZwqssj + "~" + dyqdjZwjssj);
        }
        if (StringUtils.isNotEmpty(dyqdjAgentName)) {
            // 申请人2栏代理人
            returnMap.put("dlrxm2", dyqdjAgentName);
        }
        // 国有建设用地使用权及房屋所有权转移登记事项
        // 抵押数据
        String dydbse = StringUtil.getValue(busMap, "POWERSOURCE_DBSE");
        String dyfw = StringUtil.getValue(busMap, "POWERSOURCE_DYFW");
        String zwsjks = StringUtil.getValue(busMap, "POWERSOURCE_QLQSSJ");
        String zwsjjs = StringUtil.getValue(busMap, "POWERSOURCE_QLJSSJ");
        if(StringUtils.isNotEmpty(dydbse)) {
            returnMap.put("bdbzqse", dydbse);
        }
        if(StringUtils.isNotEmpty(dyfw)) {
            returnMap.put("zjjzwdyfw", dyfw);
        }
        if(StringUtils.isNotEmpty(zwsjjs) || StringUtils.isNotEmpty(zwsjks)) {
            // 债务履行期限
            returnMap.put("zwlxqx", zwsjks + "~" + zwsjjs);
        }
    }
}
