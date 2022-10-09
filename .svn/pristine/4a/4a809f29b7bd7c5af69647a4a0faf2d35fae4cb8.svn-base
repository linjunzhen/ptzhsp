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
			
			AppUtil.initWindowForm("RFTAPPLY_FORM", function(form, valid) {
				if (valid) {

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
			if(${materForm.RECORD_ID==null }){
				var APPLICANT_NAME = parent.$("input[name='COMPANY_NAME']").val();
				$("input[name='APPLLYD_NAME']").val(APPLICANT_NAME);
			}
			

			
		});
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
						<div class="syj-title1">
							<span>基本信息</span>
						</div>
						<div style="position:relative;">
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th style="border-bottom: none;"><font class="syj-color2">*</font>单位名称：</th>
									<td style="border-bottom: none;"><input type="text"
										class="syj-input1 validate[required]" name="APPLLYD_NAME" value="${materForm.APPLLYD_NAME }"
										placeholder="请输入单位名称" maxlength="50" /></td>
								</tr>
							</table>
							<table cellpadding="0" cellspacing="0" class="syj-table2">
								<tr>
									<th><font class="syj-color2">*</font>联系人及电话：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="APPLLYLXR_NAME" value="${materForm.APPLLYLXR_NAME }"
										placeholder="请输入联系人及电话" maxlength="50" /></td>
									<th><font class="syj-color2">*</font>变更、补办证件名称：</th>
									<td><input type="text"
										class="syj-input1 validate[required]" name="APPLLYIC_NAME" value="${materForm.APPLLYIC_NAME}"
										placeholder="请输入变更、补办证件名称" maxlength="50" /></td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>原证（牌）号及相关内容：</th>
									<td colspan="3">
										<textarea rows="3" cols="200" name="APPLLYOLD_CONTENT" 
										class="eve-textarea validate[required],maxSize[500]" 
										style="width:96%;height:80px;" 
										placeholder="请输入原证（牌）号及相关内容" 
										id="form-validation-field-0">${materForm.APPLLYOLD_CONTENT }</textarea>
										</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>申请理由：</th>
									<td colspan="3">
										<textarea rows="3" cols="200" name="APPLLYLY_CONTENT" 
										class="eve-textarea validate[required],maxSize[500]" 
										style="width:96%;height:80px;" 
										placeholder="请输入申请理由" 
										id="form-validation-field-0">${materForm.APPLLYLY_CONTENT }</textarea>
										</td>
								</tr>
								<tr>
									<th><font class="syj-color2">*</font>遗失登报情况：</th>
									<td colspan="3">
										<textarea rows="3" cols="200" name="APPLLYDB_CONTENT" 
										class="eve-textarea validate[required],maxSize[500]" 
										 style="width:96%;height:80px;" 
										placeholder="请输入遗失登报情况" 
										id="form-validation-field-0">${materForm.APPLLYDB_CONTENT }</textarea>
										</td>
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
		        <a href="javascript:void(0);" onclick="$('#RFTAPPLY_FORM').submit();" class="syj-btnsave">保 存</a>
	            <a href="javascript:void(0);" class="syj-btnsbt" onclick="AppUtil.closeLayer();">取消</a>
	        </c:if>
        </div>
	</div>

</body>