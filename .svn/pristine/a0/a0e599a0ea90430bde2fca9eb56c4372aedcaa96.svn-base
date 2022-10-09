<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除问题解答列表记录
	 */
	function deletefaqProblemGridRecord() {
		AppUtil.deleteDataGridRecord("faqProblemController.do?multiDel",
				"faqProblemGrid");
	};
	/**
	 * 编辑问题解答列表记录
	 */
	function editfaqProblemGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("faqProblemGrid");
		if (entityId) {
			showfaqProblemWindow(entityId);
		}
	}

	/**
	 * 显示问题解答信息对话框
	 */
	function showfaqProblemWindow(entityId) {
		$.dialog.open("faqProblemController.do?info&entityId=" + entityId, {
			title : "编辑问题信息",
			width : "600px",
			height : "260px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("faqProblemToolbar");
	});
function reloadfaqProblemList(){
	$("#faqProblemGrid").datagrid("reload");
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
<div class="easyui-layout" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="faqProblemToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="ADD_OSTOPIC"
								iconcls="icon-note-add" plain="true"
								onclick="showfaqProblemWindow();">新增</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_OSTOPIC"
								iconcls="icon-note-edit" plain="true"
								onclick="editfaqProblemGridRecord();">编辑</a> 
							<a href="#"
								class="easyui-linkbutton" reskey="DEL_OSTOPIC"
								iconcls="icon-note-delete" plain="true"
								onclick="deletefaqProblemGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="faqProblemForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;"> 
        <td style="width:70px;text-align:right;">问题标题：</td> 
        <td style="width:156px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:96%;" name="Q_T.PROBLEM_TITLE_LIKE"/>
	    </td> 
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('faqProblemToolbar','faqProblemGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('faqProblemForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true"
			id="faqProblemGrid" fitcolumns="true" toolbar="#faqProblemToolbar"
			method="post" idfield="PROBLEM_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="faqProblemController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'PROBLEM_ID',hidden:true" width="80">PROBLEM_ID</th>	
					<th data-options="field:'PROBLEM_TITLE',align:'left'" width="280">问题标题</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="110">创建时间</th>	
					<th data-options="field:'STATUS',align:'left'" width="110" formatter="formatState">状态</th>
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
