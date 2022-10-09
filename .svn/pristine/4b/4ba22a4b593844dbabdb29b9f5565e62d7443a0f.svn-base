<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("TreeSelectorForm", function(form, valid) {
			if (valid) {
				var formData = $("#TreeSelectorForm").serialize();
				var url = $("#TreeSelectorForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
							parent.window.location.reload();
							//AppUtil.closeLayer();
						}else{
							//layer.msg(resultJson.msg, 2, 1);
							art.dialog({
								content: resultJson.msg,
								icon:"error",
								ok: true
							});
						}
					}
				});
			}
		}, null)
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">

   <form id="TreeSelectorForm" method="post"
		action="controlConfigController.do?saveOrUpdate">
		<div id="TreeSelectorFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	     <input type="hidden" name="BIND_CHINESE">
	    <input type="hidden" name="BIND_TABLENAME">
	    <input type="hidden" name="CONFIG_ID" value="${controlConfig.CONFIG_ID}" /> 
	    <input type="hidden" name="MISSION_ID" value="${controlConfig.MISSION_ID}" /> 
	    <input type="hidden" name="PARENT_ID" value="${controlConfig.PARENT_ID}" />
		<input type="hidden" name="CONTROL_TYPE" value="20">
		<input type="hidden" name="CONTROL_NAME" value="树形选择器">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">绑定实体类：</span>
				<input class="easyui-combobox" style="width:150px;" name="BIND_ENTITYNAME" data-options="
					url:'codeMissionController.do?entitys&missionId=${controlConfig.MISSION_ID}',
					editable:false,
					method: 'post',
					valueField:'className',
					textField:'className',
					panelWidth: 150,
					panelHeight: 'auto',
					onSelect:function(rec){
					     $('input[name=BIND_CHINESE]').val(rec.chineseName);
					     $('input[name=BIND_TABLENAME]').val(rec.tableName);
					}
				" />
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">树ID和NAME对应名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="TREE_IDANDNAME" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">查询其它列：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="TREE_TARGETCOLS" /> 
				</td>
			</tr>
			
			
		</table>
		</div>
		
		<div class="eve_buttons">
			<input value="确定" type="submit"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
</body>
