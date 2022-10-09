<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,artdialog,layer,validationegine"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("BdcDyrlxrForm", function(form, valid) {
			if (valid) {
				//将提交按钮禁用,防止重复提交
				$("input[type='submit']").attr("disabled", "disabled");
				var formData = $("#BdcDyrlxrForm").serialize();
				var url = $("#BdcDyrlxrForm").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						if (resultJson.success) {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"succeed",
								time:5,
							    ok: true
							});
							parent.$("#BdcDyrlxrGrid").datagrid("reload");
							AppUtil.closeLayer();
						} else {
							parent.art.dialog({
								content: resultJson.msg,
								icon:"error",
							    ok: true
							});
							$("input[type='submit']").attr("disabled", false);
						}
					}
				});
			}
		}, "T_BDC_DYRLXB");

	});
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="BdcDyrlxrForm" method="post"
		action="bdcDyrlxrController.do?saveOrUpdate">		
		<div id="BdcSurveyFormDiv" data-options="region:'center',split:true,border:false">
		<!--==========隐藏域部分开始 ===========-->
		<input type="hidden" name="ID" value="${bdcDyrlxr.YW_ID}">
		<!--==========隐藏域部分结束 ===========-->
		<table style="width:100%;border-collapse:collapse;"
			class="dddl-contentBorder dddl_table">
			<tr>
				<td colspan="2" class="dddl-legend"><div class="eui-dddltit"><a>基本信息</a></div></td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>抵押权人名称：</span>
					 <c:if test="${bdcDyrlxr.ID!=null}">
						 ${bdcDyrlxr.DYQRMC}
					 </c:if> 
					 <c:if test="${bdcDyrlxr.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required]"
							value="${bdcDyrlxr.DYQRMC}" id="DYQRMC" name="DYQRMC" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>统一社会信用代码：</span>
					 <c:if test="${bdcDyrlxr.ID!=null}">
						 ${bdcDyrlxr.TYSHXYDM}
					 </c:if> 
					 <c:if test="${bdcDyrlxr.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required]"
							value="${bdcDyrlxr.TYSHXYDM}" id="TYSHXYDM" name="TYSHXYDM" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>联系人：</span>
					 <c:if test="${bdcDyrlxr.ID!=null}">
						 ${bdcDyrlxr.LXR_NAME}
					 </c:if> 
					 <c:if test="${bdcDyrlxr.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required]"
							value="${bdcDyrlxr.LXR_NAME}" id="LXR_NAME" name="LXR_NAME" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td><span style="width: 130px;float:left;text-align:left;"><font class="dddl_platform_html_requiredFlag">*</font>联系电话：</span>
					 <c:if test="${bdcDyrlxr.ID!=null}">
						 ${bdcDyrlxr.LXR_PHONE}
					 </c:if> 
					 <c:if test="${bdcDyrlxr.ID==null}">
						<input type="text" style="width:300px;float:left;" maxlength="32"
							class="eve-input validate[required]"
							value="${bdcDyrlxr.LXR_PHONE}" id="LXR_PHONE" name="LXR_PHONE" />
					</c:if>
				</td>
			</tr>
		</table>
		</div>
		<div data-options="region:'south'" style="height:46px;" >
			<div class="eve_buttons">
				<input value="确定" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</div>
	</form>
</body>

