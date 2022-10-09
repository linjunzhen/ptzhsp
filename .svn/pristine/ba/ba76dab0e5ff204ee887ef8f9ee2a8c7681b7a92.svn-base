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
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.dao.BdcqZxdjDao;
import net.evecom.platform.bdc.service.BdcqZxdjService;
import net.evecom.platform.bdc.util.ExcelRedrawUtil;
import net.evecom.platform.system.service.DictionaryService;

/**
 * 
 * 描述 不动产权注销登记Service
 * 
 * @author Roger Li
 * @version 1.0
 * @created 2019年12月20日 上午11:29:27
 */
@Service("bdcqZxdjService")
public class BdcqZxdjServiceImpl extends BaseServiceImpl implements BdcqZxdjService {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BdcqZxdjService.class);
    /**
     * 引入Service
     */
    @Resource
    private BdcqZxdjDao bdcqZxdjDao;
    /**
     * 引入Service
     */
    @Resource
    DictionaryService dictionaryService;

    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.bdcqZxdjDao;
    }

    /**
     * 
     * 描述 阅办模板自定义回填数据的方法
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午9:58:25
     * @param flowAllObj
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public void getRedrawData(Map<String, Map<String, Object>> args) {
        Map<String, Object> flowAllObj = args.get("flowAllObj");
        Map<String, Object> execution = args.get("execution");
        Map<String, Object> serviceItem = args.get("serviceItem");
        Map<String, Object> returnMap = args.get("returnMap");
        // 获取业务表相关数据
        Map<String, Object> busInfo = null;
        if (flowAllObj.get("busRecord") != null) {
            busInfo = (Map<String, Object>) flowAllObj.get("busRecord");
        }
        // 为标签设初值以防页面出现标签
        this.setDefaultValue(returnMap, StringUtil.getValue(execution, "EXE_ID"));
        // 设值流程主表相关信息
        this.setSubmitUsrsInfo(returnMap, execution);
        // 设值服务事项和流程表单相关信息
        this.setItemAndDefInfo(returnMap, serviceItem);
        // 设值业务表相关信息
        this.setBusRecordInfo(returnMap, busInfo);
    }

    /**
     * 
     * 描述 证书模板自定义回填数据的方法
     * 
     * @author Roger Li
     * @created 2019年12月18日 上午9:58:25
     * @param args
     * @return
     */
    @Override
    public void setCertificateRedrawData(Map<String, Map<String, Object>> args) {
        Map<String, Object> returnMap = args.get("returnMap");
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
                // 模拟接口获取的值
                List<Map> result = null;
                String testJsonData = "[{ " + "\"QLR\": \"徐永杰\"," + "\"ZL\": \"平潭综合实验区万北路与平岚二路交叉口东北侧长福麒麟海湾三期5#楼1603室\","
                        + "\"BDCDYH\": \"351001011013GB00362F00160062\","
                        + "\"FJ\": \"预告登记种类：预售商品房买卖预告登记\n预售合同:2019000488\","
                        + "\"EWM\": \"351001011013GB00362F00160062/闽(2019)平潭不动产证明第0003647号/徐永杰/2019年03月11日 \","
                        + "\"JC\": \"闽\"," + "\"NF\": \"2019\"," + "\"SX\": \"平潭\"," + "\"QLMC\": \"预告登记\","
                        + "\"YWR\": \"平潭长福文化地产有限公司\"," + "\"XH\": \"0003647\"," + "\"QLLX\":\"抵押权\","
                        + "\"QLXZ\":\"出让/市场化商品房\"," + "\"QLQTQK\":\"房屋结构：钢筋混凝土解构\","
                        + "\"SYQX\":\"2019年06月起到2053年06月06日止\"," + "\"MJ\":\"共有宗地面积59374.09㎡/房屋建筑面积203.48㎡(回填)\","
                        + "\"YT\":\"回填用途\"" + "}]";
                result = JSON.parseArray(testJsonData, Map.class);

                // 构造回填数据 key:Excel中自定义的单元格名称 value:要设的值
                Map<String, Object> redrawData = result.get(0);
                String dateStr = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd");
                redrawData.put("year", DateTimeUtil.getYear(dateStr));
                redrawData.put("month", DateTimeUtil.getMonth(dateStr));
                redrawData.put("day", DateTimeUtil.getDay(dateStr));

                // 二维码字符串
                String ewm = StringUtil.getValue(redrawData, "EWM");
                // 插入二维码
                ExcelRedrawUtil.setQRCode(wb, "二维码", ewm);
                // 插入字段
                ExcelRedrawUtil.setExcelData(wb, redrawData);
                // 将流写进文件
                String filePath = ExcelRedrawUtil.getSavePath(fileExt);
                fos = new FileOutputStream(filePath);
                returnMap.put("TemplatePath", filePath);
                wb.write(fos);
                fos.flush();
            }
        } catch (Exception e) {
            log.error("读取Excel文件流时出错", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 
     * 描述 为标签设初值以防页面出现标签
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:35:52
     * @param returnMap
     * @return
     */
    private void setDefaultValue(Map<String, Object> returnMap, String exeId) {
        // // 数据初始化
        // returnMap.put("qlrxm", "");
        // returnMap.put("djlx", "");
        // returnMap.put("sfzjzl1", "");
        // returnMap.put("zjh1", "");
        // returnMap.put("zl", "");
        // returnMap.put("dbr1", "");
        // returnMap.put("dh1", "");
        // returnMap.put("ywrxm", "");
        // returnMap.put("txdz2", "");
        // returnMap.put("sfzjzl2", "");
        // returnMap.put("zjh2", "");
        // returnMap.put("dbr2", "");
        // returnMap.put("dljgmc1", "");
        // returnMap.put("dljgmc2", "");
        // returnMap.put("dh3", "");
        // returnMap.put("dlrxm2", "");
        // returnMap.put("dh4", "");
        // returnMap.put("yt", "");
        // returnMap.put("mj", "");
        // returnMap.put("ybdcqszsh", "");
        // returnMap.put("yhlx", "");
        // returnMap.put("gzwlx", "");
        // returnMap.put("lz", "");
        // returnMap.put("bdbzqse", "");
        // returnMap.put("zwlxqx", "");
        // returnMap.put("zjjzwdyfw", "");
        // returnMap.put("xydzl", "");
        // returnMap.put("xydbdcdyh", "");
        // returnMap.put("szfefbw1", "");
        // returnMap.put("szfefbw2", "");
        // returnMap.put("qtxyxw1", "");
        // returnMap.put("qtxyxw2", "");
        // returnMap.put("bdclx", "土地");
        // returnMap.put("bh", exeId);
        // returnMap.put("sjr", "");
        // String date = DateTimeUtil.dateToStr(new Date());
        // returnMap.put("rq", date);
        // returnMap.put("nian", DateTimeUtil.getCurrentYear());
        // returnMap.put("nian2", DateTimeUtil.getCurrentYear());
        // returnMap.put("yue", DateTimeUtil.getCurrentMonth());
        // returnMap.put("yue2", DateTimeUtil.getCurrentMonth());
        // returnMap.put("ri", date.substring(date.lastIndexOf("-") + 1,
        // date.length()));
        // returnMap.put("ri2", date.substring(date.lastIndexOf("-") + 1,
        // date.length()));
    }

    /**
     * 
     * 描述 获取流程主表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:52:45
     * @param execution
     * @return
     */
    private void setSubmitUsrsInfo(Map<String, Object> returnMap, Map<String, Object> execution) {
        // 申请人通信地址
        returnMap.put("txdz1", StringUtil.getValue(execution, "SQRLXDZ"));
        // 窗口收件人
        returnMap.put("sjr", StringUtil.getValue(execution, "CREATOR_NAME"));
    }

    /**
     * 
     * 描述 设值业务表数据
     * 
     * @author Roger Li
     * @created 2019年12月17日 上午10:56:30
     * @param busInfo
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    private void setBusRecordInfo(Map<String, Object> returnMap, Map<String, Object> busInfo) {
        List<Map> zxmx = JSON.parseArray(StringUtil.getValue(busInfo, "ZXMX_JSON"), Map.class);
        Map<String, Object> zxmxMap = zxmx.get(0);
        returnMap.put("zl", StringUtil.getValue(zxmxMap, "FDZL"));
        returnMap.put("bdcdyh", StringUtil.getValue(zxmxMap, "BDCDYH"));
        returnMap.put("qlxz", StringUtil.getValue(zxmxMap, "QLXZ"));
        returnMap.put("mj", StringUtil.getValue(zxmxMap, "ZDMJ"));
        returnMap.put("yt", StringUtil.getValue(zxmxMap, "FW_GHYT"));
        returnMap.put("qlrxm", StringUtil.getValue(zxmxMap, "QLRMC"));
        returnMap.put("djyy", StringUtil.getValue(busInfo, "ZXYY"));
        Map<String, Object> dictionary = dictionaryService.getByJdbc("T_MSJW_SYSTEM_DICTIONARY",
                new String[] { "TYPE_CODE", "DIC_CODE" },
                new Object[] { "ZDQLLX", StringUtil.getValue(busInfo, "QLLX") });
        returnMap.put("qllx", dictionary.get("DIC_NAME"));
    }

    /**
     * 
     * 描述 设值服务事项和流程表单相关信息
     * 
     * @author Roger Li
     * @created 2019年12月17日 下午2:26:46
     * @param returnMap,serviceItem
     * @return
     */
    private void setItemAndDefInfo(Map<String, Object> returnMap, Map<String, Object> serviceItem) {
        // 登记类型
        returnMap.put("djlx", StringUtil.getValue(serviceItem, "CATALOG_NAME"));
    }
}
