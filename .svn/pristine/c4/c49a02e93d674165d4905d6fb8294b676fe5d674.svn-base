/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import net.evecom.core.util.*;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcRegisterInterfaceService;
import net.evecom.platform.bsfw.dao.BdcApplyDao;
import net.evecom.platform.bsfw.service.BdcDyrlxrService;
import net.evecom.platform.bsfw.service.BdcQlcApplyService;
import net.evecom.platform.bsfw.service.CommercialService;
import net.evecom.platform.bsfw.util.SmsSendUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.statis.service.StatisticsService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述 不动产全流程申请业务相关方法(实现类)
 * 
 * @author Keravon Feng
 * @created 2019年10月8日 上午10:48:14
 */
@Service("bdcQlcApplyService")
public class BdcQlcApplyServiceImpl extends BaseServiceImpl implements BdcQlcApplyService {

    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BdcQlcApplyServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private BdcApplyDao dao;

    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;

    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * bdcQueryService
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 引入jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    
    /**
     * bdcQlcMaterGenAndSignService
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    
    /**
     * commercialService
     */
    @Resource
    private CommercialService commercialService;
    
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    
    /**
     * executionService
     */
    @Resource
    private StatisticsService statisticsService;
    
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcRegisterInterfaceService bdcRegisterInterfaceService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcDyrlxrService bdcDyrlxrService;
    
    /**
     * serviceItemService
     */
    @Resource
    private ServiceItemService serviceItemService;
    
   /**
    * 引入Service
    */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterService;
    
    /**
     * 
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 
     * 描述 GenericDao
     * 
     * @author Keravon Feng
     * @created 2019年10月8日 上午10:49:07
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 不动产全流程申请多表业务通用存储事件接口方法
     * 
     * @author Keravon Feng
     * @created 2019年10月8日 上午10:51:08
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        // 当前节点名称
        String currNodeName = (String) flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
        // 调用默认的保存方法
        flowEventService.saveBusData(flowDatas);
        if (flowDatas.get("EFLOW_BUSRECORDID") != null) {
            String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
            String type = String.valueOf(flowDatas.get("HF_TYPE"));
            String itemCode = String.valueOf(flowDatas.get("ITEM_CODE"));
            List<Map<String, Object>> tables = dictionaryService.findByTypeCode(itemCode);
            String tableName = "", tabColName = "";
            if (tables != null && tables.size() > 0) {
                // 从数据字典中获取业务表对应关系
                for (Map<String, Object> map : tables) {
                    String diccode = String.valueOf(map.get("DIC_CODE"));
                    if (type.equals(diccode)) {
                        tableName = String.valueOf(map.get("DIC_NAME"));
                        tabColName = String.valueOf(map.get("DIC_DESC"));
                        break;
                    }
                }
            }
            if (!"".equals(tableName)) {
                flowDatas.put(tabColName, busRecordId);
                if ("开始".equals(currNodeName)) {
                    this.remove(tableName, tabColName, new Object[] { busRecordId });
                    this.saveOrUpdate(flowDatas, tableName, null);
                } else {
                    Map<String, Object> busMap = this.getByJdbc(tableName, new String[] { tabColName },
                            new Object[] { busRecordId });
                    String primaryKeyName = this.getPrimaryKeyName(tableName).get(0).toString();
                    if (busMap != null) {
                        String primaryKeyValue = busMap.get(primaryKeyName).toString();
                        this.saveOrUpdate(flowDatas, tableName, primaryKeyValue);
                    }
                }
            }

        }
        return flowDatas;
    }

    /**
     * 描述 获取子表的业务数据
     * 
     * @author Keravon Feng
     * @created 2019年10月10日 下午2:53:01
     * @param itemcode
     *            业务编码
     * @param type
     *            子业务类型
     * @param busId
     *            主表的Id
     * @return
     */
    @Override
    public Map<String, Object> getSubRecordInfo(String itemcode, String type, String busId) {
        // 获取子业务对应的表名（配置在数据字典中）
        List<Map<String, Object>> tables = dictionaryService.findByTypeCode(itemcode);
        String tableName = "", tabColName = "";
        if (tables != null && tables.size() > 0) {
            // 从数据字典中获取业务表对应关系
            for (Map<String, Object> map : tables) {
                String diccode = String.valueOf(map.get("DIC_CODE"));
                if (type.equals(diccode)) {
                    tableName = String.valueOf(map.get("DIC_NAME"));
                    // 主表主键，在子表中的字段名称配置在DIC_DESC
                    tabColName = String.valueOf(map.get("DIC_DESC"));
                    break;
                }
            }
        }
        if (!"".equals(tableName) && !"".equals(tabColName)) {
            Map<String, Object> record = dao.getByJdbc(tableName, new String[] { tabColName }, new Object[] { busId });
            if (record != null) {
                for (Map.Entry<String, Object> ent : record.entrySet()) {
                    if (ent.getKey().endsWith("_JSON")) {
                        if (ent.getValue() != null) {
                            if (ent.getValue() != null) {
                                Map<String, Object> items = new HashMap<String, Object>();
                                items.put("items", ent.getValue());
                                record.put(ent.getKey(), JSON.toJSON(items));
                            }
                        }
                    }
                }
            }
            return record;
        }
        return null;
    }

    /**
     * 
     * 描述 换发产权证书与不动产预告登记 是否复审
     * 
     * @author Keravon Feng
     * @created 2019年11月5日 上午10:57:04
     * @param flowVars
     * @return
     */
    public Set<String> getHfIsPreFuShen(Map<String, Object> flowVars) {
        boolean isNeed = false;
        String hftype = flowVars.get("HF_TYPE").toString();
        if (hftype.equals("2")) {
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("复审");
        } else {
            resultNodes.add("登簿");
        }
        return resultNodes;
    }

    /**
     * 
     * 描述 换发产权证书与不动产预告登记 是否发证
     * 
     * @author Keravon Feng
     * @created 2019年11月5日 上午11:45:39
     * @param flowVars
     * @return
     */
    public Set<String> getHfIsPreFz(Map<String, Object> flowVars) {
        boolean isNeed = false;
        String hftype = flowVars.get("HF_TYPE").toString();
        if (hftype.equals("1") || hftype.equals("2")) {
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("发证");
        } else {
            resultNodes.add("结束");
        }
        return resultNodes;
    }

