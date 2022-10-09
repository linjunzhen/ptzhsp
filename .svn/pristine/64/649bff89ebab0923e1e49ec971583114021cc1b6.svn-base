<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
	<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,ztree,swfupload,eweb,artdialog"></eve:resources>
	<script type="text/javascript">
		$(function() {
			AppUtil.initWindowForm("DtjzwBackForm", function(form, valid) {
				if (valid) {
					//将提交按钮禁用,防止重复提交
					$("input[type='submit']").attr("disabled","disabled");
					var formData = $("#DtjzwBackForm").serialize();
					var url = $("#DtjzwBackForm").attr("action");
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
								top.$("#EFLOWFORM_IFRAME")[0].contentWindow.$("#dtjzwxxBackGrid").datagrid("reload");
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
			},"TB_FC_UNIT_PROJECT_INFO",null, $("input[name='FC_UNIT_INFO_ID']").val());
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
	<form id="DtjzwBackForm" method="post" action="xfDesignController.do?saveOrUpdateDtjzw">
	    <div>
	     	<!--==========隐藏域部分开始 ===========-->
	    	<input type="hidden" name="FC_UNIT_INFO_ID" value="${dtjzwxxInfo.FC_UNIT_INFO_ID}">
	    	<c:if test="${dtjzwxxValue == true }">
	    		<input type="hidden" name="PRJ_NUM" value="${dtjzwxxInfo.PRJ_NUM}">
	    		<input type="hidden" name="PRJ_CODE" value="${dtjzwxxInfo.PRJ_CODE }">
	    		<input type="hidden" name="UNIT_CODE" value="${dtjzwxxInfo.UNIT_CODE}">
	    	</c:if>
	    	<c:if test="${dtjzwxxValue != true }">
	    		<input type="hidden" name="PRJ_NUM" value="${prj_num }">
	    		<input type="hidden" name="PRJ_CODE" value="${prj_code }">
	    		<input type="hidden" name="UNIT_CODE" value="${prj_code }">
	    	</c:if>
	    	<!--==========隐藏域部分结束 ===========-->
		    <table style="width:100%;border-collapse:collapse;"class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend"  style="font-weight:bold;" >单体建筑物信息维护</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑物名称
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.SUB_PRJ_NAME}" name="SUB_PRJ_NAME" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center" style="width:30%">
						<font class="dddl_platform_html_requiredFlag">*</font>结构类型
					</td>
					<td style="width:70%">
						&nbsp;<eve:eveselect clazz="tab_text validate[required]" dataParams="TBFCPRJSTRUCTURETYPEDIC"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择责任主体类别" name="FC_STRUCTURE_TYPE_NUM"
							value="${dtjzwxxInfo.FC_STRUCTURE_TYPE_NUM}">
						</eve:eveselect>
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center" style="width:30%">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑耐火等级
					</td>
					<td style="width:70%">
						&nbsp;<eve:eveselect clazz="tab_text validate[required]" dataParams="TBREFLEVELDIC"
							dataInterface="dictionaryService.findDatasForSelect"
							defaultEmptyText="选择责任主体类别" name="REFRACTORY_LEVEL_NUM"
							value="${dtjzwxxInfo.REFRACTORY_LEVEL_NUM}">
						</eve:eveselect>
					</td>
				</tr>
				
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑地上层数
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.FLOOR_COUNT}" name="FLOOR_COUNT" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑地下层数
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.BOTTOM_FLOOR_COUNT}" name="BOTTOM_FLOOR_COUNT" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑高度(m)
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.BUILD_HEIGHT}" name="BUILD_HEIGHT" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑占地面积(㎡)
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.BUILD_AREA}" name="BUILD_AREA" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑地上面积(㎡)
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.FLOOR_BUILD_AREA}" name="FLOOR_BUILD_AREA" />
					</td>
				</tr>
				<tr style="height:40px;">
					<td class="center">
						<font class="dddl_platform_html_requiredFlag">*</font>建筑地下面积(㎡)
					</td>
					<td>
						<input type="text" style="width:579px;float:left;" class="eve-input validate[required]" 
							value="${dtjzwxxInfo.BOTTOM_FLOOR_BUILD_AREA}" name="BOTTOM_FLOOR_BUILD_AREA" />
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
