<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,apputil,artdialog"></eve:resources>
<script type="text/javascript">
	$(function() {
		var operType = $("input[name='operType']").val();
		if(operType=="1"){
			var defJson = parent.$("#DEF_JSON").val();
			$("#CODE").val(defJson);
		}
	});
	
	function loadCode(){
		var code = $("#CODE").val();
		parent.loadDesignCode(code);
		AppUtil.closeLayer();
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="FlowDesignCodeForm" method="post"
		action="flowTypeController.do?saveOrUpdate">
		<input type="hidden" name="operType" value="${operType}">
		<div id="FlowDesignCodeFormDiv">
			<!--==========隐藏域部分开始 ===========-->

			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr>
					<td><span style="width: 100px;float:left;text-align:right;">代码：</span>
						<textarea rows="20" id="CODE" cols="5" class="eve-textarea"
							style="width: 400px" name="CODE"></textarea></td>
				</tr>
			</table>


		</div>
		<div class="eve_buttons">
		    <c:if test="${operType=='2'}">
		    <input value="确定" type="button" onclick="loadCode();"
		    class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
		    </c:if>
			<input
				value="关闭" type="button" class="z-dlg-button z-dialog-cancelbutton"
				onclick="AppUtil.closeLayer();" />
		</div>
	</form>

</body>

