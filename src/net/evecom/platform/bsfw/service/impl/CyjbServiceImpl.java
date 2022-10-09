/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

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
import net.evecom.platform.bsfw.dao.CyjbDao;
import net.evecom.platform.bsfw.service.CyjbService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 产业奖补操作service
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("cyjbService")
public class CyjbServiceImpl extends BaseServiceImpl implements CyjbService {
    /**
     * 所引入的dao
     */
    @Resource
    private CyjbDao dao;
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
    public List<Map<String,Object>> findCyjbList(String ywId) {
        StringBuffer sql = new StringBuffer(
                "select t.* from T_BSFW_PTJ_CYJB t where t.yw_id = ? order by t.create_time desc");
        return dao.findBySql(sql.toString(), new Object[] { ywId }, null);
    }
}
