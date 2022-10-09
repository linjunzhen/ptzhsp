/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import net.evecom.core.util.QrcodeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.util.BdcDataSecretUtil;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.tyjk.service.FlowWebService;
import net.evecom.platform.wsbs.service.SwbInterfaceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.platform.bdc.service.BdcQslyshbsyService;
import net.evecom.platform.bsfw.dao.BdcApplyDao;
import net.evecom.platform.bsfw.service.BdcApplyService;
import net.evecom.platform.bsfw.service.BusApplyService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowTaskService;

/**
 * 描述 不动产业务
 * @author Keravon Feng
 * @created 2018年11月23日 下午4:08:13
 */
@Service("bdcApplyService")
public class BdcApplyServiceImpl extends BaseServiceImpl implements BdcApplyService {
    
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;

    /**
     * dao
     */
    @Resource
    private BdcApplyDao dao;
    /**
     * busApplyService
     */
    @Resource
    private BusApplyService busApplyService;
    
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    
    /**
     * bdcQslyshbsyService
     */
    @Resource
    private BdcQslyshbsyService  bdcQslyshbsyService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/13 16:09:00
     * @param
     * @return
     */
    @Resource
    private FlowWebService flowWebService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/28 15:39:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 系统附件存放路径
     */
    private static String attachFilePath = "";

    /**
     * 接口的key
     */
    private static String guid = "40288b9f555926ed01555d9e4dab192f";

    /**
     * 描述:不动产部门ID
     *
     * @author Madison You
     * @created 2020/10/26 15:43:00
     * @param
     * @return
     */
    private final String BDC_DEPART_ID = "4028013273d87d9b0173dd306ec7582b";
    
    /**
     * 静态块获取
     */
    static {
        Properties properties = FileUtil.readProperties("project.properties");
        attachFilePath = properties.getProperty("AttachFilePath");        
    }
    
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * log
     */
    private static Log log = LogFactory.getLog(BdcApplyServiceImpl.class);
    
    /**
     * 描述 不动产业务后置事件
     * @author Keravon Feng
     * @created 2018年9月19日 上午9:00:14
     * @param flowDatas
     * @return
     */
    public Map<String, Object> saveAfterBusData(Map<String, Object> flowDatas) {
         //退回流程，不执行
         String isback = (String)flowDatas.get("EFLOW_ISBACK");
         if(!"true".equals(isback)){
             //当前节点名称
             String currNodeName = (String) flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
             //审批件扫描件
             String submitmaterfilejson = (String) flowDatas.get("EFLOW_SUBMITMATERFILEJSON");
             if(StringUtils.isNotEmpty(submitmaterfilejson)){
                 List<Map> maters = JSON.parseArray(submitmaterfilejson, Map.class);
                 String sql = " SELECT TRANSID FROM T_WSBS_FILETRANS WHERE FILE_ID = ? ";
                 if(maters != null && maters.size() > 0){
                     for(Map map : maters){
                         String fileId = String.valueOf(map.get("FILE_ID"));
                         Map<String,Object> filetemp = dao.getByJdbc(sql,new Object[]{fileId});
                         if(filetemp == null){
                             /*Map<String,Object> file = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", 
                                     new String[]{"FILE_ID"}, new Object[]{fileId});
                             if(file != null){
                                 String file_path = String.valueOf(file.get("FILE_PATH"));
                                 String file_txt = FileUtil.convertFileToString(attachFilePath+file_path);
                                 Map<String,Object> fileInfo = new HashMap<String, Object>();
                                 fileInfo.put("FILE_ID", fileId);
                                 fileInfo.put("FILE_PATH", file_path);
                                 fileInfo.put("FILE_TXT", file_txt);
                                 fileInfo.put("PLAT_TYPE", "1");
                                 fileInfo.put("PARSE_STATUS", "0");
                                 dao.saveOrUpdate(fileInfo, "T_WSBS_FILETRANS", null);
                             }*/
                         }                         
                     }
                 }
             }
             /*String dyqrxxFileId = flowDatas.get("DYQRXX_FILE_ID")==null?
                     "":flowDatas.get("DYQRXX_FILE_ID").toString();
             hangRXXX(dyqrxxFileId);
             String dyqdjFileId = flowDatas.get("DYQDJ_FILE_ID")==null?
                     "":flowDatas.get("DYQDJ_FILE_ID").toString();
             hangRXXX(dyqdjFileId);
             String ygygFileId = flowDatas.get("YGYG_FILE_ID")==null?
                     "":flowDatas.get("YGYG_FILE_ID").toString();
             hangRXXX(ygygFileId);
             String ysdyqygFileId = flowDatas.get("YSDYQYG_FILE_ID")==null?
                     "":flowDatas.get("YSDYQYG_FILE_ID").toString();
             hangRXXX(ysdyqygFileId);
             String slxxFileId = flowDatas.get("SLXX_FILE_ID")==null?
                     "":flowDatas.get("SLXX_FILE_ID").toString();
             hangRXXX(slxxFileId);
             String qlrFileId = flowDatas.get("QLR_FILE_ID")==null?
                     "":flowDatas.get("QLR_FILE_ID").toString();
             hangRXXX(qlrFileId);
             String legalFileId = flowDatas.get("LEGAL_FILE_ID")==null?
                     "":flowDatas.get("LEGAL_FILE_ID").toString();
             hangRXXX(legalFileId);
             String powagentFileId = flowDatas.get("POWAGENT_FILE_ID")==null?
                     "":flowDatas.get("POWAGENT_FILE_ID").toString();
             hangRXXX(powagentFileId);
             String frdbFileId = flowDatas.get("FRDB_FILE_ID")==null?
                     "":flowDatas.get("FRDB_FILE_ID").toString();
             hangRXXX(frdbFileId);
             String dlrFileId = flowDatas.get("DLR_FILE_ID")==null?
                     "":flowDatas.get("DLR_FILE_ID").toString();
             hangRXXX(dlrFileId);*/
             if("开始".equals(currNodeName)){
                 //如果是窗口取号的事项，要把取号信息与办件进行关联
                 busApplyService.start(flowDatas);
             }
        }
        return flowDatas;
    }

