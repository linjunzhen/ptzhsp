<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("ZrztxxBackForm", function(form, valid) {
				if (valid) {
					//将提交按钮禁用,防止重复提交
					$("input[type='submit']").attr("disabled","disabled");
					var formData = $("#ZrztxxBackForm").serialize();
					var url = $("#ZrztxxBackForm").attr("action");
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
								top.$("#EFLOWFORM_IFRAME")[0].contentWindow.$("#zrztxxBackGrid").datagrid("reload");
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
			},"TB_FC_PROJECT_CORP_INFO",null, $("input[name='FC_CORP_INFO_ID']").val());
		});
	</script>
</head>
<style>
	.center{
		text-align:center;
	}
	center_text{
	　　display:inline-block;
	　　width:500px
	}
</style>
<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ZrztxxBackForm" method="post" action="xfDesignController.do?saveOrUpdateZrzt">
	    <div>
	     	<!--==========隐藏域部分开始 ===========-->
	    	<input type="hidden" name="FC_CORP_INFO_ID" value="${zrztxxInfo.FC_CORP_INFO_ID}">
	    	<c:if test="${zrztxxValue == true }">
	    		<input type="hidden" name="PRJ_NUM" value="${zrztxxInfo.PRJ_NUM}">
	    		<input type="hidden" name="PRJ_CODE" value="${zrztxxInfo.PRJ_CODE }">
	    		<input type="hidden" name="UNIT_CODE" value="${zrztxxInfo.UNIT_CODE}">
	    	</c:if>
	    	<c:if test="${zrztxxValue != true }">
	    		<input type="hidden" name="PRJ_NUM" value="${prj_num }">
	    		<input type="hidden" name="PRJ_CODE" value="${prj_code }">
	    		<input type="hidden" name="UNIT_CODE" value="${prj_code }">
	    	</c:if>
	    	<!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"  style="font-weight:bold;" >责任主体信息维护</td>
				</tr>
				<tr style="height:40px;">
					<td class="center" style="width:30%">
						<font class="dddl_platform_html_requiredFlag">*</font>责任主体类别
					</td>
					<td style="width:70%">
						&nbsp;<eve:eveselect clazz="tab_text validate[required]" dataParams="TBCORPROLEDIC"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择责任主体类别" name="CORP_ROLE_NUM"
							value="${zrztxxInfo.CORP_ROLE_NUM}">
						</eve:eveselect>
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>责任主体名称
					</td>
					<td>
						
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${zrztxxInfo.CORP_NAME}" name="CORP_NAME" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						资质等级
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input" 
							value="${zrztxxInfo.CORP_LEVEL}" name="CORP_LEVEL" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						法定代表人/主要负责人
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input" 
							value="${zrztxxInfo.LEGAL_REPRESENT}" name="LEGAL_REPRESENT" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						联系人
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input" 
							value="${zrztxxInfo.PERSON_NAME}" name="PERSON_NAME" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						联系人电话
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input" 
							value="${zrztxxInfo.PERSON_PHONE}" name="PERSON_PHONE" />
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
		    <input value="保存" type="submit"  class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
