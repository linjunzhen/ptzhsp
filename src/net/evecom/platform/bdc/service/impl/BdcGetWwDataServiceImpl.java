/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.dao.BdcGetWwDataDao;
import net.evecom.platform.bdc.service.BdcGetWwDataService;
import net.evecom.platform.bdc.service.BdcQueryService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 描述:不动产获取省外网数据
 *
 * @author Madison You
 * @created 2020/04/15 11:19
 */
@Service("bdcGetWwDataService")
public class BdcGetWwDataServiceImpl extends BaseServiceImpl implements BdcGetWwDataService {
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcGetWwDataServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:26:00
     * @param
     * @return
     */
    @Resource
    private BdcGetWwDataDao bdcGetWwDataDao;
    /**
     * 
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * 
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 
     */
    @Resource
    private BdcQueryService bdcQueryService;
    /**
     * 
     */
    @Resource
    private JbpmService jbpmService;


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 11:26:00
     * @param
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected GenericDao getEntityDao() {
        return bdcGetWwDataDao;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 15:06:00
     * @param
     * @return
     */
    private final String CLZB = "材料子表.xls";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 15:06:00
     * @param
     * @return
     */
    private final String FSDYZB = "附属单元子表.xls";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 15:06:00
     * @param
     * @return
     */
    private final String SCWJMXB = "上传文件明细表.xls";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 15:06:00
     * @param
     * @return
     */
    private final String SQB= "申请表.xls";
    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/15 15:06:00
     * @param
     * @return
     */
    private final String SQRZB= "申请人子表.xls";


    /**
     * 描述:不动产获取省外网数据
     *
     * @author Madison You
     * @created 2020/4/15 11:33:00
     * @param
     * @return
     */
    @Override
    public void bdcGetWwData(Log log) {
        /*获取不动产FTP配置文件*/
        Properties properties = FileUtil.readProperties("project.properties");
        String bdcFtpHost = properties.getProperty("BDC_FTP_HOST");
        String bdcFtpPort = properties.getProperty("BDC_FTP_PORT");
        String bdcFtpUserName = properties.getProperty("BDC_FTP_USERNAME");
        String bdcFtpPassword = properties.getProperty("BDC_FTP_PASSWORD");
        String bdcFtpFtppath = properties.getProperty("BDC_FTP_FTPPATH");
        String bdcFtpLocalpath = properties.getProperty("BDC_FTP_LOCALPATH");
        String bdcUnZipPath = properties.getProperty("BDC_UNZIP_PATH");
        String bdcFtpRenamepath = properties.getProperty("BDC_FTP_RENAMEPATH");
        String bdcFileUploadPath = properties.getProperty("BDC_FILE_UPLOADPATH");
        int bdcFtpPortParse = Integer.parseInt(bdcFtpPort);
        /*批量下载不动产FTP文件*/
        List<String> fileNameList = FtpUtils.downloadListFtpFile(bdcFtpHost, bdcFtpPortParse, bdcFtpUserName,
                bdcFtpPassword, bdcFtpFtppath, bdcFtpLocalpath, 20, ".zip");
        /*批量移动不动产FTP文件*/
        if (fileNameList != null && !fileNameList.isEmpty()) {
            log.info("成功下载不动产FTP文件：" + fileNameList);
            boolean isRemove = FtpUtils.renameListFtpFile(bdcFtpHost, bdcFtpPortParse, bdcFtpUserName,
                    bdcFtpPassword, bdcFtpFtppath, bdcFtpRenamepath, fileNameList);
            if (isRemove) {
                log.info("成功移动不动产FTP文件");
            }
            /*解压FTP文件*/
            for (String fileName : fileNameList) {
                String zipFilePath = bdcFtpLocalpath + File.separatorChar + fileName;  //zip文件存放位置
                String unZipFilePath = bdcUnZipPath;  //解压文件存放位置
                ZipUtil.unzipNew(zipFilePath, unZipFilePath);
            }
            /*解析材料*/
            for (String fileName : fileNameList) {
                String fileNameLeft = fileName.split("\\.")[0];
                String leftPath = bdcUnZipPath + File.separatorChar + fileNameLeft + File.separatorChar;
                /*保存申请表数据*/
                saveExcelData(leftPath + SQB, "T_BDCQLC_WWSJ_SQB");
                saveExcelData(leftPath + FSDYZB, "T_BDCQLC_WWSJ_FSDYZB");
                List<Map<String, Object>> scwjmxbList = saveExcelData(leftPath + SCWJMXB, "T_BDCQLC_WWSJ_SCWJMXB");
                saveExcelData(leftPath + SQRZB, "T_BDCQLC_WWSJ_SQRZB");
                saveExcelData(leftPath + CLZB, "T_BDCQLC_WWSJ_CLZB");
                /*上传附件*/
                uploadFile(scwjmxbList, leftPath, bdcFileUploadPath);
            }
        }
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/16 18:11:00
     * @param 
     * @return 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBdcWwClList(String bdcWwsqbh) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select b.clmc,b.jzlx,b.FS,b.bz,c.XSMC,c.ZSMC,c.FILE_ID ");
        sql.append(" from T_BDCQLC_WWSJ_SQB a left join T_BDCQLC_WWSJ_CLZB b on a.ID = b.ID ");
        sql.append(" left join T_BDCQLC_WWSJ_SCWJMXB c on b.iid = c.ID ");
        sql.append(" where SLBH = ? ");
        params.add(bdcWwsqbh);
        List<Map<String,Object>> list = bdcGetWwDataDao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:获取申请人子表数据
     *
     * @author Madison You
     * @created 2020/4/20 8:53:00
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getSqrzbData(String bdcWwsqbh) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" select b.* from t_bdcqlc_wwsj_sqb a left join t_bdcqlc_wwsj_sqrzb b on a.id = b.id ");
        sql.append(" where a.slbh = ? ");
        params.add(bdcWwsqbh);
        List<Map<String,Object>> list = bdcGetWwDataDao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:上传附件
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/4/16 9:04:00
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void uploadFile(List<Map<String, Object>> scwjmxbList, String leftPath, String bdcFileUploadPath) {
        if (scwjmxbList != null && !scwjmxbList.isEmpty()) {
            for (Map<String, Object> scwjmxbMap : scwjmxbList) {
                String fjlj = (String) scwjmxbMap.get("FJLJ");
                String zsmc = (String) scwjmxbMap.get("ZSMC");
                String ywId = (String) scwjmxbMap.get("YW_ID");
                String filesPath = leftPath + "files" + File.separatorChar + fjlj + File.separatorChar + zsmc;
                StringBuffer params = new StringBuffer();
                params.append("grantcode=BFbhXSKZXvjii1bMaLWg&uploadUserId=402883524863ab1f014863ab1f340000&");
                params.append("uploadUserName=bdcWwsj&attachKey=bdcWwsj&busTableName=bdcWwsj");
                String result = HttpRequestUtil.sendFilePost(bdcFileUploadPath, params.toString(), filesPath, "UTF-8");
                Map<String, Object> respMap = (Map) JSON.parse(result);
                boolean isSuccess = Boolean.parseBoolean(StringUtil.getValue(respMap, "success"));
                if (isSuccess) {
                    HashMap<String, Object> variables = new HashMap<>();
                    variables.put("FILE_ID", respMap.get("fileId"));
                    bdcGetWwDataDao.saveOrUpdate(variables, "T_BDCQLC_WWSJ_SCWJMXB", ywId);
                }
            }
        }
    }

    /**
     * 描述:保存excel数据
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/4/15 16:51:00
     */
    @SuppressWarnings("unchecked")
    private List<Map<String,Object>> saveExcelData(String excelFilePath, String tableName) {
        List<Map<String,Object>> arrayList = new ArrayList<>();
        List<List<Object>> excelRowValues = ExcelUtil.getExcelRowValues(excelFilePath, 0, 0);
        List<Object> excelRowNames = excelRowValues.get(0);
        for (int i = 1; i < excelRowValues.size(); i++) {
            HashMap<String, Object> variables = new HashMap<>();
            for (int j = 0; j < excelRowNames.size(); j++) {
                variables.put(excelRowNames.get(j).toString(), excelRowValues.get(i).get(j));
            }
            String unid = getUNID();
            bdcGetWwDataDao.saveOrUpdateForAssignId(variables, tableName, unid);
            variables.put("YW_ID", unid);
            arrayList.add(variables);
        }
        return arrayList;
    }

    /**
     * 描述:生成数据库UUID
     *
     * @author Madison You
     * @created 2020/4/16 10:32:00
     * @param
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getUNID() {
        Map m = bdcGetWwDataDao.getByJdbc("select RAWTOHEX(sys_guid()) as unid from dual ", new Object[] {});
        return (String) m.get("unid");
    }
    
    /**
     * 
     * 描述    获取待启动流程数据
     * @author Danto Huang
     * @created 2020年8月17日 上午9:49:40
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findNeedStart(){
        String sql = " select * from T_BDCQLC_WWSJ_SQB where STATUS = 0 and "
                + "QLLX in (select t.dic_name from T_MSJW_SYSTEM_DICTIONARY t where t.type_code='qllxtoitem')";
        List<Map<String,Object>> list = bdcGetWwDataDao.findBySql(sql, null, null);
        return list;
    }

    /**
     * 
     * 描述    创建办件流程
     * @author Danto Huang
     * @created 2020年8月17日 上午9:46:05
     * @param wwData
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public Map<String,Object> startWwDataFlow(Map<String,Object> wwData) throws Exception{
        log.info("开始不动产全流程外网申请流程启动："+wwData.get("ID"));
        Map<String,Object> flowVars = new HashMap<String,Object>();
        flowVars.put("EFLOW_CREATORACCOUNT","admin");
        flowVars.put("EFLOW_CREATORNAME", "不动产全流程外网申请");
        
        String itemName = wwData.get("QLLX").toString();        
        String itemCode = dictionaryService.getDicCode("qllxtoitem", itemName);
        Map<String, Object> item = bdcGetWwDataDao.getByJdbc("T_WSBS_SERVICEITEM", new String[]{"ITEM_CODE"}, new Object[]{itemCode});
        Map<String,Object> def = bdcGetWwDataDao.getByJdbc("JBPM6_FLOWDEF", new String[]{"DEF_ID"}, new Object[]{item.get("BDLCDYID")});
        Map<String,Object> form = bdcGetWwDataDao.getByJdbc("JBPM6_FLOWFORM", new String[]{"FORM_ID"}, new Object[]{def.get("BIND_FORMID")});

        flowVars.put("EFLOW_DEFKEY", def.get("DEF_KEY"));
        flowVars.put("EFLOW_BUSTABLENAME", form.get("FORM_KEY"));
        flowVars.put("EFLOW_DEFID", def.get("DEF_ID"));
        flowVars.put("EFLOW_DEFVERSION",def.get("VERSION"));
        flowVars.put("EFLOW_ISQUERYDETAIL","false");
        flowVars.put("EFLOW_ISTEMPSAVE","-1");
        flowVars.put("ITEM_NAME", item.get("ITEM_NAME"));
        flowVars.put("ITEM_CODE", item.get("ITEM_CODE"));
        flowVars.put("SSBMBM", item.get("SSBMBM"));
        flowVars.put("SQFS","1");
        //获取当前运行节点
        String currentOperNodeName = flowNodeService.getNodeName(item.get("BDLCDYID").toString(), 
                ((BigDecimal)def.get("VERSION")).intValue(), Jbpm6Constants.START_NODE);
        flowVars.put("EFLOW_CUREXERUNNINGNODENAMES",currentOperNodeName);
        flowVars.put("EFLOW_CURUSEROPERNODENAME",currentOperNodeName);
        flowVars.put("isSendMessage", "notsend");
        
        if("预购商品房预告登记".equals(itemName)){
            ygspfygdjDataSet(flowVars,wwData);
        }else if("预购商品房抵押权预告登记".equals(itemName)){
            ygspfdyqygdjSetDataSet(flowVars,wwData);
        }
        
        //获取发起节点名称
        if(StringUtils.isNotEmpty(currentOperNodeName)){
            String nextStepJson = this.jbpmService.getNextStepsJson(def.get("DEF_ID").toString(), 
                    ((BigDecimal)def.get("VERSION")).intValue(),currentOperNodeName, flowVars);
            if(StringUtils.isNotEmpty(nextStepJson)){
                flowVars.put("EFLOW_NEXTSTEPSJSON", nextStepJson);
            }
        }

        Map<String,Object> resultMap = jbpmService.doFlowJob(flowVars);//更新状态为完成
        this.updateDataStatus(wwData.get("ID").toString(), 1);
        log.info("结束不动产全流程外网申请流程启动："+wwData.get("ID"));
        return resultMap;
    }
    

    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月17日 下午3:13:35
     * @param dataId
     * @param status
     */
    private void updateDataStatus(String dataId,int status){
        StringBuffer sql = new StringBuffer("update T_BDCQLC_WWSJ_SQB S ");
        sql.append(" SET S.STATUS=? WHERE S.ID=? ");
        bdcGetWwDataDao.executeSql(sql.toString(), new Object[]{status,dataId});
    }
    
    /**
     * 
     * 描述    预购商品房预告登记表单信息
     * @author Danto Huang
     * @created 2020年8月17日 下午3:13:43
     * @param flowVars
     * @param wwData
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void ygspfygdjDataSet(Map<String,Object> flowVars,Map<String,Object> wwData){
        String sql = "select * from T_BDCQLC_WWSJ_SQRZB where ID=? and RYLX=1";
        List<Map<String,Object>> list = bdcGetWwDataDao.findBySql(sql, new Object[]{wwData.get("ID")}, null);        
        StringBuffer applicantNAME = new StringBuffer("");
        List<Map<String,Object>> qlrList = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> sqr : list){
            applicantNAME.append(sqr.get("XM")).append(",");
            Map<String,Object> qlr = new HashMap<String,Object>();
            qlr.put("MSFXM", sqr.get("XM"));
            qlr.put("MSFZJHM", sqr.get("ZJHM"));
            qlr.put("MSFZJLB", zjlxFormat(sqr.get("ZJLX").toString()));
            qlrList.add(qlr);
        }
        flowVars.put("APPLICANT_UNIT", applicantNAME.substring(0, applicantNAME.length()-1));
        flowVars.put("QLR_JSON", JSON.toJSONString(qlrList));        
        
        flowVars.put("SBMC", flowVars.get("APPLICANT_UNIT")+"-"+flowVars.get("ITEM_NAME"));
        //定义标题
        flowVars.put("EFLOW_SUBJECT",flowVars.get("APPLICANT_UNIT")+"-"+flowVars.get("ITEM_NAME"));

        List<Map<String,Object>> baxxList = new ArrayList<Map<String,Object>>();
        baxxList.add(wwData);
        flowVars.put("HTBAXX_JSON", JSON.toJSONString(baxxList));
        if(null !=  wwData.get("TDYT")){
            flowVars.put("YTSM", wwData.get("TDYT"));
            flowVars.put("YTMS", wwData.get("TDYT"));
        } else {
            flowVars.put("YTSM", "住宅");
            flowVars.put("YTMS", "住宅");
        }
        flowVars.put("FWXZ", "市场化商品房");
        String bdcdyh = wwData.get("BDCDYH") == null ? "" : wwData.get("BDCDYH").toString();
        if(StringUtils.isNotEmpty(bdcdyh)){
            Map<String,Object > variables = new HashMap<String, Object>();
            variables.put("bdcdyh", bdcdyh);
            AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "fwdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                List<Map> fwxxList = JSON.parseArray(jsonString, Map.class);
                if (null != fwxxList && fwxxList.size() > 0) {
                    flowVars.put("YTSM", fwxxList.get(0).get("YTSM") == null || fwxxList.get(0).get("YTSM").equals("")
                            ? "住宅" : fwxxList.get(0).get("YTSM"));
                    flowVars.put("YTMS", fwxxList.get(0).get("YTSM") == null || fwxxList.get(0).get("YTSM").equals("")
                            ? "住宅" : fwxxList.get(0).get("YTSM"));
                    flowVars.put("FWXZ", fwxxList.get(0).get("FWXZ") == null || fwxxList.get(0).get("FWXZ").equals("")
                            ? "市场化商品房" : fwxxList.get(0).get("FWXZ"));
                }
            }
            flowVars.put("FWXX_JSON", jsonString);
        }
        flowVars.put("BDC_WWSQBH", wwData.get("SLBH"));
        flowVars.put("ZL", wwData.get("BDCZL"));
        flowVars.put("FJ", wwData.get("FJ"));
        flowVars.put("BDCDYH", wwData.get("BDCDYH"));
        flowVars.put("JZMJ", wwData.get("FWCQMJ"));
        flowVars.put("FWDZ", wwData.get("BDCZL"));
        flowVars.put("CJJG", wwData.get("QDJG"));
        flowVars.put("BDC_BDCDYH", wwData.get("BDCDYH"));
        flowVars.put("TAKECARD_TYPE", "1");
        flowVars.put("LZRZJLB", "身份证");
        flowVars.put("DLRZJLB", "身份证");
        flowVars.put("HTLX", "预售合同");
        flowVars.put("DJYY", "预购商品房预告登记");
        flowVars.put("FWMMHTH", wwData.get("MMHTH"));
        if(null != wwData.get("MMSFCJJ")){
            DecimalFormat dF = new DecimalFormat("0.000000");
            flowVars.put("CJJG", dF.format((float)Integer.valueOf(wwData.get("MMSFCJJ").toString())/10000));
        }
        
        sql = "select * from T_BDCQLC_WWSJ_SQRZB where ID=? and RYLX=2";
        list = bdcGetWwDataDao.findBySql(sql, new Object[]{wwData.get("ID")}, null);
        if(list!=null&&list.size()>0){
            flowVars.put("ZRFXM", list.get(0).get("XM"));
            flowVars.put("ZRFZJHM", list.get(0).get("ZJHM"));
            flowVars.put("ZRFZJLB", zjlxFormat(list.get(0).get("ZJLX").toString()));
        }
        
    }
    
    /**
     * 
     * 描述    预购商品房抵押权预告登记表单信息
     * @author Danto Huang
     * @created 2020年8月17日 下午4:40:47
     * @param flowVars
     * @param wwData
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void ygspfdyqygdjSetDataSet(Map<String,Object> flowVars,Map<String,Object> wwData){
        String sql = "select * from T_BDCQLC_WWSJ_SQRZB where ID=? and RYLX=1";
        List<Map<String,Object>> list = bdcGetWwDataDao.findBySql(sql, new Object[]{wwData.get("ID")}, null);        
       
        flowVars.put("QLR_MC", list.get(0).get("XM"));
        flowVars.put("QLR_ZJLX", zjlxFormat(list.get(0).get("ZJLX").toString()));
        flowVars.put("QLR_ZJNO", list.get(0).get("ZJHM"));
        flowVars.put("DJXX_QLR", list.get(0).get("XM"));
        sql = "select * from T_BDCQLC_WWSJ_SQRZB where ID=? and RYLX=2";
        list = bdcGetWwDataDao.findBySql(sql, new Object[]{wwData.get("ID")}, null);
        if(list!=null&&list.size()>0){
            List<Map<String,Object>> zrfList = new ArrayList<Map<String,Object>>();
            StringBuffer appicantName = new StringBuffer("");
            for(Map<String,Object> sqr : list){
                Map<String,Object> zrf = new HashMap<String,Object>();
                appicantName.append(sqr.get("XM")).append(",");
                zrf.put("YWR_MC", sqr.get("XM"));
                zrf.put("YWR_ZJNO", sqr.get("ZJHM"));
                zrf.put("YWR_ZJLX", zjlxFormat(sqr.get("ZJLX").toString()));
                zrfList.add(zrf);
            }
            flowVars.put("YWR_JSON", JSON.toJSONString(zrfList));
            flowVars.put("APPLICANT_UNIT", appicantName.substring(0, appicantName.length()-1));
        }
        
        flowVars.put("SBMC", flowVars.get("APPLICANT_UNIT")+"-"+flowVars.get("ITEM_NAME"));
        //定义标题
        flowVars.put("EFLOW_SUBJECT",flowVars.get("APPLICANT_UNIT")+"-"+flowVars.get("ITEM_NAME"));
        List<Map<String,Object>> ygxxList = new ArrayList<Map<String,Object>>();
        ygxxList.add(wwData);
        flowVars.put("YGXX_JSON", JSON.toJSONString(ygxxList));
        String bdcdyh = wwData.get("BDCDYH") == null ? "" : wwData.get("BDCDYH").toString();
        if (StringUtils.isNotEmpty(bdcdyh)) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("bdcdyh", bdcdyh);
            AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "fwdyxxUrl");
            String jsonString = ajaxJson.getJsonString();
            if (StringUtils.isNotEmpty(jsonString)) {
                List<Map> fwxxList = JSON.parseArray(jsonString, Map.class);
                if (null != fwxxList && fwxxList.size() > 0) {
                    flowVars.put("YTSM", fwxxList.get(0).get("YTSM") == null || fwxxList.get(0).get("YTSM").equals("")
                            ? "住宅" : fwxxList.get(0).get("YTSM"));
                    flowVars.put("FWXZ", fwxxList.get(0).get("FWXZ") == null || fwxxList.get(0).get("FWXZ").equals("")
                            ? "市场化商品房" : fwxxList.get(0).get("FWXZ"));
                }
            }
            flowVars.put("FWXX_JSON", jsonString);
        }
        flowVars.put("BDC_WWSQBH", wwData.get("SLBH"));
        String bdczl = wwData.get("BDCZL") == null ? "" : wwData.get("BDCZL").toString();
        flowVars.put("LOCATED", bdczl);
        flowVars.put("FW_ADDR", bdczl);
        flowVars.put("DJXX_ZL", bdczl);
        flowVars.put("BDCDYH", wwData.get("BDCDYH"));
        flowVars.put("DJXX_DYH", wwData.get("BDCDYH"));
        flowVars.put("ESTATE_NUM", wwData.get("BDCDYH"));
        flowVars.put("BDC_BDCDYH", wwData.get("BDCDYH"));
        if(null != wwData.get("ZZQSE")){
            DecimalFormat dF = new DecimalFormat("0.000000");
            flowVars.put("DBSE", dF.format((float)Integer.valueOf(wwData.get("ZZQSE").toString())/10000));
        }
        flowVars.put("HT_NO", wwData.get("MMHTH"));
        flowVars.put("DJXX_JZAREA", wwData.get("FWCQMJ"));
        if (wwData.get("ZQQSSJ")!=null) {
            String ZW_BEGIN = wwData.get("ZQQSSJ").toString();
            ZW_BEGIN = ZW_BEGIN.substring(0, 10).replace("/","-");
            flowVars.put("ZW_BEGIN", ZW_BEGIN);
        }
        if (wwData.get("ZQJSSJ")!=null) {
            String ZW_END = wwData.get("ZQJSSJ").toString();
            ZW_END = ZW_END.substring(0, 10).replace("/","-");
            flowVars.put("ZW_END", ZW_END);
        }
        flowVars.put("FJ_INFO", wwData.get("FJ"));
        flowVars.put("DJXX_JZAREA", wwData.get("FWCQMJ"));
        flowVars.put("TAKECARD_TYPE", "1");
        flowVars.put("HT_LX", "预售合同");
        flowVars.put("DJYY", "预购商品房抵押预告登记");
        
        if(StringUtils.isNotEmpty(bdcdyh)){
            Map<String,Object > variables = new HashMap<String, Object>();
            variables.put("bdcdyh", bdcdyh);
            AjaxJson ajaxJson = bdcQueryService.queryAjaxJsonOfBdc(variables, "announceUrl");
            String jsonString = ajaxJson.getJsonString();
            if(StringUtils.isNotEmpty(jsonString)){
                List<Map> ygdaList = JSON.parseArray(jsonString, Map.class);
                for(Map ygda : ygdaList){
                    if("预售商品房买卖预告登记".equals(ygda.get("YGDJZL"))){
                        flowVars.put("DJXX_CQZH", ygda.get("BDCDJZMH"));
                        flowVars.put("DJXX_DYH", ygda.get("BDCDYH"));
                        flowVars.put("DJXX_QLR", ygda.get("QLR"));
                        flowVars.put("DJXX_JZAREA", ygda.get("JZMJ"));
                        flowVars.put("DJXX_ZL", ygda.get("BDCZL"));
                        //flowVars.put("TDSYQR", ygda.get("YWR"));
                        if(StringUtils.isNotEmpty(bdczl)){ 
                            flowVars.put("LOCATED", ygda.get("BDCZL"));
                            flowVars.put("FW_ADDR", ygda.get("BDCZL"));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     * 描述    
     * @author Danto Huang
     * @created 2020年8月17日 下午4:39:49
     * @param type
     * @return
     */
    private String zjlxFormat(String type){
        String zjlx = "";
        if(type.equals("1")){
            zjlx = "身份证";
        }else if(type.equals("2")){
            zjlx = "港澳台身份证";
        }else if(type.equals("3")){
            zjlx = "护照";
        }else if(type.equals("4")){
            zjlx = "户口簿";
        }else if(type.equals("5")){
            zjlx = "军官证（士兵证）";
        }else if(type.equals("6")){
            zjlx = "组织机构代码";
        }else if(type.equals("7")){
            zjlx = "营业执照";
        }else if(type.equals("8")){
            zjlx = "港澳通行证";
        }else if(type.equals("9")){
            zjlx = "台胞证";
        }else if(type.equals("10")){
            zjlx = "统一社会信用代码";
        }else if(type.equals("99")){
            zjlx = "其它";
        }else{
            zjlx = "身份证";
        }
        return zjlx;
    }
}
