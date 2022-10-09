/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bdc.dao.BdcAppointDao;
import net.evecom.platform.bdc.service.BdcAppointService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述 不动产权证预约
 *
 * @author Madison You
 * @version 1.0
 * @created 2020年2月10日 上午12:13:33
 */
@Service("bdcAppointService")
public class BdcAppointServiceImpl extends BaseServiceImpl implements BdcAppointService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/2/10 12:29:00
     * @param
     * @return
     */
    @Resource
    private BdcAppointDao bdcAppointDao;

    /**
     * 覆盖获取实体dao方法 描述
     *
     * @author Madison You
     * @created 2020年2月10日 上午12:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcAppointDao;
    }

    /**
     * 描述:不动产权证预约取证后台页面数据
     *
     * @author Madison You
     * @created 2020/2/10 12:31:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> bdcqzAppointListData(SqlFilter filter) {
        StringBuffer sql = new StringBuffer();
        ArrayList<Object> params = new ArrayList<>();
        List<Map<String, Object>> list = null;
        String isExport = filter.getRequest().getParameter("isExport");
        sql.append(" SELECT T.ID,T.CQR_NAME,T.CQR_CARDNO,T.APPOINTMENT_DATE,A.DIC_NAME TYPE,B.DIC_NAME STATUS, ");
        sql.append(" T.REASON,T.CQR_PHONE, ");
        sql.append(" T.SJR_NAME,T.SJR_CARDNO,T.SJR_ADDRESS,T.EXE_ID,T.REMARK FROM T_CKBS_BDCQZ_APPOINTMENT T ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE, DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'BDCQZYYFS') A ON A.DIC_CODE = T.LZ_TYPE ");
        sql.append(" LEFT JOIN (SELECT DIC_CODE, DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY ");
        sql.append(" WHERE TYPE_CODE = 'BDCQZLZZT') B ON B.DIC_CODE = T.STATUS ");
        sql.append(" WHERE 1 = 1 ");
        String querySql = bdcAppointDao.getQuerySql(filter, sql.toString(), params);
        querySql += " ORDER BY T.APPOINTMENT_DATE DESC ";
        if (StringUtils.isNotEmpty(isExport)) {
            list = bdcAppointDao.findBySql(querySql, params.toArray(), null);
        } else {
            list = bdcAppointDao.findBySql(querySql, params.toArray(), filter.getPagingBean());
        }
        return list;
    }
}
