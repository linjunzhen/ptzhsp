<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("TagForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled","disabled");
				var formData = $("#TagForm").serialize();
				var url = $("#TagForm").attr("action");
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
							parent.$("#centerTagGrid").datagrid("reload");
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
		},"T_USERCENTER_TAG");
		
		$("input[name='TAG_LEVEL']").click(function(){
			var Value = $(this).val();
			if(Value=='02'){
				$("#parentTag").attr("style","");
			}else{
				$("#parentTag").attr("style","display:none;");
			}
		});
		
		if('${tagInfo.TAG_LEVEL}'=='02'){
			$("#parentTag").attr("style","");
		}
	});
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="TagForm" method="post" action="tagController.do?saveOrUpdate">
	    <div  id="TagFormDiv">
	    <%--==========隐藏域部分开始 ===========--%>
	    <input type="hidden" name="TAG_ID" value="${tagInfo.TAG_ID}">
	    <input type="hidden" name="TYPE_ID" value="${tagInfo.TYPE_ID}">
	    <input type="hidden" name="TYPE_NAME" value="${tagInfo.TYPE_NAME}">
	    <%--==========隐藏域部分结束 ===========--%>
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr style="height:29px;">
				<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">所属类别：</span>
				${tagInfo.TYPE_NAME}
				</td>
				
				<td>
				</td>
			</tr>
			
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">标签级别：</span>
					<eve:exradiogroup name="TAG_LEVEL" width="200" radiolables="一级标签,二级标签" value="${tagInfo.TAG_LEVEL}" radiovalues="01,02" selectfirst="true"></eve:exradiogroup>
				</td>
				<td id="parentTag" style="display: none;">
					<span style="width:100px;float:left;text-align:right;">父标签：</span>
					<eve:eveselect clazz="eve-input validate[required]" dataParams="${tagInfo.TYPE_ID}"
						dataInterface="tagService.findParentForSelect" value="${tagInfo.PARENT_TAG}" 
						defaultEmptyText="==选择父标签==" name="PARENT_TAG"></eve:eveselect>
					<%-- <input type="hidden" name="PARENT_TAG" value="${tagInfo.PARENT_TAG}">
					<input
						type="text" style="width:150px;float:left;" 
						value="${tagInfo.PARENT_TAG_NAME}" maxlength="128"
						class="eve-input validate[required]" name="PARENT_TAG_NAME" /> --%> 
						<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
			</tr>
			<tr>
				<td>
				<span style="width: 100px;float:left;text-align:right;">标签名称：</span>
				<input
					type="text" style="width:150px;float:left;"
					value="${tagInfo.TAG_NAME}" maxlength="60"
					 class="eve-input validate[required]" name="TAG_NAME" /> 
					<font class="dddl_platform_html_requiredFlag">*</font>
				</td>
				<td>
					<span style="width:100px;float:left;text-align:right;">标签编码：</span>
					<c:if test="${tagInfo.TAG_ID!=null}"><span style="width:150px;float:left;">${tagInfo.TAG_KEY}</span></c:if>
					<c:if test="${tagInfo.TAG_ID==null}">
						<input
							type="text" style="width:150px;float:left;" 
							value="${tagInfo.TAG_KEY}" maxlength="128" id="TAG_KEY"
							class="eve-input validate[required,ajax[ajaxVerifyValueExist]]" name="TAG_KEY" /> 
							<font class="dddl_platform_html_requiredFlag">*</font>
					</c:if>
				</td>
			</tr>
			
		</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="submit" class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
