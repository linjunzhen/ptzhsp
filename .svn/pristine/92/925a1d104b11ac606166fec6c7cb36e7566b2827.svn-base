<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style>
<script type="text/javascript">

function formatStatus(val,row){
	  var href = "";
	  if(val=='0')	  {
		href="未接收到反馈";
	  }else{
		href="已接收到反馈";
	  }
	  return href;
}
</script>

<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="false"
			fitcolumns="true" nowrap="false" 
			method="post" idfield="ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="fileAttachController.do?mailInfoList&exeId=${exeId}">
			<thead>
				<tr>
				    <th data-options="field:'TXLOGISTICID',align:'left'"  width="100">TXLOGISTICID</th>
					<th data-options="field:'ID',hidden:true" width="80">ID</th> 
					<th data-options="field:'CREATE_TIME',align:'left'"  width="100" >创建时间</th>
					<th data-options="field:'STATUS',align:'left'"  width="50" formatter="formatStatus">状态</th>
					<th data-options="field:'MAILNUM',align:'left'"  width="80">物流单号</th>
<!-- 					<th data-options="field:'SENDMSG',align:'left'"  width="70">报送信息</th> -->
					<th data-options="field:'DES',align:'left'"  width="250" >揽收失败备注</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
