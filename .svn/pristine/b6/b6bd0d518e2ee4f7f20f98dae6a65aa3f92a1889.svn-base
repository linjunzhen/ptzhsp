/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.ArrayList;
import java.util.Date;
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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcDyqzxdjService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.CommonOpinionService;

/**
 * 描述 不动产抵押权注销Controller
 * @author Allin Lin
 * @created 2019年3月5日 上午11:32:19
 */
@Controller
@RequestMapping("/bdcDyqzxdjController")
public class BdcDyqzxdjController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjController.class);
    
    /**
     * 引入service
     */
    @Resource
    private BdcDyqzxdjService bdcDyqzxdjService;
    
    /**
     * 引入Service
     */
    @Resource
    private CommonOpinionService commonOpinionService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 引入Service
     */
    @Resource
    private BdcQueryService bdcQueryService; 
      
    /**
     * 描述  Ajax加载注销附记列表   
     * @author Allin Lin
     * @created 2019年3月6日 上午12:19:40
     * @param request
     * @param response
     */
    @RequestMapping(params = "ZXFJdatagrid")
    public void zxfjDatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request); 
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.USER_ID_EQ",sysUser.getUserId());
        filter.addSorted("T.CREATE_TIME","desc");
        List<Map<String, Object>> list = bdcDyqzxdjService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    保存/修改注销附记
     * @author Allin Lin
     * @created 2019年3月6日 上午12:38:16
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdateZXFJ")
    @ResponseBody
    public AjaxJson saveOrUpdateZXFJ(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String entityId = request.getParameter("OPINION_ID");
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        SysUser sysUser = AppUtil.getLoginUser();
        variables.put("USER_ID", sysUser.getUserId());
        if (variables.get("OPINION_CONTENT") != null) {
            variables.put("OPINION_CONTENT", ((String) variables.get("OPINION_CONTENT")).trim());
        }
        if (commonOpinionService.isExist(variables)) {
            j.setMsg("本条常用意见已存在！");
            j.setSuccess(false);
        } else {
            String opinionId = this.commonOpinionService.saveOrUpdate(variables, "T_WSBS_OPINION", entityId);
            if (StringUtils.isNotEmpty(opinionId)) {
                sysLogService.saveLog("修改了ID为[" + entityId + "]的常用意见记录", SysLogService.OPERATE_TYPE_EDIT);
            } else {
                sysLogService.saveLog("新增了ID为[" + opinionId + "]的常用意见记录", SysLogService.OPERATE_TYPE_ADD);
            }
            j.setJsonString(opinionId);
            j.setMsg("保存成功");
        }
        return j;
    }

    /**
     * 描述 跳转至选择器页面   
     * @author Allin Lin
     * @created 2019年3月6日 下午3:51:41
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String negativeListCodes = request.getParameter("negativeListCodes");
        String negativeListNames = request.getParameter("negativeListNames");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(negativeListCodes)&&!negativeListCodes.equals("undefined")){
            request.setAttribute("negativeListCodes", negativeListCodes);
            request.setAttribute("negativeListNames", negativeListNames);
        }
        return new ModelAndView("bsdt/applyform/bdcdyqzxdj/bdcdydacxSelector");
    }
    
    /**
     * 描述    Ajax请求列表数据
     * @author Allin Lin
     * @created 2019年3月6日 下午4:27:47
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcdjzmh = variables.get("bdcdjzmh") == null ? "" : variables.get("bdcdjzmh").toString();
        List<Map<String, Object>>  list; 
        List<Map<String, Object>>  returnList = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcdjzmh)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
                //筛选数据，只保留权属状态为"现势"的数据。
                for (Map<String, Object> map : list) {
                    if("现势".equals(StringUtil.getString(map.get("QSZT")))){
                        returnList.add(map);
                    }
                }
            }            
        }
        this.setListToJsonString(returnList.size(), returnList, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * @Description 业务选择
     * @author Luffy Cai
     * @date 2021年7月28日
     * @param request
     * @return ModelAndView
     */
    @RequestMapping(params = "busSelectorItemView")
    public ModelAndView busSelectorItemView(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/bdcqlc/dyqzxdj/busSelectorItemView");
    }

}
