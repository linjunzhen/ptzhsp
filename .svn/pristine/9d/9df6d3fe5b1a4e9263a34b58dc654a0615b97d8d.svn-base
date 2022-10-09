<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,artdialog,validationegine,icheck,json2"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ContactForm", function(form, valid) {
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
				var formData = $("#ContactForm").serialize();
				var url = $("#ContactForm").attr("action");
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
							parent.$("#ContactGrid").datagrid("reload");
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
		}, "T_LCJC_BUS_CONTRACT");

	});	
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		<div data-options="region:'center'" fit="true">
				<div>
				     <form id="ContactForm" method="post"
						action="busContactController.do?saveOrUpdate">
						<!--==========隐藏域部分开始 ===========-->
						<input type="hidden" name="ID" value="${busContact.ID}">
						<input type="hidden" name="CONTACT_DEPTNAME" value="${busContact.CONTACT_DEPTNAME}">
						<input type="hidden" name="CONTACT_PLATNAME" value="${busContact.CONTACT_PLATNAME}">
				
						<!--==========隐藏域部分结束 ===========-->
						<table style="width:100%;border-collapse:collapse;"
							class="dddl-contentBorder dddl_table">
							<tr style="height:29px;">
								<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
							</tr>
							<tr>
								<td colspan="2"><span style="width: 100px;float:left;text-align:right;">业务单位：</span>
								<input class="easyui-combobox" style="width:350px;" id="BUSSYS_UNITCODECombx"
									name="CONTACT_DEPTCODE" value="${busContact.CONTACT_DEPTCODE}"
									data-options="url:'departmentController.do?load&defaultEmpty=true',editable:false,
									method: 'post',valueField:'UNIT_CODE',textField:'UNIT_NAME',panelWidth: 350,panelHeight: '200',
									onSelect:function(rec){
									   if(rec.UNIT_ID){
									   	  $('#BUSSYS_PLATCODECombx').combobox('clear');
			        			          var url1 = 'busSysController.do?comboxSystems&uintId='+rec.UNIT_ID;
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1); 						   	  
									   }
									   if(rec.UNIT_NAME){
									   	  $('input[name=CONTACT_DEPTNAME]').val(rec.UNIT_NAME);
									   }
									},
									onLoadSuccess:function(){
									  	var unitcode = $('#BUSSYS_UNITCODECombx').combobox('getValue');
									  	if(unitcode != ''){
										  $('#BUSSYS_PLATCODECombx').combobox('clear');
			        			          var url1 = 'busSysController.do?comboxSystemsUnitCode&unitcode='+unitcode;
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('reload',url1);
			    			    		  $('#BUSSYS_PLATCODECombx').combobox('setValue','${busContact.CONTACT_PLATCODE}');
			    			    		}
									}  
								" />
								<font class="dddl_platform_html_requiredFlag">*</font>	
							</td>					
							</tr>
							<tr>
								<td colspan="2"><span style="width: 100px;float:left;text-align:right;">业务系统：</span>
									<input style="width:350px;height:25px;float:left;"
										class="easyui-combobox validate[required]" value="${busContact.CONTACT_PLATCODE}"
										name="CONTACT_PLATCODE" id="BUSSYS_PLATCODECombx"
										data-options="url:'busSysController.do?comboxSystems',
												editable:false,method: 'post',valueField:'SYSTEM_CODE',
												textField:'SYSTEM_NAME',panelWidth: 350,panelHeight: '150',
												onSelect:function(rec){
												   if(rec.SYSTEM_NAME){
												   	  $('input[name=CONTACT_PLATNAME]').val(rec.SYSTEM_NAME);
												   }
												} 
										"/>
									<font class="dddl_platform_html_requiredFlag">*</font>
								</td>					
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">姓名：</span>
									<input type="text" style="width:150px;float:left;" maxlength="5"
									class="eve-input validate[required]" value="${busContact.CONTACT_NAME}"
									name="CONTACT_NAME" /><font class="dddl_platform_html_requiredFlag">*</font></td>
								<td><span style="width: 100px;float:left;text-align:right;">性别：</span>
									<input style="width:150px;height:25px;float:left;"
									class="easyui-combobox"
									value="${busContact.CONTACT_SEX}" name="CONTACT_SEX" id="CONTACT_SEX"
									data-options="url:'dictionaryController.do?load&defaultEmpty=true&amp;typeCode=rensex',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto'" />
									<font class="dddl_platform_html_requiredFlag">*</font>
							    </td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">人员类别：</span>
									<input style="width:150px;height:25px;float:left;"
									class="easyui-combobox validate[required]"
									value="${busContact.CONTACT_TYPE}" name="CONTACT_TYPE"
									data-options="url:'dictionaryController.do?load&typeCode=ContactType',editable:false,method: 'post',valueField:'DIC_CODE',textField:'DIC_NAME',panelWidth: 150,panelHeight: 'auto'" />
									<font
									class="dddl_platform_html_requiredFlag">*</font>
							    </td>
								<td><span style="width: 100px;float:left;text-align:right;">固定电话：</span>
									<input type="text" style="width:150px;float:left;" maxlength="13"
									class="eve-input validate[required],custom[fixPhoneWithAreaCode]"
									value="${busContact.CONTACT_PHONE}" name="CONTACT_PHONE" /><font
									class="dddl_platform_html_requiredFlag">*</font></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
									<input type="text" style="width:150px;float:left;" maxlength="11"
									class="eve-input validate[required],custom[mobilePhone]"
									value="${busContact.CONTACT_MOBILE}" name="CONTACT_MOBILE" /><font
									class="dddl_platform_html_requiredFlag">*</font></td>
								<td><span style="width: 100px;float:left;text-align:right;">电子邮箱：</span>
									<input type="text" style="width:150px;float:left;" maxlength="50"
									class="eve-input validate[required],custom[email]"
									value="${busContact.CONTACT_EMAIL}" name="CONTACT_EMAIL" /><font
									class="dddl_platform_html_requiredFlag">*</font></td>
							</tr>
							<tr>
								<td><span style="width: 100px;float:left;text-align:right;">职务名称：</span>
									<input type="text" style="width:150px;float:left;" maxlength="30"
									class="eve-input validate[required]"
									value="${busContact.CONTACT_POSITION}" name="CONTACT_POSITION" /><font
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

