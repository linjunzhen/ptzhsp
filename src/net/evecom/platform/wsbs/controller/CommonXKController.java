/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.service.CommonXKService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述：许可Controller
 * 
 * @author Water Guo
 * @created 2017-4-12 上午8:32:22
 */
@Controller
@RequestMapping("/commonXKController")
public class CommonXKController extends BaseController {
    /**
     * commonXKService
     */
    @Resource
    private CommonXKService commonXKService;

    /**
     * 
     * 描述：保存或删除许可
     * 
     * @author Water Guo
     * @created 2017-4-12 上午8:39:55
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveCommonXK")
    @ResponseBody
    public void saveOrUpdate(HttpServletRequest request, HttpServletResponse response) {
        //AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String xkfile_name = request.getParameter("xkfile_name");
        String entityId = request.getParameter("XK_ID");
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("USER_ID", sysUser.getUserId());
        variables.put("DEPART_ID", sysUser.getDepId());
        if (variables.get("xkfile_name") != null) {
            variables.put("xkfile_name", ((String) variables.get("xkfile_name")).trim());
        }
        if (variables.get("xkcontent") != null) {
            variables.put("xkcontent", ((String) variables.get("xkcontent")).trim());
        }
        String xkId = commonXKService.getXKID(variables);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(xkId)) {
            resultMap.put("success", false);
            resultMap.put("msg", "此条常用（许可）内容已存在");
        } else {
            xkId = this.commonXKService.saveOrUpdate(variables, "T_WSBS_XK", entityId);
            resultMap.put("success", true);
            resultMap.put("msg", "保存成功！");
            resultMap.put("xkId", xkId);
            resultMap.put("xkfile_name", xkfile_name);
            resultMap.put("xkfile_num", variables.get("xkfile_num"));
            resultMap.put("xkcontent", variables.get("xkcontent"));
        }
        String json = JSON.toJSONString(resultMap);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述：新建文件许可
     * 
     * @author Water Guo
     * @created 2017-4-14 上午11:33:30
     * @param request
     * @return
     */
    @RequestMapping(params = "newxkwj")
    public ModelAndView newxkwj(HttpServletRequest request) {
        request.setAttribute("itemId", request.getParameter("itemId"));
        return new ModelAndView("hflow/execution/newxkwj");
    }

    /**
     * 
     * 描述：获得许可信息
     * 
     * @author Water Guo
     * @created 2017-4-23 上午9:37:35
     * @param request
     * @param response
     */
    @RequestMapping(params = "info")
    public void info(HttpServletRequest request, HttpServletResponse response) {
        String xkId = request.getParameter("xkId");
        Map<String, Object> map = commonXKService.getInfoById(xkId);
        String json = JSON.toJSONString(map);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述：根据事项id和许可内容获得许可信息
     * 
     * @author Water Guo
     * @created 2017-4-23 下午10:02:02
     * @param request
     * @param response
     */
    @RequestMapping(params = "getInfoByName")
    public void getInfoByName(HttpServletRequest request, HttpServletResponse response) {
        String item_id = request.getParameter("item_id");
        String xkcontent = request.getParameter("xkcontent");
        Map<String, Object> map = commonXKService.getByJdbc("T_WSBS_XK", new String[] { "item_id", "xkcontent" },
                new Object[] { item_id, xkcontent });
        String json = JSON.toJSONString(map);
        this.setJsonString(json, response);
    }

    /**
     * 
     * 描述：删除
     * 
     * @author Water Guo
     * @created 2017-4-12 下午11:05:53
     * @param request
     * @return
     */
    @RequestMapping(params = "removeXK")
    @ResponseBody
    public AjaxJson removeXK(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("DEPART_ID", sysUser.getDepId());
        if (variables.get("xkcontent") != null) {
            variables.put("xkcontent", ((String) variables.get("xkcontent")).trim());
        }
        String xkId = commonXKService.getXKID(variables);
        if (StringUtils.isEmpty(xkId)) {
            j.setMsg("本条常用许可已删除！");
            j.setSuccess(false);
        } else {
            commonXKService.remove("T_WSBS_XK", "XK_ID", (xkId.split(",")));
            j.setMsg("删除成功！");
        }
        return j;
    }
}
