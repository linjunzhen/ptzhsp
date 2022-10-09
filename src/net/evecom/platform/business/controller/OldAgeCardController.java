/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.business.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.HttpRequestUtil;
import net.evecom.core.util.IdCardUtils;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.business.service.OldAgeCardService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.ptwg.service.OnlineApplyService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * 描述 老年人优待证业务管理Controller 
 * @author Bryce Zhang
 * @created 2017-5-15 上午9:01:56
 */
@Controller
@RequestMapping("/oldAgeCardController")
public class OldAgeCardController extends BaseController{
    
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(OldAgeCardController.class);
    
    /**
     * 老年人优待证业务管理Service
     */
    @Resource
    private OldAgeCardService oldAgeCardService;
    
    /**
     * 系统日志管理Service
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * 附件管理Service
     */
    @Resource
    private FileAttachService fileAttachService;
    
    /**
     * 服务事项管理Service
     */
    @Resource
    private ServiceItemService serviceItemService;
    
    /**
     * 网格在线申请Service
     */
    @Resource
    private OnlineApplyService onlineApplyService;
    
    /**
     * 流程定义管理Service
     */
    @Resource
    private FlowDefService flowDefService;
    
    /**
     * 事项材料管理Service
     */
    @Resource
    private ApplyMaterService applyMaterService;
    
    /**
     * 描述 跳转至老年人优待证管理页
     * @author Bryce Zhang
     * @created 2017-5-22 上午9:08:34
     * @param request
     * @return
     */
    @RequestMapping(params = "OldAgeCardView")
    public ModelAndView oldAgeCardView(HttpServletRequest request){
        return new ModelAndView("business/oldAgeCard/OldAgeCardView");
    }
    
