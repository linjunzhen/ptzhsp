/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.bsfw.model.FlowData;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.dao.FileAttachDao;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.FileAttachService;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 系统附件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fileAttachService")
public class FileAttachServiceImpl extends BaseServiceImpl implements FileAttachService {
    /**
     * 所引入的dao
     */
    @Resource
    private FileAttachDao dao;
    /**
     * departmentService
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 删除文件
     * @author Flex Hu
     * @created 2014年9月27日 下午4:32:27
     * @param fileIds
     */
    public void removeFile(String[] fileIds){
        for(String fileId:fileIds){
            Map<String,Object> fileAttach = dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                    new String[]{"FILE_ID"},new Object[]{fileId});
            Properties properties = FileUtil.readProperties("project.properties");
            String attachFileFolderPath = properties.getProperty("AttachFilePath");
            String fileFullPath = attachFileFolderPath+fileAttach.get("FILE_PATH");
            dao.remove("T_MSJW_SYSTEM_FILEATTACH", "FILE_ID", new Object[]{fileId});
            dao.remove("T_MSJW_PROJECT_FILE", "FILE_ID", new Object[]{fileId});
            File file = new File(fileFullPath);
            if(file.exists()){
                file.delete();
            }
        }
    }
    
    /**
     * 
     * 描述 更新业务表字段
     * @author Flex Hu
     * @created 2014年10月2日 上午10:48:45
     * @param fileIds
     * @param tableName
     */
    public void updateTableName(String fileIds,String recordId){
        if(StringUtils.isNotEmpty(fileIds)){
            dao.updateTableName(fileIds, recordId);
        }
    }
    /**
     * 
     * 描述 获取附件列表
     * @author Flex Hu
     * @created 2014年10月2日 上午11:01:56
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findList(String busTableName,String busRecordId){
        return dao.findList(busTableName, busRecordId);
    }
    /**
     * 
     * 描述 获取办结结果附件列表
     * @author Yanisin Shi
     * @created 2021年07月29日 上午11:01:56
     * @param exeid
     * @return
     */
    public List<Map<String,Object>> findListByResultExeId(String exeId){
        return dao.findListByResultExeId(exeId);
    }

    /**
     * 
     * 描述 根据附件ID获取对象数据
     * 
     * @author Flex Hu
     * @created 2015年1月16日 下午3:14:12
     * @param file_id
     * @return
     */
    @Override
    public Map<String, Object> getFileAttachObject(String file_id) {
        return dao.getFileAttachObject(file_id);
    }
    /**
     * 
     * 描述 根据附件ID获取出生一件事附件数据
     * @author Yanisin Shi
     * @param exeid
     * @return
     */
    @Override
    public Map<String, Object> getBirthFileInfo(String exeid) {
        return dao.getBirthFileInfo(exeid);
    }
    /**
     * 
     * 描述   更新出生一件事附件内容
     * @author Yanisin Shi
     * @param fileId
     * @see net.evecom.platform.system.service.FileAttachService#updateBirthFileByFileId(java.lang.String)
     */
    @Override
    public void updateBirthFileByFileId(String fileId,String yw_id,String BIRTH_FILE_ID,String BIRTH_FILE_URL) {
         dao.updateBirthFileByFileId(fileId, yw_id, BIRTH_FILE_ID, BIRTH_FILE_URL);
    }
    
    /**
     * 
     * 描述 获取附件根路径
     * @author Flex Hu
     * @created 2015年1月16日 下午3:19:27
     * @return
     */
    public String getFileAttachRootPath(){
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath");
        return attachFileFolderPath;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年3月17日 下午2:21:29
     * @param busTableName
     * @param busRecordId
     * @param attachKey
     * @return
     */
    public List<Map<String,Object>> findList(String busTableName,String busRecordId,String attachKey){
        return dao.findList(busTableName, busRecordId, attachKey);
    }
    
    /**
     * 
     * 描述 根据key和附件IDS判断是否存在这个附件
     * @author Flex Hu
     * @created 2015年4月5日 上午10:02:10
     * @param attachKey
     * @param fileIds
     * @return
     */
    public boolean isExists(String attachKey, String fileIds) {
        return dao.isExists(attachKey, fileIds);
    }
    
    /**
     * 
     * 描述 更新业务表字段
     * @author Flex Hu
     * @created 2015年8月31日 下午4:57:09
     * @param fileIds
     * @param recordId
     */
    public void updateBusTableRecordId(String[] fileIds,String recordId){
        dao.updateBusTableRecordId(fileIds, recordId);
    }
    
    /**
     * 
     * 描述 根据业务表名称、业务表记录ID、材料KEY获取上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @param attachKey
     * @return
     */
    public List<Map<String,Object>> findByList(String busTableName,String busRecordId,String attachKey){
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_FILEATTACH F");
        sql.append(" WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? AND F.ATTACH_KEY=? ");
        sql.append(" ORDER BY F.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{busTableName,busRecordId,attachKey}, null);
    }

    /**
     *
     * 描述 根据业务表名称、业务表记录ID获取上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @return
     */
    @Override
    public List<Map<String, Object>> findByList(String busTableName, String busRecordId) {
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_FILEATTACH F");
        sql.append(" WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? ");
        sql.append(" ORDER BY F.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{busTableName,busRecordId}, null);
    }

    /**
     * 
     * 描述 根据业务表名称、业务表记录ID获取上传记录列表
     * @author Flex Hu
     * @created 2015年10月29日 上午11:02:45
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findYsqByList(String busTableName,String busRecordId){
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select m.mater_code,m.mater_name,m.mater_clsmlx,m.mater_clsm,f.* ");
        sql.append(" from T_MSJW_SYSTEM_FILEATTACH F ");
        sql.append(" left join T_WSBS_APPLYMATER m on m.mater_code = f.attach_key ");
        sql.append(" WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? and f.sfsq = 1");
        sql.append(" ORDER BY F.CREATE_TIME ASC ");
        return dao.findBySql(sql.toString(), new Object[]{busTableName,busRecordId}, null);
    }
    
    /**
     * 
     * 描述 保存申报材料的记录
     * @author Flex Hu
     * @created 2015年11月4日 下午3:49:33
     * @param submitMaterFileJson
     */
    @SuppressWarnings("unchecked")
    public void saveItemMaterFiles(String submitMaterFileJson,String busTableName,
            String busRecordId,Map<String,Object> flowVars){
        List<Map> fileList = JSON.parseArray(submitMaterFileJson, Map.class);
        String SQRSFZ = flowVars.get("SQRSFZ")==null?
                "":flowVars.get("SQRSFZ").toString();
        String SQJG_CODE = flowVars.get("SQJG_CODE")==null?
                "":flowVars.get("SQJG_CODE").toString();
        String EFLOW_CURUSEROPERNODENAME = flowVars.get("EFLOW_CURUSEROPERNODENAME")==null?
                "":flowVars.get("EFLOW_CURUSEROPERNODENAME").toString();
//        String JBR_ZJHM = flowVars.get("JBR_ZJHM")==null?
//                "":flowVars.get("JBR_ZJHM").toString();
        StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH T ");
        sql.append("SET T.BUS_TABLERECORDID=?,T.SQFS=?,T.SFSQ=?,T.ATTACH_KEY=?,");
        sql.append("T.SQRSFZ=?,T.SQJG_CODE=? WHERE T.FILE_ID=?");
        StringBuffer delSql =new StringBuffer("DELETE FROM T_MSJW_SYSTEM_FILEATTACH T");
        delSql.append(" WHERE T.BUS_TABLERECORDID=? AND T.BUS_TABLENAME=? ");
        delSql.append(" AND T.ATTACH_KEY=? ");
        StringBuffer attachKey = new StringBuffer();
        for(Map<String,Object> file:fileList){
            //获取材料编码
            String ATTACH_KEY = file.get("ATTACH_KEY")==null?
                    "":file.get("ATTACH_KEY").toString();
            attachKey.append(ATTACH_KEY).append(",");
            //获取收取方式
            String SQFS = file.get("SQFS")==null?
                    "":file.get("SQFS").toString();
            //获取是否收取
            String SFSQ = file.get("SFSQ")==null?
                    "":file.get("SFSQ").toString();
            //获取文件ID
            String FILE_ID = file.get("FILE_ID")==null?
                    "":file.get("FILE_ID").toString();
            //如果是上传
            if("1".equals(SQFS)){
                //先清除纸质上传数据
                StringBuffer usql =new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH T");
                usql.append(" SET T.BUS_TABLENAME='' ");
                usql.append(" WHERE T.BUS_TABLERECORDID=? AND T.BUS_TABLENAME=? AND T.ATTACH_KEY=? ");
                usql.append(" AND T.SQFS=2");
                dao.executeSql(usql.toString(), new Object[]{busRecordId,busTableName,ATTACH_KEY});
                dao.executeSql(sql.toString(),new Object[]{busRecordId,SQFS,SFSQ,ATTACH_KEY,SQRSFZ,SQJG_CODE,FILE_ID});
            }else{
                String sqfs = flowVars.get("SQFS")==null?
                        "":flowVars.get("SQFS").toString();
                if(StringUtils.isNotEmpty(sqfs)&&sqfs.equals("3")){
                    StringBuffer sql2 = new StringBuffer("update T_MSJW_SYSTEM_FILEATTACH T");
                    sql2.append(" SET T.BUS_TABLERECORDID=? WHERE T.FILE_ID=? ");
                    dao.executeSql(sql2.toString(),new Object[]{busRecordId,FILE_ID});
                }else{
                  //先清除冲突数据
                    dao.executeSql(delSql.toString(), new Object[]{busRecordId,busTableName,ATTACH_KEY});
                    Map<String,Object> fileAttach = new HashMap<String,Object>();
                    fileAttach.put("BUS_TABLENAME", busTableName);
                    fileAttach.put("BUS_TABLERECORDID", busRecordId);
                    fileAttach.put("ATTACH_KEY",ATTACH_KEY);
                    fileAttach.put("SQFS",SQFS);
                    fileAttach.put("SFSQ",SFSQ);
                    dao.saveOrUpdate(fileAttach, "T_MSJW_SYSTEM_FILEATTACH",null);
                }
            }
        }
        if((null != fileList && fileList.size()>0) ||"开始".equals(EFLOW_CURUSEROPERNODENAME)){
            //清除无效数据
            StringBuffer usql =new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH T");
            usql.append(" SET T.BUS_TABLENAME='' ");
            usql.append(" WHERE T.BUS_TABLERECORDID=? AND T.BUS_TABLENAME=? ");
            if(StringUtils.isNotEmpty(attachKey)){           
                usql.append(" AND T.ATTACH_KEY NOT IN ");
                usql.append(StringUtil.getValueArray(attachKey.deleteCharAt(attachKey.length()-1).toString()));
            }
            dao.executeSql(usql.toString(), new Object[]{busRecordId,busTableName});            
        }
    }
    
    /**
     * 
     * 描述 根据流程实例ID
     * @author Flex Hu
     * @created 2015年12月7日 下午3:29:41
     * @param fileIds
     * @param exeId
     */
    public void updateExeId(String fileIds,String exeId){
        if(StringUtils.isNotEmpty(exeId)){
            StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH F");
            sql.append(" SET F.FLOW_EXEID=? WHERE F.FILE_ID IN");
            sql.append(StringUtil.getValueArray(fileIds));
            dao.executeSql(sql.toString(), new Object[]{exeId});
        }
    }
    
    /**
     * 
     * 描述 根据流程实例ID获取列表
     * @author Flex Hu
     * @created 2015年12月8日 下午3:02:15
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findByExeId(String exeId,String sfhzd){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FILE_ID,T.FILE_NAME,T.CREATE_TIME,T.UPLOADER_NAME");
        sql.append(",T.FLOW_TASKNAME,T.UPLOADER_DEPNAME,T.FILE_PATH from T_MSJW_SYSTEM_FILEATTACH T ");
        //sql.append("LEFT JOIN T_MSJW_SYSTEM_SYSUSER U ON T.AUTH_USERCODE = U.USERNAME ");
        sql.append("WHERE T.FLOW_EXEID=? AND T.FLOW_TASKNAME IS NOT NULL  ");
        params.add(exeId);
        if(StringUtils.isNotEmpty(sfhzd)&&sfhzd.equals("1")){
            sql.append("AND (T.SFHZD = '1' OR T.SFHZD= '-9')");
        }else if(StringUtils.isNotEmpty(sfhzd)&&sfhzd.equals("-1")){
            sql.append("AND (T.SFHZD = '-1' OR T.SFHZD = '-9')");
            // 获取当前用户信息
            SysUser curUser = AppUtil.getLoginUser();
            // 非超管进行数据级别权限控制
            if (!(curUser.getResKeys().equals(SysUser.ADMIN_RESKEY) || curUser.getUsername().equals("chenci"))) {
                // 获取当前用户被授权的部门代码
                String depId = curUser.getDepId();
                String uId = curUser.getUserId();
                String ucode = curUser.getUsername();
                //sql.append(" AND (T.AUTH_USERCODE = ? OR T.UPLOADER_ID = ? OR  ");
                sql.append(" AND (T.FILE_ID IN (SELECT D.FILE_ID FROM T_MSJW_SYSTEM_DOCRECEIVER D ");
                sql.append(" WHERE D.USER_CODE = ?) OR T.UPLOADER_ID = ? OR  ");
                sql.append(" T.FILE_ID IN (SELECT F.FILE_ID FROM JBPM6_PASSINFO_FILE F WHERE  F.USER_ID = ?) )");
                params.add(ucode);
                params.add(uId);
                params.add(uId);
            }
        }
        sql.append(" order by t.create_time asc ");
        List<Map<String,Object>> listMap= dao.findBySql(sql.toString(), params.toArray(), null);
        if(listMap!=null){
            for (int i = 0; i < listMap.size(); i++) {
                Map<String,Object> e = listMap.get(i);
                String fileId = (String)e.get("FILE_ID");
                StringBuffer newsql = new StringBuffer("SELECT WMSYS.WM_CONCAT(T.USER_NAME) AS FULLNAME ");
                newsql.append(" FROM T_MSJW_SYSTEM_DOCRECEIVER T WHERE T.FILE_ID=? ");
                Map<String,Object> docreceiverMap = dao.getByJdbc(newsql.toString(), new Object[]{fileId});
                if(docreceiverMap!=null){
                    listMap.get(i).put("FULLNAME", docreceiverMap.get("FULLNAME"));
                }
            }
        }
        return listMap;
    }
    /**
     * 
     * 描述 根据流程实例ID获取列表
     * @author Flex Hu
     * @created 2015年12月8日 下午3:02:15
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findResByExeId(String exeId){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append("select t.* from JBPM6_FLOW_RESULT t where t.exe_id = ?");
        params.add(exeId);
        List<Map<String,Object>> listMap= dao.findBySql(sql.toString(), params.toArray(), null);
        List<Map<String,Object>> returnlist = new ArrayList<Map<String,Object>>();
        if(listMap!=null){
            Map<String,Object> map = listMap.get(0);
            String fileids = map.get("RESULT_FILE_ID")==null?
                    "":map.get("RESULT_FILE_ID").toString();
            if (StringUtils.isNotEmpty(fileids)) {
                String[] fileid = fileids.split(";");
                for (String id : fileid) {
                    StringBuffer newsql = new StringBuffer("  ");
                    newsql.append(" select * from T_MSJW_SYSTEM_FILEATTACH t where t.file_id = ? ");
                    Map<String,Object> docreceiverMap = dao.getByJdbc(newsql.toString(), new Object[]{id});
                    returnlist.add(docreceiverMap);
                }
            }
        }
        return returnlist;
    }
    /**
     * 
     * 描述 根据流程实例ID获取列表
     * @author Flex Hu
     * @created 2015年12月8日 下午3:02:15
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findJGByExeId(String exeId){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select * from JBPM6_FLOW_RESULT t where t.exe_id = ?");
        sql.append("  ");
        params.add(exeId);
        List<Map<String,Object>> listMap= dao.findBySql(sql.toString(), params.toArray(), null);
        return listMap;
    }
    @Override
    public List<Map<String, Object>> findMailInfoByExeId(String exeId) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" ");
        sql.append(" select t.* from T_BSFW_EMSINFO t where t.txlogisticid like '%");
        sql.append(exeId);
        sql.append("%' ");
        sql.append(" order by t.create_time asc ");
        List<Map<String,Object>> listMap= dao.findBySql(sql.toString(), params.toArray(), null);
        return listMap;
    }
    
    /**
     * 
     * 描述 更新上传材料的状态
     * @author Flex Hu
     * @created 2015年12月16日 上午10:06:54
     * @param flowVars
     * @return
     */
    public Map<String,Object> updateUserMaterStatus(Map<String,Object> flowVars){
        //获取是否是暂存操作
        String isTempSave = (String) flowVars.get("EFLOW_ISTEMPSAVE");
        if(isTempSave.equals("-1")){
            String EFLOW_ISBACK= (String) flowVars.get("EFLOW_ISBACK");
            //获取流程实例ID
            String EFLOW_BUSRECORDID = (String) flowVars.get("EFLOW_BUSRECORDID");
            String EFLOW_BUSTABLENAME = (String) flowVars.get("EFLOW_BUSTABLENAME");
            if(!(StringUtils.isNotEmpty(EFLOW_ISBACK)&&EFLOW_ISBACK.equals("true"))){
                StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH F");
                sql.append(" SET F.FILE_SHZT=? WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? ");
                if(StringUtils.isNotEmpty(EFLOW_BUSRECORDID)&&StringUtils.isNotEmpty(EFLOW_BUSTABLENAME)){
                    dao.executeSql(sql.toString(), new Object[]{"1",EFLOW_BUSTABLENAME,EFLOW_BUSRECORDID});
                }
            }
        }
        return flowVars;
    }

    /**
     * 
     * 描述 更新材料状态
     * @author Faker Li
     * @created 2015年12月16日 上午11:29:46
     * @param fieShzt
     * @param flowVars
     * @param key
     */
    public void updateFileShzt(String fieShzt, Map<String, Object> flowVars, String key) {
      //获取流程实例ID
        String EFLOW_BUSRECORDID = (String) flowVars.get("EFLOW_BUSRECORDID");
        String EFLOW_BUSTABLENAME = (String) flowVars.get("EFLOW_BUSTABLENAME");
        StringBuffer sql = new StringBuffer("UPDATE T_MSJW_SYSTEM_FILEATTACH F");
        sql.append(" SET F.FILE_SHZT=? WHERE F.BUS_TABLENAME=? AND F.BUS_TABLERECORDID=? ");
        if(StringUtils.isNotEmpty(key)){
            if(StringUtils.isNotEmpty(EFLOW_BUSRECORDID)&&StringUtils.isNotEmpty(EFLOW_BUSTABLENAME)){
                sql.append(" AND F.ATTACH_KEY=?");
                dao.executeSql(sql.toString(), new Object[]{fieShzt,EFLOW_BUSTABLENAME,EFLOW_BUSRECORDID,key});
            }
        }else{
            if(StringUtils.isNotEmpty(EFLOW_BUSRECORDID)&&StringUtils.isNotEmpty(EFLOW_BUSTABLENAME)){
                dao.executeSql(sql.toString(), new Object[]{fieShzt,EFLOW_BUSTABLENAME,EFLOW_BUSRECORDID});
            }
        }
        
    }
    
    /**
     * 
     * 描述 保存审批材料数据
     * @author Flex Hu
     * @created 2016年2月1日 下午12:23:20
     * @param appMaterFileJson
     */
    public void saveAuditerFiles(String appMaterFileJson,Map<String,Object> flowInfo){
        List<Map> fileList = JSON.parseArray(appMaterFileJson, Map.class);
        List<String> fileIds = new ArrayList<String>();
        for(Map<String,Object> file:fileList){
            String fileId = dao.saveOrUpdate(file,"T_MSJW_SYSTEM_FILEATTACH",null);
            //获取是否回执单
            String SFHZD = (String) file.get("SFHZD");
            if(StringUtils.isNotEmpty(SFHZD)&&"-1".equals(SFHZD)){
                //获取审核人名称S
                String AUTH_USERNAMES = (String) file.get("AUTH_USERNAMES");
                String AUTH_USERCODES = (String) file.get("AUTH_USERCODES");
                String[] userNames = AUTH_USERNAMES.split(",");
                String[] userCodes = AUTH_USERCODES.split(",");
                for(int i =0;i<userNames.length;i++){
                    if(StringUtils.isNotEmpty(userNames[i])&&StringUtils.isNotEmpty(userCodes[i])){
                        Map<String,Object> docReceive = new HashMap<String,Object>();
                        docReceive.put("USER_NAME", userNames[i]);
                        docReceive.put("USER_CODE", userCodes[i]);
                        docReceive.put("FILE_ID", fileId);
                        dao.saveOrUpdate(docReceive, "T_MSJW_SYSTEM_DOCRECEIVER", null);
                    }
                }
                fileIds.add(fileId);
            }
        }
        //获取传阅人IDS
        String EFLOW_PERULA_IDS = (String) flowInfo.get("EFLOW_PERULA_IDS");
        if(StringUtils.isNotEmpty(EFLOW_PERULA_IDS)){
            String[] userIds = EFLOW_PERULA_IDS.split(",");
            StringBuffer sql = new StringBuffer("insert into JBPM6_PASSINFO_FILE(");
            sql.append("USER_ID,FILE_ID) VALUES(?,?) ");
            for(String fileId:fileIds){
                for(String userId:userIds){
                    dao.executeSql(sql.toString(), new Object[]{userId,fileId});
                }
            }
        }
        
    }
    
    /**
     * 
     * 描述 根据实例ID和模版ID获取文件
     * @author Flex Hu
     * @created 2016年4月2日 上午8:56:23
     * @param exeId
     * @param tplId
     * @return
     */
    public Map<String,Object> getFilePath(String exeId,String tplId){
        return dao.getFilePath(exeId, tplId);
    }

    @Override
    public List<Map<String, Object>> findListForResult(String ids) {
        StringBuffer sb = new StringBuffer();
        String[] valuesArray = ids.split(";");
        for (String value : valuesArray) {
            sb.append(",'" + value + "'");
        }
        sb.delete(0, 1);
        sb.insert(0, "(");
        sb.append(")");
        ids=sb.toString();
//        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(
                "SELECT * from T_MSJW_SYSTEM_FILEATTACH where  ");
        //sql.append("FILE_ID IN " + StringUtil.getValueArray(ids));
        sql.append(" FILE_ID IN "+ids);
        
        list = dao.findBySql(sql.toString(), params.toArray(),null);
        
        //mlist.put("total",list.size());
        //mlist.put("list", list);
        return list;
    }
    /**
     * 
     * 根据材料标签和上传用户ID获取列表
     * @author Danto Huang
     * @created 2017-5-4 下午2:20:05
     * @param tagId
     * @param userId
     * @return
     */
    public List<Map<String,Object>> findListByTagAndUserId(String tagId,String userId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from t_msjw_system_fileattach t ");
        sql.append("left join t_wsbs_applymater a on a.mater_code=t.attach_key ");
        sql.append("where t.uploader_id=? and t.bus_tablerecordid is not null and ");
        sql.append("(a.tag_id=? or a.tag_id in(select c.tag_id from T_USERCENTER_TAG c where c.parent_tag=?))");
        sql.append("order by t.create_time desc ");
        return dao.findBySql(sql.toString(), new Object[]{userId,tagId,tagId}, null);
    }
    /**
     * 获取历史材料列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> findDatagrid(Map<String, Object> variables) {
        String name = variables.get("Q_T.MATER_NAME_LIKE") == null ? 
                "" : variables.get("Q_T.MATER_NAME_LIKE").toString();
        String fileName = variables.get("Q_T.FILE_NAME_LIKE") == null ? 
                "" : variables.get("Q_T.FILE_NAME_LIKE").toString();
//        String attachKey = variables.get("attachKey") == null ? 
//                "" : variables.get("attachKey").toString();
        String sqrsfz = variables.get("sqrsfz") == null ? 
                "" : variables.get("sqrsfz").toString();
        String sqjg_code = variables.get("sqjg_code") == null ? 
                "" : variables.get("sqjg_code").toString();
        StringBuffer sql = new StringBuffer();  
        sql.append("select a.mater_name,t.* ");
        sql.append("from T_MSJW_SYSTEM_FILEATTACH t ");
        sql.append("left join T_WSBS_APPLYMATER a on a.mater_code = t.attach_key ");
        sql.append("where 1=1 ");
//        sql.append("and t.attach_key is not null ");
        sql.append("and t.bus_tablerecordid is not null ");
        sql.append("and (t.sqrsfz = ? or t.sqjg_code = ? ) ");
        if (StringUtils.isNotEmpty(name)) {
            sql.append("and a.mater_name like '%");
            sql.append(name);
            sql.append("%' ");
        }
        if (StringUtils.isNotEmpty(fileName)) {
            sql.append("and t.file_name like '%");
            sql.append(fileName);
            sql.append("%' ");
        }
        sql.append("order by t.create_time desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{sqrsfz,sqjg_code}, null);
        return list;
    }
    /**
     * 获取历史材料列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> findHisDatagrid(Map<String, Object> variables) {
        String name = variables.get("Q_T.MATER_NAME_LIKE") == null ? 
                "" : variables.get("Q_T.MATER_NAME_LIKE").toString();
        String fileName = variables.get("Q_T.FILE_NAME_LIKE") == null ? 
                "" : variables.get("Q_T.FILE_NAME_LIKE").toString();
        String sqrsfz = variables.get("sqrsfz") == null ? 
                "" : variables.get("sqrsfz").toString();
        String sqjg_code = variables.get("sqjg_code") == null ? 
                "" : variables.get("sqjg_code").toString();
        StringBuffer sql = new StringBuffer();  
        sql.append("select s.item_code,s.item_name,e.exe_id,e.subject,e.create_time, ");
        sql.append("a.mater_name,t.file_id,t.file_name from T_MSJW_SYSTEM_FILEATTACH t ");
        sql.append("left join T_WSBS_APPLYMATER a on a.mater_code = t.attach_key ");
        sql.append("left join JBPM6_EXECUTION e on e.bus_recordid = t.bus_tablerecordid ");
        sql.append("left join T_WSBS_SERVICEITEM s on s.item_code = e.item_code ");
        sql.append("where 1=1 and t.bus_tablerecordid is not null ");
        sql.append("and s.item_code is not null ");
        sql.append("and (t.sqrsfz = ? or t.sqjg_code = ? ) ");
        if (StringUtils.isNotEmpty(name)) {
            sql.append("and a.mater_name like '%");
            sql.append(name);
            sql.append("%' ");
        }
        if (StringUtils.isNotEmpty(fileName)) {
            sql.append("and t.file_name like '%");
            sql.append(fileName);
            sql.append("%' ");
        }
        sql.append("order by t.create_time desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{sqrsfz,sqjg_code}, null);
        List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
        for (Map<String, Object> map : list) {
            String fileId = map.get("FILE_ID")==null?
                    "":map.get("FILE_ID").toString();
            Properties configProperties = FileUtil.readProperties("conf/config.properties");
            String serviceUrl = configProperties.getProperty("serviceUrl");
            String downloadUrl = serviceUrl + "/DownLoadServlet?fileId="+fileId;
            map.put("DOWNLOADURL", downloadUrl);
            returnList.add(map);
        }
        return returnList;
    }

    /**
     * 描述:查询此办件的所有上传过的材料
     *
     * @author Madison You
     * @created 2021/4/7 14:32:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> queryAllUploadFiles(HttpServletRequest request) {
        String exeId = request.getParameter("exeId");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT B.FILE_ID,B.FILE_NAME,B.ATTACH_KEY,B.CREATE_TIME FROM JBPM6_EXECUTION A ");
        sql.append(" LEFT JOIN T_MSJW_SYSTEM_FILEATTACH B ON A.BUS_RECORDID = B.BUS_TABLERECORDID ");
        sql.append(" WHERE A.EXE_ID = ? AND B.SQFS = 1 ORDER BY B.CREATE_TIME DESC ");
        List list = dao.findBySql(sql.toString(), new Object[]{exeId}, null);
        return list;
    }

    /**
     * 获取我的历史材料列表
     * @param variables
     * @return
     */
    public List<Map<String, Object>> wdcllist(Map<String, Object> variables) {
        String userId = variables.get("userId") == null ? 
                "" : variables.get("userId").toString();
        StringBuffer sql = new StringBuffer();  
        sql.append("select a.mater_name,t.* ");
        sql.append("from T_MSJW_SYSTEM_FILEATTACH t ");
        sql.append("left join T_WSBS_APPLYMATER a on a.mater_code = t.attach_key ");
        sql.append("where 1=1 ");
//        sql.append("and t.attach_key is not null ");
        sql.append("and t.bus_tablerecordid is not null ");
        sql.append("and t.uploader_id = ?  ");
        sql.append("and t.bus_tablerecordid = 'wdcl'  ");
        sql.append("order by t.create_time desc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{userId}, null);
        return list;
    }
    /**
     * 上传文件
     *
     * @param variables
     */
    public List<Map<String, Object>> useHisMatAgainByIds(Map<String, Object> variables) {
        // TODO Auto-generated method stub
        SysUser sysUser = AppUtil.getLoginUser();
        String userName = "";
        String uploadId = "";
        if (sysUser != null) {
            userName = sysUser.getUsername();
            uploadId = sysUser.getUserId();
        }
        String busTableName = String.valueOf(variables.get("busTableName"));
        String attachKey = String.valueOf(variables.get("attachKey"));
        String FILE_ID = String.valueOf(variables.get("FILE_ID"));
        
        
        FILE_ID=FILE_ID.replace("&#39;", "'");
        StringBuffer sql = new StringBuffer("select t.* from T_MSJW_SYSTEM_FILEATTACH t  ");
        sql.append(" where 1=1 ");
        sql.append(" and t.file_id in ( ");
        sql.append(FILE_ID);
        sql.append(" ) ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null,null);
        List<Map<String, Object>> resultlList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            map.remove("FILE_ID");
            map.remove("CREATE_TIME");
            map.remove("BUS_TABLERECORDID");
            map.remove("FLOW_EXEID");
            map.remove("FLOW_TASKID");
            map.remove("FLOW_TASKNAME");
            map.remove("FILE_SHZT");
            map.remove("AUTH_DEPID");
            map.remove("AUTH_USERCODES");
            map.remove("AUTH_USERNAMES");
            map.remove("TPL_ID");
            map.remove("XNB_ID");

            map.put("UPLOADER_ID", uploadId);
            map.put("UPLOADER_NAME", userName);
            map.put("BUS_TABLENAME", busTableName);
            map.put("ATTACH_KEY", attachKey);
            map.put("SQFS", 1);
            map.put("SFSQ", 1);
            
            String fileName = map.get("FILE_NAME")==null?
                    "":map.get("FILE_NAME").toString();
            String relPath = map.get("FILE_PATH")==null?
                    "":map.get("FILE_PATH").toString();
            String fileId = dao.saveOrUpdate(map, "T_MSJW_SYSTEM_FILEATTACH",null);
            //返回值
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("result", true);
            result.put("fileId", fileId);
            result.put("fileName", fileName);
            result.put("attachKey", attachKey);
            result.put("filePath", relPath);
            resultlList.add(result);
        }
        return resultlList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findListByCodeAndName(String projectCode, String fileName) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("select f.* from JBPM6_EXECUTION E join T_MSJW_SYSTEM_FILEATTACH F  ");
        sql.append(" on e.bus_recordid = f.bus_tablerecordid and e.bus_tablename = f.bus_tablename  ");
        sql.append("where  f.file_path is not null and e.project_code = ? ");
        if (StringUtils.isNotEmpty(fileName)) {
            sql.append("and f.file_name like '");
            sql.append(fileName);
            sql.append("%' ");
        }
        sql.append("order by f.create_time desc ");
        return dao.findBySql(sql.toString(), new Object[]{projectCode}, null);
    }

    @Override
    public List<Map<String, Object>> findListByCodeAndKey(String projectCode, String attachKey) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("select f.* from SPGL_XMJBXXB X join T_MSJW_SYSTEM_FILEATTACH F  ");
        sql.append("  on x.id = f.bus_tablerecordid  ");
        sql.append("where  f.file_path is not null and X.project_code = ? ");
        sql.append("and f.attach_key = ? ");
        sql.append("order by f.create_time desc ");
        return dao.findBySql(sql.toString(), new Object[]{projectCode,attachKey}, null);
    }
}
