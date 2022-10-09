<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<script type="text/javascript">
	
	function formatStatus(val,row){
		  if(val=="1"){
			  return "<b>已发布</b>";
		  }else if(val=="0"){
			  return "<b>暂存</b>";
		  }
	}
	
/**
	 * 删除系统列表记录
	 */
	function deleteMsgTemplateGridRecord() {
		AppUtil.deleteDataGridRecord("msgTemplateController.do?multiDel",
				"msgTemplateViewGrid");
	}
	/**
	 * 编辑系统列表记录
	 */
	function editMsgTemplateGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("msgTemplateViewGrid");
		if (entityId) {
			showMsgTemplateWindow(entityId);
		}
	}

function reloadNoticeList(){
	$("#msgTemplateViewGrid").datagrid("reload");
}
	/**
	 * 显示系统用户信息对话框
	 */
	function showMsgTemplateWindow(entityId) {
		//获取组织机构的ID
		var url = "msgTemplateController.do?info&entityId=" + entityId;
		$.dialog.open(url, {
    		title : "短信模板信息",
            width:"700px",
            lock: true,
            resize:false,
            height:"400px",
    	}, false);
	};
	$(document).ready(function() {
		
	});
	
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="msgTemplateToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
						<a href="#" class="easyui-linkbutton" reskey="Add_SysNotice"
								iconcls="eicon-plus" plain="true"
								onclick="showMsgTemplateWindow();">新建</a> <a href="#"
								class="easyui-linkbutton" reskey="EDIT_SysNotice"
								iconcls="eicon-pencil" plain="true"
								onclick="editMsgTemplateGridRecord();">编辑</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_SysNotice"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteMsgTemplateGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="msgTemplateForm">
				<table class="dddl-contentBorder dddl_table"
					style="background-repeat:repeat;width:100%;border-collapse:collapse;">
					<tbody>
						<tr style="height:28px;">
							<td style="width:68px;text-align:right;">模板名称</td>
							<td style="width:235px;">
							<input class="eve-input" type="text" maxlength="50" style="width:228px;" name="Q_T.TEMPLATE_NAME_LIKE" />
							</td>
							
							<td></td>
							<td colspan="2"><input type="button" value="查询"
								class="eve-button"
								onclick="AppUtil.gridDoSearch('msgTemplateToolbar','msgTemplateViewGrid')" />
								<input type="button" value="重置" class="eve-button"
								onclick="AppUtil.gridSearchReset('msgTemplateForm')" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="msgTemplateViewGrid" fitcolumns="true" toolbar="#msgTemplateToolbar"
			method="post" idfield="TEMPLATE_ID" checkonselect="true"
			selectoncheck="true" fit="true" border="false" nowrap="false"
			url="msgTemplateController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TEMPLATE_ID',hidden:true">TEMPLATE_ID</th>
					<th data-options="field:'TEMPLATE_NAME',align:'left'" width="25%">模板名称</th><!-- 
					<th data-options="field:'STATUS',align:'left'" width="60" formatter="formatStatus">状态</th> -->
					<th data-options="field:'TEMPLATE_CONTENT',align:'left'" width="45%">模板内容</th>
					<th data-options="field:'CREATE_USERNAME',align:'left'" width="10%">创建人</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="16%">新建时间</th>
				</tr>
			</thead>
		</table>
		
		<!-- =========================表格结束==========================-->
	</div>
</div>
