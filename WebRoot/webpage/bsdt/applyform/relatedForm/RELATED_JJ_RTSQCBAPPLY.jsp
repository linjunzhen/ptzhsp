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
	<style type="text/css">
	.mininput{
		max-width:120px;
	}
	</style>
	<script type="text/javascript">
	function isDate6(sDate) {

		 if (!/^[0-9]{6}$/.test(sDate)) {

		  return false;
		 }

	}
	function isDate8(sDate) {

		 if (!/^[0-9]{8}$/.test(sDate)) {

		  return false;
		 }

	} 
	function isIdCardNo(num)

	{

	 var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
	   2, 1);

	 var error;

	 var varArray = new Array();

	 var intValue;

	 var lngProduct = 0;

	 var intCheckDigit;

	 var intStrLen = num.length;

	 var idNumber = num;

	 // initialize

	 if ((intStrLen != 15) && (intStrLen != 18)) {

	  // error = "输入身份证号码长度不对！";

	  // alert(error);

	  // frmAddUser.txtIDCard.focus();

	  return false;

	 }

	 // check and set value

	 for (i = 0; i < intStrLen; i++) {

	  varArray[i] = idNumber.charAt(i);

	  if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {

	   // error = "错误的身份证号码！.";

	   // alert(error);

	   // frmAddUser.txtIDCard.focus();

	   return false;

	  } else if (i < 17) {

	   varArray[i] = varArray[i] * factorArr[i];

	  }

	 }

	 if (intStrLen == 18) {

	  // check date

	  var date8 = idNumber.substring(6, 14);

	  if (isDate8(date8) == false) {

	   // error = "身份证中日期信息不正确！.";

	   // alert(error);

	   return false;

	  }

	  // calculate the sum of the products

	  for (i = 0; i < 17; i++) {

	   lngProduct = lngProduct + varArray[i];

	  }

	  // calculate the check digit

	  intCheckDigit = 12 - lngProduct % 11;

	  switch (intCheckDigit) {

	  case 10:

	   intCheckDigit = 'X';

	   break;

	  case 11:

	   intCheckDigit = 0;

	   break;

	  case 12:

	   intCheckDigit = 1;

	   break;

	  }

	  // check last digit

	  if (varArray[17].toUpperCase() != intCheckDigit) {

	   // error = "身份证效验位错误!...正确为： " + intCheckDigit + ".";

	   // alert(error);

	   return false;

	  }

	 }

	 else { // length is 15

	  // check date

	  var date6 = idNumber.substring(6, 12);

	  if (isDate6(date6) == false) {

	   // alert("身份证日期信息有误！.");

	   return false;

	  }

	 }

	 // alert ("Correct.");

	 return true;

	}
	
	
	$.extend($.fn.validatebox.defaults.rules, {   
	    idcared: {   
	        validator: function(value,param){  
	        	return isIdCardNo(value);
	        },   
	        message: '不是有效的身份证号码'  
	    },
	    numberCheckSub : {
	        validator : function(value) {
	        return /^[0-9]+$/.test(value);},
	        message : "只能输入数字"
	       }
	});
	</script>
	<script type="text/javascript">
		$(function() {
			
			AppUtil.initWindowForm("RFTAPPLY_FORM", function(form, valid) {
				if (valid&&TheoreticalInstructorSubmit()&&PracticalTeacherSubmit()&&AdministrativeStaffSubmit()&&CoachVehicleSubmit()) {
					TheoreticalInstructorSubmit();
					PracticalTeacherSubmit();
					AdministrativeStaffSubmit();
					CoachVehicleSubmit();
					var photo = $("input[name='DEALER_PHOTO']").val();
					 if(photo==null||photo==''||photo=='undefined'){
				 		parent.art.dialog({
							content: "请上传照片",
							icon:"warning",
							ok: true
						});
				 		return ;
					 }
					//将提交按钮禁用,防止重复提交
					//var formData = $("#RFTAPPLY_FORM").serialize();
					
			    	//先获取表单上的所有值
					var formData = FlowUtil.getFormEleData("RFTAPPLY_FORM");
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
			
			if(${materForm.RECORD_ID!=null }){
				if('${materForm.THEORETICALINSTRUCTOR_JSON }'!=null&&'${materForm.THEORETICALINSTRUCTOR_JSON }'!=''){
					var TheoreticalInstructorJson = '${materForm.THEORETICALINSTRUCTOR_JSON }';
					var TheoreticalInstructor = eval(TheoreticalInstructorJson);
					var jsonstr = '{"total":'+TheoreticalInstructor.length+',"rows":'+TheoreticalInstructorJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#TheoreticalInstructorGrid').datagrid('loadData',data);
				} 
				if('${materForm.PRACTICALTEACHER_JSON }'!=null&&'${materForm.PRACTICALTEACHER_JSON }'!=''){
					var PracticalTeacherJson = '${materForm.PRACTICALTEACHER_JSON }';
					var PracticalTeacher = eval(PracticalTeacherJson);
					var jsonstr = '{"total":'+PracticalTeacher.length+',"rows":'+PracticalTeacherJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#PracticalTeacherGrid').datagrid('loadData',data);
				} 
				if('${materForm.ADMINISTRATIVESTAFF_JSON}'!=null&&'${materForm.ADMINISTRATIVESTAFF_JSON}'!=''){
					var AdministrativeStaffJson = '${materForm.ADMINISTRATIVESTAFF_JSON}';
					var AdministrativeStaff = eval(AdministrativeStaffJson);
					var jsonstr = '{"total":'+AdministrativeStaff.length+',"rows":'+AdministrativeStaffJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#AdministrativeStaffGrid').datagrid('loadData',data);
				} 
				if('${materForm.COACHVEHICLE_JSON}'!=null&&'${materForm.COACHVEHICLE_JSON}'!=''){
					var CoachVehicleJson = '${materForm.COACHVEHICLE_JSON}';
					var CoachVehicle = eval(CoachVehicleJson);
					var jsonstr = '{"total":'+CoachVehicle.length+',"rows":'+CoachVehicleJson+'}';
					var data = $.parseJSON(jsonstr);
					$('#CoachVehicleGrid').datagrid('loadData',data);
				} 
				
			}
			
			AppUtil.initUploadControl({
				file_types : "*.jpg;*.jpeg;*.gif;*.png;",
				file_upload_limit : 1000,
				file_queue_limit : 1000,
				uploadPath : "photo",
				busTableName : "RELATED_JJ_RTSQCBAPPLY",
				uploadUserId : "",
				uploadButtonId : "IMAGE_PATH_BTN",
				uploadFileType : "image",
				uploadImageId : "IMAGE_PATH_IMG",
				uploadImageFieldName : "DEALER_PHOTO",
				limit_size : "4 MB",
				uploadSuccess : function(file, serverData, responseReceived) {
					var resultJson = $.parseJSON(serverData);
					var filePath = resultJson.filePath;
					$("#IMAGE_PATH_IMG").attr("src", filePath);
					$("input[name='DEALER_PHOTO']").val(filePath);
				}
			});
			
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
						<input type="hidden" name="THEORETICALINSTRUCTOR_JSON" value="${materForm.THEORETICALINSTRUCTOR_JSON }"/>
						<input type="hidden" name="PRACTICALTEACHER_JSON" value="${materForm.PRACTICALTEACHER_JSON}"/>
						<input type="hidden" name="ADMINISTRATIVESTAFF_JSON" value="${materForm.ADMINISTRATIVESTAFF_JSON}"/>
						<input type="hidden" name="COACHVEHICLE_JSON" value="${materForm.COACHVEHICLE_JSON}"/>
						<div class="syj-title1">
							<span>法人信息</span>
							<a href="javascript:void(0);" onclick="javascript:downloadDoc();" class="syj-addbtn" >下 载</a>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>法人姓名：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="FRXM" value="${materForm.FRXM }"
										placeholder="请输入法人姓名" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>性别：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="sex"
											dataInterface="dictionaryService.findDatasForSelect"
											defaultEmptyText="请选择性别" name="XB" value="${materForm.XB}">
											</eve:eveselect>
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>出生年月：</th>
									<td>
										<input class="Wdate validate[required]" readonly="readonly" id="birthday" onclick="WdatePicker({dateFmt:'yyyy年MM月dd日'})" 
										name="CSNY" placeholder="请输入出生日期" value="${materForm.CSNY}" type="text">
									</td>
									<th><font class="syj-color2">*</font>民族：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="nation"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择民族" name="MZ" value="${materForm.MZ}">
										</eve:eveselect>
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>文化程度：</th>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="degree"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择文化程度" name="WHCD" value="${materForm.WHCD}">
										</eve:eveselect>
									</td>
									<th><font class="syj-color2">*</font>身份证号：</th>
									<td><input type="text" class="syj-input1 validate[required],custom[vidcard]"
										name="SFZH" maxlength="30" placeholder="请输入身份证号"  value="${materForm.SFZH}"/></td>
									</td>
								</tr>
								<tr>
									 <th><font class="syj-color2">*</font>工作单位：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="GZDW" value="${materForm.GZDW }"
										placeholder="请输入工作单位" maxlength="100" /></td>
									<th rowspan="3"><font class="tab_color">*</font>二寸彩色相片：</th>
									<td rowspan="3">
										<c:choose>
											<c:when test="${materForm.DEALER_PHOTO!=null&&materForm.DEALER_PHOTO!=''}">
												<img id="IMAGE_PATH_IMG" src="${materForm.DEALER_PHOTO}"
													height="140px" width="125px">
											</c:when>
											<c:otherwise>
												<img id="IMAGE_PATH_IMG" src="webpage/common/images/nopic.jpg"
													height="140px" width="125px">
											</c:otherwise>
										</c:choose>	
										<c:if test="${materForm.operType=='write' }">
										<a id="IMAGE_PATH_BTN"></a>	
										</c:if>		
							    		<input type="hidden" name="DEALER_PHOTO" value="${materForm.DEALER_PHOTO}">
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>联系电话：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="LXDH" value="${materForm.LXDH }"
										placeholder="请输入联系电话" maxlength="11" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>邮编：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[chinaZip]]" name="YB" value="${materForm.YB }"
										placeholder="请输入邮编" maxlength="6" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>驾驶证准驾车型：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="JSZZJSCX" value="${materForm.JSZZJSCX }"
										placeholder="请输入驾驶证准驾车型" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>学历：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="XL" value="${materForm.XL}"
										placeholder="请输入学历" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>学历证书号：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="XLZSH" value="${materForm.XLZSH}"
										placeholder="请输入学历证书号" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>个人简历：</th>
									<td colspan="3">
										<textarea rows="3" cols="200" name="GRJL" 
										class="eve-textarea validate[required],maxSize[500]" 
										 style="width:96%;height:100px;" 
										placeholder="请输入个人简历" 
										id="form-validation-field-0">${materForm.GRJL}</textarea>
										</td>
								</tr>
							</table>
							<div class="syj-title1">
								<span>岗位职责及管理制度</span>
							</div>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="width: 20px;">序号</th>
									<th style="width: 100px;">内容</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>评审情况</th>
									<th>备注</th>
								</tr>
								<tr>
									<td colspan="4">一、岗位职责</td>
								</tr>
								<tr>
									<td>1</td>
									<td>负责人岗位职责</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GWZZA" value="${materForm.GWZZA}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GWZZBZA" value="${materForm.GWZZBZA}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td>管理人员岗位职责</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GWZZB" value="${materForm.GWZZB}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GWZZBZB" value="${materForm.GWZZBZB}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>3</td>
									<td>教学人员岗位职责</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GWZZC" value="${materForm.GWZZC}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GWZZBZC" value="${materForm.GWZZBZC}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>4</td>
									<td>其他人员岗位职责</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GWZZD" value="${materForm.GWZZD}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GWZZBZD" value="${materForm.GWZZBZD}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td colspan="4">二、管理制度</td>
								</tr>
								<tr>
									<td>1</td>
									<td>诚信承诺制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZA" value="${materForm.GLZZA}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZA" value="${materForm.GLZZBZA}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td>教学管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZB" value="${materForm.GLZZB}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZB" value="${materForm.GLZZBZB}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>3</td>
									<td>教练员管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZC" value="${materForm.GLZZC}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZC" value="${materForm.GLZZBZC}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>4</td>
									<td>学员管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZD" value="${materForm.GLZZD}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZD" value="${materForm.GLZZBZD}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>5</td>
									<td>结业考试制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZE" value="${materForm.GLZZE}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZE" value="${materForm.GLZZBZE}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>6</td>
									<td>培训预约制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZF" value="${materForm.GLZZF}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZF" value="${materForm.GLZZBZF}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>7</td>
									<td>责任倒查制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZG" value="${materForm.GLZZG}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZG" value="${materForm.GLZZBZG}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>8</td>
									<td>学员投诉受理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZH" value="${materForm.GLZZH}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZH" value="${materForm.GLZZBZH}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>9</td>
									<td>安全管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZI" value="${materForm.GLZZI}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZI" value="${materForm.GLZZBZI}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>10</td>
									<td>教练车管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZJ" value="${materForm.GLZZJ}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZJ" value="${materForm.GLZZBZJ}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>11</td>
									<td>教学设施、设备管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZK" value="${materForm.GLZZK}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZK" value="${materForm.GLZZBZK}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>12</td>
									<td>计算机教学管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZL" value="${materForm.GLZZL}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZL" value="${materForm.GLZZBZL}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>13</td>
									<td>培训收费管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZM" value="${materForm.GLZZM}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZM" value="${materForm.GLZZBZM}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>14</td>
									<td>培训质量管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZN" value="${materForm.GLZZN}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZN" value="${materForm.GLZZBZN}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>15</td>
									<td>教练场地管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZO" value="${materForm.GLZZO}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZO" value="${materForm.GLZZBZO}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>16</td>
									<td>档案管理制度</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="GLZZP" value="${materForm.GLZZP}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="GLZZBZP" value="${materForm.GLZZBZP}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
							</table>
							<div class="syj-title1">
								<span>理论教员简表</span>
							</div>
							<div id="TheoreticalInstructorDiv">
								<div style="position:relative;">
									<div id="TheoreticalInstructorToolbar">
										<c:if test="${materForm.operType=='write' }">
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
												plain="true" onclick="addTheoreticalInstructor();">添加</a>
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
												onclick="removeTheoreticalInstructor();">删除</a>
										</c:if>
									</div>
									<table class="easyui-datagrid" toolbar="#TheoreticalInstructorToolbar" id="TheoreticalInstructorGrid" rownumbers="true" border="true" fitColumns="true"
										data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowTheoreticalInstructor" nowrap="false">
										<thead>
											<tr>
												<th data-options="field:'XM',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">姓名</th>
												<th data-options="field:'SFZH',align:'left',editor:{type:'validatebox',options:{required:true,validType:'idcared'}}"
													width="20%">身份证号</th>
												<th data-options="field:'WHCD',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">文化程度</th>
												<th data-options="field:'JSDAH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="20%">驾驶档案号</th>
												<th data-options="field:'JLYH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="20%">教员证号</th>
												<th data-options="field:'JL',align:'left',editor:{type:'validatebox',options:{required:true,validType:'numberCheckSub'}}"
													width="10%">教龄</th>
												<th data-options="field:'BZ',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="9%">备注</th>
												
											</tr>
										</thead>
									</table>
								</div>
							</div>
							
							<div class="syj-title1">
								<span>实操教员简表</span>
							</div>
							<div id="PracticalTeacherDiv">
								<div style="position:relative;">
									<div id="PracticalTeacherToolbar">
										<c:if test="${materForm.operType=='write' }">
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
												plain="true" onclick="addPracticalTeacher();">添加</a>
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
												onclick="removePracticalTeacher();">删除</a>
										</c:if>
									</div>
									<table class="easyui-datagrid" toolbar="#PracticalTeacherToolbar" id="PracticalTeacherGrid" rownumbers="true" border="true" fitColumns="true"
										data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowPracticalTeacher" nowrap="false">
										<thead>
											<tr>
												<th data-options="field:'XM',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">姓名</th>
												<th data-options="field:'SFZH',align:'left',editor:{type:'validatebox',options:{required:true,validType:'idcared'}}"
													width="20%">身份证号</th>
												<th data-options="field:'WHCD',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">文化程度</th>
												<th data-options="field:'JSDAH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">驾驶档案号</th>
												<th data-options="field:'JLYH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">教员证号</th>
												<th data-options="field:'ZJCXH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">准驾车型</th>
												<th data-options="field:'ZJCX',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">准教车型</th>
												<th data-options="field:'JL',align:'left',editor:{type:'validatebox',options:{required:true,validType:'numberCheckSub'}}"
													width="10%">教龄</th>
												<th data-options="field:'BZ',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="9%">备注</th>
												
											</tr>
										</thead>
									</table>
								</div>
							</div>
						
						<div class="syj-title1">
								<span>行政管理人员简表</span>
						</div>
						<div id="AdministrativeStaffDiv">
								<div style="position:relative;">
									<div id="AdministrativeStaffToolbar">
										<c:if test="${materForm.operType=='write' }">
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
												plain="true" onclick="addAdministrativeStaff();">添加</a>
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
												onclick="removeAdministrativeStaff();">删除</a>
										</c:if>
									</div>
									<table class="easyui-datagrid" toolbar="#AdministrativeStaffToolbar" id="AdministrativeStaffGrid" rownumbers="true" border="true" fitColumns="true"
										data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowAdministrativeStaff">
										<thead>
											<tr>
												<th data-options="field:'XM',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">姓名</th>
												<th data-options="field:'SFZH',align:'left',editor:{type:'validatebox',options:{required:true,validType:'idcared'}}"
													width="20%">身份证号</th>
												<th data-options="field:'WHCD',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">文化程度</th>
												<th data-options="field:'JSDAH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="20%">驾驶档案号</th>
												<th data-options="field:'ZW',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="20%">职称或职务</th>
												<th data-options="field:'JL',align:'left',editor:{type:'validatebox',options:{required:true,validType:'numberCheckSub'}}"
													width="10%">教龄</th>
												<th data-options="field:'BZ',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="9%">备注</th>
												
											</tr>
										</thead>
									</table>
								</div>
							</div>
						
						
							<div class="syj-title1">
								<span>常规教学用具表</span>
							</div>
							
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="width: 20px;">序号</th>
									<th style="width: 50px;">项目</th>
									<th style="width: 100px;">标准</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>型号</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>数量</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>评审情况</th>
									<th>备注</th>
								</tr>
								<tr>
									<td rowspan="3">1</td>
									<td rowspan="3">电化教学设备</td>
									<td>多媒体教学设备≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_A" value="${materForm.CGJXYJBXH_A}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_A" value="${materForm.CGJXYJBSL_A}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_A" value="${materForm.CGJXYJB_A}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_A" value="${materForm.CGJXYJBBZ_A}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>多媒体理论教学软件≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_B" value="${materForm.CGJXYJBXH_B}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_B" value="${materForm.CGJXYJBSL_B}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_B" value="${materForm.CGJXYJB_B}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_B" value="${materForm.CGJXYJBBZ_B}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★无纸化理论考试用计算机≥10</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_C" value="${materForm.CGJXYJBXH_C}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_C" value="${materForm.CGJXYJBSL_C}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_C" value="${materForm.CGJXYJB_C}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_C" value="${materForm.CGJXYJBBZ_C}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td>教学挂图</td>
									<td>道路交通标志、标线、信号挂图≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_D" value="${materForm.CGJXYJBXH_D}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_D" value="${materForm.CGJXYJBSL_D}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_D" value="${materForm.CGJXYJB_D}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_D" value="${materForm.CGJXYJBBZ_D}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td rowspan="11">3</td>
									<td rowspan="11">程控电教板</td>
									<td>★汽油机工作原理≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_E" value="${materForm.CGJXYJBXH_E}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_E" value="${materForm.CGJXYJBSL_E}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_E" value="${materForm.CGJXYJB_E}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_E" value="${materForm.CGJXYJBBZ_E}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★柴油机工作原理≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_F" value="${materForm.CGJXYJBXH_F}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_F" value="${materForm.CGJXYJBSL_F}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_F" value="${materForm.CGJXYJB_F}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_F" value="${materForm.CGJXYJBBZ_F}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★发动机点火系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_G" value="${materForm.CGJXYJBXH_G}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_G" value="${materForm.CGJXYJBSL_G}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_G" value="${materForm.CGJXYJB_G}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_G" value="${materForm.CGJXYJBBZ_G}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★化油器式汽油机燃料供给系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_H" value="${materForm.CGJXYJBXH_H}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_H" value="${materForm.CGJXYJBSL_H}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_H" value="${materForm.CGJXYJB_H}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_H" value="${materForm.CGJXYJBBZ_H}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★电控汽油喷射发动机燃料供给系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_I" value="${materForm.CGJXYJBXH_I}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_I" value="${materForm.CGJXYJBSL_I}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_I" value="${materForm.CGJXYJB_I}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_I" value="${materForm.CGJXYJBBZ_I}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★柴油机燃料供给系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_J" value="${materForm.CGJXYJBXH_J}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_J" value="${materForm.CGJXYJBSL_J}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_J" value="${materForm.CGJXYJB_J}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_J" value="${materForm.CGJXYJBBZ_J}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★发动机冷却系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_K" value="${materForm.CGJXYJBXH_K}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_K" value="${materForm.CGJXYJBSL_K}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_K" value="${materForm.CGJXYJB_K}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_K" value="${materForm.CGJXYJBBZ_K}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>汽车气压或液压制动系≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_L" value="${materForm.CGJXYJBXH_L}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_L" value="${materForm.CGJXYJBSL_L}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_L" value="${materForm.CGJXYJB_L}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_L" value="${materForm.CGJXYJBBZ_L}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★离合器≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_M" value="${materForm.CGJXYJBXH_M}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_M" value="${materForm.CGJXYJBSL_M}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_M" value="${materForm.CGJXYJB_M}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_M" value="${materForm.CGJXYJBBZ_M}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★手动器变动器≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_N" value="${materForm.CGJXYJBXH_N}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_N" value="${materForm.CGJXYJBSL_N}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_N" value="${materForm.CGJXYJB_N}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_N" value="${materForm.CGJXYJBBZ_N}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★自动器变动器≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_O" value="${materForm.CGJXYJBXH_O}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_O" value="${materForm.CGJXYJBSL_O}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_O" value="${materForm.CGJXYJB_O}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_O" value="${materForm.CGJXYJBBZ_O}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td rowspan="3">4</td>
									<td rowspan="3">模型教具</td>
									<td>透明或实物解剖全车制动系统模型≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_P" value="${materForm.CGJXYJBXH_P}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_P" value="${materForm.CGJXYJBSL_P}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_P" value="${materForm.CGJXYJB_P}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_P" value="${materForm.CGJXYJBBZ_P}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>发动机机体解剖模型≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_Q" value="${materForm.CGJXYJBXH_Q}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_Q" value="${materForm.CGJXYJBSL_Q}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_Q" value="${materForm.CGJXYJB_Q}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_Q" value="${materForm.CGJXYJBBZ_Q}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>转向机构模型≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_R" value="${materForm.CGJXYJBXH_R}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_R" value="${materForm.CGJXYJBSL_R}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_R" value="${materForm.CGJXYJB_R}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_R" value="${materForm.CGJXYJBBZ_R}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td rowspan="6">5</td>
									<td rowspan="6">其他教学设备</td>
									<td>培训学时计算机记时管理系统</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_S" value="${materForm.CGJXYJBXH_S}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_S" value="${materForm.CGJXYJBSL_S}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_S" value="${materForm.CGJXYJB_S}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_S" value="${materForm.CGJXYJBBZ_S}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>教学磁板</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_T" value="${materForm.CGJXYJBXH_T}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_T" value="${materForm.CGJXYJBSL_T}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_T" value="${materForm.CGJXYJB_T}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_T" value="${materForm.CGJXYJBBZ_T}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>更换车轮工具</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_U" value="${materForm.CGJXYJBXH_U}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_U" value="${materForm.CGJXYJBSL_U}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_U" value="${materForm.CGJXYJB_U}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_U" value="${materForm.CGJXYJBBZ_U}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>车用灭火器</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_V" value="${materForm.CGJXYJBXH_V}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_V" value="${materForm.CGJXYJBSL_V}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_V" value="${materForm.CGJXYJB_V}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_V" value="${materForm.CGJXYJBBZ_V}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★红外线桩考仪≥1</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_W" value="${materForm.CGJXYJBXH_W}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_W" value="${materForm.CGJXYJBSL_W}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_W" value="${materForm.CGJXYJB_W}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_W" value="${materForm.CGJXYJBBZ_W}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>★汽车驾驶模拟器</td>
									<td><input type="text"
										class="syj-input1 validate[required] mininput" name="CGJXYJBXH_X" value="${materForm.CGJXYJBXH_X}"
										placeholder="请输入型号" maxlength="50" /></td>
									<td><input type="text"
										class="syj-input1 validate[required,custom[numberplus]] mininput" name="CGJXYJBSL_X" value="${materForm.CGJXYJBSL_X}"
										placeholder="请输入数量" maxlength="50" /></td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required] mininput"  dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="CGJXYJB_X" value="${materForm.CGJXYJB_X}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="CGJXYJBBZ_X" value="${materForm.CGJXYJBBZ_X}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
							</table>
							
							
						<div class="syj-title1">
							<span>教练场地</span>
						</div>
						<div >
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>总面积：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_ZMJ" value="${materForm.JLCD_ZMJ }"
										placeholder="请输入总面积" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>平场面积：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_PCMJ" value="${materForm.JLCD_PCMJ }"
										placeholder="请输入平场面积" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>大车车库：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_DCCK" value="${materForm.JLCD_DCCK }"
										placeholder="请输入大车车库" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>小车车库：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_XCCK" value="${materForm.JLCD_XCCK }"
										placeholder="请输入小车车库" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>道路面积：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required]" name="JLCD_DLMJ" value="${materForm.JLCD_DLMJ }"
										placeholder="请输入道路面积" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>长度：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_CD" value="${materForm.JLCD_CD }"
										placeholder="请输入长度" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>宽度：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JLCD_GD" value="${materForm.JLCD_GD }"
										placeholder="请输入宽度" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>地面质量：</th>
									<td colspan="3">
										<eve:exradiogroup name="JLCD_DMZL" width="400px"
										allowblank="false" value="${materForm.JLCD_DMZL}"
										radiovalues="1,2,3,4" radiolables="沙,土,柏油,水泥"
										></eve:exradiogroup>
									</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>其他设施：</th>
									<td colspan="3">
										<eve:excheckbox dataInterface="dictionaryService.findDatasForSelect"
										clazz="checkbox validate[required]"
										dataParams="JLCDQTCS" value="${materForm.JLCD_QTSZ}"
										 name="JLCD_QTSZ" width="500px"></eve:excheckbox>
									</td>
								</tr>
							</table>
							</div>
							
							<div class="syj-title1">
								<span>训练科目设置标准</span>
							</div>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="width: 20px;">序号</th>
									<th style="width: 100px;">内容</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>评审情况</th>
									<th>备注</th>
								</tr>
								<tr>
									<td>1</td>
									<td>连续障碍</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_A" value="${materForm.SZBZ_A}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_A" value="${materForm.SZBZBZ_A}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td>单边桥</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_B" value="${materForm.SZBZ_B}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_B" value="${materForm.SZBZBZ_B}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>3</td>
									<td>直角转弯</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_C" value="${materForm.SZBZ_C}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_C" value="${materForm.SZBZBZ_C}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>4</td>
									<td>侧方停车</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_D" value="${materForm.SZBZ_D}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_D" value="${materForm.SZBZBZ_D}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>5</td>
									<td>上坡路定点停车</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_E" value="${materForm.SZBZ_E}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_E" value="${materForm.SZBZBZ_E}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>6</td>
									<td>坡道起步</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_F" value="${materForm.SZBZ_F}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_F" value="${materForm.SZBZBZ_F}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>7</td>
									<td>限宽门</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_G" value="${materForm.SZBZ_G}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_G" value="${materForm.SZBZBZ_G}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>8</td>
									<td>百米加减档</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_H" value="${materForm.SZBZ_H}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_H" value="${materForm.SZBZBZ_H}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>9</td>
									<td>起伏路</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_I" value="${materForm.SZBZ_I}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_I" value="${materForm.SZBZBZ_I}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>10</td>
									<td>曲线行驶</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="SZBZ_J" value="${materForm.SZBZ_J}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="SZBZBZ_J" value="${materForm.SZBZBZ_J}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								
							</table>
							
							
							
							
							<div class="syj-title1">
								<span>教练车辆登记表</span>
							</div>
							<div id="CoachVehicleDiv">
								<div style="position:relative;">
									<div id="CoachVehicleToolbar">
										<c:if test="${materForm.operType=='write' }">
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
												plain="true" onclick="addCoachVehicle();">添加</a>
											<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
												onclick="removeCoachVehicle();">删除</a>
										</c:if>
									</div>
									<table class="easyui-datagrid" toolbar="#CoachVehicleToolbar" id="CoachVehicleGrid" rownumbers="true" border="true" fitColumns="true"
										data-options="singleSelect:true,autoSave:true,url:'',method:'post',onClickRow: onClickRowCoachVehicle" nowrap="false">
										<thead>
											<tr>
												<th data-options="field:'JLCPH',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">教练车牌号</th>
												<th data-options="field:'CPXH',align:'left',editor:{type:'validatebox',options:{required:true,}}"
													width="20%">厂牌型号</th>
												<th data-options="field:'FDJHM',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="10%">发动机号码</th>
												<th data-options="field:'CJHM',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="20%">车架号码</th>
												<th data-options="field:'BPSJ',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="15%">报牌时间</th>
												<th data-options="field:'CLJSDJPD',align:'left',editor:{type:'validatebox',options:{required:true,}}"
													width="15%">车辆技术等级评定</th>
												<th data-options="field:'BZ',align:'left',editor:{type:'validatebox',options:{required:true}}"
													width="9%">备注</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
						<div class="syj-title1">
							<span>教室</span>
						</div>
						<div >
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th rowspan="2"><font class="syj-color2">*</font>教室总面积：</th>
									<td rowspan="2"><input type="text"
										class="syj-input1 validate[required]" name="JSZMJ" value="${materForm.JSZMJ }"
										placeholder="请输入教室总面积" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>理论教室面积：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="LLJSMJ" value="${materForm.LLJSMJ }"
										placeholder="请输入理论教室面积" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>排故教室面积：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="PGJSMJ" value="${materForm.PGJSMJ }"
										placeholder="请输入排故教室面积" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>长度：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JSCD" value="${materForm.JSCD }"
										placeholder="请输入长度" maxlength="20" /></td>
									<th><font class="syj-color2">*</font>宽度：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="JSKD" value="${materForm.JSKD }"
										placeholder="请输入宽度" maxlength="20" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>教室内设置：</th>
									<td colspan="3">桌子<input type="text" style="width: 100px;"
										class="syj-input1 validate[required,custom[numberplus]]" name="JSNSZ_ZZ" value="${materForm.JSNSZ_ZZ }"
										placeholder="请输入" maxlength="20" />张  椅子 <input type="text" style="width: 100px;"
										class="syj-input1 validate[required,custom[numberplus]]" name="JSNSZ_YZ" value="${materForm.JSNSZ_YZ }"
										placeholder="请输入" maxlength="20" />张</td>
								</tr>
								<tr>
									<th>其他设施：</th>
									<td colspan="3"><input type="text"
										class="syj-input1" name="QTSZ" value="${materForm.QTSZ }"
										placeholder="请输入其他设施" maxlength="100" /></td>
								</tr>
							</table>
							</div>
							<div class="syj-title1">
								<span>办公设施及其他设施</span>
							</div>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="width: 20px;">序号</th>
									<th style="width: 100px;">内容</th>
									<th style="width: 100px;"><font class="syj-color2">*</font>评审情况</th>
									<th>备注</th>
								</tr>
								<tr>
									<td>1</td>
									<td>办公室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_A" value="${materForm.BGSZ_A}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_A" value="${materForm.BGSZBZ_A}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>2</td>
									<td>财务室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_B" value="${materForm.BGSZ_B}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_B" value="${materForm.BGSZBZ_B}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>3</td>
									<td>教员休息室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_C" value="${materForm.BGSZ_C}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_C" value="${materForm.BGSZBZ_C}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>4</td>
									<td>安全教练室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_D" value="${materForm.BGSZ_D}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_D" value="${materForm.BGSZBZ_D}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>5</td>
									<td>学员休息室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_E" value="${materForm.BGSZ_E}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_E" value="${materForm.BGSZBZ_E}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>6</td>
									<td>娱乐室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_F" value="${materForm.BGSZ_F}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_F" value="${materForm.BGSZBZ_F}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>7</td>
									<td>会议室</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_G" value="${materForm.BGSZ_G}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_G" value="${materForm.BGSZBZ_G}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>8</td>
									<td>厨房</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_H" value="${materForm.BGSZ_H}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_H" value="${materForm.BGSZBZ_H}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>9</td>
									<td>饭厅</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_I" value="${materForm.BGSZ_I}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_I" value="${materForm.BGSZBZ_I}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								<tr>
									<td>10</td>
									<td>其他</td>
									<td>
										<eve:eveselect clazz="input-dropdown validate[required]" dataParams="HasOrNoHas"
										dataInterface="dictionaryService.findDatasForSelect"
										defaultEmptyText="请选择评审情况" name="BGSZ_J" value="${materForm.BGSZ_J}">
										</eve:eveselect>
									</td>
									<td><input type="text"
										class="syj-input1" name="BGSZBZ_J" value="${materForm.BGSZBZ_J}"
										placeholder="请输入备注" maxlength="100" /></td>
								</tr>
								
							</table>
							
							
							
							
					</form>
				</div>
			</div>
		</div>
        <div class="tbmargin40 syj-btn">
	        <c:if test="${materForm.operType=='read' }">
		        <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">关闭</a>
	        </c:if>
        	<c:if test="${materForm.operType=='write' }">
		        <a href="javascript:void(0);" onclick="$('#RFTAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>

</body>

<script type="text/javascript">


/**理论教员可编辑表格开始*/
var editIndexTheoreticalInstructor = undefined;
/**
 * 结束编辑模式
 */
function endEditingTheoreticalInstructor(){
	if (editIndexTheoreticalInstructor == undefined){return true}
	if ($("#TheoreticalInstructorGrid").datagrid("validateRow", editIndexTheoreticalInstructor)){
		$("#TheoreticalInstructorGrid").datagrid("endEdit", editIndexTheoreticalInstructor);
		editIndexTheoreticalInstructor = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加信息
 */
function addTheoreticalInstructor(){
	if (endEditingTheoreticalInstructor()){
		$("#TheoreticalInstructorGrid").datagrid("appendRow",{});
		editIndexTheoreticalInstructor = $("#TheoreticalInstructorGrid").datagrid("getRows").length-1;
		$("#TheoreticalInstructorGrid").datagrid("selectRow", editIndexTheoreticalInstructor)
				.datagrid("beginEdit", editIndexTheoreticalInstructor);
	}
}
/**
 * 删除信息
 */
function removeTheoreticalInstructor() {
	if (editIndexTheoreticalInstructor == undefined){return}
	$("#TheoreticalInstructorGrid").datagrid("cancelEdit", editIndexTheoreticalInstructor)
			.datagrid("deleteRow", editIndexTheoreticalInstructor);
	editIndexTheoreticalInstructor = undefined;
}
/**
 * 保存信息
 */
function confirmTheoreticalInstructor(){
	var e = endEditingTheoreticalInstructor();
    if (e){
        $("#TheoreticalInstructorGrid").datagrid("acceptChanges");
    }
    return e;
}
/**
 * 编辑行
 */
function onClickRowTheoreticalInstructor(index){
	if (editIndexTheoreticalInstructor != index){
		if (endEditingTheoreticalInstructor()){
			$("#TheoreticalInstructorGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndexTheoreticalInstructor = index;
		} else {
			$("#TheoreticalInstructorGrid").datagrid("selectRow", editIndexTheoreticalInstructor);
		}
	}
}
/**
 * 提交方法
 */
function TheoreticalInstructorSubmit(){
	if(!confirmTheoreticalInstructor()){
        alert("“理论教员”信息不完整。请核对并填写后再提交。")
        return false;
    }
	var rows = $("#TheoreticalInstructorGrid").datagrid("getRows");
	if(rows.length > 0){
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"XM":rows[i].XM,
				"SFZH":rows[i].SFZH,
				"WHCD":rows[i].WHCD,
				"JSDAH":rows[i].JSDAH,
				"JLYH":rows[i].JLYH,
				"JL":rows[i].JL,
				"BZ":rows[i].BZ
			});
		}
		$("input[type='hidden'][name='THEORETICALINSTRUCTOR_JSON']").val(JSON.stringify(datas));
	}
	return true;
}
/**理论教员可编辑表格结束*/

/**实操教员可编辑表格开始*/
var editIndexPracticalTeacher = undefined;
/**
 * 结束编辑模式
 */
function endEditingPracticalTeacher(){
	if (editIndexPracticalTeacher == undefined){return true}
	if ($("#PracticalTeacherGrid").datagrid("validateRow", editIndexPracticalTeacher)){
		$("#PracticalTeacherGrid").datagrid("endEdit", editIndexPracticalTeacher);
		editIndexPracticalTeacher = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加信息
 */
function addPracticalTeacher(){
	if (endEditingPracticalTeacher()){
		$("#PracticalTeacherGrid").datagrid("appendRow",{});
		editIndexPracticalTeacher = $("#PracticalTeacherGrid").datagrid("getRows").length-1;
		$("#PracticalTeacherGrid").datagrid("selectRow", editIndexPracticalTeacher)
				.datagrid("beginEdit", editIndexPracticalTeacher);
	}
}
/**
 * 删除信息
 */
function removePracticalTeacher() {
	if (editIndexPracticalTeacher == undefined){return}
	$("#PracticalTeacherGrid").datagrid("cancelEdit", editIndexPracticalTeacher)
			.datagrid("deleteRow", editIndexPracticalTeacher);
	editIndexPracticalTeacher = undefined;
}
/**
 * 保存信息
 */
function confirmPracticalTeacher(){
	var e = endEditingPracticalTeacher();
    if (e){
        $("#PracticalTeacherGrid").datagrid("acceptChanges");
    }
    return e;
}
/**
 * 编辑行
 */
function onClickRowPracticalTeacher(index){
	if (editIndexPracticalTeacher != index){
		if (endEditingPracticalTeacher()){
			$("#PracticalTeacherGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndexPracticalTeacher = index;
		} else {
			$("#PracticalTeacherGrid").datagrid("selectRow", editIndexPracticalTeacher);
		}
	}
}
/**
 * 提交方法
 */
function PracticalTeacherSubmit(){
	if(!confirmPracticalTeacher()){
        alert("“实操教员”信息不完整。请核对并填写后再提交。")
        return false;
    }
	var rows = $("#PracticalTeacherGrid").datagrid("getRows");
	if(rows.length > 0){
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"XM":rows[i].XM,
				"SFZH":rows[i].SFZH,
				"WHCD":rows[i].WHCD,
				"JSDAH":rows[i].JSDAH,
				"JLYH":rows[i].JLYH,
				"JL":rows[i].JL,
				"BZ":rows[i].BZ,
				"ZJCXH":rows[i].ZJCXH,
				"ZJCX":rows[i].ZJCX
			});
		}
		$("input[type='hidden'][name='PRACTICALTEACHER_JSON']").val(JSON.stringify(datas));
	}
	return true;
}
/**实操教员可编辑表格结束*/

/**行政管理人员可编辑表格开始*/
var editIndexAdministrativeStaff = undefined;
/**
 * 结束编辑模式
 */
function endEditingAdministrativeStaff(){
	if (editIndexAdministrativeStaff == undefined){return true}
	if ($("#AdministrativeStaffGrid").datagrid("validateRow", editIndexAdministrativeStaff)){
		$("#AdministrativeStaffGrid").datagrid("endEdit", editIndexAdministrativeStaff);
		editIndexAdministrativeStaff = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加信息
 */
function addAdministrativeStaff(){
	if (endEditingAdministrativeStaff()){
		$("#AdministrativeStaffGrid").datagrid("appendRow",{});
		editIndexAdministrativeStaff = $("#AdministrativeStaffGrid").datagrid("getRows").length-1;
		$("#AdministrativeStaffGrid").datagrid("selectRow", editIndexAdministrativeStaff)
				.datagrid("beginEdit", editIndexAdministrativeStaff);
	}
}
/**
 * 删除信息
 */
function removeAdministrativeStaff() {
	if (editIndexAdministrativeStaff == undefined){return}
	$("#AdministrativeStaffGrid").datagrid("cancelEdit", editIndexAdministrativeStaff)
			.datagrid("deleteRow", editIndexAdministrativeStaff);
	editIndexAdministrativeStaff = undefined;
}
/**
 * 保存信息
 */
function confirmAdministrativeStaff(){
	var e = endEditingAdministrativeStaff();
    if (e){
        $("#AdministrativeStaffGrid").datagrid("acceptChanges");
    }
    return e;
}
/**
 * 编辑行
 */
function onClickRowAdministrativeStaff(index){
	if (editIndexAdministrativeStaff != index){
		if (endEditingAdministrativeStaff()){
			$("#AdministrativeStaffGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndexAdministrativeStaff = index;
		} else {
			$("#AdministrativeStaffGrid").datagrid("selectRow", editIndexAdministrativeStaff);
		}
	}
}
/**
 * 提交方法
 */
function AdministrativeStaffSubmit(){
	if(!confirmAdministrativeStaff()){
        alert("“行政管理人员”信息不完整。请核对并填写后再提交。")
        return false;
    }
	var rows = $("#AdministrativeStaffGrid").datagrid("getRows");
	if(rows.length > 0){
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"XM":rows[i].XM,
				"SFZH":rows[i].SFZH,
				"WHCD":rows[i].WHCD,
				"JSDAH":rows[i].JSDAH,
				"JL":rows[i].JL,
				"BZ":rows[i].BZ,
				"ZW":rows[i].ZW
			});
		}
		$("input[type='hidden'][name='ADMINISTRATIVESTAFF_JSON']").val(JSON.stringify(datas));
	}
	return true;
}
/**行政管理人员可编辑表格结束*/

/**教练车辆登记表可编辑表格开始*/
var editIndexCoachVehicle = undefined;
/**
 * 结束编辑模式
 */
function endEditingCoachVehicle(){
	if (editIndexCoachVehicle == undefined){return true}
	if ($("#CoachVehicleGrid").datagrid("validateRow", editIndexCoachVehicle)){
		$("#CoachVehicleGrid").datagrid("endEdit", editIndexCoachVehicle);
		editIndexCoachVehicle = undefined;
		return true;
	} else {
		return false;
	}
}
/**
 * 添加信息
 */
function addCoachVehicle(){
	if (endEditingCoachVehicle()){
		$("#CoachVehicleGrid").datagrid("appendRow",{});
		editIndexCoachVehicle = $("#CoachVehicleGrid").datagrid("getRows").length-1;
		$("#CoachVehicleGrid").datagrid("selectRow", editIndexCoachVehicle)
				.datagrid("beginEdit", editIndexCoachVehicle);
	}
}
/**
 * 删除信息
 */
function removeCoachVehicle() {
	if (editIndexCoachVehicle == undefined){return}
	$("#CoachVehicleGrid").datagrid("cancelEdit", editIndexCoachVehicle)
			.datagrid("deleteRow", editIndexCoachVehicle);
	editIndexCoachVehicle = undefined;
}
/**
 * 保存信息
 */
function confirmCoachVehicle(){
	var e = endEditingCoachVehicle();
    if (e){
        $("#CoachVehicleGrid").datagrid("acceptChanges");
    }
    return e;
}
/**
 * 编辑行
 */
function onClickRowCoachVehicle(index){
	if (editIndexCoachVehicle != index){
		if (endEditingCoachVehicle()){
			$("#CoachVehicleGrid").datagrid("selectRow", index)
					.datagrid("beginEdit", index);
			editIndexCoachVehicle = index;
		} else {
			$("#CoachVehicleGrid").datagrid("selectRow", editIndexCoachVehicle);
		}
	}
}
/**
 * 提交方法
 */
function CoachVehicleSubmit(){
	if(!confirmCoachVehicle()){
        alert("“教练车辆登记表”信息不完整。请核对并填写后再提交。")
        return false;
    }
	var rows = $("#CoachVehicleGrid").datagrid("getRows");
	if(rows.length > 0){
		var datas = [];
		for(var i = 0;i<rows.length;i++){
			datas.push({
				"JLCPH":rows[i].JLCPH,
				"CPXH":rows[i].CPXH,
				"FDJHM":rows[i].FDJHM,
				"CJHM":rows[i].CJHM,
				"BPSJ":rows[i].BPSJ,
				"CLJSDJPD":rows[i].CLJSDJPD,
				"BZ":rows[i].BZ
			});
		}
		$("input[type='hidden'][name='COACHVEHICLE_JSON']").val(JSON.stringify(datas));
	}
	return true;
}
/**教练车辆登记表可编辑表格结束*/
</script>