<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除数据源列表记录
	 */
	function deleteProjectGridRecord() {
		AppUtil.deleteDataGridRecord("projectController.do?multiDel",
				"ProjectGrid");
	};
	/**
	 * 编辑数据源列表记录
	 */
	function editProjectGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ProjectGrid");
		if (entityId) {
			showProjectWindow(entityId);
		}
	}

	/**
	 * 显示数据源信息对话框
	 */
	function showProjectWindow(entityId) {
		$.dialog.open("projectController.do?info&entityId=" + entityId, {
			title : "编辑项目信息",
			width : "600px",
			height : "260px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("ProjectToolbar");
	});
	function reloadProjectList(){
		$("#ProjectGrid").datagrid("reload");
	}
</script>
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ProjectToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_OSTOPIC"
								iconcls="icon-note-add" plain="true"
								onclick="showProjectWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="icon-note-edit" plain="true"
								onclick="editProjectGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CONSULT"
								iconcls="icon-note-delete" plain="true"
								onclick="deleteProjectGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ProjectForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROJECT_NAME_LIKE" />
	    </td> 	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('ProjectToolbar','ProjectGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('ProjectForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="ProjectGrid" fitcolumns="true" toolbar="#ProjectToolbar"
			method="post" idfield="PROJECT_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="projectController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PROJECT_ID',hidden:true" width="80">PROJECT_ID</th>
					<th data-options="field:'PROJECT_NAME',align:'left'" width="250">项目名称</th>					
					<th data-options="field:'PROJECT_TIME',align:'left'" width="100">立项时间</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
