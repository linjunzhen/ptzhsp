<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 编辑服务事项列表记录
	 */
	function editEsuperGridRecord() {
		var $dataGrid = $("#EsuperGrid");
		var rowsData = $dataGrid.datagrid("getChecked");
		var entityId = "";
		var businessId = "";
		if (!(rowsData != null && typeof (rowsData) != 'undefined' && rowsData.length != 0)) {
			art.dialog({
				content: "请选择需要被操作的记录!",
				icon:"warning",
			    ok: true
			});
			return null;
		}else if(rowsData.length>1){
			var colName = $dataGrid.datagrid('options').idField;  
			var selectColNames = "";
			var isFb=0;
			for ( var i = 0; i < rowsData.length; i++) {
				if(eval('rowsData[i].FB_STATUS')=="1"){
					isFb=1;
				}
				if(i>0){
					selectColNames+=",";
					businessId+=",";
				}
				selectColNames+=eval('rowsData[i].'+colName);
				businessId+=eval('rowsData[i].BUSINESS_ID');
			}
			entityId = selectColNames;
			if(isFb==1){
				art.dialog({
					content: "只能选择未反馈状态的记录，您选中的记录中存在已反馈状态的信息，请重新选择!",
					icon:"warning",
				    ok: true
				});
				return null;
			}
		}else{
			if(eval("rowsData[0].FB_STATUS")=="1"){
				art.dialog({
					content: "您选中的记录为已反馈状态，请选择未反馈状态的记录!",
					icon:"warning",
				    ok: true
				});
				return null;
			}else{
				var colName = $dataGrid.datagrid("options").idField; 
				entityId = eval("rowsData[0]."+colName);
				businessId=eval('rowsData[0].BUSINESS_ID');
			}
		}
		if (entityId) {
			showEsuperFbDetail(entityId,businessId,"update");
		}
	}
	$(document).ready(function() {
		AppUtil.initAuthorityRes("EsuperToolbar");
	});
	function formatEsuperStatus(val,row){
		if(val=="红灯"){
			return "<img src='plug-in/images/icons/red.png'>";
		}else if(val=="黄灯"){
			return "<img src='plug-in/images/icons/yellow.png'>";
		}else if(val=="未预警"){
			return "<font color='green'><b>"+val+"</b></font>";
		}
	};
	function formatExeId(val,row){
		//获取流程申报号
		var href = "<a href='";
		href += "executionController.do?goDetail&exeId="+val;
		href += "' target='_blank'><span style='text-decoration:underline;color:green;'>"+val+"</span></a>";
	    return href;
	}
	function formatFbStatus(val,row){
		//获取流程申报号
		if(val=="已反馈"){
			return "<font color='green'><b>"+val+"</b></font>";
		}else{
			return "<font color='red'><b>"+val+"</b></font>";
		}
	}
	function formatFbInfo(val,row){
		//获取流程申报号
		if(row.FB_STATUS==1){
			return "<a href='javascript:void(0)' onclick='showEsuperFbDetail(\""+row.SUPER_ID+"\",\"\",\"detail\");'><font color='blue'>反馈信息</font></a>";
		}else{
			return "";
		}
	}
	function showEsuperFbDetail(superId,businessId,operType){
		var title="预警反馈信息";
		var height="500px";
		if(superId.indexOf(",")>0){
			title = "预警批量反馈";
			height="250px";
		}
		var url = "esuperController.do?info&operType="+operType+"&superId=" + superId+"&businessId="+businessId;
		$.dialog.open(url , {
			title : title,
			width : "700px",
			height : height,
			lock : true,
			resize : false
		}, false);
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="EsuperToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Esuper"
								iconcls="icon-note-add" plain="true"
								onclick="editEsuperGridRecord();">预警反馈</a> 
								<a href="#"
								class="easyui-linkbutton" iconcls="icon-reload" plain="true"
								onclick="AppUtil.gridDoSearch('EsuperToolbar','EsuperGrid');">刷新</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="EsuperGrid" fitcolumns="true" method="post" idfield="SUPER_ID" checkonselect="true"  fit="true"
			  singleSelect="true" selectoncheck="false" border="false"
			  nowrap="false" url="esuperController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'SUPER_ID',hidden:true" width="80">SUPER_ID</th>
					<th data-options="field:'WARN_TYPENAME',align:'center'" width="40" formatter="formatEsuperStatus">预警类型</th>
					<th data-options="field:'BUSINESS_ID',align:'left'" width="90" formatter="formatExeId">办件申报号</th>
					<th data-options="field:'PROCESS_NAME',align:'center'" width="50">预警环节</th>
					<th data-options="field:'WARN_INFO',align:'left'" width="330">预警信息</th>
					<th data-options="field:'WARN_REASON',align:'left'" width="150">预警原因</th>
					<th data-options="field:'REPLY_LIMIT',align:'left'" width="60">反馈期限</th>
					<th data-options="field:'FB_STATUSNAME',align:'left'" width="50"  formatter="formatFbStatus">反馈状态</th>
					<th data-options="field:'FB_STATUS',align:'left'" width="60" formatter="formatFbInfo">查看</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
