/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.model.ApasInfoData;
import net.evecom.platform.wsbs.service.ApasInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Controller
@RequestMapping("/apasInfoController")
public class ApasInfoController extends BaseController{
    /**
     * service服务
     */
    @Resource
    private ApasInfoService apasInfoService;
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        SysUser sysUser = AppUtil.getLoginUser();
        request.setAttribute("sysUser", sysUser);
        return new ModelAndView("wsbs/apasinfo/apasinfo");
    }
    /**
     * 获取横向效率统计数据URL
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "apasInfoData")
    public void apasInfoData(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATETIME", "desc");
        //filter.addSorted("T.CREATETIME", "desc");
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //String dept=request.getParameter("RECEIVEDEPT");
        List<Map<String, Object>> list = apasInfoService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 导出EXCEL
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //String deptId=request.getParameter("departId");
        //Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        //String begin=(String) request.getParameter("Q_T.CREATETIME_BEGIN");
        //String end=(String)  request.getParameter("Q_T.CREATETIME_END");
        List<Map<String, Object>> dataList =apasInfoService.findExBySqlFilter(filter);
        String tplPath = AppUtil.getAppAbsolutePath()+"/webpage/wsbs/apasinfo/apasInfoda.xlsx";
        int startRow = 2;
        int startCol = 1;
        StringBuffer fileName  = new StringBuffer("办件情况信息");
        fileName.append(".xlsx");
        Set<String>  excludeFields= new HashSet<String>();
        excludeFields.add("CURROW");
        //ExcelUtil.exportXlsxDa(tplPath, dataList, fileName.toString(), excludeFields, response, startRow, startCol);
        ApasInfoData.exportApasDuo(tplPath, dataList, fileName.toString(), excludeFields, response, startRow, startCol);
    }
}
