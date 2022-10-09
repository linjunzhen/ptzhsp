/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.platform.hflow.dao.AgainMaterDao;
import net.evecom.platform.hflow.service.AgainMaterService;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2016年4月22日 下午5:07:39
 */
@Service("againMaterService")
public class AgainMaterServiceImpl extends BaseServiceImpl implements AgainMaterService {
    /**
     * 所引入的dao
     */
    @Resource
    private AgainMaterDao dao;
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
     * 描述 保存二次审核的数据
     * @author Flex Hu
     * @created 2016年4月22日 下午5:09:02
     * @param jsonInfo
     * @param flowVars
     */
    public void saveDatas(String jsonInfo,Map<String,Object> flowVars){
        List<Map> dataList = JSON.parseArray(jsonInfo, Map.class);
        String exeId = (String) flowVars.get("EFLOW_EXEID");
        String FROMTASK_NODENAMES = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        //获取下一环节经办人的JSON
        String EFLOW_NEXTSTEPSJSON = (String) flowVars.get("EFLOW_NEXTSTEPSJSON");
        String nextTaskName = null;
        if(StringUtils.isNotEmpty(EFLOW_NEXTSTEPSJSON)){
            Map<String,Object> map = JSON.parseObject(EFLOW_NEXTSTEPSJSON,Map.class);
            nextTaskName = map.keySet().toArray(new String[map.keySet().size()])[0];
        }
        String userName = AppUtil.getLoginUser().getUsername();
        for(Map<String,Object> data:dataList){
            data.put("EXEID", exeId);
            data.put("FROMTASK_NODENAMES",FROMTASK_NODENAMES);
            data.put("NEXT_TASK_NAME",nextTaskName);
            data.put("USERNAME",userName);
            dao.saveOrUpdate(data, "JBPM6_FLOW_AGAIN_MATER",null);
        }
    }


    public List<Map<String, Object>> findAgainMaterByExeId(String exeId,String curnodename) {
        StringBuffer sql =new StringBuffer("SELECT M.MATER_ID,M.MATER_CODE,");
        sql.append("M.MATER_NAME,M.MATER_TYPE,M.MATER_SIZE,");
        sql.append("M.MATER_PATH FROM T_WSBS_APPLYMATER M ");
        sql.append(" WHERE M.MATER_ID IN ( SELECT AM.MATER_ID FROM JBPM6_FLOW_AGAIN_MATER AM  ");
        sql.append(" WHERE AM.EXEID = ? AND AM.NEXT_TASK_NAME = ? AND AM.SFTG = 0 )");
        return dao.findBySql(sql.toString(), new Object[]{exeId,curnodename}, null);
    }

    /**
     * 描述
     * @author Faker Li
     * @created 2016年5月6日 上午9:19:06
     * @param exeId
     * @param curnodename
     * @param materId
     * @return
     */
    @Override
    public String getYjByExeIdAnd(String exeId, String curnodename,
            String materId) {
        StringBuffer sql =new StringBuffer("SELECT wm_concat(AM.BTGYJ) FROM JBPM6_FLOW_AGAIN_MATER AM");
        sql.append("  WHERE AM.EXEID = ? AND AM.NEXT_TASK_NAME = ? AND AM.SFTG = 0  ");
        sql.append(" AND AM.MATER_ID = ? ");
        return (String)dao.getObjectBySql(sql.toString(), new Object[]{exeId,curnodename,materId});
    }
}
