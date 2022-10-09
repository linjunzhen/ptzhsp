/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import net.evecom.platform.bdc.service.BdcSpbPrintConfigService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcQzPrintDao;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.bdc.service.BdcQzPrintService;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;

/**
 * 描述 不动产登记证明及不动产产权证书打印模板数据回填
 * 
 * @author Keravon Feng
 * @created 2019年12月25日 上午11:47:22
 */
@Service("bdcQzPrintService")
public class BdcQzPrintServiceImpl extends BaseServiceImpl implements BdcQzPrintService {

    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcQzPrintServiceImpl.class);
    /**
     * dao
     */
    @Resource
    private BdcQzPrintDao dao;

    /**
     * 描述
     * 
     * @author Keravon Feng
     * @created 2019年12月25日 上午11:51:13
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * bdcQueryService
     */
    @Resource
    private BdcQueryService bdcQueryService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/6/11 11:49:00
     * @param
     * @return
     */
    @Resource
    private BdcSpbPrintConfigService bdcSpbConfig;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/6/11 11:56:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述 证件打印的功能获取数据的方法
     * 
     * @author Keravon Feng
     * @created 2019年12月25日 上午11:55:30
     * @param args
     */
    @Override
    public void setQzTemplateData(Map<String, Map<String, Object>> args) {
        Map<String, Object> returnMap = args.get("returnMap");
        // 参数
        Map<String, Object> requestMap = args.get("requestMap");
        /*业务数据*/
        Map<String, Object> busInfo = args.get("busInfo");
        /*基本信息*/
        Map<String, Object> execution = args.get("execution");
        // *******************************使用poi插入图片*************************************
        InputStream fis = null;
        FileOutputStream fos = null;
        String dirPath = AppUtil.getRealPath("attachFiles//certificateTemplate//files/");
        String templatePath = StringUtil.getValue(returnMap, "TemplatePath");
        File f = new File(dirPath + templatePath);
        // 文件格式名 xls/xlsx
        String fileExt = templatePath.substring(templatePath.lastIndexOf(".") + 1);
        try {
            if (f != null) {
                fis = new FileInputStream(f);
                Workbook wb = null;
                // 获取Workbook对象
                wb = ExcelRedrawUtil.getVersionWorkbook(fis);
                if (wb == null) {
                    throw new Exception("Excel格式错误");
                }
                List<Map> result = null;
                AjaxJson ajaxJson = null;
                Map<String, Object> info = new HashMap<String, Object>();
                info.put("QLRZJH", StringUtil.getValue(requestMap, "QLRZJH"));
                info.put("BDCQZH", StringUtil.getValue(requestMap, "BDCQZH"));
                String eveId = StringUtil.getValue(requestMap, "EFLOW_EXEID");
                if (eveId != null && eveId.length() >= 12) {
                    // 取受理编号后12为作为业务号
                    info.put("YWH", eveId.substring(eveId.length() - 12));
                } else {
                    info.put("YWH", eveId);
                }
                String templateAlias = StringUtil.getValue(requestMap, "templateAlias");
                String typeId = StringUtil.getValue(requestMap, "typeId");
                if ("BDCQZ".equals(templateAlias)) {
                    if (typeId.equals("1")) {
                        setZsylData(result,busInfo,execution,info);
                    } else {
                        ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "cxbdcqzsxxxxUrl");
                        if (ajaxJson != null && ajaxJson.isSuccess()) {
                            String retJson = ajaxJson.getJsonString();
                            if (retJson != null) {
                                retJson = retJson.replace("\r\n", "");
                            }
                            Map map = JSON.parseObject(retJson, Map.class);
                            if (map != null && map.get("result_object") != null) {
                                result = JSON.parseArray(String.valueOf(map.get("result_object")), Map.class);
                            }
                        }
                    }
                } else if ("DJZM".equals(templateAlias)) {
                    ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "dycxbdcqzsxxxxUrl");
                    if (ajaxJson != null && ajaxJson.isSuccess()) {
                        String retJson = ajaxJson.getJsonString();
                        if (retJson != null) {
                            retJson = retJson.replace("\r\n", "");
                        }
                        result = JSON.parseArray(retJson, Map.class);
                    }
                }
                if (result != null && result.size() > 0) {
                    // 构造回填数据 key:Excel中自定义的单元格名称 value:要设的值
                    Map<String, Object> redrawData = result.get(0);
                    if (redrawData.get("EWM") != null) {
                        // 二维码字符串
                        String ewm = StringUtil.getValue(redrawData, "EWM");
                        // 插入二维码
                        ExcelRedrawUtil.setQRCode(wb, "二维码", ewm);
                    }
                    // 不动产证书
                    if (redrawData.get("ZZSJ") != null) {
                        // yyyy-MM-dd
                        Date date = DateTimeUtil.strToDate(String.valueOf(redrawData.get("ZZSJ")));
                        String ZZSJ_Y = DateTimeUtil.dateToStr(date, "yyyy");
                        redrawData.put("ZZSJ_Y", ZZSJ_Y);
                        String ZZSJ_M = DateTimeUtil.dateToStr(date, "MM");
                        redrawData.put("ZZSJ_M", ZZSJ_M);
                        String ZZSJ_D = DateTimeUtil.dateToStr(date, "dd");
                        redrawData.put("ZZSJ_D", ZZSJ_D);
                    }
                    // 插入字段
                    ExcelRedrawUtil.setExcelData(wb, redrawData);
                    // 将流写进文件
                    String filePath = ExcelRedrawUtil.getSavePath(fileExt);
                    fos = new FileOutputStream(filePath);
                    returnMap.put("TemplatePath", filePath);
                    wb.write(fos);
                    fos.flush();
                }
            }
        } catch (Exception e) {
            log.error("读取Excel文件流时出错", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("关闭Excel fis文件流时出错", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("关闭Excel fos文件流时出错", e);
                }
            }
        }
    }

    /**
     * 描述 产权证
     * 
     * @author Keravon Feng
     * @created 2019年12月25日 上午11:55:30
     * @param args
     */
    @Override
    public void setQzTemplateTestData(Map<String, Map<String, Object>> args) {
        Map<String, Object> returnMap = args.get("returnMap");
        // 参数
        Map<String, Object> requestMap = args.get("requestMap");
        /*业务数据*/
        Map<String, Object> busInfo = args.get("busInfo");
        /*基本信息*/
        Map<String, Object> execution = args.get("execution");
        // *******************************使用poi插入图片*************************************
        InputStream fis = null;
        FileOutputStream fos = null;
        String dirPath = AppUtil.getRealPath("attachFiles//certificateTemplate//files/");
        String templatePath = StringUtil.getValue(returnMap, "TemplatePath");
        File f = new File(dirPath + templatePath);
        // 文件格式名 xls/xlsx
        String fileExt = templatePath.substring(templatePath.lastIndexOf(".") + 1);
        try {
            if (f != null) {
                fis = new FileInputStream(f);
                Workbook wb = null;
                // 获取Workbook对象
                wb = ExcelRedrawUtil.getVersionWorkbook(fis);
                if (wb == null) {
                    throw new Exception("Excel格式错误");
                }
                List<Map> result = new ArrayList<>();
                AjaxJson ajaxJson = null;
                Map<String, Object> info = new HashMap<String, Object>();
                info.put("QLRZJH", StringUtil.getValue(requestMap, "QLRZJH"));
                info.put("BDCQZH", StringUtil.getValue(requestMap, "BDCQZH"));
                String eveId = StringUtil.getValue(requestMap, "EFLOW_EXEID");
                if (eveId != null && eveId.length() >= 12) {
                    // 取受理编号后12为作为业务号
                    info.put("YWH", eveId.substring(eveId.length() - 12));
                } else {
                    info.put("YWH", eveId);
                }
                String templateAlias = StringUtil.getValue(requestMap, "templateAlias");
                String typeId = StringUtil.getValue(requestMap, "typeId");
                if ("BDCQZ".equals(templateAlias)) {
                    /*如果是证书预览，则用表单中现有数据回填*/
                    if (typeId.equals("1")) {
                        setZsylData(result,busInfo,execution,info);
                    } else {
                        ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "cxbdcqzsxxxxUrl");
                        if (ajaxJson != null && ajaxJson.isSuccess()) {
                            String retJson = ajaxJson.getJsonString();
                            //测试用
                            retJson = getBdcqzInfo();
                            Map map = JSON.parseObject(retJson, Map.class);
                            if (map != null && map.get("result_object") != null) {
                                result = JSON.parseArray(String.valueOf(map.get("result_object")), Map.class);
                            }
                        }
                    }

                } else if ("DJZM".equals(templateAlias)) {
                    if (typeId.equals("1")) {
                        setDjzmylData(result, busInfo, execution, info);
                    } else {
                        ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(info, "dycxbdcqzsxxxxUrl");
                        if (ajaxJson != null && ajaxJson.isSuccess()) {
                            String retJson = ajaxJson.getJsonString();
                            //测试用
                            retJson = getDjzmInfo();
                            result = JSON.parseArray(retJson, Map.class);
                        }
                    }
                }
                if (result != null && result.size() > 0) {
                    // 构造回填数据 key:Excel中自定义的单元格名称 value:要设的值
                    Map<String, Object> redrawData = result.get(0);
                    if (redrawData.get("EWM") != null) {
                        // 二维码字符串
                        String ewm = StringUtil.getValue(redrawData, "EWM");
                        // 插入二维码
                        ExcelRedrawUtil.setQRCode(wb, "二维码", ewm);
                    }
                    // 不动产证书的zhi
                    if (redrawData.get("ZZSJ") != null) {
                        // yyyy-MM-dd
                        Date date = DateTimeUtil.strToDate(String.valueOf(redrawData.get("ZZSJ")));
                        String ZZSJ_Y = DateTimeUtil.dateToStr(date, "yyyy");
                        redrawData.put("ZZSJ_Y", ZZSJ_Y);
                        String ZZSJ_M = DateTimeUtil.dateToStr(date, "MM");
                        redrawData.put("ZZSJ_M", ZZSJ_M);
                        String ZZSJ_D = DateTimeUtil.dateToStr(date, "dd");
                        redrawData.put("ZZSJ_D", ZZSJ_D);
                    }
                    // 插入字段
                    ExcelRedrawUtil.setExcelData(wb, redrawData);
                    // 将流写进文件
                    String filePath = ExcelRedrawUtil.getSavePath(fileExt);
                    fos = new FileOutputStream(filePath);
                    returnMap.put("TemplatePath", filePath);
                    wb.write(fos);
                    fos.flush();
                }
            }
        } catch (Exception e) {
            log.error("读取Excel文件流时出错", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("关闭Excel fis文件流时出错", e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error("关闭Excel fos文件流时出错", e);
                }
            }
        }
    }

    /**
     * 描述:设置证书预览数据，需要回填表单中的数据
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/6/11 11:16:00
     */
    private void setZsylData(List<Map> result, Map<String, Object> busInfo,
                             Map<String, Object> execution,Map<String,Object> info) {
        String busTablename = (String) execution.get("BUS_TABLENAME");
        if (StringUtils.isNotEmpty(busTablename)) {
            if (busTablename.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0 || busTablename.equals("T_BDCQLC_LSYLWTCZ")) {
                /*设置国有建设及房屋转移登记预览证书回填数据*/
                setGyjsjfwzyZsylData(result, busInfo, execution,info);
            } else if (busTablename.equals("T_BDCQLC_GYJSSCDJ")) {
                /*设置国有建设用地使用权首次登记预览证书回填数据*/
                setGyjsscdjZsylData(result, busInfo, execution,info);
            } 
            else if (busTablename.equals("T_BDCQLC_JTJSSCDJ") || busTablename.equals("T_BDCQLC_JTJSZYDJ")
                    || busTablename.equals("T_BDCQLC_JTJSBGDJ")) {
                /*设置集体建设预览证书回填数据*/
                setGyjsjfwzyZsylData(result, busInfo, execution,info);
            } else if (busTablename.equals("T_BDCQLC_JTJSFWSCDJ") || busTablename.equals("T_BDCQLC_JTJSFWZYDJ")
                    || busTablename.equals("T_BDCQLC_JTJSFWBGDJ")) {
                /*设置集体建设房屋预览证书回填数据*/
                setGyjsjfwzyZsylData(result, busInfo, execution,info);
            }
        }
    }

    /**
     * 描述:设置登记证明预览数据，需要回填表单中的数据
     *
     * @author Madison You
     * @created 2020/8/5 9:56:00
     * @param
     * @return
     */
    private void setDjzmylData(List<Map> result, Map<String, Object> busInfo,
                               Map<String, Object> execution, Map<String, Object> info) {
        String busTablename = (String) execution.get("BUS_TABLENAME");
        //国有转移7个事项虚拟主表替换真实表名称
        if(busTablename.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTablename = "T_BDCQLC_GYJSJFWZYDJ";
        }
        if (StringUtils.isNotEmpty(busTablename)) {
            if (busTablename.equals("T_BDCQLC_GYJSJFWZYDJ")) {
                /*设置国有建设及房屋转移登记预览证书回填数据*/
                setGyjsjfwzyDjzmylData(result, busInfo, execution,info);
            } else if (busTablename.equals("T_BDCQLC_YGSPFYGDJ")) {
                /*设置预购商品房预告登记预览证书回填数据*/
                setYgspfygdjData(result, busInfo, execution,info);
            }else if (busTablename.equals("T_BDCQLC_YGSPFDYQYGDJ")) {
                /*设置预购商品房抵押预告登记预览证书回填数据*/
                setYgspfdyygdjData(result, busInfo, execution,info);
            }else if (busTablename.equals("T_BDCQLC_DYQSCDJZB")) {
                /*设置抵押权首次登记（以及转本）登记预览证书回填数据*/
                setDyqscdjData(result, busInfo, execution,info);
            }
        }
    }
    
    /**
     * 描述     设置抵押权首次登记（以及转本）登记预览证书回填数据
     * @author Allin Lin
     * @created 2020年11月28日 下午8:43:23
     * @param result
     * @param busInfo
     * @param execution
     * @param info
     */
    private void setDyqscdjData(List<Map> result, Map<String, Object> busInfo,
                                        Map<String, Object> execution, Map<String, Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*权利人*/
        params.put("QLR", StringUtil.getValue(busInfo, "DYQRXX_NAME"));
        /*抵押人*/
        params.put("DYR", StringUtil.getValue(busInfo, "DYQDJ_NAME"));
        /*坐落*/
        params.put("ZJJZWZL", StringUtil.getValue(busInfo, "LOCATED"));
        /*不动产单元号*/
        String dymxJson = StringUtil.getValue(busInfo, "DYMX_JSON");
        if (StringUtils.isNotEmpty(dymxJson)) {
            StringBuffer bdcdyh = new StringBuffer();
            List<Map> dwmxList = JSONArray.parseArray(dymxJson, Map.class);
            for (Map<String, Object> dymxMap : dwmxList) {
                bdcdyh.append(StringUtil.getValue(dymxMap, "BDCDYH")).append(",");
            }
            params.put("BDCDYH", bdcSpbConfig.bdcStringOutFormate(bdcdyh));
        }
        //params.put("BDCDYH", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        /*权利类型*/
        params.put("QLLX", "抵押首次登记");
        /*年、月、日*/
        params.put("CZSJ_N", DateTimeUtil.getCurrentYear());//年
        params.put("CZSJ_Y", DateTimeUtil.getCurrentMonth());//月
        Calendar cal = Calendar.getInstance();
        params.put("CZSJ_R", cal.get(Calendar.DATE));//日
        /*附记(其他)*/
        StringBuffer fj = new StringBuffer("");
        fj.append(StringUtil.getValue(busInfo, "DYQDJ_FJ"));
        params.put("FJ", fj);
        if(StringUtils.isNotEmpty(StringUtil.getValue(busInfo,"BDC_BDCDJZMH"))){
            params.put("JC",StringUtil.getValue(busInfo,"BDC_BDCDJZMH") ); 
        }
        result.add(params);
    }

    /**
     * 
     * 描述：设置预购商品房抵押预告登记预览证书回填数据
     * @author Rider Chen
     * @created 2020年8月15日 下午8:54:32
     * @param result
     * @param busInfo
     * @param execution
     * @param info
     */
    private void setYgspfdyygdjData(List<Map> result, Map<String, Object> busInfo,
                                        Map<String, Object> execution, Map<String, Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*抵押人*/
        String powpJson = StringUtil.getValue(busInfo, "YWR_JSON");
        if (StringUtils.isNotEmpty(powpJson)) {
            StringBuffer DYR = new StringBuffer();
            List<Map> powpList = JSONArray.parseArray(powpJson, Map.class);
            for (Map<String, Object> powpMap : powpList) {
                DYR.append(StringUtil.getValue(powpMap, "YWR_MC")).append(",");
            }
            params.put("DYR", bdcSpbConfig.bdcStringOutFormate(DYR));
        }
        /*权利人*/
        params.put("QLR", StringUtil.getValue(busInfo, "QLR_MC"));
        /*坐落*/
        params.put("ZJJZWZL", StringUtil.getValue(busInfo, "LOCATED"));
        /*不动产单元号*/
        params.put("BDCDYH", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        /*所属区域*/
        params.put("SSQY", "平潭");
        /*权利类型*/
        params.put("QLLX", "预告登记");
        /*序号*/
        params.put("XH", "0000001");
        params.put("ND", DateTimeUtil.getCurrentYear());//年
        params.put("CZSJ_N", DateTimeUtil.getCurrentYear());//年
        params.put("CZSJ_Y", DateTimeUtil.getCurrentMonth());//月
        Calendar cal = Calendar.getInstance();
        params.put("CZSJ_R", cal.get(Calendar.DATE));//日
        

        /*附记(其他)*/
        StringBuffer fj = new StringBuffer("");
        fj.append("预告登记种类：").append("预售商品房抵押权预告登记").append("\r\n");
        fj.append("不动产登记证明号：").append(StringUtil.getValue(busInfo, "DJXX_CQZH")).append("\r\n");
        fj.append("被担保主债权数额：").append(StringUtil.getValue(busInfo, "DBSE")).append("万元").append("\r\n");
        fj.append("债务履行期限：")
                .append(DateTimeUtil.formatDateStr(StringUtil.getValue(busInfo, "ZW_BEGIN"), "yyyy-MM-dd",
                        "yyyy年MM月dd日"))
                .append("至")
                .append(DateTimeUtil.formatDateStr(StringUtil.getValue(busInfo, "ZW_END"), "yyyy-MM-dd", "yyyy年MM月dd日"))
                .append("\r\n");

        fj.append(StringUtil.getValue(busInfo, "HT_LX")).append("：").append(StringUtil.getValue(busInfo, "HT_NO"))
                .append("\r\n");
        params.put("FJ", fj);
        if(StringUtils.isNotEmpty(StringUtil.getValue(busInfo,"BDC_BDCDJZMH"))){
            params.put("JC",StringUtil.getValue(busInfo,"BDC_BDCDJZMH") ); 
        }
        result.add(params);
    }
    /**
     * 
     * 描述： 设置预购商品房预告登记预览证书回填数据
     * @author Rider Chen
     * @created 2020年8月15日 下午2:54:24
     * @param result
     * @param busInfo
     * @param execution
     * @param info
     */
    private void setYgspfygdjData(List<Map> result, Map<String, Object> busInfo,
                                        Map<String, Object> execution, Map<String, Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*权利人信息*/
        String powpJson = StringUtil.getValue(busInfo, "QLR_JSON");
        if (StringUtils.isNotEmpty(powpJson)) {
            StringBuffer QLR = new StringBuffer();
            List<Map> powpList = JSONArray.parseArray(powpJson, Map.class);
            for (Map<String, Object> powpMap : powpList) {
                QLR.append(StringUtil.getValue(powpMap, "MSFXM")).append(",");
            }
            params.put("QLR", bdcSpbConfig.bdcStringOutFormate(QLR));
        }
        /*抵押人*/
        params.put("DYR", StringUtil.getValue(busInfo, "ZRFXM"));
        /*坐落*/
        params.put("ZL", StringUtil.getValue(busInfo, "ZL"));
        /*不动产单元号*/
        params.put("BDCDYH", StringUtil.getValue(busInfo, "BDCDYH"));
        /*所属区域*/
        params.put("SSQY", "平潭");
        /*权利类型*/
        params.put("QLLX", "预告登记");
        /*序号*/
        params.put("XH", "0000001");
        params.put("ND", DateTimeUtil.getCurrentYear());//年
        params.put("CZSJ_N", DateTimeUtil.getCurrentYear());//年
        params.put("CZSJ_Y", DateTimeUtil.getCurrentMonth());//月
        Calendar cal = Calendar.getInstance();
        params.put("CZSJ_R", cal.get(Calendar.DATE));//日

        /*附记(其他)*/
        StringBuffer fj = new StringBuffer("");
        fj.append("预告登记种类：").append("预售商品房买卖预告登记").append("\r\n");
        fj.append(StringUtil.getValue(busInfo, "HTLX")).append("：").append(StringUtil.getValue(busInfo, "FWMMHTH")).append("\r\n");
        params.put("FJ", fj);
        params.put("JC", StringUtil.getValue(busInfo,"BDC_BDCDJZMH"));
        result.add(params);
    }
    /**
     * 描述:设置国有建设及房屋转移登记预览登记证明回填数据
     *
     * @author Madison You
     * @created 2020/8/5 9:57:00
     * @param
     * @return
     */
    private void setGyjsjfwzyDjzmylData(List<Map> result, Map<String, Object> busInfo,
                                        Map<String, Object> execution, Map<String, Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*权利人信息*/
        params.put("QLR", StringUtil.getValue(busInfo, "DY_DYQR"));
        /*抵押人*/
        params.put("DYR", StringUtil.getValue(busInfo, "DY_DYR"));
        /*坐落*/
        params.put("ZL", StringUtil.getValue(busInfo, "LOCATED"));
        /*不动产单元号*/
        params.put("BDCDYH", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        /*附记*/
        StringBuffer fj = new StringBuffer();
        fj.append("不动产权证书号：\r");
        fj.append("抵押不动产类型：土地和房屋\r");
        fj.append("抵押方式：").append(dictionaryService.getDicNames("BDCDYFS", StringUtil.getValue(busInfo, "DY_DYFS"))).append("\r");
        String DY_SFZGEDY = StringUtil.getValue(busInfo, "DY_SFZGEDY");
        if ("0".equals(DY_SFZGEDY)) {
            fj.append("被担保主债权数额：").append(StringUtil.getValue(busInfo, "DY_DBSE")).append("万元").append("\r");
        } else {
            fj.append("最高债权额：").append(StringUtil.getValue(busInfo, "DY_ZGZQSE")).append("万元").append("\r");
        }
        fj.append("债务履行期限：");
        String dyQlqssj = StringUtil.getValue(busInfo, "DY_QLQSSJ");
        String dyQljssj = StringUtil.getValue(busInfo, "DY_QLJSSJ");
        if (StringUtils.isNotEmpty(dyQljssj) && StringUtils.isNotEmpty(dyQlqssj)) {
            fj.append(bdcSpbConfig.bdcDateFormat(dyQlqssj, "yyyy-MM-dd", "yyyy年MM月dd日"))
                    .append("至").append(bdcSpbConfig.bdcDateFormat(dyQljssj, "yyyy-MM-dd", "yyyy年MM月dd日")).append("\r");
        } else {
            fj.append("\r");
        }
        fj.append("抵押范围：").append(StringUtil.getValue(busInfo, "DY_DYFW"));
        params.put("FJ", fj);
        result.add(params);
    }

    /**
     * 描述:设置国有建设及房屋转移登记预览证书回填数据
     *
     * @author Madison You
     * @created 2020/6/11 14:28:00
     * @param
     * @return
     */
    private void setGyjsjfwzyZsylData(List<Map> result, Map<String, Object> busInfo,
                                      Map<String, Object> execution,Map<String,Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*权利人信息*/
        String powpJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(powpJson)) {
            StringBuffer QLR = new StringBuffer();
            List<Map> powpList = JSONArray.parseArray(powpJson, Map.class);
            for (Map<String, Object> powpMap : powpList) {
                QLR.append(StringUtil.getValue(powpMap, "POWERPEOPLENAME")).append(",");
            }
            params.put("QLR", bdcSpbConfig.bdcStringOutFormate(QLR));
        }
        /*权限来源*/
        String powsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        StringBuffer YQLR = new StringBuffer();
        if (StringUtils.isNotEmpty(powsJson)) {
            List<Map> powsList = JSONArray.parseArray(powsJson, Map.class);
            for (Map<String, Object> powsMap : powsList) {
                YQLR.append(StringUtil.getValue(powsMap, "POWERSOURCE_QLRMC")).append(",");
            }
        }
        /*持证类型*/
        params.put("GYQK", dictionaryService.getDicNames("GYFS",
                StringUtil.getValue(busInfo, "FW_GYQK")));
        /*坐落*/
        params.put("ZL", StringUtil.getValue(busInfo, "LOCATED"));
        /*不动产单元号*/
        params.put("BDCDYH", StringUtil.getValue(busInfo, "ESTATE_NUM"));

        String FW_SFDJ = StringUtil.getValue(busInfo, "FW_SFDJ");
        if (StringUtils.isNotEmpty(FW_SFDJ) && FW_SFDJ.equals("1")) {
            /*权利类型*/
            params.put("QLLX", "房屋所有权");
            /*权利性质*/
            params.put("QLXZ", StringUtil.getValue(busInfo,"FW_XZ"));
            /*用途*/
            params.put("YT",StringUtil.getValue(busInfo,"FW_YTSM"));
            /*面积*/
            StringBuffer mj = new StringBuffer();
            mj.append("房屋建筑面积：").append(StringUtil.getValue(busInfo, "FW_JZMJ")).append("m²");
            params.put("MJ",mj);
        } else {
            /*权利类型*/
            params.put("QLLX", dictionaryService.getDicNames("ZDQLLX", StringUtil.getValue(busInfo, "FW_QLLX")));
            /*权利性质*/
            params.put("QLXZ", dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"),"100")
                    + "/" + StringUtil.getValue(busInfo,"FW_XZ"));
            /*用途*/
            params.put("YT",StringUtil.getValue(busInfo,"ZD_YTSM") + "/" + StringUtil.getValue(busInfo,"FW_YTSM"));
            /*面积*/
            StringBuffer mj = new StringBuffer();
            mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²").append("/");
            mj.append("房屋建筑面积：").append(StringUtil.getValue(busInfo, "FW_JZMJ")).append("m²");
            params.put("MJ",mj);
        }

        String GYTD_BEGIN_TIME = StringUtil.getValue(busInfo, "GYTD_BEGIN_TIME");
        String GYTD_END_TIME1 = StringUtil.getValue(busInfo, "GYTD_END_TIME1");
        String GYTD_END_TIME2 = StringUtil.getValue(busInfo, "GYTD_END_TIME2");
        String GYTD_END_TIME3 = StringUtil.getValue(busInfo, "GYTD_END_TIME3");
        String begin = "";
        String end1 = "";
        String end2 = "";
        String end3 = "";
        StringBuffer SYQX = new StringBuffer();
        if (StringUtils.isNotEmpty(GYTD_BEGIN_TIME)) {
            begin = bdcSpbConfig.bdcDateFormat(GYTD_BEGIN_TIME,"yyyy-MM-dd","yyyy年MM月dd日");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME1)) {
            end1 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME1,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append(begin + "起至" + end1 + "止");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME2)) {
            end2 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME2,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append("\r").append(begin + "起至" + end2 + "止");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME3)) {
            end3 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME3,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append("\r").append(begin + "起至" + end3 + "止");
        }
        /*权利期限*/
        params.put("SYQX",SYQX);
        StringBuffer QLQTQK = new StringBuffer();
        StringBuffer QLGYQK = new StringBuffer();
        String QLGYQKSTR = "";
        String fwGyqk = StringUtil.getValue(busInfo, "FW_GYQK");
        if (fwGyqk.equals("2")) {
            QLGYQK.append("\r").append("共有情况：");
            String powpJsonS = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
            if (StringUtils.isNotEmpty(powpJsonS)) {
                List<Map> powpListS = JSONArray.parseArray(powpJsonS, Map.class);
                for (Map<String, Object> powpMapS : powpListS) {
                    QLGYQK.append(StringUtil.getValue(powpMapS, "POWERPEOPLENAME")).append(" ").
                            append("权利占比：").append(StringUtil.getValue(powpMapS, "POWERPEOPLEPRO") + "%").append("、");
                }
            }
            QLGYQKSTR = QLGYQK.substring(0, QLGYQK.length() - 1) + "\r";
        }
        QLQTQK.append(QLGYQKSTR).append("房屋结构：").append(StringUtil.getValue(busInfo, "FW_FWJG")).append("\r");
        String dydytdmj = StringUtil.getValue(busInfo, "FW_DYDYTDMJ");
        if (StringUtils.isNotEmpty(dydytdmj)) {
            QLQTQK.append("独有土地面积：").append(dydytdmj).append("㎡").append("\r");
        }
        QLQTQK.append("专有建筑面积：").append(StringUtil.getValue(busInfo, "FW_ZYJZMJ")).append("㎡");
        String fwFtjzmj = StringUtil.getValue(busInfo, "FW_FTJZMJ");
        if (!Objects.equals(fwFtjzmj, "0")) {
            QLQTQK.append("，").append("分摊建筑面积:").append(fwFtjzmj).append("㎡").append("\r");
        } else {
            QLQTQK.append("\r");
        }
        QLQTQK.append("房屋总层数：").append(StringUtil.getValue(busInfo, "FW_ZCS")).append("层，所在层：")
                .append(StringUtil.getValue(busInfo, "FW_SZC")).append("\r").append("房屋竣工时间：")
                .append(bdcSpbConfig.bdcDateFormat(StringUtil.getValue(busInfo, "FW_JGSJ"),
                        "yyyy-MM-dd", "yyyy年MM月dd日"));
        /*其他性质*/
        params.put("QLQTQK", QLQTQK);
        /*附记*/
        params.put("FJ", StringUtil.getValue(busInfo,"FW_FJ"));
        result.add(params);
    }

    
    /**
     * 
     * 描述    国有建设用地使用权首次登记证书预览数据回填
     * @author Allin Lin
     * @created 2020年11月10日 上午10:31:53
     * @param result
     * @param busInfo
     * @param execution
     * @param info
     */
    private void setGyjsscdjZsylData(List<Map> result, Map<String, Object> busInfo,
                                      Map<String, Object> execution,Map<String,Object> info) {
        Map<String, Object> params = new HashMap<>();
        /*权利人信息*/
        String powpJson = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
        if (StringUtils.isNotEmpty(powpJson)) {
            StringBuffer QLR = new StringBuffer();
            List<Map> powpList = JSONArray.parseArray(powpJson, Map.class);
            for (Map<String, Object> powpMap : powpList) {
                QLR.append(StringUtil.getValue(powpMap, "POWERPEOPLENAME")).append(",");
            }
            params.put("QLR", bdcSpbConfig.bdcStringOutFormate(QLR));
        }
        /*权限来源*/
        String powsJson = StringUtil.getValue(busInfo, "POWERSOURCEINFO_JSON");
        StringBuffer YQLR = new StringBuffer();
        if (StringUtils.isNotEmpty(powsJson)) {
            List<Map> powsList = JSONArray.parseArray(powsJson, Map.class);
            for (Map<String, Object> powsMap : powsList) {
                YQLR.append(StringUtil.getValue(powsMap, "QLR")).append(",");
            }
        }
        /*共有情况*/
        params.put("GYQK", dictionaryService.getDicNames("GYFS",
                StringUtil.getValue(busInfo, "GYTD_GYFS")));
        /*坐落*/
        params.put("ZL", StringUtil.getValue(busInfo, "LOCATED"));
        /*不动产单元号*/
        params.put("BDCDYH", StringUtil.getValue(busInfo, "ESTATE_NUM"));
        
        /*权利类型*/
        params.put("QLLX", "国有建设用地使用权");
        /*权利性质*/
        String ZD_QLXZ =  dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"),"100");
        if(StringUtils.isEmpty(ZD_QLXZ)){
            ZD_QLXZ =  dictionaryService.findByDicCodeAndTypeCode(StringUtil.getValue(busInfo, "ZD_QLXZ"),"200");
        }
        params.put("QLXZ",ZD_QLXZ);
        
        /*用途*/
        params.put("YT",StringUtil.getValue(busInfo,"ZD_YTSM"));
        
        /*面积*/
        StringBuffer mj = new StringBuffer();
        mj.append("共有宗地面积：").append(StringUtil.getValue(busInfo, "ZD_MJ")).append("m²");
        params.put("MJ",mj);
        
        String GYTD_BEGIN_TIME = StringUtil.getValue(busInfo, "GYTD_BEGIN_TIME");
        String GYTD_END_TIME1 = StringUtil.getValue(busInfo, "GYTD_END_TIME1");
        String GYTD_END_TIME2 = StringUtil.getValue(busInfo, "GYTD_END_TIME2");
        String GYTD_END_TIME3 = StringUtil.getValue(busInfo, "GYTD_END_TIME3");
        String begin = "";
        String end1 = "";
        String end2 = "";
        String end3 = "";
        StringBuffer SYQX = new StringBuffer();
        if (StringUtils.isNotEmpty(GYTD_BEGIN_TIME)) {
            begin = bdcSpbConfig.bdcDateFormat(GYTD_BEGIN_TIME,"yyyy-MM-dd","yyyy年MM月dd日");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME1)) {
            end1 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME1,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append(begin + "起至" + end1 + "止");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME2)) {
            end2 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME2,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append("\r").append(begin + "起至" + end2 + "止");
        }
        if (StringUtils.isNotEmpty(GYTD_END_TIME3)) {
            end3 = bdcSpbConfig.bdcDateFormat(GYTD_END_TIME3,"yyyy-MM-dd","yyyy年MM月dd日");
            SYQX.append("\r").append(begin + "起至" + end3 + "止");
        }
        /*权利期限*/
        params.put("SYQX",SYQX);

        
        StringBuffer QLQTQK = new StringBuffer();
        StringBuffer QLGYQK = new StringBuffer();
        String QLGYQKSTR = "";
        String fwGyqk = StringUtil.getValue(busInfo, "GYTD_GYFS");
        if (fwGyqk.equals("2")) {//按份共有
            QLGYQK.append("\r").append("共有情况：");
            String powpJsonS = StringUtil.getValue(busInfo, "POWERPEOPLEINFO_JSON");
            if (StringUtils.isNotEmpty(powpJsonS)) {
                List<Map> powpListS = JSONArray.parseArray(powpJsonS, Map.class);
                for (Map<String, Object> powpMapS : powpListS) {
                    QLGYQK.append(StringUtil.getValue(powpMapS, "POWERPEOPLENAME")).append(" ").
                            append("权利占比：").append(StringUtil.getValue(powpMapS, "POWERPEOPLEPRO") + "%").append("、");
                }
            }
            QLGYQKSTR = QLGYQK.substring(0, QLGYQK.length() - 1) + "\r";
        }
        QLQTQK.append(QLGYQKSTR);
        /*权利其他性质*/
        params.put("QLQTQK", QLQTQK);
        /*附记*/
        params.put("FJ", StringUtil.getValue(busInfo,"GYTD_FJ"));
        result.add(params);
    }
    

    /**
     * 描述
     * @author Keravon Feng
     * @created 2019年12月27日 上午9:32:56
     * @return
     */
    private String getDjzmInfo() {
        String retJson;
        retJson = "[{\"SSQY\":\"平潭\",\"ND\":\"2019\",\"XH\":\"0011364\",\"ZMQL\":\"抵押权\","
                + "\"QLR\":\"林同强\",\"DYR\":null,\"BDCDYH\":\"351001012010GB01859F00040015\","
                + "\"ZJJZWZL\":\"平潭县潭城镇溪南庄188号旺达富春城B区9#楼502室\",\"QT\":\"不动产权证书号："
                + "闽(2019)平潭不动产权第0011364号\\n抵押不动产类型：土地和房屋\\n抵押方式：一般抵押\\n被担保主债权数额："
                + "70万元\\n债务履行期限:2017年03月03日至2047年03月03日\\n抵押范围：详见抵押合同\\n\",\"CZSJ_N\":"
                + "\"2019\",\"CZSJ_Y\":\"11\",\"CZSJ_R\":\"26\",\"EWM\":\"351001012010GB01859F00040015/"
                + "闽(2019)平潭不动产权第0011364号/林同强/2019-11-26\"}]";
        return retJson;
    }

    /**
     * 描述
     * @author Keravon Feng
     * @created 2019年12月27日 上午9:32:20
     * @return
     */
    private String getBdcqzInfo() {
        String retJson;
        retJson = "{\"log\":null,\"result\":true,\"result_object\":"
                + "[{\"QLR\":\"董玉侠\",\"GYQK\":\"单独所有\",\"ZL\":\"平潭县竹屿湖中路8号正荣.御湖湾6#楼23A01室\","
                + "\"BDCDYH\":\"351001 011013 GB00359 F00050071\",\"QLLX\":\"国有建设用地使用权/房屋所有权\","
                + "\"QLXZ\":\"出让/市场化商品房\",\"YT\":\"其他商服用地,城镇住宅用地/成套住宅\",\"MJ\":\"共有宗地面积23228㎡/房屋建筑面积141.3㎡\","
                + "\"SYQX\":\"2015年07月31日起至2055年07月30日止\\r2015年07月31日起至2085年07月30日止\","
                + "\"QLQTQK\":\"\\r房屋结构：钢筋混凝土结构\\r专有建筑面积:    108.25㎡,分摊建筑面积:     33.05㎡\\r房屋总层数："
                + "33层，所在层：24层\\r房屋竣工时间：2017年08月17日\",\"FJ\":\"业务编号:201911110057"
                + "\\r不动产取得方式:国有建设用地使用权/房屋所有权转移登记(分户)(抵押联办)\\r原权利人"
                + ":正荣山田（平潭）投资发展有限公司\\r该宗地为共用宗，土地使用权为全体业主共有，共有土地使用权"
                + "面积为23228平方米。\\\\r\\\\n不动产抵押、查封情况及相关附图可在平潭综合实验区行政服务中"
                + "心门户网站上查阅。\",\"ZDT\":\"http://10.23.202.15/fileservice/"
                + "Upload/ZDJBXX/351001011013GB00359F00050071/184/宗地图.jpg\","
                + "\"FHT\":\"http://10.23.202.15/fileservice/Upload/HU/351001011013GB00359F00050071/185/分户图.jpg\""
                + ",\"EWM\":\"351001011013GB00359F00050071/闽(2019)平潭不动产权第0011049号/董玉侠/2019年11月13日\","
                + "\"JC\":\"闽\",\"NF\":\"2019\",\"SX\":\"平潭\",\"XH\":\"0011049\",\"SFJF\":\"0\","
                + "\"ZZSJ\":\"2019-11-13\",\"YWDL\":\"转移登记\"}]}";
        return retJson;
    }

}
