/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.SwbDataResDao;
import net.evecom.platform.bsfw.service.SwbDataAttrService;
import net.evecom.platform.bsfw.service.SwbDataResService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowMappedService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2015年12月28日 下午4:22:22
 */
@Service("swbDataResService")
public class SwbDataResServiceImpl extends BaseServiceImpl implements SwbDataResService {

    /**
     * 所引入的dao
     */
    @Resource
    private SwbDataResDao      dao;
    /**
     * 
     */
    @Resource
    private ExecutionService   executionService;
    /**
     * 
     */
    @Resource
    private ServiceItemService   serviceItemService;
    /**
     * 
     */
    @Resource
    private FileAttachService  fileAttachService;
    /**
     * 
     */
    @Resource
    private SwbDataAttrService swbDataAttrService;
    /**
     * 
     */
    @Resource
    private FlowMappedService  flowMappedService;
    /**
     * 
     */
    @Resource
    private DictionaryService  dictionaryService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 保存办件信息的指令
     * 
     * @author Flex Hu
     * @created 2015年12月28日 下午5:01:54
     * @param flowVars
     * @param flowExe
     * @return
     */
    private void saveBjxxDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe) {
        // 获取流程实例ID
        String exeId = (String) flowExe.get("EXE_ID");
        boolean isExists = dao.isHaveSaveBjxxInfo(exeId);
        if (!isExists) {
            // 开始保存办件信息
            Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
            bjxxDataRes.put("EXE_ID", exeId);
            bjxxDataRes.put("DATA_TYPE", "10");
            bjxxDataRes.put("OPER_TYPE", "I");
            // 定义是否有附件
            int HAS_ATTR = 0;
            // 获取业务表名称
            String BUS_TABLENAME = (String) flowExe.get("BUS_TABLENAME");
            // 获取业务表记录ID
            String BUS_RECORDID = (String) flowExe.get("BUS_RECORDID");
            // 获取附件列表
            List<Map<String, Object>> fileList = fileAttachService.findList(BUS_TABLENAME, BUS_RECORDID);
            if (fileList != null && fileList.size() > 0) {
                HAS_ATTR = 1;
            }
            bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
            bjxxDataRes.put("INTER_TYPE", "10");
            String resId = dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
            // 获取事项编码
            String itemCode = (String) flowExe.get("ITEM_CODE");
            if (fileList != null && fileList.size() > 0) {
                swbDataAttrService.saveSwbDataAttr(resId, fileList, itemCode);
            }
        }
    }

    /**
     * 
     * 描述 保存结果信息
     * 
     * @author Flex Hu
     * @created 2015年12月29日 上午10:46:53
     * @param flowVars
     * @param flowExe
     */
    private void saveJgxxDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe, String currentTaskId) {
        // 获取流程实例ID
        String exeId = (String) flowExe.get("EXE_ID");
        // 开始保存办件信息
        Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
        bjxxDataRes.put("EXE_ID", exeId);
        bjxxDataRes.put("DATA_TYPE", "30");
        bjxxDataRes.put("OPER_TYPE", "I"); 
        // 定义是否有附件
        int HAS_ATTR = 0;
        // 获取办结结果附件列表
        List<Map<String, Object>> fileList = fileAttachService.findListByResultExeId(exeId);
        if (fileList != null && fileList.size() > 0) {
            HAS_ATTR = 1;
          }
        bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
        bjxxDataRes.put("INTER_TYPE", "10");
        bjxxDataRes.put("TASK_ID", currentTaskId);
        String resId = dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
        // 获取事项编码
        String itemCode = (String) flowExe.get("ITEM_CODE");
        if (fileList != null && fileList.size() > 0) {
            swbDataAttrService.saveSwbResultDataAttr(resId, fileList, itemCode);
        }
    }


    /**
     * 
     * 描述 保存结果信息（电子证照、批文）
     * 
     * @author Flex Hu
     * @created 2015年12月29日 上午10:46:53
     * @param flowVars
     * @param flowExe
     */
    private void saveJgxxLicDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe, String currentTaskId) {
        // 获取流程实例ID
        String exeId = (String) flowExe.get("EXE_ID");
        // 开始保存办件信息
        Map<String, Object> bjxxDataRes = new HashMap<String, Object>();
        bjxxDataRes.put("EXE_ID", exeId);
        bjxxDataRes.put("DATA_TYPE", "31");
        bjxxDataRes.put("OPER_TYPE", "I");
        // 定义是否有附件
        int HAS_ATTR = 0;
        // 获取办结结果附件列表
        List<Map<String, Object>> fileList = fileAttachService.findListByResultExeId(exeId);
        if (fileList != null && fileList.size() > 0) {
            HAS_ATTR = 1;
          }
        bjxxDataRes.put("HAS_ATTR", HAS_ATTR);
        bjxxDataRes.put("INTER_TYPE", "10");
        bjxxDataRes.put("TASK_ID", currentTaskId);        
        String resId =  dao.saveOrUpdate(bjxxDataRes, "T_BSFW_SWBDATA_RES", null);
        // 获取事项编码
        String itemCode = (String) flowExe.get("ITEM_CODE");
        if (fileList != null && fileList.size() > 0) {
            swbDataAttrService.saveSwbResultDataAttr(resId, fileList, itemCode);
    }
    }
    /**
     * 
     * 描述 保存结果信息
     * 
     * @author Flex Hu
     * @created 2015年12月29日 上午10:46:53
     * @param flowVars
     * @param flowExe
     */
    private void saveXzxkDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe, String currentTaskId) {
        // 获取流程实例ID
        String exeId = (String) flowExe.get("EXE_ID");
        // 开始保存办件信息
        Map<String, Object> xzxkDataRes = new HashMap<String, Object>();
        xzxkDataRes.put("EXE_ID", exeId);
        xzxkDataRes.put("DATA_TYPE", "40");
        xzxkDataRes.put("OPER_TYPE", "I");
      
        // 定义是否有附件
        int HAS_ATTR = 0;
        // 获取办结结果附件列表
        List<Map<String, Object>> fileList = fileAttachService.findListByResultExeId(exeId);
        if (fileList != null && fileList.size() > 0) {
            HAS_ATTR = 1;
          }
          xzxkDataRes.put("HAS_ATTR", HAS_ATTR);
          xzxkDataRes.put("INTER_TYPE", "10");
          xzxkDataRes.put("TASK_ID", currentTaskId);
        String resId=dao.saveOrUpdate(xzxkDataRes, "T_BSFW_SWBDATA_RES", null);
        // 获取事项编码
        String itemCode = (String) flowExe.get("ITEM_CODE");
        if (fileList != null && fileList.size() > 0) {
            swbDataAttrService.saveSwbResultDataAttr(resId, fileList, itemCode);
    }
    }

    /**
     * 
     * 描述 保存结果信息
     * 
     * @author Flex Hu
     * @created 2015年12月29日 上午10:46:53
     * @param flowVars
     * @param flowExe
     */
    private void saveXzxkGgxyDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe, String currentTaskId) {
        // 获取流程实例ID
        String exeId = (String) flowExe.get("EXE_ID");
        // 开始保存办件信息
        Map<String, Object> xzxkDataRes = new HashMap<String, Object>();
        xzxkDataRes.put("EXE_ID", exeId);
        xzxkDataRes.put("DATA_TYPE", "41");
        xzxkDataRes.put("OPER_TYPE", "I");
        // 定义是否有附件
       int HAS_ATTR = 0;
       String BUS_TABLENAME = (String) flowExe.get("BUS_TABLENAME");
       // 获取业务表记录ID
       String BUS_RECORDID = (String) flowExe.get("BUS_RECORDID");
       // 获取附件列表
       List<Map<String, Object>> fileList = fileAttachService.findList(BUS_TABLENAME, BUS_RECORDID);
       if (fileList != null && fileList.size() > 0) {
           HAS_ATTR = 1;
         }
        xzxkDataRes.put("HAS_ATTR", HAS_ATTR);
        xzxkDataRes.put("INTER_TYPE", "10");
        xzxkDataRes.put("TASK_ID", currentTaskId);
      String resId=  dao.saveOrUpdate(xzxkDataRes, "T_BSFW_SWBDATA_RES", null);
      // 获取事项编码
      String itemCode = (String) flowExe.get("ITEM_CODE");
      if (fileList != null && fileList.size() > 0) {
          swbDataAttrService.saveSwbDataAttr(resId, fileList, itemCode);
  }
    }

    /**
     * 描述:保存评价信息
     *
     * @author Madison You
     * @created 2019/10/23 11:35:00
     * @param
     * @return
     */
    private void savePjxxDataRes(Map<String, Object> flowVars, Map<String, Object> flowExe, String currentTaskId) {
        String exeId = (String) flowExe.get("EXE_ID");
        String itemCode = (String) flowExe.get("ITEM_CODE");
        Map<String, Object> pjxxDataRes = new HashMap<>();
        pjxxDataRes.put("EXE_ID", exeId);
        pjxxDataRes.put("DATA_TYPE", "36");
        pjxxDataRes.put("OPER_TYPE", "I");
        // 定义是否有附件
        int HAS_ATTR = 0;
        pjxxDataRes.put("HAS_ATTR", HAS_ATTR);
        pjxxDataRes.put("INTER_TYPE", "10");
        pjxxDataRes.put("TASK_ID", currentTaskId);
        /*没有省网编码的不做保存,没有评价的不作保存*/
        if (itemCode != null) {
            Map<String, Object> itemMap = dao.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"}, new Object[]{itemCode});
            if (itemMap != null && itemMap.get("SWB_ITEM_CODE") != null) {
                Map<String,Object> callMap = dao.getByJdbc("T_CKBS_QUEUERECORD",
                        new String[]{"EXE_ID"}, new Object[]{exeId});
                if (callMap != null && callMap.get("EVALUATE") != null) {
                    dao.saveOrUpdate(pjxxDataRes, "T_BSFW_SWBDATA_RES", null);
                }
            }
        }
    }
    /**
     * 
     * 描述 保存通知缴费信息指令
     * @author Kester Chen
     * @created 2020年2月27日 上午10:53:33
     * @param exeId
     */
    @SuppressWarnings("unchecked")
    public String saveJftzxxDataRes( String exeId) {
        // 开始通知缴费信息
        Map<String, Object> jftzxxDataRes = new HashMap<String, Object>();
        jftzxxDataRes.put("EXE_ID", exeId);
        String DATA_TYPE = "24";
        String HAS_ATTR = "0";
        jftzxxDataRes.put("DATA_TYPE", DATA_TYPE);
        jftzxxDataRes.put("OPER_TYPE", "I");
        jftzxxDataRes.put("HAS_ATTR", HAS_ATTR);
        jftzxxDataRes.put("INTER_TYPE", "10");
        String OTHER_STATUS = "3";
        jftzxxDataRes.put("OTHER_STATUS", OTHER_STATUS);
        String resId = dao.saveOrUpdate(jftzxxDataRes, "T_BSFW_SWBDATA_RES", null);
        return resId;
    }
    /**
     * 
     * 描述 保存过程指令数据
     * 
     * @author Flex Hu
     * @created 2015年12月29日 上午10:31:53
     */
    @SuppressWarnings("unchecked")
    private void saveGcxxDataRes(String taskId, String nodeName, String exeId, Map<String, Object> flowVars,
            String ysName) {
        // 开始保存过程信息
        Map<String, Object> gcxxDataRes = new HashMap<String, Object>();
        gcxxDataRes.put("EXE_ID", exeId);
        // 获取是否退回补件
        String EFLOW_ISBACK_PATCH = (String) flowVars.get("EFLOW_ISBACK_PATCH");
        String DATA_TYPE = "20";
        String HAS_ATTR = "0";
        /*if (StringUtils.isNotEmpty(EFLOW_ISBACK_PATCH) && EFLOW_ISBACK_PATCH.equals("true")) {
            DATA_TYPE = "22";
            List<Map> fileList = JSON.parseArray((String)flowVars.get("BJCLLB"), Map.class);
            if (fileList != null && fileList.size() > 0) {
                HAS_ATTR = "1";
            }
        }*/
        gcxxDataRes.put("DATA_TYPE", DATA_TYPE);
        gcxxDataRes.put("OPER_TYPE", "I");
        gcxxDataRes.put("HAS_ATTR", HAS_ATTR);
        gcxxDataRes.put("INTER_TYPE", "10");
        gcxxDataRes.put("TASK_ID", taskId);
        String OTHER_STATUS = "3";
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] {
                    "TYPE_CODE", "DIC_NAME" 
                }, new Object[] { "SWBDYZ", ysName }
        );
        if (dictionary != null) {
            OTHER_STATUS = (String) dictionary.get("DIC_CODE");
        }
        gcxxDataRes.put("OTHER_STATUS", OTHER_STATUS);
        dao.saveOrUpdate(gcxxDataRes, "T_BSFW_SWBDATA_RES", null);
        if (StringUtils.isEmpty(EFLOW_ISBACK_PATCH) || !EFLOW_ISBACK_PATCH.equals("true")) {
            Map<String, Object> task = this.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                    new Object[] { taskId });
            Map<String, Object> fromtask = this.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                    new Object[] { task.get("FROMTASK_IDS") });
            Map<String, Object> hangInfo = null;
            if(fromtask!=null){
                hangInfo = this.getByJdbc("JBPM6_HANGINFO", new String[] { "TASK_ID" },
                        new Object[] { fromtask.get("PARENT_TASKID") });
            }
            if (hangInfo != null && "true".equals(StringUtil.getValue(hangInfo, "EFLOW_ISBACK_PATCH"))) {
                Map<String, Object> bjDataRes = new HashMap<String, Object>();
                HAS_ATTR = "0";
                Map<String, Object> hangTask = this.getByJdbc("JBPM6_TASK", new String[] { "TASK_ID" },
                        new Object[] { task.get("FROMTASK_IDS") });
                Map<String, Object> bjxx = this.getByJdbc("T_WSBS_BJXX", new String[] { "TASK_ID", "EXE_ID" },
                        new Object[] { hangTask.get("FROMTASK_IDS"), exeId });

                bjDataRes.put("EXE_ID", exeId);
                bjDataRes.put("DATA_TYPE", "22");
                bjDataRes.put("OPER_TYPE", "I");
                bjDataRes.put("INTER_TYPE", "10");
                bjDataRes.put("TASK_ID", hangInfo.get("HANG_ID"));
                if (bjxx != null && bjxx.get("BJCLLB") != null) {
                    HAS_ATTR = "1";
                }
                bjDataRes.put("HAS_ATTR", HAS_ATTR);
                String resId = this.saveOrUpdate(bjDataRes, "T_BSFW_SWBDATA_RES", null);
                if (bjxx != null && bjxx.get("BJCLLB") != null) {
                    String itemCode = this
                            .getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId })
                            .get("ITEM_CODE").toString();
                    List<Map> fileList = JSON.parseArray((String) bjxx.get("BJCLLB"), Map.class);
                    String EFLOW_BUSRECORDID = (String) flowVars.get("EFLOW_BUSRECORDID");
                    String EFLOW_BUSTABLENAME = (String) flowVars.get("EFLOW_BUSTABLENAME");
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    if (fileList != null && fileList.size() > 0) {
                        for (Map file : fileList) {
                            file.put("ATTACH_KEY", file.get("MATER_CODE"));
                            Map<String,Object> map=this
                                    .getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "BUS_TABLENAME",
                                                    "BUS_TABLERECORDID", "ATTACH_KEY" },
                                            new Object[] { EFLOW_BUSTABLENAME,
                                                    EFLOW_BUSRECORDID, file.get("MATER_CODE") });
                            String sqfs="2";
                            if(map!=null){
                                sqfs = map.get("SQFS").toString();
                            }
                            file.put("SQFS", sqfs);
                            list.add(file);
                        }
                        swbDataAttrService.saveSwbDataAttr(resId, list, itemCode);
                    }
                }
            }
        }
        /*if(DATA_TYPE.equals("22")){
            String itemCode = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" }, new Object[] { exeId })
                    .get("ITEM_CODE").toString();
            List<Map> fileList = JSON.parseArray((String)flowVars.get("BJCLLB"), Map.class);
            String EFLOW_BUSRECORDID = (String) flowVars.get("EFLOW_BUSRECORDID");
            String EFLOW_BUSTABLENAME = (String) flowVars.get("EFLOW_BUSTABLENAME");
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            if (fileList != null && fileList.size() > 0) {
                for(Map file : fileList){
                    file.put("ATTACH_KEY", file.get("MATER_CODE"));
                    String sqfs = this
                            .getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                                    new String[] { "BUS_TABLENAME", "BUS_TABLERECORDID", "ATTACH_KEY" },
                                    new Object[] { EFLOW_BUSTABLENAME, EFLOW_BUSRECORDID, file.get("MATER_CODE") })
                            .get("SQFS").toString();
                    file.put("SQFS", sqfs);
                    list.add(file);
                }
                swbDataAttrService.saveSwbDataAttr(resId, list, itemCode);
            }
        }*/
    }

    /**
     * 
     * 描述 保存指令表数据
     * 
     * @author Flex Hu
     * @created 2015年12月28日 下午4:34:38
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveSwbDataRes(Map<String, Object> flowVars) {
        // 获取是否是暂存操作
        String isTempSave = StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));
        if (isTempSave.equals("-1")) {
            // 获取流程实例ID
            String exeId = (String) flowVars.get("EFLOW_EXEID");
            if (StringUtils.isNotEmpty(exeId)) {
                Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                        new Object[] { exeId });
                String defId = (String) flowExe.get("DEF_ID");
                String sqfs = (String) flowExe.get("SQFS");
                // 获取新产生的流程任务ID
                String EFLOW_CURRENTTASK_ID = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
                // 获取新产生的流程任务名称
                String EFLOW_CURUSEROPERNODENAME = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
                if (StringUtils.isNotEmpty(EFLOW_CURRENTTASK_ID) && StringUtils.isNotEmpty(EFLOW_CURUSEROPERNODENAME)) {
                    // 获取映射数据
                    Map<String, Object> flowMapInfo = flowMappedService.getFlowMapInfo(defId,
                            EFLOW_CURUSEROPERNODENAME, "1");
                    if (flowMapInfo != null) {
                        // 获取事项编码
                        int RUN_STATUS = Integer.parseInt(flowExe.get("RUN_STATUS").toString());
                        String ITEM_CODE = (String) flowExe.get("ITEM_CODE");
                        String SWB_ITEM_CODE = serviceItemService.getSwbItemCode(ITEM_CODE);
                        if (StringUtils.isNotEmpty(SWB_ITEM_CODE)) {
                            if (StringUtils.isNotEmpty(ITEM_CODE)&&!sqfs.equals("3")) {//省网下发办件不需同步办件基本信息
                                this.saveBjxxDataRes(flowVars, flowExe);
                            }
                            // 获取映射名称
                            String YS_NAME = (String) flowMapInfo.get("YS_NAME");
                            this.saveGcxxDataRes(EFLOW_CURRENTTASK_ID,
                                    EFLOW_CURUSEROPERNODENAME, exeId, flowVars,
                                    YS_NAME);
                            // 获取实例状态
                            if (RUN_STATUS != 0 && RUN_STATUS != 1) {
                                this.saveJgxxDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
                                this.saveJgxxLicDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
                                this.savePjxxDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
                            }
                        }
                        if (RUN_STATUS != 0 && RUN_STATUS != 1) {
                            String sxxz = flowVars.get("SXXZ")==null?
                                    "":flowVars.get("SXXZ").toString();
                            String bsyhlx = flowExe.get("BSYHLX")==null?
                                    "":flowExe.get("BSYHLX").toString();
                            if ("行政许可".equals(sxxz)&&"2".equals(bsyhlx)) {
                                this.saveXzxkDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
//                                this.saveXzxkGgxyDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
                            }
                        }
                    }
                }
                // 获取实例状态
//                int RUN_STATUS = Integer.parseInt(flowExe.get("RUN_STATUS").toString());
//                if (RUN_STATUS != 0 && RUN_STATUS != 1) {
//                    this.saveJgxxDataRes(flowVars, flowExe, EFLOW_CURRENTTASK_ID);
//                }
            }
        }
        return flowVars;
    }
}
