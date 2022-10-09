<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="plug-in/easyui-1.4/extension/datagrid-dnd/datagrid-dnd.js"></script>
<script type="text/javascript">
	function dialogalert(msg,flag){
		art.dialog({
			content : msg,
			icon : flag==null?"warning":flag,
			ok : true
		});
	}
	function editAppointTime(){
		var entityId = AppUtil.getEditDataGridRecord("appointTimeGrid");
		if (entityId) {
			$.dialog.open("callSetController.do?appointmentSetInfo&entityId=" + entityId, {
				title : "编辑时段配置信息",
				width : "500px",
				height : "250px",
				lock : true,
				resize : false
			}, false);
		}
	}
	function addAppointTime(){
		var departId = $("#DEPART_ID").val();
		if(departId){
			$.dialog.open("callSetController.do?appointmentSetInfo&departId="+departId, {
				title : "新增时段配置信息",
				width : "500px",
				height : "250px",
				lock : true,
				resize : false
			}, false);
		}else{
			dialogalert("请选择需要配置的部门");
		}
	}
	function delAppointTime(){
		AppUtil.deleteDataGridRecord("callSetController.do?doDelForTime","appointTimeGrid");
	}
	
	//点击树形节点触发的事件
	function onappointDepartmentTreeClick(event, treeId, treeNode, clickFlag) {
		if (event.target.tagName == "SPAN") {
			if(treeNode.parentId==0){
				$("#DEPART_ID").val(treeNode.id);
				$("#departNamediv").html("_"+treeNode.name);
				AppUtil.gridDoSearch("appointTimeToolbar", "appointTimeGrid");
			}else{
				dialogalert("仅一级部门可提供时段配置");
			}
		}
	}

	$(document).ready(function() {
		var appointDepartmentTreeSetting = {
			edit: {
				enable: false,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			view : {
				selectedMulti: false
			},
			callback : {
				onClick : onappointDepartmentTreeClick,
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.expandAll(true);
				}
			},
			async : {
				enable : true,
				url : "callSetController.do?tree",
				otherParam : {
					"tableName" : "T_CKBS_BUSINESSDATA",
					"idAndNameCol" : "DEPART_ID,DEPART_NAME",
					"rootParentId" : "0",
					"isShowRoot" : "true",
					"rootName" : "预约部门/业务树"
				}
			}
		};
		$.fn.zTree.init($("#appointDepartmentTree"), appointDepartmentTreeSetting);

	});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false" style="width:350px;">
		<div class="right-con">
			<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
				<div class="e-toolbar-ct">
					<div class="e-toolbar-overflow">
						<div style="color:#005588;">
							<img src="plug-in/easyui-1.4/themes/icons/script.png"
								style="position: relative; top:7px; float:left;" />&nbsp;预约部门/业务树
						</div>
					</div>
				</div>
			</div>
		</div>
		<ul id="appointDepartmentTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false">

		<div id="appointTimeToolbar">
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<div style="color:#005588;">
								<img src="plug-in/easyui-1.4/themes/icons/script.png"
									style="position: relative; top:7px; float:left;" />&nbsp;预约时段配置<a
									id="departNamediv"></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" id="DEPART_ID" name="Q_T.DEPART_ID_EQ" />
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" resKey="ADD_AppointTime"
								iconCls="eicon-plus" plain="true"
								onclick="addAppointTime();">新增</a> <a href="#"
								class="easyui-linkbutton" resKey="EDIT_AppointTime"
								iconCls="eicon-pencil" plain="true"
								onclick="editAppointTime();">编辑</a> <a href="#"
								class="easyui-linkbutton" resKey="EDIT_AppointTime"
								iconCls="eicon-trash-o" plain="true"
								onclick="delAppointTime();">删除</a>

						</div>
					</div>
				</div>
			</div>

		</div>
		<!-- =========================查询面板结束========================= -->

		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			pageSize="50" id="appointTimeGrid" fitColumns="true"
			toolbar="#appointTimeToolbar" method="post" idField="RECORD_ID"
			checkonselect="true" selectoncheck="true" fit="true" border="false"
			data-options="onLoadSuccess:function(){
				       $(this).datagrid('enableDnd');
			   }"
			url="callSetController.do?appointmentSetDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'RECORD_ID',hidden:true">RECORD_ID</th>
					<th data-options="field:'BEGIN_TIME',align:'left'" width="20%">开始时间</th>
					<th data-options="field:'END_TIME',align:'left'" width="20%">结束时间</th>
					<th data-options="field:'ALLOW_BESPEAK_NUMBER',align:'left'"
						width="52%">该时段可预约数</th>
				</tr>
			</thead>
		</table>

	</div>
</div>



