/*
 * Copyright (c) 2005, 2018, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.smoga.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.smoga.dao.PreApprovalDao;
import net.evecom.platform.smoga.service.PreApprovalService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月7日 下午4:42:53
 */
@Service("preApprovalService")
public class PreApprovalServiceImpl extends BaseServiceImpl implements PreApprovalService {
    /**
     * 引入dao
     */
    @Resource
    private PreApprovalDao dao;
    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2018年8月1日 上午10:10:19
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述    查询数据列表
     * @author Danto Huang
     * @created 2018年8月8日 上午9:00:08
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findPreApprovalsBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.* FROM T_SMOGA_PREAPPROVAL T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID=T.IMPL_DEPART_ID ");
        sql.append("WHERE 1=1 ");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            sql.append(" AND D.DEPART_CODE in ").append(
                    StringUtil.getValueArray(authDepCodes));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述    获取最大排序值
     * @author Danto Huang
     * @created 2018年8月8日 下午4:49:01
     * @return
     */
    public int getMaxSn() {
        return dao.getMaxSn();
    }
    /**
     * 
     * 描述    获取当前版本
     * @author Danto Huang
     * @created 2018年8月8日 下午4:50:45
     * @return
     * @see net.evecom.platform.smoga.service.PreApprovalService#getCurVersion()
     */
    public int getCurVersion(){
        return dao.getCurVersion();
    } 

    /**
     * 
     * 描述    保存变更记录到历史版本库
     * @author Danto Huang
     * @created 2018年8月9日 上午9:17:36
     * @param preId
     */
    public void copyToHis(String preId){
        StringBuffer sql = new StringBuffer();
        sql.append("insert into T_SMOGA_PREAPPROVAL_HIS(PRE_ID,ITEM_CODE,ITEM_NAME,IMPL_DEPART_ID,IMPL_DEPART_NAME,");
        sql.append("TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,PRE_ITEMCODES,PRE_ITEMNAMES,PRE_ITEMNAMES_WRITE,");
        sql.append("PRE_BASIS,PRE_REMARK,C_SN,C_VERSION,STATUS,CREATE_TIME,MODIFY_TYPE,MODIFY_DESC,MODIFY_TIME,");
        sql.append("AUDITOR_ID,AUDITOR_NAME,AUDITOR_TIME,AUDITOR_OPINION,IS_PRE_ENTER,NOTENTER_DEPART,");
        sql.append("NOTENTER_ITEMNAME,NOTENTER_CONTACTS,NOTENTER_PHONE,NOTENTER_CONTACTTIME,INFORMANT_NAME,");
        sql.append("INFORMANT_PHONE,NOTENTER_REMARK) select PRE_ID,ITEM_CODE,ITEM_NAME,");
        sql.append("IMPL_DEPART_ID,IMPL_DEPART_NAME,TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,PRE_ITEMCODES,");
        sql.append("PRE_ITEMNAMES,PRE_ITEMNAMES_WRITE,PRE_BASIS,PRE_REMARK,C_SN,C_VERSION,STATUS,CREATE_TIME,");
        sql.append("MODIFY_TYPE,MODIFY_DESC,MODIFY_TIME,AUDITOR_ID,AUDITOR_NAME,AUDITOR_TIME,AUDITOR_OPINION,");
        sql.append("IS_PRE_ENTER,NOTENTER_DEPART,NOTENTER_ITEMNAME,NOTENTER_CONTACTS,NOTENTER_PHONE,");
        sql.append("NOTENTER_CONTACTTIME,INFORMANT_NAME,INFORMANT_PHONE,NOTENTER_REMARK ");
        sql.append("from T_SMOGA_PREAPPROVAL t where t.pre_id=?");
        dao.executeSql(sql.toString(), new Object[]{preId});
    }
    /**
     * 
     * 描述    保存
     * @author Danto Huang
     * @created 2018年8月3日 上午11:38:52
     * @param variables
     * @return
     */
    public String saveOrUpdateRecord(Map<String,Object> variables){
        return dao.saveOrUpdateRecord(variables);
    }
    
    /**
     * 
     * 描述    获取流转日志
     * @author Danto Huang
     * @created 2018年8月13日 上午10:09:25
     * @param busTableName
     * @param busRecordId
     * @return
     */
    public List<Map<String,Object>> findTransLog(String busTableName,String busRecordId){
        String sql = "select * from T_SMOGA_BILLOFRIGHTS_LOG t where t.bustable_name=? and t.busrecord_id=? "
                + "order by t.operate_time desc";
        return dao.findBySql(sql, new Object[]{busTableName,busRecordId}, null);
    }
    