    private void hangRXXX(String ids) {
//        System.out.println("==========================处理人像信息开始=====================");
//        System.out.println("1232312====="+ids);
        if (StringUtils.isNotEmpty(ids)) {
            String[] id = ids.split(";");
            for (int i = 0; i < id.length; i++) {
                String fileId = id[i];
                String sql = " SELECT TRANSID FROM T_WSBS_FILETRANS WHERE FILE_ID = ? ";
                Map<String,Object> filetemp = dao.getByJdbc(sql,new Object[]{fileId});
                if(filetemp == null){
                    Map<String,Object> file = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", 
                            new String[]{"FILE_ID"}, new Object[]{fileId});
                    if(file != null){
                        String file_path = String.valueOf(file.get("FILE_PATH"));
                        String file_txt = FileUtil.convertFileToString(attachFilePath+file_path);
                        Map<String,Object> fileInfo = new HashMap<String, Object>();
                        fileInfo.put("FILE_ID", fileId);
                        fileInfo.put("FILE_PATH", file_path);
                        fileInfo.put("FILE_TXT", file_txt);
                        fileInfo.put("PLAT_TYPE", "1");
                        fileInfo.put("PARSE_STATUS", "0");
                        dao.saveOrUpdate(fileInfo, "T_WSBS_FILETRANS", null);
                    }
                }                         
            }
        }
//        System.out.println("==========================处理人像信息结束=====================");
    }

    /**
     * 描述 换发不动产权证书和不动产登记证明保存事件（多表）
     * @author Keravon Feng
     * @created 2019年3月18日 下午4:34:18
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object> saveBusData(Map<String, Object> flowDatas) {
        //当前节点名称
        String currNodeName = (String) flowDatas.get("EFLOW_CUREXERUNNINGNODENAMES");
        //调用默认的保存方法
        flowEventService.saveBusData(flowDatas);
        if("开始".equals(currNodeName)){
            if(flowDatas.get("EFLOW_BUSRECORDID") != null){
                String busRecordId = (String)flowDatas.get("EFLOW_BUSRECORDID");
                String type = String.valueOf(flowDatas.get("HF_TYPE"));
                String tableName = "";
                if("1".equals(type)){
                    tableName = "T_BDC_CQZ_HFDJ";
                }else if("2".equals(type)){
                    tableName = "T_BDC_HFDYQZMDJ";
                }else if("3".equals(type)){
                    tableName = "T_BDC_HFYGDJZM";
                }else if("4".equals(type)){
                    tableName = "T_BDC_DYCYG_HFDJ";
                }
                this.remove(tableName, "BDC_SUB_YWID", new Object[] { busRecordId });
                flowDatas.put("BDC_SUB_YWID", busRecordId);
                this.saveOrUpdate(flowDatas, tableName, null);
            }
        }
        return flowDatas;
    }

    /**
     * 描述 根据类别及业务主表ID获取业务数据
     * @author Keravon Feng
     * @created 2019年3月19日 下午1:21:20
     * @param type
     * @param ywId
     * @return
     */
    @Override
    public Map<String, Object> getSubRecordInfo(String type, String ywId) {
        Map<String,Object> record = null;
        String tableName = "";
        if("1".equals(type)){
            tableName = "T_BDC_CQZ_HFDJ";
        }else if("2".equals(type)){
            tableName = "T_BDC_HFDYQZMDJ";
        }else if("3".equals(type)){
            tableName = "T_BDC_HFYGDJZM";
        }else if("4".equals(type)){
            tableName = "T_BDC_DYCYG_HFDJ";
        }
        record = this.getByJdbc(tableName, 
                new String[]{"BDC_SUB_YWID"}, new Object[]{ywId});
        if(record != null){
            for(Map.Entry<String,Object> ent : record.entrySet()){
                if(ent.getKey().endsWith("_JSON")){
                    if(ent.getValue() != null){
                        if(ent.getValue() != null){
                            String value = String.valueOf(ent.getValue()).replace("\"", "\'");
                            record.put(ent.getKey(), value);
                        }
                    }
                }
            } 
        }
        return record;
    }

    /**
     * 描述 判断字典的名称是否存在
     * @author Keravon Feng
     * @created 2019年3月22日 下午5:25:29
     * @param typeId
     * @param dicName
     * @return
     */
    @Override
    public boolean isExist(String typeId, String dicName) {
        return dao.isExist(typeId, dicName);
    }

    /**
     * 描述:增加时间
     *
     * @author Madison You
     * @created 2019/11/28 10:54:00
     * @param
     * @return
     */
    public String addDateStr(String dateStr , int addTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = "";
        try {
            if (!dateStr.equals("")) {
                Date parse = sdf.parse(dateStr);
                Date date = new Date(parse.getTime() + addTime);
                format = sdf.format(date);
            }
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        return format;
    }

    /**
     * 描述:不动产登记资料查询办结环节自动跳转
     *
     * @author Madison You
     * @created 2020/2/13 14:23:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findNeedAutoJumpBdcdjzlcx() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.EXE_ID,T.DEF_ID,T.DEF_VERSION,T.CUR_STEPNAMES ");
        sql.append(" ,TA.CREATE_TIME,D.DEF_KEY,T.ITEM_CODE,TA.TASK_ID ");
        sql.append(" from JBPM6_EXECUTION T LEFT JOIN JBPM6_FLOWDEF D ");
        sql.append(" ON T.DEF_ID=D.DEF_ID ");
        sql.append(" LEFT JOIN JBPM6_TASK TA ON TA.EXE_ID=T.EXE_ID ");
        sql.append(" WHERE T.CUR_STEPNAMES=? AND TA.TASK_NODENAME=? and TA.IS_AUDITED=1 ");
        sql.append(" AND D.DEF_KEY = 'T_BDC_DATAQUERY' and TA.TASK_STATUS=1 ");
        sql.append(" ORDER BY TA.CREATE_TIME ASC ");
        List<Map<String, Object>> datas = dao.findBySql(sql.toString(), new Object[]{"办结", "办结"}, null);
        return datas;
    }

    /**
     * 描述:不动产登记资料查询办结环节自动跳转
     *
     * @author Madison You
     * @created 2020/2/13 14:36:00
     * @param
     * @return
     */
    public void jumpTaskForBdcdjzlcx(Map<String,Object> data) throws Exception {
        JSONObject flowInfoJosn = new JSONObject();
        flowInfoJosn.put("RESULT_FILE_URL", "");
        flowInfoJosn.put("RESULT_FILE_ID", "");
        flowInfoJosn.put("CUR_NODE", "办结");
        flowInfoJosn.put("RUN_MODE", "");
        flowInfoJosn.put("SDCONTENT", "");
        flowInfoJosn.put("XKCONTENT", "");
        flowInfoJosn.put("ISLONG_TERM", "");
        flowInfoJosn.put("CLOSE_TIME", "");
        flowInfoJosn.put("EFFECT_TIME", "");
        flowInfoJosn.put("XKDEPT_ID", "");
        flowInfoJosn.put("XKDEPT_NAME", "");
        flowInfoJosn.put("XKFILE_NAME", "");
        flowInfoJosn.put("XKFILE_NUM", "");
        flowInfoJosn.put("EFLOW_HANDLE_OPINION", "办结环节自动跳转");
        flowInfoJosn.put("ASSIGNER_NAME", "自动跳转");
        flowInfoJosn.put("ASSIGNER_CODE", "ssyshz");
        flowInfoJosn.put("TASK_NODENAME", "办结");
        flowInfoJosn.put("itemCode", data.get("ITEM_CODE"));
        flowInfoJosn.put("EXE_ID", data.get("EXE_ID"));
        flowWebService.flowExecute(guid, flowInfoJosn.toJSONString());
    }

    /**
     * 描述:获取不动产查询附件url
     *
     * @author Madison You
     * @created 2020/7/21 20:35:00
     * @param
     * @return
     */
    @Override
    public String getBdcImgFullUrl(String fileId) {
        String fileFullUrl = "";
        Map<String, Object> fileMap = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"}, new Object[]{fileId});
        if (fileMap != null) {
            Properties properties = FileUtil.readProperties("project.properties");
            String uploadFileUrl = properties.getProperty("uploadFileUrl");
            String fileUrl = StringUtil.getValue(fileMap, "FILE_PATH");
            if (StringUtils.isNotEmpty(fileUrl)) {
                fileFullUrl = uploadFileUrl + fileUrl;
            }
        }
        return fileFullUrl;
    }

    /**
     * 描述:在word文档中生成二维码
     *
     * @author Madison You
     * @created 2020/7/21 21:44:00
     * @param
     * @return
     */
    @Override
    public void createBdcQueryQrcode(Map<String, Object> busInfo, Map<String, Object> returnMap , int type) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> qrImg = new HashMap<>();
        if (type == 1) {
            String name = StringUtil.getValue(busInfo, "QLR");
            String idCard = StringUtil.getValue(busInfo, "ZJH");
            String unitNumber = StringUtil.getValue(busInfo, "DYH");
            String propertyNumber = StringUtil.getValue(busInfo, "QZH");
            jsonObject.put("name", name);
            jsonObject.put("idCard", idCard);
            jsonObject.put("unitNumber", unitNumber);
            jsonObject.put("propertyNumber", propertyNumber);
        } else if (type == 2) {
            String name = StringUtil.getValue(busInfo, "NAME");
            String idCard = StringUtil.getValue(busInfo, "ZJHM");
            jsonObject.put("name", name);
            jsonObject.put("idCard", idCard);
        }
        String secretStr = BdcDataSecretUtil.encryptToBase64(jsonObject.toJSONString(), BdcDataSecretUtil.KEY);
        JSONObject body = new JSONObject();
        body.put("type", "real_estate_inquiry");
        body.put("appId", "02");
        body.put("data", secretStr);
        QrcodeUtil.createBosQrcode(body.toJSONString(), "png", 15, bos);
        qrImg.put("width", 120);
        qrImg.put("height", 120);
        qrImg.put("type", "png");
        qrImg.put("content", bos.toByteArray());
        returnMap.put("bdcQrCode", qrImg);
    }

    /**
     * 描述:在word文档中插入图片
     *
     * @author Madison You
     * @created 2020/7/21 22:02:00
     * @param
     * @return
     */
    @Override
    public void fillBdcQueryImg(Map<String, Object> returnMap, String fileId , String imgName) {
        if (StringUtils.isNotEmpty(fileId)) {
            Map<String, Object> fileMap = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"}, new Object[]{fileId});
            if (fileMap != null) {
                Properties properties = FileUtil.readProperties("project.properties");
                String uploadFileUrl = properties.getProperty("uploadFileUrl");
                String fileUrl = StringUtil.getValue(fileMap, "FILE_PATH");
                if (StringUtils.isNotEmpty(fileUrl)) {
                    Map<String,Object> bdcQueryImg = new HashMap<>();
                    String fileFullUrl = uploadFileUrl + fileUrl;
                    InputStream inStream = null;
                    InputStream inStreamRead = null;
                    try{
                        URL url = new URL(fileFullUrl);
                        inStream = url.openConnection().getInputStream();
                        inStreamRead = url.openConnection().getInputStream();
                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[2048];
                        int len = 0;
                        while( (len=inStream.read(buffer)) != -1 ){
                            outStream.write(buffer, 0, len);
                        }
                        /*获取图片长宽*/
                        BufferedImage imgRead = ImageIO.read(inStreamRead);
                        int height = imgRead.getHeight();
                        int width = imgRead.getWidth();
                        inStream.close();
                        inStreamRead.close();
                        byte[] data =  outStream.toByteArray();
                        bdcQueryImg.put("width", 600);
                        bdcQueryImg.put("height", 600 * height / width);
                        bdcQueryImg.put("type", "jpg");
                        bdcQueryImg.put("content", data);
                        returnMap.put(imgName, bdcQueryImg);
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    } finally {
                        if (inStream != null) {
                            try{
                                inStream.close();
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                        if (inStreamRead != null) {
                            try{
                                inStreamRead.close();
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    /**
     * 描述    并联审批是否通过（不动产权属来源审核表事宜）
     * @author Allin Lin
     * @created 2020年10月23日 下午4:22:13
     * @param flowVars
     * @return
     */
    public Set<String> getIsParallelAuditPass(Map<String, Object> flowVars){
        //String sql = "update jbpm6_execution t set t.apply_status=? where t.exe_id=?";
        boolean isPass = true;
        Set<String> resultNodes = new HashSet<String>();
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "TASK_ID" }, new Object[] { currentTaskId });
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String fromTaskId = currentTask.get("FROMTASK_IDS").toString();
        List<Map<String,Object>> fromTasks = flowTaskService.findFromtasks(fromTaskId, exeId);
        for(Map<String,Object> fromTask : fromTasks){
            if(fromTask.get("IS_PASS").equals("1")){
                continue;
            }else{
                isPass = false;
                break;
            }
        }
        if (!isPass) {
            //并审不通过，退回至受理环节，签章文件重置
           bdcQslyshbsyService.genSignFile(flowVars);
            resultNodes.add("受理");           
        } else {
            resultNodes.add("办结");
        }
        return resultNodes;
    }

    /**
     * 描述    并联审批是否通过（民宿办件旧）
     * @author Scolder Lin
     * @created 2021年6月1日 上午10:41:13
     * @param flowVars
     * @return
     */
    public Set<String> getIsBAndBPass(Map<String, Object> flowVars){
        boolean isPass = true;
        Set<String> resultNodes = new HashSet<String>();
        String currentTaskId = (String) flowVars.get("EFLOW_CURRENTTASK_ID");
        Map<String, Object> currentTask = this.getByJdbc("JBPM6_TASK",
                new String[] { "TASK_ID" }, new Object[] { currentTaskId });
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String fromTaskId = currentTask.get("FROMTASK_IDS").toString();
        List<Map<String,Object>> fromTasks = flowTaskService.findFromtasks(fromTaskId, exeId);
        for(Map<String,Object> fromTask : fromTasks){
            if(fromTask.get("IS_PASS").equals("1")){
                continue;
            }else{
                isPass = false;
                break;
            }
        }
        if (!isPass) {
            //并审不通过，退回至受理环节
            resultNodes.add("受理");           
        } else {
            resultNodes.add("审批");
        }
        return resultNodes;
    }

    /**
     * 描述:获取不动产在线监管总体情况
     *
     * @author Madison You
     * @created 2020/10/20 14:55:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getDjsjjgZtqkStatis(HttpServletRequest request) {
        /*查找近七天办件数量排名前七得事项*/
        Integer remain = 0;
        List<Map<String,Object>> ztqkList = new ArrayList<>();
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(1);
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from ( select count(t.EXE_ID) sum,x.item_name,x.item_code from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= ");
        sql.append(" to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss')  ");
        sql.append(" < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and t.RUN_STATUS <> 0 group by x.item_name,x.item_code order by sum desc ) where rownum <= 7 ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        /*查找近七天办件总数*/
        Integer count = getDjsjjgBsxnStatis("", "", "");
        if (list != null && !list.isEmpty()) {
            Map<String,Object> remainMap = new HashMap<>();
            Integer i = 0;
            for (Map<String, Object> map : list) {
                Map<String, Object> ztqkMap = new HashMap<>();
                Integer sum = Integer.parseInt(map.get("SUM").toString());
                remain += sum;
                i++;
                String itemName = map.get("ITEM_NAME").toString();
                String itemCode = map.get("ITEM_CODE").toString();
                ztqkMap.put("itemName", itemName);
                ztqkMap.put("itemCode", itemCode);
                ztqkMap.put("sum", nf.format(Double.valueOf(sum) / Double.valueOf(count)));
                ztqkMap.put("count", sum);
                ztqkList.add(ztqkMap);
            }
            if (i >= 7) {
                remainMap.put("itemName", "其他");
                remainMap.put("itemCode", "");
                remainMap.put("sum", nf.format((Double.valueOf(count) - Double.valueOf(remain)) / Double.valueOf(count)));
                remainMap.put("count", count - remain);
                ztqkList.add(remainMap);
            }
        }
        return ztqkList;
    }

    /**
     * 描述:获取不动产在线监管办事效能
     *
     * @author Madison You
     * @created 2020/10/22 17:08:00
     * @param
     * @return
     */
    @Override
    public Integer getDjsjjgBsxnStatis(String whereSql , String itemCode , String userName) {
        StringBuffer sql = new StringBuffer();
        Integer count = 0;
        sql.append(" SELECT count(*) SUM FROM JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" LEFT JOIN JBPM6_EFFICINFO D ON A.EXE_ID = D.EFLOW_EXEID ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_SYSUSER Y ON Y.USERNAME = A.CREATOR_ACCOUNT ");
        sql.append(" WHERE to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') <= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and B.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and A.RUN_STATUS <> 0 ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and B.item_code = '").append(itemCode).append("' ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and Y.USERNAME = '").append(userName).append("' ");
        }
        sql.append(whereSql);
        List<Map<String,Object>> listCount = dao.findBySql(sql.toString(), null, null);
        if (listCount != null && !listCount.isEmpty()) {
            count = Integer.parseInt(listCount.get(0).get("SUM").toString());
        }
        return count;
    }

    /**
     * 描述:获取不动产在线监管近七天受理量
     *
     * @param
     * @return
     * @author Madison You
     * @created 2020/10/23 9:37:00
     */
    @Override
    public List<Integer> getDjsjjgJqtsllStatis(ArrayList<String> pastWeekDate, String itemCode, String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(EXE_ID) SUM ,DAYTIME from ( select t.EXE_ID, substr(t.CREATE_TIME, 1, 10) DAYTIME ");
        sql.append(" from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER y on y.username = t.creator_account ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and x.item_code = '").append(itemCode).append("' ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and y.username = '").append(userName).append("' ");
        }
        sql.append(" and X.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and t.RUN_STATUS <> 0) group by DAYTIME order by DAYTIME ");
        List<Map<String, Object>> listCount = dao.findBySql(sql.toString(), null, null);
        List<Integer> countList = new ArrayList<>();
        if (listCount != null) {
            for (String passDate : pastWeekDate) {
                Integer count = 0;
                for (Map<String, Object> map : listCount) {
                    if (passDate.equals(map.get("DAYTIME"))) {
                        count = Integer.parseInt(map.get("SUM").toString());
                    }
                }
                countList.add(count);
            }
        }
        return countList;
    }

    /**
     * 描述:获取不动产在线监管近七天评价
     *
     * @author Madison You
     * @created 2020/10/23 14:23:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getDjsjjgFwpjStatis(HttpServletRequest request , String itemCode , String userName) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("fcmy", 0);
        countMap.put("my", 0);
        countMap.put("yb", 0);
        countMap.put("bmy", 0);
        countMap.put("wpj", 0);
        sql.append(" select count(t.EXE_ID) SUM, y.evaluate from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join t_ckbs_queuerecord y on t.exe_id = y.exe_id ");
        sql.append(" left join t_msjw_system_sysuser z on z.username = t.creator_account ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and x.ITEM_CODE = '").append(itemCode).append("' ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and z.USERNAME = '").append(userName).append("' ");
        }
        sql.append(" and X.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and t.RUN_STATUS <> 0 and y.evaluate is not null group by y.evaluate ");
        List<Map<String,Object>> listCount = dao.findBySql(sql.toString(), null, null);
        if (listCount != null && !listCount.isEmpty()) {
            for (Map<String, Object> mapCount : listCount) {
                Object evaluate = mapCount.get("EVALUATE");
                Integer sum = Integer.parseInt(mapCount.get("SUM").toString());
                if ("3".equals(evaluate)) {
                    countMap.put("fcmy", sum);
                } else if ("2".equals(evaluate)) {
                    countMap.put("my", sum);
                } else if ("1".equals(evaluate)) {
                    countMap.put("yb", sum);
                } else if ("0".equals(evaluate)) {
                    countMap.put("bmy", sum);
                } else {
                    countMap.put("wpj", sum);
                }
            }
        }
        return countMap;
    }

    /**
     * 描述:近七天平均受理时长
     *
     * @author Madison You
     * @created 2020/10/23 15:25:00
     * @param
     * @return
     */
    @Override
    public List<Integer> getDjsjjgJqtslscStatis(ArrayList<String> pastWeekDate) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DAYTIME,ROUND(AVG(SLSC)) PJSC FROM ( SELECT substr(a.create_time,0,10) DAYTIME,(to_date(A.REV_ENDTIME,'yyyy-mm-dd hh24:mi:ss') ");
        sql.append(" - to_date(A.REV_STARTTIME,'yyyy-mm-dd hh24:mi:ss'))*60*24 SLSC FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" WHERE to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" AND to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and B.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and A.RUN_STATUS <> 0 and A.REV_ENDTIME > A.REV_STARTTIME ) GROUP BY DAYTIME ");
        List<Map<String,Object>> listCount = dao.findBySql(sql.toString(), null, null);
        List<Integer> countList = new ArrayList<>();
        if (listCount != null && !listCount.isEmpty()) {
            for (String passDate : pastWeekDate) {
                Integer pjsc = 0;
                for (Map<String, Object> map : listCount) {
                    if (passDate.equals(map.get("DAYTIME"))) {
                        pjsc = Integer.parseInt(map.get("PJSC").toString());
                    }
                }
                countList.add(pjsc);
            }
        }
        return countList;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/11/24 15:05:00
     * @param
     * @return
     */
    @Override
    public List<Integer> getDjsjjgJqtbjscStatis(ArrayList<String> pastWeekDate) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DAYTIME,ROUND(AVG(SLSC)) PJSC FROM ( SELECT substr(a.create_time,0,10) DAYTIME,CEIL(to_date(A.END_TIME,'yyyy-mm-dd hh24:mi:ss') ");
        sql.append(" - to_date(A.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss')) SLSC FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" WHERE to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" AND to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and B.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and A.RUN_STATUS = 2 and A.END_TIME IS NOT NULL ) GROUP BY DAYTIME ");
        List<Map<String,Object>> listCount = dao.findBySql(sql.toString(), null, null);
        List<Integer> countList = new ArrayList<>();
        if (listCount != null && !listCount.isEmpty()) {
            for (String passDate : pastWeekDate) {
                Integer pjsc = 0;
                for (Map<String, Object> map : listCount) {
                    if (passDate.equals(map.get("DAYTIME"))) {
                        pjsc = Integer.parseInt(map.get("PJSC").toString());
                    }
                }
                countList.add(pjsc);
            }
        }
        return countList;
    }

    /**
     * 描述:获取不动产事项统计表事项
     *
     * @author Madison You
     * @created 2020/10/26 15:42:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBdcsxtjbItems() {
        StringBuffer sql = new StringBuffer();
        sql.append(" select a.item_name,a.item_code from t_wsbs_serviceitem a ");
        sql.append(" where a.zbcs_id = '").append(BDC_DEPART_ID).append("' ");
        List<Map<String,Object>> itemList = dao.findBySql(sql.toString(), null, null);
        return itemList;
    }

    /**
     * 描述:不动产事项统计表叫号状态
     *
     * @author Madison You
     * @created 2020/10/27 9:06:00
     * @param
     * @return
     */
    @Override
    public Map<String,Integer> getBdcsxtjbJhzt(String itemCode , String userName) {
        Map<String,Integer> jhztMap = new HashMap<>();
        jhztMap.put("wcls", 0);
        jhztMap.put("ghs", 0);
        jhztMap.put("zxs", 0);
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(t.exe_id) sum , y.call_status from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join t_ckbs_queuerecord y on t.exe_id = y.exe_id ");
        sql.append(" left join t_msjw_system_sysuser z on z.username = t.creator_account ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and t.RUN_STATUS <> 0 and y.call_status is not null ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and x.ITEM_CODE = '").append(itemCode).append("' ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and z.username = '").append(userName).append("' ");
        }
        sql.append(" group by y.call_status ");
        List<Map<String,Object>> countList = dao.findBySql(sql.toString(), null, null);
        if (countList != null && !countList.isEmpty()) {
            for (Map<String, Object> countMap : countList) {
                String callStatus = countMap.get("CALL_STATUS").toString();
                Integer sum = Integer.parseInt(countMap.get("SUM").toString());
                if (callStatus.equals("0")) {
                    jhztMap.put("wcls", sum);
                } else if (callStatus.equals("2")) {
                    jhztMap.put("ghs", sum);
                } else if (callStatus.equals("4")) {
                    jhztMap.put("zxs", sum);
                }
            }
        }
        return jhztMap;
    }

    /**
     * 描述:不动产事项统计表个人受理来源
     *
     * @author Madison You
     * @created 2020/10/27 10:01:00
     * @param
     * @return
     */
    @Override
    public ArrayList<ArrayList<Object>> getBdcsxtjbGrslly(ArrayList<String> pastWeekDate , String itemCode) {
        List<Map<String, Object>> grbjlList = getBdcsxtjbGrbjl(itemCode);
        List<Map<String, Object>> mrbjlList = getBdcsxtjbMrbjl(itemCode);
        ArrayList<ArrayList<Object>> list = new ArrayList<>();
        if (!grbjlList.isEmpty() && !mrbjlList.isEmpty()) {
            /*获取受理人员名单并且按照受理量排序*/
            ArrayList<Object> grList = new ArrayList<>();
            grList.add("受理时间");
            for (Map<String, Object> grbjlMap : grbjlList) {
                String creatorName = grbjlMap.get("CREATOR_NAME").toString();
                grList.add(creatorName);
            }
            list.add(grList);
            /*根据受理人员名单获取每日受理人员受理量*/
            for (String pastDate : pastWeekDate) {
                ArrayList<Object> mrList = new ArrayList<>();
                mrList.add(pastDate.replaceAll("-", "."));
                for (int i = 1; i < grList.size(); i++) {
                    Boolean flag = true;
                    for (Map<String, Object> mrbjlMap : mrbjlList) {
                        Integer sum = Integer.parseInt(mrbjlMap.get("SUM").toString());
                        String daytime = mrbjlMap.get("DAYTIME").toString();
                        String creatorName = mrbjlMap.get("CREATOR_NAME").toString();
                        if (pastDate.equals(daytime) && creatorName.equals(grList.get(i))) {
                            mrList.add(sum);
                            flag = false;
                        }
                    }
                    if (flag) {
                        mrList.add(0);
                    }
                }
                list.add(mrList);
            }
        }
        return list;
    }

    /**
     * 描述:不动产事项统计表个人办件量
     *
     * @author Madison You
     * @created 2020/10/27 10:49:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBdcsxtjbGrbjl(String itemCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(t.exe_id) sum , t.creator_name from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join t_ckbs_queuerecord y on t.exe_id = y.exe_id ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and x.ITEM_CODE = '").append(itemCode).append("' ");
        }
        sql.append(" and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' and t.RUN_STATUS <> 0 and t.sqfs = 2 ");
        sql.append(" group by t.creator_name order by sum desc ");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 描述:不动产事项统计表每日办件量
     *
     * @author Madison You
     * @created 2020/10/27 10:55:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBdcsxtjbMrbjl(String itemCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(exe_id) sum,creator_name,DAYTIME from ( select t.exe_id , t.creator_name , substr(t.create_time,1,10) DAYTIME ");
        sql.append(" from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join t_ckbs_queuerecord y on t.exe_id = y.exe_id ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        if (StringUtils.isNotEmpty(itemCode)) {
            sql.append(" and x.ITEM_CODE = '").append(itemCode).append("' ");
        }
        sql.append(" and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' ").append(" and t.RUN_STATUS <> 0 and t.sqfs = 2 ) ");
        sql.append(" group by creator_name,DAYTIME ");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 描述:获取不动产的所有用户
     *
     * @author Madison You
     * @created 2020/10/28 9:51:00
     * @param
     * @return
     */
    @Override
    public Set<Map<String,Object>> getUsersFromBdc() {
        StringBuffer sql = new StringBuffer();
        HashSet<Map<String,Object>> set = new HashSet<>();
        sql.append(" select Y.USERNAME,Y.FULLNAME from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER y on y.USERNAME = T.CREATOR_ACCOUNT where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= ");
        sql.append(" to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < ");
        sql.append(" to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' ");
        sql.append(" and t.RUN_STATUS <> 0 and t.sqfs = 2 ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), null, null);
        set.addAll(list);
        return set;
    }

    /**
     * 描述:获取经办人不动产事项统计
     *
     * @author Madison You
     * @created 2020/10/28 11:13:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getJbrsxtjList(String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(t.exe_id) sum , x.item_name from JBPM6_EXECUTION t left join T_WSBS_SERVICEITEM x on t.ITEM_CODE = x.ITEM_CODE ");
        sql.append(" left join T_MSJW_SYSTEM_SYSUSER y on y.USERNAME = T.CREATOR_ACCOUNT ");
        sql.append(" where to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and to_date(t.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and x.ZBCS_ID = '").append(BDC_DEPART_ID).append("' ");
        sql.append(" and t.RUN_STATUS <> 0 and t.sqfs = 2 and y.username = '").append(userName).append("' ");
        sql.append(" group by x.item_name order by sum desc ");
        return dao.findBySql(sql.toString(), null, null);
    }

    /**
     * 描述:获取
     *
     * @author Madison You
     * @created 2020/11/18 15:08:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> getExeList(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" SELECT A.EXE_ID,A.SUBJECT,B.ITEM_NAME,F.DIC_NAME SXXZ,A.CREATOR_NAME,A.SQFS,G.DIC_NAME EVALUATE,D.EFFI_DESC ");
        sql.append(" FROM JBPM6_EXECUTION A LEFT JOIN T_WSBS_SERVICEITEM B ON A.ITEM_CODE = B.ITEM_CODE ");
        sql.append(" LEFT JOIN JBPM6_EFFICINFO D ON A.EXE_ID = D.EFLOW_EXEID ");
        sql.append(" LEFT JOIN T_CKBS_QUEUERECORD E ON A.EXE_ID = E.EXE_ID ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY  ");
        sql.append(" WHERE TYPE_CODE = 'ServiceItemXz') F ON B.SXXZ = F.DIC_CODE ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'PJXX') G ON E.EVALUATE = G.DIC_CODE ");
        sql.append(" WHERE to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') >= to_date(to_char(sysdate-7,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" AND to_date(A.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') < to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') ");
        sql.append(" and B.ZBCS_ID = '").append(BDC_DEPART_ID).append("' ");
        sql.append(" and A.RUN_STATUS <> 0 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        StringBuffer querySqlStr = new StringBuffer(querySql).append(" ORDER BY A.CREATE_TIME DESC ");
        List list = dao.findBySql(querySqlStr.toString(), params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述:获取基准地价
     *
     * @author Madison You
     * @created 2020/12/28 15:35:00
     * @param
     * @return
     */
    @Override
    public double getBenchmarkLandPrc(Map<String, Object> requestMap) {
        double jzdj = 0;
        String tdxz = StringUtil.getValue(requestMap, "TDXZ");
        String tdyt = StringUtil.getValue(requestMap, "TDYT");
        String tdxzValue = dictionaryService.getDicNames("JSQTDXZ", tdxz);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM T_MSJW_SYSTEM_DICTIONARY WHERE TYPE_CODE = ? AND DIC_NAME = ? ");
        Map<String, Object> map = dao.getMapBySql(sql.toString(), new Object[]{"JSQJZDJ", tdxzValue});
        if (map != null) {
            String dicCode = StringUtil.getValue(map, "DIC_CODE");
            String dicDesc = StringUtil.getValue(map, "DIC_DESC");
            if (Objects.equals(tdyt, "1")) {
                jzdj = Double.parseDouble(dicDesc);
            } else {
                jzdj = Double.parseDouble(dicCode);
            }
        }
        return jzdj;
    }

    /**
     * 描述:获取修正系数
     *
     * @author Madison You
     * @created 2020/12/28 16:33:00
     * @param
     * @return
     */
    @Override
    public double getCorrectionFactor(Map<String, Object> requestMap) {
        double xzxs = 0;
        Map<String, Object> startMap = null;
        Map<String, Object> endMap = null;
        double tdsyqmj = Double.parseDouble(StringUtil.getValue(requestMap, "TDSYQMJ"));
        double jzmj = Double.parseDouble(StringUtil.getValue(requestMap, "JZMJ"));
        double rjl = jzmj / tdsyqmj;
        rjl = new BigDecimal(rjl).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        List<Map<String, Object>> jsqxzxsList = dictionaryService.findByTypeCode("JSQXZXS");
        for (Map<String, Object> jsqxzxsMap : jsqxzxsList) {
            double dicName = Double.parseDouble(StringUtil.getValue(jsqxzxsMap, "DIC_NAME"));
            Double dicCode = Double.parseDouble(StringUtil.getValue(jsqxzxsMap, "DIC_CODE"));
            String dicDesc = StringUtil.getValue(jsqxzxsMap, "DIC_DESC");
            if (rjl == dicName) {
                xzxs = dicCode;
            }
            if (Objects.equals(dicDesc, "start")) {
                startMap = jsqxzxsMap;
            }
            if (Objects.equals(dicDesc, "end")) {
                endMap = jsqxzxsMap;
            }
        }
        double startDicName = Double.parseDouble(StringUtil.getValue(startMap, "DIC_NAME"));
        double startDicCode = Double.parseDouble(StringUtil.getValue(startMap, "DIC_CODE"));
        double endDicName = Double.parseDouble(StringUtil.getValue(endMap, "DIC_NAME"));
        double endDicCode = Double.parseDouble(StringUtil.getValue(endMap, "DIC_CODE"));
        if (rjl <= startDicName) {
            xzxs = startDicCode;
        }
        if (rjl >= endDicName) {
            xzxs = endDicCode;
        }
        return xzxs;
    }

    /**
     * 描述:四舍五入到小数点后二位
     *
     * @author Madison You
     * @created 2020/12/28 16:29:00
     * @param
     * @return
     */
    public double roundToOneDecimalPlce(double value) {
        BigDecimal fvalue = new BigDecimal(value);
        return fvalue.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 描述:四舍五入到小数点后二位
     *
     * @author Madison You
     * @created 2020/12/29 10:05:00
     * @param
     * @return
     */
    public String roundToOneDecimalPlceS(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 描述:计算单位集资房
     *
     * @author Madison You
     * @created 2020/12/29 8:52:00
     * @param
     * @return
     */
    @Override
    public String calUnitFundraisingHouse(Map<String, Object> requestMap) {
        double tdsyqmj = Double.parseDouble(StringUtil.getValue(requestMap, "TDSYQMJ"));
        double jzdj = getBenchmarkLandPrc(requestMap);
        double xzxs = getCorrectionFactor(requestMap);
        BigDecimal tdsyqmjB = new BigDecimal(tdsyqmj);
        BigDecimal jzdjB = new BigDecimal(jzdj);
        BigDecimal xzxsB = new BigDecimal(xzxs);
        BigDecimal coeffi = new BigDecimal(0.3);
        BigDecimal v = tdsyqmjB.multiply(xzxsB).multiply(jzdjB).multiply(coeffi);
        return v.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/29 9:34:00
     * @param
     * @return
     */
    @Override
    public String calLandPrc(Map<String, Object> requestMap) {
        String v = null;
        String bllx = StringUtil.getValue(requestMap, "BLLX");
        String type = StringUtil.getValue(requestMap, "TYPE");
        if (Objects.equals(type, "1")) {
            if (Objects.equals(bllx, "1")) {
                v = calUnitFundraisingHouse(requestMap);
            } else if (Objects.equals(bllx, "2")) {
                String tdzrqx1 = StringUtil.getValue(requestMap, "TDZRQX1");
                if (Objects.equals(tdzrqx1, "1")) {
                    v = calOfSelfbuiltHouses1(requestMap);
                } else if (Objects.equals(tdzrqx1, "2")) {
                    v = calOfSelfbuiltHouses2(requestMap);
                } else if (Objects.equals(tdzrqx1, "3")) {
                    v = calOfSelfbuiltHouses3(requestMap);
                }
            } else if (Objects.equals(bllx, "3")) {
                String tdzrqx2 = StringUtil.getValue(requestMap, "TDZRQX2");
                if (Objects.equals(tdzrqx2, "1")) {
                    v = calOldCityRenovationProjects1(requestMap);
                } else if (Objects.equals(tdzrqx2, "2")) {
                    v = calOldCityRenovationProjects2(requestMap);
                } else if (Objects.equals(tdzrqx2, "3")) {
                    v = calOldCityRenovationProjects3(requestMap);
                }
            }
        } else {
            v = calUnitFundraisingHouse(requestMap);
        }
        return v;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/29 15:03:00
     * @param 
     * @return 
     */
    @Override
    public String calLandPrcWjbj(Map<String, Object> requestMap) {
        String w = null;
        String bllx = StringUtil.getValue(requestMap, "BLLX");
        String type = StringUtil.getValue(requestMap, "TYPE");
        if (Objects.equals(type, "1")) {
            if (Objects.equals(bllx, "2")) {
                w = calOfSelfbuiltHouses4(requestMap);
            }
        }
        return w;
    }

    /**
     * 描述:计算国有土地上自建房屋分割销售国有出让地上建设补缴
     *
     * @author Madison You
     * @created 2020/12/29 9:37:00
     * @param
     * @return
     */
    private String calOfSelfbuiltHouses1(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj1 = StringUtil.getValue(requestMap, "JJZMJ1");
        String zdmj1 = StringUtil.getValue(requestMap, "ZDMJ1");
        String rjl1 = StringUtil.getValue(requestMap, "RJL1");
        BigDecimal jjzmj1B = new BigDecimal(jjzmj1);
        BigDecimal zdmj1B = new BigDecimal(zdmj1);
        BigDecimal lmdjB = new BigDecimal(lmdj);
        /*应补缴价款  =   楼面地价*（总建筑面积-占地面积*2.5）*/
        BigDecimal v = lmdjB.multiply(jjzmj1B.subtract(zdmj1B.multiply(new BigDecimal(rjl1))));
        return roundToOneDecimalPlceS(v);
    }

    /**
     * 描述:计算国有土地上自建房屋分割销售国有划拨地上建设补缴
     *
     * @author Madison You
     * @created 2020/12/29 9:37:00
     * @param
     * @return
     */
    private String calOfSelfbuiltHouses2(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj1 = StringUtil.getValue(requestMap, "JJZMJ1");
        String zdmj1 = StringUtil.getValue(requestMap, "ZDMJ1");
        String ydmj = StringUtil.getValue(requestMap, "YDMJ");
        String rjl1 = StringUtil.getValue(requestMap, "RJL1");
        /*应补缴价款=楼面地价*（总建筑面积-占地面积*2.5）+（7050元/平分米-4425元/平分米）*用地面积*/
        BigDecimal jjzmj1B = new BigDecimal(jjzmj1);
        BigDecimal zdmj1B = new BigDecimal(zdmj1);
        BigDecimal lmdjB = new BigDecimal(lmdj);
        BigDecimal ydmjB = new BigDecimal(ydmj);
        BigDecimal a = lmdjB.multiply(jjzmj1B.subtract(zdmj1B.multiply(new BigDecimal(rjl1))));
        BigDecimal b = ydmjB.multiply(new BigDecimal(2625));
        return roundToOneDecimalPlceS(a.add(b));
    }

    /**
     * 描述:计算国有土地上自建房屋分割销售城镇区划内原未征收土地价款补缴
     *
     * @author Madison You
     * @created 2020/12/29 9:37:00
     * @param
     * @return
     */
    private String calOfSelfbuiltHouses3(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj1 = StringUtil.getValue(requestMap, "JJZMJ1");
        String zdmj1 = StringUtil.getValue(requestMap, "ZDMJ1");
        String ydmj = StringUtil.getValue(requestMap, "YDMJ");
        String rjl1 = StringUtil.getValue(requestMap, "RJL1");
        /*应补缴价款=楼面地价*（总建筑面积-占地面积*2.5）+（7050元/平分米-4350元/平分米）*用地面积；*/
        BigDecimal jjzmj1B = new BigDecimal(jjzmj1);
        BigDecimal zdmj1B = new BigDecimal(zdmj1);
        BigDecimal lmdjB = new BigDecimal(lmdj);
        BigDecimal ydmjB = new BigDecimal(ydmj);
        BigDecimal a = lmdjB.multiply(jjzmj1B.subtract(zdmj1B.multiply(new BigDecimal(rjl1))));
        BigDecimal b = ydmjB.multiply(new BigDecimal(2700));
        return roundToOneDecimalPlceS(a.add(b));
    }

    /**
     * 描述:计算国有土地上自建房屋分割销售城违建面积补价
     *
     * @author Madison You
     * @created 2021/3/29 14:37:00
     * @param
     * @return
     */
    private String calOfSelfbuiltHouses4(Map<String, Object> requestMap) {
        String wjmj = StringUtil.getValue(requestMap, "WJMJ");
        if (StringUtils.isNotEmpty(wjmj)) {
            BigDecimal wjmjB = new BigDecimal(wjmj);
            BigDecimal v = wjmjB.multiply(new BigDecimal(2820));
            return roundToOneDecimalPlceS(v);
        } else {
            return null;
        }
    }

    /**
     * 描述:计算旧城改造项目国有出让地上建设补缴
     *
     * @author Madison You
     * @created 2020/12/29 10:10:00
     * @param 
     * @return 
     */
    private String calOldCityRenovationProjects1(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj2 = StringUtil.getValue(requestMap, "JJZMJ2");
        String dyjzmj = StringUtil.getValue(requestMap, "DYJZMJ");
        String zdmj2 = StringUtil.getValue(requestMap, "ZDMJ2");
        String rjl2 = StringUtil.getValue(requestMap, "RJL2");
        /*应补缴价款  =  单元建筑面积*楼面地价*（总建筑面积-占地面积*4.0）/总建筑面积；*/
        BigDecimal lmdjB = new BigDecimal(lmdj);
        BigDecimal jjzmj2B = new BigDecimal(jjzmj2);
        BigDecimal dyjzmjB = new BigDecimal(dyjzmj);
        BigDecimal zdmj2B = new BigDecimal(zdmj2);
        BigDecimal v = lmdjB.multiply(dyjzmjB.multiply(jjzmj2B.
                subtract(zdmj2B.multiply(new BigDecimal(rjl2))))).divide(jjzmj2B,5,BigDecimal.ROUND_HALF_UP);
        return roundToOneDecimalPlceS(v);
    }

    /**
     * 描述:计算旧城改造项目国有划拨地上建设补缴
     *
     * @author Madison You
     * @created 2020/12/29 10:10:00
     * @param
     * @return
     */
    private String calOldCityRenovationProjects2(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj2 = StringUtil.getValue(requestMap, "JJZMJ2");
        String dyjzmj = StringUtil.getValue(requestMap, "DYJZMJ");
        String zdmj2 = StringUtil.getValue(requestMap, "ZDMJ2");
        String rjl2 = StringUtil.getValue(requestMap, "RJL2");
        /*应补缴价款  =  单元建筑面积*（楼面地价*（总建筑面积-占地面积*4.0）/总建筑面积+（7050元/平分米-4425元/平分米）*占地面积/总建筑面积）；*/
        BigDecimal lmdjB = new BigDecimal(lmdj);
        BigDecimal jjzmj2B = new BigDecimal(jjzmj2);
        BigDecimal dyjzmjB = new BigDecimal(dyjzmj);
        BigDecimal zdmj2B = new BigDecimal(zdmj2);
        BigDecimal a = lmdjB.multiply(jjzmj2B.subtract(zdmj2B.multiply(new BigDecimal(rjl2)))).divide(jjzmj2B,5,BigDecimal.ROUND_HALF_UP);
        BigDecimal b = zdmj2B.multiply(new BigDecimal(2625)).divide(jjzmj2B,5,BigDecimal.ROUND_HALF_UP);
        BigDecimal v = dyjzmjB.multiply(a.add(b));
        return roundToOneDecimalPlceS(v);
    }

    /**
     * 描述:计算旧城改造项目城镇区划内原未征收土地价款补缴
     *
     * @author Madison You
     * @created 2020/12/29 10:10:00
     * @param
     * @return
     */
    private String calOldCityRenovationProjects3(Map<String, Object> requestMap) {
        String lmdj = dictionaryService.getDicCode("JSQCSPZ", "JSQLMDJ");
        String jjzmj2 = StringUtil.getValue(requestMap, "JJZMJ2");
        String dyjzmj = StringUtil.getValue(requestMap, "DYJZMJ");
        String zdmj2 = StringUtil.getValue(requestMap, "ZDMJ2");
        String rjl2 = StringUtil.getValue(requestMap, "RJL2");
        /*应补缴价款  =  单元建筑面积*（楼面地价*（总建筑面积-占地面积*4.0）/总建筑面积+（7050元/平分米-4350元/平分米）*占地面积/总建筑面积）；*/
        BigDecimal lmdjB = new BigDecimal(lmdj);
        BigDecimal jjzmj2B = new BigDecimal(jjzmj2);
        BigDecimal dyjzmjB = new BigDecimal(dyjzmj);
        BigDecimal zdmj2B = new BigDecimal(zdmj2);
        BigDecimal a = lmdjB.multiply(jjzmj2B.subtract(zdmj2B.multiply(new BigDecimal(rjl2)))).divide(jjzmj2B,5,BigDecimal.ROUND_HALF_UP);
        BigDecimal b = zdmj2B.multiply(new BigDecimal(2700)).divide(jjzmj2B,5,BigDecimal.ROUND_HALF_UP);
        BigDecimal v = dyjzmjB.multiply(a.add(b));
        return roundToOneDecimalPlceS(v);
    }


}
