/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.developer.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.TableColumn;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.FileUtil;
import net.evecom.core.util.FreeMarkerUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.developer.dao.ControlConfigDao;
import net.evecom.platform.developer.model.CodeTableInfo;
import net.evecom.platform.developer.service.CodeMissionService;
import net.evecom.platform.developer.service.CodeProjectService;
import net.evecom.platform.developer.service.ControlConfigService;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

/**
 * 描述 配置控件操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("controlConfigService")
public class ControlConfigServiceImpl extends BaseServiceImpl implements ControlConfigService {
    /**
     * 所引入的dao
     */
    @Resource
    private ControlConfigDao dao;
    /**
     * codeMissionService
     */
    @Resource
    private CodeMissionService codeMissionService;
    /**
     * codeProjectService
     */
    @Resource
    private CodeProjectService codeProjectService;
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
     * 描述 保存布局控件
     * @author Flex Hu
     * @created 2014年9月18日 下午2:48:00
     * @param config
     */
    public void saveLayoutControl(Map<String,Object> config){
        String parentId = (String) config.get("PARENT_ID");
        String layoutType = (String) config.get("LAYOUT_TYPE");
        if(layoutType.equals("1")){
            config.put("CONTROL_NAME","中央布局");
            this.saveOrUpdateTreeData(parentId, config,"T_MSJW_DEVELOPER_CONTROLCONFIG",null);
        }
    }
    
    /**
     * 
     * 描述 保存datagrid控件
     * @author Flex Hu
     * @created 2014年9月18日 下午2:48:00
     * @param config
     */
    public void saveDataGridControl(Map<String,Object> config){
        String parentId = (String) config.get("PARENT_ID");
        config.put("CONTROL_NAME","普通表格");
        this.saveOrUpdateTreeData(parentId, config,"T_MSJW_DEVELOPER_CONTROLCONFIG",null);
    }
    
    /**
     * 
     * 描述 根据任务ID判断是否存在控件
     * @author Flex Hu
     * @created 2014年9月18日 上午11:48:36
     * @param missionId
     * @return
     */
    public boolean isExists(String missionId){
        return dao.isExists(missionId);
    }
    /**
     * 
     * 描述 根据父亲id获取列表数据
     * @author Flex Hu
     * @created 2014年9月18日 下午2:42:39
     * @param parentId
     * @return
     */
    public List<Map<String,Object>> findList(String parentId,String missionId){
        StringBuffer sql = new StringBuffer("select * from T_MSJW_DEVELOPER_CONTROLCONFIG ")
                .append("WHERE MISSION_ID=? AND PARENT_ID=? ORDER BY TREE_SN ASC ");
        return dao.findBySql(sql.toString(), new Object[]{missionId,parentId},null);
    }
    /**
     * 
     * 描述 获取数据表格代码
     * @author Flex Hu
     * @created 2014年9月25日 下午4:48:30
     * @param dataGridConfig
     * @param projectPath
     * @return
     */
    public String getDataGridCode(Map<String,Object> dataGridConfig,String projectPath,String isWindow){
        String configId = (String) dataGridConfig.get("CONFIG_ID");
        String BIND_ENTITYNAME = (String) dataGridConfig.get("BIND_ENTITYNAME");
        String BIND_TABLENAME = (String) dataGridConfig.get("BIND_TABLENAME");
        String BIND_CHINESE = (String) dataGridConfig.get("BIND_CHINESE");
        String primaryKeyName = (String) this.getPrimaryKeyName(BIND_TABLENAME).get(0);
        //模版路径
        String datagridftl =  projectPath + "src/codegen/DatagridFtl.jsp";
        String datagridftlString = FileUtil.getContentOfFile(datagridftl);
        Map<String,Object> datagridDataMap = new HashMap<String,Object>();
        datagridDataMap.put("MainClassName",BIND_ENTITYNAME);
        datagridDataMap.put("MainChineseName",BIND_CHINESE);
        datagridDataMap.put("MainPrimaryKey",primaryKeyName);
        datagridDataMap.put("CONFIG_ID", configId);
        //获取配置的SQL语句
        String gridSql = (String) dataGridConfig.get("SQL_CONTENT");
        List<TableColumn> gridColumns = dao.findQueryResultColsBySql(gridSql);
        datagridDataMap.put("gridColumns",gridColumns);
        //获取配置的查询条件
        List<Map<String,Object>> queryConfigs = dao.findByParentId(configId);
        String queryConfigHtml = this.genQueryConfigHtml(queryConfigs, BIND_ENTITYNAME);
        datagridDataMap.put("QueryConfigContent", queryConfigHtml);
        //开始设置所绑定的时间控件代码
        String bindDateControlContent = this.genBindTimeCodes(BIND_ENTITYNAME, queryConfigs);
        datagridDataMap.put("bindDateControlContent", bindDateControlContent);
        //结束设置所绑定的时间控件代码
        //开始设置下拉树的代码
        this.setComboTreeFieldContent(queryConfigs, datagridDataMap,isWindow);
        //结束设置下拉树的代码
        String ftlResult = FreeMarkerUtil.getResultString(datagridftlString, datagridDataMap);
        return ftlResult;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:08:37
     * @param showControl
     * @param notBlank
     * @param formLayoutCode
     * @param fieldValue
     * @param showControlName
     */
    public void contructTextControl(Map<String, Object> showControl,String notBlank,
            StringBuffer formLayoutCode,StringBuffer fieldValue,String showControlName){
        String maxLength = (String) showControl.get("MAX_LENGTH");
        String validateRule = (String) showControl.get("VALIDATE_RULE");
        StringBuffer classValue = new StringBuffer("eve-input");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            classValue.append(" validate[required]");
        }
        if(StringUtils.isNotEmpty(validateRule)){
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                classValue.append(",").append(validateRule);
            }else{
                classValue.append(" ").append(validateRule);
            }
        }
        formLayoutCode.append("<input type=\"text\" style=\"width:150px;float:left;\"").append(" maxlength=\"")
                .append(maxLength).append("\" ").append(" class=\"").append(classValue).append("\" ")
                .append(" value=\"").append(fieldValue).append("\" ");
        if(StringUtils.isNotEmpty(validateRule)&&validateRule.contains("ajax[ajaxVerifyValueExist]")){
            formLayoutCode.append(" id=\"").append(showControlName).append("\" ");
        }
        formLayoutCode.append(" name=\"").append(showControlName).append("\" />");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:10:06
     * @param showControl
     * @param notBlank
     * @param formLayoutCode
     * @param fieldValue
     * @param showControlName
     */
    public void contructComboControl(Map<String, Object> showControl,String notBlank,
            StringBuffer formLayoutCode,StringBuffer fieldValue,String showControlName){
        String comboboxUrlValue = (String) showControl.get("COMBOBOX_URLVALUE");
        String url = "";
        String valueField = (String) showControl.get("COMBOBOX_VALUEFIELD");
        String textField = (String) showControl.get("COMBOBOX_TEXTFIELD");
        if(comboboxUrlValue.equals("1")){
            String typeCode = (String) showControl.get("COMBOBOX_TYPECODE");
            url = "dictionaryController.do?load&typeCode="+typeCode;
        }else{
            url = (String) showControl.get("COMBOBOX_DEFURL");
        }
        StringBuffer dataoptions = new StringBuffer("");
        dataoptions.append("url:'").append(url).append("',")
        .append("editable:false,method: 'post',")
        .append("valueField:'").append(valueField).append("',")
        .append("textField:'").append(textField).append("',")
        .append("panelWidth: 150,panelHeight: 'auto'");
        StringBuffer classValue = new StringBuffer("easyui-combobox");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            classValue.append(" validate[required]");
        }
        formLayoutCode.append("<input style=\"width:150px;height:25px;float:left;\"").append(" class=\"")
                .append(classValue).append("\" ").append(" value=\"").append(fieldValue).append("\" ")
                .append(" name=\"").append(showControlName).append("\" ").append("data-options=\"")
                .append(dataoptions).append("\" />");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:11:08
     * @param showControl
     * @param notBlank
     * @param formLayoutCode
     * @param fieldValue
     * @param showControlName
     */
    public void contructDateControl(Map<String, Object> showControl,String notBlank,
            StringBuffer formLayoutCode,StringBuffer fieldValue,String showControlName){
        String timeFormat = (String) showControl.get("TIME_FORMAT");
        StringBuffer onclick = new StringBuffer("");
        if(timeFormat.equals("YYYY-MM-DD")){
            onclick.append("laydate({istime: false, format: 'YYYY-MM-DD'})");
        }else{
            onclick.append("laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})");
        }
        StringBuffer classValue = new StringBuffer("laydate-icon");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            classValue.append(" validate[required]");
        }
        formLayoutCode.append("<input type=\"text\" style=\"width:130px;float:left;\"").append(" class=\"")
                .append(classValue).append("\" ").append(" value=\"").append(fieldValue).append("\" ")
                .append(" onclick=\"").append(onclick).append("\" ").append(" name=\"").append(showControlName)
                .append("\" />");
        if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
            formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:12:11
     * @param showControl
     * @param notBlank
     * @param formLayoutCode
     * @param fieldValue
     * @param showControlName
     */
    public void contructEwebControl(Map<String, Object> showControl,String notBlank,
            StringBuffer formLayoutCode,StringBuffer fieldValue,String showControlName){
        formLayoutCode.append("<input type=\"hidden\" ").append(" name=\"").append(showControlName).append("\" ")
        .append(" value=\"").append(fieldValue).append("\" >\n")
        .append("<IFRAME ID=\"eWebEditor\" SRC=\"plug-in/ewebeditor/ewebeditor.htm?").append("id=")
        .append(showControlName)
        .append("&style=mini500\" FRAMEBORDER=\"0\" SCROLLING=\"no\"" + " WIDTH=\"700\" HEIGHT=\"350\">")
        .append("</IFRAME>\n");
    }
    /**
     * 
     * 描述获取表单控件HTML内容
     * @author Flex Hu
     * @created 2014年9月28日 下午2:48:20
     * @param showControl
     * @param formLayoutCode
     * @return
     */
    public void getFormControlContent(Map<String, Object> showControl, StringBuffer formLayoutCode, 
            String mainClassName) {
        String controlId = (String) showControl.get("CONFIG_ID");
        if(controlId.equals("-1")){
            return;
        }
        String showControlType = (String) showControl.get("CONTROL_TYPE");
        String showControlLabel = (String) showControl.get("LABLE_VALUE");
        String showControlName = (String) showControl.get("WIDGET_NAME");
        String className = StringUtil.convertFirstLetterToLower(mainClassName);
        String notBlank = (String) showControl.get("NOT_BLANK");
        StringBuffer fieldValue = new StringBuffer("${");
        fieldValue.append(className).append(".").append(showControlName)
        .append("}");
        formLayoutCode.append("<span style=\"width: 100px;float:left;text-align:right;\">").append(showControlLabel)
                .append("：</span>\n");
        if(showControlType.equals(ControlConfigService.CONTROL_TYPE_TEXT)){
            this.contructTextControl(showControl, notBlank, formLayoutCode, fieldValue, showControlName);
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_NUMBER)){
            String maxLength = (String) showControl.get("MAX_LENGTH");
            String precision = (String) showControl.get("PRECISION_VALUE");
            StringBuffer classValue = new StringBuffer("easyui-numberbox");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                classValue.append(" validate[required]");
            }
            formLayoutCode.append("<input type=\"text\" style=\"width:150px;float:left;\"").append(" maxlength=\"")
                    .append(maxLength).append("\" ").append(" class=\"").append(classValue).append("\" ")
                    .append(" value=\"").append(fieldValue).append("\" ").append(" precision=\"").append(precision)
                    .append("\" ").append(" name=\"").append(showControlName).append("\" />");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_DATE)){
            this.contructDateControl(showControl, notBlank, formLayoutCode, fieldValue, showControlName);
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_COMBOBOX)){
            this.contructComboControl(showControl, notBlank, formLayoutCode, fieldValue, showControlName);
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_TEXTAREA)){
            StringBuffer classValue = new StringBuffer("eve-textarea");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                classValue.append(" validate[required]");
            }
            formLayoutCode.append("<textarea rows=\"5\" cols=\"5\" class=\"").append(classValue)
                    .append("\" style=\"width: 400px\" ").append(" name=\"").append(showControlName).append("\" >")
                    .append(fieldValue).append("</textarea>");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_RADIO)){
            String width = (String) showControl.get("CONTROL_WIDTH");
            String typeCode = (String) showControl.get("COMBOBOX_TYPECODE");
            String defaultValue = (String) showControl.get("DEFAULT_VALUE");
            formLayoutCode.append("<eve:radiogroup typecode=\"").append(typeCode).append("\" width=\"").append(width)
                    .append("\" fieldname=\"").append(showControlName).append("\" ").append("defaultvalue=\"")
                    .append(defaultValue).append("\" ").append(" value=\"").append(fieldValue).append("\" >")
                    .append("</eve:radiogroup>");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_CHECKBOX)){
            String width = (String) showControl.get("CONTROL_WIDTH");
            String typeCode = (String) showControl.get("COMBOBOX_TYPECODE");
            String defaultValue = (String) showControl.get("DEFAULT_VALUE");
            formLayoutCode.append("<eve:checkboxgroup typecode=\"").append(typeCode).append("\" width=\"")
                    .append(width).append("\" fieldname=\"").append(showControlName).append("\" ")
                    .append("defaultvalue=\"").append(defaultValue).append("\" ").append(" value=\"")
                    .append(fieldValue).append("\" >").append("</eve:checkboxgroup>");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_CMOBOTREE)){
            StringBuffer classValue = new StringBuffer("eve-input");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                classValue.append(" validate[required]");
            }
            formLayoutCode.append("<input type=\"text\" style=\"width:150px;float:left;\"")
                    .append(" readonly=\"readonly\" ").append(" value=\"").append(fieldValue).append("\" ")
                    .append(" class=\"").append(classValue.toString()).append("\" ").append(" name=\"")
                    .append(showControlName).append("\" ").append(" id=\"").append(showControlName).append("\" ")
                    .append(" onclick=\"showSelect").append(showControlName).append("Tree();\" >");
            if(StringUtils.isNotEmpty(notBlank)&&notBlank.equals("1")){
                formLayoutCode.append("<font class=\"dddl_platform_html_requiredFlag\">*</font>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_UPLOAD)){
            String uploadType = (String) showControl.get("UPLOAD_TYPE");
            if(uploadType.equals("1")){
                formLayoutCode.append("<div style=\"float:left;width:150px; overflow:hidden; text-align:center;\">")
                        .append("<img id=\"").append(showControlName).append("_IMG\" ")
                        .append("src=\"webpage/common/images/nopic.jpg\" height=\"107px\" width=\"125px\">")
                        .append("<a id=\"").append(showControlName).append("_BTN\" ></a>").append("</div>");
            }else if(uploadType.equals("2")){
                formLayoutCode.append("<a id=\"").append(showControlName).append("_BTN\" ></a>")
                .append("<div id=\"").append(showControlName).append("_DIV\" ></div>");
            }
        }else if(showControlType.equals(ControlConfigService.CONTROL_TYPE_EWEB)){
            this.contructEwebControl(showControl, notBlank, formLayoutCode, fieldValue, showControlName);
        }
    }
    /**
     * 
     * 描述 判断是否是tab组件
     * @author Flex Hu
     * @created 2014年10月4日 上午10:15:05
     * @param topList
     * @return
     */
    public boolean isTabs(List<Map<String,Object>> topList){
        boolean isTab = false;
        for(Map<String,Object> top:topList){
            String controlType = (String) top.get("CONTROL_TYPE");
            if(controlType.equals(ControlConfigService.CONTROL_TYPE_TAB)){
                isTab = true;
                break;
            }
        }
        return isTab;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:15:34
     * @param controlId
     * @param top
     * @param missionId
     * @param mainTableInfo
     * @param hiddenContent
     * @param IS_WINDOW
     * @param ftlRoot
     * @param targetCode
     */
    public void contructFormLayout(String controlId,Map<String,Object> top,String missionId,
            CodeTableInfo mainTableInfo,StringBuffer hiddenContent,String IS_WINDOW,Map<String,Object> ftlRoot
            ,StringBuffer targetCode){
        //获取布局列数量
        int formColumnNum = Integer.parseInt(top.get("FORM_COLUMNNUM").toString());
        //获取布局标题
        String formLayoutTitle = (String) top.get("FORM_LAYOUTTITLE");
        StringBuffer formLayoutCode = new StringBuffer("<table style=\"width:100%;border-collapse:collapse;\"");
        formLayoutCode.append("class=\"dddl-contentBorder dddl_table\">\n");
        if(StringUtils.isNotEmpty(formLayoutTitle)){
            formLayoutCode.append("<tr style=\"height:29px;\">").append("<td colspan=\"").append(formColumnNum)
                    .append("\" class=\"dddl-legend\" ").append(" style=\"font-weight:bold;\" >")
                    .append(formLayoutTitle).append("</td></tr>");
        }
        List<Map<String,Object>> children = this.findList(controlId, missionId);
        //开始设置隐藏域的值内容
        this.setHiddenFieldContent(children, hiddenContent, mainTableInfo.getClassName());
        //结束设置隐藏域的值内容
        //开始设置树形下拉框的内容
        this.setComboTreeFieldContent(children, ftlRoot,IS_WINDOW);
        //开始设置上传控件的内容
        this.setUploadControlContent(children, ftlRoot);
        //获取非隐藏域控件
        List<Map<String,Object>> showControls = this.getNotHiddenControl(children,formColumnNum);
        if(formColumnNum==1){
            for(Map<String,Object> showControl:showControls){
                formLayoutCode.append("<tr><td>");
                this.getFormControlContent(showControl, formLayoutCode,mainTableInfo.getClassName());
                formLayoutCode.append("</td></tr>\n");
            }
        }else if(formColumnNum==2){
            for(int i = 0;i<showControls.size();i++){
                Map<String,Object> showControl = showControls.get(i);
                if(i%2==0){
                    formLayoutCode.append("<tr>");
                }
                formLayoutCode.append("<td>");
                this.getFormControlContent(showControl, formLayoutCode,mainTableInfo.getClassName());
                formLayoutCode.append("</td>");
                if(i%2!=0){
                    formLayoutCode.append("</tr>\n");
                }
            }
        }else if(formColumnNum==3){
            for(int i = 0;i<showControls.size();i++){
                Map<String,Object> showControl = showControls.get(i);
                if(i%3==0){
                    formLayoutCode.append("<tr>");
                }
                formLayoutCode.append("<td>");
                this.getFormControlContent(showControl, formLayoutCode,mainTableInfo.getClassName());
                formLayoutCode.append("</td>");
                if(i%3==2){
                    formLayoutCode.append("</tr>\n");
                }
            }
        }
        formLayoutCode.append("</table>\n");
        targetCode.append(formLayoutCode);
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:16:56
     * @param controlId
     * @param top
     * @param missionId
     * @param mainTableInfo
     * @param hiddenContent
     * @param IS_WINDOW
     * @param ftlRoot
     * @param targetCode
     */
    public void contructUploadControl(String controlId,Map<String,Object> top,String missionId,
            CodeTableInfo mainTableInfo,StringBuffer hiddenContent,String IS_WINDOW,Map<String,Object> ftlRoot
            ,StringBuffer targetCode){
        String uploadTableId= (String) top.get("UPLOAD_TABLEID");
        String uploadBtnId = uploadTableId+"_BTN";
        String bindTableName = (String) top.get("BIND_TABLENAME");
        String recordIdName = "${"+StringUtil.convertFirstLetterToLower(mainTableInfo.getClassName())
                +"."+mainTableInfo.getPrimaryKeyName()+"}";
        String limitFileSize= (String) top.get("UPLOAD_LIMITFILESIZE");
        String fileTypes= (String) top.get("UPLOAD_LIMITTYPES");
        String uploadPath = (String) top.get("UPLOAD_PATH");
        Map<String,Object> tableRoot = new HashMap<String,Object>();
        tableRoot.put("uploadTableId", uploadTableId);
        tableRoot.put("uploadTableBtnId", uploadBtnId);
        tableRoot.put("bindTableName", bindTableName);
        tableRoot.put("recordIdName", recordIdName);
        tableRoot.put("limitFileSize", limitFileSize);
        String uploadTableFtlContent = FreeMarkerUtil.getFtlContent("UploadTableFtl.jsp");
        String uploadTableHtml = FreeMarkerUtil.getResultString(uploadTableFtlContent, tableRoot);
        targetCode.append(uploadTableHtml).append("\n");
        
        StringBuffer initUploadContent = new StringBuffer("");
        StringBuffer uploadTableFnContent= new StringBuffer("");
        initUploadContent.append("AppUtil.initUploadControl({").append("file_types:\"").append(fileTypes)
                .append("\",").append("file_upload_limit:").append(limitFileSize).append(",")
                .append("file_queue_limit:").append(limitFileSize).append(",").append("uploadPath:\"")
                .append(uploadPath).append("\",").append("busTableName:\"").append(bindTableName).append("\",")
                .append("uploadUserId:$(\"input[name='CURLOGIN_USERID']\").val(),").append("uploadButtonId:\"")
                .append(uploadBtnId).append("\",").append("uploadTableId:\"").append(uploadBtnId).append("\",")
                .append("limit_size:\"").append(limitFileSize).append("\"").append("});\n");
        Map<String,Object> fnRoot = new HashMap<String,Object>();
        fnRoot.put("uploadTableId", uploadTableId);
        fnRoot.put("uploadTableBtnId", uploadBtnId);
        String uploadFtlContent = FreeMarkerUtil.getFtlContent("UploadTableFn.js");
        String uploadFnContent = FreeMarkerUtil.getResultString(uploadFtlContent,fnRoot);
        uploadTableFnContent.append(uploadFnContent).append("\n");
        ftlRoot.put("initUploadContent", initUploadContent.toString());
        ftlRoot.put("uploadTableFnContent", uploadTableFnContent.toString());
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:18:24
     * @param controlId
     * @param top
     * @param missionId
     * @param mainTableInfo
     * @param hiddenContent
     * @param IS_WINDOW
     * @param ftlRoot
     * @param targetCode
     */
    public String contructTreeListControl(String controlId,Map<String,Object> top,String missionId,
            CodeTableInfo mainTableInfo,StringBuffer hiddenContent,String IS_WINDOW,Map<String,Object> ftlRoot
            ,StringBuffer targetCode){
        Map<String,Object> treeListRoot = new HashMap<String,Object>();
        String TreeEntityName = (String) top.get("TREE_ENTITYNAME");
        String TreeTableName = (String) top.get("TREE_BINDTABLENAME");
        String TreePrimaryKeyName = (String) dao.getPrimaryKeyName(TreeTableName).get(0);
        String TreeChinese = (String) top.get("TREE_CHINESE");
        String TreeIdAndName= (String) top.get("TREE_IDANDNAME");
        String TreeTargetCols= (String) top.get("TREE_TARGETCOLS");
        String GridEntityName = (String) top.get("BIND_ENTITYNAME");
        String GridChinese = (String) top.get("BIND_CHINESE");
        String GridTableName = (String) top.get("BIND_TABLENAME");
        String GridPrimaryName= (String) dao.getPrimaryKeyName(GridTableName).get(0);
        treeListRoot.put("TreeEntityName", TreeEntityName);
        treeListRoot.put("TreeTableName", TreeTableName);
        treeListRoot.put("TreePrimaryKeyName", TreePrimaryKeyName);
        treeListRoot.put("TreeChinese", TreeChinese);
        treeListRoot.put("TreeIdAndName", TreeIdAndName);
        treeListRoot.put("TreeTargetCols", TreeTargetCols);
        treeListRoot.put("GridEntityName", GridEntityName);
        treeListRoot.put("GridChinese", GridChinese);
        treeListRoot.put("GridPrimaryName", GridPrimaryName);
        //获取配置的SQL语句
        String gridSql = (String) top.get("SQL_CONTENT");
        List<TableColumn> gridColumns = dao.findQueryResultColsBySql(gridSql);
        treeListRoot.put("gridColumns",gridColumns);
        //获取配置的查询条件
        List<Map<String,Object>> queryConfigs = dao.findByParentId(controlId);
        String queryConfigHtml = this.genQueryConfigHtml(queryConfigs, GridEntityName);
        treeListRoot.put("QueryConfigContent", queryConfigHtml);
        //开始设置所绑定的时间控件代码
        String bindDateControlContent = this.genBindTimeCodes(GridEntityName, queryConfigs);
        treeListRoot.put("bindDateControlContent", bindDateControlContent);
        //结束设置所绑定的时间控件代码
        //开始设置下拉树的代码
        this.setComboTreeFieldContent(queryConfigs, treeListRoot,"0");
        String treeListContent = FreeMarkerUtil.getFtlContent("TreeDatagridFtl.jsp");
        String treeListResult = FreeMarkerUtil.getResultString(treeListContent, treeListRoot);
        return treeListResult;
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年11月14日 下午10:20:49
     * @param controlId
     * @param top
     * @param missionId
     * @param mainTableInfo
     * @param hiddenContent
     * @param IS_WINDOW
     * @param ftlRoot
     * @param targetCode
     * @param projectPath
     */
    public void contructTypeLayout(String controlId,Map<String,Object> top,String missionId,
            CodeTableInfo mainTableInfo,StringBuffer hiddenContent,String IS_WINDOW,Map<String,Object> ftlRoot
            ,StringBuffer targetCode,String projectPath){
        String layoutType = top.get("LAYOUT_TYPE").toString();
        targetCode.append("<div class=\"easyui-layout\" fit=\"true\" id=\"designCodeId\" >");
        if(layoutType.equals("1")){
            targetCode.append("<div region=\"center\" >");
            List<Map<String,Object>> children = this.findList(controlId, missionId);
            for(Map<String,Object> child:children){
                String childControlType = (String) child.get("CONTROL_TYPE");
                if(childControlType.equals(ControlConfigService.CONTROL_TYPE_DATAGRID)){
                    String dataGridCode = this.getDataGridCode(child, projectPath,IS_WINDOW);
                    targetCode.append(dataGridCode);
                }
            }
            targetCode.append("</div>");
        }
        targetCode.append("</div>");
    }
    /**
     * 
     * 描述 根据missionId获取生成代码
     * @author Flex Hu
     * @created 2014年9月18日 下午2:50:33
     * @param missionId
     * @return
     */
    public String getGenCodeByMissionId(String missionId) {
        //定义需要移除ID属性
        String topElementId = null;
        String projectId = codeMissionService.getProjectId(missionId);
        Map<String,Object> mission = codeMissionService.getByJdbc("T_MSJW_DEVELOPER_CODEMISSION",
                new String[]{"MISSION_ID"},new Object[]{missionId});
        //是否弹出框
        String IS_WINDOW = mission.get("IS_WINDOW").toString();
        /*List<CodeTableInfo> allTableInfos = codeProjectService.findByProject(projectId);*/
        CodeTableInfo mainTableInfo = codeProjectService.getMainTableInfo(projectId);
        List<Map<String,Object>> topList = this.findList("0", missionId);
        //判断是否是tab组件
        boolean isTab = this.isTabs(topList);
        Properties properties = FileUtil.readProperties("project.properties");
        //获取项目代码地址
        String projectPath = properties.getProperty("projectPath");
        StringBuffer targetCode = new StringBuffer("");
        StringBuffer hiddenContent = new StringBuffer("");
        Map<String,Object> ftlRoot =new HashMap<String,Object>();
        if(IS_WINDOW.equals("1")){
            ftlRoot.put("MainClassName", mainTableInfo.getClassName());
            ftlRoot.put("MainTableName", mainTableInfo.getTableName());
        }
        if(isTab==true){
            ftlRoot.put("evetabs", topList);
            String tabFtlContent = FreeMarkerUtil.getFtlContent("TabFtl.jsp");
            String resultCode = FreeMarkerUtil.getResultString(tabFtlContent, ftlRoot);
            return resultCode;
        }
        for(Map<String,Object> top:topList){
            String controlType = (String) top.get("CONTROL_TYPE");
            String controlId = (String) top.get("CONFIG_ID");
            if(controlType.equals(ControlConfigService.CONTROL_TYPE_LAYOUT)){
                topElementId = "designCodeId";
                //获取布局类型
                this.contructTypeLayout(controlId, top, missionId, mainTableInfo, hiddenContent,
                        IS_WINDOW, ftlRoot, targetCode, projectPath);
            }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_FORMLAYOUT)){
                this.contructFormLayout(controlId, top, missionId, mainTableInfo, hiddenContent, 
                        IS_WINDOW, ftlRoot, targetCode);
            }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_UPLOAD)){
                this.contructUploadControl(controlId, top, missionId, mainTableInfo, 
                        hiddenContent, IS_WINDOW, ftlRoot, targetCode);
            }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_TREELIST)){
                String treeListResult= this.contructTreeListControl(
                        controlId, top, missionId, mainTableInfo, hiddenContent,
                        IS_WINDOW, ftlRoot, targetCode);
                return treeListResult;
            }else if(controlType.equals(ControlConfigService.CONTROL_TYPE_TREESELECTOR)){
                Map<String,Object> treeRoot = new HashMap<String,Object>();
                String TreeIdAndName= (String) top.get("TREE_IDANDNAME");
                String TreeTargetCols= (String) top.get("TREE_TARGETCOLS");
                String bindEntityName = (String) top.get("BIND_ENTITYNAME");
                String bindChinese = (String) top.get("BIND_CHINESE");
                String bindTableName = (String) top.get("BIND_TABLENAME");
                treeRoot.put("TreeIdAndName", TreeIdAndName);
                treeRoot.put("TreeTargetCols", TreeTargetCols);
                treeRoot.put("bindEntityName", bindEntityName);
                treeRoot.put("bindChinese", bindChinese);
                treeRoot.put("bindTableName", bindTableName);
                String treeSelectFtl = FreeMarkerUtil.getFtlContent("TreeSelectorFtl.jsp");
                String treeListResult = FreeMarkerUtil.getResultString(treeSelectFtl, treeRoot);
                return treeListResult;
            }
        }
        if(StringUtils.isNotEmpty(topElementId)){
            Document doc = Jsoup.parse(targetCode.toString());
            //获取设计元素节点
            Element topDesignElement = doc.getElementById(topElementId);
            topDesignElement.removeAttr("id");
            StringBuffer javaScriptCode = new StringBuffer("");
            if(doc.getElementsByTag("script")!=null&&doc.getElementsByTag("script").size()>0){
                Element jsEle = doc.getElementsByTag("script").get(0);
                javaScriptCode = new StringBuffer(jsEle.toString()).append("\n");
                jsEle.remove();
            }
            javaScriptCode.append(topDesignElement.toString());
            return javaScriptCode.toString();
        }else{
            if(IS_WINDOW.equals("1")){
                String windowFtlContent = FreeMarkerUtil.getFtlContent("WindowFtl.jsp");
                if(ftlRoot.get("ComboTreeFnAndSetContent")==null){
                    ftlRoot.put("ComboTreeFnAndSetContent","");
                }
                if(ftlRoot.get("uploadTableFnContent")==null){
                    ftlRoot.put("uploadTableFnContent","");
                }
                if(ftlRoot.get("InitComboTreeContent")==null){
                    ftlRoot.put("InitComboTreeContent", "");
                }
                if(ftlRoot.get("initUploadContent")==null){
                    ftlRoot.put("initUploadContent", "");
                }
                if(ftlRoot.get("comboTreeDiv")==null){
                    ftlRoot.put("comboTreeDiv", "");
                }
                
                ftlRoot.put("formContent", targetCode.toString());
                ftlRoot.put("hiddenContent", hiddenContent.toString());
                String resultCode = FreeMarkerUtil.getResultString(windowFtlContent, ftlRoot);
                return resultCode;
            }
            return "";
        }
      
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2014年9月28日 上午11:13:29
     * @param children
     * @return
     */
    public List<Map<String,Object>> getNotHiddenControl(List<Map<String,Object>> children,int formColumnNum){
        List<Map<String,Object>> controls = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> child:children){
            String childControlType = (String) child.get("CONTROL_TYPE");
            if(!childControlType.equals(ControlConfigService.CONTROL_TYPE_HIDDEN)){
                controls.add(child);
            }
        }
        if(controls.size()>=1){
            Map<String,Object> emptyControl = new HashMap<String,Object>();
            emptyControl.put("CONFIG_ID","-1");
            if(formColumnNum==2){
                if(children.size()%2!=0){
                    children.add(emptyControl);
                }
            }else if(formColumnNum==3){
                if(children.size()%3==1){
                    children.add(emptyControl);
                    children.add(emptyControl);
                }else if(children.size()%3==2){
                    children.add(emptyControl);
                }
            }
        }
        return controls;
    }
    /**
     * 
     * 描述设置隐藏域的内容值
     * @author Flex Hu
     * @created 2014年9月28日 上午11:10:38
     * @param children
     * @param hiddenContent
     * @param className
     */
    public void setHiddenFieldContent(List<Map<String,Object>> children,StringBuffer hiddenContent,String className){
        for(Map<String,Object> child:children){
            String childControlType = (String) child.get("CONTROL_TYPE");
            if(childControlType.equals(ControlConfigService.CONTROL_TYPE_HIDDEN)){
                String widgetName = (String) child.get("WIDGET_NAME");
                hiddenContent.append("<input type=\"hidden\" name=\"").append(widgetName).append("\" value=\"${")
                        .append(StringUtil.convertFirstLetterToLower(className)).append(".").append(widgetName)
                        .append("}\"> \n");
            }
        }
        
    }
    
    /**
     * 
     * 描述 设置上传控件JS内容
     * @author Flex Hu
     * @created 2014年10月2日 下午5:02:59
     * @param children
     * @param ftlRoot
     */
    public void setUploadControlContent(List<Map<String,Object>> children,Map<String,Object> ftlRoot){
        StringBuffer initUploadContent = new StringBuffer("");
        StringBuffer uploadTableFnContent= new StringBuffer("");
        for(Map<String,Object> child:children){
            String childControlType = (String) child.get("CONTROL_TYPE");
            if(childControlType.equals(ControlConfigService.CONTROL_TYPE_UPLOAD)){
                String widgetName = (String) child.get("WIDGET_NAME");
                String uploadType = (String) child.get("UPLOAD_TYPE");
                String fileTypes = (String) child.get("UPLOAD_LIMITTYPES");
                String limitSize = (String) child.get("UPLOAD_LIMITSIZE");
                String limitFileSize=  (String) child.get("UPLOAD_LIMITFILESIZE");
                String uploadPath = (String) child.get("UPLOAD_PATH");
                String tableId = (String) child.get("UPLOAD_TABLEID");
                String tableName = (String) child.get("BIND_TABLENAME");
                if(uploadType.equals("1")){
                    initUploadContent.append("AppUtil.initUploadControl({").append("file_types:\"").append(fileTypes)
                            .append("\",").append("file_upload_limit:0,").append("file_queue_limit:1,")
                            .append("uploadPath:\"").append(uploadPath).append("\",").append("busTableName:\"")
                            .append(tableName).append("\",")
                            .append("uploadUserId:$(\"input[name='CURLOGIN_USERID']\").val(),")
                            .append("uploadButtonId:\"").append(widgetName).append("_BTN\",")
                            .append("uploadFileType:\"image\",").append("uploadImageId:\"").append(widgetName)
                            .append("_IMG\",").append("uploadImageFieldName:\"").append(widgetName).append("\",")
                            .append("limit_size:\"").append(limitSize).append("\",")
                            .append("uploadSuccess:function(file, serverData, responseReceived){")
                            .append("var resultJson = $.parseJSON(serverData);")
                            .append("var filePath = resultJson.filePath;").append("$(\"#").append(widgetName)
                            .append("_IMG\")").append(".attr(\"src\",__attachFileUrl+filePath);")
                            .append("$(\"input[name='").append(widgetName).append("']\").val(filePath);}\n")
                            .append("});\n");
                }else if(uploadType.equals("2")){
                    initUploadContent.append("AppUtil.initUploadControl({").append("file_types:\"").append(fileTypes)
                            .append("\",").append("file_upload_limit:0,").append("file_queue_limit:1,")
                            .append("uploadPath:\"").append(uploadPath).append("\",").append("busTableName:\"")
                            .append(tableName).append("\",")
                            .append("uploadUserId:$(\"input[name='CURLOGIN_USERID']\").val(),")
                            .append("uploadButtonId:\"").append(widgetName).append("_BTN\",")
                            .append("singleFileDivId:\"").append(widgetName).append("_DIV\",").append("singleFileId:")
                            .append("$(\"input[name='").append(widgetName).append("']\").val(),")
                            .append("singleFileFieldName:\"").append(widgetName).append("\",").append("limit_size:\"")
                            .append(limitSize).append("\",").append("uploadSuccess:function(resultJson){")
                            .append("$(\"input[name='").append(widgetName).append("']\").val(resultJson.fileId);}\n")
                            .append("});\n");
                }else if(uploadType.equals("3")){
                    initUploadContent.append("AppUtil.initUploadControl({").append("file_types:\"").append(fileTypes)
                            .append("\",").append("file_upload_limit:").append(limitFileSize).append(",")
                            .append("file_queue_limit:").append(limitFileSize).append(",").append("uploadPath:\"")
                            .append(uploadPath).append("\",").append("busTableName:\"").append(tableName).append("\",")
                            .append("uploadUserId:$(\"input[name='CURLOGIN_USERID']\").val(),")
                            .append("uploadButtonId:\"").append(widgetName).append("_BTN\",")
                            .append("uploadTableId:\"").append(tableId).append("\",").append("limit_size:\"")
                            .append(limitSize).append("\"").append("});\n");
                    Map<String,Object> fnRoot = new HashMap<String,Object>();
                    fnRoot.put("uploadTableId", tableId);
                    fnRoot.put("uploadTableBtnId", widgetName+"_BTN");
                    String uploadFtlContent = FreeMarkerUtil.getFtlContent("UploadTableFn.js");
                    String uploadFnContent = FreeMarkerUtil.getResultString(uploadFtlContent,fnRoot);
                    uploadTableFnContent.append(uploadFnContent).append("\n");
                }
            }
        }
        ftlRoot.put("initUploadContent", initUploadContent.toString());
        ftlRoot.put("uploadTableFnContent", uploadTableFnContent.toString());
    }
    /**
     * 
     * 描述 设置下拉树内容
     * @author Flex Hu
     * @created 2014年9月29日 下午8:31:11
     * @param children
     * @param ftlRoot
     */
    public void setComboTreeFieldContent(List<Map<String,Object>> children,Map<String,Object> ftlRoot,String isWindow){
        StringBuffer comboTreeFnAndSetContent = new StringBuffer("");
        StringBuffer initComboTreeContent = new StringBuffer("");
        StringBuffer comboTreeDiv = new StringBuffer("");
        for(Map<String,Object> child:children){
            String childControlType = (String) child.get("CONTROL_TYPE");
            if(childControlType.equals(ControlConfigService.CONTROL_TYPE_CMOBOTREE)){
                String widgetName = (String) child.get("WIDGET_NAME");
                widgetName = widgetName.replace(".", "");
                String treeDataType = (String) child.get("TREE_DATATYPE");
                String bindTableName = (String) child.get("BIND_TABLENAME");
                String idAndName = (String) child.get("TREE_IDANDNAME");
                String targetCols = (String) child.get("TREE_TARGETCOLS");
                String rootName = (String) child.get("TREE_ROOTNAME");
                String dicTypeCode = "";
                if(treeDataType.equals("2")){
                    bindTableName = "T_MSJW_SYSTEM_DICTYPE";
                    dicTypeCode = (String) child.get("COMBOBOX_TYPECODE");
                }
                Map<String,Object> setAndFnRoot = new HashMap<String,Object>();
                setAndFnRoot.put("showControlName",widgetName);
                setAndFnRoot.put("bindTableName",bindTableName);
                setAndFnRoot.put("idAndNameCol",idAndName);
                setAndFnRoot.put("targetCols",targetCols);
                setAndFnRoot.put("dicTypeCode",dicTypeCode);
                setAndFnRoot.put("rootName",rootName);
                if(isWindow.equals("1")){
                    setAndFnRoot.put("treeOffLeft","(treeOffset.left) + \"px\"");
                    setAndFnRoot.put("treeOffTop","(treeOffset.top + treeObj.outerHeight()) + \"px\"");
                }else{
                    setAndFnRoot.put("treeOffLeft","(treeOffset.left-160)+\"px\"");
                    setAndFnRoot.put("treeOffTop","(treeOffset.top - 75) + \"px\"");
                }
                String setAndRootResult = FreeMarkerUtil.
                        getResultString(FreeMarkerUtil.getFtlContent("WindowComboTreeFnAndSetFtl.js"), setAndFnRoot);
                comboTreeFnAndSetContent.append(setAndRootResult).append("\n");
                initComboTreeContent.append("$.fn.zTree.init($(\"#").append(widgetName).append("Tree\"),")
                        .append(widgetName).append("Setting);\n");
                comboTreeDiv
                        .append("<div class=\"treeContent eve-combotree\" style=\"display:none;"
                                + " position: absolute;\"").append(" id=\"").append(widgetName)
                        .append("TreeContent\" >\n")
                        .append("<ul class=\"ztree\" style=\"margin-top:0; width:160px;height: 150px\"")
                        .append(" id=\"").append(widgetName).append("Tree\" ></ul>").append("</div>\n");
            }
        }
        ftlRoot.put("ComboTreeFnAndSetContent", comboTreeFnAndSetContent.toString());
        ftlRoot.put("InitComboTreeContent", initComboTreeContent.toString());
        ftlRoot.put("comboTreeDiv", comboTreeDiv.toString());
    }
    /**
     * 
     * 描述 获取绑定时间代码生成结果
     * @author Flex Hu
     * @created 2014年9月24日 下午4:45:39
     * @param mainClassName
     * @param queryConfigs
     * @return
     */
    public String genBindTimeCodes(String mainClassName,
            List<Map<String,Object>> queryConfigs){
        StringBuffer code = new StringBuffer("");
        Set<String> isTimeFormatNames = new HashSet<String>();
        for(Map<String,Object> queryConfig:queryConfigs){
            String CONTROL_TYPE = (String) queryConfig.get("CONTROL_TYPE");
            String WIDGET_NAME = (String) queryConfig.get("WIDGET_NAME");
            String QUERY_RULE = (String) queryConfig.get("QUERY_RULE");
            if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_DATE)){
                String TIME_FORMAT = (String) queryConfig.get("TIME_FORMAT");
                if(!TIME_FORMAT.equals("YYYY-MM-DD")){
                    isTimeFormatNames.add(WIDGET_NAME);
                }
            }
        }
        Map<String,String> dateConfigs = this.getBindTimes(queryConfigs);
        if(dateConfigs.size()>=1){
            Set<String> beginTimes = dateConfigs.keySet();
            String bindDateControlFtl = FreeMarkerUtil.getFtlContent("BindDateControl.js");
            int i = 1;
            for(String beginTime:beginTimes){
                Map<String,Object> ftlRoot = new HashMap<String,Object>();
                ftlRoot.put("startDateName","start"+i);
                ftlRoot.put("startDateId", mainClassName+beginTime+"_BEGIN");
                ftlRoot.put("startDateQueryName", "Q_"+beginTime+"_>=");
                ftlRoot.put("endDateQueryName", "Q_"+beginTime+"_<=");
                if(isTimeFormatNames.contains(beginTime)){
                    ftlRoot.put("dateFormat","YYYY-MM-DD hh:mm:ss");
                    ftlRoot.put("istime","true");
                }else{
                    ftlRoot.put("dateFormat","YYYY-MM-DD");
                    ftlRoot.put("istime","false");
                }
                ftlRoot.put("endDateName","end"+i);
                ftlRoot.put("endDateId", mainClassName+beginTime+"_END");
                String bingDateControlResult = FreeMarkerUtil.
                        getResultString(bindDateControlFtl, ftlRoot);
                code.append(bingDateControlResult).append("\n");
                i++;
            }
        }
        return code.toString();
    }
    /**
     * 
     * 描述 获取绑定的时间关系
     * @author Flex Hu
     * @created 2014年9月24日 上午9:33:32
     * @param queryConfigs
     * @return
     */
    public Map<String,String> getBindTimes(List<Map<String,Object>> queryConfigs){
        Map<String,String> map = new HashMap<String,String>();
        for(Map<String,Object> queryConfig:queryConfigs){
            String CONTROL_TYPE = (String) queryConfig.get("CONTROL_TYPE");
            String WIDGET_NAME = (String) queryConfig.get("WIDGET_NAME");
            if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_DATE)){
                String BIND_BEGINTIME = (String) queryConfig.get("BIND_BEGINTIME");
                if(StringUtils.isNotEmpty(BIND_BEGINTIME)){
                    map.put(BIND_BEGINTIME, WIDGET_NAME);
                }
            }
        }
        return map;
    }
    /**
     * 
     * 描述 生成权限条件的HTML代码
     * @author Flex Hu
     * @created 2014年9月23日 下午4:56:07
     * @param queryConfigs
     * @return
     */
    public String genQueryConfigHtml(List<Map<String,Object>> queryConfigs,String mainClassName){
        StringBuffer html = new StringBuffer("");
        int totalQuerySize = queryConfigs.size();
        int totalLineSize = this.getQueryLineTotal(totalQuerySize);
        Map<String,String> bindTimes = this.getBindTimes(queryConfigs);
        Set<String> bindBeginTimes = bindTimes.keySet();
        Collection<String> bindEndTimes= bindTimes.values();
        for(int i =1;i<=totalLineSize;i++){
            html.append("<tr style=\"height:28px;\">\n");
            for(int j = (i*3)-3;(j<(i*3)&&j<totalQuerySize);j++){
                Map<String,Object> queryConfig = queryConfigs.get(j);
                String LABLE_VALUE = (String) queryConfig.get("LABLE_VALUE");
                String WIDGET_NAME = (String) queryConfig.get("WIDGET_NAME");
                String QUERY_RULE = (String) queryConfig.get("QUERY_RULE");
                String CONTROL_TYPE = (String) queryConfig.get("CONTROL_TYPE");
                String queryName = "Q_"+WIDGET_NAME+"_"+QUERY_RULE;
                html.append("<td style=\"width:68px;text-align:right;\">").append(LABLE_VALUE).append("</td>\n");
                html.append("<td style=\"width:135px;\">\n");
                if (CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_TEXT)) {
                    // 如果是文本控件
                    html.append("<input class=\"eve-input\" type=\"text\" maxlength=\"")
                            .append("20\" style=\"width:128px;\" name=\"").append(queryName).append("\" />");
                }else if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_DATE)){
                    //如果是日期控件
                    String TIME_FORMAT = (String) queryConfig.get("TIME_FORMAT");
                    //获取绑定时间
                    String BIND_BEGINTIME = (String) queryConfig.get("BIND_BEGINTIME");
                    if(bindBeginTimes.contains(WIDGET_NAME)||bindEndTimes.contains(WIDGET_NAME)){
                        String dateId = "";
                        if(StringUtils.isNotEmpty(BIND_BEGINTIME)){
                            dateId = mainClassName+WIDGET_NAME+"_END";
                        }else{
                            dateId = mainClassName+WIDGET_NAME+"_BEGIN";
                        }
                        html.append("<input  type=\"text\" style=\"width:128px;float:left;\"")
                                .append(" class=\"laydate-icon\" id=\"").append(dateId).append("\" name=\"")
                                .append(queryName).append("\" />");
                    }else{
                        html.append("<input type=\"text\" style=\"width:128px;float:left;\"");
                        if(TIME_FORMAT.equals("YYYY-MM-DD")){
                            html.append(" onclick=\"laydate({istime: false, format: '").append(TIME_FORMAT)
                                    .append("'})\" ");
                        }else{
                            html.append(" onclick=\"laydate({istime: true, format: '").append(TIME_FORMAT)
                                    .append("'})\" ");
                        }
                        html.append(" class=\"laydate-icon\"  name=\"").append(queryName).append("\" />");
                    }
                }else if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_NUMBER)){
                    //获取精度值
                    String PRECISION_VALUE = (String) queryConfig.get("PRECISION_VALUE");
                    //如果是数值控件
                    html.append("<input class=\"easyui-numberbox\" type=\"text\" maxlength=\"")
                            .append("20\" style=\"width:128px;\" name=\"").append(queryName).append("\" precision=\"")
                            .append(PRECISION_VALUE).append("\" />");
                }else if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_COMBOBOX)){
                    String COMBOBOX_URLVALUE = (String) queryConfig.get("COMBOBOX_URLVALUE");
                    String COMBOBOX_DEFURL = (String) queryConfig.get("COMBOBOX_DEFURL");
                    String COMBOBOX_VALUEFIELD = (String) queryConfig.get("COMBOBOX_VALUEFIELD");
                    String COMBOBOX_TEXTFIELD= (String) queryConfig.get("COMBOBOX_TEXTFIELD");
                    String COMBOBOX_TYPECODE = (String) queryConfig.get("COMBOBOX_TYPECODE");
                    html.append("<input class=\"easyui-combobox\" style=\"width:128px;\"").append(" name=\"")
                            .append(queryName).append("\" data-options=\"\n");
                    String url = "";
                    if(COMBOBOX_URLVALUE.equals("1")){
                        url = "dictionaryController.do?load&defaultEmpty=true&typeCode="+COMBOBOX_TYPECODE;
                    }else{
                        url = COMBOBOX_DEFURL;
                    }
                    html.append("url:'").append(url).append("',")
                    .append("editable:false,method: 'post',")
                    .append("valueField:'").append(COMBOBOX_VALUEFIELD).append("',")
                    .append("textField:'").append(COMBOBOX_TEXTFIELD).append("',")
                    .append("panelWidth: 128,panelHeight: 'auto' \" /> ");
                }else if(CONTROL_TYPE.equals(ControlConfigService.CONTROL_TYPE_CMOBOTREE)){
                    StringBuffer classValue = new StringBuffer("eve-input");
                    html.append("<input type=\"text\" style=\"width:128px;\"").append(" readonly=\"readonly\" ")
                            .append(" class=\"").append(classValue.toString()).append("\" ").append(" name=\"")
                            .append(queryName).append("\" ").append(" id=\"").append(WIDGET_NAME.replace(".", ""))
                            .append("\" ").append(" onclick=\"showSelect").append(WIDGET_NAME.replace(".", ""))
                            .append("Tree();\" >");

                }
                html.append("</td>\n");
            }
            if(i<totalLineSize){
                html.append("<td colspan=\"2\"></td>\n");
            }else{
                int colspan = 2;
                if(totalQuerySize%3==1){
                    colspan = 6;
                }else if(totalQuerySize%3==2){
                    colspan = 4;
                }
                html.append("<td colspan=\"").append(colspan).append("\">");
                html.append("<input type=\"button\" value=\"查询\" class=\"eve-button\" ")
                        .append(" onclick=\"AppUtil.gridDoSearch('").append(mainClassName).append("Toolbar")
                        .append("','").append(mainClassName).append("Grid')\" /> ");
                html.append("<input type=\"button\" value=\"重置\" class=\"eve-button\" ")
                        .append(" onclick=\"AppUtil.gridSearchReset('").append(mainClassName).append("Form')\" />");
                html.append("</td>\n");
            }
            html.append("</tr>\n");
        }
        return html.toString();
    }
    /**
     * 
     * 描述 获取查询条件需要被排列的总行数
     * @author Flex Hu
     * @created 2014年9月23日 下午5:28:59
     * @param totalQueryNumber
     * @return
     */
    private int getQueryLineTotal(int totalQueryNumber){
        int totalLine = 0;
        if(totalQueryNumber<=3){
            totalLine = 1;
        }else if((totalQueryNumber/3>=1)&&(totalQueryNumber%3!=0)){
            totalLine = (totalQueryNumber/3+1);
        }else if((totalQueryNumber/3>=1)&&(totalQueryNumber%3==0)){
            totalLine = totalQueryNumber/3;
        }
        return totalLine;
    }
    
    /**
     * 
     * 描述 根据任务ID、控件类型获取配置的信息记录
     * @author Flex Hu
     * @created 2014年9月24日 下午3:03:21
     * @param missionId
     * @param controlType
     * @return
     */
    public List<Map<String,Object>> findControl(String missionId,String controlType){
        StringBuffer sql = new StringBuffer("select * from ")
                .append("T_MSJW_DEVELOPER_CONTROLCONFIG WHERE MISSION_ID=? AND CONTROL_TYPE=? ");
        List<Map<String,Object>> controls = dao.findBySql(sql.toString(), 
                new Object[]{missionId,controlType},null);
        return controls;
    }
}
