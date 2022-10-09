/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.IpFilterConfigService;

/**
 * 描述 IP地址过滤配置
 * @author Danto Huang
 * @created 2020年9月11日 上午10:10:02
 */
@Controller
@RequestMapping("/ipFilterConfigController")
public class IpFilterConfigController extends BaseController {

    /**
     * 
     */
    @Resource
    private IpFilterConfigService ipFilterConfigService;
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:31:13
     * @param request
     * @return
     */
    @RequestMapping(params="filterConfigView")
    public ModelAndView filterConfigView(HttpServletRequest request){
        return new ModelAndView("system/ipfilterconfig/filterconfigView");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:32:22
     * @param request
     * @param response
     */
    @RequestMapping(params="datagrid")
    public void datagrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String,Object>> list = ipFilterConfigService.findBySqlFilter(filter);

        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:37:50
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> config = ipFilterConfigService.getByJdbc("T_SYSTEM_IPFILTER_CONFIG", new String[] { "CONFIG_ID" },
                    new Object[] { entityId });
            request.setAttribute("config", config);
        }
        return new ModelAndView("system/ipfilterconfig/filterconfigInfo");
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:38:51
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("CONFIG_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {            
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        List<Map<String, Object>> list = ipFilterConfigService.getAllByJdbc("T_SYSTEM_IPFILTER_CONFIG",
                new String[] { "IP_SEGMENT" }, new Object[] { variables.get("IP_SEGMENT") });
        boolean repeatFlag = false;
        if(list!=null&&list.size()>0){
            int start = Integer.valueOf(variables.get("IP_BIT_START").toString());
            int end = Integer.valueOf(variables.get("IP_BIT_END").toString());
            for(Map<String,Object> config : list){
                String configId = StringUtil.getValue(config, "CONFIG_ID");
                if (!Objects.equals(entityId, configId)) {
                    int bitStart = Integer.valueOf(config.get("IP_BIT_START").toString());
                    int bitEnd = Integer.valueOf(config.get("IP_BIT_END").toString());
                    if((start>=bitStart&&start<=bitEnd)||(end>=bitStart&&end<=bitEnd)){
                        repeatFlag = true;
                        break;
                    }
                }
            }
        }
        if(!repeatFlag){
            ipFilterConfigService.saveOrUpdate(variables, "T_SYSTEM_IPFILTER_CONFIG", entityId);
            j.setSuccess(true);
            j.setMsg("保存成功");
            
        }else{
            j.setSuccess(false);
            j.setMsg("录入IP段与已有记录存在重复！");
        }
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:39:19
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        ipFilterConfigService.remove("T_SYSTEM_IPFILTER_CONFIG","CONFIG_ID",selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年9月11日 上午10:41:13
     * @param request
     * @return
     */
    @RequestMapping(params = "updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        String status = request.getParameter("status");
        ipFilterConfigService.updateConfigStatus(selectColNames, Integer.parseInt(status));
        j.setMsg("更新状态成功");
        return j;
    }
}
