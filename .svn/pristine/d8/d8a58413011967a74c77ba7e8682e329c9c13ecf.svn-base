<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除数据源列表记录
	 */
	function deleteModelDsGridRecord() {
		AppUtil.deleteDataGridRecord("modelDsController.do?multiDel",
				"ModelDsGrid");
	};
	/**
	 * 编辑数据源列表记录
	 */
	function editModelDsGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ModelDsGrid");
		if (entityId) {
			showModelDsWindow(entityId);
		}
	}

	/**
	 * 显示数据源信息对话框
	 */
	function showModelDsWindow(entityId) {
		$.dialog.open("modelDsController.do?info&entityId=" + entityId, {
			title : "编辑数据源信息",
			width : "700px",
			height : "500px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("ModelDsToolbar");
	});
function reloadModelDsList(){
	$("#ModelDsGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>否</font>";
	}else{
		return "<font color='green'>是</font>";
	}
};
function formatType(val,row){
	if(val=="1"){
		return "办事咨询";
	}else{
		return "其他咨询";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ModelDsToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_OSTOPIC"
								iconcls="eicon-plus" plain="true"
								onclick="showModelDsWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-pencil" plain="true"
								onclick="editModelDsGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CONSULT"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteModelDsGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ModelDsForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">名称：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.DSNAME_LIKE" />
	    </td> 	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('ModelDsToolbar','ModelDsGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('ModelDsForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ModelDsGrid" fitcolumns="true" toolbar="#ModelDsToolbar"
			method="post" idfield="DSID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="modelDsController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'DSID'" width="5%">DSID</th>
					<th data-options="field:'DSNAME',align:'left'" width="10%">名称</th>
					<th data-options="field:'DSCODE',align:'left'" width="45%">代码</th>					
					<th data-options="field:'DSRETURNDATATYPE',align:'left'" width="10%">返回结果集</th>
					<th data-options="field:'DSDESC',align:'left'" width="10%">描述</th>
					<th data-options="field:'DSCACHEKEYS',align:'left'" width="15%">对应表</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
