<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deleteConsultGridRecord() {
		AppUtil.deleteDataGridRecord("consultController.do?multiDel",
				"busConsultGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editConsultGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("busConsultGrid");
		if (entityId) {
			showBusConsultWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showBusConsultWindow(entityId) {
		$.dialog.open("consultController.do?info&entityId=" + entityId, {
			title : "回复咨询信息",
			width : "700px",
			height : "620px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("busConsultToolbar");
	});
function reloadBusConsultList(){
	$("#busConsultGrid").datagrid("reload");
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
	if(val=="0"){
		return "办事咨询";
	}else{
		return "其他咨询";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="busConsultToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-reply" plain="true"
								onclick="editConsultGridRecord();">回复</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CONSULT"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteConsultGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="busConsultForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">咨询标题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.CONSULT_TITLE_LIKE" />
	    </td> 
	    <td style="width:70px;text-align:right;">回复状态：</td> 
	    <td style="width:156px;"> 
			<select defaultemptytext="请选择回复状态" class="eve-input" name="Q_U.REPLY_FLAG_=">
				<option value="">请选择回复状态</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>		
	    </td>
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('busConsultToolbar','busConsultGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('busConsultForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="busConsultGrid" fitcolumns="true" toolbar="#busConsultToolbar"
			method="post" idfield="CONSULT_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="consultController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'CONSULT_ID',hidden:true">CONSULT_ID</th>
					<th data-options="field:'CONSULT_TITLE',align:'left'" width="30%">咨询标题</th>
					<th data-options="field:'CONSULT_TYPE',align:'left'" width="8%" formatter="formatType">咨询类型</th>		
					<th data-options="field:'CONSULT_DEPT',align:'left'" width="15%">咨询部门</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">咨询时间</th>
					<th data-options="field:'CREATE_USERNAME',align:'left'" width="8%">咨询者</th>
					<th data-options="field:'REPLY_FLAG',align:'left'" width="8%" formatter="formatState">回复状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="13%">回复时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
