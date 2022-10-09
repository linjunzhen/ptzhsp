<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除调查主题列表记录
	 */
	function deletemessageGridRecord() {
		AppUtil.deleteDataGridRecord("messageController.do?multiDel",
				"messageGrid");
	};
	/**
	 * 编辑调查主题列表记录
	 */
	function editmessageGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("messageGrid");
		if (entityId) {
			showmessageWindow(entityId);
		}
	}
	
	/**
	 * 显示信息对话框
	 */
	function showmessageWindow(entityId) {
		$.dialog.open("messageController.do?info&entityId=" + entityId, {
			title : "编辑消息信息",
			width : "600px",
			height : "300px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("messageToolbar");
	});
function reloadmessageList(){
	$("#messageGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>未发布</font>";
	}else if(val=="1"){
		return "<font color='green'>已发布</font>";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="messageToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_MESSAGE"
								iconcls="eicon-plus" plain="true"
								onclick="showmessageWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_MESSAGE"
								iconcls="eicon-pencil" plain="true"
								onclick="editmessageGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_MESSAGE"
								iconcls="eicon-trash-o" plain="true"
								onclick="deletemessageGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="messageForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">消息标题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.MESSAGE_TITLE_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('messageToolbar','messageGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('messageForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="messageGrid" fitcolumns="true" toolbar="#messageToolbar"
			method="post" idfield="MESSAGE_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="messageController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'MESSAGE_ID',hidden:true">MESSAGE_ID</th>	
					<th data-options="field:'MESSAGE_TITLE',align:'left'" width="65">消息标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="15">创建时间</th>	
					<th data-options="field:'MESSAGE_STATUS',align:'left'" width="15%" formatter="formatState">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
