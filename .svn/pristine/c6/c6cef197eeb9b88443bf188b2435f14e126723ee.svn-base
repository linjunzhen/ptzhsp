<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="e" uri="/WEB-INF/tld/e-tags.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<eve:resources loadres="jquery,easyui,laydate,layer,artdialog,swfupload,json2"></eve:resources>
	
    <script type="text/javascript" src="<%=basePath%>/webpage/common/dynamic.jsp"></script>
	<!---引入验证--->
	<link rel="stylesheet" href="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/css/validationEngine.jquery.css" type="text/css"></link>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/validationegine-2.6.2/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/eveutil-1.0/AppUtil.js"></script>
	<link href="<%=path%>/webpage/website/zzhy/css/css.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/webpage/common/css/common.css" />
	<script src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.1.js"></script>

	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/pin-1.1/jquery.pin.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.SuperSlide.2.1.2.js"></script>
	<script type="text/javascript" src="<%=path%>/webpage/website/zzhy/js/jquery.slimscroll.js"></script>
	<!-- my97 begin -->
	<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("RFTAPPLY_FORM", function(form, valid) {
				if (valid&&purchaseSubmit()&&purchasePlanSubmit()&&currentSubmit()&&employeesSubmit()) {
					purchaseSubmit();
					purchasePlanSubmit();
					currentSubmit();
					employeesSubmit();
					//将提交按钮禁用,防止重复提交
					var formData = $("#RFTAPPLY_FORM").serialize();
					var url = $("#RFTAPPLY_FORM").attr("action");
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
								art.dialog.data("backFormInfo",{
									formName : '${materForm.formName }',
				    				recordId:resultJson.jsonString
								});
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
			
			
			$('#RFTAPPLY_FORM').find('input,textarea').prop("readonly", true);
			$('#RFTAPPLY_FORM').find('select').prop("disabled", "disabled");
			$('#RFTAPPLY_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#RFTAPPLY_FORM').find(".laydate-icon").attr('disabled',"disabled");
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.PURCHASE_JSON }'!=null&&'${materForm.PURCHASE_JSON }'!=''){
					var purchaseJson = '${materForm.PURCHASE_JSON }';
					var purchase = eval(purchaseJson);
					var jsonstr = '{"total":'+purchase.length+',"rows":'+purchaseJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#purchaseGrid').datagrid('loadData',data);
				} 
				if('${materForm.PURCHASE_PLAN_JSON }'!=null&&'${materForm.PURCHASE_PLAN_JSON }'!=''){
					var purchasePlanJson = '${materForm.PURCHASE_PLAN_JSON }';
					var purchasePlan = eval(purchasePlanJson);
					var jsonstr = '{"total":'+purchasePlan.length+',"rows":'+purchasePlanJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#purchasePlanGrid').datagrid('loadData',data);
				}  
				if('${materForm.CURR_JSON }'!=null&&'${materForm.CURR_JSON }'!=''){
					var currentJson = '${materForm.CURR_JSON }';
					var current = eval(currentJson);
					var jsonstr = '{"total":'+current.length+',"rows":'+currentJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#currentGrid').datagrid('loadData',data);
				} 
				if('${materForm.PLAN_EMPLOYEES }'!=null&&'${materForm.PLAN_EMPLOYEES }'!=''){
					var employeesJson = '${materForm.PLAN_EMPLOYEES }';
					var employees = eval(employeesJson);
					var jsonstr = '{"total":'+employees.length+',"rows":'+employeesJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#employeesGrid').datagrid('loadData',data);
				}
			}
		});
		
		function downloadDoc(){
			var recordId = '${materForm.RECORD_ID }';
			var fornName = '${materForm.formName }';
			window.location.href=__ctxPath+"/domesticControllerController/downLoadRelatedMater.do?recordId="+recordId
			+"&fornName="+fornName;
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="RFTAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="PURCHASE_JSON" value="${materForm.PURCHASE_JSON }"/>
						<input type="hidden" name="PURCHASE_PLAN_JSON" value="${materForm.PURCHASE_PLAN_JSON }"/>
						<input type="hidden" name="CURR_JSON" value="${materForm.CURR_JSON }"/>
						<input type="hidden" name="PLAN_EMPLOYEES" value="${materForm.PLAN_EMPLOYEES }"/>
						<div class="syj-title1">
							<a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
							<span>申请人基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>申请人名称：</th>
									<td colspan="3" style="border-bottom: none;"><input type="text"
										class="syj-input1 validate[required]" name="APPLICANT_NAME" value="${materForm.APPLICANT_NAME }"
										placeholder="要求填写企业（公司）全称、企业预先核准全称或个体经营者姓名" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th><font class="syj-color2">*</font>经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="请输入邮编" maxlength="16" /></td>
									<th> 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="16" /></td>
									<th> 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="16" /></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>申请许可内容</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="text-align: left;">拟申请的道路货物运输经营范围或拟申请扩大的道路货物运输经营范围：</th>
								</tr>
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="APPLY_CONTENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="tranScope" value="${materForm.APPLY_CONTENT }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th style="text-align: left;">如拟申请扩大道路货物运输经营范围，请选择现有的经营范围：</th>
								</tr>
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="CURR_CONTENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="tranScope" value="${materForm.CURR_CONTENT }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>货物运输车辆信息</span>
						</div>
						<div class="syj-title1 tmargin20">
							<span>已购置货物运输车辆情况</span>
						</div>
						<div id="purchaseDiv">
							<div style="position:relative;">
								<div id="purchaseToolbar">
								</div>
								<table class="easyui-datagrid" toolbar="#purchaseToolbar" id="purchaseGrid" rownumbers="true" border="true" fitColumns="true"
									 nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="30%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">数量</th>
											<th data-options="field:'DEAD_WEIGHT',align:'left',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="10%">载重质量（吨）</th>
											<th data-options="field:'TECH_LEVEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">车辆技术等级</th>
											<th data-options="field:'OUT_SIZE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="29%">外廓尺寸（毫米）</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<div class="syj-title1 tmargin20">
							<span>拟购置货物运输车辆情况</span>
						</div>
						<div id="purchasePlanDiv">
							<div style="position:relative;">
								<div id="purchasePlanToolbar">
								</div>
								<table class="easyui-datagrid" toolbar="#purchasePlanToolbar" id="purchasePlanGrid" rownumbers="true" border="true" fitColumns="true"
									 nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="30%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">数量</th>
											<th data-options="field:'DEAD_WEIGHT',align:'left',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="10%">载重质量（吨）</th>
											<th data-options="field:'TECH_LEVEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">车辆技术等级</th>
											<th data-options="field:'OUT_SIZE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="29%">外廓尺寸（毫米）</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>现有运输车辆情况</span>
						</div>
						<div id="currentDiv">
							<div style="position:relative;">
								<div id="currentToolbar">
								</div>
								<table class="easyui-datagrid" toolbar="#currentToolbar" id="currentGrid" rownumbers="true" border="true" fitColumns="true"
									 nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'TRAN_CERT',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">道路运输证号</th>
											<th data-options="field:'BRAND_MODEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="25%">厂牌型号</th>
											<th data-options="field:'AMOUNT',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">数量</th>
											<th data-options="field:'DEAD_WEIGHT',align:'left',editor:{type:'numberbox',options:{required:true,min:0,precision:2}}"
												width="10%">载重质量（吨）</th>
											<th data-options="field:'TECH_LEVEL',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">车辆技术等级</th>
											<th data-options="field:'OUT_SIZE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="19%">外廓尺寸（毫米）</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>拟聘用营运货车驾驶员情况</span>
						</div>
						<div id="employeesDiv">
							<div style="position:relative;">
								<div id="employeesToolbar">
								</div>
								<table class="easyui-datagrid" toolbar="#employeesToolbar" id="employeesGrid" rownumbers="true" border="true" fitColumns="true"
									 nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="20%">姓名</th>
											<th data-options="field:'SEX',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:60,
											data:[  
												{'id':'1','text':'男'},  
												{'id':'2','text':'女'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='1') return '男';
											        if(value=='2') return '女';
												}"
												width="15%">性别</th>
											<th data-options="field:'AGE',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">年龄</th>
											<th data-options="field:'TAKE_TIME',align:'left',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="20%">取得驾驶证时间</th>
											<th data-options="field:'CERT_NO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">从业资格证号</th>
											<th data-options="field:'CERT_TYPE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="19%">从业人员资格类型</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>