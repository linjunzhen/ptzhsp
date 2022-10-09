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
			AppUtil.initWindowForm("CARRENTAL_FORM", function(form, valid) {
				if (valid) {
					//将提交按钮禁用,防止重复提交
					var formData = $("#CARRENTAL_FORM").serialize();
					var url = $("#CARRENTAL_FORM").attr("action");
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
			
			if(${materForm.RECORD_ID==null }){
				//企业名称
				var APPLICANT_NAME = parent.$("input[name='COMPANY_NAME']").val();
				$("input[name='APPLY_COMPANY']").val(APPLICANT_NAME);
				var itemCode = parent.$("input[name='ITEM_CODE']").val();
				var APPLY_PHONE = "";
				var MAILING_ADDR = "";
				var leaderName = "";
				var handlerName = "";
				if(itemCode=='201605170002XK00101'||itemCode=='201605170002XK00102'){
					APPLY_PHONE = parent.$("input[name='CONTACT_PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSSINESS_ADDR']").val();
					leaderName = parent.$("input[name='LEGAL_NAME']").val();
					handlerName = parent.$("input[name='OPERATOR_NAME']").val();
				}else if(itemCode=='201605170002XK00103'){
					APPLY_PHONE = parent.$("input[name='PHONE']").val();
					MAILING_ADDR = parent.$("input[name='BUSINESS_ADDR']").val();
				}
				//联系电话
				$("input[name='PHONE']").val(APPLY_PHONE);
				//生产地址/通信地址
				$("input[name='COMPANY_ADDR']").val(MAILING_ADDR);
				//企业法人
				$("input[name='LEGAL_NAME']").val(leaderName);
				//经办人
				$("input[name='HANDLER_NAME']").val(handlerName);
			}
		});
	</script>
</head>
<body>
	<div class="container">
		<div class="syj-sbmain2 tmargin20">
			<div class="syj-tyys tmargin20" style="z-index: 99;" id="formcontainer">
				<div class="bd margin20">
					<form action="domesticControllerController/saveRelatedMaterForm.do" method="post" id="CARRENTAL_FORM">
						<input type="hidden" name="formName" value="${materForm.formName }"/>
						<input type="hidden" name="EXE_ID" value="${materForm.EXE_ID }"/>
						<input type="hidden" name="RECORD_ID" value="${materForm.RECORD_ID }"/>
						<div class="syj-title1">
							<span>申请信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>申请企业：</th>
									<td colspan="3" style="border-bottom: none;"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="APPLY_COMPANY" value="${materForm.APPLY_COMPANY }"
										placeholder="请输入申请企业名称" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>法定代表人：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="LEGAL_NAME" value="${materForm.LEGAL_NAME }"
										placeholder="请输入法定代表人姓名" maxlength="16" /></td>
									<th><font class="syj-color2">*</font>经办人：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="HANDLER_NAME" value="${materForm.HANDLER_NAME }"
										placeholder="请输入经办人姓名" maxlength="16" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>企业地址：</th>
									<td colspan="3"><input type="text" maxlength="62"
										class="syj-input1 validate[required]" name="COMPANY_ADDR" value="${materForm.COMPANY_ADDR }"
										placeholder="请输入企业地址" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>联系电话：</th>
									<td><input type="text"
										class="syj-input1 validate[[],custom[fixPhoneWithAreaCode]]" name="PHONE" value="${materForm.PHONE }"
										placeholder="请输入联系电话" maxlength="16" /></td>
									<th><font class="syj-color2">*</font>企业员工(人)：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="EMPLOYEES_NUM" value="${materForm.EMPLOYEES_NUM }"
										placeholder="请输入企业员工人数" maxlength="8" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>自有车辆(辆)：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[integerplus]]" name="HAVECAR_NUM" value="${materForm.HAVECAR_NUM }"
										placeholder="请输入自有车辆数量" maxlength="8" /></td>
									<th><font class="syj-color2">*</font>办公场所(㎡)：</th>
									<td><input type="text"
										class="syj-input1 validate[required,custom[number2plus]]" name="OFFICE_SPACE" value="${materForm.OFFICE_SPACE }"
										placeholder="请输入办公场所面积" maxlength="8" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>停车场地(㎡)：</th>
									<td colspan="3"><input type="text"
										class="syj-input1 validate[required,custom[number2plus]]" name="PARKING_AREA" value="${materForm.PARKING_AREA }"
										placeholder="请输入停车场地面积" maxlength="8" /></td>
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
		        <a href="javascript:void(0);" onclick="$('#CARRENTAL_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>
</body>