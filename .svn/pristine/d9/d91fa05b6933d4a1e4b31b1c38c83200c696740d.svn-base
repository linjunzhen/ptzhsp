<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style>
<script type="text/javascript">
  function formatFileName(val,row){
	  var fileId = row.FILE_ID;
	  var href = "<a href=\"javascript:void(0)\" ";
	  href+=("onclick=\"AppUtil.downLoadPrintFile('"+fileId+"');\" >");
	  href+=(val+"</a>");
	  return href;
  }
  
</script>

<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			fitcolumns="true" nowrap="false" 
			method="post" idfield="FILE_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="printAttachController.do?printList&exeId=${exeId}">
			<thead>
				<tr>
					<th data-options="field:'FILE_ID',hidden:true" width="80">FILE_ID</th> 
					<th data-options="field:'FILE_NAME',align:'left'"  width="250" formatter="formatFileName">打印材料名称</th>
					<th data-options="field:'USER_NAME',align:'left'"  width="100" >操作人员</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="100" >打印时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
