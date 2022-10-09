/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.service.BdcSsdjService;
import net.evecom.platform.bsfw.model.MaterDownload;
import net.evecom.platform.bsfw.util.MaterDownloadUtil;
import net.evecom.platform.bsfw.util.ReturnInfoUtil;
import net.evecom.platform.system.controller.LoginController;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.yb.service.YbStampService;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bsfw.service.BdcApplyService;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;
import sun.misc.BASE64Decoder;

/**
 * 描述 不动产业务受理
 * @author Keravon Feng
 * @created 2018年12月12日 上午10:12:20
 */

@Controller
@RequestMapping("/bdcApplyController")
public class BdcApplyController extends BaseController {
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:34:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcApplyController.class);
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:34:00
     * @param
     * @return
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:33:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:33:00
     * @param
     * @return
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:33:00
     * @param
     * @return
     */
    @Resource
    private BdcQueryService bdcQueryService;
    
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/29 9:33:00
     * @param
     * @return
     */
    @Resource
    private BdcApplyService bdcApplyService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/13 10:13:00
     * @param 
     * @return 
     */
    @Resource
    private BdcSsdjService bdcSsdjService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/1/18 10:36:00
     * @param
     * @return
     */
    @Resource
    private YbStampService ybStampService;

    
    /**
     * 
     * 描述
     * @author Keravon Feng
     * @created 2018年12月12日 上午10:18:45
     * @param request
     * @param response
     */
    @RequestMapping("/fluidComboBox")
    public void selectTree(HttpServletRequest request, HttpServletResponse response){
        String typeCode =  request.getParameter("typeCode");
        List<Map<String,Object>> children ;
        if(StringUtils.isNotEmpty(typeCode)){
            children = dicTypeService.findByParentCode(typeCode);
        }else{
            children = dicTypeService.findByParentCode("qlxz");
        }
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> child : children) {
            Map<String,Object> resultMap = null;
            List<Map<String,Object>> dics = dictionaryService.findByTypeCode(String.valueOf(child.get("TYPE_CODE")));
            if(dics != null && dics.size() > 0){
                for(Map<String,Object> map : dics){
                    resultMap = new HashMap<String, Object>();
                    resultMap.put("value", map.get("DIC_CODE"));
                    resultMap.put("text", map.get("DIC_NAME"));
                    resultMap.put("group", child.get("TYPE_NAME"));
                    resultMap.put("groupId", child.get("TYPE_CODE"));
                    result.add(resultMap);
                }
            }else{
                resultMap = new HashMap<String, Object>();
                resultMap.put("value", child.get("TYPE_CODE"));
                resultMap.put("text", child.get("TYPE_NAME"));
                result.add(resultMap);
            }
            
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }

    /**
     * 描述:测试不动产接口
     *
     * @author Madison You
     * @created 2019/12/3 16:51:00
     * @param
     * @return
     */
    @RequestMapping("/testBdcQuery")
    @ResponseBody
    public AjaxJson testBdcQuery(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> info = BeanUtil.getMapFromRequest(request);
        String urlDicType = request.getParameter("urlDicType");
        info.remove("urlDicType");
        AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, urlDicType);
        return ajaxJson;
    }
    
    /**
     * 描述 调用接口查询
     * @author Keravon Feng
     * @created 2018年12月25日 下午2:50:44
     * @param request
     * @param response
     */
    @RequestMapping("/searchBdcQuery")
    public void searchBdcQuery(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        String datas = request.getParameter("datas");
        if(datas != null && StringUtils.isNotEmpty(datas)){
            List<Map> datasJson = JSON.parseArray(datas, Map.class);
            List<Map<String,Object>> qlrlist = new ArrayList<Map<String,Object>>();
            for(Map map : datasJson){
                Map<String,Object> qlr = new HashMap<String,Object>();
                qlr.put("qlrmc", map.get("qlrmc"));
                qlr.put("qlrzjhm", map.get("qlrzjhm"));
                qlrlist.add(qlr);
            }
            try {
                List<Map<String,Object>> list = bdcQueryService.queryOgligeeOfDataByIF(qlrlist);
                result.put("success", true);
                result.put("data", list);
            } catch (Exception e) {
                result.put("success", false);
                result.put("msg", "调用诚信所接口出现系统错误。");
            }
        }else{
            result.put("success", false);
            result.put("msg", "调用诚信所接口参数不能为空。");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 描述 打印
     * @author Keravon Feng
     * @created 2018年12月27日 下午4:32:58
     * @param request
     * @return
     */
    @RequestMapping(params = "printBdcQueryTemplate")
    public ModelAndView printBdcQueryTemplate(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String eveId = request.getParameter("eveId");
        String ob_id = request.getParameter("OB_ID");
        if(eveId != null && StringUtils.isNotEmpty(eveId)){
            Map<String,Object> execution = dicTypeService.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{eveId});
            if(execution != null){
                templateData.put("eveId", eveId);
                String bsyhlx = (String)execution.get("BSYHLX");
                if("1".equals(bsyhlx)){
                    templateData.put("SQRMC", execution.get("SQRMC"));
                    templateData.put("MOBILE", execution.get("SQRSJH"));
                }else{
                    templateData.put("SQRMC", execution.get("JBR_NAME"));
                    templateData.put("MOBILE", execution.get("JBR_MOBILE"));
                }
                String create_time = String.valueOf(execution.get("CREATE_TIME")).substring(0, 10);
                templateData.put("CREATE_TIME", create_time);
                templateData.put("YEAR", ""+DateTimeUtil.getYear(create_time));
                templateData.put("MONTH",""+DateTimeUtil.getMonth(create_time));
                templateData.put("DAY", ""+DateTimeUtil.getDay(create_time));
                Map<String,Object> busRecord = dicTypeService.getByJdbc("T_BDC_DATAQUERY", 
                        new String[]{"YW_ID"},
                        new Object[]{execution.get("BUS_RECORDID")});
                if(busRecord != null){
                    String family_json = (String)busRecord.get("FAMILY_JSON");
                    if(""!=family_json && family_json!=null){
                        List<Map> familys = JSON.parseArray(family_json, Map.class);
                        if(familys != null && familys.size() > 0){
                            for(int i=0;i<familys.size();i++){
                                Map map = familys.get(i);
                                templateData.put("qlrmc_"+i, map.get("qlrmc"));
                                templateData.put("sf_"+i, "身份证");
                                templateData.put("qlrzjhm_"+i, map.get("qlrzjhm"));
                                templateData.put("qlraddr_"+i, map.get("qlraddr"));
                            }
                            for(int j=familys.size();j<5;j++){
                                templateData.put("qlrmc_"+j, " ");
                                templateData.put("sf_"+j, " ");
                                templateData.put("qlrzjhm_"+j, " ");
                                templateData.put("qlraddr_"+j, " ");
                            }
                        }
                    }
                    Map<String,Object> obj = dictionaryService.get("queryResult",
                            String.valueOf(busRecord.get("QUERY_RESULT")));
                    templateData.put("QUERY_RESULT", obj.get("DIC_NAME"));
                    String result_json = (String)busRecord.get("RESULT_JSON");
                    templateData.put("PROPERTY_ADDR", " ");
                    if(""!=result_json && result_json!=null){
                        List<Map> resultMap = JSON.parseArray(result_json, Map.class);
                        for(Map map : resultMap){
                            if(String.valueOf(map.get("OB_ID")).equals(ob_id)){
                                templateData.put("PROPERTY_ADDR", map.get("PROPERTY_ADDR")); 
                            }
                        }
                    }
                }
            }
        }
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        request.setAttribute("TemplateData", templateData);
        return new ModelAndView("bsdt/applyform/estate/printBdcQuery");
    }
    
    /**
     * 描述 不动产委托书打印
     * @author Keravon Feng
     * @created 2019年1月3日 下午3:48:12
     * @param request
     * @return
     */
    @RequestMapping(params = "printBdcWtTemplate")
    public ModelAndView printBdcWtTemplate(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        String eveId = request.getParameter("eveId");
        if(eveId != null && StringUtils.isNotEmpty(eveId)){
            Map<String,Object> execution = dicTypeService.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{eveId});
            if(execution != null){
                templateData.put("eveId", eveId);
                Map<String,Object> busRecord = dicTypeService.getByJdbc("T_BDC_WT", 
                        new String[]{"YW_ID"},
                        new Object[]{execution.get("BUS_RECORDID")});
                if(busRecord != null){
                    String create_time = String.valueOf(execution.get("CREATE_TIME")).substring(0, 10);
                    templateData.putAll(busRecord); 
                    templateData.put("CREATE_TIME", create_time);
                    //委托人模板设值
                    setWtrInfoFn(templateData, busRecord);
                    //受托人信息打印模板设值
                    setStrInfoFn(templateData, busRecord);
                    String bd_json = (String)busRecord.get("BD_JSON");
                    if(""!=bd_json && bd_json!=null){
                        List<Map> bdinfos = JSON.parseArray(bd_json, Map.class);
                        StringBuffer bdc_addr= new StringBuffer("");
                        if(bdinfos != null && bdinfos.size() > 0){
                            for(int i=0;i<bdinfos.size();i++){
                                Map map = bdinfos.get(i);
                                if(i>0){
                                    bdc_addr.append(",");
                                }
                                bdc_addr.append((String)map.get("bdc_addr"));
                            }
                        }
                        templateData.put("bdc_addr", bdc_addr);                        
                    }
                    String wtItems = (String)busRecord.get("WT_ITEMS");
                    if(wtItems != null){
                        templateData.put("wt_items_len", wtItems.split(",").length);
                    }else{
                        templateData.put("wt_items_len", "");
                    }
                }
            }
        }
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        request.setAttribute("TemplateData", templateData);
        return new ModelAndView("bsdt/applyform/estate/printBdcQuery");
    }

    /**
     * 描述 委托人打印模板设值
     * @author Keravon Feng
     * @created 2019年1月3日 下午5:02:14
     * @param templateData
     * @param busRecord
     */
    private void setWtrInfoFn(Map<String, Object> templateData, Map<String, Object> busRecord) {
        String wt_json = (String)busRecord.get("WT_JSON");
        if(""!=wt_json && wt_json!=null){
            List<Map> wtinfos = JSON.parseArray(wt_json, Map.class);
            String wt_name="",wt_fddb="",wt_zjlx="",
                    wt_zjhm="",wt_lxdh="",wt_zipcode="",wt_addr="";
            if(wtinfos != null && wtinfos.size() > 0){
                for(int i=0;i<wtinfos.size();i++){
                    Map map = wtinfos.get(i);
                    if(i>0){
                        wt_name+=",";wt_fddb+=",";
                        wt_zjlx+=",";wt_zjhm+=",";
                        wt_lxdh+=",";wt_zipcode+=",";
                        wt_addr+=",";
                    }
                    wt_name+=(String)map.get("wtr_name");
                    wt_fddb+=(String)map.get("fddb_name");
                    Map<String,Object> obj = dictionaryService.get("DocumentType",
                            String.valueOf(map.get("wtr_zjlx")));
                    wt_zjlx+=(String)obj.get("DIC_NAME");
                    wt_zjhm+=(String)map.get("wtr_zjhm");
                    wt_lxdh+=(String)map.get("wtr_lxdh");
                    wt_zipcode+=(String)map.get("wtr_zipcode");
                    wt_addr+=(String)map.get("wtr_addr");
                    obj = null;
                }
            }
            templateData.put("wt_name", wt_name);
            templateData.put("wt_fddb", wt_fddb);
            templateData.put("wt_zjlx", wt_zjlx);
            templateData.put("wt_zjhm", wt_zjhm);
            templateData.put("wt_lxdh", wt_lxdh);
            templateData.put("wt_zipcode", wt_zipcode);
            templateData.put("wt_addr", wt_addr);
        }
    }

    /**
     * 描述 受托人信息打印模板设值
     * @author Keravon Feng
     * @created 2019年1月3日 下午5:01:19
     * @param templateData
     * @param busRecord
     */
    private void setStrInfoFn(Map<String, Object> templateData, Map<String, Object> busRecord) {
        String st_json = (String)busRecord.get("ST_JSON");
        if(""!=st_json && st_json!=null){
            List<Map> stinfos = JSON.parseArray(st_json, Map.class);
            String st_name="",st_fddb="",st_zjlx="",
                    st_zjhm="",st_lxdh="",st_zipcode="",st_addr="";
            if(stinfos != null && stinfos.size() > 0){
                for(int i=0;i<stinfos.size();i++){
                    Map map = stinfos.get(i);
                    if(i>0){
                        st_name+=",";st_fddb+=",";
                        st_zjlx+=",";st_zjhm+=",";
                        st_lxdh+=",";st_zipcode+=",";
                        st_addr+=",";
                    }
                    st_name+=(String)map.get("str_name");
                    st_fddb+=(String)map.get("fddb_name");
                    Map<String,Object> obj = dictionaryService.get("DocumentType",
                            String.valueOf(map.get("str_zjlx")));
                    st_zjlx+=(String)obj.get("DIC_NAME");
                    st_zjhm+=(String)map.get("str_zjhm");
                    st_lxdh+=(String)map.get("str_lxdh");
                    st_zipcode+=(String)map.get("str_zipcode");
                    st_addr+=(String)map.get("str_addr");
                    obj = null;
                }
            }
            templateData.put("st_name", st_name);
            templateData.put("st_fddb", st_fddb);
            templateData.put("st_zjlx", st_zjlx);
            templateData.put("st_zjhm", st_zjhm);
            templateData.put("st_lxdh", st_lxdh);
            templateData.put("st_zipcode", st_zipcode);
            templateData.put("st_addr", st_addr);
        }
    }
    
    /**
     * 描述  委托事项选择器
     * @author Keravon Feng
     * @created 2019年1月3日 上午10:47:17
     * @param request
     * @return
     */
    @RequestMapping(params = "selector")
    public ModelAndView selector(HttpServletRequest request) {
        String wtitemCodes = request.getParameter("wtitemCodes");
        String wtitemNames = request.getParameter("wtitemNames");
        if (StringUtils.isNotEmpty(wtitemCodes) && !wtitemCodes.equals("undefined")) {
            request.setAttribute("wtitemCodes", wtitemCodes);
            request.setAttribute("wtitemNames", wtitemNames);
        }
        return new ModelAndView("bsdt/applyform/estate/wtItemsSelector");
    }
    
    /**
     * 描述 已选委托事项
     * @author Keravon Feng
     * @created 2019年1月3日 上午11:04:45
     * @param request
     * @param response
     */
    @RequestMapping(params = "selected")
    public void selected(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter sqlFilter = new SqlFilter(request);
        String wtitemCodes = request.getParameter("wtitemCodes");
        sqlFilter.addFilter("Q_T.TYPE_CODE_=", "wtitems"); 
        sqlFilter.addFilter("Q_D.DIC_CODE_IN", wtitemCodes); 
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(sqlFilter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 根据原申报号获取数据
     * @author Keravon Feng
     * @created 2019年1月4日 上午10:36:47
     * @param request
     * @param response
     */
    @RequestMapping(params = "loadDataByEveId")
    public void loadDataByEveId(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        String eveId = request.getParameter("eveId");
        if(eveId != null && StringUtils.isNotEmpty(eveId)){
            Map<String,Object> execution = dicTypeService.getByJdbc("JBPM6_EXECUTION", 
                    new String[]{"EXE_ID"}, new Object[]{eveId});
            if(execution != null){
                Map<String,Object> busRecord = dicTypeService.getByJdbc("T_BDC_WT", 
                        new String[]{"YW_ID"},
                        new Object[]{execution.get("BUS_RECORDID")});
                if(busRecord != null){
                    String bd_json = (String)busRecord.get("BD_JSON");
                    if(""!=bd_json && bd_json!=null){
                        List<Map> bdinfos = JSON.parseArray(bd_json, Map.class);
                        StringBuffer bdc_addr=new StringBuffer(""),
                                wt_cqzh=new StringBuffer(""),
                                wt_qlr=new StringBuffer("");
                        if(bdinfos != null && bdinfos.size() > 0){
                            for(int i=0;i<bdinfos.size();i++){
                                Map map = bdinfos.get(i);
                                if(i>0){
                                    bdc_addr.append(",");
                                    wt_cqzh.append(",");
                                    wt_qlr.append(",");
                                }
                                bdc_addr.append((String)map.get("bdc_addr"));                                
                                wt_cqzh.append((String)map.get("bdc_cqzh"));                                
                                wt_qlr.append((String)map.get("bdc_name"));                                
                            }
                        }
                        busRecord.put("WT_ADDR", bdc_addr);
                        busRecord.put("WT_CQZH", wt_cqzh);
                        busRecord.put("WT_QLR", wt_qlr);
                    }
                    Map<String, Object> templateData = new HashMap<String, Object>();
                    //委托人模板设值
                    setWtrInfoFn(templateData, busRecord);
                    //受托人信息打印模板设值
                    setStrInfoFn(templateData, busRecord);
                    busRecord.put("WTR_NAME", templateData.get("wt_name"));
                    busRecord.put("STR_NAME", templateData.get("st_name"));                    
                }  
                result.put("success", true);
                result.put("data", busRecord);
            }else{
                result.put("success", false);
                result.put("msg", "查无数据。");
            }
        }else{
            result.put("success", false);
            result.put("msg", "原申报号参数不能为空。");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }
    
    /**
     * 
     * 描述 委托备案标的物查询选择面板
     * @author Keravon Feng
     * @created 2019年1月16日 下午4:16:54
     * @param request
     * @return
     */
    @RequestMapping(params = "bdSelector")
    public ModelAndView bdSelector(HttpServletRequest request) {
        return new ModelAndView("bsdt/applyform/estate/wtBdSelector");
    }
    
    /**
     * 描述 调用城信所接口获取标的物信息 
     * @author Keravon Feng
     * @created 2019年1月16日 下午4:40:49
     * @param request
     * @param response
     */
    @RequestMapping(params = "bdDatagrid")
    public void bdDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        int ipage = Integer.parseInt(page!=null?page:"1");
        if(ipage ==0){
            ipage = 1;
        }
        int length = Integer.parseInt(rows!=null?rows:"30");
        int start = (ipage - 1)*length;
        String bdcdyh = request.getParameter("bdcdyh");
        String fwbm = request.getParameter("fwbm");
        String bdcqzh = request.getParameter("bdcqzh");
        if(bdcqzh != null && StringUtils.isNotEmpty(bdcqzh)){
            bdcqzh = bdcqzh.replace("&#40;", "(");
            bdcqzh = bdcqzh.replace("&#41;", ")");
            bdcqzh = bdcqzh.replace("&#42;", "*");
        }
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("bdcdyh", bdcdyh!=null?bdcdyh:"");
        param.put("fwbm", fwbm!=null?fwbm:"");
        param.put("bdcqzh", bdcqzh!=null?bdcqzh:"");
        AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(param, "entrusteUrl");
        List<Map> jsons = new ArrayList<Map>();
        Object[] list = null;
        if(ajaxJson.isSuccess()){
            if(StringUtils.isNotEmpty(ajaxJson.getJsonString()) && ajaxJson.getJsonString() !=""){
                jsons = JSON.parseArray(ajaxJson.getJsonString(), Map.class);
                if(jsons.size() > length){
                    if(ipage > jsons.size()/length){
                        list = new Object[jsons.size() % length];
                        System.arraycopy(jsons.toArray(), start, list, 0, jsons.size() % length); 
                    }else{
                        list = new Object[length];
                        System.arraycopy(jsons.toArray(), start, list, 0, length); 
                    }
                }else{
                    list = new Object[jsons.size()];
                    System.arraycopy(jsons.toArray(), start, list, 0, jsons.size()); 
                }
                for(Object map : list){
                    ((Map)map).put("OB_ID", DigestUtils.md5Hex(JSON.toJSONString(map)));
                }
            }
        }
        this.setListToJsonString(jsons != null ? jsons.size() : 0, 
                list != null ? Arrays.asList(list) : new ArrayList(),
                null, JsonUtil.EXCLUDE, response); 
    }
    
    
    /**
     * 跳转到不动产档案信息查询选择器页面
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcDocInfoSelector")
    public ModelAndView bdcdaxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        //1表示全字段返回
        String isAllClo = request.getParameter("isAllClo");
        if(isAllClo == null){
            isAllClo = "0";
        }
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("isAllClo", isAllClo);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("bsdt/applyform/estate/bdcDocInfoSelector");
    }

    /**
     * 跳转到不动产档案信息查询选择器页面
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcDocInfoSelectorWw")
    public ModelAndView bdcdaxxcxSelectorWw(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        //1表示全字段返回
        String isAllClo = request.getParameter("isAllClo");
        if(isAllClo == null){
            isAllClo = "0";
        }
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("isAllClo", isAllClo);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("website/applyforms/bdcqlc/selector/bdcDocInfoSelectorWw");
    }
    
    /**
     * 描述 不动产抵押档案查询选择器页面
     * @author Keravon Feng
     * @created 2019年3月7日 上午11:28:38
     * @param request
     * @return
     */
    @RequestMapping(params = "bdcDyInfoSelector")
    public ModelAndView bdcDyInfoSelector(HttpServletRequest request) {
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
        return new ModelAndView("bsdt/applyform/estate/bdcDyInfoSelector");
    }
    
    /**
     * 
     * 描述 不动产房屋状态检验
     * @author Keravon Feng
     * @created 2019年3月14日 下午2:39:38
     * @param request
     * @param response
     */
    @RequestMapping("/bdcFwztCheck")
    public void bdcFwztCheck(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String fwbm = variables.get("fwbm") == null ? "" : variables.get("fwbm").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(fwbm)){
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("bdcdyh", bdcdyh);
            param.put("fwbm", fwbm);
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(param, "fwztjyUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }
            result.put("success", true);
            result.put("data", list);
        }else{
            result.put("success", false);
            result.put("msg", "调用诚信所接口参数不能为空。");
        }
        String json = JSON.toJSONString(result);
        this.setJsonString(json, response);
    }


    
    /**
     * 描述 委托事项列表
     * @author Keravon Feng
     * @created 2019年3月22日 上午11:58:41
     * @param request
     * @param response
     */
    @RequestMapping(params = "wtItemsdatagrid")
    public void wtItemsdatagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        String typeCode = request.getParameter("typeCode");
        if(typeCode == null){
            typeCode = "wtitems";
        }  
        filter.addFilter("Q_T.TYPE_CODE_=", typeCode);
        filter.addSorted("D.DIC_SN","desc");
        filter.addSorted("D.CREATE_TIME","desc");
        filter.addSorted("T.CREATE_TIME","asc");
        filter.setPagingBean(null);
        List<Map<String, Object>> list = dictionaryService.findBySqlFilter(filter);
        this.setListToJsonString(list != null ? list.size() : 0, 
                list,null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 委托事项新增页面
     * @author Keravon Feng
     * @created 2019年3月22日 下午2:12:17
     * @param request
     * @return
     */
    @RequestMapping(params = "wtItemInfo")
    public ModelAndView wtItemInfo(HttpServletRequest request) {
        String typeCode = request.getParameter("typeCode");
        if(typeCode == null){
            typeCode = "wtitems";
        }
        Map<String,Object> dicType =  dicTypeService.getByTypeCode(typeCode);
        if(dicType != null){
            Map<String,Object> dictionary = new HashMap<String, Object>();
            dictionary.put("TYPE_ID", dicType.get("TYPE_ID"));
            dictionary.put("TYPE_CODE", dicType.get("TYPE_CODE"));
            dictionary.put("TYPE_NAME", dicType.get("TYPE_NAME"));
            request.setAttribute("dictionary", dictionary);
        }
        return new ModelAndView("bsdt/applyform/estate/wtItemInfo");
    }
    
    /**
     * 保存委托事项
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveWtItem")
    @ResponseBody
    public AjaxJson saveWtItem(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("DIC_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if(StringUtils.isEmpty(entityId)){
            boolean isExist1 = dictionaryService.isExist(request.getParameter("TYPE_ID"), 
                    request.getParameter("DIC_CODE"));
            if(isExist1){
                j.setSuccess(false);
                j.setMsg("该编码已存在,创建失败!");
                return j;
            }
            boolean isExist2 = bdcApplyService.isExist(request.getParameter("TYPE_ID"), 
                    request.getParameter("DIC_NAME"));
            if(isExist2){
                j.setSuccess(false);
                j.setMsg("该名称已存在,创建失败!");
                return j;
            }
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String typeId = (String) variables.get("TYPE_ID");
            int maxSn = dictionaryService.getMaxSn(typeId);
            variables.put("DIC_SN", maxSn+1);
            if(variables.get("DIC_CODE")==null){
                variables.put("DIC_CODE", maxSn+1);
            }
        }
        //获取字典类别
        Map<String,Object> dicType = dicTypeService.getByJdbc("T_MSJW_SYSTEM_DICTYPE",new String[]{"TYPE_ID"},
                new Object[]{variables.get("TYPE_ID")});
        variables.put("TYPE_CODE",dicType.get("TYPE_CODE"));
        dictionaryService.saveOrUpdate(variables, "T_MSJW_SYSTEM_DICTIONARY", entityId);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 跳转到房地产合同备案信息查询页面
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "fdchtbaxxcxSelector")
    public ModelAndView fdchtbaxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String callbackFn = request.getParameter("callbackFn");
        String isForWin = request.getParameter("isForWin");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("isKfsywsl", request.getParameter("isKfsywsl"));
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("bsdt/applyform/estate/fdchtbaxxcxSelector");
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
        return new ModelAndView("website/applyforms/bdcqlc/selector/fdchtbaxxcxSelectorWw");
    }

    /**
     * 描述:房屋单元信息查询页面
     *
     * @author Madison You
     * @created 2020/12/17 10:39:00
     * @param
     * @return
     */
    @RequestMapping(params = "fwdyxxcxSelector")
    public ModelAndView fwdyxxcxSelector(HttpServletRequest request) {
        String allowCount= request.getParameter("allowCount");
        String noAuth = request.getParameter("noAuth");
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("noAuth", noAuth);
        return new ModelAndView("bsdt/applyform/estate/fwdyxxcxSelector");
    }


    
    /**
     * 描述    Ajax获取房屋单元信息列表数据
     * @author Allin Lin
     * @created 2019年4月19日 下午4:17:47
     * @param request
     * @param response
     */
    @RequestMapping(params = "fwdyxxcxDatagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String bdcdyh = variables.get("bdcdyh") == null ? "" : variables.get("bdcdyh").toString();
        String fwbm = variables.get("fwbm") == null ? "" : variables.get("fwbm").toString();
        String bdcqzh = variables.get("bdcqzh") == null ? "" : variables.get("bdcqzh").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(bdcdyh)||StringUtils.isNotEmpty(fwbm) || StringUtils.isNotEmpty(bdcqzh)){
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "fwdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }            
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    社会限制人员校验接口
     * @author Allin Lin
     * @created 2019年5月14日 下午6:33:05
     * @param request
     * @param response
     */
    @RequestMapping(params="checkLimitPerson")
    public void checkLimitPerson(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String cxlist = variables.get("cxlist").toString();
        List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>(); 
        if(StringUtils.isNotEmpty(cxlist)){
            variables.remove("cxlist");
            variables.put("cxlist", JSONArray.parse(cxlist));
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "checkLimitPersonUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                list = JSON.parseObject(jsonString, List.class);
            }
        }
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    跳转至不动产预告登记界面
     * @author Allin Lin
     * @created 2019年4月22日 下午4:27:18
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
        request.setAttribute("allowCount", Integer.parseInt(allowCount));
        request.setAttribute("callbackFn", callbackFn);
        request.setAttribute("isForWin", isForWin);
        request.setAttribute("noAuth", noAuth);
        request.setAttribute("noLike", noLike);
        return new ModelAndView("bsdt/applyform/bdchfcqdj/bdcygdacxSelector");
    }

    /**
     * 描述:不动产查询  产权证号
     *
     * @author Madison You
     * @created 2019/12/24 11:13:00
     * @param
     * @return
     */
    @RequestMapping("/bdcdjzlcxbr")
    @ResponseBody
    public Map<String, Object> bdcdjzlcxbr(HttpServletRequest request, HttpServletResponse response) {
        String bscxmm = request.getParameter("bscxmm");
        String idCard = request.getParameter("idCard");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, Object> returnMap = new HashMap<>();
        Properties properties = FileUtil.readProperties("project.properties");
        String bdcdjzlcxUrl = properties.getProperty("BDCDJZLCX_BR_URL");
        String bdcdjzlcxToken = properties.getProperty("BDCDJZLCX_TOKEN");
        String verificationCode = createVerificationCode();
        String dealToken = "Bearer " + bdcdjzlcxToken;
        String url = bdcdjzlcxUrl + "ESTATE_PROPERTY_WORD_ID='" + bscxmm + "'";
        String json = "";
        String ptiiId = "";
        try{
            json = sendBdcQueryGet(url, dealToken);
        } catch (Exception e) {
            log.info("不动产登记资料查询获取数据出错，" + e.getMessage());
        }
        try{
            ptiiId = getIdentification(idCard, name, mobile , verificationCode);
            if (ptiiId != "") {
                request.getSession().setAttribute("ptiiId", ptiiId);
            }
        } catch (Exception e) {
            log.info("不动产登记资料查询身份认证出错," + e.getMessage());
        }
        variables.put("RESULT_JSON", json);
        variables.put("CODE", verificationCode);
        bdcApplyService.saveOrUpdateForAssignId(variables, "T_BSFW_IDENTIFICATION", ptiiId);
        returnMap.put("ptiiId", ptiiId);
        return returnMap;
    }

    /**
     * 描述:不动产登记资料查询厉害关系人查询（新）
     *
     * @author Madison You
     * @created 2020/7/22 10:20:00
     * @param
     * @return
     */
    @RequestMapping("/bdcdjzlcxbrNew")
    @ResponseBody
    public Map<String, Object> bdcdjzlcxbrNew(HttpServletRequest request, HttpServletResponse response) {
        String zjhm = request.getParameter("zjhm");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String bscxmm = request.getParameter("bscxmm");
        Map<String, Object> returnMap = new HashMap<>();
        String ptiiId = "";
        try{
            Properties properties = FileUtil.readProperties("project.properties");
            String brUrl = properties.getProperty("DSJ_GYW_BDCDJZLCX_BR");
            Map<String, String> headerMap = setBearHeaderMap();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("qzh", bscxmm);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            String result = "";
            result = HttpSendUtil.sendHttpPostJson(brUrl, headerMap, jsonArray.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                /*智慧岛数字认证开始*/
                ptiiId = digitalAuthentication(zjhm, name, mobile, request);
                returnMap.put("ptiiId", ptiiId);
                /*解析结果信息*/
                parseResultInfo(result, returnMap, ptiiId);
            }
        } catch (Exception e) {
            log.error("获取不动产登记资料查询厉害关系人查询信息出错，PTII_ID为：" + ptiiId);
            log.error(e.getMessage(), e);
        }
        return returnMap;
    }

    /**
     * 描述:解析返回结果
     *
     * @author Madison You
     * @created 2020/7/22 13:13:00
     * @param
     * @return
     */
    private void parseResultInfo(String result , Map<String,Object> returnMap ,String ptiiId) {
        Map<String,Object> resultMap = JSON.parseObject(result);
        String code = StringUtil.getValue(resultMap, "code");
        if (StringUtils.isNotEmpty(code)) {
            returnMap.put("success", true);
            if (code.equals("01")) {
                List<Map<String, Object>> dataList = (List) resultMap.get("data");
                if (dataList != null && !dataList.isEmpty()) {
                    Map<String, Object> dataMap = dataList.get(0);
                    List<Map<String, Object>> cxjgList = (List) dataMap.get("cxjg");
                    if (cxjgList != null && !cxjgList.isEmpty()) {
                        for (Map<String, Object> cxjgMap : cxjgList) {
                            String ZDT = (String) cxjgMap.get("ZDT");
                            if (StringUtils.isNotEmpty(ZDT)) {
                                String zdtFileId = uploadBase64File(ZDT, ptiiId);
                                cxjgMap.put("ZDT_FILE_ID", zdtFileId);
                            }
                            String FHT = "";
                            List<String> FHTARR = (List) cxjgMap.get("FHT");
                            if (FHTARR != null && !FHTARR.isEmpty()) {
                                FHT = FHTARR.get(0);
                                if (StringUtils.isNotEmpty(FHT)) {
                                    String fhtFileId = uploadBase64File(FHT, ptiiId);
                                    cxjgMap.put("FHT_FILE_ID", fhtFileId);
                                }
                            }
                            cxjgMap.remove("ZDT");
                            cxjgMap.remove("FHT");
                            cxjgMap.put("PTII_ID", ptiiId);
                            bdcApplyService.saveOrUpdate(cxjgMap, "T_BSFW_IDENTIFICATION_DETAIL", null);
                        }
                    }
                }
            }
        } else {
            log.error("不动产登记资料查询接口出错，返回数据为：" + result);
            returnMap.put("success", false);
        }
    }

    /**
     * 描述：不动产登记资料查询验证码认证
     *
     * @author Madison You
     * @created 2020/3/9 15:57:00
     * @param
     * @return
     */
    @RequestMapping("/testingVerificationCode")
    @ResponseBody
    public Map<String, Object> testingVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        String ptiiId = (String) request.getSession().getAttribute("ptiiId");
        String verificationCode = request.getParameter("verificationCode");
        Map<String, Object> returnMap = new HashMap<>();
        List<Map<String,Object>> list = null;
        if (ptiiId != null) {
            try{
                Map<String, Object> idenMap = bdcApplyService.getByJdbc("T_BSFW_IDENTIFICATION",
                        new String[]{"PTII_ID"}, new Object[]{ptiiId});
                if (idenMap != null) {
                    String code = (String) idenMap.get("CODE");
                    String createTime = (String) idenMap.get("CREATE_TIME");
                    String today = DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日");
                    idenMap.put("TODAY", today);
                    returnMap.put("idenMap", idenMap);
                    if (verificationCode.equals(code)) {
                        Date createDate = DateTimeUtil.format(createTime, "yyyy-MM-dd HH:mm:ss");
                        Date currentDate = new Date();
                        long seconds = (currentDate.getTime() - createDate.getTime()) / 60 / 1000;
                        if (seconds < 6) {
                            List<Map<String,Object>> detailList = bdcApplyService.getAllByJdbc(
                                    "T_BSFW_IDENTIFICATION_DETAIL", new String[]{"PTII_ID"}, new Object[]{ptiiId});
                            if (detailList != null && !detailList.isEmpty()) {
                                returnMap.put("success", true);
                                list = detailList;
                            } else {
                                returnMap.put("success", true);
                                returnMap.put("msg", "暂无不动产信息");
                            }
                        } else {
                            returnMap.put("success", false);
                            returnMap.put("msg", "验证码超时，请重新查询");
                        }
                    } else {
                        returnMap.put("success", false);
                        returnMap.put("msg", "验证码输入错误");
                    }
                } else {
                    returnMap.put("success", false);
                    returnMap.put("msg", "系统错误，请联系技术人员");
                }
            } catch (Exception e) {
                log.error("不动产登记资料查询验证码认证出错,PTII_ID为：" + ptiiId);
                log.error(e.getMessage(), e);
                returnMap.put("success", false);
                returnMap.put("msg", "系统错误，请联系技术人员");
            }
        }
        returnMap.put("data", list);
        return returnMap;
    }


    /**
     * 描述:身份认证展示查询数据
     *
     * @author Madison You
     * @created 2019/12/29 10:34:00
     * @param
     * @return
     */
    @RequestMapping("/identificationForData")
    @ResponseBody
    public List<Map<String, Object>> identificationForData(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String,Object>> list = null;
        String ptiiId = request.getParameter("ptiiId");
        Map<String, Object> idenMap = bdcApplyService.getByJdbc("T_BSFW_IDENTIFICATION",
                new String[]{"PTII_ID","IS_SUCCESS"}, new Object[]{ptiiId,"1"});
        if (idenMap != null) {
            String json = (String)idenMap.get("RESULT_JSON");
            if (json != null) {
                list = parseBdcdjzlcxbrStr(json);
            }
        }
        return list;
    }


    /**
     * 描述:不动产查询  身份证
     *
     * @author Madison You
     * @created 2019/12/25 10:10:00
     * @param
     * @return
     */
    @RequestMapping("/bdcdjzlcxlhr")
    @ResponseBody
    public Map<String, Object> bdcdjzlcxlhr(HttpServletRequest request, HttpServletResponse response) {
        String zjlx = request.getParameter("zjlx");
        String zjhm = request.getParameter("zjhm");
        String idCard = request.getParameter("idCard");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        HashMap<String, Object> returnMap = new HashMap<>();
        HashMap<String, Object> variables = new HashMap<>();
        Properties properties = FileUtil.readProperties("project.properties");
        String bdcdjzlcxUrl = properties.getProperty("BDCDJZLCX_LHR_URL");
        String bdcdjzlcxToken = properties.getProperty("BDCDJZLCX_TOKEN");
        String verificationCode = createVerificationCode();
        String dealToken = "Bearer " + bdcdjzlcxToken;
        /*身份证类型对应*/
        String zjSort = identifyCompare(zjlx);
        String url = bdcdjzlcxUrl + "OWNER_CERT_ID='" + zjhm + "'&CERT_TYPE_ID='" + zjSort + "'";
        String json = "";
        String ptiiId = "";
        try{
            json = sendBdcQueryGet(url, dealToken);
        } catch (Exception e) {
            log.info("不动产登记资料查询获取数据出错，" + e.getMessage());
        }
        try{
            ptiiId = getIdentification(idCard, name, mobile, verificationCode);
            if (ptiiId != "") {
                request.getSession().setAttribute("ptiiId", ptiiId);
            }
        } catch (Exception e) {
            log.info("不动产登记资料查询身份认证出错," + e.getMessage());
        }
        variables.put("RESULT_JSON", json);
        variables.put("CODE", verificationCode);
        bdcApplyService.saveOrUpdateForAssignId(variables, "T_BSFW_IDENTIFICATION", ptiiId);
        returnMap.put("ptiiId", ptiiId);
        return returnMap;
    }

    /**
     * 描述:不动产登记资料查询个人身份证查询（新）
     *
     * @author Madison You
     * @created 2020/7/21 15:28:00
     * @param
     * @return
     */
    @RequestMapping("/bdcdjzlcxlhrNew")
    @ResponseBody
    public Map<String, Object> bdcdjzlcxlhrNew(HttpServletRequest request, HttpServletResponse response) {
        String zjhm = request.getParameter("zjhm");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        Map<String, Object> returnMap = new HashMap<>();
        String ptiiId = "";
        try{
            Properties properties = FileUtil.readProperties("project.properties");
            String lhrUrl = properties.getProperty("DSJ_GYW_BDCDJZLCX_LHR");
            Map<String, String> headerMap = setBearHeaderMap();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("qlrmc", name);
            jsonObject.put("qlrzjhm", zjhm);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            String result = "";
            result = HttpSendUtil.sendHttpPostJson(lhrUrl, headerMap, jsonArray.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                /*智慧岛数字认证开始*/
                ptiiId = digitalAuthentication(zjhm, name, mobile, request);
                returnMap.put("ptiiId", ptiiId);
                /*解析结果信息*/
                parseResultInfo(result, returnMap, ptiiId);
            }
        } catch (Exception e) {
            log.error("获取不动产登记资料查询个人身份证查询信息出错,PTIID_ID为：" + ptiiId);
            log.error(e.getMessage(), e);
            returnMap.put("success", false);
        }
        return returnMap;
    }

    /**
     * 描述:身份证类型对应
     *
     * @author Madison You
     * @created 2019/12/24 16:02:00
     * @param
     * @return
     */
    private String identifyCompare(String zjlx) {
        String zjSort = "";
        if (zjlx.equals("SF")) {
            zjSort = "1";
        } else if (zjlx.equals("TWTX") || zjlx.equals("HKSF") || zjlx.equals("AMSF") ||
                zjlx.equals("TWSF") || zjlx.equals("GATX")) {
            zjSort = "2";
        } else if (zjlx.equals("HZ")) {
            zjSort = "3";
        } else if (zjlx.equals("JG") || zjlx.equals("SB")) {
            zjSort = "5";
        } else if (zjlx.equals("JGDM")) {
            zjSort = "6";
        } else if (zjlx.equals("YYZZ")) {
            zjSort = "7";
        } else {
            zjSort = "99";
        }
        return zjSort;
    }

    /**
     * 描述:解析不动产登记资料查询json数据
     *
     * @author Madison You
     * @created 2019/12/24 14:22:00
     * @param
     * @return
     */
    private List<Map<String, Object>> parseBdcdjzlcxbrStr(String json) {
        List<Map<String, Object>> list = null;
        try{
            if (!json.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String success = jsonObject.getString("success");
                if (Boolean.parseBoolean(success)) {
                    String data = jsonObject.getString("data");
                    list = (List)JSONArray.parseArray(data);
                }
            }
        } catch (Exception e) {
            log.info("解析不动产登记资料查询json数据失败，" + e.getMessage());
        }
        return list;
    }

    /**
     * 描述:不动产查询  get
     *
     * @author Madison You
     * @created 2019/12/24 11:27:00
     * @param
     * @return
     */
    private String sendBdcQueryGet(String bdcUrl, String token) throws IOException {
        StringBuffer sbf= new StringBuffer();
        String strRead = null;
        BufferedReader reader = null;
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(bdcUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            if (token != null)
                connection.setRequestProperty("Authorization", token);
            connection.getInputStream();
            connection.connect();
            is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            log.info(sbf);
        } catch (Exception e) {
            log.info("不动产查询发送请求出错，" + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sbf.toString();
    }

    /**
     * 描述:不动产查询回调
     *
     * @author Madison You
     * @created 2019/12/29 9:16:00
     * @param
     * @return
     */
    @RequestMapping("/queryBdcCallBack")
    @ResponseBody
    public Map<String, Object> queryBdcCallBack(HttpServletRequest request) {
        String ptiiId = request.getParameter("ptiiId");
        String code = request.getParameter("code");
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, Object> returnMap = new HashMap<>();
        if (code.equals("0")) {
            variables.put("IS_SUCCESS", "1");
            bdcApplyService.saveOrUpdate(variables, "T_BSFW_IDENTIFICATION", ptiiId);
            returnMap.put("code", "00");
        } else {
            returnMap.put("code", "99");
        }
        return returnMap;
    }

    /**
     * 描述:保存智慧岛活体认证
     *
     * @author Madison You
     * @created 2020/7/22 13:08:00
     * @param
     * @return
     */
    private String digitalAuthentication(String zjhm , String name , String mobile , HttpServletRequest request) {
        /*智慧岛数字认证开始*/
        String verificationCode = createVerificationCode();
        String ptiiId = getIdentification(zjhm, name, mobile, verificationCode);
        if (StringUtils.isNotEmpty(ptiiId)) {
            request.getSession().setAttribute("ptiiId", ptiiId);
        }
        Map<String, Object> variables = new HashMap<>();
        variables.put("CODE", verificationCode);
        variables.put("ZJHM", zjhm);
        variables.put("NAME", name);
        variables.put("MOBILE", mobile);
        bdcApplyService.saveOrUpdateForAssignId(variables, "T_BSFW_IDENTIFICATION", ptiiId);
        /*智慧岛数字认证结束*/
        return ptiiId;
    }


    /**
     * 描述:获取智慧岛活体身份认证返回信息
     *
     * @author Madison You
     * @created 2019/12/29 9:18:00
     * @param
     * @return
     */
    private String getIdentification(String idCard , String name , String mobile,String verificationCode) {
        Properties properties = FileUtil.readProperties("project.properties");
        String url = properties.getProperty("BDC_QUERY_IDENTIFICATION_URL");
        String appid = properties.getProperty("BDC_QUERY_IDENTIFICATION_APPID");
        String callback = properties.getProperty("BDC_QUERY_IDENTIFICATION_CALLBACK");
        String key = properties.getProperty("BDC_QUERY_IDENTIFICATION_KEY");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String applicationTime = df.format(new Date());
        String expirationTime = bdcApplyService.addDateStr(applicationTime, 300000);
        JSONObject dataJson = new JSONObject();
        dataJson.put("appId", appid);
        dataJson.put("code", verificationCode);
        dataJson.put("applicationTime", applicationTime);
        dataJson.put("idCard", idCard);
        dataJson.put("expirationTime", expirationTime);
        dataJson.put("name", name);
        dataJson.put("mobile", mobile);
        dataJson.put("callbackURL", callback);
        dataJson.put("handleBusiness", "不动产登记查询");
        dataJson.put("anenum", "SMS_VERIFICATION");
        String sign = EncryptUtils.buildSign(dataJson, key);
        String dataEncrypt = AesUtils.encryptToBase64(dataJson.toString(), key);
        JSONObject json = new JSONObject();
        json.put("appId", appid);
        json.put("sign", sign);
        json.put("data", dataEncrypt);
        String result = "";
        try{
            result = HttpSendUtil.sendHttpsPostJson(url, null, json.toString(), "utf-8");
        } catch (Exception e) {
            log.error("智慧岛数字认证出错");
        }
        JSONObject resultJson = JSONObject.parseObject(result);
        String code = resultJson.getString("code");
        String ptiiId = "";
        if (code.equals("0")) {
            ptiiId = resultJson.getString("ptiiId");
        }
        return ptiiId;
    }
    /**
     * 描述 网上审批系统材料打印
     * @author Scolder Lin
     * @created 2019年8月13日 下午4:03:12
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "printSmartForm")
    public ModelAndView printSmartForm(HttpServletRequest request) {
        Map<String, Object> templateData = new HashMap<String, Object>();
        String instCode = request.getParameter("instCode");
        Properties properties = FileUtil.readProperties("conf/config.properties");
        
        String invokeUrl = properties.getProperty("invoke_url");
        invokeUrl+="?servicecode=print_template_data&grantcode=EvehXSKZXvCom1bMaLYLx&instCode="+instCode;
        
        String result =  HttpRequestUtil.sendPost(invokeUrl, null);

        String templatePath = request.getParameter("TemplatePath");
        String templateName = request.getParameter("TemplateName");
        Map<String,Object> mapResult = JSON.parseObject(result, Map.class);
        if(mapResult.get("success") !=null && "true".equals(mapResult.get("success").toString())) {
            Map<String,Object> dataMap = (Map<String, Object>) mapResult.get("data");
            
            if(dataMap.get("PRINT_TEMPLATE") != null) {
                templatePath = dataMap.get("PRINT_TEMPLATE").toString();
            }
            
            if(dataMap.get("PRINT_NAME") != null) {
                templateName = dataMap.get("PRINT_NAME").toString();
            }
            
            if(dataMap.get("PRINT_DATA") != null) {
                templateData = (Map<String, Object>)dataMap.get("PRINT_DATA");
            }
        }
        
        templateData.put("TemplatePath", templatePath);
        templateData.put("TemplateName", templateName);
        request.setAttribute("TemplateData", templateData);
        return new ModelAndView("smartform/print/printSmartForm");
    }

    /**
     * 描述:生成6位随机数（验证码）
     *
     * @author Madison You
     * @created 2020/3/9 15:37:00
     * @param
     * @return
     */
    public String createVerificationCode() {
        int v = (int)((Math.random() * 9 + 1)*100000);
        return Integer.toString(v);
    }

    /**
     * 描述:查看附图页面
     *
     * @author Madison You
     * @created 2020/7/21 12:59:00
     * @param
     * @return
     */
    @RequestMapping(params = "lookBdcImgView")
    public ModelAndView lookBdcImgView(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,Object> requestMap = new HashMap<>();
        Map<String,Object> map = bdcApplyService.getByJdbc("T_BSFW_IDENTIFICATION_DETAIL",
                new String[]{"ID"}, new Object[]{id});
        if (map != null) {
            requestMap.put("ADDRESS", StringUtil.getValue(map,"ADDRESS"));
            requestMap.put("id", id);
            String zdtFileId = StringUtil.getValue(map, "ZDT_FILE_ID");
            String fhtFileId = StringUtil.getValue(map, "FHT_FILE_ID");
            if (StringUtils.isNotEmpty(zdtFileId) || StringUtils.isNotEmpty(fhtFileId)) {
                requestMap.put("isImg", true);
                if (StringUtils.isNotEmpty(zdtFileId)) {
                    String zdtFileUrl = bdcApplyService.getBdcImgFullUrl(zdtFileId);
                    if (StringUtils.isNotEmpty(zdtFileUrl)) {
                        requestMap.put("zdtFileUrl", zdtFileUrl);
                    }
                }
                if (StringUtils.isNotEmpty(fhtFileId)) {
                    String fhtFileUrl = bdcApplyService.getBdcImgFullUrl(fhtFileId);
                    if (StringUtils.isNotEmpty(fhtFileUrl)) {
                        requestMap.put("fhtFileUrl", fhtFileUrl);
                    }
                }
            } else {
                requestMap.put("isImg", false);
            }
        }
        request.setAttribute("requestMap", requestMap);
        return new ModelAndView("site/bdc/info/lookBdcImgView");
    }

    /**
     * 描述:下载不动产查询文件
     *
     * @author Madison You
     * @created 2020/7/20 9:32:00
     * @param
     * @return
     */
    @RequestMapping("/downLoadBdcQueryFile")
    public void downLoadBdcQueryFile(HttpServletRequest request, HttpServletResponse response) {
        String ptiiId = (String) request.getSession().getAttribute("ptiiId");
        String cxyt = request.getParameter("cxyt");
        Map<String,Object> returnMap = new HashMap<>();
        String uuId = UUIDGenerator.getUUID();
        String templateWord = "";
        try{
            Properties properties = FileUtil.readProperties("project.properties");
            String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
            String currentDate = "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
            String uploadFullPath = "attachFiles/pdf/" + currentDate;
            // 建立全路径目录和临时目录
            File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
            if (!pdfFullPathFolder.exists()) {
                pdfFullPathFolder.mkdirs();
            }
            if (StringUtils.isNotEmpty(ptiiId) && StringUtils.isNotEmpty(cxyt)) {
                /*保存查询用途*/
                Map<String,Object> saveMap = new HashMap<>();
                saveMap.put("CXYT", cxyt);
                bdcApplyService.saveOrUpdate(saveMap, "T_BSFW_IDENTIFICATION", ptiiId);
                Map<String, Object> idenMap = bdcApplyService.getByJdbc("T_BSFW_IDENTIFICATION",
                        new String[]{"PTII_ID"}, new Object[]{ptiiId});
                if (idenMap != null) {
                    returnMap.put("bdcName", StringUtil.getValue(idenMap, "NAME"));
                    returnMap.put("bdcCardNo", StringUtil.getValue(idenMap, "ZJHM"));
                    String today = DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日");
                    returnMap.put("bdcDate", today);
                    returnMap.put("bdcReason", cxyt);
                    bdcApplyService.createBdcQueryQrcode(idenMap, returnMap, 2);
                    List<Map<String,Object>> detailList = bdcApplyService.getAllByJdbc("T_BSFW_IDENTIFICATION_DETAIL",
                            new String[]{"PTII_ID"}, new Object[]{ptiiId});
                    if (detailList != null && !detailList.isEmpty()) {
                        List<Map<String,Object>> bdcList = new ArrayList<>();
                        returnMap.put("bdcList", bdcList);
                        templateWord = "attachFiles//readtemplate//files//BDCQUERYYF.docx";
                        int bdcxh = 0;
                        for (Map<String, Object> detailMap : detailList) {
                            Map<String, Object> bdcMap = new HashMap<>();
                            bdcMap.put("bdcxh", ++bdcxh);
                            bdcMap.put("bdccqlx", StringUtil.getValue(detailMap, "ZSLX"));
                            bdcMap.put("bdccqzt", StringUtil.getValue(detailMap, "ZT"));
                            bdcMap.put("bdcqlr", StringUtil.getValue(detailMap, "QLR"));
                            bdcMap.put("bdczl", StringUtil.getValue(detailMap, "ADDRESS"));
                            bdcMap.put("bdczh", StringUtil.getValue(detailMap, "QZH"));
                            bdcMap.put("bdcdjsj", StringUtil.getValue(detailMap, "DJSJ"));
                            bdcMap.put("bdcmj", StringUtil.getValue(detailMap, "BUILDAREA"));
                            bdcMap.put("bdcyt", StringUtil.getValue(detailMap, "USEFACT"));
                            bdcMap.put("bdcqlxz", StringUtil.getValue(detailMap, "HOUSETYPE"));
                            bdcMap.put("bdcytqx", StringUtil.getValue(detailMap, "YTQX"));
                            bdcList.add(bdcMap);
                        }
                    } else {
                        templateWord = "attachFiles//readtemplate//files//BDCQUERYWF.docx";
                    }
                    String newWordPath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".docx";
                    String templateRealPath = AppUtil.getRealPath(templateWord);
                    WordReplaceUtil.replaceWord(returnMap, templateRealPath, newWordPath);
                    uploadFullPath += "/" + uuId + ".pdf";
                    WordToPdfUtil.word2pdf(newWordPath, pdfFile + uploadFullPath);
                    downLoadFile(response, uuId + ".pdf", pdfFile + uploadFullPath);
                }
            }
        } catch (Exception e) {
            log.error("下载不动产查询文件出错，PTII_ID为：" + ptiiId);
            log.error(e.getMessage(), e);
        }
    }

//    /**
//     * 描述:下载不动产查询文件新
//     *
//     * @author Madison You
//     * @created 2021/1/13 9:18:00
//     * @param
//     * @return
//     */
//    @RequestMapping("/downLoadBdcQueryFileNew")
//    public void downLoadBdcQueryFileNew(HttpServletRequest request, HttpServletResponse response) {
//        String ptiiId = (String) request.getSession().getAttribute("ptiiId");
//        String cxyt = request.getParameter("cxyt");
//        Properties properties = FileUtil.readProperties("project.properties");
//        String url = properties.getProperty("BDC_QUERY_DOWNLOAD_URL");
//        try{
//            if (StringUtils.isNotEmpty(ptiiId) && StringUtils.isNotEmpty(cxyt)) {
//                Map<String,Object> saveMap = new HashMap<>();
//                saveMap.put("CXYT", cxyt);
//                bdcApplyService.saveOrUpdate(saveMap, "T_BSFW_IDENTIFICATION", ptiiId);
//                Map<String, Object> loginMember = AppUtil.getLoginMember();
//                if (loginMember == null) {
//                    MaterDownloadUtil.downloadReturnMsg(response, "请登录后再下载");
//                }
//                String userType = StringUtil.getValue(loginMember, "USER_TYPE");
//                String yhzt = StringUtil.getValue(loginMember, "YHZT");
//                if (!Objects.equals(userType, "1")) {
//                    MaterDownloadUtil.downloadReturnMsg(response, "请用个人用户登录下载");
//                }
//                if (!Objects.equals(yhzt, "1")) {
//                    MaterDownloadUtil.downloadReturnMsg(response, "该用户已被禁用");
//                }
//                String zjhm = StringUtil.getValue(loginMember, "ZJHM");
//                String yhmc = StringUtil.getValue(loginMember, "YHMC");
//                Map<String,Object> senMap = new HashMap<>();
//                senMap.put("idCard", zjhm);
//                senMap.put("name", yhmc);
//                senMap.put("typeCode", "111");
//                senMap.put("kzYy", cxyt);
//                JSONObject json = new JSONObject(senMap);
//                Map<String, String> headerMap = ybStampService.setTencentNetHeadMap();
//                String result = HttpSendUtil.sendHttpsPostJson(url, headerMap,
//                        json.toJSONString(), "UTF-8");
//                if (StringUtils.isNotEmpty(result)) {
//                    Map<String, Object> resultMap = (Map) JSON.parse(result);
//                    String code = StringUtil.getValue(resultMap, "code");
//                    if (Objects.equals(code, "01")) {
//                        String data = StringUtil.getValue(resultMap, "data");
//                        if (StringUtils.isNotEmpty(data)) {
//                            Map<String, Object> dataMap = (Map) JSON.parse(data);
//                            String content = StringUtil.getValue(dataMap, "content");
//                            String fileType = StringUtil.getValue(dataMap, "fileType");
//                            MaterDownloadUtil.downLoadFileBase64(response, content, "不动产证明." + fileType);
//                        }
//                    } else {
//                        MaterDownloadUtil.downloadReturnMsg(response, StringUtil.getValue(resultMap, "message"));
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            log.error("下载不动产查询文件出错，PTII_ID为：" + ptiiId);
//            log.error(e.getMessage(), e);
//        }
//        MaterDownloadUtil.downloadReturnMsg(response, "系统错误，请联系管理员");
//    }

    /**
     * 描述:下载不动产登记资料查询附图文件
     *
     * @author Madison You
     * @created 2020/7/21 21:18:00
     * @param
     * @return
     */
    @RequestMapping("/downLoadBdcQueryFtFile")
    public void downLoadBdcQueryFtFile(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("bdcFhtImg", "");
        returnMap.put("bdcZdtImg", "");
        try{
            Map<String, Object> detailMap = bdcApplyService.getByJdbc("T_BSFW_IDENTIFICATION_DETAIL",
                    new String[]{"ID"}, new Object[]{id});
            if (detailMap != null) {
                String uuId = UUIDGenerator.getUUID();
                Properties properties = FileUtil.readProperties("project.properties");
                String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
                String currentDate = "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
                String uploadFullPath = "attachFiles/pdf/" + currentDate;
                // 建立全路径目录和临时目录
                File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
                if (!pdfFullPathFolder.exists()) {
                    pdfFullPathFolder.mkdirs();
                }
                String zdtFileId = StringUtil.getValue(detailMap, "ZDT_FILE_ID");
                bdcApplyService.fillBdcQueryImg(returnMap, zdtFileId, "bdcZdtImg");
                String fhtFileId = StringUtil.getValue(detailMap, "FHT_FILE_ID");
                bdcApplyService.fillBdcQueryImg(returnMap, fhtFileId, "bdcFhtImg");
                returnMap.put("bdczl", StringUtil.getValue(detailMap, "ADDRESS"));
                bdcApplyService.createBdcQueryQrcode(detailMap, returnMap,1);
                String templateWord = "attachFiles//readtemplate//files//BDCQUERYFT.docx";
                String newWordPath = pdfFile + uploadFullPath + "/" + UUIDGenerator.getUUID() + ".docx";
                String templateRealPath = AppUtil.getRealPath(templateWord);
                WordReplaceUtil.replaceWord(returnMap, templateRealPath, newWordPath);
                uploadFullPath += "/" + uuId + ".pdf";
                WordToPdfUtil.word2pdf(newWordPath, pdfFile + uploadFullPath);
                downLoadFile(response, uuId + ".pdf", pdfFile + uploadFullPath);
            }
        } catch (Exception e) {
            log.error("下载不动产登记资料查询附图文件出错，ID为：" + id);
            log.error(e.getMessage(), e);
        }

    }



    /**
     * 描述:下载文件
     *
     * @author Madison You
     * @created 2020/7/20 15:09:00
     * @param
     * @return
     */
    public void downLoadFile(HttpServletResponse response, String fileName, String newPdfFilePath) {
        OutputStream toClient = null;
        InputStream fis  = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(newPdfFilePath);
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(newPdfFilePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(fileName.getBytes("gbk"), "iso8859-1") + "\"");
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-msdownload");

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            log.info("取消下载或下载异常");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (null != toClient) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 描述:设置大数据接口通用请求头
     *
     * @author Madison You
     * @created 2020/7/21 15:35:00
     * @param
     * @return
     */
    private void setDsjUsualHeaderMap(Map<String, String> headerMap) {
        String str = new Date().getTime()/1000 + "vkpfWU4Rcz2Ba8lwJ5RpAfMCyE2GXMtR" +
                "123456789abcdefg" +new Date().getTime()/1000;
        String signature = "";
        try{
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            signature = Hex.encodeHexString(instance.digest(str.getBytes()));
        } catch (Exception e) {
            log.error("大数据接口加密错误");
        }
        headerMap.put("x-tif-paasid","bigdatainter");
        headerMap.put("x-tif-timestamp", new Date().getTime() / 1000 + "");
        headerMap.put("x-tif-signature",signature);
        headerMap.put("x-tif-nonce","123456789abcdefg");
        headerMap.put("re-app", "民生服务与资格资质管理处(不动产登记交易科)");
    }

    /**
     * 描述:获取大数据BEAR请求头,大数据接口登录
     *
     * @author Madison You
     * @created 2020/7/21 15:35:00
     * @param
     * @return
     */
    private Map<String, String> setBearHeaderMap() {
        Map<String,String> headMap = new HashMap<>();
        Map<String,String> tokenHeadMap = new HashMap<>();
        JSONObject json = new JSONObject();
        Properties properties = FileUtil.readProperties("project.properties");
        String SSDJ_LOGIN_USERNAME = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String SSDJ_LOGIN_PASSWORD = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        String DSJ_GYW_LOGIN_URL = properties.getProperty("DSJ_GYW_LOGIN_URL");
        setDsjUsualHeaderMap(headMap);
        json.put("username", SSDJ_LOGIN_USERNAME);
        json.put("password", SSDJ_LOGIN_PASSWORD);
        String result = "";
        try{
            result = HttpSendUtil.sendHttpPostJson(DSJ_GYW_LOGIN_URL, headMap, json.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                JSONObject resultJson = JSONObject.parseObject(result);
                String token = resultJson.getString("token");
                if (StringUtils.isNotEmpty(token)) {
                    setDsjUsualHeaderMap(tokenHeadMap);
                    tokenHeadMap.put("Authorization",token);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("大数据中心统一认证接口登录失败：" + result);
        }
        return tokenHeadMap;
    }

    /**
     * 描述:上传base64图片
     *
     * @author Madison You
     * @created 2020/7/21 16:49:00
     * @param
     * @return
     */
    public String uploadBase64File(String base64Code , String ptiiId) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String fileId = "";
        String unid = UUIDGenerator.getUUID();
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "bdcdjzlcx");// 上传人ID
                param.put("uploaderName", "不动产登记资料查询"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", unid + ".jpg");// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", "T_BSFW_IDENTIFICATION_DETAIL");// 业务表名
                param.put("busRecordId", ptiiId);// 业务表ID
                String result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode,
                        app_id, password, param);
                if (StringUtils.isNotEmpty(result)) {
                    Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                    Map<String,Object> data = (Map) resultMap.get("data");
                    fileId = (String) data.get("fileId");
                }
            } catch (Exception e) {
                log.error("上传不动产登记资料查询附件出错，PTII_ID为：" + ptiiId);
            }
        }
        return fileId;
    }
    
    /**
     * 描述    缴费成功调不动产缴费结果回传接口
     * @author Allin Lin
     * @created 2020年7月24日 上午10:55:12
     * @param request
     * @param response
     */
    @RequestMapping("/sendPayNotifyToBdc")
    public void sendPayNotifyToBdc(HttpServletRequest request, HttpServletResponse response) { 
        String Dci_id = request.getParameter("Dci_id");//不动产下单业务号
        String yjje = request.getParameter("yjje");//实际缴费金额
        String jfzt = request.getParameter("jfzt");//缴费状态
        Map<String, Object> result = new HashMap<String, Object>();
        String json = "";
        //判断数据合格性
        if(StringUtils.isEmpty(Dci_id)||StringUtils.isEmpty(yjje)||StringUtils.isEmpty(jfzt)){
            result.put("retcode", "00");
            json = JSON.toJSONString(result);
        }else{
            Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
            variables.put("jfzt", Integer.valueOf(jfzt));
            //info.put("paras", JSON.toJSONString(variables));
            //info.put("paras", variables);
            log.info("调用不动产缴费状态回传接口请求参数："+JSON.toJSONString(variables));
            AjaxJson ajaxJson=bdcQueryService.queryAjaxJsonOfBdc(variables, "bdcPayNotifyUrl");
            String jsonString = ajaxJson.getJsonString();
            /*String jsonString = "{\"retcode\":\"10\"}";*/
            log.info("调用不动产缴费状态回传接口返回结果："+jsonString);
            if (StringUtils.isNotEmpty(jsonString)) {
                json = jsonString;             
            }else{
                log.debug("调用不动产缴费状态回传接口失败：返回数据为空！");
            } 
        }
        this.setJsonString(json, response);
    }

    /**
     * 描述:登记数据监管
     *
     * @author Madison You
     * @created 2020/8/26 12:38:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcdjsjjgView")
    public ModelAndView bdcdjsjjgView(HttpServletRequest request) {
        return new ModelAndView("bdcqlc/bdcdjsjjg/bdcdjsjjgView");
    }
    
    /**
     * 描述:登记在线监管
     *
     * @author Madison You
     * @created 2020/8/26 12:39:00
     * @param 
     * @return 
     */
    @RequestMapping(params = "bdcdjsjjgImgView")
    public ModelAndView bdcdjsjjgImgView(HttpServletRequest request) {
        try{
            String pastSevenDate = DateTimeUtil.getPastDate(7);
            String pastOneDate = DateTimeUtil.getPastDate(1);
            /*总体情况*/
            List<Map<String,Object>> ztqkList = bdcApplyService.getDjsjjgZtqkStatis(request);
            /*收件量和受理量*/
            Integer sjlCount = bdcApplyService.getDjsjjgBsxnStatis("", "", "");
            /*即办件（按时）*/
            Integer jbjasCount = bdcApplyService.getDjsjjgBsxnStatis(" and B.SXLX = '1' AND D.EFFI_DESC IN ('1','2') ", "", "");
            /*即办件（逾期）*/
            Integer jbjyqCount = bdcApplyService.getDjsjjgBsxnStatis(" and B.SXLX = '1' AND D.EFFI_DESC IN ('3') ", "", "");
            /*承诺件（按时）*/
            Integer cnjasCount = bdcApplyService.getDjsjjgBsxnStatis(" and B.SXLX = '2' AND D.EFFI_DESC IN ('1','2') ", "", "");
            /*承诺件（逾期）*/
            Integer cnjyqCount = bdcApplyService.getDjsjjgBsxnStatis(" and B.SXLX = '2' AND D.EFFI_DESC IN ('3') ", "", "");
            /*网上申请量*/
            Integer wssqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('1','3') ", "", "");
            /*窗口申请量*/
            Integer cksqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('2') ", "", "");
            /*近七天每天日期*/
            ArrayList<String> pastWeekDate = DateTimeUtil.getPastWeekDate(7);
            /*近七天受理量*/
            List<Integer> jqtCountList = bdcApplyService.getDjsjjgJqtsllStatis(pastWeekDate, "", "");
            /*近七天评价*/
            Map<String, Object> pjCountMap = bdcApplyService.getDjsjjgFwpjStatis(request, "", "");
            /*近七天平均受理时长*/
            List<Integer> jqtSlscCountList = bdcApplyService.getDjsjjgJqtslscStatis(pastWeekDate);
            /*仅七天平均办结时长*/
            List<Integer> jqtBjscCountList = bdcApplyService.getDjsjjgJqtbjscStatis(pastWeekDate);
            request.setAttribute("date", (pastSevenDate + "———" + pastOneDate).replaceAll("-", "."));
            request.setAttribute("ztqkList", ztqkList);
            request.setAttribute("ztqkJson", JSONArray.toJSONString(ztqkList));
            request.setAttribute("sjlCount", sjlCount);
            request.setAttribute("jbjasCount", jbjasCount);
            request.setAttribute("jbjyqCount", jbjyqCount);
            request.setAttribute("cnjasCount", cnjasCount);
            request.setAttribute("cnjyqCount", cnjyqCount);
            request.setAttribute("wssqPersent", Math.round(Double.valueOf(wssqCount) / Double.valueOf(sjlCount) * 100));
            request.setAttribute("cksqPersent", Math.round(Double.valueOf(cksqCount) / Double.valueOf(sjlCount) * 100));
            request.setAttribute("pastWeekDate", JSONArray.toJSONString(pastWeekDate));
            request.setAttribute("jqtCountList", JSONArray.toJSONString(jqtCountList));
            request.setAttribute("pjCountMap", pjCountMap);
            request.setAttribute("jqtSlscCountList", jqtSlscCountList);
            request.setAttribute("jqtBjscCountList", jqtBjscCountList);
        } catch (Exception e) {
            log.error("不动产登记在线监管统计报表读取错误");
            log.error(e.getMessage(), e);
        }
        return new ModelAndView("bdcqlc/bdcdjsjjg/bdcdjsjjgImgView");
    }

    /**
     * 描述:事项统计表
     *
     * @author Madison You
     * @created 2020/10/26 14:34:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcsxtjbView")
    public ModelAndView bdcsxtjbView(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        String pastSevenDate = DateTimeUtil.getPastDate(7);
        String pastOneDate = DateTimeUtil.getPastDate(1);
        List<Map<String,Object>> bdcsxtjbItemList = bdcApplyService.getBdcsxtjbItems();
        request.setAttribute("date", (pastSevenDate + "———" + pastOneDate).replaceAll("-", "."));
        request.setAttribute("itemList", bdcsxtjbItemList);
        request.setAttribute("itemCode", itemCode);
        return new ModelAndView("bdcqlc/bdcdjsjjg/bdcsxtjbView");
    }

    /**
     * 描述:事项统计表数据
     *
     * @author Madison You
     * @created 2020/10/26 14:54:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcsxtjbData")
    @ResponseBody
    public Map<String, Object> bdcsxtjbData(HttpServletRequest request) {
        String itemCode = request.getParameter("itemCode");
        Map<String,Object> returnMap = new HashMap<>();
        try{
            /*获取事项名称*/
            Map<String, Object> itemMap = bdcApplyService.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"},
                    new Object[]{itemCode});
            /*收件量和受理量*/
            Integer sjlCount = bdcApplyService.getDjsjjgBsxnStatis("", itemCode, "");
            /*网上申请量*/
            Integer wssqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('1','3') ", itemCode, "");
            /*窗口申请量*/
            Integer cksqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('2') ", itemCode, "");
            /*近七天每天日期*/
            ArrayList<String> pastWeekDate = DateTimeUtil.getPastWeekDate(7);
            /*近七天受理量*/
            List<Integer> jqtCountList = bdcApplyService.getDjsjjgJqtsllStatis(pastWeekDate, itemCode, "");
            /*近七天评价*/
            Map<String, Object> pjCountMap = bdcApplyService.getDjsjjgFwpjStatis(request, itemCode, "");
            /*叫号状态*/
            Map<String, Integer> callMap = bdcApplyService.getBdcsxtjbJhzt(itemCode, "");
            /*个人近七天办件量*/
            ArrayList<ArrayList<Object>> grSllyCountList = bdcApplyService.getBdcsxtjbGrslly(pastWeekDate,itemCode);
            returnMap.put("pastWeekDate", pastWeekDate);
            returnMap.put("jqtCountList", jqtCountList);
            returnMap.put("pjCountMap", pjCountMap);
            returnMap.put("sjlCount", sjlCount);
            returnMap.put("callMap", callMap);
            returnMap.put("itemName", StringUtil.getValue(itemMap, "ITEM_NAME"));
            if (sjlCount != 0) {
                returnMap.put("wssqPersent", Math.round(Double.valueOf(wssqCount) / Double.valueOf(sjlCount) * 100));
                returnMap.put("cksqPersent", Math.round(Double.valueOf(cksqCount) / Double.valueOf(sjlCount) * 100));
            } else {
                returnMap.put("wssqPersent", "0");
                returnMap.put("cksqPersent", "0");
            }
            returnMap.put("grSllyCountList", grSllyCountList);
        } catch (Exception e) {
            log.error("不动产事项统计表数据报表读取错误");
            log.error(e.getMessage(), e);
        }

        return returnMap;
    }

    /**
     * 描述:不动产经办人员办件统计表
     *
     * @author Madison You
     * @created 2020/10/28 9:59:00
     * @param 
     * @return 
     */
    @RequestMapping(params = "bdcjbrytjbView")
    public ModelAndView bdcjbrytjbView(HttpServletRequest request) {
        String pastSevenDate = DateTimeUtil.getPastDate(7);
        String pastOneDate = DateTimeUtil.getPastDate(1);
        List<Map<String, Object>> ryfzList = dictionaryService.findByTypeCode("RYFZ");
        Set<Map<String,Object>> userList = bdcApplyService.getUsersFromBdc();
        request.setAttribute("date", (pastSevenDate + "———" + pastOneDate).replaceAll("-", "."));
        request.setAttribute("ryfzList", ryfzList);
        request.setAttribute("userList", userList);
        request.setAttribute("userListFirst", userList);
        for (Map<String, Object> map : userList) {
            if (map != null) {
                request.setAttribute("userListFirst",map.get("USERNAME"));
                break;
            }
        }
        return new ModelAndView("bdcqlc/bdcdjsjjg/bdcjbrytjbView");
    }

    /**
     * 描述:不动产经办人员办件统计表数据
     *
     * @author Madison You
     * @created 2020/10/28 10:45:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcjbrytjbData")
    @ResponseBody
    public Map<String, Object> bdcjbrytjbData(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        String userName = request.getParameter("userName");
        /*收件量和受理量*/
        Integer sjlCount = bdcApplyService.getDjsjjgBsxnStatis("", "", userName);
        /*网上申请量*/
        Integer wssqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('1','3') ", "", userName);
        /*窗口申请量*/
        Integer cksqCount = bdcApplyService.getDjsjjgBsxnStatis(" and A.SQFS in ('2') ", "", userName);
        /*近七天评价*/
        Map<String,Object> pjCountMap = bdcApplyService.getDjsjjgFwpjStatis(request,"",userName);
        /*叫号状态*/
        Map<String, Integer> callMap = bdcApplyService.getBdcsxtjbJhzt("", userName);
        if (sjlCount != 0) {
            returnMap.put("wssqPersent", Math.round(Double.valueOf(wssqCount) / Double.valueOf(sjlCount) * 100));
            returnMap.put("cksqPersent", Math.round(Double.valueOf(cksqCount) / Double.valueOf(sjlCount) * 100));
        } else {
            returnMap.put("wssqPersent", "0");
            returnMap.put("cksqPersent", "0");
        }
        List<Map<String, Object>> jbrsxtjCountList = bdcApplyService.getJbrsxtjList(userName);
        /*近七天每天日期*/
        ArrayList<String> pastWeekDate = DateTimeUtil.getPastWeekDate(7);
        /*近七天受理量*/
        List<Integer> jqtCountList = bdcApplyService.getDjsjjgJqtsllStatis(pastWeekDate, "", userName);
        returnMap.put("jbrsxtjCountList", jbrsxtjCountList);
        returnMap.put("pastWeekDate", pastWeekDate);
        returnMap.put("jqtCountList", jqtCountList);
        returnMap.put("pjCountMap", pjCountMap);
        returnMap.put("callMap", callMap);
        returnMap.put("sjlCount", sjlCount);
        return returnMap;
    }

    /**
     * 描述:办件列表页面
     *
     * @author Madison You
     * @created 2020/11/18 15:32:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcbjmxbView")
    public ModelAndView bdcbjmxbView(HttpServletRequest request) {
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        String pastSevenDate = DateTimeUtil.getPastDate(7);
        String pastOneDate = DateTimeUtil.getPastDate(1);
        List<Map<String, Object>> ryfzList = dictionaryService.findByTypeCode("RYFZ");
        List<Map<String, Object>> pjxxList = dictionaryService.findByTypeCode("PJXX");
        Set<Map<String,Object>> userList = bdcApplyService.getUsersFromBdc();
        request.setAttribute("dateStart", pastSevenDate.replaceAll("-","."));
        request.setAttribute("dateEnd", pastOneDate.replaceAll("-","."));
        request.setAttribute("ryfzList", ryfzList);
        request.setAttribute("pjxxList", pjxxList);
        request.setAttribute("userList", userList);
        request.setAttribute("userListFirst", userList);
        request.setAttribute("requestMap", JSON.toJSONString(requestMap));
        for (Map<String, Object> map : userList) {
            if (map != null) {
                request.setAttribute("userListFirst",map.get("USERNAME"));
                break;
            }
        }
        return new ModelAndView("bdcqlc/bdcdjsjjg/bdcbjmxbView");
    }

    /**
     * 描述:办件列表数据
     *
     * @author Madison You
     * @created 2020/11/18 17:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcbjmxbData")
    @ResponseBody
    public Map<String, Object> bdcbjmxbData(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        SqlFilter filter = new SqlFilter(request);
        Integer nowPage = Integer.parseInt(request.getParameter("page"));
        List<Map<String, Object>> exeList = bdcApplyService.getExeList(filter);
        returnMap.put("nowPage", nowPage);
        returnMap.put("pageSize", exeList.size());
        ArrayList<Integer> pageList = new ArrayList<>();
        int totalItems = filter.getPagingBean().getTotalItems();
        Integer pageSize = filter.getPagingBean().getPageSize();
        int maxPage = (totalItems - 1) / pageSize + 1;
        Integer j = 0;
        if (maxPage < 5) {
            for (int i = 1; i <= maxPage; i++) {
                pageList.add(i);
            }
        } else {
            if (nowPage == 1 || nowPage == 2) {
                j = 3;
            } else if (maxPage - nowPage <= 2){
                j = maxPage - 2;
            } else {
                j = nowPage;
            }
            for (int i = j - 2; i <= j + 2; i++) {
                pageList.add(i);
            }
        }

        returnMap.put("pageList", pageList);
        returnMap.put("exeList", exeList);
        returnMap.put("maxPage", maxPage);
        return returnMap;
    }

    /**
     * 描述:不动产历史遗留问题计算器页面
     *
     * @author Madison You
     * @created 2020/12/25 11:19:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcLsyljfjsView")
    public ModelAndView bdcLsyljfjsView(HttpServletRequest request) {
        List<Map<String, Object>> jsqbllx = dictionaryService.findByTypeCode("JSQBLLX");
        List<Map<String, Object>> jsqtdxz = dictionaryService.findByTypeCode("JSQTDXZ");
        List<Map<String, Object>> jsqtdyt = dictionaryService.findByTypeCode("JSQTDYT");
        List<Map<String, Object>> jsqtdzrlx = dictionaryService.findByTypeCode("JSQTDZRLX");
        request.setAttribute("jsqbllx", jsqbllx);
        request.setAttribute("jsqtdxz", jsqtdxz);
        request.setAttribute("jsqtdyt", jsqtdyt);
        request.setAttribute("jsqtdzrlx", jsqtdzrlx);
        return new ModelAndView("site/bdc/info/bdclsyljfjs");
    }

    /**
     * 描述:不动产历史遗留问题计算器
     *
     * @author Madison You
     * @created 2020/12/28 15:07:00
     * @param
     * @return
     */
    @RequestMapping(params = "bdcLandPrcCalculator")
    @ResponseBody
    public Map<String, Object> bdcLandPrcCalculator(HttpServletRequest request) {
        Map<String,Object> returnMap = new HashMap<>();
        String v = null;
        String w = null;
        Map<String, Object> requestMap = BeanUtil.getMapFromRequest(request);
        try{
            v = bdcApplyService.calLandPrc(requestMap);
            w = bdcApplyService.calLandPrcWjbj(requestMap);
        } catch (Exception e) {
            log.error("不动产地价计算器计算错误", e);
            returnMap.put("success", false);
        }
        returnMap.put("success", true);
        returnMap.put("value", v);
        returnMap.put("wjmjbj", w);
        return returnMap;
    }
}
