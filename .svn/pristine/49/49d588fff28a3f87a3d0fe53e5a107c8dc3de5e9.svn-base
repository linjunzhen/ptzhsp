<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">

function addHoverDom(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
	var aObj = $("#" + treeNode.tId + "_a");
	if (treeNode.id != "-1") {
		if ($("#editHref_"+treeNode.id).length>0) return;
		if ($("#delHref_"+treeNode.id).length>0) return;
		var editStr = "<a id='editHref_" +treeNode.id+ "'  style='margin:0 0 0 5px;'>编辑</a>" +
			"<a id='delHref_" +treeNode.id+ "' style='margin:0 0 0 5px;'>删除</a>";
		aObj.append(editStr);
		$("#editHref_" + treeNode.id).bind("click", function() {
			showWindow(treeNode.id);
		});
		$("#delHref_" + treeNode.id).bind("click", function() {
			AppUtil.deleteTreeNode({
				treeId:"projectCodeTree",
				url:"codeProjectController.do?multiDel",
				treeNode:treeNode
			});
		});
	} 
}

function onClick(event, treeId, treeNode, clickFlag) {
	if(event.target.tagName=="SPAN"){
		$("#codeMissionToolbar input[name='Q_M.PROJECT_ID_=']").val(treeNode.id);
		AppUtil.gridDoSearch("codeMissionToolbar","codeMissionGrid");
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#editHref_"+treeNode.id).unbind().remove();
	$("#delHref_"+treeNode.id).unbind().remove();
}

var setting = {
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	view: {
		addHoverDom: addHoverDom,
		selectedMulti: false,
		removeHoverDom : removeHoverDom
	},
	async : {
		enable : true,
		url : "codeProjectController.do?tree"
	},
	callback : {
		onClick : onClick
	}
};

/**
 * 弹出信息窗口
 */
function showWindow(entityId) {
	$.dialog.open("codeProjectController.do?info&entityId="+entityId, {
		title : "代码建模项目信息",
        width:"600px",
        lock: true,
        resize:false,
        height:"450px",
	}, false);
};

/**
 * 删除列表记录
 */
function deleteDataGridRecord() {
	AppUtil.deleteDataGridRecord(
			"codeMissionController.do?multiDel", "codeMissionGrid");
};
/**
 * 编辑代码任务列表记录
 */
function editMissionGridRecord(){
	var missionId = AppUtil.getEditDataGridRecord("codeMissionGrid");
	if(missionId){
		showMissionWindow(AppUtil.getEditDataGridRecord("codeMissionGrid"));
	}
}
/**
 * 在线设计代码
 */
function designMissionGridRecord(){
	var missionId = AppUtil.getEditDataGridRecord("codeMissionGrid");
	if(missionId){
		window.open("codeMissionController.do?design&missionId="+missionId, "_blank");
	}
}

//工程文件目录

function projectMissionGridRecord(){
	$.dialog.open("codeMissionController.do?projectTree", {
		title : "提取更新代码",
        width:"600px",
        lock: true,
        resize:false,
        height:"450px",
	}, false);
	
}

/**
 * 显示代码任务对话框
 */
function showMissionWindow(entityId) {
	var projectId = $("#codeMissionToolbar input[name='Q_M.PROJECT_ID_=']").val();
	if(projectId&&projectId!="-1"){
		$.dialog.open("codeMissionController.do?info&entityId="+entityId+"&projectId="+projectId, {
			title : "代码任务信息",
	        width:"500px",
	        lock: true,
	        resize:false,
	        height:"200px",
		}, false);
		
	}else{
		art.dialog({
			content: "请选择代码建模包!",
			icon:"warning",
		    ok: true
		});
	}
};

function formatIsWindow(val,row){
	if(val=="1"){
		return "<font color='green'><b>是</b></font>";
	}else{
		return "<font color='red'><b>否</b></font>";
	}
}

$(document).ready(function(){
	$.fn.zTree.init($("#projectCodeTree"), setting);
});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div data-options="region:'west',split:false" 
		style="width:250px;" >
		<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#" class="easyui-linkbutton" iconCls="icon-note-add" style="margin-top:5px;"
								plain="true" onclick="showWindow();">新建项目</a>
								
						</div>
					</div>
				</div>
		</div>
		<ul id="projectCodeTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',split:false" >
	    <!-- =========================查询面板开始========================= -->
		<div id="codeMissionToolbar">
		    <%--====================开始编写隐藏域============== --%>
		     <input type="hidden" name="Q_M.PROJECT_ID_="  />
		    <%--====================结束编写隐藏域============== --%> 
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" iconCls="eicon-plus"
								plain="true" onclick="showMissionWindow();">新建任务</a>
							<a href="#" class="easyui-linkbutton" iconCls="eicon-pencil"
								plain="true" onclick="editMissionGridRecord();">编辑任务</a>
							<a href="#" class="easyui-linkbutton" iconCls="eicon-trash-o" plain="true"
								onclick="deleteDataGridRecord();">删除任务</a>
							<a href="#" class="easyui-linkbutton" iconCls="icon-magicwand" plain="true"
								onclick="designMissionGridRecord();">在线设计</a>
								<a href="#" class="easyui-linkbutton" iconCls="icon-magicwand" plain="true"
								onclick="projectMissionGridRecord();">提取更新包*.zip</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="CodeMissionForm">
			<table class="dddl-contentBorder dddl_table"
				style="background-repeat:repeat;width:100%;border-collapse:collapse;">
				<tr>
					<td style="width:125px;text-align:center;">JSP名称</td>
					<td style="width:135px;" ><input class="eve-input" type="text"
						 name="Q_M.JSP_NAME_LIKE" style="width:128px;"></td>
					<td><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('codeMissionToolbar','codeMissionGrid')">
						<input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('CodeMissionForm')"></td>
				</tr>
			</table>
			</form>
		</div>
        <!-- =========================查询面板结束========================= -->
        
        <!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="codeMissionGrid" fitColumns="true" toolbar="#codeMissionToolbar"
			method="post" idField="MISSION_ID" checkOnSelect="false"
			selectOnCheck="false" fit="true" border="false"
			url="codeMissionController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'MISSION_ID',hidden:true">MISSION_ID</th>
					<th data-options="field:'JSP_NAME',align:'left'" width="15%">JSP名称</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="30%">所属建模项目</th>
					<th data-options="field:'IS_WINDOW',align:'left'" width="20%" formatter="formatIsWindow">是否弹出框</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="25%">创建时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
		
	</div>
</div>




