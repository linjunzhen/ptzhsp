/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.util.*;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.bsfw.service.BdcSurveyService;
import net.evecom.platform.system.service.SysLogService;

/**
 * 描述  不动产-测绘管理controller()
 * @author Allin Lin
 * @created 2020年12月16日 上午10:32:57
 */
@Controller
@RequestMapping("/bdcSurveyController")
public class BdcSurveyController  extends BaseController{
    
    
    /**
     * 引入service
     */
    @Resource
    private BdcSurveyService bdcSurveyService;
    
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
     * 
     * 描述 页面跳转(测绘管理)
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "surveyView")
    public ModelAndView surveyView(HttpServletRequest request) {
        return new ModelAndView("bsfw/bdcSurvey/surveyView");
    }
    
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
        List<Map<String, Object>> list = bdcSurveyService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:导出
     *
     * @author Madison You
     * @created 2020/12/17 9:33:00
     * @param
     * @return
     */
    @RequestMapping(params = "exportBdcSurveyGridRecord")
    public void exportBdcSurveyGridRecord(HttpServletRequest request,HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> dataList = bdcSurveyService.findBySqlFileterExport(filter);
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/webpage/bsfw/bdcSurvey/surveyView.xls";
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
        ExcelUtil.exportXls(tplPath, dataList, "平潭综合实验区行政服务中心测绘管理表.xls",
                excludeFields, response, startRow, startCol, datas,"",true,new int[]{1,2});
    }

    /**
     * 获取测绘公司集合
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getDrawOrgList")
    @ResponseBody
    public List<Map<String, Object>> getDrawOrgList(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> chgsList = bdcExecutionService.getDrawOrgList("chgs%");
        return chgsList;
    }
    
    /**
    *
    * 显示测绘信息界面
    * 
    * @param request
    * @return
    */
   @RequestMapping(params = "info")
   public ModelAndView info(HttpServletRequest request) {
       String entityId = request.getParameter("entityId");
       if (StringUtils.isNotEmpty(entityId) && !entityId.equals("undefined")) {
           Map<String, Object> bdcSurvey = bdcSurveyService.getByJdbc("T_BDC_SURVEY",
                   new String[] { "YW_ID" }, new Object[] { entityId });
           request.setAttribute("bdcSurvey", bdcSurvey);
       }
       return new ModelAndView("bsfw/bdcSurvey/surveyInfo");
   }
   
   /**
    * 增加或者修改测绘信息
    * @param request
    * @return
    */
   @RequestMapping(params = "saveOrUpdate")
   @ResponseBody
   public AjaxJson saveOrUpdate(HttpServletRequest request) {
       AjaxJson j = new AjaxJson();
       String entityId = request.getParameter("ID");
       Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
       SysUser loginUser = AppUtil.getLoginUser();
       String located = StringUtil.getValue(variables, "LOCATED");
       String isMr = StringUtil.getValue(variables, "IS_MR");
       String surveyUserid = StringUtil.getValue(variables, "SURVEY_USERID");
       Map<String, Object> mrSurveyMap = bdcSurveyService.getByJdbc("T_BDC_SURVEY",
               new String[]{"LOCATED", "IS_MR"}, new Object[]{located, "1"});
       if (mrSurveyMap != null && Objects.equals(isMr, "1") &&
               !Objects.equals(StringUtil.getValue(mrSurveyMap,"YW_ID"), entityId)) {
           j.setSuccess(false);
           j.setMsg("此坐落已存在默认测绘公司");
           return j;
       }

       Map<String, Object> uniqueSurveyMap = bdcSurveyService.getByJdbc("T_BDC_SURVEY",
               new String[]{"LOCATED", "SURVEY_USERID"}, new Object[]{located, surveyUserid});
       if (uniqueSurveyMap != null) {
           String ywId = StringUtil.getValue(uniqueSurveyMap, "YW_ID");
           if (!Objects.equals(ywId, entityId)) {
               j.setSuccess(false);
               j.setMsg("此坐落已存在相同的测绘公司，请勿重复录入");
               return j;
           }
       }
       if (loginUser != null) {
           String username = loginUser.getUsername();
           String fullname = loginUser.getFullname();
           variables.put("CREATE_NAME", fullname);
           variables.put("CREATE_ACCOUNT", username);
       }
       String recordId = bdcSurveyService.saveOrUpdate(variables, "T_BDC_SURVEY", entityId);
       if(StringUtils.isNotEmpty(entityId)){
           sysLogService.saveLog("修改了ID为["+entityId+"]的不动产-测绘管理记录",SysLogService.OPERATE_TYPE_EDIT);
       }else{
           sysLogService.saveLog("新增了ID为["+recordId+"]的不动产-测绘管理记录",SysLogService.OPERATE_TYPE_ADD);
       }
       j.setMsg("保存成功");
       return j;
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
        bdcSurveyService.remove("T_BDC_SURVEY","YW_ID",selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
}
