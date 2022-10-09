/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

import com.alibaba.fastjson.JSON;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;

import net.evecom.platform.project.dao.ProjectApplyDao;
import net.evecom.platform.project.service.CjdaClientService;
import net.evecom.platform.project.service.ProjectWebsiteApplyService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.rpc.ParameterMode;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 描述 城建档案系统对接业务处理service
 * 
 * @author Adrian Bian
 * @version 1.0
 * @created 2020年1月7日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
@Service("cjdaClientService")
public class CjdaClientServiceImpl extends BaseServiceImpl implements CjdaClientService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CjdaClientServiceImpl.class);
    /**
     * @Resource EsuperDao
     */
    @Resource
    private ProjectApplyDao dao;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 引入附件服务
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * projectWebsiteApplyService
     */
    @Resource
    private ProjectWebsiteApplyService projectWebsiteApplyService;
    /**
     * applyMaterService
     */
    @Resource
    private ApplyMaterService applyMaterService;
    /**
     * 描述
     *
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    /**
     * 描述 用数据包填充模版
     * @param xmlMap
     * @param configXml
     * @return
     */
    private StringBuffer makeDataXml(Map<String, Object> xmlMap, String configXml) {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append(FreeMarkerUtil.getResultString(configXml, xmlMap));
        if ((sbuffer.toString()).equals("null")) {
            return null;
        }
        return sbuffer;
    }


    /**
     * 后置事件-插入待推送信息进中间表
     * @param flowDatas
     * @return
     */
    @Override
    public Map<String, Object>  saveMiddleData(Map<String, Object> flowDatas){
        // 获取业务表名称
        String busTableName = (String) flowDatas.get("EFLOW_BUSTABLENAME");
        // 获取业务表记录ID
        String busRecordId = (String) flowDatas.get("EFLOW_BUSRECORDID");
        //事项ID
        String itemCode = (String) flowDatas.get("ITEM_CODE");
        //事项名称
        String itemName = (String) flowDatas.get("ITEM_NAME");
        // 记录工程建设项目PROJECT_CODE(不同的表单工程编码字段名都不一样必须做特殊处理)
        String projectCode = (String) flowDatas.get("PROJECT_CODE");
        if(StringUtil.isBlank(projectCode)){
            projectCode = (String) flowDatas.get("project_code");
        }
        if(StringUtil.isBlank(projectCode)){
            projectCode = (String) flowDatas.get("PRJ_CODE");
        }
        if(StringUtil.isBlank(projectCode)){
            projectCode = (String) flowDatas.get("PROJECTCODE");
        }
        if(!StringUtil.isBlank(projectCode)){
            //是否为退回操作
            Object EFLOW_ISBACK = flowDatas.get("EFLOW_ISBACK");
            if(EFLOW_ISBACK ==null || !"true".equals(String.valueOf(EFLOW_ISBACK))){
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("BUS_TABLERECORDID",busRecordId);
                data.put("BUS_TABLENAME",busTableName);
                data.put("ITEM_CODE",itemCode);
                data.put("ITEM_NAME",itemName);
                data.put("PROJECT_CODE",projectCode);
                this.saveOrUpdate(data,"T_CJDA_MIDDLE",null);
            }
        }
        return flowDatas;
    }
    /**
     * 描述 定时调用的推送工程建设项目附件至城建档案系统内业务的服务
     * @param log
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sendCjda(Log log) {
        String resultCode ="";
        // 1.遍例工程建设项目推送城建档案接口中间表，报送数据
        String sql = " select r.* "
                + " from T_CJDA_MIDDLE r "
                + " where r.send_status=? ";
        List<Map<String, Object>> dataList = this.dao.findBySql(sql, new Object[] { "0" }, null);

        // 2.报送数据
        if (dataList != null && !dataList.isEmpty() && dataList.size() > 0) {
            log.info("开始调用城建档案接口报送处理定时任务，向城建档案系统报送数据....");
            for (Map<String, Object> dataMap : dataList) {
                //用来存放每一条文件推送结果信息
                List<Map<String, Object>> filePushedReturnInfoList = new ArrayList<>();
                if (dataMap != null && dataMap.get("PROJECT_CODE") != null) {
                    String projectCode = (String )dataMap.get("PROJECT_CODE");
                    if(!StringUtil.isBlank(projectCode)){
                        //工程项目基本信息
                        Map<String, Object> projectInfo = this.getByJdbc("SPGL_XMJBXXB",
                                new String[]{"PROJECT_CODE"},new Object[]{projectCode});
                        //工程项目所有附件信息
                        List<Map<String, Object>> fileList = this.findFileListAll(dataMap);
                        if(fileList != null && fileList.size() >0){
                            //该城建档案接口为单文件传输、多份文件需要循环调用
                            for (Map<String,Object> fileInfo : fileList){
                                Map<String, Object> filePushedReturnInfo = new HashMap<String, Object>();
                                Map<String, Object> updateFileInfo = new HashMap<String, Object>();
                                updateFileInfo.put("FILE_ID",fileInfo.get("FILE_ID"));
                                updateFileInfo.put("CJDA_PUSH_STATUS",1);
                                if("0".equals((String)fileInfo.get("CJDA_PUSH_STATUS"))){
                                    //调用推送方法
                                    /**
                                     * resultCode:
                                     * 000 审批系统文件不存在
                                     * 1 归档成功
                                     * 2 账户或密码有误
                                     * 3 传递目录参数不匹配
                                     * 4 传递目录长度超过约定值
                                     * 5 文件解析失败
                                     * 6 文件传输失败
                                     * 7 连接失败
                                     */
                                    resultCode = this.sendData(dataMap,projectInfo,fileInfo);
                                    //String resultMsg="归档成功";
                                    String selfMsg = "正常推送";
                                    //归档成功
                                    if("000".equals(resultCode)){
                                        selfMsg ="系统文件不存在";
                                    }
                                    filePushedReturnInfo.put("FILE_ID",fileInfo.get("FILE_ID"));
                                    filePushedReturnInfo.put("CJDA_PUSH_STATUS",1);
                                    filePushedReturnInfo.put("RESULT_CODE",resultCode);
                                    //filePushedReturnInfo.put("RESULT_MSG",resultMsg);
                                    filePushedReturnInfo.put("SELF_MSG",selfMsg);
                                    filePushedReturnInfoList.add(filePushedReturnInfo);
                                    if("1".equals(resultCode)){
                                        //更新附件推送状态
                                        this.saveOrUpdate(updateFileInfo,"T_MSJW_SYSTEM_FILEATTACH",
                                                (String)fileInfo.get("FILE_ID"));
                                    }

                                }
                            }
                            //该项目本次推送的所有附件及返回结果信息
                            String allReturnInfoJson = JSON.toJSONString(filePushedReturnInfoList);
                            updateMiddelData(dataMap, allReturnInfoJson);
                        }
                    }
                }
            }
            log.info("结束调用城建档案接口报送处理定时任务，向城建档案系统报送数据....");
        }
    }

    /**
     * 查找该工程建设项目附件（前台共性材料 type=1 ,后台批复材料 type =2）
     * @param dataMap
     * @return
     */
    private List<Map<String, Object>> findFileListAll(Map<String, Object> dataMap){
        String projectCode = (String )dataMap.get("PROJECT_CODE");
        List<Map<String,Object>> allFileList = new ArrayList<Map<String, Object>>();
        //1.工程项目共性材料（暂时不分阶段）
        List<Map<String,Object>> commonFileList = projectWebsiteApplyService.findMaterListByProjectCode(
                projectCode,"1");
//        List<Map<String,Object>> commonFileList = this.getAllByJdbc("T_MSJW_PROJECT_FILE",
//                new String[]{"PROJECT_CODE","FILE_TYPE","IS_PUSHED"}, new Object[]{projectCode,"1","0"});
        for(Map<String, Object> commonFile : commonFileList ){
            String fileId  = (String)commonFile.get("FILE_ID");
            Map<String,Object> fileInfo = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID","CJDA_PUSH_STATUS"}, new Object[]{fileId,0});
            allFileList.add(fileInfo);
        }
        //2.工程项目后台批复材料（暂时不分阶段）
        List<Map<String,Object>> replyFileList = projectWebsiteApplyService.findMaterListByProjectCode(
                projectCode,"2");
        for(Map<String, Object> replyFile : replyFileList ) {
            String fileId = (String) replyFile.get("FILE_ID");
            Map<String, Object> fileInfo = fileAttachService.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID", "CJDA_PUSH_STATUS"}, new Object[]{fileId, 0});
            allFileList.add(fileInfo);
        }
        //3.工程项目审批事项流程附件
        List<Map<String,Object>> itemAttachFileList  = fileAttachService.findList(
                (String)dataMap.get("BUS_TABLENAME"),(String)dataMap.get("BUS_TABLERECORDID"));
        allFileList.addAll(itemAttachFileList);
        return allFileList;
    }


    /**
     * 工程基本信息转为xml并整体进行Base64编码
     * @param projectInfo
     * @return
     */
    private String projectInfoToXml(Map<String, Object> dataMap,Map<String, Object> projectInfo,
                                    Map<String, Object> fileInfo){
        String xmlString ="";
        Document doc = DocumentHelper.createDocument();
        Element fieldMsg = doc.addElement("FieldMsg");
        Element content = fieldMsg.addElement("Content");
        //逗号隔开
        StringBuffer enterpriseNames = new StringBuffer("");
        String lerepInfoJson = (String)projectInfo.get("LEREP_INFO");
        //案卷级名称(此处使用材料编码对应的材料名称)
        String folderName = getFolderName(fileInfo);

        if(!StringUtil.isBlank(lerepInfoJson)){
            List<Map<String, Object>> lerepInfoList = JSON.parseObject(lerepInfoJson, List.class);
            for(Map<String, Object> lerepInfo : lerepInfoList){
                enterpriseNames.append(lerepInfo.get("enterprise_name")).append(",");
            }
            enterpriseNames.deleteCharAt(enterpriseNames.lastIndexOf(","));
            content.addAttribute("ProjectNo", toGBKBase64Str((String)projectInfo.get("PROJECT_CODE")));
            //系统里暂无准确对应类别
            content.addAttribute("ProjectType", toGBKBase64Str("城建"));
            content.addAttribute("ProjectName", toGBKBase64Str((String)projectInfo.get("PROJECT_NAME")));
            content.addAttribute("ProjectAddr",toGBKBase64Str((String)projectInfo.get("PLACE_CODE_DETAIL")));
            content.addAttribute("ProjectBunit",enterpriseNames.toString());
            //案卷级编号（此处使用材料编码作为编号）
            content.addAttribute("FolderNo",toGBKBase64Str((String)fileInfo.get("ATTACH_KEY")));
            content.addAttribute("FolderName",toGBKBase64Str(folderName));
            xmlString = this.docToXmlBase64Str(doc);
        }
        return xmlString;
    }

    /**
     * 获取案卷级名称（材料名称）
     * @param fileInfo
     * @return
     */
    public String  getFolderName(Map<String, Object> fileInfo){
        String folderName = "";
        Map<String, Object> applyMater = applyMaterService.getByJdbc("T_WSBS_APPLYMATER",
                new String[]{"MATER_CODE"},new Object[]{(String)fileInfo.get("ATTACH_KEY")});
        if(applyMater != null){
            folderName = (String)applyMater.get("MATER_NAME");
        }
        return folderName;
    }
    /**
     * 将用户信息写为xml
     * @param userName
     * @param userPwd
     * @param archiveType
     * @return
     */
    private String userInfoToXml(String userName, String userPwd, String archiveType) {
        String xmlString = "";
        Document doc = DocumentHelper.createDocument();
        Element userMsg = doc.addElement("UserMsg");
        Element condition = userMsg.addElement("Condition");
        condition.addAttribute("UserName", toGBKBase64Str(userName));
        condition.addAttribute("UserPwd", toGBKBase64Str(userPwd));
        condition.addAttribute("ArchiveType",toGBKBase64Str(archiveType));
        xmlString = this.docToXmlBase64Str(doc);
        return xmlString;
    }

    /**
     * 将文件信息写为xml
     * @param dataMap
     * @return
     */
    private String fileInfoToXml(Map<String, Object> dataMap) {
        String xmlString = "";
        Properties projectProperties = FileUtil.readProperties("project.properties");
        String fileServer = projectProperties.getProperty("uploadFileUrl");
        String fileRealPath = fileServer + dataMap.get("FILE_PATH");
        int urlFileSize = 0;

        StringBuffer fileSb = new StringBuffer("");
        URL url = null;
        URLConnection conn = null;
        InputStream inStream =null;
        BufferedReader reader = null;
        try{
            url = new URL(fileRealPath);
            conn = url.openConnection();
            inStream = conn.getInputStream();
            urlFileSize = conn.getContentLength();
            reader = new BufferedReader(new InputStreamReader(inStream));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                fileSb.append(tempString.trim());
            }
            reader.close();
            inStream.close();
        }catch (Exception e){
            try{
                if(reader !=null){
                    reader.close();
                }
                if(inStream !=null){
                    inStream.close();
                }

            }catch(MalformedURLException e1){
                log.error(e1.getMessage());

            }catch(IOException e2 ){
                log.error(e2.getMessage());
            }
        }
        if(!StringUtil.isBlank(fileSb.toString())){
            String encodeFileString = Base64.getEncoder().encodeToString(fileSb.toString().getBytes());
            String fileSize = String.valueOf(urlFileSize);
            String fileName = (String)dataMap.get("FILE_NAME");

            Document doc = DocumentHelper.createDocument();
            Element fileMsg = doc.addElement("FileMsg");
            Element content = fileMsg.addElement("OriFile1");
            content.addAttribute("code1", toGBKBase64Str(encodeFileString));
            content.addAttribute("FileName1", toGBKBase64Str(fileName));
            content.addAttribute("FileSize1", toGBKBase64Str(fileSize));
            xmlString = this.docToXmlBase64Str(doc);
        }

        return xmlString;
    }

    /**
     * 将doc对象转为xml格式并整体Base64编码
     * @param doc
     * @return
     */
    private String docToXmlBase64Str (Document doc){
        String xmlString = "";
        String encodeXmlString ="";
        OutputFormat format = OutputFormat.createCompactFormat();
        //设置是否缩进
        format.setIndent(true);
        //以四个空格方式实现缩进
        format.setIndent("    ");
        //设置是否换行
        format.setNewlines(true);

        //设置输出编码
        format.setEncoding("UTF-8");
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(doc);
            writer.close();
            output.close();
            xmlString =  writer.toString();
            encodeXmlString = Base64.getEncoder().encodeToString(xmlString.getBytes());
        }  catch (IOException e) {
            log.error(e);
        }
        return encodeXmlString;
    }

    /**
     * 将字段值以GBK编码转为Base64串
     * @param fieldValue
     * @return
     */
    private String toGBKBase64Str(String fieldValue){
       String encodeStr ="";
       if(fieldValue !=null && !StringUtil.isBlank(fieldValue)){
           try{
              encodeStr = Base64.getEncoder().encodeToString(fieldValue.getBytes("GBK"));
           }catch (UnsupportedEncodingException e){
               log.error(e.getMessage());
           }
       }
       return encodeStr;
    }
    /**
     * 推送数据
     * @param projectInfo
     *  @param fileInfo
     * @return
     */
    private String sendData(Map<String, Object> dataMap,Map<String, Object> projectInfo,Map<String, Object> fileInfo) {
        String resultCode = "000";
        if (fileInfo != null) {
            String archiveType = this.dictionaryService.getDicNames("CJDASJJKPZ", "archiveType");
            String userName = this.dictionaryService.getDicNames("CJDASJJKPZ", "userName");
            String userPwd = this.dictionaryService.getDicNames("CJDASJJKPZ", "userPwd");
            String webNameSpace = this.dictionaryService.getDicNames("CJDASJJKPZ", "webNameSpace");
            //http://chnsys.com.cn/rcs_ws/
            Map<String, Object> dataAbutment = this.getByJdbc("T_BSFW_DATAABUTMENT",
                    new String[] { "AABUT_CODE" }, new String[] { AllConstant.INTER_CODE_CJDA_SEND });
            String webserviceUrl = (String) dataAbutment.get("WEBSERVICE_URL");
            Map<String, Object[]> xmlMap = new HashMap<String, Object[]>();

            String userMsg = userInfoToXml(userName,userPwd,archiveType);
            String fieldString = projectInfoToXml(dataMap,projectInfo,fileInfo);
            String fileMsg = fileInfoToXml(fileInfo);
            if(StringUtil.isBlank(fileMsg)){
                //文件不存在
                resultCode = "000";
                return resultCode;
            }

            Object[] userObj = new Object[]{org.apache.axis.encoding.XMLType.XSD_ANYTYPE,ParameterMode.IN,userMsg};
            Object[] fieldObj = new Object[]{org.apache.axis.encoding.XMLType.XSD_ANYTYPE,ParameterMode.IN,fieldString};
            Object[] fileObj = new Object[]{org.apache.axis.encoding.XMLType.XSD_ANYTYPE,ParameterMode.IN,fileMsg};
            xmlMap.put("UserMsg",userObj);
            xmlMap.put("FieldMsg",fieldObj);
            xmlMap.put("FileMsg",fileObj);

            Object ob = WebServiceCallUtil.callService(webserviceUrl, webNameSpace, "zdUploadArchiveFile",
                    xmlMap, XMLType.XSD_STRING);

            //处理返回信息。
            resultCode = handleReturnMsg(ob);
        }
        return resultCode;
    }

    /**
     * 描述 更新城建档案接口结果返回码
     *
     * @param dataMap
     * @param resultJson
     */
    private void updateMiddelData(Map<String, Object> dataMap, String resultJson) {
        String updateSql = "update T_CJDA_MIDDLE t set t.send_result_json = ? ,t.send_time= ? ,t.send_status= ? " +
                " where t.id=? ";
        this.dao.executeSql(updateSql,
                new Object[] { resultJson, DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"),"1",
                        dataMap.get("ID") });


    }

    /**
     * 处理接口返回的xml
     * @param object
     * @return
     */
    private String handleReturnMsg(Object object){
        String resultCode = "";
        if (object == null) {
            return null;
        }
        String returnStr = (String) object;
        returnStr = new String(Base64.getDecoder().decode(returnStr));
        if (returnStr != null && !StringUtil.isBlank(returnStr)) {
            Object m = null;
            try {
                m = XmlUtil.xml2Map(returnStr);
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
            Map body = (Map) ((Map) m).get("Body");
            Map zdUploadArchiveFileResponse = (Map) body.get("zdUploadArchiveFileResponse");
            Map result = (Map) zdUploadArchiveFileResponse.get("return");
            String resultStr = (String) result.get("return");
            byte[] b= Base64.getDecoder().decode(resultStr);
            SAXReader saxReader = new SAXReader();
            Document document=DocumentHelper.createDocument();
            try {
                document = saxReader.read(new ByteArrayInputStream(b));
            } catch (DocumentException e) {
                log.error(e.getMessage());
            }
            Element root = document.getRootElement();
            //String resultMsg = root.attribute("Message").getValue();
            //resultMsg = new String (Base64.getDecoder().decode(resultMsg),"GBK");//可能需要转码
            //resultMsg = new String (Base64.getDecoder().decode(resultMsg));

            resultCode = root.attribute("Result").getValue();
            resultCode = new String (Base64.getDecoder().decode(resultCode));
        }

        return resultCode;
    }

}
