/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.SqlFilter;
import net.evecom.platform.hflow.dao.MaterConfigDao;
import net.evecom.platform.hflow.model.FlowNextHandler;
import net.evecom.platform.hflow.model.FlowNextStep;
import net.evecom.platform.hflow.service.MaterConfigService;
import net.evecom.platform.system.service.DepartmentService;
import net.evecom.platform.system.service.FileAttachService;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * 描述 审批材料操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("materConfigService")
public class MaterConfigServiceImpl extends BaseServiceImpl implements MaterConfigService {
    /**
     * 所引入的dao
     */
    @Resource
    private MaterConfigDao dao;
    /**
     * 
     */
    @Resource
    private DepartmentService departmentService;
    /**
     * 
     */
    @Resource
    private FileAttachService fileAttachService;
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
     * 根据sqlfilter获取到数据列表
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findBySqlFilter(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.CONFIG_ID,D.DOCUMENT_NAME,DIC.DIC_NAME");
        sql.append(",T.CONFIG_SN,T.IS_BACKFILL,D.DOCUMENT_TYPE FROM JBPM6_MATERCONFIG T ");
        sql.append("LEFT JOIN T_WSBS_DOCUMENTTEMPLATE D ON T.TPL_ID=D.DOCUMENT_ID ");
        sql.append("LEFT JOIN T_MSJW_SYSTEM_DICTIONARY DIC ON DIC.DIC_CODE=D.DOCUMENT_TYPE ");
        sql.append("WHERE DIC.TYPE_CODE='documentType' ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 保存模版配置信息
     * @author Flex Hu
     * @created 2016年1月29日 上午9:53:15
     * @param defId
     * @param nodeName
     * @param tplIds
     */
    public void saveOrUpdate(String defId,String nodeName,String tplIds){
        String[] tplIdArray = tplIds.split(",");
        for(String tplId:tplIdArray){
            Map<String,Object> materConfig = dao.getByJdbc("JBPM6_MATERCONFIG",
                    new String[]{"DEF_ID","NODE_NAME","TPL_ID"},new Object[]{defId,nodeName,tplId});
            if(materConfig==null){
                //获取最大排序值
                int maxSn = dao.getMaxSn(defId, nodeName);
                materConfig = new HashMap<String,Object>();
                materConfig.put("DEF_ID", defId);
                materConfig.put("NODE_NAME", nodeName);
                materConfig.put("TPL_ID", tplId);
                materConfig.put("CONFIG_SN", maxSn+1);
                dao.saveOrUpdate(materConfig, "JBPM6_MATERCONFIG", null);
            }
        }
    }
    
    /**
     * 
     * 描述 更新排序字段
     * @author Flex Hu
     * @created 2014年10月3日 下午12:54:23
     * @param dicIds
     */
    public void updateSn(String[] configIds){
        dao.updateSn(configIds);
    }
    
    /***
     * 
     * 描述 根据流程定义ID和节点名称和模版类型获取数据列表
     * @author Flex Hu
     * @created 2016年1月30日 下午3:43:57
     * @param defId
     * @param nodeName
     * @param documentType
     * @return
     */
    public List<Map<String,Object>> findList(String defId,String nodeName,String documentType,String exeId){
        StringBuffer sql = new StringBuffer("SELECT T.CONFIG_ID,D.DOCUMENT_NAME,T.CONFIG_SN,T.TPL_ID");
        sql.append(",D.DOCUMENT_TYPE,D.SFHTML,T.IS_BACKFILL FROM JBPM6_MATERCONFIG T");
        sql.append(" LEFT JOIN T_WSBS_DOCUMENTTEMPLATE D ON T.TPL_ID=D.DOCUMENT_ID ");
        sql.append("WHERE T.DEF_ID=? AND T.NODE_NAME=? AND D.DOCUMENT_TYPE=? ");
        sql.append(" ORDER BY T.CONFIG_SN ASC ");
        List<Map<String,Object>> list = dao.findBySql(sql.toString(), new Object[]{defId,nodeName,documentType}, null);
        if(StringUtils.isNotEmpty(exeId)){
            for(Map<String,Object> mater:list){
                String isBackFill = mater.get("IS_BACKFILL").toString();
                String tplId = mater.get("TPL_ID").toString();
                if(isBackFill.equals("1")){
                    Map<String,Object> file = fileAttachService.getFilePath(exeId, tplId);
                    if(file!=null){
                        mater.putAll(file);
                    }
                }
            }
        }
        return list;
    }
    
    
    /**
     * 
     * 描述 获取下一环节具备查看审核材料权限的部门列表
     * @author Flex Hu
     * @created 2016年1月30日 下午5:05:14
     * @param steps
     * @return
     */
    public List<Map<String,Object>> findMaterAuiderDeps(List<FlowNextStep> steps){
        List<Map<String,Object>> depList = new ArrayList<Map<String,Object>>();
        Set<String> depIds = new HashSet<String>();
        for(FlowNextStep step:steps){
            List<FlowNextHandler> handlers = step.getHandlers();
            for(FlowNextHandler handler:handlers){
                String nextStepAssignerType = handler.getNextStepAssignerType();
                String nextStepAssignerCode = handler.getNextStepAssignerCode();
                if(!(StringUtils.isNotEmpty(nextStepAssignerType)&&nextStepAssignerType.equals("2"))){
                    Map<String,Object> depInfo = departmentService.getDepInfoByUserAccount(nextStepAssignerCode);
                    String depId = (String) depInfo.get("DEPART_ID");
                    if(!depIds.contains(depId)){
                        depList.add(depInfo);
                    }
                    depIds.add(depId);
                }
            }
        }
        if(depList.size()>0){
            return depList;
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 获取下一环节的有权限审核材料的人员列表
     * @author Flex Hu
     * @created 2016年3月3日 下午4:17:33
     * @param steps
     * @return
     */
    public List<Map<String,Object>> findMaterAuditers(List<FlowNextStep> steps){
        List<Map<String,Object>> auditers = new ArrayList<Map<String,Object>>();
        for(FlowNextStep step:steps){
            List<FlowNextHandler> handlers = step.getHandlers();
            for(FlowNextHandler handler:handlers){
                Map<String,Object> auditer = new HashMap<String,Object>();
                String nextStepAssignerType = handler.getNextStepAssignerType();
                String nextStepAssignerCode = handler.getNextStepAssignerCode();
                auditer.put("ASSIGNER_NAME", handler.getNextStepAssignerName());
                auditer.put("ASSIGNER_CODE", handler.getNextStepAssignerCode());
                if(!(StringUtils.isNotEmpty(nextStepAssignerType)&&nextStepAssignerType.equals("2"))){
                    Map<String,Object> depInfo = departmentService.getDepInfoByUserAccount(nextStepAssignerCode);
                    if(depInfo!=null){
                        auditer.put("ASSIGNER_DEPID", depInfo.get("DEPART_ID"));
                        auditer.put("ASSIGNER_DEPNAME", depInfo.get("DEPART_NAME"));
                    }
                }
                auditers.add(auditer);
            }
        }
        return auditers;
    }
    
    /**
     * 
     * 描述 
     * @author Flex Hu
     * @created 2016年3月22日 上午11:07:01
     * @param defId
     * @param nodeName
     * @param steps
     * @param publicDocRule 公文流转方式(-1:按个人 1:按组织)
     * @return
     */
    public List<Map<String,Object>> findNextStepPublicDocs(String defId,String nodeName,
            List<FlowNextStep> steps,String publicDocRule,String exeId){
        //获取是否有没有模版
        List<Map<String,Object>> materList = this.findList(defId, nodeName,"2",exeId);
        if(materList!=null&&materList.size()>0){
            List<Map<String,Object>> userList = this.findMaterAuditers(steps);
            if(publicDocRule.equals("-1")){
                for(Map<String,Object> user:userList){
                    user.put("materList", materList);
                }
                return userList;
            }else if(publicDocRule.equals("1")){
                StringBuffer authUserCodes = new StringBuffer("");
                StringBuffer authUserNames = new StringBuffer("");
                for(int i =0;i<userList.size();i++){
                    if(i>0){
                        authUserCodes.append(",");
                        authUserNames.append(",");
                    }
                    Map<String,Object> user = userList.get(i);
                    authUserCodes.append(user.get("ASSIGNER_CODE"));
                    authUserNames.append(user.get("ASSIGNER_NAME"));
                }
                for(Map<String,Object> mater:materList){
                    mater.put("AUTH_USER_CODES", authUserCodes.toString());
                    mater.put("AUTH_USER_NAMES", authUserNames.toString());
                }
                return materList;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    
    /**
     * 
     * 描述 根据定义ID和节点名称获取公文JSON
     * @author Flex Hu
     * @created 2016年3月3日 下午8:51:14
     * @param defId
     * @param nodeName
     * @return
     */
    public String getPublicDocJson(String defId,String nodeName,String exeId){
        //获取是否有没有模版
        List<Map<String,Object>> materList = this.findList(defId, nodeName,"2",exeId);
        if(materList!=null&&materList.size()>0){
            String json = JSON.toJSONString(materList);
            return StringEscapeUtils.escapeHtml3(json);
        }else{
            return "";
        }
    }
    
    /**
     * 
     * 描述 拷贝材料配置信息
     * @author Flex Hu
     * @created 2016年3月31日 下午3:53:13
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyMaterConfigs(String targetDefId,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_MATERCONFIG(CONFIG_ID,DEF_ID,");
        sql.append("NODE_NAME,TPL_ID,CONFIG_SN) SELECT RAWTOHEX(SYS_GUID()),?,");
        sql.append("BR.NODE_NAME,BR.TPL_ID,BR.CONFIG_SN ");
        sql.append("FROM JBPM6_MATERCONFIG BR WHERE BR.DEF_ID=?");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId});
    }

    /**
     * 
     * 描述
     * @author Faker Li
     * @created 2016年4月1日 下午3:38:27
     * @param isBackfill
     * @param configIds
     */
    public void updateIsBackfill(String isBackfill, String configIds) {
        dao.updateIsBackfill(isBackfill,configIds);
    }
}
