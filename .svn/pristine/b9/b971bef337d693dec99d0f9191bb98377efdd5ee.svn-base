/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tyjk.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SendMsgUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.bsfw.dao.SwbDataResDao;
import net.evecom.platform.call.service.NewCallService;
import net.evecom.platform.hflow.service.*;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.WorkdayService;
import net.evecom.platform.tyjk.dao.FlowWebDao;
import net.evecom.platform.tyjk.service.FlowWebService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 描述 平潭综合审批统一接口
 * 
 * @author Keravon Feng
 * @created 2018年7月23日 上午10:03:07
 */
@Service("flowWebService")
public class FlowWebServiceImpl extends BaseServiceImpl implements FlowWebService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowWebServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private FlowWebDao dao;

    /**
     * 接口的key
     */
    private static String guid = "40288b9f555926ed01555d9e4dab192f";
    /**
     * 引入serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 引入flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;

    /**
     * 引入Service
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 所引入的dao
     */
    @Resource
    private SwbDataResDao swbDataResDao;

    /**
     * 引入Service
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;

    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;
    /**
     * newCallService
     */
    @Resource
    private NewCallService newCallService;
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 引入Service
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * flowEndService
     */
    @Resource
    private FlowEndService flowEndService;
    /**
     * 引入dataSourceService
     */
    @Resource
    private DataSourceService dataSourceService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述 流程启动
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 上午10:09:38
     * @param flowInfoJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public String flowStart(String uuid, String flowInfoJson) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("itemCode") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("itemCode")))) {
                String itemCode = String.valueOf(flowInfo.get("itemCode"));
                Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
                if (itemInfo != null) {
                    Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                            new String[] { "DEF_ID" }, new Object[] { itemInfo.get("BDLCDYID") });
                    if (flowDef != null) {
                        Map<String, Object> user = jbpmService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                                new String[] { "USERNAME" }, new Object[] { flowInfo.get("EFLOW_CREATORACCOUNT") });
                        if (user == null || !user.get("FULLNAME").equals(flowInfo.get("EFLOW_CREATORNAME"))) {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误:用户账号：" + flowInfo.get("EFLOW_CREATORACCOUNT") + " 和用户姓名："
                                    + flowInfo.get("EFLOW_CREATORNAME") + " 在审批平台无匹配用户");
                        } else {
                            doFlowStart(result, flowInfo, itemInfo, flowDef);
                        }
                    } else {
                        result.put("resultCode", "004");
                        result.put("resultMsg", "数据有误," + itemCode + "业务对应的流程未定义");
                    }
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }
    
    
    /**
     * 描述 查询流程定义节点
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 上午10:09:38
     * @param flowInfoJson
     * @return
     */
    @Override
    public String queryFlowConfig(String itemCode) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (itemCode == null ) {
            result.put("resultCode", "999");
            result.put("resultMsg", "事项编码不能为空");
            return JSON.toJSONString(result);
        }
        List<Map<String, Object>> nodeConfigInfoList = serviceItemService.getNodeConfigInfo(itemCode);
        if (nodeConfigInfoList == null || nodeConfigInfoList.size() == 0) {
            result.put("resultCode", "005");
            result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应流程配置关系");
            return JSON.toJSONString(result);
        }
        List<Map<String, Object>> flowConfigInfoList = serviceItemService.getFlowConfigInfo(itemCode);
        if (flowConfigInfoList == null || flowConfigInfoList.size() == 0) {
            result.put("resultCode", "004");
            result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应流程配置");
            return JSON.toJSONString(result);
        }
        result.put("nodeConfig", nodeConfigInfoList);
        result.put("flowNode", flowConfigInfoList);
        result.put("resultMsg", "查询流程定义及对应关系成功");
        result.put("resultCode", "001");
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 描述 网上用户流程启动
     *
     * @author Keravon Feng
     * @created 2018年7月23日 上午10:09:38
     * @param flowInfoJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public String onlineFlowStart(String uuid, String flowInfoJson) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("itemCode") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("itemCode")))) {
                String itemCode = String.valueOf(flowInfo.get("itemCode"));
                Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
                if (itemInfo != null) {
                    boolean flag = true;
                    if (itemCode.equals("201605170002XK00104")) {// 个体事项
                        flag = isValidateGtxx(result, flowInfo, 1);
                    }
                    if (flag) {
                        Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                                new String[] { "DEF_ID" }, new Object[] { itemInfo.get("BDLCDYID") });
                        if (flowDef != null) {
                            Map<String, Object> user = getMztUserForOnlineApply(flowInfo);
                            if (user == null) {
                                user = jbpmService.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                                        new Object[] { flowInfo.get("EFLOW_CREATORACCOUNT") });
                            }
                            //if (user == null || !user.get("YHMC").equals(flowInfo.get("EFLOW_CREATORNAME"))) {
                            if (user == null) {
                                result.put("resultCode", "004");
                                result.put("resultMsg",
                                        "数据有误:用户手机号码：" + flowInfo.get("EFLOW_SJHM") + " 和用户姓名："
                                                + flowInfo.get("EFLOW_CREATORNAME") + " 和证件号码："
                                                + flowInfo.get("EFLOW_ZJHM") + " 在审批平台无匹配用户");
                            } else {
                                flowInfo.put("EFLOW_CREATORACCOUNT", StringUtil.getString(user.get("YHZH")));
                                flowInfo.put("SQFS", "1");// 申请方式：网上申请
                                doOnlineFlowStart(result, flowInfo, itemInfo, flowDef);
                            }
                        } else {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误," + itemCode + "业务对应的流程未定义");
                        }
                    }
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 个体平台参数
     */
    private static String[] ptInfoKey = new String[] { "BUSINESS_SCOPE", "PLACE_POSTCODE", "PLACE_PHONE",
            "EMPLOYED_NUM", "CAPITAL_AMOUNT", "BUSINESS_SCOPE_ID", "REGISTER_SQUARE_ADDR", "PLACE_REGISTER_OWNER",
            "PLACE_REGISTER_TEL", "RESIDENCE_REGISTER_WAYOFGET", "ARMY_REGISTER_HOURSE", "ARMYHOURSE_REGISTER_REMARKS",
            "LIAISON_NAME", "LIAISON_MOBILE", "LIAISON_IDNO", "LIAISON_ADDR", "LIAISON_SFZZM", "LIAISON_SFZFM",
            "XQ_NAME", "MAIN_BUSSINESS_NAME", "ML_NAME", "DL_NAME", "ZL_NAME", "XL_NAME", "MAIN_BUSSINESS_ID",
            "PT_NAME", "PT_HY", "PT_ZZXS" };
    /**
     * 经营者必填参数
     */
    private static String[] dealerKey = new String[] { "DEALER_NAME", "DEALER_SEX", "DEALER_IDCARD", "DEALER_ADDR",
            "DEALER_POSTCODE", "DEALER_MOBILE", "DEALER_EMAIL", "DEALER_POLITICAL", "DEALER_NATION", "DEALER_DEGREE",
            "DEALER_JOB" };

    /**
     * 
     * 描述 验证个体信息参数
     * 
     * @author Rider Chen
     * @created 2021年5月21日 上午11:35:49
     * @param result
     * @param flowInfo
     * @param type
     *            1 启动 2 过程执行
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean isValidateGtxx(Map<String, Object> result, Map<String, Object> flowInfo, int type) {
        boolean flag = true;
        String ptid = StringUtil.getString(flowInfo.get("PT_ID"));// 平台ID
        String SSSBLX = StringUtil.getString(flowInfo.get("SSSBLX"));// 商事申报类型
        if (StringUtils.isNotEmpty(SSSBLX) && SSSBLX.equals("1")) {// 商事秒批才进行以下数据验证
            String INDIVIDUAL_NAME = StringUtil.getString(flowInfo.get("INDIVIDUAL_NAME"));// 公司名称
            String EXE_ID = StringUtil.getString(flowInfo.get("EXE_ID"));// 申报号
            String wordNum = StringUtil.getString(flowInfo.get("WORD_NUM"));// 字号
            String DEALER_NAME = StringUtil.getString(flowInfo.get("DEALER_NAME"));// 经营者姓名
            String BUSINESS_PLACE = StringUtil.getString(flowInfo.get("BUSINESS_PLACE"));// 经营场所
            String LAW_SEND_ADDR = StringUtil.getString(flowInfo.get("LAW_SEND_ADDR"));// 法律文书送达地址
            String ZH_NUM = StringUtil.getString(flowInfo.get("ZH_NUM"));// 字号数字
            String LAW_EMAIL_ADDR = StringUtil.getString(flowInfo.get("LAW_EMAIL_ADDR"));// 法律文书电子送达地址
                                                                                         // 邮箱
            String LAW_FAX_ADDR = StringUtil.getString(flowInfo.get("LAW_FAX_ADDR"));// 法律文书电子送达地址
                                                                                     // 传真
            String LAW_IM_ADDR = StringUtil.getString(flowInfo.get("LAW_IM_ADDR"));// 法律文书电子送达地址
                                                                                   // 即时通讯账号（如微信）
            for (String key : dealerKey) {//必填判断
                String v1 = StringUtil.getString(flowInfo.get(key));// 接口参数
                if (StringUtils.isEmpty(v1)) {
                    result.put("resultCode", "005");
                    result.put("resultMsg", "输入参数不合法,参数" + key + "的值不能为空");
                    flag = false;
                    return flag;
                }
            }
            if (type == 2) {
                DEALER_NAME = StringUtil.getString(flowInfo.get("WORD_NAME"));// 经营者姓名
                if (StringUtils.isEmpty(DEALER_NAME)) {
                    result.put("resultCode", "005");
                    result.put("resultMsg", "输入参数不合法,参数WORD_NAME的值不能为空");
                    flag = false;
                    return flag;
                }
                if (StringUtils.isEmpty(ZH_NUM)) {
                    result.put("resultCode", "005");
                    result.put("resultMsg", "输入参数不合法,参数ZH_NUM的值不能为空");
                    flag = false;
                    return flag;
                }
            }
            boolean isExistIndividual = false;
            boolean isExistIndividualAddr = false;
            if (StringUtils.isNotEmpty(EXE_ID)) {
                Map<String, Object> exeinfo = serviceItemService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { EXE_ID });
                if (null != exeinfo && exeinfo.size() > 0) {
                    String BUS_RECORDID = StringUtil.getString(exeinfo.get("BUS_RECORDID"));
                    isExistIndividual = dataSourceService.isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL",
                            "INDIVIDUAL_NAME", INDIVIDUAL_NAME, BUS_RECORDID);
                    isExistIndividualAddr = dataSourceService.isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL",
                            "BUSINESS_PLACE", BUSINESS_PLACE, BUS_RECORDID);
                }
            } else {
                isExistIndividual = dataSourceService.isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME",
                        INDIVIDUAL_NAME, null);
                isExistIndividualAddr = dataSourceService.isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL",
                        "BUSINESS_PLACE", BUSINESS_PLACE, null);
            }

            if (isExistIndividual) {
                result.put("resultCode", "005");
                result.put("resultMsg", "公司名称重名请重新输入！请确保其他申报件没有引用该公司名称。");
                flag = false;
                return flag;
            }
            if (isExistIndividualAddr) {
                result.put("resultCode", "005");
                result.put("resultMsg", "经营场所重复请重新输入！请确保其他申报件没有引用该经营场所。");
                flag = false;
                return flag;
            }
            if (StringUtils.isEmpty(LAW_EMAIL_ADDR) && StringUtils.isEmpty(LAW_FAX_ADDR)
                    && StringUtils.isEmpty(LAW_IM_ADDR)) {
                result.put("resultCode", "005");
                result.put("resultMsg", "输入参数不合法,法律文书电子送达地址中邮箱、传真、即时通讯账号必须填写一项");
                flag = false;
                return flag;
            }
            if (StringUtils.isNotEmpty(ptid)) {
                Map<String, Object> ptinfo = serviceItemService.getByJdbc("T_COMMERCIAL_GTPTXXGL",
                        new String[] { "PT_ID" }, new Object[] { ptid });
                if (null != ptinfo && ptinfo.size() > 0) {
                    String ptName = StringUtil.getString(ptinfo.get("PT_NAME"));
                    String businessPlace = StringUtil.getString(ptinfo.get("BUSINESS_PLACE"));
                    String PT_HY = StringUtil.getString(ptinfo.get("PT_HY"));
                    String PT_ZZXS = StringUtil.getString(ptinfo.get("PT_ZZXS"));
                    String IS_TEST = StringUtil.getString(ptinfo.get("IS_TEST"));
                    flowInfo.put("IS_TEST", IS_TEST);
                    String swordnum = ptName + DEALER_NAME + ZH_NUM;
                    String name = "平潭" + swordnum + PT_HY + PT_ZZXS;
                    String addr = businessPlace + "（集群注册）";
                    if (!wordNum.equals(swordnum)) {// 字号不规范
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,字号必须为平台名称+经营者名称（退回的办件需要加字号数字）");
                        flag = false;
                    } else if (!INDIVIDUAL_NAME.equals(name)) {// 公司名称不规范
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,名称必须为平潭+字号+行业+组织形式");
                        flag = false;
                    } else if (BUSINESS_PLACE.indexOf(businessPlace) < 0) {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,经营场所地址与平台信息中的注册地址不匹配");
                        flag = false;
                    } else if (BUSINESS_PLACE.indexOf("（集群注册）") < 0) {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,经营场所地址必须带有“（集群注册）”");
                        flag = false;
                    } else if (BUSINESS_PLACE.equals(addr)) {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,经营场所地址必须带住所编码");
                        flag = false;
                    } else if (!BUSINESS_PLACE.equals(LAW_SEND_ADDR)) {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "输入参数不合法,经营场所地址与法律文书送达地址必须相同");
                        flag = false;
                    }
                    for (String key : ptInfoKey) {
                        String v1 = StringUtil.getString(flowInfo.get(key));// 接口参数
                        String v2 = StringUtil.getString(ptinfo.get(key));// 平台参数
                        if (!v1.equals(v2)) {
                            result.put("resultCode", "005");
                            result.put("resultMsg", "输入参数不合法,参数" + key + "的值“" + v1 + "”与平台信息中的值“" + v2 + "”不一致");
                            flag = false;
                            return flag;
                        }
                    }
                } else {
                    result.put("resultCode", "005");
                    result.put("resultMsg", "输入参数不合法,查询不到相关平台信息");
                    flag = false;
                }
            } else {
                result.put("resultCode", "005");
                result.put("resultMsg", "输入参数不合法,参数PT_ID为空");
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 描述 根据闽政通账号获取用户账号名
     *
     * @author Keravon Feng
     * @created 2018年7月23日 上午10:09:38
     * @param flowInfoJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public String getAccountByMztInfo(String uuid, String flowInfoJson) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("EFLOW_SJHM") != null
                    && StringUtils.isNotBlank(String.valueOf(flowInfo.get("EFLOW_SJHM")))) {
                Map<String, Object> userInfo = getMztUserForOnlineApply(flowInfo);
                String yhzh = "";
                if (userInfo != null) {
                    yhzh = StringUtil.getString(userInfo.get("YHZH"));
                }
                if (StringUtils.isEmpty(yhzh)) {
                    result.put("resultCode", "003");
                    result.put("resultMsg", "没有该账号信息");
                } else {
                    result.put("resultCode", "001");
                    result.put("resultMsg", "获取账号成功");
                    result.put("account", yhzh);
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失闽政通关键数据");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("获取账号信息为：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 网上申请获取闽政通用户信息
     * 
     * @param flowInfo
     * @return
     */
    public Map<String, Object> getMztUserForOnlineApply(Map<String, Object> flowInfo) {
        Map<String, Object> data = new HashMap();
        String userType = StringUtil.getString(flowInfo.get("BSYHLX"));
        String sjhm = StringUtil.getString(flowInfo.get("EFLOW_SJHM")); // 手机号码
        String zjhm = StringUtil.getString(flowInfo.get("EFLOW_ZJHM"));// 证件号码
        String userName = StringUtil.getString(flowInfo.get("EFLOW_CREATORNAME"));// 真实姓名
        if (StringUtils.isEmpty(userType) || StringUtils.isEmpty(sjhm) || StringUtils.isEmpty(zjhm)
                || StringUtils.isEmpty(userName)) {
            return null;
        }
        if ("1".equals(userType)) {
            // 个人
            data.put("USER_MOBILE", sjhm);
            data.put("USER_IDCARD", zjhm);
            data.put("USER_NAME", userName);
        } else {
            // 法人
            data.put("USER_MOBILE", sjhm);// 经办人手机号码
            data.put("LEGALPERSON_JBRSJH", sjhm);// 经办人手机号码
            data.put("USER_NAME", userName);// 公司名称
            data.put("LEGALPERSON_IDNUM", zjhm);// 公司营业执照
            // data.put("LEGALPERSON_PHONE",StringUtil.getString(flowInfo.get("LEGALPERSON_PHONE")));//法人电话
            // data.put("LEGALPERSON_NAME",StringUtil.getString(flowInfo.get("LEGALPERSON_NAME")));//法人姓名
            // data.put("LEGALPERSON_JBRIDCARD",StringUtil.getString(flowInfo.get("LEGALPERSON_JBRIDCARD")));//经办人身份证号
            // data.put("LEGALPERSON_JBRNAME",StringUtil.getString(flowInfo.get("LEGALPERSON_JBRNAME")));//经办人姓名
        }
        data.put("USER_TYPE", userType);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", data);
        String result = JSON.toJSONString(map);
        return sysLogService.mztUserInfor(result);
    }

    /**
     * 描述 存储流程业务数据，并启动业务流程。
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 下午3:43:43
     * @param result
     * @param flowInfo
     * @param itemInfo
     * @param flowDef
     */
    @SuppressWarnings("unchecked")
    private void doFlowStart(Map<String, Object> result, Map<String, Object> flowInfo, Map<String, Object> itemInfo,
            Map<String, Object> flowDef) {
        Map<String, Object> flowVars = new HashMap<String, Object>();
        flowVars.put("EFLOW_CREATORACCOUNT", flowInfo.get("EFLOW_CREATORACCOUNT"));
        flowVars.put("EFLOW_CREATORNAME", flowInfo.get("EFLOW_CREATORNAME"));
        flowVars.put("EFLOW_SUBJECT", flowInfo.get("SBMC"));
        flowInfo.put("SUBJECT", flowInfo.get("SBMC"));
        if (flowInfo.get("EXECUTE_TIME") == null || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("CREATE_TIME", df.format(new Date()));
        } else {
            flowVars.put("CREATE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
        flowVars.put("EFLOW_DEFKEY", itemInfo.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", itemInfo.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", itemInfo.get("BDLCDYID"));
        flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("ITEM_NAME", itemInfo.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", itemInfo.get("ITEM_CODE"));
        flowVars.put("SSBMBM", itemInfo.get("SSBMBM"));
        flowVars.put("SQFS", "2");
        flowVars.put("SOURCE", "统一接口平台");
        String randomNum = StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000) + "");
        flowVars.put("BJCXMM", randomNum);
        // 获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
        // 办件基本信息
        // 申报对象信息
        // 经办人信息
        flowVars.putAll(flowInfo);

        // 用于判断是否来源与接口调用的办件过程执行
        flowVars.put("DATAFROM", "webservice");
        if (flowInfo.get("EXECUTE_TIME") == null || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("EXECUTE_TIME", df.format(new Date()));
        } else {
            flowVars.put("EXECUTE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
        String nextStepJson = jbpmService.getNextStepsJson(itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), currentOperNodeName, flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }

        Map<String, Object> user = jbpmService.getByJdbc("T_MSJW_SYSTEM_SYSUSER", new String[] { "USERNAME" },
                new Object[] { flowInfo.get("EFLOW_CREATORACCOUNT") });
        if (!doFlowJson(result, flowInfo, itemInfo, flowDef, flowVars, user, null))
            return;

        try {
            Map<String, Object> resultMap = jbpmService.doFlowJob(flowVars);
            String exeId = (String) resultMap.get("EFLOW_EXEID");
            result.put("resultCode", "001");
            result.put("exeid", exeId);
            result.put("resultMsg", "保存成功，在审批平台流程实例ID：" + exeId);
        } catch (Exception e) {
            result.put("resultCode", "000");
            result.put("resultMsg", "发起流程异常");
            log.error("", e);
        }
    }

    /**
     * 描述 存储流程业务数据，并启动业务流程(网上用户）
     *
     * @author Keravon Feng
     * @created 2018年7月23日 下午3:43:43
     * @param result
     * @param flowInfo
     * @param itemInfo
     * @param flowDef
     */
    private void doOnlineFlowStart(Map<String, Object> result, Map<String, Object> flowInfo,
            Map<String, Object> itemInfo, Map<String, Object> flowDef) {
        Map<String, Object> flowVars = new HashMap<String, Object>();
        flowVars.put("EFLOW_CREATORACCOUNT", flowInfo.get("EFLOW_CREATORACCOUNT"));
        flowVars.put("EFLOW_CREATORNAME", flowInfo.get("EFLOW_CREATORNAME"));
        flowVars.put("EFLOW_SUBJECT", flowInfo.get("SBMC"));
        flowInfo.put("SUBJECT", flowInfo.get("SBMC"));
        if (flowInfo.get("EXECUTE_TIME") == null || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("CREATE_TIME", df.format(new Date()));
        } else {
            flowVars.put("CREATE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
        flowVars.put("EFLOW_DEFKEY", itemInfo.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", itemInfo.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", itemInfo.get("BDLCDYID"));
        flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("ITEM_NAME", itemInfo.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", itemInfo.get("ITEM_CODE"));
        flowVars.put("SSBMBM", itemInfo.get("SSBMBM"));
        flowVars.put("SQFS", flowInfo.get("SQFS") == null ? "2" : flowInfo.get("SQFS"));
        flowVars.put("SOURCE", "统一接口平台");
        String randomNum = StringUtil.getFormatNumber(6, StringUtil.getRandomIntNumber(1000000) + "");
        flowVars.put("BJCXMM", randomNum);
        // 获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME", currentOperNodeName);
        // 办件基本信息
        // 申报对象信息
        // 经办人信息
        flowVars.putAll(flowInfo);

        // 用于判断是否来源与接口调用的办件过程执行
        flowVars.put("DATAFROM", "webservice");
        if (flowInfo.get("EXECUTE_TIME") == null || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("EXECUTE_TIME", df.format(new Date()));
        } else {
            flowVars.put("EXECUTE_TIME", flowInfo.get("EXECUTE_TIME"));
        }
        String nextStepJson = jbpmService.getNextStepsJson(itemInfo.get("BDLCDYID").toString(),
                Integer.valueOf(flowDef.get("VERSION").toString()), currentOperNodeName, flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }

        Map<String, Object> user = getMztUserForOnlineApply(flowInfo);
        if (user == null) {
            result.put("resultCode", "000");
            result.put("resultMsg", "用户信息不完善");
        }
        user.put("USERNAME", StringUtil.getString(user.get("YHMC")));
        user.put("USER_ID", user.get("USER_ID") == null ? StringUtil.getString(user.get("YHZH")) : user.get("USER_ID"));
        if (!doFlowJson(result, flowInfo, itemInfo, flowDef, flowVars, user, null))
            return;
        try {
            Map<String, Object> resultMap = jbpmService.doFlowJob(flowVars);
            String exeId = (String) resultMap.get("EFLOW_EXEID");
            result.put("resultCode", "001");
            result.put("exeid", exeId);
            result.put("resultMsg", "保存成功，在审批平台流程实例ID：" + exeId);
            sendEms(flowVars,"");
        } catch (Exception e) {
            result.put("resultCode", "000");
            result.put("resultMsg", "发起流程异常");
            log.error("", e);
        }
    }

    /**
     * 发送ems
     * @param variables
     * @param msg
     * @return
     */
    private String sendEms(Map<String,Object> variables,String msg){
        log.info("判断是否要对接EMS订单");
        if(StringUtils.isNotBlank(String.valueOf(variables.get("EFLOW_SUBJECT")))
                &&variables.get("EFLOW_SUBJECT").toString().indexOf("测试")<0){//非测试数据
            log.info("EMS订单接口开始发送");
            msg = executionService.sendEms(variables,msg);
            log.info("EMS订单接口结束发送");
        }

        return msg;
    }

    /**
     * 
     * 描述： 处理json信息
     * 
     * @author Rider Chen
     * @created 2018年10月17日 下午3:05:21
     * @param result
     * @param flowInfo
     * @param itemInfo
     * @param flowDef
     * @param flowVars
     */
    @SuppressWarnings("unchecked")
    private boolean doFlowJson(Map<String, Object> result, Map<String, Object> flowInfo, Map<String, Object> itemInfo,
            Map<String, Object> flowDef, Map<String, Object> flowVars, Map<String, Object> user,
            Map<String, Object> flowexe) {
        // 解析办理结果邮寄信息
        String express = null == flowInfo.get("Express") ? "" : flowInfo.get("Express").toString();
        if (StringUtils.isNotEmpty(express)) {
            try {
                Map<String, Object> expressMap = JSON.parseObject(express);
                flowVars.put("FINISH_GETTYPE", "02");
                flowVars.putAll(expressMap);
            } catch (Exception e) {
                // TODO: handle exception
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,Express不是有效的JSON");
                log.error("", e);
                return false;
            }
        } else {
            flowVars.put("FINISH_GETTYPE", "01");
        }
        // 解析收件材料邮寄信息
        String attrsExpress = null == flowInfo.get("Attrs_Express") ? "" : flowInfo.get("Attrs_Express").toString();
        if (StringUtils.isNotEmpty(attrsExpress)) {
            try {
                Map<String, Object> attrsExpressMap = JSON.parseObject(attrsExpress);
                flowVars.put("MAT_SENDTYPE", "02");
                flowVars.putAll(attrsExpressMap);
            } catch (Exception e) {
                // TODO: handle exception
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,Attrs_Express不是有效的JSON");
                log.error("", e);
                return false;
            }
        } else {
            flowVars.put("MAT_SENDTYPE", "01");
        }

        // 获取表单信息
        Map<String, Object> flowForm = serviceItemService.getByJdbc("JBPM6_FLOWFORM", new String[] { "FORM_ID" },
                new Object[] { flowDef.get("BIND_FORMID") });
        // 获取材料信息列表
        String bussName = flowInfo.get("EXE_SUBBUSCLASS") == null ? "" : flowInfo.get("EXE_SUBBUSCLASS").toString();
        List<Map<String, Object>> applyMaters1 = applyMaterService.findByItemId(itemInfo.get("ITEM_ID").toString(),
                null);
        List<Map<String, Object>> applyMaters = new ArrayList<Map<String, Object>>();
        List<String> materCodeList = new ArrayList<String>();
        if (StringUtils.isNotEmpty(bussName)) {
            for (Map<String, Object> map : applyMaters1) {
                String busn = map.get("BUSCLASS_NAME") == null ? "" : map.get("BUSCLASS_NAME").toString();
                if (bussName.equals(busn)) {
                    applyMaters.add(map);
                }
            }
        } else {  
            applyMaters.addAll(applyMaters1);
        }
        for (Map<String, Object> map : applyMaters1) {
            materCodeList.add(map.get("MATER_CODE") == null ? "" : map.get("MATER_CODE").toString());        
        }

        String busTableName = (String) flowForm.get("FORM_KEY");
        Map<String, Object> uploadUser = new HashMap<String, Object>();
        uploadUser.put("uploadUserId", user.get("USER_ID"));
        uploadUser.put("uploadUserName", user.get("USERNAME"));
        if (flowInfo.get("EXE_ID") == null || StringUtils.isEmpty(String.valueOf(flowInfo.get("EXE_ID")))) {

            applyMaters = applyMaterService.setHisUploadFiles(applyMaters, busTableName, uploadUser);
        } else {
            String busRecordId = (String) flowexe.get("BUS_RECORDID");
            applyMaters = applyMaterService.setUploadFiles(busRecordId, busTableName, applyMaters);
        }
        // 附件信息
        String Attrs = null == flowInfo.get("Attrs") ? "" : flowInfo.get("Attrs").toString();
        if (StringUtils.isNotEmpty(Attrs)) {
            try {
                List<Map<String, Object>> attrsList = JSON.parseObject(Attrs, List.class);
                for (Map<String, Object> marersMap : applyMaters) {
                    // 获取材料编码
                    String MATER_CODE1 = marersMap.get("MATER_CODE").toString();
                    String MATER_NAME = marersMap.get("MATER_NAME").toString();
                    //// 获取是否必须上传
                    String MATER_ISNEED = marersMap.get("MATER_ISNEED").toString();
                    // 获取收取方式
                    String SQFS = null == marersMap.get("SQFS") ? "1" : marersMap.get("SQFS").toString();
                    // 收取方式为上传且必须上传
                    if ("1".equals(MATER_ISNEED) && "1".equals(SQFS)) {
                        boolean isok = false;
                        for (Map<String, Object> map : attrsList) {
                            // 材料编码
                            String ATTACH_KEY = map.get("ATTACH_KEY").toString();
                            if (MATER_CODE1.equals(ATTACH_KEY)) {
                                isok = true;
                                break;
                            }
                            
                        }
                        if (!isok) {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误,材料“" + MATER_NAME + "”必须上传");
                            return false;
                        }
                    } 
                }
                for (Map<String, Object> map : attrsList) {
                    // 材料编码
                    String ATTACH_KEY = map.get("ATTACH_KEY").toString();
                    if (!materCodeList.contains(ATTACH_KEY)) {
                        result.put("resultCode", "004");
                        result.put("resultMsg", "数据有误,材料编码ATTACH_KEY“" + ATTACH_KEY + "”与事项材料编码不匹配");
                        return false;
                    }
                }
                flowVars.put("EFLOW_SUBMITMATERFILEJSON", Attrs);
            } catch (Exception e) {
                // TODO: handle exception
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,Attrs不是有效的JSON");
                log.error("", e);
                return false;
            }
        } else {
            for (Map<String, Object> marersMap : applyMaters) {
                // 获取材料
                String MATER_NAME = marersMap.get("MATER_NAME").toString();
                //// 获取是否必须上传
                String MATER_ISNEED = marersMap.get("MATER_ISNEED").toString();
                // 获取收取方式
                String SQFS = marersMap.get("SQFS").toString();
                boolean isok = true;
                // 收取方式为上传且必须上传
                if ("1".equals(MATER_ISNEED) && "1".equals(SQFS)) {
                    isok = false;
                }
                if (!isok) {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,材料“" + MATER_NAME + "”必须上传");
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * 
     * 描述 流程执行
     * 
     * @author Keravon Feng
     * @created 2018年7月23日 下午4:02:10
     * @param flowInfoJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public String flowExecute(String uuid, String flowInfoJson) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            // 参数验证
            Map<String, Object> flowexe = null;
            if (flowInfo.get("EXE_ID") == null || StringUtils.isEmpty(String.valueOf(flowInfo.get("EXE_ID")))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "数据有误,缺失流程实例编号EXE_ID");
                return JSON.toJSONString(result);
            } else {
                String exeId = String.valueOf(flowInfo.get("EXE_ID"));
                flowexe = jbpmService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId });
                if (flowexe == null) {
                    result.put("resultCode", "002");
                    result.put("resultMsg", "查无编号EXE_ID：" + exeId + "对应的流程业务实例");
                    return JSON.toJSONString(result);
                }
            }
            // 审批意见
            if (flowInfo.get("EFLOW_HANDLE_OPINION") == null
                    || StringUtils.isEmpty((String) flowInfo.get("EFLOW_HANDLE_OPINION"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审核意见EFLOW_HANDLE_OPINION");
                return JSON.toJSONString(result);
            }
            if (flowInfo.get("TASK_NODENAME") == null || StringUtils.isEmpty((String) flowInfo.get("TASK_NODENAME"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，任务节点名称TASK_NODENAME");
                return JSON.toJSONString(result);
            }
            /*
             * if (flowInfo.get("TASK_NODENAME").equals("开始")) {
             * result.put("resultCode", "006"); result.put("resultMsg",
             * "提交数据环节有误，【开始】环节请走发起流程接口"); return JSON.toJSONString(result); }
             */
            if (flowInfo.get("ASSIGNER_CODE") == null || StringUtils.isEmpty((String) flowInfo.get("ASSIGNER_CODE"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台流程审核人账号ASSIGNER_CODE");
                return JSON.toJSONString(result);
            }
            if (flowInfo.get("ASSIGNER_NAME") == null || StringUtils.isEmpty((String) flowInfo.get("ASSIGNER_NAME"))) {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台流程审核人姓名ASSIGNER_NAME");
                return JSON.toJSONString(result);
            }
            if (flowInfo.get("itemCode") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("itemCode")))) {
                // 获取申报流程中的事项编码
                String exeItemCode = String.valueOf(flowexe.get("ITEM_CODE"));
                // 获取接口参数中的事项编码
                String itemCode = String.valueOf(flowInfo.get("itemCode"));
                boolean flag = true;
                if (itemCode.equals("201605170002XK00104")) {// 个体事项
                    flag = isValidateGtxx(result, flowInfo, 2);
                }
                if (flag) {
                    // 对比事项编码
                    if (StringUtils.isNotEmpty(exeItemCode) && exeItemCode.equals(itemCode)) {
                        Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
                        if (itemInfo != null) {
                            Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                                    new String[] { "DEF_ID" }, new Object[] { flowexe.get("DEF_ID") });
                            if (flowDef != null) {
                                doFlowExecute(result, flowInfo, flowDef, flowexe);
                            } else {
                                result.put("resultCode", "004");
                                result.put("resultMsg", "数据有误,查询不到对应的流程定义（" + flowexe.get("DEF_ID") + "）");
                            }
                        } else {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
                        }
                    } else {
                        result.put("resultCode", "004");
                        result.put("resultMsg", "数据有误,itemCode:" + itemCode + "与流程中的事项编码(" + exeItemCode + ")不一致");
                    }
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "未提供流程相关数据");
        }
        log.info("流程执行请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 描述 处理流程执行具体操作
     * 
     * @author Keravon Feng
     * @created 2018年7月24日 上午9:14:09
     * @param result
     * @param flowInfo
     * @param flowDef
     */
    @SuppressWarnings("unchecked")
    private void doFlowExecute(Map<String, Object> result, Map<String, Object> flowInfo, Map<String, Object> flowDef,
            Map<String, Object> flowexe) {
        String itemCode = String.valueOf(flowInfo.get("itemCode"));
        String exeId = String.valueOf(flowInfo.get("EXE_ID"));
        String isExecuteSave = StringUtil.getString(flowInfo.get("isExecuteSave"));// 是否执行业务数据保存
                                                                                   // 1保存
        String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
        String taskNodeName = (String) flowInfo.get("TASK_NODENAME");
        if (StringUtils.isNotEmpty(curStepNames) && curStepNames.contains(taskNodeName)) {
            String TASK_STATUS = StringUtil.getString(flowInfo.get("TASK_STATUS"));// 任务状态
            if (StringUtils.isEmpty(TASK_STATUS)) {
                TASK_STATUS = "1";
            }
            Map<String, Object> flowTask = jbpmService.getByJdbc("JBPM6_TASK",
                    new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                    new Object[] { exeId, taskNodeName, flowInfo.get("ASSIGNER_CODE"), flowInfo.get("ASSIGNER_NAME"),
                            TASK_STATUS });
            if (null == flowTask) {
                result.put("resultCode", "005");
                result.put("resultMsg", "数据有误,流程环节名称TASK_NODENAME：“" + taskNodeName + "” 与审批系统中流程环节名称curStepNames：“"
                        + curStepNames + "”,审核人账号ASSIGNER_CODE：" + flowInfo.get("ASSIGNER_CODE") + " 未能查询到匹配的流程待办任务");
            } else {
                Map<String, Object> flowVars = new HashMap<String, Object>();
                flowVars.put("EFLOW_EXEID", exeId);

                flowVars.put("EFLOW_DEFKEY", flowDef.get("DEF_KEY"));
                flowVars.put("EFLOW_DEFID", flowDef.get("DEF_ID"));
                flowVars.put("EFLOW_DEFVERSION", flowDef.get("VERSION"));

                flowVars.put("EFLOW_ISQUERYDETAIL", "false");
                flowVars.put("EFLOW_ISTEMPSAVE", "-1");
                flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", flowexe.get("CUR_STEPNAMES"));
                flowVars.put("EFLOW_CURUSEROPERNODENAME", flowexe.get("CUR_STEPNAMES"));
                flowVars.put("ITEM_CODE", itemCode);
                flowVars.put("EFLOW_INVOKEBUSSAVE", "-1");
                flowVars.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
                flowVars.put("EFLOW_HANDLE_OPINION", flowInfo.get("EFLOW_HANDLE_OPINION"));

                flowVars.put("EFLOW_CREATORNAME", flowInfo.get("ASSIGNER_NAME"));
                flowVars.put("SQFS", flowInfo.get("SQFS") == null ? "2" : flowInfo.get("SQFS").toString());
                flowVars.put("XKFILE_NUM", flowInfo.get("XKFILE_NUM"));
                
                if (StringUtils.isNotEmpty(isExecuteSave) && isExecuteSave.equals("1")) {
                    flowVars.put("EFLOW_BUSTABLENAME", flowexe.get("BUS_TABLENAME"));
                    flowVars.put("EFLOW_BUSRECORDID", flowexe.get("BUS_RECORDID"));
                    flowVars.put("EFLOW_INVOKEBUSSAVE", "1");
                    flowVars.putAll(flowInfo);
                }
                // 用于判断是否来源与接口调用的办件过程执行
                flowVars.put("DATAFROM", "webservice");
                if (flowInfo.get("EXECUTE_TIME") == null
                        || StringUtils.isEmpty((String) flowInfo.get("EXECUTE_TIME"))) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    flowVars.put("EXECUTE_TIME", df.format(new Date()));
                } else {
                    flowVars.put("EXECUTE_TIME", flowInfo.get("EXECUTE_TIME"));
                }
                try {
                    String nextStepJson = jbpmService.getNextStepsJson((String) flowDef.get("DEF_ID"),
                            Integer.valueOf(flowDef.get("VERSION").toString()), (String) flowexe.get("CUR_STEPNAMES"),
                            flowVars);
                    if (StringUtils.isNotEmpty(nextStepJson) || flowInfo.get("TASK_NODENAME").equals("办结")
                            || (null != flowInfo.get("CUR_NODE") && flowInfo.get("CUR_NODE").equals("办结"))) {
                        flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                        jbpmService.doFlowJob(flowVars);
                        // 办结
                        if (flowInfo.get("TASK_NODENAME").equals("办结")
                                || (null != flowInfo.get("CUR_NODE") && flowInfo.get("CUR_NODE").equals("办结"))) {
                            Map<String, Object> data = new HashMap<String, Object>();
                            data.put("XKFILE_NUM", flowInfo.get("XKFILE_NUM"));
                            data.put("XKFILE_NAME", flowInfo.get("XKFILE_NAME"));
                            data.put("XKDEPT_NAME", flowInfo.get("XKDEPT_NAME"));
                            data.put("XKDEPT_ID", flowInfo.get("XKDEPT_ID"));
                            data.put("EFFECT_TIME", flowInfo.get("EFFECT_TIME"));
                            data.put("CLOSE_TIME", flowInfo.get("CLOSE_TIME"));
                            data.put("ISLONG_TERM", flowInfo.get("ISLONG_TERM"));
                            data.put("XKCONTENT", flowInfo.get("XKCONTENT"));
                            data.put("SDCONTENT", flowInfo.get("SDCONTENT"));
                            data.put("RESULT_FILE_ID", flowInfo.get("RESULT_FILE_ID"));
                            data.put("RESULT_FILE_URL", flowInfo.get("RESULT_FILE_URL"));
                            data.put("RUN_MODE", flowInfo.get("RUN_MODE"));
                            data.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
                            data.put("CUR_NODE", flowVars.get("EFLOW_CURUSEROPERNODENAME"));

                            data.put("XKDOCUMENT_NUM", flowInfo.get("XKDOCUMENT_NUM"));
                            data.put("XKDOCUMENT_NAME", flowInfo.get("XKDOCUMENT_NAME"));
                            data.put("XK_TYPE", flowInfo.get("XK_TYPE"));
                            data.put("XKDECIDE_TIME", flowInfo.get("XKDECIDE_TIME"));
                            data.put("XK_USC", flowInfo.get("XK_USC"));
                            data.put("XK_HOLDER", flowInfo.get("XK_HOLDER"));
                            data.put("RES_SOURCE", flowInfo.get("RES_SOURCE"));

                            data.put("XKDEPT_SWDJH", flowInfo.get("XKDEPT_SWDJH"));
                            data.put("XKDEPT_SYDWZSH", flowInfo.get("XKDEPT_SYDWZSH"));
                            data.put("XKDEPT_SJLYDW", flowInfo.get("XKDEPT_SJLYDW"));
                            data.put("XKDEPT_SJLYDW_USC", flowInfo.get("XKDEPT_SJLYDW_USC"));

                            List<Map<String, Object>> resultList = dao.findBySql(
                                    "select * from JBPM6_FLOW_RESULT t where t.exe_id = ? ",
                                    new Object[] { flowVars.get("EFLOW_EXEID") }, null);
                            if (null != resultList && resultList.size() > 0) {
                                result.put("resultCode", "005");
                                result.put("resultMsg", "数据有误,重复提交");
                                return;
                            } else {
                                jbpmService.saveOrUpdate(data, "JBPM6_FLOW_RESULT", null);
                                // 执行办结后续操作
                                flowEndService.exeSuccessByInter(flowInfo);
                            }
                        }
                        result.put("resultCode", "001");
                        result.put("resultMsg", "流程过程执行成功");
                    } else {
                        result.put("resultCode", "005");
                        result.put("resultMsg", "数据有误,nextStepJson为空，或者TASK_NODENAME、CUR_NODE不为“办结”");
                    }
                } catch (Exception e) {
                    result.put("resultCode", "000");
                    result.put("resultMsg", "执行流程异常");
                    log.error("", e);
                }
            }
        } else {
            result.put("resultCode", "005");
            result.put("resultMsg", "数据有误,流程环节名称TASK_NODENAME：“" + taskNodeName + "” 与审批系统中流程环节名称curStepNames：“"
                    + curStepNames + "” 与事项流程配置信息不匹配");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getItemApplyMaters(String uuid, String itemCode) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(itemCode)) {
            Map<String, Object> itemInfo = serviceItemService.getItemAndDefInfo(itemCode);
            if (itemInfo != null) {
                Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                        new Object[] { itemInfo.get("BDLCDYID") });
                if (flowDef != null) {
                    // 获取材料信息列表
                    List<Map<String, Object>> applyMaters = applyMaterService
                            .findByItemId(itemInfo.get("ITEM_ID").toString(), null);
                    result.put("resultCode", "001");
                    result.put("resultMsg", JSON.toJSONString(applyMaters));
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误," + itemCode + "业务对应的流程未定义");
                }
            } else {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,itemCode:" + itemCode + "在审批平台未查询到对应事项");
            }
        } else {
            result.put("resultCode", "003");
            result.put("resultMsg", "缺失流程关键数据，审批平台事项编码itemCode");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String notAccept(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("exeId") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("exeId")))) {
                String exeId = String.valueOf(flowInfo.get("exeId"));// 流水号
                String assignerCode = String.valueOf(flowInfo.get("ASSIGNER_CODE"));// 审批平台挂起操作人后台用户账号

                Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { exeId });

                // 获取流程实例信息
                Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                        new String[] { "EXE_ID", "IS_AUDITED", "ASSIGNER_TYPE", "TASK_STATUS", "ASSIGNER_CODE" },
                        new Object[] { exeId, "1", "1", "1", assignerCode });
                if (null != flowExe && null != flowTask) {
                    String sqfs = (String) flowExe.get("SQFS");
                    // 获取意见内容
                    String EFLOW_HANDLE_OPINION = (String) flowInfo.get("EFLOW_HANDLE_OPINION");
                    String EFLOW_CURRENTTASK_ID = flowTask.get("TASK_ID").toString();
                    // 退件判断是否已保存过同步省网信息 是否为省网下发办件
                    Boolean isSaveBoolean = swbDataResDao.isHaveSaveBjxxInfo(exeId);
                    if (isSaveBoolean || sqfs.equals("3")) {
                        this.saveTjxxDataRes(exeId, EFLOW_CURRENTTASK_ID);
                    }
                    flowInfo.put("EFLOW_CURRENTTASK_ID", EFLOW_CURRENTTASK_ID);
                    this.executionService.updateExeToNotAccept(exeId, EFLOW_HANDLE_OPINION, flowInfo);

                    // 开始保存工程建设项目审批事项办理详细信息
                    if (StringUtils.isEmpty(EFLOW_HANDLE_OPINION)) {
                        EFLOW_HANDLE_OPINION = "审核不通过";
                    }
                    projectApplyService.saveAfterToXmspsxblxxxxb(13, exeId, flowTask.get("ASSIGNER_NAME").toString(),
                            EFLOW_HANDLE_OPINION);
                    // 结束保存工程建设项目审批事项办理详细信息
                    result.put("resultCode", "001");
                    result.put("resultMsg", "退件成功，在审批平台流程实例ID：" + exeId);
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,流水号:" + exeId + "在审批平台未查询到对应办件");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，流水号exeId");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 描述
     * 
     * @author Kester Chen
     * @created 2017-6-27 下午4:25:49
     * @param flowVars
     * @param flowExe
     * @param currentTaskId
     */
    @SuppressWarnings("unchecked")
    private void saveTjxxDataRes(String exeId, String currentTaskId) {
        // 开始保存办件信息
        Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
        bjxxDataRes.put("EXE_ID", exeId);
        bjxxDataRes.put("DATA_TYPE", "30");
        bjxxDataRes.put("OPER_TYPE", "I");
        // 定义是否有附件
        int HAS_ATTR = 0;
        bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
        bjxxDataRes.put("INTER_TYPE", "10");
        bjxxDataRes.put("TASK_ID", currentTaskId);
        swbDataResDao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String handUp(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("exeId") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("exeId")))) {
                String exeId = String.valueOf(flowInfo.get("exeId"));// 流水号
                String LinkId = String.valueOf(flowInfo.get("LinkId"));// 审批平台挂起特殊环节ID
                String link_man_tel = String.valueOf(flowInfo.get("link_man_tel"));// 挂起执行人电话
                String link_man = String.valueOf(flowInfo.get("link_man"));// 挂起执行人

                String assignerCode = String.valueOf(flowInfo.get("ASSIGNER_CODE"));// 审批平台挂起操作人后台用户账号

                // 获取流程实例信息
                Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                        new String[] { "EXE_ID", "IS_AUDITED", "ASSIGNER_TYPE", "TASK_STATUS", "ASSIGNER_CODE" },
                        new Object[] { exeId, "1", "1", "1", assignerCode });
                // 获取用户信息
                Map<String, Object> sysUser = flowTaskService.getByJdbc("T_MSJW_SYSTEM_SYSUSER",
                        new String[] { "USERNAME" }, new Object[] { assignerCode });
                if (null != flowTask && null != sysUser) {

                    flowTaskService.handUpFlowTask(exeId, flowTask.get("TASK_ID").toString());

                    Map<String, Object> flowHangInfo = new HashMap<String, Object>();
                    flowHangInfo.put("TASK_ID", (String) flowTask.get("PARENT_TASKID"));
                    String beginTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                    String gqsj = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");

                    // 特殊环节挂起
                    if (LinkId != null) {
                        Map<String, Object> linkInfo = flowHangInfoService.getByJdbc("T_WSBS_SERVICEITEM_LINK",
                                new String[] { "record_id" }, new Object[] { LinkId });
                        if (null != linkInfo) {
                            String linkTime = linkInfo.get("LINK_LIMITTIME").toString();
                            String linkEndTime = workdayService.getDeadLineDate(gqsj, Integer.parseInt(linkTime));
                            flowHangInfo.put("link_End_Time", linkEndTime + " 23:59:59");
                            flowHangInfo.put("link_man_tel", link_man_tel);
                            flowHangInfo.put("link_man", link_man);
                            flowHangInfo.put("link_id", LinkId);
                            flowHangInfo.put("link_status", "1");
                            flowHangInfo.put("userid", sysUser.get("USER_ID"));

                            flowHangInfo.put("BEGIN_TIME", beginTime);
                            flowHangInfoService.saveOrUpdate(flowHangInfo, "JBPM6_HANGINFO", "");

                            Map<String, Object> exeHandledescInfo = new HashMap<String, Object>();
                            String exeHandledesc = "";
                            if (StringUtils.isNotEmpty((String) flowTask.get("EXE_HANDLEDESC"))) {
                                exeHandledesc = flowTask.get("EXE_HANDLEDESC").toString();
                                exeHandledesc = exeHandledesc + "\r\n";
                            }
                            exeHandledesc = exeHandledesc + "[" + flowTask.get("ASSIGNER_NAME").toString() + "]" + "于"
                                    + gqsj + "挂起";
                            exeHandledescInfo.put("EXE_HANDLEDESC", exeHandledesc);
                            flowTaskService.saveOrUpdate(exeHandledescInfo, "JBPM6_TASK",
                                    flowTask.get("TASK_ID").toString());
                            flowTaskService.updateDescInfo(flowTask.get("PARENT_TASKID").toString(), exeHandledesc);

                            // 挂起成功通知公众用户
                            handUpSendMsg(exeId, gqsj, LinkId, link_man_tel);
                            result.put("resultCode", "001");
                            result.put("resultMsg", "挂起成功，在审批平台流程实例ID：" + exeId);
                            // 开始保存工程建设项目审批事项办理详细信息
                            projectApplyService.saveAfterToXmspsxblxxxxb(9, exeId, link_man, exeHandledesc);
                            // 结束保存工程建设项目审批事项办理详细信息
                        } else {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误,在审批平台未查询到对应特殊环节");

                        }
                    }
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,在审批平台未查询到对应办件任务，exeId或ASSIGNER_CODE错误");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，流水号exeId");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 描述 挂起流程发送提醒给公众用户和后台人员
     * 
     * @author Rider Chen
     * @created 2017年6月22日 下午3:58:05
     * @param exeId
     * @param type
     */
    @SuppressWarnings("unchecked")
    public void handUpSendMsg(String exeId, String gqsj, String linkId, String link_man_tel) {
        // 获取流程实例
        Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String itemCode = flowExe.get("ITEM_CODE").toString();
        String lxdh = (String) executionService
                .getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" }, new Object[] { itemCode }).get("LXDH");
        Map<String, Object> link = flowHangInfoService.getByJdbc("T_WSBS_SERVICEITEM_LINK",
                new String[] { "record_id" }, new Object[] { linkId });
        StringBuffer mobileMsg = new StringBuffer();
        mobileMsg.append("您好！您在区综合审批服务平台上提交的申请件，遇特殊环节'");
        mobileMsg.append(link.get("LINK_NAME")).append("'需挂起操作，");
        mobileMsg.append("咨询电话：").append(link_man_tel).append(",");
        mobileMsg.append(lxdh).append(",");
        mobileMsg.append("申报号:").append(exeId).append("，标题为“");
        mobileMsg.append(flowExe.get("SUBJECT")).append("”");
        // 获取申请人类型
        String BSYHLX = (String) flowExe.get("BSYHLX");
        String sjhm = "";
        if (BSYHLX.equals("1")) {
            sjhm = (String) flowExe.get("SQRSJH");
        } else {
            sjhm = (String) flowExe.get("JBR_MOBILE");
        }
        if (StringUtils.isNotEmpty(sjhm)) {
            log.info("发送号码:" + sjhm + ",的内容是:" + mobileMsg.toString());
            SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
        }
        StringBuffer msg = new StringBuffer();
        msg.append("挂起通知，您在区综合审批服务平台上有办件被执行挂起操作，请注意挂起时限。申报号");
        msg.append(exeId).append("，标题为“").append(flowExe.get("SUBJECT")).append("”");
        SendMsgUtil.saveSendMsg(msg.toString(), link_man_tel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String reStart(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }

            String exeId = String.valueOf(flowInfo.get("exeId"));// 审批平台流程任务ID
            String assignerCode = String.valueOf(flowInfo.get("ASSIGNER_CODE"));// 挂起流程任务ID
            String Explain = String.valueOf(flowInfo.get("EXPLAIN"));// 挂起说明
            String fileid = String.valueOf(flowInfo.get("EXPLAIN_FILE_ID"));// 挂起说明附件ID
            // 获取流程实例信息
            Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                    new String[] { "EXE_ID", "IS_AUDITED", "ASSIGNER_TYPE", "TASK_STATUS", "ASSIGNER_CODE" },
                    new Object[] { exeId, "1", "1", "-1", assignerCode });
            // String endTime = DateTimeUtil.getStrOfDate(new Date(),
            // "yyyy-MM-dd HH:mm:ss");
            if (null != flowTask) {

                flowTaskService.startUpFlowTask(flowTask.get("TASK_ID").toString());
                flowHangInfoService.hangExplain(flowTask.get("TASK_ID").toString(), Explain, fileid);
                flowHangInfoService.endHangTime(flowTask.get("TASK_ID").toString());

                String qdsj = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
                Map<String, Object> exeHandledescInfo = new HashMap<String, Object>();
                String exeHandledesc = "";
                if (StringUtils.isNotEmpty((String) flowTask.get("EXE_HANDLEDESC"))) {
                    exeHandledesc = flowTask.get("EXE_HANDLEDESC").toString();
                    exeHandledesc = exeHandledesc + "\r\n";
                }
                exeHandledesc = exeHandledesc + "[" + flowTask.get("ASSIGNER_NAME").toString() + "]" + "于" + qdsj
                        + "启动";
                exeHandledescInfo.put("EXE_HANDLEDESC", exeHandledesc);
                flowTaskService.saveOrUpdate(exeHandledescInfo, "JBPM6_TASK", flowTask.get("TASK_ID").toString());
                flowTaskService.updateDescInfo(flowTask.get("PARENT_TASKID").toString(), exeHandledesc);
                handUpFinishSendMsg(flowTask.get("TASK_ID").toString());

                // 开始保存工程建设项目审批事项办理详细信息
                projectApplyService.saveAfterToXmspsxblxxxxb(10, (String) flowTask.get("EXE_ID"),
                        flowTask.get("ASSIGNER_NAME").toString(), exeHandledesc);
                // 结束保存工程建设项目审批事项办理详细信息
                result.put("resultCode", "001");
                result.put("resultMsg", "重启成功，在审批平台流程实例ID：" + flowTask.get("EXE_ID"));
            } else {
                result.put("resultCode", "004");
                result.put("resultMsg", "数据有误,在审批平台未查询到对应流程任务");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    /**
     * 
     * 描述 挂起流程结束发送提醒给公众用户和后台人员
     * 
     * @author Rider Chen
     * @created 2017年6月22日 下午3:58:05
     * @param exeId
     * @param type
     */
    public void handUpFinishSendMsg(String taskId) {
        // 获取exeId
        String exeId = executionService.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" }, new Object[] { taskId })
                .get("EXE_ID").toString();
        // 获取taksParentId
        String taksParentId = executionService
                .getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" }, new Object[] { taskId }).get("PARENT_TASKID")
                .toString();
        // 获取挂起信息
        Map<String, Object> hangInfo = flowTaskService.getHangInfo(taksParentId);
        // 挂起userID
        String userId = hangInfo.get("USERID") == null ? "" : hangInfo.get("USERID").toString();
        // 新挂起件有userId,短信通知，旧挂起件不需要短信通知
        if (StringUtils.isNotEmpty(userId)) {
            String linkId = hangInfo.get("LINK_ID").toString();
            String link_man_tel = hangInfo.get("LINK_MAN_TEL").toString();
            // 获取流程实例
            Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String itemCode = flowExe.get("ITEM_CODE").toString();
            Map<String, Object> maplxdh = executionService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                    new Object[] { itemCode });
            String lxdh = maplxdh.get("LXDH") == null ? "" : maplxdh.get("LXDH").toString();
            Map<String, Object> link = flowHangInfoService.getByJdbc("T_WSBS_SERVICEITEM_LINK",
                    new String[] { "record_id" }, new Object[] { linkId });
            StringBuffer mobileMsg = new StringBuffer();
            mobileMsg.append("您好！您在区综合审批服务平台上提交的申请件，特殊环节'");
            mobileMsg.append(link.get("LINK_NAME")).append("'结束并进入下一环节，");
            mobileMsg.append("咨询电话：").append(link_man_tel).append(",");
            mobileMsg.append(lxdh).append(",");
            mobileMsg.append("申报号:").append(exeId).append("，标题为“");
            mobileMsg.append(flowExe.get("SUBJECT")).append("”");
            // 获取申请人类型
            String BSYHLX = (String) flowExe.get("BSYHLX");
            String sjhm = "";
            if (BSYHLX.equals("1")) {
                sjhm = (String) flowExe.get("SQRSJH");
            } else {
                sjhm = (String) flowExe.get("JBR_MOBILE");
            }
            if (StringUtils.isNotEmpty(sjhm)) {
                log.info("发送号码:" + sjhm + ",的内容是:" + mobileMsg.toString());
                SendMsgUtil.saveSendMsg(mobileMsg.toString(), sjhm);
            }
            StringBuffer msg = new StringBuffer();
            msg.append("挂起结束通知,您在区综合审批服务平台上有办件被结束挂起操作，请注意及时办理。申报号");
            msg.append(exeId).append("，标题为“").append(flowExe.get("SUBJECT")).append("”");
            SendMsgUtil.saveSendMsg(msg.toString(), link_man_tel);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String saveFile(String uuid, String json) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(json)) {
            Map<String, Object> info = JSON.parseObject(json, Map.class);
            for (Entry<String, Object> ent : info.entrySet()) {
                if (ent.getValue() == null) {
                    info.put(ent.getKey(), "");
                }
            }
            String fileId = applyMaterService.saveOrUpdate(info, "T_MSJW_SYSTEM_FILEATTACH", null);

            Map<String, Object> fileTrans = new HashMap<String, Object>();
            fileTrans.put("FILE_ID", fileId);
            // 获取文件存储路径
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath");
            String fileWebType = properties.getProperty("fileWebType");

            fileTrans.put("FILE_TXT", FileUtil.convertFileToString(attachFileFolderPath + info.get("FILE_PATH")));
            fileTrans.put("FILE_PATH", info.get("FILE_PATH"));
            fileTrans.put("PLAT_TYPE", fileWebType);
            fileTrans.put("PARSE_STATUS", 0);
            applyMaterService.saveOrUpdate(fileTrans, "T_WSBS_FILETRANS", null);

            result.put("resultCode", "001");
            result.put("FILE_ID", fileId);
            result.put("resultMsg", "保存成功");
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getFileTrans(String platType) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> list = dao.findBySql(
                "select t.* from T_WSBS_FILETRANS t where t.plat_type = ? "
                        + "and t.parse_status = 0 and rownum<10 order by t.create_time asc",
                new Object[] { platType }, null);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getCallNumber(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("callType") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("callType")))) {
                String callType = String.valueOf(flowInfo.get("callType"));
                StringBuffer sql = new StringBuffer();
                sql.append(" select * from T_CKBS_CALLWAIT t where t.take_status = 0 ");
                sql.append(" and t.type = ? and substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
                List<Object> params = new ArrayList<Object>();
                params.add(callType);
                if (flowInfo.get("roomNo") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("roomNo")))) {
                    String roomNo = String.valueOf(flowInfo.get("roomNo"));
                    sql.append(" and t.room_no = ? ");
                    params.add(roomNo);
                }
                sql.append(" order by t.create_time asc ");
                // 获取叫号信息
                List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
                result.put("resultCode", "001");
                result.put("resultMsg", JSON.toJSONString(list));
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失关键数据，叫号类型callType");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        return JSON.toJSONString(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getQueryLine(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("callStatus") != null
                    && StringUtils.isNotBlank(String.valueOf(flowInfo.get("callStatus")))) {
                String callStatus = String.valueOf(flowInfo.get("callStatus"));
                StringBuffer sql = new StringBuffer();
                sql.append("select count(*) as total ");
                sql.append("from T_CKBS_QUEUERECORD t ");
                sql.append("left join T_CKBS_BUSINESSDATA b on b.BUSINESS_CODE = t.BUSINESS_CODE ");
                sql.append("where substr(t.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
                sql.append(" and T.CALL_STATUS = ? ");
                List<Object> params = new ArrayList<Object>();
                params.add(callStatus);
                if (flowInfo.get("lineNo") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("lineNo")))) {
                    String lineNo = String.valueOf(flowInfo.get("lineNo"));
                    sql.append(" and t.business_code in (select t1.business_code"
                            + " from T_CKBS_QUEUERECORD t1 where t1.line_no = ?  ");
                    sql.append("  and substr(t1.create_time, 0, 10) = to_char(sysdate, 'yyyy-mm-dd') ");
                    sql.append(" and t1.call_status = 0 )");
                    params.add(lineNo);
                }
                // 获取叫号信息
                List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
                result.put("resultCode", "001");
                result.put("resultMsg", JSON.toJSONString(list));
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失关键数据，叫号状态callStatus");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        return JSON.toJSONString(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String getQueryResult(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            if (flowInfo.get("exeId") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("exeId")))) {
                if (flowInfo.get("bjcxmm") != null && StringUtils.isNotBlank(String.valueOf(flowInfo.get("bjcxmm")))) {
                    String exeId = String.valueOf(flowInfo.get("exeId"));
                    String bjcxmm = String.valueOf(flowInfo.get("bjcxmm"));
                    // 获取流程实例信息
                    Map<String, Object> flowExe = null;
                    flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID", "BJCXMM" },
                            new Object[] { exeId, bjcxmm });
                    if (null != flowExe) {
                        result.put("resultCode", "001");
                        result.put("resultMsg", JSON.toJSONString(flowExe));
                    } else {
                        result.put("resultCode", "004");
                        result.put("resultMsg", "无查询结果，烦请确认申报号及查询码！");
                    }
                } else {
                    result.put("resultCode", "003");
                    result.put("resultMsg", "缺失关键数据，查询码bjcxmm");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失关键数据，申报号exeId");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        return JSON.toJSONString(result);
    }

    @Override
    public void delFileTrans() {
        // TODO Auto-generated method stub
        String sql = "delete  from T_WSBS_FILETRANS t where t.parse_status = 1  "
                + "and t.create_time <= to_char( add_months(sysdate,-1),'yyyy-MM-dd HH24:mi:ss')";
        dao.executeSql(sql, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public String exeBjFlow(String uuid, String flowInfoJson) {
        // TODO Auto-generated method stub
        Map<String, Object> result = new HashMap<String, Object>();
        if (uuid == null || !guid.equals(uuid)) {
            result.put("resultCode", "999");
            result.put("resultMsg", "对接口令错误");
            return JSON.toJSONString(result);
        }
        if (StringUtils.isNotEmpty(flowInfoJson)) {
            Map<String, Object> flowInfo = JSON.parseObject(flowInfoJson, Map.class);
            for (Entry<String, Object> ent : flowInfo.entrySet()) {
                if (ent.getValue() == null) {
                    flowInfo.put(ent.getKey(), "");
                }
            }
            String exeId = flowInfo.get("exeId") == null ? "" : flowInfo.get("exeId").toString();// 流水号
            String assignerCode = flowInfo.get("ASSIGNER_CODE") == null ? "" : flowInfo.get("ASSIGNER_CODE").toString();// 任务审核人
            String BJCLLB = flowInfo.get("BJCLLB") == null ? "[]" : flowInfo.get("BJCLLB").toString();// 补件材料列表
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> exeMap = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { exeId });
                if (null != exeMap) {
                    String startNode = flowNodeService.getNodeName(exeMap.get("DEF_ID").toString(),
                            Integer.parseInt(exeMap.get("DEF_VERSION").toString()), Jbpm6Constants.START_NODE);
                    List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
                    Map<String, Object> nextStepAssigner = new HashMap<String, Object>();
                    nextStepAssigner.put("nextStepAssignerCode", exeMap.get("CREATOR_ACCOUNT"));
                    nextStepAssigner.put("nextStepAssignerName", exeMap.get("CREATOR_NAME"));
                    String sqfs = (String) exeMap.get("SQFS");
                    if (StringUtils.isNotEmpty(sqfs) && sqfs.equals("1")) {
                        nextStepAssigner.put("nextStepAssignerType", "2");
                    } else {
                        nextStepAssigner.put("nextStepAssignerType", "1");
                    }
                    nextStepAssigner.put("taskOrder", 0);
                    listMap.add(nextStepAssigner);
                    Map<String, Object> backJson = new HashMap<String, Object>();
                    backJson.put(startNode, listMap);
                    // 获取审核任务信息
                    Map<String, Object> flowTask = flowTaskService.getByJdbc("JBPM6_TASK",
                            new String[] { "EXE_ID", "IS_AUDITED", "ASSIGNER_TYPE", "TASK_STATUS", "ASSIGNER_CODE" },
                            new Object[] { exeId, "1", "1", "1", assignerCode });
                    boolean isPassTime = false;
                    if (flowTask != null) {
                        // 获取状态
                        String TASK_STATUS = flowTask.get("TASK_STATUS").toString();
                        if (!TASK_STATUS.equals("1")) {
                            isPassTime = true;
                        }
                        if (isPassTime) {
                            result.put("resultCode", "004");
                            result.put("resultMsg", "数据有误,该流程任务已经被其他经办人处理,您的流程任务被取消!");
                        } else {
                            try {
                                flowInfo.put("EFLOW_EXEID", exeId);
                                flowInfo.put("BJCLLB", BJCLLB);
                                flowInfo.put("EFLOW_CURRENTTASK_ID", flowTask.get("TASK_ID"));
                                flowInfo.put("EFLOW_NEXTSTEPSJSON", JSON.toJSONString(backJson));
                                flowInfo.put("EFLOW_DEFVERSION", exeMap.get("DEF_VERSION"));
                                flowInfo.put("EFLOW_DEFID", exeMap.get("DEF_ID"));
                                flowInfo.put("APPLY_STATUS", exeMap.get("APPLY_STATUS"));
                                Map<String, Object> flowDef = serviceItemService.getByJdbc("JBPM6_FLOWDEF",
                                        new String[] { "DEF_ID" }, new Object[] { exeMap.get("DEF_ID") });
                                flowInfo.put("EFLOW_DEFKEY", flowDef.get("DEF_KEY"));
                                flowInfo.put("EFLOW_ISTEMPSAVE", "-1");
                                flowInfo.put("ASSIGNER_NAME", flowTask.get("ASSIGNER_NAME"));
                                jbpmService.doBjFlowJob(flowInfo);
                                result.put("resultCode", "001");
                                result.put("resultMsg", "退回补件成功");
                                exeDataService.sendExeBackMsg(exeId);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                log.error("", e);
                                result.put("resultCode", "999");
                                result.put("resultMsg", e.getMessage());
                            }
                        }
                    } else {
                        result.put("resultCode", "004");
                        result.put("resultMsg", "数据有误,在审批平台未查询到对应办件任务，exeId或ASSIGNER_CODE错误");
                    }
                } else {
                    result.put("resultCode", "004");
                    result.put("resultMsg", "数据有误,在审批平台未查询到对应办件任务，exeId或ASSIGNER_CODE错误");
                }
            } else {
                result.put("resultCode", "003");
                result.put("resultMsg", "缺失流程关键数据，流水号exeId");
            }
        } else {
            result.put("resultCode", "002");
            result.put("resultMsg", "接收的报文为空。");
        }
        log.info("流程发起请求处理结果：" + result.get("resultMsg"));
        return JSON.toJSONString(result);
    }
}