    /**
     * 描述 国有建设用地及房屋所有权转移登记 是否初审决策
     * 
     * @author Keravon Feng
     * @created 2019年12月13日 上午10:14:19
     * @param flowVars
     * @return
     */
    @Override
    public Set<String> getIsPreChuShen(Map<String, Object> flowVars) {
        String exeId = StringUtil.getValue(flowVars, "EFLOW_EXEID");
        Map<String,Object> exeMap = dao.getByJdbc("SELECT * FROM JBPM6_EXECUTION A LEFT JOIN T_BDCQLC_GYJSJFWZYDJ B " +
                "ON A.BUS_RECORDID = B.YW_ID WHERE A.EXE_ID = ?", new Object[]{exeId});
        String type = StringUtil.getValue(exeMap, "ZYDJ_TYPE");
        String isqcwb = StringUtil.getValue(exeMap, "ISQCWB");
        Set<String> resultNodes = new HashSet<String>();
        if (StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb)) {
            resultNodes.add("登簿");
        } else {
            if (type.equals("4") || type.equals("7")) {
                resultNodes.add("初审");
            } else if (type.equals("5") || type.equals("1") || type.equals("6")) {
                String is_finishtax = StringUtil.getValue(flowVars,"IS_FINISHTAX");
                if (is_finishtax.equals("0")) {
                    resultNodes.add("预登簿");
                } else {
                    resultNodes.add("登簿");
                }
            } else if (type.equals("3")) {
                resultNodes.add("登簿");
            }
        }
        return resultNodes;
    }

    /**
     * 描述 国有建设用地及房屋所有权转移登记 是否完税 决策
     * 
     * @author Keravon Feng
     * @created 2019年12月13日 上午10:14:19
     * @param flowVars
     * @return
     */
    @Override
    public Set<String> getIsFinish(Map<String, Object> flowVars) {
        boolean isNeed = false;
        String type = flowVars.get("IS_FINISHTAX") == null ? "" : flowVars.get("IS_FINISHTAX").toString();
        if (type.equals("0")) {
            isNeed = true;
        }
        Set<String> resultNodes = new HashSet<String>();
        if (isNeed) {
            resultNodes.add("预登簿");
        } else {
            resultNodes.add("登簿");
        }
        return resultNodes;
    }

    /**
     * 描述 不动产全流程业务涉及缴费发证等业务需要后台填报的部分
     * 
     * @author Keravon Feng
     * @created 2019年12月17日 下午1:09:08
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> afterBusData(Map<String, Object> flowDatas) {
        // 下一环节的名称
        String eflow_nextnodename = String.valueOf(flowDatas.get("EFLOW_NEXTNODENAME"));
        if ("办结".equals(eflow_nextnodename) || "缮证".equals(eflow_nextnodename)) {
            String eveId = String.valueOf(flowDatas.get("EFLOW_EXEID"));
            Map<String, Object> fzinfo = dao.getByJdbc("T_BDCQLC_SFFZINFO", new String[] { "EVEID" },
                    new Object[] { eveId });
            if (fzinfo == null) {
                fzinfo = new HashMap<String, Object>();
                fzinfo.put("EVEID", eveId);
                fzinfo.put("ITEMNAME", flowDatas.get("ITEM_NAME"));
                fzinfo.put("ITEMCODE", flowDatas.get("ITEM_CODE"));
                Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { eveId });
                if (execution != null) {
                    fzinfo.put("SQRMC", execution.get("SQRMC"));
                    fzinfo.put("SQRZH", execution.get("SQRSFZ"));
                }
                fzinfo.put("OPTYPE", 1);
                dao.saveOrUpdateForAssignId(fzinfo, "T_BDCQLC_SFFZINFO", eveId);
            }
        }
        return flowDatas;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> beforeBdcdb(Map<String, Object> flowDatas) throws Exception {
        // TODO Auto-generated method stub
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请
                                                                  // 触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));// 是否全程网办方式（1：是）
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) && StringUtils.isEmpty(eflowIsback)
                && !"1".equals(eflowIstempsave) && "1".equals(isqcwb)) {
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { exeId });
                if (execution != null) {
                    Map<String, Object> user = AppUtil.getLoginMember();
                    if (null != user && user.size() > 0) {
                        String username = user.get("YHMC").toString();
                        String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                        Map<String, Object> info = new HashMap<String, Object>();
                        info.put("exe_id", exeId);
                        info.put("item_code", String.valueOf(execution.get("ITEM_CODE")));
                        info.put("dbr", username);
                        info.put("dbsj", dateStr);
                        AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "jsywqdUrl");                      
                        if (ajaxJson.isSuccess()) {
                            String retJson = ajaxJson.getJsonString();
                            if (StringUtils.isNotEmpty(retJson)) {
                                retJson = retJson.replace("\r\n", "");
                                Map<String, Object> result = JSON.parseObject(retJson, Map.class);
                                if (null != result && result.size() > 0) {
                                    String retcode = result.get("retcode").toString();
                                    String dbzt = result.get("dbzt").toString();
                                    if (StringUtils.isNotEmpty(retcode) && StringUtils.isNotEmpty(dbzt)
                                            && retcode.equals("00") && dbzt.equals("1")) {
                                        List<Map<String, Object>> qzinfo = (List<Map<String, Object>>) result
                                                .get("qzinfo");
                                        if (null != qzinfo && qzinfo.size() > 0) {
                                            String gdzt = qzinfo.get(0).get("gdzt").toString();
                                            if (StringUtils.isNotEmpty(gdzt) && gdzt.equals("1")) {// 判断归档状态
                                                flowDatas.put("success", true);
                                                flowDatas.put("data", retJson);
                                                flowDatas.put("BDC_DBR", username);
                                                flowDatas.put("BDC_DJSJ", dateStr);
                                                flowDatas.put("BDC_DBZT", gdzt);
                                                flowDatas.put("BDC_BDCDJZMH", qzinfo.get(0).get("qzzh"));
                                                flowDatas.put("BDC_DBJSON", retJson);

                                            } else {
                                                flowDatas.put("success", false);
                                                flowDatas.put("SIGN_MSG", "归档失败，" + qzinfo.get(0).get("ret_msg"));
                                                throw new Exception("归档失败，" + qzinfo.get(0).get("ret_msg"));
                                            }
                                        } else {
                                            flowDatas.put("success", false);
                                            flowDatas.put("SIGN_MSG", "登簿失败，返回信息qzinfo为空");
                                            throw new Exception("登簿失败，返回信息qzinfo为空");
                                        }
                                    } else {
                                        flowDatas.put("success", false);
                                        flowDatas.put("SIGN_MSG", "登簿失败，" + result.get("ret_msg"));
                                        throw new Exception("登簿失败，" + result.get("ret_msg").toString());
                                    }
                                } else {
                                    flowDatas.put("success", false);
                                    flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                    throw new Exception("登薄失败，返回信息为空");
                                }
                            } else {
                                flowDatas.put("success", false);
                                flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                throw new Exception("登薄失败，返回信息为空");
                            }
                        } else {
                            flowDatas.put("success", false);
                            flowDatas.put("SIGN_MSG", "登簿失败，" + ajaxJson.getMsg());
                            throw new Exception("登簿失败，" + ajaxJson.getMsg());
                        }
                    } else {
                        flowDatas.put("SIGN_FLAG", false);
                        flowDatas.put("SIGN_MSG", "获取登录用户信息失败，请重新登录。");
                        throw new Exception("获取登录用户信息失败，请重新登录。");
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "未找到受理申请编号" + exeId + "对应的流程实例。");
                    throw new Exception("未找到受理申请编号" + exeId + "对应的流程实例。");
                }
            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，请先暂存后在提交");
                throw new Exception("办件编号为空，请先暂存后在提交");
            }
        }
        return flowDatas;
    }
    
    
    /**
     * 描述   不动产登薄前置接口(待受理)-自动跳转
     * @author Allin Lin
     * @created 2020年9月10日 下午9:29:17
     * @param flowDatas
     * @return
     * @throws Exception
     */
     public Map<String, Object> beforeBdcdbAuto(Map<String, Object> flowDatas) throws Exception{
         String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
         String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请
                                                                   // 触发签章）
         String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));//前台申报方式（1：智能审批 0：全程网办）
         String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
         String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
         if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) && StringUtils.isEmpty(eflowIsback)
                 && !"1".equals(eflowIstempsave)) {
             if (StringUtils.isNotEmpty(exeId)) {
                 Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                         new Object[] { exeId });
                 String pttSqfs  = StringUtil.getValue(execution,"PTT_SQFS");//平潭通申报方式（1：智能审批 2：全程网办）
                 if (execution != null) {
                       if((StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb)) ||
                               (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs))){
                           String username = dictionaryService.findByDicCodeAndTypeCode("dbr","dslzdtz");
                           String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                           Map<String, Object> info = new HashMap<String, Object>();
                           info.put("exe_id", exeId);
                           info.put("item_code", String.valueOf(execution.get("ITEM_CODE")));
                           info.put("dbr", username);
                           info.put("dbsj", dateStr);
                           log.info("不动产流程申报号："+exeId+"-开始执行自动登簿！");
                           AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info,"jsywqdUrl");
                           if (ajaxJson.isSuccess()) {
                               String retJson = ajaxJson.getJsonString();
                               if (StringUtils.isNotEmpty(retJson)) {
                                   retJson = retJson.replace("\r\n", "");
                                   Map<String, Object> result = JSON.parseObject(retJson, Map.class);
                                   if (null != result && result.size() > 0) {
                                       String retcode = result.get("retcode").toString();
                                       String dbzt = result.get("dbzt").toString();
                                       if (StringUtils.isNotEmpty(retcode) && StringUtils.isNotEmpty(dbzt)
                                               && retcode.equals("00") && dbzt.equals("1")) {
                                           List<Map<String, Object>> qzinfo = (List<Map<String, Object>>) result
                                                   .get("qzinfo");
                                           if (null != qzinfo && qzinfo.size() > 0) {
                                               String gdzt = qzinfo.get(0).get("gdzt").toString();
                                               if (StringUtils.isNotEmpty(gdzt) && gdzt.equals("1")) {// 判断归档状态
                                                   flowDatas.put("success", true);
                                                   flowDatas.put("data", retJson);
                                                   flowDatas.put("BDC_DBR", username);
                                                   flowDatas.put("BDC_DJSJ", dateStr);
                                                   flowDatas.put("BDC_DBZT", gdzt);
                                                   flowDatas.put("BDC_DBJG", "登簿成功");
                                                   flowDatas.put("BDC_BDCDJZMH", qzinfo.get(0).get("qzzh"));
                                                   flowDatas.put("BDC_DBJSON", retJson); 
                                                   //抵押权注销登记
                                                   flowDatas.put("BDC_ZXR", username);//注销人
                                                   flowDatas.put("BDC_ZXSJ", dateStr);//注销时间
                                                   log.info("不动产流程申报号："+exeId+"-登簿成功！");
                                               } else {
                                                   flowDatas.put("success", false);
                                                   flowDatas.put("SIGN_MSG", "归档失败，" + qzinfo.get(0).get("ret_msg"));
                                                   throw new Exception("不动产流程申报号："+exeId+"归档失败，" + qzinfo.get(0).get("ret_msg"));
                                               }
                                           } else {
                                               flowDatas.put("success", false);
                                               flowDatas.put("SIGN_MSG", "登簿失败，返回信息qzinfo为空");
                                               throw new Exception("不动产流程申报号："+exeId+"登簿失败，返回信息qzinfo为空");
                                           }
                                       } else {
                                           flowDatas.put("success", false);
                                           flowDatas.put("SIGN_MSG", "登簿失败，" + result.get("ret_msg"));
                                           throw new Exception("不动产流程申报号："+exeId+"登簿失败，"
                                           + result.get("ret_msg").toString());
                                       }
                                   } else {
                                       flowDatas.put("success", false);
                                       flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                       throw new Exception("不动产流程申报号："+exeId+"登薄失败，返回信息为空");
                                   }
                               } else {
                                   flowDatas.put("success", false);
                                   flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                   throw new Exception("不动产流程申报号："+exeId+"登薄失败，返回信息为空");
                               }
                           } else {
                               flowDatas.put("success", false);
                               flowDatas.put("SIGN_MSG", "登簿失败，" + ajaxJson.getMsg());
                               throw new Exception("不动产流程申报号："+exeId+"登簿失败，" + ajaxJson.getMsg());
                           }
                       }
                 } else {
                     flowDatas.put("SIGN_FLAG", false);
                     flowDatas.put("SIGN_MSG", "未找到受理申请编号" + exeId + "对应的流程实例。");
                     throw new Exception("未找到受理申请编号" + exeId + "对应的流程实例。");
                 }
             } else {
                 flowDatas.put("SIGN_FLAG", false);
                 flowDatas.put("SIGN_MSG", "办件编号为空，请先暂存后在提交");
                 throw new Exception("办件编号为空，请先暂存后在提交");
             }
         }
         return flowDatas;
     }

    /**
     * 描述:不动产登薄前置接口(待受理)（用于类似国有转移表单结构的登簿，权利人可能为两个人，登簿在权利人信息中）
     *
     * @author Madison You
     * @created 2020/9/17 14:33:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> beforeBdcdbAutoGyzy(Map<String, Object> flowDatas) throws Exception {
        String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请
        // 触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));// 前台申请方式（1：智能审批
                                                                      // 0：全程网办）
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) && StringUtils.isEmpty(eflowIsback)
                && !"1".equals(eflowIstempsave)) {
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { exeId });
                String pttSqfs = StringUtil.getValue(execution, "PTT_SQFS");// 平潭通申报方式（1:智能审批  2:全程网办)
                if (execution != null) {
                    String busTableName = (String) execution.get("BUS_TABLENAME");
                    String busRecordId = (String) execution.get("BUS_RECORDID");
                    //国有转移6个事项虚拟主表替换真实表名称
                    if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                        busTableName = "T_BDCQLC_GYJSJFWZYDJ";
                    }
                    String primaryKey = dao.getPrimaryKeyName(busTableName).get(0).toString();
                    Map<String, Object> busMap = dao.getByJdbc(busTableName, new String[] { primaryKey },
                            new Object[] { busRecordId });
                    if ((StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb))
                            || (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs))) {
                        String username = dictionaryService.findByDicCodeAndTypeCode("dbr", "dslzdtz");
                        String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                        Map<String, Object> info = new HashMap<String, Object>();
                        info.put("exe_id", exeId);
                        info.put("item_code", String.valueOf(execution.get("ITEM_CODE")));
                        info.put("dbr", username);
                        info.put("dbsj", dateStr);
                        AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "jsywqdUrl");
                        if (ajaxJson.isSuccess()) {
                            String retJson = ajaxJson.getJsonString();
                            if (StringUtils.isNotEmpty(retJson)) {
                                retJson = retJson.replace("\r\n", "");
                                Map<String, Object> result = JSON.parseObject(retJson, Map.class);
                                if (null != result && result.size() > 0) {
                                    String retcode = result.get("retcode").toString();
                                    String dbzt = result.get("dbzt").toString();
                                    if (StringUtils.isNotEmpty(retcode) && StringUtils.isNotEmpty(dbzt)
                                            && retcode.equals("00") && dbzt.equals("1")) {
                                        List<Map<String, Object>> qzinfo = (List<Map<String, Object>>) result
                                                .get("qzinfo");
                                        if (null != qzinfo && qzinfo.size() > 0) {
                                            String powJson = StringUtil.getValue(busMap, "POWERPEOPLEINFO_JSON");
                                            List<Map<String, Object>> powObj = JSON.parseObject(powJson, List.class);
                                            if (powObj.size() == qzinfo.size()) {
                                                int iflag = 0;
                                                for (int i = 0; i < qzinfo.size(); i++) {
                                                    String gdzt = qzinfo.get(i).get("gdzt").toString();
                                                    if ("1".equals(gdzt)) {
                                                        for (int j = 0; j < powObj.size(); j++) {
                                                            if (qzinfo.get(i).get("qlr_mc")
                                                                    .equals(powObj.get(j).get("POWERPEOPLENAME"))
                                                                    && qzinfo.get(i).get("qlr_zh").equals(
                                                                            powObj.get(j).get("POWERPEOPLEIDNUM"))) {
                                                                powObj.get(j).put("BDC_SZZH",
                                                                        qzinfo.get(i).get("qzzh"));
                                                                powObj.get(j).put("BDC_CZR", username);
                                                                powObj.get(j).put("BDC_CZSJ", dateStr);
                                                                iflag = iflag + 1;
                                                            }
                                                        }
                                                    } else {
                                                        flowDatas.put("success", false);
                                                        flowDatas.put("SIGN_MSG",
                                                                "归档失败，" + qzinfo.get(i).get("ret_msg"));
                                                        throw new Exception("归档失败，" + qzinfo.get(i).get("ret_msg"));
                                                    }
                                                }
                                                if (iflag != powObj.size()) {
                                                    flowDatas.put("success", false);
                                                    flowDatas.put("SIGN_MSG", "登簿失败，存在与受理时的权利不一致的情况");
                                                    throw new Exception("登簿失败，存在与受理时的权利不一致的情况");
                                                } else {
                                                    flowDatas.put("POWERPEOPLEINFO_JSON", JSON.toJSONString(powObj));
                                                    flowDatas.put("BDC_DBJG", "登簿成功");
                                                    flowDatas.put("BDC_DBR", username);
                                                    flowDatas.put("BDC_DJSJ", dateStr);
                                                    flowDatas.put("data", retJson);
                                                    flowDatas.put("success", true);
                                                    flowDatas.put("BDC_DBZT", "1");
                                                    flowDatas.put("HZ_OPINION_CONTENT", "准予登记");// 审核意见-核定意见
                                                    flowDatas.put("HZ_OPINION_NAME", username);// 审核意见-填写人
                                                    flowDatas.put("HZ_OPINION_TIME", dateStr);// 审核意见-填写时间
                                                    // 抵押首次登记转本
                                                    flowDatas.put("BDC_DBSJ", dateStr);
                                                    flowDatas.put("DJSHXX_HZYJ", "准予登记");// 审核意见-核定意见
                                                    flowDatas.put("DJSHXX_SHR", username);// 审核意见-填写人
                                                    flowDatas.put("DJSHXX_SHJSRQ", dateStr);// 审核意见-填写时间
                                                }
                                            } else {
                                                flowDatas.put("success", false);
                                                flowDatas.put("SIGN_MSG", "登簿失败，与受理时的权利人数不匹配");
                                                throw new Exception("登簿失败，与受理时的权利人数不匹配");
                                            }
                                        } else {
                                            flowDatas.put("success", false);
                                            flowDatas.put("SIGN_MSG", "登簿失败，返回信息qzinfo为空");
                                            throw new Exception("登簿失败，返回信息qzinfo为空");
                                        }
                                    } else {
                                        flowDatas.put("success", false);
                                        flowDatas.put("SIGN_MSG", "登簿失败，" + result.get("ret_msg"));
                                        throw new Exception("登簿失败，" + result.get("ret_msg").toString());
                                    }
                                } else {
                                    flowDatas.put("success", false);
                                    flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                    throw new Exception("登薄失败，返回信息为空");
                                }
                            } else {
                                flowDatas.put("success", false);
                                flowDatas.put("SIGN_MSG", "登薄失败，返回信息为空");
                                throw new Exception("登薄失败，返回信息为空");
                            }
                        } else {
                            flowDatas.put("success", false);
                            flowDatas.put("SIGN_MSG", "登簿失败，" + ajaxJson.getMsg());
                            throw new Exception("登簿失败，" + ajaxJson.getMsg());
                        }
                    }
                } else {
                    flowDatas.put("SIGN_FLAG", false);
                    flowDatas.put("SIGN_MSG", "未找到受理申请编号" + exeId + "对应的流程实例。");
                    throw new Exception("未找到受理申请编号" + exeId + "对应的流程实例。");
                }
            } else {
                flowDatas.put("SIGN_FLAG", false);
                flowDatas.put("SIGN_MSG", "办件编号为空，请先暂存后在提交");
                throw new Exception("办件编号为空，请先暂存后在提交");
            }
        }
        return flowDatas;
    }


    @Override
    public Map<String, Object> afterBdcdbEnd(Map<String, Object> flowDatas) {
        // TODO Auto-generated method stub
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请
                                                                  // 触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));// 是否全程网办方式（1：是）
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) && StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb)
                && StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
            String BDC_DBZT = StringUtil.getString(flowDatas.get("BDC_DBZT"));// 登薄状态
            if (StringUtils.isNotEmpty(BDC_DBZT) && BDC_DBZT.equals("1")) {
                flowAutoFinish(exeId);
            }
        }
        return flowDatas;
    }

    @Override
    public Map<String, Object> afterBdcdbAutoProcess(Map<String, Object> flowDatas) {
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请
                                                                  // 触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));//前台申请方式（1：智能审批 0：全程网办）
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        String BDC_DBZT = StringUtil.getString(flowDatas.get("BDC_DBZT"));// 登薄状态
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) 
                && StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
            Map<String, Object> execution = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            String pttSqfs  = StringUtil.getValue(execution,"PTT_SQFS");//平潭通申报方式（1：智能审批 2：全程网办）
            if((StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb)) ||
                (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs))){
                if (StringUtils.isNotEmpty(BDC_DBZT) && BDC_DBZT.equals("1")) {
                    flowAutoProcess(exeId);
                }
            }
        }
        return flowDatas;
    }
    
    /**
     * 
     * 描述 不动产全程网办待受理签章成功-自动流转至登簿环节（目前应用于预抵、首登子项全程网办个性化流程）
     * @author Allin Lin
     * @created 2020年12月17日 下午4:07:50
     * @param flowDatas
     * @return
     */
    public Map<String, Object> bdcQcwbDslAutoProcess(Map<String, Object> flowDatas) {
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请// 触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));// 前台申请方式（1：智能审批0：全程网办 2（预抵、首登子项全程网办个性化流程））
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs) && StringUtils.isEmpty(eflowIsback)
                && !"1".equals(eflowIstempsave)) {
            String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
            if ((StringUtils.isNotEmpty(isqcwb) && "2".equals(isqcwb))) {
                    flowAutoProcessToDb(exeId);
            }
        }
        return flowDatas;
    }   
    
    /**
     * 描述    不动产(智能审批开始环节后置事件)-前台申报开始环节审核人、收件人固定
     * @author Allin Lin
     * @created 2020年9月14日 上午9:07:09
     * @param flowDatas
     * @return
     */
    public Map<String, Object> bdcChangeGdShr(Map<String, Object> flowDatas){
        String sqfs = StringUtil.getString(flowDatas.get("SQFS"));// 受理方式（1：网上申请 //触发签章）
        String isqcwb = StringUtil.getString(flowDatas.get("ISQCWB"));// 前台申报方式（1：智能审批 0：全程网办  2（预抵与首登全程网办个性化） ）
        String eflowIstempsave = StringUtil.getString(flowDatas.get("EFLOW_ISTEMPSAVE"));// 是否暂存操作
        String eflowIsback = StringUtil.getString(flowDatas.get("EFLOW_ISBACK"));// 退回不执行
        if (StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
               && StringUtils.isEmpty(eflowIsback) && !"1".equals(eflowIstempsave)) {
            String exeId = StringUtil.getString(flowDatas.get("EFLOW_EXEID"));// 申报号
            Map<String, Object> exe = this.getByJdbc("jbpm6_execution", new String[]{"EXE_ID"}, new Object[]{exeId});
            String pttSqfs = StringUtil.getValue(exe, "PTT_SQFS");//平潭通申请方式(1:智能审批 2：全程网办)
            if((StringUtils.isNotEmpty(isqcwb) && "1".equals(isqcwb)) ||
                 (StringUtils.isNotEmpty(isqcwb) && "0".equals(isqcwb)) ||   
                 (StringUtils.isNotEmpty(isqcwb) && "2".equals(isqcwb)) ||
                (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs)) ||
                (StringUtils.isNotEmpty(pttSqfs) && "2".equals(pttSqfs))){
                //更新指定申报号开始环节流程任务表信息（团队审核人名称、执行动作描述）
                String shr = dictionaryService.findByDicCodeAndTypeCode("gdshr", "dslzdtz");
                List<Map<String, Object>> taskLists = this.getAllByJdbc("JBPM6_TASK", 
                        new String[]{"EXE_ID","TASK_NODENAME"}, new Object[]{exeId,"开始"});           
                for (Map<String, Object> map : taskLists) {               
                    String handlerDesc = StringUtil.getString(map.get("EXE_HANDLEDESC"));              
                    map.put("TEAM_NAMES", shr);//固定开始环节-团队审核人
                    int beginIndex = handlerDesc.indexOf("[");
                    int secondIndex = handlerDesc.indexOf("]");
                    if(StringUtils.isNotEmpty(handlerDesc) && beginIndex<secondIndex){
                        StringBuilder sb = new StringBuilder(handlerDesc);
                        map.put("EXE_HANDLEDESC", sb.replace(beginIndex+1,secondIndex,shr));
                        this.saveOrUpdate(map, "JBPM6_TASK", StringUtil.getString(map.get("TASK_ID"))); 
                    }
                }
                //更新指定申报号流程发起人姓名
                exe.put("CREATOR_NAME", shr);
                this.saveOrUpdate(exe, "jbpm6_execution", StringUtil.getString(exe.get("EXE_ID")));   
            }
        }
        return flowDatas;
    }

    /**
     * 
     * 描述： 流程自动流转
     * 
     * @author Rider Chen
     * @created 2020年8月28日 上午10:53:49
     * @param exeId
     */
    @SuppressWarnings({ "unchecked" })
    private void flowAutoProcess(String exeId) {
        Map<String, Object> flowexe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        Map<String, Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { flowexe.get("DEF_ID") });
        String itemCode = (String) flowexe.get("ITEM_CODE");
        String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
        if (StringUtils.isNotEmpty(curStepNames) && !curStepNames.equals("办结")) {
            String ASSIGNER_CODE = "ssyshz";
            String ASSIGNER_NAME = "自动跳转";
            Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                    new Object[] { exeId, curStepNames, ASSIGNER_CODE, ASSIGNER_NAME, "1" });
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
            flowVars.put("EFLOW_HANDLE_OPINION", "登薄成功自动流转");

            flowVars.put("EFLOW_CREATORNAME", ASSIGNER_NAME);

            // 用于判断是否来源与接口调用的办件过程执行
            flowVars.put("DATAFROM", "webservice");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("EXECUTE_TIME", df.format(new Date()));
            try {
                String nextStepJson = jbpmService.getNextStepsJson((String) flowDef.get("DEF_ID"),
                        Integer.valueOf(flowDef.get("VERSION").toString()), (String) flowexe.get("CUR_STEPNAMES"),
                        flowVars);
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                jbpmService.doFlowJob(flowVars);
                // 不等于办结时候，继续自动流转
                flowAutoProcess(exeId);
            } catch (Exception e) {
                log.error("流程自动流转失败", e);
            }
        }
    }

    /**
     * 
     * 描述： 流程自动办结
     * 
     * @author Rider Chen
     * @created 2020年8月28日 上午10:53:49
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    private void flowAutoFinish(String exeId) {
        Map<String, Object> flowexe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        Map<String, Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { flowexe.get("DEF_ID") });
        String itemCode = (String) flowexe.get("ITEM_CODE");
        String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
        String ASSIGNER_CODE = "ssyshz";
        String ASSIGNER_NAME = "自动跳转";
        Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                new Object[] { exeId, curStepNames, ASSIGNER_CODE, ASSIGNER_NAME, "1" });
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
        flowVars.put("EFLOW_HANDLE_OPINION", "登薄成功自动办结");

        flowVars.put("EFLOW_CREATORNAME", ASSIGNER_NAME);

        // 用于判断是否来源与接口调用的办件过程执行
        flowVars.put("DATAFROM", "webservice");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        flowVars.put("EXECUTE_TIME", df.format(new Date()));
        try {
            flowVars.put("EFLOW_DESTTOEND", "");
            flowVars.put("EFLOW_NEXTSTEPSJSON", "");
            jbpmService.doFlowJob(flowVars);
        } catch (Exception e) {
            log.error("流程自动办结失败", e);
        }
    }
    
    
    /**
     * 描述    流程自动流转至登簿环节
     * @author Allin Lin
     * @created 2020年12月17日 下午4:27:29
     * @param exeId
     */
    @SuppressWarnings({ "unchecked" })
    private void flowAutoProcessToDb(String exeId) {
        Map<String, Object> flowexe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        Map<String, Object> flowDef = this.getByJdbc("JBPM6_FLOWDEF", new String[] { "DEF_ID" },
                new Object[] { flowexe.get("DEF_ID") });
        String itemCode = (String) flowexe.get("ITEM_CODE");
        String curStepNames = (String) flowexe.get("CUR_STEPNAMES");
        if (StringUtils.isNotEmpty(curStepNames) && !curStepNames.equals("登簿")) {
            String ASSIGNER_CODE = "ssyshz";
            String ASSIGNER_NAME = "自动跳转";
            Map<String, Object> flowTask = this.getByJdbc("JBPM6_TASK",
                    new String[] { "EXE_ID", "TASK_NODENAME", "ASSIGNER_CODE", "ASSIGNER_NAME", "TASK_STATUS" },
                    new Object[] { exeId, curStepNames, ASSIGNER_CODE, ASSIGNER_NAME, "1" });
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
            flowVars.put("EFLOW_HANDLE_OPINION", "签章成功自动流转");

            flowVars.put("EFLOW_CREATORNAME", ASSIGNER_NAME);

            // 用于判断是否来源与接口调用的办件过程执行
            flowVars.put("DATAFROM", "webservice");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            flowVars.put("EXECUTE_TIME", df.format(new Date()));
            try {
                String nextStepJson = jbpmService.getNextStepsJson((String) flowDef.get("DEF_ID"),
                        Integer.valueOf(flowDef.get("VERSION").toString()), (String) flowexe.get("CUR_STEPNAMES"),
                        flowVars);
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
                jbpmService.doFlowJob(flowVars);
                // 不等于登簿时候，继续自动流转
                flowAutoProcessToDb(exeId);
            } catch (Exception e) {
                log.error("流程自动流转失败", e);
            }
        }
    }
    
    /**
     * 描述   获取待受理需要自动跳转记录(自动跳转账号指定申请方式)  
     * @author Allin Lin
     * @created 2020年9月10日 上午9:24:34
     * @return
     */
    public List<Map<String, Object>> findNeedAutoJump(){
        List<Map<String, Object>> autoDatas = new ArrayList<Map<String,Object>>();
        //1、获取自动跳转账号（ssyshz）前台网上申报、智能审批待受理需自动跳转的记录。
        StringBuffer sql = new StringBuffer("select S.SXLX,T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES,T.SQFS ");
        sql.append(",T.PTT_SQFS,TA.ASSIGNER_NAME,TA.ASSIGNER_CODE,TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE ");
        sql.append(",TA.TASK_ID,T.BUS_RECORDID,T.BUS_TABLENAME");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM S ON S.ITEM_CODE=T.ITEM_CODE ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" AND TA.ASSIGNER_CODE = 'ssyshz' AND TA.ASSIGNER_NAME='自动跳转' AND T.SQFS = '1' ");
        sql.append(" AND D.DEF_KEY IN ( ");
        sql.append(" select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='dslzdtz' ");
        sql.append(" ) and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "待受理", "待受理" }, null);
        //2.判断对应业务表的是否为智能审批（ISQCWB=1）或者来源于平潭通智能审批（PTT_SQFS=1）
        for (Map<String, Object> map : datas) {
            String busTableName = StringUtil.getString(map.get("BUS_TABLENAME"));
            //国有转移7个事项虚拟主表替换真实表名称
            if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                busTableName = "T_BDCQLC_GYJSJFWZYDJ";
            }
            Map<String, Object> record = this.getByJdbc(busTableName,new String[]{"YW_ID"},
                    new Object[]{map.get("BUS_RECORDID")});
            if(StringUtil.isNotEmpty(record) && "1".equals(StringUtil.getString(record.get("ISQCWB")))){
                map.put("ISQCWB", record.get("ISQCWB"));
                autoDatas.add(map);
            }else if("1".equals(StringUtil.getString(map.get("PTT_SQFS")))){
                autoDatas.add(map);
            }else if(StringUtil.isNotEmpty(record) && 
                    "2".equals(StringUtil.getString(record.get("ISQCWB")))){//预抵、首登子项全程网办个性化流程
                map.put("ISQCWB", record.get("ISQCWB"));
                autoDatas.add(map);
            }
        }
        return autoDatas;
    }
    
    /**
     * 描述    获取平潭通网上预审需要自动跳转记录
     * @author Allin Lin
     * @created 2020年9月10日 上午9:24:34
     * @return
     */
    public List<Map<String, Object>> findPttNeedAutoJump(){
        List<Map<String, Object>> autoDatas = new ArrayList<Map<String,Object>>();
        //1、获取自动跳转账号（ssyshz）平潭通智能审批网上预审需自动跳转的记录。
        StringBuffer sql = new StringBuffer("select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES,T.SQFS,T.PTT_SQFS");
        sql.append(",TA.ASSIGNER_NAME,TA.ASSIGNER_CODE,TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE ");
        sql.append(",TA.TASK_ID,T.BUS_RECORDID,T.BUS_TABLENAME");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append("ON T.DEF_ID=D.DEF_ID  ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 and ");
        sql.append(" ( T.PTT_SQFS='1'  OR T.PTT_SQFS='2') ");
        sql.append(" AND TA.ASSIGNER_CODE = 'ssyshz' AND TA.ASSIGNER_NAME='自动跳转' AND T.SQFS = '1' ");
        sql.append(" AND D.DEF_KEY IN ( ");
        sql.append(" select dic.dic_code from T_MSJW_SYSTEM_DICTIONARY DIC WHERE DIC.TYPE_CODE='dslzdtz' ");
        sql.append(" ) and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(
                sql.toString(), new Object[] { "网上预审", "网上预审" }, null);
       //2.判断是否为预抵、首登全程网办个性化流程需自动跳转至待受理环节
        for (Map<String, Object> map : datas) {
            String busTableName = StringUtil.getString(map.get("BUS_TABLENAME"));
            //国有转移7个事项虚拟主表替换真实表名称
            if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                busTableName = "T_BDCQLC_GYJSJFWZYDJ";
            }
            Map<String, Object> record = this.getByJdbc(busTableName,new String[]{"YW_ID"},
                    new Object[]{map.get("BUS_RECORDID")});
            if(StringUtil.isNotEmpty(record) && "2".equals(StringUtil.getString(record.get("ISQCWB")))
                    && "2".equals(StringUtil.getString(map.get("PTT_SQFS")))){//预抵、首登子项全程网办个性化网上预审自动流转流程
                map.put("ISQCWB", record.get("ISQCWB"));
                autoDatas.add(map);
            }else if("1".equals(StringUtil.getString(map.get("PTT_SQFS")))){//智能审批
                autoDatas.add(map);
            }
        }
        return autoDatas;
    }
    
    /**
     * 描述    待受理自动跳转
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToBdcSl(Map<String, Object> data)throws Exception{
        //1、组装自动流程自动跳转所需的参数
        Map<String, Object> flowVars = new HashMap<String, Object>();
        String defId = (String) data.get("DEF_ID");
        int flowVersion = Integer.parseInt(data.get("DEF_VERSION").toString());
        flowVars.put("EFLOW_EXEID", data.get("EXE_ID"));
        flowVars.put("EFLOW_DEFKEY", data.get("DEF_KEY"));
        flowVars.put("EFLOW_DEFID", data.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION", data.get("DEF_VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", data.get("CUR_STEPNAMES"));
        flowVars.put("EFLOW_CURUSEROPERNODENAME", data.get("CUR_STEPNAMES"));
        flowVars.put("ITEM_CODE", data.get("ITEM_CODE"));
        //flowVars.put("EFLOW_INVOKEBUSSAVE", "1");
        flowVars.put("EFLOW_CURRENTTASK_ID", data.get("TASK_ID"));
        //String ASSIGNER_NAME = "自动跳转";
        flowVars.put("EFLOW_CREATORNAME", data.get("ASSIGNER_NAME"));
        flowVars.put("EFLOW_HANDLE_OPINION", "签章成功待受理自动流转");
        flowVars.put("ISQCWB", data.get("ISQCWB"));//前台申报方式 1:智能审批 0：全程网办  2(预抵、首登子项全程网办个性化流程)
        flowVars.put("SQFS", data.get("SQFS"));  
        flowVars.put("PTT_SQFS", data.get("PTT_SQFS"));//平潭通申请方式 1:智能审批 2：全程网办
        flowVars.put("EFLOW_BUSRECORDID", data.get("BUS_RECORDID")); 
        flowVars.put("EFLOW_BUSTABLENAME", data.get("BUS_TABLENAME"));
        
        //2.判断签章是否在规定时间内完成、未完成进行退回补件
        String signStatus = bdcQlcMaterGenAndSignService.getSignStatusByExeId
                (StringUtil.getString(data.get("EXE_ID")));//签章状态
        //任务创建时间转时间戳
        String signTime = String.valueOf(data.get("CREATE_TIME"));//取待受理流程任务创建时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(signTime);
        Long signTimestamp=date.getTime();
        log.info("流程任务:"+data.get("TASK_ID")+"-待受理流程任务创建时间为："+format.
                format(date));
        //普通件签章有效期为48小时，即办件签章有效期为：17:30前的办件（23-申请时间（取小时））、17:30后的办件在加一个24小时。
        int sxlx = Integer.parseInt(StringUtil.getString(data.get("SXLX")));
        int signAllowTime =0;
        if(sxlx==2){//普通件
            signAllowTime = Integer.valueOf(StringUtil.getString(dictionaryService.
                    get("dslzdtz", "signAllowTime").get("DIC_DESC")));//普通件规定时间(48小时) 
            log.info("流程申报号:"+data.get("EXE_ID")+"-(普通件)签章截止时间为："+format.format(
                    new Date(signTimestamp+signAllowTime*60L*60L*1000)));
        }else if(sxlx==1){//即办件
            Date createTimeDate = DateTimeUtil.getDateOfStr(signTime, "yyyy-MM-dd HH:mm");//办件时间
            String deadLineDate = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd HH:mm");//截止时间
            //当天17:30前的办件，截止时间为当天23:59:59秒
            String createTimeDated = DateTimeUtil.getStrOfDate(createTimeDate, "yyyy-MM-dd");
            deadLineDate = String.format("%s %s", createTimeDated, "23:59:59");
            //即办件晚于下班时间（17:30）收件或在周末收件截至时间为最近一个工作日
            deadLineDate = statisticsService.getJbjDeadLineDate(createTimeDate, deadLineDate, "即办件");
            log.info("流程申报号:"+data.get("EXE_ID")+"-(即办件)签章截止时间为："+deadLineDate);
            long diff;
            //计算两个时间的毫秒时间差
            diff = format.parse(deadLineDate).getTime() - format.parse(signTime).getTime();
            long nh = 1000*60*60;//一小时的毫秒数
            long hour = diff/nh;
            signAllowTime = (int)hour;
            //System.out.println("时间间隔为："+signAllowTime+"-小时");
        }
        signTimestamp += signAllowTime*60L*60L*1000;//(加上规定时间)
        log.info("流程申报号:"+data.get("EXE_ID")+"-签章状态为："+signStatus);
        if("2".equals(signStatus) && (System.currentTimeMillis() <= signTimestamp)){
            String nextStepJson = this.jbpmService.getNextStepsJson(defId, flowVersion, data.get("CUR_STEPNAMES")
                    .toString(), flowVars);
            if (StringUtils.isNotEmpty(nextStepJson)) {
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            }
            if("待受理".equals(data.get("CUR_STEPNAMES").toString())){
                jbpmService.doFlowJob(flowVars);
            }           
        }else if(System.currentTimeMillis() > signTimestamp && !"2".equals(signStatus)){//签章超期未成功退回补件
            /*log.info("流程申报号："+data.get("EXE_ID")+"-开始执行自动退件");
            try {
                commercialService.jumpTaskForZdtj(data);
            } catch (Exception e) {
                log.info("流程申报号："+data.get("EXE_ID")+"-自动退件失败，原因："+e.getMessage());
            } 
            log.info("流程申报号："+data.get("EXE_ID")+"-执行自动退件成功！");*/
            log.info("流程申报号："+data.get("EXE_ID")+"-开始执行退回补件");
            try {
                Map<String, Object> flowInfo = new HashMap<String, Object>();
                flowInfo.put("exeId", data.get("EXE_ID"));
                flowInfo.put("ASSIGNER_CODE", data.get("ASSIGNER_CODE"));
                flowInfo.put("EFLOW_HANDLE_OPINION", "签章超期未通过执行退回补件！");
                flowInfo.put("type", "auto");
                //签署流程文件需作废
                List signList = serviceItemService.getAllByJdbc("T_BDCQLC_MATERSIGNINFO", new String[]{"EXE_ID"},
                        new Object[]{data.get("EXE_ID")});
                if (signList != null && !signList.isEmpty()) {
                    Boolean isCancel = bdcQlcMaterService.cancelSignFlow(StringUtil.getString(data.get("EXE_ID")));
                    isCancel = true;
                    if (isCancel) {
                        //退回补件
                        executionService.exeThBj(flowInfo);
                        log.info("流程申报号："+data.get("EXE_ID")+"-执行退回补件成功！");
                    } else {
                        log.info("流程申报号："+data.get("EXE_ID")+"-执行退回补件失败原因：签章作废失败！");
                    }
                } else {
                    executionService.exeThBj(flowInfo);
                    log.info("流程申报号："+data.get("EXE_ID")+"-执行退回补件成功！");
                }
            } catch (Exception e) {
                log.info("流程申报号："+data.get("EXE_ID")+"-退回补件失败，原因："+e.getMessage());
            } 
        }  
    }
    
    /**
     * 描述    网上预审自动跳转（平潭通智能审批）
     * @author Allin Lin
     * @created 2020年9月10日 上午10:25:20
     * @param data
     */
    public void jumpTaskToBdcDsl(Map<String, Object> data){
        //1、组装自动流程自动跳转所需的参数
        Map<String, Object> flowVars = new HashMap<String, Object>();
        String defId = (String) data.get("DEF_ID");
        int flowVersion = Integer.parseInt(data.get("DEF_VERSION").toString());
        flowVars.put("EFLOW_EXEID", data.get("EXE_ID"));
        flowVars.put("EFLOW_DEFKEY", data.get("DEF_KEY"));
        flowVars.put("EFLOW_DEFID", data.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION", data.get("DEF_VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL", "false");
        flowVars.put("EFLOW_ISTEMPSAVE", "-1");
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES", data.get("CUR_STEPNAMES"));
        flowVars.put("EFLOW_CURUSEROPERNODENAME", data.get("CUR_STEPNAMES"));
        flowVars.put("ITEM_CODE", data.get("ITEM_CODE"));
        //flowVars.put("EFLOW_INVOKEBUSSAVE", "1");
        flowVars.put("EFLOW_CURRENTTASK_ID", data.get("TASK_ID"));
        //String ASSIGNER_NAME = "自动跳转";
        flowVars.put("EFLOW_CREATORNAME", data.get("ASSIGNER_NAME"));
        flowVars.put("EFLOW_HANDLE_OPINION", "发起签章成功网上预审自动流转");
        flowVars.put("PTT_SQFS", data.get("PTT_SQFS"));//平潭通申请方式
        flowVars.put("SQFS", data.get("SQFS"));    
        flowVars.put("EFLOW_BUSRECORDID", data.get("BUS_RECORDID")); 
        flowVars.put("EFLOW_BUSTABLENAME", data.get("BUS_TABLENAME"));
        flowVars.put("ISQCWB", data.get("ISQCWB"));
        Map<String, Object> busRecord = exeDataService.getBuscordMap(StringUtil.getString(data.get("EXE_ID")));
        if(busRecord!=null){
            flowVars.putAll(busRecord);
        }
        //2.执行流程提交方法、若签章触发失败则进行退回
        log.info("流程申报号:"+data.get("EXE_ID")+"-开始触发签章");
        String nextStepJson = this.jbpmService.getNextStepsJson(defId, flowVersion, data.get("CUR_STEPNAMES")
                .toString(), flowVars);
        if (StringUtils.isNotEmpty(nextStepJson)) {
            flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
        }
        if("网上预审".equals(data.get("CUR_STEPNAMES").toString())){
            try {
               flowVars = jbpmService.doFlowJob(flowVars);
               log.info("流程申报号:"+data.get("EXE_ID")+"-触发签章成功");
            } catch (Exception e) {
                if(flowVars.get("SIGN_FLAG")!=null && !(boolean)flowVars.get("SIGN_FLAG")){
                    log.info("流程申报号:"+data.get("EXE_ID")+"-(平潭通智能审批网上预审)自动跳转触发签章失败，执行退回，"
                            + "原因：" + flowVars.get("SIGN_MSG"));
                    //签章失败、执行退回（带上退回意见）
                    flowVars.put("EFLOW_HANDLE_OPINION",flowVars.get("SIGN_MSG"));
                    executionService.goBackFlow(flowVars);
                }
                log.error("流程申报号:"+data.get("EXE_ID")+"-(平潭通智能审批网上预审)自动跳转任务出错：" + e.getMessage(), e);
            }
        }           
    }
    
    /**
     * 
     * 描述    保存不动产智能审批业务的额外需要自动生成的字段
     * @author Allin Lin
     * @created 2020年12月18日 上午11:25:07
     * @param flowVars
     * @return
     */
     public Map<String,Object> saveBdcZnspElseField(Map<String, Object> flowVars){
         HashMap<String, Object> variables = new HashMap<>();
         String EFLOW_EXEID = StringUtil.getValue(flowVars, "EFLOW_EXEID");
         String EFLOW_BUSRECORDID = StringUtil.getValue(flowVars, "EFLOW_BUSRECORDID");
         String EFLOW_BUSTABLENAME = StringUtil.getValue(flowVars, "EFLOW_BUSTABLENAME");
         String ISQCWB = StringUtil.getValue(flowVars, "ISQCWB");//是否全程网办方式（0：全程网办 1：智能审批 2：首登全程网办个性化流程）
         String SQFS = StringUtil.getString(flowVars.get("SQFS"));// 受理方式（1：网上申请 触发签章）
         if (StringUtils.isNotEmpty(EFLOW_EXEID) && "1".equals(SQFS)) {
             Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                     new Object[] { EFLOW_EXEID });
             if (flowExe == null) {
                 flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                         new Object[] {EFLOW_EXEID});
             }
             String pttSqfs = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
            if (("1".equals(ISQCWB)) || (StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs))) {
                 /*填写核准意见*/
                 String username = dictionaryService.findByDicCodeAndTypeCode("dbr","dslzdtz");
                 String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                 //预购商品房预告登记、抵押注销
                 variables.put("HZ_OPINION_CONTENT", "准予登记");//审核意见-核定意见      
                 variables.put("HZ_OPINION_NAME", username);//审核意见-填写人
                 variables.put("HZ_OPINION_TIME", dateStr);//审核意见-填写时间
                 //抵押首次登记转本
                 variables.put("DJSHXX_HZYJ", "经审核，该不动产抵押权首次登记手续符合法律规定，申报材料齐全完整，"
                         + "同意为该宗不动产办理抵押权首次登记");//审核意见-核定意见
                 variables.put("DJSHXX_SHR",username);//审核意见-填写人
                 variables.put("DJSHXX_SHJSRQ", dateStr);//审核意见-填写时间
                 dao.saveOrUpdate(variables, EFLOW_BUSTABLENAME, EFLOW_BUSRECORDID);
             }
         }
         return flowVars;
     }

     /**
      * 描述:保存登簿信息
      *
      * @author Madison You
      * @created 2021/1/11 10:26:00
      * @param
      * @return
      */
     @Override
     public void saveDbInfo(String eveId, String retJson) {
         Map<String, Object> result = JSON.parseObject(retJson, Map.class);
         if (null != result && result.size() > 0) {
             String retcode = StringUtil.getString(result.get("retcode"));
             String dbzt = StringUtil.getString(result.get("dbzt"));
             if (StringUtils.isNotEmpty(retcode) && StringUtils.isNotEmpty(dbzt)
                     && retcode.equals("00") && dbzt.equals("1")) {
                 FlowData flowData = new FlowData(eveId, FlowData.BUS_MAP);
                 Map<String, Object> exeMap = flowData.getExeMap();
                 String busTablename = exeMap.get("BUS_TABLENAME").toString();
                 //国有转移7个事项虚拟主表替换真实表名称
                 if(busTablename.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
                     busTablename = "T_BDCQLC_GYJSJFWZYDJ";
                 }
                 List<Map<String, Object>> qzinfo = (List<Map<String, Object>>) result.get("qzinfo");
                 /*由于登簿保存的信息不同，所以通过表单名称予以区分*/
                 String qlrxxdb = dictionaryService.get("DBPZ", "QLRXXDB").
                         get("DIC_DESC").toString();
                 if (qlrxxdb.contains(busTablename)) {
                     savePowerPeopleDbInfo(qzinfo, flowData);
                 } else {
                     saveYgspfDbInfo(qzinfo, flowData);
                 }
             }
         }
     }

     /**
      * 描述:保存权利人登簿信息
      *
      * @author Madison You
      * @created 2021/1/11 10:53:00
      * @param
      * @return
      */
     private void savePowerPeopleDbInfo(List<Map<String, Object>> qzinfo , FlowData flowData) {
         Map<String,Object> variables = new HashMap<>();
         Map<String, Object> busMap = flowData.getBusMap();
         Map<String, Object> exeMap = flowData.getExeMap();
         String busTablename = StringUtil.getValue(exeMap, "BUS_TABLENAME");
         String busRecordid = StringUtil.getValue(exeMap, "BUS_RECORDID");
         SysUser sysUser = AppUtil.getLoginUser();
         String username = sysUser.getFullname();
         String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
         //国有转移7个事项虚拟主表替换真实表名称
         if(busTablename.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
             busTablename = "T_BDCQLC_GYJSJFWZYDJ";
         }
         if (null != qzinfo && qzinfo.size() > 0) {
             String powJson = StringUtil.getValue(busMap, "POWERPEOPLEINFO_JSON");
             List<Map<String, Object>> powObj = JSON.parseObject(powJson, List.class);
             if (powObj.size() == qzinfo.size()) {
                 int iflag = 0;
                 for (int i = 0; i < qzinfo.size(); i++) {
                     String gdzt = qzinfo.get(i).get("gdzt").toString();
                     if ("1".equals(gdzt)) {
                         for (int j = 0; j < powObj.size(); j++) {
                             if (qzinfo.get(i).get("qlr_mc").equals(powObj.get(j).get("POWERPEOPLENAME"))
                                     && qzinfo.get(i).get("qlr_zh").equals(powObj.get(j).get("POWERPEOPLEIDNUM"))){
                                 powObj.get(j).put("BDC_SZZH", qzinfo.get(i).get("qzzh"));
                                 powObj.get(j).put("BDC_CZR", username);
                                 powObj.get(j).put("BDC_CZSJ", dateStr);
                                 iflag = iflag + 1;
                             }
                         }
                     }
                 }
                 if (iflag == powObj.size()) {
                     variables.put("POWERPEOPLEINFO_JSON", JSON.toJSONString(powObj));
                     variables.put("BDC_DBJG", "登簿成功");
                     variables.put("BDC_DBR", username);
                     variables.put("BDC_DJSJ", dateStr);
                     variables.put("BDC_DBSJ", dateStr);
                     variables.put("BDC_DBZT", "1");
                     dao.saveOrUpdate(variables, busTablename, busRecordid);
                 }
             }
         }
     }

     /**
      * 描述:普通保存登簿信息
      *
      * @author Madison You
      * @created 2021/1/11 11:08:00
      * @param
      * @return
      */
     private void saveYgspfDbInfo(List<Map<String, Object>> qzinfo, FlowData flowData) {
         Map<String,Object> variables = new HashMap<>();
         Map<String, Object> exeMap = flowData.getExeMap();
         String busTablename = StringUtil.getValue(exeMap, "BUS_TABLENAME");
         String busRecordid = StringUtil.getValue(exeMap, "BUS_RECORDID");
         SysUser sysUser = AppUtil.getLoginUser();
         String username = sysUser.getFullname();
         String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
         if (null != qzinfo && qzinfo.size() > 0) {
             String gdzt = qzinfo.get(0).get("gdzt").toString();
             if (StringUtils.isNotEmpty(gdzt) && gdzt.equals("1")) {
                 variables.put("BDC_DBR", username);
                 variables.put("BDC_DJSJ", dateStr);
                 variables.put("BDC_DBZT", gdzt);
                 variables.put("BDC_DJZT", gdzt);
                 variables.put("BDC_DBJG", "登簿成功");
                 variables.put("BDC_BDCDJZMH", qzinfo.get(0).get("qzzh"));
                 //抵押权注销登记
                 variables.put("BDC_ZXR", username);//注销人
                 variables.put("BDC_ZXSJ", dateStr);//注销时间
                 dao.saveOrUpdate(variables, busTablename, busRecordid);
             }
         }
     }
     
     /**
      *     
      * 
      * @author Scolder Lin
      * @param flowDatas
      * @created 2021年7月22日 上午10:26:05
      * @return
      * @throws Exception 
      */
     @SuppressWarnings("unchecked")
     @Override
     public Map<String, Object> saveAfterBusData2(Map<String, Object> flowDatas) throws Exception {
         String isTempSave = (String) flowDatas.get("EFLOW_ISTEMPSAVE");
         String FW_FJ = (String) flowDatas.get("busRecord[FW_FJ]");
         String ESTATE_NUM = (String) flowDatas.get("busRecord[ESTATE_NUM]");
         
         if("-1".equals(isTempSave)) {
             //判断类型是否为国有建设用地使用权及房屋所有权转移登记（分户）
             if(FW_FJ.indexOf("国有建设用地使用权及房屋所有权转移登记（分户）") != -1) {
                 if(FW_FJ.indexOf("抵押联办") == -1 && ESTATE_NUM != null) {
                     Map<String, Object> map = new HashMap<String, Object>();
                     //不动产单元号，8个参数任意赋值一个不为空就行
                     map.put("bdcdyh", ESTATE_NUM);
                     AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(map, "announceUrl");
                     String listJson=ajaxJson.getJsonString();
                     List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) JSON.parse(listJson);
                     String QLRZJH  = "";
                     String BDCDJZMH  = "";
                     log.info("查询诚信所不动产预告档案查询接口服务 list:" + list);
                     for (Map<String, Object> queryData : list) {
                         if(queryData.get("YGDJZL") != null && queryData.get("QSZT") != null){
                             if("预售商品房抵押权预告登记".equals(queryData.get("YGDJZL").toString()) && "现势".equals(queryData.get("QSZT").toString())){
                                 QLRZJH = queryData.get("QLRZJH").toString();
                                 BDCDJZMH = queryData.get("BDCDJZMH").toString();
                             }
                         }
                     }
                     if(StringUtils.isNotEmpty(BDCDJZMH) && BDCDJZMH != ""){
                         List<HashMap<String, Object>> querylist = bdcDyrlxrService.getAllByJdbc("T_BDC_DYRLXB", new String[]{"TYSHXYDM"}, new Object[]{QLRZJH});
                         for (Map<String, Object> item : querylist) {
                             Map<String, Object> resultMap;
                             Properties properties = FileUtil.readProperties("conf/messageConfig.properties");
                             String templetId = properties.getProperty("messageTemplate1");
                             resultMap = SmsSendUtil.sendSms(BDCDJZMH,item.get("LXR_PHONE").toString(), templetId);
                             //进行短信下发记录表录入
                             Map<String, Object> msgRecord = new HashMap<String, Object>();
                             msgRecord.put("PHONE", item.get("LXR_PHONE").toString());
                             if (resultMap != null) {
                                 String resultcode = resultMap.get("resultcode").toString();
                                 StringBuffer logInfo = new StringBuffer();
                                 if (Objects.equals(resultcode, "0")) {
                                     msgRecord.put("MSG","您好，你行"+BDCDJZMH+"不动产登记证明所涉不动产单元已在我中心办理完新建商品房转移登记业务，请及时申请相关抵押登记业务。不动产单元号为:"+ ESTATE_NUM);
                                     log.info(logInfo.append("统一社会信用代码为" +QLRZJH).append("发送短信提醒成功,手机号码为：").append(item.get("LXR_PHONE")));
                                 } else {
                                     msgRecord.put("MSG",""+BDCDJZMH+"短信发送失败。不动产单元号为:"+ ESTATE_NUM);
                                     log.info(logInfo.append("统一社会信用代码为" +QLRZJH).append("发送短信提醒失败,手机号码为：").append(item.get("LXR_PHONE")));
                                 }
                                 msgRecord.put("MSG_STATUS", resultcode);
                                 msgRecord.put("RETURN_MSG", JSON.toJSONString(resultMap));
                             }else{
                                 msgRecord.put("MSG_STATUS", "-1");
                                 msgRecord.put("RETURN_MSG", JSON.toJSONString("null"));
                             }
                             bdcQueryService.saveOrUpdate(msgRecord, "T_BDC_DYRLXB_MSGRECORD", null);
                         }
                     }
                 }
             }
         }
         return flowDatas;
     }

}
