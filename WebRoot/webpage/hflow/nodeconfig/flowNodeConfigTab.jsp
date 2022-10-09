<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,artdialog,ztree"></eve:resources>
<script type="text/javascript">
	
	function saveTaskDecide(nodeName,defId){
		var taskDecideId = $("#TASK_DECIDE_CONFIGID").val();
		if(taskDecideId){
			var formData = $("#FlowNodeOtherConfigForm").serialize();
			AppUtil.ajaxProgress({
				url : "nodeConfigController.do?saveOrUpdate",
				params : formData,
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						$("input[name='NODE_CONFID']").val(resultJson.jsonString);
					} else {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}else{
			alert("任务决策方式不能为空!");
		}
	}
	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		
		<div class="easyui-layout" fit="true">
			<div data-options="region:'center'" fit="true">
				<div class="easyui-tabs eve-tabs" fit="true" >
					<div title="字段权限配置" href="${fieldRightUrl}">
					
					</div>
					
					<div title="事件接口配置" href="${flowEventUrl}">
					
					</div>
					<div title="操作按钮配置" href="${buttonRightUrl}">
					
					</div>
					<div title="下一环节审核人配置" href="${nodeAudierUrl}">
					
					</div>
					<div title="审批材料模版配置" href="${materConfigUrl}">
					
					</div>
					<div title="其他配置" style="background-color: #f7f7f7;" >
					    <form id="FlowNodeOtherConfigForm" method="post" >
					    <input type="hidden" name="NODE_CONFID" value="${nodeData.NODE_CONFID}">
					    <input type="hidden" name="NODE_NAME" value="${nodeData.nodeName}">
					    <input type="hidden" name="DEF_ID" value="${nodeData.defId}">
					    <input type="hidden" name="DEF_VERSION" value="${nodeData.flowVersion}">
							<a href="#" class="easyui-linkbutton"
								iconCls="icon-save" plain="true"
								onclick="saveTaskDecide('${nodeData.nodeName}','${nodeData.defId}');">保存其他配置</a>
					    <table style="width:100%;border-collapse:collapse;" class="dddl-contentBorder dddl_table">
					    <tr>
							<td><span style="width: 100px;float:left;text-align:right;">任务决策方式：</span>
							    <eve:eveselect clazz="eve-input" dataParams="2" id="TASK_DECIDE_CONFIGID"
							         value="${nodeData.CONFIG_ID}"
							         dataInterface="auditConfigService.findForSelect" defaultEmptyText="请选择任务决策方式"
							         name="CONFIG_ID">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							<td><span style="width: 120px;float:left;text-align:right;">允许指定办理时限：</span>
							    <eve:eveselect clazz="eve-input" dataParams="FlowZdblsx" 
							         value="${nodeData.IS_ASSIGNDEADTIME}"
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_ASSIGNDEADTIME">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr>
						   <td>
							   <span style="width: 100px;float:left;text-align:right;">任务是否串审：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" id="IS_TASKORDER"
							         value="${nodeData.IS_TASKORDER}" 
							         dataInterface="dictionaryService.findDatasForSelect" defaultEmptyText="请选择"
							         name="IS_TASKORDER">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							<td><span style="width: 100px;float:left;text-align:right;">接口代码：</span>
								<input type="text" style="width:150px;float:left;" 
								class="eve-input"
								value="${nodeData.TASK_ORDER_CODE}" name="TASK_ORDER_CODE" />
							</td>
						</tr>
						<tr>
						
						   <td><span style="width: 100px;float:left;text-align:right;">办理工作日限制：</span>
							    <input type="text" style="width:150px;float:left;" 
								class="eve-input" value="${nodeData.WORKDAY_LIMIT}" name="WORKDAY_LIMIT" />天
						   </td>
						   <td>
							   <span style="width: 120px;float:left;text-align:right;">添加审核结果控件：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowAddAgree" 
							         value="${nodeData.IS_ADDPASS}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_ADDPASS">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr>
						
						   <td>
							   <span style="width: 120px;float:left;text-align:right;">是否省网办发起节点：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.IS_PROVINCE_START}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_PROVINCE_START">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							 <td>
							   <span style="width: 120px;float:left;text-align:right;">是否添加传阅控件：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.IS_ADDPERUAL}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_ADDPERUAL">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr>
						    <td>
							   <span style="width: 120px;float:left;text-align:right;">公文流转规则：</span>
								<eve:eveselect clazz="eve-input" dataParams="GWLZLX" 
							         value="${nodeData.PUBDOC_RULE}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="PUBDOC_RULE">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							<td>
							   <span style="width: 120px;float:left;text-align:right;">是否允许上传：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.UPLOAD_FILES}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="UPLOAD_FILES">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						
						<tr>
						    <td>
							   <span style="width: 120px;float:left;text-align:right;">支持移动审批：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.MOBILE_AUDIT}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="MOBILE_AUDIT">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							<td>
							   <span style="width: 120px;float:left;text-align:right;">添加绑定材料控件：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowAddAgree" 
							         value="${nodeData.IS_MARTER}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_MARTER">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
						</tr>
						<tr>
						   <td>
							   <span style="width: 120px;float:left;text-align:right;">是否二次审批材料：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.SECOND_AUDIT}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="SECOND_AUDIT">
							    </eve:eveselect>
								<font class="dddl_platform_html_requiredFlag">*</font>
							</td>
							<td>
							   <span style="width: 120px;float:left;text-align:right;">是否支持分发：</span>
								<eve:eveselect clazz="eve-input" dataParams="FlowButtonAuth" 
							         value="${nodeData.IS_DISTRIBUTE}" 
							         dataInterface="dictionaryService.findDatasForSelect" 
							         name="IS_DISTRIBUTE" defaultEmptyText="请选择是否支持分发">
							    </eve:eveselect>
							 </td>
						</tr>
						</table>
						<h2>配置提示:1、办理工作日限制默认为0天,代表这个环节办理没有时限限制,<font style="color: red">-1代表这个环节要求即办</font>。<br>
						         2、添加审核结果控件如果配置为添加,那么审核弹出框将会出现通过不通过选择下拉框
						</h2>
						</form>
					</div>
				</div>
			</div>
		</div>
		<%-- 
		<div class="eve_buttons">
			<input
				value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
		--%>
</body>
