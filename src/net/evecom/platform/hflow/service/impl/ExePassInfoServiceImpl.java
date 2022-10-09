/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.ExePassInfoDao;
import net.evecom.platform.hflow.service.ExePassInfoService;

import org.springframework.stereotype.Service;

/**
 * 描述 流程传阅操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("exePassInfoService")
public class ExePassInfoServiceImpl extends BaseServiceImpl implements ExePassInfoService {
    /**
     * 所引入的dao
     */
    @Resource
    private ExePassInfoDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
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
     * 描述 保存传阅信息记录
     * @author Flex Hu
     * @created 2016年2月18日 下午2:12:46
     * @param perualIds
     * @param exeId
     */
    public void saveExePassInfo(String perualIds,String exeId){
        String[] userIds = perualIds.split(",");
        for(String userId:userIds){
            Map<String,Object> info = new HashMap<String,Object>();
            info.put("FEXE_ID", exeId);
            info.put("USER_ID", userId);
            dao.saveOrUpdate(info, "JBPM6_EXEPASSINFO", null);
        }
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.EXE_ID,T.SUBJECT,T.CREATE_TIME,T.RUN_STATUS");
        sql.append(",T.CUR_STEPNAMES,T.CUR_STEPAUDITNAMES,T.END_TIME,T.WORK_HOURS ");
        sql.append(" FROM JBPM6_EXECUTION T LEFT JOIN JBPM6_EXEPASSINFO P ");
        sql.append(" ON T.EXE_ID=P.FEXE_ID ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        return dao.findBySql(exeSql,params.toArray(), sqlFilter.getPagingBean());
    }
}
