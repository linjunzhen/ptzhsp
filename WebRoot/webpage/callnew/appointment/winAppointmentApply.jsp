<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		$("#zjhm").addClass(",custom[vidcard]");
		AppUtil.initWindowForm("AppointmentApplyForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#AppointmentApplyForm").serialize();
				var url = $("#AppointmentApplyForm").attr("action");
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
							parent.$("#NewAppointGrid").datagrid("reload");
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
		},null);
	});
	
	function loadtimelist(departId,wDate){
		AppUtil.ajaxProgress({
			url : "appointmentController.do?loadBespeakTimeList",
			params : {
				departId:departId,
				wDate:wDate
			},
			callback : function(resultJson) {
				console.log(resultJson)
				if(resultJson){
					var html = "";
					for(var i=0;i<resultJson.length;i++){
						html+="<input type=\"radio\" name=\"TIMESET_ID\" ";
						if(i>0){
							html+="style=\"margin-left:104px;\" ";
						}
						if(resultJson[i].isOverTime==0||resultJson[i].ALLOW_BESPEAK_NUMBER <= resultJson[i].bespeakNumber){
							html+="disabled=true ";
						}
						html+=" value=\""+resultJson[i].RECORD_ID+"\">"+resultJson[i].BEGIN_TIME+"-"+resultJson[i].END_TIME+"("+resultJson[i].bespeakNumber+"/"+resultJson[i].ALLOW_BESPEAK_NUMBER+")<br>";
					}
					$("#timelist").html(html);
				}
			}
		});
	}
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="AppointmentApplyForm" method="post" action="appointmentController.do?saveAppointApply">
	    <div id="AppointmentApplyFormDiv" data-options="region:'center',split:true,border:false">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="businessCode" >
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<!-- <tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr> -->
				<tr>
					<td colspan="2"><span
						style="width: 100px;float:left;text-align:right;">姓名：</span> <input
						type="text" style="width:150px;float:left;"
						maxlength="8" 
						class="eve-input validate[required]" name="userName" /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">身份证号：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="18" id="zjhm"
						class="eve-input validate[required]"
						name="cardNo" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">手机号码：</span>
						<input type="text" style="width:150px;float:left;"
						maxlength="11" id="mobile"
						class="eve-input validate[required,custom[mobilePhoneLoose]]"
						name="mobile" /> <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">服务部门：</span> 
						<input name="depart_id" id="depart_id" class="easyui-combobox" style="width:182px; height:26px;" 
						data-options="
			                url:'appointmentController.do?loadDepart',
			                method:'post',
			                valueField:'DEPART_ID',
			                textField:'DEPART_NAME',
			                panelHeight:'200',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   $('#BUSINESS_CODE').combobox('clear');
							   if(rec.DEPART_ID){				                   
							       var url = 'appointmentController.do?loadBusiness&departId='+rec.DEPART_ID;
							       $('#BUSINESS_CODE').combobox('reload',url);  
							   }
							}
	                " /> <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">预约业务：</span> 
						<input name="BUSINESS_CODE" id="BUSINESS_CODE" class="easyui-combobox" style="width:182px; height:26px;" 
						data-options="
			                url:'appointmentController.do?loadBusiness&departId=0',
			                method:'post',
			                valueField:'BUSINESS_CODE',
			                textField:'BUSINESS_NAME',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   var url='appointmentController.do?loadDate';
							   $('#DATE_TIME').combobox('reload',url);  
							}
	                " />  <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td><span
						style="width: 100px;float:left;text-align:right;">预约日期：</span> 
						<input name="DATE_TIME" id="DATE_TIME" class="easyui-combobox" style="width:182px; height:26px;" 
						data-options="
			                url:'',
			                method:'post',
			                valueField:'W_DATE',
			                textField:'W_DATE',
			                panelHeight:'auto',
			                required:true,
			                editable:false,
			                onSelect:function(rec){
							   var departId = $('#depart_id').combobox('getValue');
							   loadtimelist(departId,rec.W_DATE);
							}
	                " />  <font
						class="dddl_platform_html_requiredFlag">*</font></td>
				</tr>
				<tr>
					<td id="timelist"><span
						style="width: 100px;float:left;text-align:right;">预约时段：</span> 
					</td>
			</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
<OBJECT id="LODOP_OB" CLASSID="CLSID:2105C259-1E0C-4534-8141-A753534CB4CA" WIDTH=0 HEIGHT=0> 
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</OBJECT>
