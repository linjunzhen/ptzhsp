/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.UserProjectService;
/**
 * 描述  用户中心项目Controller
 * @author Scolder Lin
 * @version 1.0
 * @created 2019年6月14日 上午11:25:55
 */
@Controller
@RequestMapping("/userProjectController")
public class UserProjectController extends BaseController {
    /**
     * 引入service
     */
    @Resource
    private UserProjectService userProjectService;
    
    /**
     * easyui AJAX请求数据 用户中心我的项目列表
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(params ="pagelist")
    public void pagelist(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        Map<String, Object> userInfo = AppUtil.getLoginMember();
        if(userInfo!=null && userInfo.size()>0) {
            Map<String, Object> mapList = userProjectService.findfrontList(page, rows,
                    (String)AppUtil.getLoginMember().get("YHZH"));
            this.setListToJsonString(Integer.parseInt(mapList.get("total").toString()),
                    (List) mapList.get("list"), null,JsonUtil.EXCLUDE, response);
        }
    }
    
    /**
     * easyui AJAX请求数据 项目建设事项列表
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "websiteItemList")
    public ModelAndView websiteItemList(HttpServletRequest request, HttpServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();     
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        session.setAttribute("fileServer", fileServer);
        
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("S.TREE_SN", "ASC");
        filter.addSorted("S.CREATE_TIME", "ASC");
        filter.addSorted("L.SORT", "ASC");
        String projectCode = request.getParameter("PROJECT_CODE");
        List<Map<String, Object>> list = userProjectService.findByPublishSqlFilter(filter);
        List<Map<String, Object>> flowList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> projectItemList = userProjectService.findProjectItemList(projectCode);
        Map<String, Object> flowMap = null;
        Map<String, Object> itemMap = null;
        if(list!=null && list.size()>0) {
            String idTemp = "";
            int i=1;
            int index = 0;
            for(Map<String, Object> map : list) {
                flowMap = new HashMap<String, Object>();
                itemMap = new HashMap<String, Object>();
                String categoryId = map.get("ID").toString();
                if(categoryId!=null && !"".equals(categoryId)) {
                    if(!categoryId.equals(idTemp)) {
                        flowMap.put("CATEGORY_ID", categoryId);
                        flowMap.put("CATEGORY_NAME", map.get("NAME").toString());
                        flowList.add(flowMap);
                        i=1;
                    }else {
                        i++;
                    }
                    itemMap.put("RN", i);
                    itemMap.put("PARENT_ID", categoryId);
                    itemMap.put("ITEM_ID", map.get("ITEM_ID").toString());
                    itemMap.put("ITEM_CODE", map.get("ITEM_CODE").toString());
                    //前台隐藏事项名称包含XX30的字段
                    String ITEM_NAME = map.get("ITEM_NAME").toString();
                    boolean status = ITEM_NAME.contains("XX30");
                    if(status) {
                        ITEM_NAME=ITEM_NAME.substring(0, ITEM_NAME.indexOf("XX30"));
                    }
                    
                    itemMap.put("ITEM_NAME", ITEM_NAME);
                    itemMap.put("DEPART_NAME", map.get("DEPART_NAME").toString());
                    itemMap.put("EXE_ID", map.get("EXE_ID").toString());
                    itemMap.put("ISFILED", map.get("ISFILED"));
                    String isKeyItem = map.get("IS_KEY_ITEM").toString();
                    if(isKeyItem!=null && !"".equals(isKeyItem)) {
                        if("0".equals(isKeyItem)) {
                            itemMap.put("iskeyItem", false);
                            itemMap.put("IS_KEY_ITEM", "否");
                        }else if("1".equals(isKeyItem)) {
                            itemMap.put("iskeyItem", true);
                            itemMap.put("IS_KEY_ITEM", "是");
                        }
                    }
                    if(projectItemList!=null && projectItemList.size()>0) {
                        if(map.get("ITEM_ID")!=null) {
                            String ITEM_ID = map.get("ITEM_ID").toString();
                            for(Map<String,Object> projectItemMap : projectItemList) {
                                if (projectItemMap.get("ITEM_ID") != null
                                        && ITEM_ID.equals(projectItemMap.get("ITEM_ID").toString())) {
                                    itemMap.put("isCheckItem", true);
                                    break;
                                }else {
                                    itemMap.put("isCheckItem", false);
                                }
                            }
                        }else {
                            itemMap.put("isCheckItem", false);
                        }
                    }else {
                        itemMap.put("isCheckItem", false);
                    }
                    String state = map.get("STATE").toString();
                    if(state!=null && !"".equals(state)) {
                        if("-1".equals(state)) {
                            itemMap.put("FLOW_STATUS", "未受理");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "办理");
                        }else if("0".equals(state)) {
                            itemMap.put("FLOW_STATUS", "草稿");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "办理");
                        }else if("1".equals(state)) {
                            itemMap.put("FLOW_STATUS", "正在办理");
                            itemMap.put("isHandle", true);
                            itemMap.put("handle", "查看");
                        }else if("2".equals(state) || "3".equals(state)){
                            itemMap.put("FLOW_STATUS", "已办结");
                            itemMap.put("isHandle", true);
                            itemMap.put("handle", "查看");
                        }else if("4".equals(state)){
                            itemMap.put("FLOW_STATUS", "审核不通过");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "重新办理");
                        }else if("5".equals(state)){
                            itemMap.put("FLOW_STATUS", "退回");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "重新办理");
                        }else if("6".equals(state)){
                            itemMap.put("FLOW_STATUS", "强制结束");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "重新办理");
                        }else if("7".equals(state)){
                            itemMap.put("FLOW_STATUS", "预审不通过");
                            itemMap.put("isHandle", false);
                            itemMap.put("handle", "重新办理");
                        }
                    }
                    //总记录数的下标
                    itemMap.put("index", index);
                    index++;
                    
                    itemList.add(itemMap);
                    idTemp = categoryId;
                }
            }
        }

        Map<String, Object> queryParams = filter.getQueryParams();
        String sourceCategoryId = (String) queryParams.get("Q_C.ID_EQ");
        
        request.setAttribute("sourceCategoryId", sourceCategoryId);
        request.setAttribute("projectCode", projectCode);
        request.setAttribute("flowList", flowList);
        request.setAttribute("itemList", itemList);
        return new ModelAndView("tzxm/itemList");
    }
    
    /**
     * 
     * 描述 更新预计受理信息
     * 
     * @author Scolder Lin
     * @created 2019-8-22 15:51:22
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/updateProjectItem")
    @ResponseBody
    public AjaxJson updateProjectItem(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String categoryId = request.getParameter("categoryId");
        String checkValue = request.getParameter("checkValue");
        String isKeyValue = request.getParameter("isKeyValue");
        String projectCode = request.getParameter("projectCode");
        if(isKeyValue !=null && !"".equals(isKeyValue)) {
            if("1".equals(isKeyValue)) {//必办
                List<Map<String,Object>> itemIdList = userProjectService.findItemIdList(categoryId);
                if(itemIdList!=null && itemIdList.size()>0) {
                    for(Map<String,Object> itemIdMap : itemIdList) {
                        if(itemIdMap.get("ITEM_ID")!=null) {
                            String itemId = itemIdMap.get("ITEM_ID").toString();
                            Map<String, Object> mapInfo  = new HashMap<String, Object>();
                            mapInfo.put("PROJECT_CODE", projectCode);
                            mapInfo.put("ITEM_ID", itemId);
                            mapInfo.put("IS_CHECK", checkValue);
                            userProjectService.deleteProjectItemInfo(itemId, projectCode);
                            userProjectService.saveOrUpdate(mapInfo, "T_MSJW_PROJECTITEM", null);
                        }
                    }
                }
            }else {//非必办
                String itemId = request.getParameter("itemId");
                Map<String, Object> mapInfo  = new HashMap<String, Object>();
                mapInfo.put("PROJECT_CODE", projectCode);
                mapInfo.put("ITEM_ID", itemId);
                mapInfo.put("IS_CHECK", checkValue);
                userProjectService.deleteProjectItemInfo(itemId, projectCode);
                userProjectService.saveOrUpdate(mapInfo, "T_MSJW_PROJECTITEM", null);
            }
            j.setMsg("设置成功");
            j.setSuccess(true);
        }else {
            j.setMsg("设置失败");
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 
     * 描述 上传附件
     * 
     * @author Scolder Lin
     * @created 2019-8-30 17:01:22
     * @param request
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/uploadTZXMFile")
    @ResponseBody
    public AjaxJson uploadTZXMFile(HttpServletRequest request) throws UnsupportedEncodingException {
        AjaxJson j = new AjaxJson();
        String projectFileId = request.getParameter("projectFileId");
        String fileId = request.getParameter("fileId");
        String fileName = request.getParameter("fileName");
        if(fileName!=null && !"".equals(fileName)) {
            fileName = URLDecoder.decode(fileName,"UTF-8"); 
        }
        String projectCode = request.getParameter("projectCode");
        String categoryId = request.getParameter("categoryId");
        String categoryName = request.getParameter("categoryName");
        if(categoryName!=null && !"".equals(categoryName)) {
            categoryName = URLDecoder.decode(categoryName,"UTF-8"); 
        }
        String attachType = request.getParameter("attachType");
        String fileType = request.getParameter("fileType");
        if(projectCode !=null && !"".equals(projectCode)) {
            boolean saveFile = true;
            if(projectFileId!=null && !"".equals(projectFileId)) {
                boolean fileIsExist = userProjectService.findFileIsExist(projectFileId);
                if(fileIsExist) {
                    saveFile = false;
                }
            }
            Map<String, Object> mapInfo  = new HashMap<String, Object>();
            mapInfo.put("FILE_ID", fileId);
            mapInfo.put("FILE_TYPE", fileType);
            mapInfo.put("FILE_NAME", fileName);
            mapInfo.put("PROJECT_CODE", projectCode);
            mapInfo.put("CATEGORY_ID", categoryId);
            mapInfo.put("CATEGORY_NAME", categoryName);
            mapInfo.put("ATTACH_TYPE", attachType);
            if(saveFile) {
                userProjectService.saveOrUpdate(mapInfo, "T_MSJW_PROJECT_FILE", null);
            }else {
                userProjectService.saveOrUpdate(mapInfo, "T_MSJW_PROJECT_FILE", projectFileId);
            }
            j.setMsg("上传成功");
            j.setSuccess(true);
        }else {
            j.setMsg("上传失败");
            j.setSuccess(false);
        }
        return j;
    }
}
