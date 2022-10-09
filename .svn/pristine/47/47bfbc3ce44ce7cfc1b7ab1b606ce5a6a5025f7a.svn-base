/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.DomesticControllerDao;
import net.evecom.platform.zzhy.service.DomesticControllerService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 企业登记信息操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("domesticControllerService")
public class DomesticControllerServiceImpl extends BaseServiceImpl implements DomesticControllerService {
    /**
     * 所引入的dao
     */
    @Resource
    private DomesticControllerDao dao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/10/20 11:13:00
     * @param
     * @return
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @Override
    public List<Map<String, Object>> findByParentId(String parentId, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.INDU_ID,T.INDU_NAME,T.INDU_CODE, ");
        sql.append("  s.indu_code as findu_code,T.TREE_LEVEL,t.parent_id ");
        sql.append(" from T_WSBS_INDUSTRY T LEFT JOIN T_WSBS_INDUSTRY S ");  
        sql.append(" on (substr(t.path, 0, instr(t.path, '.', 1, 3) - 1) = s.path or t.path = s.path) ");
        sql.append(" WHERE S.TREE_LEVEL =1 ");
        if(StringUtils.isNotEmpty(parentId)){
            sql.append(" and T.PARENT_ID=?");
            params.add(parentId);
        }
        for (String key : variables.keySet()) {  
            String paramName = key;
            if (paramName.startsWith("Q_")) {
                String paramValue =  (String)variables.get(paramName);
                if (StringUtils.isNotEmpty(paramValue)) {
                    SqlFilter.addRequestQueryParam(paramName, paramValue, sql, params);
                }
            }
        } 
        sql.append(" ORDER BY  T.TREE_LEVEL asc , T.indu_code ASC, T.CREATE_TIME DESC ");
        List<Map<String, Object>> list =  dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    @Override
    public String getCodeById(String id) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer(" SELECT replace(wm_concat(A.INDU_CODE),',','') as INDU_CODE ");
        sql.append(" from (SELECT I.INDU_CODE FROM T_WSBS_INDUSTRY I START WITH I.INDU_ID = ? "); 
        sql.append("CONNECT BY PRIOR I.PARENT_ID = I.INDU_ID and i.parent_id<>'0' ORDER BY I.TREE_LEVEL ASC) A ");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        Map<String, Object> map = dao.getByJdbc(sql.toString(), params.toArray());
        if(null!=map&&map.size()>0){
            return (String)map.get("INDU_CODE");
        }
        return null;
    }
    @Override
    public List<Map<String, Object>> findByRelatedItemSqlFilter(SqlFilter sqlFilter, Map<String, Object> variables) {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select * from T_COMMERCIAL_RELATED_ITEM T WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    @Override
    public List<Map<String, Object>> findByRelatedItemId(String ids) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("select T.* ");
        sql.append(" FROM T_COMMERCIAL_RELATED_ITEM T WHERE t.ITEM_CODE IN　");
        sql.append(StringUtil.getValueArray(ids));
        sql.append(" ORDER BY T.SSBMBM DESC,T.RELATED_ID DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    /**
     * 
     * 描述 根据itemCodes获取在线编辑附件
     * @author Danto Huang
     * @created 2017年9月12日 下午12:43:16
     * @param itemCodes
     * @return
     */
    public List<Map<String ,Object>> findOnlineMaterByItemCodes(String itemCodes){
        StringBuffer sql = new StringBuffer("");
        sql.append("select t.* from T_COMMERCIAL_RELATMATER t ");
        sql.append("left join t_wsbs_serviceitem s on t.item_id = s.item_id ");
        sql.append("where t.is_form=1 and s.item_code in ").append(StringUtil.getValueArray(itemCodes));
        return dao.findBySql(sql.toString(),null,null);
    }

    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select T.* FROM T_WSBS_BUSSCOPE T ");
        sql.append(" where 1=1 ");
        if(sqlFilter.getQueryParams().get("Q_T.BUSSCOPE_NAME_LIKE")!=null){
            String name = sqlFilter.getQueryParams().get("Q_T.BUSSCOPE_NAME_LIKE").toString();
            sqlFilter.removeFilter("Q_T.BUSSCOPE_NAME_LIKE");
            if(StringUtils.isNotEmpty(name)){
                sql.append(" and ");
                sql.append(" ( ");
                sql.append(" t.busscope_name like '%");
                sql.append(name);
                sql.append("%' ");
                sql.append(" or ");
                sql.append(" t.remark like '%");
                sql.append(name);
                sql.append("%' ");
                sql.append(" or ");
                sql.append(" t.indu_name like '%");
                sql.append(name);
                sql.append("%' ");
                sql.append(" or ");
                sql.append(" t.gftmxghd like '%");
                sql.append(name);
                sql.append("%' ");
                sql.append(" or ");
                sql.append(" t.fgftmxghd like '%");
                sql.append(name);
                sql.append("%' ");
                sql.append(" ) ");
            }
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据用户IDS获取列表数据
     * @author Flex Hu
     * @created 2015年9月23日 下午5:18:22
     * @param userIds
     * @return
     */
    public List<Map<String,Object>> findByBusinessScopeId(String ids){
        StringBuffer sql = new StringBuffer("select U.*");
        sql.append(" FROM T_WSBS_BUSSCOPE U WHERE U.ID IN　");
        sql.append(StringUtil.getValueArray(ids));
        sql.append(" ORDER BY instr('").append(ids).append("',U.ID)");
          //String ids1=ids.replace("(","");
//        sql.append("  ORDER BY DECODE(ID,").append(ids1).append(")");
//        sql.append(" ORDER BY U.BUSSCOPE_CODE ASC ");
        return dao.findBySql(sql.toString(),null,null);
    }

    /**
     * 描述:获取公司类型
     *
     * @author Madison You
     * @created 2020/10/20 10:56:00
     * @param
     * @return
     */
    @Override
    public String getCompanyType(Map<String, Object> busMap) {
        StringBuffer companyType = new StringBuffer();
        String sql = "SELECT TYPE_NAME FROM T_MSJW_SYSTEM_DICTYPE WHERE TYPE_CODE = ?";
        List<Map<String, Object>> settypeList = dao.findBySql(sql, new Object[]{busMap.get("COMPANY_SETTYPE")}, null);
        if (settypeList != null && !settypeList.isEmpty()) {
            companyType.append(settypeList.get(0).get("TYPE_NAME")).append("，");
        }
        List<Map<String, Object>> typecodeList = dao.findBySql(sql, new Object[]{busMap.get("COMPANY_TYPECODE")}, null);
        if (typecodeList != null && !typecodeList.isEmpty()) {
            companyType.append(typecodeList.get(0).get("TYPE_NAME")).append("，");
        }
        String setnatureName = dictionaryService.getDicNames(busMap.get("COMPANY_TYPECODE").toString(), busMap.get("COMPANY_SETNATURE").toString());
        if (StringUtils.isNotEmpty(setnatureName)) {
            companyType.append(setnatureName);
        }
        return companyType.toString();
    }

    /**
     * 
     * 描述 根据按钮IDS删除掉记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:49:10
     * @param buttonIds
     */
    public void deleteBusinessScope(String[] ids){
        dao.remove("T_WSBS_BUSSCOPE","ID",ids);
    }
}
