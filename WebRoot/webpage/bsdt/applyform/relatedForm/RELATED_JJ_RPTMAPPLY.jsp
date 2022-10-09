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
	<script type="text/javascript" src="plug-in/eveflowdesign-1.0/js/FlowUtil.js"></script>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("RDGTAPPLY_FORM", function(form, valid) {
				if (valid && carConditionSubmit() && employeesSubmit()) {
					carConditionSubmit();
					employeesSubmit();
					//将提交按钮禁用,防止重复提交
					//var formData = $("#RDGTAPPLY_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("RDGTAPPLY_FORM");
					var url = $("#RDGTAPPLY_FORM").attr("action");
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
			
			$('#RDGTAPPLY_FORM').find('input,textarea').prop("readonly", true);
			$('#RDGTAPPLY_FORM').find('select').prop("disabled", "disabled");
			$('#RDGTAPPLY_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#RDGTAPPLY_FORM').find(".laydate-icon").attr('disabled',"disabled");
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.CAR_CONDITION_JSON }'!=null&&'${materForm.CAR_CONDITION_JSON }'!=''){
					var carconditionJson = '${materForm.CAR_CONDITION_JSON }';
					var carcondition = eval(carconditionJson);
					var jsonstr = '{"total":'+carcondition.length+',"rows":'+carconditionJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#carConditionGrid').datagrid('loadData',data);
				} 
				if('${materForm.PLAN_EMPLOYEES }'!=null&&'${materForm.PLAN_EMPLOYEES }'!=''){
					var planemplyeesJson = '${materForm.PLAN_EMPLOYEES }';
					var planemplyees = eval(planemplyeesJson);
					var jsonstr = '{"total":'+planemplyees.length+',"rows":'+planemplyeesJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#employeesGrid').datagrid('loadData',data);
				} 
				
			}else{
				//企业名称
				var APPLICANT_NAME = parent.$("input[name='COMPANY_NAME']").val();
				$("input[name='APPLICANT_NAME']").val(APPLICANT_NAME);
				//邮政编码
				var POST_CODE = parent.$("input[name='POST_CODE']").val();
				$("input[name='POST_CODE']").val(POST_CODE);
				var itemCode = parent.$("input[name='ITEM_CODE']").val();
				var APPLY_PHONE = "";
				var MAILING_ADDR = "";			
				var handlerName = "";
				if(itemCode=='201605170002XK00101'||itemCode=='201605170002XK00102'){
					APPLY_PHONE = parent.$("input[name='CONTACT_PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSSINESS_ADDR']").val();
					handlerName = parent.$("input[name='OPERATOR_NAME']").val();
				}else if(itemCode=='201605170002XK00103'){
					APPLY_PHONE = parent.$("input[name='PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSINESS_ADDR']").val();
				}
				//联系电话
				$("input[name='APPLY_PHONE']").val(APPLY_PHONE);
				//生产地址/通信地址
				$("input[name='MAILING_ADDR']").val(MAILING_ADDR);
				//经办人
				$("input[name='HANDLER_NAME']").val(handlerName);
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
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="RDGTAPPLY_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="CAR_CONDITION_JSON" value="${materForm.CAR_CONDITION_JSON }"/>
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
										placeholder="要求填写企业（公司）全称、企业预先核准全称" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th ><font class="syj-color2">*</font>负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th ><font class="syj-color2">*</font>经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="请输入邮编" maxlength="6" /></td>
									<th > 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="11" /></td>
									<th > 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="32" /></td>
								</tr>
								<th ><font class="syj-color2">*</font>经营许可证编号：</th>
								<td colspan="3"><input type="text" class="syj-input1 validate[required]" name="BUSS_CODE" value="${materForm.BUSS_CODE }"
									placeholder="" maxlength="30" /></td>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>已获得道路班车客运经营许可的经营者，申请新增客运班线的，需填写下列内容（请在  □内划√）</span>
						</div>
						
						<div style="position:relative;">
						<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th >已获许可经营范围：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="BUSS_SCOPE" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPortR" value="${materForm.BUSS_SCOPE }">
										</eve:excheckbox>
									</td>
								</tr>
								
								<tr>
									<th >已获许可客运班线类型：</th>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="TRAINLINE_TYPE" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPortL" value="${materForm.TRAINLINE_TYPE }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						
						<!--现有营运客车情况-->
						<div class="syj-title1 tmargin20">
							<span>现有营运客车情况</span>
						</div>
						
						<div style="position:relative;">
						<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="text-align: right;width:17%"></th>
									<th style="text-align: center;width:28%">总数</th>
									<th style="text-align: center;width:27%">高级客车</th>
									<th style="text-align: center;width:28%">中级客车</th>
								</tr>
								<tr>
									<th >客车数（辆）</th>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="BUS_NUM" value="${materForm.BUS_NUM }"
										maxlength="4" />
									</td>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="SBUS_NUM" value="${materForm.SBUS_NUM }"
										maxlength="4" />
									</td>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="IBUS_NUM" value="${materForm.IBUS_NUM }"
										maxlength="4" />
									</td>
								</tr>
								
								<tr>
									<th >座位数（个）</th>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="SEAT_NUM" value="${materForm.SEAT_NUM }"
										maxlength="4" />
									</td>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="SBUS_SEATNUM" value="${materForm.SBUS_SEATNUM }"
										maxlength="4" />
									</td>
									<td>
										<input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="IBUS_SEATNUM" value="${materForm.IBUS_SEATNUM }"
										maxlength="4" />
									</td>
								</tr>
							</table>
						</div>
						<!--现有营运客车情况-->
						
						<!--申请许可客运班线情况-->
						
						<div class="syj-title1 tmargin20">
							<span>申请许可客运班线情况（请在  □内划√）</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th ><font class="syj-color2">*</font>始发地：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="STARTLOCATION" value="${materForm.STARTLOCATION }"
										placeholder="请输入始发地" maxlength="128"/></td>
								</tr>
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>终到地：</th>
									<td colspan="3" style="border-bottom: none;"><input type="text"
										class="syj-input1 validate[required]" name="ENDLOCATION" value="${materForm.ENDLOCATION }"
										placeholder="请输入终到地" maxlength="128"/></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th ><font class="syj-color2">*</font>拟始发地客运站：</th>
									<td><input type="text"
										class="syj-input1" name="STARTSTATION" value="${materForm.STARTSTATION }"
										placeholder="请输入拟始发地客运站" maxlength="128" /></td>
									<th style="text-align: right;">是否已签意向书</th>
									<td><input type="checkbox" <c:if test="${materForm.ISLETTER_INTENT==1 }">checked="checked"</c:if>
										onclick="if(this.checked)$('#ISLETTER_INTENT').val('1');else $('#ISLETTER_INTENT').val('0');"/>
										<input type="hidden" id="ISLETTER_INTENT" name="ISLETTER_INTENT" value="${materForm.ISLETTER_INTENT}"/> 
									</td>
										
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>拟终到地客运站：</th>
									<td><input type="text"
										class="syj-input1" name="ENDSTATION" value="${materForm.ENDSTATION }"
										placeholder="请输入拟终到地客运站" maxlength="128" /></td>
									<th style="text-align: right;"> 是否已签意向书</th>
									<td><input type="checkbox"
									<c:if test="${materForm.ISLETTER_INTENT2==1 }">checked="checked"</c:if>
										 onclick="if(this.checked)$('#ISLETTER_INTENT2').val('1');else $('#ISLETTER_INTENT2').val('0');"/>
									<input type="hidden" id="ISLETTER_INTENT2" name="ISLETTER_INTENT2" value="${materForm.ISLETTER_INTENT2}"/> 
									</td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>途径主要地点：</th>
									<td colspan="3"><input type="text"
										class="syj-input1" name="ROUTESTATION" value="${materForm.ROUTESTATION }"
										placeholder="请输入途径主要地点" maxlength="128" /></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>途中停靠站点：</th>
									<td colspan="3"><input type="text"
										class="syj-input1" name="ROUTECALLSTATION" value="${materForm.ROUTECALLSTATION }"
										placeholder="请输入途中停靠站点" maxlength="128"/></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>营运里程(公里)：</th>
											<td colspan="3"><input type="text"
												class="syj-input1 validate[required,custom[number2plus]]" name="OPERATING_MILEAGE" value="${materForm.OPERATING_MILEAGE }"
												placeholder="请输入营运里程" maxlength="12"/></td>
										</tr>
										<tr>
											<th ><font class="syj-color2">*</font>高速公路里程(公里)：</th>
											<td><input type="text"
												class="syj-input1 validate[required,custom[number2plus]]" name="EXPRESSWAY_MILEAGE" value="${materForm.EXPRESSWAY_MILEAGE }"
												placeholder="请输入高速公路里程" maxlength="12"/></td>
											<th >占总营运里程:</th>
											<td><input type="text"
												class="syj-input1 validate[required,custom[number2plus]]" name="PERCENTAGE_MILEAGE" value="${materForm.PERCENTAGE_MILEAGE }"
												placeholder="请输入占总营运里程" maxlength="12"  style="width:70%;"/>%</td>
										</tr>
								<tr>
									<th ><font class="syj-color2">*</font>日发班次(个)：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="DAILY_FLIGHT" 
										value="${materForm.DAILY_FLIGHT }" placeholder="请输入日发班次" maxlength="5"/></td>								
									<th ><font class="syj-color2">*</font>申请经营期限(年)：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]]" name="OPERATING_PERIOD" 
										value="${materForm.OPERATING_PERIOD }" placeholder="请输入申请经营期限" maxlength="5"/></td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>客运班线类型：</th>
									<td colspan="3">
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="TRAINLINE_TYPET" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPortL" value="${materForm.TRAINLINE_TYPET }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th ><font class="syj-color2">*</font>班车类别：</th>
									<td colspan="3">
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="BUSSCLASS" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPortBL" value="${materForm.BUSSCLASS }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						<!--申请许可客运班线情况-->
						
						
						<!--    拟投入营运客车情况-->
						<div class="syj-title1 tmargin20">
							<span>拟投入营运客车情况</span>
						</div>
						<div id="purchasePlanDiv">
							<div style="position:relative;">
								<div id="carConditionToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addCarCondition();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeCarCondition();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#carConditionToolbar" id="carConditionGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowCarCondition" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'BRAND_MODEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="10%">厂牌型号</th>
											<th data-options="field:'NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">数量</th>
											<th data-options="field:'SEAT_NUM',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="10%">座位数（个）</th>
											<th data-options="field:'CAR_TYPELEVEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="15%">车辆类型及等级</th>
											<th data-options="field:'CAR_TECHLEVEL',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="15%">车辆技术等级</th>
											<th data-options="field:'CAR_WIDTH',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="15%">车辆外廓长宽高</th>
											<th data-options="field:'ISNX',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:50,
											data:[  
												{'id':'0','text':'拟购'},  
												{'id':'1','text':'现有'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='0') return '拟购';
											        if(value=='1') return '现有';
												}"
												width="29%">拟购还是现有</th>
									</thead>
								</table>
							</div>
						</div>
						<!--    拟投入营运客车情况-->
						
						<!--经营方式-->
						<div class="syj-title1 tmargin20">
							<span>经营方式</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th ><font class="syj-color2">*</font>经营方式：</th>
									<td colspan="3">
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="MANAGEMENT_MODE" width="650px;" clazz="checkbox validate[]"
											dataParams="passTransPortJT" value="${materForm.MANAGEMENT_MODE}">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						<!--经营方式-->
						
						
						<!--对开客运经营者名称-->
						<div class="syj-title1 tmargin20">
							<span>对开客运经营者名称（如果有）</span>
						</div>
						
						<div style="position:relative;">
						<table cellpadding="0" cellspacing="0" class="syj-table2">
							<th >对开客运经营者名称：</th>
								<td>
									<input type="text"
										class="syj-input1" name="OPERATOR_NAME" value="${materForm.OPERATOR_NAME}"
										placeholder="请输入对开客运经营者名称" maxlength="128" />
								</td>
						</table>
						</div>
						<!--现有营运客车情况-->
						
						
						<!--拟聘用营运客车驾驶员情况-->
						<div class="syj-title1 tmargin20">
							<span>拟聘用营运客车驾驶员情况</span>
						</div>
						
						<div id="driveruserDiv">
							<div style="position:relative;">
								<div id="employeesToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addEmployees();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeEmployees();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#employeesToolbar" id="employeesGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowEmployees" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'USERNAME',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="10%">姓名</th>
											<th data-options="field:'SEX',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:50,
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
												width="10%">性别</th>
											<th data-options="field:'AGE',align:'center',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="5%">年龄</th>
											<th data-options="field:'GETTIME',align:'center',editor:{type:'datebox',options:{required:true,editable:false}}"
												width="15%">取得相应驾驶证时间</th>
											<th data-options="field:'QUCODE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="15%">从业资格证号</th>
											<th data-options="field:'QUTYPE',align:'center',editor:{type:'validatebox',options:{required:true}}"
												width="15%">从业资格证类型</th>
											<th data-options="field:'ISTRAACC',align:'center',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:50,
											data:[  
												{'id':'0','text':'是'},  
												{'id':'1','text':'否'}  
												],
												valueField:'id',  
												textField:'text'}},
												formatter:function(value,row){
											        if(value=='0') return '是';
											        if(value=='1') return '否';
												}"
												width="29%">三年内是否发生重大以上交通责任事故</th>
									</thead>
								</table>
							</div>
						</div>
						<!--拟聘用营运客车驾驶员情况-->
					</form>
				</div>
			</div>
		</div>
		
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#RDGTAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
	
	<script type="text/javascript">
		 /**拟购置可编辑表格开始*/
		var editIndexCarCondition = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingCarCondition(){
	    	if (editIndexCarCondition == undefined){return true}
	    	if ($("#carConditionGrid").datagrid("validateRow", editIndexCarCondition)){
	    		$("#carConditionGrid").datagrid("endEdit", editIndexCarCondition);
	    		editIndexCarCondition = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addCarCondition(){
	    	if (endEditingCarCondition()){
	    		$("#carConditionGrid").datagrid("appendRow",{});
	    		editIndexCarCondition = $("#carConditionGrid").datagrid("getRows").length-1;
	    		$("#carConditionGrid").datagrid("selectRow", editIndexCarCondition)
	    				.datagrid("beginEdit", editIndexCarCondition);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeCarCondition() {
	    	if (editIndexCarCondition == undefined){return}
	    	$("#carConditionGrid").datagrid("cancelEdit", editIndexCarCondition)
	    			.datagrid("deleteRow", editIndexCarCondition);
	    	editIndexCarCondition = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmCarCondition(){
	    	var e = endEditingCarCondition();
	        if (e){
	            $("#carConditionGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowCarCondition(index){
	    	if (editIndexCarCondition != index){
	    		if (endEditingCarCondition()){
	    			$("#carConditionGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexCarCondition = index;
	    		} else {
	    			$("#carConditionGrid").datagrid("selectRow", editIndexCarCondition);
	    		}
	    	}
	    }
	    /**
	     * 提交方法
	     */
	    function carConditionSubmit(){
	    	if(!confirmCarCondition()){
	            alert("“拟投入营运客车情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#carConditionGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					 datas.push({
					 	"BRAND_MODEL":rows[i].BRAND_MODEL,
						"NUM":rows[i].NUM,
						"SEAT_NUM":rows[i].SEAT_NUM,
						"CAR_TYPELEVEL":rows[i].CAR_TYPELEVEL,
						"CAR_TECHLEVEL":rows[i].CAR_TECHLEVEL,
						"CAR_WIDTH":rows[i].CAR_WIDTH,
						"ISNX":rows[i].ISNX
					}); 
				}
				$("input[type='hidden'][name='CAR_CONDITION_JSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**拟购置可编辑表格结束*/
	    
	     /**驾驶员信息开始*/
		var editIndexEmployees = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingEmployees(){
	    	if (editIndexEmployees == undefined){return true}
	    	if ($("#employeesGrid").datagrid("validateRow", editIndexEmployees)){
	    		$("#employeesGrid").datagrid("endEdit", editIndexEmployees);
	    		editIndexEmployees = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addEmployees(){
	    	if (endEditingEmployees()){
	    		$("#employeesGrid").datagrid("appendRow",{});
	    		editIndexEmployees = $("#employeesGrid").datagrid("getRows").length-1;
	    		$("#employeesGrid").datagrid("selectRow", editIndexEmployees)
	    				.datagrid("beginEdit", editIndexEmployees);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeEmployees() {
	    	if (editIndexEmployees == undefined){return}
	    	$("#employeesGrid").datagrid("cancelEdit", editIndexEmployees)
	    			.datagrid("deleteRow", editIndexEmployees);
	    	editIndexEmployees = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmEmployees(){
	    	var e = endEditingEmployees();
	        if (e){
	            $("#employeesGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowEmployees(index){
	    	if (editIndexEmployees != index){
	    		if (endEditingEmployees()){
	    			$("#employeesGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexEmployees = index;
	    		} else {
	    			$("#employeesGrid").datagrid("selectRow", editIndexEmployees);
	    		}
	    	}
	    }
	    
	    /**
	     * 提交方法
	     */
	    function employeesSubmit(){
	    	if(!confirmEmployees()){
	            alert("“拟聘用营运货车驾驶员情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#employeesGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"USERNAME":rows[i].USERNAME,
						"SEX":rows[i].SEX,
						"AGE":rows[i].AGE,
						"GETTIME":rows[i].GETTIME,
						"QUCODE":rows[i].QUCODE,
						"QUTYPE":rows[i].QUTYPE,
						"ISTRAACC":rows[i].ISTRAACC
					});
				}
				$("input[type='hidden'][name='PLAN_EMPLOYEES']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**驾驶员信息结束*/
	    
	</script>
</body>