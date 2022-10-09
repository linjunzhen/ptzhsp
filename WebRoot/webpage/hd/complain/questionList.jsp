<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 编辑服务事项列表记录
	 */
	function editquestionGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("questionGrid");
		if (entityId) {
			showquestionWindow(entityId);
		}
	}

	/**
	 * 显示服务事项信息对话框
	 */
	function showquestionWindow(entityId) {
		$.dialog.open("complainController.do?questionInfo&entityId=" + entityId, {
			title : "回复投诉信息",
			width : "600px",
			height : "450px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("questionToolbar");
	});
function reloadquestionList(){
	$("#questionGrid").datagrid("reload");
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
		<div id="questionToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" 
								iconcls="eicon-reply" plain="true"
								onclick="editquestionGridRecord();">回复</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="questionForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">标题：</td>
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_U.QUESTION_TITLE_LIKE" />
	    </td> 
	    <td style="width:70px;text-align:right;">回复状态：</td> 
	    <td style="width:156px;"> 
			<select defaultemptytext="请选择回复状态" class="eve-input" name="Q_U.REPLY_FLAG_=">
				<option value="">请选择回复状态</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>		
	    </td>
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('questionToolbar','questionGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('questionForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="questionGrid" fitcolumns="true" toolbar="#questionToolbar"
			method="post" idfield="QUESTION_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="complainController.do?questionDatagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'QUESTION_ID',hidden:true">question_ID</th>					
					<th data-options="field:'QUESTION_NAME',align:'left'" width="8%">姓名</th>
					<th data-options="field:'QUESTION_ADDRESS',align:'left'" width="15%">堵点单位</th>
					<th data-options="field:'QUESTION_TITLE',align:'left'" width="35%">问题征集标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">提议时间</th>
					<th data-options="field:'REPLY_FLAG',align:'left'" width="8%" formatter="formatState">回复状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="17%">回复时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
