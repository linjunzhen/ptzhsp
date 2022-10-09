/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.controller;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.ExcelUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.JsonUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.UUIDGenerator;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.service.TwQualificationService;

/**
 * 描述 台湾地区职业资格采集证书相关业务操作Controller
 * @author Allin Lin
 * @created 2019年5月24日 上午10:18:41
 */
@Controller
@RequestMapping("twQualificationController")
public class TwQualificationController extends BaseController{
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TwQualificationController.class);
    
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    
    /**
     * twQualificationService
     */
    @Resource
    private TwQualificationService twQualificationService;
    
    /**
     * 描述    页面跳转
     * @author Allin Lin
     * @created 2019年5月24日 上午10:29:39
     * @param request
     * @return
     */
    @RequestMapping(params = "view")
    public ModelAndView view(HttpServletRequest request){
        return new ModelAndView("wsbs/twqualification/view");
    }
    
    /**
     * 描述    easyui AJAX请求数据
     * @author Allin Lin
     * @created 2019年5月24日 上午10:56:03
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request,
            HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        filter.addSorted("T.CXZS_TIME","asc");
        List<Map<String, Object>> list = twQualificationService.findBySqlFilter(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list,
                null, JsonUtil.EXCLUDE, response);
    }
    
    /**
     * 描述    前台根据姓名、证书编号查询证件信息
     * @author Allin Lin
     * @created 2019年6月3日 上午10:51:35
     * @param request
     * @param response
     */
    @RequestMapping("/twzyzgcxzscx")
    public void twzyzgcxzscx(HttpServletRequest request,
            HttpServletResponse response) {   
        String username=request.getParameter("USERNAME");
        String zsNum=request.getParameter("ZS_NUMBER");
        List<Map<String, Object>> list = twQualificationService.getTwQualification(username,zsNum);
        this.setJsonString(JSON.toJSONString(list), response);
    }   
    
    /**
     * 描述    跳转是二维码打印界面
     * @author Allin Lin
     * @created 2019年6月3日 上午10:52:13
     * @param request
     * @return
     */
    @RequestMapping("/QRcodeSearch")
    public ModelAndView qrcodeSearch(HttpServletRequest request){
        String id=request.getParameter("id");
        Map<String, Object> twQualification = twQualificationService.getByJdbc("T_TW_QUALIFICATION",
                new String[] { "QUALIFICATION_ID" }, new Object[] { id });
        request.setAttribute("twQualification",twQualification);
        return new ModelAndView("wsbs/twqualification/zsInfo");  
    }
    
    /**
     * 描述    新增台湾地区职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月24日 下午2:53:40
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "info")
    public ModelAndView info(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object> twQualification = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            twQualification=twQualificationService.getByJdbc("T_TW_QUALIFICATION", 
                    new String[]{"QUALIFICATION_ID"}, new Object[]{entityId});
        }else{
            twQualification=new HashMap<String, Object>();
        }
        request.setAttribute("twQualification", twQualification);
        return new ModelAndView("wsbs/twqualification/info");
    }
    
    /**
     * 描述    查看详细信息
     * @created 2019年6月19日 下午4:55:30
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "detail")
    public ModelAndView detail(HttpServletRequest request) {
        String entityId = request.getParameter("entityId");
        Map<String,Object> twQualification = null;
        if(StringUtils.isNotEmpty(entityId)&&!entityId.equals("undefined")){
            twQualification=twQualificationService.getByJdbc("T_TW_QUALIFICATION", 
                    new String[]{"QUALIFICATION_ID"}, new Object[]{entityId});
        }else{
            twQualification=new HashMap<String, Object>();
        }
        request.setAttribute("twQualification", twQualification);
        return new ModelAndView("wsbs/twqualification/detail");
    }
    
    /**
     * 描述   查看二维码 
     * @author Allin Lin
     * @created 2019年6月19日 下午5:16:53
     * @param request
     * @return
     */
    @RequestMapping(params = "showQRcode")
    public ModelAndView showQRcode(HttpServletRequest request) {
        String id =request.getParameter("entityId");
        request.setAttribute("id", id);
        return new ModelAndView("wsbs/twqualification/showQRcode");
    }
    
    
    /**
     * 描述    增加或修改台湾职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:14:01
     * @param request
     * @return
     */
    @RequestMapping(params = "saveOrUpdate")
    @ResponseBody
    public AjaxJson saveOrUpdate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String entityId = request.getParameter("QUALIFICATION_ID");
        Map<String, Object> variables = BeanUtil.getMapFromRequest(request);
        String recordId=twQualificationService.saveOrUpdateTwQualify(variables,entityId);
        if(StringUtils.isNotEmpty(recordId)){
            sysLogService.saveLog("修改了ID为["+entityId+"]的台湾职业资格采信证书信息记录",SysLogService.OPERATE_TYPE_EDIT);
            j.setMsg("保存成功");
        }else{
            j.setMsg("新增台湾地区职业资格采信证书成功");
            sysLogService.saveLog("新增了ID为["+recordId+"]的台湾职业资格采信证书信息记录",SysLogService.OPERATE_TYPE_ADD);
        }
        return j;
    }
    
    /**
     * 描述    删除台湾职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:44:56
     * @param request
     * @return
     */
    @RequestMapping(params = "multiDel")
    @ResponseBody
    public AjaxJson multiDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String selectColNames = request.getParameter("selectColNames");
        this.twQualificationService.removeCascade(selectColNames.split(","));
        sysLogService.saveLog("删除了ID为["+selectColNames+"]的 台湾地区职业资格采信证书信息记录",SysLogService.OPERATE_TYPE_DEL);
        j.setMsg("删除成功");
        return j;
    }  
    
    /**
     * 描述   跳转至excel导入界面 
     * @author Allin Lin
     * @created 2019年5月28日 上午9:40:00
     * @return
     */
    @RequestMapping(params = "excelImportView")
    public ModelAndView excelImportView(HttpServletRequest request){
        return new ModelAndView("wsbs/twqualification/excelImport");     
    }
 
    /**
     * 描述    excel批量导入数据
     * @author Allin Lin
     * @created 2019年5月28日 上午11:24:41
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(params = "uploadexcel")
    @ResponseBody
    public AjaxJson uploadexcel(HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        AjaxJson j = new AjaxJson();
        POIFSFileSystem fs;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        //统计成功导入行数
        int count=0;
        //统计导入失败的行号
        String failRows="";
        //起始读取行数字
        int startRowNum=Integer.valueOf(request.getParameter("startRowNum"));   
        if (file.isEmpty()) {
            j.setMsg("文件不存在！");
            j.setSuccess(false);
        }else {
            //这里一定要转换一下file类型，不然无法读取流
            CommonsMultipartFile cFile = (CommonsMultipartFile) file;
            DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
            InputStream inputStream = null;
            //获取一个绝对地址的流
            try {
                inputStream=fileItem.getInputStream();      
                fs = new POIFSFileSystem(inputStream);
                // 取得相应工作簿
                workbook = new HSSFWorkbook(fs);
                sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows();
                for (int k = startRowNum; k < rowCount; k++) { // 获取到第K行 HSSFRow
                   Map<String, Object> twQualification=new HashMap<String, Object>();
                    HSSFRow row = sheet.getRow(k);
                    //获取台湾地区职业资格采信证书基本信息
                    Object UERNAME = ExcelUtil.getCellValue(row, 1);
                    Object SEX = ExcelUtil.getCellValue(row, 2);        
                    Object BIRTHDAY = ExcelUtil.getCellValue(row, 3);
                    Object TB_ZJHM = ExcelUtil.getCellValue(row, 4);
                    Object ZYZG_LEVEL = ExcelUtil.getCellValue(row, 5);
                    Object ZSHM = ExcelUtil.getCellValue(row, 6);
                    Object ZY_LEVEL = ExcelUtil.getCellValue(row, 7); 
                    Object YXFW = ExcelUtil.getCellValue(row, 8); 
                    Object ZS_NUMBER = ExcelUtil.getCellValue(row, 9);
                    Object ZS_YXQ = ExcelUtil.getCellValue(row, 10);                
                    Object BZ = ExcelUtil.getCellValue(row, 11);                 
                    if(UERNAME==null || SEX==null || BIRTHDAY==null || TB_ZJHM==null 
                            || ZYZG_LEVEL==null || ZSHM==null || ZY_LEVEL==null
                            || YXFW==null || ZS_NUMBER==null || ZS_YXQ==null){
                        failRows+=String.valueOf(k+1)+"、";
                        continue;
                    }else if(UERNAME=="" || SEX=="" || BIRTHDAY=="" || TB_ZJHM=="" 
                            || ZYZG_LEVEL=="" || ZSHM=="" || ZY_LEVEL==""
                            || YXFW=="" || ZS_NUMBER=="" || ZS_YXQ==""){
                        failRows+=String.valueOf(k+1)+"、";
                        continue;
                    }else{
                        if(StringUtils.isEmpty(UERNAME.toString())){
                            continue;
                        }
                        if(twQualificationService.isExist(ZS_NUMBER.toString())){
                            failRows+=String.valueOf(k+1)+"、";
                            continue;
                        }
                        twQualification.put("USERNAME", UERNAME);
                        if(SEX!=null && "女".equals(SEX.toString())){
                            twQualification.put("SEX","2");
                        }else if(SEX!=null && "男".equals(SEX.toString())){
                            twQualification.put("SEX","1");
                        }
                        twQualification.put("BIRTHDAY", DateTimeUtil
                                .dateToStr(DateTimeUtil.format(BIRTHDAY.toString(), "yyyy.mm.dd"), "yyyy-mm-dd"));
                        twQualification.put("TB_ZJHM", TB_ZJHM);
                        twQualification.put("ZYZG_LEVEL", ZYZG_LEVEL);
                        twQualification.put("ZY_LEVEL", ZY_LEVEL);
                        twQualification.put("ZS_NUMBER", ZS_NUMBER);
                        twQualification.put("ZSHM", ZSHM);
                        twQualification.put("YXFW", YXFW);
                        String[] zsyxq=ZS_YXQ.toString().split("-");
                        twQualification.put("CXZS_TIME", DateTimeUtil
                                .dateToStr(DateTimeUtil.format(zsyxq[0].toString(), "yyyymmdd"), "yyyy-mm-dd"));
                        twQualification.put("ZS_VALIDITY", DateTimeUtil
                                .dateToStr(DateTimeUtil.format(zsyxq[1].toString(), "yyyymmdd"), "yyyy-mm-dd"));
                        twQualification.put("BZ", BZ);
                        twQualificationService.saveOrUpdateTwQualify(twQualification, null);
                        count++;
                    }     
                }
            } catch (IOException e) {    
                log.error(e.getMessage(), e);
            }  
            String trueRows=String.valueOf(count);
            if("".equals(failRows)){
                j.setMsg("成功导入"+trueRows+"条记录!");
            }else{
                failRows=failRows.substring(0,failRows.length()-1);
                j.setMsg("成功导入"+trueRows+"条记录!导入失败的行号为("+failRows+")，请确认失败行的数据是否完整或已存在!");
            }         
            j.setSuccess(true);
        }
        return j;
    }
    
    /**
     * 描述    二维码
     * @author Allin Lin
     * @created 2019年5月31日 上午11:47:56
     * @param request
     * @return
     */
    @RequestMapping(params = "printQRcode")
    @ResponseBody
    public  AjaxJson printQRcode(HttpServletRequest request){
        URL url;
        String imagePath="";
        //String qrcodeurl="http://www.yingcaibang.cn/ptwx/qrcode/img?sfnum="+sfnum+"&zsnum="+zsnum;
        String qrcodeurl="http://www.yingcaibang.cn/ptwx/qrcode/getQRCode?"
                + "url=/ptwx/qrcode/zscxX1sfnum=02916195X2zsnum=100-029169";
        AjaxJson j=new AjaxJson();
        try {
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
            String attachFileUrl = properties.getProperty("AttachFileUrl") + "attachFiles/";
            String uploadPath = "ZSQrcodeAttach/";
            // 定义二维码图片的保存的相对目录路径
            SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = "DATE_" + dirSdf.format(new Date());
            String uploadFullPath = attachFileFolderPath + uploadPath + currentDate + "/";
            // 建立全路径目录和临时目录
            File uploadFullPathFolder = new File(uploadFullPath);
            if (!uploadFullPathFolder.exists()) {
                uploadFullPathFolder.mkdirs();
            }
            String uuId = UUIDGenerator.getUUID();
            String fileName = uuId + ".png";
            String filePath = uploadFullPath + fileName;
            filePath = filePath.replace("\\", "/");
            imagePath=attachFileUrl + uploadPath + currentDate + "/" + fileName;

            url = new URL(qrcodeurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();
            FileOutputStream fout = new FileOutputStream(filePath);
            BufferedImage prevImage = ImageIO.read(inStream);
            int width = 100;
            int height = 100;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics graphics = image.createGraphics();
            graphics.drawImage(prevImage, 0, 0, width, height, null);
            ImageIO.write(image, "png", fout);
            fout.flush();
            fout.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        j.setSuccess(true);
        j.setMsg(imagePath);
        return j;
    }
}
