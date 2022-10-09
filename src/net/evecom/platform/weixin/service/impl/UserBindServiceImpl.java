/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service.impl;

import java.util.*;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.weixin.dao.UserBindDao;
import net.evecom.platform.weixin.service.UserBindService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 描述
 * @author Sundy Sun
 * @version v1.0
 */
@Service("userBindService")
public class UserBindServiceImpl extends BaseServiceImpl implements UserBindService{
    
    /**
     * 数据访问dao
     */
    @Resource
    private UserBindDao dao;
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/5/27 11:18:00
     * @param
     * @return
     */
    @Resource
    private EncryptionService encryptionService;

    @Override
    public List<Map<String, Object>> findUserBySql(String whereSql) {
        //Map<String, Object> mlist = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_BSFW_USERINFO T ");
        sql.append(whereSql);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }
    @Override
    public List<Map<String, Object>> findBindBySql(String whereSql) {
        //Map<String, Object> mlist = new HashMap<String, Object>();
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.* FROM T_WX_USERBIND T ");
        sql.append(whereSql);
        List<Map<String, Object>> list = dao.findBySql(sql.toString(), params.toArray(), null);
        return list;
    }

    /**
     * 描述:判断MAP集合中参数是否为空
     *
     * @author Madison You
     * @created 2020/5/26 14:48:00
     * @param
     * @return
     */
    @Override
    public boolean isParamsEmpty(Map<String, Object> map) {
        boolean flag = false;
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            Object value = next.getValue();
            if (value == null || value.equals("")) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 描述:查询个人用户账号信息
     *
     * @author Madison You
     * @created 2020/5/26 15:06:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findGryhUserInfo(Map<String, Object> variables) {
        List<Object> params = new ArrayList<>();
        Sm4Utils sm4 = new Sm4Utils();
        params.add(sm4.encryptDataCBC(StringUtil.getValue(variables, "SJHM")));
        params.add(sm4.encryptDataCBC(StringUtil.getValue(variables, "ZJHM")));
        params.add(StringUtil.getValue(variables, "YHMC"));
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM T_BSFW_USERINFO WHERE SJHM = ? AND ZJHM = ? AND YHMC = ? AND USER_TYPE = '1' ");
        sql.append(" ORDER BY ZCSJ DESC ");
        List<Map<String, Object>> userList = dao.findBySql(sql.toString(), params.toArray(), null);
        return encryptionService.listDecrypt(userList,"T_BSFW_USERINFO");
    }

    /**
     * 描述:查询法人用户账号信息
     *
     * @author Madison You
     * @created 2020/5/26 15:19:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findFryhUserInfo(Map<String, Object> variables) {
        List<Object> params = new ArrayList<>();
        Sm4Utils sm4 = new Sm4Utils();
        params.add(sm4.encryptDataCBC(StringUtil.getValue(variables, "MOBILE_PHONE")));
        params.add(StringUtil.getValue(variables, "ZZJGDM"));
        params.add(StringUtil.getValue(variables, "ORG_NAME"));
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM T_BSFW_USERINFO WHERE MOBILE_PHONE = ? AND ZZJGDM = ? AND ORG_NAME = ? ");
        sql.append(" AND USER_TYPE = '2' ORDER BY ZCSJ DESC ");
        List<Map<String, Object>> userList = dao.findBySql(sql.toString(), params.toArray(), null);
        return encryptionService.listDecrypt(userList,"T_BSFW_USERINFO");
    }

    /**
     * 
     * 描述 获取前台我的办件列表
     * @author Faker Li
     * @created 2015年12月1日 上午9:55:53
     * @param page
     * @param rows
     * @return
     */
//    public Map<String, Object> findfrontWdbjList(String page, String rows) {
//        Map<String, Object> mlist = new HashMap<String,Object>();
//        List<Map<String, Object>> list = null;
//        List<Object> params = new ArrayList<Object>();
//        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
//        StringBuffer sql = new StringBuffer( "SELECT E.EXE_ID,E.ITEM_NAME,E.RUN_STATUS,E.ITEM_CODE,");
//        sql.append("E.FINAL_HANDLEOPINION,E.CREATE_TIME  FROM JBPM6_EXECUTION E ");
//        sql.append(" WHERE E.CREATOR_ACCOUNT = '"+AppUtil.getLoginMember().get("YHZH")+"' ");
//        sql.append(" AND E.SQFS = '1' ");
//        sql.append(" ORDER BY E.CREATE_TIME DESC ");
//        list = dao.findBySql(sql.toString(),params.toArray(), pb);
//        for (int i = 0; i < list.size(); i++) {
//            Map<String,Object>  bspj = null;
//            bspj = bspjService.getByJdbc("T_WSBS_BSPJ",
//                    new String[]{"EXE_ID"},new Object[]{list.get(i).get("EXE_ID").toString()});
//            if(bspj!=null){
//                list.get(i).put("isPj", "1");//已评价
//            }else{
//                list.get(i).put("isPj", "0");//未评价
//            }
//        }
//        mlist.put("total", pb.getTotalItems());
//        mlist.put("list", list);
//        return mlist;
//    }

}
