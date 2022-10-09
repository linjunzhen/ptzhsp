/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.ems.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.ems.service.EmsService;
import net.evecom.platform.ems.util.EmsSend;
import net.evecom.platform.ems.util.HttpUtil;
import net.evecom.platform.hflow.dao.FlowEventDao;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 描述
 * @author Danto Huang
 * @created 2016-1-13 上午11:31:28
 */
@Controller
@RequestMapping("/emsController")
public class EmsController extends BaseController {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(EmsController.class);
    /**
     * 
     */
    @Resource
    private EmsService emsService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 所引入的dao
     */
    @Resource
    private FlowEventDao dao;
    
//    http://localhost:8087/ptzhsp/emsController/emsRecInfo.do     
    /**
     *
     * 删除VIP信息
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping("/emsRecInfo")
    @ResponseBody
    public AjaxJson emsRecInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String txLogisticID = request.getParameter("txLogisticID");
        String status = request.getParameter("status");
        String mailNum = request.getParameter("mailNum");
        String desc = request.getParameter("desc");

        StringBuffer sql = new StringBuffer("update T_BSFW_EMSINFO T set ");
        sql.append(" t.status = '").append(status).append("', ");
        sql.append(" t.mailNum = '").append(mailNum).append("', ");
        sql.append(" t.des = '").append(desc).append("' ");
        sql.append(" where t.txlogisticid = '").append(txLogisticID).append("' ");
        
        dao.executeSql(sql.toString(), null);
        
        j.setMsg("接收反馈信息成功");
        return j;
    }
    
    /**
     * 
     * 描述    跳转揽件服务管理
     * @author Danto Huang
     * @created 2020年2月11日 下午3:57:35
     * @param request
     * @return
     */
    @RequestMapping(params="packageServiceView")
    public ModelAndView packageServiceView(HttpServletRequest request){
        return new ModelAndView("ems/packageServiceView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月11日 下午4:00:12
     * @param request
     * @param response
     */
    @RequestMapping(params="packageServiceGrid")
    public void packageServiceGrid(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.CREATE_TIME", "desc");
        List<Map<String,Object>> list = emsService.findBySqlfilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    通知揽件
     * @author Danto Huang
     * @created 2020年2月16日 上午11:11:30
     * @param request
     * @return
     */
    @RequestMapping(params="noticeToPackage")
    @ResponseBody
    public AjaxJson noticeToPackage(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        Map<String,Object> record = new HashMap<String, Object>();
        record.put("MAIL_STATUS", 1);
        emsService.saveOrUpdate(record, "JBPM6_FLOW_EMSINFO", entityId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    跳转揽件窗口
     * @author Danto Huang
     * @created 2020年2月16日 下午12:09:38
     * @param request
     * @return
     */
    @RequestMapping(params="packageInfo")
    public ModelAndView packageInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> packageInfo = emsService.getByJdbc("JBPM6_FLOW_EMSINFO", new String[] { "RECORD_ID" },
                new Object[] { entityId });
        request.setAttribute("packageInfo", packageInfo);
        return new ModelAndView("ems/packageInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年2月16日 下午2:36:27
     * @param request
     * @return
     */
    @RequestMapping(params="savePackageInfo")
    @ResponseBody
    public AjaxJson savePackageInfo(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String,Object> variables = BeanUtil.getMapFromRequest(request);
        variables.put("MAIL_STATUS", 2);
        emsService.saveOrUpdate(variables, "JBPM6_FLOW_EMSINFO", entityId);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 
     * 描述    物流跟踪
     * @author Danto Huang
     * @created 2020年2月16日 下午2:49:16
     * @param request
     * @return
     */
    @RequestMapping(params="trackingInfo")
    public ModelAndView trackingInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String,Object> emsInfo = emsService
                .getByJdbc("JBPM6_FLOW_EMSINFO", new String[] { "RECORD_ID" }, new Object[] { entityId });
        String mailNo = (String) emsInfo.get("MAIL_NO");
        //String mailNo = "1199830687479";
        Map<String,String> params = new HashMap<String, String>();
        params.put("mailNo", mailNo);
        Map<String,Object> result = EmsSend.tracesQuery(params);
        if((boolean) result.get("success")){
            if(result.get("tracelist")==null){
                request.setAttribute("success", "F");
                request.setAttribute("errorMsg", "暂无物流信息");
            }else{
                request.setAttribute("success", "T");
                request.setAttribute("tracelist", result.get("tracelist"));
            }
        }else{
            request.setAttribute("success", "F");
            request.setAttribute("errorMsg", result.get("errorMsg"));
        }
        /*String url = "http://211.156.193.140:8000/cotrackapi/api/track/mail/"+mailNo;
        Map<String,String> header = new HashMap<String, String>();
        header.put("version", "ems_track_cn_3.0");
        header.put("authenticate", dictionaryService.getDicCode("EMSDJCS", "authorization"));
        try {
            String respContent = HttpUtil.sendHttpGet(url, "UTF-8", header, null);
            //respContent = FileUtil.getContentOfFile("d:/ems.json");
            System.out.println("返回物流信息："+respContent);
            if(respContent!=null&&respContent.length()>0){
                Map<String, Object> resultMap = (Map)JSON.parse(respContent);
                if(resultMap.get("traces")!=null){
                    JSONArray traces = (JSONArray) resultMap.get("traces");
                    List<Map> tracelist = JSONObject.parseArray(traces.toJSONString(), Map.class);
                    request.setAttribute("tracelist", tracelist);
                }else{
                    request.setAttribute("error", "1");
                    String msg = "获取物流信息接口异常";
                    if(resultMap.get("error")!=null){
                        msg += resultMap.get("error");
                    }
                    request.setAttribute("errorMsg", msg);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }*/
        request.setAttribute("mailNo", mailNo);
        request.setAttribute("EXE_ID", emsInfo.get("EXE_ID"));
        return new ModelAndView("ems/trackingInfo");
    }
    
    /**
     * 
     * 描述    退单
     * @author Danto Huang
     * @created 2020年2月16日 下午4:22:43
     * @param request
     * @return
     */
    @RequestMapping(params="cancelEms")
    @ResponseBody
    public AjaxJson cancelEms(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("entityId");
        Map<String,Object> emsInfo = emsService
                .getByJdbc("JBPM6_FLOW_EMSINFO", new String[] { "RECORD_ID" }, new Object[] { entityId });
        Map<String,String> map = new HashMap<String, String>();
        map.put("txLogisticID", (String) emsInfo.get("LOGISTICS_ID"));
        map.put("cancelCode", "28");
        Map<String,Object> result = EmsSend.emsCancel(map);
        if((boolean) result.get("success")){
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("MAIL_STATUS", 3);
            emsService.saveOrUpdate(variables, "JBPM6_FLOW_EMSINFO", entityId);
            j.setSuccess(true);
            j.setMsg("退单成功");
        }else{
            j.setSuccess(false);
            j.setMsg((String) result.get("errorMsg"));
        }
        return j;
    }

    /**
     * 描述:转发ems请求
     *
     * @author Madison You
     * @created 2020/3/11 11:24:00
     * @param
     * @return
     */
    @RequestMapping("/sendEmsHttpForHlw")
    @ResponseBody
    public Map<String, Object> sendEmsHttpForHlw(HttpServletRequest request, HttpServletResponse response) {
        String path = dictionaryService.getDicCode("EMSDJCS", "path");
        String result = null;
        Map<String, Object> returnMap = null;
        Map<String, Object> map = BeanUtil.getMapFromRequest(request);
        HashMap<String, String> headerMap = new HashMap<>();
        try{
            result = HttpSendUtil.sendPostParamsH(path, headerMap, map);
        } catch (Exception e) {
            log.error("ems转发请求失败");
        }
        if (result != null) {
            returnMap = JSONObject.parseObject(result);
        }
        return returnMap;
    }

    /**
     * 描述:外网预审入口
     *
     * @author Madison You
     * @created 2020/3/12 16:58:00
     * @param
     * @return
     */
    @RequestMapping(params = "wwysrkView")
    public ModelAndView wwysrkView(HttpServletRequest request) {
        return new ModelAndView("ems/wwysrkView");
    }

}
