/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

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
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.project.dao.ProjectSgxkbgDao;
import net.evecom.platform.project.service.ProjectSgxkbgService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 施工许可变更操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("projectSgxkbgService")
public class ProjectSgxkbgServiceImpl extends BaseServiceImpl implements ProjectSgxkbgService {
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectSgxkbgDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
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
    public Map<String, Object> findSgxkToConstrNum(String constrNum) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.*,E.EXE_ID FROM T_BSFW_GCJSSGXK T ,JBPM6_EXECUTION e ");
        sql.append(" WHERE T.CONSTRNUM = ? and t.yw_id = e.bus_recordid  ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        param.add(constrNum);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), param.toArray(), null);
        Map<String, Object> sgxk = null;
        if (null != list && list.size() > 0) {
            sgxk = list.get(0);
            sgxk.remove("SBMC");
        }
        return sgxk;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findSgxkbg(String exeid) {
        // TODO Auto-generated method stub
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*,E.EXE_ID FROM T_BSFW_GCJSSGXKBG t,JBPM6_EXECUTION e ");
        sql.append(" where 1=1 and t.yw_id = e.bus_recordid ");
        if (StringUtils.isNotEmpty(exeid)) {
            sql.append(" and E.EXE_ID = ? ");
            param.add(exeid);
        }
        Map<String, Object> sgxkbg = dao.getByJdbc(sql.toString(), param.toArray());
        return sgxkbg;
    }
}
