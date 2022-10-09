/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.FlowButtonDao;
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.hflow.service.FlowButtonService;

import org.springframework.stereotype.Service;

/**
 * 描述 流程按钮操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowButtonService")
public class FlowButtonServiceImpl extends BaseServiceImpl implements FlowButtonService {
    /**
     * 所引入的dao
     */
    @Resource
    private FlowButtonDao dao;
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_BUTTON T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年8月13日 下午3:09:30
     * @return
     */
    public List<Map<String,Object>> findList(){
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_BUTTON T");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(), null, null);
    }
    
    /**
     * 
     * 描述 根据按钮IDS删除掉记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:49:10
     * @param buttonIds
     */
    public void deleteButton(String[] buttonIds){
        for(String buttonId:buttonIds){
            //加载按钮
            Map<String,Object> button = dao.getByJdbc("JBPM6_BUTTON",
                    new String[]{"BUTTON_ID"},new Object[]{buttonId});
            //获取按钮KEY
            String buttonKey = (String) button.get("BUTTON_KEY");
            buttonRightService.deleteByButtonKey(buttonKey);
        }
        dao.remove("JBPM6_BUTTON","BUTTON_ID",buttonIds);
    }
    
    /**
     * 
     * 描述 获取新配置的按钮数据
     * @author Flex Hu
     * @created 2015年12月3日 上午9:43:54
     * @param defId
     * @param nodeName
     * @param flowVersion
     * @return
     */
    public List<Map<String,Object>> findNewConfigButtons(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("select B.* from JBPM6_BUTTON B");
        sql.append(" WHERE B.BUTTON_KEY NOT IN (select T.BUTTON_KEY from JBPM6_BUTTONRIGHT T");
        sql.append(" WHERE T.DEF_ID=? AND T.NODE_NAME=? AND T.DEF_VERSION=?) ");
        sql.append("ORDER BY B.CREATE_TIME ASC");
        return dao.findBySql(sql.toString(), new Object[]{defId,nodeName,flowVersion}, null);
    }
}
