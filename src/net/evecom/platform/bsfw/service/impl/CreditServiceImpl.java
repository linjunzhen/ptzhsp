/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.platform.bdc.service.BdcSsdjService;
import net.evecom.platform.bsfw.constant.CreditConstant;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.model.CreditConfig;
import net.evecom.platform.bsfw.model.PtspFile;
import net.evecom.platform.bsfw.service.CreditService;
import net.evecom.platform.fjfda.util.TokenUtil;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.org.tempuri.LicenseShareSoapStub;
import net.evecom.platform.org.tempuri.metadata.LicenseMetadataLocator;
import net.evecom.platform.org.tempuri.metadata.LicenseMetadataSoap_BindingStub;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DicTypeService;
import net.evecom.platform.system.service.DictionaryService;
import org.apache.axis.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 描述 政府投资项目第一阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月16日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
@PropertySource("classpath:conf/env.properties")
@Service("creditService")
public class CreditServiceImpl extends BaseServiceImpl implements CreditService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(CreditServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;
    /**
     * dicTypeService
     */
    @Resource
    private DicTypeService dicTypeService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/10 11:31:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * env
     */
    @Autowired
    private Environment env;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/12/23 9:54:00
     * @param
     * @return
     */
    @Resource
    private BdcSsdjService bdcSsdjService;

    /**
     * 描述 是否需要协调
     *
     * @return
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findCreditList(String codes, String names) {
        String sql = "select count(c.credit_id) from t_bsfw_credit c  where 1=1 ";
        List<Object> objList = new ArrayList<Object>();
        if (!StringUtils.isBlank(names)) {
            sql += " and  c.corp_name like ? ";
            objList.add("%" + names.trim() + "%");
        }
        if (!StringUtils.isBlank(codes)) {
            sql += "and (c.corp_code like ? or c.reg_num like ? or c.credit_code like ? ) ";
            objList.add("%" + codes.trim() + "%");
            objList.add("%" + codes.trim() + "%");
            objList.add("%" + codes.trim() + "%");
        }
        Map<String, Object> resultNodes = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> creditCountList = this.dao.
                    findBySql(sql, objList.toArray(), null, null);
            if (creditCountList == null || creditCountList.isEmpty() ||
                    creditCountList.size() == 0 || StringUtils.isBlank(("" + creditCountList.get(0))) ||
                    ("" + creditCountList.get(0)).equals("0")) {
                resultNodes.put("HAS_CREDIT", false);
            } else {
                if (creditCountList.size() == 1) {
                    resultNodes.put("HAS_CREDIT", true);
                }
            }
        } catch (Exception e) {
            resultNodes.put("HAS_CREDIT", false);
            log.error(e.getMessage());
        }
        return resultNodes;
    }

    /**
     * 描述
     *
     * @return
     * @author Derek Zhang
     * @created 2016年6月17日 上午9:49:53
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述
     *
     * @param filter
     * @return
     * @author Derek Zhang
     * @created 2016年6月22日 下午4:54:46
     * @see net.evecom.platform.bsfw.service.CreditService
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter, String codes, String names) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* ");
        sql.append(" FROM T_BSFW_CREDIT T where 1=1 ");
        if (!StringUtils.isBlank(names)) {
            sql.append(" and T.CORP_NAME LIKE ? ");
            params.add("%" + names.trim() + "%");
        }
        if (!StringUtils.isBlank(codes)) {
            sql.append(" and ( T.CORP_CODE LIKE ?  or T.REG_NUM LIKE ? or T.CREDIT_CODE LIKE ? ) ");
            params.add("%" + codes.trim() + "%");
            params.add("%" + codes.trim() + "%");
            params.add("%" + codes.trim() + "%");
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 获取文件名
     *
     * @param uploadPath 上传的特征路径
     * @param suffix     文件后缀名
     * @return
     */
    public String getFilePath(String uploadPath, String suffix, String fileId) {
        // 定义上传文件的保存的相对目录路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath").replace("\\", "/");
        attachFileFolderPath = attachFileFolderPath + "attachFiles/";
        //具体路径
        uploadPath += "/";
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String dirPath = attachFileFolderPath + uploadPath + currentDate;
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        fileId= UUIDGenerator.getUUID();
        String filePath = dirPath + "/" + fileId + "." + suffix;
        return filePath;
    }

    /**
     * 电子证照基本配置值
     *
     * @return
     */
    public CreditConfig getbaseConfig() {
        CreditConfig baseConfig = new CreditConfig();
        String username = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "DZZZDJ_USERNAME");
        String password = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "DZZZDJ_PASSWORD");
        String appID = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "DZZZDU_APPID");
        String businessID = dicTypeService.getDicCode("T_MSJW_SYSTEM_DICTIONARY", "DZZZDJ_BUSINESSID");
        //        String username = "sirc_cj";
        //        String password = "chenjian123";
        //        String appID = "A002";
        //        String businessID = "002";
        baseConfig.setUsername(username);
        baseConfig.setPassword(password);
        baseConfig.setAppID(appID);
        baseConfig.setBusinessID(businessID);
        return baseConfig;
    }

    /**
     * 下载文件,返回相对路径
     *
     * @param variables
     */
    public PtspFile downFile(Map<String, Object> variables) {
        String fileId = String.valueOf(variables.get("fileId"));
        String fileSuffix=CreditConstant.FILE_SUFFIX;
        String fileName = String.valueOf(variables.get("licenseName")) + "." + "edc";
        String typeCode=String.valueOf(variables.get("typeCode"));
        //获取保存的地址
        String type = variables.get("type") == null ? "other" : variables.get("type").toString();
        String uploadPath = "Evidence/" + type;
        String filePath = getFilePath(uploadPath, fileSuffix, fileId+typeCode);
        log.info("filePath==="+filePath);
        File file=new File(filePath);
        //String reltivePath = filePath.replace(attachFileFolderPath, "");
        //if(file.exists()){
        //    return reltivePath;
        //}
        if ("persona".equals(type)) {
            downLicenseFileWithPerson(variables, filePath);
        } else {
            downLicenseFileWithEnterprise(variables, filePath);
        }
        PtspFile fileInfo=uploadAndGetPtspfile(variables,fileName,file,"1");
        return fileInfo;
    }
    
    
    /**
     * 下载文件,返回相对路径
     *
     * @param variables
     */
    public PtspFile downCertFile(Map<String, Object> variables) {
        String fileId = String.valueOf(variables.get("fileId"));
        String fileName =  String.valueOf(variables.get("licenseName"));
        String itemId = variables.get("itemId") == null ? "" : variables.get("itemId").toString();
        Map<String, Object> map = this.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new String[] { itemId });
        String ITEM_CODE = map.get("SWB_ITEM_CODE") == null ? itemId : map.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = map.get("ITEM_NAME") == null ? "" : map.get("ITEM_NAME").toString();
        // 获取公共参数
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        //Map<String, Object> params = getParams("11350128345071904JGF000007","预告登记的注销","certificateShare", gcjsxmGrantCode);
        Map<String, Object> params = getParams(ITEM_CODE,ITEM_NAME,"certificateShare", gcjsxmGrantCode);
        SysUser sysUser = AppUtil.getLoginUser();
        String proposerName = "超级管理员";
        if (null != sysUser) {
            proposerName = sysUser.getUsername();
        }
        params.put("interfaceName", "getCertificateFileContentByIdentifier");
        params.put("certificateIdentifier", fileId);
        params.put("proposerName", proposerName);
        params.put("proposerCodeTypeCode", "111");
        params.put("proposerCode", "12350128MB1A16337B");
        params.put("queryCause", "电子证照高级别证书查询");
        params.put("queryResume", "查询产权证明等");
        Map<String, Object> result = TokenUtil.doPost(devbaseUrl, params);
        PtspFile fileInfo=new PtspFile();
        if (null != result && result.size() > 0) {
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            //String message = headMap.get("message") == null ? "" : headMap.get("message").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                String content = dataMap.get("content") == null ? "" : dataMap.get("content").toString();
                String fileType = dataMap.get("fileType") == null ? ""
                        : dataMap.get("fileType").toString();
                // 上传证照文件
                String busTableName = (String) Optional.ofNullable(variables.get("busTableName")).orElse("T_BSFW_PTJINFO");
                String busRecordId = (String) Optional.ofNullable(variables.get("busRecordId")).orElse("");
                String fileResult = uploadBase64File(content,fileName, fileType, busTableName, busRecordId,fileId);
                Map<String, Object> respMap = (Map) JSON.parse(fileResult);
                boolean  flag=Boolean.parseBoolean(StringUtil.getString(respMap.get("success")));
                if(flag==true){
                    String data=StringUtil.getString(respMap.get("data"));
                    fileInfo=JSONObject.parseObject(data,PtspFile.class);
                }
            } 
        } 
        return fileInfo;
    }    
    

    /**
     * 上传文件服务器并获取Ptspfile对象
     * @param fileName
     * @param file
     */
    public PtspFile uploadAndGetPtspfile(Map<String,Object> variables,String fileName, File file,String typeId) {
        //文件服务器上传
        String url=env.getProperty("ptzhspFile.url");
        String app_id = env.getProperty("ptzhspFile.app_id");// 分配的用户名
        String password = env.getProperty("ptzhspFile.password");// 分配的密码
        String responesCode = env.getProperty("ptzhspFile.responesCode");// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "1");// 上传人ID
        param.put("uploaderName","1"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        param.put("name", fileName);// 上传附件名
        param.put("busTableName", Optional.ofNullable(variables.get("busTableName")).orElse("T_BSFW_PTJINFO"));// 业务表名
        param.put("busRecordId",Optional.ofNullable(variables.get("busRecordId")).orElse(""));// 业务表ID
        param.put("CREADIT_FILEID",Optional.ofNullable(variables.get("fileId")).orElse(""));
        String respResult= HttpRequestUtil.sendFilePost(url,file,responesCode,app_id,password,param);
        log.info("respResult="+respResult);
        Map<String, Object> respMap = (Map) JSON.parse(respResult);
        boolean  flag=Boolean.parseBoolean(StringUtil.getString(respMap.get("success")));
        PtspFile fileInfo=new PtspFile();
        if(flag==true){
            String data=StringUtil.getString(respMap.get("data"));
            fileInfo=JSONObject.parseObject(data,PtspFile.class);
        }
        return fileInfo;
    }
    /**
     * 上传baseCode64服务器并获取Ptspfile对象
     * @param fileName
     */
    public PtspFile uploadBase64AndGetPtspfile(String fileName,String baseCode64,String typeId) {
        //文件服务器上传
        String url=env.getProperty("ptzhspFile.url");
        String app_id = env.getProperty("ptzhspFile.app_id");// 分配的用户名
        String password = env.getProperty("ptzhspFile.password");// 分配的密码
        String responesCode = env.getProperty("ptzhspFile.responesCode");// 编码
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploaderId", "1");// 上传人ID
        param.put("uploaderName","1"); // 上传人姓名
        param.put("typeId", "0");// 上传类型ID，默认0
        param.put("name", fileName);// 上传附件名
        param.put("busTableName", "1");// 业务表名
        param.put("busRecordId", "1");// 业务表ID
        String respResult= HttpRequestUtil.sendBase64FilePost(url,baseCode64,responesCode,app_id,password,param);
        Map<String, Object> respMap = (Map) JSON.parse(respResult);
        String  flag=StringUtil.getString(respMap.get("success"));
        PtspFile fileInfo=new PtspFile();
        if("true".equals(flag)){
            String data=StringUtil.getString(respMap.get("data"));
            fileInfo=JSONObject.parseObject(data,PtspFile.class);
        }
        return fileInfo;
    }

    /**
     * 描述:根据用户id获取信息
     *
     * @author Madison You
     * @created 2020/11/26 10:30:00
     * @param
     * @return
     */
    @Override
    public void getUserInfoByUserId(Map<String, Object> clientInfoMap, Map<String, Object> requestMap) {
        String aheadUserId = StringUtil.getValue(requestMap, "AHEAD_USER_ID");
        String backUserId = StringUtil.getValue(requestMap, "BACK_USER_ID");
        if (StringUtils.isNotEmpty(aheadUserId)) {
            Map<String, Object> userMap = dao.getMapBySql("SELECT YHZH,YHMC FROM T_BSFW_USERINFO WHERE USER_ID = ?",
                    new Object[]{aheadUserId});
            clientInfoMap.put("operName", StringUtil.getValue(userMap, "YHMC"));
            clientInfoMap.put("operId", StringUtil.getValue(userMap, "YHZH"));
            clientInfoMap.put("deptName", "办事群众部门");
            clientInfoMap.put("deptCode", "12350128MB1A16337B");
        } else if (StringUtils.isNotEmpty(backUserId)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select A.USERNAME,A.FULLNAME,A.DEPART_ID from t_msjw_system_sysuser A WHERE A.USER_ID = ? ");
            Map<String, Object> userMap = dao.getMapBySql(sql.toString(), new Object[]{backUserId});
            clientInfoMap.put("operName", StringUtil.getValue(userMap, "FULLNAME"));
            clientInfoMap.put("operId", StringUtil.getValue(userMap, "USERNAME"));
            /*判断部门*/
            String departId = StringUtil.getValue(userMap, "DEPART_ID");
            Map<String, Object> departMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT", 
                    new String[]{"DEPART_ID"}, new Object[]{departId});
            String treeLevel = StringUtil.getValue(departMap, "TREE_LEVEL");
            if (Objects.equals(treeLevel, "4")) {
                String parentId = StringUtil.getValue(departMap, "PARENT_ID");
                Map<String, Object> parentDepartMap = dao.getByJdbc("T_MSJW_SYSTEM_DEPARTMENT",
                        new String[]{"DEPART_ID"}, new Object[]{parentId});
                clientInfoMap.put("deptName", StringUtil.getValue(parentDepartMap, "DEPART_NAME"));
                clientInfoMap.put("deptCode", StringUtil.getValue(parentDepartMap, "USC"));
            } else if (Objects.equals(treeLevel, "3")) {
                clientInfoMap.put("deptName", StringUtil.getValue(departMap, "DEPART_NAME"));
                clientInfoMap.put("deptCode", StringUtil.getValue(departMap, "USC"));
            }
            String usc = StringUtil.getValue(clientInfoMap, "deptCode");
            if (StringUtils.isEmpty(usc)) {
                clientInfoMap.put("deptName", "行政服务中心");
                clientInfoMap.put("deptCode", "12350128MB1A16337B");
            }

        }
    }

    /**
     * 电子证照Sm4数据解密
     * @param inText
     * @return
     */
    public String decryptCreditData(String inText){
        Sm4Utils sm4Utils=new Sm4Utils(CreditConstant.SECRE_KEY,"",false);
        String decrtptText=sm4Utils.decryptDataECBByCharsetName(inText,"GBK");
        return decrtptText;
    }
    /**
     * 个人下载电子证照(新）
     *
     */
    private void downLicenseFileWithPerson(Map<String,Object> variable, String filePath) {
        FileOutputStream fos = null;
        Map<String,Object> data=new HashMap<String,Object>();
        try {
            //data.put("ownerCode",StringUtil.getString(variable.get("queryCode")));
            //data.put("ownerName",StringUtil.getString(variable.get("queryName")));
            data.put("type","证照");
            data.put("itemName",StringUtil.getString(variable.get("itemName")));
            data.put("itemId",StringUtil.getString(variable.get("itemId")));
            data.put("transactor",StringUtil.getString(variable.get("transactor")));
            data.put("fileNumber",StringUtil.getString(variable.get("fileId")));
            String base64File = getDataFormInterface(data,CreditConstant.P_FILE_INTERFACEID);
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64File);
            fos = new FileOutputStream(filePath);
            fos.write(buffer);
        } catch (AxisFault axisFault) {
            log.info(axisFault.getMessage());
        } catch (RemoteException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }
    /**
     * 个人下载电子证照
     *
     * @param fileId
     * @param filePath
     */
    private void downLicenseFileWithPerson(String fileId, String filePath) {
        //接口调用
        CreditConfig baseConfig = getbaseConfig();
        LicenseShareSoapStub stub;
        String guid;
        FileOutputStream fos = null;
        try {
            stub = new LicenseShareSoapStub();
            guid = stub.authorization(baseConfig.getUsername(), baseConfig.getPassword());
            String requesXml = getRequestXml(fileId);
            byte[] buffer = stub.getLicenseFileByQueryOfPerson(guid, requesXml);
            fos = new FileOutputStream(filePath);
            fos.write(buffer);
        } catch (AxisFault axisFault) {
            log.info(axisFault.getMessage());
        } catch (RemoteException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }

    /**
     * 企业下载电子证照
     *
     * @param filePath
     */
    private void downLicenseFileWithEnterprise(Map<String, Object> variable, String filePath) {
        FileOutputStream fos = null;
        Map<String,Object> data=new HashMap<String,Object>();
        data.put("itemName",StringUtil.getString(variable.get("itemName")));
        data.put("itemId",StringUtil.getString(variable.get("itemId")));
        data.put("transactor",StringUtil.getString(variable.get("transactor")));
        data.put("type","证照");
        data.put("fileNumber",StringUtil.getString(variable.get("fileId")));
        int interfaceId;
        if(StringUtils.isNotEmpty(StringUtil.getString(variable.get("queryCode")))){
            interfaceId=CreditConstant.E_FILE_INTERFACEID_CODE;
            //data.put("ownerCode",StringUtil.getString(variable.get("queryCode")));
        }else{
            interfaceId=CreditConstant.E_FILE_INTERFACEID_NAME;
            //data.put("ownerName",StringUtil.getString(variable.get("queryName")));
        }
        String base64File = getDataFormInterface(data,interfaceId);
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64File);
            fos = new FileOutputStream(filePath);
            fos.write(buffer);
        } catch (AxisFault axisFault) {
            log.info(axisFault.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.info(e.getMessage());
                }
            }
        }
    }

    /**
     * 获取公众用户文件下载的请求xml
     *
     * @return
     */
    public String getRequestXml(String fileId) {
        CreditConfig creditConfig = getbaseConfig();
        Document document = DocumentHelper.createDocument();
        Element request = document.addElement("Request");
        Element message = request.addElement("Message");
        Element bussinessInfo = message.addElement("BusinessInfo");
        bussinessInfo.addAttribute("AppID", creditConfig.getAppID());
        bussinessInfo.addAttribute("BusinessID", creditConfig.getBusinessID());
        Element info = message.addElement("Info");
        info.addAttribute("Type", "电子证照");
        Element typeCode = info.addElement("TypeCode");
        Element node = typeCode.addElement("Node");
        node.addAttribute("FileID", fileId);
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(document);
            writer.close();
            output.close();
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
        }
        String requestXml = writer.toString();
        return requestXml;
    }

    /**
     * 上传文件
     *
     * @param variables
     */
    public List<Map<String, Object>> uploadFile(Map<String, Object> variables) {
        //PtspFile fileInfo=downFile(variables);
        PtspFile fileInfo=downCertFile(variables);
        String attachKey = String.valueOf(variables.get("attachKey"));
        //返回值
        List<Map<String, Object>> resultlList = new ArrayList<Map<String, Object>>();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("fileId", fileInfo.getFileId());
        result.put("fileName", fileInfo.getFileName());
        result.put("attachKey",attachKey);
        result.put("filePath", fileInfo.getFilePath());
        resultlList.add(result);
        return resultlList;
    }

    /**
     * 获取机构列表
     *
     * @return
     */
    public List<Map<String, Object>> getOrgList() {
        return dao.findAllByTableName("T_EVIDENCE_ORG");
    }

    /**
     * 保存接口获取机构名称
     *
     * @return
     */
    public void saveOrgListByInterface() {
        LicenseMetadataLocator service = new LicenseMetadataLocator();
        LicenseMetadataSoap_BindingStub meta;
        try {
            meta = (LicenseMetadataSoap_BindingStub) service.getLicenseMetadataSoap();
            String guid = getGuid();
            String orgs = meta.getOrgNameList(guid);
            Document doc = DocumentHelper.parseText(orgs);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            //删除表数据
            if (list.size() > 1) {
                deleteTable("T_EVIDENCE_ORG");
                deleteTable("T_EVIDENCE_META");
            }
            for (Element e : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                String name = e.attributeValue("name");
                String code = e.attributeValue("code");
                String areacode = e.attributeValue("areacode");
                map.put("NAME", name);
                map.put("CODE", code);
                map.put("AREACODE", areacode);
                dao.saveOrUpdate(map, "T_EVIDENCE_ORG", "");
                saveEvidenceMetaByInterface(name);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 删除表数据
     *
     * @param tableName
     */
    private void deleteTable(String tableName) {
        StringBuffer sql = new StringBuffer("delete from ");
        sql.append(tableName).append(" where 1=1");
        dao.executeSql(sql.toString(), null);
    }

    /**
     * 获取证照目录
     *
     * @param orgName
     * @return
     */
    public List<Map<String, Object>> getEvidenceMeta(String orgName) {
        List<Map<String, Object>> metas = dao.getAllByJdbc("T_EVIDENCE_META",
                new String[]{"ORGNAME"}, new Object[]{orgName});
        return metas;
    }

    /**
     * 保存接口获取证照目录信息
     *
     * @param orgName
     * @return
     */
    public void saveEvidenceMetaByInterface(String orgName) {
        String guid = getGuid();
        LicenseMetadataLocator service = new LicenseMetadataLocator();
        LicenseMetadataSoap_BindingStub meta = null;
        String metaDatas = "";
        try {
            meta = (LicenseMetadataSoap_BindingStub) service.getLicenseMetadataSoap();
            metaDatas = meta.getMetadataList(guid, orgName);
            Document doc1 = DocumentHelper.parseText(metaDatas);
            Element root1 = doc1.getRootElement();
            List<Element> list1 = root1.elements();
            for (Element e : list1) {
                String typeCode = e.attributeValue("Type");
                if ("证照".equals(typeCode)) {
                    List<Element> list2 = e.elements();
                    for (Element e2 : list2) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        String name = e2.attributeValue("Name");
                        String code = e2.attributeValue("TypeCode");
                        String id = e2.attributeValue("id");
                        map.put("CODE", code);
                        map.put("NAME", name);
                        map.put("ID", id);
                        map.put("ORGNAME", orgName);
                        dao.saveOrUpdate(map, "T_EVIDENCE_META", "");
                    }
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 通过目录接口获得guid
     *
     * @return
     */
    public String getGuid() {
        CreditConfig creditConfig = getbaseConfig();
        String username=creditConfig.getUsername();
        String password=creditConfig.getPassword();
        LicenseMetadataLocator service = new LicenseMetadataLocator();
        LicenseMetadataSoap_BindingStub meta = null;
        String guid = "";
        try {
            meta = (LicenseMetadataSoap_BindingStub) service.getLicenseMetadataSoap();
            guid = meta.authorization(username, password);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return guid;
    }

    /**
     * 通过共享接口获得guid
     *
     * @return
     */
    private String getGuidByShareIF(LicenseShareSoapStub stub) {
        CreditConfig creditConfig = getbaseConfig();
        String username=creditConfig.getUsername();
        String password=creditConfig.getPassword();
        String guid = "";
        try {
            guid = stub.authorization(username, password);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return guid;
    }

    /**
     * 获取电子证照树jsonStr
     *
     * @return
     */
    public String getEviditTreeJson(Map<String, Object> variables) {
        String isShowRoot = (String) variables.get("isShowRoot");
        String needCheckIds = (String) variables.get("needCheckIds");
        //String rootParentId = variables.get("rootParentId") == null ? "0" : variables.get("rootParentId").toString();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", "0");
        root.put("name", "类别结构树");
        root.put("open", true);
        root.put("PARENT_ID", -1);
        root.put("TREE_LEVEL", 1);
        root.put("nocheck", true);
        List<Map<String, Object>> toplist = getEveditListByShareIF(needCheckIds);
        String json = "";
        if (isShowRoot.equals("true")) {
            root.put("children", toplist);
            json = JSON.toJSONString(root);
        } else {
            json = JSON.toJSONString(toplist);
        }
        return json;
    }

    /**
     * 通过共享接口getLicenseType获得电子证照list
     *
     * @return
     */
    private List<Map<String, Object>> getEveditListByShareIF(String needCheckIds) {
        List<Map<String, Object>> orgList = new ArrayList<Map<String, Object>>();
        String licenseType = "";
        try {
            Map<String,Object> data=new HashMap<String,Object>();
            data.put("licenseTypeName","");
            licenseType = getDataFormInterface(data,CreditConstant.TYPE_INTERFACEID);
            log.info("返回电子证照数据：" + licenseType);
            Document doc = DocumentHelper.parseText(licenseType.toString());
            Element licenseTypeTree = doc.getRootElement();
            List<Element> list = licenseTypeTree.elements();
            //机构名称
            for (Element e : list) {
                Map<String, Object> org = new HashMap<String, Object>();
                String orgName = e.getName();
                org.put("id", orgName);
                org.put("code", orgName);
                org.put("name", orgName);
                org.put("nocheck", true);
                org.put("children", this.getChildrenList(needCheckIds, e));
                orgList.add(org);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return orgList;
    }

    /**
     * 获取子类证照list
     *
     * @param needCheckIds
     * @param bigOfType
     * @return
     */
    private List<Map<String, Object>> getChildrenList(String needCheckIds, Element bigOfType) {
        List<Element> smallOfTypeList = bigOfType.elements();
        //证照子类
        List<Map<String, Object>> smallList = new ArrayList<Map<String, Object>>();
        for (Element smallOfType : smallOfTypeList) {
            Map<String, Object> small = new HashMap<String, Object>();
            String smallOfTypeName = String.valueOf(smallOfType.attributeValue("name"));
            String smallOfTypeCode = String.valueOf(smallOfType.attributeValue("code"));
            small.put("id", smallOfTypeCode);
            small.put("code", smallOfTypeCode);
            small.put("name", smallOfTypeName);
            if (needCheckIds.contains(smallOfTypeCode)) {
                small.put("checked", true);
            }
            List<Element> childEle = smallOfType.elements();
            if (childEle.size() > 0) {
                small.put("children", this.getChildrenList(needCheckIds, smallOfType));
            }
            smallList.add(small);
        }
        return smallList;
    }

    /**
     * 获取电子证照信息列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> findDatagrid(Map<String, Object> variables) {
        String licenseListString = getLicenceString(variables);
        //String type = variables.get("TYPE") == null ? "" : variables.get("TYPE").toString();
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(licenseListString)) {
            List<Map> licenseList = JSONArray.parseArray(licenseListString, Map.class);
                for (Map<String,Object> map : licenseList) {
                    map.put("type", map.get("certificateTypeCode"));
                    map.put("FileID", map.get("certificateIdentifier"));
                    map.put("LicenseID", map.get("certificateNumber"));
                    map.put("Name", map.get("certificateName"));
                    map.put("TypeCode", map.get("certificateTypeCode"));
                    map.put("IsExpired", "否");
                    map.put("DataTime", map.get("certificateIssuedDate"));
                    map.put("FileSuffix",map.get("certificateNumber"));
                    returnList.add(map);
                }
        }

        return returnList;
    }

    /**
     * 通过接口获得返回的证照返回值
     * @param variables
     * @return
     */
    private String getLicenceString(Map<String, Object> variables) {
        String type = variables.get("TYPE") == null ? "" : variables.get("TYPE").toString();
        String codes = variables.get("CORP_CODE") == null ? "" : variables.get("CORP_CODE").toString();
        String companyCode = codes;
        String licenseListString = "";
        try {
            if ("persona".equals(type)) {
                licenseListString = getPersonListByHolder(variables);
            } else if (StringUtils.isNotEmpty(companyCode)) {
                licenseListString = getLegalPersonListByHolderCode(variables);
            } else {
                licenseListString = getLegalPersonListByHolderName(variables);
            }
            log.info("返回电子证照数据：" + licenseListString);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return licenseListString;
    }

    /**
     * 从新证照接口获取数据
     * @param data
     * @param interfaceID
     * @return
     */
    public String getDataFormInterface(Map<String,Object> data,int interfaceID){
        String itemId = StringUtil.getValue(data, "itemId");
        Properties properties = FileUtil.readProperties("project.properties");
        String method = dictionaryService.getDicNames("creditInterface",
                String.valueOf(interfaceID));
        String url = properties.getProperty("CREDIT_PATH_URL") + method;
        String creditToken = getCreditToken(itemId);
        String queryResult="";
        // 实例化httpPost
        HttpPost httpPost = new HttpPost(url);
        //封装头部
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setHeader("Authorization",env.getProperty("dzzz.AUTHORIZATION"));
        httpPost.setHeader("Connection","keep-alive");
        //CloseableHttpClient client = HttpClients.createDefault();
        //公共子data数据封装
        data.put("serialnumber",Optional.ofNullable(data.get("serialnumber")).orElse(""));
        data.put("transactor",Optional.ofNullable(data.get("transactor")).orElse(""));
        if (StringUtils.isNotEmpty(itemId)) {
            Map<String, Object> itemMap = dao.getByJdbc("T_WSBS_SERVICEITEM",
                    new String[]{"ITEM_CODE"}, new Object[]{itemId});
            data.put("itemName", StringUtil.getValue(itemMap, "ITEM_NAME"));
        } else {
            data.put("itemName", "");
        }
        data.put("itemId",itemId);
        // 解决中文乱码问题
        JSONObject jsonParam = new JSONObject(data);
        log.info("电子证照发送参数为：" + jsonParam.toJSONString());
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization", creditToken);
        String departName = bdcSsdjService.getItemChildDeptByItemCode(itemId);
        if (StringUtils.isNotEmpty(departName)) {
            headerMap.put("re-app", departName);
        }
        String result = HttpSendUtil.sendHttpPostJson(url, headerMap, jsonParam.toJSONString(), "UTF-8");
        if (StringUtils.isNotEmpty(result)) {
            Map<String, Object> jsonMap = JSON.parseObject(result, Map.class);
            String code = StringUtil.getValue(jsonMap, "code");
            if (Objects.equals(code, "00")) {
                queryResult = StringUtil.getValue(jsonMap, "data");
            }
        }
        return queryResult;
    }

    /**
     * 从新证照接口获取数据
     * @param data
     * @param interfaceID
     * @return
     */
    public String getDataFromInterfaceByconn(Map<String,Object> data,int interfaceID){
        String url=env.getProperty("dzzz.CREDIT_URL")+interfaceID;
        log.info("电子证照url=="+url);
        PrintWriter out = null;
        BufferedReader in = null;
        DataInputStream inFile = null;
        BufferedReader reader = null;
        StringBuffer result = new StringBuffer("");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization",env.getProperty("dzzz.AUTHORIZATION"));
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            //para数据json封装
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("systemID", env.getProperty("dzzz.SYSTEM_ID"));
            jsonParam.put("interfaceID",interfaceID);
            jsonParam.put("username",env.getProperty("dzzz.username"));
            jsonParam.put("password",env.getProperty("dzzz.password"));
            //公共子data数据封装
            data.put("appid",env.getProperty("dzzz.APP_ID"));
            data.put("businessid",env.getProperty("dzzz.BUSINESS_ID"));
            data.put("serialnumber",Optional.ofNullable(data.get("serialnumber")).orElse(""));
            data.put("transactor",Optional.ofNullable(data.get("transactor")).orElse(""));
            data.put("itemName",Optional.ofNullable(data.get("itemName")).orElse(""));
            data.put("itemId",Optional.ofNullable(data.get("itemId")).orElse(""));
            jsonParam.put("data",data);
            out.print(jsonParam.toJSONString());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (inFile != null) {
                    inFile.close();
                }
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("", ex);
            }
        }
        return result.toString();
    }
    /**
     * 电子证照返回结果解析json数据
     * @param respContent
     * @return
     * @throws Exception
     */
    private String getResultXmlByRespContent(String respContent) {
        String resultXml = "";
        if(respContent.length()>2) {
            int end = respContent.length() > 1000 ? 1000 : respContent.length() - 2;
            log.info("respContentresp==="+respContent.substring(0, end));
        }else{
            log.info("respContent====="+respContent);
        }
        if (StringUtils.isNotEmpty(respContent)) {
            Map<String, Object> respMap = (Map) JSON.parse(respContent);
            String result = String.valueOf(respMap.get("result"));
            Map<String, Object> resultMap = (Map) JSON.parse(result);
            String code = StringUtil.getString(resultMap.get("code"));
            log.info("code=="+code);
            // 00查询结果正常
            if ("00".equals(code)) {
                String data = String.valueOf(resultMap.get("data"));
                resultXml = decryptCreditData(data);
            }
        }
        return resultXml;
    }

    /**
     * 根据企业获得requestXml
     * @param holder
     * @param licenceCode
     * @param type （10代表企业机构代码，11代表企业名称）
     * @return
     */
    private String getXmlByHolderAndLicenceCode(String holder,String licenceCode,String type){
        CreditConfig creditConfig = getbaseConfig();
        Document document = DocumentHelper.createDocument();
        Element request = document.addElement("Request");
        Element message = request.addElement("Message");
        Element apply = message.addElement("Apply");
        apply.addAttribute("Type",type);
        apply.addAttribute("Value",holder);
        Element bussinessInfo = message.addElement("BusinessInfo");
        bussinessInfo.addAttribute("AppID", creditConfig.getAppID());
        bussinessInfo.addAttribute("BusinessID", creditConfig.getBusinessID());
        Element info = message.addElement("Info");
        info.addAttribute("Type", "电子证照");
        Element info1 = message.addElement("Info");
        info1.addAttribute("Type", "电子批文");
        Element typeCode = info.addElement("TypeCode");

        if(StringUtils.isNotEmpty(licenceCode)){
            String[] codes=licenceCode.split(",");
            for(String code:codes){
                Element node = typeCode.addElement("Node");
                node.setText(code);
            }
        }

        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(document);
            writer.close();
            output.close();
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
        }
        String requestXml = writer.toString();
        log.info("requestXml==="+requestXml);
        return requestXml;
    }

    /**
     * 根据个人获得requestXml
     * @param name
     * @param idCard
     * @param licenceCode
     * @return
     */
    private String getXmlByPerson(String name,String idCard,String licenceCode){
        CreditConfig creditConfig = getbaseConfig();
        Document document = DocumentHelper.createDocument();
        Element request = document.addElement("Request");
        Element message = request.addElement("Message");
        Element apply = message.addElement("HolderInfo");
        apply.addAttribute("HolderID",idCard);
        apply.addAttribute("HolderName",name);
        Element bussinessInfo = message.addElement("BusinessInfo");
        bussinessInfo.addAttribute("AppID", creditConfig.getAppID());
        bussinessInfo.addAttribute("BusinessID", creditConfig.getBusinessID());
        Element info = message.addElement("Info");
        info.addAttribute("Type", "电子证照");
        Element info1 = message.addElement("Info");
        info1.addAttribute("Type", "电子批文");
        Element typeCode = info.addElement("TypeCode");

        if(StringUtils.isNotEmpty(licenceCode)){
            String[] codes=licenceCode.split(",");
            for(String code:codes){
                Element node = typeCode.addElement("Node");
                node.setText(code);
            }
        }

        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(document);
            writer.close();
            output.close();
        } catch (Exception e) {
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("Catch an exception!", e);
                }
            }
        }
        String requestXml = writer.toString();
        return requestXml;
    }

    /**
     * 描述:获取电子证照token
     *
     * @author Madison You
     * @created 2020/12/10 11:13:00
     * @param
     * @return
     */
    private String getCreditToken(String itemCode) {
        String token = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String username = properties.getProperty("SSDJ_LOGIN_USERNAME");
        String password = properties.getProperty("SSDJ_LOGIN_PASSWORD");
        String tokenUrl = properties.getProperty("CREDIT_TOKEN_URL");
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("username", username);
        jsonMap.put("password", password);
        JSONObject json = new JSONObject(jsonMap);
        Map<String,String> headerMap = new HashMap<>();
        String departName = bdcSsdjService.getItemChildDeptByItemCode(itemCode);
        if (StringUtils.isNotEmpty(departName)) {
            headerMap.put("re-app", departName);
        }
        try{
            String result = HttpSendUtil.sendHttpPostJson(tokenUrl, null,
                    json.toJSONString(), "UTF-8");
            if (StringUtils.isNotEmpty(result)) {
                Map<String,Object> bodyMap = JSON.parseObject(result, Map.class);
                token = StringUtil.getValue(bodyMap, "token");
            }
        } catch (Exception e) {
            log.error("新电子证照接口认证登录失败", e);
        }
        return token;
    }
    
    /**
     * 
     * @Description 根据个人持证者代码和名称获取该持证者的电子证照列表
     * @author Luffy Cai
     * @date 2021年6月24日
     * @param map
     * @return Map<String,Object>
     */
    public String getPersonListByHolder(Map<String, Object> data) {
        Map<String, Object> result = null;
        String dataList = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        String itemId = data.get("itemId") == null ? "" : data.get("itemId").toString();
        String CORP_CODE = data.get("CORP_CODE") == null ? "" : data.get("CORP_CODE").toString();
        String CORP_NAME = data.get("CORP_NAME") == null ? "" : data.get("CORP_NAME").toString();
        String IS_HIGH_LEVEL = data.get("IS_HIGH_LEVEL") == null ? "" : data.get("IS_HIGH_LEVEL").toString();//高级别证照
        Map<String, Object> map = this.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new String[] { itemId });
        String ITEM_CODE = map.get("SWB_ITEM_CODE") == null ? itemId : map.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = map.get("ITEM_NAME") == null ? "" : map.get("ITEM_NAME").toString();
        if (StringUtils.isNotEmpty(CORP_CODE) && StringUtils.isNotEmpty(CORP_NAME)) {
            // 获取公共参数
            //Map<String, Object> params = getParams("11350128345071904JGF000007","预告登记的注销","certificateShare", gcjsxmGrantCode);
            Map<String, Object> params = getParams(ITEM_CODE,ITEM_NAME,"certificateShare", gcjsxmGrantCode);
            params.put("interfaceName", "getPersonListByHolder");
            params.put("certificateHolderCode", CORP_CODE);
            params.put("certificateHolderName", CORP_NAME);
            if(StringUtils.isNotEmpty(IS_HIGH_LEVEL)) {
                String HIGH_CREDIT_NAME = data.get("HIGH_CREDIT_NAME") == null ? "" : data.get("HIGH_CREDIT_NAME").toString();
                String HIGH_CREDIT_CODE = data.get("HIGH_CREDIT_CODE") == null ? "" : data.get("HIGH_CREDIT_CODE").toString();
                SysUser sysUser = AppUtil.getLoginUser();
                String proposerName = sysUser.getUsername();
                if(StringUtils.isEmpty(proposerName)) {
                    proposerName="超级管理员";
                }
                params.put("certificateTypeName", HIGH_CREDIT_NAME);
                params.put("certificateNumber", HIGH_CREDIT_CODE);
                params.put("proposerName", proposerName);
                params.put("proposerCodeTypeCode", "111");
                params.put("proposerCode", "12350128MB1A16337B");
                params.put("queryCause", "电子证照高级别证书查询");
                params.put("queryResume", "查询产权证明等");
            }
            result = TokenUtil.doPost(devbaseUrl, params);
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                dataList = dataMap.get("dataList") == null ? "" : dataMap.get("dataList").toString();
            }
        }
        return dataList;
    }

    /**
     * 
     * @Description 根据法人持证者代码获取该持证者的电子证照列表
     * @author Luffy Cai
     * @date 2021年6月24日
     * @param map
     * @return Map<String,Object>
     */
    public String getLegalPersonListByHolderCode(Map<String, Object> data) {
        // TODO Auto-generated method stub
        Map<String, Object> result = null;
        String dataList = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        String itemId = data.get("itemId") == null ? "" : data.get("itemId").toString();
        String IS_HIGH_LEVEL = data.get("IS_HIGH_LEVEL") == null ? "" : data.get("IS_HIGH_LEVEL").toString();//高级别证照
        Map<String, Object> map = this.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new String[] { itemId });
        String ITEM_CODE = map.get("SWB_ITEM_CODE") == null ? itemId : map.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = map.get("ITEM_NAME") == null ? "" : map.get("ITEM_NAME").toString();
        String certificateHolderCode = data.get("CORP_CODE")==null?"":data.get("CORP_CODE").toString();
        if (StringUtils.isNotEmpty(certificateHolderCode)) {
            // 获取公共参数
            //Map<String, Object> params = getParams("11350128345071904JGF000007","预告登记的注销","certificateShare", gcjsxmGrantCode);
            Map<String, Object> params = getParams(ITEM_CODE,ITEM_NAME,"certificateShare", gcjsxmGrantCode);
            params.put("interfaceName", "getLegalPersonListByHolderCode");
            params.put("certificateHolderCode", certificateHolderCode);
            if(StringUtils.isNotEmpty(IS_HIGH_LEVEL)) {//高级别证照获取
                String HIGH_CREDIT_NAME = data.get("HIGH_CREDIT_NAME") == null ? "" : data.get("HIGH_CREDIT_NAME").toString();
                String HIGH_CREDIT_CODE = data.get("HIGH_CREDIT_CODE") == null ? "" : data.get("HIGH_CREDIT_CODE").toString();
                SysUser sysUser = AppUtil.getLoginUser();
                String proposerName = sysUser.getUsername();
                if(StringUtils.isEmpty(proposerName)) {
                    proposerName="超级管理员";
                }
                params.put("certificateTypeName", HIGH_CREDIT_NAME);
                params.put("certificateNumber", HIGH_CREDIT_CODE);
                params.put("proposerName",proposerName);
                params.put("proposerCodeTypeCode", "111");
                params.put("proposerCode", "12350128MB1A16337B");
                params.put("queryCause", "电子证照高级别证书查询");
                params.put("queryResume", "查询产权证明等");
            }
            result = TokenUtil.doPost(devbaseUrl, params);
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                dataList = dataMap.get("dataList") == null ? "" : dataMap.get("dataList").toString();
            }
        }
        return dataList;
    }

    /**
     * 
     * @Description 根据法人持证者名称获取该持证者的电子证照列表
     * @author Luffy Cai
     * @date 2021年6月24日
     * @param map
     * @return Map<String,Object>
     */
    public String getLegalPersonListByHolderName(Map<String, Object> data) {
        Map<String, Object> result = null;
        String dataList = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        String certificateHolderName = data.get("CORP_NAME")==null?"":data.get("CORP_NAME").toString();
        String itemId = data.get("itemId") == null ? "" : data.get("itemId").toString();
        String IS_HIGH_LEVEL = data.get("IS_HIGH_LEVEL") == null ? "" : data.get("IS_HIGH_LEVEL").toString();//高级别证照
        Map<String, Object> map = this.getByJdbc("T_WSBS_SERVICEITEM", new String[] { "ITEM_CODE" },
                new String[] { itemId });
        String ITEM_CODE = map.get("SWB_ITEM_CODE") == null ? itemId : map.get("SWB_ITEM_CODE").toString();
        String ITEM_NAME = map.get("ITEM_NAME") == null ? "" : map.get("ITEM_NAME").toString();
        if (StringUtils.isNotEmpty(certificateHolderName)) {
            // 获取公共参数
            //Map<String, Object> params = getParams("11350128345071904JGF000007","预告登记的注销","certificateShare", gcjsxmGrantCode);
            Map<String, Object> params = getParams(ITEM_CODE,ITEM_NAME,"certificateShare", gcjsxmGrantCode);
            params.put("interfaceName", "getLegalPersonListByHolderName");
            params.put("certificateHolderName", certificateHolderName);
            if(StringUtils.isNotEmpty(IS_HIGH_LEVEL)) {
                String HIGH_CREDIT_NAME = data.get("HIGH_CREDIT_NAME") == null ? "" : data.get("HIGH_CREDIT_NAME").toString();
                String HIGH_CREDIT_CODE = data.get("HIGH_CREDIT_CODE") == null ? "" : data.get("HIGH_CREDIT_CODE").toString();
                SysUser sysUser = AppUtil.getLoginUser();
                String proposerName = sysUser.getUsername();
                if(StringUtils.isEmpty(proposerName)) {
                    proposerName="超级管理员";
                }
                params.put("certificateTypeName", HIGH_CREDIT_NAME);
                params.put("certificateNumber", HIGH_CREDIT_CODE);
                params.put("proposerName", proposerName);
                params.put("proposerCodeTypeCode", "111");
                params.put("proposerCode", "12350128MB1A16337B");
                params.put("queryCause", "电子证照高级别证书查询");
                params.put("queryResume", "查询产权证明等");
            }
            result = TokenUtil.doPost(devbaseUrl, params);
            Map<String, Object> headMap = (Map<String, Object>) result.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
                Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
                dataList = dataMap.get("dataList") == null ? "" : dataMap.get("dataList").toString();
            }
        }
        return dataList;
    }   
    
    
    /**
     * 
     * 描述 获取公共的参数
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午5:59:50
     * @param exeId
     * @param servicecode
     * @param gcjsxmGrantCode
     * @return
     */
    private Map<String, Object> getParams(String item_code, String item_name,String servicecode, String gcjsxmGrantCode) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("servicecode", servicecode);
        params.put("grantcode", gcjsxmGrantCode);
        params.put("areaCode", "35012800000");
        params.put("areaName", "平潭综合实验区");
        params.put("deptName", "平潭综合实验区行政审批局");
        params.put("deptCode", "11350128345071904J");
        params.put("operName", "evecom");
        params.put("operId", "evecom");
        params.put("systemName", "平潭综合实验区综合服务平台");
        params.put("taskCode", item_code);
        params.put("taskName", item_name);
        params.put("projectNo", item_code);
        return params;
    }   
    
    /**
     * 
     * 描述 Base64文件上传
     * 
     * @author Rider Chen
     * @created 2021年3月16日 下午4:19:30
     * @param base64Code
     * @param fileType
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public String uploadBase64File(String base64Code,String fileName ,String fileType, String busTableName, String busRecordId,String certificateIdentifier) {
        Properties properties = FileUtil.readProperties("project.properties");
        String uploadFileUrl = properties.getProperty("uploadFileUrlIn");
        String url = uploadFileUrl + "upload/file";// 上传路径
        String result = "";
        if (StringUtils.isNotEmpty(base64Code)) {
            Map<String, Object> param;
            try {
                String app_id = "0001";// 分配的用户名
                String password = "bingo666";// 分配的密码
                String responesCode = "UTF-8";// 编码
                param = new HashMap<String, Object>();
                param.put("uploaderId", "ddzzwjsc");// 上传人ID
                param.put("uploaderName", "电子证照文件上传"); // 上传人姓名
                param.put("typeId", "0");// 上传类型ID，默认0
                param.put("name", fileName + "." + fileType);// 上传附件名
                param.put("attachKey", "");// 材料编码
                param.put("busTableName", busTableName);// 业务表名
                param.put("busRecordId", busRecordId);// 业务表ID
                param.put("CREADIT_FILEID",certificateIdentifier);
                result = HttpRequestUtil.sendBase64FilePost(url, base64Code, responesCode, app_id, password, param);
                return result;
            } catch (Exception e) {
                log.error("证照文件上传错误，CREADIT_FILEID：" + certificateIdentifier);
            }
        }
        return result;
    }    
    
    /**
     * 获取电子证照信息列表
     * 
     * @param variables
     * @return
     */
    public List<Map<String, Object>> getCertificateType(Map<String, Object> data) {
        Map<String, Object> result = null;
        String dataList = "";
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        // 获取公共参数
        Map<String, Object> params = getParams("", "", "certificateShare", gcjsxmGrantCode);
        params.put("interfaceName", "getCertificateType");
        // 根据证照标识获取证照信息
        result = TokenUtil.doPost(devbaseUrl, params);
        Map<String, Object> headMap = (Map<String, Object>) result.get("head");
        String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
        if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功，返回证照标识
            Map<String, Object> dataMap = (Map<String, Object>) result.get("data");
            dataList = dataMap.get("dataList") == null ? "" : dataMap.get("dataList").toString();
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty(dataList)) {
            List<Map> CertificateType = JSONArray.parseArray(dataList, Map.class);
            for (Map<String, Object> map : CertificateType) {
                if (map.get("KZ_securityClass").toString().equals("高")) {
                    String type = StringUtil.getValue(map, "certificateType");
                    Map<String,Object> creditNameMap = new HashMap<>();
                    creditNameMap.put("DIC_CODE", type);
                    creditNameMap.put("DIC_NAME", type);
                    returnList.add(creditNameMap);
                }
            }
        }
        return returnList;
    }
    
    /**
     * 获取可信文件列表信息
     * 
     * @author Scolder Lin
     * @param variables
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findElectDocumentDatagrid(Map<String, Object> variables) {
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        String projectCode = variables.get("projectCode") == null ? "" : variables.get("projectCode").toString();
        String itemCode = variables.get("itemCode") == null ? "" : variables.get("itemCode").toString();
        String itemName = variables.get("itemName") == null ? "" : variables.get("itemName").toString();
        String exeId = (variables.get("exeId") == null || "".equals(variables.get("exeId"))) ?
                    itemCode : variables.get("exeId").toString();

        String creditCode = variables.get("creditCode") == null ? "" : variables.get("creditCode").toString();
        String creditName = variables.get("creditName") == null ? "" : variables.get("creditName").toString();
        
        // 获取公共参数
        //Map<String, Object> params = getClientParams("getEfileShare", gcjsxmGrantCode);
        Map<String, Object> params = getParams("getEfileShare", gcjsxmGrantCode, itemCode, itemName, exeId);
        params.put("interfaceName", "getIndexByProjectCode");
        params.put("projectCode", projectCode);
        
        Map<String, Object> resultMap = TokenUtil.doPost(devbaseUrl, params);
        List<Map<String, Object>> electDocumentList = new ArrayList<Map<String, Object>>();
        if(resultMap!=null && resultMap.size()>0) {
            Map<String, Object> headMap = (Map<String, Object>) resultMap.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
                List<Map<String, Object>> dataList = JSON.parseObject(dataMap.get("dataList").toString(), List.class);
                if(StringUtils.isNotEmpty(creditCode) || StringUtils.isNotEmpty(creditName)) {
                    if(dataList!=null && dataList.size()>0) {
                        int index = 1;
                        for(int i=0;i<dataList.size();i++) {
                            Map<String, Object> electDocumentMap = dataList.get(i);
                            String fileNumber = (String)electDocumentMap.get("fileNumber");
                            String fileName = (String)electDocumentMap.get("fileName");
                            if(StringUtils.isNotEmpty(creditCode) && StringUtils.isNotEmpty(creditName)) {
                                if(fileNumber.contains(creditCode) && fileName.contains(creditName)) {
                                    electDocumentMap.put("serialNumber", index);
                                    electDocumentList.add(electDocumentMap);
                                    index++;
                                }
                            }else if(StringUtils.isNotEmpty(creditCode) && StringUtils.isEmpty(creditName)) {
                                if(fileNumber.contains(creditCode)) {
                                    electDocumentMap.put("serialNumber", index);
                                    electDocumentList.add(electDocumentMap);
                                    index++;
                                }
                            }else {
                                if(fileName.contains(creditName)) {
                                    electDocumentMap.put("serialNumber", index);
                                    electDocumentList.add(electDocumentMap);
                                    index++;
                                }
                            }
                        }
                    }
                }else {
                    if(dataList!=null && dataList.size()>0) {
                        for(int i=0;i<dataList.size();i++) {
                            Map<String, Object> electDocumentMap = dataList.get(i);
                            
                            electDocumentMap.put("serialNumber", i+1);
                            electDocumentList.add(electDocumentMap);
                        }
                    }
                }
            }
        }
        return electDocumentList;
    }
    
    /**
     * 
     * @Description 获取公共的参数
     * @author Scolder Lin
     * 
     * @param servicecode 
     * @param gcjsxmGrantCode
     * @param itemCode 服务事项编码
     * @param itemName 服务事项名称
     * @param exeId 申报号
     * @return Map<String,Object>
     */
    private Map<String, Object> getParams(String servicecode, String gcjsxmGrantCode,
            String itemCode, String itemName, String exeId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("servicecode", servicecode);
        params.put("grantcode", gcjsxmGrantCode);
        params.put("areaCode", "35012800000");
        params.put("areaName", "平潭综合实验区");
        params.put("deptName", "平潭综合实验区行政审批局");
        params.put("deptCode", "11350128345071904J");
        params.put("operName", "evecom");
        params.put("operId", "evecom");
        params.put("systemName", "平潭综合实验区工改业务系统");
        params.put("taskCode", itemCode);
        params.put("taskName", itemName);
        params.put("projectNo", exeId);
        return params;
    }
    
    /**
     * 根据电子文件标识获取电子文件附件列表接口
     * @author Scolder Lin
     * 
     * @param variables 前台传递的参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> uploadElectDocumentFile(Map<String, Object> variables) {
        List<Map<String, Object>> resultlList = new ArrayList<Map<String, Object>>();
        String attachKey = String.valueOf(variables.get("attachKey"));
        
        Properties properties = FileUtil.readProperties("project.properties");
        String devbaseUrl = properties.getProperty("devbaseUrl");
        String gcjsxmGrantCode = properties.getProperty("gcjsxmGrantCode");
        
        String fileIdentifier = variables.get("fileIdentifier") == null ? "" : variables.get("fileIdentifier").toString();
        String fileSignatureStatus = variables.get("fileSignatureStatus") == null ?
                                        "" : variables.get("fileSignatureStatus").toString();
        String itemCode = variables.get("itemCode") == null ? "" : variables.get("itemCode").toString();
        String itemName = variables.get("itemName") == null ? "" : variables.get("itemName").toString();
        String exeId = (variables.get("exeId") == null || "".equals(variables.get("exeId"))) ?
                    itemCode : variables.get("exeId").toString();
        
        // 获取公共参数
        Map<String, Object> params = getParams("getEfileShare", gcjsxmGrantCode, itemCode, itemName, exeId);
        params.put("interfaceName", "getEfileAttachListByID");
        params.put("fileIdentifier", fileIdentifier);
        
        Map<String, Object> resultMap = TokenUtil.doPost(devbaseUrl, params);
        if(resultMap!=null && resultMap.size()>0) {
            Map<String, Object> headMap = (Map<String, Object>) resultMap.get("head");
            String status = headMap.get("status") == null ? "" : headMap.get("status").toString();
            if (StringUtils.isNotEmpty(status) && status.equals("0")) {// 调用成功
                Map<String, Object> dataMap = (Map<String, Object>) resultMap.get("data");
                List<Map<String, Object>> fileIdList = JSON.parseObject(dataMap.get("dataList").toString(), List.class);
                if(fileIdList!=null && fileIdList.size()>0) {
                    if(fileIdList!=null && fileIdList.size()>0) {
                        for(int i=0;i<fileIdList.size();i++) {
                            Map<String, Object> fileIdMap = fileIdList.get(i);
                            if(fileIdMap.get("fileId")!=null) {
                                String fileId = (String)fileIdMap.get("fileId");
                                if("0".equals(fileSignatureStatus)) {
                                    Map<String, Object> unSignParams = getParams("getEfileShare", gcjsxmGrantCode, itemCode, itemName, exeId);
                                    unSignParams.put("interfaceName", "getEfileAttachByID");
                                    unSignParams.put("fileId", fileId);
                                    Map<String, Object> unSignMap = TokenUtil.doPost(devbaseUrl, unSignParams);
                                    if(unSignMap!=null && unSignMap.size()>0) {
                                        Map<String, Object> unSignHeadMap = (Map<String, Object>) unSignMap.get("head");
                                        String unSignStatus = unSignHeadMap.get("status") == null ? "" : unSignHeadMap.get("status").toString();
                                        if (StringUtils.isNotEmpty(unSignStatus) && unSignStatus.equals("0")) {// 调用成功
                                            Map<String, Object> unSignDataMap = (Map<String, Object>) unSignMap.get("data");
                                            if(unSignDataMap.get("fileName")!=null && unSignDataMap.get("fileContent")!=null) {
                                                String fileName = (String)unSignDataMap.get("fileName");
                                                String fileContent = (String)unSignDataMap.get("fileContent");
                                                if(fileContent.contains("DownLoadServlet")) {
                                                    String[] contentArr = fileContent.split("=");
                                                    String sourceFileId = contentArr[1];
                                                    Map<String, Object> fileMap = this.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                                                            new String[] { sourceFileId });
                                                    
                                                    //返回值
                                                    Map<String, Object> Map = new HashMap<String, Object>();
                                                    Map.put("result", true);
                                                    Map.put("fileId", fileMap.get("FILE_ID"));
                                                    Map.put("fileName", fileMap.get("FILE_NAME"));
                                                    Map.put("attachKey",attachKey);
                                                    Map.put("filePath", fileMap.get("FILE_PATH"));
                                                    resultlList.add(Map);
                                                }else {
                                                    Map<String, Object> uploadFileMap = uploadElectDocumentToZHSP(variables,
                                                            fileName, fileContent, fileId);
                                                    
                                                    //返回值
                                                    Map<String, Object> Map = new HashMap<String, Object>();
                                                    Map.put("result", true);
                                                    Map.put("fileId", uploadFileMap.get("fileId"));
                                                    Map.put("fileName", uploadFileMap.get("fileName"));
                                                    Map.put("attachKey",attachKey);
                                                    Map.put("filePath", uploadFileMap.get("filePath"));
                                                    resultlList.add(Map);
                                                }
                                            }
                                        }
                                    }
                                }else if("1".equals(fileSignatureStatus)){
                                    Map<String, Object> signParams = getParams("getEfileShare", gcjsxmGrantCode, itemCode, itemName, exeId);
                                    signParams.put("interfaceName", "getEfileIndexByID");
                                    signParams.put("fileId", fileId);
                                    Map<String, Object> signMap = TokenUtil.doPost(devbaseUrl, signParams);
                                    if(signMap!=null && signMap.size()>0) {
                                        Map<String, Object> signHeadMap = (Map<String, Object>) signMap.get("head");
                                        String signStatus = signHeadMap.get("status") == null ? "" : signHeadMap.get("status").toString();
                                        if (StringUtils.isNotEmpty(signStatus) && signStatus.equals("0")) {// 调用成功
                                            Map<String, Object> signDataMap = (Map<String, Object>) signMap.get("data");
                                            if(signDataMap.get("fileName")!=null && signDataMap.get("fileContent")!=null) {
                                                String fileName = (String)signDataMap.get("fileName");
                                                String fileContent = (String)signDataMap.get("fileContent");
                                                if(fileContent.contains("DownLoadServlet")) {
                                                    String[] contentArr = fileContent.split("=");
                                                    String sourceFileId = contentArr[1];
                                                    Map<String, Object> fileMap = this.getByJdbc("T_MSJW_SYSTEM_FILEATTACH", new String[] { "FILE_ID" },
                                                            new String[] { sourceFileId });
                                                    
                                                    //返回值
                                                    Map<String, Object> Map = new HashMap<String, Object>();
                                                    Map.put("result", true);
                                                    Map.put("fileId", fileMap.get("FILE_ID"));
                                                    Map.put("fileName", fileMap.get("FILE_NAME"));
                                                    Map.put("attachKey",attachKey);
                                                    Map.put("filePath", fileMap.get("FILE_PATH"));
                                                    resultlList.add(Map);
                                                }else {
                                                    Map<String, Object> uploadFileMap = uploadElectDocumentToZHSP(variables,
                                                            fileName, fileContent, fileId);
                                                    
                                                    //返回值
                                                    Map<String, Object> Map = new HashMap<String, Object>();
                                                    Map.put("result", true);
                                                    Map.put("fileId", uploadFileMap.get("fileId"));
                                                    Map.put("fileName", uploadFileMap.get("fileName"));
                                                    Map.put("attachKey",attachKey);
                                                    Map.put("filePath", uploadFileMap.get("filePath"));
                                                    resultlList.add(Map);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultlList;
    }
    /**
     * 将可信附件信息上传到综合审批平台
     * @author Scolder Lin
     * 
     * @param variables
     * @param fileName
     * @param fileContent
     * @param fileId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadElectDocumentToZHSP(Map<String, Object> variables,
            String fileName, String fileContent, String fileId){
        Map<String, Object> resultInfo = new HashMap<String, Object>();
        String fileNameStr = fileName.substring(0,fileName.lastIndexOf("."));
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        /*String[] fileNameStr = fileName.split("[.]");
        fileName = fileNameStr[0];*/
        
        // 上传证照文件
        String busTableName = (String) Optional.ofNullable(variables.get("busTableName")).orElse("T_BSFW_PTJINFO");
        String busRecordId = (String) Optional.ofNullable(variables.get("busRecordId")).orElse("");
        String fileResult = uploadBase64File(fileContent, fileNameStr, fileType, busTableName, busRecordId, fileId);
        Map<String, Object> respMap = (Map) JSON.parse(fileResult);
        //HttpRequestUtil.getStreamDownloadOutFile(apiUrl)
        boolean  flag=Boolean.parseBoolean(StringUtil.getString(respMap.get("success")));
        if(flag==true){
            String data=StringUtil.getString(respMap.get("data"));
            resultInfo = JSONObject.parseObject(data,Map.class);
            
        }
        return resultInfo;
    }
}

