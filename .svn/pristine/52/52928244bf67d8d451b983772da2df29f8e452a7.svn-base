/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.tzxm.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.JsonUtil;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.tzxm.service.GovIvestService;

/**
 * 描述 投资项目控制层
 * 
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年5月31日 下午2:34:15
 */
@Controller
@RequestMapping("/govIvestController")
public class GovIvestController extends BaseController {
    /**
     * 初始化数据字典常量(在线审批树ID)
     */
    private static final String DIC_TYPE= "initGovIvestId";
    /**
     * 栏目名称(政策法规)
     */
    private static final String MUDULE_NAME_ZCFG= "重要文件";
    /**
     * 栏目名称(申报指引)
     */
    private static final String MUDULE_NAME_SBZY= "申报指引流程图";
    /**
     * 父级栏目名称
     */
    private static final String PARENT_NAME= "工程建设项目";
    /**
     * 初始化数据字典常量(流程实例状态)
     */
    //private static final String FLOW_STATUS= "FlowRunStatus";
    /**
     * 引入service
     */
    @Resource
    private GovIvestService govIvestService;
    /**
     * 引入数据字典service
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 跳转到项目登记详情页
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("tzxm/index");
    }
    
    /**
     * 建设列表数据
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping("/govIvestlList")
    public ModelAndView govIvestlList(HttpServletRequest request) {
        String categoryId = request.getParameter("id");
        String type = request.getParameter("type");
        if(categoryId==null || "".equals(categoryId)) {
            if(type !=null && !"".equals(type)) {
                if("1".equals(type)) {
                    categoryId = dictionaryService.getDicCode(DIC_TYPE, "政府投资项目审批");
                }else if("2".equals(type)) {
                    categoryId = dictionaryService.getDicCode(DIC_TYPE, "企业投资项目审核");
                }else {
                    categoryId = dictionaryService.getDicCode(DIC_TYPE, "企业投资项目备案");
                }
            }
        }
        List<Map<String, Object>> topList = govIvestService.getGovIvestTopList();
        List<Map<String, Object>> titleList  = govIvestService.getTitleListById(categoryId);
        List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
        if(titleList!=null && titleList.size()>0) {
            List<Map<String, Object>> centerList = new ArrayList<Map<String, Object>>();
            for(Map<String, Object> titleMap : titleList) {
                Map centerMap = new HashMap();
                String id = titleMap.get("ID").toString();
                List<Map<String, Object>> projectList  = govIvestService.getTitleListById(id);
                if(projectList!=null && projectList.size()>0) {
                    List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
                    int i = 1;
                    for(Map<String, Object> resultMap : projectList) {
                        if(i%2!=0) {
                            resultMap.put("bg", true);
                        }
                        resultList.add(resultMap);
                        i++;
                    }
                    centerMap.put("id", id);
                    centerMap.put("resultList", resultList);
                    centerList.add(centerMap);
                    resultlist.add(titleMap);
                }
            }
            request.setAttribute("centerList", centerList);
        }
        request.setAttribute("topList", topList);
        request.setAttribute("resultlist", resultlist);
        request.setAttribute("categoryId", categoryId);
        
        return new ModelAndView("tzxm/govIvest");
    }
    /**
     * 集成流程及事项列表数据
     * @param request
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping("/bsznList")
    public ModelAndView bsznList(HttpServletRequest request) {
        String categoryId = request.getParameter("categoryId");
        Map<String, Object> categoryInfo = govIvestService.getByJdbc(
                "T_FLOW_CONFIG_CATEGORY", new String[] { "ID" }, new Object[] { categoryId });
        if(categoryInfo!=null && categoryInfo.size()>0) {
            request.setAttribute("titleName", categoryInfo.get("NAME").toString());
        }
        List<Map<String, Object>> categoryChildList = govIvestService.findCategoryChildList(categoryId);//集成流程列表信息
        List<Map<String, Object>> categoryList = new ArrayList<Map<String, Object>>();
        if(categoryChildList!=null && categoryChildList.size()>0) {
            for(Map<String, Object> categoryChildInfo : categoryChildList) {
                Map categoryChildMap = new HashMap();
                String childId = categoryChildInfo.get("ID").toString();
                String childName = categoryChildInfo.get("NAME").toString();
                List<Map<String, Object>> itemList = govIvestService.findItemList(childId);//事项列表信息
                if(itemList!=null && itemList.size()>0) {
                    List<Map<String, Object>> itemResultList = new ArrayList<Map<String, Object>>();
                    int rn = 1;
                    for(Map<String, Object> itemMap : itemList) {
                        if(itemMap.get("ITEM_CODE")!=null) {
                            itemMap.put("RN", rn);
                          //前台隐藏事项名称包含XX30的字段
                            String ITEM_NAME = itemMap.get("ITEM_NAME").toString();
                            boolean status = ITEM_NAME.contains("XX30");
                            if(status) {
                                ITEM_NAME=ITEM_NAME.substring(0, ITEM_NAME.indexOf("XX30"));
                            }
                            itemMap.put("ITEM_NAME", ITEM_NAME);
                            itemResultList.add(itemMap);
                            rn++;
                        }
                    }
                    if(itemResultList!=null && itemResultList.size()>0) {
                        categoryChildMap.put("ID", childId);
                        categoryChildMap.put("NAME", childName);
                        categoryChildMap.put("itemList", itemResultList);
                        
                        categoryList.add(categoryChildMap);
                    }
                }
            }
        }
        request.setAttribute("categoryList", categoryList);
        return new ModelAndView("tzxm/bsznList");
    }
    /**
     * 跳转到项目登记详情页
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/xmdjDetail")
    public ModelAndView xmdjDetail(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        Map<String, Object> categoryInfo = govIvestService.getByJdbc("T_FLOW_CONFIG_CATEGORY",
                new String[] { "ID" },new Object[] { projectId });
        Map<String, Object> projectApply = new HashMap<String, Object>();
        projectApply.put("FLOW_CATE_ID", projectId);
        if(categoryInfo!=null && categoryInfo.size()>0) {
            projectApply.put("FLOW_CATE_NAME", categoryInfo.get("NAME").toString());
        }
        
        request.setAttribute("projectApply", projectApply);
        return new ModelAndView("tzxm/xmdjDetail");
    }
    
    /**
     * 公示信息界面
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/promulgateInfo")
    public void promulgateInfo(HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(rows==null || "".equals(rows)) {
            rows = "15";//页面大小默认为15
        }
        if(page==null || "".equals(page)) {
            page = "1";//当前页默认第一页
        }
        String type = request.getParameter("type");
        String textValue = request.getParameter("textValue");
        if (textValue != null && !"".equals(textValue)) {
            textValue = URLDecoder.decode(textValue, "utf-8");
            textValue = textValue.trim();
        }
       
        Map<String, Object> resultMap = govIvestService.findHandleResultMap(page, rows, textValue);
        List<Map<String, Object>> resultList =  (List<Map<String, Object>>) resultMap.get("list");
        List<Map<String, Object>> handleResultList = new ArrayList<Map<String, Object>>();
        if(type!=null && "handleResult".equals(type)) {
            if(resultList!=null && resultList.size()>0) {
                for(Map<String, Object> map : resultList) {
                    String endTime = map.get("END_TIME").toString();
                    if(endTime!=null && !"".equals(endTime)) {
                        endTime = endTime.substring(0, 10);
                        map.put("END_TIME", endTime);
                    }
                    String runStatus = map.get("RUN_STATUS").toString();
                    if(runStatus!=null && !"".equals(runStatus)) {
                        if("2".equals(runStatus) || "3".equals(runStatus) 
                                || "4".equals(runStatus)|| "5".equals(runStatus)) {
                            map.put("FLOW_STATUS", "办结");
                        }else if("6".equals(runStatus)){
                            map.put("FLOW_STATUS", "强制结束");
                        }else {
                            map.put("FLOW_STATUS", "预审不通过");
                        }
                    }
                    handleResultList.add(map);
                }
            }
        }
        request.setAttribute("textValue", textValue);
        this.setListToJsonString(Integer.parseInt(resultMap.get("total").toString()), handleResultList,
                null, JsonUtil.EXCLUDE, response);
        //return new ModelAndView("tzxm/promulgateInfo/"+type);
    }
    /**
     * 公示信息查询跳转界面
     * 
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request) throws UnsupportedEncodingException {
        String textValue = request.getParameter("textValue");
        if (textValue != null && !"".equals(textValue)) {
            String encodeValue = URLEncoder.encode(textValue, "utf-8");
            request.setAttribute("encodeValue", encodeValue);
        }
        request.setAttribute("textValue", textValue);
        return new ModelAndView("tzxm/promulgateInfo/handleResult");
    }
    
    /**
     * 政策法规跳转界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/policiesRegulations")
    public ModelAndView policiesRegulations(HttpServletRequest request) {
        String moduleId = request.getParameter("moduleId");
        if(moduleId!=null && !"".equals(moduleId)) {
            Map<String, Object> moduleMap = govIvestService.getByJdbc("T_CMS_ARTICLE_MODULE",
                    new String[] { "MODULE_ID" },new Object[] { moduleId });
            if(moduleMap!=null && moduleMap.size()>0) {
                request.setAttribute("pName", moduleMap.get("MODULE_NAME").toString());
            }
        }else {
            moduleId = govIvestService.findPoliciesRegulationsModuleId(MUDULE_NAME_ZCFG, PARENT_NAME);
            request.setAttribute("pName", "全部");
        }
        List<Map<String, Object>> menuList = govIvestService.findMenuListByModuleId(moduleId);
        if(menuList!=null && menuList.size()>0) {
            if(moduleId!=null && !"".equals(moduleId)) {
                List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> menuMap : menuList) {
                    String menuId = menuMap.get("MODULE_ID").toString();
                    if(menuId.equals(moduleId)) {
                        menuMap.put("classon", true);
                    }
                    tempList.add(menuMap);
                }
                request.setAttribute("menuList", tempList);
            }else {
                request.setAttribute("menuList", menuList);
            }
        }
        request.setAttribute("moduleId", moduleId);
        return new ModelAndView("tzxm/zcfg");
    }
    
    /**
     * 政策法规列表信息界面
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/policiesRegulationsInfo")
    public void policiesRegulationsInfo(HttpServletRequest request,
            HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(rows==null || "".equals(rows)) {
            rows = "15";//页面大小默认为15
        }
        if(page==null || "".equals(page)) {
            page = "1";//当前页默认第一页
        }
        String moduleId = request.getParameter("moduleId");
        Map<String, Object> resultMap = govIvestService.findModuleInfo(page, rows, moduleId);
        List<Map<String, Object>> handleResultList =  (List<Map<String, Object>>) resultMap.get("list");
        request.setAttribute("moduleId", moduleId);
        this.setListToJsonString(Integer.parseInt(resultMap.get("total").toString()), handleResultList,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 申报指引跳转界面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/sbzylctList")
    public ModelAndView sbzylctList(HttpServletRequest request) {
        String moduleId = request.getParameter("moduleId");
        if(moduleId!=null && !"".equals(moduleId)) {
            Map<String, Object> moduleMap = govIvestService.getByJdbc("T_CMS_ARTICLE_MODULE",
                    new String[] { "MODULE_ID" },new Object[] { moduleId });
            if(moduleMap!=null && moduleMap.size()>0) {
                request.setAttribute("pName", moduleMap.get("MODULE_NAME").toString());
            }
        }else {
            moduleId = govIvestService.findPoliciesRegulationsModuleId(MUDULE_NAME_SBZY, PARENT_NAME);
            request.setAttribute("pName", "全部");
        }
        List<Map<String, Object>> menuList = govIvestService.findMenuListByModuleId(moduleId);
        if(menuList!=null && menuList.size()>0) {
            if(moduleId!=null && !"".equals(moduleId)) {
                List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> menuMap : menuList) {
                    String menuId = menuMap.get("MODULE_ID").toString();
                    if(menuId.equals(moduleId)) {
                        menuMap.put("classon", true);
                    }
                    tempList.add(menuMap);
                }
                request.setAttribute("menuList", tempList);
            }else {
                request.setAttribute("menuList", menuList);
            }
        }
        request.setAttribute("moduleId", moduleId);
        return new ModelAndView("tzxm/sbzy");
    }
    
    /**
     * 申报指引列表信息界面
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/sbzylctInfo")
    public void sbzylctInfo(HttpServletRequest request,
            HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(rows==null || "".equals(rows)) {
            rows = "15";//页面大小默认为15
        }
        if(page==null || "".equals(page)) {
            page = "1";//当前页默认第一页
        }
        String moduleId = request.getParameter("moduleId");
        Map<String, Object> resultMap = govIvestService.findModuleInfo(page, rows, moduleId);
        List<Map<String, Object>> handleResultList =  (List<Map<String, Object>>) resultMap.get("list");
        request.setAttribute("moduleId", moduleId);
        this.setListToJsonString(Integer.parseInt(resultMap.get("total").toString()), handleResultList,
                null, JsonUtil.EXCLUDE, response);
    }
}
