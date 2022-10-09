/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.dao.BdcDyqscdjDao;
import net.evecom.platform.bdc.service.BdcDyqscdjService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.system.service.SysRoleService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述  不动产抵押权登记Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/bdcDyqscdjController")
public class BdcDyqscdjController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcDyqscdjController.class);
    /**
     * 引入Service
     */
    @Resource
    private BdcDyqscdjService bdcDyqscdjService;
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
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * sysRoleService
     */
    @Resource
    private SysRoleService sysRoleService;
    
    /**
     * 所引入的dao
     */
    @Resource
    private BdcDyqscdjDao dao;
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        bdcDyqscdjService.remove("T_BDC_DYQSCDJ","YW_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 不动产抵押权登记记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  bdcDyqscdj = bdcDyqscdjService.getByJdbc("T_BDC_DYQSCDJ",
                    new String[]{"YW_ID"},new Object[]{entityId});
            request.setAttribute("bdcDyqscdj", bdcDyqscdj);
        }
        return new ModelAndView("bdc/bdcDyqscdj/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("YW_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        String recordId = bdcDyqscdjService.saveOrUpdate(variables, "T_BDC_DYQSCDJ", entityId);
        if(StringUtils.isNotEmpty(entityId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的 不动产抵押权登记记录",SysLogService.OPERATE_TYPE_EDIT);
        }else{
            sysLogService.saveLog("新增了ID为["+recordId+"]的 不动产抵押权登记记录",SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到选择器页面
     * 
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
        String isYh = request.getParameter("isYh");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if(StringUtils.isNotEmpty(negativeListCodes)&&!negativeListCodes.equals("undefined")){
            request.setAttribute("negativeListCodes", negativeListCodes);
            request.setAttribute("negativeListNames", negativeListNames);
        }
        if (isYh != null && "0".equals(isYh)) {
            request.setAttribute("required", "");
            request.setAttribute("isBank", false);
        } else {
            //判断是否银行人员
            SysUser curUser = AppUtil.getLoginUser();
            boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"});
            if(isBankRole) {
                request.setAttribute("required", "required");
            }else {
                request.setAttribute("required", "");
            }
            request.setAttribute("isBank", isBankRole);
        }
        return new ModelAndView("bsdt/applyform/bdcdyqscdj/bdcdydacxSelector");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcdjzmh = variables.get("bdcdjzmh") == null ? "" : variables.get("bdcdjzmh").toString();
        if(bdcdjzmh != null && StringUtils.isNotEmpty(bdcdjzmh)){
            bdcdjzmh = bdcdjzmh.replace("&#40;", "(");
            bdcdjzmh = bdcdjzmh.replace("&#41;", ")");
            bdcdjzmh = bdcdjzmh.replace("&#42;", "*");
        }
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcdjzmh)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:查封信息数据
     *
     * @author Madison You
     * @created 2020/6/8 15:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdccfxxDatagrid")
    public void bdccfxxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String qlrmc = variables.get("qlrmc") == null ? "" : variables.get("qlrmc").toString();
        String zjhm = variables.get("zjhm") == null ? "" : variables.get("zjhm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(bdcdyh) || StringUtils.isNotEmpty(qlrmc) || StringUtils.isNotEmpty(zjhm)) {
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdccfxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述:根据申报号查询许可文书编号$许可文书名称
     *
     * @author Madison You
     * @created 2020/6/8 15:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "queryXkfileNum")
    public void queryXkfileNum(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String exeid = variables.get("exeid") == null ? "" : variables.get("exeid").toString();
        String exeidType = variables.get("exeidType") == null ? "" : variables.get("exeidType").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
        if (StringUtils.isNotEmpty(exeid) || StringUtils.isNotEmpty(exeidType)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select c.XKFILE_NUM,c.XKFILE_NAME from T_BDCQLC_GYJSJFWZYDJ T join JBPM6_EXECUTION a on a.Bus_Recordid = T.Yw_Id ");
            sql.append(" join JBPM6_FLOW_RESULT c on c.exe_id = a.exe_id where 1=1 ");
            switch (exeidType) {
                case "0":
                    sql.append(" and T.water_exeid = ? ");
                    break;
                case "1":
                    sql.append(" and T.pow_exeid = ? ");
                    break;
                case "2":
                    sql.append(" and T.gas_exeid = ? ");
                    break;
                case "3":
                    sql.append(" and T.sva_exeid = ? ");
                    break;
                default:
                    break;
            }
            list = dao.findBySql(sql.toString(), new Object[] { exeid }, null);
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                String Str = map.get("XKFILE_NUM").toString();
                Str = Str.replace("&#40;", "(");
                Str = Str.replace("&#41;", ")");
                map.put("XKFILE_NUM", Str);
                list.set(i, map);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 跳转到不动产档案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcdaxxcxSelector")
    public ModelAndView bdcdaxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        String isYh = request.getParameter("isYh");
        String isRequired = request.getParameter("isRequired");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        if (isYh != null && "0".equals(isYh)) {
            request.setAttribute("required", "");
            request.setAttribute("isBank", false);
        } else {
            //判断是否银行人员
            SysUser curUser = AppUtil.getLoginUser();
            boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"});
            if(isBankRole) {
                request.setAttribute("required", "required");
            }else {
                request.setAttribute("required", "");
            }
            request.setAttribute("isBank", isBankRole);
        }
        if (StringUtils.isNotEmpty(isRequired) && isRequired.equals("1")) {
            request.setAttribute("required", "required");
            request.setAttribute("isBank", true);
        }
        return new ModelAndView("bsdt/applyform/bdcdyqscdj/bdcdaxxcxSelector");
    }
    

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "bdcdaxxcxDatagrid")
    public void bdcdaxxcxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcqzh = variables.get("bdcqzh") == null ? "" : variables.get("bdcqzh").toString();
        if(bdcqzh != null && StringUtils.isNotEmpty(bdcqzh)){
            bdcqzh = bdcqzh.replace("&#40;", "(");
            bdcqzh = bdcqzh.replace("&#41;", ")");
            bdcqzh = bdcqzh.replace("&#42;", "*");
        }
        String ywh = variables.get("ywh") == null ? "" : variables.get("ywh").toString();
        String qlrmc = variables.get("qlrmc") == null ? "" : variables.get("qlrmc").toString();
        String zjhm = variables.get("zjhm") == null ? "" : variables.get("zjhm").toString();
        String fwbm = variables.get("fwbm") == null ? "" : variables.get("fwbm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcqzh)
                ||StringUtils.isNotEmpty(ywh)||StringUtils.isNotEmpty(qlrmc)
                ||StringUtils.isNotEmpty(zjhm)||StringUtils.isNotEmpty(fwbm)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcdaxxcxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    

    /**
     * 跳转到不动产档案信息查询选择器页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcygdacxSelector")
    public ModelAndView bdcygdacxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        //当noLike=1时表示页面上的不动产单元号采用精确查询
        String noLike = request.getParameter("noLike");
        if(noLike == null){
            noLike = "0";
        }
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("noLike", noLike);
        //判断是否银行人员
        SysUser curUser = AppUtil.getLoginUser();
        boolean isBankRole = sysRoleService.hasRoleByCode(curUser.getUserId(), new String[] {"BDCBANKROLE"});
        if(isBankRole) {
            request.setAttribute("required", "required");
        }else {
            request.setAttribute("required", "");
        }
        request.setAttribute("isBank", isBankRole);
        return new ModelAndView("bsdt/applyform/bdcdyqscdj/bdcygdacxSelector");
    }

    /**
     * 跳转到不动产档案信息查询选择器页面（外网）
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcygdacxSelectorWw")
    public ModelAndView bdcygdacxSelectorWw(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        //当noLike=1时表示页面上的不动产单元号采用精确查询
        String noLike = request.getParameter("noLike");
        if(noLike == null){
            noLike = "0";
        }
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("noLike", noLike);
        return new ModelAndView("website/applyforms/bdcqlc/selector/bdcygdacxSelectorWw");
    }

    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "bdcygdacxDatagrid")
    public void bdcygdacxDatagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String bdcdjzmh = variables.get("bdcdjzmh") == null ? "" : variables.get("bdcdjzmh").toString();
        if(bdcdjzmh != null && StringUtils.isNotEmpty(bdcdjzmh)){
            bdcdjzmh = bdcdjzmh.replace("&#40;", "(");
            bdcdjzmh = bdcdjzmh.replace("&#41;", ")");
            bdcdjzmh = bdcdjzmh.replace("&#42;", "*");
        }
        String qlr = variables.get("qlr") == null ? "" : variables.get("qlr").toString();
        String qlrzjh = variables.get("qlrzjh") == null ? "" : variables.get("qlrzjh").toString();
        String ywr = variables.get("ywr") == null ? "" : variables.get("ywr").toString();
        String ywrzjh = variables.get("ywrzjh") == null ? "" : variables.get("ywrzjh").toString();
        String ywh = variables.get("ywh") == null ? "" : variables.get("ywh").toString();
        String bdczl = variables.get("bdczl") == null ? "" : variables.get("bdczl").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(bdcdjzmh)
                ||StringUtils.isNotEmpty(qlr)||StringUtils.isNotEmpty(qlrzjh)
                ||StringUtils.isNotEmpty(ywr)||StringUtils.isNotEmpty(ywrzjh)
                ||StringUtils.isNotEmpty(ywh)||StringUtils.isNotEmpty(bdczl)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "announceUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述：常用意见选择器
     * @author Rider Chen
     * @created 2019年3月21日 下午3:41:20
     * @param request
     * @return
     */
    @RequestMapping(params = "cyyjmbSelector")
    public ModelAndView cyyjmbSelector(HttpServletRequest request) {
        String opnionId = request.getParameter("OPINION_ID");
        String businessName = request.getParameter("businessName");
        if (StringUtils.isNotEmpty(opnionId) && !opnionId.equals("undefined")) {
            request.setAttribute("OPINION_ID", opnionId);
        }
        request.setAttribute("businessName", businessName);
        return new ModelAndView("bsdt/applyform/cyyjmb/cyyjmbSelector");
    }
    
    /**
     * 描述  跳转至常用意见信息页面   
     * @author Allin Lin
     * @created 2019年3月6日 上午12:24:40
     * @param request
     * @return
     */
    @RequestMapping(params = "cyjjmbInfo")
    public ModelAndView cyjjmbInfo(HttpServletRequest request){
        String businessName = request.getParameter("businessName");
        request.setAttribute("businessName", businessName);
        return new ModelAndView("bsdt/applyform/cyyjmb/cyyjmbInfo");
    }
    
    /**
     * 方法del
     * 
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "cyyjmbMmultiDel")
    @ResponseBody
    public AjaxJson cyyjmbMmultiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        bdcDyqscdjService.remove("T_WSBS_OPINION","OPINION_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 常用意见记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    /**
     * 
     * 
     * 加载数据字典自动补全数据
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadDicSearch")
    public void loadDicSearch(HttpServletRequest request, HttpServletResponse response) {
        String typeCode = request.getParameter("typeCode");
        List<Map<String, Object>> list = dictionaryService.findByTypeCode(typeCode);
        for (Map<String, Object> map : list) {
            map.put("PINYINSZM", StringUtil.converterToFirstSpell((String) map.get("DIC_NAME")));
            map.put("PINYIN", StringUtil.getPingYin((String) map.get("DIC_NAME")));
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 描述:判断是否有受理分户业务
     *
     * @author Madison You
     * @created 2020/8/26 10:12:00
     * @param
     * @return
     */
    @RequestMapping(params = "checkBdcfh")
    @ResponseBody
    public Map<String, Object> checkBdcfh(HttpServletRequest request) {
        HashMap<String, Object> returnMap = new HashMap<>();
        String bdcdyh = request.getParameter("bdcdyh");
        if (StringUtils.isNotEmpty(bdcdyh)) {
            List<Map<String, Object>> list = bdcDyqscdjService.findBdcfhData(bdcdyh);
            if (list != null && !list.isEmpty()) {
                returnMap.put("success", true);
            } else {
                returnMap.put("success", false);
            }
        } else {
            returnMap.put("success", false);
        }
        return returnMap;
    }

    
   /**
    * 
    * 描述    保存抵押权登记信息
    * @author Allin Lin
    * @created 2020年11月26日 下午4:41:06
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(params = "saveDyqdjxx")
    @ResponseBody
    public Map<String, Object> saveDyqdjxx(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("success", false);
        String busTableName = (String) variables.get("busTableName");
        String busRecordId = (String) variables.get("busRecordId");
        try{
            if (StringUtils.isNotEmpty(busTableName) && StringUtils.isNotEmpty(busRecordId)) {
                bdcDyqscdjService.saveOrUpdate(variables, busTableName, busRecordId);
                returnMap.put("success", true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return returnMap;
    }
}

