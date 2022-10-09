<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,swfupload"></eve:resources>
<script type="text/javascript">
$(function() {
	AppUtil.initWindowForm("submitAuditFrom", function(form, valid) {
		if (valid) {
			var rows = $("#AUDID_UPLOADTABLE").datagrid("getRows");
			var fileIds = "";
			$.each(rows,function(index,row){
				fileIds += row.FILE_ID+",";
			});
			if(fileIds != ""){
				fileIds = fileIds.substring(0,fileIds.length-1);
			}
			$("#ATTACHMENT").val(fileIds);
			//将提交按钮禁用,防止重复提交
			$("input[type='submit']").attr("disabled", "disabled");
			var formData = $("#submitAuditFrom").serialize();
			var url = $("#submitAuditFrom").attr("action");
			
			AppUtil.ajaxProgress({
				url : url,
				params : formData,
				callback : function(resultJson) {
					if (resultJson.success) {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "succeed",
							time : 3,
							ok : true
						});
						parent.$("#BusAuditGrid").datagrid("reload");
						AppUtil.closeLayer();
					} else {
						parent.art.dialog({
							content : resultJson.msg,
							icon : "error",
							ok : true
						});
					}
				}
			});
		}
	}, "T_LCJC_BUS_AUDIT");
	
	 /*
	 * 
	 */
	AppUtil.initUploadControl({
		file_types : "*.docx;*.doc;",
		file_upload_limit : 0,
		file_queue_limit : 0,
		uploadPath : "audit",
		busTableName : "T_LCJC_APPLYINFO",
		uploadUserId : $("input[name='CURLOGIN_USERID']").val(),
		uploadButtonId : "AUDIT_UPLOAD_BTN",
		limit_size : "10 MB",
		uploadTableId:"AUDID_UPLOADTABLE",
		uploadSuccess : function(resultJson) {
			
		}
	});
});	

function formatDel(val,row){
	if(val){
		return val;
	}else{
		return "<a href='#' style='float:left;height:28px; line-height:28px;' onclick='AppUtil.removeUploadFile(\""+row.FILE_ID+"\",null,null,\"AUDID_UPLOADTABLE\",\"AUDIT_UPLOAD_BTN\");' >删除</a>"
	}
}
function formatProcess(val, row){
	if(val){
		return val;
	}else{
		return "已上传";
	}
}
</script>
 <style> 
   .divcss5{border:1px solid #666;display:table-cell;vertical-align:middle;text-align:center;width:200px; height:200px;overflow:hidden;} 
   .divcss5 img{max-width:200px;_width:expression(this.width > 200 ? "200px" : this.width);max-height:200px;_height:expression(this.height > 200 ? "200px" : this.height);vertical-align:middle;} 
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	<form id="submitAuditFrom" method="post" action="busAuditController.do?submitAudit" style="height:400px">
			<!--==========隐藏域部分开始 ===========-->
			<%-- <input type="hidden" name="CURLOGIN_USERID" value="${session111Scope.curLoginUser.userId}"> --%>
			<input type="hidden" name="BUS_CODE" value="${BUS_CODE}">
			<input type="hidden" name="EXE_ID" value="${APPLYINFO.EXE_ID}">
			<input type="hidden" name="ATTACHMENT" id="ATTACHMENT" value="">
			<!--==========隐藏域部分结束 ===========-->
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
					<td colspan="2" class="dddl-legend" style="font-weight:bold;">基本信息</td>
				</tr>
				<tr>
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">申报理由：</span>
					<textarea rows="10" cols="5" class="validate[required] eve-textarea"
						style="width: 450px" name="REMARK">${APPLYINFO.REMARK}</textarea>
						<font
					class="dddl_platform_html_requiredFlag">*</font>
				</td>
				</tr>
					
				<tr>
					<td align="center" style="padding:3px 0 2px 0px">
						<span style="width: 100px;float:left;text-align:right;">报告附件：</span>
						<span style="width: 50px;float:left;text-align:left;padding-top:3px;"><a id="AUDIT_UPLOAD_BTN"></a></span>
						<span style="float:left">(请上传word文档)</span>
					</td>
				</tr>	
			
			</table>
			<table id="AUDID_UPLOADTABLE" class="easyui-datagrid" rownumbers="true" pagination="false" fitColumns="false"
			method="post" idField="SWF_FILEID" checkOnSelect="false"  style="width:300px;htight:200px" url="fileAttachController.do?datagrid&${fileIds}"
			selectOnCheck="false" fit="true" border="false">
			<thead>
				<tr height="29px">
				<th data-options="field:'FILE_ID',hidden:true" width="80"></th>
				<th data-options="field:'FILE_NAME'" width="300">文件名</th>
				<!-- <th data-options="field:'FILE_SIZE',hidden:true" width="20"></th>
				<th data-options="field:'FILE_TYPE',hidden:true" width="20"></th>
				<th data-options="field:'SWF_FILEID',hidden:true" width="80"></th> -->
				<th data-options="field:'fileprogress',formatter:formatProcess" width="150">进度</th>
				<th data-options="field:'FILE_DELETE',formatter:formatDel" width="100">操作</th>
				</tr>
				</thead>
			</table>
			<div class="eve_buttons">
				<input value="提交" type="submit"
					class="z-dlg-button z-dialog-okbutton aui_state_highlight" />
				<input
					value="取消" type="button" class="z-dlg-button z-dialog-cancelbutton"
					onclick="AppUtil.closeLayer();" />
			</div>
		</form>
</body>