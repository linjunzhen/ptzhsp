/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.bdc.dao.BdcSsdjDao;
import net.evecom.platform.bdc.service.BdcSsdjService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 不动产全流程涉税登记
 * @author Madison You
 * @created 2020年6月518日 19:32:19
 */
@Service("bdcSsdjService")
public class BdcSsdjServiceImpl extends BaseServiceImpl implements BdcSsdjService {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/4/27 8:38:00
     * @param
     * @return
     */
    private static Log log = LogFactory.getLog(BdcSsdjServiceImpl.class);

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/6/18 19:54:00
     * @param
     * @return
     */
    @Resource
    private BdcSsdjDao bdcSsdjDao;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2020/6/18 19:54:00
     * @param
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return bdcSsdjDao;
    }

    /**
     * 描述:获取涉税登记最新受理号
     *
     * @author Madison You
     * @created 2020/6/27 14:09:00
     * @param
     * @return
     */
    @Override
    public String getSsdjSlh(String exeId) {
        StringBuffer sql = new StringBuffer();
        String SLH = "";
        sql.append(" SELECT SLH FROM T_BDCQLC_SSDJ_RETURNINFO WHERE EXE_ID = ? ORDER BY CREATE_TIME DESC ");
        List<Map<String,Object>> list = bdcSsdjDao.findBySql(sql.toString(), new Object[]{exeId}, null);
        if (list != null && !list.isEmpty()) {
            SLH = (String) list.get(0).get("SLH");
        }
        return SLH;
    }

    /**
     * 描述:获取涉税登记返回信息列表
     *
     * @author Madison You
     * @created 2020/7/1 11:32:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getLateSsdjInfo(String exeId) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> ssdjMap = null;
        sql.append(" SELECT T.SSDJ_ID,T.EXE_ID,T.SJBBH,T.SLH FROM T_BDCQLC_SSDJ_RETURNINFO T WHERE EXE_ID = ? ");
        sql.append(" ORDER BY CREATE_TIME DESC ");
        List<Map<String,Object>> ssdjList = bdcSsdjDao.findBySql(sql.toString(), new Object[]{exeId}, null);
        if (ssdjList != null && !ssdjList.isEmpty()) {
            ssdjMap = ssdjList.get(0);
        }
        return ssdjMap;
    }

    /**
     * 描述:交易ID取最新涉税登记返回
     *
     * @author Madison You
     * @created 2020/7/14 17:10:00
     * @param
     * @return
     */
    @Override
    public Map<String, Object> getLateSsdjInfoBySjbbh(String sjbbh) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> ssdjMap = null;
        sql.append(" SELECT T.* FROM T_BDCQLC_SSDJ_RETURNINFO T WHERE T.SJBBH = ? ");
        sql.append(" ORDER BY CREATE_TIME DESC ");
        List<Map<String,Object>> ssdjList = bdcSsdjDao.findBySql(sql.toString(), new Object[]{sjbbh}, null);
        if (ssdjList != null && !ssdjList.isEmpty()) {
            ssdjMap = ssdjList.get(0);
        }
        return ssdjMap;
    }

    /**
     * 描述:根据申报号查询事项子部门
     *
     * @author Madison You
     * @created 2020/12/23 9:22:00
     * @param
     * @return
     */
    @Override
    public String getItemChildDeptByExeId(String exeId) {
        String departName = "";
        StringBuffer sql = new StringBuffer();
        sql.append(" select d.depart_name from jbpm6_execution a ");
        sql.append(" left join t_wsbs_serviceitem b on a.item_code = b.item_code ");
        sql.append(" left join t_msjw_system_department d on b.zbcs_id = d.depart_id ");
        sql.append(" where exe_id = ? ");
        List<Map<String,Object>> departList= bdcSsdjDao.findBySql(sql.toString(), new Object[]{exeId}, null);
        if (departList != null && !departList.isEmpty()) {
            departName = (String) departList.get(0).get("DEPART_NAME");
        }
        try {
            departName = URLEncoder.encode(departName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return departName;
    }

    /**
     * 描述:根据事项编码查询事项子部门
     *
     * @author Madison You
     * @created 2020/12/23 9:42:00
     * @param
     * @return
     */
    @Override
    public String getItemChildDeptByItemCode(String itemCode) {
        String departName = "";
        StringBuffer sql = new StringBuffer();
        sql.append(" select d.depart_name from t_wsbs_serviceitem a ");
        sql.append(" left join t_msjw_system_department d on a.zbcs_id = d.depart_id ");
        sql.append(" where a.item_code = ? ");
        List<Map<String,Object>> departList = bdcSsdjDao.findBySql(sql.toString(), new Object[]{itemCode}, null);
        if (departList != null && !departList.isEmpty()) {
            departName = (String) departList.get(0).get("DEPART_NAME");
        }
        try {
            departName = URLEncoder.encode(departName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return departName;
    }
}
