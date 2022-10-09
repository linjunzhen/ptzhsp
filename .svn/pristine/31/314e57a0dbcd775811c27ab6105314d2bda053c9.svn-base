/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.statics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.statis.dao.StatisticsDao;
import net.evecom.platform.statis.service.StatisticsService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年3月8日 下午2:03:11
 */
public class StatisticsTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(StatisticsTestCase.class);
    /**
     * 
     */
    @Resource
    private StatisticsDao statisticsDao;
    /**
     * 
     */
    @Resource
    private StatisticsService statisticsService;
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月8日 下午2:03:56
     */
    @Test
    public void getStaticCount(){
        SqlFilter filter= new SqlFilter(new PagingBean());
        filter.addFilter("Q_E.SSBMBM_EQ","577012559");
        statisticsDao.getStaticCount(filter);
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月8日 下午2:24:09
     */
    @Test
    public void saveOrUpdateStatis(){
        statisticsService.saveOrUpdateStatis("2016-02-26","2016-03-08");
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月8日 下午7:49:18
     */
    @Test
    public void findBySqlFilter(){
        SqlFilter filter = new SqlFilter(new PagingBean());
        List<Map<String,Object>> dataList = statisticsService.findBySqlFilter(filter);
        String tplPath = "d:/bjtjbstatisc.xls";
        POIFSFileSystem fs;
        int startRow = 6;
        int startCol = 1;
        // 工作簿
        HSSFWorkbook hssfworkbook = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(tplPath));
            // 取得相应工作簿
            hssfworkbook = new HSSFWorkbook(fs);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        // 创建sheet页
        HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);
        // 内容样式
        HSSFCellStyle contentStyle = hssfworkbook.createCellStyle();
        for (int i = 0; i < dataList.size(); i++) {
            Map<String,Object> results = dataList.get(i);
            // 取得第一行
            HSSFRow hssfrow = hssfsheet.createRow(startRow - 1 + i);
            hssfrow.setHeight((short) 1000);
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 靠右
            contentStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
            contentStyle.setWrapText(true);
            contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
            contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
            contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
            contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
            // contentStyle.set
            // 开始向右创建单元格
            Iterator it = results.entrySet().iterator();
            int j = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String fieldName = (String) entry.getKey();
                if(!fieldName.equals("DEP_CODE")){
                    Object val = entry.getValue();
                    String value = "";
                    HSSFCell cell = hssfrow.createCell(startCol - 1 + j);
                    cell.setCellStyle(contentStyle); // 设置数据单元格的样式
                    if (val != null) {
                        value = val.toString();
                    }
                    cell.setCellValue(new HSSFRichTextString(value));
                    j++;
                }
            }
        }
        
        // 输出
        FileOutputStream fileoutputstream = null;
        try {
            fileoutputstream = new FileOutputStream("d:/测试.xls");
            hssfworkbook.write(fileoutputstream);
            fileoutputstream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }finally{
            if (fileoutputstream!=null) {
                try {
                    fileoutputstream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage());
                }
            }
        }
    }
}
