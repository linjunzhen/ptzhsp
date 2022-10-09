/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import net.evecom.platform.hflow.dao.FieldRightDao;
import net.evecom.platform.hflow.service.FieldRightService;
import net.evecom.platform.hflow.util.Jbpm6Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;

/**
 * 描述 字段权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("fieldRightService")
public class FieldRightServiceImpl extends BaseServiceImpl implements FieldRightService {
    /**
     * 所引入的dao
     */
    @Resource
    private FieldRightDao dao;
    /**
     * 权限:写
     */
    public static final int RIGHTFLAG_WRITE = 1; 
    /**
     * 权限:读
     */
    public static final int RIGHTFLAG_READ = 2;
    /**
     * 权限:不可见
     */
    public static final int RIGHTFLAG_HIDE = 3;
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
    
    /***
     * 
     * 描述 更新或者保存表单字段
     * @author Flex Hu
     * @created 2015年8月12日 上午10:50:35
     * @param isExist
     * @param formField
     * @param defId
     * @param nodeName
     */
    public void saveOrUpdate(boolean isExist,Map<String,Object> formField,
            String defId,String nodeName,int flowDefVersion,int oldVersion){
        //获取字段名称
        String fieldName = (String) formField.get("FIELD_NAME");
        Map<String,Object> fieldRight =new HashMap<String,Object>();
        //直接进行数据的初始化
        fieldRight.put("FIELD_NAME", fieldName);
        fieldRight.put("RIGHTFLAG", RIGHTFLAG_WRITE);
        fieldRight.put("DEF_ID",defId);
        fieldRight.put("NODE_NAME",nodeName);
        fieldRight.put("DEF_VERSION",flowDefVersion);
        if(isExist){
            //如果已经存在的,那么只考虑新增的字段
            //判断字段是否存在
            boolean isRightConfed = dao.isExists(defId, nodeName, fieldName,flowDefVersion);
            if(!isRightConfed){
                dao.saveOrUpdate(fieldRight, "JBPM6_FIELDRIGHT",null);
            }
        }else{
            //获取旧版本的字段权限信息
            Map<String,Object> oldVersionRight = dao.getByJdbc("JBPM6_FIELDRIGHT",
                    new String[]{"FIELD_NAME","DEF_ID","NODE_NAME","DEF_VERSION"},
                    new Object[]{fieldName,defId,nodeName,oldVersion});
            if(oldVersionRight!=null){
                fieldRight.put("RIGHTFLAG",oldVersionRight.get("RIGHTFLAG"));
            }
            dao.saveOrUpdate(fieldRight, "JBPM6_FIELDRIGHT",null);
        }
    }
    
    /**
     * 
     * 描述 更新或者保存字段权限
     * @author Flex Hu
     * @created 2015年8月12日 上午9:43:40
     * @param formFields
     * @param nodeDataArray
     * @param defId
     */
    public void saveOrUpdate(List<Map<String,Object>> formFields,
            JSONArray nodeDataArray,String defId,int flowDefVersion,int oldVersion){
        boolean isExists = this.dao.isExists(defId,flowDefVersion);
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> nodeData = (Map<String, Object>) nodeDataArray.get(i);
            //获取节点名称
            String nodeName = (String) nodeData.get("text");
            //获取节点类型
            String nodeType = (String) nodeData.get("nodeType");
            if(nodeType.equals(Jbpm6Constants.START_NODE)||nodeType.equals(Jbpm6Constants.TASK_NODE)){
                for(Map<String,Object> formField:formFields){
                    this.saveOrUpdate(isExists, formField, defId, nodeName,flowDefVersion,oldVersion);
                }
            }
        }
    }
    
    /**
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.* FROM JBPM6_FIELDRIGHT T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 更新字段的权限
     * @author Flex Hu
     * @created 2015年8月12日 下午3:57:10
     * @param rightIds
     * @param rightFlag
     */
    public void updateRightFlag(String[] rightIds,int rightFlag){
        for(String rightId:rightIds){
            dao.updateRightFlag(rightId, rightFlag);
        }
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称获取字段权限控制值
     * @author Flex Hu
     * @created 2015年8月17日 下午3:14:01
     * @param defId
     * @param nodeName
     * @return
     */
    public List<Map<String,Object>> findList(String defId,String nodeName,int flowVersion){
        StringBuffer sql = new StringBuffer("select R.FIELD_NAME,R.RIGHTFLAG FROM ");
        sql.append("JBPM6_FIELDRIGHT R WHERE R.DEF_ID=? AND R.NODE_NAME=? ");
        sql.append(" AND R.DEF_VERSION=? ");
        sql.append("ORDER BY R.CREATE_TIME ASC");
        return this.dao.findBySql(sql.toString(), new Object[]{defId,nodeName,flowVersion}, null);
    }
    
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion){
        dao.deleteByDefIdAndVersion(defId, flowVersion);
    }
    
    /**
     * 
     * 描述 拷贝字段权限数据
     * @author Flex Hu
     * @created 2016年3月31日 下午3:37:29
     * @param targetDefId
     * @param targetVersion
     * @param newDefId
     */
    public void copyFieldRights(String targetDefId,int targetVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_FIELDRIGHT(RIGHT_ID,");
        sql.append("FIELD_NAME,RIGHTFLAG,DEF_ID,NODE_NAME,DEF_VERSION)");
        sql.append("SELECT RAWTOHEX(SYS_GUID()),BR.FIELD_NAME,BR.RIGHTFLAG,");
        sql.append("?,BR.NODE_NAME,BR.DEF_VERSION FROM JBPM6_FIELDRIGHT BR");
        sql.append(" WHERE BR.DEF_ID=? AND BR.DEF_VERSION=? ");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetVersion});
    }
}
