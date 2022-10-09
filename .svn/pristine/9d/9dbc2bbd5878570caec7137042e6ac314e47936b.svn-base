/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bsfw.service.CyjbPtService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysRoleService;

/**
 * 描述 产业奖补全流程
 * @author Keravon Feng
 * @created 2018年9月14日 下午5:34:31
 */
@Controller
@RequestMapping("/cyjbPtController")
public class CyjbPtController extends BaseController {
    
    /**
     * logger
     */
    private static Log log = LogFactory.getLog(CyjbPtController.class);
    
    /**
     * cyjbPtService
     */
    @Resource
    private CyjbPtService cyjbPtService;
    
    /**
     * sysRoleService
     */
    @Resource
    private SysRoleService sysRoleService;
    
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /** 
     * 描述 浏览器预览 产业奖补
     * @author Keravon Feng
     * @created 2018年9月13日 下午5:58:08
     * @param request
     * @return
     */
    @RequestMapping(params = "print")
    public ModelAndView preview(HttpServletRequest request) {
        //查询通过编号查询当前的材料对应的图片
        String busId = request.getParameter("busId");
        Map<String,Object> busRecord = cyjbPtService.getByJdbc("T_BSFW_CYJB", 
                new String[]{"CYJB_ID"}, new Object[]{busId});
        if(busRecord != null){
            String file_id = busRecord.get("ATTACH_FILEID") != null 
                    ? String.valueOf(busRecord.get("ATTACH_FILEID")) : null;
            if(file_id != null){
                Map<String,Object> fileInfo = fileAttachService.getFileAttachObject(file_id);
                if(fileInfo != null){
                    Properties projectProperties = FileUtil.readProperties("project.properties");
                    String uploadFileUrl = projectProperties.getProperty("uploadFileUrl").replace("\\", "/");
                    busRecord.put("attach_url", uploadFileUrl + fileInfo.get("FILE_PATH"));
                }
            }
            request.setAttribute("isShowJb", true);
            String hyzgPerson = StringUtil.getValue(busRecord, "HYZG_JB_PERSON");
            if (StringUtils.isNotEmpty(hyzgPerson)) {
                List<Map<String,Object>> list = cyjbPtService.getAllByJdbc("T_MSJW_SYSTEM_SYSUSER",
                        new String[]{"FULLNAME"}, new Object[]{hyzgPerson});
                if (list != null && !list.isEmpty()) {
                    for (Map<String, Object> map : list) {
                        if ("4028818c510dce9701510e1709910004".equals(map.get("DEPART_ID"))) {
                            request.setAttribute("isShowJb", false);
                        }
                    }
                }
            }
            request.setAttribute("busRecord", busRecord);
        }
        return new ModelAndView("bsdt/applyform/cyjb/print");
    }
    
    /**
     * 描述 人员角色选择器
     * @author Keravon Feng
     * @created 2018年9月14日 下午5:38:07
     * @param request
     * @return
     */
    @RequestMapping(params = "selectUserByRole")
    public ModelAndView selectUserByRole(HttpServletRequest request) {
        String flowSubmitInfoJson =  (String) AppUtil.getSession().getAttribute("flowSubmitInfoJson");
        String noAuth = "false";
        if(StringUtils.isNotEmpty(request.getParameter("noAuth"))){
            noAuth = request.getParameter("noAuth");
        }
        Map<String,Object> flowSubmitInfo = JSON.parseObject(flowSubmitInfoJson,Map.class);
        request.setAttribute("rolevars", flowSubmitInfo.get("EFLOW_SELECTORVARS"));
        request.setAttribute("noAuth",noAuth);
        request.setAttribute("allowCount","15");
        return new ModelAndView("hflow/handlerconfig/SysUserRolesSelector");
    }
    
    /**
     * 描述 copy by SysUserController datagrid
     * @author Keravon Feng
     * @created 2018年9月14日 下午5:52:54
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        filter.addSorted("T.USER_ID","desc");
        List<Map<String, Object>> list = cyjbPtService.findBySqlFilter(filter);
        for(Map<String,Object> user:list){
            String userId = (String) user.get("USER_ID");
            StringBuffer roleNames = new StringBuffer("");
            List<Map<String,Object>> roleList = sysRoleService.findByUserId(userId);
            for(int i =0;i<roleList.size();i++){
                Map<String,Object> role = roleList.get(i);
                if(i>0){
                    roleNames.append(",");
                }
                roleNames.append(role.get("ROLE_NAME"));
            }
            user.put("ROLE_NAMES", roleNames.toString());
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2018年10月9日 下午2:22:47
     * @param request
     * @return
     */
    @RequestMapping("/refreshPtCyjbDiv")
    public ModelAndView refreshTechnicalPersonnelDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("bsdt/applyform/refreshPtCyjbDiv");
    }
    
    /**
     * 描述 保存产业奖补信息
     * @author Keravon Feng
     * @created 2018年10月9日 下午3:37:15
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveCyjb")
    @ResponseBody
    public AjaxJson saveCyjb(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String busRecordId = request.getParameter("CYJB_ID");
        String CyjbJson = request.getParameter("CyjbJson");
        String CYJB_SPJE = request.getParameter("CYJB_SPJE");
        String CYJB_SPDEPT_NAME = request.getParameter("CYJB_SPDEPT_NAME");
        String CYJB_CZSJ = request.getParameter("CYJB_CZSJ");
        String CYJB_SPDEPT_ID = request.getParameter("CYJB_SPDEPT_ID");
        Map<String, Object> variables = cyjbPtService.getByJdbc("T_BSFW_CYJB", new String[] { "CYJB_ID" },
                new String[] { busRecordId });
        if(null!=variables&&variables.size()>0){
            variables.put("CYJB_SPJE", CYJB_SPJE);
            variables.put("CYJB_SPDEPT_NAME", CYJB_SPDEPT_NAME);
            variables.put("CYJB_CZSJ", CYJB_CZSJ);
            variables.put("CYJB_SPDEPT_ID", CYJB_SPDEPT_ID);            
            cyjbPtService.saveOrUpdate(variables, "T_BSFW_CYJB", busRecordId);
        }
        if (StringUtils.isNotEmpty(CyjbJson)) {
            // 保存信息前，先删除
            sysLogService.remove("T_BSFW_PTJ_CYJB", new String[] { "YW_ID" }, new Object[] { busRecordId });
            List<Map> cyjbList = JSON.parseArray(CyjbJson, Map.class);
            for (int i = 0; i < cyjbList.size(); i++) {
                Map<String, Object> productCpxxMap = cyjbList.get(i);
                productCpxxMap.put("YW_ID", busRecordId);
                cyjbPtService.saveOrUpdate(productCpxxMap, "T_BSFW_PTJ_CYJB", "");
            }
        }
        if (StringUtils.isNotEmpty(busRecordId)) {
            sysLogService.saveLog("修改了ID为[" + busRecordId + "]的 产业奖补记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + busRecordId + "]的 产业奖补记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
}
