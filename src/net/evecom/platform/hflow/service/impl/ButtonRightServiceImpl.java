/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.AppUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hflow.dao.ButtonRightDao;
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FlowButtonService;
import net.evecom.platform.hflow.util.Jbpm6Constants;
import net.evecom.platform.wsbs.service.ServiceItemService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述 按钮权限操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("buttonRightService")
public class ButtonRightServiceImpl extends BaseServiceImpl implements ButtonRightService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(ButtonRightServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private ButtonRightDao dao;
    /**
     * flowButtonService
     */
    @Resource
    private FlowButtonService flowButtonService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * 
     */
    @Resource
    private ServiceItemService serviceItemService;
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
     * 描述 保存或者更新节点的事件配置
     * @author Flex Hu
     * @created 2015年8月12日 下午4:40:30
     * @param nodeDataArray
     * @param defId
     */
    public void saveOrUpdate(JSONArray nodeDataArray,String defId,int flowVersion,int oldFlowVersion){
        List<Map<String,Object>> buttons = flowButtonService.findList();
        for(int i =0;i<nodeDataArray.size();i++){
            Map<String,Object> nodeData = (Map<String, Object>) nodeDataArray.get(i);
            //获取节点名称
            String nodeName = (String) nodeData.get("text");
            //获取节点类型
            String nodeType = (String) nodeData.get("nodeType");
            if(nodeType.equals(Jbpm6Constants.START_NODE)||nodeType.equals(Jbpm6Constants.TASK_NODE)){
                //遍历按钮
                for(Map<String,Object> button:buttons){
                    String buttonKey = (String) button.get("BUTTON_KEY");
                    //判断是否存在记录
                    boolean isExist = dao.isExist(defId, nodeName, buttonKey,flowVersion);
                    if(!isExist){
                        //进行缺省按钮配置的保存
                        Map<String,Object> buttonRight = new HashMap<String,Object>();
                        buttonRight.put("DEF_ID", defId);
                        buttonRight.put("NODE_NAME", nodeName);
                        buttonRight.put("BUTTON_KEY", buttonKey);
                        buttonRight.put("DEF_VERSION", flowVersion);
                        //如果是提交或者暂存按钮,那么默认进行授权
                        if(buttonKey.equals(Jbpm6Constants.FLOW_BTN_SUBMIT_KEY)
                                ||buttonKey.equals(Jbpm6Constants.FLOW_BTN_TEMPSAVE_KEY)||
                                buttonKey.equals(Jbpm6Constants.FLOW_BTN_BACK_KEY)){
                            if(nodeType.equals(Jbpm6Constants.START_NODE)&&
                                    buttonKey.equals(Jbpm6Constants.FLOW_BTN_BACK_KEY)){
                                buttonRight.put("IS_AUTH",Jbpm6Constants.FLOW_BTN_AUTH_NO);
                            }else{
                                buttonRight.put("IS_AUTH",Jbpm6Constants.FLOW_BTN_AUTH_YES);
                            }
                        }else{
                            buttonRight.put("IS_AUTH",Jbpm6Constants.FLOW_BTN_AUTH_NO);
                        }
                        //判断是否存在旧版本的配置信息
                        Map<String,Object> oldButtonRight = dao.getByJdbc("JBPM6_BUTTONRIGHT", 
                                new String[]{"DEF_ID","NODE_NAME","BUTTON_KEY","DEF_VERSION"},
                                new Object[]{defId,nodeName,buttonKey,oldFlowVersion});
                        if(oldButtonRight!=null){
                            buttonRight.put("IS_AUTH",oldButtonRight.get("IS_AUTH"));
                            buttonRight.put("AUTH_INTERFACECODE",oldButtonRight.get("AUTH_INTERFACECODE"));
                        }
                        dao.saveOrUpdate(buttonRight, "JBPM6_BUTTONRIGHT",null);
                    }
                }
                
            }
        }
    }
    
    
    /**
     * 
     * 描述 根据按钮KEY删除掉权限配置记录
     * @author Flex Hu
     * @created 2015年8月13日 下午3:52:05
     * @param buttonKey
     */
    public void deleteByButtonKey(String buttonKey){
        dao.deleteByButtonKey(buttonKey);
    }
    
    /**
     * 
     * 描述 根据流程定义ID和节点名称获取权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:10:32
     * @param nodeName
     * @param defId
     * @return
     */
    public List<Map<String,Object>> findList(String nodeName,String defId,boolean filterAuth,int flowVersion){
        List<Map<String,Object>> list = dao.findList(nodeName, defId, filterAuth, flowVersion);
        return list;
    }
    
    /**
     * 
     * 描述 面向省网办发起流程个性化判断权限
     * @author Flex Hu
     * @created 2016年1月25日 下午4:04:17
     * @param flowVars
     * @return
     */
    public boolean isAuthForProvinceSite(Map<String,Object> flowVars){
        String exeId = (String) flowVars.get("exeId");
        if(StringUtils.isNotEmpty(exeId)){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            String SQFS = (String) flowExe.get("SQFS");
            if(SQFS.equals("3")){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
        
    }
    
    /**
     * 
     * 描述 判断是否有挂起按钮权限
     * @author Flex Hu
     * @created 2016年3月18日 下午4:37:25
     * @param flowVars
     * @return
     */
    public boolean isAuthForHandUp(Map<String,Object> flowVars){
        String exeId = (String) flowVars.get("exeId");
        if(StringUtils.isNotEmpty(exeId)){
            Map<String,Object> flowExe = executionService.getByJdbc("JBPM6_EXECUTION",
                    new String[]{"EXE_ID"},new Object[]{exeId});
            String itemCode = (String) flowExe.get("ITEM_CODE");
            return serviceItemService.isForHandUp(itemCode);
        }else{
            return false;
        }
    }
    
    /**
     * 
     * 描述 获取个性化权限之后的按钮列表
     * @author Flex Hu
     * @created 2016年1月25日 下午4:18:50
     * @param oldButtonRightList
     * @return
     */
    public List<Map<String,Object>> getFilterAuthList(List<Map<String,Object>> oldButtonRightList
            ,Map<String,Object> flowVars){
        List<Map<String,Object>> finalButtonRights = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> oldButtonRight:oldButtonRightList){
            //获取权限控制代码
            String AUTH_INTERFACECODE = (String) oldButtonRight.get("AUTH_INTERFACECODE");
            if(StringUtils.isNotEmpty(AUTH_INTERFACECODE)){
                String[] beanMethods = AUTH_INTERFACECODE.split("[.]");
                String beanId = beanMethods[0];
                String method = beanMethods[1];
                Object serviceBean = AppUtil.getBean(beanId);
                boolean authResult = false;
                if (serviceBean != null) {
                    try {
                        Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
                                new Class[] {Map.class});
                        authResult = (Boolean) invokeMethod.invoke(serviceBean,
                                new Object[] {flowVars});
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                if(authResult){
                    finalButtonRights.add(oldButtonRight);
                }
            }else{
                boolean flag = true;
                String BUTTON_KEY = oldButtonRight.get("BUTTON_KEY") == null ? "" : 
                    oldButtonRight.get("BUTTON_KEY").toString();
                if ("HandUpFlow".equals(BUTTON_KEY)) {
                    String exeId = flowVars.get("exeId") == null ? "" : flowVars.get("exeId").toString();
                    StringBuffer sql = new StringBuffer("SELECT L.RECORD_ID FROM T_WSBS_SERVICEITEM_LINK L  ");
                    sql.append("LEFT JOIN T_WSBS_SERVICEITEM T ON T.ITEM_ID=L.ITEM_ID ");
                    sql.append("LEFT JOIN JBPM6_EXECUTION E ON T.ITEM_CODE=E.ITEM_CODE ");
                    sql.append(" WHERE E.EXE_ID=?");
                    List<Map<String, Object>> list = dao.findBySql(sql.toString(), new Object[] { exeId }, null);
                    if (list.isEmpty() || null == list) {
                        flag = false;
                    }
                }
                if (flag) {
                    finalButtonRights.add(oldButtonRight);
                }
            }
        }
        return finalButtonRights;
    }
    
    /**
     * 
     * 描述 保存按钮权限配置
     * @author Flex Hu
     * @created 2015年8月13日 下午4:45:53
     * @param defId
     * @param nodeName
     * @param rightJson
     */
    public void saveButtonRight(String defId,String nodeName,String rightJson,int flowVersion){
        //先清除掉数据
        dao.delete(defId, nodeName,flowVersion);
        List<Map> rightList = JSON.parseArray(rightJson, Map.class);
        for(Map<String,Object> right:rightList){
            right.put("DEF_ID", defId);
            right.put("NODE_NAME", nodeName);
            right.put("DEF_VERSION", flowVersion);
            dao.saveOrUpdate(right, "JBPM6_BUTTONRIGHT",null);
        }
    }
    
    /**
     * 
     * 描述 删除数据
     * @author Flex Hu
     * @created 2015年8月27日 上午10:51:30
     * @param defId
     * @param flowVersion
     */
    public void deleteByDefIdAndVersion(String defId,int flowVersion){
        dao.deleteByDefIdAndVersion(defId, flowVersion);
    }
    
    /**
     * 
     * 描述 保存新配置的按钮权限
     * @author Flex Hu
     * @created 2015年12月3日 上午9:50:49
     * @param defId
     * @param nodeName
     * @param flowVersion
     */
    public void saveNewButtonRight(String defId,String nodeName,int flowVersion){
        List<Map<String,Object>> flowButtons = flowButtonService.findNewConfigButtons(defId, nodeName, flowVersion);
        for(Map<String,Object> flowButton:flowButtons){
            Map<String,Object> buttonRight = new HashMap<String,Object>();
            buttonRight.put("DEF_ID", defId);
            buttonRight.put("NODE_NAME", nodeName);
            buttonRight.put("BUTTON_KEY", flowButton.get("BUTTON_KEY"));
            buttonRight.put("IS_AUTH",-1);
            buttonRight.put("DEF_VERSION",flowVersion);
            dao.saveOrUpdate(buttonRight, "JBPM6_BUTTONRIGHT",null);
        }
    }
    
    /**
     * 
     * 描述 拷贝按钮权限数据
     * @author Flex Hu
     * @created 2016年3月31日 上午10:58:16
     * @param targetDefId
     * @param targetFlowVersion
     * @param newDefId
     */
    public void copyButtonRights(String targetDefId,int targetFlowVersion,String newDefId){
        StringBuffer sql = new StringBuffer("INSERT INTO JBPM6_BUTTONRIGHT(");
        sql.append("RIGHT_ID,DEF_ID,NODE_NAME,BUTTON_KEY,IS_AUTH,");
        sql.append("DEF_VERSION,AUTH_INTERFACECODE)");
        sql.append("SELECT RAWTOHEX(SYS_GUID()),?,BR.NODE_NAME,BR.BUTTON_KEY");
        sql.append(",BR.IS_AUTH,BR.DEF_VERSION,BR.AUTH_INTERFACECODE");
        sql.append(" FROM JBPM6_BUTTONRIGHT BR WHERE BR.DEF_ID=? ");
        sql.append(" AND BR.DEF_VERSION=? ");
        dao.executeSql(sql.toString(), new Object[]{newDefId,targetDefId,targetFlowVersion});
    }
}
