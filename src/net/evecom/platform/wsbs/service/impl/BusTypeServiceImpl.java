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
import net.evecom.platform.system.service.SysLogService;
import net.evecom.platform.wsbs.dao.BusTypeDao;
import net.evecom.platform.wsbs.service.BusTypeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述 业务类别操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("busTypeService")
public class BusTypeServiceImpl extends BaseServiceImpl implements BusTypeService {
    /**
     * 所引入的dao
     */
    @Resource
    private BusTypeDao dao;
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
    
    /**
     * 
     * 描述 根据材料ID获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月17日 下午5:41:55
     * @param materId
     * @return
     */
    public Map<String,Object> getIdAndNamesByMaterId(String materId){
        return dao.getIdAndNamesByMaterId(materId);
    }
    
    /**
     * 
     * 描述 根据服务对象值获取列表记录
     * @author Flex Hu
     * @created 2015年9月18日 上午11:46:35
     * @param fwdxValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findDatasForSelect(String fwdxValue){
        String typeCode = "";
        if(fwdxValue.equals("1")){
            typeCode = "public";
        }else if(fwdxValue.equals("2")){
            typeCode = "enterprise";
        }else if(fwdxValue.equals("3")){
            typeCode = "DepartService";
        }
        StringBuffer sql = new StringBuffer("select T.TYPE_ID as value,T.TYPE_NAME as text from T_WSBS_BUSTYPE T");
        sql.append(" WHERE T.PARENT_ID=(SELECT P.TYPE_ID FROM T_WSBS_BUSTYPE P WHERE P.TYPE_CODE=?)");
        sql.append(" ORDER BY T.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
    
    /**
     * 
     * 描述 保存类别项目中间表
     * @author Flex Hu
     * @created 2015年9月22日 下午3:17:40
     * @param typeIds
     * @param itemId
     */
    public void saveBusTypeItem(String[] typeIds,String itemId){
        dao.remove("T_WSBS_SERVICEITEM_TYPE","ITEM_ID",new String[]{itemId});
        dao.saveBusTypeItem(typeIds, itemId);
    }
    
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getIdAndNamesByItemId(String itemId){
        return dao.getIdAndNamesByItemId(itemId);
    }
    /**
     * 
     * 描述 根据项目Id获取IDS和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public Map<String,Object> getLsIdAndNamesByItemId(String itemId){
        return dao.getLsIdAndNamesByItemId(itemId);
    }
    /**
     * 
     * 描述 根据项目Id获取ParentNames和NAMES
     * @author Flex Hu
     * @created 2015年9月22日 下午4:33:36
     * @param itemId
     * @return
     */
    public List<Map<String, Object>> getParentNamesAndNamesByItemId(String itemId){
        return dao.getParentNamesAndNamesByItemId(itemId);
    }
    
    /**
     * 
     * 描述 根据类别编码获取数据列表
     * @author Flex Hu
     * @created 2015年11月20日 下午5:16:59
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeForWebSite(String typeCode){
        StringBuffer sql = new StringBuffer("select B.TYPE_ID,B.TYPE_NAME,B.ICON_PATH");
        sql.append(" from T_WSBS_BUSTYPE B WHERE B.IS_SHOW=1 AND B.PARENT_ID=");
        sql.append("(SELECT T.TYPE_ID FROM T_WSBS_BUSTYPE T WHERE T.TYPE_CODE=? )");
        //前台展示排除没有事项的类型
        sql.append(" and exists(select type_id from T_WSBS_SERVICEITEM_TYPE st where st.type_id=b.type_id");
        sql.append(" and exists(select item_id from t_wsbs_serviceitem s where s.item_id=st.item_id))");
        sql.append(" ORDER BY B.TREE_SN ASC");
        List<Map<String,Object>> returnList = new ArrayList();
        if (typeCode.equals("LSTDGRTDRQ")) {
            List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{"GRTDRQ"}, null);
            for (Map<String, Object> map : list) {
                String typeId = map.get("TYPE_ID").toString();
                if (typeId.equals("4028818c512273e7015122dcad0c002a")||
                        typeId.equals("4028818c512273e7015122dcd4d7002b")||
                        typeId.equals("4028818c512273e7015122dcffec002c")||
                        typeId.equals("4028818c512273e7015122dd334b002d")||
                        typeId.equals("4028818c512273e7015122de05960030")||
                        typeId.equals("4028818c512273e7015122df1ff90033")) {
                    returnList.add(map);
                }
            }
        }else {
            List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
            returnList=list;
        }
        return returnList;
    }

    /**
     * 
     * 描述 根据编码与类别查询
     * @author Rider Chen
     * @created 2017年5月17日 下午2:56:03
     * @param typeCode
     * @param deptType
     * @return
     */
    public List<Map<String,Object>> findByTypeCodeForWebSite(String typeCode,String deptType){
        StringBuffer sql = new StringBuffer("select B.TYPE_ID,B.TYPE_NAME,B.ICON_PATH");
        sql.append(" from T_WSBS_BUSTYPE B WHERE B.IS_SHOW=1 AND B.DEPT_TYPE=? AND B.PARENT_ID=");
        sql.append("(SELECT T.TYPE_ID FROM T_WSBS_BUSTYPE T WHERE T.TYPE_CODE=? )");
        sql.append(" ORDER BY B.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[]{deptType,typeCode}, null);
    }

    /**
     * 
     * 描述   根据分类获取数据列表
     * @author Danto Huang
     * @created 2016-9-20 上午9:08:23
     * @param typeCode
     * @return
     */
    public List<Map<String,Object>> findDatasByClassForSelect(String typeCode){
        StringBuffer sql = new StringBuffer("select T.TYPE_ID as value,T.TYPE_NAME as text from T_WSBS_BUSTYPE T");
        sql.append(" WHERE T.PARENT_ID=(SELECT P.TYPE_ID FROM T_WSBS_BUSTYPE P WHERE P.TYPE_CODE=?)");
        sql.append(" ORDER BY T.TREE_SN ASC");
        return dao.findBySql(sql.toString(), new Object[]{typeCode}, null);
    }
}
