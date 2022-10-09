/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.hd.dao.LetterDao;
import net.evecom.platform.hd.service.LetterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 写信求诉操作service
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("letterService")
public class LetterServiceImpl extends BaseServiceImpl implements LetterService {
    /**
     * 所引入的dao
     */
    @Resource
    private LetterDao dao;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String whereSql) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.*,F.FILE_NAME from T_HD_LETTER T LEFT"
                + " JOIN T_MSJW_SYSTEM_FILEATTACH F ON T.LETTER_FILE_ID = F.FILE_ID ");
        sql.append(whereSql);
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    @Override
    public String getNextLetterCode(Map<String, Object> letterVars) {
        // TODO Auto-generated method stub
        return dao.getNextLetterCode(letterVars);
    }

    @Override
    public Map<String, Object> findfrontList(String page, String rows,
            String code,String title) {
        Map<String, Object> mlist = new HashMap<String, Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page) - 1) * Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_HD_LETTER T ");
        sql.append(" WHERE  T.LETTER_ISPUBLIC=1 ");
        if (StringUtils.isNotEmpty(code)) {
            sql.append(" AND T.LETTER_CODE = '"+code+"' ");
            //params.add(code);
        }
        if (StringUtils.isNotEmpty(title)) {
            sql.append(" AND T.LETTER_TITLE like '%"+title+"%' ");
            //params.add(title);
        }
        sql.append(" ORDER BY T.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(), params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }
    /**
     * 获取诉求件数量
     * @return
     */
    public int getAllSqNum(String replyFlag){
        return this.dao.getAllSqNum(replyFlag);
    }
}
