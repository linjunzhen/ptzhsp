/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.model.flow;


import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.FlowEventService;

import java.util.Map;

/**
 * 描述  有限公司办件办结类
 * @author Water Guo
 * @version 1.0
 * @created 2020年9月6日 上午7:19:11
 */
public class CompanyFlowEndProcess implements FlowEndProcess {
    /**
     * 办结数据处理
     * @param flowInfo
     */
    @Override
    public void flowEnd(Map<String, Object> flowInfo) {
        ExecutionDao dao= (ExecutionDao) AppUtil.getBean("executionDao");
        FlowEventService flowEventService= (FlowEventService) AppUtil.getBean("flowEventService");
        String exeId = StringUtil.getString(flowInfo.get("EXE_ID"));
        String xkfileNum=StringUtil.getString(flowInfo.get("XKFILE_NUM"));

        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //更新社会信用统一代码
        String busRecordId = (String) execution.get("BUS_RECORDID");//获取业务ID
        String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0);//获取业务表主键名称
        StringBuffer sql=new StringBuffer();
        sql.append("update  ").append(busTableName);
        sql.append(" c  set c.social_creditunicode= ?   ");
        sql.append(" where c.").append(busPkName).append("=? ");
        dao.executeSql(sql.toString(),new Object[]{xkfileNum,busRecordId});
        //生成社会保险登记表
        flowEventService.createSocialForm(exeId);
    }
}
