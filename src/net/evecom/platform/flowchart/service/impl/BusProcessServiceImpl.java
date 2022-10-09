/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.flowchart.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableInfo;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.flowchart.dao.BusProcessDao;
import net.evecom.platform.flowchart.model.BusProcessInfo;
import net.evecom.platform.flowchart.service.BusProcessService;

/**
 * 描述 业务过程service
 * @author Sundy Sun
 * @version v2.0
 *
 */
@Service("busProcessService")
public class BusProcessServiceImpl extends BaseServiceImpl implements BusProcessService {

    /**
     * data access 
     */
    @Resource
    private BusProcessDao dao;
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    @Override
    public List findProcessForTache(String tacheCode) {
        return dao.queryProcessByTache(tacheCode);
    }
    @Override
    public void saveBatch(List plist,String unitcode,String user,String tacheCode) {
        dao.saveBatch(plist, unitcode, user,tacheCode);
    }
    @Override
    public void saveProcess(Map map,String tablename,String processId) {
        dao.saveOrUpdateForAssignId(map, tablename, processId);
    }
    @Override
    public BusProcessInfo getProcessByCode(String processCode) {
        return dao.getProcessByCode(processCode);
    }
    @Override
    public void submitAudit(String tacheCode, String busCode,String userId) {
        dao.submitAudit(tacheCode, busCode,userId);
    }
    @Override
    public void cancelNodeAduit(String tacheCode, String busCode, String userId) {
        dao.cancelNodeAduit(tacheCode, busCode, userId);
    }
    @Override
    public void deleteProcessByBus(String busCode) {
        dao.deleteProcessByBus(busCode);
    }
    @Override
    public void updateByProcessCode(String processCode,String userId,String state) {
        String date = DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm");
        StringBuffer updateSql = new StringBuffer("update T_LCJC_BUS_RULECONFIG set is_valid=?"
                + ",update_user=?,update_time=? where process_code=?");
        dao.executeSql(updateSql.toString(), new Object[] {state, userId, date,processCode });
    }



}
