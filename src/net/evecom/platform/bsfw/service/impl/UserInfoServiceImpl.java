/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.platform.bsfw.dao.UserInfoDao;
import net.evecom.platform.bsfw.service.UserInfoService;
import net.evecom.platform.bsfw.util.MztSample;
import net.evecom.platform.encryption.service.EncryptionService;
import net.evecom.platform.system.model.SysUser;
import net.evecom.platform.system.service.SysLogService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 前台用户管理操作service
 * 
 * @author Faker Li
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {
    /**
     * log
     */
    private static Log log= LogFactory.getLog(UserInfoServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private UserInfoDao dao;
    /**
     * sysLogService
     */
    @Resource
    private SysLogService sysLogService;
    /**
     * encryptionService
     */
    @Resource
    private EncryptionService encryptionService;

    /**
     * 覆盖获取实体dao方法 描述
     * 
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
     * 描述 获取前台用户列表
     * 
     * @author Faker Li
     * @created 2015年11月20日 下午3:03:06
     * @param filter
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select T.USER_ID,T.YHZH,T.YHMC,T.SJHM,"
                + "T.USER_TYPE,T.YHZT from T_BSFW_USERINFO T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        
        list = encryptionService.listDecrypt(list, "T_BSFW_USERINFO");
        
        SysUser sysUser = AppUtil.getLoginUser();
        // 获取菜单KEY
        String resKey = sysUser.getResKeys();
        Set<String> roleCodes = sysUser.getRoleCodes();
        // 判断是否行政服务中心管理员
        boolean gly = roleCodes.contains("88888_GLY");        
        List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
        if ("__ALL".equals(resKey)||gly) {// 判断是否超级管理员/行政服务中心管理员
            for (Map<String, Object> map : list) {//SJHM 手机 
                if (map.get("SJHM")!=null) {
                    String sjhm = map.get("SJHM").toString();
                    char [] str = sjhm.toCharArray();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.replace(3, 7, "****");
                    map.put("SJHM", sb.toString().toLowerCase());
                }
                returnList.add(map);
            }
            return returnList;
        }else {
            return list;
        }
    }

    @Override
    public int isExistsUser(String username, String password) {
        // TODO Auto-generated method stub
        return dao.isExistsUser(username, password);
    }

    /**
     * 
     * 描述 更新状态
     * @author Faker Li
     * @created 2015年11月20日 下午5:26:43
     * @param selectColNames
     * @param parseInt
     * @see net.evecom.platform.bsfw.service.UserInfoService#updateYHZT(java.lang.String, int)
     */
    public void updateYHZT(String selectColNames, int parseInt) {
        dao.updateYHZT(selectColNames,parseInt);
    }
    
    /**
     * 
     * 描述 根据帐号获取用户列表数据
     * @author Flex Hu
     * @created 2015年12月14日 下午4:22:29
     * @param accounts
     * @return
     */
    public List<Map<String,Object>> findByAccounts(String accounts){
        StringBuffer sql = new StringBuffer("SELECT U.USER_ID,U.YHZH,U.YHMC");
        sql.append(",U.SJHM FROM T_BSFW_USERINFO U WHERE ");
        sql.append("U.YHZH IN ").append(StringUtil.getValueArray(accounts));
        return dao.findBySql(sql.toString(), null, null);
    }


    public Map<String, Object> findfrontSjdzList(String page, String rows,String yhzh) {
        Map<String, Object> mlist = new HashMap<String,Object>();
        List<Map<String, Object>> list = null;
        List<Object> params = new ArrayList<Object>();
        PagingBean pb = new PagingBean((Integer.parseInt(page)-1)*Integer.parseInt(rows), Integer.parseInt(rows));
        StringBuffer sql = new StringBuffer( " ");
        sql.append(" SELECT T.ADD_ID, T.USER_ID, T.REC_NAME, ");
        sql.append(" T.MOBILE, T.FIXED_TEL, T.PROVINCE,t.CITY, T.DETAIL_ADD, ");
        sql.append(" T.POSTCODE, T.IS_DEFAULT FROM T_BSFW_USERRECADD T ");
        sql.append(" WHERE T.USER_ID = '"+yhzh+"' ");
        sql.append(" ORDER BY t.CREATE_TIME DESC ");
        list = dao.findBySql(sql.toString(),params.toArray(), pb);
        mlist.put("total", pb.getTotalItems());
        mlist.put("list", list);
        return mlist;
    }

    @Override
    public void deleteByAddrId(String[] addId) {
        // TODO Auto-generated method stub
        dao.remove("T_BSFW_USERRECADD","ADD_ID",addId);
    }
    @Override
    public void deleteByFileId(String[] fileId) {
        // TODO Auto-generated method stub
        dao.remove("T_MSJW_SYSTEM_FILEATTACH","FILE_ID",fileId);
    }

    public void updateIsDefault(String string) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("update T_BSFW_USERRECADD t ");
        sql.append("set t.is_default = 0 ");
        sql.append("where t.user_id = ? ");
        dao.executeSql(sql.toString(), new Object[]{string});
    }

    @Override
    public void makeDefaultAddr(String addId, String userId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("update T_BSFW_USERRECADD t ");
        sql.append("set t.is_default = 0 ");
        sql.append("where t.user_id = ? ");
        dao.executeSql(sql.toString(), new Object[]{userId});
        
        StringBuffer sql2 = new StringBuffer("update T_BSFW_USERRECADD t ");
        sql2.append("set t.is_default = 1 ");
        sql2.append("where t.add_id = ? ");
        dao.executeSql(sql2.toString(), new Object[]{addId});
        
    }

    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> findAddrByUserId(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select t.* from T_BSFW_USERRECADD t  where 1=1");

        // 获取当前用户信息
        Map<String, Object> curUser = AppUtil.getLoginMember();
        // 非超管进行数据级别权限控制
        if (curUser!=null) {
            // 获取当前用户被授权的部门代码
            String userId = curUser.get("USER_ID")==null?
                    "":curUser.get("USER_ID").toString();
            sql.append(" AND T.USER_ID in ").append(
                    StringUtil.getValueArray(userId));
        }
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 
     * 描述    更新闽政通登录用户至session
     * @author Danto Huang
     * @created 2020年5月19日 上午11:01:22
     * @param trustTicket
     */
    public void getMztUserInfoToSession(String trustTicket){
        Properties properties = FileUtil.readProperties("conf/config.properties");
        String callerCode = properties.getProperty("callerCode");
        String resultData = MztSample.getUserInfo(callerCode, trustTicket);
        if (resultData != null && !"".equals(resultData)) {
            Map<String, Object> resultMap = sysLogService.mztUserInfor(resultData);
            String yhzh = resultMap.get("YHZH").toString();
            Map<String, Object> userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "YHZH" },
                    new Object[] { yhzh });
            if (userInfo == null || userInfo.size() == 0) {
                userInfo = dao.getByJdbc("T_BSFW_USERINFO", new String[] { "MZT_YHZH" },
                        new Object[] { yhzh });
            }
            if (userInfo != null) {
                userInfo.put("MZT_USER_ID", resultMap.get("MZT_USER_ID"));
                userInfo.put("MZT_USER_TOKEN", resultMap.get("MZT_USER_TOKEN"));
                // 获取用户的状态
                String userStatus = (String) userInfo.get("YHZT");
                if (userStatus.equals(AllConstant.WEBSITEUSER_STATUS_DISABLE)) {
                    
                } else if (userStatus.equals(AllConstant.WEBSITEUSER_STATUS_FREEZEN)) {
                    
                } else {
                    userInfo = sysLogService.getLastLoginInfoForMember(userInfo);
                    HttpSession session = AppUtil.getSession();
                    AppUtil.addMemberToSession(session, userInfo);
                    String yhmc = (String) userInfo.get("YHMC");
                    sysLogService.saveLogForMember("公众用户[" + yhmc + "]登录了网站.", SysLogService.OPERATE_TYPE_LOGIN);
                }
            } 
        }
        
    }
}
