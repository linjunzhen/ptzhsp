/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.taxilicense.controller;

import net.evecom.core.poi.ExcelReplaceDataVO;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.util.*;
import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.taxilicense.service.TaxiLisenceService;
import net.evecom.platform.zzhy.controller.DomesticControllerController;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 描述
 * @author Bennett Zhang
 * @created 2021年12月14日 下午10:14:49
 */
@Controller
@RequestMapping("/TaxiLicenseController")
public class TaxiLicenseController extends BaseController {
    /**
     * 引入taxiLisenceService
     */
    @Resource
    private TaxiLisenceService taxiLisenceService;

    /**
     * 引入exeDataService
     */
    @Resource
    private ExeDataService exeDataService;

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(TaxiLicenseController.class);


    /**
     * TaxiLicenseController.do?goList
     * 描述
     * @author Bennett Zhang
     * @created 2021-12-20 上午11:19:25
     * @param request
     * @return
     */
    @RequestMapping(params = "goList")
    public ModelAndView goList(HttpServletRequest request) {

        return new ModelAndView("zhsp/taxilicense/taxiLicense");
    }


    /**
     * TaxiLicenseController.do?passedTaxiLisenceList
     * 描述 列表dataGrid
     * @author Bennett Zhang
     * @created 2015-11-23 下午2:44:33
     * @param request
     * @param response
     */
    @RequestMapping(params = "passedTaxiLisenceList")
    public void passedTaxiLisenceList(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);
        SysUser sysUser = AppUtil.getLoginUser();
//        filter.addFilter("Q_T.ASSIGNER_CODE_EQ",sysUser.getUsername());
        filter.addSorted("E.CREATE_TIME", "desc");
        List<Map<String, Object>> list = taxiLisenceService.passedTaxiLisenceList(filter);
        this.setListToJsonString(filter.getPagingBean().getTotalItems(), list, null, JsonUtil.EXCLUDE, response);
    }

    /**
     * 描述:下载 附件3：出租汽车驾驶员安全驾驶信用证明
     * TaxiLicenseController/downloadTaxiSafeDrivingCert.do?exeId=?
     * @author Bennett Zhang
     * @created 2020/10/19 17:32:00
     * @param
     * @return
     */
    @RequestMapping("/downloadTaxiSafeDrivingCert")
    public void downloadTaxiSafeDrivingCert(HttpServletRequest request,HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        HashMap<String, Object> busRecord = new HashMap<>();
        Map<String, Object> busMap =exeDataService.getBuscordMap(exeId);
        busRecord.put("applyuser", StringUtil.getValue(busMap, "ZGZ_APPLY_USERNAME"));
        //从业资格证申请人性别(1-男，2-女)
        String sex = StringUtil.getValue(busMap, "ZGZ_APPLY_SEX");
        if("1".equals(sex)){
            sex = "男";
        }else{
            sex = "女";
        }
        busRecord.put("sex", sex);
        busRecord.put("idcardnumber", StringUtil.getValue(busMap, "ZGZ_APPLY_CARDNUM"));
        busRecord.put("allowcartype", StringUtil.getValue(busMap, "ZGZ_APPLY_ALLOWCARTYPE"));
        /*选项记录*/
        selectYesOrNo("IS_THREE_YEAR","1","isTreeYear", busMap, busRecord);
        selectYesOrNo("IS_TRAFFIC_CRIME", "1","isTrafficCrime", busMap, busRecord);
        selectYesOrNo("IS_DANGEROUS_DRIVING", "1", "IS_DANGEROUS_DRIVING",busMap, busRecord);
        selectYesOrNo("IS_DUI", "1","IS_DUI",  busMap, busRecord);
        selectYesOrNo("IS_THREEWEEK_TWELVESCORE", "1","IS_THREEWEEK_TWELVESCORE",  busMap, busRecord);
        selectYesOrNo("IS_DRUGS", "1","IS_DRUGS", busMap, busRecord);
        selectYesOrNo("IS_VIOLENT_CRIME", "1","IS_VIOLENT_CRIME", busMap, busRecord);


        Properties properties = FileUtil.readProperties("project.properties");
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        String uploadFullPath = "attachFiles/pdf/" + "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());;
        String unid = UUIDGenerator.getUUID();
        String uploadFullFile = uploadFullPath + "/" + unid + ".docx";
        //新文件地址
        String newWordFilePath = pdfFile + uploadFullFile;
        //文件夹路径 本地没有路径就创建
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String fileRullPath = AppUtil.getRealPath("attachFiles//spmb//附件3：出租汽车驾驶员安全驾驶信用证明.docx");
        WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        downLoadFile(response, pdfFile, "附件3：出租汽车驾驶员安全驾驶信用证明.docx", uploadFullFile);
    }

    /**
     * 描述:记录是否选择框
     *
     * @author Madison You
     * @created 2020/8/17 14:25:00
     * @param
     * @return
     */
    public void selectYesOrNo(String column, String columnValue , String docColumn,Map<String,Object> busInfo , Map<String,Object> returnMap) {
        String value = StringUtil.getValue(busInfo, column);
        if (StringUtils.isNotEmpty(value)) {
            if (value.equals(columnValue)) {
                returnMap.put(docColumn + "_1", "■");
                returnMap.put(docColumn + "_2", "□");
            } else {
                returnMap.put(docColumn + "_1", "□");
                returnMap.put(docColumn + "_2", "■");
            }
        } else {
            returnMap.put(docColumn + "_1", "□");
            returnMap.put(docColumn + "_2", "□");
        }
    }

    /**
     * 描述:下载 出租汽车驾驶员从业资格证申请表（模板）
     * TaxiLicenseController/downloadTaxiSafeDrivingCert.do?exeId=?
     * @author Bennett Zhang
     * @created 2020/10/19 17:32:00
     * @param
     * @return
     */
    @RequestMapping("/downloadApplyModel")
    public void downloadApplyModel(HttpServletRequest request,HttpServletResponse response) {
        String exeId = request.getParameter("exeId");
        HashMap<String, Object> busRecord = new HashMap<>();
        Map<String, Object> busMap =exeDataService.getBuscordMap(exeId);
        busRecord.put("applyuser", StringUtil.getValue(busMap, "ZGZ_APPLY_USERNAME"));
        //从业资格证申请人性别(1-男，2-女)
        String sex = StringUtil.getValue(busMap, "ZGZ_APPLY_SEX");
        if("1".equals(sex)){
            sex = "男";
        }else{
            sex = "女";
        }
        busRecord.put("sex", sex);
        busRecord.put("idcardnumber", StringUtil.getValue(busMap, "ZGZ_APPLY_CARDNUM"));
        busRecord.put("allowcartype", StringUtil.getValue(busMap, "ZGZ_APPLY_ALLOWCARTYPE"));
        busRecord.put("address", StringUtil.getValue(busMap, "ZGZ_APPLY_ADDRESS"));
        busRecord.put("mobile", StringUtil.getValue(busMap, "ZGZ_APPLY_MOBILENUMBER"));
        busRecord.put("trainingUnit", StringUtil.getValue(busMap, "ZGZ_APPLY_TRAININGUNIT"));
        String firstGetDate = StringUtil.getValue(busMap, "ZGZ_APPLY_FIRSTGETJSZTIME");
        if(StringUtils.isNotEmpty(firstGetDate)){
            String date[] = firstGetDate.split("-");//2021-12-15
            String year = date[0];
            String month = date[1];
            String day = date[2];
            busRecord.put("year", year);
            busRecord.put("month", month);
            busRecord.put("day", day);
        }

        busRecord.put("examTime", StringUtil.getValue(busMap, "ZGZ_EXAM_TIME"));
        busRecord.put("score", StringUtil.getValue(busMap, "ZGZ_EXAM_SCORE"));
        busRecord.put("examUser", StringUtil.getValue(busMap, "ZGZ_EXAM_USER"));
        busRecord.put("areaExamTime", StringUtil.getValue(busMap, "ZGZ_AREAEXAM_TIME"));
        busRecord.put("areaScore", StringUtil.getValue(busMap, "ZGZ_AREAEXAM_SCORE"));
        busRecord.put("areaExamUser", StringUtil.getValue(busMap, "ZGZ_AREAEXAM_USER"));
        busRecord.put("disagreeReason", StringUtil.getValue(busMap, "ZGZ_DISAGREE_REASON"));
        busRecord.put("cyzgzNum", StringUtil.getValue(busMap, "ZGZ_CYZGZ_NUM"));
        busRecord.put("licensor", StringUtil.getValue(busMap, "ZGZ_CYZGZ_LICENSOR"));
        busRecord.put("getUser", StringUtil.getValue(busMap, "ZGZ_CYZGZ_GETUSER"));

        Properties properties = FileUtil.readProperties("project.properties");
        String pdfFile = properties.getProperty("PdfFilePath").replace("\\", "/");
        String uploadFullPath = "attachFiles/pdf/" + "DATE_" + new SimpleDateFormat("yyyyMMdd").format(new Date());;
        String unid = UUIDGenerator.getUUID();
        String uploadFullFile = uploadFullPath + "/" + unid + ".docx";
        //新文件地址
        String newWordFilePath = pdfFile + uploadFullFile;
        //文件夹路径 本地没有路径就创建
        File pdfFullPathFolder = new File(pdfFile + uploadFullPath);
        if (!pdfFullPathFolder.exists()) {
            pdfFullPathFolder.mkdirs();
        }
        String fileRullPath = AppUtil.getRealPath("attachFiles//spmb//出租汽车驾驶员从业资格证申请表（模板）.docx");
        WordReplaceUtil.replaceWord(busRecord, fileRullPath, newWordFilePath);
        downLoadFile(response, pdfFile, "出租汽车驾驶员从业资格证申请表（模板）.docx", uploadFullFile);
    }



    /**
     *
     * 描述 下载文件
     *
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



    /**
     * 描述:导出出租汽车驾驶员认证信息表数据表
     * TaxiLicenseController.do?exportExcel
     * @param
     * @return
     */
    @RequestMapping(params = "exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        SqlFilter filter = new SqlFilter(request);

        filter.addSorted("E.CREATE_TIME", "desc");
        List<Map<String, Object>> list = taxiLisenceService.exportDatas(filter);
        for (Map<String, Object> map : list) {
            map.remove("CURRENTROW");
        }
        String tplPath = AppUtil.getAppAbsolutePath()
                + "/attachFiles/spmb/出租汽车驾驶员认证.xls";
        int startRow = 2;
        int startCol = 1;
        Set<String> excludeFields = new HashSet<String>();
        List<ExcelReplaceDataVO> datas = new ArrayList<ExcelReplaceDataVO>();
        ExcelUtil.exportXls(tplPath, list, "出租汽车驾驶员认证.xls",
                excludeFields, response, startRow, startCol, datas,"",false,new int[]{1,1});
    }

    /**
     * 描述   跳转至excel导入界面
     * @author Allin Lin
     * @created 2019年5月28日 上午9:40:00
     * @return
     */
    @RequestMapping(params = "excelImportView")
    public ModelAndView excelImportView(HttpServletRequest request){
        return new ModelAndView("zhsp/taxilicense/excelImport");
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
                    Map<String, Object> scoreData=new HashMap<String, Object>();
                    HSSFRow row = sheet.getRow(k);
                    //获取台湾地区职业资格采信证书基本信息
                    Object EXE_ID = ExcelUtil.getCellValue(row, 1);
                    Object ZGZ_APPLY_USERNAME = ExcelUtil.getCellValue(row, 2);
                    Object ZGZ_APPLY_CARDNUM = ExcelUtil.getCellValue(row, 3);
                    Object ZGZ_APPLY_MOBILENUMBER = ExcelUtil.getCellValue(row, 4);
                    Object ZGZ_EXAM_TIME = ExcelUtil.getCellValue(row, 5);
                    Object ZGZ_EXAM_SCORE = ExcelUtil.getCellValue(row, 6);
                    Object ZGZ_EXAM_USER = ExcelUtil.getCellValue(row, 7);
                    Object ZGZ_AREAEXAM_TIME = ExcelUtil.getCellValue(row, 8);
                    Object ZGZ_AREAEXAM_SCORE = ExcelUtil.getCellValue(row, 9);
                    Object ZGZ_AREAEXAM_USER = ExcelUtil.getCellValue(row, 10);
                    Object ZGZ_EXAM_ISPASS = ExcelUtil.getCellValue(row, 11);

                    if(EXE_ID== null ||ZGZ_APPLY_USERNAME==null || ZGZ_APPLY_CARDNUM==null || ZGZ_APPLY_MOBILENUMBER==null || ZGZ_EXAM_TIME==null
                            || ZGZ_EXAM_SCORE==null || ZGZ_EXAM_USER==null || ZGZ_AREAEXAM_TIME==null
                            || ZGZ_AREAEXAM_SCORE==null || ZGZ_AREAEXAM_USER==null || ZGZ_EXAM_ISPASS==null){
                        failRows+=String.valueOf(k+1)+"、";
                        continue;
                    }else if(EXE_ID== "" || ZGZ_APPLY_USERNAME=="" || ZGZ_APPLY_CARDNUM=="" || ZGZ_APPLY_MOBILENUMBER=="" || ZGZ_EXAM_TIME==""
                            || ZGZ_EXAM_SCORE=="" || ZGZ_EXAM_USER=="" || ZGZ_AREAEXAM_TIME==""
                            || ZGZ_AREAEXAM_SCORE=="" || ZGZ_AREAEXAM_USER=="" || ZGZ_EXAM_ISPASS==""){
                        failRows+=String.valueOf(k+1)+"、";
                        continue;
                    }else{
                        if(StringUtils.isEmpty(EXE_ID.toString())){
                            failRows+=String.valueOf(k+1)+"、";
                            continue;
                        }
                        scoreData = taxiLisenceService.getImportData(EXE_ID.toString());
                        if(scoreData == null){
                            failRows+=String.valueOf(k+1)+"、";
                            continue;
                        }
//                        scoreData.put("ZGZ_APPLY_USERNAME", ZGZ_APPLY_USERNAME);
//                        scoreData.put("ZGZ_APPLY_CARDNUM", ZGZ_APPLY_CARDNUM);
//                        scoreData.put("ZGZ_APPLY_MOBILENUMBER", ZGZ_APPLY_MOBILENUMBER);
                        scoreData.put("ZGZ_EXAM_TIME", ZGZ_EXAM_TIME);
                        scoreData.put("ZGZ_EXAM_SCORE", ZGZ_EXAM_SCORE);
                        scoreData.put("ZGZ_EXAM_USER", ZGZ_EXAM_USER);
                        scoreData.put("ZGZ_AREAEXAM_TIME", ZGZ_AREAEXAM_TIME);
                        scoreData.put("ZGZ_AREAEXAM_SCORE", ZGZ_AREAEXAM_SCORE);
                        scoreData.put("ZGZ_AREAEXAM_USER", ZGZ_AREAEXAM_USER);
                        if(ZGZ_EXAM_ISPASS != null){
                            switch (ZGZ_EXAM_ISPASS.toString()){
                                case "通过":
                                    ZGZ_EXAM_ISPASS = "0";
                                    scoreData.put("ZGZ_APPLY_STATUS", "考试通过");
                                    break;
                                case "不通过":
                                    ZGZ_EXAM_ISPASS = "1";
                                    scoreData.put("ZGZ_APPLY_STATUS", "考试不通过");
                                    break;
                                default:
                                    ZGZ_EXAM_ISPASS = "";
                                    break;
                            }
                        }
                        scoreData.put("ZGZ_EXAM_ISPASS", ZGZ_EXAM_ISPASS);

                        taxiLisenceService.saveOrUpdate(scoreData, "T_CZCZGZ_YGXTGETSCORE",scoreData.get("YW_ID").toString());
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
     * 描述:接收运管系统获取考试成绩接口
     * TaxiLicenseController/importTaxiLisenceApplyScore.do
     * @author Bennett Zhang
     * @created 2020/10/19 17:32:00
     * @param
     * @return
     */
    @RequestMapping("/importTaxiLisenceApplyScore")
    public void getScore(HttpServletRequest request,HttpServletResponse response){

    }

}