    /**
     * 描述 对接公安人像比对入口
     * @author Bryce Zhang
     * @created 2017-5-15 上午9:05:15
     * @param request
     * @return
     */
    @RequestMapping(params = "faceCompare")
    @ResponseBody
    public AjaxJson faceCompare(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        //1、检查请求参数是否合法
        String name = request.getParameter("name");
        String idNum = request.getParameter("idNum");
        String fileId = request.getParameter("fileId");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(idNum) || StringUtils.isEmpty(fileId)){
            j.setSuccess(false);
            j.setMsg("人像比对请求失败，参数缺失。");
            return j;
        }
        Map<String, Object> fileAttach = fileAttachService.getByJdbc(
                                "T_MSJW_SYSTEM_FILEATTACH", new String[]{"FILE_ID"}, new Object[]{fileId});
        if(fileAttach == null || fileAttach.get("FILE_PATH") == null){
            j.setSuccess(false);
            j.setMsg("人像比对请求失败，图片文件记录为空。");
            return j;
        }
        Properties properties = FileUtil.readProperties("project.properties");
        String imgFilePath = properties.getProperty("uploadFileUrlIn") + fileAttach.get("FILE_PATH");
        try {
            InputStream is = HttpRequestUtil.getStreamDownloadOutFile(imgFilePath);
            String imgBase64Code = FileUtil.encodeBase64File(is);
            //2、调用人像比对接口，查得结果
            String requestIp = BrowserUtils.getIpAddr(request);
            j = oldAgeCardService.faceCompare(name, idNum, imgBase64Code, requestIp);
            return j;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("", e);
            j.setSuccess(false);
            j.setMsg("人像比对请求失败，图片文件不存在。");
            return j;
        }
    }
    
    /**
     * 描述 跳转至待我审批页面
     * @author Bryce Zhang
     * @created 2017-5-15 下午4:41:31
     * @param request
     * @return
     */
    @RequestMapping(params = "goNeedMeHandle")
    public ModelAndView goNeedMeHandle(HttpServletRequest request){
        return new ModelAndView("business/oldAgeCard/needMeHandle");
    }
    
    /**
     * 描述 待我审批datagrid数据获取
     * @author Bryce Zhang
     * @created 2017-5-15 下午5:59:45
     * @param request
     * @param response
     */
    @RequestMapping(params = "needMeHandle")
    public void needMeHandle(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        SysUser loginUser = AppUtil.getLoginUser();
        filter.addFilter("Q_T.ASSIGNER_CODE_EQ", loginUser.getUsername());
        List<Map<String, Object>> list = oldAgeCardService.findNeedMeHandleBySqlFilter(filter);
        this.setListToJsonString(list.size(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 跳转至经我审批页面
     * @author Bryce Zhang
     * @created 2017-5-16 上午8:59:13
     * @param request
     * @return
     */
    @RequestMapping(params = "goHandledByMe")
    public ModelAndView goHandledByMe(HttpServletRequest request){
        return new ModelAndView("business/oldAgeCard/handledByMe");
    }
    
    /**
     * 描述 经我审批datagrid数据获取
     * @author Bryce Zhang
     * @created 2017-5-16 上午9:51:28
     * @param request
     * @param response
     */
    @RequestMapping(params = "handledByMe")
    public void handledByMe(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME","desc");
        SysUser loginUser = AppUtil.getLoginUser();
        List<Map<String, Object>> list = oldAgeCardService.findHandledByUser(filter, loginUser.getUsername());
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 根据所属部门，跳转至老年证办理页
     * @author Bryce Zhang
     * @created 2017-5-22 上午10:58:00
     * @param request
     * @return
     */
    @RequestMapping(params = "newApply")
    public ModelAndView newApply(HttpServletRequest request){
        SysUser loginUser = AppUtil.getLoginUser();
        String deptCode = loginUser.getDepCode();
        //非超级管理员
        if(!loginUser.getResKeys().equals(SysUser.ADMIN_RESKEY)){
            Map<String, Object> serviceItem = serviceItemService.getByNameAndDeptcode("老年优待证", deptCode);
            if(serviceItem != null){
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 
                                  request.getServerPort() + request.getContextPath() + "/";
                String applyUrlPre = "executionController.do?goStart&defKey=OldAgeCardNewFlow&itemCode=";
                return new ModelAndView("redirect:"+basePath+applyUrlPre+serviceItem.get("ITEM_CODE"));
            }else{
                return null;
            }
        }else{
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 
                    request.getServerPort() + request.getContextPath() + "/";
            String applyUrlPre = "executionController.do?goStart&defKey=OldAgeCardNewFlow&itemCode=";
            return new ModelAndView("redirect:"+basePath+applyUrlPre+"00041GF00801");
        }
    }
    
    /**
     * 描述 跳转至数据查询页面
     * @author Bryce Zhang
     * @created 2017-5-22 上午11:39:21
     * @param request
     * @return
     */
    @RequestMapping(params = "goListByAuth")
    public ModelAndView goListByAuth(HttpServletRequest request){
        return new ModelAndView("business/oldAgeCard/listByAuth");
    }
    
    /**
     * 描述 数据查询datagrid（数据集权限）
     * @author Bryce Zhang
     * @created 2017-5-22 下午2:06:17
     * @param request
     * @param response
     */
    @RequestMapping(params = "listByAuth")
    public void listByAuth(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("E.CREATE_TIME","desc");
        SysUser loginUser = AppUtil.getLoginUser();
        List<Map<String, Object>> list = oldAgeCardService.findListByAuth(filter, loginUser);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述 申请检查
     * @author Bryce Zhang
     * @created 2017-5-26 上午11:45:58
     * @param request
     * @param idNum
     * @return
     */
    @RequestMapping(params = "applyCheck")
    @ResponseBody
    public AjaxJson applyCheck(HttpServletRequest request, String idNum){
        AjaxJson j = new AjaxJson();
        //1、检查请求参数
        if(StringUtils.isEmpty(idNum)){
            j.setSuccess(false);
            j.setMsg("请求失败,参数缺失!");
            return j;
        }
        boolean idvalid = IdCardUtils.validateCard(idNum);
        if(!idvalid){
            j.setSuccess(false);
            j.setMsg("申请失败,申请人身份证号码不合法!");
            return j;
        }
        //2、申请年龄判断
        int age = IdCardUtils.getAgeByIdCard(idNum);
        int cardType;
        if(age < 60){
            j.setSuccess(false);
            j.setMsg("申请失败,申请人年龄未达到《老年人优待证》办理要求!");
            return j;
        }else if(age < 70){
            cardType = 1;
        }else{
            cardType = 2;
        }
        //3、重复办件判断
        Map<String, Object> apply = oldAgeCardService.getByIdnumAndCardType(idNum, cardType);
        if(apply != null){
            j.setSuccess(false);
            j.setMsg("申请失败,重复办理!");
            return j;
        }
        String gender = IdCardUtils.getGenderByIdCard(idNum);
        int sex = 0;
        if("M".equals(gender)){
            sex = 1;
        }else if("F".equals(gender)){
            sex = 2;
        }
        String orgBirth = IdCardUtils.getBirthByIdCard(idNum);
        String formatBirth = "";
        if(StringUtils.isNotEmpty(orgBirth)){
            Date orgBirthDate = DateTimeUtil.getDateOfStr(orgBirth, "yyyyMMdd");
            formatBirth = DateTimeUtil.getStrOfDate(orgBirthDate, "yyyy-MM-dd");
        }
        j.setJsonString("{\"age\":"+age+",\"cardType\":"+cardType+",\"sex\":"+sex+",\"birthday\":\""+formatBirth+"\"}");
        return j;
    }
    
    /**
     * 描述 检验身份证号
     * @author Bryce Zhang
     * @created 2017-7-14 上午11:53:53
     * @param request
     * @param idNum
     * @return
     */
    @RequestMapping(params = "validIdNum")
    @ResponseBody
    public AjaxJson validIdNum(HttpServletRequest request, String idNum){
        AjaxJson j = new AjaxJson();
        //1、检查请求参数
        if(StringUtils.isEmpty(idNum)){
            j.setSuccess(false);
            j.setMsg("请求失败,参数缺失!");
            return j;
        }
        boolean idvalid = IdCardUtils.validateCard(idNum);
        if(!idvalid){
            j.setSuccess(false);
            j.setMsg("请求失败,身份证号码不合法!");
            return j;
        }
        //2、计算年龄、出生、性别等
        int age = IdCardUtils.getAgeByIdCard(idNum);
        String orgBirth = IdCardUtils.getBirthByIdCard(idNum);
        String formatBirth = "";
        if(StringUtils.isNotEmpty(orgBirth)){
            Date orgBirthDate = DateTimeUtil.getDateOfStr(orgBirth, "yyyyMMdd");
            formatBirth = DateTimeUtil.getStrOfDate(orgBirthDate, "yyyy-MM-dd");
        }
        String gender = IdCardUtils.getGenderByIdCard(idNum);
        int sex = 0;
        if("M".equals(gender)){
            sex = 1;
        }else if("F".equals(gender)){
            sex = 2;
        }
        //3、返回结果
        j.setJsonString("{\"age\":"+age+",\"sex\":"+sex+",\"birthday\":\""+formatBirth+"\"}");
        return j;
    }
    
    /**
     * 描述 跳转至补换证件页面 
     * @author Bryce Zhang
     * @created 2017-5-27 上午9:30:20
     * @param request
     * @param busId
     * @return
     */
    @RequestMapping(params = "goChangeApply")
    public ModelAndView goChangeApply(HttpServletRequest request, String busId){
        if(StringUtils.isEmpty(busId)){
            return null;
        }
        Map<String, Object> oldAgeCard = oldAgeCardService.getByBusId(busId);
        request.setAttribute("oldAgeCard", oldAgeCard);
        return new ModelAndView("business/oldAgeCard/ChangeApplyForm");
    }
    
    /**
     * 描述 跳转至遗失声明打印页面
     * @author Bryce Zhang
     * @created 2017-5-27 下午2:52:47
     * @param request
     * @param busId
     * @return
     */
    @RequestMapping("/printLostState")
    public ModelAndView printLostState(HttpServletRequest request, String busId){
        if(StringUtils.isEmpty(busId)){
            return null;
        }
        Map<String, Object> oldAgeCard = oldAgeCardService.getByBusId(busId);
        request.setAttribute("oldAgeCard", oldAgeCard);
        return new ModelAndView("business/oldAgeCard/OldAgeCardLostState");
    }
    
    /**
     * 描述 查询业务
     * @author Bryce Zhang
     * @created 2017-7-14 下午4:02:38
     * @param request
     * @param busId
     * @return
     */
    @RequestMapping(params = "get")
    @ResponseBody
    public AjaxJson get(HttpServletRequest request, String busId){
        AjaxJson j = new AjaxJson();
        if(StringUtils.isEmpty(busId)){
            j.setSuccess(false);
            j.setMsg("操作失败,参数缺失!");
            return j;
        }
        Map<String, Object> oldAgeCard = oldAgeCardService.getByBusId(busId);
        if(oldAgeCard == null){
            j.setSuccess(false);
            j.setMsg("操作失败,系统未查找到该业务记录!");
            return j;
        }
        j.setJsonString(JSON.toJSONString(oldAgeCard));
        return j;
    }
    
    /**
     * 描述 更新证件注销入口
     * @author Bryce Zhang
     * @created 2017-5-27 下午5:05:58
     * @param request
     * @return
     */
    @RequestMapping(params = "updateUnregister")
    @ResponseBody
    public AjaxJson updateUnregister(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String busId = request.getParameter("BUSINESS_ID");
        String lostStateId = request.getParameter("LOSTSTATE_FILEID");
        String lostStatePath = request.getParameter("LOSTSTATE_PATH");
        String lostStateName = request.getParameter("LOSTSTATE_FILENAME");
        if(StringUtils.isEmpty(busId) || StringUtils.isEmpty(lostStateId) || 
           StringUtils.isEmpty(lostStateName) || StringUtils.isEmpty(lostStatePath)){
            j.setSuccess(false);
            j.setMsg("请求失败,参数缺失!");
            return j;
        }
        oldAgeCardService.updateUnregister(busId, lostStateId, lostStateName, lostStatePath);
        j.setMsg("已成功注销该证件!");
        return j;
    }
    
    /**
     * 描述 导出数据入口
     * @author Bryce Zhang
     * @created 2017-5-29 下午8:38:26
     * @param request
     * @param response
     */
    @RequestMapping(params = "export")
    public void export(HttpServletRequest request, HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("E.CREATE_TIME","desc");
        SysUser loginUser = AppUtil.getLoginUser();
        List<Map<String, Object>> list = oldAgeCardService.findListByAuth4Exp(filter, loginUser);
        OutputStream ouputStream = null;
        try{
            HSSFWorkbook workbook = oldAgeCardService.generateExcel(list);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="
                          + new String("《福建省老年人优待证》业务数据.xls".getBytes("gbk"), "iso8859-1"));
            ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();
        }catch(Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }finally{
            if(ouputStream != null){
                try{
                    ouputStream.close();
                }catch(IOException e){
                    log.error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }
    
    /**
     * 描述 APP端申请验证
     * @author Bryce Zhang
     * @created 2017-7-31 下午5:32:43
     * @param request
     * @return
     */
    @RequestMapping("/appApplyCheck")
    @ResponseBody
    public AjaxJson appApplyCheck(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        //1、检查请求参数是否合法
        String name = request.getParameter("name");
        String idNum = request.getParameter("idNum");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(idNum)){
            j.setSuccess(false);
            j.setMsg("申请验证请求失败，参数缺失!");
            return j;
        }
        boolean idvalid = IdCardUtils.validateCard(idNum);
        if(!idvalid){
            j.setSuccess(false);
            j.setMsg("申请失败,申请人身份证号码不合法!");
            return j;
        }
        //2、申请年龄判断
        int age = IdCardUtils.getAgeByIdCard(idNum);
        int cardType;
        if(age < 60){
            j.setSuccess(false);
            j.setMsg("申请失败,申请人年龄未达到《老年人优待证》办理要求!");
            return j;
        }else if(age < 70){
            cardType = 1;
        }else{
            cardType = 2;
        }
        //3、姓名与身份证公安验证
//        String requestIp = BrowserUtils.getIpAddr(request);
//        j = oldAgeCardService.idCompare(name, idNum, requestIp);
//        if(!j.isSuccess()){
//            return j;
//        }
        //4、重复办件判断
        Map<String, Object> apply = oldAgeCardService.getByIdnumAndCardType(idNum, cardType);
        if(apply != null){
            j.setSuccess(false);
            j.setMsg("申请失败,重复办理!");
            return j;
        }
        String gender = IdCardUtils.getGenderByIdCard(idNum);
        int sex = 0;
        if("M".equals(gender)){
            sex = 1;
        }else if("F".equals(gender)){
            sex = 2;
        }
        j.setJsonString("{\"age\":"+age+",\"cardType\":"+cardType+",\"sex\":"+sex+"}");
        return j;
    }
    
    /**
     * 描述 根据事项部门编码，APP端获取事项流程等信息
     * @author Bryce Zhang
     * @created 2017-8-1 上午10:35:54
     * @param request
     * @return
     */
    @RequestMapping("/appGetServiceItem")
    @ResponseBody
    public AjaxJson appGetServiceItem(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        //1、检查请求参数是否合法
        String deptCode = request.getParameter("deptCode");
        if(StringUtils.isEmpty(deptCode)){
            j.setSuccess(false);
            j.setMsg("数据请求失败，参数缺失!");
            return j;
        }
        Map<String, Object> rspJson = new HashMap<String, Object>();
        //2、查询组装服务事项数据
        Map<String, Object> serviceItem = serviceItemService.getByNameAndDeptcode("老年优待证", deptCode);
        if(serviceItem != null){
            rspJson.put("itemName", serviceItem.get("ITEM_NAME"));
            rspJson.put("itemCode", serviceItem.get("ITEM_CODE"));
            Map<String,Object> flowDef = flowDefService.getByJdbc("JBPM6_FLOWDEF",
                               new String[]{"DEF_ID"},new Object[]{serviceItem.get("BDLCDYID")});
            rspJson.put("defKey", flowDef.get("DEF_KEY"));
            //3、获取流程材料信息
            List<Map<String, Object>> applyMaters = 
                applyMaterService.findMaterByItemCode((String)serviceItem.get("ITEM_CODE"));
            if(applyMaters != null){
                rspJson.put("applyMaters", applyMaters);
            }
        }else{
            j.setSuccess(false);
            j.setMsg("数据请求失败，未查找到该乡镇的服务事项!");
            return j;
        }
        //4、验证网格用户
        String userId = onlineApplyService.checkGridUser(request);
        rspJson.put("userId", userId);
        //5、响应结果
        j.setJsonString(JSON.toJSONString(rspJson));
        return j;
    }

}
