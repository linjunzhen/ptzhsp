/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.wsbs.controller;

import java.util.*;

import javassist.expr.NewArray;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import oracle.net.aso.h;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import freemarker.template.utility.DateUtil;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.LogConfigService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CatalogService;
import net.evecom.platform.wsbs.service.DepartServiceItemService;
import net.evecom.platform.wsbs.service.ServiceItemService;

/**
 * 描述  部门服务事项管理
 * @author Danto Huang
 * @version 1.0
 * @created 2016-9-13 下午4:48:25
 */
@Controller
@RequestMapping("/departServiceItemController")
public class DepartServiceItemController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DepartServiceItemController.class);
    /**
     * 引入Service
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * 引入Service
     */
    @Resource
    private DepartServiceItemService departServiceItemService;
    /**
     * 引入Service
     */
    @Resource
    private CatalogService catalogService;
    /**
     * 引入Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    /**
     * 
     */
    @Resource
    private FlowDefService flowDefService;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private LogConfigService logConfigService;
    
    /**
     * 
     * 描述   跳转部门服务事项管理主页
     * @author Danto Huang
     * @created 2016-9-13 下午4:50:41
     * @param request
     * @return
     */
    @RequestMapping(params="departServiceItemTab")
    public ModelAndView departServiceItemTab(HttpServletRequest request){
        
        return new ModelAndView("wsbs/departServiceItem/departServiceitemtab");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-18 下午2:08:38
     * @param request
     * @return
     */
    @RequestMapping(params="serviceItemView")
    public ModelAndView serviceItemView(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemView");
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-18 下午3:20:06
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
//        filter.addSorted("T.FWSXZT", "asc");
//        filter.addSorted("T.CREATE_TIME", "desc");
        filter.addSorted("DECODE(operate_time,NULL,0,1)", "DESC");
        filter.addSorted("OPERATE_TIME", "DESC");
        List<Map<String, Object>> list = departServiceItemService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    @RequestMapping(params="allDatagrid")
    public void allDatagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.DEPART_NAME", "desc");
//        filter.addSorted("T.CATALOG_NAME", "asc");
        List<Map<String, Object>> list = departServiceItemService.findAllBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-19 上午8:40:42
     * @param request
     * @return
     */
    @RequestMapping(params="info")
    public ModelAndView info(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                    new Object[] { entityId });
            // 获取所属目录
            String catalogCode = (String) serviceItem.get("CATALOG_CODE");
            if (StringUtils.isNotEmpty(catalogCode)) {
                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
            }

            Map<String, Object> userInfo = this.serviceItemService.getBindUserIdANdNames(entityId);
            serviceItem.put("PREAUDITER_IDS", userInfo.get("USER_IDS"));
            serviceItem.put("PREAUDITER_NAMES", userInfo.get("FULL_NAMES"));
            // 获取绑定的流程定义ID
            String defId = (String) serviceItem.get("BDLCDYID");
            if (StringUtils.isNotEmpty(defId)) {
                serviceItem.put("BDLCDYNAME", flowDefService.getDefNames(defId));
            }
            
            String ssbmbm = (String) serviceItem.get("SSBMBM");
            Map<String,Object> depart = departServiceItemService .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                            new String[] { "DEPART_CODE" }, new Object[] { ssbmbm });
            serviceItem.put("DEPART_ID", depart.get("DEPART_ID"));
            serviceItem.put("IMPL_DEPART", depart.get("DEPART_NAME"));
            
            if(serviceItem.get("ZBCS_ID")!=null){
                Map<String,Object> zbcs = departServiceItemService .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[] { "DEPART_ID" }, new Object[] { serviceItem.get("ZBCS_ID") });
                serviceItem.put("ZBCS", zbcs.get("DEPART_NAME"));
            }
                        
            if(serviceItem.get("BGSJ")!=null){
                String BGSJ = serviceItem.get("BGSJ").toString().replace("\n", "").replace("\r", "");
                serviceItem.put("BGSJ", BGSJ);
            }
            if(serviceItem.get("TRAFFIC_GUIDE")!=null){
                String TRAFFIC_GUIDE = serviceItem.get("TRAFFIC_GUIDE").toString().replace("\n", "").replace("\r", "");
                serviceItem.put("TRAFFIC_GUIDE", TRAFFIC_GUIDE);
            }
            if(serviceItem.get("BLDD")!=null){
                String BLDD = serviceItem.get("BLDD").toString().replace("\n", "").replace("\r", "");
                serviceItem.put("BLDD", BLDD);
            }
            if(serviceItem.get("JDDH")!=null){
                String JDDH = serviceItem.get("JDDH").toString().replace("\n", "").replace("\r", "");
                serviceItem.put("JDDH", JDDH);
            }
            
            Map<String, Object> right = departServiceItemService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                    new String[] { "RIGHT_ID" }, new Object[] { serviceItem.get("RIGHT_ID") });
            if(right!=null){
                serviceItem.put("RIGHT_NAME", right.get("RIGHT_NAME"));
                serviceItem.put("SUBITEM_NAME", right.get("SUBITEM_NAME"));
            }
            
            Map<String, Object> agency = departServiceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                    new String[] { "SERVICE_ID" }, new Object[] { serviceItem.get("AGENCYSERVICE_ID") });
            if(agency!=null){
                serviceItem.put("AGENCYSERVICE_NAME", agency.get("AGENCY_ITEM_NAME"));
            }
        } else {
            serviceItem = new HashMap<String, Object>();
            serviceItem.put("SFSF", "0");
        }
        //维护日志
        List<Map<String, Object>> uplist = serviceItemService.findLogByItemId(entityId,"1");
        //业务表维护日志
        List<Map<String, Object>> busLogList = sysLogService.getBusTableLogs("ITEM_ID", "T_WSBS_SERVICEITEM",
                new String[] { "ITEM_ID" }, new Object[] { entityId });
        uplist.addAll(busLogList);//代码质量
        serviceItem.put("uplogList", uplist);
        //排序
        this.listSortByTime(uplist,"OPERATE_TIME");
        List<Map<String, Object>> quxiaolist = serviceItemService.findLogByItemId(entityId,"3");
        List<Map<String, Object>> chexiaolist = serviceItemService.findLogByItemId(entityId,"-1");
        //quxiaolist.addAll(chexiaolist);
        //serviceItem.put("canclogList", quxiaolist);
        List<Map<String, Object>> fabulist = serviceItemService.findLogByItemId(entityId,"2");
        fabulist.addAll(quxiaolist);
        fabulist.addAll(chexiaolist);
        fabulist=this.listSortByTime(fabulist,"OPERATE_TIME");
        serviceItem.put("fabulogList", fabulist);
        
        if (StringUtils.isNotEmpty(request.getParameter("shan")) && !request.getParameter("shan").equals("undefined")) {
            request.setAttribute("shan", request.getParameter("shan"));// 区别草稿中的修改与审核库中的修改
        }
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("fileFlag", request.getParameter("fileFlag"));
        return new ModelAndView("wsbs/departServiceItem/departServiceitemInfo");
    }
    /**
     * 
     * 描述：list排序
     * @author Water Guo
     * @created 2017-11-20 上午10:19:35
     * @param list
     * @param sortName
     * @return
     */
    public List<Map<String,Object>> listSortByTime(List<Map<String,Object>> list,final String sortName){
        try {
            // 按时间sortName进行排序
            Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
                    Date day0= DateTimeUtil.getDateOfStr(arg0.get(sortName).toString(),"yyyy-MM-dd HH:mm:ss");
                    Date day1= DateTimeUtil.getDateOfStr(arg1.get(sortName).toString(),"yyyy-MM-dd HH:mm:ss");
                    if(day0.after(day1)){
                      return -1;  
                    }else{
                      return 1;
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }
    /**
     * 
     * 描述   部门服务事项更新
     * @author Danto Huang
     * @created 2016-9-20 上午10:36:56
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdate")
    public void saveOrUpdate(HttpServletRequest request,HttpServletResponse response){
        String entityId = request.getParameter("ITEM_ID");
        String status = "";
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        // 获取编码值
        String itemCode = request.getParameter("ITEM_CODE");
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> depart = null;
        if(variables.get("IMPL_DEPART_ID")!=null){
            depart = departServiceItemService .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                    new String[] { "DEPART_ID" }, new Object[] { variables.get("IMPL_DEPART_ID") });
            variables.put("SSBMBM", depart.get("DEPART_CODE"));
        }

        boolean isExists = serviceItemService.isExists(itemCode);
        if (StringUtils.isEmpty(entityId) && isExists) {
            result.put("success", false);
            result.put("msg", "事项编码重复,请修改");
        }else {
            if (StringUtils.isEmpty(entityId)) {
                /*获取本地新建事项数量*/
                int localItemCount = departServiceItemService.getLocalItemCount();
                /*部门编码*/
                String departCode = StringUtil.getValue(depart, "DEPART_CODE");
                String sxxz = StringUtil.getValue(variables, "SXXZ");
                String numFormate = String.format("%05d", ++localItemCount);
                variables.put("ITEM_CODE", departCode + sxxz + numFormate + "L");
                variables.put("FWSXZT", "-1");
                variables.put("IS_LOCAL", "1");
            }

            // 业务表保存操作日志
            //sysLogService.saveLogByMap("T_WSBS_SERVICEITEM", "ITEM_ID", entityId, variables,entityId);
            logConfigService.saveLogForBusTable("T_WSBS_SERVICEITEM", "ITEM_ID", entityId, variables, entityId);
            variables.put("UPDATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> serviceItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" },new Object[] {entityId});
            String currentStep=(String) variables.get("currentStep");
            if("1".equals(currentStep)){
                String oldSXLX="";
                if(serviceItem!=null){
                    oldSXLX=dictionaryService.findByDicCodeAndTypeCode(
                            (String) serviceItem.get("SXLX"), "ServiceItemType");
                }
                String newSXLX=dictionaryService.findByDicCodeAndTypeCode(
                        (String) variables.get("SXLX"), "ServiceItemType");
                if(!oldSXLX.equals(newSXLX)&&"即办件".equals(newSXLX)){
                    variables.put("CNQXGZR", "0");
                }
                
                if("1".equals(request.getParameter("IS_NEED_AGENCY"))){
                    if(serviceItem!=null&&serviceItem.get("AGENCYSERVICE_ID")!=null){
                        updateAgencyServiceRemove(serviceItem.get("AGENCYSERVICE_ID").toString(),itemCode);
                        updateAgencyService(variables);
                    }else{
                        updateAgencyService(variables);
                    }
                }else{
                    if(serviceItem!=null&&serviceItem.get("AGENCYSERVICE_ID")!=null){
                        updateAgencyServiceRemove(serviceItem.get("AGENCYSERVICE_ID").toString(),itemCode);
                    }
                }
            }
            String defid = request.getParameter("BDLCDYID");
            setBLLC(variables,defid);
            
            String recordId = departServiceItemService.saveOrUpdateCascade(variables);
            if (StringUtils.isNotEmpty(entityId)) {
                String defId = request.getParameter("BDLCDYID");
                if(StringUtils.isNotEmpty(defId)){
                    departServiceItemService.saveItemDefNode(entityId, defId);
                }
                status = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                                new Object[] { entityId }).get("FWSXZT").toString();
                if(status.equals("1")&&StringUtils.isEmpty(defId)){
                    serviceItemService.updateFwsxzt(entityId, "-1");
                    String version = serviceItem.get("C_VERSION").toString();
                    departServiceItemService.copyToHis(entityId,version);//复制到历史版本库
                    status = "-1";
                    saveItemLog("已发布事项撤销回草稿库",entityId,"-1",request);
                }
                sysLogService.saveLog("修改了ID为[" + entityId + "]的 服务事项记录", SysLogService.OPERATE_TYPE_EDIT);



                //添加维护记录
                if("1".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的基本信息部分",entityId,"1",request);
                }else if("2".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的申请方式部分", entityId, "1", request);
                }else if("3".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的时限配置部分", entityId, "1", request);
                }else if("4".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的办公指引部分", entityId, "1", request);
                }else if("5".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的申请材料部分",entityId,"1",request);
                }else if ("6".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的邮递服务部分",entityId,"1",request);
                }else if ("7".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的清单配置部分",entityId,"1",request);
                }else if ("8".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的办理流程部分",entityId,"1",request);
                }else if("9".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的收费配置部分",entityId,"1",request);
                }else if("10".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的预审人员部分",entityId,"1",request);
                }else if("11".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的常见问题部分",entityId,"1",request);
                }else if ("12".equals(currentStep)){
                    saveItemLog("修改了服务事项记录的其它配置部分",entityId,"1",request);
                }

            } else {
                String readIds = "402881ae52339704015233a8c997000d,402881ae52339704015233b930290012,"
                        + "402881ae52339704015233c94a09001e,402881ae52339704015233bebc1b0015,"
                        + "402881ae52339704015233c1c3540018,402881ae52339704015233c3c8cf001b,"
                        + "402881ae52339704015233cbcea20021,402881ae52339704015233ced6590024";
                serviceItemService.saveItemRead(recordId, readIds);
                status = "-1";
                sysLogService.saveLog("新增了ID为[" + recordId + "]的 服务事项记录", SysLogService.OPERATE_TYPE_ADD);
            }
            result.put("success", true);
            result.put("itemId", recordId);
            result.put("fwsxzt", status);
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/23 9:26:00
     * @param
     * @return
     */
    private void currentStepTwo(HttpServletRequest request, String entityId, Map<String, Object> variables,
                                Map<String, Object> serviceItem) {
        StringBuffer str = new StringBuffer();
        String oldtext=(String) variables.get("OLDRZBSDTFS_TEXT");
        String newtext=(String) variables.get("RZBSDTFS_TEXT");
        if(StringUtils.isNotEmpty(oldtext)&&!oldtext.equals(newtext)){
            str.append(itemLogNote(oldtext, newtext));
            saveItemLog(str.toString(),entityId,"1",request);
        }else if(StringUtils.isEmpty(oldtext)){
            str.append(itemLogNote(oldtext, newtext));
            saveItemLog(str.toString(),entityId,"1",request);
        }
    }

    /**
     * 
     * 描述    更新中介服务
     * @author Danto Huang
     * @created 2018年8月23日 上午9:16:30
     * @param variables
     */
    private void updateAgencyService(Map<String,Object> variables){
        String agencyId = variables.get("AGENCYSERVICE_ID").toString();
        Map<String, Object> agency = serviceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                new String[] { "SERVICE_ID" }, new Object[] { agencyId });
        String itemCode = "";
        String itemName = "";
        if(agency.get("ITEM_CODE")!=null){
            itemCode = agency.get("ITEM_CODE").toString();
            if(StringUtils.isNotEmpty(itemCode)){
                itemCode = itemCode+",";
            }
        }
        if(agency.get("ITEM_NAME")!=null){
            itemName = agency.get("ITEM_NAME").toString();
            if(StringUtils.isNotEmpty(itemName)){
                itemName = itemName+",";
            }
        }
        Map<String,Object> update = new HashMap<String, Object>();
        update.put("ITEM_CODE", itemCode+variables.get("ITEM_CODE"));
        update.put("ITEM_NAME", itemName+variables.get("ITEM_NAME"));
        serviceItemService.saveOrUpdate(update, "T_SMOGA_AGENCYSERVICE", agencyId);
    }
    
    /**
     * 
     * 描述    更新中介服务(删除)
     * @author Danto Huang
     * @created 2018年8月23日 上午9:16:30
     * @param agencyId
     * @param itemCode
     */
    private void updateAgencyServiceRemove(String agencyId,String itemCode){
        Map<String, Object> agency = serviceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                new String[] { "SERVICE_ID" }, new Object[] { agencyId });
        String[] itemCodes = agency.get("ITEM_CODE").toString().split(",");
        String[] itemNames = agency.get("ITEM_NAME").toString().split(",");
        String newCodes = "";
        String newNames = "";
        for(int i=0;i<itemCodes.length;i++){
            if(!itemCodes[i].equals(itemCode)){
                if(StringUtils.isEmpty(newCodes)){
                    newCodes = itemCodes[i];
                    newNames = itemNames[i];
                }else{
                    newCodes += "," + itemCodes[i];
                    newNames += "," + itemNames[i];
                }
            }
        }
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("ITEM_CODE", newCodes);
        variables.put("ITEM_NAME", newNames);
        serviceItemService.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", agencyId);
    }
    
    /**
     * 
     * 流程配置
     */
    private void currentStepThirteen(HttpServletRequest request, String entityId, Map<String, Object> serviceItem) {
        String str;
        str="流程配置";
        String oldLXR=(String) serviceItem.get("BDLCDYID")!=null?(String) serviceItem.get("BDLCDYID"):"";
        Map<String,Object> newItem = serviceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_ID" },new Object[] {entityId});
        String newLXR=(String) newItem.get("BDLCDYID");
        String olddefNames = "";
        String newdefNames = "";
        if(StringUtils.isNotEmpty(oldLXR)){
            olddefNames = flowDefService.getDefNames(oldLXR);
        }
        if(StringUtils.isNotEmpty(newLXR)){
            newdefNames = flowDefService.getDefNames(newLXR);
        }
        if(!oldLXR.equals(newLXR)){
            str+="【流程配置】:'"+olddefNames+"'修改为'"+newdefNames+"';";
            saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
        }
    }
    /**
     * 
     * 描述：办理流程
     * @author Water Guo
     * @created 2017-4-9 下午8:31:24
     * @param variables
     * @param defId
     */
    private void setBLLC(Map<String, Object> variables,String defId){
        if(StringUtils.isNotEmpty(defId)){
        StringBuffer BLLC=new StringBuffer();
        List<Map<String,Object>>  nodeNames=departServiceItemService.getNodeNamesByDefid(defId);
        for(Map<String,Object> map:nodeNames){
            String nodeName = map.get("NODE_NAME")==null?
                    "":map.get("NODE_NAME").toString();
            if ("开始".equals(nodeName)
                    ||"结束".equals(nodeName)
                    ||"待预审".equals(nodeName)
                    ||"网上预审".equals(nodeName)) {
                
            }else{
               BLLC.append(nodeName).append("-");
            }
        }
        if(StringUtils.isNotEmpty(BLLC)){
            variables.put("BLLC",BLLC.substring(0, BLLC.length()-1));
        }
        }
    }
    /**
     * 
     * 办理结果
     */
    private void currentStepOneS(HttpServletRequest request, String entityId, Map<String, Object> variables,
            Map<String, Object> serviceItem) {
        StringBuffer str = new StringBuffer("办理结果部分");
        String oldLXR = (String) serviceItem.get("FINISH_TYPE") != null ? (String) serviceItem
                .get("FINISH_TYPE") : "";
        String newLXR = (String) variables.get("FINISH_TYPE") != null ? (String) variables
                .get("FINISH_TYPE") : "";
        String oldLXDH = (String) serviceItem.get("FINISH_GETTYPE") != null ? (String) serviceItem
                .get("FINISH_GETTYPE") : "";
        String newLXDH = (String) variables.get("FINISH_GETTYPE") != null ? (String) variables
                .get("FINISH_GETTYPE") : "";
        String oldtxt = (String) serviceItem.get("FINISH_INFO") != null ? (String) serviceItem
                .get("FINISH_INFO") : "";
        String newtxt = (String) variables.get("FINISH_INFO") != null ? (String) variables
                .get("FINISH_INFO") : "";
        boolean flag=false;
        if(!oldLXR.equals(newLXR)){
            String oldname = dictionaryService.findByDicCodeAndTypeCode(
                            (String) serviceItem.get("FINISH_TYPE"),"FinishType");
            String newname = dictionaryService.findByDicCodeAndTypeCode(
                            (String) variables.get("FINISH_TYPE"),"FinishType");
            str.append(itemLogNote("办理结果", oldname, newname));
            flag=true;
        }
        if(!oldLXDH.equals(newLXDH)){
            String oldname=dictionaryService.findByDicCodeAndTypeCode(oldLXDH, "FinishGetType");
            String newname=dictionaryService.findByDicCodeAndTypeCode(newLXDH, "FinishGetType");
            str.append(itemLogNote("获取方式", oldname, newname));
            flag=true;
        }
        if(!oldtxt.equals(newtxt)){
            str.append(itemLogNote("结果获取说明", oldtxt, newtxt));
            flag=true;
        }
        if(flag){
            saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
        }
    }
    /**
     * 
     * 办理时限
     */
    private void currentStepFour(HttpServletRequest request, String entityId, Map<String, Object> variables,
            Map<String, Object> serviceItem) {
        String str;
        str="时限配置部分:";
        String oldwork=serviceItem.get("CNQXGZR")!=null?serviceItem.get("CNQXGZR").toString():"";
        String newwork=variables.get("CNQXGZR")!=null?(String) variables.get("CNQXGZR"):"";
        String oldqx=serviceItem.get("CNQXSM")!=null?(String) serviceItem.get("CNQXSM"):"";
        String newqx=variables.get("CNQXSM")!=null?(String) variables.get("CNQXSM"):"";
        String oldlaw=(String) serviceItem.get("FDQX")!=null?(String) serviceItem.get("FDQX"):"";
        String newlaw=(String) variables.get("FDQX")!=null?(String) variables.get("FDQX"):"";
        if(!oldwork.equals(newwork)){
            str+="<br/>【承诺时限工作日】:'"+oldwork+"'修改为'"+newwork+"';";
        }
        if(!oldqx.equals(newqx)){
            str+="<br/>【承诺时限说明】:'"+oldqx+"'修改为'"+newqx+"';";
        }
        if(!oldlaw.equals(newlaw)){
            str+="<br/>【法定时限说明】:'"+oldlaw+"'修改为'"+newlaw+"';";
        }
        //saveItemLog("修改了服务事项记录的"+str,entityId,"1",request);
    }
    /**
     * 
     * 申报方式
     */
    private void currentStepThree(HttpServletRequest request, String entityId, 
            Map<String, Object> variables, String str) {
        String content="修改了申报方式部分:";
        String oldtext=(String) variables.get("OLDRZBSDTFS_TEXT");
        String newtext=(String) variables.get("RZBSDTFS_TEXT");
        if(StringUtils.isNotEmpty(oldtext)&&!oldtext.equals(newtext)){
//            str+="'"+oldtext+"'修改为'"+newtext+"'";
            saveItemLog(content,entityId,"1",request);
        }else if(StringUtils.isEmpty(oldtext)){
//            str+="'"+oldtext+"'修改为'"+newtext+"'";
            saveItemLog(content,entityId,"1",request);
        }
    }
    
    /**
     * 基本信息
     */
    private void currentStepOne(HttpServletRequest request, String entityId, Map<String, Object> variables,
            Map<String, Object> serviceItem,Map<String, Object> result) {
        StringBuffer str = new StringBuffer("基本信息");
        String oldSXXZ = dictionaryService.findByDicCodeAndTypeCode(
                        (String) serviceItem.get("SXXZ"),"ServiceItemXz");
        String newSXXZ = dictionaryService.findByDicCodeAndTypeCode(
                        (String) variables.get("SXXZ"),"ServiceItemXz");
        String oldSXLX = dictionaryService.findByDicCodeAndTypeCode(
                        (String) serviceItem.get("SXLX"),"ServiceItemType");
        String newSXLX = dictionaryService.findByDicCodeAndTypeCode(
                        (String) variables.get("SXLX"),"ServiceItemType");
        String oldflag = dictionaryService.findByDicCodeAndTypeCode(
                        (String) serviceItem.get("FTA_FLAG"),"isFta");
        String newflag = dictionaryService.findByDicCodeAndTypeCode(
                        (String) variables.get("FTA_FLAG"), "isFta");
        String oldSsdw = (String) serviceItem.get("IMPL_DEPART");
        String newSsdw = (String) variables.get("IMPL_DEPART");
        String oldZbcs = (String) serviceItem.get("ZBCS");
        String newZbcs = (String) variables.get("ZBCS");
        if(!oldSXXZ.equals(newSXXZ)){
            str.append(itemLogNote("事项性质", oldSXXZ, newSXXZ));
        }
        if(!oldSXLX.equals(newSXLX)){
            str.append(itemLogNote("办件类型", oldSXLX, newSXLX));
            if("即办件".equals(oldSXLX)){
                result.put("SXLXflag",2);
            }else if("即办件".equals(newSXLX)){
                result.put("SXLXflag",1);
            }
        }
        if(!oldflag.equals(newflag)){
            str.append(itemLogNote("事项属性", oldflag, newflag));
        }
        if (!Objects.equals(oldSsdw, newSsdw)) {
            str.append(itemLogNote("实施单位", oldSsdw, newSsdw));
        }
        if (!Objects.equals(oldZbcs, newZbcs)) {
            str.append(itemLogNote("主办处室", oldZbcs, newZbcs));
        }
        saveItemLog("修改了服务事项记录的" + str, entityId, "1", request);
    }
    /**
     * save item log
     * @param content
     * @param itemId
     * @param type
     * @param request
     */
    public void saveItemLog(String content,String itemId,String type,HttpServletRequest request){
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE",type);
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        serviceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
    }
    
    /**
     * 
     * 描述   删除服务事项
     * @author Danto Huang
     * @created 2016-9-20 下午3:37:42
     * @param request
     * @return
     */
    @RequestMapping(params="multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        List<Map<String, Object>> list=serviceItemService.findByItemIds(selectColNames);
        if(list!=null&&list.size()>0){
            j.setSuccess(false);
            j.setMsg("要删除的服务事项存在对应的办件，禁止删除");
        }else{
            departServiceItemService.removeCascade(selectColNames);
            sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 服务事项记录", SysLogService.OPERATE_TYPE_DEL);
            j.setMsg("删除成功");
        }
        return j;
    }
    
    /**
     * 
     * 描述   特殊环节数据列表
     * @author Danto Huang
     * @created 2016-9-21 下午4:35:41
     * @param request
     * @param response
     */
    @RequestMapping(params="specialLinkData")
    public void specialLinkData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.CREATE_TIME", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        List<Map<String, Object>> list = departServiceItemService.getSpecialLink(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   特殊环节信息
     * @author Danto Huang
     * @created 2016-9-21 下午4:59:35
     * @param request
     * @return
     */
    @RequestMapping(params="specialLinkInfo")
    public ModelAndView specialLinkInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String itemId = request.getParameter("itemId");
        Map<String,Object> linkInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            linkInfo = departServiceItemService.getByJdbc(
                    "T_WSBS_SERVICEITEM_LINK", new String[] { "RECORD_ID" },
                    new Object[] { entityId });
        }else{
            linkInfo.put("ITEM_ID", itemId);
        }
        request.setAttribute("linkInfo", linkInfo);
        return new ModelAndView("wsbs/departServiceItem/specialLinkInfo");
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-9-21 下午5:22:25
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateLink")
    @ResponseBody
    public AjaxJson saveOrUpdateLink(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String sfzcgq="0";
        String entityId = request.getParameter("RECORD_ID");
        String itemId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);

        if (StringUtils.isNotEmpty(itemId)) {
            Map<String, Object> item = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" }, new Object[] { itemId });
            String status = item.get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                departServiceItemService.copyToHis(itemId,item.get("C_VERSION").toString());//复制到历史版本库
                status = "-1";
                saveItemLog("新增或修改特殊环节，已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
        //业务表保存操作日志
        sysLogService.saveLogByMap("T_WSBS_SERVICEITEM_LINK", "RECORD_ID"
                , entityId, variables,entityId);
        String recordId=departServiceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_LINK", entityId);
        //日志
        String content="";
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 特殊环节",SysLogService.OPERATE_TYPE_EDIT);
            content="修改了服务事项记录的特殊环节记录：'修改环节'的名称为："+variables.get("LINK_NAME");
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 特殊环节",SysLogService.OPERATE_TYPE_ADD);
            content="修改了服务事项记录的特殊环节记录：'新增环节'的名称为："+variables.get("LINK_NAME");
        }
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE","1");
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("BUS_TABLENAME", "T_WSBS_SERVICEITEM_LINK");
        data.put("BUS_INDEX", entityId);
        data.put("REMARK","T_WSBS_SERVICEITEM_LINK");
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        departServiceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
        
        //根据特殊环节判断是否支持挂起
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        List<Map<String, Object>> list = departServiceItemService.getSpecialLink(filter);
        if(!list.isEmpty()){
            sfzcgq="1";
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("SFZCGQ", sfzcgq);
        departServiceItemService.saveOrUpdate(map, "T_WSBS_SERVICEITEM", itemId);
        return j;
    }
    @SuppressWarnings("unchecked")
    @RequestMapping(params="logDetail")
    public ModelAndView logDetail(HttpServletRequest request){
      String tableName = request.getParameter("TABLENAME");
      String index = request.getParameter("INDEX");
      String OPERATETIME=request.getParameter("OPERATETIME");
      List<Map<String,Object>> busLogList=sysLogService.getChBusTableLogs(index, tableName);
      busLogList=sysLogService.logToStand(busLogList);
      String json = JSON.toJSONString(busLogList);
      request.setAttribute("resultJson", json);
      request.setAttribute("busLogList", busLogList);
      request.setAttribute("OPERATETIME", OPERATETIME);
      return new ModelAndView("hflow/execution/logDetail");
    }
    /**
     * 
     * 描述   删除特殊环节信息
     * @author Danto Huang
     * @created 2016-9-22 上午8:49:24
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelTshj")
    @ResponseBody
    public AjaxJson multiDelTshj(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");

        String itemId = request.getParameter("itemId");
        if (StringUtils.isNotEmpty(itemId)) {
            Map<String, Object> item = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" }, new Object[] { itemId });
            String status = item.get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                departServiceItemService.copyToHis(itemId,item.get("C_VERSION").toString());//复制到历史版本库
                status = "-1";
                saveItemLog("删除特殊环节，已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
        
        departServiceItemService.remove("T_WSBS_SERVICEITEM_LINK", "RECORD_ID", selectColNames.split(","));
        //根据特殊环节判断是否支持挂起
        String sfzcgq="0";
        SqlFilter filter = new SqlFilter(request);
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        List<Map<String, Object>> list = departServiceItemService.getSpecialLink(filter);
        if(!list.isEmpty()){
            sfzcgq="1";
        }
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("SFZCGQ", sfzcgq);
        departServiceItemService.saveOrUpdate(map, "T_WSBS_SERVICEITEM", itemId);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 描述   退回库
     * @author Danto Huang
     * @created 2016-9-23 下午2:28:24
     * @param request
     * @return
     */
    @RequestMapping(params="back")
    public ModelAndView back(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemBack");
    }
    /**
     * 
     * 描述   审核库
     * @author Danto Huang
     * @created 2016-9-23 下午2:58:01
     * @param request
     * @return
     */
    @RequestMapping(params="auditing")
    public ModelAndView auditing(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemAudit");
    }

    /**
     * easyui AJAX请求数据 审核库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "auditingdatagrid")
    public void auditingdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("OPERATE_TIME", "DESC");
        List<Map<String, Object>> list = departServiceItemService.findByAuditingSqlFilter(filter);
        if (list != null)
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述：请求数据 拟发布库
     * @author Water Guo
     * @created 2017-3-22 下午4:40:53
     * @param request
     * @param response
     */
    @RequestMapping(params = "prePublishdatagrid")
    public void prePublishdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("OPERATE_TIME", "DESC");
        List<Map<String, Object>> list = departServiceItemService.findByPrePublicSqlFilter(filter);
        if (list != null)
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   审核库
     * @author Danto Huang
     * @created 2016-9-23 下午2:58:01
     * @param request
     * @return
     */
    @RequestMapping(params="publish")
    public ModelAndView publish(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemPublish");
    }
    /**
     * 
     * 描述：拟发布库
     * @author Water Guo
     * @created 2017-3-22 上午9:17:20
     * @param request
     * @return
     */
    @RequestMapping(params="prePublish")
    public ModelAndView prePublish(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemPrePublish");
    }
    /**
     * easyui AJAX请求数据 发布库列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "publishdatagrid")
    public void publishdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
//        filter.addSorted("T.C_SN", "desc");
        filter.addSorted("operate_time", "desc nulls last");
        filter.addSorted("t.create_time", "desc");
        List<Map<String, Object>> list = departServiceItemService.findByPublishSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述   取消库
     * @author Danto Huang
     * @created 2016-9-23 下午2:58:01
     * @param request
     * @return
     */
    @RequestMapping(params="cancel")
    public ModelAndView cancel(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/departServiceItemCancel");
    }
    /**
     * 
     * 描述： 事项汇总
     * @author Water Guo
     * @created 2017-3-3 上午11:10:04
     * @param request
     * @return
     */
    @RequestMapping(params="allView")
    public ModelAndView allView(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/serviceallItem");
    }
    
    /**
     * 
     * 描述   流程节点设置数据
     * @author Danto Huang
     * @created 2016-9-28 下午2:48:48
     * @param request
     * @param response
     */
    @RequestMapping(params="defNodeData")
    public void defNodeData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.NODE_KEY","asc");
        String itemId = request.getParameter("itemId");
        if(StringUtils.isEmpty(itemId)||itemId.equals("undefined")){
            itemId = request.getParameter("Q_T.ITEM_ID_EQ");
        }
        if(StringUtils.isNotEmpty(itemId)){
            filter.addFilter("Q_T.ITEM_ID_=",itemId);
            List<Map<String, Object>> list = departServiceItemService.getDefNode(filter);
            List<Map<String, Object>> resultList =  new ArrayList<Map<String,Object>>();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Map<String, Object> map = (Map<String, Object>) iterator.next();
                String nodeName = map.get("NODE_NAME")==null?
                        "":map.get("NODE_NAME").toString();
                if ("开始".equals(nodeName)
                        ||"结束".equals(nodeName)
                        ||"待受理".equals(nodeName)
                        ||"网上预审".equals(nodeName)
                        ||"受理自动跳转".equals(nodeName)) {
                    
                }else{
                    resultList.add(map);
                }
            }
            this.setListToJsonString(filter.getPagingBean().getTotalItems(), resultList,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    /**
     * 
     * 描述   流程环节审核人信息
     * @author Danto Huang
     * @created 2016-9-28 下午5:19:50
     * @param request
     * @return
     */
    @RequestMapping(params="auditerInfo")
    public ModelAndView auditerInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> auditerInfo = departServiceItemService.getByJdbc(
                "T_WSBS_SERVICEITEM_AUDITER", new String[] { "RECORD_ID" },
                new Object[] { entityId });
        request.setAttribute("auditerInfo", auditerInfo);
        return new ModelAndView("wsbs/departServiceItem/auditerInfo");
    }
    
    /**
     * 
     * 描述   保存/更新事项流程环节审核人
     * @author Danto Huang
     * @created 2016-9-29 上午8:49:55
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateAuditer")
    @ResponseBody
    public AjaxJson saveOrUpdateAuditer(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        departServiceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_AUDITER", entityId);
        j.setMsg("保存成功！");
        return j;
    } 
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2016-10-27 上午10:03:26
     * @param request
     * @return
     */
    @RequestMapping(params="checkNeed")
    @ResponseBody
    public AjaxJson checkNeed(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectIds = request.getParameter("selectIds");
        String[] itemIds = selectIds.split(",");
        String msg = null;
        String breakItemName = null;
        for(int i=0;i<itemIds.length;i++){
            String itemId = itemIds[i];
            Map<String, Object> item = departServiceItemService.getByJdbc(
                    "T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                    new Object[] { itemId });
            /*判断材料是否完整*/
            Map<String,Object> checkMap = departServiceItemService.checkServiceItemApplyMater(item);
            /*判断部门是否正确*/
            Map<String,Object> checkDepartMap = departServiceItemService.checkServiceItemDepart(item);
            String sxlx = StringUtil.getValue(item, "SXLX");
            String preday = StringUtil.getValue(item, "PREDAY");
            String ckcs1 = item.get("CKCS_1") == null?
                    "0":item.get("CKCS_1").toString();
            String ckcs2 = item.get("CKCS_2") == null?
                    "0":item.get("CKCS_2").toString();
            String ckcs3 = item.get("CKCS_3") == null?
                    "0":item.get("CKCS_3").toString();
            String ckcs4 = item.get("CKCS_4") == null?
                    "0":item.get("CKCS_4").toString();
            breakItemName = item.get("ITEM_NAME").toString();
            if (!(boolean)checkDepartMap.get("flag")){
                msg = StringUtil.getValue(checkDepartMap, "msg");
                break;
            } else if(item.get("RZBSDTFS")==null){
                msg = "事项未确定申报方式！";
                break;
            }else if (Objects.equals(sxlx,"1") && !Objects.equals(preday,"1")){
                msg = "即办件的外网预审时限应设置为1日！";
                break;
            }
            else if (!(boolean)checkMap.get("flag")) {
                msg = (String)checkMap.get("msg");
                break;
            }else if(item.get("SSZTMC")==null){
                msg = "办理窗口未填写！";
                break;
            }else if(item.get("BDLCDYID")==null){
                msg = "事项未绑定流程！";
                break;
            }/* else if ("in01".equals(item.get("RZBSDTFS"))
                    &&("-1".equals(ckcs1)||"-1".equals(ckcs3)||"-1".equals(ckcs4))) {
                msg = "到窗口次数未填写规范！";
                break;
            }else if(!"in01".equals(item.get("RZBSDTFS"))
                    &&("-1".equals(ckcs1)||"-1".equals(ckcs2)||"-1".equals(ckcs3)||"-1".equals(ckcs4))){
                msg = "到窗口次数未填写规范！";
                break;
            }*/ else if(Integer.parseInt(ckcs2)>Integer.parseInt(ckcs1)||
                    Integer.parseInt(ckcs3)>Integer.parseInt(ckcs1)||
                    Integer.parseInt(ckcs4)>Integer.parseInt(ckcs1)){
                msg = "到窗口次数未填写规范！网上申请到窗口次数应不大于政服务中心窗口受理到窗口次数。";
                break;
            }else if(item.get("CNQXGZR")==null){
                msg = "承诺时限未配置！";
                break;
            }else if(!(departServiceItemService.defNodeSetDone(itemId, item.get("BDLCDYID").toString())
                  ||departServiceItemService.isGcjsDefTypeNode(item.get("BDLCDYID").toString()))){
                msg = "流程设置环节办理人员配置未完成！";
                break;
            } else{
                Map<String, Object> userInfo = this.serviceItemService.getBindUserIdANdNames(itemId);
                //指南式in01 和链接式in02 不做校验; 收办分离式 in04  全流程 in05校验
                String rzbsdtfs = item.get("RZBSDTFS").toString();
                if(userInfo.get("USER_IDS")==null&&
                        (rzbsdtfs.endsWith("in04")||rzbsdtfs.endsWith("in05"))){
                    msg = "事项未配置预审人员！";
                    break;
                }
            }
            breakItemName = null;
        }
        if(StringUtils.isNotEmpty(msg)){
            j.setMsg("事项【"+breakItemName+"】"+msg);
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 
     * 描述： 事项汇总
     * @author Water Guo
     * @created 2017-3-3 上午11:10:04
     * @param request
     * @return
     */
    @RequestMapping(params="allCatalogView")
    public ModelAndView allCatalogView(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/allCatalogView");
    }
    /**
     *
     * 描述： 事项汇总
     * @author Water Guo
     * @created 2017-3-3 上午11:10:04
     * @param request
     * @return
     */
    @RequestMapping("/isGcjsDefTypeNode")
    public void isGcjsDefTypeNode(HttpServletRequest request,HttpServletResponse response){
        String defId=request.getParameter("defId");
        boolean flag=departServiceItemService.isGcjsDefTypeNode(defId);;
        String json = JSON.toJSONString(flag);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午2:58:16
     * @param request
     * @return
     */
    @RequestMapping(params="selector")
    public ModelAndView selector(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");
        String itemCodes = request.getParameter("itemCodes");
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("itemCodes", itemCodes);
        return new ModelAndView("wsbs/departServiceItem/ItemSelector");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午4:19:53
     * @param request
     * @param response
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request,
            HttpServletResponse response) {
        String itemCodes = request.getParameter("itemCodes");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(itemCodes)){
            list = departServiceItemService.findByItemCodes(itemCodes);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * 
     * 描述    收费明细数据
     * @author Danto Huang
     * @created 2018年8月27日 下午5:44:59
     * @param request
     * @param response
     */
    @RequestMapping(params="chargeData")
    public void chargeData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.CREATE_TIME", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        List<Map<String, Object>> list = departServiceItemService.getChargeData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月27日 下午5:59:43
     * @param request
     * @return
     */
    @RequestMapping(params="chargeInfo")
    public ModelAndView chargeInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String itemId = request.getParameter("itemId");
        Map<String,Object> chargeInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            chargeInfo = departServiceItemService.getByJdbc(
                    "T_WSBS_SERVICEITEM_CHARGE", new String[] { "RECORD_ID" },
                    new Object[] { entityId });
        }else{
            chargeInfo.put("ITEM_ID", itemId);
        }
        request.setAttribute("chargeInfo", chargeInfo);
        return new ModelAndView("wsbs/departServiceItem/chargeInfo");
    }
    
    /**
     * 
     * 描述    保存收费明细
     * @author Danto Huang
     * @created 2018年8月27日 下午6:04:55
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateCharge")
    @ResponseBody
    public AjaxJson saveOrUpdateCharge(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        String itemId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isNotEmpty(itemId)) {
            Map<String, Object> item = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" }, new Object[] { itemId });
            String status = item.get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                departServiceItemService.copyToHis(itemId,item.get("C_VERSION").toString());//复制到历史版本库
                status = "-1";
                saveItemLog("新增或修改收费明细，已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
        //业务表保存操作日志
        sysLogService.saveLogByMap("T_WSBS_SERVICEITEM_CHARGE", "RECORD_ID"
                , entityId, variables,entityId);
        String recordId=departServiceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_CHARGE", entityId);
        //日志
        String content="";
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 收费明细",SysLogService.OPERATE_TYPE_EDIT);
            content="修改了服务事项记录的收费明细记录：'修改明细'名称为："+variables.get("CHARGE_DETAIL");
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的  收费明细",SysLogService.OPERATE_TYPE_ADD);
            content="修改了服务事项记录的收费明细记录：'新增明细'名称为："+variables.get("CHARGE_DETAIL");
        }
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE","1");
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("BUS_TABLENAME", "T_WSBS_SERVICEITEM_CHARGE");
        data.put("BUS_INDEX", entityId);
        data.put("REMARK","T_WSBS_SERVICEITEM_CHARGE");
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        departServiceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
        
        return j;
    }
    
    /**
     * 
     * 描述    删除收费明细
     * @author Danto Huang
     * @created 2018年8月27日 下午6:05:07
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelCharge")
    @ResponseBody
    public AjaxJson multiDelCharge(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String itemId = request.getParameter("itemId");
        if (StringUtils.isNotEmpty(itemId)) {
            Map<String, Object> item = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[] { "ITEM_ID" }, new Object[] { itemId });
            String status = item.get("FWSXZT").toString();
            if(status.equals("1")){
                serviceItemService.updateFwsxzt(itemId, "-1");
                departServiceItemService.copyToHis(itemId,item.get("C_VERSION").toString());//复制到历史版本库
                status = "-1";
                saveItemLog("删除收费明细，已发布事项撤销回草稿库",itemId,"-1",request);
            }
        }
        
        departServiceItemService.remove("T_WSBS_SERVICEITEM_CHARGE", "RECORD_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    自查条件数据
     * @author Danto Huang
     * @created 2018年9月3日 下午5:40:13
     * @param request
     * @param response
     */
    @RequestMapping(params="selfExamData")
    public void selfExamData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.EXAM_SN", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        List<Map<String, Object>> list = departServiceItemService.getSelfExamData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    自查信息
     * @author Danto Huang
     * @created 2018年9月4日 上午9:04:20
     * @param request
     * @return
     */
    @RequestMapping(params="selfExamInfo")
    public ModelAndView selfExamInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String handleType = request.getParameter("handleType");
        String itemId = request.getParameter("itemId");
        Map<String, Object> examinationInfo = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            examinationInfo = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM_SELFEXAM",
                    new String[] { "RECORD_ID" }, new Object[] { entityId });
        }else{
            examinationInfo.put("BUS_HANDLE_TYPE", handleType);
            examinationInfo.put("ITEM_ID", itemId);
        }
        request.setAttribute("examinationInfo", examinationInfo);
        return new ModelAndView("wsbs/departServiceItem/selfExamInfo");
    }
    
    /**
     * 
     * 描述    保存自查条件信息
     * @author Danto Huang
     * @created 2018年9月4日 上午9:54:30
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateSelfEaxm")
    @ResponseBody
    public AjaxJson saveOrUpdateSelfEaxm(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        String itemId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("EXAM_SN", departServiceItemService.getMaxExamSn()+1);
        }
        String recordId=departServiceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM_SELFEXAM", entityId);
        
        //业务表保存操作日志
        sysLogService.saveLogByMap("T_WSBS_SERVICEITEM_SELFEXAM", "RECORD_ID"
                , entityId, variables,entityId);
        //日志
        String content="";
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 自查条件",SysLogService.OPERATE_TYPE_EDIT);
            content="修改了服务事项记录的自查条件记录：'修改自查条件'标题为："+variables.get("EXAM_TITLE");
        }else{
            sysLogService.saveLog("修改了ID为["+recordId+"]的 特殊环节",SysLogService.OPERATE_TYPE_ADD);
            content="修改了服务事项记录的自查条件记录：'新增自查条件'标题为："+variables.get("EXAM_TITLE");
        }
        Map<String,Object> data=new HashMap<String, Object>();
        data.put("OPERATE_CONTENT",content);
        data.put("ITEM_ID", itemId);
        data.put("OPERATE_TYPE","1");
        data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        SysUser sysUser = AppUtil.getLoginUser();
        data.put("FULLNAME",sysUser.getFullname());
        data.put("USERNAME",sysUser.getUsername());
        data.put("BUS_TABLENAME", "T_WSBS_SERVICEITEM_SELFEXAM");
        data.put("BUS_INDEX", entityId);
        data.put("REMARK","T_WSBS_SERVICEITEM_SELFEXAM");
        data.put("USERID", sysUser.getUserId());
        String idAddress = BrowserUtils.getIpAddr(request);
        data.put("IP_ADDRESS",idAddress);
        departServiceItemService.saveOrUpdate(data, "T_WSBS_SERVICEITEM_LOG", null);
        
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月4日 上午10:58:32
     * @param request
     * @return
     */
    @RequestMapping(params="multiDelSelfExam")
    @ResponseBody
    public AjaxJson multiDelSelfExam(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        
        departServiceItemService.remove("T_WSBS_SERVICEITEM_SELFEXAM", "RECORD_ID", selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    

    /**
     * 
     * 描述   商户管理
     * @author Danto Huang
     * @created 2016-9-23 下午2:58:01
     * @param request
     * @return
     */
    @RequestMapping(params="merchant")
    public ModelAndView merchant(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/merchantView");
    }

    /**
     * 
     * 描述    列表数据
     * @author Danto Huang
     * @created 2018年9月20日 上午11:01:05
     * @param request
     * @param response
     */
    @RequestMapping(params="merchantData")
    public void merchantData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.CREATE_TIME", "desc");
        List<Map<String, Object>> list = departServiceItemService.getMerchantData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    商户信息
     * @author Danto Huang
     * @created 2018年9月20日 下午2:29:08
     * @param request
     * @return
     */
    @RequestMapping(params="merchantInfo")
    public ModelAndView merchantInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String ,Object> merchant = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(entityId)){
            merchant = departServiceItemService.getByJdbc("T_SERVICEITEM_MERCHANT", new String[] { "RECORD_ID" },
                    new Object[] { entityId });
        }
        request.setAttribute("merchant", merchant);
        return new ModelAndView("wsbs/departServiceItem/merchantInfo");
    }
    
    /**
     * 
     * 描述    保存商户
     * @author Danto Huang
     * @created 2018年9月20日 下午2:48:10
     * @param request
     * @return
     */
    @RequestMapping(params="saveOrUpdateMerchantInfo")
    @ResponseBody
    public AjaxJson saveOrUpdateMerchantInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = departServiceItemService.saveOrUpdate(variables, "T_SERVICEITEM_MERCHANT", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 商户记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的商户记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    删除商户
     * @author Danto Huang
     * @created 2018年9月20日 下午2:54:46
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDelMerchant")
    @ResponseBody
    public AjaxJson multiDelMerchant(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        departServiceItemService.remove("T_SERVICEITEM_MERCHANT","RECORD_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的 商户记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    收件时限
     * @author Danto Huang
     * @created 2018年9月20日 下午3:34:28
     * @param request
     * @return
     */
    @RequestMapping(params="receivingTime")
    public ModelAndView receivingTime(HttpServletRequest request){
        return new ModelAndView("wsbs/departServiceItem/receivingTimeView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月20日 下午4:02:46
     * @param request
     * @return
     */
    @RequestMapping(params="receivingTimeInfo")
    public ModelAndView receivingTimeInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> itemInfo = departServiceItemService.getByJdbc("T_WSBS_SERVICEITEM",
                new String[] { "ITEM_ID" }, new Object[] { entityId });
        request.setAttribute("itemInfo", itemInfo);
        return new ModelAndView("wsbs/departServiceItem/receivingTimeInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年9月20日 下午4:17:51
     * @param request
     * @return
     */
    @RequestMapping(params="updateReceivingTimeInfo")
    @ResponseBody
    public AjaxJson updateReceivingTimeInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ITEM_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        departServiceItemService.saveOrUpdate(variables, "T_WSBS_SERVICEITEM", entityId);
        sysLogService.saveLog("修改了ID为[" + entityId + "]的 服务事项的收件天数", SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 描述    历史版本列表
     * @author Danto Huang
     * @created 2018年11月9日 下午3:55:59
     * @param request
     * @return
     */
    @RequestMapping(params="itemHisView")
    public ModelAndView itemHisView(HttpServletRequest request){
        return new ModelAndView("wsbs/itemhis/itemHisView");
    }
    
    /**
     * 
     * 描述    历史版本数据列表
     * @author Danto Huang
     * @created 2018年11月9日 下午3:57:36
     * @param request
     * @param response
     */
    @RequestMapping(params="hisDataGrid")
    public void hisDataGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = departServiceItemService.findItemHisBySqlfilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    历史版本详细信息
     * @author Danto Huang
     * @created 2018年11月9日 下午4:53:31
     * @param request
     * @return
     */
    @RequestMapping(params="hisItemInfo")
    public ModelAndView hisItemInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        String version  = request.getParameter("version");
        Map<String, Object> serviceItem = null;
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            serviceItem = departServiceItemService.getByJdbc("T_WSBS_ITEM_HIS", new String[] { "ITEM_ID", "C_VERSION" },
                    new Object[] { entityId, version });
            // 获取所属目录
            String catalogCode = (String) serviceItem.get("CATALOG_CODE");
            if (StringUtils.isNotEmpty(catalogCode)) {
                Map<String, Object> catalog = catalogService.getCatalogByCatalogCode(catalogCode);
                serviceItem.put("CATALOG_NAME", (String) catalog.get("CATALOG_NAME"));
            }
            Map<String, Object> userInfo = departServiceItemService.getHisBindUserIdANdNames(entityId,version);
            serviceItem.put("PREAUDITER_IDS", userInfo.get("USER_IDS"));
            serviceItem.put("PREAUDITER_NAMES", userInfo.get("FULL_NAMES"));
            // 获取绑定的流程定义ID
            String defId = (String) serviceItem.get("BDLCDYID");
            if (StringUtils.isNotEmpty(defId)) {
                serviceItem.put("BDLCDYNAME", flowDefService.getDefNames(defId));
            }
            
            String ssbmbm = (String) serviceItem.get("SSBMBM");
            Map<String,Object> depart = departServiceItemService .getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                            new String[] { "DEPART_CODE" }, new Object[] { ssbmbm });
            serviceItem.put("DEPART_ID", depart.get("DEPART_ID"));
            serviceItem.put("IMPL_DEPART", depart.get("DEPART_NAME"));
            
            Map<String, Object> right = departServiceItemService.getByJdbc("T_SMOGA_BILLOFRIGHTS",
                    new String[] { "RIGHT_ID" }, new Object[] { serviceItem.get("RIGHT_ID") });
            if(right!=null){
                serviceItem.put("RIGHT_NAME", right.get("RIGHT_NAME"));
                serviceItem.put("SUBITEM_NAME", right.get("SUBITEM_NAME"));
            }
            
            Map<String, Object> agency = departServiceItemService.getByJdbc("T_SMOGA_AGENCYSERVICE",
                    new String[] { "SERVICE_ID" }, new Object[] { serviceItem.get("AGENCYSERVICE_ID") });
            if(agency!=null){
                serviceItem.put("AGENCYSERVICE_NAME", agency.get("AGENCY_ITEM_NAME"));
            }
        } else {
            serviceItem = new HashMap<String, Object>();
            serviceItem.put("SFSF", "0");
        }        
        
        request.setAttribute("serviceItem", serviceItem);
        request.setAttribute("fileFlag", request.getParameter("fileFlag"));
        return new ModelAndView("wsbs/itemhis/hisServiceitemInfo");
    }
    /**
     * 
     * 描述    特殊环节(历史版本)
     * @author Danto Huang
     * @created 2018年11月12日 下午4:42:28
     * @param request
     * @param response
     */
    @RequestMapping(params="hisSpecialLinkData")
    public void hisSpecialLinkData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.CREATE_TIME", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_t.C_VERSION_=",version);
        filter.addFilter("Q_t.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = departServiceItemService.getSpecialLinkHis(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述    材料列表（历史版本）
     * @author Danto Huang
     * @created 2018年11月12日 下午4:46:04
     * @param request
     * @param response
     */
    @RequestMapping(params = "hisforItemDatas")
    public void forItemDatas(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("SM.MATER_SN","asc");
        //获取服务事项ID
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_SM.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_SM.C_VERSION_=",version);
        filter.addFilter("Q_SM.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = departServiceItemService.getMatterHis(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    收费明细信息（历史版本）
     * @author Danto Huang
     * @created 2018年11月13日 上午9:59:46
     * @param request
     * @param response
     */
    @RequestMapping(params="hisChargeData")
    public void hisChargeData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.CREATE_TIME", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_t.C_VERSION_=",version);
        filter.addFilter("Q_t.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = departServiceItemService.getChargeDataHis(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述    自查条件列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:07:44
     * @param request
     * @param response
     */
    @RequestMapping(params="hisSelfExamData")
    public void hisSelfExamData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.EXAM_SN", "asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_t.C_VERSION_=",version);
        filter.addFilter("Q_t.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = departServiceItemService.getSelfExamDataHis(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述    流程环节审核人列表(历史版本)
     * @author Danto Huang
     * @created 2018年11月13日 上午10:24:58
     * @param request
     * @param response
     */
    @RequestMapping(params="hisDefNodeData")
    public void hisDefNodeData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.NODE_KEY","asc");
        String itemId = request.getParameter("itemId");
        filter.addFilter("Q_t.ITEM_ID_=",itemId);
        String version = request.getParameter("version");
        filter.addFilter("Q_t.C_VERSION_=",version);
        filter.addFilter("Q_t.EFFECT_STATUS_=","1");
        List<Map<String, Object>> list = departServiceItemService.getDefNodeHis(filter);
        List<Map<String, Object>> resultList =  new ArrayList<Map<String,Object>>();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Map<String, Object> map = (Map<String, Object>) iterator.next();
            String nodeName = map.get("NODE_NAME")==null?
                    "":map.get("NODE_NAME").toString();
            if ("开始".equals(nodeName)
                    ||"结束".equals(nodeName)
                    ||"待受理".equals(nodeName)
                    ||"网上预审".equals(nodeName)
                    ||"受理自动跳转".equals(nodeName)) {
                
            }else{
                resultList.add(map);
            }
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), resultList,
                null, JsonUtil.EXCLUDE, response);
    }
    
    
    /**
     * 
     * 描述： 一窗通办事项配置选择器
     * @author Rider Chen
     * @created 2019年3月12日 上午11:08:36
     * @param request
     * @return
     */
    @RequestMapping(params="yctbSelector")
    public ModelAndView yctbSelector(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");
        String itemCodes = request.getParameter("itemCodes");
        String businessCode = request.getParameter("businessCode");
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("businessCode", businessCode);
        return new ModelAndView("wsbs/departServiceItem/yctbItemSelector");
    }

    /**
     *
     * 描述： 一窗通办事项配置选择器
     * @author Rider Chen
     * @created 2019年3月12日 上午11:08:36
     * @param request
     * @return
     */
    @RequestMapping(params="stampSelector")
    public ModelAndView stampSelector(HttpServletRequest request){
        String allowCount = request.getParameter("allowCount");
        String itemCodes = request.getParameter("itemCodes");
        String stampId = request.getParameter("stampId");
        String itemCodeLimit = request.getParameter("itemCodeLimit");
        request.setAttribute("allowCount", allowCount);
        request.setAttribute("itemCodes", itemCodes);
        request.setAttribute("stampId", stampId);
        request.setAttribute("itemCodeLimit", itemCodeLimit);
        return new ModelAndView("wsbs/departServiceItem/stampItemSelector");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2018年8月8日 下午4:19:53
     * @param request
     * @param response
     */
    @RequestMapping(params = "yctbSelected")
    public void yctbSelected(HttpServletRequest request,
            HttpServletResponse response) {
        String businessCode = request.getParameter("businessCode");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(businessCode)){
            list = departServiceItemService.findByBusinessCode(businessCode);
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }

    /**
     *
     * 描述
     * @author Danto Huang
     * @created 2018年8月8日 下午4:19:53
     * @param request
     * @param response
     */
    @RequestMapping(params = "stampSelected")
    public void stampSelected(HttpServletRequest request,
                             HttpServletResponse response) {
        String stampId = request.getParameter("stampId");
        String itemCodeLimit = request.getParameter("itemCodeLimit");
        List<Map<String,Object>> list = null;
        if (StringUtils.isNotEmpty(itemCodeLimit)) {
            list = departServiceItemService.findByItemCodes(itemCodeLimit);
        } else {
            if(StringUtils.isNotEmpty(stampId)){
                list = departServiceItemService.findByStampId(stampId);
            }
        }
        if(list!=null){
            this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
        }
    }

    
    /**
     * 
     * 描述：一窗通办事项业务绑定
     * @author Rider Chen
     * @created 2019年3月12日 下午2:28:25
     * @param request
     * @return
     */
    @RequestMapping(params = "updateItembusinessCode")
    @ResponseBody
    public AjaxJson updateItembusinessCode(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String businessCode = request.getParameter("businessCode");
        departServiceItemService.updateItemCodes(selectColNames, businessCode);
        sysLogService.saveLog("修改了业务编码为[" + businessCode + "]的事项绑定记录", SysLogService.OPERATE_TYPE_UPLOAD);
        j.setMsg("绑定成功");
        j.setSuccess(true);
        return j;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/19 11:05:00
     * @param
     * @return
     */
    private String itemLogNote(String columnName,String oldColumn,String newColumn){
        StringBuffer str = new StringBuffer();
        str.append("【").append(columnName).append("】:'").append(oldColumn)
                .append("'修改为'").append(newColumn).append("';");
        return str.toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/2/19 11:51:00
     * @param
     * @return
     */
    private String itemLogNote(String oldColumn,String newColumn){
        StringBuffer str = new StringBuffer();
        str.append(oldColumn).append("'修改为'").append(newColumn).append("';");
        return str.toString();
    }

    /**
     * 
     * 描述    事项划转
     * @author Danto Huang
     * @created 2021年5月18日 下午3:24:20
     * @param request
     * @return
     */
    @RequestMapping(params="transferView")
    public ModelAndView transferView(HttpServletRequest request){
        String itemIds = request.getParameter("itemIds");
        request.setAttribute("itemIds", itemIds);
        return new ModelAndView("wsbs/departServiceItem/transferView");
    }
    
    /**
     * 
     * 描述    待划转数据
     * @author Danto Huang
     * @created 2021年5月18日 下午3:41:38
     * @param request
     * @param response
     */
    @RequestMapping(params="selectedForTransferData")
    public void selectedForTransferData(HttpServletRequest request,HttpServletResponse response){
        String itemIds = request.getParameter("itemIds");
        List<Map<String,Object>> list = departServiceItemService.selectedForTransferData(itemIds);

        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    划转
     * @author Danto Huang
     * @created 2021年5月19日 上午10:45:30
     * @param request
     * @return
     */
    @RequestMapping(params="doTransfer")
    @ResponseBody
    public AjaxJson doTransfer(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        departServiceItemService.doTransfer(request);
        j.setMsg("操作成功");
        return j;
    }
}
