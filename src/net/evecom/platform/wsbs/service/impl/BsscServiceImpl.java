/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

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
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.wsbs.dao.BsscDao;
import net.evecom.platform.wsbs.service.BsscService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 办事收藏操作service
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("bsscService")
public class BsscServiceImpl extends BaseServiceImpl implements BsscService {
    /**
     * 所引入的dao
     */
    @Resource
    private BsscDao dao;
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
    /**
     * 
     * 描述 请求数据 用户中心我的评价列表
     * @author Faker Li
     * @created 2015年12月4日 下午3:51:16
     * @param page
     * @param rows
     * @return
     * @see net.evecom.platform.wsbs.service.BsscService#findfrontList(java.lang.String, java.lang.String)
     */
    public Map<String, Object> findfrontList(String page, String rows,String yhzh) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        params.add(yhzh);
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("select T.BSSC_ID,T.CREATE_TIME,");
        sql.append(" S.ITEM_NAME,S.ITEM_ID,S.ITEM_CODE,D.DEPART_NAME from T_WSBS_BSSC T  "
                + "LEFT JOIN T_WSBS_SERVICEITEM S ON T.ITEM_CODE = S.ITEM_CODE ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DEPARTMENT D ON D.DEPART_CODE=S.SSBMBM ");
        sql.append(" WHERE T.YHZH = ? ");
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
}
