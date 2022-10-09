/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.dao.SerialNumberDao;
import net.evecom.platform.wsbs.service.SerialNumberService;
import net.evecom.platform.wsbs.service.SerialParamsService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述 编号配置管理服务
 * 
 * @author Derek Zhang
 * @created 2015年9月22日 下午6:02:44
 */
@SuppressWarnings("rawtypes")
@Service("serialNumberService")
public class SerialNumberServiceImpl extends BaseServiceImpl implements SerialNumberService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SerialNumberServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SerialNumberDao dao;
    /**
     * 
     */
    @Resource
    private SerialParamsService serialParamsService;

    /**
     * 描述 获取Dao方法
     * 
     * @author Derek Zhang
     * @created 2015年10月4日 下午2:49:33
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 根据sqlfilter获取到数据列表
     * 
     * @param sqlFilter
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.serial_id,t.serial_name,t.serial_type,");
        sql.append("decode(t.serial_type, null, '', (select di.dic_name from ");
        sql.append(" t_msjw_system_dictype d, t_msjw_system_dictionary di where ");
        sql.append("d.type_id = di.type_id and d.type_code = 'SerialNumberType'");
        sql.append(" and t.serial_type = di.dic_code)) as serial_typename, t.serial_rule,");
        sql.append(" t.sequence_type,t.ssbmbm, t.initseq,t.create_time ");
        sql.append(" from t_wsbs_serialnumber t ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述 加载编号配置数据
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findSerialNumbers(SqlFilter filter) {
        return dao.findSerialNumbers(filter);
    }

    /**
     * 
     * 描述 生成最新编号
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param busName
     *            busType otherParam
     * @return
     */
    @Override
    public String generateSerialNumber(String busName, String busType, Map<String, String> otherParam) {

        Map<String, Object> serialNumberMap = dao.getSerialNumberByBus(busName, busType, otherParam);
        List<Map<String, Object>> dicList = dao.getSerialParamDicList();
        if (serialNumberMap == null || dicList == null) {
            return "";
        } else {
            String ruleStr = (String) serialNumberMap.get("SERIAL_RULE");
            BigDecimal maxSequence = (BigDecimal) serialNumberMap.get("MAX_SEQUENCE");
            String SequenceType = (String) serialNumberMap.get("SEQUENCE_TYPE");
            for (Map<String, Object> map : dicList) {
                String dicName = (String) map.get("DIC_NAME");
                String dicDesc = (String) map.get("DIC_DESC");
                String serialTempStr = "";
                otherParam.put("maxSequence", String.valueOf(maxSequence));
                otherParam.put("sequenceType", SequenceType);
                if (StringUtils.isNotBlank(dicDesc) && !dicDesc.equals("null")) {
                    try {
                        Method method = serialParamsService.getClass().getMethod(dicDesc + "Execute",
                                new Class[] { String.class, String.class, Map.class });
                        serialTempStr = (String) method.invoke(serialParamsService, new Object[] { ruleStr, dicName,
                            otherParam });
                    } catch (SecurityException e) {
                        log.error(e.getMessage());
                    } catch (NoSuchMethodException e) {
                        log.error(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        log.error(e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        log.error(e.getMessage());
                    }
                    if (StringUtils.isNotBlank(serialTempStr)) {
                        ruleStr = serialTempStr;
                    }
                }
            }
            return ruleStr;
        }
    }

    /**
     * 
     * 描述 根据serialid,busname,bustype判断是否存在记录
     * @author Faker Li
     * @created 2015年11月3日 上午10:22:15
     * @param serialid
     * @param busname
     * @param bustype
     * @return
     */
    public boolean isExistBySerialidAndBusnameAndBustype(String serialid, String busname, String bustype) {
        return dao.isExistBySerialidAndBusnameAndBustype(serialid,busname,bustype);
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2015年11月3日 上午10:49:04
     * @param serialNumber
     * @see net.evecom.platform.wsbs.service.SerialNumberService#insertserialRes(java.util.Map)
     */
    public void insertserialRes(Map<String, Object> serialNumber) {
        dao.insertserialRes(serialNumber);
    }

}
