/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.ImageRemarkUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.PdfRemarkUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.commercial.service.BankService;
import net.evecom.platform.system.service.FileAttachService;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年12月4日 上午9:55:29
 */
@Controller
@RequestMapping("/bankDealController")
public class BankDealController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BankDealController.class);

    /**
     * 引入bankService
     */
    @Resource
    private BankService bankService;
    /**
     * 
     * 描述 工商页面
     * @author Danto Huang
     * @created 2017年12月4日 上午9:59:13
     * @param request
     * @return
     */
    @RequestMapping(params="uplaodView")
    public ModelAndView uplaodView(HttpServletRequest request){
        
        return new ModelAndView("commercial/bank/uploadView");
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月4日 上午10:10:36
     * @param request
     * @param response
     */
    @RequestMapping(params="uploadGrid")
    public void uploadGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.create_time", "asc");
        List<Map<String,Object>> list = bankService.findForUpload(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月4日 下午2:26:27
     * @param request
     * @return
     */
    @RequestMapping(params="uploadInfo")
    public ModelAndView uploadInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> uploadInfo = bankService.getByJdbc("T_COMMERCIAL_BANKFILE", new String[] { "EXE_ID" },
                new Object[] { entityId });
        if(uploadInfo==null){
            uploadInfo = new HashMap<String, Object>();
            uploadInfo.put("EXE_ID", entityId);
        }
        request.setAttribute("uploadInfo", uploadInfo);
        return new ModelAndView("commercial/bank/uploadInfo");
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月4日 下午3:23:09
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        bankService.saveUploadInfo(variables);
        j.setMsg("保存成功");
        return j;
    }
    /**
     * 
     * 描述 银行页面
     * @author Danto Huang
     * @created 2017年12月4日 上午10:00:48
     * @param request
     * @return
     */
    @RequestMapping(params="downLoadView")
    public ModelAndView downLoadView(HttpServletRequest request){
        
        return new ModelAndView("commercial/bank/downloadView");
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月4日 上午10:10:36
     * @param request
     * @param response
     */
    @RequestMapping(params="downloadGrid")
    public void downloadGrid(HttpServletRequest request,HttpServletResponse response){
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("t.create_time", "asc");
        Set<String> roleCodes = AppUtil.getLoginUser().getRoleCodes();
        if(roleCodes.contains("ccb")){
            filter.addFilter("Q_t.bank_type_=", "ccb");
        }else if(roleCodes.contains("pdb")){
            filter.addFilter("Q_t.bank_type_=", "pdb");
        }
        filter.addFilter("Q_t.status_=", "已上传");
        List<Map<String,Object>> list = bankService.findForUpload(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
        
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月4日 下午2:26:27
     * @param request
     * @return
     */
    @RequestMapping(params="downloadInfo")
    public ModelAndView downloadInfo(HttpServletRequest request){
        String entityId = request.getParameter("entityId");
        Map<String, Object> uploadInfo = bankService.getByJdbc("T_COMMERCIAL_BANKFILE", new String[] { "EXE_ID" },
                new Object[] { entityId });
        String itemCode = bankService.getByJdbc("jbpm6_execution", new String[] { "EXE_ID" },
                new Object[] { entityId }).get("ITEM_CODE").toString();
        uploadInfo.put("itemCode", itemCode);
        request.setAttribute("uploadInfo", uploadInfo);
        return new ModelAndView("commercial/bank/downloadInfo");
    }
    
    /**
     * 
     * 描述 下载加水印图片文件
     * @author Danto Huang
     * @created 2017年12月5日 上午9:00:24
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="downLoadMarkImage")
    public void downLoadMarkImage(HttpServletRequest request,HttpServletResponse response){
        FileAttachService fileAttachService = (FileAttachService) AppUtil.getBean("fileAttachService");
        String fileId =  request.getParameter("fileId");
        Map<String,Object> fileAttach = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");
        String fileName = (String) fileAttach.get("FILE_NAME");
        String fileRullPath = attachFileFolderPath+fileAttach.get("FILE_PATH");
        String iconPath = attachFileFolderPath + "webpage/bsdt/applyform/banktemplate/pdfmark.png";
        String targetPath = attachFileFolderPath + "attachFiles/bankDownload/";
        File targetPathFolder = new File(targetPath);
        if (!targetPathFolder.exists()) {
            targetPathFolder.mkdirs();
        }
        String filetargetPath = targetPath + fileId + "." + fileAttach.get("FILE_TYPE");

        String isImg = fileAttach.get("IS_IMG").toString();
        if(isImg.equals("1")){
            ImageRemarkUtil.setImageMarkOptions(1f, 1, 1, null, null);
            ImageRemarkUtil.markImageByIcon(iconPath, fileRullPath, filetargetPath);
        }else{
            PdfRemarkUtil.markPdfByIcon(iconPath, fileRullPath, filetargetPath);
        }
        
        OutputStream toClient = null;
        InputStream fis  = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filetargetPath);

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filetargetPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            if(BrowserUtils.checkBrowse(request).contains("IE")){
                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + java.net.URLEncoder.encode(fileName,"utf-8")+"\"");
            }else{
                response.setHeader("Content-Disposition", "attachment;filename=\""
                        + new String(fileName.getBytes("gb2312"), "ISO8859-1")+"\"");
            }
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
                    log.error(e.getMessage(),e);
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月6日 上午9:13:00
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params="downloadApplyRar")
    public void downloadApplyRar(HttpServletRequest request,HttpServletResponse response){
        String exeId = request.getParameter("entityId");
        bankService.genApplyRar(exeId,response);
    }
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月8日 上午9:28:33
     * @param request
     */
    @RequestMapping(params="writeInfo")
    public ModelAndView writeInfo(HttpServletRequest request) {
        String exeId = request.getParameter("entityId");
        Map<String, Object> exe = bankService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                new Object[] { exeId });
        String tableName = exe.get("BUS_TABLENAME").toString();
        String recordId = exe.get("BUS_RECORDID").toString();
        if (tableName.equals("T_COMMERCIAL_DOMESTIC") || tableName.equals("T_COMMERCIAL_FOREIGN")) {
            tableName = "T_COMMERCIAL_COMPANY";
        }
        String pkName = bankService.getPrimaryKeyName(tableName).get(0).toString();
        Map<String, Object> busInfo = bankService.getByJdbc(tableName, new String[] { pkName },
                new Object[] { recordId });
        Map<String, Object> licInfo = new HashMap<String, Object>();
        licInfo.put("BANK_LICENSE", busInfo.get("BANK_LICENSE"));
        licInfo.put("recordId", recordId);
        licInfo.put("tableName", tableName);
        request.setAttribute("licInfo", licInfo);
        return new ModelAndView("commercial/bank/writeInfo");
    }
    
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2017年12月8日 上午10:08:24
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="saveOrUpdateBankLic")
    @ResponseBody
    public AjaxJson saveOrUpdateBankLic(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        String recordId = request.getParameter("recordId");
        String tableName = request.getParameter("tableName");
        String licNo = request.getParameter("BANK_LICENSE");
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("BANK_LICENSE", licNo);
        bankService.saveOrUpdate(variables, tableName, recordId);
        j.setMsg("保存成功");
        return j;
    }
}
