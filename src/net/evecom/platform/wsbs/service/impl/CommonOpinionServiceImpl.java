/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.wsbs.dao.CommonOpinionDao;
import net.evecom.platform.wsbs.service.CommonOpinionService;

import org.springframework.stereotype.Service;

/**
 * 描述 常用意见管理
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年10月15日 下午4:04:48
 */
@SuppressWarnings("rawtypes")
@Service("commonOpinionService")
public class CommonOpinionServiceImpl extends BaseServiceImpl implements CommonOpinionService {
    /**
     * 所引入的dao
     */
    @Resource
    private CommonOpinionDao dao;

    /**
     * 描述 获取Dao方法
     * 
     * @author Derek Zhang
     * @created 2015年10月4日 下午2:49:33
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 加载编号配置数据
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午3:00:54
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findCommonOpinionList(String queryParam) {
        String busName = queryParam.split(",")[0];
        String busType = queryParam.split(",")[1];
        String sysuserid = AppUtil.getLoginUser().getUserId();
        return dao.findCommonOpinionList(sysuserid, busName, busType);
    }

    /**
     * 
     * 描述 判断要保存的常用意见是否已存在
     * 
     * @author Derek Zhang
     * @created 2015年10月16日 上午9:20:41
     * @param variables
     * @return
     */
    public boolean isExist(Map<String, Object> variables) {
        if (variables.get("OPINION_CONTENT") != null) {
            String content = (String) variables.get("OPINION_CONTENT");
            String userid = (String) variables.get("USER_ID");
            String busName = (String) variables.get("BUSINESS_NAME");
            String busType = (String) variables.get("BUSINESS_TYPE");
            return this.dao.isExist("", userid, busName, busType, content);
        } else {
            return false;
        }

    }
}
