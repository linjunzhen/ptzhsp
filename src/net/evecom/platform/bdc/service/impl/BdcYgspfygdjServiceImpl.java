/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import net.evecom.core.util.AppUtil;
import net.evecom.core.util.CustomXWPFDocument;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bdc.service.BdcDyqscdjService;
import net.evecom.platform.bdc.service.BdcQlcCreateSignFlowService;
import net.evecom.platform.bdc.service.BdcQlcMaterGenAndSignService;
import net.evecom.platform.bdc.util.WordRedrawUtil;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.bdc.dao.BdcYgspfygdjDao;
import net.evecom.platform.bdc.service.BdcYgspfygdjService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述 预购商品房预告操作service
 * @author Allin Lin
 * @created 2019年3月13日 上午10:22:53
 */
@Service("bdcYgspfygdjService")
public class BdcYgspfygdjServiceImpl extends BaseServiceImpl implements BdcYgspfygdjService{

    /**
     * log
     */
    private static Log log=LogFactory.getLog(BdcYgspfygdjServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private BdcYgspfygdjDao dao;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * 
     */
    @Resource
    private BdcQlcMaterGenAndSignService bdcQlcMaterGenAndSignService;
    
    /**
     * 
     */
    @Resource
    private ExeDataService exeDataService;
    
    /**
     * 描述
     * @author Allin Lin
     * @created 2019年3月13日 上午10:24:38
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {      
        return dao;
    }
    /**
     * 
     * 描述 
     * @author Danto Huang
     * @created 2020年8月24日 下午5:28:37
     * @param flowVars
     * @return
     * @see net.evecom.platform.bdc.service.BdcYgspfygdjService#initGenValue(java.util.Map)
     */
    public Map<String,Object> initGenValue(Map<String,Object> flowVars){
        Map<String,Object> result = new HashMap<String, Object>();
        String QLR_JSON = (String) flowVars.get("QLR_JSON");
        List QLR_LIST = JSON.parseArray(QLR_JSON, Map.class);
        for(int i=0;i<QLR_LIST.size();i++){
            Map qlr = (Map) QLR_LIST.get(i);
            qlr.put("MSFZJLB_F", zjlxFormmat(qlr.get("MSFZJLB").toString()));
            if(i==0){
                if(QLR_LIST.size()==1){
                    qlr.put("GYQK", "■单独所有  □共同共有  □按份共有______%"); 
                }else{
                    qlr.put("GYQK", "□单独所有 ■共同共有  □按份共有______%");  
                }               
            }else{
                qlr.put("GYQK", "■共同共有 □按份共有______%");
            }
        }
        result.put("QLR_LIST", QLR_LIST);
        if(flowVars.get("LZRXM")!=null){
            result.put("QLR_LXR", flowVars.get("LZRXM"));
        }else{
            result.put("QLR_LXR", "");
        }
        if(flowVars.get("LZRSJHM")!=null){
            result.put("QLR_LXRSJ", flowVars.get("LZRSJHM"));
        }else{
            result.put("QLR_LXRSJ", "");
        }
        //义务人
        result.put("ZRFXM", flowVars.get("ZRFXM"));
        result.put("ZRFZJLB", flowVars.get("ZRFZJLB"));
        result.put("ZRFZJLB_C", zjlxFormmat(flowVars.get("ZRFZJLB").toString()));
        result.put("ZRFZJHM", flowVars.get("ZRFZJHM"));
        result.put("DLRXM", flowVars.get("DLRXM"));
        result.put("DLRZJLB", flowVars.get("DLRZJLB"));
        result.put("DLRZJLB_C", zjlxFormmat(flowVars.get("DLRZJLB").toString()));
        result.put("DLRZJHM", flowVars.get("DLRZJHM"));
        result.put("DLRSJHM", flowVars.get("DLRSJHM"));
        result.put("CLSCSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        result.put("CRRQZSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        result.put("MSRQZSJ", DateTimeUtil.dateToStr(new Date(), "yyyy年MM月dd日"));
        
        result.put("ZL", flowVars.get("ZL")==null?"":flowVars.get("ZL"));
        result.put("BDC_BDCDJZMH", flowVars.get("BDC_BDCDJZMH")==null?"":flowVars.get("BDC_BDCDJZMH"));
        result.put("LZRXM", flowVars.get("LZRXM")==null?"":flowVars.get("LZRXM"));
        result.put("LZRZJLB", zjlxFormmat(flowVars.get("LZRZJLB")==null?"":flowVars.get("LZRZJLB").toString()));
        result.put("LZRZJHM", flowVars.get("LZRZJHM")==null?"":flowVars.get("LZRZJHM"));
        result.put("LZRSJHM", flowVars.get("LZRSJHM")==null?"":flowVars.get("LZRSJHM"));
        
        return result;
    }
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月24日 下午5:28:32
     * @param zjlx
     * @return
     */
    private String zjlxFormmat(String zjlx){
        String fmtzjlx = "";
        if(zjlx.equals("身份证")){
            fmtzjlx = "IDCard";
        }else if(zjlx.equals("港澳居民来往内地通行证")){
            fmtzjlx = "HMPass";
        }else if(zjlx.equals("护照")){
            fmtzjlx = "Passport";
        }else if(zjlx.equals("台湾居民来往内地通行证")){
            fmtzjlx = "MTP";
        }else if(zjlx.equals("营业执照")||zjlx.equals("统一社会信用代码")){
            fmtzjlx = "SOCNO";
        }else if(zjlx.equals("组织机构代码")){
            fmtzjlx = "ORANO";
        }else{
            fmtzjlx = "Other";
        }
        return fmtzjlx;
    }
    
    /**
    * 
    * 描述 生成预购预告申请表
    * 
    * @author Roger Li
    * @created 2020年1月15日 上午9:51:57
    */
    public void generateSQB(Map<String, Object> returnMap,String path) {       
       FileInputStream in = null;
       FileOutputStream fos = null;
       try {
           String fileExt = path.substring(path.lastIndexOf(".") + 1);
           in = new FileInputStream(new File(path));
           CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
           List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();
           for (XWPFTableRow row : xwpfDocument.getTables().get(0).getRows()) {
               cells.addAll(row.getTableCells());
           }
           for (XWPFTableCell cell1 : cells) {
               String tag = cell1.getText().trim();
               List<XWPFParagraph> paragraphs = cell1.getParagraphs();
               WordRedrawUtil.processParagraphs(paragraphs, returnMap, xwpfDocument);
           }
           // 处理动态表格
           List<Map<String, Object>> equipList = (List<Map<String, Object>>) returnMap.get("QLR_LIST");
           Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
           while (it.hasNext()) {
               XWPFTable table = it.next();
               List<XWPFTableRow> rows = table.getRows();
               // table.addRow(rows.get(0));
               XWPFTableRow oldRow = null;
    
               WordReplaceUtil.addTableRow3(returnMap, table, rows, oldRow, equipList, "MSFXM");
               for (XWPFTableRow row : rows) {
                   List<XWPFTableCell> celllists = row.getTableCells();
                   for (XWPFTableCell cell : celllists) {
                       List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                       WordReplaceUtil.setTableRow(returnMap, xwpfDocument, celllists, paragraphListTable, equipList,
                               "MSFXM");
                       WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                   }
               }
           }
           
           String savePath = WordRedrawUtil.getSavePath(fileExt);
           fos = new FileOutputStream(savePath);
           xwpfDocument.write(fos);
           returnMap.put("bdcPath", savePath);
       } catch (Exception e) {
           // TODO
           log.error(e.getMessage(),e);
       } finally {
           try {
               if (in != null) {
                   in.close();
               }
               if (fos != null) {
                   fos.close();
               }
           } catch (IOException e) {
               // TODO Auto-generated catch block
               log.error(e.getMessage());
           }
       }
    }
  
    /**
     * 
     * 描述    通用文档生成
     * @author Danto Huang
     * @created 2020年8月24日 下午10:47:59
     * @param returnMap
     * @param path
     */
    public void generateWord(Map<String, Object> returnMap,String path) {
        FileInputStream in = null;
        FileOutputStream fos = null;
        try {
            String fileExt = path.substring(path.lastIndexOf(".") + 1);
            in = new FileInputStream(new File(path));
            CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
            // 处理段落
            List<XWPFParagraph> paragraphList = xwpfDocument.getParagraphs();
            WordReplaceUtil.processParagraphs(paragraphList, returnMap, xwpfDocument);
            Iterator<XWPFTable> it = xwpfDocument.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                        
                        WordReplaceUtil.processParagraphs(paragraphListTable, returnMap, xwpfDocument);
                    }
                }
            }
            
            String savePath = WordRedrawUtil.getSavePath(fileExt);
            fos = new FileOutputStream(savePath);
            xwpfDocument.write(fos);
            returnMap.put("bdcPath", savePath);
        } catch (Exception e) {
            // TODO
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
    }
    
