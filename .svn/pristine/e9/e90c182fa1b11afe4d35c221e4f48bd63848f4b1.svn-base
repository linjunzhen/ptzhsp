/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.GlobalUrlService;

/**
 * 描述
 * @author Danto Huang
 * @created 2020年6月22日 上午11:39:15
 */
@Controller
@RequestMapping("/globalUrlController")
public class GlobalUrlController extends BaseController {

    /**
     * 
     */
    @Resource
    private GlobalUrlService globalUrlService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 下午2:46:35
     * @param request
     * @return
     */
    @RequestMapping(params="globalUrlView")
    public ModelAndView globalUrlView(HttpServletRequest request){
        return new ModelAndView("system/globalurl/globalUrlView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年6月22日 下午2:48:16
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.URL_CREATETIME", "desc");
        List<Map<String,Object>> list = globalUrlService.findBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    

    /**
     *
     * 显示全局URL列表
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> globalUrl = globalUrlService.getByJdbc("T_SYSTEM_GLOBALURL", new String[] { "URL_ID" },
                    new Object[] { entityId });
            request.setAttribute("globalUrl", globalUrl);
        }
        return new ModelAndView("system/globalurl/globalUrlInfo");
    }

    /**
     *
     * 增加或者修改全局URL
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("URL_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("URL_CREATETIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        globalUrlService.saveOrUpdate(variables, "T_SYSTEM_GLOBALURL", entityId);

        Set<String> allAnonUrlSet = new HashSet<String>();
        List<String> allAnonUrls = globalUrlService.findByFilterType("1");
        allAnonUrlSet.addAll(allAnonUrls);
        AppUtil.setAllAnonUrlSet(allAnonUrlSet);
        
        Set<String> allSessionUrlSet = new HashSet<String>();
        List<String> allSessionUrls = globalUrlService.findByFilterType("2");
        allSessionUrlSet.addAll(allSessionUrls);
        AppUtil.setAllSessionUrlSet(allSessionUrlSet);
        
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 
     * 删除全局URL数据
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        globalUrlService.remove("T_SYSTEM_GLOBALURL","URL_ID",selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
}
