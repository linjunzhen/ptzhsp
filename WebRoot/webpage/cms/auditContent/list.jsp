<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.datagrid-btable tr{height: 40px;}
.datagrid-btable td{word-break: break-all;}
</style>
<script type="text/javascript">
  function formatHandleDesc(val,row){
	  if(val){
		  return "<font color='black'><b>"+val+"</b></font>";
	  }
  }
  
  function formatIsOverTime(val,row){
	  if(val=="-1"){
		  return "<font color='green'><b>未超时</b></font>";
	  }else{
		  return "<font color='red'><b>已超时</b></font>";
	  }
  }

  function formatAuditStatus(val,row){
	  if(val=="0"){
		  return "<font color='green'><b>正在审核</b></font>";
	  }else if(val=="1"){
		  return "<font color='blue'><b>已审核</b></font>";
	  }else if(val=="2"){
		  return "<font color='black'><b>审核结束</b></font>";
	  }else if(val=="-1"){
		  return "<font color='red'><b>回退</b></font>";
	  }
  }
	function formatStatus(val,row){
		if(val=="0"){
			return "信息采编";
		}else if(val=="1"){
			return "领导审核";
		}else if(val=="2"||val=="3"){
			return "信息发布";
		}else if(val=="4"){
			return "结束";
		}else{
			return "";
		}
	}
  function formatStepSeq(val,row){
	  return "<b>"+val+"</b>";
  }
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="false" pagination="false"
			id="AuditGrid" fitcolumns="true" nowrap="false" 
			method="post" idfield="AUDIT_ID" checkonselect="false"
			selectoncheck="false" fit="true" border="false"
			url="auditContentController.do?datagrid&Q_T.CONTENT_ID_EQ=${contentId}&start=0&limit=100">
			<thead>
				<tr>
					<th data-options="field:'ROWNO',align:'center'" width="60" formatter="formatStepSeq">步骤序号</th>
					<th data-options="field:'CURRENT_STATUS',align:'left'"  width="80" formatter="formatStatus">环节名称</th>
					<th data-options="field:'NEXT_STATUS',align:'left'"  width="80" formatter="formatStatus">下一环节</th>
					<!--<th data-options="field:'AUDIT_USERNAME',align:'left'"  width="150">审核人</th>-->
					<th data-options="field:'AUDIT_STATUS',align:'left'"  width="100" formatter="formatAuditStatus">办理状态</th>
					<th data-options="field:'AUDIT_OPINION',align:'left'"  width="200">审核意见</th>
					<th data-options="field:'CREATE_TIME',align:'left'"  width="100">提交时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
