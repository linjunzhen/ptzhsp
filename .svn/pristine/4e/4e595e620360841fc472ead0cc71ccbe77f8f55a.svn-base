/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.FileAttachDao;

/**
 * 描述  系统附件操作dao
 * @author  Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("fileAttachDao")
public class FileAttachDaoImpl extends BaseDaoImpl implements FileAttachDao {

    /**
     * 
     * 描述 更新业务表字段
     * @author Flex Hu
     * @created 2014年10月2日 上午10:48:45
     * @param fileIds
     * @param recordId
     */
    public void updateTableName(String fileIds,String recordId){
        StringBuffer sql = new StringBuffer("update T_MSJW_SYSTEM_FILEATTACH T SET T.")
                .append("BUS_TABLERECORDID=? WHERE T.FILE_ID IN ");
        String ids = StringUtil.getValueArray(fileIds);
        sql.append(ids);
        this.jdbcTemplate.update(sql.toString(), new Object[]{recordId});
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
        StringBuffer sql = new StringBuffer("update T_MSJW_SYSTEM_FILEATTACH T SET T.")
            .append("BUS_TABLERECORDID=? WHERE T.FILE_ID IN ");
        if(fileIds!=null&&fileIds.length>0){
            String ids = StringUtil.getStringByArray(fileIds);            
            sql.append(StringUtil.getValueArray(ids));
            this.jdbcTemplate.update(sql.toString(), new Object[]{recordId});
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
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_FILEATTACH T WHERE ")
                .append("T.BUS_TABLENAME=? AND T.BUS_TABLERECORDID=? order by T.CREATE_TIME ASC ");
        return this.findBySql(sql.toString(), new Object[]{busTableName,busRecordId},null);
    }
    
    /**
     * 
     * 描述 获取结果附件列表
     * @author Flex Hu
     * @created 2014年10月2日 上午11:01:56
     * @param EXEID
     * @return
     */
    public List<Map<String,Object>> findListByResultExeId(String exeId){
        StringBuffer sql = new StringBuffer("select * from T_MSJW_SYSTEM_FILEATTACH T WHERE ")
                .append(" T.FILE_ID IN ( SELECT RESULT_FILE_ID FROM JBPM6_FLOW_RESULT WHERE EXE_ID=?) ORDER BY T.CREATE_TIME ASC");
        return this.findBySql(sql.toString(), new Object[]{exeId},null);
    }
    /**
     * 
     * @param file_id
     * @return
     */
    public Map<String, Object> getFileAttachObject(String file_id) {
        String sql = new String("select T.* from T_MSJW_SYSTEM_FILEATTACH T WHERE T.FILE_ID=? ");
        return this.getByJdbc(sql, new Object[] { file_id });
    }
    /**
     * 
     * @param file_id
     * @return
     */
    public Map<String, Object> getBirthFileInfo(String exeid) {
        StringBuffer sql = new StringBuffer("select t.* from T_BSFW_PTJINFO  t inner join jbpm6_execution e on");
        sql.append(" t.yw_id=e.bus_recordid where e.exe_id='"+exeid+"'");
        return this.getByJdbc(sql.toString(), null);
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
    public List<Map<String, Object>> findList(String busTableName, String busRecordId, String attachKey) {
        StringBuffer sql = new StringBuffer("select * from ");
        sql.append("T_MSJW_SYSTEM_FILEATTACH F WHERE F.BUS_TABLENAME=? AND ")
                .append("F.BUS_TABLERECORDID=? AND F.ATTACH_KEY=? ").append(" ORDER BY F.CREATE_TIME ASC ");
        return this.findBySql(sql.toString(), new Object[] { busTableName, busRecordId, attachKey }, null);
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
    public boolean isExists(String attachKey, String fileIds){
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append(" T_MSJW_SYSTEM_FILEATTACH F WHERE ").append("F.ATTACH_KEY=? AND F.FILE_ID in")
                .append(StringUtil.getValueArray(fileIds));
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[]{attachKey});
        if(count!=0){
            return true;
        }else{
            return false;
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
        StringBuffer sql = new StringBuffer("SELECT F.FILE_ID,F.FILE_NAME,F.FILE_PATH");
        sql.append(" FROM T_MSJW_SYSTEM_FILEATTACH F WHERE ");
        sql.append(" F.FLOW_EXEID=? AND F.TPL_ID=? ");
        sql.append(" ORDER BY F.CREATE_TIME DESC");
        List<Map<String,Object>> files = this.findBySql(sql.toString(), 
                new Object[]{exeId,tplId}, null);
        if(files!=null&&files.size()>0){
            return files.get(0);
        }else{
            return null;
        }
    }
/**
 * 
 * 描述 更新出生一件事附件内容
 * @author Yanisin Shi
 * @param fileId
 * @see net.evecom.platform.system.dao.FileAttachDao#updateBirthFileByFileId(java.lang.String)
 */
    @Override
    public void updateBirthFileByFileId(String fileid,String yw_id,String birth_file_id,String birth_rile_url) {
        StringBuffer sql = new StringBuffer("update T_BSFW_PTJINFO set birth_file_id='"+birth_file_id
                + "',birth_rile_url='"+birth_rile_url
                + "' where yw_id='"
                +yw_id+ "'");
        this.executeSql(sql.toString(), null);
        StringBuffer delsql = new StringBuffer("delete from T_MSJW_SYSTEM_FILEATTACH where file_id='"+fileid+"'"); 
        this.executeSql(delsql.toString(), null);
    }
}
