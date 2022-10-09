/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.extra.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.extra.dao.ExtraSgssjDao;
import net.evecom.platform.extra.service.ExtraSgssjService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 双公示数据操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@SuppressWarnings("rawtypes")
@Service("extraSgssjService")
public class ExtraSgssjServiceImpl extends BaseServiceImpl implements ExtraSgssjService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ExtraSgssjServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ExtraSgssjDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveSgssj() {
        // TODO Auto-generated method stub
        PagingBean pb = new PagingBean(0, 1000);
        StringBuffer sql = new StringBuffer("select * from VIEW_EXTRA_INDUSTRYAWARD_SGSSJ t ");
        sql.append(" order by t.exe_id asc ");
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), null, pb);
        if (null != list && list.size() > 0) {
            Map<String, Object> exeInfo = null;
            for (Map<String, Object> map : list) {
                String exeId = StringUtil.getString(map.get("EXE_ID"));
                try {
                    String csql = "select wmsys.wm_concat(t.DIC_CODE) columnname from T_MSJW_SYSTEM_DICTIONARY t "
                            + "where t.TYPE_CODE=?";
                    String column = (String) dao.getByJdbc(csql, new Object[] { "sgssjjmzd" }).get("COLUMNNAME");
                    String[] columns = column.split(",");
                    mapDecrypt(map, columns);// 解密
                    dao.saveOrUpdateForAssignId(map, "EXTRA_INDUSTRYAWARD_SGSSJ", exeId);
                    exeInfo = new HashMap<String, Object>();
                    exeInfo.put("EXE_ID", exeId);
                    exeInfo.put("SGSSJ_STATUS", "1");
                    dao.saveOrUpdate(exeInfo, "JBPM6_EXECUTION", exeId);
                } catch (Exception e) {
                    // TODO: handle exception
                    log.error("", e);
                    continue;
                }
            }
        }
    }

    /**
     * 
     * 描述 Map数据解密
     * 
     * @author Rider Chen
     * @created 2021年4月22日 下午6:08:57
     * @param map
     * @param tableName
     * @return
     */
    public Map<String, Object> mapDecrypt(Map<String, Object> map, String[] columns) {
        if (null != columns && columns.length > 0) {
            Sm4Utils sm4 = new Sm4Utils();
            for (String columnName : columns) {
                if (map.get(columnName) != null) {
                    String val = (String) map.get(columnName);
                    String newVal = sm4.decryptDataCBC(val);
                    if (StringUtils.isNotEmpty(newVal)) {
                        map.put(columnName, newVal);
                    } else {
                        map.put(columnName, val);
                    }
                }
            }
        }
        return map;
    }

}
