<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,layer,validationegine,icheck,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		AppUtil.initWindowForm("ExcelExportSelector", function(form, valid) {
			if (valid) {
				var formData = $("#ExcelExportSelector").serialize();
				var url = $("#ExcelExportSelector").attr("action");
				AppUtil.ajaxProgress({
					url : url,
					params : formData,
					callback : function(resultJson) {
						window.location.href = resultJson.msg;
						AppUtil.closeLayer();
					}
				});
			}
		}, "T_MSJW_SYSTEM_EXCELCONFIG");

	});
	
	function  submitForExp(){
	  window.location.href = "excelConfigController.do?exp';	
	}
	
</script>
</head>

<body class="eui-diabody" style="margin:0px;">
	<form id="ExcelExportSelector" method="post"
		action="excelConfigController.do?exp"  >
		<div id="ExcelExportSelectorDiv">
			<!--==========隐藏域部分开始 ===========-->
			<c:forEach items="${queryParams}" var="queryParam">
			 <input type="hidden" name="${queryParam.queryName}" value="${queryParam.queryValue}">
			</c:forEach>
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">导出方式：</span>
						<eve:radiogroup typecode="exportExcelType" width="350"
							fieldname="exportType" defaultvalue="1" >
						</eve:radiogroup>
					</td>
				</tr>
			</table>
		</div>
		<div class="eve_buttons">
			<input value="确定" type="submit"  onclick="submitForExp()"
				class="z-dlg-button z-dialog-okbutton aui_state_highlight" /> <input
				value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>
</body>

