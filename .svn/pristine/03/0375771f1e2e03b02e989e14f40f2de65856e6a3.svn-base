<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除调查主题列表记录
	 */
	function deleteosTopicGridRecord() {
		AppUtil.deleteDataGridRecord("osTopicController.do?multiDel",
				"osTopicGrid");
	};
	/**
	 * 编辑调查主题列表记录
	 */
	function editosTopicGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("osTopicGrid");
		if (entityId) {
			showosTopicWindow(entityId);
		}
	}
	/**
	* 调查主题问题列表
	*/
	function listOsQuestionGridRecord(){
		var entityId = AppUtil.getEditDataGridRecord("osTopicGrid");
		if (entityId) {
			 $.dialog.open("osQuestionController.do?view&topicId=" + entityId, {
				title : "调查问题列表",
				width : "1000px",
				height : "540px",
				lock : true,
				resize : false
			}, false); 
		}
	}
	
	/**
	 * 显示调查主题信息对话框
	 */
	function showosTopicWindow(entityId) {
		$.dialog.open("osTopicController.do?info&entityId=" + entityId, {
			title : "编辑主题信息",
			width : "650px",
			height : "450px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("osTopicToolbar");
	});
function reloadosTopicList(){
	$("#osTopicGrid").datagrid("reload");
}
/**
 * 格式化
 */
function formatState(val,row){
	if(val=="0"){
		return "<font color='red'>未发布</font>";
	}else if(val=="1"){
		return "<font color='green'>已发布</font>";
	}else{
		return "<font color='blue'>已结束</font>";
	}
};
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="osTopicToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_OSTOPIC"
								iconcls="eicon-plus" plain="true"
								onclick="showosTopicWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_OSTOPIC"
								iconcls="eicon-pencil" plain="true"
								onclick="editosTopicGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_OSTOPIC"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteosTopicGridRecord();">删除</a>
							<a href="#"
								class="easyui-linkbutton" reskey="LIST_OSQUESTION"
								iconcls="eicon-list" plain="true"
								onclick="listOsQuestionGridRecord();">问题列表</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="osTopicForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">调查主题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.TITLE_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('osTopicToolbar','osTopicGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('osTopicForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="osTopicGrid" fitcolumns="true" toolbar="#osTopicToolbar"
			method="post" idfield="TOPICID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="osTopicController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'TOPICID',hidden:true">TOPICID</th>	
					<th data-options="field:'TITLE',align:'left'" width="60%">调查主题</th>
					<th data-options="field:'STARTDATE',align:'left'" width="13%">开始日期</th>						
					<th data-options="field:'ENDDATE',align:'left'" width="13%">结束日期</th>
					<th data-options="field:'STATE',align:'left'" width="11%" formatter="formatState">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
