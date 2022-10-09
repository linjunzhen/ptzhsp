/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.WordReplaceParamUtil;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.CommercialSetService;
import net.evecom.platform.wsbs.service.ApplyMaterService;

/**
 * 商事1+N信息配置
 * @author Danto Huang
 * @created 2017年7月11日 上午9:06:21
 */
@Controller
@RequestMapping("/commercialSetController")
public class CommercialSetController extends BaseController {
    /**
     * 引入commercialSetService
     */
    @Resource
    private CommercialSetService commercialSetService;
    /**
     * 引入applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 引入exeDataService
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 
     * 跳转1+N关联事项配置页面
     * @author Danto Huang
     * @created 2017年7月11日 上午9:23:05
     * @param request
     * @return
     */
    @RequestMapping(params="relatedItemView")
    public ModelAndView relatedItemView(HttpServletRequest request){        
        return new ModelAndView("commercial/relateditem/relatedItemView");
    }
    /**
     * 
     * 1+N关联事项列表
     * @author Danto Huang
     * @created 2017年7月11日 上午10:07:08
     * @param request
     * @param response
     */
    @RequestMapping(params="relatedItemData")
    public void relatedItemData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.ssbmbm", "asc");
        filter.addSorted("t.item_code", "asc");
        List<Map<String,Object>> list = commercialSetService.getRelatedItems(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 保存1+N关联事项
     * @author Danto Huang
     * @created 2017年7月12日 上午8:51:14
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="saveRelatedItem")
    @ResponseBody
    public AjaxJson saveRelatedItem(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String itemCodes = request.getParameter("itemCodes");
        String[] itemCode = itemCodes.split(",");
        for(int i=0;i<itemCode.length;i++){
            Map<String, Object> item = commercialSetService.getByJdbc("T_COMMERCIAL_RELATED_ITEM",
                    new String[] { "ITEM_CODE" }, new Object[] { itemCode[i] });
            if(item==null){
                item = commercialSetService.getByJdbc("T_WSBS_SERVICEITEM",
                        new String[] { "ITEM_CODE" }, new Object[] { itemCode[i] });
                String recordId = commercialSetService.saveOrUpdate(item, "T_COMMERCIAL_RELATED_ITEM", null);
                List<Map<String, Object>> materList = commercialSetService.getAllByJdbc("T_WSBS_SERVICEITEM_MATER",
                        new String[] { "ITEM_ID" }, new Object[] { item.get("ITEM_ID") });
                for(Map<String,Object> mater : materList){
                    mater.put("RELATED_ID", recordId);
                    commercialSetService.saveOrUpdate(mater, "T_COMMERCIAL_RELATMATER", null);
                }
            }
        }
        j.setMsg("操作成功");
        return j;
    }
    /**
     * 
     * 删除1+N关联事项
     * @author Danto Huang
     * @created 2017年7月12日 上午9:15:57
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="multiDelRelatedItem")
    @ResponseBody
    public AjaxJson multiDelRelatedItem(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        commercialSetService.remove("T_COMMERCIAL_RELATED_ITEM","RELATED_ID",selectColNames.split(","));
        commercialSetService.remove("T_COMMERCIAL_RELATMATER","RELATED_ID",selectColNames.split(","));
        j.setMsg("删除成功");
        return j;
    }
    /**
     * 
     * 关联事项材料加载
     * @author Danto Huang
     * @created 2017年7月12日 下午4:23:34
     * @param request
     * @param response
     */
    @RequestMapping(params="getRelatedItemMater")
    public void getRelatedItemMater(HttpServletRequest request,HttpServletResponse response){
        String itemCodes = request.getParameter("itemCodes");
        String busTableName = request.getParameter("busTableName");
        String busRecordId = request.getParameter("busRecordId");
        List<Map<String,Object>> list = commercialSetService.findMaterByItemCodes(itemCodes,null);
        list = applyMaterService.setUploadFiles(busRecordId, busTableName, list);
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }
    /**
     * 
     * 跳转1+n事项材料配置列表页面
     * @author Danto Huang
     * @created 2017年8月3日 下午5:56:52
     * @param request
     * @return
     */
    @RequestMapping(params="materSetView")
    public ModelAndView materSetView(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        request.setAttribute("entityId", entityId);
        return new ModelAndView("commercial/materset/materSetView");
    }
    /**
     * 
     * 1+n事项材料配置列表
     * @author Danto Huang
     * @created 2017年8月3日 下午6:20:26
     * @param request
     * @param reponse
     */
    @RequestMapping(params="materSetData")
    public void materTemplateData(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.mater_sn", "asc");
        List<Map<String,Object>> list = commercialSetService.findRelatedItemMater(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 
     * 描述 材料列表设置
     * @author Danto Huang
     * @created 2017年8月23日 上午11:13:53
     * @param request
     * @return
     */
    @RequestMapping(params="materSetInfo")
    public ModelAndView materSetInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> mater = commercialSetService.getByJdbc("T_COMMERCIAL_RELATMATER",
                new String[] { "RECORD_ID" }, new Object[] { entityId });
        request.setAttribute("mater", mater);
        return new ModelAndView("commercial/materset/materSetInfo");
    }
    /**
     * 
     * 描述 材料列表设置保存
     * @author Danto Huang
     * @created 2017年8月23日 下午3:10:15
     * @param request
     * @param response
     */
    @RequestMapping(params="saveOrUpdateMaterSet")
    @ResponseBody
    public AjaxJson saveOrUpdateMaterSet(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("RECORD_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        commercialSetService.saveOrUpdate(variables, "T_COMMERCIAL_RELATMATER", entityId);
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * 
     * 在线材料表单后台审核查看
     * @author Danto Huang
     * @created 2017年8月31日 下午4:09:44
     * @param request
     * @return
     */
    @RequestMapping(params="onlineFormView")
    public ModelAndView onlineFormView(HttpServletRequest request){
        String formName = request.getParameter("formName");
        String exeId = request.getParameter("exeId");
        Map<String,Object> materForm;
        materForm = commercialSetService.getByJdbc(formName, new String[]{"EXE_ID"}, new Object[]{exeId});
        
        materForm.put("formName", formName);
        request.setAttribute("materForm", materForm);
        return new ModelAndView("bsdt/applyform/relatedForm/"+formName);
    }
    /**
     * 
     * 更新1+N事项的关联材料列表
     * @author Danto Huang
     * @created 2017年9月18日 下午5:51:07
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="refreshRelatedItemMater")
    @ResponseBody
    public AjaxJson refreshRelatedItemMater(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String relatedId = request.getParameter("relatedId");
        List<Map<String, Object>> oldList = commercialSetService.getAllByJdbc("T_COMMERCIAL_RELATMATER",
                new String[] { "RELATED_ID" }, new Object[] { relatedId });
        String itemId = oldList.get(0).get("ITEM_ID").toString();
        List<Map<String, Object>> newList = commercialSetService.getAllByJdbc("T_WSBS_SERVICEITEM_MATER",
                new String[] { "ITEM_ID" }, new Object[] { itemId });
        for(Map<String,Object> old : oldList){
            String oldMaterId = old.get("MATER_ID").toString();
            boolean inNew = false;
            for(Map<String,Object> news : newList){
                if(oldMaterId.equals(news.get("MATER_ID"))){
                    inNew = true;
                    commercialSetService.saveOrUpdate(news, "T_COMMERCIAL_RELATMATER", old.get("RECORD_ID").toString());
                }
            }
            if(!inNew){
                commercialSetService.remove("T_COMMERCIAL_RELATMATER", "RECORD_ID",
                        new Object[] { old.get("RECORD_ID").toString() });
            }
        }
        
        for(Map<String,Object> news : newList){
            String newMaterId = news.get("MATER_ID").toString();
            boolean inOld = false;
            for(Map<String,Object> old : oldList){
                if(newMaterId.equals(old.get("MATER_ID"))){
                    inOld = true;
                    break;
                }
            }
            if(!inOld){
                news.put("RELATED_ID", relatedId);
                commercialSetService.saveOrUpdate(news, "T_COMMERCIAL_RELATMATER", null);
            }             
        }
        j.setMsg("操作成功，已更新为最新材料!");
        return j;
    }
    /**
     * 
     * 描述  获取当前审核人需审核1+N关联事项
     * @author Danto Huang
     * @created 2017年9月28日 下午4:38:23
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="getAuditerRelatedItem")
    @ResponseBody
    public AjaxJson getAuditerRelatedItem(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        List<Map<String,Object>> result = null;
        String itemCodes = request.getParameter("itemCodes");
        String currNodeName = request.getParameter("currNodeName");
        String userName = AppUtil.getLoginUser().getUsername();
        if(itemCodes!=null&&StringUtils.isNotEmpty(itemCodes)){
            String[] itemCode = itemCodes.split(",");
            for(int i=0;i<itemCode.length;i++){
                Map<String, Object> checkResult = commercialSetService.checkAuditerRelatedItem(userName, itemCode[i],
                        currNodeName);
                if(Boolean.valueOf(checkResult.get("resultFlag").toString())){
                    if(result==null){
                        result = new ArrayList<Map<String,Object>>();
                    }
                    result.add(checkResult);
                }
            }
        }
        if(result!=null&&result.size()>0){
            j.setSuccess(true);
            j.setJsonString(JSON.toJSONString(result));
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 
     * 描述 更新关联事项信息
     * @author Danto Huang
     * @created 2017年10月23日 下午12:00:28
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="refreshRelatedItem")
    @ResponseBody
    public AjaxJson refreshRelatedItem(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String relatedId = request.getParameter("relatedId");
        List<Map<String, Object>> oldList = commercialSetService.getAllByJdbc("T_COMMERCIAL_RELATMATER",
                new String[] { "RELATED_ID" }, new Object[] { relatedId });
        String itemId = (String) commercialSetService
                .getByJdbc("T_COMMERCIAL_RELATED_ITEM", new String[] { "RELATED_ID" }, new Object[] { relatedId })
                .get("ITEM_ID");
        Map<String, Object> item = commercialSetService.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_ID" },
                new Object[] { itemId });
        commercialSetService.saveOrUpdate(item, "T_COMMERCIAL_RELATED_ITEM", relatedId);
        List<Map<String, Object>> newList = commercialSetService.getAllByJdbc("T_WSBS_SERVICEITEM_MATER",
                new String[] { "ITEM_ID" }, new Object[] { itemId });
        for(Map<String,Object> old : oldList){
            String oldMaterId = old.get("MATER_ID").toString();
            boolean inNew = false;
            for(Map<String,Object> news : newList){
                if(oldMaterId.equals(news.get("MATER_ID"))){
                    inNew = true;
                    commercialSetService.saveOrUpdate(news, "T_COMMERCIAL_RELATMATER", old.get("RECORD_ID").toString());
                    break;
                }
            }
            if(!inNew){
                commercialSetService.remove("T_COMMERCIAL_RELATMATER", "RECORD_ID",
                        new Object[] { old.get("RECORD_ID").toString() });
            }
        }
        
        for(Map<String,Object> news : newList){
            String newMaterId = news.get("MATER_ID").toString();
            boolean inOld = false;
            for(Map<String,Object> old : oldList){
                if(newMaterId.equals(old.get("MATER_ID"))){
                    inOld = true;
                    break;
                }
            }
            if(!inOld){
                news.put("RELATED_ID", relatedId);
                commercialSetService.saveOrUpdate(news, "T_COMMERCIAL_RELATMATER", null);
            }             
        }
        j.setMsg("操作成功，已更新为最新事项信息!");
        return j;
    }
    
    /**
     * 
     * 加载支撑平台字典列表
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping("/load")
    public void load(HttpServletRequest request, HttpServletResponse response){
        String param = request.getParameter("param");
        String defaultEmptyText = request.getParameter("defaultEmptyText");
        List<Map<String,Object>> list = null;
        if(StringUtils.isNotEmpty(param)){
            list = commercialSetService.findDatasForSelectFromDevbase(param);
        }
        if(StringUtils.isNotEmpty(defaultEmptyText)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("TEXT","请选择"+defaultEmptyText);
            map.put("VALUE","");
            if(list==null){
                list = new ArrayList<Map<String,Object>>();
            }
            list.add(0, map);
        }
        String json = JSON.toJSONString(list);
        this.setJsonString(json, response);
    }

    /**
     * 描述:公章刻制材料下载页面
     *
     * @author Madison You
     * @created 2019/9/4 10:58:00
     * @param
     * @return
     */
    @RequestMapping(params = "sealMaterView")
    public ModelAndView sealMaterView(HttpServletRequest request) {
        return new ModelAndView("commercial/materset/sealMaterView");
    }

    /**
     * 描述:公章刻制材料下载数据
     *
     * @author Madison You
     * @created 2019/9/4 10:58:00
     * @param
     * @return
     */
    @RequestMapping(params = "sealMaterData")
    public void sealMaterData(HttpServletRequest request , HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("b.CREATE_TIME", "desc");
        List<Map<String,Object>> list = commercialSetService.findSealMaterData(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述: 公章刻制材料数据修改页面
     *
     * @author Madison You
     * @created 2019/9/4 15:11:00
     * @param
     * @return
     */
    @RequestMapping(params = "sealMaterReviseView")
    public ModelAndView sealMaterReviseView(HttpServletRequest request, HttpServletResponse response){
        String companyId = request.getParameter("COMPANY_ID");
        String bustableName = request.getParameter("BUS_TABLENAME");
       /* Map<String,Object> map = commercialSetService.getByJdbc("T_COMMERCIAL_COMPANY",
                new String[]{"COMPANY_ID"}, new Object[]{companyId});*/

        Map<String,Object> map = new HashMap<String, Object>();
        if(bustableName.equals("T_COMMERCIAL_DOMESTIC")){
            bustableName = "T_COMMERCIAL_COMPANY";
            map = commercialSetService.getByJdbc("T_COMMERCIAL_COMPANY",
                    new String[]{"COMPANY_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_NZ_JOINTVENTURE")){
            map = commercialSetService.getByJdbc("T_COMMERCIAL_NZ_JOINTVENTURE",
                    new String[]{"COMPANY_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_INTERNAL_STOCK")){
            map = commercialSetService.getByJdbc("T_COMMERCIAL_INTERNAL_STOCK",
                    new String[]{"INTERNAL_STOCK_ID"}, new Object[]{companyId});
        }else if(bustableName.equals("T_COMMERCIAL_BRANCH")){
            map = commercialSetService.getByJdbc("T_COMMERCIAL_BRANCH",
                    new String[]{"BRANCH_ID"}, new Object[]{companyId});
            map.put("COMPANY_NAME", map.get("BRANCH_NAME"));
        }else if(bustableName.equals("T_COMMERCIAL_SOLELYINVEST")){
            map = commercialSetService.getByJdbc("T_COMMERCIAL_SOLELYINVEST",
                    new String[]{"SOLELY_ID"}, new Object[]{companyId});
        }
        map.put("BUS_TABLENAME", bustableName);
        map.put("PRIMARY_KEY", commercialSetService.getPrimaryKeyName(bustableName).get(0));
        map.put("PRIMARY_KEY_VAL", companyId);
        request.setAttribute("subject", map);
        return new ModelAndView("commercial/materset/sealMaterReviseView");
    }

    /**
     * 描述:公章刻制材料数据修改
     *
     * @author Madison You
     * @created 2019/9/4 15:22:00
     * @param
     * @return
     */
    @RequestMapping("/sealMaterUpdateAndSave")
    @ResponseBody
    public AjaxJson sealMaterUpdateAndSave(HttpServletRequest request , HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String bustableName = request.getParameter("BUS_TABLENAME");
        String primaryKey = (String) commercialSetService.getPrimaryKeyName(bustableName).get(0);
        String busrecordId = request.getParameter(primaryKey);
        Map<String, Object> flowForm = BeanUtil.getMapFromRequest(request);
        commercialSetService.saveOrUpdate(flowForm, bustableName, busrecordId);
        j.setMsg("保存成功");
        return j;
    }

    /**
     * 描述:公章刻制材料详细信息
     *
     * @author Madison You
     * @created 2019/9/4 16:15:00
     * @param
     * @return
     */
    @RequestMapping(params = "sealMaterDetailView")
    public ModelAndView sealMaterDetailView(HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        SqlFilter filter = new SqlFilter(request);
        Map<String,Object> busRecord = commercialSetService.findSealMaterDetailData(filter);
        if (busRecord.get("JBR_ZJHM") != null && busRecord.get("LEGAL_IDNO") != null &&
                busRecord.get("RESULT_FILE_ID") != null) {
            String EXE_ID = busRecord.get("EXE_ID").toString();
            String ITEM_CODE = busRecord.get("ITEM_CODE").toString();
            /*经办人身份证*/
            String JBR_ZJHM = busRecord.get("JBR_ZJHM").toString();
            Map<String,Object> signInfoJBR = commercialSetService.getByJdbc("T_COMMERCIAL_SIGN",
                    new String[]{"SIGN_IDNO", "SIGN_FLAG", "EXE_ID"},
                    new Object[]{JBR_ZJHM, "1", EXE_ID});
            /*法人身份证*/
            String LEGAL_IDNO = busRecord.get("LEGAL_IDNO").toString();
            Map<String,Object> signInfoFR = commercialSetService.getByJdbc("T_COMMERCIAL_SIGN",
                    new String[]{"SIGN_IDNO", "SIGN_FLAG", "EXE_ID"},
                    new Object[]{LEGAL_IDNO, "1", EXE_ID});
            /*营业执照*/
            String YYZZ = busRecord.get("RESULT_FILE_ID").toString();
            map.put("YYZZ", YYZZ);
            map.put("signInfoJBR", signInfoJBR);
            map.put("signInfoFR", signInfoFR);
            map.put("WTS", "2c90b38a6c0a0aa1016c0a3673410d8f");
            map.put("QRS", "2c90b38a6c0a0aa1016c0a36d5190da0");
            map.put("YJSDWTS", "40289fdc6d0127df016d0127dfea0000");
            map.put("EXE_ID", EXE_ID);
            map.put("ITEM_CODE", ITEM_CODE);
            map.put("ISEMAIL", busRecord.get("ISEMAIL"));
            request.setAttribute("subject", map);
        }
        return new ModelAndView("commercial/materset/sealMaterDetailView");
    }


}
