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
			AppUtil.initWindowForm("DLLKYS_FORM", function(form, valid) {
				if (valid&&purchaseSubmit()) {
					//将提交按钮禁用,防止重复提交
					//var formData = $("#DLLKYS_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("DLLKYS_FORM");
					var url = $("#DLLKYS_FORM").attr("action");
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
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.PERSON_INFOMATIONS }'!=null&&'${materForm.PERSON_INFOMATIONS }'!=''){
					var purchaseJson = '${materForm.PERSON_INFOMATIONS }';
					var purchase = eval(purchaseJson);
					var jsonstr = '{"total":'+purchase.length+',"rows":'+purchaseJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#personInfoGrid').datagrid('loadData',data);
				} 
			}else {
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
		$.extend($.fn.validatebox.defaults.rules, {
			vidcard:{
				validator  : function(value, param) {
					return isChinaIDCard(value);
				},
				message : '请输入正确的身份证号码'
			}
		});
		/**
		*身份证验证
		*/
		function isChinaIDCard(num) {
			if (num == 111111111111111)
				return false;
			num = num.toUpperCase();
			//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
			if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
				return false;
			}
			//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
			//下面分别分析出生日期和校验位
			if (idcard_getAge(num) <= 0) {
				return false;
			}
			var len, re;
			len = num.length;
			if (len == 15) {
				re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
				var arrSplit = num.match(re);
		
				//检查生日日期是否正确 
				var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
				var bGoodDay;
				bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
				if (!bGoodDay) {               
					return false;
				}
				else {
					//将15位身份证转成18位 
					//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
					var nTemp = 0, i;
					num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
					for (i = 0; i < 17; i++) {
						nTemp += num.substr(i, 1) * arrInt[i];
					}
					num += arrCh[nTemp % 11];
					return num;
				}
			}
			if (len == 18) {
				re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
				var arrSplit = num.match(re);
		
				//检查生日日期是否正确 
				var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
				var bGoodDay;
				bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
				if (!bGoodDay) {   
					return false;
				}
				else {
					//检验18位身份证的校验码是否正确。 
					//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
					var valnum;
					var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
					var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
					var nTemp = 0, i;
					for (i = 0; i < 17; i++) {
						nTemp += num.substr(i, 1) * arrInt[i];
					}
					valnum = arrCh[nTemp % 11];
					if (valnum != num.substr(17, 1)) {
						return false;
					}
					return num;
				}
			}        
			return false;
		}
		
		function idcard_getAge(id) {
			var id = String(id);
			var age;
			if (id.length == 15) {
				age = (new Date()).getFullYear()  - (new Date(id.substr(6, 2), id.substr(8, 2) - 1, id.substr(10, 2))).getFullYear();
			} else if (id.length == 18) {
				age = (new Date()).getFullYear()  - (new Date(id.slice(6, 10), id.slice(10, 12) - 1, id.slice(12, 14))).getFullYear();
			} else {
				return false;
			}
			return age;
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="DLLKYS_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="PERSON_INFOMATIONS" value="${materForm.PERSON_INFOMATIONS }"/>
						<div class="syj-title1">
							<span>申请人基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom:none;"><font class="syj-color2">*</font>申请人名称：</th>
									<td colspan="3" style="border-bottom:none;"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="APPLICANT_NAME" value="${materForm.APPLICANT_NAME }"
										placeholder="要求填写企业（公司）全称、个体经营者姓名" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>负责人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="RESPO_NAME" value="${materForm.RESPO_NAME }"
										placeholder="请输入负责人姓名" maxlength="16" /></td>
									<th><font class="syj-color2">*</font>经办人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>通信地址：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="MAILING_ADDR" value="${materForm.MAILING_ADDR }"
										placeholder="请输入通信地址" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										placeholder="请输入邮编" maxlength="6" /></td>
									<th> 电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="APPLY_PHONE" value="${materForm.APPLY_PHONE }"
										placeholder="请输入电话" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>手机：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[mobilePhoneLoose]]" name="APPLY_MOBILE" value="${materForm.APPLY_MOBILE }"
										placeholder="请输入手机号码" maxlength="11" /></td>
									<th> 电子邮箱：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="APPLY_EMAIL" value="${materForm.APPLY_EMAIL }"
										placeholder="请输入电子邮箱" maxlength="32" /></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1  tmargin20">
							<span>申请事项</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>客运站名称：</th>
									<td colspan="3"><input type="text" maxlength="62" placeholder="请输入客运站名称" 
										class="syj-input1 validate[required]" name="STATION_NAME" value="${materForm.STATION_NAME }"
									/></td>
								</tr>
								<tr>
									<th style="border-bottom:none;"><font class="syj-color2">*</font>所在地址：</th>
									<td colspan="3" style="border-bottom:none;"><input type="text" maxlength="62"
									        placeholder="请输入所在地址" 
										class="syj-input1 validate[required]" name="STATION_ADDR" value="${materForm.STATION_ADDR }"
									/></td>
								</tr>
							</table>
							
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>占地面积：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="STATION_COVERD_AREA" value="${materForm.STATION_COVERD_AREA }"
										placeholder="请输入占地面积" /></td>
									<th>客运站建筑面积：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="STATION_BUILD_AREA" value="${materForm.STATION_BUILD_AREA }"
										placeholder="请输入客运站建筑面积" maxlength="32" /></td>
								</tr>
								<tr>
									<th>设计年度日旅客发送量：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="PASSENGER_VOLUME" value="${materForm.PASSENGER_VOLUME }"
										placeholder="请输入设计年度日旅客发送量" /></td>
									<th>竣工时间：</th>
									<td>
										<input type="text" class="Wdate validate[]" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
										 name="COMPLETION_TIME"  placeholder="请选择竣工时间" value="${materForm.COMPLETION_TIME }"/>
								</tr>
								<tr>
									<th>经验收符合的站场级别：</th>
									<td ><input type="text" maxlength="32"
										class="syj-input1 validate[]" name="STATION_LEVEL" value="${materForm.STATION_LEVEL }"
										placeholder="请输入经验收符合的站场级别" /></td>
									<th>验收时间：</th>
									<td>
										<input type="text" class="Wdate validate[" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
										 name="ACCEPTANCE_TIME"  placeholder="请选择验收时间" value="${materForm.ACCEPTANCE_TIME }"/>
								</tr>
								<tr>
                                    <th>拟投入运营时间：</th>
                                    <td>
                                        <input type="text" class="Wdate validate[" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'%y-%M-%d'})" 
                                         name="OPERATION_TIME"  placeholder="请选择运营时间" value="${materForm.OPERATION_TIME }"/>
                                    <th>申请范围：</th>
                                    <td ><input type="text" maxlength="32"
                                        class="syj-input1 validate[]" name="APPLY_SCORE" value="${materForm.APPLY_SCORE }"
                                        placeholder="请输入申请经营范围" /></td>
                                </tr>
							</table>
						</div>
						
						
						
						<div class="syj-title1  tmargin20">
							<span>场地设施</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>站前广场（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="SQUARE_AREA" value="${materForm.SQUARE_AREA }"
									/></td>
									<th>停车场（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="PARKING_AREA" value="${materForm.PARKING_AREA }"
									/></td>
								</tr>
								<tr>
									<th>发车位（个）：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="PARKING_LOT" value="${materForm.PARKING_LOT }"
									/></td>
								</tr>
							</table>
						</div>
						
						
						<div class="syj-title1  tmargin20">
							<span>建筑设施</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>候车厅\室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="WAITING_HALL_AREA" value="${materForm.WAITING_HALL_AREA }"
									/></td>
									<th>重点旅客候车室\区<br>（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ZDWAITING_HALL_AREA" value="${materForm.ZDWAITING_HALL_AREA }"
									/></td>
								</tr>
								<tr>
									<th>售票厅（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="TICKET_HALL_AREA" value="${materForm.TICKET_HALL_AREA }"
									/></td>
									<th>行包托运厅\处<br>（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="BAGGAGE_HALL_AREA" value="${materForm.BAGGAGE_HALL_AREA }"
									/></td>
								</tr>
								
								<tr>
									<th>综合服务处（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="SEARVICE_ROOM" value="${materForm.SEARVICE_ROOM }"
									/></td>
									<th>站务员室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ATTENDANT_ROOM" value="${materForm.ATTENDANT_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>驾乘人员休息室<br>（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="CREW_LOUNGE" value="${materForm.CREW_LOUNGE }"
									/></td>
									<th>调度室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="DISPATCH_ROOM" value="${materForm.DISPATCH_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>治安室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="POLICE_OFFICE" value="${materForm.POLICE_OFFICE }"
									/></td>
									<th>广播室（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="BROADCASTING_ROOM" value="${materForm.BROADCASTING_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>无障碍通道（米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="WHEELCHAIR_ACCESS" value="${materForm.WHEELCHAIR_ACCESS }"
									/></td>
									<th>残疾人服务设施（件）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="CJRFUCS_COUNT" value="${materForm.CJRFUCS_COUNT }"/></td>
								</tr>
								
								<tr>
									<th>智能化系统用房 <br>（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="ZNHXTYF_ROOM" value="${materForm.ZNHXTYF_ROOM }"
									/></td>
									<th>盥洗室和旅客厕所<br>（平方米）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="YSSHLKCS_ROOM" value="${materForm.YSSHLKCS_ROOM }"
									/></td>
								</tr>
								
								<tr>
									<th>办公用房（平方米）：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="BGYF_ROOM" value="${materForm.BGYF_ROOM }"
									/></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1  tmargin20">
							<span>生产辅助用房</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th>汽车安全检验台（个）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="QCAQJCT_COUNT" value="${materForm.QCAQJCT_COUNT }"
									/></td>
									<th>车辆清洁、清洗台（个）：</th>
									<td ><input type="text" maxlength="62"
										class="syj-input1 validate[custom[numberplus]]" name="CLQJQXT_COUNT" value="${materForm.CLQJQXT_COUNT }"
									/></td>
								</tr>
								<tr>
									<th>配电室（平方米）：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[custom[number2plus]]" name="PDS_ROOM" value="${materForm.PDS_ROOM }"
									/></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>基本设备</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="BASICAL_EQUIPMENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="basicalEquipment" value="${materForm.BASICAL_EQUIPMENT }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						
						
						<div class="syj-title1 tmargin20">
							<span>智能系统设备</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<td>
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="INTELLIGENT_EQUIPMENT" width="650px;" clazz="checkbox validate[required]"
											dataParams="intlligentEquipment" value="${materForm.INTELLIGENT_EQUIPMENT }">
										</eve:excheckbox>
									</td>
								</tr>
							</table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>拟聘用专业人员、管理人员情况</span>
						</div>
						<div id="purchaseDiv">
							<div style="position:relative;">
								<div id="purchaseToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addPurchase();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removePurchase();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#purchaseToolbar" id="personInfoGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowPurchase">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,6]'}}"
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
												width="14%">性别</th>
											<th data-options="field:'AGE',align:'left',editor:{type:'numberbox',options:{required:true,min:0,max:999}}"
												width="10%">年龄</th>
											<th data-options="field:'POST',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="10%">工作岗位</th>
											<th data-options="field:'IDCARD',align:'left',editor:{type:'validatebox',options:{required:true,validType:['vidcard']}}"
												width="15%">身份证号码</th>
											<th data-options="field:'JOB',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="10%">职称</th>
											<th data-options="field:'PROFESSIONALNUM',align:'left',editor:{type:'validatebox',options:{required:true,validType:'length[1,16]'}}"
												width="20%">专业证书号码</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#DLLKYS_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
	<script type="text/javascript">
		
	    /**拟聘用专业人员、管理人员情况可编辑表格开始*/
		var editIndexPurchase = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingPurchase(){
	    	if (editIndexPurchase == undefined){return true}
	    	if ($("#personInfoGrid").datagrid("validateRow", editIndexPurchase)){
	    		$("#personInfoGrid").datagrid("endEdit", editIndexPurchase);
	    		editIndexPurchase = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addPurchase(){
	    	if (endEditingPurchase()){
	    		$("#personInfoGrid").datagrid("appendRow",{});
	    		editIndexPurchase = $("#personInfoGrid").datagrid("getRows").length-1;
	    		$("#personInfoGrid").datagrid("selectRow", editIndexPurchase)
	    				.datagrid("beginEdit", editIndexPurchase);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removePurchase() {
	    	if (editIndexPurchase == undefined){return}
	    	$("#personInfoGrid").datagrid("cancelEdit", editIndexPurchase)
	    			.datagrid("deleteRow", editIndexPurchase);
	    	editIndexPurchase = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmPurchase(){
	    	var e = endEditingPurchase();
	        if (e){
	            $("#personInfoGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowPurchase(index){
	    	if (editIndexPurchase != index){
	    		if (endEditingPurchase()){
	    			$("#personInfoGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexPurchase = index;
	    		} else {
	    			$("#personInfoGrid").datagrid("selectRow", editIndexPurchase);
	    		}
	    	}
	    }
	    /**
	     * 提交方法
	     */
	    function purchaseSubmit(){
	    	if(!confirmPurchase()){
	            alert("“拟聘用专业人员、管理人员情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#personInfoGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"NAME":rows[i].NAME,
						"SEX":rows[i].SEX,
						"AGE":rows[i].AGE,
						"POST":rows[i].POST,
						"IDCARD":rows[i].IDCARD,
						"JOB":rows[i].JOB,
						"PROFESSIONALNUM":rows[i].PROFESSIONALNUM
					});
				}
				$("input[type='hidden'][name='PERSON_INFOMATIONS']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**拟聘用专业人员、管理人员情况可编辑表格结束*/
	    
	</script>
</body>