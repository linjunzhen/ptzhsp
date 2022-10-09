/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.system.dao.DicTypeDao;
import net.evecom.platform.system.service.DicTypeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述  办件数据Controller
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/exeDataController")
public class ExeDataController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(ExeDataController.class);
    /**
     * 引入Service
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * 引入Service
     */
    @Resource
    private ExeDataService exeDataService;
    /**
     * 引入Service
     */
    @Resource
    private DicTypeService dicTypeService;
    /**
     * 商事面签认证
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "getResultOfSign")
    @ResponseBody
    public AjaxJsonCode getResultOfSign(HttpServletRequest request) {
        String exeId=request.getParameter("EXEID");
        String name=request.getParameter("NAME");
        String idNo=request.getParameter("IDNO");
        AjaxJsonCode j =exeDataService.getResultOfSign(exeId,name,idNo);
        return j;
    }

    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/fontSignInfo")
    public ModelAndView fontSignInfo(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> signInfo=exeDataService.findSignListById(exeId);
        request.setAttribute("signInfoList", signInfo);
        request.setAttribute("exeId",exeId);
        return new ModelAndView("website/yhzx/fontSigninfo");
    }
    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/sendMsgByTime")
    @ResponseBody
    public AjaxJson sendMsgByTime(HttpServletRequest request) {
        exeDataService.sendMsgByTime();
        return  new AjaxJson();
    }
    /**
     * 描述:前台用户展示面签详情（devbase接口）
     *
     * @author Madison You
     * @created 2020/7/15 16:13:00
     * @param
     * @return
     */
    @RequestMapping("/fontSignInfoForDevbase")
    @ResponseBody
    public Map<String, Object> fontSignInfoForDevbase(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String,Object>> signInfo=exeDataService.findSignListById(exeId);
        returnMap.put("signInfoList", signInfo);
        return returnMap;
    }

    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/uploadCompanyFileView")
    public ModelAndView uploadCompanyFileView(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        String itemCode=request.getParameter("itemCode");
        List<Map<String,Object>> companyLegal=exeDataService.getAllByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String rJson=JSONObject.toJSONString(companyLegal);
        request.setAttribute("rJson",rJson);
        request.setAttribute("rList", companyLegal);
        request.setAttribute("exeId",exeId);
        request.setAttribute("itemCode",itemCode);
        return new ModelAndView("website/yhzx/uploadCompanyFileView");
    }
    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/commercialOpinionView")
    public ModelAndView commercialOpinionView(HttpServletRequest request) {
        return new ModelAndView("hflow/execution/commercialOpinionView");
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "opinionDataGrid")
    public void opinionDataGrid(HttpServletRequest request,
                                   HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","DESC");
        List<Map<String, Object>> list = exeDataService.findCommercialOpinionList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/goSignNextStep")
    @ResponseBody
    public AjaxJsonCode goSignNextStep(HttpServletRequest request) {
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        // 获取流程申报号
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> needSignLegalList=exeDataService.findLegalCompanyByexeId(exeId);
        List<Map<String,Object>> havedSignLegalList=exeDataService.getAllByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID"},new Object[]{exeId});
        //判断是否全部完成面签，才可上传附件公章信息
        boolean isSignFinish = true;
        List<Map<String, Object>> list = exeDataService.findSignListById(exeId);
        if(list.size()>0){
            for (Map<String, Object> map : list) {
                if(!"1".equals(StringUtil.getString(map.get("SIGN_FLAG")))){
                    isSignFinish = false;
                    break;
                }
            }
            if(isSignFinish==false){
                ajaxJsonCode.setSuccess(false);
                ajaxJsonCode.setMsg("请先完成所有面签，在上传公章附件信息！");
            }else{
                if(needSignLegalList.size()>0&&needSignLegalList.size()==havedSignLegalList.size()){
                    ajaxJsonCode.setSuccess(true);
                    ajaxJsonCode.setMsg("面签成功");
                    exeDataService.changeSignStatus(exeId,"1");
                }else if(needSignLegalList.size()!=havedSignLegalList.size()){
                    ajaxJsonCode.setSuccess(false);
                    ajaxJsonCode.setMsg("请上传公司法人公章附件");
                }else if(needSignLegalList.size()==0){
                    ajaxJsonCode.setSuccess(false);
                    ajaxJsonCode.setMsg("此办件不需要上传法人公章附件");
                }else if(havedSignLegalList.size()==0){
                    ajaxJsonCode.setSuccess(false);
                    ajaxJsonCode.setMsg("此办件暂时不需要上传法人公章附件");
                }
            }
        }
        return ajaxJsonCode;
    }
    /**
     *前台用户展示面签详情
     * @param request
     * @return
     */
    @RequestMapping("/uploadCompanyFile")
    public void uploadCompanyFile(HttpServletRequest request) {
        String companyId = request.getParameter("companyId");
        String fileId = request.getParameter("legalFileId");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("LEGAL_FILEID",fileId);
        map.put("UPLOAD_FLAG","1");
        exeDataService.saveOrUpdate(map,"T_COMMERCIAL_COMPANY_LEGALFILE",companyId);
    }
    /**
     * 商事面签认证
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "uploadBaseCode")
    @ResponseBody
    public AjaxJson uploadBaseCode(HttpServletRequest request) {
        String token=request.getParameter("TOKEN");//验证的token
        String baseCode=request.getParameter("UPLOADBASECODE");//上传的base64编码
        String type=request.getParameter("TYPE");//类型
        AjaxJson j=exeDataService.uploadAndSaveImg(token,baseCode,type);
        return j;
    }
    /**
     * 获得公司名称
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping("/getCompanyNameByToken")
    @ResponseBody
    public AjaxJson getCompanyNameByToken(HttpServletRequest request) {
        String token=request.getParameter("TOKEN");//验证的token
        AjaxJson j=exeDataService.getCompanyNameByToken(token);
        return j;
    }
    /**
     *
     * 跳转至子行业信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectRegisterAddrView")
    public ModelAndView selectRegisterAddrView(HttpServletRequest request) {
        return new ModelAndView("commercial/industry/selectRegisterAddrView");
    }
    /**
     * 修改用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/saveCommercialOpinion")
    @ResponseBody
    public AjaxJson updateUserInfo(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String content = request.getParameter("OPINION_CONTENT");
        if(StringUtils.isNotEmpty(content)){
            exeDataService.saveOrUpdate(variables,"T_COMMERCIAL_OPINION","");
            j.setMsg("修改成功！");
        }else{
            j.setMsg("修改失败！");
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 修改面签状态
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateSignStatus")
    @ResponseBody
    public AjaxJson updateSignStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = new HashMap<String,Object>();
        String signId = request.getParameter("signId");
        String opinion=request.getParameter("opinion");
        String signFlag=request.getParameter("signFlag");
        if(StringUtils.isNotEmpty(signId)){
            Map<String,Object> sign=exeDataService.getByJdbc("T_COMMERCIAL_SIGN",
                    new String[]{"SIGN_ID"},new Object[]{signId});
            String exeId=StringUtil.getString(sign.get("EXE_ID"));
            String signName=StringUtil.getString(sign.get("SIGN_NAME"));
            String operationalName=AppUtil.getLoginUser().getFullname();
            log.info(String.format("%s审核办件id=%s，面签人为%s，审核结果为%s",operationalName,exeId,signName,signFlag));
            variables.put("SIGN_FLAG",signFlag);
            variables.put("SIGN_OPINION",opinion);
            exeDataService.saveOrUpdate(variables,"T_COMMERCIAL_SIGN",signId);
            //面签审核不通过时，清除办件公章附件信息
            if("-1".equals(signFlag)){
                exeDataService.remove("T_COMMERCIAL_COMPANY_LEGALFILE",new String[]{"EXE_ID"},new Object[]{exeId});
            }
            j.setMsg("修改成功！");
        }else{
            j.setMsg("修改失败！");
            j.setSuccess(false);
        }
        return j;
    }
    /**
     * 商事面签认证
     *
     * @param request 传入参数
     * @return 返回值AjaxJson
     */
    @RequestMapping(params = "getResultOfSignJsonP")
    @ResponseBody
    public String getResultOfSignJsonP(@RequestParam("callback") String callback,HttpServletRequest request) {
        String exeId=request.getParameter("EXEID");
        String name=request.getParameter("NAME");
        String idNo=request.getParameter("IDNO");
        AjaxJsonCode j =exeDataService.getResultOfSign(exeId,name,idNo);
        return callback+"("+ JSONObject.toJSONString(j)+")";
    }
    /**
     * 跳转到信息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "commercialSignView")
    public ModelAndView commercialSignView(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        request.setAttribute("exeId", exeId);
        return new ModelAndView("zzhy/sign/commercialSignView");
    }
    /**
     * easyui 面签信息展示
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "signDatagrid")
    public void signDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String, Object>> list = exeDataService.findSignListById(exeId);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui 面签信息展示
     *
     * @param request
     * @param response
     */
    @RequestMapping("/companyFileDatagrid")
    public void companyFileDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        List<Map<String,Object>> companyLegal=exeDataService.getAllByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID"},new Object[]{exeId});
        this.setListToJsonString(companyLegal.size(), companyLegal, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui 面签信息展示
     *
     * @param request
     * @param response
     */
    @RequestMapping("/dicTypeByParentDatagrid")
    public void dicTypeByParentDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parentId");
        List<Map<String,Object>> dicTypes=dicTypeService.findProvince(parentId);
        this.setListToJsonString(dicTypes.size(), dicTypes, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 跳转到获取信用平台公司信息页面
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "companyInfoByCreditView")
    public ModelAndView companyInfoByCreditView(HttpServletRequest request) {
        String sqrsfz = request.getParameter("SQRSFZ");
        request.setAttribute("sqrsfz", sqrsfz);
        String entName = request.getParameter("entName");
        request.setAttribute("entName", entName);
        return new ModelAndView("website/applyforms/change/T_CREDIT_COMPANY_VIEW");
    }
    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping("/findCreditCompanyInfos")
    public void findCreditCompanyInfos(HttpServletRequest request,
                              HttpServletResponse response) {
        String entName = request.getParameter("ENT_NAME");//企业名称
        String sqrsfz=request.getParameter("sqrsfz");//身份证号码
        List<Map<String,Object>> list= Lists.newArrayList();
        if(StringUtils.isNotEmpty(sqrsfz)){
            list=exeDataService.findCreditCompanyInfos(sqrsfz,entName);
        }
        this.setListToJsonString(list.size(), list,
                    null, JsonUtil.EXCLUDE, response);
    }
    /**
     *
     * 描述  推送字段名
     * @created 2016-11-17 上午11:31:23
     * @param request
     * @param response
     */
    @RequestMapping("/pushCompanyName")
    @ResponseBody
    public AjaxJson pushCompanyName(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map=BeanUtil.getMapFromRequest(request);
        AjaxJson j=exeDataService.pushCompanyName(map);
        return j;
    }
    /**
     *
     * 描述  推送字段名
     * @created 2016-11-17 上午11:31:23
     * @param request
     * @param response
     */
    @RequestMapping("/getCompanyNameResult")
    @ResponseBody
    public AjaxJsonCode getCompanyNameResult(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> map=BeanUtil.getMapFromRequest(request);
        AjaxJsonCode j=exeDataService.getCompanyNameResult(map);
        return j;
    }
    
    /**
     * 
     * 描述：名称查重保存（个体）
     * @author Rider Chen
     * @created 2021年1月13日 下午2:27:55
     * @param request
     * @param response
     * @return
     */
   @RequestMapping("/pushCompanyNameToGt")
   @ResponseBody
   public AjaxJson pushCompanyNameToGt(HttpServletRequest request, HttpServletResponse response) {
       Map<String,Object> map=BeanUtil.getMapFromRequest(request);
       AjaxJson j=exeDataService.pushCompanyNameToGt(map);
       return j;
   }
   /**
    * 
    * 描述：名称查重结果查询（个体）
    * @author Rider Chen
    * @created 2021年1月13日 下午2:28:23
    * @param request
    * @param response
    * @return
    */
   @RequestMapping("/getCompanyNameResultToGt")
   @ResponseBody
   public AjaxJsonCode getCompanyNameResultToGt(HttpServletRequest request, HttpServletResponse response) {
       Map<String,Object> map=BeanUtil.getMapFromRequest(request);
       AjaxJsonCode j=exeDataService.getCompanyNameResultToGt(map);
       return j;
   }
}