<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/webpage/common/baseinclude.jsp"%>


<eve:resources loadres="jquery,easyui,apputil,laydate,layer,validationegine,icheck,artdialog"></eve:resources>
<link rel="stylesheet" type="text/css" href="webpage/main/css/style1.css"/>
<link rel="stylesheet" type="text/css" href="webpage/main/css/fonticon.css"/>
<script type="text/javascript">
  	
	/**
	 * 删除主行业列表记录
	 */
	function deleteChildIndustryGridRecord() {
		AppUtil.deleteDataGridRecord("industryController.do?multiDelChildIndustry",
				"ChildIndustryGrid");
	};

	/**
	 * 编辑主行业列表记录
	 */
	function editChildIndustryGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("ChildIndustryGrid");
		if (entityId) {
			showChildIndustryWindow(entityId);
		}
	}
	
	/**
	 * 显示子行业信息对话框
	 */
	function showChildIndustryWindow(entityId){ 
        var  parentId = $("input[name='PARENT_ID']").val();
		$.dialog.open("industryController.do?childIndustryinfo&entityId=" + entityId+"&parentId="+parentId, {
			title : "子行业信息",
			width : "450px",
			lock : true,
			resize : false,
			height : "200px",
			close : function() {
 				//$("#ChildIndustryGrid").attr("url","industryController.do?childIndustryDatagrid&Q_T.PARENT_ID_EQ="+parentId); 
 				$("#ChildIndustryGrid").datagrid("reload");
			}
		}, false);
	};
	
	//查询
	function search(){
	    $('#ChildIndustryGrid').datagrid('clearChecked')=='none';
		AppUtil.gridDoSearch('ChildIndustryToolbar','ChildIndustryGrid');
	}
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="ChildIndustryToolbar">
			<!--====================开始编写隐藏域============== -->
			<input type="hidden" name="PARENT_ID" value="${entityId}">
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#" class="easyui-linkbutton" reskey="ADD_SysRole"
								iconcls="eicon-plus" plain="true"
								onclick="showChildIndustryWindow();">新建</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysRole"
								iconcls="eicon-pencil" plain="true"
								onclick="editChildIndustryGridRecord();">编辑</a>
							 <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysRole"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteChildIndustryGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="ChildIndustryForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">子行业名称</td>
							<td style="width:135px;"><input class="eve-input"
								type="text" maxlength="20" style="width:128px;"
								name="Q_T.INDUSTRY_NAME_LIKE" /></td>
							<td ><input type="button" value="查询"
								class="eve-button"
								onclick="search()" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('ChildIndustryForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="ChildIndustryGrid" fitcolumns="true" toolbar="#ChildIndustryToolbar"
			method="post" idfield="INDUSTRY_ID" checkonselect="true"
			nowrap="false"
			selectoncheck="true" fit="true" border="false"
			url="industryController.do?childIndustryDatagrid&Q_T.PARENT_ID_EQ=${entityId}">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'INDUSTRY_ID',hidden:true">INDUSTRY_ID</th>
					<th data-options="field:'INDUSTRY_NAME',align:'left'" width="60%">子行业名称</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>