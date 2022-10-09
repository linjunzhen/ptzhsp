<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function showIconGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("DepartIconGrid");
		if (entityId) {
			$.dialog.open("callSetController.do?departIconInfo&entityId=" + entityId, {
				title : "图标设置",
				width : "450px",
				height : "215px",
				lock : true,
				resize : false
			}, false);
		}
	}

	function formateRoomNo(val,row) {
		return uniqueRoomNo(val)
	}

	/*大厅号去重*/
	function uniqueRoomNo(roomNo) {
		var roomNoArr = roomNo.split(",");
		var str = "";
		for (let i = 0; i < roomNoArr.length; i++) {
			if (str.indexOf(roomNoArr[i]) == -1) {
				str += roomNoArr[i] + ","
			}
		}
		return str.substring(0, str.length - 1);
	}

</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="DepartIconToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_Win"
								iconcls="eicon-photo" plain="true"
								onclick="showIconGridRecord();">图标配置</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="DepartIconGrid" nowrap="false"
			toolbar="#DepartIconToolbar" method="post" idfield="DEPART_ID"
			checkonselect="true" selectoncheck="false" fit="true" border="false"
			url="callSetController.do?departIconDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'DEPART_ID',hidden:true">DEPART_ID</th>
					<th data-options="field:'DEPART_NAME',align:'left'" width="30%">部门名称</th>
					<th data-options="field:'BELONG_ROOM',align:'left'" width="65%" formatter="formateRoomNo">所属大厅</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
