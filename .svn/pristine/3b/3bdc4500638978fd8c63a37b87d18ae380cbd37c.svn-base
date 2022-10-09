/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.fjfda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.fjfda.service.FjfdaTransService;

/**
 * 描述 食药监业务数据上报功能查询
 * 
 * @author Keravon Feng
 * @created 2019年2月26日 上午10:13:02
 */
@Controller
@RequestMapping("/fjfdaTransController")
public class FjfdaTransController extends BaseController {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(FjfdaTransController.class);
    
    /**
     * fjfdaTransService
     */
    @Resource
    private FjfdaTransService fjfdaTransService;
    
    /**
     * 描述 跳转至列表页
     * @author Keravon Feng
     * @created 2019年2月26日 下午2:17:12
     * @param request
     * @return
     */
    @RequestMapping(params = "transView")
    public ModelAndView transView(HttpServletRequest request) {
        return new ModelAndView("fjfda/trans/transList");
    }
    
    /**
     * 描述 接口查看当前业务省平台的审批记录
     * @author Keravon Feng
     * @created 2019年3月4日 下午3:38:10
     * @param request
     * @return
     */
    @RequestMapping(params = "auditInfo")
    public ModelAndView auditInfo(HttpServletRequest request) {
        String fjfdaeveId = request.getParameter("fjfdaeveId");
        request.setAttribute("fjfdaeveId", fjfdaeveId); 
        return new ModelAndView("fjfda/trans/showAuditInfo");
    }
    
    /**
     * 描述
     * @author Keravon Feng
     * @created 2019年2月28日 上午9:51:38
     * @param request
     * @return
     */
    @RequestMapping(params = "transInfo")
    public ModelAndView transInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(entityId != null){
            Map<String,Object> entity = fjfdaTransService.getByJdbc("T_FJFDA_TRANS", 
                    new String[]{"YW_ID"}, new Object[]{entityId});
            if(entity != null && entity.get("BUS_RECORDID")!= null){
                Map<String,Object> dataJson = fjfdaTransService.getByJdbc("T_FJFDA_TRANS_DATA", 
                        new String[]{"BUS_RECORDID"}, 
                        new Object[]{String.valueOf(entity.get("BUS_RECORDID"))});
                if(dataJson != null){
                    entity.put("data", dataJson.get("DATA_JSON"));
                }
            }
            request.setAttribute("entity", entity);
        }
        return new ModelAndView("fjfda/trans/transInfo");
    }

    /**
     * 表格数据列表
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = fjfdaTransService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 上报接口
     * @author Keravon Feng
     * @created 2019年2月28日 上午10:18:05
     * @param request
     * @return
     */
    @RequestMapping(params = "submitUpload")
    @ResponseBody
    public AjaxJson submitUpload(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String yw_Id = request.getParameter("YW_ID");
        Map<String,Object> map = fjfdaTransService.getByJdbc("T_FJFDA_TRANS", 
                new String[]{"YW_ID"}, new Object[]{yw_Id});
        if(map != null){
            if(!"1".equals(String.valueOf(map.get("TRANS_STATE")))){ 
                Map<String,Object> result =  fjfdaTransService.createItemRemote(yw_Id);
                if(result != null && "true".equals(String.valueOf(result.get("success")))){
                    j.setMsg(String.valueOf(result.get("msg")));
                }else{
                    j.setMsg(String.valueOf(result.get("msg")));
                    j.setSuccess(false); 
                }
            }else{
                j.setMsg("已报送成功禁止重新报送。");
            }
        }        
        return j;
    }
    
    /**
     * 描述  批量重报数据
     * @author Keravon Feng
     * @created 2019年2月28日 上午11:07:20
     * @param request
     * @return
     */
    @RequestMapping(params = "multiSubmitUpload")
    @ResponseBody
    public AjaxJson multiSubmitUpload(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ywIds = request.getParameter("entityIds");
        if(ywIds != null && StringUtils.isNotEmpty(ywIds)){ 
            String[] yw_ids = ywIds.split(",");
            for(String yw_Id : yw_ids){
                Map<String,Object> map = fjfdaTransService.getByJdbc("T_FJFDA_TRANS", 
                        new String[]{"YW_ID"}, new Object[]{yw_Id});
                if(map != null){
                    if(!"1".equals(String.valueOf(map.get("TRANS_STATE")))){ 
                        Map<String,Object> result =  fjfdaTransService.createItemRemote(yw_Id);
                    }
                }
            }
        }
        j.setMsg("操作成功!");
        return j;
    }
    
    /**
     * 描述  
     * @author Keravon Feng
     * @created 2019年3月11日 下午4:47:37
     * @param request
     * @return
     */
    @RequestMapping(params = "mutilUpdateStatus")
    @ResponseBody
    public AjaxJson mutilUpdateStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ywIds = request.getParameter("entityIds");
        if(ywIds != null && StringUtils.isNotEmpty(ywIds)){ 
            String[] yw_ids = ywIds.split(",");
            for(String yw_Id : yw_ids){
                Map<String,Object> map = fjfdaTransService.getByJdbc("T_FJFDA_TRANS", 
                        new String[]{"YW_ID"}, new Object[]{yw_Id});
                if(map != null){
                    if("1".equals(String.valueOf(map.get("TRANS_STATE")))){ 
                        Map<String,Object> result =  fjfdaTransService.updateFlowRunStatus(map);
                    }
                }
            }
        }
        j.setMsg("操作成功!");
        return j;
    }
    
    /**
     * 描述 对未传递成功的附件重新报送
     * @author Keravon Feng
     * @created 2019年2月28日 上午11:30:22
     * @param request
     * @return
     */
    @RequestMapping(params = "multiMatersUpload")
    @ResponseBody
    public AjaxJson multiMatersUpload(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ywIds = request.getParameter("entityIds");
        if(ywIds != null && StringUtils.isNotEmpty(ywIds)){ 
            String[] yw_ids = ywIds.split(",");
            for(String yw_Id : yw_ids){
                fjfdaTransService.matersReUpload(yw_Id);
            }
        }
        j.setMsg("操作成功!");
        return j;
    }
    
    /**
     * 描述 查询食药监业务审批记录
     * @author Keravon Feng
     * @created 2019年3月4日 下午3:55:09
     * @param request
     * @param response
     */
    @RequestMapping(params = "auditDataGrid")
    public void auditDataGrid(HttpServletRequest request, HttpServletResponse response) {
        String fjfdaeveId = request.getParameter("fjfdaeveId");
        //调用接口获取当前业务的流程状态
        Map<String,Object> result = fjfdaTransService.getStatusByEveId(fjfdaeveId);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        if(result != null && "true".equals(String.valueOf(result.get("result")))){
            list = (List<Map<String, Object>>) result.get("audit");
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    
}
