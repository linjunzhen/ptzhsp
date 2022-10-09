/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.bsfw.service.BdcDyrlxrService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述  不动产-抵押权人联系人controller()
 * @author Allin Lin
 * @created 2020年12月16日 上午10:32:57
 */
@Controller
@RequestMapping("/bdcDyrlxrController")
public class BdcDyrlxrController  extends BaseController{
    
    
    /**
     * 引入service
     */
    @Resource
    private BdcDyrlxrService bdcDyrlxrService;
    
    /**
     * 引入service
     */
    @Resource
    private BdcExecutionService bdcExecutionService;
    
    /**
     * 引入service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * easyui AJAX请求测绘管理列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcDyrlxrService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/17 10:11:00
     * @param
     * @return
     */
     @RequestMapping(params = "multiDel")
     @ResponseBody
     public AjaxJson multiDel(HttpServletRequest request) {
         AjaxJson j = new AjaxJson();
         String selectColNames = request.getParameter("selectColNames");
         bdcDyrlxrService.remove("T_BDC_DYRLXB","YW_ID",selectColNames.split(","));
         j.setMsg("删除成功");
         return j;
     }
     
     /**
     *
     * 显示抵押权人联系人界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
            Map<String, Object> bdcDyrlxr = bdcDyrlxrService.getByJdbc("T_BDC_DYRLXB",
                    new String[] { "YW_ID" }, new Object[] { entityId });
            request.setAttribute("bdcDyrlxr", bdcDyrlxr);
        }
        return new ModelAndView("bsfw/bdcDyrlxr/dyrlxrInfo");
    }
    
    /**
     * 增加或者修改抵押权人联系人
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId = bdcDyrlxrService.saveOrUpdate(variables, "T_BDC_DYRLXB", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的不动产-抵押权人联系人记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的不动产-抵押权人联系人记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:33:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportBdcDyrlxrGridRecord")
    public void exportBdcSurveyGridRecord(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = bdcDyrlxrService.findBySqlFileterExport(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/bsfw/bdcDyrlxr/dyrlxrView.xls";
        int startRow = 4;
        int startCol = 1;
        StringBuffer time = new StringBuffer("");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        if (StringUtils.isNotEmpty(beginDate)) {
            time.append(beginDate);
        }
        if (StringUtils.isNotEmpty(beginDate)
                || StringUtils.isNotEmpty(endDate)) {
            time.append("至");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            time.append(endDate);
        }
        time.append("(制表时间："
                + DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd") + ")");
        Set<String> excludeFields = new HashSet<String>();
        excludeFields.add("CURRENTROW");
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        // 找到第2行第1列的"times"，用""替换掉"title"
        ExcelReplaceDataVO vo1 = new ExcelReplaceDataVO();
        vo1.setRow(1);
        vo1.setColumn(0);
        vo1.setKey("${times}");
        vo1.setValue(time.toString());
        datas.add(vo1);
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心抵押权人联系表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1,2});
    }
}
