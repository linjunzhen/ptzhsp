<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除服务事项列表记录
	 */
	function deletecomplainGridRecord() {
		AppUtil.deleteDataGridRecord("complainController.do?multiDel",
				"complainGrid");
	};
	/**
	 * 编辑服务事项列表记录
	 */
	function editcomplainGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("complainGrid");
		if (entityId) {
			showcomplainWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showcomplainWindow(entityId) {
		$.dialog.open("complainController.do?info&entityId=" + entityId, {
			title : "回复投诉信息",
			width : "600px",
			height : "470px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("complainToolbar");
	});
function reloadcomplainList(){
	$("#complainGrid").datagrid("reload");
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
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="complainToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-reply" plain="true"
								onclick="editcomplainGridRecord();">回复</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_complain"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletecomplainGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="complainForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">投诉标题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.COMPLAIN_TITLE_LIKE" />
	    </td> 
	    <td style="width:70px;text-align:right;">回复状态：</td> 
	    <td style="width:156px;"> 
			<select defaultemptytext="请选择回复状态" class="eve-input" name="Q_U.REPLY_STATUS_=">
				<option value="">请选择回复状态</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>		
	    </td>
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('complainToolbar','complainGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('complainForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="complainGrid" fitcolumns="true" toolbar="#complainToolbar"
			method="post" idfield="COMPLAIN_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="complainController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'COMPLAIN_ID',hidden:true">complain_ID</th>					
					<th data-options="field:'COMPLAIN_NAME',align:'left'" width="8%">投诉人</th>
					<th data-options="field:'COMPLAIN_DEPT',align:'left'" width="15%">投诉单位</th>
					<th data-options="field:'COMPLAIN_TITLE',align:'left'" width="35%">投诉标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">投诉时间</th>
					<th data-options="field:'REPLY_STATUS',align:'left'" width="8%" formatter="formatState">回复状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="17%">回复时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
