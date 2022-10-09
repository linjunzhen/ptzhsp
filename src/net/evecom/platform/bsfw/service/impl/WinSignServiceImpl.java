/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.bsfw.dao.ShtzDao;
import net.evecom.platform.bsfw.service.WinSignService;
import net.evecom.platform.hflow.service.JbpmService;
import net.evecom.platform.system.model.SysUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 描述 政府投资项目第一阶段服务接口
 * 
 * @author Derek Zhang
 * @version 1.0
 * @created 2015年11月16日 上午9:39:36
 */
@SuppressWarnings("rawtypes")
@Service("winSignService")
public class WinSignServiceImpl extends BaseServiceImpl implements WinSignService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(WinSignServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ShtzDao dao;
    /**
     * jbpmService
     */
    @Resource
    private JbpmService jbpmService;

    /**
     * 描述 是否需要协调
     * 
     * @author Derek Zhang
     * @created 2015年11月11日 上午10:42:40
     * @param flowVars
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> findCreditList(String codes, String names) {
        String sql = "select count(c.credit_id) from t_bsfw_credit c  where 1=1 ";
        List<Object> objList = new ArrayList<Object>();
        if (!StringUtils.isBlank(names)) {
            sql += " and  c.corp_name like ? ";
            objList.add("%" + names.trim() + "%");
        }
        if (!StringUtils.isBlank(codes)) {
            sql += "and (c.corp_code like ? or c.reg_num like ? or c.credit_code like ? ) ";
            objList.add("%" + codes.trim() + "%");
            objList.add("%" + codes.trim() + "%");
            objList.add("%" + codes.trim() + "%");
        }
        Map<String, Object> resultNodes = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> creditCountList = this.dao.findBySql(sql, objList.toArray(), null, null);
            if (creditCountList == null || creditCountList.isEmpty() || creditCountList.size() == 0
                    || StringUtils.isBlank(("" + creditCountList.get(0)))
                    || ("" + creditCountList.get(0)).equals("0")) {
                resultNodes.put("HAS_CREDIT", false);
            } else {
                if (creditCountList.size() == 1) {
                    resultNodes.put("HAS_CREDIT", true);
                }
            }
        } catch (Exception e) {
            resultNodes.put("HAS_CREDIT", false);
            log.error(e.getMessage());
        }
        return resultNodes;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年6月17日 上午9:49:53
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return null;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年6月22日 下午4:54:46
     * @param filter
     * @return
     * @see net.evecom.platform.bsfw.service.CreditService#findBySqlFilter(net.evecom.core.util.SqlFilter)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findBySqlFilter(SqlFilter filter, String beginTime, String endTime,
            String userId, String vIp) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("select  t.sign_id,t.win_no,t.login_user_name,to_char"
                + "(t.login_time,'yyyy-mm-dd hh24:mi:ss') as login_time,t.win_ip ");
        sql.append(" FROM T_BSFW_WIN_SIGN T where 1=1 ");
        if (!StringUtils.isBlank(beginTime)) {
            sql.append(" and t.login_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            params.add(beginTime);
        }
        if (!StringUtils.isBlank(endTime)) {
            sql.append(" and t.login_time<=to_date(?,'yyyy-mm-dd hh24:mi:ss')");
            params.add(endTime.substring(0, 10) + " 23:59:59");
        }
        if (!StringUtils.isBlank(userId)) {
            sql.append(" and (t.login_user_code=? or t.win_ip=?)");
            params.add(userId);
            params.add(vIp);
        }
        String exeSql = dao.getQuerySql(filter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), filter.getPagingBean());
        return list;
    }

    /**
     * 描述
     * 
     * @author Derek Zhang
     * @created 2016年7月7日 上午11:00:40
     * @param userId
     * @param string
     * @param ipAddr
     * @see net.evecom.platform.bsfw.service.WinSignService#doSign(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> doSign(SysUser sysUser, String winUserAddr, String ipAddr) {
        Map<String, Object> colValues = new HashMap<String, Object>();
        String sql = "select * from t_ckbs_win_screen t  where t.proxy_addr=? and t.win_addr = ? ";
        List<Map<String, Object>> list = this.dao.findBySql(sql, new Object[] { winUserAddr, ipAddr }, null);
        String winNo = "";
        if (list != null && !list.isEmpty()) {
            winNo = list.get(0).get("WIN_NO") == null ? "" : (String) list.get(0).get("WIN_NO");
            colValues.put("WIN_NO", winNo);
        }
        colValues.put("LOGIN_USER_CODE", sysUser.getUsername());
        colValues.put("LOGIN_USER_NAME", sysUser.getFullname());
        colValues.put("LOGIN_TIME", new Date());
        colValues.put("WIN_IP", winUserAddr);
        colValues.put("CLIENT_IP", ipAddr);
        String returnStr = this.dao.saveOrUpdate(colValues, "T_BSFW_WIN_SIGN", "SIGN_ID");
        colValues.put("saveSta", returnStr);
        return colValues;
    }

    /**
     * 描述 判断是否是需要签到的IP
     * 
     * @author Derek Zhang
     * @created 2016年7月9日 上午11:32:49
     * @param userWinIp
     * @param clientIp
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean isNeedSign(String userWinIp, String clientIp) {
        String sql = "select count(*) as ipCount from t_ckbs_win_screen t  where t.proxy_addr=? and t.win_addr = ? ";
        List<Map<String, Object>> list = this.dao.findBySql(sql, new Object[] { userWinIp, clientIp }, null);
        if (list != null && !list.isEmpty()) {
            Map<String, Object> map = list.get(0);
            Object vcount = map.get("IPCOUNT");
            if (vcount != null && Integer.valueOf(vcount + "") > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 描述 判断是否是当日首次签到
     * 
     * @author Derek Zhang
     * @created 2016年7月9日 下午1:27:54
     * @param userWinIp
     * @param ipAddr
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> isFirstSignByCurDate(String userWinIp, String ipAddr) {
        String sql = "select m.* from (select t.sign_id,t.win_no,"
                + " to_char(t.login_time,'yyyy-mm-dd hh24:mi:ss') as login_time,t.win_ip,t.client_ip"
                + " from t_bsfw_win_sign t where t.win_ip = ? and t.client_ip = ?"
                + " and t.login_time > to_date(?, 'yyyy-mm-dd hh24:mi:ss') order by t.login_time desc) m"
                + " where rownum=1";
        List<Map<String, Object>> list = this.dao.findBySql(sql, new Object[] { userWinIp, ipAddr,
                (new SimpleDateFormat("yyyy-MM-dd")).format(new Date())+" 00:00:00" }, null);
        return list;
    }
}
