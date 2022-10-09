/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.SqlFilter;
import net.evecom.core.util.StringUtil;
import net.evecom.core.util.SvgUtil;
import net.evecom.core.util.XmlUtil;
import net.evecom.platform.hflow.dao.FlowDefDao;
import net.evecom.platform.hflow.service.ButtonRightService;
import net.evecom.platform.hflow.service.ExecutionService;
import net.evecom.platform.hflow.service.FieldRightService;
import net.evecom.platform.hflow.service.FlowDefService;
import net.evecom.platform.hflow.service.FlowEventService;
import net.evecom.platform.hflow.service.FlowNodeService;
import net.evecom.platform.hflow.service.FlowTaskService;
import net.evecom.platform.hflow.service.FormFieldService;
import net.evecom.platform.hflow.service.HistDeployService;
import net.evecom.platform.hflow.service.MaterConfigService;
import net.evecom.platform.hflow.service.NodeAuditerService;
import net.evecom.platform.hflow.service.NodeConfigService;
import net.evecom.platform.hflow.service.QueryButtonAuthService;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 描述 流程定义操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("flowDefService")
public class FlowDefServiceImpl extends BaseServiceImpl implements FlowDefService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(FlowDefServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private FlowDefDao dao;
    /**
     * formFieldService
     */
    @Resource
    private FormFieldService formFieldService;
    /**
     * fieldRightService
     */
    @Resource
    private FieldRightService fieldRightService;
    /**
     * flowEventService
     */
    @Resource
    private FlowEventService flowEventService;
    /**
     * buttonRightService
     */
    @Resource
    private ButtonRightService buttonRightService;
    /**
     * histDeployService
     */
    @Resource
    private HistDeployService histDeployService;
    /**
     * flowNodeService
     */
    @Resource
    private FlowNodeService flowNodeService;
    /**
     * flowTaskService
     */
    @Resource
    private FlowTaskService flowTaskService;
    /**
     * executionService
     */
    @Resource
    private ExecutionService executionService;
    /**
     * nodeAuditerService
     */
    @Resource
    private NodeAuditerService nodeAuditerService;
    /**
     * nodeConfigService
     */
    @Resource
    private NodeConfigService nodeConfigService;
    /**
     * 
     */
    @Resource
    private MaterConfigService materConfigService;
    /**
     * 
     */
    @Resource
    private QueryButtonAuthService queryButtonAuthService;
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
        StringBuffer sql = new StringBuffer("select T.DEF_ID,P.TYPE_NAME,T.DEF_NAME,T.DEF_KEY,T.VERSION");
        sql.append(",F.FORM_NAME ,T.CREATE_TIME FROM JBPM6_FLOWDEF T LEFT JOIN JBPM6_FLOWTYPE P  ");
        sql.append("ON T.TYPE_ID=P.TYPE_ID LEFT JOIN JBPM6_FLOWFORM F ON T.BIND_FORMID=F.FORM_ID");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    
    /**
     * 
     * 描述 根据流程定义KEY判断是否存在
     * @author Flex Hu
     * @created 2015年8月7日 上午9:01:13
     * @param defKey
     * @return
     */
    public boolean isExists(String defKey){
        return dao.isExists(defKey);
    }
    
    /**
     * 
     * 描述 将绑定表单的ID更新为空
     * @author Flex Hu
     * @created 2015年8月10日 上午10:49:42
     * @param formId
     */
    public void updateBindFormIdToNull(String formId){
        dao.updateBindFormIdToNull(formId);
    }
    
    /**
     * 
     * 描述 转换DEFXML
     * @author Flex Hu
     * @created 2015年11月11日 上午11:09:57
     * @param defXml
     * @return
     */
    private String changeDefXml(String defXml){
        String nsp = "xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\"";
        String replaceNsp = "eveflownsp=\"\"";
        //先将标准进行替换
        //先将标准进行替换
        defXml = defXml.replaceAll("xmlns:xlink=\"http://www.w3.org/1999/xlink\"","");
        defXml = defXml.replaceAll("xmlns=\"http://www.w3.org/2000/svg\"",replaceNsp);
        Document document = XmlUtil.stringToDocument(defXml);
        Element root = document.getRootElement();
        List<Node> nodes = root.selectNodes("//text[@fill='black']");
        for(Node node:nodes){
            Element textEle = (Element) node;
            String textValue = textEle.getText().trim();
            if(StringUtils.isEmpty(textValue)){
                textEle.getParent().remove(textEle);
            }
        }
        String finalXml = root.asXML();
        //再将标准替换回来
        finalXml = finalXml.replaceAll(replaceNsp, nsp);
        return finalXml;
    }
    
    /**
     * 
     * 描述 部署流程定义
     * @author Flex Hu
     * @created 2015年8月12日 上午9:20:26
     * @param flowDef
     * @param defId
     */
    public void deployFlow(Map<String,Object> flowDef,String defId){
        String defXml = this.changeDefXml((String)flowDef.get("DEF_XML"));
        flowDef.put("DEF_XML",defXml);
        //保存或者更新流程定义值
        defId = dao.saveOrUpdate(flowDef, "JBPM6_FLOWDEF", defId);
        //获取流程定义版本号
        int flowVersion = (Integer)flowDef.get("VERSION");
        //保存历史部署信息
        histDeployService.saveOrUpdate(defId, flowDef);
        //保存节点信息
        flowNodeService.saveFlowNodes(defId,flowVersion,(String)flowDef.get("DEF_JSON"));
        //获取绑定的表单ID
        String bindFormId = (String) flowDef.get("BIND_FORMID");
        if(StringUtils.isNotEmpty(bindFormId)){
            List<Map<String,Object>> formFields = formFieldService.findByFormId(bindFormId);
            //开始解析流程的定义JSON
            String defJson = (String) flowDef.get("DEF_JSON");
            if(StringUtils.isNotEmpty(defJson)){
                Map<String,Object> map = JSON.parseObject(defJson,Map.class);
                JSONArray nodeDataArray =  (JSONArray) map.get("nodeDataArray");
                //初始化字段权限
                fieldRightService.saveOrUpdate(formFields, nodeDataArray, defId,flowVersion,flowVersion-1);
                //初始化节点缺省事件
                flowEventService.saveOrUpdate(nodeDataArray, defId,flowVersion,flowVersion-1);
                //初始化按钮缺省配置
                buttonRightService.saveOrUpdate(nodeDataArray,defId,flowVersion,flowVersion-1);
                //拷贝旧配置信息
                nodeAuditerService.saveNewFlowVersionAuditer(defId, flowVersion, flowVersion-1);
                nodeConfigService.saveNewFlowVersionConfig(defId, flowVersion, flowVersion-1);
            }
        }
    }
    
    /**
     * 
     * 描述 根据流程定义获取流程版本号
     * @author Flex Hu
     * @created 2015年8月15日 下午1:12:57
     * @param defId
     * @return
     */
    public int getFlowVersion(String defId){
        return dao.getFlowVersion(defId);
    }
    
    /**
     * 
     * 描述 获取合并后的节点名称
     * @author Flex Hu
     * @created 2015年11月11日 下午1:21:49
     * @param textEle
     * @return
     */
    private String getMergeElementText(Element textEle){
        //获取父亲节点
        Element parentEle = textEle.getParent();
        if(parentEle!=null){
            //获取孩子节点
            List<Node> textChilds =  parentEle.selectNodes("text");
            if(textChilds!=null){
                if(textChilds.size()<=1){
                    return textChilds.get(0).getText();
                }else{
                    StringBuffer finalText = new StringBuffer(textChilds.get(0).getText().trim());
                    for(int i =1;i<textChilds.size();i++){
                        String otherText = textChilds.get(i).getText().trim();
                        finalText.append(otherText);
                    }
                    return finalText.toString();
                }
            }else{
                return "";
            }
        }else{
            return textEle.getText().trim();
        }
    }
    
    /**
     * 
     * 描述 生成流程图片到磁盘
     * @author Flex Hu
     * @created 2015年12月21日 下午1:35:24
     * @param defXml :定义XML
     * @param targetPath:目标磁盘路径
     * @param changeColorNodeNames 
     */
    public void genFlowImgToDisk(String defXml,String targetPath,Map<String,Set<String>> changeColorNodeNames){
        File targetFile = new File(targetPath);
        if(!targetFile.exists()){
            String nsp = "xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\"";
            String replaceNsp = "eveflownsp=\"\"";
            defXml = defXml.replaceAll("stroke=\"transparent\"","stroke=\"none\"");
            defXml = defXml.replaceAll("fill=\"transparent\"","fill=\"none\"");
            if(changeColorNodeNames!=null&&changeColorNodeNames.size()>0){
                //先将标准进行替换
                defXml = defXml.replaceAll("xmlns:xlink=\"http://www.w3.org/1999/xlink\"","");
                defXml = defXml.replaceAll("xmlns=\"http://www.w3.org/2000/svg\"",replaceNsp);
                Document document = XmlUtil.stringToDocument(defXml);
                Element root = document.getRootElement();
                List<Node> nodes = root.selectNodes("//text[@fill='black']");
                Iterator it = changeColorNodeNames.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String,Set<String>> entry = (Map.Entry<String,Set<String>>) it.next();
                    String colorValue = entry.getKey();
                    Set<String> targetNodeNames = entry.getValue();
                    for(Node node:nodes){
                        Element textEle = (Element) node;
                        String mergetText = this.getMergeElementText(textEle);
                        if(targetNodeNames.contains(mergetText)){
                            Element parentEle = textEle.getParent();
                            Element pathEle = (Element) parentEle.selectSingleNode("path");
                            pathEle.attribute("fill").setValue(colorValue);
                        }
                    }
                }
                defXml = root.asXML();
                defXml = defXml.replaceAll(replaceNsp, nsp);
            }
            StringBuffer resultXml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
            //StringBuffer resultXml = new StringBuffer();
            resultXml.append(defXml);
            try {
                SvgUtil.convertStr2Png(resultXml.toString(), new File(targetPath));
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (TranscoderException e) {
                log.error(e.getMessage());
            }
        }
    }
    
    
    /**
     * 
     * 描述 生成流程图片到磁盘
     * @author Flex Hu
     * @created 2015年8月17日 下午12:47:41
     * @param defXml
     * @param currentNodeNames
     * @param targetPath
     */
    public void genFlowImgToDisk(String defXml,Set<String> currentNodeNames,String targetPath){
        File targetFile = new File(targetPath);
        if(!targetFile.exists()){
            String nsp = "xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\"";
            String replaceNsp = "eveflownsp=\"\"";
            defXml = defXml.replaceAll("stroke=\"transparent\"","stroke=\"none\"");
            defXml = defXml.replaceAll("fill=\"transparent\"","fill=\"none\"");
            if(currentNodeNames!=null&&currentNodeNames.size()>0){
                //先将标准进行替换
                defXml = defXml.replaceAll("xmlns:xlink=\"http://www.w3.org/1999/xlink\"","");
                defXml = defXml.replaceAll("xmlns=\"http://www.w3.org/2000/svg\"",replaceNsp);
                Document document = XmlUtil.stringToDocument(defXml);
                Element root = document.getRootElement();
                List<Node> nodes = root.selectNodes("//text[@fill='whitesmoke']");
                for(Node node:nodes){
                    Element textEle = (Element) node;
                    String mergetText = this.getMergeElementText(textEle);
                    if(currentNodeNames.contains(mergetText)){
                        Element parentEle = textEle.getParent();
                        //获取pathElement
                        Element blackPathEle = (Element) parentEle.selectSingleNode("path");
                        if(blackPathEle==null){
                            parentEle = textEle.getParent().getParent();
                            blackPathEle = (Element) parentEle.selectSingleNode("path");
                        }
                        Element redPathEle = parentEle.addElement("path");
                        String transform = blackPathEle.attributeValue("transform");
                        String d = blackPathEle.attributeValue("d");
                        redPathEle.addAttribute("transform",transform);
                        redPathEle.addAttribute("stroke-miterlimit","10");
                        redPathEle.addAttribute("stroke-linejoin","miter");
                        redPathEle.addAttribute("stroke-linecap","butt");
                        redPathEle.addAttribute("stroke-width","3");
                        redPathEle.addAttribute("stroke","red");
                        redPathEle.addAttribute("fill","none");
                        redPathEle.addAttribute("d",d);
                    }
                }
                defXml = root.asXML();
                defXml = defXml.replaceAll(replaceNsp, nsp);
            }
            StringBuffer resultXml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
            //StringBuffer resultXml = new StringBuffer();
            resultXml.append(defXml);
            try {
                SvgUtil.convertStr2Png(resultXml.toString(), new File(targetPath));
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (TranscoderException e) {
                log.error(e.getMessage());
            }
        }
    }
    
    /**
     * 
     * 描述 删除掉流程定义数据
     * @author Flex Hu
     * @created 2015年8月20日 下午1:46:57
     * @param defIds
     */
    public void deleteFlowDef(String[] defIds){
        for(String defId:defIds){
            //删除掉实例
            this.executionService.deleteByDefId(defId);
            //删除掉流程任务
            this.flowTaskService.deleteByDefId(defId);
        }
        dao.remove("JBPM6_FLOWDEF","DEF_ID",defIds);
    }
    
    /**
     * 
     * 描述 获取ID和名称列表
     * @author Flex Hu
     * @created 2015年10月26日 下午4:57:48
     * @return
     */
    public List<Map<String,Object>> findIdAndName(){
        StringBuffer sql = new StringBuffer("select T.DEF_ID,T.DEF_NAME");
        sql.append(" FROM JBPM6_FLOWDEF T ORDER BY T.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), null,null);
    }
    
    /**
     * 
     * 描述 根据流程定义ID获取流程定义名称S
     * @author Flex Hu
     * @created 2015年10月26日 下午5:20:54
     * @param defIds
     * @return
     */
    public String getDefNames(String defIds){
        return dao.getDefNames(defIds);
    }
    
    /**
     * 
     * 描述 拷贝流程定义和配置相关信息
     * @author Flex Hu
     * @created 2016年3月31日 上午10:19:58
     * @param targetDefKey
     * @param newDefName
     * @param newDefKey
     * @return
     */
    public String copyFlowDefAndConfig(String targetDefKey,String newDefName,String newDefKey){
        Map<String,Object> targetFlowDef = dao.getByJdbc("JBPM6_FLOWDEF",
                new String[]{"DEF_KEY"},new Object[]{targetDefKey});
        //获取版本号
        int targetFlowVersion = Integer.parseInt(targetFlowDef.get("VERSION").toString());
        //获取流程定义ID
        String targetFlowDefId = (String) targetFlowDef.get("DEF_ID");
        Map<String,Object> newFlowDef = targetFlowDef;
        newFlowDef.put("DEF_ID",null);
        newFlowDef.put("DEF_NAME", newDefName);
        newFlowDef.put("DEF_KEY", newDefKey);
        newFlowDef.put("CREATE_TIME",DateTimeUtil.getStrOfDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
        String newDefFlowId = dao.saveOrUpdate(newFlowDef, "JBPM6_FLOWDEF",null);
        //拷贝按钮权限数据
        buttonRightService.copyButtonRights(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝流程事件数据
        flowEventService.copyEvents(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝字段权限数据
        fieldRightService.copyFieldRights(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝流程字典数据
        flowNodeService.copyFlowNodes(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝材料配置数据
        materConfigService.copyMaterConfigs(targetFlowDefId, newDefFlowId);
        //拷贝环节审核人
        nodeAuditerService.copyNodeAuditers(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝节点数据
        nodeConfigService.copyNodeConfigs(targetFlowDefId, targetFlowVersion, newDefFlowId);
        //拷贝查询按钮权限数据
        queryButtonAuthService.copyQueryButtons(targetFlowDefId,targetFlowVersion,newDefFlowId);
        return newDefFlowId;
    }

    
    /**
     * 
     * 描述 获取ID和名称列表(部门服务流程)
     * @author Flex Hu
     * @created 2015年10月26日 下午4:57:48
     * @return
     */
    public List<Map<String,Object>> findIdAndNameForDept(){
        StringBuffer sql = new StringBuffer("select T.DEF_ID,T.DEF_NAME,F.TYPE_NAME ");
        sql.append("FROM JBPM6_FLOWDEF T ");
        sql.append("LEFT JOIN JBPM6_FLOWTYPE F ON F.TYPE_ID=T.TYPE_ID ");
        sql.append("WHERE F.TYPE_NAME IN ('部门服务','不动产管理','医保全流程','不动产全流程','社保全流程','工程建设项目','商事全程网办','一件事流程') ");
        sql.append("ORDER BY T.CREATE_TIME DESC");
        return dao.findBySql(sql.toString(), null,null);
    }
}
