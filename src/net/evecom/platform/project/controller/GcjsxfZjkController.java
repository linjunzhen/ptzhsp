/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.controller;

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

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.project.service.GcjsxfZjkService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述 工程建设消防验收专家库信息controller
 * @author Keravon Feng
 * @created 2021年11月16日 上午11:18:08
 */
@Controller
@RequestMapping("/gcjsxfZjkController")
public class GcjsxfZjkController extends BaseController {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(GcjsxfZjkController.class);
    
    /**
     * gcjsxfZjkService
     */
    @Resource
    private GcjsxfZjkService gcjsxfZjkService;
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    
    /**
     * 
     * 描述 跳转至专家库的列表
     * @author Keravon Feng
     * @created 2021年11月16日 上午11:25:41
     * @param request
     * @return
     */
    @RequestMapping(params = "zjklist")
    public ModelAndView zjkList(HttpServletRequest request) {
        return new ModelAndView("project/zjk/zjkList");
    }
    
    /**
     * 
     * 描述  表单详情
     * @author Keravon Feng
     * @created 2021年11月16日 上午11:37:54
     * @param request
     * @return
     */
    @RequestMapping(params = "zjkinfo")
    public ModelAndView zjkInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> zjkinfo = gcjsxfZjkService.getByJdbc("T_BSFW_GCJSXF_ZJK", new String[] { "JBXX_ID" },
                    new Object[] { entityId });
            request.setAttribute("zjkinfo", zjkinfo);
        }
        return new ModelAndView("project/zjk/zjkInfo");
    }
    
    /**
     * 描述 专家库选择器
     * @author Keravon Feng
     * @created 2021年11月22日 上午11:12:25
     * @param request
     * @return
     */
    @RequestMapping(params = "zjkSelector")
    public ModelAndView zjkSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/tsxf/zjkSelector");
    }
    
    /**
     * 描述 列表数据
     * @author Keravon Feng
     * @created 2021年11月16日 下午2:41:27
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = gcjsxfZjkService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述 删除
     * @author Keravon Feng
     * @created 2021年11月16日 下午3:19:37
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        gcjsxfZjkService.multiDel(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为[" + selectColNames + "]的专家库信息记录", SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 描述 保存与更新的方法
     * @author Keravon Feng
     * @created 2021年11月16日 下午4:04:33
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("JBXX_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        boolean isok = false;
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            isok = true;
        }
        String recordId = gcjsxfZjkService.saveOrUpdate(variables, "T_BSFW_GCJSXF_ZJK", entityId);
        if(isok){
            sysLogService.saveLog("新增了ID为[" + recordId + "]的专家库信息记录", SysLogService.OPERATE_TYPE_ADD);
        }else {
            sysLogService.saveLog("修改了ID为[" + recordId + "]的专家库信息记录", SysLogService.OPERATE_TYPE_DEL);
        }
        j.setMsg("保存成功");
        return j;
    }
    
}
