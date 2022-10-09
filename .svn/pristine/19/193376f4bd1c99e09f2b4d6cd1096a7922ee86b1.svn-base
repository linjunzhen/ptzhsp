<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("EstimateInfoForm", function(form, valid) {
			if (valid) {
				if(!$('#BUSSYS_UNITCODECombx').combobox('getValue') || $('#BUSSYS_UNITCODECombx').combobox('getValue') ==''){
					$.messager.alert('警告',"业务单位不能为空！");
					return;
				}
				if(!$('#BUSSYS_PLATCODECombx').combobox('getValue')||$('#BUSSYS_PLATCODECombx').combobox('getValue') == ''){
					$.messager.alert('警告',"业务系统不能为空！");
					return;
				}
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#EstimateInfoForm").serialize();
				var url = $("#EstimateInfoForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "succeed",
								time : 3,
								ok : true
							});
							parent.$("#EstimateGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content : resultJson.msg,
								icon : "error",
								ok : true
							});
						}
					}
				});
			}
		}, "T_LCJC_BUS_ESTIMATE");

		//...
	});	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
<div id="ContactInfoTabDiv" class="easyui-layout" fit="true">
 <div data-options="region:'center'" fit="true">
	<form id="EstimateInfoForm" method="post" action="busEstimateController.do?saveOrUpdate">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="ID" value="${estimate.ID}">
			<input type="hidden" name="ESTIMATE_PLATNAME" value="${estimate.ESTIMATE_PLATNAME}">
			<input type="hidden" name="ESTIMATE_DEPTNAME" value="${estimate.ESTIMATE_DEPTNAME}">
	
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
					<td colspan="2"><span style="width: 130px;float:left;text-align:right;">业务单位：</span>
					<input class="easyui-combobox" style="width:350px;"  id="BUSSYS_UNITCODECombx"
						name="ESTIMATE_DEPTCODE" value="${estimate.ESTIMATE_DEPTCODE}"
						data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
						method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 350,panelHeight: '200',
						onSelect:function(rec){
						   if(rec.UNIT_ID){
						   	  $('#BUSSYS_PLATCODECombx').combobox('clear');
        			          var url1 = 'busSysController.do?comboxSystems&uintId='+rec.UNIT_ID;
    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1); 						   	  
						   }
						   if(rec.UNIT_NAME){
						   	  $('input[name=ESTIMATE_DEPTNAME]').val(rec.UNIT_NAME);
						   }
						},
						onLoadSuccess:function(){
						  	var unitcode = $('#BUSSYS_UNITCODECombx').combobox('getValue');
						  	if(unitcode != ''){
							  $('#BUSSYS_PLATCODECombx').combobox('clear');
        			          var url1 = 'busSysController.do?comboxSystemsUnitCode&unitcode='+unitcode;
    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1);
    			    		  $('#BUSSYS_PLATCODECombx').combobox('setValue','${estimate.ESTIMATE_PLATCODE}');
    			    		}
						} 
					" />
					<font class="dddl_platform_html_requiredFlag">*</font>	
				</td>					
				</tr>
				<tr>
					<td colspan="2"><span style="width: 130px;float:left;text-align:right;">业务系统：</span>
						<input style="width:350px;height:25px;float:left;"
							class="easyui-combobox validate[required]" value="${estimate.ESTIMATE_PLATCODE}"
							name="ESTIMATE_PLATCODE" id="BUSSYS_PLATCODECombx"
							data-options="url:'busSysController.do?comboxSystems',
									editable:false,method: 'post',valueField:'SYSTEM_CODE',
									textField:'SYSTEM_NAME',panelWidth: 350,panelHeight: '150',
									onSelect:function(rec){
									   if(rec.SYSTEM_NAME){
									   	  $('input[name=ESTIMATE_PLATNAME]').val(rec.SYSTEM_NAME);
									   }
									} 
							"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>					
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">面向个人总数：</span>
						<input type="text" style="width:100px;float:left;" maxlength="5"
						class="eve-input validate[required],custom[numberplus]" value="${estimate.ESTIMATE_PERSONNUM}"
						name="ESTIMATE_PERSONNUM" />
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td><span style="width: 130px;float:left;text-align:right;">面向单位总数：</span>
						<input style="width:100px;float:left;" maxlength="5"
						class="eve-input validate[required],custom[numberplus]"
						value="${estimate.ESTIMATE_DEPTNUM}" name="ESTIMATE_DEPTNUM"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
				    </td>
				</tr>
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">年业务总数量：</span>
						<input type="text" style="width:100px;float:left;" maxlength="5"
						class="eve-input validate[required],custom[numberplus]" value="${estimate.ESTIMATE_YEARNUM}"
						name="ESTIMATE_YEARNUM" />
						<font class="dddl_platform_html_requiredFlag">*</font>
				    </td>
					<td><span style="width: 130px;float:left;text-align:right;">年业务数据量大小：</span>
						<input type="text" style="width:100px;float:left;" maxlength="5"
						class="eve-input validate[required],custom[numberplus]"
						value="${estimate.ESTIMATE_YEARDATASIZE}" name="ESTIMATE_YEARDATASIZE" />G<font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>				
				<tr>
					<td><span style="width: 130px;float:left;text-align:right;">非结构化数据量大小：</span>
						<input type="text" style="width:100px;float:left;" maxlength="5"
						class="eve-input validate[required],custom[numberplus]"
						value="${estimate.ESTIMATE_UNSTRUCTSIZE}" name="ESTIMATE_UNSTRUCTSIZE" />G<font
						class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
			</table>
			
			<div class="eve_buttons" style="position: relative;">
				<input value="提交" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</form>
 </div>
</div>
</body>