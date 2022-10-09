/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.service.impl;

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
import net.evecom.platform.project.dao.ProjectSgxkfqzblDao;
import net.evecom.platform.project.service.ProjectSgxkfqzblService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 施工许可废弃再办理操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("projectSgxkfqzblService")
public class ProjectSgxkfqzblServiceImpl extends BaseServiceImpl implements ProjectSgxkfqzblService {
    /**
     * 所引入的dao
     */
    @Resource
    private ProjectSgxkfqzblDao dao;
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
    @Override
    public Map<String, Object> findSgxkfqzbl(String exeid) {
        // TODO Auto-generated method stub

        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer(" select t.*,E.EXE_ID FROM T_BSFW_GCJSSGXKFQZBL t,JBPM6_EXECUTION e ");
        sql.append(" where 1=1 and t.yw_id = e.bus_recordid ");
        if (StringUtils.isNotEmpty(exeid)) {
            sql.append(" and E.EXE_ID = ? ");
            param.add(exeid);
        }
        Map<String, Object> info = dao.getByJdbc(sql.toString(), param.toArray());
        return info;
    }
}
