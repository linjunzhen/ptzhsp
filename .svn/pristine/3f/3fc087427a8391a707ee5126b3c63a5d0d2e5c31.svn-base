/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.utility.DateUtil;
import net.evecom.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQlcSffzInfoService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述 不动产收费发证打证后台流程相关方法
 * @author Keravon Feng
 * @created 2019年12月17日 下午2:37:28
 */
@Controller
@RequestMapping("/bdcQlcSffzInfoController")
public class BdcQlcSffzInfoController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcQlcSffzInfoController.class);
    
    /**
     * bdcQlcSffzInfoService
     */
    @Resource
    private BdcQlcSffzInfoService bdcQlcSffzInfoService;
    
    /**
     * 描述 填写权证标识码打证列表
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:48:04
     * @param request
     * @return
     */
    @RequestMapping(params = "qzprintList")
    public ModelAndView qzprintList(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/qzbsm/List");
    }
    
    /**
     * 
     * 描述 我处理过的证件列表
     * @author Keravon Feng
     * @created 2019年12月20日 下午3:39:04
     * @param request
     * @return
     */
    @RequestMapping(params = "myqzprintList")
    public ModelAndView myqzprintList(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/qzbsm/myList");
    }

    /**
     * 描述:办件进度查询列表
     *
     * @author Madison You
     * @created 2020/6/12 9:57:00
     * @param
     * @return
     */
    @RequestMapping(params = "scheduleQueryList")
    public ModelAndView scheduleQueryList(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("bdcqlc/qzbsm/scheduleQueryList");
    }
    
    /**
     * 
     * 描述  权证打印tab列表
     * @author Keravon Feng
     * @created 2019年12月20日 下午2:48:46
     * @param request
     * @return
     */
    @RequestMapping(params = "qzprintListTab")
    public ModelAndView qzprintListTab(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/qzbsm/ListTab");
    }
    
    /**
     * 描述 获取权证标识码的列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:55:24
     * @param request
     * @param response
     */
    @RequestMapping(params = "qzprintdatagrid")
    public void qzprintdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcQlcSffzInfoService.findQzPrintListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 获取的打印证书数据
     * @author Keravon Feng
     * @created 2020年1月2日 上午11:15:19
     * @param request
     * @param response
     */
    @RequestMapping(params = "myqzprintdatagrid")
    public void myqzprintdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcQlcSffzInfoService.findMyQzPrintListBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:获取不动产全流程办件进度查询列表
     *
     * @author Madison You
     * @created 2020/6/12 10:16:00
     * @param
     * @return
     */
    @RequestMapping(params = "sheduleQueryDatagrid")
    public void sheduleQueryDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String,Object>> list = bdcQlcSffzInfoService.findSheduleQueryDatagrid(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 缴费发证列表
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:48:35
     * @param request
     * @return
     */
    @RequestMapping(params = "jffzList")
    public ModelAndView jffzList(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/jffz/jffzList");
    }
    
    /**
     * 描述 跳转我的发证类别
     * @author Keravon Feng
     * @created 2020年1月2日 下午2:23:57
     * @param request
     * @return
     */
    @RequestMapping(params = "myjffzList")
    public ModelAndView myjffzList(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/jffz/MyJffzList");
    }
    
    /**
     * 
     * 描述 
     * @author Keravon Feng
     * @created 2019年12月20日 下午2:49:59
     * @param request
     * @return
     */
    @RequestMapping(params = "jffzListTab")
    public ModelAndView jffzListTab(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/jffz/jffzListTab");
    }
    
    /**
     * 描述 获取缴费发证领证的列表数据
     * @author Keravon Feng
     * @created 2019年12月17日 下午2:56:02
     * @param request
     * @param response
     */
    @RequestMapping(params = "jffzListdatagrid")
    public void jffzListdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcQlcSffzInfoService.findJffzBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 我的发证
     * @author Keravon Feng
     * @created 2020年1月2日 下午2:27:10
     * @param request
     * @param response
     */
    @RequestMapping(params = "myJffzListdatagrid")
    public void myJffzListdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcQlcSffzInfoService.findMyJffzBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述
     * @author Keravon Feng
     * @created 2019年12月18日 下午3:47:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "saveBdcJffzinfo")
    @ResponseBody
    public void saveBdcJffzinfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            // 获取当前登录用户信息
            //SysUser sysUser = AppUtil.getLoginUser();
            //String exeId = (String) variables.get("EFLOW_EXEID");
            //String itemName = (String) variables.get("ITEM_NAME");
            //调用流程开始环节绑定的保存事件方法
            bdcQlcSffzInfoService.doSaveBdcJffzinfo(variables);
            variables.put("OPER_SUCCESS", true);
            String isTempSave = (String) variables.get("EFLOW_ISTEMPSAVE");
            if ("1".equals(isTempSave)) {
                variables.put("OPER_MSG", "操作成功");
            } else {
                variables.put("OPER_MSG", "提交成功");
            }
        } catch (Exception e) {
            variables.put("OPER_SUCCESS", false);
            variables.put("OPER_MSG", "提交失败,请联系系统管理员!");
            log.error("", e);
        }
        String json = JSON.toJSONString(variables);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 提交跳转页面
     * @author Keravon Feng
     * @created 2019年12月19日 下午4:57:12
     * @param request
     * @return
     */
    @RequestMapping(params = "qzprintaudit")
    public ModelAndView qzprintaudit(HttpServletRequest request) {
        String eveId =  request.getParameter("eveId");
        // 获取当前登录用户信息
        SysUser sysUser = AppUtil.getLoginUser();
        Map<String,Object> auditConfig = null;
        if(eveId != null && StringUtils.isNotEmpty(eveId)) {
            auditConfig = bdcQlcSffzInfoService.getByJdbc("T_BDCQLC_SFFZINFO",
                    new String[] {"EVEID"}, new Object[] {eveId});
            if(auditConfig != null) {
                auditConfig.put("CZR_USERNAME", sysUser.getFullname());
                auditConfig.put("CZR_USER", sysUser.getUserId());
                auditConfig.put("EVEID", eveId);
                String optype = String.valueOf(auditConfig.get("OPTYPE"));
                if("1".equals(optype)) {
                    //当前处于权证打证环节
                    auditConfig.put("CZ_HJMC", "权证打印");
                }else if("2".equals(optype)) {
                    //当前处于权证打证环节
                    auditConfig.put("CZ_HJMC", "缴费发证");
                }
            }
        }else {
            auditConfig = new HashMap<String,Object>();
            auditConfig.put("CZR_USERNAME", sysUser.getFullname());
            auditConfig.put("CZR_USER", sysUser.getUserId());
            auditConfig.put("EVEID", eveId);
        }
        request.setAttribute("auditConfig", auditConfig); 
        return new ModelAndView("bdcqlc/audit/qzprintaudit");
    }
    
    /**
     * 
     * 描述  后台打证与收费发证环节提交
     * @author Keravon Feng
     * @created 2019年12月20日 上午10:23:14
     * @param request
     * @param response
     */
    @RequestMapping(params = "saveBdcAudit")
    @ResponseBody
    public void saveBdcAudit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        try {
            //调用流程开始环节绑定的保存事件方法
            Map<String, Object> result = bdcQlcSffzInfoService.saveBdcAudit(variables);
            if("true".equals(String.valueOf(result.get("success")))) {
                retMap.put("success", true);
                retMap.put("msg", "提交成功");
            }else {
                retMap.put("success", false);
                retMap.put("msg", result.get("retMsg")); 
            }
        } catch (Exception e) {
            retMap.put("success", false);
            retMap.put("msg", "提交失败,请联系系统管理员!");
            log.error("", e);
        }
        String json = JSON.toJSONString(retMap);
        this.setJsonString(json, response);
    }

