/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bespeak.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.rpc.ParameterMode;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.bespeak.dao.BespeakOnlineDao;
import net.evecom.platform.bespeak.model.UserInfo;
import net.evecom.platform.bespeak.service.BespeakOnlineService;
import net.evecom.platform.bespeak.util.ClientManager;
import net.evecom.platform.bespeak.util.StringUtils;
import net.evecom.platform.bespeak.util.WebServiceUtil;
import net.evecom.platform.system.service.SysSendMsgService;

import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述
 * 
 * @author Panda Chen
 * @created 2016-12-1 下午05:14:38
 */
@SuppressWarnings("unchecked")
@Service("bespeakOnlineServiceImpl")
public class BespeakOnlineServiceImpl extends BaseServiceImpl implements BespeakOnlineService {
    /**
     * log
     */
    private static Log log = LogFactory.getLog(BespeakOnlineServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    BespeakOnlineDao dao;
    /**
     * sendMsgService
     */
    @Resource
    SysSendMsgService sendMsgService;

    /**
     * 
     * 描述
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:18:00
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     * 
     * 描述 根据父亲ID获取部门数据
     * 
     * @author Panda Chen
     * @created 2016-12-1 下午05:20:16
     * @param parentId
     * @return
     */
    public List<Map<String, Object>> findByParentId(String parentId) {
        String sql = "select * from T_BESPEAK_ONLINE_CONFIG WHERE";
        sql += " PARENT_ID=? ORDER BY TREE_SN ASC,CREATE_TIME ASC";
        return dao.findBySql(sql, new Object[] { parentId }, null);
    }

    /**
     * 
     * 描述保存树形结构
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:25:18
     * @param parentId
     * @param department
     * @return
     */
    public String saveOrUpdateTreeData(String parentId, Map<String, Object> department) {
        String recordId = this.saveOrUpdateTreeData(parentId, department, "T_BESPEAK_ONLINE_CONFIG", null);
        return recordId;
    }

    /**
     * 
     * 描述根据sqlfilter获取到数据列表
     * 
     * @author Panda Chen
     * @created 2016-12-2 上午11:24:56
     * @param sqlFilter
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> findBySqlFilter(SqlFilter sqlFilter, String tableName) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select " + "* from " + tableName + " T");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 
     * 描述更新字段顺序
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:35:18
     * @param column
     * @param tablename
     * @param configIds
     * @see net.evecom.platform.business.service.CommonService#updateSn(java.lang.String,
     *      java.lang.String, java.lang.String[])
     */
    public void updateSn(String column, String tablename, String[] configIds) {
        dao.updateSn(column, tablename, configIds);
    }

    /**
     * 
     * 描述获取最大排序
     * 
     * @author Panda Chen
     * @created 2016年3月24日 上午10:44:04
     * @param tablename
     * @param column
     * @return
     */
    public int getMaxSn(String tablename, String column) {
        return dao.getMaxSn(tablename, column);
    }

    /**
     * 
     * 描述根据sql获取列表
     * 
     * @author Panda Chen
     * @created 2016年3月23日 下午3:17:00
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String, Object>> getListBySql(String sql, Object[] queryParams) {
        List<Map<String, Object>> list = dao.findBySql(sql, queryParams, null);
        return list;
    }

    /**
     * 
     * 描述运行sql
     * 
     * @author Panda Chen
     * @created 2016年3月23日 下午3:16:53
     * @param sql
     * @param queryParams
     */
    public void executeSql(String sql, Object[] queryParams) {
        dao.executeSql(sql, queryParams);
    }

    /**
     * 
     * 描述根据usercode调用星榕基webservice获取登陆用户信息
     * 
     * @author Panda Chen
     * @created 2016-12-6 下午04:40:51
     * @param userCode
     * @return
     */
    public UserInfo getUser(String userCode) {
        log.info(userCode);
        UserInfo userInfoSession = ClientManager.getInstance().getClient();
        if (StringUtils.isEmpty(userInfoSession)) {
            String webservice = ClientManager.getInstance().getWebservice();
            String namespace = ClientManager.getInstance().getNamespace();
            try {
                // 根据usercode调用星榕基webservice获取登陆用户信息
                Map<String, Object[]> map = new HashMap<String, Object[]>();
                Object[] objs0 = new Object[3];
                objs0[0] = XMLType.XSD_STRING;
                objs0[1] = ParameterMode.IN;
                objs0[2] = "TFUhGhfzhRWdecKuTA==";
                Object[] objs1 = new Object[3];
                objs1[0] = XMLType.XSD_STRING;
                objs1[1] = ParameterMode.IN;
                objs1[2] = userCode;
                map.put("arg0", objs0);
                map.put("arg1", objs1);
                Object obj = WebServiceUtil.callService(webservice, namespace, "getPublicUserById", map,
                        XMLType.XSD_STRING);
                if(!StringUtils.isEmpty(obj) && !obj.equals("{}")){
                    userInfoSession = JSON.parseObject(obj.toString(), UserInfo.class);
                    ClientManager.getInstance().addClinet(userInfoSession);
                    log.info("成功获取用户信息并保持到session中");
                }else{
                    log.info("获取用户失败");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
                log.info("获取星榕基用户信息失败，userCode=" + userCode);
            }
            return userInfoSession;
        } else {
            log.info("成功获取保存在session中的用户信息"+userInfoSession);
            return userInfoSession;
        }
    }

    /**
     * 
     * 描述 发送短信
     * 
     * @author Panda Chen
     * @created 2016-12-7 上午09:59:58
     * @param variables
     */
    public void sendMsg(Map variables) {
        //获取开始时间后三十分钟
        String beginTime = variables.get("DATE_TIME") + " " + variables.get("BEGIN_TIME");
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(beginTime);
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            log.error(e1.getMessage());
        }
        Calendar limitTime = Calendar.getInstance();
        limitTime.setTime(date);
        limitTime.add(limitTime.MINUTE, 30);
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String limitTimeStr = sdf1.format(limitTime.getTime());
        
        try {
            // 当手机号码不为空时发送短信
            if (StringUtils.isNotBlank(variables.get("PHONE").toString())) {
                StringBuffer content = new StringBuffer();
                content.append("您好，您于");
                content.append(variables.get("CREATE_TIME"));
                content.append("成功预约了");
                content.append(variables.get("DATE_TIME"));
                content.append(" ");
                content.append(variables.get("BEGIN_TIME"));
                content.append("-");
                content.append(variables.get("END_TIME"));
                content.append("时段，请于");
                content.append(limitTimeStr);
//                content.append("前进行人工取票，谢谢！");
                content.append("前 前往自助取号机取号，谢谢！");
                sendMsgService.saveSendMsg(content.toString(), null, variables.get("PHONE").toString(), 0, null);
                log.info("发送短信成功，手机号码为："+variables.get("PHONE"));
            }else{
                log.info("发送短信失败，手机号码为空");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
            log.info("发送短信失败");
        }
    }

    @Override
    public void passAppointmentToDeptId(String id) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("update T_BESPEAK_APPLY t set t.status=2 ");
        sql.append("where t.is_take=0 and t.status=1 and t.DEPART_ID ");
        sql.append(" in ( select c.depart_id from T_BESPEAK_ONLINE_CONFIG c where c.id = ?)");
        dao.executeSql(sql.toString(), new Object[]{id});
    }
}
