/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AllConstant;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.BeanUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.weixin.dao.UserBindDao;
import net.evecom.platform.weixin.dao.WxUserDao;
import net.evecom.platform.weixin.service.WxUserService;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * 微信公众号用户service
 * @author Laura Song
 */
@Service("wxUserService")
public class WxUserServiceImpl extends BaseServiceImpl implements WxUserService{
    
    /**
     * 数据访问dao
     */
    @Resource
    private WxUserDao dao;
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    /**
     * 根据openid 获取用户信息
     * @author Laura Song
     * @param openid
     * @return
     */
    public Map<String, Object> findUserByOpenId(String openid){
        
        Map<String, Object> result = new HashMap<String, Object>();
        Map userBind = dao.getByJdbc("T_WX_USERBIND", 
                new String[]{"OPEN_ID"}, new Object[]{openid});
        if(userBind==null){
            result.put("success", false);
            result.put("msg", "该用户还没绑定");
            return result;
        }
        
        // 1、取T_WX_USERBIND（微信公众号关联表）中的用户名 密码
        // 2、与T_BSFW_USERINFO（用户表）中的用户名、密码比对
        String yhzh = (String) userBind.get("USER_NAME");
        String newPwd = (String)userBind.get("USER_PWD");
        /*StringBuffer sql = new StringBuffer("SELECT T.* FROM T_BSFW_USERINFO T ");
        sql.append("where T.YHZH='?' and T.DLMM='?' ");
        Map user = dao.getByJdbc(sql.toString(), 
                new Object[]{yhzh, newPwd});*/
        Map<String,Object>  user = dao.getByJdbc("T_BSFW_USERINFO",
                new String[]{"YHZH","DLMM"},new Object[]{yhzh,newPwd});
        if(user==null){
            result.put("success", false);
            result.put("msg", "绑定的用户信息有误");
            return result;
        }
        
        result.put("success", true);
        result.put("info", user);
        return result;
    }

}
