/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.system.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.system.dao.SysEhcacheDao;
import net.evecom.platform.system.service.SysEhcacheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述 缓存管理操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("sysEhcacheService")
public class SysEhcacheServiceImpl extends BaseServiceImpl implements SysEhcacheService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SysEhcacheServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SysEhcacheDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Faker Li
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 
     * 描述 根据sqlfilter获取到数据列表
     * @author Faker Li
     * @created 2015年11月17日 上午11:26:43
     * @param sqlFilter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.EHCACHE_ID,T.EHCACHE_NAME,T.EHCACHE_CLASS_NAME,");
        sql.append("T.EHCACHE_STATUE,T.EHCACHE_KEY from T_MSJW_SYSTEM_EHCACHE T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述 根据key获取ehcachekey
     * @author Faker Li
     * @created 2015年11月17日 下午3:47:39
     * @param delEhcacheKeys
     * @return
     */
    public List<Map<String, Object>> findByDelEhcacheKeys(String delEhcacheKeys) {
        StringBuffer sql = new StringBuffer("select SE.EHCACHE_ID,SE.EHCACHE_CLASS_NAME,SE.EHCACHE_NAME,");
        sql.append("SE.EHCACHE_KEY FROM T_MSJW_SYSTEM_EHCACHE SE WHERE SE.EHCACHE_KEY IN　");
        sql.append(StringUtil.getValueArray(delEhcacheKeys));
        sql.append(" ORDER BY SE.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    /**
     * 
     * 描述 根据操作类型获取缓存列表
     * @author Faker Li
     * @created 2015年11月17日 下午3:46:46
     * @param statue
     * @return
     */
    public List<Map<String, Object>> findByStatue(String statue) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        StringBuffer sql = new StringBuffer("select SE.EHCACHE_ID,SE.EHCACHE_CLASS_NAME,SE.EHCACHE_NAME,");
        sql.append("SE.EHCACHE_KEY,SE.EHCACHE_DEL_KEY FROM T_MSJW_SYSTEM_EHCACHE SE WHERE SE.EHCACHE_STATUE=?　");
        sql.append(" ORDER BY SE.CREATE_TIME DESC ");
        if(dao==null){
            try {
                Sm4Utils sm4 = new Sm4Utils();
                Properties properties = FileUtil.readProperties("conf/jdbc.properties");
                String dbUrl = sm4.decryptDataCBC(properties.getProperty("jdbc.url"));
                String username = sm4.decryptDataCBC(properties.getProperty("jdbc.username"));
                String password = sm4.decryptDataCBC(properties.getProperty("jdbc.password"));
                Connection conn = DbUtil.getConnect(dbUrl, username, password);
                list = DbUtil.findBySql(conn, sql.toString(), new Object[]{statue});
            } catch (SQLException e) {
                log.error(e.getMessage());
            }
        }else{
            list = dao.findBySql(sql.toString(),new Object[]{statue},null);
        }
        return list;
    }
}
