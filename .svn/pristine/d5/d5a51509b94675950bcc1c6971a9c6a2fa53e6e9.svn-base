/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.website.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.website.service.XFDesignService;
/**
 * 描述 消防设计控制层
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年12月9日 上午11:23:10
 */
@Controller
@RequestMapping("/xfDesignController")
public class XFDesignController extends BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WebSiteController.class);
    /**
     * xfDesignService
     */
    @Resource
    private XFDesignService xfDesignService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * easyui 责任主体列表信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "findZrztxxList")
    public void findZrztxxList(HttpServletRequest request, HttpServletResponse response) {
        String prj_code = request.getParameter("prj_code");
        String prj_num = request.getParameter("prj_num");
        if(prj_num == null || "".equals(prj_num)) {
            //prj_num = xfDesignService.getPrjNum(prj_code);
            prj_num="";
        }
        /*if(prj_num != null && !"".equals(prj_num)) {
            list = xfDesignService.findZrztxxList(prj_code, prj_num);
        }*/

        List<Map<String, Object>> list = xfDesignService.findZrztxxList(prj_code, prj_num);
        request.setAttribute("PRJ_NUM", prj_num);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui 单体建筑物列表信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "findDtjzwList")
    public void findDtjzwList(HttpServletRequest request, HttpServletResponse response) {
        String prj_code = request.getParameter("prj_code");
        String prj_num = request.getParameter("prj_num");
        //List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(prj_num == null || "".equals(prj_num)) {
            //prj_num = xfDesignService.getPrjNum(prj_code);
            prj_num = "";
        }
        /*if(prj_num != null && !"".equals(prj_num)) {
            list = xfDesignService.findDtjzwxxList(prj_code, prj_num);
        }*/
        List<Map<String, Object>> list = xfDesignService.findDtjzwxxList(prj_code, prj_num);
        request.setAttribute("PRJ_NUM", prj_num);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelZrzt")
    @ResponseBody
    public AjaxJson multiDelZrzt(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        xfDesignService.remove("TB_FC_PROJECT_CORP_INFO", "FC_CORP_INFO_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 方法del
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDelDtjzw")
    @ResponseBody
    public AjaxJson multiDelDtjzw(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        xfDesignService.remove("TB_FC_UNIT_PROJECT_INFO", "FC_UNIT_INFO_ID", selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的单体建筑物信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 责任主体弹窗信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zrztInfo")
    @SuppressWarnings("unchecked")
    public ModelAndView zrztInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String type =  request.getParameter("type");
        String prj_num =  request.getParameter("prj_num");
        String prj_code =  request.getParameter("prj_code");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // 责任主体信息
            Map<String, Object> zrztxxInfo = xfDesignService.getByJdbc("TB_FC_PROJECT_CORP_INFO",
                    new String[] { "FC_CORP_INFO_ID" }, new Object[] { entityId });
            if (zrztxxInfo != null && zrztxxInfo.size() > 0) {
                request.setAttribute("zrztxxValue", true);
                request.setAttribute("zrztxxInfo", zrztxxInfo);
            }
        }
        request.setAttribute("prj_num", prj_num);
        request.setAttribute("prj_code", prj_code);
        //后台
        if(type!=null && "back".equals(type)) {
            return new ModelAndView("bsdt/applyform/xfsj/zrztxxWindow");
        }
        return new ModelAndView("website/applyforms/xfsj/zrztxxWindow");
    }
    
    /**
     * 单体建筑物弹窗信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "dtjzwInfo")
    @SuppressWarnings("unchecked")
    public ModelAndView dtjzwInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String type =  request.getParameter("type");
        String prj_num =  request.getParameter("prj_num");
        String prj_code =  request.getParameter("prj_code");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            // 责任主体信息
            Map<String, Object> dtjzwxxInfo = xfDesignService.getByJdbc("TB_FC_UNIT_PROJECT_INFO",
                    new String[] { "FC_UNIT_INFO_ID" }, new Object[] { entityId });
            if (dtjzwxxInfo != null && dtjzwxxInfo.size() > 0) {
                request.setAttribute("dtjzwxxValue", true);
                request.setAttribute("dtjzwxxInfo", dtjzwxxInfo);
            }
        }
        request.setAttribute("prj_num", prj_num);
        request.setAttribute("prj_code", prj_code);
        //后台
        if(type!=null && "back".equals(type)) {
            return new ModelAndView("bsdt/applyform/xfsj/dtjzwxxWindow");
        }
        return new ModelAndView("website/applyforms/xfsj/dtjzwxxWindow");
    }
    
    /**
     * 修改或者修改操作(责任主体)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateZrzt")
    @ResponseBody
    public AjaxJson saveOrUpdateZrzt(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FC_CORP_INFO_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = xfDesignService.saveOrUpdate(variables, "TB_FC_PROJECT_CORP_INFO", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的责任主体信息记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 修改或者修改操作(单体建筑物)
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateDtjzw")
    @ResponseBody
    public AjaxJson saveOrUpdateDtjzw(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("FC_UNIT_INFO_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = xfDesignService.saveOrUpdate(variables, "TB_FC_UNIT_PROJECT_INFO", entityId);
       
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的单体建筑物信息记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的单体建筑物信息记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 消防设计事项列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "xfPrjectInfo")
    public void xfPrjectInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String projectCode = request.getParameter("prjCode");
        String prjNum = request.getParameter("prjNum");
        if (projectCode != null && !"".equals(projectCode)) {
            Map<String, Object> xfPrjectMap = xfDesignService.findXfProjectInfo(projectCode, prjNum);
            if (xfPrjectMap != null && xfPrjectMap.size() > 0) {
                if(xfPrjectMap!=null && xfPrjectMap.size()>0) {
                    if(xfPrjectMap.get("SBMC")!=null) {
                        xfPrjectMap.remove("SBMC");
                    }
                }
            }
            result.put("success", true);
            result.put("data", xfPrjectMap);
        } else {
            result.put("success", false);
            result.put("msg", "调用诚信所接口参数不能为空。");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

}
