/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.callyqyz.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.callyqyz.dao.CallYqyzDao;
import net.evecom.platform.callyqyz.service.CallYqyzService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author Madison You
 * @created 2019-12-26 17:30:00
 */
@Service("callYqyzService")
public class CallYqyzServiceImpl extends BaseServiceImpl implements CallYqyzService {


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/26 19:15:00
     * @param
     * @return
     */
    @Resource
    private CallYqyzDao dao;


    /**
     * 描述:
     *
     * @author Madison You
     * @created 2019/12/26 17:34:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }


    /**
     * 描述:一企一证配置数据
     *
     * @author Madison You
     * @created 2019/12/26 19:13:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select t.*,B.BUSINESS_NAME,D.TYPE_NAME,E.DIC_NAME CATALOG from  ");
        sql.append(" T_CKBS_YQYZQH_BUS T LEFT JOIN T_CKBS_YQYZQH D ON T.YQYZQH_ID = D.YQYZQH_ID  ");
        sql.append(" LEFT JOIN T_CKBS_BUSINESSDATA B ON T.BUSINESS_CODE = B.BUSINESS_CODE");
        sql.append(" LEFT JOIN (SELECT DIC_CODE,DIC_NAME FROM T_MSJW_SYSTEM_DICTIONARY WHERE");
        sql.append(" TYPE_CODE = 'YQYZML') E ON E.DIC_CODE = D.CATALOG ");
        sql.append(" WHERE 1=1 ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }


    /**
     * 描述:一企一证添加业务
     *
     * @author Madison You
     * @created 2019/12/27 8:56:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getBusinessCode(String yqyzqhId) {
        return dao.getYqyzBusinessCode(yqyzqhId);
    }


    /**
     * 描述:查找业主中是否包含营业执照和许可证件业务
     *
     * @author Madison You
     * @created 2019/12/30 16:33:00
     * @param
     * @return
     */
    @Override
    public boolean findBusinessCatalog(SqlFilter filter) {
        ArrayList<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        boolean flag = false;
        sql.append(" select distinct CATALOG from T_CKBS_YQYZQH a ");
        sql.append(" left join T_CKBS_YQYZQH_BUS b on a.YQYZQH_ID = b.YQYZQH_ID where 1 = 1 ");
        String querySql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String,Object>> list = dao.findBySql(querySql, params.toArray(), null);
        if (list != null && !list.isEmpty()) {
            String listStr = list.toString();
            if (listStr.contains("yyzz") && listStr.contains("xkzj")) {
                flag = true;
            }
        }
        return flag;
    }


}
