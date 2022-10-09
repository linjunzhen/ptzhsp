/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bsfw.service.BdcQlcApplyService;

/**
 *  描述 不动产相关的全流程业务
 * @author Keravon Feng
 * @created 2019年12月2日 上午10:31:39
 */
@Controller
@RequestMapping("/bdcQlcApplyController")
public class BdcQlcApplyController extends BaseController {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcQlcApplyController.class);
    
    /**
     * bdcQueryService
     */
    @Resource
    private BdcQueryService bdcQueryService;
    
    /**
     * bdcQlcApplyService
     */
    @Resource
    private BdcQlcApplyService bdcQlcApplyService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/11 16:47:00
     * @param
     * @return
     */
    @Resource
    private DataSourceService dataSourceService;
    
    /**
     * 不动产登簿操作
     * @author Keravon Feng
     * @created 2019年12月5日 下午3:44:46
     * @param request
     * @param response
     */
    @RequestMapping("/bdcdbcz")
    public void bdcdbcz(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        String eveId = request.getParameter("eveId");
        if(eveId != null && StringUtils.isNotEmpty(eveId)) {
            Map<String,Object> execution = bdcQlcApplyService.getByJdbc("JBPM6_EXECUTION", 
                    new String[] {"EXE_ID"}, 
                    new Object[] {eveId});
            if(execution != null) {
                SysUser sysUser = AppUtil.getLoginUser();
                String fullname = sysUser.getFullname();
                String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                Map<String,Object> info = new HashMap<String,Object>();
                info.put("exe_id", eveId);
                info.put("item_code", String.valueOf(execution.get("ITEM_CODE")));
                info.put("dbr", fullname);
                info.put("dbsj", dateStr);
                AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "jsywqdUrl");
                if(ajaxJson.isSuccess()) {
                    String retJson = ajaxJson.getJsonString();
                    String resultJson = "";
                    if(retJson != null) {
                        retJson = retJson.replace("\r\n", "");
                        resultJson = retJson;
                        bdcQlcApplyService.saveDbInfo(eveId,retJson);
                    }
                    result.put("success", true);
                    result.put("data", resultJson);
                }else {
                    result.put("success", false);
                    result.put("msg", ajaxJson.getMsg());
                }
            }else {
                result.put("success", false);
                result.put("msg", "未找到受理申请编号"+eveId+"对应的流程实例。");
            }
        }else {
            result.put("success", false);
            result.put("msg", "eveId 受理申请编号不能为空");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
   
    /**
     * 跳转到房地产合同备案信息查询页面（外网）
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "fdchtbaxxcxSelectorWw")
    public ModelAndView fdchtbaxxcxSelectorWw(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("website/applyforms/bdcqlc/bdcqlcygspfygdj/fdchtbaxxcxSelectorWw");
    }

    /**
     * 跳转到不动产预告档案查询信息查询页面（外网）
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcygdacxSelectorWw")
    public ModelAndView bdcygdacxSelectorWw(HttpServletRequest request) {
        String allowCount = request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("website/applyforms/bdcqlc/bdcqlcygspfdyqygdj/bdcygdacxSelectorWw");
    }

    /**
     *
     * 描述：判断不动产单元号是否办理业务
     * @author Rider Chen
     * @created 2020年8月18日 下午5:19:49
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "verifyBdcdyhExistSubmit")
    @ResponseBody
    public void verifyBdcdyhExistSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String busTableName = request.getParameter("busTableName");
        //国有转移6个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String fieldValue = request.getParameter("fieldValue");
        String busRecordId = request.getParameter("busRecordId");
        Map<String, Object> result = new HashMap<>();
        boolean isExist = this.dataSourceService.isExistsFlowToBdcdyhRecord(busTableName, fieldValue, busRecordId);
        if(isExist){
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        String jsonStr = JSON.toJSONString(result);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
}
