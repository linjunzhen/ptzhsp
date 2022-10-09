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
import net.evecom.core.util.BrowserUtils;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.smoga.dao.BillOfRightDao;
import net.evecom.platform.smoga.service.BillOfRightService;
import net.evecom.platform.system.model.SysUser;

/**
 * 描述
 * @author Danto Huang
 * @created 2018年8月1日 上午10:09:02
 */
@Service("billOfRightService")
public class BillOfRightServiceImpl extends BaseServiceImpl implements BillOfRightService {
    /**
     * 所引入的dao
     */
    @Resource
    private BillOfRightDao dao;

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
     * @created 2018年8月2日 下午3:24:19
     * @param sqlfilter
     * @return
     */
    public List<Map<String,Object>> findBillOfRightsByFilter(SqlFilter sqlfilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.RIGHT_ID,T.RIGHT_CODE,T.RIGHT_NAME,T.SUBITEM_NAME,LB.DIC_NAME RIGHT_TYPE,EXERCISE_LEVEL,");
        sql.append("T.STATUS,T.IMPL_DEPART_ID,T.IMPL_DEPART_NAME,T.C_VERSION,T.C_SN,RIGHT_SOURCE,RIGHT_SOURCE_OTHER ");
        sql.append("FROM T_SMOGA_BILLOFRIGHTS T ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_ID=T.IMPL_DEPART_ID ");
        sql.append("LEFT JOIN (SELECT D.DIC_NAME,D.DIC_CODE FROM T_MSJW_SYSTEM_DICTIONARY D WHERE D.TYPE_ID=(");
        sql.append("SELECT TYPE_ID FROM T_MSJW_SYSTEM_DICTYPE DT WHERE DT.TYPE_CODE='qzlb' )) LB ");
        sql.append("ON LB.DIC_CODE=T.RIGHT_TYPE ");
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
     * 描述    保存变更记录到历史版本库
     * @author Danto Huang
     * @created 2018年8月7日 上午11:43:11
     * @param rightId
     */
    public void copyToHis(String rightId){
        StringBuffer sql = new StringBuffer();
        sql.append("insert into T_SMOGA_BILLOFRIGHTS_HIS(RIGHT_ID,NEW_CATEGORY,RIGHT_CODE,PROVINCE_CODE,RIGHT_SOURCE,");
        sql.append("RIGHT_SOURCE_OTHER,RIGHT_NAME,SUBITEM_NAME,RIGHT_TYPE,EXERCISE_LEVEL,IMPL_BASIS,IMPL_DEPART_ID,");
        sql.append("IMPL_DEPART_NAME,TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,IMPUTATION_SITUATION,IMPL_OBJECT,REMARK,");
        sql.append("MODIFY_TYPE,MODIFY_DESC,STATUS,CREATE_TIME,C_SN,C_VERSION,AUDITOR_ID,AUDITOR_NAME,AUDITOR_TIME,");
        sql.append("AUDITOR_OPINION,MODIFY_TIME) select RIGHT_ID,NEW_CATEGORY,RIGHT_CODE,PROVINCE_CODE,RIGHT_SOURCE,");
        sql.append("RIGHT_SOURCE_OTHER,RIGHT_NAME,SUBITEM_NAME,RIGHT_TYPE,EXERCISE_LEVEL,IMPL_BASIS,IMPL_DEPART_ID,");
        sql.append("IMPL_DEPART_NAME,TOGETHER_DEPART_ID,TOGETHER_DEPART_NAME,IMPUTATION_SITUATION,IMPL_OBJECT,REMARK,");
        sql.append("MODIFY_TYPE,MODIFY_DESC,STATUS,CREATE_TIME,C_SN,C_VERSION,AUDITOR_ID,AUDITOR_NAME,AUDITOR_TIME,");
        sql.append("AUDITOR_OPINION,MODIFY_TIME from T_SMOGA_BILLOFRIGHTS t where t.right_id=?");
        dao.executeSql(sql.toString(), new Object[]{rightId});
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
        String sql = "select * from T_SMOGA_BILLOFRIGHTS_HIS where right_id=?";
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
        sql.append("T_SMOGA_BILLOFRIGHTS_HIS D WHERE D.RIGHT_ID = ? and D.EFFECT_STATUS=1 ORDER BY D.C_VERSION DESC");
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
    public void copyToOtherDepart(String rightIds,String departIds,String departNames,String idAddress){
        String[] rightId = rightIds.split(",");
        String[] departId = departIds.split(",");
        String[] departName = departNames.split(",");
        for(int i=0;i<rightId.length;i++){
            Map<String, Object> rightInfo = dao.getByJdbc("T_SMOGA_BILLOFRIGHTS", new String[] { "RIGHT_ID" },
                    new Object[] { rightId[i] });
            Map<String,Object> variables = new HashMap<String, Object>();
            variables.put("NEW_CATEGORY", rightInfo.get("NEW_CATEGORY"));
            variables.put("PROVINCE_CODE", rightInfo.get("PROVINCE_CODE"));
            variables.put("RIGHT_SOURCE", rightInfo.get("RIGHT_SOURCE"));
            variables.put("RIGHT_SOURCE_OTHER", rightInfo.get("RIGHT_SOURCE_OTHER"));
            variables.put("RIGHT_NAME", rightInfo.get("RIGHT_NAME"));
            variables.put("SUBITEM_NAME", rightInfo.get("SUBITEM_NAME"));
            variables.put("RIGHT_TYPE", rightInfo.get("RIGHT_TYPE"));
            variables.put("EXERCISE_LEVEL", rightInfo.get("EXERCISE_LEVEL"));
            variables.put("IMPL_BASIS", rightInfo.get("IMPL_BASIS"));
            variables.put("TOGETHER_DEPART_ID", rightInfo.get("TOGETHER_DEPART_ID"));
            variables.put("TOGETHER_DEPART_NAME", rightInfo.get("TOGETHER_DEPART_NAME"));
            variables.put("IMPUTATION_SITUATION", rightInfo.get("IMPUTATION_SITUATION"));
            variables.put("IMPL_OBJECT", rightInfo.get("IMPL_OBJECT"));
            variables.put("REMARK", rightInfo.get("REMARK"));
            variables.put("STATUS", rightInfo.get("STATUS"));
            variables.put("C_VERSION", "V" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd.HHmmss"));
            for(int j=0;j<departId.length;j++){
                variables.put("IMPL_DEPART_ID", departId[j]);
                variables.put("IMPL_DEPART_NAME", departName[j]);
                int maxSn = this.getMaxSn();
                variables.put("C_SN", maxSn+1);
                String rightCode = "QZQD" + DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd");
                rightCode += variables.get("RIGHT_TYPE").toString();
                variables.put("RIGHT_CODE", rightCode+String.format("%04d", maxSn+1));
                String recordId = dao.saveOrUpdate(variables, "T_SMOGA_BILLOFRIGHTS", null);
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("OPERATE_CONTENT", "由" + rightInfo.get("IMPL_DEPART_NAME") + "的编码和名称为【"
                        + rightInfo.get("RIGHT_CODE") + "】【" + rightInfo.get("RIGHT_NAME") + "】的项目复制创建");
                data.put("OPERATE_TYPE","11");
                data.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                SysUser sysUser = AppUtil.getLoginUser();
                data.put("FULLNAME",sysUser.getFullname());
                data.put("USERNAME",sysUser.getUsername());
                data.put("USERID", sysUser.getUserId());
                data.put("IP_ADDRESS",idAddress);
                data.put("BUSRECORD_ID",recordId);
                data.put("BUSTABLE_NAME","T_SMOGA_BILLOFRIGHTS");
                dao.saveOrUpdate(data, "T_SMOGA_BILLOFRIGHTS_LOG", null);
            }
        }
    }
}
