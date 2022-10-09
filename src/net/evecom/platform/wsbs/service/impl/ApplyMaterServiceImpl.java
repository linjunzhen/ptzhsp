/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import bsh.EvalError;
import bsh.Interpreter;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.FileAttachService;
import net.evecom.platform.wsbs.dao.ApplyMaterDao;
import net.evecom.platform.wsbs.service.ApplyMaterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 申请材料操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("applyMaterService")
public class ApplyMaterServiceImpl extends BaseServiceImpl implements ApplyMaterService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ApplyMaterServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ApplyMaterDao dao;
    /**
     * fileAttachService
     */
    @Resource
    private FileAttachService fileAttachService;
    /**
     * 
     */
    @Resource
    private ExecutionService executionService;
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(" from T_WSBS_APPLYMATER T ");
        String typeId = sqlFilter.getRequest().getParameter("TYPE_ID");
        if(StringUtils.isNotEmpty(typeId)&&!typeId.equals("0")){
            sql.append(" WHERE T.MATER_ID IN (SELECT M.MATER_ID FROM");
            sql.append(" T_WSBS_BUSTYPE_MATER M WHERE M.TYPE_ID=?)");
            params.add(typeId);
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据sqlfilter获取数据列表
     * @author Flex Hu
     * @created 2015年9月22日 下午5:26:25
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findForItem(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(",T.RAW_NUM,T.ISNEED_RAW,T.MATER_XZ,T.MATER_CLSMLX,T.MATER_CLSM");
        sql.append(",F1.FILE_ID as FILE_ID1,F1.FILE_NAME AS CLMB");
        sql.append(",F2.FILE_ID as FILE_ID2,F2.FILE_NAME AS CLYB,T.MATER_SSYW");
        sql.append(",T.MATER_DESC,t.gatherorver,t.mater_source,t.mater_source_type");
        sql.append(",T.PAGECOPY_NUM,T.PAGE_NUM");
        sql.append(",SM.MATER_ISNEED,SM.MATER_SN,SM.MATER_FPARA from T_WSBS_SERVICEITEM_MATER SM ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM S ON SM.ITEM_ID=S.ITEM_ID ");
        sql.append("LEFT JOIN T_WSBS_APPLYMATER T ON SM.MATER_ID=T.MATER_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F1  ON F1.FILE_ID=T.MATER_PATH ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F2  ON F2.FILE_ID=T.MATER_PATH2 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        //获取拼凑的子项名
        list=getBusClass(list);
        return list;
    }
    /**
     * 
     * 描述 根据sqlfilter获取数据列表
     * @author Flex Hu
     * @created 2015年9月22日 下午5:26:25
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findForItem2(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(",T.RAW_NUM,T.ISNEED_RAW,T.MATER_XZ,T.MATER_CLSMLX,T.MATER_CLSM");
        sql.append(",F1.FILE_ID as FILE_ID1,F1.FILE_NAME AS CLMB");
        sql.append(",F2.FILE_ID as FILE_ID2,F2.FILE_NAME AS CLYB,T.MATER_SSYW");
        sql.append(",T.MATER_DESC,t.gatherorver,t.mater_source,t.mater_source_type");
        sql.append(",T.PAGECOPY_NUM,T.PAGE_NUM");
        sql.append(",SM.MATER_ISNEED,SM.MATER_SN,SM.MATER_FPARA from T_WSBS_SERVICEITEM_MATER SM ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM S ON SM.ITEM_ID=S.ITEM_ID ");
        sql.append("LEFT JOIN T_WSBS_APPLYMATER T ON SM.MATER_ID=T.MATER_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F1  ON F1.FILE_ID=T.MATER_PATH ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_FILEATTACH F2  ON F2.FILE_ID=T.MATER_PATH2 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        sqlFilter.getPagingBean().setPageSize(10000);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        //获取拼凑的子项名
        list=getBusClass(list);
        return list;
    }
    /**
     * 
     * 描述：获取拼凑的子项名
     * @author Water Guo
     * @created 2018-1-25 上午11:04:49
     * @param list
     * @return
     */
    private List<Map<String,Object>> getBusClass(List<Map<String,Object>> list){
        for(Map<String,Object> map:list){
            String materSsyw=map.get("MATER_SSYW")==null?
                    "":map.get("MATER_SSYW").toString();
            if (StringUtils.isNotEmpty(materSsyw)) {
                String[] materSsyws=materSsyw.split(",");
                if(materSsyws.length>0){
                    StringBuffer busClassNames=new StringBuffer("");
                    for(String str:materSsyws){
                        if (StringUtils.isNotEmpty(str)) {
                            Map<String,Object> busClass=this.getByJdbc("T_WSBS_SERVICEITEM_BUSCLASS",
                                    new String[]{"RECORD_ID"}, new Object[]{str});
                            if(busClass!=null){
                                String busclassName = busClass.get("BUSCLASS_NAME") ==null?
                                        "":busClass.get("BUSCLASS_NAME").toString();
                                busClassNames.append(busclassName).append(",");
                            }
                        }
                    }
                    if (StringUtils.isNotEmpty(busClassNames.toString())) {
                        map.put("BUSCLASS_NAME", busClassNames.substring(0, busClassNames.length()-1));
                    }
                }
            }
        }
        return list;
    }
    /**
     * 
     * 描述 获取列表数据
     * @author Flex Hu
     * @created 2015年9月22日 下午5:41:10
     * @param materIds
     * @return
     */
    public List<Map<String,Object>> findByMaterIds(String materIds){
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(" from T_WSBS_APPLYMATER T ");
        sql.append(" WHERE T.MATER_ID in ").append(StringUtil.getValueArray(materIds));
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, null);
        return list;
    }
    
    /**
     * 
     * 描述 保存或者更新,级联更新中间表
     * @author Flex Hu
     * @created 2015年9月17日 下午4:45:06
     * @param applyMater
     * @return
     */
    @SuppressWarnings("unchecked")
    public String saveOrUpdateCascadeMiddle(Map<String, Object> applyMater){
        String entityId = (String) applyMater.get("MATER_ID");
        String recordId = this.saveOrUpdate(applyMater, "T_WSBS_APPLYMATER", entityId);
        //先清除掉中间表数据
        this.remove("T_WSBS_BUSTYPE_MATER",new String[]{"MATER_ID"},new Object[]{recordId});
        //获取选择的类别IDS
        String typeIds = (String) applyMater.get("BUS_TYPEIDS");
        //开始保存中间表数据
        dao.saveMaterBusTypes(recordId, typeIds.split(","));
        return recordId;
    }
    
    /**
     * 
     * 描述 级联删除掉数据
     * @author Flex Hu
     * @created 2015年9月17日 下午5:26:59
     * @param materIds
     */
    public void removeCascadeMiddle(String[] materIds){
        dao.remove("T_WSBS_APPLYMATER","MATER_ID",materIds);
        dao.remove("T_WSBS_BUSTYPE_MATER","MATER_ID",materIds);
        dao.remove("T_WSBS_SERVICEITEM_MATER","MATER_ID",materIds);
    }
    
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterItem(String materIds,String itemId){
        dao.deleteMaterItem(materIds, itemId);
    }
    /**
     * 
     * 描述 删除材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:20:52
     * @param materIds
     * @param itemId
     */
    public void deleteMaterByItem(String itemId){
        dao.deleteMaterByItem( itemId);
    }
    
    /**
     * 
     * 描述 保存材料项目中间表
     * @author Flex Hu
     * @created 2015年9月23日 上午9:52:05
     * @param materIds
     * @param itemId
     */
    public void saveMaterItem(String materIds,String itemId){
        dao.saveMaterItem(materIds, itemId);
    }
    
    /**
     * 
     * 描述 更新排序值
     * @author Flex Hu
     * @created 2015年9月23日 上午11:27:27
     * @param itemId
     * @param materIds
     */
    public void updateSn(String itemId,String[] materIds){
        dao.updateSn(itemId, materIds);
    }
    
    /**
     * 
     * 描述 根据项目ID获取材料信息列表
     * @author Flex Hu
     * @created 2015年10月27日 下午5:03:23
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findByItemId(String itemId,String exeId){
        StringBuffer sql =new StringBuffer("SELECT SM.MATER_SN,M.MATER_SSYW,SM.MATER_FPARA,SM.MATER_ISNEED,");
        sql.append("b.busclass_name,");
//        sql.append("M.MATER_NAME,M.MATER_TYPE,M.MATER_SIZE,M.MATER_DESC,");
//        sql.append("M.MATER_CLSMLX,M.MATER_CLSM,M.MATER_PARENTCODE,");
//        sql.append("M.MATER_ID,M.MATER_CODE,M.MATER_PATH,M.CLLX,");
//        sql.append("M.PAGECOPY_NUM,M.PAGE_NUM,M.GATHERORVER,");
//        sql.append("M.SUPPORT_WORD,M.MATER_FILTER,M.TAG_ID ");
        sql.append("M.* ");
        sql.append("FROM T_WSBS_APPLYMATER M ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_MATER SM ON M.MATER_ID=SM.MATER_ID ");
        sql.append("left join T_WSBS_SERVICEITEM_BUSCLASS b on b.record_id = M.MATER_SSYW ");
        //sql.append("LEFT JOIN T_WSBS_SERVICEITEM_BUSCLASS SB ON M.MATER_SSYW = SB.RECORD_ID ");
        sql.append("WHERE SM.ITEM_ID=? ");
        sql.append(" ORDER BY SM.MATER_SN ASC ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{itemId}, null);
        list=getBusClass(list);
        List<Map<String,Object>> maters = new ArrayList<Map<String,Object>>();
        Map<String,Object> flowInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(exeId)&&!"null".equals(exeId)){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            flowInfo = flowExe;
            /*if(flowInfo.get("FLOW_STAGE")==null||flowInfo.get("FLOW_STAGE").toString().equals("1")){
                
            }else if(flowInfo.get("FLOW_STAGE")!=null&&flowInfo.get("FLOW_STAGE").toString().equals("2")){
                
            }*/
        }
        for(Map<String,Object> mater:list){
            String materParam = (String) mater.get("MATER_FPARA");
            if(StringUtils.isNotEmpty(materParam)){
                String isAdd = "";
                StringBuffer exeCode =new StringBuffer("if(").append(materParam).append("){ isAdd=\"true\";}");
                try {
                    Interpreter it = new Interpreter();
                    it.set("flowInfo", flowInfo);
                    it.eval(exeCode.toString());
                    isAdd = (String) it.get("isAdd");
                    if(StringUtils.isNotEmpty(isAdd)&&isAdd.equals("true")){
                        maters.add(mater);
                    }
                } catch (EvalError e) {
                    log.error(e.getMessage());
                }
            }else{
                maters.add(mater);
            }
        }
        return maters;
    }

    /**
     * 
     * 描述 根据项目Codes获取材料信息列表
     * @author Rider Chen
     * @created 2017年7月11日 下午4:12:38
     * @param itemCodes
     * @param exeId
     * @return
     */
    public List<Map<String, Object>> findByItemCodes(String itemCodes, String exeId) {
        StringBuffer sql = new StringBuffer("SELECT M.MATER_SSYW,M.MATER_ID,M.MATER_CODE,");
        sql.append("M.MATER_NAME,M.MATER_TYPE,M.MATER_SIZE,M.MATER_DESC,");
        sql.append("M.MATER_CLSMLX,M.MATER_CLSM,M.MATER_PARENTCODE,");
        sql.append("M.MATER_PATH,SM.MATER_ISNEED,M.CLLX,SM.MATER_FPARA,");
        sql.append("M.SUPPORT_WORD,M.MATER_FILTER,M.TAG_ID,M.BINDCATALOG_CODE ");
        sql.append("FROM T_WSBS_APPLYMATER M ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_MATER SM ON M.MATER_ID=SM.MATER_ID ");
        //sql.append("LEFT JOIN T_WSBS_SERVICEITEM_BUSCLASS SB ON M.MATER_SSYW = SB.RECORD_ID ");
        sql.append("WHERE SM.ITEM_ID in(");
        sql.append("select t.item_id from T_WSBS_SERVICEITEM t where t.item_code in ");
        sql.append(StringUtil.getValueArray(itemCodes));
        sql.append(") ");
        sql.append(" ORDER BY SM.MATER_SN ASC ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(),null, null);
        list=getBusClass(list);
        List<Map<String, Object>> maters = new ArrayList<Map<String, Object>>();
        Map<String, Object> flowInfo = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(exeId) && !"null".equals(exeId)) {
            Map<String, Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION", new String[] { "EXE_ID" },
                    new Object[] { exeId });
            flowInfo = flowExe;
            /*
             * if(flowInfo.get("FLOW_STAGE")==null||flowInfo.get("FLOW_STAGE").
             * toString().equals("1")){
             * 
             * }else
             * if(flowInfo.get("FLOW_STAGE")!=null&&flowInfo.get("FLOW_STAGE").
             * toString().equals("2")){
             * 
             * }
             */
        }
        for (Map<String, Object> mater : list) {
            String materParam = (String) mater.get("MATER_FPARA");
            if (StringUtils.isNotEmpty(materParam)) {
                String isAdd = "";
                StringBuffer exeCode = new StringBuffer("if(").append(materParam).append("){ isAdd=\"true\";}");
                try {
                    Interpreter it = new Interpreter();
                    it.set("flowInfo", flowInfo);
                    it.eval(exeCode.toString());
                    isAdd = (String) it.get("isAdd");
                    if (StringUtils.isNotEmpty(isAdd) && isAdd.equals("true")) {
                        maters.add(mater);
                    }
                } catch (EvalError e) {
                    log.error(e.getMessage());
                }
            } else {
                maters.add(mater);
            }
        }
        return maters;
    }
    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年11月4日 下午3:24:57
     * @param isneed
     * @param itemId
     * @param materIds
     */
    public void updateIsneed(String isneed, String itemId, String materIds) {
        dao.updateIsneed(isneed,itemId,materIds);
    }
    /**
     * 
     * 描述 根据项目ITEMCODE获取材料信息列表
     * @author Faker Li
     * @created 2015年11月6日 上午9:23:25
     * @param itemCode
     * @return
     */
    public List<Map<String, Object>> findMaterByItemCode(String itemCode) {
        StringBuffer sql =new StringBuffer("SELECT M.MATER_ID,M.MATER_CODE,");
        sql.append("M.MATER_NAME,M.MATER_TYPE,M.MATER_SIZE,");
        sql.append("M.MATER_PATH,SM.MATER_ISNEED FROM T_WSBS_APPLYMATER M ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM_MATER SM ");
        sql.append("ON M.MATER_ID=SM.MATER_ID ");
        sql.append("LEFT JOIN T_WSBS_SERVICEITEM S ON S.ITEM_ID = SM.ITEM_ID" );
        sql.append(" WHERE S.ITEM_CODE=? ");
        sql.append(" ORDER BY SM.MATER_SN ASC ");
        return dao.findBySql(sql.toString(), new Object[]{itemCode}, null);
    }
    
    /**
     * 
     * 描述 设置已经上传的材料列表
     * @author Flex Hu
     * @created 2015年12月1日 上午11:31:18
     * @param busRecordId
     * @param busTableName
     * @param applyMaters
     * @return
     */
    public List<Map<String,Object>> setUploadFiles(String busRecordId,String busTableName,
            List<Map<String,Object>> applyMaters){
        List<Map<String,Object>> uploadFiles = fileAttachService.
                findByList(busTableName, busRecordId);
        List<Map<String, Object>> uploadFileList = null;
        for(Map<String,Object> applyMater:applyMaters){
            uploadFileList = new ArrayList<>();
            String materCode = (String) applyMater.get("MATER_CODE");
            String sqfs = "";
            //定义审核状态
            for(Map<String,Object> uploadFile:uploadFiles){
                String ATTACH_KEY = (String) uploadFile.get("ATTACH_KEY");
                if (Objects.equals(materCode, ATTACH_KEY)) {
                    //获取收取方式
                    String SQFS = (String) uploadFile.get("SQFS");
                    //获取是否收取
                    String SFSQ = (String) uploadFile.get("SFSQ");
                    //材料编码
                    sqfs= SQFS;
                    applyMater.put("SQFS", SQFS);
                    applyMater.put("SFSQ", SFSQ);
                    applyMater.put("FILE_SHZT", uploadFile.get("FILE_SHZT"));
                    uploadFileList.add(uploadFile);
                }
            }
            if(sqfs.equals("2")){
                applyMater.put("UPLOADED_SIZE",0);
            }else{
                applyMater.put("UPLOADED_SIZE", uploadFiles.size());
            }

            applyMater.put("uploadFiles", uploadFileList);
        }
        return applyMaters;
    }
    /**
     * 
     * 根据标签设置自动代填上传材料 
     * @author Danto Huang
     * @created 2017-5-4 下午2:01:36
     * @param applyMaters
     * @param busTableName
     * @param resultMap
     * @return
     */
    public List<Map<String, Object>> setHisUploadFiles(List<Map<String, Object>> applyMaters, String busTableName,
            Map<String, Object> resultMap) {
        String userId = null;
        String userName = null;
        if(AppUtil.getLoginMember()==null){
            userId = resultMap.get("uploadUserId").toString();
            userName = StringUtil.getString(resultMap.get("uploadUserName"));
        }else{
            userId = (String) AppUtil.getLoginMember().get("USER_ID");
            userName = (String) AppUtil.getLoginMember().get("YHMC");
        }
        for(Map<String,Object> applyMater:applyMaters){
            List<Map<String,Object>> uploadFiles = new ArrayList<Map<String,Object>>();
            if(applyMater.get("TAG_ID")!=null){
                String tagId = (String) applyMater.get("TAG_ID");
                List<Map<String,Object>> hisFiles = fileAttachService.findListByTagAndUserId(tagId, userId);
                if(hisFiles!=null&&hisFiles.size()>0){
                    for(int i=0;i<hisFiles.size();i++){
                        Map<String, Object> hisFile = hisFiles.get(i);
                        if(i>0&&!hisFile.get("BUS_TABLERECORDID").equals(hisFiles.get(0).get("BUS_TABLERECORDID"))){
                            break;
                        }
                        Map<String, Object> newFile = new HashMap<String, Object>();
                        String hisFilePath = hisFile.get("FILE_PATH").toString();
                        String newFilePath = this.copyFileToNew(hisFilePath);
                        newFile.put("BUS_TABLENAME", busTableName);
                        newFile.put("UPLOADER_ID", userId);
                        newFile.put("UPLOADER_NAME", userName);
                        newFile.put("FILE_PATH", newFilePath);
                        newFile.put("FILE_NAME", hisFile.get("FILE_NAME"));
                        newFile.put("FILE_TYPE", hisFile.get("FILE_TYPE"));
                        newFile.put("FILE_LENGTH", hisFile.get("FILE_LENGTH"));
                        newFile.put("FILE_SIZE", hisFile.get("FILE_SIZE"));
                        newFile.put("ATTACH_KEY", applyMater.get("MATER_CODE"));
                        newFile.put("IS_IMG", hisFile.get("IS_IMG"));
                        newFile.put("SQFS", 1);
                        newFile.put("SFSQ", 1);
                        String fileId = fileAttachService.saveOrUpdate(newFile, "T_MSJW_SYSTEM_FILEATTACH", null);
                        newFile.put("FILE_ID", fileId);
                        uploadFiles.add(newFile);
                    }
                }
            }
            applyMater.put("uploadFiles", uploadFiles);
            applyMater.put("UPLOADED_SIZE", uploadFiles.size());
            applyMater.put("SQFS", 1);
            applyMater.put("SFSQ", 1);
        }
        return applyMaters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> setSameUploadFiles(List<Map<String, Object>> applyMaters, String busTableName,
            String projectCode) {
        // TODO Auto-generated method stub
        String userId = null;
        String userName = null;
        if (AppUtil.getLoginMember() == null) {
            SysUser sysuser = AppUtil.getLoginUser();
            userId = sysuser.getUserId();
            userName = sysuser.getFullname();
        } else {
            userId = (String) AppUtil.getLoginMember().get("USER_ID");
            userName = (String) AppUtil.getLoginMember().get("YHMC");
        }
        for (Map<String, Object> applyMater : applyMaters) {
            List<Map<String, Object>> uploadFiles = new ArrayList<Map<String, Object>>();
            if (applyMater.get("MATER_NAME") != null) {
                String materName = (String) applyMater.get("MATER_NAME");
                List<Map<String, Object>> hisFiles = fileAttachService.findListByCodeAndName(projectCode, materName);
                if (hisFiles != null && hisFiles.size() > 0) {
                    //取最新一条材料数据回填
                    Map<String, Object> hisFile = hisFiles.get(0);
                    Map<String, Object> newFile = new HashMap<String, Object>();
                    newFile.put("BUS_TABLENAME", busTableName);
                    newFile.put("UPLOADER_ID", userId);
                    newFile.put("UPLOADER_NAME", userName);
                    newFile.put("FILE_PATH", hisFile.get("FILE_PATH"));
                    newFile.put("FILE_NAME", hisFile.get("FILE_NAME"));
                    newFile.put("FILE_TYPE", hisFile.get("FILE_TYPE"));
                    newFile.put("FILE_LENGTH", hisFile.get("FILE_LENGTH"));
                    newFile.put("FILE_SIZE", hisFile.get("FILE_SIZE"));
                    newFile.put("ATTACH_KEY", applyMater.get("MATER_CODE"));
                    newFile.put("IS_IMG", hisFile.get("IS_IMG"));
                    newFile.put("SQFS", 1);
                    newFile.put("SFSQ", 1);
                    newFile.put("PLAT_TYPE", hisFile.get("PLAT_TYPE"));
                    String fileId = fileAttachService.saveOrUpdate(newFile, "T_MSJW_SYSTEM_FILEATTACH", null);
                    newFile.put("FILE_ID", fileId);
                    uploadFiles.add(newFile);
                }
            }
            applyMater.put("uploadFiles", uploadFiles);
            applyMater.put("UPLOADED_SIZE", uploadFiles.size());
            applyMater.put("SQFS", 1);
            applyMater.put("SFSQ", 1);
        }
        return applyMaters;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> setSameKeyUploadFiles(
            List<Map<String, Object>> applyMaters,String busTableName,
            String projectCode) {
        // TODO Auto-generated method stub
        String userId = null;
        String userName = null;
        if (AppUtil.getLoginMember() == null) {
            SysUser sysuser = AppUtil.getLoginUser();
            userId = sysuser.getUserId();
            userName = sysuser.getFullname();
        } else {
            userId = (String) AppUtil.getLoginMember().get("USER_ID");
            userName = (String) AppUtil.getLoginMember().get("YHMC");
        }
        for (Map<String, Object> applyMater : applyMaters) {
            List<Map<String, Object>> uploadFiles = new ArrayList<Map<String, Object>>();
            if (applyMater.get("MATER_CODE") != null) {
                String attachKey = (String) applyMater.get("MATER_CODE");
                List<Map<String, Object>> hisFiles = fileAttachService.findListByCodeAndKey(projectCode, attachKey);
                if (hisFiles != null && hisFiles.size() > 0) {
                    //取最新一条材料数据回填
                    Map<String, Object> hisFile = hisFiles.get(0);
                    Map<String, Object> newFile = new HashMap<String, Object>();
                    newFile.put("BUS_TABLENAME", busTableName);
                    newFile.put("UPLOADER_ID", userId);
                    newFile.put("UPLOADER_NAME", userName);
                    newFile.put("FILE_PATH", hisFile.get("FILE_PATH"));
                    newFile.put("FILE_NAME", hisFile.get("FILE_NAME"));
                    newFile.put("FILE_TYPE", hisFile.get("FILE_TYPE"));
                    newFile.put("FILE_LENGTH", hisFile.get("FILE_LENGTH"));
                    newFile.put("FILE_SIZE", hisFile.get("FILE_SIZE"));
                    newFile.put("ATTACH_KEY", applyMater.get("MATER_CODE"));
                    newFile.put("IS_IMG", hisFile.get("IS_IMG"));
                    newFile.put("SQFS", 1);
                    newFile.put("SFSQ", 1);
                    newFile.put("PLAT_TYPE", hisFile.get("PLAT_TYPE"));
                    String fileId = fileAttachService.saveOrUpdate(newFile, "T_MSJW_SYSTEM_FILEATTACH", null);
                    newFile.put("FILE_ID", fileId);
                    uploadFiles.add(newFile);
                }
            }
            applyMater.put("uploadFiles", uploadFiles);
            applyMater.put("UPLOADED_SIZE", uploadFiles.size());
            applyMater.put("SQFS", 1);
            applyMater.put("SFSQ", 1);
        }
        return applyMaters;
    }
    
    private String copyFileToNew(String hisFilePath){
        // 定义上传目录的根路径
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String uuId = UUIDGenerator.getUUID();
        String fileType = hisFilePath.substring(hisFilePath.lastIndexOf("."));
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String relativeFolderPath = "applyform/" + currentDate + "/";
        String newFullPath = attachFileFolderPath + relativeFolderPath + uuId + fileType ;
        FileUtil.copyFile(new File(properties.getProperty("AttachFilePath")+hisFilePath), newFullPath);
        return "attachFiles/" + relativeFolderPath + uuId + fileType ;
    }
    

    /**
     * 
     * 描述 表格下载分页列表
     * @author Faker Li
     * @created 2015年12月10日 下午3:39:07
     * @param page
     * @param rows
     * @return
     * @see net.evecom.platform.wsbs.service.ApplyMaterService#findbgxzList(java.lang.String, java.lang.String)
     */
    public Map<String, Object> findbgxzList(String page, String rows,String materName,String busTypeIds) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT A.MATER_ID, A.MATER_NAME, S.ITEM_NAME,A.MATER_PATH");
        sql.append("  FROM T_WSBS_APPLYMATER A");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM_MATER SM");
        sql.append("  ON A.MATER_ID = SM.MATER_ID");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM S");
        sql.append("  ON SM.ITEM_ID = S.ITEM_ID");
        sql.append("  WHERE A.MATER_PATH IS NOT NULL");
        sql.append("  AND S.FWSXZT = '1'");
        if(!(StringUtils.isEmpty(busTypeIds)||busTypeIds.equals("undefined"))){
            sql.append("AND  S.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID IN "+StringUtil.getValueArray(busTypeIds)+" )");
        }
        if(StringUtils.isNotEmpty(materName)){
            sql.append("  AND A.MATER_NAME LIKE '%"+materName.trim()+"%'");
        }
        sql.append("  ORDER BY A.CREATE_TIME DESC");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    /**
     * 
     * 描述 获取所有表格下载列表
     * @author Faker Li
     * @created 2015年12月17日 上午9:26:32
     * @return
     * @see net.evecom.platform.wsbs.service.ApplyMaterService#findAllBgxzList()
     */
    public List<Map<String, Object>> findAllBgxzList() {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT distinct(A.MATER_NAME)");
        sql.append("  FROM T_WSBS_APPLYMATER A");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM_MATER SM");
        sql.append("  ON A.MATER_ID = SM.MATER_ID");
        sql.append("  LEFT JOIN T_WSBS_SERVICEITEM S");
        sql.append("  ON SM.ITEM_ID = S.ITEM_ID");
        sql.append("  WHERE A.MATER_PATH IS NOT NULL");
        sql.append("  AND S.FWSXZT = '1'");
        return dao.findBySql(sql.toString(), params.toArray(), null);
    }

    /**
     * 
     * 描述 保存材料过滤参数
     * @author Faker Li
     * @created 2016年3月7日 下午1:07:09
     * @param fpara
     * @param materIds
     * @see net.evecom.platform.wsbs.service.ApplyMaterService#updateFilterPara(java.lang.String, java.lang.String)
     */
    public void updateFilterPara(String fpara, String materIds,String itemId) {
        dao.updateFilterPara(fpara,materIds,itemId);
    }
    
    /**
     * 
     * 描述 根据用户NAME获取材料ID
     * @author Rider Chen
     * @created 2016-4-19 下午03:23:33
     * @param userName
     * @return
     */
    @SuppressWarnings("unchecked")
    public  String findByUserMater(String userName){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql =new StringBuffer("select t.* from JBPM6_FLOW_USER_MATER t where "
                + "t.status = 1 and t.username=? ");
        params.add(userName);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        String materIds  = "";
        for (Map<String, Object> map : list) {
            String materId = map.get("MATER_ID")==null?"":map.get("MATER_ID").toString();
            if(StringUtils.isNotEmpty(materId)){
                materIds += ","+materId;
            }
        }
        if(StringUtils.isNotEmpty(materIds)){
            materIds = materIds.substring(1, materIds.length());
        }
        return materIds;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findByTaskId(String taskId, String exeId,
            HttpServletRequest request) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql =new StringBuffer("select t.*,t2.MATER_FPARA ");
        sql.append(" from T_WSBS_SERVICEITEM t3, JBPM6_TASK t1, ");
        sql.append(" T_WSBS_SERVICEITEM_MATER t2,T_WSBS_APPLYMATER t ");
        sql.append(" where t3.bdlcdyid = t1.def_id and t3.ITEM_ID = t2.ITEM_ID  ");
        sql.append(" and t2.mater_id = t.mater_id and t.MATER_CLSMLX<>'综合件' and t1.task_id = ?");
        params.add(taskId);
        String MATER_CODE = request.getParameter("MATER_CODE");
        if(StringUtils.isNotEmpty(MATER_CODE)){
            sql.append(" and t.MATER_CODE like ? ");
            params.add("%"+MATER_CODE+"%");
        }
        String MATER_NAME = request.getParameter("MATER_NAME");
        if(StringUtils.isNotEmpty(MATER_NAME)){
            sql.append(" and t.MATER_NAME like ? ");
            params.add("%"+MATER_NAME+"%");
        }
        sql.append(" ORDER BY t2.MATER_SN ASC ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        List<Map<String,Object>> maters = new ArrayList<Map<String,Object>>();
        Map<String,Object> flowInfo = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(exeId)&&!"null".equals(exeId)){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            flowInfo = flowExe;
            /*if(flowInfo.get("FLOW_STAGE")==null||flowInfo.get("FLOW_STAGE").toString().equals("1")){
                
            }else if(flowInfo.get("FLOW_STAGE")!=null&&flowInfo.get("FLOW_STAGE").toString().equals("2")){
                
            }*/
        }
        for(Map<String,Object> mater:list){
            String materParam = (String) mater.get("MATER_FPARA");
            if(StringUtils.isNotEmpty(materParam)){
                String isAdd = "";
                StringBuffer exeCode =new StringBuffer("if(").append(materParam).append("){ isAdd=\"true\";}");
                try {
                    Interpreter it = new Interpreter();
                    it.set("flowInfo", flowInfo);
                    it.eval(exeCode.toString());
                    isAdd = (String) it.get("isAdd");
                    if(StringUtils.isNotEmpty(isAdd)&&isAdd.equals("true")){
                        maters.add(mater);
                    }
                } catch (EvalError e) {
                    log.error(e.getMessage());
                }
            }else{
                maters.add(mater);
            }
        }
        return maters;
    }

    @Override
    public void updateFlowUserMaterStatus(String ids,String nextTaskName) {
        // TODO Auto-generated method stub
        dao.updateFlowUserMaterStatus(ids,nextTaskName);
    }

    @Override
    public List<Map<String, Object>> findByMaterIds(String userName,
            String exeId, String taskId) {
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(" from T_WSBS_APPLYMATER T ");
        sql.append(" WHERE T.MATER_ID in ");
        sql.append("(select M.MATER_ID from JBPM6_FLOW_USER_MATER M WHERE M.USERNAME =? AND M.EXEID=? AND M.TASKID=?)");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Object> param = new ArrayList<Object>();
        param.add(userName);
        param.add(exeId);
        param.add(taskId);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }


    public List<Map<String, Object>> findFilterMater(String sysUserName,String currentNodeName, String exeId) {
        StringBuffer sql = new StringBuffer("select T.MATER_ID,T.MATER_CODE,T.MATER_NAME,T.RECEIVE_TYPES");
        sql.append(" from T_WSBS_APPLYMATER T ");
        sql.append(" WHERE T.MATER_ID in ");
        sql.append("(select M.MATER_ID from JBPM6_FLOW_USER_MATER M WHERE M.USERNAME =? AND M.EXEID=? ");
        sql.append(" AND M.NEXT_TASK_NAME=? AND M.STATUS = 1 ) ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        List<Object> param = new ArrayList<Object>();
        param.add(sysUserName);
        param.add(exeId);
        param.add(currentNodeName);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }

    @Override
    public List<Map<String, Object>> findAgainMater(String sysUserName,
            String currentNodeName, String exeId) {
        StringBuffer sql = new StringBuffer("select * ");
        sql.append(" from JBPM6_FLOW_AGAIN_MATER M WHERE M.USERNAME =? AND M.EXEID=? ");
        sql.append(" AND M.NEXT_TASK_NAME=? ");
        sql.append(" ORDER BY M.CREATE_TIME DESC ");
        List<Object> param = new ArrayList<Object>();
        param.add(sysUserName);
        param.add(exeId);
        param.add(currentNodeName);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    /**
     * 
     * 描述   获取服务事项材料编码
     * @author Danto Huang
     * @created 2016-9-23 上午8:39:12
     * @param itemId
     * @return
     */
    public String getMaxNumCode(String itemId){
        int num = dao.getMaxSn(itemId)+1;
        String numcode = StringUtils.leftPad(String.valueOf(num), 2, '0');
        return numcode;
    }
    
    /**
     * 
     * 描述   根据事项编码获取最大材料编码
     * @author Danto Huang
     * @created 2016-10-20 上午9:35:21
     * @param itemId
     * @return
     */
    public String getMaxMaterCodeByItemCode(String itemCode,String maxCode){
        String codeLike = itemCode + "_";
        String sql = "select max(t.mater_code) mater_code from T_WSBS_APPLYMATER t "
                + "where t.mater_code like '" + codeLike + "%'";
        Map<String,Object> code = dao.getByJdbc(sql, null);
        if(code==null||code.get("mater_code")==null){
            return itemCode+"_"+maxCode;
        }else{
            String mater_code = code.get("mater_code").toString();
            int oldCode = 0;
            if(mater_code.contains("_")){
                oldCode = Integer.parseInt(mater_code.substring(mater_code.lastIndexOf("_")+1));
            }else{
                oldCode = Integer.parseInt(mater_code.substring(mater_code.length()-2));
            }
            if(oldCode>=Integer.parseInt(maxCode)){
                return itemCode+"_"+StringUtils.leftPad(String.valueOf(oldCode+1), 2, '0');
            }else{
                return itemCode+"_"+maxCode;
            }
        }
    }
    
    /**
     * 
     * 描述    业务办理分类列表
     * @author Danto Huang
     * @created 2018年9月3日 下午3:22:16
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findHandleTypeList(String itemId){
        StringBuffer sql = new StringBuffer("select a.RECORD_ID,a.BUSCLASS_NAME as bus_handle_type ");
        sql.append("from T_WSBS_SERVICEITEM_BUSCLASS a where a.item_id=? ");
        sql.append("order by a.BUSCLASS_NAME ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{itemId}, null);
        return list;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/8/31 15:02:00
     * @param
     * @return
     */
    public List<Map<String, Object>> findHandleTypeListE(String itemId) {
        StringBuffer sql = new StringBuffer("select a.RECORD_ID,a.BUSCLASS_NAME as bus_handle_type ");
        sql.append("from T_WSBS_SERVICEITEM_BUSCLASS a where a.item_id=? and a.is_shownet = '1' ");
        sql.append("order by a.BUSCLASS_NAME ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{itemId}, null);
        return list;
    }
    /**
     * 
     * 描述    获取材料信息列表
     * @author Danto Huang
     * @created 2019年3月7日 下午4:41:50
     * @param itemId
     * @return
     */
    public List<Map<String,Object>> findMatersByItemId(String itemId){
        StringBuffer sql = new StringBuffer();
        sql.append("select t.MATER_ID,t.MATER_SSYW from T_WSBS_APPLYMATER t ");
        sql.append("left join T_WSBS_SERVICEITEM_MATER s on s.MATER_ID = T.MATER_ID ");
        sql.append("where s.ITEM_ID=?");
        return dao.findBySql(sql.toString(), new Object[]{itemId}, null);
    }

    /**
     * 描述:根据事项ID获取材料列表
     *
     * @author Madison You
     * @created 2020/11/3 17:37:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findMaterListByItemId(String itemId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_WSBS_APPLYMATER t ");
        sql.append("left join T_WSBS_SERVICEITEM_MATER s on s.MATER_ID = T.MATER_ID ");
        sql.append("where s.ITEM_ID=?");
        return dao.findBySql(sql.toString(), new Object[]{itemId}, null);
    }


}