    /**
     * 描述    平潭通--预购商品房预告登记业务网上预审-签章前置事件
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public Map<String, Object> ygspfygdjGenAndSignForPtt(Map<String,Object> flowVars)throws Exception{
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        String sqfs = StringUtil.getString(flowVars.get("SQFS"));//受理方式（1：网上申请 触发签章）
        String eflowIsback = StringUtil.getString(flowVars.get("EFLOW_ISBACK"));//退回不执行
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作（1是）
        if(StringUtils.isNotEmpty(exeId) && StringUtils.isNotEmpty(sqfs) && "1".equals(sqfs)
                &&StringUtils.isEmpty(eflowIsback)&&!"1".equals(eflowIstempsave)){
            Map<String, Object> flowExe = this.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            if (flowExe == null) {
                flowExe = this.getByJdbc("JBPM6_EXECUTION_EVEHIS", new String[] { "EXE_ID" },
                        new Object[] {exeId});
            }
            Map<String, Object> busRecord = exeDataService.getBuscordMap(exeId);
            String pttSqfs = StringUtil.getValue(flowExe, "PTT_SQFS");//平潭通申报方式（1智能审批 2全程网办）
            if(StringUtils.isNotEmpty(pttSqfs) && "1".equals(pttSqfs)){
                flowVars.putAll(busRecord);
                genAndSign(flowVars); 
            }
        }
        return flowVars;
    }
    
    /**
     * 描述    预购商品房预告登记业务（材料生成及签章）
     * @author Allin Lin
     * @created 2020年8月24日 上午10:19:24
     * @param flowVars
     * @return
     */
    public void genAndSign(Map<String,Object> flowVars)throws Exception{
        Map<String, Object> returnMap ;
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));//申报号
        /*合同类型（HTLX）*/
        String htlx =StringUtil.getString( flowVars.get("HTLX"));
        //1、材料生成PDF
        /*材料业务数据回填*/
        returnMap = initGenValue(flowVars);
        /*材料替换字符串&生成PDF文件&签章流程*/
        if("现售合同".equals(htlx) || "预售合同".equals(htlx)){//商品房              
            bdcQlcMaterGenAndSignService.ygSqbGenPdf(BdcQlcMaterGenAndSignService.YGSQS_KEY,
                    BdcQlcCreateSignFlowService.YGSQS_MATERNAME,exeId,returnMap); //预告申请表-生成PDF 
            bdcQlcMaterGenAndSignService.ygCommonGenPdf(BdcQlcMaterGenAndSignService.YGSPF_KEY,
                    BdcQlcCreateSignFlowService.YGSPF_MATERNAME,exeId,returnMap);//商品房预告约定书-生成PDF 
            bdcQlcMaterGenAndSignService.ygSqbSign(flowVars,returnMap);//预告申请表-签章 
            if((boolean)flowVars.get("SIGN_FLAG")){
                bdcQlcMaterGenAndSignService.ygYdsSign(flowVars,returnMap);//商品房预告约定书-签章
            }               
        }
        if("拆迁安置协议".equals(htlx)){//安置房
            bdcQlcMaterGenAndSignService.ygSqbGenPdf(BdcQlcMaterGenAndSignService.YGSQS_KEY,
                    BdcQlcCreateSignFlowService.YGSQS_MATERNAME,exeId,returnMap); //预告申请表-生成PDF 
            bdcQlcMaterGenAndSignService.ygCommonGenPdf(BdcQlcMaterGenAndSignService.YGAZF_KEY,
                    BdcQlcCreateSignFlowService.YGAZF_MATERNAME,exeId,returnMap);//安置房预告约定书-生成PDF              
            bdcQlcMaterGenAndSignService.ygSqbSign(flowVars,returnMap);//预告申请表-签章 
            if((boolean)flowVars.get("SIGN_FLAG")){
                bdcQlcMaterGenAndSignService.ygYdsSign(flowVars,returnMap);//安置房预告约定书-签章
            }
        }   
    }
}
