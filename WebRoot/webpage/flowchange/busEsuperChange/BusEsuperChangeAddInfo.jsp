<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {

	});
	function toAddEsuperChangeWindow(){
		var unitCode = $("#BusEsuperChangeAddForm input[name='BUSSYS_UNITCODE']").val();
		var platCode = $("#BusEsuperChangeAddForm input[name='BUSSYS_PLATCODE']").val();
		var busCode = $("#BusEsuperChangeAddForm input[name='BUS_CODE']").val();
		if(!unitCode){
			$.messager.alert('警告',"请选择业务单位!");
			return;
		}
		if(!platCode){
			$.messager.alert('警告',"请选择业务系统!");
			return;
		}
		if (busCode) {
			window.open("busEsuperChangeController.do?toAddColumn&entityId=" + busCode,"addEsuperChangeWin");
			AppUtil.closeLayer();
		}else{
			$.messager.alert('警告',"请选择业务专项!");
			return;
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="BusEsuperChangeAddForm" method="post"
		action="busEsuperChangeController.do?toAddColumn">
		<!--==========隐藏域部分开始 ===========-->
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">业务单位：</span>
					<input class="easyui-combobox" style="width:250px;" name="BUSSYS_UNITCODE" 
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 250,panelHeight: 'auto',
						onSelect:function(rec){
						   if(rec.UNIT_ID){
						   	  $('#BUSSYS_PLATCODECombx').combobox('clear');
        			          var url1 = 'busSysController.do?comboxSystems&uintId='+rec.UNIT_ID;
    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1); 						   	  
						   }
						   if(rec.UNIT_CODE){
						      $('#PROCESSCODECombox').combobox('clear');
        			          var url2 = 'busSpecialController.do?comboxSystems&unitcode='+rec.UNIT_CODE;
    			    		  $('#PROCESSCODECombox').combobox('reload',url2);
						   }
						} 		
						 " />
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">业务系统：</span>
					<input style="width:250px;height:25px;float:left;"
							class="easyui-combobox validate[required]" 
							name="BUSSYS_PLATCODE" id="BUSSYS_PLATCODECombx"
							data-options="url:'busSysController.do?comboxSystems',editable:false,
							method: 'post',valueField:'SYSTEM_CODE',textField:'SYSTEM_NAME',panelWidth: 250,panelHeight: 'auto'" />
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
			</tr>
			<tr>
				<td><span style="width: 150px;float:left;text-align:right;">业务专项：</span>
					<input class="easyui-combobox" style="width:250px;" name="BUS_CODE" id="PROCESSCODECombox"  
					       	data-options="url:'busSpecialController.do?load&defaultEmpty=true',editable:false,
					       	method: 'post',valueField:'BUS_CODE',textField:'BUS_NAME',panelWidth: 250,panelHeight: 'auto' " />	
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>
			</tr>
		</table>

		<div class="eve_buttons">
			<input value="确定" type="button" class="z-dlg-button z-dialog-okbutton aui_state_highlight" 
				onclick="toAddEsuperChangeWindow();"/>
			<input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

