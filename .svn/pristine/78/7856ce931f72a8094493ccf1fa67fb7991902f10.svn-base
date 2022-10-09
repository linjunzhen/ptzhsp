<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		$('#PROCESSCODECombox').combobox({
			
			filter: function(q, row){
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			}
		});
	});
	function goChange(){

		
		var busCode = $("#BusSpecialChangeGoForm input[name='BUS_CODE']").val();
		var changeCode = $("#BusSpecialChangeGoForm input[name='BUSSYS_CHANGECODE']").val();
		var applyName = $("#BusSpecialChangeGoForm input[name='APPLY_NAME']").val();
		if (!busCode){
			$.messager.alert('警告',"请选择业务专项!");
			return;
		}
		if (!changeCode) {
			$.messager.alert('警告',"请选择要变更项!");
			return;
		}
		if(!applyName || applyName==""){
			$.messager.alert('警告',"请输入变更描述！");
			return;
		}
		var formData = $("#BusSpecialChangeGoForm").serialize();
		var url = $("#BusSpecialChangeGoForm").attr("action");
		AppUtil.ajaxProgress({
			url : url,
			params : formData,
			callback : function(resultJson) {
				if (resultJson.success) {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
					    ok: true
					});
					parent.$("#SpecialChangeGrid").datagrid("reload");
					AppUtil.closeLayer();
				} else {
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
					    ok: true
					});
				}
			}
		});
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusSpecialChangeGoForm" method="post"
		action="busSpecialChangeController.do?saveToChange">
		<!--==========隐藏域部分开始 ===========-->
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">业务专项：</span>
					<select class="easyui-combobox special" style="width:300px;" name="BUS_CODE" id="PROCESSCODECombox"  
					       	data-options="url:'busSpecialChangeController.do?loadNotChanged',editable:true,mode:'local',
					       	method: 'post',valueField:'BUS_CODE',textField:'BUS_NAME',panelWidth: 300,panelHeight: '120px' " >
					</select>	
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">要变更项：</span>
					<input style="width:300px;height:40px;float:left;" placehold="ss"
							class="easyui-combobox validate[required]" 
							name="BUSSYS_CHANGECODE" id="BUSSYS_PLATCODECombx"
							data-options="url:'dictionaryController/loadData.do?typeCode=BUSAUDIT&orderType=asc',editable:false,
							multiline:true,multiple:true,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 300,panelHeight: 'auto'" />
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
			</tr>
			<tr>
			<td><span style="width: 150px;float:left;text-align:right;">变更描述：</span>
					<input maxlength="60" class="eve-input validate[required]"
						style="width: 298px;float:left" name="APPLY_NAME" ></input>
						<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
				</tr>
		</table>

		<div class="eve_buttons">
			<input value="确定" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight" 
				onclick="goChange();"/>
			<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

