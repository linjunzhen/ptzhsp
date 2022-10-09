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
import net.evecom.platform.smoga.dao.AgencyServiceDao;
import net.evecom.platform.smoga.service.AgencyServiceService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月9日 下午3:52:32
 */
@Service("agencyServiceService")
public class AgencyServiceServiceImpl extends BaseServiceImpl implements AgencyServiceService {

    /**
     * 引入dao
     */
    @Resource
    private AgencyServiceDao dao;

    /**
     * 
     * 描述
     * @author Danto Huang
     * @created 2018年8月9日 下午3:55:05
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
     * @created 2018年8月9日 下午4:37:45
     * @param sqlfilter
     * @return
     */
    public List<Map<String,Object>> findAgencyServiceBySqlFilter(SqlFilter sqlfilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.*,LX.DIC_NAME AGENCY_ORG_TYPE_NAME FROM T_SMOGA_AGENCYSERVICE T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID=T.IMPL_DEPART_ID ");
        sql.append("LEFT JOIN (SELECT D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY D WHERE D.TYPE_ID=(");
        sql.append("SELECT TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE DT WHERE DT.TYPE_CODE='agencyType' )) LX ");
        sql.append("ON LX.DIC_CODE=T.AGENCY_ORG_TYPE ");
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
        String exeSql = dao.getQuerySql(sqlfilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlfilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    获取最大排序值
     * @author Danto Huang
     * @created 2018年8月2日 下午6:10:28
     * @return
     */
    public int getMaxSn() {
        return dao.getMaxSn();
    }
    /**
     * 
     * 描述    获取当前版本
     * @author Danto Huang
     * @created 2018年8月2日 下午6:11:18
     * @return
     */
    public int getCurVersion(){
        return dao.getCurVersion();
    }

    /**
     * 
     * 描述    保存变更记录到历史版本库
     * @author Danto Huang
     * @created 2018年8月10日 上午9:59:04
     * @param serviceId
     */
    public void copyToHis(String serviceId){
        StringBuffer sql = new StringBuffer();
        sql.append("insert into T_SMOGA_AGENCYSERVICE_HIS(SERVICE_ID,ITEM_CODE,ITEM_NAME,AGENCY_ITEM_NAME,");
        sql.append("AGENCY_ITEM_CODE,AGENCY_ORG_TYPE,CHARGE_STANDARD_BASIS,SET_BASIS,TIME_LIMIT_BASIS,");
        sql.append("IMPL_DEPART_ID,IMPL_DEPART_NAME,TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,AGENCY_REMARK,");
        sql.append("C_SN,C_VERSION,STATUS,CREATE_TIME,MODIFY_TYPE,MODIFY_DESC,MODIFY_TIME,AUDITOR_ID,");
        sql.append("AUDITOR_NAME,AUDITOR_TIME,AUDITOR_OPINION) select SERVICE_ID,ITEM_CODE,ITEM_NAME,");
        sql.append("AGENCY_ITEM_NAME,AGENCY_ITEM_CODE,AGENCY_ORG_TYPE,CHARGE_STANDARD_BASIS,SET_BASIS,");
        sql.append("TIME_LIMIT_BASIS,IMPL_DEPART_ID,IMPL_DEPART_NAME,TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,");
        sql.append("AGENCY_REMARK,C_SN,C_VERSION,STATUS,CREATE_TIME,MODIFY_TYPE,MODIFY_DESC,MODIFY_TIME,");
        sql.append("AUDITOR_ID,AUDITOR_NAME,AUDITOR_TIME,AUDITOR_OPINION from T_SMOGA_AGENCYSERVICE t ");
        sql.append("where t.service_id=?");
        dao.executeSql(sql.toString(), new Object[]{serviceId});
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
        String sql = "select * from T_SMOGA_AGENCYSERVICE_HIS where service_id=?";
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
        sql.append("T_SMOGA_AGENCYSERVICE_HIS D WHERE D.SERVICE_ID=? and D.EFFECT_STATUS=1 ORDER BY D.C_VERSION DESC");
        return dao.findBySql(sql.toString(), new Object[] { entityId }, null);
    }
    
    /**
     * 
     * 描述    复制
     * @author Danto Huang
     * @created 2018年8月17日 上午9:54:43
     * @param serviceIds
     * @param departIds
     * @param departNames
     * @param idAddress
     */
    public void copyToOtherDepart(String serviceIds,String departIds,String departNames,String idAddress){
        String[] serviceId = serviceIds.split(",");
        String[] departId = departIds.split(",");
        String[] departName = departNames.split(",");
        for(int i=0;i<serviceId.length;i++){
            Map<String, Object> serviceInfo = dao.getByJdbc("T_SMOGA_AGENCYSERVICE", new String[] { "SERVICE_ID" },
                    new Object[] { serviceId[i] });
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("ITEM_CODE", serviceInfo.get("ITEM_CODE"));
            variables.put("ITEM_NAME", serviceInfo.get("ITEM_NAME"));
            variables.put("AGENCY_ITEM_NAME", serviceInfo.get("AGENCY_ITEM_NAME"));
            variables.put("AGENCY_ITEM_CODE", serviceInfo.get("AGENCY_ITEM_CODE"));
            variables.put("AGENCY_ORG_TYPE", serviceInfo.get("AGENCY_ORG_TYPE"));
            variables.put("CHARGE_STANDARD_BASIS", serviceInfo.get("CHARGE_STANDARD_BASIS"));
            variables.put("SET_BASIS", serviceInfo.get("SET_BASIS"));
            variables.put("TIME_LIMIT_BASIS", serviceInfo.get("TIME_LIMIT_BASIS"));
            variables.put("TOGETHER_DEPART_ID", serviceInfo.get("TOGETHER_DEPART_ID"));
            variables.put("TOGETHER_DEPART_NAME", serviceInfo.get("TOGETHER_DEPART_NAME"));
            variables.put("IMPUTATION_SITUATION", serviceInfo.get("IMPUTATION_SITUATION"));
            variables.put("IMPL_OBJECT", serviceInfo.get("IMPL_OBJECT"));
            variables.put("AGENCY_REMARK", serviceInfo.get("AGENCY_REMARK"));
            variables.put("STATUS", serviceInfo.get("STATUS"));
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
            for(int j=0;j<departId.length;j++){
                variables.put("IMPL_DEPART_ID", departId[j]);
                variables.put("IMPL_DEPART_NAME", departName[j]);
                int maxSn = this.getMaxSn();
                variables.put("C_SN", maxSn+1);
                String recordId = dao.saveOrUpdate(variables, "T_SMOGA_AGENCYSERVICE", null);
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("OPERATE_CONTENT", "由" + serviceInfo.get("IMPL_DEPART_NAME") + "的名称为【"
                        + serviceInfo.get("ITEM_NAME") + "】的项目复制创建");
                data.put("OPERATE_TYPE","11");
                data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                SysUser sysUser = AppUtil.getLoginUser();
                data.put("FULLNAME",sysUser.getFullname());
                data.put("USERNAME",sysUser.getUsername());
                data.put("USERID", sysUser.getUserId());
                data.put("IP_ADDRESS",idAddress);
                data.put("BUSRECORD_ID",recordId);
                data.put("BUSTABLE_NAME","T_SMOGA_AGENCYSERVICE");
                dao.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
            }
        }
    }
}
