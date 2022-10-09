/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import icepdf.a;
import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.wsbs.dao.TwQualificationDao;
import net.evecom.platform.wsbs.service.TwQualificationService;

/**
 * 描述   台湾地区职业资格采信证书相关业务操作Service
 * @author Allin Lin
 * @created 2019年5月24日 下午3:19:53
 */
@Service("twQualificationService")
public class TwQualificationServiceImpl extends BaseServiceImpl implements TwQualificationService{
 
    /**
     * 所引入的dao
     */
    @Resource
    private TwQualificationDao dao;
    
    /** 
     * 描述  覆盖获取实体dao方法 描述
     * @author Allin Lin
     * @created 2019年5月24日 下午3:23:20
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 描述    修改或保存台湾地区职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:25:56
     * @param data
     * @param entityId
     * @return
     */
    public String saveOrUpdateTwQualify(Map<String, Object> data,String entityId){
        String recordId=dao.saveOrUpdate(data, "T_TW_QUALIFICATION", entityId);
        return recordId;
    }
    
    /**
     * 描述    删除台湾地区职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月27日 上午10:49:05
     * @param qualificationIds
     */
    public void removeCascade(String[] qualificationIds){
        for (String qualificationId : qualificationIds) {
            StringBuffer sql = new StringBuffer("update T_TW_QUALIFICATION")
                    .append(" set DISABLED = '1'")
                    .append(" WHERE QUALIFICATION_ID = ? ");
            dao.executeSql(sql.toString(), new Object[]{qualificationId});
        }
    }
    
    /**
     * 描述    根据sqlFilter获取列表
     * @author Allin Lin
     * @created 2019年5月27日 上午11:18:05
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.* from T_TW_QUALIFICATION t")
                .append(" where t.disabled = '0'");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;   
    }
    
    /**
     * 描述   验证台胞证件号是否已经存在
     * @author Allin Lin
     * @created 2019年5月28日 下午3:14:32
     * @param zjhm
     * @return
     */
    public boolean isExist(String zsNumber){
        boolean flag=true;
        StringBuffer sql = new StringBuffer(
                "select t.* from T_TW_QUALIFICATION t where t.zs_number = ? and t.disabled=0");
        Map<String, Object> record=dao.getByJdbc(sql.toString(),new Object[]{zsNumber});
        if(record==null)
            flag=false;
        return flag;
    }
    
    /**
     * 描述    根据姓名、证书编号获取职业资格采信证书信息
     * @author Allin Lin
     * @created 2019年5月30日 上午10:43:53
     * @param username
     * @param zsNum
     * @return 
     */
    public List<Map<String, Object>> getTwQualification(String username,String zsNum){
        StringBuffer sql=new StringBuffer("select t.*,sex.dic_name zssex from T_TW_QUALIFICATION t")
                .append(" left join (select d.dic_code, d.dic_name")
                .append(" from t_msjw_system_dictionary d")
                .append(" where d.type_id = (select type_id")
                .append(" from t_msjw_system_dictype")
                .append(" where type_code = 'sex')) sex")
                .append(" on sex.dic_code = t.sex")
                .append("  where t.disabled='0'")
                .append(" and  t.USERNAME = ?")
                .append(" and t.ZS_NUMBER = ?")
                .append(" order  by CXZS_TIME asc");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[]{username,zsNum}, null);
        return list;
    }
}