    /**
     * 
     * 描述    判断是否存在历史版本
     * @author Danto Huang
     * @created 2018年8月13日 下午3:17:44
     * @param entityId
     * @return
     */
    public boolean isHavingHis(String entityId){
        String sql = "select * from T_SMOGA_PREAPPROVAL_HIS where pre_id=?";
        List list = dao.findBySql(sql, new Object[]{entityId}, null);
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 
     * 描述    历史版本查询列表
     * @author Danto Huang
     * @created 2018年8月13日 下午3:28:27
     * @param entityId
     * @return
     */
    public List<Map<String,Object>> findHisVersionForSelect(String entityId){
        StringBuffer sql = new StringBuffer("select D.C_VERSION as DIC_CODE,D.C_VERSION as DIC_NAME FROM ");
        sql.append("T_SMOGA_PREAPPROVAL_HIS D WHERE D.PRE_ID = ? and D.EFFECT_STATUS=1 ORDER BY D.C_VERSION DESC");
        return dao.findBySql(sql.toString(), new Object[] { entityId }, null);
    }
    
    /**
     * 
     * 描述    复制
     * @author Danto Huang
     * @created 2018年8月17日 上午9:54:43
     * @param rightIds
     * @param departIds
     * @param departNames
     * @param idAddress
     */
    public void copyToOtherDepart(String preIds,String departIds,String departNames,String idAddress){
        String[] preId = preIds.split(",");
        String[] departId = departIds.split(",");
        String[] departName = departNames.split(",");
        for(int i=0;i<preId.length;i++){
            Map<String, Object> preInfo = dao.getByJdbc("T_SMOGA_PREAPPROVAL", new String[] { "PRE_ID" },
                    new Object[] { preId[i] });
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("ITEM_CODE", preInfo.get("ITEM_CODE"));
            variables.put("ITEM_NAME", preInfo.get("ITEM_NAME"));
            variables.put("TOGETHER_DEPART_ID", preInfo.get("TOGETHER_DEPART_ID"));
            variables.put("TOGETHER_DEPART_NAME", preInfo.get("TOGETHER_DEPART_NAME"));
            variables.put("PRE_ITEMCODES", preInfo.get("PRE_ITEMCODES"));
            variables.put("PRE_ITEMNAMES", preInfo.get("PRE_ITEMNAMES"));
            variables.put("PRE_ITEMNAMES_WRITE", preInfo.get("PRE_ITEMNAMES_WRITE"));
            variables.put("PRE_BASIS", preInfo.get("PRE_BASIS"));
            variables.put("PRE_REMARK", preInfo.get("PRE_REMARK"));
            variables.put("STATUS", preInfo.get("STATUS"));
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
            variables.put("IS_PRE_ENTER", preInfo.get("IS_PRE_ENTER"));
            variables.put("NOTENTER_DEPART", preInfo.get("NOTENTER_DEPART"));
            variables.put("NOTENTER_ITEMNAME", preInfo.get("NOTENTER_ITEMNAME"));
            variables.put("NOTENTER_CONTACTS", preInfo.get("NOTENTER_CONTACTS"));
            variables.put("NOTENTER_PHONE", preInfo.get("NOTENTER_PHONE"));
            variables.put("NOTENTER_CONTACTTIME", preInfo.get("NOTENTER_CONTACTTIME"));
            variables.put("INFORMANT_NAME", preInfo.get("INFORMANT_NAME"));
            variables.put("INFORMANT_PHONE", preInfo.get("INFORMANT_PHONE"));
            variables.put("NOTENTER_REMARK", preInfo.get("NOTENTER_REMARK"));
            for(int j=0;j<departId.length;j++){
                variables.put("IMPL_DEPART_ID", departId[j]);
                variables.put("IMPL_DEPART_NAME", departName[j]);
                int maxSn = this.getMaxSn();
                variables.put("C_SN", maxSn+1);
                String recordId = dao.saveOrUpdate(variables, "T_SMOGA_PREAPPROVAL", null);
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("OPERATE_CONTENT", "由" + preInfo.get("IMPL_DEPART_NAME") + "的名称为【"
                        + preInfo.get("ITEM_NAME") + "】的项目复制创建");
                data.put("OPERATE_TYPE","11");
                data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                SysUser sysUser = AppUtil.getLoginUser();
                data.put("FULLNAME",sysUser.getFullname());
                data.put("USERNAME",sysUser.getUsername());
                data.put("USERID", sysUser.getUserId());
                data.put("IP_ADDRESS",idAddress);
                data.put("BUSRECORD_ID",recordId);
                data.put("BUSTABLE_NAME","T_SMOGA_PREAPPROVAL");
                dao.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
            }
        }
    }
}
