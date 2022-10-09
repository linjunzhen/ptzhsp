<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="net.evecom.platform.system.model.SysUser"%>
<%@ taglib prefix="eve" uri="/evetag"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,swfupload"></eve:resources>
<!-- my97 begin -->
<script type="text/javascript" src="<%=path%>/plug-in/My97DatePicker/WdatePicker.js"></script>
<!-- my97 end -->
 <script type="text/javascript" src="<%=path%>/plug-in/jqueryUpload/AppUtilNew.js"></script> 
<script type="text/javascript">
var __ctxPath="<%=request.getContextPath() %>";
$(function() {
	var fileids=$("#NOTICE_FILE_IDS").val();
		$.post("executionController.do?getResultFiles&fileIds="+fileids,{fileIds:fileids}, function(resultJson) {
	    		 if(resultJson!="websessiontimeout"){
	    		 	$("#fileListDiv").html("");
	    		 	var newhtml = "";
	    		 	var list=resultJson.rows;
	    		 	for(var i=0; i<list.length; i++){
	    		 		newhtml+='<p style="margin-left: 5px; margin-right: 5px;line-height: 20px;">';
	    		 		newhtml+='<a style="color: blue;" href="javascript:AppUtil.downLoadFile(\''+list[i].FILE_ID+'\');">';
	    		 		newhtml+=list[i].FILE_NAME+'</a>';
	    		 		newhtml+='</p>';
	    		 	} 
	    		 	$("#fileListDiv").html(newhtml);
	    		 }
         });			
});

	function onSelectClass(o){
		if(o==1){
			$("#resultcontent_tr").show();
			$("#resultcontent").attr("disabled",false); 
		}else{
			$("#resultcontent_tr").hide();
			$("#resultcontent").attr("disabled",true); 
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
		<div region="center" style="min-height:360px;">
			<!--==========隐藏域部分开始 ===========-->
			<input type="hidden" name="NOTICE_ID" value="${noticeInfo.NOTICE_ID}">
			<input type="hidden" name="NOTICE_FILE_URLS" >
			<input type="hidden" id="NOTICE_FILE_IDS" name="NOTICE_FILE_IDS" value="${noticeInfo.NOTICE_FILE_IDS}">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
			<!--  <tr style="height:29px;">
				<td colspan="4" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			   </tr>  -->	
				<tr style="height:35px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">公告标题：</span>
					</td>
					<td colspan="3">
						<input class="eve-input validate[required]" maxLength="100" type="text" 
						name="NOTICE_TITLE" value="${noticeInfo.NOTICE_TITLE}" readonly="readonly"
						 style="width:500px;"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				
				<tr style="height:35px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">开始日期：</span>
					</td>
					<td style="width: 207px;">
						<input type="text" disabled="disabled"
                                style="width:187px;float:left; height: 24px; line-height: 24px;"  readonly="ture" 
                                class="validate[required] Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end_date\')}'})"
                                 id="start_date" name="BEGIN_TIME" value="${noticeInfo.BEGIN_TIME}"  />
                                 <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;">结束日期：</span>
					</td>
					<td>
						<input type="text" disabled="disabled"
                                style="width:197px;float:left; height: 24px; line-height: 24px;" class="validate[required] Wdate"
                                id="end_date" name="END_TIME" value="${noticeInfo.END_TIME}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start_date\')}'})"/>
						<font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:200px;">
					<td style="width:100px;">
						<span style="width: 100px;float:left;text-align:right;height:100px;">公告内容：</span>
					</td>
					<td  colspan="3">
						<textarea class="eve-textarea validate[required],maxSize[500]" rows="3" cols="200" style="width:500px;height:200px;" 
						  name="NOTICE_CONTENT" readonly="readonly">${noticeInfo.NOTICE_CONTENT}</textarea>
						  <font class="dddl_platform_html_requiredFlag">*</font>
					</td>
				</tr>
				<tr style="height:35px;">
					<td>
						<span style="width: 100px;float:left;text-align:right;">状态：</span>
					</td>
					<td colspan="3">
					<select defaultemptytext="请选择状态" disabled="disabled" class="eve-input validate[required]" name="STATUS" >
						<option value="">请选择状态</option>						
						<option value="0" <c:if test="${noticeInfo.STATUS==0}">selected="selected"</c:if>>暂存</option>
						<option value="1" <c:if test="${noticeInfo.STATUS==1}">selected="selected"</c:if>>已发布</option>
					</select>	
					</td>
				</tr>
				<tr style="height:35px;">
					<td ><span style="width: 100px;float:left;text-align:right;">附件：</span>
					<td colspan="3">
						<div style="width:100%;" id=fileListDiv></div>
					</td>
				</tr>
			</table>
		</div>

</body>

