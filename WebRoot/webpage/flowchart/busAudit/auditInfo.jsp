<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>

<head>
<eve:resources
	loadres="jquery,easyui,apputil,laydate,layer,validationegine,artdialog,swfupload"></eve:resources>

 <style> 
   .divcss5{border:1px solid #666;display:table-cell;vertical-align:middle;text-align:center;width:200px; height:200px;overflow:hidden;} 
   .divcss5 img{max-width:200px;_width:expression(this.width > 200 ? "200px" : this.width);max-height:200px;_height:expression(this.height > 200 ? "200px" : this.height);vertical-align:middle;} 
</style>
</head>

<body style="margin:0px;background-color: #f7f7f7;">
	
			<table style="width:100%;border-collapse:collapse;"
				class="dddl-contentBorder dddl_table">
				<tr style="height:29px;">
				</tr>
				<tr>
				<td colspan="4"><span style="width: 100px;float:left;text-align:right;">申报理由：</span>
					<textarea readonly="readonly" rows="10" cols="5" class="eve-textarea"
						style="width: 450px" name="REMARK">${APPLYINFO.REMARK}</textarea>
				</td>
				</tr>
					
				<tr>
					<td align="center" style="padding:3px 0 2px 0px">
						<span style="width: 100px;float:left;text-align:right;">报告附件：</span>
					</td>
				</tr>	
			
			</table>
			<table id="downTable" class="easyui-datagrid" rownumbers="true" pagination="false" fitColumns="false"
			method="post" idField="FILE_ID" checkOnSelect="false"  style="width:300px;htight:200px" url="fileAttachController.do?datagrid&${fileIds}"
			selectOnCheck="false" fit="true" border="false">
			<thead>
				<tr height="29px">
				<th data-options="field:'FILE_NAME'" width="450">文件名</th>
				<th data-options="field:'FILE_TYPE',align:'center',formatter:formatDll"  width="80" >操作</th>
				</tr>
				</thead>
			</table>
			<script type="text/javascript">

function formatDll(val, row){
	return '<a href="DownLoadServlet?fileId='+row.FILE_ID+'" iconcls="icon-note-edit" class="easyui-linkbutton">下载</a>';
}
</script>
</body>