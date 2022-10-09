/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.esuper.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.DbUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.esuper.dao.EsuperDao;
import net.evecom.platform.esuper.service.EsuperServerService;
import net.evecom.platform.system.service.DictionaryService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 电子监察数据解析处理业务service
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2016年1月27日 下午3:08:01
 */
@SuppressWarnings("rawtypes")
@Service("esuperServerService")
public class EsuperServerServiceImpl extends BaseServiceImpl implements EsuperServerService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(EsuperServerServiceImpl.class);
    /**
     * @Resource EsuperDao
     */
    @Resource
    private EsuperDao dao;
    /**
     * 引入字典处理业务服务
     */
    @Resource
    private DictionaryService dictionaryService;

    /**
     * 描述 Dao
     * 
     * @author Derek Zhang
     * @created 2016年1月27日 下午3:14:27
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return this.dao;
    }

    /**
     * 描述 接收处理预警数据实现
     * 
     * @author Derek Zhang
     * @created 2016年3月1日 上午10:13:24
     * @param log
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receiveEsupers(Log log) {

        String receiveTableName = this.dictionaryService.getDicNames("esuperMiddleDb", "receiveTableName");
        String platCode = this.dictionaryService.getDicNames("dzjc", "platCode");
        Connection conn = null;
        String dbUrl = this.dictionaryService.getDicNames("esuperMiddleDb", "dburl");
        String dbUserName = this.dictionaryService.getDicNames("esuperMiddleDb", "dbuser");
        String dbPass = this.dictionaryService.getDicNames("esuperMiddleDb", "dbpassword");

        // 1.遍例中间库表数据， exdata.t_datatransfer_freeback_temp 表 每个周期处理2000条数据
        String sql = "SELECT * FROM (SELECT F.*,ROWNUM AS ROWNUMBER FROM " + receiveTableName + " F"
                + " WHERE F.BUS_PLATCODE = ? ORDER BY F.CREATE_DATE ASC) B WHERE B.ROWNUMBER <= 2000";

        try {
            conn = DbUtil.getConnect(dbUrl, dbUserName, dbPass);
            List<Map<String, Object>> dataList = DbUtil.findBySql(conn, sql, new Object[] {platCode},false);
            // 2.报送数据
            if (dataList != null && !dataList.isEmpty() && dataList.size() > 0) {
                log.info("开始调用【电子监察预警数据接收处理】定时任务，接收处理电子监察预警数据....");
                for (Map<String, Object> m : dataList) {
                    if (conn != null && !conn.isClosed()) {
                        // 1.读到本地中间表 2.删除中间库中对应数据
                        try {
                            m.put("STATUS", 0);
                            m.put("FB_ID", (String) m.get("FB_ID"));
                            this.dao.saveOrUpdate(m, "T_BSFW_ESUPER_FREEBACK", (String) m.get("FB_ID"));
                            DbUtil.update(conn, "delete from " + receiveTableName + " f where f.fb_id =? ",
                                    new Object[] { m.get("FB_ID") }, false);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
                log.info("结束调用【电子监察预警数据接收处理】定时任务，完成电子监察预警数据接收处理....");
            }
            // 解析本地中间表数据
            String updateSql = "update T_BSFW_ESUPER_FREEBACK m set m.status = 8  where m.status = 0 and "
                    + " exists(select 1 from  ( select fb_id from (select f.*,ROWNUM AS ROWNUMBER "
                    + " from T_BSFW_ESUPER_FREEBACK f where f.status=? and f.fb_type = 4 order by f.create_date asc) a"
                    + " where a.rownumber<1000) b where b.fb_id = m.fb_id)";
            this.dao.executeSql(updateSql, new Object[] { 0 });
            String querySql = "select * from T_BSFW_ESUPER_FREEBACK f where f.status = ? ";
            List<Map<String, Object>> esupersList = this.dao.findBySql(querySql, new Object[] { 8 }, null);
            if (esupersList != null && !esupersList.isEmpty() && esupersList.size() > 0) {
                log.info("结束调用【电子监察预警数据接收处理】定时任务，开始解析处理预警数据....");
                for (Map<String, Object> esuper : esupersList) {
                    Map<String,Object> oMap = new HashMap<String,Object>();
                    oMap.put("FB_ID", esuper.get("FB_ID"));
                    oMap.put("BUSINESS_ID", esuper.get("BUSINESS_ID"));
                    oMap.put("PROCESS_CODE", esuper.get("PROCESS_CODE"));
                    oMap.put("WARN_TYPE", esuper.get("WARN_TYPE"));
                    oMap.put("WARN_INFO", esuper.get("WARN_INFO"));
                    oMap.put("WARN_REASON", esuper.get("WARN_REASON"));
                    oMap.put("WARN_AWAY", 0);
                    oMap.put("REPLY_LIMIT", esuper.get("REPLY_TIME"));
                    oMap.put("CREATE_TIME", DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
                    oMap.put("ESUPER_ID",  esuper.get("ESUPER_ID"));
                    this.dao.saveOrUpdate(oMap, "t_bsfw_esuper_info", null);
                    String updateStaSql = "update T_BSFW_ESUPER_FREEBACK f set f.status = 1 where f.fb_id = ?";
                    this.dao.executeSql(updateStaSql, new Object[] { esuper.get("FB_ID") });
                }
                log.info("结束调用【电子监察预警数据接收处理】定时任务，解析处理预警数据完成....");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
    }

}
