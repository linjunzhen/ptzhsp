/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.zzhy.service.CommercialSealService;
import net.evecom.platform.zzhy.service.MsgSendService;
import net.evecom.platform.zzhy.util.MsgSendUtils;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 描述  商事印章信息管理Controller
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:15:15
 */
@Controller
@RequestMapping("/commercialSealController")
public class CommercialSealController extends BaseController {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(CommercialSealController.class);
    /**
     * 引入Service
     */
    @Resource
    private CommercialSealService commercialSealService;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;
    /**
     * 所引入的dao
     */
    @Resource
    private MsgSendService msgSendService;
    /**
     * 所引入的dao
     */
    @Resource
    private DictionaryService dictionaryService;

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
        commercialSealService.remove("T_COMMERCIAL_SEAL","SEAL_ID",selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 商事印章信息管理记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }
    
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request) {
        return new ModelAndView("zzhy/commercialSeal/list");
    }
    
    @RequestMapping(params = "gysView")
    public ModelAndView gysView(HttpServletRequest request) {
        return new ModelAndView("zzhy/commercialSeal/gysList");
    }
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String isEdit= request.getParameter("isEdit");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  commercialSeal = commercialSealService.getByJdbc("T_COMMERCIAL_SEAL",
                    new String[]{"SEAL_ID"},new Object[]{entityId});
            request.setAttribute("commercialSeal", commercialSeal);
        }
        request.setAttribute("isEdit", isEdit);        
        return new ModelAndView("zzhy/commercialSeal/info");
    }
    
    /**
     * 修改或者修改操作
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("SEAL_ID");
        String STATUS = request.getParameter("STATUS");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        if (StringUtils.isEmpty(entityId)) {
            variables.put("CREATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(STATUS) && STATUS.equals("1")) {
            variables.put("PUSH_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            String SEAL_PACKAGE = StringUtil.getString(variables.get("SEAL_PACKAGE"));
            String mobileStr = StringUtil.getString(dictionaryService.get("SSYZGYS", SEAL_PACKAGE).get("DIC_DESC"));// 短信提醒者
            String COMPANY_NAME = StringUtil.getString(variables.get("COMPANY_NAME"));
            String msgTemplate= StringUtil.getString(dictionaryService.get("msgContent",
                    "yzkzdxnr").get("DIC_DESC"));//获取短信模版
            String content = String.format(msgTemplate, COMPANY_NAME);//替换模版内容
            if (StringUtils.isNotEmpty(mobileStr)) {
                MsgSendUtils.sendMsg(mobileStr, content); //发送短信
            }

        } else if (StringUtils.isNotEmpty(STATUS) && STATUS.equals("2")) {
            variables.put("KZZT", 1);
        } else if (StringUtils.isNotEmpty(STATUS) && STATUS.equals("3")) {
            String END_TIME = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
            variables.put("END_TIME", END_TIME);
            String CREATE_TIME = StringUtil.getString(variables.get("CREATE_TIME"));
            if(StringUtils.isNotEmpty(CREATE_TIME)){
                long leftWorkMinute = new DateTimeUtil().getWorkMinute(CREATE_TIME, END_TIME);
                if (leftWorkMinute > 420) {
                    variables.put("IS_OVERTIME", 1);
                    String REMARK = StringUtil.getString(variables.get("REMARK"));
                    if(StringUtils.isNotEmpty(REMARK)){
                        variables.put("REMARK", REMARK+"，上传凭证已超时");
                    } else{
                        variables.put("REMARK", "上传凭证已超时");
                    }
                }
            }
        } 
        
        String recordId = commercialSealService.saveOrUpdate(variables, "T_COMMERCIAL_SEAL", entityId);
        if (StringUtils.isNotEmpty(entityId)) {
            sysLogService.saveLog("修改了ID为[" + entityId + "]的 商事印章信息管理记录", SysLogService.OPERATE_TYPE_EDIT);
        } else {
            sysLogService.saveLog("新增了ID为[" + recordId + "]的 商事印章信息管理记录", SysLogService.OPERATE_TYPE_ADD);
        }
        j.setMsg("保存成功");
        return j;
    }
    
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.SEAL_ID", "DESC");
        List<Map<String, Object>> list = commercialSealService.findBySqlFilter(filter, null);
        /*for (Map<String, Object> map : list) {
            String pushTime = StringUtil.getString(map.get("PUSH_TIME"));
            String PAY_TIME = StringUtil.getString(map.get("PAY_TIME"));
            boolean isOverTime = false;
            if (StringUtils.isNotEmpty(pushTime)) {
                String payTime = StringUtils.isNotEmpty(PAY_TIME) ? PAY_TIME
                        : DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                long leftWorkMinute = new DateTimeUtil().getWorkMinute(pushTime, payTime);
                if (leftWorkMinute > 60) {
                    map.put("IS_OVERTIME", 1);
                    isOverTime = true;
                }
            }
            String END_TIME = StringUtil.getString(map.get("END_TIME"));
            if(StringUtils.isNotEmpty(PAY_TIME) && !isOverTime){
                String endTime = StringUtils.isNotEmpty(END_TIME) ? END_TIME
                        : DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                long leftWorkMinute = new DateTimeUtil().getWorkMinute(PAY_TIME, endTime);
                if (leftWorkMinute > 480) {
                    map.put("IS_OVERTIME", 1);
                }
            }
        }*/
        encryptionService.listDecrypt(list, "T_COMMERCIAL_SEAL");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * easyui AJAX请求列表数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "gysDatagrid")
    public void gysDatagrid(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CREATE_TIME", "DESC");
        filter.addSorted("T.SEAL_ID", "DESC");
        filter.addFilter("Q_T.STATUS_IN", "1,2,3");
        SysUser user = AppUtil.getLoginUser();
        if(null != user){
            // 获取菜单KEY
            String resKey = user.getResKeys();
            if (!"__ALL".equals(resKey)){ //不等于超级管理员时
                if(user.getDepCode().equals("ZZSDAYZZZFWYXGS")){ //漳州市盾安印章制作服务有限公司
                    filter.addFilter("Q_T.SEAL_PACKAGE_=", "1");
                } else  if(user.getDepCode().equals("PTZHSYQXCYZZZYXGS")){//平潭综合实验区鑫楚印章制作有限公司
                    filter.addFilter("Q_T.SEAL_PACKAGE_=", "2");
                }
            }
        }
        List<Map<String, Object>> list = commercialSealService.findBySqlFilter(filter, null);
       /* for (Map<String, Object> map : list) {
            String pushTime = StringUtil.getString(map.get("PUSH_TIME"));
            String PAY_TIME = StringUtil.getString(map.get("PAY_TIME"));
            boolean isOverTime = false;
            if (StringUtils.isNotEmpty(pushTime)) {
                String payTime = StringUtils.isNotEmpty(PAY_TIME) ? PAY_TIME
                        : DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                long leftWorkMinute = new DateTimeUtil().getWorkMinute(pushTime, payTime);
                if (leftWorkMinute > 60) {
                    map.put("IS_OVERTIME", 1);
                    isOverTime = true;
                }
            }
            String END_TIME = StringUtil.getString(map.get("END_TIME"));
            if(StringUtils.isNotEmpty(PAY_TIME) && !isOverTime){
                String endTime = StringUtils.isNotEmpty(END_TIME) ? END_TIME
                        : DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                long leftWorkMinute = new DateTimeUtil().getWorkMinute(PAY_TIME, endTime);
                if (leftWorkMinute > 480) {
                    map.put("IS_OVERTIME", 1);
                }
            }
        }*/
        encryptionService.listDecrypt(list, "T_COMMERCIAL_SEAL");
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }
    /**
     * 方法multiPublish
     * 
     * @param request
     *            传入参数
     * @return 返回值AjaxJson
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "updateStatus")
    @ResponseBody
    public AjaxJson updateStatus(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        int status = Integer.parseInt(request.getParameter("status"));
        if (StringUtils.isNotEmpty(selectColNames)) {
            String[] ids = selectColNames.split(",");
            for (String id : ids) {
                Map<String, Object> info = new HashMap<String, Object>();
                Map<String,Object>  commercialSeal = commercialSealService.getByJdbc("T_COMMERCIAL_SEAL",
                        new String[]{"SEAL_ID"},new Object[]{id});
                String pushTime = StringUtil.getString(commercialSeal.get("PUSH_TIME"));
                if(StringUtils.isNotEmpty(pushTime)){
                    info.put("STATUS", status);
                    if(status==2){
                        info.put("KZZT", 1);
                        String payTime = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                        long leftWorkMinute= new DateTimeUtil().getWorkMinute(pushTime, payTime);
                        if(leftWorkMinute>60){
                            info.put("IS_OVERTIME", 1);
                            info.put("REMARK", "交付已超时");
                        }
                    }
                }
                info.put("PAY_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                commercialSealService.saveOrUpdate(info, "T_COMMERCIAL_SEAL", id);
            }
        }
        sysLogService.saveLog("修改ID为[" + selectColNames + "]的 印章记录中的STATUS为[" + status + "]",
                SysLogService.OPERATE_TYPE_EDIT);
        j.setMsg("操作成功");
        return j;
    }
    
    /**
     * 跳转到信息页面
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "gysInfo")
    public ModelAndView gysInfo(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        String isEdit= request.getParameter("isEdit");
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            Map<String,Object>  commercialSeal = commercialSealService.getByJdbc("T_COMMERCIAL_SEAL",
                    new String[]{"SEAL_ID"},new Object[]{entityId});
            request.setAttribute("commercialSeal", commercialSeal);
        }
        request.setAttribute("isEdit", isEdit);        
        return new ModelAndView("zzhy/commercialSeal/gysInfo");
    }
    
    /**
     * 描述:下载医疗保险材料
     *
     * @author Madison You
     * @created 2020/10/19 17:32:00
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downloadSealMater")
    public void downloadMedicialMater(HttpServletRequest request,HttpServletResponse response) {
        String entityId = request.getParameter("entityId");
        Map<String,Object>  commercialSeal = commercialSealService.getByJdbc("T_COMMERCIAL_SEAL",
                new String[]{"SEAL_ID"},new Object[]{entityId});
        Properties properties = FileUtil.readProperties("project.properties");
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        String uploadFullPath = "attachFiles/pdf/" + "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());;
        String unid = UUIDGenerator.getUUID();
        String uploadFullFile = uploadFullPath + "/" + unid + ".docx";
        String newWordFilePath = pdfFile + uploadFullFile;
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String fileRullPath = AppUtil.getRealPath("attachFiles//applymater//kzyz//kzyzdjb.docx");
        WordReplaceUtil.replaceWord(commercialSeal, fileRullPath, newWordFilePath);
        downLoadFile(response, pdfFile, "新开办企业免费刻制印章登记表.docx", uploadFullFile);
    }
    

    /**
     * 
     * 描述 下载文件
     * 
     * @author Rider Chen
     * @created 2016年11月30日 下午2:11:47
     * @param response
     * @param attachFileFolderPath
     * @param fileName
     * @param uploadFullPath
     */
    private void downLoadFile(HttpServletResponse response, String attachFileFolderPath, String fileName,
            String uploadFullPath) {
        OutputStream toClient = null;
        InputStream fis  = null;
        try {
            String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "."
                    + uploadFullPath.substring(uploadFullPath.lastIndexOf(".") + 1);
            // path是指欲下载的文件的路径。
            File file = new File(attachFileFolderPath + uploadFullPath);

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(attachFileFolderPath + uploadFullPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=\""
                    + new String(newFileName.getBytes("gbk"), "iso8859-1") + "\"");
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
}

