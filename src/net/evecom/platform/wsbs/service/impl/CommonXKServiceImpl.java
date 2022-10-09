/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
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
import net.evecom.platform.wsbs.dao.CommonXKDao;
import net.evecom.platform.wsbs.service.CommonXKService;

import org.springframework.stereotype.Service;

/**
 * 描述：许可serviceImpl
 * 
 * @author Water Guo
 * @created 2017-4-12 上午9:15:49
 */
@SuppressWarnings("rawtypes")
@Service("commonXKService")
public class CommonXKServiceImpl extends BaseServiceImpl implements CommonXKService {
    /**
     * 引入dao
     */
    @Resource
    private CommonXKDao dao;

    /**
     * 
     * 描述：判断是否存在许可
     * 
     * @author Water Guo
     * @created 2017-4-12 上午10:11:31
     * @param variables
     * @return
     * @see net.evecom.platform.wsbs.service
     */
    @Override
    public String getXKID(Map<String, Object> variables) {
        if (variables.get("xkcontent") != null) {
            String xkcontent = (String) variables.get("xkcontent");
            String itemId = (String) variables.get("item_id");
            return this.dao.getXKID(itemId, xkcontent);
        } else {
            return "";
        }
    }

    /**
     * 
     * 描述：获得dao
     * 
     * @author Water Guo
     * @created 2017-4-12 上午9:23:12
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述：获得许可List
     * 
     * @author Water Guo
     * @created 2017-4-12 下午5:01:49
     * @param queryParam
     * @return
     * @see net.evecom.platform.wsbs.service.C
     */
    public List<Map<String, Object>> findXKList(String queryParam) {
        String itemId = queryParam;
        String departId = AppUtil.getLoginUser().getDepId();
        List<Map<String, Object>> list = dao.findXKList(itemId, departId);
        return list;
    }

    /**
     * 
     * 描述：获得许可信息
     * 
     * @author Water Guo
     * @created 2017-4-23 上午9:42:57
     * @param xkId
     * @return
     * @see net.evecom.platform.wsbs.service.CommonXKService#getInfoById(java.lang.String)
     */
    public Map<String, Object> getInfoById(String xkId) {
        Map<String, Object> map = dao.getByJdbc("T_WSBS_XK", new String[] { "XK_ID" }, new Object[] { xkId });
        return map;
    }
}
