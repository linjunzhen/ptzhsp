/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bsfw.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.platform.bsfw.dao.BusApplyDao;
import net.evecom.platform.bsfw.service.BusApplyService;
import net.evecom.platform.system.model.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年10月22日 下午3:41:03
 */
@Service("busApplyService")
public class BusApplyServiceImpl extends BaseServiceImpl implements BusApplyService {

    /**
     * 所引入的dao
     */
    @Resource
    private BusApplyDao dao;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Flex Hu
     * @created 2014年9月11日 上午9:14:37
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
   
    /**
     * 交通与建设局运管处是否审核通过
     * 描述
     * @author Rider Chen
     * @created 2016年1月21日 下午4:01:53
     * @param flowVars
     * @return
     */
    public Set<String> getAuditPass(Map<String,Object> flowVars){
        String nodeName = (String) flowVars.get("EFLOW_CURUSEROPERNODENAME");
        String isPass = (String) flowVars.get("isAuditPass");
        Set<String> resultNodes = new HashSet<String>();
        if (null != isPass && isPass.equals("1")) {
            if (nodeName.equals("初审")) {
                resultNodes.add("窗口会审");
            } else if (nodeName.equals("窗口会审")) {
                resultNodes.add("处事复审");
            } else if (nodeName.equals("处事复审")) {
                resultNodes.add("分管领导审核");
            } else if (nodeName.equals("分管领导审核")) {
                resultNodes.add("办结");
            }
        } else if (null != isPass && !isPass.equals("1")) {
            resultNodes.add("受理");
        } else {
            if (nodeName.equals("初审")) {
                resultNodes.add("窗口会审");
            } else if (nodeName.equals("窗口会审")) {
                resultNodes.add("处事复审");
            } else if (nodeName.equals("处事复审")) {
                resultNodes.add("分管领导审核");
            } else if (nodeName.equals("分管领导审核")) {
                resultNodes.add("办结");
            }
        }
        return resultNodes;        
    }
    /**
     * 
     * 描述 流程提交后置时间
     * @author Danto Huang
     * @created 2015-12-14 下午3:04:29
     * @param flowVars
     * @return
     */
    public Map<String, Object> start(Map<String,Object> flowVars){
        String lineId = (String)flowVars.get("lineId");
        String itemdetail_id = (String)flowVars.get("itemdetail_id");
        SysUser curLoginUser = null;
        try {
            curLoginUser =  AppUtil.getLoginUser();
        } catch (Exception e) {
            // 省下发事项无当前登录人
            return flowVars;
        }
        if(curLoginUser!=null){
            Map<String,Object> variable = new HashMap<String, Object>();
            variable.put("CALL_STATUS", "1");
            variable.put("OPERATOR", AppUtil.getLoginUser().getFullname());
            variable.put("OPERATOR_ID", AppUtil.getLoginUser().getUserId());
            //variable.put("OPER_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            variable.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
            if(StringUtils.isNotEmpty(lineId)){
                if(StringUtils.isNotEmpty(lineId)){
                    Map<String, Object> queue = this.getByJdbc("T_CKBS_QUEUERECORD", new String[] { "RECORD_ID" },
                            new Object[] { lineId });
                    if(queue!=null){
                        if (queue.get("IS_ITEM_CHOOSE").equals("1") && !"7".equals(queue.get("CALL_STATUS"))
                                && !"1".equals(queue.get("CALL_STATUS"))) {
                            variable = new HashMap<String, Object>();
                            variable.put("OPERATE_TIME", DateTimeUtil.getStrOfDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                            variable.put("EXE_ID", flowVars.get("EFLOW_EXEID"));
                            variable.put("DETAIL_STATUS", "1");
                            this.dao.saveOrUpdate(variable, "T_CKBS_QUEUERECORD_ITEMDETAIL", itemdetail_id);
                        }else{
                            variable.put("OPERATOR_NAME", AppUtil.getLoginUser().getFullname());
                            this.dao.saveOrUpdate(variable, "T_CKBS_QUEUERECORD", lineId);
                        }
                    }else{
                        this.dao.saveOrUpdate(variable, "T_CKBS_NUMRECORD", lineId);
                    }
                }
            }
        }
        return flowVars;
    }
}
