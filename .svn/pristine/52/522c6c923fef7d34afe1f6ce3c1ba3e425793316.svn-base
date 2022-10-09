/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.ArrayUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.ServiceItemDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 描述 服务事项操作dao
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("serviceItemDao")
public class ServiceItemDaoImpl extends BaseDaoImpl implements ServiceItemDao {

    /**
     * 
     * 描述 根据项目编码判断是否存在记录
     * 
     * @author Flex Hu
     * @created 2015年9月22日 上午11:28:45
     * @param itemCode
     * @return
     */
    public boolean isExists(String itemCode) {
        StringBuffer sql = new StringBuffer("select count(*) from ");
        sql.append("T_WSBS_SERVICEITEM S WHERE S.ITEM_CODE=? ");
        int count = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { itemCode });
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * 描述 保存办事项目用户中间表
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:48:57
     * @param itemId
     * @param userIds
     */
    public void saveItemUsers(String itemId, String userIds) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_PREAUDITER");
        sql.append("(ITEM_ID,USER_ID) VALUES(?,?)");
        for (String userId : userIds.split(",")) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, userId });
        }
    }

    /**
     * 
     * 描述 根据项目ID获取绑定的用户IDSNAMES
     * 
     * @author Flex Hu
     * @created 2015年9月24日 下午5:57:47
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindUserIdANdNames(String itemId) {
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(SU.USER_ID) USER_IDS");
        sql.append(",WMSYS.WM_CONCAT(SU.Fullname) FULL_NAMES FROM T_MSJW_SYSTEM_SYSUSER SU");
        sql.append(" WHERE SU.USER_ID IN (select P.USER_ID from T_WSBS_PREAUDITER P");
        sql.append(" WHERE P.ITEM_ID=?) ORDER BY SU.CREATE_TIME DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { itemId });
    }

    /**
     * 
     * 描述 保存办事事项票单模板配置
     * @author Faker Li
     * @created 2015年10月16日 下午5:28:31
     * @param itemId
     * @param ticketsIds
     */
    public void saveItemTickets(String itemId, String ticketsIds) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERVICEITEM_TICKETS");
        sql.append("(ITEM_ID,TICKETS_ID) VALUES(?,?)");
        for (String ticketsId : ticketsIds.split(",")) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, ticketsId });
        }
        
    }

    /**
     * 
     * 描述 根据项目ID获取绑定的票单IDSNAMES
     * @author Faker Li
     * @created 2015年10月19日 上午9:11:19
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#getBindTicketsIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindTicketsIdANdNames(String itemId) {
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(T.TICKETS_ID) TICKETS_IDS");
        sql.append(",WMSYS.WM_CONCAT(T.TICKETS_NAME) TICKETS_NAMES FROM T_WSBS_TICKETS T");
        sql.append(" WHERE T.TICKETS_ID IN (select ST.TICKETS_ID from T_WSBS_SERVICEITEM_TICKETS ST");
        sql.append(" WHERE ST.ITEM_ID=?) ORDER BY T.CREATE_TIME DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { itemId });
    }

    /**
     * 
     * 描述  根据项目ID获取绑定的公文IDSNAMES
     * @author Faker Li
     * @created 2015年10月19日 下午3:03:47
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#getBindDocumentIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindDocumentIdANdNames(String itemId) {
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(T.DOCUMENT_ID) DOCUMENT_IDS");
        sql.append(",WMSYS.WM_CONCAT(T.DOCUMENT_NAME) DOCUMENT_NAMES FROM T_WSBS_DOCUMENTTEMPLATE T");
        sql.append(" WHERE T.DOCUMENT_ID IN (select SD.DOCUMENT_ID from T_WSBS_SERVICEITEM_DOCUMENT SD");
        sql.append(" WHERE SD.ITEM_ID=?) ORDER BY T.CREATE_TIME DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { itemId });
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年10月19日 下午3:11:46
     * @param itemId
     * @param documentIds
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#saveItemDocument(java.lang.String, java.lang.String)
     */
    public void saveItemDocument(String itemId, String documentIds) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERVICEITEM_DOCUMENT");
        sql.append("(ITEM_ID,DOCUMENT_ID) VALUES(?,?)");
        for (String documentId : documentIds.split(",")) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, documentId });
        }
        
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年10月19日 下午4:12:31
     * @param itemId
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#getBindReadIdANdNames(java.lang.String)
     */
    public Map<String, Object> getBindReadIdANdNames(String itemId) {
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(T.READ_ID) READ_IDS");
        sql.append(",WMSYS.WM_CONCAT(T.READ_NAME) READ_NAMES FROM T_WSBS_READTEMPLATE T");
        sql.append(" WHERE T.READ_ID IN (select SR.READ_ID from T_WSBS_SERVICEITEM_READ SR");
        sql.append(" WHERE SR.ITEM_ID=?) ORDER BY T.CREATE_TIME DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { itemId });
    }

    /**
     * 
     * 描述 保存阅办中间表
     * @author Faker Li
     * @created 2015年10月19日 下午4:12:35
     * @param itemId
     * @param readIds
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#saveItemRead(java.lang.String, java.lang.String)
     */
    public void saveItemRead(String itemId, String readIds) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERVICEITEM_READ");
        sql.append("(ITEM_ID,READ_ID) VALUES(?,?)");
        for (String readId : readIds.split(",")) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, readId });
        }
        
    }

    /**
     * 
     * 描述 更新办事事项状态
     * @author Faker Li
     * @created 2015年10月21日 上午10:23:41
     * @param selectColNames
     * @param state
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#updateFwsxzt(java.lang.String, java.lang.String)
     */
    public void updateFwsxzt(String selectColNames,String state) { 
        StringBuffer sql = new StringBuffer("update T_WSBS_SERVICEITEM T set T.FWSXZT = ?");
        String strdate=DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        sql.append("  ,T.UPDATE_TIME='").append(strdate).append("'");
        sql.append(" where T.ITEM_ID IN ");
        sql.append(StringUtil.getValueArray(selectColNames));
        this.jdbcTemplate.update(sql.toString(),new Object[] { state });
    }

    /**
     * 
     * 描述 根据目录编码获取事项编码
     * @author Faker Li
     * @created 2015年10月28日 下午5:11:01
     * @param catalogCode
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#getMaxNumCode(java.lang.String)
     */
    public int getMaxNumCode(String catalogCode) {
        StringBuffer sql = new StringBuffer("select max(NUM_CODE) FROM T_WSBS_SERVICEITEM "
                + " WHERE CATALOG_CODE = ?");
        return this.jdbcTemplate.queryForInt(sql.toString(),new Object[]{catalogCode});
    }

    /**
     * 
     * 描述 保存绑定表单
     * @author Faker Li
     * @created 2015年11月16日 下午5:33:27
     * @param itemId
     * @param bindFormIds
     */
    public void saveItemFormIds(String itemId, String bindFormIds) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERVICEITEM_FORM");
        sql.append("(ITEM_ID,DIC_ID) VALUES(?,?)");
        for (String dicid : bindFormIds.split(",")) {
            this.jdbcTemplate.update(sql.toString(), new Object[] { itemId, dicid });
        }
        
    }

    /**
     * 
     * 描述 描述 根据itemid获取绑定表单
     * @author Faker Li
     * @created 2015年11月17日 上午9:14:01
     * @param itemId
     * @return
     */
    public Map<String, Object> getBindFormIdAndName(String itemId) {
        StringBuffer sql = new StringBuffer("SELECT WMSYS.WM_CONCAT(T.DIC_ID) BINDFORM_IDS");
        sql.append(",WMSYS.WM_CONCAT(T.DIC_NAME) BINDFORM_NAMES FROM T_MSJW_SYSTEM_DICTIONARY T");
        sql.append(" WHERE T.DIC_ID IN (select SF.DIC_ID from T_WSBS_SERVICEITEM_FORM SF");
        sql.append(" WHERE SF.ITEM_ID=?) AND T.TYPE_CODE = 'SXSJBD'  ORDER BY T.DIC_SN DESC");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { itemId });
    }

    /**
     * 
     * 描述 保存排序
     * @author Faker Li
     * @created 2015年11月24日 下午3:42:05
     * @param itemIds
     */
    public void updatSn(String[] itemIds) {
        int[] oldSns = new int[itemIds.length];
        StringBuffer sql = new StringBuffer("");
        sql.append("select C_SN FROM T_WSBS_SERVICEITEM ").append(" WHERE ITEM_ID=? ");
        for (int i = 0; i < itemIds.length; i++) {
            int dicSn = jdbcTemplate.queryForInt(sql.toString(), new Object[] { itemIds[i] });
            oldSns[i] = dicSn;
        }
        int[] newSns = ArrayUtil.sortByDesc(oldSns);
        StringBuffer updateSql = new StringBuffer("update T_WSBS_SERVICEITEM T ")
                .append(" SET T.C_SN=? WHERE T.ITEM_ID=? ");
        for (int i = 0; i < itemIds.length; i++) {
            jdbcTemplate.update(updateSql.toString(), new Object[] { newSns[i], itemIds[i] });
        }
        
    }

    /**
     * 
     * 描述 获取最大排序值
     * @author Faker Li
     * @created 2015年11月24日 下午3:49:18
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#getMaxSn()
     */
    public int getMaxSn() {
        StringBuffer sql = new StringBuffer("select max(C_SN) FROM T_WSBS_SERVICEITEM "
                + "");
        return this.jdbcTemplate.queryForInt(sql.toString());
    }

    /**
     * 
     * 描述 根据catalogCode更新事项所属部门编码
     * @author Faker Li
     * @created 2015年12月16日 下午5:06:04
     * @param catalogCode
     * @param departCode
     */
    public void updateSSBMBM(String catalogCode, String departCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append("UPDATE T_WSBS_SERVICEITEM T SET T.SSBMBM = ? WHERE T.CATALOG_CODE = ?");
        jdbcTemplate.update(sql.toString(), new Object[] { departCode, catalogCode});
    }

    /**
     * 
     * 描述 根据事项所属部门，事项性质，星级获取统计数
     * @author Faker Li
     * @created 2016年1月26日 上午9:58:49
     * @param ssbmbm
     * @param sxxz
     * @param itemstar
     * @return
     */
    public int getXnjcNum(String ssbmbm, String sxxz, String itemstar,String isKT) {
        List<Object> list = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select count(*) FROM T_WSBS_SERVICEITEM T");
        sql.append(" WHERE T.FWSXZT = '1' ");
        if(StringUtils.isNotEmpty(ssbmbm)){
            sql.append("AND  T.ITEM_ID in (select ST.ITEM_ID from T_WSBS_SERVICEITEM_TYPE ST WHERE ");
            sql.append("ST.TYPE_ID =? )");
            list.add(ssbmbm);
        }
        if(StringUtils.isNotEmpty(sxxz)){
            sql.append(" AND T.SXXZ = ? ");
            list.add(sxxz);
        }
        if(StringUtils.isNotEmpty(itemstar)){
            sql.append(" AND T.ITEMSTAR = ? ");
            list.add(itemstar);
        }
        if(StringUtils.isNotEmpty(isKT)){
//            sql.append(" AND ( T.SQFS like '%3%' or T.SQFS like '%4%') ");
            sql.append(" AND ( T.rzbsdtfs like '%in04%' or T.rzbsdtfs like '%in05%') ");
        }
        return this.jdbcTemplate.queryForInt(sql.toString(),list.toArray());
    }
    
    /**
     * 
     * 描述 是否支持挂起
     * @author Flex Hu
     * @created 2016年3月19日 上午8:55:45
     * @param itemCode
     * @return
     */
    public boolean isForHandUp(String itemCode){
        StringBuffer sql = new StringBuffer("SELECT I.SFZCGQ");
        sql.append(" FROM T_WSBS_SERVICEITEM I WHERE I.ITEM_CODE=?");
        String isHandUp = this.jdbcTemplate.queryForObject(sql.toString(), 
                new Object[]{itemCode}, String.class);
        if("1".equals(isHandUp)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getSwbItemCode(String itemCode) {
        // TODO Auto-generated method stub
        String sql = "select t.swb_item_code from T_WSBS_SERVICEITEM t where t.item_code = ?";
        String swbItemCode = this.jdbcTemplate.queryForObject(sql.toString(), 
                new Object[]{itemCode}, String.class);
        return swbItemCode;
    }
    
    /**
     * 
     * 描述    获取自查条件最大排序值
     * @author Danto Huang
     * @created 2018年9月4日 上午10:45:38
     * @return
     */
    public int getMaxExamSn(){
        StringBuffer sql = new StringBuffer("select max(EXAM_SN) FROM T_WSBS_SERVICEITEM_SELFEXAM "
                + "");
        return this.jdbcTemplate.queryForInt(sql.toString());
    }
}
