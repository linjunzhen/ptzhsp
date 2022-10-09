/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.wsbs.dao.SerialNumberDao;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * 描述 编号管理
 * 
 * @author Derek Zhang
 * @created 2015年10月7日 下午2:46:30
 */
@SuppressWarnings("rawtypes")
@Repository("serialNumberDao")
public class SerialNumberDaoImpl extends BaseDaoImpl implements SerialNumberDao {

    /**
     * 描述 获取编号配置数据
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:03:50
     * @param filter
     * @return
     * @see net.evecom.platform.wsbs.dao.ServiceItemDao#findSerialNumbers(SqlFilter)
     */
    @Override
    public List<Map<String, Object>> findSerialNumbers(SqlFilter filter) {
        List<Object> params = new ArrayList<Object>();
        filter.addSorted("S.CREATE_TIME", "DESC");
        StringBuffer sql = new StringBuffer("select S.* FROM T_WSBS_SERIALNUMBER S");
        // 获取当前用户信息
        SysUser curUser = AppUtil.getLoginUser();
        // 非超管进行数据级别权限控制
        if (!curUser.getResKeys().equals(SysUser.ADMIN_RESKEY)) {
            // 获取当前用户被授权的部门代码
            String authDepCodes = curUser.getAuthDepCodes();
            if (StringUtils.isNotBlank(authDepCodes))
                sql.append(" WHERE S.SSBMBM in ").append(StringUtil.getValueArray(authDepCodes));
        }
        @SuppressWarnings("unchecked")
        String exeSql = this.getQuerySql(filter, sql.toString(), params);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> list = this.findBySql(exeSql, params.toArray(), null);
        return list;
    }

    /**
     * 
     * 描述 获取业务对应的编号配置
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:34
     * @param busName
     * @param busType
     * @param otherParam
     * @return
     */
    public Map<String, Object> getSerialNumberByBus(String busName, String busType, Map<String, String> otherParam) {
        StringBuffer sql = new StringBuffer("SELECT R.MAX_SEQUENCE,S.SEQUENCE_TYPE,S.INITSEQ,S.SERIAL_RULE");
        sql.append(" FROM T_WSBS_SERIALNUMBER_RES R,T_WSBS_SERIALNUMBER S");
        sql.append(" WHERE R.SERIAL_ID=S.SERIAL_ID AND R.BUS_NAME = ? AND R.BUS_TYPE = ?");
        return this.jdbcTemplate.queryForMap(sql.toString(), new Object[] { busName, busType });
    }

    /**
     * 
     * 描述 获取编号参数过滤信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:26
     * @return
     */
    public List<Map<String, Object>> getSerialParamDicList() {
        return findDicListByType("SerialParameter", "");
    }

    /**
     * 
     * 描述 获取字典项
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午2:55:44
     * @param dicType
     * @return
     */

    private List<Map<String, Object>> findDicListByType(String dicType, String dicCode) {
        String exeSql = "SELECT D.DIC_CODE,D.DIC_NAME,D.DIC_DESC "
                + " FROM T_MSJW_SYSTEM_DICTYPE DT ,T_MSJW_SYSTEM_DICTIONARY D " + " WHERE DT.TYPE_ID = D.TYPE_ID "
                + " AND DT.TYPE_CODE = '" + dicType + "' ";
        if (StringUtils.isNotBlank(dicCode)) {
            exeSql += " AND D.DIC_CODE = '" + dicCode + "'";
        }
        exeSql += " ORDER BY  D.CREATE_TIME ASC ";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(exeSql);
        return list;
    }

    /**
     * 
     * 描述 获取字典项
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午2:55:44
     * @param dicType
     * @return
     */

    private List<Map<String, Object>> findDicListByName(String dicType, String dicName) {
        String exeSql = "SELECT D.DIC_CODE,D.DIC_NAME,D.DIC_DESC "
                + " FROM T_MSJW_SYSTEM_DICTYPE DT ,T_MSJW_SYSTEM_DICTIONARY D " + " WHERE DT.TYPE_ID = D.TYPE_ID "
                + " AND DT.TYPE_CODE = '" + dicType + "' ";
        if (StringUtils.isNotBlank(dicName)) {
            exeSql += " AND D.DIC_NAME = '" + dicName + "'";
        }
        exeSql += " ORDER BY D.CREATE_TIME ASC ";
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(exeSql);
        return list;
    }

    /**
     * 描述 获取序号类型信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午2:56:28
     * @return
     * @see net.evecom.platform.wsbs.dao.SerialNumberDao#getSequenceTypeDicList()
     */

    @Override
    public List<Map<String, Object>> getSequenceTypeDicList(String sequenceType) {
        return findDicListByType("SerialSeqType", sequenceType);
    }

    /**
     * 
     * 描述 获取省网办接口对接配置信息
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 上午10:52:26
     * @return
     */
    public Map<String, Object> findSwbjkConfig(String dictName) {
        List<Map<String, Object>> mlist = findDicListByName("swbdjpz", dictName);
        if (mlist != null && mlist.size() > 0) {
            return mlist.get(0);
        } else {
            return null;
        }
    }

    /**
     * 
     * 描述 根据serialid,busname,bustype判断是否存在记录
     * 
     * @author Faker Li
     * @created 2015年11月3日 上午10:25:16
     * @param serialid
     * @param busname
     * @param bustype
     * @return
     */
    public boolean isExistBySerialidAndBusnameAndBustype(String serialid, String busname, String bustype) {
        StringBuffer sql = new StringBuffer("select max(t.max_sequence) from T_WSBS_SERIALNUMBER_RES t "
                + " where t.serial_id = ? and t.bus_name =? and t.bus_type = ?");
        int m = this.jdbcTemplate.queryForInt(sql.toString(), new Object[] { serialid, busname, bustype });
        if (m == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 
     * 描述
     * 
     * @author Faker Li
     * @created 2015年11月3日 上午10:49:55
     * @param serialNumber
     * @see net.evecom.platform.wsbs.dao.SerialNumberDao#insertserialRes(java.util.Map)
     */
    public void insertserialRes(Map<String, Object> serialNumber) {
        StringBuffer sql = new StringBuffer("insert into T_WSBS_SERIALNUMBER_RES(SERIAL_ID,"
                + "BUS_NAME,BUS_TYPE,MAX_SEQUENCE,CREATE_TIME) values (?,?,?,?,?)");
        this.jdbcTemplate
                .update(sql.toString(),
                        new Object[] { serialNumber.get("SERIAL_ID"), serialNumber.get("BUS_NAME"),
                                serialNumber.get("BUS_TYPE"), serialNumber.get("MAX_SEQUENCE"),
                                serialNumber.get("CREATE_TIME") });
    }
}
