/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.wsbs.dao.LawReguDao;
import net.evecom.platform.wsbs.service.LawReguService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 法律法规操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("lawReguService")
public class LawReguServiceImpl extends BaseServiceImpl implements LawReguService {
    /**
     * 所引入的dao
     */
    @Resource
    private LawReguDao dao;
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
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.LAW_ID,T.LAW_TITLE,T.UPDATE_TIME");
        sql.append(" FROM T_WSBS_LAWREGU T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据办事项目ID获取法律法规列表
     * @author Flex Hu
     * @created 2015年9月2日 上午9:10:14
     * @param guideId
     * @return
     */
    public List<Map<String,Object>> findByGuideId(String guideId){
        return dao.findByGuideId(guideId);
    }
    
    /**
     * 
     * 描述 更新中间表
     * @author Flex Hu
     * @created 2015年9月2日 上午10:10:51
     * @param guideId
     * @param lawIds
     */
    public void saveMiddleConfig(String guideId,String lawIds){
        dao.deleteMiddelConfig(guideId);
        dao.saveMiddleConfig(guideId, lawIds);
    }
    
    /**
     * 
     * 描述 删除法律法规并且级联删除掉中间表数据
     * @author Flex Hu
     * @created 2015年9月2日 上午10:20:46
     * @param lawIds
     */
    public void deleteCascade(String[] lawIds){
        for(String lawId:lawIds){
            //删除掉中间表数据
            dao.remove("T_WSBS_LAW_GUIDE", "LAW_ID",new Object[]{lawId});
        }
        dao.remove("T_WSBS_LAWREGU","LAW_ID",lawIds);
    }
}
