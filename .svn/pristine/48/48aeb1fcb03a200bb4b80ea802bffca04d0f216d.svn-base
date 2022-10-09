/*
 * Copyright (c) 2005, 2021, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.GenPlatReqUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.ExtraDictionaryService;

/**
 * 描述
 * @author Danto Huang
 * @created 2021年4月1日 下午3:27:02
 */
@Controller
@RequestMapping("/enterpriseChangeController")
public class EnterpriseChangeController extends BaseController {
    
    /**
     * 引入extraDictionaryService
     */
    @Resource
    private ExtraDictionaryService extraDictionaryService;
    
    /**
     * 
     * 获取变更选项列表
     * @author Danto Huang
     * @created 2021年4月1日 下午3:51:52
     * @param request
     * @return
     */
    @RequestMapping("/findChangeOption")
    @ResponseBody
    public AjaxJson findChangeOption(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        List<Map<String, Object>> list = extraDictionaryService.getDictonaryByTypeCode("changeItem");
        j.setJsonString(JSON.toJSONString(list));
        return j;
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2021年4月2日 上午9:20:43
     * @param request
     * @param response
     */
    @RequestMapping("/queryCreditEnterprise")
    @ResponseBody
    public AjaxJson queryCreditEnterprise(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String socialCreditUnicode = request.getParameter("socialCreditUnicode");
        String companyName = request.getParameter("companyName");
        Map<String,Object> queryParams = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(socialCreditUnicode)){
            queryParams.put("SOCIAL_CREDIT_UNICODE", socialCreditUnicode);
        }
        if(StringUtils.isNotEmpty(companyName)){
            queryParams.put("ENTNAME", companyName);
        }

        Properties properties = FileUtil.readProperties("conf/config.properties");
        String url =properties.getProperty("genplatrequrlIn");
        url+="?servicecode=getCompanyInfo";
        queryParams.put("grantcode", "BFbhXSKZXvjii1bMaLWg");
        String result = GenPlatReqUtil.sendPostParams(url, queryParams);
        if(StringUtils.isNotEmpty(result)){
            Map<String,Object> datas = JSON.parseObject(result,Map.class);
            Boolean success = (Boolean) datas.get("success");
            if(success){
                Map<String, Object> data = (Map<String, Object>) datas.get("data");
                if (null != data && data.size() > 0) {
                    Map<String, Object> baseInfo = (Map<String, Object>) data.get("baseInfo");
                    String entType = StringUtil.getString(baseInfo.get("ENTTYPE"));
                    boolean flag = isEntTypeToNz(entType);
                    if (flag) {
                        j.setSuccess(true);
                        j.setJsonString(JSON.toJSONString(datas.get("data")));
                    } else {
                        j.setSuccess(false);
                        j.setMsg("请输入内资有限责任公司“国有独资”，“国有控股”，“自然人独资”，“法人独资”，“自然人投资或控股”<br/>，“其他有限责任公司”的企业信息!");
                    }
                } else {
                    j.setSuccess(false);
                    j.setMsg("查询不到数据!");
                }
            }else{
                j.setSuccess(false);
                j.setMsg("查询主体信息失败");
            }
        }       
        return j;
    }
    
    /**
     * 
     * 描述 判断公司类型
     * 
     * @author Rider Chen
     * @created 2021年4月6日 上午11:52:54
     * @param entType
     * @return
     */
    public boolean isEntTypeToNz(String entType) {
        boolean flag = false;
        switch (entType) {
            case "1110":// 有限责任公司(国有独资)
                flag = true;
                break;
            case "1130":// 有限责任公司(自然人投资或控股)
                flag = true;
                break;
            case "1140":// 有限责任公司(国有控股)
                flag = true;
                break;
            case "1150":// 一人有限责任公司
                flag = true;
                break;
            case "1151":// 有限责任公司(自然人独资)
                flag = true;
                break;
            case "1152":// 有限责任公司(自然人投资或控股的法人独资)
                flag = true;
                break;
            case "1153":// 有限责任公司（非自然人投资或控股的法人独资）
                flag = true;
                break;
            case "1190":// 其他有限责任公司
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        return flag;
    }

    /**
     * 跳转到股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGdxxDiv")
    public ModelAndView refreshGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/ssqcwb/change/gdxxDiv");
    }

    /**
     * 跳转到新股东信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshNewGdxxDiv")
    public ModelAndView refreshNewGdxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/ssqcwb/change/newGdxxDiv");
    }

    /**
     * 跳转到股权来源信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshGqlyDiv")
    public ModelAndView refreshGqlyDiv(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/ssqcwb/change/gqlyDiv");
    }

    /**
     * 跳转到原董事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldDsxxDiv")
    public ModelAndView refreshOldDsxxDiv(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/ssqcwb/change/oldDsxxDiv");
    }

    /**
     * 跳转到原监事信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldJsxxDiv")
    public ModelAndView refreshOldJsxxDiv(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/ssqcwb/change/oldJsxxDiv");
    }

    /**
     * 跳转到原经理信息界面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/refreshOldJlxxDiv")
    public ModelAndView refreshOldJlxxDiv(HttpServletRequest request) {
        return new ModelAndView("website/applyforms/ssqcwb/change/oldJlxxDiv");
    }
    /**
     * 
     * 跳转到法人信息界面
     * @author Danto Huang
     * @created 2021年4月15日 上午10:46:01
     * @param request
     * @return
     */
    @RequestMapping("/refreshDsxxDiv")
    public ModelAndView refreshDsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        String ssdjzw = request.getParameter("ssdjzw");
        request.setAttribute("ssdjzw", ssdjzw);
        return new ModelAndView("website/applyforms/ssqcwb/change/dsxxDiv");
    }
    /**
     * 
     * 跳转到法人信息界面
     * @author Danto Huang
     * @created 2021年4月15日 上午10:46:01
     * @param request
     * @return
     */
    @RequestMapping("/refreshJsxxDiv")
    public ModelAndView refreshJsxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/ssqcwb/change/jsxxDiv");
    }
    /**
     * 
     * 跳转到法人信息界面
     * @author Danto Huang
     * @created 2021年4月15日 上午10:46:01
     * @param request
     * @return
     */
    @RequestMapping("/refreshJlxxDiv")
    public ModelAndView refreshJlxxDiv(HttpServletRequest request) {
        String currentTime = UUIDGenerator.getUUID();
        request.setAttribute("currentTime", currentTime);
        return new ModelAndView("website/applyforms/ssqcwb/change/jlxxDiv");
    }
    
    /**
     * 
     * 跳转到法人信息界面
     * @author Danto Huang
     * @created 2021年4月15日 上午10:46:01
     * @param request
     * @return
     */
    @RequestMapping("/refreshLegalDiv")
    public ModelAndView refreshLegalDiv(HttpServletRequest request) {
        String ssdjzw = request.getParameter("ssdjzw");
        request.setAttribute("ssdjzw", ssdjzw);
        return new ModelAndView("website/applyforms/ssqcwb/change/legalDiv");
    }
}
