<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	 * 删除写信求诉列表记录
	 */
	function deleteLetterGridRecord() {
		AppUtil.deleteDataGridRecord("letterController.do?multiDel",
				"LetterGrid");
	};
	/**
	 * 编辑写信求诉列表记录
	 */
	function editLetterGridRecord() {
		var entityId = AppUtil.getEditDataGridRecord("LetterGrid");
		if (entityId) {
			showBusLetterWindow(entityId);
		}
	}

	/**
	 * 显示写信求诉信息对话框
	 */
	function showBusLetterWindow(entityId) {
		$.dialog.open("letterController.do?info&entityId=" + entityId, {
			title : "回复咨询信息",
			width : "800px",
			height : "550px",
			lock : true,
			resize : false
		}, false);
	};

	$(document).ready(function() {
		//AppUtil.initAuthorityRes("LetterToolbar");
	});
function reloadLetterList(){
	$("#LetterGrid").datagrid("reload");
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
	
//空数据，横向滚动
$('#LetterGrid').datagrid({
	onLoadSuccess: function(data){
		if(data.total==0){
			var dc = $(this).data('datagrid').dc;
	        var header2Row = dc.header2.find('tr.datagrid-header-row');
	        dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
        }
	}
});
</script>
<div class="easyui-layout eui-jh-box" fit="true">
	<div region="center">
		<!-- =========================查询面板开始========================= -->
		<div id="LetterToolbar">
			<!--====================开始编写隐藏域============== -->
			<!--====================结束编写隐藏域============== -->
			<div class="right-con">
				<div class="e-toolbar" style="z-index: 1111;top: 0px;left: 0px;">
					<div class="e-toolbar-ct">
						<div class="e-toolbar-overflow">
							<a href="#"
								class="easyui-linkbutton" reskey="EDIT_REPLY"
								iconcls="eicon-reply" plain="true"
								onclick="editLetterGridRecord();">回复</a> <a href="#"
								class="easyui-linkbutton" reskey="DEL_CONSULT"
								iconcls="eicon-trash-o" plain="true"
								onclick="deleteLetterGridRecord();">删除</a>
						</div>
					</div>
				</div>
			</div>
			<form action="#" name="LetterForm"> 
    <table class="dddl-contentBorder dddl_table" style="background-repeat:repeat;width:100%;border-collapse:collapse;"> 
     <tbody>
      <tr style="height:28px;">
        <td style="width:70px;text-align:right;">信件编号：</td> 
        <td style="width:128px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:95%;" name="Q_T.LETTER_CODE_LIKE" />
	    </td>  
        <td style="width:70px;text-align:right;">信件标题：</td> 
        <td style="width:128px;"> 
			<input class="eve-input" type="text" maxlength="50" style="width:95%;" name="Q_T.LETTER_TITLE_LIKE" />
	    </td> 
	    <td style="width:70px;text-align:right;">信件类型：</td> 
	    <td style="width:128px;"> 
			<select defaultemptytext="请选择信件类型" class="eve-input" name="Q_T.LETTER_TYPE_=">
				<option value="">请选择信件类型</option>
				<option value="咨询">咨询</option>
				<option value="举报">举报</option>
				<option value="投诉">投诉</option>
				<option value="建议">建议</option>
				<option value="感谢">感谢</option>
			</select>		
	    </td> 
	    <td style="width:70px;text-align:right;">回复状态：</td> 
	    <td style="width:128px;"> 
			<select defaultemptytext="请选择回复状态" class="eve-input" name="Q_T.REPLY_FLAG_=">
				<option value="">请选择回复状态</option>
				<option value="1">是</option>
				<option value="0">否</option>
			</select>		
	    </td>
	   
	   <td colspan="2"><input type="button" value="查询" class="eve-button" onclick="AppUtil.gridDoSearch('LetterToolbar','LetterGrid')" />
	       <input type="button" value="重置" class="eve-button" onclick="AppUtil.gridSearchReset('LetterForm')" />
	    </td> 
      </tr> 
     </tbody>
    </table> 
   </form> 
		</div>
		<!-- =========================查询面板结束========================= -->
		<!-- =========================表格开始==========================-->
		<table class="easyui-datagrid" rownumbers="true" pagination="true" striped="true"
			id="LetterGrid" fitcolumns="true" toolbar="#LetterToolbar"
			method="post" idfield="LETTER_ID" checkonselect="true" 
			selectoncheck="true" fit="true" border="false" 
			nowrap="false" singleSelect="false"
			url="letterController.do?datagrid">
			<thead>
				<tr>
					<th field="ck" checkbox="true"></th>
					<th data-options="field:'LETTER_ID',hidden:true">LETTER_ID</th>
					<th data-options="field:'LETTER_CODE',align:'left'" width="12%">信件编号</th>
					<th data-options="field:'LETTER_TITLE',align:'left'" width="25%">标题</th>
					<th data-options="field:'LETTER_TYPE',align:'left'" width="8%">信件类型</th>
					<th data-options="field:'LETTER_DEPT',align:'left'" width="15%">部门</th>		
					<th data-options="field:'LETTER_NAME',align:'left'" width="8%">姓名</th>
					<th data-options="field:'LETTER_ISPUBLIC',align:'left'" width="8%" formatter="formatState">是否公开</th>
					<th data-options="field:'CREATE_TIME',align:'left'" width="13%">来信时间</th>   
					<th data-options="field:'REPLY_FLAG',align:'left'" width="8%" formatter="formatState">回复状态</th>
					<th data-options="field:'REPLY_TIME',align:'left'" width="13%">回复时间</th>   
				</tr>
			</thead>
		</table>
		<!-- =========================表格结束==========================-->
	</div>
</div>
