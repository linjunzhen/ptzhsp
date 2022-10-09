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
			AppUtil.initWindowForm("FOODLIC_FORM", function(form, valid) {
				if (valid&&techSubmit()&&manageSubmit()&&employeeSubmit()&&equipSubmit()) {
					//将提交按钮禁用,防止重复提交
					var formData = FlowUtil.getFormEleData("FOODLIC_FORM");
					//var formData = $("#FOODLIC_FORM").serialize();
					var url = $("#FOODLIC_FORM").attr("action");
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
			
			
			$('#FOODLIC_FORM').find('input,textarea').prop("readonly", true);
			$('#FOODLIC_FORM').find('select').prop("disabled", "disabled");
			$('#FOODLIC_FORM').find(":radio,:checkbox").attr('disabled',"disabled");
			$('#FOODLIC_FORM').find(".laydate-icon").attr('disabled',"disabled");
			
			if(${materForm.RECORD_ID!=null }){
				var MAIN_BUSINESS='${materForm.MAIN_BUSINESS}';
				$("input[name='MAIN_BUSINESS']").each(function(){
					var val = $(this).val();
					if(MAIN_BUSINESS.indexOf(val)!='-1'){
						$(this).attr("checked","checked");
					}
				});
				var BUS_PROJECT='${materForm.BUS_PROJECT}';
				$("input[name='BUS_PROJECT']").each(function(){
					var val = $(this).val();
					if(BUS_PROJECT.indexOf(val)!='-1'){
						$(this).attr("checked","checked");
						if(val=='04')$("input[name='OTHER_FOOD_SALE']").attr("disabled",false);
						if(val=='10')$("input[name='OTHER_FOOD_MANU']").attr("disabled",false);
					}
				});
				var IS_NETWORK='${materForm.IS_NETWORK}';
				$("input[name='IS_NETWORK'][value='"+IS_NETWORK+"']").attr("checked","checked");
				if(IS_NETWORK==1){
					$("input[name='SITE_URL']").attr("disabled",false);
				}
				var IS_STORE='${materForm.IS_STORE}';
				$("input[name='IS_STORE'][value='"+IS_STORE+"']").attr("checked","checked");
				var IS_CENTRAL_KITCHEN='${materForm.IS_CENTRAL_KITCHEN}';
				$("input[name='IS_CENTRAL_KITCHEN'][value='"+IS_CENTRAL_KITCHEN+"']").attr("checked","checked");
				var IS_TOGETHER_DELIVERY='${materForm.IS_TOGETHER_DELIVERY}';
				$("input[name='IS_TOGETHER_DELIVERY'][value='"+IS_TOGETHER_DELIVERY+"']").attr("checked","checked");
				var IS_VENDING='${materForm.IS_VENDING}';
				$("input[name='IS_VENDING'][value='"+IS_VENDING+"']").attr("checked","checked");
				var IS_EDU='${materForm.IS_EDU}';
				$("input[name='IS_EDU'][value='"+IS_EDU+"']").attr("checked","checked");
				var IS_BULK_SALE='${materForm.IS_BULK_SALE}';
				$("input[name='IS_BULK_SALE'][value='"+IS_BULK_SALE+"']").attr("checked","checked");
				var IS_BREWED_SALE='${materForm.IS_BREWED_SALE}';
				$("input[name='IS_BREWED_SALE'][value='"+IS_BREWED_SALE+"']").attr("checked","checked");
								
				if('${materForm.SECURITY_TECHJSON }'!=null&&'${materForm.SECURITY_TECHJSON }'!=''){
					var techJson = '${materForm.SECURITY_TECHJSON }';
					var tech = eval(techJson);
					var jsonstr = '{"total":'+tech.length+',"rows":'+techJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#techGrid').datagrid('loadData',data);
					
			    	var rows = $("#techGrid").datagrid("getRows");
			    	if(rows.length > 0){
						var techAutoIndex = 0;
			    		techTime = setInterval(function(){
							onClickRowTech(techAutoIndex);
							techAutoIndex++;
							if(techAutoIndex>=rows.length){							
			    				setTimeout("endEditingTech()",100);
			    				$('#techGrid').datagrid('clearSelections'); 
								clearInterval(techTime);
							}
			    		}, 100);
					}
				}
				if('${materForm.SECURITY_MANJSON }'!=null&&'${materForm.SECURITY_MANJSON }'!=''){
					var manageJson = '${materForm.SECURITY_MANJSON }';
					var manage = eval(manageJson);
					var jsonstr = '{"total":'+manage.length+',"rows":'+manageJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#manageGrid').datagrid('loadData',data);
					
			    	var rows = $("#manageGrid").datagrid("getRows");
			    	if(rows.length > 0){
						var manageAutoIndex = 0;
			    		manageTime = setInterval(function(){
							onClickRowManage(manageAutoIndex);
							manageAutoIndex++;
							if(manageAutoIndex>=rows.length){							
			    				setTimeout("endEditingManage()",100);
			    				$('#manageGrid').datagrid('clearSelections'); 
								clearInterval(manageTime);
							}
			    		}, 100);
					}
				}
				if('${materForm.EMPLOYEEJSON }'!=null&&'${materForm.EMPLOYEEJSON }'!=''){
					var employeeJson = '${materForm.EMPLOYEEJSON }';
					var employee = eval(employeeJson);
					var jsonstr = '{"total":'+employee.length+',"rows":'+employeeJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#employeeGrid').datagrid('loadData',data);
					
			    	var rows = $("#employeeGrid").datagrid("getRows");
			    	if(rows.length > 0){
						var employeeAutoIndex = 0;
			    		employeeTime = setInterval(function(){
							onClickRowEmployee(employeeAutoIndex);
							employeeAutoIndex++;
							if(employeeAutoIndex>=rows.length){							
			    				setTimeout("endEditingEmployee()",100);
			    				$('#employeeGrid').datagrid('clearSelections'); 
								clearInterval(employeeTime);
							}
			    		}, 100);
					}
				}
				if('${materForm.EQUIPMENTJSON }'!=null&&'${materForm.EQUIPMENTJSON }'!=''){
					var equipJson = '${materForm.EQUIPMENTJSON }';
					var equip = eval(equipJson);
					var jsonstr = '{"total":'+equip.length+',"rows":'+equipJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#equipGrid').datagrid('loadData',data);
				}
			}
			//初始化经营项目选择联动
			$("input[name='BUS_PROJECT']").click(function(){
				var val = $(this).val();
				if($("input[name='BUS_PROJECT'][value='"+val+"']").is(":checked")){
					if(val.indexOf("-")=='-1'){
						if(val=='04'){
							$("input[name='OTHER_FOOD_SALE']").prop("disabled",false);
						}else if(val=='10'){
							$("input[name='OTHER_FOOD_MANU']").prop("disabled",false);
						}else{
							var arrChk = $("input[name='BUS_PROJECT']");
							$(arrChk).each(function(){
								if((this.value).indexOf(val+"-")!='-1')
									$(this).prop("checked",true);
							});
						}
					}else{
						var pval = val.substring(0,val.lastIndexOf("-"));
						$("input[name='BUS_PROJECT'][value='"+pval+"']").prop("checked",true);
					}
				}else{
					if(val.indexOf("-")=='-1'){					
						if(val=='04'){
							$("input[name='OTHER_FOOD_SALE']").prop("disabled",true);
						}else if(val=='10'){
							$("input[name='OTHER_FOOD_MANU']").prop("disabled",true);
						}else{
							var arrChk = $("input[name='BUS_PROJECT']");
							$(arrChk).each(function(){
								if((this.value).indexOf(val+"-")!='-1')
									$(this).prop("checked",false);
							});
						}
					}else{
						var pval = val.substring(0,val.lastIndexOf("-"));						
						var arrChk = $("input[name='BUS_PROJECT']");
						var flag = true;
						$(arrChk).each(function(){
							if((this.value).indexOf(pval+"-")!='-1'){
								if($(this).is(":checked")){
									flag = false;
									return false;
								}
							}
						});
						if(flag){							
							$("input[name='BUS_PROJECT'][value='"+pval+"']").prop("checked",false);
						}
					}
				}
			});
		});
		$.extend($.fn.validatebox.defaults.rules, {
			vidcard:{
				validator  : function(value, param) {
					return isChinaIDCard(value);
				},
				message : '请输入正确的身份证号码'
			},
			mobilePhoneLoose:{
				validator  : function(value, param) {				
					var regex=/^(13[0-9]|15[012356789]|17[03678]|18[0-9]|14[57])[0-9]{8}$/;
					return regex.test(value);
				},
				message : '请输入正确的手机号码'
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
		
		
		//选择证件类型为身份证时添加证件号码验证
		function setZjValidate(zjlx,name){
			if(zjlx=="SF"){
				$("input[name='"+name+"']").addClass(",custom[vidcard]");
			}else{
				$("input[name='"+name+"']").removeClass(",custom[vidcard]");
			}
		}
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
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="FOODLIC_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<input type="hidden" name="SECURITY_TECHJSON" value="${materForm.SECURITY_TECHJSON }"/>
						<input type="hidden" name="SECURITY_MANJSON" value="${materForm.SECURITY_MANJSON }"/>
						<input type="hidden" name="EMPLOYEEJSON" value="${materForm.EMPLOYEEJSON }"/>
						<input type="hidden" name="EQUIPMENTJSON" value="${materForm.EQUIPMENTJSON }"/>
						
						<div class="syj-title1">
							<a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
							<span>申请信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>经营者名称：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="BUSINESS_DEALER" value="${materForm.BUSINESS_DEALER }"
										placeholder="请输入经营者名称" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>社会信用代码/身份证号码：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="SOCIAL_CREDITUNICODE" value="${materForm.SOCIAL_CREDITUNICODE }"
										placeholder="请输入社会信用代码（身份证号码）" maxlength="32" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>住所：</th>
									<td colspan="3">
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="ABODE_PROVINCE" value="${materForm.ABODE_PROVINCE }"/>省(区/市)
										<input type="text" maxlength="8" style="width:7%;"
											class="syj-input1 validate[required]" name="ABODE_CITY" value="${materForm.ABODE_CITY }"/>市(区/州/盟)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="ABODE_AREA" value="${materForm.ABODE_AREA }"/>县(区)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="ABODE_TOWN" value="${materForm.ABODE_TOWN }"/>乡（镇/街道）
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="ABODE_ROAD" value="${materForm.ABODE_ROAD }"/>村（路/弄）
										<input type="text" maxlength="8" style="width:4%;"
											class="syj-input1 validate[required]" name="ABODE_NO" value="${materForm.ABODE_NO }"/>门牌号码
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>经营场所：</th>
									<td colspan="3">
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="BUS_PROVINCE" value="${materForm.BUS_PROVINCE }"/>省(区/市)
										<input type="text" maxlength="8" style="width:7%;"
											class="syj-input1 validate[required]" name="BUS_CITY" value="${materForm.BUS_CITY }"/>市(区/州/盟)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="BUS_AREA" value="${materForm.BUS_AREA }"/>县(区)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="BUS_TOWN" value="${materForm.BUS_TOWN }"/>乡（镇/街道）
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 validate[required]" name="BUS_ROAD" value="${materForm.BUS_ROAD }"/>村（路/弄）
										<input type="text" maxlength="8" style="width:4%;"
											class="syj-input1 validate[required]" name="BUS_NO" value="${materForm.BUS_NO }"/>门牌号码
									</td>
								</tr>
								<tr>
									<th style="border-bottom: none;"> 仓库地址（如有）：</th>
									<td colspan="3" style="border-bottom: none;">
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 " name="DEPOT_PROVINCE" value="${materForm.DEPOT_PROVINCE }"/>省(区/市)
										<input type="text" maxlength="8" style="width:7%;"
											class="syj-input1 " name="DEPOT_CITY" value="${materForm.DEPOT_CITY }"/>市(区/州/盟)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 " name="DEPOT_AREA" value="${materForm.DEPOT_AREA }"/>县(区)
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 " name="DEPOT_TOWN" value="${materForm.DEPOT_TOWN }"/>乡（镇/街道）
										<input type="text" maxlength="8" style="width:6%;"
											class="syj-input1 " name="DEPOT_ROAD" value="${materForm.DEPOT_ROAD }"/>村（路/弄）
										<input type="text" maxlength="8" style="width:4%;"
											class="syj-input1 " name="DEPOT_NO" value="${materForm.DEPOT_NO }"/>门牌号码
									</td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>申请副本数（份）：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="COPY_NUM" value="${materForm.COPY_NUM }"
										maxlength="3" /></td>
									<th><font class="syj-color2">*</font>有效期（年）：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="LIMITYEAR" value="${materForm.LIMITYEAR }"
										maxlength="3" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>经济性质：</th>
									<td colspan="3">
										<eve:excheckbox
											dataInterface="dictionaryService.findDatasForSelect"
											name="NATURE" width="650px;" clazz="checkbox validate[required]"
											dataParams="nature" value="${materForm.NATURE }">
										</eve:excheckbox>
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>职工人数（人）：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="EMPLOYEE_NUM" value="${materForm.EMPLOYEE_NUM }"
										maxlength="6" /></td>
									<th><font class="syj-color2">*</font>应体检人数（人）：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="HEALTH_CHK_NUM" value="${materForm.HEALTH_CHK_NUM }"
										maxlength="6" /></td>
								</tr>
								<tr>
									<th> 邮政编码：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[chinaZip]]" name="POST_CODE" value="${materForm.POST_CODE }"
										maxlength="6" /></td>
									<th> E-mail：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[email]]" name="EMAIL" value="${materForm.EMAIL }"
										maxlength="32" /></td>
								</tr>
								<tr>
									<th> 主体业态：</th>
									<td colspan="3">
										<input type="checkbox" name="MAIN_BUSINESS" value="01"/>食品销售经营者<br>
										<input type="checkbox" name="MAIN_BUSINESS" value="02"/>餐饮服务经营者<br>
										<input type="checkbox" name="MAIN_BUSINESS" value="03"/>单位食堂<br>
										备注：<br/>
										1.是否含网络经营：
										<input type="radio" name="IS_NETWORK" value="1" onclick="$('input[name=\'SITE_URL\']').prop('disabled',false);"/>是
										<input type="radio" name="IS_NETWORK" value="0" onclick="$('input[name=\'SITE_URL\']').val('');$('input[name=\'SITE_URL\']').prop('disabled',true);"/>否
										<input type="text" maxlength="64" style="width:60%;" disabled="disabled"
											class="syj-input1 validate[required,custom[url]]" name="SITE_URL" value="${materForm.SITE_URL }"
											placeholder="请输入网站地址"/><br>
										　如开展网络经营，是否同时具有实体门店：
										<input type="radio" name="IS_STORE" value="1"/>是
										<input type="radio" name="IS_STORE" value="0"/>否<br>
										2.中央厨房：
										<input type="radio" name="IS_CENTRAL_KITCHEN" value="1"/>有
										<input type="radio" name="IS_CENTRAL_KITCHEN" value="0"/>无<br>
										3.集体用餐配送单位：
										<input type="radio" name="IS_TOGETHER_DELIVERY" value="1"/>是
										<input type="radio" name="IS_TOGETHER_DELIVERY" value="0"/>否<br>
										4.利用自动售货设备从事食品销售：
										<input type="radio" name="IS_VENDING" value="1"/>是
										<input type="radio" name="IS_VENDING" value="0"/>否<br>
										5.如主体业态为单位食堂，是否为职业学校、普通中等学校、小学、特殊教育学校、托幼机构：
										<input type="radio" name="IS_EDU" value="1"/>是
										<input type="radio" name="IS_EDU" value="0"/>否
									</td>
								</tr>
								<tr>
									<th> 经营项目：</th>
									<td colspan="3">
										1、<input type="checkbox" name="BUS_PROJECT" value="01"/>预包装食品销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="01-1"/>预包装食品（含冷藏冷冻食品）销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="01-2"/>预包装食品（不含冷藏冷冻食品）销售<br>
										2、<input type="checkbox" name="BUS_PROJECT" value="02"/>散装食品销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="02-1"/>散装食品（含冷藏冷冻食品）销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="02-2"/>散装食品（不含冷藏冷冻食品）销售<br>
										3、<input type="checkbox" name="BUS_PROJECT" value="03"/>特殊食品销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="03-1"/>保健食品销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="03-2"/>特殊医学用途配方食品销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="03-3"/>婴幼儿配方乳粉销售<br>
										　　　<input type="checkbox" name="BUS_PROJECT" value="03-4"/>其他婴幼儿配方食品销售<br>
										4、<input type="checkbox" name="BUS_PROJECT" value="04"/>其他类食品销售
										<input type="text" maxlength="32" style="width:60%;" disabled="disabled"
											class="syj-input1 validate[required]" name="OTHER_FOOD_SALE" value="${materForm.OTHER_FOOD_SALE }"
											placeholder="请输入其他类食品销售信息"/><br>
										5、<input type="checkbox" name="BUS_PROJECT" value="05"/>热食类食品制售<br>
										6、<input type="checkbox" name="BUS_PROJECT" value="06"/>冷食类食品制售<br>
										7、<input type="checkbox" name="BUS_PROJECT" value="07"/>生食类食品制售<br>
										8、<input type="checkbox" name="BUS_PROJECT" value="08"/>糕点类食品制售<br>
										9、<input type="checkbox" name="BUS_PROJECT" value="09"/>自制饮品制售<br>
										10、<input type="checkbox" name="BUS_PROJECT" value="10"/>其他类食品制售
										<input type="text" maxlength="32" style="width:60%;" disabled="disabled"
											class="syj-input1 validate[required]" name="OTHER_FOOD_MANU" value="${materForm.OTHER_FOOD_MANU }"
											placeholder="请输入其他类食品制售信息"/><br><br>
										备注：<br/>
										如申请散装食品销售，是否含散装熟食销售：
										<input type="radio" name="IS_BULK_SALE" value="1"/>是
										<input type="radio" name="IS_BULK_SALE" value="0"/>否
										<br/>如申请自制饮品制售，是否含自酿酒制售：
										<input type="radio" name="IS_BREWED_SALE" value="1"/>是
										<input type="radio" name="IS_BREWED_SALE" value="0"/>否
									</td>
								</tr>
							</table>
						</div>
						<div class="syj-title1 tmargin20">
							<span>法定代表人（负责人）信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">							
								<tr>
									<th><font class="syj-color2">*</font>姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="LEADER_NAME" value="${materForm.LEADER_NAME }"
										maxlength="14" /></td>
									<th><font class="syj-color2">*</font>性别：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="sex"
											dataInterface="dictionaryService.findDatasForSelect"
											defaultEmptyText="请选择性别" name="LEADER_SEX" value="${materForm.LEADER_SEX}">
										</eve:eveselect>
									</td>
								</tr>	
								<tr>
									<th><font class="syj-color2">*</font>户籍登记住址：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="LEADER_DOMICILE" value="${materForm.LEADER_DOMICILE }"
										maxlength="64" /></td>
								</tr>						
								<tr>
									<th><font class="syj-color2">*</font>民族：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="nation"
											dataInterface="dictionaryService.findDatasForSelect"
											defaultEmptyText="请选择民族" name="LEADER_NATION" value="${materForm.LEADER_NATION}">
										</eve:eveselect>
									</td>
									<th><font class="syj-color2">*</font>职务：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="LEADER_JOB" value="${materForm.LEADER_JOB }"
										maxlength="14" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>证件类型：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="DocumentType"
										dataInterface="dictionaryService.findDatasForSelect"  onchange="setZjValidate(this.value,'LEADER_CARDNO');" 
										defaultEmptyText="请选择证件类型" name="LEADER_CARDTYPE" value="${materForm.LEADER_CARDTYPE}">
										</eve:eveselect>
									</td>
									<th><font class="syj-color2">*</font>证件号码：</th>
									<td><input type="text" class="syj-input1 validate[required]"
										name="LEADER_CARDNO" placeholder="请输入证件号码" maxlength="32" value="${materForm.LEADER_CARDNO}"/></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>移动电话：</th>
									<td><input type="text" class="syj-input1 validate[required,custom[mobilePhoneLoose]]"
										name="LEADER_MOBILE" maxlength="11" value="${materForm.LEADER_MOBILE}"/></td>
									<th> 固定电话：</th>
									<td><input type="text" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]"
										name="LEADER_PHONE" maxlength="16" value="${materForm.LEADER_PHONE}"/></td>
								</tr>
							</table>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>食品安全专业技术人员</span>
						</div>
						<div id="techDiv">
							<div style="position:relative;">
								<div id="techToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addTech();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeTech();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#techToolbar" id="techGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowTech" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="8%">姓名</th>
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
												width="7%">性别</th>
											<th data-options="field:'NATION',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME'}},
												formatter:function(value,row){
											        return row.nationname;
												}"
												width="7%">民族</th>
											<th data-options="field:'DOMICILE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">户籍登记住址</th>
											<th data-options="field:'ID_TYPE',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME',
								                onSelect:function(rec){
								                	var ed = $('#techGrid').datagrid('getEditor', {index:editIndexTech,field:'ID_NO'});
								                	if(rec.DIC_CODE=='SF'){
								                		$(ed.target).validatebox('options').validType='vidcard';
								                	}else{
								                		$(ed.target).validatebox('options').validType='';
								                	}
								                }}},
												formatter:function(value,row){
											        return row.typename;
												}"
												width="12%">证件类型</th>
											<th data-options="field:'ID_NO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">证件号</th>
											<th data-options="field:'JOB',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">职务</th>
											<th data-options="field:'PHONE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'mobilePhoneLoose'}}"
												width="10%">联系电话</th>
											<th data-options="field:'POINTDEP',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">任免单位</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>食品安全管理人员</span>
						</div>
						<div id="manageDiv">
							<div style="position:relative;">
								<div id="manageToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addManage();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeManage();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#manageToolbar" id="manageGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowManage" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="8%">姓名</th>
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
												width="7%">性别</th>
											<th data-options="field:'NATION',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME'}},
												formatter:function(value,row){
											        return row.nationname;
												}"
												width="7%">民族</th>
											<th data-options="field:'DOMICILE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">户籍登记住址</th>
											<th data-options="field:'ID_TYPE',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME',
								                onSelect:function(rec){
								                	var ed = $('#manageGrid').datagrid('getEditor', {index:editIndexManage,field:'ID_NO'});
								                	if(rec.DIC_CODE=='SF'){
								                		$(ed.target).validatebox('options').validType='vidcard';
								                	}else{
								                		$(ed.target).validatebox('options').validType='';
								                	}
								                }}},
												formatter:function(value,row){
											        return row.typename;
												}"
												width="12%">证件类型</th>
											<th data-options="field:'ID_NO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">证件号</th>
											<th data-options="field:'JOB',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">职务</th>
											<th data-options="field:'PHONE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'mobilePhoneLoose'}}"
												width="10%">联系电话</th>
											<th data-options="field:'POINTDEP',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="15%">任免单位</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>从业人员情况</span>
						</div>
						<div id="employeeDiv">
							<div style="position:relative;">
								<div id="employeeToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addEmployee();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeEmployee();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#employeeToolbar" id="employeeGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowEmployee" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'NAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="6%">姓名</th>
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
												width="5%">性别</th>
											<th data-options="field:'NATION',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME'}},
												formatter:function(value,row){
											        return row.nationname;
												}"
												width="7%">民族</th>
											<th data-options="field:'DOMICILE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="10%">户籍登记住址</th>
											<th data-options="field:'ID_TYPE',align:'left',editor:{type:'combobox',options:{required:true,editable:false,panelHeight:120,
												valueField:'DIC_CODE',
								                textField:'DIC_NAME',
								                onSelect:function(rec){
								                	var ed = $('#employeeGrid').datagrid('getEditor', {index:editIndexEmployee,field:'ID_NO'});
								                	if(rec.DIC_CODE=='SF'){
								                		$(ed.target).validatebox('options').validType='vidcard';
								                	}else{
								                		$(ed.target).validatebox('options').validType='';
								                	}
								                }}},
												formatter:function(value,row){
											        return row.typename;
												}"
												width="10%">证件类型</th>
											<th data-options="field:'ID_NO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="13%">证件号</th>
											<th data-options="field:'JOB',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="9%">职务</th>
											<th data-options="field:'PHONE',align:'left',editor:{type:'validatebox',options:{required:true,validType:'mobilePhoneLoose'}}"
												width="9%">联系电话</th>
											<th data-options="field:'POINTDEP',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="9%">任免单位</th>
											<th data-options="field:'HEALTHNO',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="7%">健康证号</th>
											<th data-options="field:'WTYPE',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="5%">工种</th>
											<th data-options="field:'SENDDEP',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="9%">发证单位</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>食品安全设施设备</span>
						</div>
						<div id="equipDiv">
							<div style="position:relative;">
								<div id="equipToolbar">
									<c:if test="${materForm.operType=='write' }">
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
											plain="true" onclick="addEquip();">添加</a>
										<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
											onclick="removeEquip();">删除</a>
									</c:if>
								</div>
								<table class="easyui-datagrid" toolbar="#equipToolbar" id="equipGrid" rownumbers="true" border="true" fitColumns="true"
									data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowEquip" nowrap="false">
									<thead>
										<tr>
											<th data-options="field:'EQUIPNAME',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="30%">设备名称</th>
											<th data-options="field:'COUNT',align:'left',editor:{type:'numberbox',options:{required:true,min:0}}"
												width="15%">数量</th>
											<th data-options="field:'LOCATION',align:'left',editor:{type:'validatebox',options:{required:true}}"
												width="30%">位置</th>
											<th data-options="field:'REMARK',align:'left',editor:{type:'validatebox',options:{}}"
												width="24%">备注</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						
						<div class="syj-title1 tmargin20">
							<span>委托书信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">							
								<tr>
									<th>代表或代理人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[]" name="AGENT_NAME" value="${materForm.AGENT_NAME }"
										maxlength="14" /></td>
									<th></th>
									<td></td>
								</tr>
								<tr>
									<th> 名称：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[]" name="AGENT_ITEM" value="${materForm.AGENT_ITEM }"
										maxlength="62" placeholder="向食品药品监督管理部门办理【名称】 的《食品经营许可证》申请相关手续"  /></td>
								</tr>						
								<tr>
									<th> 委托事项及权限：</th>
									<td colspan="3">
										1、<input type="radio" name="CHECKCOPY" <c:if test="${materForm.CHECKCOPY==1}">checked=checked</c:if> value="1"/>同意
										<input type="radio" name="CHECKCOPY" <c:if test="${materForm.CHECKCOPY==0}">checked=checked</c:if> value="0"/>不同意
										核对申请材料中的复印件并签署核对意见
										<br/>
										2、<input type="radio" name="CHECKSELF" <c:if test="${materForm.CHECKSELF==1}">checked=checked</c:if> value="1"/>同意
										<input type="radio" name="CHECKSELF" <c:if test="${materForm.CHECKSELF==0}">checked=checked</c:if> value="0"/>不同意
										修改自备材料中的填写错误
										<br/>
										3、<input type="radio" name="CHECKFORM" <c:if test="${materForm.CHECKFORM==1}">checked=checked</c:if> value="1"/>同意
										<input type="radio" name="CHECKFORM" <c:if test="${materForm.CHECKFORM==0}">checked=checked</c:if> value="0"/>不同意
										修改有关表格的填写错误
										<br/>
										4、<input type="radio" name="LICRECIVE" <c:if test="${materForm.LICRECIVE==1}">checked=checked</c:if> value="1"/>同意
										<input type="radio" name="LICRECIVE" <c:if test="${materForm.LICRECIVE==0}">checked=checked</c:if> value="0"/>不同意
										领取《食品经营许可证》和有关文书
									</td>
								</tr>
								<tr>
									<th> 其他委托事项及权限：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 " name="AGENT_OTHER" value="${materForm.AGENT_OTHER }"
										maxlength="62" /></td>
								</tr>
								<tr>
									<th> 委托的期限：</th>
									<td colspan="3">
										<input type="text" class="Wdate" id="AGENT_STARTDATE" 
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'AGENT_ENDDATE\')}'})"
										readonly="readonly" name="AGENT_STARTDATE"  placeholder="请输入开始时间" value="${materForm.AGENT_STARTDATE}"  maxlength="16"/>至
										<input type="text" class="Wdate" id="AGENT_ENDDATE" readonly="readonly"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'AGENT_STARTDATE\',{M:1})||\'%y-{%M+1}-%d\'}'
										,maxDate:'#F{$dp.$D(\'AGENT_STARTDATE\',{y:1})||\'{%y+1}-%M-%d\'}'})"
										name="AGENT_ENDDATE"  placeholder="请输入结束时间" value="${materForm.AGENT_ENDDATE}"  maxlength="16"/></td>
								</tr>
								<tr>
									<th> 移动电话：</th>
									<td><input type="text" class="syj-input1 validate[[],custom[mobilePhoneLoose]]"
										name="AGENT_MOBILE" maxlength="11" value="${materForm.AGENT_MOBILE}"/></td>
									<th> 固定电话：</th>
									<td><input type="text" class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]"
										name="AGENT_PHONE" maxlength="16" value="${materForm.AGENT_PHONE}"/></td>
								</tr>
							</table>
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
		        <a href="javascript:void(0);" onclick="$('#FOODLIC_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
	
	
	<script type="text/javascript">
		/**食品安全专业技术人员可编辑表格开始*/
		var editIndexTech = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingTech(){
	    	if (editIndexTech == undefined){return true}
	    	if ($("#techGrid").datagrid("validateRow", editIndexTech)){
	    		var ed = $('#techGrid').datagrid('getEditor', {index:editIndexTech,field:'NATION'});
				var nationname = $(ed.target).combobox('getText');
				$('#techGrid').datagrid('getRows')[editIndexTech]['nationname'] = nationname;
	    		var ided = $('#techGrid').datagrid('getEditor', {index:editIndexTech,field:'ID_TYPE'});
				var typename = $(ided.target).combobox('getText');
				$('#techGrid').datagrid('getRows')[editIndexTech]['typename'] = typename;
	    		$("#techGrid").datagrid("endEdit", editIndexTech);
	    		editIndexTech = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addTech(){
	    	if (endEditingTech()){
	    		$("#techGrid").datagrid("appendRow",{});
	    		editIndexTech = $("#techGrid").datagrid("getRows").length-1;
	    		$("#techGrid").datagrid("selectRow", editIndexTech)
	    				.datagrid("beginEdit", editIndexTech);
	    		synchTech(editIndexTech);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeTech() {
	    	if (editIndexTech == undefined){return}
	    	$("#techGrid").datagrid("cancelEdit", editIndexTech)
	    			.datagrid("deleteRow", editIndexTech);
	    	editIndexTech = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmTech(){
	    	var e = endEditingTech();
	        if (e){
	            $("#techGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowTech(index){
	    	if (editIndexTech != index){
	    		if (endEditingTech()){
	    			$("#techGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexTech = index;
	    		} else {
	    			$("#techGrid").datagrid("selectRow", editIndexTech);
	    		}
	    		synchTech(editIndexTech);
	    	}
	    }
	    
	    function synchTech(rowIndex){
	          var jqData;  
		      var url = 'dictionaryController/loadData.do?typeCode=nation&orderType=asc';  
		      var ed = $('#techGrid' ).datagrid('getEditor',{index:rowIndex,field: 'NATION' });  
		      $.ajax({  
		           url:url,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ed.target).combobox( 'loadData' , data);  
		           }  
		      });   
		      var idurl = 'dictionaryController/loadData.do?typeCode=DocumentType&orderType=asc';  
		      var ided = $('#techGrid' ).datagrid('getEditor',{index:rowIndex,field: 'ID_TYPE' });  
		      $.ajax({  
		           url:idurl,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ided.target).combobox( 'loadData' , data);  
		           }  
		      }); 
	    }
	    
	    /**
	     * 提交方法
	     */
	    function techSubmit(){
	    	if(!confirmTech()){
	            alert("“食品安全专业技术人员”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#techGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"NAME":rows[i].NAME,
						"SEX":rows[i].SEX,
						"NATION":rows[i].NATION,
						"DOMICILE":rows[i].DOMICILE,
						"ID_TYPE":rows[i].ID_TYPE,
						"ID_NO":rows[i].ID_NO,
						"JOB":rows[i].JOB,
						"PHONE":rows[i].PHONE,
						"POINTDEP":rows[i].POINTDEP
					});
				}
				$("input[type='hidden'][name='SECURITY_TECHJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**食品安全专业技术人员可编辑表格结束*/
	    
	    /**食品安全管理人员可编辑表格开始*/
		var editIndexManage = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingManage(){
	    	if (editIndexManage == undefined){return true}
	    	if ($("#manageGrid").datagrid("validateRow", editIndexManage)){
	    		var ed = $('#manageGrid').datagrid('getEditor', {index:editIndexManage,field:'NATION'});
				var nationname = $(ed.target).combobox('getText');
				$('#manageGrid').datagrid('getRows')[editIndexManage]['nationname'] = nationname;
	    		var ided = $('#manageGrid').datagrid('getEditor', {index:editIndexManage,field:'ID_TYPE'});
				var typename = $(ided.target).combobox('getText');
				$('#manageGrid').datagrid('getRows')[editIndexManage]['typename'] = typename;
	    		$("#manageGrid").datagrid("endEdit", editIndexManage);
	    		editIndexManage = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addManage(){
	    	if (endEditingManage()){
	    		$("#manageGrid").datagrid("appendRow",{});
	    		editIndexManage = $("#manageGrid").datagrid("getRows").length-1;
	    		$("#manageGrid").datagrid("selectRow", editIndexManage)
	    				.datagrid("beginEdit", editIndexManage);
	    		synchManage(editIndexManage);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeManage() {
	    	if (editIndexManage == undefined){return}
	    	$("#manageGrid").datagrid("cancelEdit", editIndexManage)
	    			.datagrid("deleteRow", editIndexManage);
	    	editIndexManage = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmManage(){
	    	var e = endEditingManage();
	        if (e){
	            $("#manageGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowManage(index){
	    	if (editIndexManage != index){
	    		if (endEditingManage()){
	    			$("#manageGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexManage = index;
	    		} else {
	    			$("#manageGrid").datagrid("selectRow", editIndexManage);
	    		}
	    		synchManage(editIndexManage);
	    	}
	    }
	    
	    function synchManage(rowIndex){
	          var jqData;  
		      var url = 'dictionaryController/loadData.do?typeCode=nation&orderType=asc';  
		      var ed = $('#manageGrid' ).datagrid('getEditor',{index:rowIndex,field: 'NATION' });  
		      $.ajax({  
		           url:url,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ed.target).combobox( 'loadData' , data);  
		           }  
		      });   
		      var idurl = 'dictionaryController/loadData.do?typeCode=DocumentType&orderType=asc';  
		      var ided = $('#manageGrid' ).datagrid('getEditor',{index:rowIndex,field: 'ID_TYPE' });  
		      $.ajax({  
		           url:idurl,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ided.target).combobox( 'loadData' , data);  
		           }  
		      }); 
	    }
	    
	    /**
	     * 提交方法
	     */
	    function manageSubmit(){
	    	if(!confirmManage()){
	            alert("“食品安全管理人员”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#manageGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"NAME":rows[i].NAME,
						"SEX":rows[i].SEX,
						"NATION":rows[i].NATION,
						"DOMICILE":rows[i].DOMICILE,
						"ID_TYPE":rows[i].ID_TYPE,
						"ID_NO":rows[i].ID_NO,
						"JOB":rows[i].JOB,
						"PHONE":rows[i].PHONE,
						"POINTDEP":rows[i].POINTDEP
					});
				}
				$("input[type='hidden'][name='SECURITY_MANJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**食品安全管理人员可编辑表格结束*/
	    
	    /**从业人员情况可编辑表格开始*/
		var editIndexEmployee = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingEmployee(){
	    	if (editIndexEmployee == undefined){return true}
	    	if ($("#employeeGrid").datagrid("validateRow", editIndexEmployee)){
	    		var ed = $('#employeeGrid').datagrid('getEditor', {index:editIndexEmployee,field:'NATION'});
				var nationname = $(ed.target).combobox('getText');
				$('#employeeGrid').datagrid('getRows')[editIndexEmployee]['nationname'] = nationname;
	    		var ided = $('#employeeGrid').datagrid('getEditor', {index:editIndexEmployee,field:'ID_TYPE'});
				var typename = $(ided.target).combobox('getText');
				$('#employeeGrid').datagrid('getRows')[editIndexEmployee]['typename'] = typename;
	    		$("#employeeGrid").datagrid("endEdit", editIndexEmployee);
	    		editIndexEmployee = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addEmployee(){
	    	if (endEditingEmployee()){
	    		$("#employeeGrid").datagrid("appendRow",{});
	    		editIndexEmployee = $("#employeeGrid").datagrid("getRows").length-1;
	    		$("#employeeGrid").datagrid("selectRow", editIndexEmployee)
	    				.datagrid("beginEdit", editIndexEmployee);
	    		synchEmployee(editIndexEmployee);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeEmployee() {
	    	if (editIndexEmployee == undefined){return}
	    	$("#employeeGrid").datagrid("cancelEdit", editIndexEmployee)
	    			.datagrid("deleteRow", editIndexEmployee);
	    	editIndexEmployee = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmEmployee(){
	    	var e = endEditingEmployee();
	        if (e){
	            $("#employeeGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowEmployee(index){
	    	if (editIndexEmployee != index){
	    		if (endEditingEmployee()){
	    			$("#employeeGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexEmployee = index;
	    		} else {
	    			$("#employeeGrid").datagrid("selectRow", editIndexEmployee);
	    		}
	    		synchEmployee(editIndexEmployee);
	    	}
	    }
	    
	    function synchEmployee(rowIndex){
	          var jqData;  
		      var url = 'dictionaryController/loadData.do?typeCode=nation&orderType=asc';  
		      var ed = $('#employeeGrid').datagrid('getEditor',{index:rowIndex,field: 'NATION' });  
		      $.ajax({  
		           url:url,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ed.target).combobox( 'loadData' , data);  
		           }  
		      });   
		      var idurl = 'dictionaryController/loadData.do?typeCode=DocumentType&orderType=asc';  
		      var ided = $('#employeeGrid' ).datagrid('getEditor',{index:rowIndex,field: 'ID_TYPE' });  
		      $.ajax({  
		           url:idurl,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ided.target).combobox( 'loadData' , data);  
		           }  
		      }); 
	    }
	    
	    /**
	     * 提交方法
	     */
	    function employeeSubmit(){
	    	if(!confirmEmployee()){
	            alert("“从业人员情况”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#employeeGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"NAME":rows[i].NAME,
						"SEX":rows[i].SEX,
						"NATION":rows[i].NATION,
						"DOMICILE":rows[i].DOMICILE,
						"ID_TYPE":rows[i].ID_TYPE,
						"ID_NO":rows[i].ID_NO,
						"JOB":rows[i].JOB,
						"PHONE":rows[i].PHONE,
						"POINTDEP":rows[i].POINTDEP,
						"HEALTHNO":rows[i].HEALTHNO,
						"WTYPE":rows[i].WTYPE,
						"SENDDEP":rows[i].SENDDEP
					});
				}
				$("input[type='hidden'][name='EMPLOYEEJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**从业人员情况可编辑表格结束*/
	    
		/**食品安全设施设备可编辑表格开始*/
		var editIndexEquip = undefined;
	    /**
	     * 结束编辑模式
	     */
	    function endEditingEquip(){
	    	if (editIndexEquip == undefined){return true}
	    	if ($("#equipGrid").datagrid("validateRow", editIndexEquip)){
	    		$("#equipGrid").datagrid("endEdit", editIndexEquip);
	    		editIndexEquip = undefined;
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    /**
	     * 添加信息
	     */
	    function addEquip(){
	    	if (endEditingEquip()){
	    		$("#equipGrid").datagrid("appendRow",{});
	    		editIndexEquip = $("#equipGrid").datagrid("getRows").length-1;
	    		$("#equipGrid").datagrid("selectRow", editIndexEquip)
	    				.datagrid("beginEdit", editIndexEquip);
	    		synchEquip(editIndexEquip);
	    	}
	    }
	    /**
	     * 删除信息
	     */
	    function removeEquip() {
	    	if (editIndexEquip == undefined){return}
	    	$("#equipGrid").datagrid("cancelEdit", editIndexEquip)
	    			.datagrid("deleteRow", editIndexEquip);
	    	editIndexEquip = undefined;
	    }
	    /**
	     * 保存信息
	     */
	    function confirmEquip(){
	    	var e = endEditingEquip();
	        if (e){
	            $("#equipGrid").datagrid("acceptChanges");
	        }
	        return e;
	    }
	    /**
	     * 编辑行
	     */
	    function onClickRowEquip(index){
	    	if (editIndexEquip != index){
	    		if (endEditingEquip()){
	    			$("#equipGrid").datagrid("selectRow", index)
	    					.datagrid("beginEdit", index);
	    			editIndexEquip = index;
	    		} else {
	    			$("#equipGrid").datagrid("selectRow", editIndexEquip);
	    		}
	    		synchEquip(editIndexEquip);
	    	}
	    }
	    
	    function synchEquip(rowIndex){
	          var jqData;  
		      var url = 'dictionaryController/loadData.do?typeCode=nation&orderType=asc';  
		      var ed = $('#equipGrid' ).datagrid('getEditor',{index:rowIndex,field: 'NATION' });  
		      $.ajax({  
		           url:url,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ed.target).combobox( 'loadData' , data);  
		           }  
		      });   
		      var idurl = 'dictionaryController/loadData.do?typeCode=DocumentType&orderType=asc';  
		      var ided = $('#equipGrid' ).datagrid('getEditor',{index:rowIndex,field: 'ID_TYPE' });  
		      $.ajax({  
		           url:idurl,  
		           dataType : 'json',  
		           type : 'POST',  
		           success: function (data){
		                $(ided.target).combobox( 'loadData' , data);  
		           }  
		      }); 
	    }
	    
	    /**
	     * 提交方法
	     */
	    function equipSubmit(){
	    	if(!confirmEquip()){
	            alert("“食品安全设施设备”信息不完整。请核对并填写后再提交。")
	            return false;
	        }
	    	var rows = $("#equipGrid").datagrid("getRows");
	    	if(rows.length > 0){
				var datas = [];
				for(var i = 0;i<rows.length;i++){
					datas.push({
						"EQUIPNAME":rows[i].EQUIPNAME,
						"COUNT":rows[i].COUNT,
						"LOCATION":rows[i].LOCATION,
						"REMARK":rows[i].REMARK
					});
				}
				$("input[type='hidden'][name='EQUIPMENTJSON']").val(JSON.stringify(datas));
			}
			return true;
	    }
	    /**食品安全设施设备可编辑表格结束*/
	</script>
</body>