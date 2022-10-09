<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("CodeMissionForm", function(form, valid) {
			if (valid) {
				var formData = $("#CodeMissionForm").serialize();
				var url = $("#CodeMissionForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if(resultJson.success){
						   parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:3,
								ok: true
							});
							parent.$("#codeMissionGrid").datagrid("reload");
							AppUtil.closeLayer();
						}else{
							parent.art.dialog({
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
	<form id="CodeMissionForm" method="post" action="codeMissionController.do?saveOrUpdate">
	    <div  id="CodeMissionFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="MISSION_ID" value="${codeMission.MISSION_ID}">
	    <input type="hidden" name="PROJECT_ID" value="${codeMission.PROJECT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">JSP名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${codeMission.JSP_NAME}"
					 class="eve-input validate[required]" name="JSP_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">是否弹出框：</span>
				<div class="eve-chcekbox" style="width: 160px;">
				     <ul>
						<li>是<input type="radio" name="IS_WINDOW"
						 <c:if test="${codeMission.IS_WINDOW==1}">checked="checked"</c:if>
						  value="1" ></li>
						<li>不是<input type="radio" name="IS_WINDOW" 
						<c:if test="${codeMission.IS_WINDOW==0}">checked="checked"</c:if>
						value="0"></li>
				     </ul>	
				</div>
				</td>
			</tr>
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
