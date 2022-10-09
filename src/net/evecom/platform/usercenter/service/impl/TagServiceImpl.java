/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
/**
 * 
 */
package net.evecom.platform.usercenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.usercenter.dao.TagDao;
import net.evecom.platform.usercenter.service.TagService;

/**
 * 描述
 * @author Danto Huang
 * @version 1.0
 * @created 2017-4-28 下午4:57:19
 */
@Service("tagService")
public class TagServiceImpl extends BaseServiceImpl implements TagService {

    /**
     * 引入dao
     */
    @Resource
    private TagDao dao;
    /**
     * 
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }


    /**
     * 
     * 获取用户中心标签列表
     * @author Danto Huang
     * @created 2017-5-2 上午9:36:38
     * @param filter
     * @return
     */
    public List<Map<String,Object>> findCatalogBySqlFilter(SqlFilter filter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.*,ct.type_name,");
        sql.append("(select count(*) from T_USERCENTER_TAG c where c.parent_tag=t.tag_id ) as haschild, ");
        sql.append("(select count(*) from T_WSBS_APPLYMATER a where a.tag_id = t.tag_id) as isuse ");
        sql.append("from T_USERCENTER_TAG t ");
        sql.append("left join T_USERCENTER_TAGTYPE ct on ct.type_id=t.type_id where 1=1 ");
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        exeSql += "start with t.parent_tag is null connect by prior t.tag_id = t.parent_tag ";
        exeSql += "order siblings by t.create_time desc";
        List<Map<String, Object>> list = dao.findBySql(exeSql,params.toArray(), filter.getPagingBean());
        return list;
    }
    /**
     * 
     * 根据字典类别ID删除掉数据
     * @author Danto Huang
     * @created 2017-5-2 下午2:23:37
     * @param typeId
     */
    public void removeByTypeId(String typeId){
        dao.removeByTypeId(typeId);
    }

    /**
     * 
     *  判断某个标签类别下是否存在相同编码的标签
     * @author Danto Huang
     * @created 2017-5-2 下午2:39:46
     * @param typeId
     * @param key
     * @return
     */
    public boolean isExist(String typeId,String key){
        return dao.isExist(typeId, key);
    }

    /**
     * 
     * 根据标签IDS获取列表数据
     * @author Danto Huang
     * @created 2017-5-3 下午2:23:39
     * @param tagIds
     * @return
     */
    public List<Map<String,Object>> findByTagId(String tagIds){
        StringBuffer sql = new StringBuffer("select U.TAG_ID,U.TAG_KEY,U.TAG_NAME");
        sql.append(" FROM T_USERCENTER_TAG U WHERE U.TAG_ID IN　");
        sql.append(StringUtil.getValueArray(tagIds));
        sql.append(" ORDER BY U.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),null,null);
    }
    /**
     * 
     * 描述   获取一级标签
     * @author Danto Huang
     * @created 2017-5-15 上午10:27:34
     * @param typeId
     * @return
     */
    public List<Map<String,Object>> findParentForSelect(String typeId){
        StringBuffer sql = new StringBuffer("select T.TAG_NAME as text,T.TAG_ID as value FROM")
                .append(" T_USERCENTER_TAG T WHERE T.TYPE_ID = ? and T.TAG_LEVEL='01' ORDER BY T.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), new Object[] { typeId }, null);
    }
    /**
     * 
     * 描述   
     * @author Danto Huang
     * @created 2017-5-18 上午9:03:19
     * @param typeId
     * @return
     */
    public List<Map<String,Object>> findByTypeId(String typeId){
        String sql = "select * from T_USERCENTER_TAG T WHERE T.TYPE_ID = ?";
        return dao.findBySql(sql.toString(), new Object[] { typeId }, null);
    }
}
