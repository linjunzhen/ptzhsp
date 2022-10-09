<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,validationegine,icheck"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("FormLayoutConfigForm", function(form, valid) {
			if (valid) {
				var formData = $("#FormLayoutConfigForm").serialize();
				var url = $("#FormLayoutConfigForm").attr("action");
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
     <form id="FormLayoutConfigForm" method="post"
		action="controlConfigController.do?saveOrUpdate">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="CONFIG_ID" value="${controlConfig.CONFIG_ID}" />
		<input type="hidden" name="MISSION_ID" value="${controlConfig.MISSION_ID}" />
		<input type="hidden" name="PARENT_ID" value="${controlConfig.PARENT_ID}" />
	    <input type="hidden" name="CONTROL_TYPE" value="11">
	    <input type="hidden" name="CONTROL_NAME" value="表单布局">
	    <div id="LayoutControlFormDiv">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			
			<tr>
				<td>
				<span style="width: 125px;float:left;text-align:right;">标题：</span>
				<input
					type="text" style="width:150px;float:left;"
					 class="eve-input" name="FORM_LAYOUTTITLE" /> 
				</td>
				<td>
				<span style="width: 125px;float:left;text-align:right;">控件列数量：</span>
				<input class="easyui-combobox" style="width:150px;" name="FORM_COLUMNNUM" data-options="
					url:'dictionaryController.do?load&typeCode=FormLayoutColNum',
					editable:false,
					method: 'post',
					valueField:'DIC_CODE',
					textField:'DIC_NAME',
					panelWidth: 150,
					panelHeight: 'auto'
				" />
				
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
   </form>
</body>
