/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.core.util.WordToPdfUtil;
import net.evecom.platform.bsfw.model.PtspFile;
import net.evecom.platform.bsfw.service.BjxxService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowHangInfoService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.project.dao.ProjectXFDao;
import net.evecom.platform.project.service.ProjectApplyService;
import net.evecom.platform.project.service.ProjectXFService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 
 * 描述：工程建设项目消防事项数据共享定时器
 * 
 * @author Scolder Lin
 * @created 2020年1月8日 下午5:30:39
 */

@SuppressWarnings("rawtypes")
@Service("projectXFService")
public class ProjectXFServiceImpl extends BaseServiceImpl implements ProjectXFService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ProjectApplyServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectXFDao dao;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * flowTaskService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * 引入Service
     */
    @Resource
    private BjxxService bjxxService;
    
    /**
     * 引入Service
     */
    @Resource
    private ProjectApplyService projectApplyService;
    /**
     * 引入Service
     */
    @Resource
    private FlowHangInfoService flowHangInfoService;
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    public Map<String, Object> saveXFBaseInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFBaseList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCBaseInfo", "BASE_PUSH_STATUS", "TB_FC_PROJECT_INFO",
                        "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFUnitInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFUnitList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCUnitInfo", "UNIT_PUSH_STATUS", "TB_FC_PROJECT_INFO",
                        "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFCorpInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFCorpList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCCorpInfo", "CORP_PUSH_STATUS", "TB_FC_PROJECT_INFO",
                        "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFStorageInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFStorageList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCStorageInfo", "STORAGE_PUSH_STATUS",
                        "TB_FC_PROJECT_INFO", "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFYardInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFYardList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCYardInfo", "YARD_PUSH_STATUS", "TB_FC_PROJECT_INFO",
                        "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFInsuInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFInsuList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCInsuInfo", "INSU_PUSH_STATUS", "TB_FC_PROJECT_INFO",
                        "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveXFDecorateInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFDecorateList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFCDecorateInfo", "DECORATE_PUSH_STATUS",
                        "TB_FC_PROJECT_INFO", "FC_PROJECT_INFO_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public Map<String, Object> saveFinishManageInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findFinishManageList(0);
            if (list != null && list.size() > 0) {
                result = dataTransferAndUpdate(result, list, "saveFinishManageInfo", "PUSH_STATUS",
                        "TB_PROJECT_FINISH_MANAGE", "YW_ID");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    /**
     * 描述:保存消防验收（备案）申请信息
     *
     * @author Madison You
     * @created 2020/1/16 10:40:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> saveXFYsbaInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFYsbaList(0);
            if (list != null && !list.isEmpty()) {
                result = dataTransferAndUpdate(result, list, "saveFCYsbaInfo", "XFYSBA_STATUS", "T_BSFW_GCJSXFYS",
                        "YW_ID");
            }
        } catch (Exception e) {
            log.info("推送消防验收（备案）申请信息出错" + e.getMessage());
        }
        return result;
    }

    /**
     * 描述:保存消防验收情况信息
     *
     * @author Madison You
     * @created 2020/1/16 11:17:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> saveXFYsqkInfo() {
        Map<String, Object> result = null;
        try {
            List<Map<String, Object>> list = dao.findXFYsqkList(0);
            if (list != null && !list.isEmpty()) {
                result = dataTransferAndUpdate(result, list, "saveFCYsqkInfo", "XFYSQK_STATUS", "T_BSFW_GCJSXFYS",
                        "YW_ID");
            }
        } catch (Exception e) {
            log.info("推送消防验收情况信息出错" + e.getMessage());
        }
        return result;
    }

    /**
     * 工程建设项目数据传到前置库以及更新本库表字段
     * 
     * @param result      传输后的返回结果
     * @param list        准备传输的数据集
     * @param serviceCode 服务码
     * @param updateField 数据传输状态
     * @param tableName   表名
     * @param pkName      主键名称
     * 
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> dataTransferAndUpdate(Map<String, Object> result, List<Map<String, Object>> list,
            String serviceCode, String updateField, String tableName, String pkName) {
        Map<String, Object> params = new HashMap<String, Object>();
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        params.put("servicecode", serviceCode);
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        params.put("grantcode", gcjsxmGrantCode);
        params.put("infoJson", JSON.toJSONString(list));
        result = TokenUtil.doPost(devbaseUrl, params);
        log.info("saveTBFCProjectInfo返回数据：" + result);
        if (result.get("invokeResultCode") != null) {
            String invokeResultCode = result.get("invokeResultCode").toString();
            if ("000".equals(invokeResultCode)) {// 数据传送前置库成功
                for (Map<String, Object> map : list) {
                    String ywId = map.get(pkName).toString();
                    Map<String, Object> fcPrjMap = dao.getByJdbc(tableName, new String[] { pkName },
                            new Object[] { ywId });
                    fcPrjMap.put(updateField, "1");
                    dao.saveOrUpdate(fcPrjMap, tableName, ywId);
                }
            }
        }
        return result;
    }

    /**
     *
     * 描述 是否受理通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsAcceptPass(Map<String, Object> flowVars) {
        String GSSFTG = (String) flowVars.get("SLSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (GSSFTG.equals("-1")) {
            resultNodes.add("开始");
        } else {
            // 获取
            //String SYDYXZ = (String) flowVars.get("SYDYXZ");
            //String CHECKNUM = (String) flowVars.get("CHECKNUM");
            String EFLOW_BUSRECORDID =  (String) flowVars.get("EFLOW_BUSRECORDID");
            Map<String, Object> xfys = new HashMap<String, Object>();
            //boolean flag = isCheck(SYDYXZ, CHECKNUM);
            boolean flag = false;
            if(flag) {
                xfys.put("ISCHECK", "1");       
            }else {
                xfys.put("ISCHECK", "0");      
            }
            dao.saveOrUpdate(xfys, "T_BSFW_GCJSXFYS", EFLOW_BUSRECORDID);
            resultNodes.add("初审");
        }
        return resultNodes;
    }

    /**
     *
     * 描述 受理环节后置事件（工程-建设工程消防验收及备案抽查流程）
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getAcceptResult(Map<String, Object> flowVars) {     
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        //申报类型
        String declarationType  = (String) flowVars.get("DECLARATIONTYPE") == null ? ""
                : flowVars.get("DECLARATIONTYPE").toString();
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            String dicName = "";
            if(declarationType.equals("001")&& declarationType != null) {
                dicName = "特殊建设工程消防验收申请不予受理凭证";
            }else {
                dicName = "建设工程消防验收备案不予受理凭证";
            }
            if (isBack.equals("true") && isBack != null) {// 退回
                getDocument(busRecord, busTableName, busRecordId, dicName);
            } else {
                String SLSFTG = (String) flowVars.get("SLSFTG");
                if (SLSFTG.equals("-1")) {
                    getDocument(busRecord, busTableName, busRecordId, dicName);
                }
            }
        }

        return flowVars;
    }

    /**
     *
     * 描述 审批是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsRandomCheck(Map<String, Object> flowVars) {
        // 获取
        String ISCHECK = (String) flowVars.get("ISCHECK");
        Set<String> resultNodes = new HashSet<String>();
        if (ISCHECK.equals("1")) {
            resultNodes.add("结论登记");
        } else {
            resultNodes.add("结束");
        }
        return resultNodes;
    }
    
    /**
     * 
     * @Description 抽奖方法
     * @author Luffy Cai
     * @date 2021年3月26日
     * @param SYDYXZ
     * @param CHECKNUM
     * @return boolean
     */
    public boolean isCheck(String SYDYXZ,String CHECKNUM) {
        boolean flag = false;
        int num = 0;
        Integer checkNum = Integer.valueOf(CHECKNUM);
        if(SYDYXZ.equals("1")) {//人员密集场所，抽取比例为50%；
            num = 50;
        }else if(SYDYXZ.equals("2")) {//火灾危险性为丙、丁、戊类的工业建筑物或者构筑物，抽取比例为30%；
            num = 30;
        }else{//其他项目，抽取比例为10%。
            num = 15;
        }
        HashSet<Integer> has = new HashSet<Integer>();
        Integer i = 0;
        for (int j = 1; j != 0; j++) {
            i = (int) (Math.random() * 100) + 1;
            has.add(i);
            if (num == has.size()) {
                break;
            }
        }
        for (Integer n : has) {
            if (n == checkNum) {
                flag = true;
                break;
            }
        }
        return flag;
    }   

    /**
     *
     * 描述 审批是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getApprovalResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        //申报类型
        String declarationType  = (String) flowVars.get("busRecord[DECLARATIONTYPE]") == null ? ""
                : flowVars.get("busRecord[DECLARATIONTYPE]").toString();        
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            String dicName ="";
            if (isBack.equals("true") && !isBack.isEmpty()) {// 环节退回 不做操作
            } else {
                String SPSFTG = (String) flowVars.get("SPSFTG");
                if (SPSFTG.equals("1")) {
                    if(declarationType.equals("001")&&!declarationType.isEmpty()) {
                        dicName = "特殊建设工程消防验收申请受理凭证";
                    }else {
                        dicName = "建设工程消防验收备案凭证";
                    }
                    getDocument(busRecord, busTableName, busRecordId, dicName);
                } else {
                    if(declarationType.equals("001")&&!declarationType.isEmpty()) {
                        dicName = "特殊建设工程消防验收申请不予受理凭证";
                    }else {
                        dicName = "建设工程消防验收备案不予受理凭证";
                    }
                    getDocument(busRecord, busTableName, busRecordId, dicName);
                }
            }
        }

        return flowVars;
    }

    /**
     *
     * 描述 结论登记是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsConcludePass(Map<String, Object> flowVars) {
        String JLDJSFTG = (String) flowVars.get("JLDJSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (JLDJSFTG.equals("-1")) {
            resultNodes.add("退回业主申请");
        } else {
            resultNodes.add("结论初审");
        }
        return resultNodes;
    }

    /**
    *
    * 描述 结论登记是否通过
    * 
    * @author Luffy Cai
    * @created 2020-5-26 下午5:16:49
    * @param flowVars
    * @return
     * @throws Exception 
    */
   public Map<String, Object> getIsBack(Map<String, Object> flowVars){
       String JLDJSFTG = (String) flowVars.get("JLDJSFTG") ==null? "" : flowVars.get("JLDJSFTG").toString();
       String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
       String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
               : flowVars.get("EFLOW_ISTEMPSAVE").toString();       
       if (isTempSave.equals("-1")) {
           if (isBack.equals("true") && !isBack.isEmpty()) {// 环节退回 不做操作
           } else {
               if (JLDJSFTG.equals("-1")) {
                   doBjFlowJob(flowVars);
               }     
           }           
       }
       return flowVars;
   }    

   /**
   *
   * 描述 结论登记是否通过
   * 
   * @author Luffy Cai
   * @created 2020-5-26 下午5:16:49
   * @param flowVars
   * @return
    * @throws Exception 
   */
  public Map<String, Object> getIsReturn(Map<String, Object> flowVars){
      String FCSFTG = (String) flowVars.get("FCSFTG") ==null? "" : flowVars.get("FCSFTG").toString();
      String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
      String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
              : flowVars.get("EFLOW_ISTEMPSAVE").toString();       
      if (isTempSave.equals("-1")) {
          if (isBack.equals("true") && !isBack.isEmpty()) {// 环节退回 不做操作
          } else {
              if (FCSFTG.equals("-1")) {
                  doBjFlowJob(flowVars);
              }     
          }           
      }
      return flowVars;
  }    
   
    /**
     *
     * 描述 结论复查是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsRecheckPass(Map<String, Object> flowVars) {
        String FCSFTG = (String) flowVars.get("FCSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (FCSFTG.equals("-1")) {
            resultNodes.add("退回业主申请");
        } else {
            resultNodes.add("结论初审");
        }
        return resultNodes;
    }

    /**
     *
     * 描述 结论审批是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsConclusionPass(Map<String, Object> flowVars) {
        String JLSPSFTG = (String) flowVars.get("JLSPSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (JLSPSFTG.equals("-1")) {
            resultNodes.add("结论登记");
        } else {
            resultNodes.add("监督报告登记");
        }
        return resultNodes;
    }

    /**
     *
     * 描述 结论审批是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getConclusionResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            if (isBack.equals("true") && !isBack.isEmpty()) {// 审批环节退回 不做操作

            } else {
                String JLSPSFTG = (String) flowVars.get("JLSPSFTG");
                if (JLSPSFTG.equals("-1")) {
                    String dicName = "建设工程消防验收备案抽（复）查通知书";
                    getDocument(busRecord, busTableName, busRecordId, dicName);
                }
            }
        }

        return flowVars;
    }

    /**
     *
     * 描述 监督报告初审是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsFirstTrialPass(Map<String, Object> flowVars) {
        String JDBGCSSFTG = (String) flowVars.get("JDBGCSSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (JDBGCSSFTG.equals("-1")) {
            resultNodes.add("监督报告登记");
        } else {
            resultNodes.add("监督报告审批");
        }
        return resultNodes;
    }

    /**
     *
     * 描述 监督报告审批是否通过
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Set<String> getIsReportPass(Map<String, Object> flowVars) {
        String JDBGSPSFTG = (String) flowVars.get("JDBGSPSFTG");
        Set<String> resultNodes = new HashSet<String>();
        if (JDBGSPSFTG.equals("-1")) {
            resultNodes.add("监督报告登记");
        } else {
            resultNodes.add("结束");
        }
        return resultNodes;
    }

    /**
     *
     * 描述 监督报告审批后置事件
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getReportResult(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? "" : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            if (isBack.equals("true") && !isBack.isEmpty()) {//环节退回不做操作

            } else {
                String JDBGSPSFTG = (String) flowVars.get("JDBGSPSFTG");
                if (JDBGSPSFTG.equals("1")) {
                    // 监督报告审批通过，生成《消防验收监督报告》;
                    String dicName = "特殊建设工程消防验收意见书";
                    getDocument(busRecord, busTableName, busRecordId, dicName);                   
                }
            }
        }
        return flowVars;
    }

    /**
     *
     * 描述 监督报告审批后置事件
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getAcceptPass(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        if (isTempSave.equals("-1")) {
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            // 使用性质
            String FC_CHARACTER = (String) busRecord.get("FC_CHARACTER");
            FC_CHARACTER = dictionaryService.getDicNames("CHARACTER", FC_CHARACTER);
            busRecord.put("FC_CHARACTER", FC_CHARACTER);
            if (isBack.equals("true") && !isBack.isEmpty()) {// 受理退回，生成不予受理文书
                String dicName = "特殊建设工程消防设计审查申请不予受理凭证";
                getDocument(busRecord, busTableName, busRecordId, dicName);
            } else {
                String dicName = "特殊建设工程消防设计审查申请受理凭证";
                getDocument(busRecord, busTableName, busRecordId, dicName);
            }
        }

        return flowVars;
    }

    /**
     *
     * 描述 监督报告审批后置事件
     * 
     * @author Luffy Cai
     * @created 2020-5-26 下午5:16:49
     * @param flowVars
     * @return
     */
    public Map<String, Object> getOfficeAction(Map<String, Object> flowVars) {
        String isBack = (String) flowVars.get("EFLOW_ISBACK") == null ? "" : flowVars.get("EFLOW_ISBACK").toString();
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE") == null ? ""
                : flowVars.get("EFLOW_ISTEMPSAVE").toString();
        if (isTempSave.equals("-1")) {
            String isBackPatch = (String) flowVars.get("EFLOW_ISBACK_PATCH") == null ? ""
                    : flowVars.get("EFLOW_ISBACK_PATCH").toString();
            String exeId = (String) flowVars.get("busRecord[EXE_ID]");
            Map<String, Object> execution = dao.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            // 获取业务表名称
            String busTableName = (String) execution.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String busRecordId = (String) execution.get("BUS_RECORDID");
            Map<String, Object> busRecord = getBuscordByIdAndTableName(busRecordId, busTableName);
            // 使用性质
            String FC_CHARACTER = (String) busRecord.get("FC_CHARACTER");
            FC_CHARACTER = dictionaryService.getDicNames("CHARACTER", FC_CHARACTER);
            busRecord.put("FC_CHARACTER", FC_CHARACTER);
            busRecord.put("EXE_ID", exeId);
            if (isBackPatch.equals("true") && !isBackPatch.isEmpty() && isBack.equals("true")) {
                String dicName = "特殊建设工程消防设计审查不合格意见书";
                getDocument(busRecord, busTableName, busRecordId, dicName);
            } else {// 审核通过，系统自动生成审核意见书
                String dicName = "特殊建设工程消防设计审查合格意见书";
                getDocument(busRecord, busTableName, busRecordId, dicName);
            }
        }
        return flowVars;
    }


    /**
     * 生成附件
     * 
     * @param busRecord
     * @param resultId
     */
    @SuppressWarnings("unchecked")
    private void getDocument(Map<String, Object> busRecord, String busTableName, String busRecordId, String dicName) {
        String APPLY_DATE = (String)busRecord.get("APPLY_DATE");
        String[] strArr = APPLY_DATE.split("\\-");
        for (int j = 0; j< strArr.length; j++) {
            if(j==0) {
                busRecord.put("YEAR", strArr[j]);
            }else if(j==1) {
                busRecord.put("MOUTH", strArr[j]);
            }else if(j==2) {
                busRecord.put("DAY", strArr[j]);
            }
        }
        Properties properties = FileUtil.readProperties("project.properties");
        String attachmentFilePath = properties.getProperty("uploadFileUrl").replace("\\", "/");
        // 模板路径
        String templatePath = dictionaryService.getDicCode("templatePath", dicName);
        String filefullPath = attachmentFilePath + templatePath;
        
        // 定义相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = "attachFiles/scws/" + currentDate;
        //String uuId = UUIDGenerator.getUUID();
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        // 建立全路径目录和临时目录
        File docFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!docFullPathFolder.exists()) {
            docFullPathFolder.mkdirs();
        }
        String newWordFilePath = "";
        newWordFilePath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".doc";
        // 替换模版字段,生成word
        WordReplaceUtil.replaceWord(busRecord, filefullPath, newWordFilePath);
        File file = new File(newWordFilePath);
        String url = attachmentFilePath + "upload/file";
        // 将文件上传至文件服务器
        PtspFile fileInfo = uploadAndGetFileInfo(url, dicName, file, busTableName);
        // 插入系统附件表
        Map<String, Object> fileAttach = new HashMap<>();
        fileAttach.put("FILE_NAME", fileInfo.getFileName());
        fileAttach.put("FILE_PATH", fileInfo.getFilePath());
        fileAttach.put("FILE_TYPE", fileInfo.getFileType());
        fileAttach.put("BUS_TABLENAME", busTableName + "_SCWS");
        fileAttach.put("BUS_TABLERECORDID", busRecordId);
        fileAttach.put("ATTACH_KEY", "SCWS");
        fileAttach.put("PLAT_TYPE", 1);
        dao.saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH", null);
    }

    /**
     * 根据主键id和tableName获取业务表数据
     * 
     * @param busRecordId
     * @param tableName
     * @return
     */
    public Map<String, Object> getBuscordByIdAndTableName(String busRecordId, String busTableName) {
        String busPkName = (String) executionService.getPrimaryKeyName(busTableName).get(0);// 获取业务表主键名称
        // 获取业务记录
        Map<String, Object> busRecord = executionService.getByJdbc(busTableName, new String[] { busPkName },
                new Object[] { busRecordId });
        return busRecord;
    }

    /**
     * 上传文件服务器并获取Ptspfile对象
     * 
     * @param fileName
     * @param file
     */
    public PtspFile uploadAndGetFileInfo(String url, String fileName, File file, String busTableName) {
        // 文件服务器上传
        String app_id = "0001";// 分配的用户名
        String password = "bingo666";// 分配的密码
        String responesCode = "UTF-8";// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "402881e65127d9ea015127d9ea830000");// 上传人ID
        param.put("uploaderName", "系统自动上传"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        param.put("name", fileName + ".doc");// 上传附件名
        param.put("busTableName", busTableName);// 业务表名 param.put("busRecordId",busRecordId);// 业务表ID
        String respResult = HttpRequestUtil.sendFilePost(url, file, responesCode, app_id, password, param);
        log.info("respResult=" + respResult);
        Map<String, Object> respMap = (Map) JSON.parse(respResult);
        boolean flag = Boolean.parseBoolean(StringUtil.getString(respMap.get("success")));
        PtspFile fileInfo = new PtspFile();
        if (flag == true) {
            String data = StringUtil.getString(respMap.get("data"));
            fileInfo = JSONObject.parseObject(data, PtspFile.class);
        }
        return fileInfo;
    }
 
    /**


    /**
     * 
     * 描述 退回补件
     * @author Faker Li
     * @created 2015年11月30日 下午4:28:07
     * @param variables
     * @return
     * @throws Exception
     * @see net.evecom.platform.hflow.service.JbpmService#doBjFlowJob(java.util.Map)
     */
    public void doBjFlowJob(Map<String, Object> flowVars) {
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        //执行退回挂起代码 EFLOW_NEWTASK_PARENTIDS
        Map<String, Object> flowHangInfo = new HashMap<String, Object>();
        flowHangInfo.put("TASK_ID",(String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
        String beginTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        flowHangInfo.put("BEGIN_TIME", beginTime);
        //flowTaskService.handUpFlowTask((String) flowVars.get("EFLOW_EXEID"),(String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
//        flowTaskService.returnFlowTask((String) flowVars.get("EFLOW_EXEID"),
//                (String)flowVars.get("EFLOW_NEWTASK_PARENTIDS"));
        String EFLOW_ISBACK_PATCH = (String) flowVars.get("EFLOW_ISBACK_PATCH");
        flowHangInfo.put("EFLOW_ISBACK_PATCH", EFLOW_ISBACK_PATCH);
        String recordId = flowHangInfoService.saveOrUpdate(flowHangInfo, "JBPM6_HANGINFO", "");
        //保存计时暂停指令数据
        this.saveHandUpDataRes(recordId, exeId);
    }    
    /**
     * 
     * 保存计时暂停指令数据
     * @author Danto Huang
     * @created 2019年5月10日 上午11:19:49
     * @param hangInfoId
     * @param exeId
     */
    private void saveHandUpDataRes(String hangInfoId,String exeId){
        Map<String, Object> handUpDataRes = new HashMap<String, Object>();
        handUpDataRes.put("EXE_ID", exeId);
        handUpDataRes.put("DATA_TYPE", "21");
        handUpDataRes.put("OPER_TYPE", "I");
        handUpDataRes.put("HAS_ATTR", 0);
        handUpDataRes.put("INTER_TYPE", "10");
        handUpDataRes.put("TASK_ID", hangInfoId);
        handUpDataRes.put("OTHER_STATUS", 1);
        flowTaskService.saveOrUpdate(handUpDataRes, "T_BSFW_SWBDATA_RES", null);
    }   
    
}
