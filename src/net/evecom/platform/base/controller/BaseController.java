/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.base.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.CheckPwd;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.base.service.DataSourceService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 描述
 * @author Flex Hu
 * @created 2014年9月5日 上午11:13:15
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BaseController.class);
    /**
     * 引入dataSourceService
     */
    @Resource
    private DataSourceService dataSourceService;
    
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 返回JSON字符串
     * 
     * @author Flex Hu
     * @param totalCount
     *            :数据总量
     * @param list
     *            :集合
     * @param includeOrExclude
     *            :根据ISInclude为真或者假俩来决定JSON序列化object的策略
     * @param isExclude
     *            :true:表明第三个参数为无需序列化成JSON的字段,false:表明嗲三个参数为需要序列化成JSON的字段
     */
    protected void setListToJsonString(int totalCount, List list, String[] includeOrExclude, boolean isExclude,
            HttpServletResponse response) {
        StringBuffer jsonTemp = new StringBuffer("{\"total\":");
        jsonTemp.append(totalCount).append(",\"rows\":");
        String json = JsonUtil.jsonStringFilter(list, includeOrExclude, isExclude);
        jsonTemp.append(json).append("}");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        pw.write(jsonTemp.toString());
        pw.flush();
        pw.close();
    }

    /**
     * 获取孩子数据
     * 
     * @param allList
     * @param parentId
     * @param parentMap
     * @param idAndNameCol
     */
    public void getChildren(List<Map<String, Object>> allList, String parentId, Map<String, Object> parentMap,
            String[] idAndNameCol, Set<String> needCheckIds) {
        List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : allList) {
            // 获取父亲ID
            String pId = map.get("PARENT_ID").toString();
            if (pId.equals(parentId)) {
                // 获取ID值
                String id = map.get(idAndNameCol[0]).toString();
                // 获取NAME值
                String name = map.get(idAndNameCol[1]).toString();
                map.put("id", id);
                map.put("name", name);
                //map.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                if (needCheckIds.contains(id)) {
                    map.put("checked", true);
                }
                children.add(map);
            }
        }
        if (children.size() > 0) {
            parentMap.put("children", children);
            for (Map<String, Object> child : children) {
                String id = child.get(idAndNameCol[0]).toString();
                this.getChildren(allList, id, child, idAndNameCol, needCheckIds);
            }
        }
    }

    /**
     * 通用树形数据源
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "tree")
    @ResponseBody
    public void tree(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取表名称
        String tableName = request.getParameter("tableName").toUpperCase();
        // 获取代表ID、NAME的名称
        String[] idAndNameCol = request.getParameter("idAndNameCol").split(",");
        // 获取需要开始读取的根节点ID
        String rootParentId = request.getParameter("rootParentId");
        // 判断是否是字典类别树
        String dicTypeCode = request.getParameter("dicTypeCode");
        if (StringUtils.isNotEmpty(dicTypeCode)) {
            Map<String, Object> dicType = dataSourceService.
                    getByJdbc("T_MSJW_SYSTEM_DICTYPE", new String[] { "TYPE_CODE" },
                    new Object[] { dicTypeCode });
            String typeId = (String) dicType.get("TYPE_ID");
            rootParentId = typeId;
        }
        // 获取是否需要显示根节点
        String isShowRoot = request.getParameter("isShowRoot");
        String rootName = request.getParameter("rootName");
        // 获取需要回显的IDS
        String checkIds = request.getParameter("needCheckIds");
        Set<String> needCheckIds = new HashSet<String>();
        if (StringUtils.isNotEmpty(checkIds)) {
            String[] checkIdArray = checkIds.split(",");
            for (String checkId : checkIdArray) {
                needCheckIds.add(checkId);
            }
        }
        String json = "";
        Map<String, Object> rootMap = null;
        if (isShowRoot.equals("true")) {

            if (rootParentId.equals("0")) {
                rootMap = new HashMap<String, Object>();
                if (StringUtils.isNotEmpty(rootName)) {
                    rootMap.put("name", rootName);
                } else {
                    rootMap.put("name", "所有类别");
                }
                rootMap.put("TREE_LEVEL", 1);
            } else {
                rootMap = this.dataSourceService.getByJdbc(tableName, new String[] { idAndNameCol[0] },
                        new Object[] { rootParentId });
                rootMap.put("name", rootMap.get(idAndNameCol[1]));
            }
            rootMap.put("id", rootParentId);
            rootMap.put("PARENT_ID", "-1");
            rootMap.put("open", true);
            //rootMap.put("icon", "plug-in/easyui-1.4/themes/icons/monitor.png");
            if (needCheckIds.size() > 0) {
                rootMap.put("checked", true);
            }
        }
        String cols = request.getParameter("idAndNameCol") + "," + request.getParameter("targetCols");
        List<Map<String, Object>> allList = dataSourceService.findTreeData(request, tableName, rootParentId, cols);
        List<Map<String, Object>> topList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : allList) {
            // 获取ID值
            String id = map.get(idAndNameCol[0]).toString();
            // 获取NAME值
            String name = (String) map.get(idAndNameCol[1]);
            // 获取父亲ID
            String parentId = map.get("PARENT_ID").toString();
            if (parentId.equals(rootParentId)) {
                map.put("id", id);
                map.put("name", name);
                //map.put("icon", "plug-in/easyui-1.4/themes/icons/folder_table.png");
                if (needCheckIds.contains(id)) {
                    map.put("checked", true);
                }
                this.getChildren(allList, id, map, idAndNameCol, needCheckIds);
                topList.add(map);
            }
        }
        if (isShowRoot.equals("true")) {
            rootMap.put("children", topList);
            json = JSON.toJSONString(rootMap);
        } else if (isShowRoot.equals("false")) {
            json = JSON.toJSONString(topList);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();

    }

    /**
     * 
     * 描述 返回json到客户端
     * 
     * @author Flex Hu
     * @created 2014年9月20日 上午8:33:57
     * @param json
     * @param response
     */
    protected void setJsonString(String json, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 返回JSON数据
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "verifyValueExist")
    @ResponseBody
    public void verifyValueExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldName = request.getParameter("fieldId");
        String fieldValue = request.getParameter("fieldValue");
        String tableName = request.getParameter("tableName");
        String recordId = request.getParameter("recordId");
        Map<String, Object> config = this.dataSourceService.getByJdbc("ENCRYPTION_CONFIG_TABLE",
                new String[] { "BUSTABLE_NAME" }, new Object[] { tableName.toUpperCase() });
        if(config!=null){
            Sm4Utils sm4 = new Sm4Utils();
            List<Map<String, Object>> columnList = this.dataSourceService.getAllByJdbc("ENCRYPTION_CONFIG_COLUMN",
                    new String[] { "CONFIG_ID" }, new Object[] { config.get("CONFIG_ID") });
            if(columnList!=null&&columnList.size()>0){
                for(Map<String,Object> column : columnList){
                    String columnName = (String) column.get("COLUMN_NAME");
                    if(fieldName.equals(columnName)){
                        fieldValue = sm4.encryptDataCBC(fieldValue); 
                        break;                       
                    }
                }
            }
        }
        String jsonStr = null;
        boolean isExist = this.dataSourceService.isExistsRecord(tableName, fieldName, fieldValue,recordId);
        if (isExist) {
            jsonStr = "[\"" + fieldName + "\",false]";
        } else {
            jsonStr = "[\"" + fieldName + "\",true]";
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }

    /**
     * 更新树形排序字段
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "updateTreeSn")
    @ResponseBody
    public AjaxJson updateTreeSn(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // 获取被拖动节点ID
        String dragTreeNodeId = request.getParameter("dragTreeNodeId");
        // 获取被拖动节点最新level
        int dragTreeNodeNewLevel = Integer.parseInt(request.getParameter("dragTreeNodeNewLevel"));
        // 获取目标节点ID
        String targetNodeId = request.getParameter("targetNodeId");
        // 获取目标节点level
        int targetNodeLevel = Integer.parseInt(request.getParameter("targetNodeLevel"));
        String moveType = request.getParameter("moveType");
        String tableName = request.getParameter("tableName");
        try{
            if (dragTreeNodeNewLevel == 0) {
                j.setSuccess(false);
                j.setMsg("不能将该节点拖曳成根节点!");
            } else {
                this.dataSourceService.updateTreeSn(tableName, dragTreeNodeId, dragTreeNodeNewLevel, targetNodeId,
                        targetNodeLevel, moveType);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            j.setSuccess(false);
            j.setMsg("系统出错!拖拽排序失败!");
        }
        return j;
    }

    /**
     * 
     * 描述 获取初始化分页对象
     * 
     * @author Flex Hu
     * @created 2014年10月4日 下午10:31:55
     * @param request
     * @return
     */
    protected PagingBean getInitPagingBean(HttpServletRequest request) {
        // 取得分页的信息
        Integer start = 0;
        Integer limit = PagingBean.DEFAULT_PAGE_SIZE;
        String s_start = request.getParameter("start");
        String s_limit = request.getParameter("limit");
        if (StringUtils.isNotEmpty(request.getParameter("page"))) {
            // 获取当前页
            int currentPage = Integer.parseInt(request.getParameter("page"));
            // 计算开始记录
            // 获取页数限制
            int rows = Integer.parseInt(request.getParameter("rows"));
            int startRecord = (currentPage - 1) * rows;
            s_start = String.valueOf(startRecord);
        }
        if (StringUtils.isNotEmpty(request.getParameter("rows"))) {
            s_limit = request.getParameter("rows");
        }
        if (StringUtils.isNotEmpty(s_start)) {
            start = Integer.valueOf(s_start);
        }
        if (StringUtils.isNotEmpty(s_limit)) {
            limit = Integer.valueOf(s_limit);
        }
        return new PagingBean(start, limit);
    }
    /**
     * 返回JSON数据-验证重名的公司
     * @author Corliss Chen
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "verifyCompanyNameExist")
    @ResponseBody
    public void verifyCompanyNameExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldValue = request.getParameter("fieldValue");
        String companyId = request.getParameter("companyId");
        String individualId = request.getParameter("individualId");
        String solelyId=request.getParameter("solelyId");
        //T_COMMERCIAL_COMPANY      商事登记企业基本信息
        //T_COMMERCIAL_INDIVIDUAL   个体工商户登记
        //T_COMMERCIAL_SOLELYINVEST 个人独资企业
        boolean isExistCompany=false;
        boolean isExistIndividual=false;
        boolean isExistSolelyinvest=false;
        if((!StringUtils.isNotEmpty(companyId)||companyId.equals("undefined"))&&
                (!StringUtils.isNotEmpty(individualId)||individualId.equals("undefined"))&&
                (!StringUtils.isNotEmpty(solelyId)||solelyId.equals("undefined"))){//说明为新注册用户
            isExistIndividual= this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
            isExistSolelyinvest = this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);
            isExistCompany = this.dataSourceService
                    .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
        }else{
            //T_COMMERCIAL_COMPANY如果这张表中原先就存在这个名称
            if(StringUtils.isNotEmpty(companyId)&&!companyId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,companyId); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);  
            //T_COMMERCIAL_INDIVIDUAL如果这张表中原先就存在这个名称
            }else if(StringUtils.isNotEmpty(individualId)&&!individualId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,individualId);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,null);
            //T_COMMERCIAL_SOLELYINVEST如果这张表中原先就存在这个名称
            }else if(StringUtils.isNotEmpty(solelyId)&&!solelyId.equals("undefined")){
                isExistCompany = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME", fieldValue,null); 
                isExistIndividual= this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_INDIVIDUAL", "INDIVIDUAL_NAME", fieldValue,null);
                isExistSolelyinvest = this.dataSourceService
                        .isExistsFlowRecord("T_COMMERCIAL_SOLELYINVEST", "COMPANY_NAME", fieldValue,solelyId); 
            }
        }
        String jsonStr = null; 
        if (isExistCompany||isExistIndividual||isExistSolelyinvest) {
            jsonStr = "[false]";
        } else {
            jsonStr = "[true]";
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
    /**
     * 返回JSON数据-验证英文重名的公司
     * @author Corliss Chen
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "verifyCompanyEngExist")
    @ResponseBody
    public void verifyCompanyEngExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String companyId = request.getParameter("companyId");
        String fieldValue = request.getParameter("fieldValue");
        //T_COMMERCIAL_COMPANY      商事登记企业基本信息
        boolean isExistEnglish=false;
        isExistEnglish= this.dataSourceService
                .isExistsFlowRecord("T_COMMERCIAL_COMPANY", "COMPANY_NAME_ENG", fieldValue,companyId);
        String jsonStr = null; 
        if (isExistEnglish) {
            jsonStr = "[false]";
        } else {
            jsonStr = "[true]";
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
    /**
     * 
     * 描述：返回JSON数据-验证密码强度
     * @author Rider Chen
     * @created 2020年6月28日 上午9:38:57
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "verifyCheckPwd")
    @ResponseBody
    public void verifyCheckPwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldName = request.getParameter("fieldId");
        String fieldValue = new String(request.getParameter("fieldValue").getBytes("iso-8859-1"),"utf-8");
        String jsonStr = null;
        boolean isExist = CheckPwd.evalPwd(fieldValue);
        //增加黑名单库验证
        boolean isBlack = isBlack(fieldValue);
        if (isExist && isBlack) {
            jsonStr = "[\"" + fieldName + "\",true]";
        } else {
            jsonStr = "[\"" + fieldName + "\",false]";
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
    
    /**
     * 描述    验证密码是否属于黑名单库
     * @author Allin Lin
     * @created 2020年7月6日 下午5:05:58
     * @param password
     * @return
     */
    private boolean isBlack(String password){
        boolean result = true;
        List<Map<String, Object>> list = dictionaryService.findByTypeCode("BlackList");
        for (Map<String, Object> map : list) {
            if(password.equals(map.get("DIC_CODE"))){
                result = false;
                break;               
            }
        }
        return result;
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
    @RequestMapping(params = "verifyBdcdyhExist")
    @ResponseBody
    public void verifyBdcdyhExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String busRecordId = request.getParameter("busRecordId");
        String busTableName = request.getParameter("busTableName");
        String fieldName = request.getParameter("fieldName");
        String fieldValue = request.getParameter("fieldValue");
        Map<String, Object> result = new HashMap<String, Object>();

        boolean isExist= this.dataSourceService
                .isExistsFlowToBdcdyhRecord(busTableName, fieldName, fieldValue,busRecordId);
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
   /**
    * 
    * 描述：判断不动产单元号是否办理过所有的业务(此接口被devbase调用，修改时请注意 )
    * @author Rider Chen
    * @created 2020年8月19日 上午10:20:56
    * @param request
    * @param response
    * @throws IOException
    */
    @RequestMapping(params = "verifyAllBdcdyhExist")
    @ResponseBody
    public void verifyAllBdcdyhExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fieldValue = request.getParameter("fieldValue");
        String exeId = request.getParameter("exeId");
        /*(此接口被devbase调用，修改时请注意 )*/
        Map<String, Object> result  = this.dataSourceService.findAllBdcdyh(fieldValue, exeId);
        String jsonStr = JSON.toJSONString(result);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
}
