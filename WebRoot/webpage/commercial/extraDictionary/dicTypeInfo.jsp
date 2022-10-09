<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("DicTypeForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#DicTypeForm").serialize();
				var url = $("#DicTypeForm").attr("action");
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
							parent.$.fn.zTree.getZTreeObj("ExtraDicTypeTree").reAsyncChildNodes(null, "refresh");
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
		}, "T_COMMERCIAL_DICTYPE");
	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="DicTypeForm" method="post" action="extraDicTypeController.do?saveOrUpdateTree">
	    <div  id="DicTypeFormDiv" data-options="region:'center',split:true,border:false">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TYPE_ID" value="${dicType.TYPE_ID}">
	    <input type="hidden" name="PARENT_ID" value="${dicType.PARENT_ID}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			
			
			<tr>
				<td colspan="2">
				<span style="width: 100px;float:left;text-align:right;">上级类别：</span>
				${dicType.PARENT_NAME}
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">类别名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${dicType.TYPE_NAME}" maxlength="30"
					 class="eve-input validate[required]" name="TYPE_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td >
				<span style="width:100px;float:left;text-align:right;">类别编码：</span>
				<c:if test="${dicType.TYPE_ID!=null}">
				   ${dicType.TYPE_CODE}
				</c:if>
				<c:if test="${dicType.TYPE_ID==null}">
				<input
					type="text" style="width:150px;float:left;" 
					value="${dicType.TYPE_CODE}" id="TYPE_CODE" maxlength="32"
					class="eve-input validate[required,custom[onlyLetterNumber],ajax[ajaxVerifyValueExist]]" name="TYPE_CODE" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="3">
				<span style="width: 100px;float:left;text-align:right;">类别描述：</span>
				<input
					type="text" style="width:450px;float:left;"
					value="${dicType.TYPE_DESC}" maxlength="30"
					 class="eve-input" name="TYPE_DESC" /> 
				</td>
			</tr>
			
		</table>
		</div>
		
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
			    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
			    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
			</div>
		</div>
	</form>
</body>
