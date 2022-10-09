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
import net.evecom.platform.wsbs.dao.ServiceItemFormDao;
import net.evecom.platform.wsbs.service.ServiceItemFormService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 服务事项绑定表单操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("serviceItemFormService")
public class ServiceItemFormServiceImpl extends BaseServiceImpl implements ServiceItemFormService {
    /**
     * 所引入的dao
     */
    @Resource
    private ServiceItemFormDao dao;
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
}
