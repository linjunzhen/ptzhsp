<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources loadres="jquery,easyui,apputil,layer,artdialog"></eve:resources>
<script type="text/javascript">
	function tosubmit(){
		var file = $("input[name='upload']").val();
		if(file.length>0){
			//$("#ImpPersonForm").submit();
			$("input[id='submit']").attr("disabled","disabled");
			var formData = new FormData($("#ImpPersonForm")[0]);
			var url = $("#ImpPersonForm").attr("action");
			var layload = parent.layer.load('正在提交请求中…');
			$.ajax({
				url : url,
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(returndata) {
					parent.layer.close(layload);
					var resultJson = $.parseJSON(returndata);
					parent.art.dialog({
						content: resultJson.msg,
						icon:"succeed",
						time:3,
						ok: true
					});
					//window.parent.setImpPersonInfo(resultJson.jsonString);
					art.dialog.data("backInfo", {
						jsonString : resultJson.jsonString
					});
					AppUtil.closeLayer();
				},
				error : function(returndata) {
					layer.close(layload);
					var resultJson = $.parseJSON(returndata);
					parent.art.dialog({
						content: resultJson.msg,
						icon:"error",
						ok: true
					});
				}
			});
		}else{
			parent.art.dialog({
				content: "请选择需要导入的Excel文件",
				icon:"error",
				ok: true
			});
		}
	}
</script>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="ImpPersonForm" action="ybCommonController.do?impPersonInfo" method="post" enctype="multipart/form-data">
	    <div  id="ImpPersonFormDiv">
		    <%--==========隐藏域部分开始 ===========--%>
		    <input type="hidden" name="exeId" value="${exeId }" />
		     <input type="hidden" name="impTableName" value="${impTableName}" />
		    
		    <%--==========隐藏域部分结束 ===========--%>
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				
				<tr>
					<td>
						<input type="file" accept=".xls,.xlsx" name="upload" class="eve-input" style="width:300px;float:left;"/>
					</td>
				</tr>

			</table>
		</div>
		<div class="eve_buttons">
		    <input value="确定" type="button" onclick="tosubmit();" class="z-dlg-button z-dialog-okbutton aui_state_highlight" id="submit"/>
		    <input value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton" onclick="AppUtil.closeLayer();"/>
		</div>
	</form>
</body>