//    /**
//     * 描述:不动产全流程重新打证功能
//     *
//     * @author Madison You
//     * @created 2020/6/12 15:09:00
//     * @param
//     * @return
//     */
//    @RequestMapping(params = "bdcCqzsRePrint")
//    @ResponseBody
//    public void bdcCqzsRePrint(HttpServletRequest request) {
//        Map<String,Object> returnMap = new HashMap<>();
//        String exeId = request.getParameter("exeId");
//        if (StringUtils.isNotEmpty(exeId)) {
//            Map<String,Object> exeMap = bdcQlcSffzInfoService.getByJdbc("JBPM6_EXECUTION",
//                    new String[]{"EXE_ID"}, new Object[]{exeId});
//            String BUS_TABLENAME = (String) exeMap.get("BUS_TABLENAME");
//            String BUS_RECORDID = (String) exeMap.get("BUS_RECORDID");
//            String primaryKeyName = bdcQlcSffzInfoService.getPrimaryKeyName(BUS_TABLENAME).get(0).toString();
//            if (StringUtils.isNotEmpty(BUS_RECORDID) && StringUtils.isNotEmpty(primaryKeyName)) {
//                Map<String, Object> busMap = bdcQlcSffzInfoService.getByJdbc(BUS_TABLENAME,
//                        new String[]{primaryKeyName}, new Object[]{BUS_RECORDID});
//                if (busMap != null) {
//                    if (BUS_TABLENAME.equals("T_BDCQLC_GYJSJFWZYDJ")) {
//                        String POWERPEOPLEINFO_JSON = (String) busMap.get("POWERPEOPLEINFO_JSON");
////                        bdcGyjsjfwzyRePrint(exeMap,busMap,)
//                    }
//                }
//            }
//        }
//
//    }

    /**
     * 描述:保存不动产登记审核意见
     *
     * @author Madison You
     * @created 2020/6/12 17:57:00
     * @param
     * @return
     */
    @RequestMapping(params = "saveBdcDjshOpinion")
    @ResponseBody
    public Map<String, Object> saveBdcDjshOpinion(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        String busTableName = (String) variables.get("busTableName");
        //国有转移6个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String busRecordId = (String) variables.get("busRecordId");
        String CS_OPINION_CONTENT = (String) variables.get("CS_OPINION_CONTENT");
        String HZ_OPINION_CONTENT = (String) variables.get("HZ_OPINION_CONTENT");
        try{
            if (StringUtils.isNotEmpty(busTableName) && StringUtils.isNotEmpty(busRecordId)) {
                SysUser sysUser = AppUtil.getLoginUser();
                String userName = sysUser.getFullname();
                String userId = sysUser.getUserId();
                String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                if (StringUtils.isNotEmpty(CS_OPINION_CONTENT)) {
                    variables.put("CS_OPINION_NAME", userName);
                    variables.put("CS_OPINION_ID", userId);
                    variables.put("CS_OPINION_TIME", nowTime);
                    returnMap.put("CS_OPINION_NAME", userName);
                    returnMap.put("CS_OPINION_TIME", nowTime);
                    returnMap.put("TYPE", "1");
                }
                if (StringUtils.isNotEmpty(HZ_OPINION_CONTENT)) {
                    variables.put("HZ_OPINION_NAME", userName);
                    variables.put("HZ_OPINION_ID", userId);
                    variables.put("HZ_OPINION_TIME", nowTime);
                    returnMap.put("HZ_OPINION_NAME", userName);
                    returnMap.put("HZ_OPINION_TIME", nowTime);
                    returnMap.put("TYPE", "2");
                }
                bdcQlcSffzInfoService.saveOrUpdate(variables, busTableName, busRecordId);
                returnMap.put("success", true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return returnMap;
    }
    

    /**
     * 
     * 描述： 保存不动产首次登记核定意见
     * @author Rider Chen
     * @created 2020年8月18日 上午11:41:23
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveBdcscdjshxxOpinion")
    @ResponseBody
    public Map<String, Object> saveBdcscdjshxxOpinion(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        String busTableName = (String) variables.get("busTableName");
        String busRecordId = (String) variables.get("busRecordId");
        String DJSHXX_HZYJ = (String) variables.get("DJSHXX_HZYJ");
        try{
            if (StringUtils.isNotEmpty(busTableName) && StringUtils.isNotEmpty(busRecordId) && StringUtils.isNotEmpty(DJSHXX_HZYJ)) {
                SysUser sysUser = AppUtil.getLoginUser();
                String userName = sysUser.getFullname();
                String nowTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                variables.put("DJSHXX_SHR", userName);
                variables.put("DJSHXX_SHJSRQ", nowTime);
                returnMap.put("DJSHXX_SHR", userName);
                returnMap.put("DJSHXX_SHJSRQ", nowTime);
                bdcQlcSffzInfoService.saveOrUpdate(variables, busTableName, busRecordId);
                returnMap.put("success", true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return returnMap;
    }
}
