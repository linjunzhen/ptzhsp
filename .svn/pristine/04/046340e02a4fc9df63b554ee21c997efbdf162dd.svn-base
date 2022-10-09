/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcExecutionService;
import net.evecom.platform.bdc.service.BdcLsylwtcxService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 描述： 不动产登记Controller
 * 
 * @author Rider Chen
 * @created 2019年2月22日 上午10:14:56
 */
@Controller
@RequestMapping("/bdcExecutionController")
public class BdcExecutionController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcExecutionController.class);
    /**
     * 引入Service
     */
    @Resource
    private BdcExecutionService bdcExecutionService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    
    /**
     * 
     */
    @Resource
    private BdcLsylwtcxService bdcLsylwtcxService;

    
    /**
     * 跳转到不动产待预审界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBdcDwys")
    public ModelAndView goZzhyDwys(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/bdcDwysTask");
    }

    /**
     * 跳转到不动产待受理界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBdcNeedMeHandle")
    public ModelAndView goZzhyNeedMeHandle(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/bdcNeedmeHandlerTask");
    }

    /**
     * 跳转到不动产待审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBdcDwsp")
    public ModelAndView goZzhyDwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/bdcDwspTask");
    }

    /**
     * 跳转到不动产已审批界面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goBdcJwsp")
    public ModelAndView goZzhyJwsp(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/bdcJwspTask");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bdcNeedMeHandle")
    public void bdcNeedMeHandle(HttpServletRequest request, HttpServletResponse response) {
        boolean haveHandUp = false;
        String isHaveHandup = request.getParameter("isHaveHandup");
        if (StringUtils.isNotEmpty(isHaveHandup)) {
            haveHandUp = true;
        }
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "ASC");
        List<Map<String, Object>> list = bdcExecutionService.findBdcNeedMeHandle(filter, haveHandUp);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bdcHandledByMe")
    public void bdcHandledByMe(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("userAccount",sysUser.getUsername());
        filter.addSorted("T.CREATE_TIME", "desc");
        String userAccount = sysUser.getUsername();
        List<Map<String, Object>> list = bdcExecutionService.findBdcHandledByUser(filter, userAccount);
        List<Map<String, Object>> newlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Map<String, Object> dic = this.bdcExecutionService.getByJdbc("t_msjw_system_dictionary",
                    new String[] { "DIC_CODE", "TYPE_CODE" }, new Object[] { map.get("DEF_KEY"), "ZFJointDefKey" });
            if (dic != null) {
                map.put("DIC_STATE", 1);
            }
            newlist.add(map);
        }
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), newlist, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "glcStatis")
    public ModelAndView glcStatis(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/glcStatis");
    }

    /**
     * 
     * 描述 转跳企业办件情况查询
     * 
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "manSituationStatis")
    public ModelAndView manSituationStatis(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/manSituationStatis");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "manSituationStatisData")
    public void manSituationStatisData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcExecutionService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 
     * 描述 异常办件统计
     * 
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @return
     */
    @RequestMapping(params = "abnormalStatis")
    public ModelAndView abnormalStatis(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/abnormalStatis");
    }

    /**
     * 
     * 描述 异常办件统计
     * 
     * @author Water Guo
     * @created 2016-12-26 上午10:47:01
     * @param request
     * @param response
     */
    @RequestMapping(params = "adnormalData")
    public void adnormalData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("e.create_time", "desc");
        List<Map<String, Object>> list = bdcExecutionService.getAdnormalStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到个人绩效报表页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "goIndPerStatis")
    public ModelAndView goIndPerStatis(HttpServletRequest request) {
        return new ModelAndView("hflow/bdc/statist/indPerStatis");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "indPerData")
    public void glcData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        List<Map<String, Object>> list = bdcExecutionService.getIndPerDataStatist(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "countsDetail")
    public ModelAndView countsDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("hflow/bdc/statist/countsDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "countsDetailData")
    public void countsDetailData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcExecutionService.countsDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "banJSDetail")
    public ModelAndView banJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("hflow/bdc/statist/banJSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "banJSDetailData")
    public void banJSDetailData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcExecutionService.banJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "zBSDetail")
    public ModelAndView zBSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("hflow/bdc/statist/zBSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "zBSDetailData")
    public void zBSDetailData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcExecutionService.zBSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "tJSDetail")
    public ModelAndView tJSDetail(HttpServletRequest request) {
        String operatorId = request.getParameter("operatorId");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String beginTime2 = request.getParameter("beginTime2");
        String endTime2 = request.getParameter("endTime2");
        request.setAttribute("operatorId", operatorId);
        request.setAttribute("beginTime", beginTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("beginTime2", beginTime2);
        request.setAttribute("endTime2", endTime2);
        return new ModelAndView("hflow/bdc/statist/tJSDetail");
    }

    /**
     * 
     * 描述 窗口办件量统计分析表
     * 
     * @author Rider Chen
     * @created 2016-3-9 上午11:02:39
     * @param request
     * @param response
     */
    @RequestMapping(params = "tJSDetailData")
    public void tJSDetailData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "desc");
        List<Map<String, Object>> list = bdcExecutionService.tJSDetailData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addBbfbdcdjzmInfo")
    public ModelAndView addBbfbdcdjzmInfo(HttpServletRequest request) {
        String type = request.getParameter("type");
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        if(StringUtils.isNotEmpty(type) && type.equals("2")){
            return new ModelAndView("bsdt/applyform/bdcqlc/bdcqlcbfbdcdjzm/T_BFBDCDJZM_YGSPFYGBFDJ");
        } else if(StringUtils.isNotEmpty(type) && type.equals("3")){
            return new ModelAndView("bsdt/applyform/bdcqlc/bdcqlcbfbdcdjzm/T_BFBDCDJZM_DYQYGBFDJ");
        } else{
            return new ModelAndView("bsdt/applyform/bdcqlc/bdcqlcbfbdcdjzm/T_BFBDCDJZM_INFO");
        }
    }
    
    /**
     * 描述 不动产业务银行人员收件新增窗口
     * @author Keravon Feng
     * @created 2020年1月9日 下午2:47:41
     * @param request
     * @return
     */
    @RequestMapping(params = "bankapply")
    public ModelAndView bankapply(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/bankapply/acceptList");
    }
    
    /**
     * 描述  获取配置的银行需要受理的业务清单
     * @author Keravon Feng
     * @created 2020年1月9日 下午3:04:24
     * @param request
     * @param response
     */
    @RequestMapping(params = "publishdatagrid")
    public void publishdatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        //filter.addSorted("T.C_SN", "desc");
        filter.addSorted("ecount", "desc");
        List<Map<String, Object>> list = bdcExecutionService.findByPublishSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:开发商业务受理页面
     *
     * @author Madison You
     * @created 2020/8/13 10:59:00
     * @param 
     * @return 
     */
    @RequestMapping(params = "kfsywslListView")
    public ModelAndView kfsywslListView(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/kfsywsl/kfsywslListView");
    }

    /**
     * 描述:开发商业务受理数据列表
     *
     * @author Madison You
     * @created 2020/8/13 11:10:00
     * @param
     * @return
     */
    @RequestMapping(params = "kfsywslListData")
    public void kfsywslListData(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("ecount", "desc");
        List<Map<String, Object>> list = bdcExecutionService.findByKfsywslListSqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:根据坐落查找测绘公司
     *
     * @author Madison You
     * @created 2020/12/16 11:14:00
     * @param
     * @return
     */
    @RequestMapping(params = "findDrawOrgByLocated")
    @ResponseBody
    public Map<String, Object> findDrawOrgByLocated(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        String located = request.getParameter("located");
        try{
            Map<String, Object> surveyMap = bdcExecutionService.getByJdbc("T_BDC_SURVEY",
                    new String[]{"LOCATED", "IS_MR"}, new Object[]{located, "1"});
            if (surveyMap != null) {
                returnMap.put("success", true);
                returnMap.put("surveyUserId", StringUtil.getValue(surveyMap, "SURVEY_USERID"));
            } else {
                returnMap.put("success", false);
            }
        } catch (Exception e) {
            log.error("根据坐落查找测绘公司出错", e);
            returnMap.put("success", false);
        }
        return returnMap;
    }

    /**
     * 
     * 描述    批量收件人员独立生成办件并受理
     * @author Allin Lin
     * @created 2021年2月1日 下午4:54:51
     * @param request
     * @return
     */
    @RequestMapping(params="acceptPersonExe")
    @ResponseBody
    public AjaxJson acceptPersonExe(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        Map<String,Object> result = bdcLsylwtcxService.acceptPersonExe(recordId);
        
        j.setSuccess((boolean) result.get("OPER_SUCCESS"));
        j.setMsg((String) result.get("OPER_MSG"));
        return j;
    }
    
    /**
     * 
     * 描述    初始化不动产历史遗留问题批量件入数据
     * @author Allin Lin
     * @created 2021年2月2日 上午10:56:33
     * @param request
     * @param response
     */
    @RequestMapping(params="loadImpPerson")
    @ResponseBody
    public void loadImpPersonData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("GEN_STATUS", "asc");
        /*if(filter.getQueryParams().get("Q_t.EXE_ID_EQ")==null){
            filter.addFilter("Q_t.EXE_ID_EQ", "0");
        }*/
        List<Map<String, Object>> list = bdcLsylwtcxService.loadPLperson(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response); 
    }
    
    /**
     * 描述    跳转到抵押权人联系人
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "bdcdyrlxr")
    public ModelAndView bdcdyrlxr(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hflow/bdc/bdcDyrlxr");
    }
}
